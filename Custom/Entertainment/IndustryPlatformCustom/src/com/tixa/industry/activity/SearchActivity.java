package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CollectAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AndroidUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.LXProgressDialog;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.TopBar;

public class SearchActivity extends Fragment implements OnItemClickListener {

	private Activity context;
	private View view;
	private CollectAdapter adapter;
	private TopBar topbar;
	private ListView listView;
	private int rowNum = Data.DATA_NUM;
	private ArrayList<Goods> myData;
	private IndustryApplication application;
	private PageConfig config;
	private EditText edit;
	private ImageView delBtn;
	private LinearLayout search_layout;
	private String searchText;
	private Button cancelBtn;
	private LinearLayout layout_cancel;
	private HttpApi api;
	private String appID;
	private boolean isDestroy = false;
	private ArrayList<View> mListViews;
	private ArrayList<Advert> adList;
	private IndexData indexData;
	private View radioGroup;
	private int pageStyle;
	private int pageStatus;
	private int secmenuStyle;
	private int secmenuShow;
	private String titleName;
	private String modularName;
	private boolean isNav = false;
	private int lastID = 0;
	private int firstID = 0;
	private String search;
	private TopBarUtil util;
	private int listStyle;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private ProgressBar seeMore_progressBar;
	private LXProgressDialog pd = null;
	private TextView loadView;
	
	private Handler handler = new Handler() {		
		@Override
		public void handleMessage(Message msg) {
			if (pd != null)
				pd.dismiss();
			if (isDestroy)
				return;
			switch (msg.what) {
			case Data.NONETWORK:
				view_loading.noNetWork(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						view_loading.loading();
						upData();						
					}
				});
				//T.shortT(context, getResources().getString(R.string.nonetwork));
				break;
	
			case Data.FULLDATA:
				view_loading.close();
				ArrayList<Goods> tempData = (ArrayList<Goods>) msg.obj;
				myData = tempData;		
				initListBottom();
				adapter = new CollectAdapter(context, myData ,listStyle,rowNum);
				listView.setAdapter(adapter);
				break;
			case Data.NODATA:
				view_loading.showNodataView();				
				break;
			default:
				break;
			}

		}
	};

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.listview_search, null);
		modularName = getArguments().getString("modularName");
		isNav = getArguments().getBoolean("isNav");
		search = getArguments().getString("searchKey");
		initData();

		initView();
		if(StrUtil.isNotEmpty(search)) {
			edit.setText(search);
			upData();
			search = "";
		}
		return view;

	}

	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/SearchLayout.xml");
		config = p.parser();
		listStyle = config.getBlock().getShowType();
	}

	private void initData() {
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		indexData = application.getMainData();
		adList = indexData.getAdList();
		getPageConfig();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
		secmenuStyle = config.getBlock().getSecmenuType();
		secmenuShow = config.getBlock().getSecmenuShow();
	}



	private void initView() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		listView = (ListView) view.findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		listView.setDivider(null);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		view_loading.close();
		if(StrUtil.isEmpty(modularName)){
			titleName="搜索";
		}else{
			titleName=modularName;
		}
		
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName, getFragmentManager(), context,
				application.getTemplateId() , false,navi);
		util.showConfig();
		
		search_layout =  (LinearLayout) view.findViewById(R.id.search_layout);
		layout_cancel =  (LinearLayout) view.findViewById(R.id.layout_cancel);
		cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
		edit = (EditText) view.findViewById(R.id.EditText_Search);
		edit.setHint("搜索");
		delBtn = (ImageView) view.findViewById(R.id.btn_del_search);
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				if (StrUtil.isNotEmpty(edit.getText().toString())) {
					delBtn.setVisibility(View.VISIBLE);
					layout_cancel.setVisibility(View.VISIBLE);
					
				} else {
					delBtn.setVisibility(View.GONE);
					layout_cancel.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		delBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				edit.setText("");
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AndroidUtil.collapseSoftInputMethod(context, edit);

				upData();				
			}
		});
	}
	
	private void initListBottom() {		
		if (myData.size() >= rowNum) {
			if (loadingLayout == null) {
				initFooter();
				listView.addFooterView(loadingLayout);
			} else {
				loadView.setText("查看更多");
				loadView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	
	private void initFooter() {
		loadingLayout = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.seemorelayout, null);
		seeMore_progressBar = (ProgressBar) loadingLayout
				.findViewById(R.id.seeMore_progressBar);
		loadView = (TextView) loadingLayout.findViewById(R.id.seeMoreText);
		loadView.setText("查看更多");
		loadingLayout.setOnClickListener(new OnClickListener() {
		
			
			@Override
			public void onClick(View v) {
			
				if (adapter.getCount() + rowNum < myData.size()) {
					adapter.setCount(adapter.getCount() + rowNum);
					adapter.notifyDataSetChanged();
					
				} else {
									
					adapter.setCount(myData.size());
					adapter.notifyDataSetChanged();
					if (loadingLayout != null) {
						listView.removeFooterView(loadingLayout);
						loadingLayout = null;
					}				
				}
			}
		});
	}
	

	private void upData() {
		
		view_loading.loading();
		
		searchText= edit.getText().toString().trim();
		if(StrUtil.isNotEmpty(search)&&StrUtil.isEmpty(searchText)) {
			searchText = search;
		}
		
		L.d("test", "searchText="+searchText);
		if (myData != null && myData.size() > 0) {
			lastID = (int) myData.get(myData.size() - 1).getId();
			firstID = (int) myData.get(0).getId();
		}
		api.searchGoods(searchText,2000, -1, 0 , new RequestListener() {
		
			@Override
			public void onIOException(IOException e) {
				T.shortT(context, "未知错误:" + e.getMessage());
			}

			@Override
			public void onError(TixaException e) {
				L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
				handler.sendEmptyMessage(Data.NONETWORK);
			}

			@Override
			public void onComplete(String response) {
				ArrayList<Goods> tempData = new ArrayList<Goods>();
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("goodsBuyInfoList")) {
							res = json.optString("goodsBuyInfoList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
							L.e("--------------------");
							L.e("res=" + res);
							JSONArray cjar = json
									.optJSONArray("goodsBuyInfoList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								Goods info = new Goods(temp);
								tempData.add(info);
							}
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.FULLDATA;
							handler.sendMessage(msg);
						}

					}
				} catch (Exception e) {
					L.e(e.toString());
					handler.sendEmptyMessage(Data.NONETWORK);
				}

			}
		});
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		position = position - listView.getHeaderViewsCount();
		Intent intent = new Intent(context, SupplyDetailAct.class);
		intent.putExtra("goods", myData.get(position));
		startActivity(intent);
	}

}
