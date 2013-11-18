package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.SortAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.GoodsCata;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.BannerView;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.MyNoScrollGridView;
import com.tixa.industry.widget.TopBar;

/**
 * 产品分类(第二种)
 * 
 */
public class SortCataActivity extends Fragment implements OnItemClickListener{

	private FragmentActivity context;
	private View view;
	private CataAdapter adapter;
	private TopBar topbar;
	private MyNoScrollGridView listView;
	//private ArrayList<GoodsCata> myData;
	private IndustryApplication application;
	private PageConfig config;
	private HttpApi api;
	private String appID;
	private boolean isDestroy = false;
	private ArrayList<Advert> adList;
	private IndexData indexData;
	private int pageStyle;
	private int pageStatus;
	private String modularName;
	private String typeID;
	private int lastID = 0;
	private int firstID = 0;
	private boolean isNav = false;
	private TopBarUtil util;
	private final static String SORT_LIST = "sort_list.tx";
	private int rowNum = Data.DATA_NUM;
	private BannerView banner;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private LinearLayout container;
	private RadioGroup radioGroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private ArrayList<GoodsCata> firstCata;
	private ArrayList<GoodsCata> secondCata;
	private String parentCode;
	private LoadView sec_view_loading;
	private HashMap<String, ArrayList<GoodsCata>> map;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.FULLDATA:
				ArrayList<GoodsCata> cata = (ArrayList<GoodsCata>) msg.obj;
				firstCata = cata;				
				L.e("myData is "+firstCata);				
				if(firstCata != null  && firstCata.size()>0) {
					view_loading.close();
					sec_view_loading.loading();
					if(StrUtil.isEmpty(parentCode)){
						radioButton1.setText(firstCata.get(0).getCataName());
						if(firstCata.size() >1) {
							radioButton2.setText(firstCata.get(1).getCataName());
						}else{
							radioButton2.setVisibility(View.GONE);
						}
						getSecondCata(firstCata.get(0).getCataCode());
					}else{
						getSecondCata(parentCode);
					}
				}
				break;

			case Data.NODATA:
				view_loading.showNodataView();			
				break;
			
