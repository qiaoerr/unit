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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.JobAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.RecruitingInfo;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
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

public class JobActivity extends Fragment implements OnItemClickListener {

	private Activity context;
	private View view;
	private JobAdapter adapter;
	private TopBar topbar;
	private PushListView listView;
	private ArrayList<RecruitingInfo> myData;
	private IndustryApplication application;
	private PageConfig config;

	private HttpApi api;
	private String appID;
	private boolean isDestroy = false;


	private ArrayList<Advert> adList;
	private IndexData indexData;

	private int pageStyle;
	private int pageStatus;

	private String titleName;
	private String modularName;
	private String typeID;
	private boolean isNav = false;
	private int lastID = 0;
	private int firstID = 0;
	private TopBarUtil util;
	private final static String JOB_LIST= "job_list.tx";
	private int rowNum = Data.DATA_NUM;
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
			ArrayList<RecruitingInfo> tempData = (ArrayList<RecruitingInfo>) msg.obj;
			if (isDestroy)
				return;
			switch (msg.what) {
		
				case Data.NONETWORK:	
					listView.onRefreshComplete();				
					isHttpRunning = false;
					if(seeMore_progressBar != null) {
						seeMore_progressBar.setVisibility(View.GONE);
						loadView.setText("查看更多");	
					}
				
					if(myData == null || myData.size() ==0) {
						view_loading.noNetWork(new OnClickListener() {					
							@Override
							public void onClick(View v) {
								view_loading.loading();
								upData();						
							}
						});
					}else{				
						T.shortT(context, getResources().getString(R.string.nonetwork));
					}
				
				break;

			case Data.NODATA:
				listView.onRefreshComplete();
				if(myData == null || myData.size() == 0) {
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
					myData.addAll(0, tempData);			
					dialogText.setVisibility(View.VISIBLE);
					dialogText.setText("您有" + tempData.size() + "条更新");
					postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(Data.CLOSE_POPUP);
						}
					}, 3000);
					
					saveToLocal(myData, JOB_LIST);
				}
				
				if (myData.size() >= rowNum) {
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
					if (myData == null)
						myData = new ArrayList<RecruitingInfo>();
					myData.addAll(tempData);
				}
				seeMore_progressBar.setVisibility(View.GONE);
				adapter.setCount(myData.size());			
				adapter.notifyDataSetChanged();
				if (tempData != null && tempData.size() == 0) {
					loadView.setVisibility(View.GONE);
				} else if (tempData != null && tempData.size() >= 0) {
					loadView.setText("查看更多");
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
				"layout/JobLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
	}

	private void initBanner() {
		if(pageStatus == ConfigData.SHOW_AD && adList.size() > 0) {//显示广告
			banner = new BannerView(context, adList, pageStyle);
			banner.show();
			listView.addHeaderView(banner);
		}
	}




	private void initView() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		listView = (PushListView) view.findViewById(R.id.list);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		listView.setOnItemClickListener(this);
		listView.setDivider(null);
		if(StrUtil.isEmpty(modularName)){
			titleName="招聘";
		}else{
			titleName=modularName;			
		}
		
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		
		util = new TopBarUtil(isNav, naviStyle, topbar, titleName, getFragmentManager(), context,
				application.getTemplateId() , false,navi);
		util.showConfig();
		
		myData = getFromLocal(JOB_LIST);
		if (myData == null) {
			myData = new ArrayList<RecruitingInfo>();
		}else{
			view_loading.close();
		}
		
		initBanner();
		
		adapter = new JobAdapter(context, myData, rowNum);
		
		listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/"+ typeID+ "/"+JOB_LIST);
		
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
			lastID = (int) myData.get(0).getId();
			firstID = (int) myData.get(myData.size() - 1).getId();
		}

		initListBottom();
		
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


	private ArrayList<RecruitingInfo> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/"+ typeID
				+ "/"  + fileName;
		ArrayList<RecruitingInfo> data = (ArrayList<RecruitingInfo>) FileUtil
				.getFile(filePath);
		return data;
	}

	private void saveToLocal(ArrayList<RecruitingInfo> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/"+ typeID+ "/" ;
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void upData() {
		if (myData != null && myData.size() > 0) {
			lastID = (int) myData.get(myData.size() - 1).getId();
			firstID = (int) myData.get(0).getId();
		}
		api.getRecruitInfos(rowNum, -1, firstID, new RequestListener() {
		
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
				ArrayList<RecruitingInfo> tempData = new ArrayList<RecruitingInfo>();
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("recruitingInfoList")) {
							res = json.optString("recruitingInfoList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
		
							JSONArray cjar = json
									.optJSONArray("recruitingInfoList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								RecruitingInfo info = new RecruitingInfo(temp);
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
			lastID = (int) myData.get(myData.size() - 1).getId();
			firstID = (int) myData.get(0).getId();
		}

		api.getRecruitInfos(rowNum, 1, lastID, new RequestListener() {

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
				ArrayList<RecruitingInfo> tempData = new ArrayList<RecruitingInfo>();

				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("recruitingInfoList")) {
							res = json.optString("recruitingInfoList");
						}
						if (res.equals("none")) {
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {
				
							JSONArray cjar = json
									.optJSONArray("recruitingInfoList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								RecruitingInfo info = new RecruitingInfo(temp);
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


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		position = position - listView.getHeaderViewsCount();
		Intent intent = new Intent(context, JobDetailAct.class);
		intent.putExtra("recruitingInfo", myData.get(position));
		startActivity(intent);
	}

}
