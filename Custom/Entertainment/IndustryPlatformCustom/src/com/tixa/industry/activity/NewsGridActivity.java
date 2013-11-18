package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import javax.sql.CommonDataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.NewsCommonAdapter;
import com.tixa.industry.adapter.NewsGridAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Article;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.Modular;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.BannerView;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.PushListView;
import com.tixa.industry.widget.PushListView.OnRefreshListener;
import com.tixa.industry.widget.TopBar;

public class NewsGridActivity extends Fragment {

	private FragmentActivity context;
	private View view;
	private NewsGridAdapter adapter;
	private TopBar topbar;
	private PushListView listView;
	private ArrayList<Article> data;
	private ArrayList<ArrayList<Article>> myData;
	private IndustryApplication application;
	private PageConfig config;

	private HttpApi api;
	private String appID;

	private boolean isDestroy = false;
	private ArrayList<Advert> adList;

	private ArrayList<Modular> modularList;
	private ArrayList<View> radio;
	private RadioButton[] radioButtons = null;
	private IndexData indexData;

	private int pageStyle;
	private int pageStatus;
	private int secmenuStyle;
	private int secmenuShow;
	private HorizontalScrollView horizontalScrollView;
	private RadioGroup radioGroup;

	private String titleName;
	private String modularName;
	private String typeID;
	private boolean isNav = false;
	private int lastID = 0;
	private int firstID = 0;
	private int listStyle;
	private final static String NEWS_LIST = "news_list.tx";
	private TopBarUtil util;
	
	private int dataNum = 40;
	private int rowNum = Data.DATA_NUM;
	
/*	
	private int dataNum = 9;
	private int rowNum = 5;
	*/
	