			case Data.NONETWORK:
				view_loading.noNetWork(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						view_loading.loading();
						upData();						
					}
				});
				
				break;
				
			case Data.MOREDATA:
				sec_view_loading.close();
				cata = (ArrayList<GoodsCata>) msg.obj;
				secondCata = cata;
				if(map == null) {
					map = new HashMap<String, ArrayList<GoodsCata>>();
				}
				map.put(parentCode,secondCata);
				
				adapter = new CataAdapter(context, secondCata, rowNum);
				listView.setAdapter(adapter);
				break;
			case Data.NOMOREDATA:
				sec_view_loading.showNodataView();
			
			break;
			
			case Data.LOCALDATA:
				sec_view_loading.close();
				cata = (ArrayList<GoodsCata>) msg.obj;
				secondCata = cata;
				adapter = new CataAdapter(context, secondCata, rowNum);
				listView.setAdapter(adapter);				
				break;
			
			case Data.NOMORENETWORK:
				sec_view_loading.noNetWork(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						view_loading.loading();
						getSecondCata(parentCode);				
					}
				});
			break;
			}
		}
		
	};

	@Override
	public void onDestroy() {
		isDestroy = true;
		map.clear();
		map = null;
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gridview_radiobutton, null);
		initData();
		initView(); // 显示布局
		return view;
	}

	private void initView() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		sec_view_loading = (LoadView) view.findViewById(R.id.loadView_);
		
		listView = (MyNoScrollGridView) view.findViewById(R.id.myGridView);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		listView.setOnItemClickListener(this);
		container = (LinearLayout) view.findViewById(R.id.container);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		
		if (pageStatus == ConfigData.SHOW_AD) { //展示广告
			banner = new BannerView(context, adList, pageStyle);		
			container.addView(banner, 0);		
			banner.show();
		}
		
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radioButton1:
					parentCode = firstCata.get(0).getCataCode();
					ArrayList<GoodsCata> catas = map.get(parentCode);
					sec_view_loading.loading();
					if(catas == null || catas.size()==0) {
						getSecondCata(parentCode);
					}else{
						Message msg = new Message();
						msg.obj = catas;
						msg.what = Data.LOCALDATA;
						handler.sendMessage(msg);
					}
					radioButton1.setTextColor(getResources().getColor(R.color.white));
					radioButton2.setTextColor(getResources().getColor(R.color.black));
					break;
				case R.id.radioButton2:
					parentCode = firstCata.get(1).getCataCode();
					catas = map.get(parentCode);
					sec_view_loading.loading();
					if(catas == null || catas.size()==0) {
						getSecondCata(parentCode);
					}else{
						Message msg = new Message();
						msg.obj = catas;
						msg.what = Data.LOCALDATA;
						handler.sendMessage(msg);
					}
					radioButton2.setTextColor(getResources().getColor(R.color.white));
					radioButton1.setTextColor(getResources().getColor(R.color.black));
					break;
				}
				
			}
		});
		
		if(map == null) {
			map = new HashMap<String, ArrayList<GoodsCata>>();
		}
		
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();
		if (firstCata == null) {
			firstCata = new ArrayList<GoodsCata>();
		} else {
			view_loading.close();
		}
		upData();
	}
	
	

	private void initData() {
		context = getActivity();
		modularName = getArguments().getString("modularName");
		typeID = getArguments().getString("typeID");
		isNav = getArguments().getBoolean("isNav");
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		indexData = application.getMainData();
		adList = indexData.getSubAdList();
		PageConfigParser p = new PageConfigParser(context,
				"layout/SortLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
	}
	
	private void getSecondCata(String parentCode) {
		api.getSubGoodsCata(parentCode, rowNum, -1, 0,
				new RequestListener() {

					@Override
					public void onIOException(IOException e) {
						T.shortT(context, "未知错误:" + e.getMessage());

					}

					@Override
					public void onError(TixaException e) {
						L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
						handler.sendEmptyMessage(Data.NOMORENETWORK);
					}

					@Override
					public void onComplete(String response) {
						ArrayList<GoodsCata> tempData = new ArrayList<GoodsCata>();
						try {
							if (StrUtil.isHttpException(response)) {
								handler.sendEmptyMessage(Data.NOMORENETWORK);
							} else {
								JSONObject json = new JSONObject(response);
								String res = "";
								if (json.has("goodsCataChildList")) {
									res = json.optString("goodsCataChildList");
									if (res.equals("none")) {
										handler.sendEmptyMessage(Data.NOMOREDATA);
									} else {
										JSONArray cjar = json
												.optJSONArray("goodsCataChildList");
										for (int i = 0; i < cjar.length(); i++) {
											JSONObject temp = cjar
													.optJSONObject(i);
											GoodsCata info = new GoodsCata(temp);
											tempData.add(info);
										}
										Message msg = new Message();
										msg.obj = tempData;
										msg.what = Data.MOREDATA;
										handler.sendMessage(msg);
									}
								}
							}
						} catch (Exception e) {
							L.e(e.toString());
							handler.sendEmptyMessage(Data.NOMORENETWORK);
						}
					}
				});
	}
	
	
	private void upData() {
		if (firstCata != null && firstCata.size() > 0) {
			lastID = (int) firstCata.get(firstCata.size() - 1).getId();
			firstID = (int) firstCata.get(0).getId();
		}
		api.getGoodsCata(rowNum, -1, firstID, new RequestListener() {

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
				ArrayList<GoodsCata> tempData = new ArrayList<GoodsCata>();
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("goodsCataList")) {
							res = json.optString("goodsCataList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);

						} else {
							JSONArray cjar = json.optJSONArray("goodsCataList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								GoodsCata info = new GoodsCata(temp);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodsCata goodsCata = secondCata.get(position);
		Bundle args = new Bundle();
		args.putString("appID", goodsCata.getAppID() + "");
		args.putString("cataCode", goodsCata.getCataCode());
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		Fragment fragment = new ProductListActivity();
		fragment.setArguments(args);
		transaction.add(R.id.fragment, fragment);
		transaction.addToBackStack(null);
		transaction.commit();		
	}
	
	class CataAdapter extends BaseAdapter {
		
		private ArrayList<GoodsCata> myData;
		private LayoutInflater mInflater;
		private AsyncImageLoader loader;
		private Context context;
		private int rowNum ;
		private int count ;
		
		public CataAdapter(Context context, ArrayList<GoodsCata> myData,int rowNum) {
			this.context = context;
			this.myData = myData;
			this.rowNum =  rowNum;
			this.count = rowNum;
			mInflater = LayoutInflater.from(context);
			loader = new AsyncImageLoader();
		}
		
		public void setCount(int count) {
			this.count = count;
		}
		
		
		@Override
		public int getCount() {
			return myData.size() < rowNum ? myData.size() : count;
		}

		@Override
		public Object getItem(int position) {
			return myData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			//if(convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.cata_button_item, null);
				holder.title = (TextView) convertView.findViewById(R.id.imagebutton);			
				convertView.setTag(holder);
		//	}else{
				
		//		holder = (ViewHolder) convertView.getTag();
		//	}
			
			holder.title.setText(myData.get(position).getCataName());			
			return convertView;
		}
		
		class ViewHolder {
			TextView title;
		}
		
		
	}
	
	

}