	//private int 
	private BannerView banner;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private ProgressBar seeMore_progressBar;
	private TextView loadView;
	private boolean isHttpRunning = false;
	private TextView dialogText;


	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			ArrayList<Article> tempData = (ArrayList<Article>) msg.obj;
			if (isDestroy)
				return;
			switch (msg.what) {

			case Data.NONETWORK:
				listView.onRefreshComplete();
				isHttpRunning = false;
				if (seeMore_progressBar != null) {
					seeMore_progressBar.setVisibility(View.GONE);
					loadView.setText("查看更多");
				}
				if (myData == null || myData.size() == 0) {
					view_loading.noNetWork(new OnClickListener() {
						@Override
						public void onClick(View v) {
							view_loading.loading();
							upData();
						}
					});
				} else {
					T.shortT(context,
							getResources().getString(R.string.nonetwork));
				}

				break;

			case Data.NODATA:
				listView.onRefreshComplete();
				if (myData == null || myData.size() == 0) {
					view_loading.showNodataView();
				}
				break;

			case Data.CLOSE_POPUP:
				dialogText.setVisibility(View.GONE);
				break;

			case Data.FULLDATA:
				isHttpRunning = false;
				view_loading.close();
				if (tempData != null && tempData.size() > 0) {
					lastID = (int) tempData.get(0).getId();
					data.addAll(0, tempData);
					myData = formatData(data);
					
					dialogText.setVisibility(View.VISIBLE);
					dialogText.setText("您有" + tempData.size() + "条更新");
					postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(Data.CLOSE_POPUP);
						}
					}, 3000);

					saveToLocal(data, NEWS_LIST);
				}

				if (myData.size() %2 == 0 && myData.size() >= rowNum ) {
					if (loadingLayout == null) {
						initFooter();
						listView.addFooterView(loadingLayout);
					} else {
						loadView.setText("查看更多");
						loadView.setVisibility(View.VISIBLE);
					}
				} else {
					if (loadingLayout != null) {
						listView.removeFooterView(loadingLayout);
						loadingLayout = null;
					}
				}
				adapter.setData(myData);
				adapter.setCount(myData.size() > rowNum ? rowNum : myData
						.size());
				adapter.notifyDataSetChanged();
				if (msg.arg1 == 1) {
					listView.onRefreshComplete(true, 0);
				} else {
					listView.onRefreshComplete(false, myData.size());
				}

				break;
			case Data.MOREDATA:
				isHttpRunning = false;
				view_loading.close();
				
				if (tempData != null && tempData.size() > 0) {
					if (data == null)
						data = new ArrayList<Article>();
					data.addAll(tempData);
					myData  =  formatData(data);
				}
				
				seeMore_progressBar.setVisibility(View.GONE);
				
				adapter.setCount(myData.size());
				adapter.setData(myData);
				adapter.notifyDataSetChanged();

				if (tempData != null && tempData.size() == 0) {
					loadView.setVisibility(View.GONE);
				} else if (tempData != null && tempData.size() >= 0) {
					loadView.setText("查看更多");
				}
				break;
			case Data.LOCALDATA:

				if (tempData == null || tempData.size() == 0) {
					myData.clear();
					upData(); // 本地数据为空则从网络获取
				} else {
					view_loading.close();
					data = tempData;
					myData = formatData(data);

					if (myData.size() %2 == 0 && myData.size() >= rowNum) {
						if (loadingLayout == null) {
							initFooter();
							listView.addFooterView(loadingLayout);
						} else {
							loadView.setText("查看更多");
							loadView.setVisibility(View.VISIBLE);
						}
					} else {
						if (loadingLayout != null) {
							listView.removeFooterView(loadingLayout);
							loadingLayout = null;
						}
					}

					// adapter = new NewsCommonAdapter(context, myData
					// ,listStyle);
					// listView.setAdapter(adapter);
					adapter = new NewsGridAdapter(context, myData, listStyle,
							rowNum );
					listView.setAdapter(adapter);
					/*
					 * adapter.setCount(myData.size() > rowNum ? rowNum : myData
					 * .size()); adapter.notifyDataSetChanged();
					 */
				}
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
		view = inflater.inflate(R.layout.listview, null);
		initData();
		initView();
		return view;

	}
	
	public ArrayList<ArrayList<Article>> formatData(ArrayList<Article> arts) {
	
		ArrayList<ArrayList<Article>> result = new ArrayList<ArrayList<Article>>();
		if(arts == null || arts.size() == 0) {
			return result;
		}
		int j = 0;
		ArrayList<Article> art = null;
		for(int i = 0;i<arts.size();i++) {
			if(art == null) {
				art = new ArrayList<Article>();
			}
			Article a = arts.get(i);
			art.add(a);
			j ++ ;
			if(j == 2 || arts.size() == i+1) {
				result.add(art);
				art = null;
				j = 0;
			}
		}
	
		return result;
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
		adList = indexData.getAdList();
		PageConfigParser p = new PageConfigParser(context,
				"layout/NewsLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
		listStyle = config.getBlock().getShowType();
		secmenuStyle = config.getBlock().getSecmenuType();
		secmenuShow = config.getBlock().getSecmenuShow();
		modularList = indexData.getModularList();

		L.e("secmenuShow is" + secmenuShow);

		if (secmenuShow == 1 && modularList.size() > 0) { // 如果显示二级头，则默认首页选中第一个栏目
			typeID = modularList.get(0).getChildType();
		}
	}

	private void initBanner() {
		if (pageStatus == ConfigData.SHOW_AD && adList.size() > 0) {// 显示广告
			banner = new BannerView(context, adList, pageStyle);
			banner.show();
			listView.addHeaderView(banner);
		}
	}

	private void initView() {

		horizontalScrollView = (HorizontalScrollView) view
				.findViewById(R.id.horizontalScrollView);
		if (secmenuShow == 0) {
			horizontalScrollView.setVisibility(View.GONE);
		} else {
			horizontalScrollView.setVisibility(View.VISIBLE);
			initHeadView1(secmenuStyle);
		}
		topbar = (TopBar) view.findViewById(R.id.topbar);
		listView = (PushListView) view.findViewById(R.id.list);

		//listView.setOnItemClickListener(this);
		listView.setDivider(null);
		listView.setDividerHeight(CommonUtil.dip2px(context, 10));
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		dialogText = (TextView) view.findViewById(R.id.dialogText);

		if (StrUtil.isEmpty(modularName)) {
			titleName = "资讯";
		} else {
			titleName = modularName;
		}

		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				false, navi);
		util.showConfig();

		data = getFromLocal(NEWS_LIST);

		if (data == null) {
			data = new ArrayList<Article>();
		} else {
			view_loading.close();
		}
		
		myData = formatData(data);
		
		
		initBanner();

		adapter = new NewsGridAdapter(context, myData, listStyle, rowNum);

		listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/" + typeID
				+ "/" + NEWS_LIST);

		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				upData();
			}
		});

		if (myData == null || myData.size() == 0) {
			upData();
		}
		if (myData.size() > 0) {
			lastID = (int) data.get(0).getId();
			firstID = (int) data.get(data.size() - 1).getId();
		}

		initListBottom();

	}

	// 取得本地数据
	private void getLocalData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Article> obj = getFromLocal(NEWS_LIST);
				Message msg = new Message();
				msg.obj = obj;
				msg.what = Data.LOCALDATA;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private void initListBottom() {
		if (myData.size() %2 == 0 && myData.size() >= rowNum) {
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

				if (adapter.getCount() + rowNum <= myData.size()) {
					adapter.setCount(adapter.getCount() + rowNum);
					adapter.notifyDataSetChanged();
				} else {

					if (adapter.getCount() == myData.size()) {
						if (isHttpRunning) {
							return;
						}

						seeMore_progressBar.setVisibility(View.VISIBLE);
						loadView.setText("加载中..");
						new Thread(new Runnable() {
							@Override
							public void run() {
								isHttpRunning = true;
								getHistory();
							}
						}).start();
					} else {
						adapter.setCount(myData.size());
						adapter.notifyDataSetChanged();
					}
				}
			}
		});
	}

	/**
	 * 动态加载二级
	 * 
	 * @param secmenuStyle
	 */
	private void initHeadView1(int secmenuStyle) {
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		radio = new ArrayList<View>();
		radioButtons = new RadioButton[modularList.size()];
		for (int i = 0; i < modularList.size(); i++) {
			RadioButton radioButton = new RadioButton(context);
			radioButton.setId(i + 1);
			radioButton.setBackgroundResource(R.drawable.radio);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			radioButton.setLayoutParams(lp);
			radioButton.setPadding(0, 0, 0, 0);
			radioButton.setText(modularList.get(i).getModularName());
			radioButton.setTextColor(getResources().getColor(R.color.black));
			radioButton.setButtonDrawable(R.color.transparent);
			radioButton.setTextSize(16);
			
			radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) {
						buttonView.setTextColor(getResources().getColor(R.color.white));
					}else{
						buttonView.setTextColor(getResources().getColor(R.color.black));
					}
					
				}
			});
			
			
			radioButton.setGravity(Gravity.CENTER);
			if (i == 0) {
				radioButton.setChecked(true);
			}

			final String typeId = modularList.get(i).getChildType();
			radioButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try{
						typeID = typeId;
					}catch(Exception e) {
						typeID = 0+"";
					}
					view_loading.loading();
					getLocalData();
				}
			});
			radioGroup.addView(radioButton);
		}
	}

	private ArrayList<Article> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + typeID + "/"
				+ fileName;
		L.e("-----getFromLocal typeID = " + typeID);
		ArrayList<Article> data = (ArrayList<Article>) FileUtil
				.getFile(filePath);
		return data;
	}

	private void saveToLocal(ArrayList<Article> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + typeID + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upData() {
		if (data != null && data.size() > 0) {
			lastID = (int) data.get(data.size() - 1).getId();
			firstID = (int) data.get(0).getId();
		}
		L.e("-----upData typeID = " + typeID);

		api.getArtices(dataNum, -1, firstID, typeID, new RequestListener() {

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
				ArrayList<Article> tempData = new ArrayList<Article>();
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("articleList")) {
							res = json.optString("articleList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {

							JSONArray cjar = json.optJSONArray("articleList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								Article info = new Article(temp);
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

	private void getHistory() {

		if (myData != null && myData.size() > 0) {
			lastID = (int) data.get(data.size() - 1).getId();
			firstID = (int) data.get(0).getId();
		}

		api.getArtices(dataNum, 1, lastID, typeID, new RequestListener() {

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
				ArrayList<Article> tempData = new ArrayList<Article>();

				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("articleList")) {
							res = json.optString("articleList");
						}
						if (res.equals("none")) {
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {
							JSONArray cjar = json.optJSONArray("articleList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								Article info = new Article(temp);
								tempData.add(info);
							}
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
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


}
