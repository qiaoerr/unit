package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.SortAdapter;
import com.tixa.industry.api.DianpingApi;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.GoodsCata;
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
import com.tixa.industry.widget.TopBar;

/**
 * 产品分类
 * 
 */
public class SortActivity extends Fragment implements OnItemClickListener {

	private FragmentActivity context;
	private View view;
	private SortAdapter adapter;
	private TopBar topbar;
	private PushListView listView;
	private ArrayList<GoodsCata> myData;
	private ArrayList<GoodsCata> myData_dianping;
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
	// private int rowNum = Data.DATA_NUM;
	private int rowNum = 1000;
	private BannerView banner;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private ProgressBar seeMore_progressBar;
	private TextView loadView;
	private boolean isHttpRunning = false;
	private TextView dialogText;
	private long showType;
	// private DianpingApi dApi;
	private Map<String, String> map;
	private int count;
	private final static int COMPLETE = 1000;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ArrayList<GoodsCata> tempData = (ArrayList<GoodsCata>) msg.obj;
			if (isDestroy)
				return;
			switch (msg.what) {

			case Data.NONETWORK:
				listView.onRefreshComplete();
				isHttpRunning = false;
				if (myData == null || myData.size() == 0) {
					view_loading.noNetWork(new OnClickListener() {
						@Override
						public void onClick(View v) {
							view_loading.loading();
							upData();
							System.out.println("");
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

			case COMPLETE:
				isHttpRunning = false;
				view_loading.close();
				listView.onRefreshComplete();
				myData.addAll(myData_dianping);
				adapter.setM(myData.size() - myData_dianping.size());
				adapter.notifyDataSetChanged();
				saveToLocal(myData, SORT_LIST);
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
		showType = getArguments().getLong("showType");
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
		// indexData.get
		// dApi = new DianpingApi();
		myData_dianping = new ArrayList<GoodsCata>();
	}

	private void initBanner() {
		if (pageStatus == ConfigData.SHOW_AD && adList.size() > 0) {// 显示广告
			banner = new BannerView(context, adList, pageStyle);
			banner.show();
			listView.addHeaderView(banner);
		}
	}

	private void initView() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		listView = (PushListView) view.findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		listView.setDivider(null);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();
		myData = getFromLocal(SORT_LIST);
		if (myData == null) {
			myData = new ArrayList<GoodsCata>();
		} else {
			view_loading.close();
		}
		adapter = new SortAdapter(context, myData, rowNum, showType);

		initBanner();

		listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/" + typeID
				+ "/" + SORT_LIST);

		// listView.setonRefreshListener(new OnRefreshListener() {
		//
		// @Override
		// public void onRefresh() {
		// upData();
		// }
		// });

		if (myData == null || myData.size() == 0) {
			upData();
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

	private ArrayList<GoodsCata> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + typeID + "/"
				+ fileName;
		ArrayList<GoodsCata> data = (ArrayList<GoodsCata>) FileUtil
				.getFile(filePath);
		return data;
	}

	private void saveToLocal(ArrayList<GoodsCata> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + typeID + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upData() {
		if (myData != null && myData.size() > 0) {
			lastID = (int) myData.get(myData.size() - 1).getId();
			// firstID = (int) myData.get(0).getId();
			firstID = 0;
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
							// myData
							myData.clear();
							myData.addAll(0, tempData);
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.FULLDATA;
							// handler.sendMessage(msg);
						}

					}
				} catch (Exception e) {
					L.e(e.toString());
					handler.sendEmptyMessage(Data.NONETWORK);
				}
				count++;
				if (count % 2 == 0) {
					handler.sendEmptyMessage(COMPLETE);
				}
			}
		});
		new Thread() {
			public void run() {
				map = new HashMap<String, String>();
				map.put("format", "json");
				try {
					String response = DianpingApi.requestApi(
							InterfaceURL.categories_with_businesses,
							Constants.appkey_dianping,
							Constants.appSecret_dianping, map);
					L.d("http", response);
					JSONObject json = new JSONObject(response);
					JSONArray array = json.optJSONArray("categories");
					for (int i = 0; i < array.length(); i++) {
						JSONObject temObject = array.optJSONObject(i);
						GoodsCata info = new GoodsCata();
						info.setCataName(temObject.getString("category_name"));// set
						info.setIsExistChild(2);//
						JSONArray jsonArray = temObject
								.optJSONArray("subcategories");
						ArrayList<String> childCateList = new ArrayList<String>();
						for (int j = 0; j < jsonArray.length(); j++) {
							String t = jsonArray.optJSONObject(j).optString(
									"category_name");
							childCateList.add(t);
							// System.out.println(t);
						}
						info.setChildCataName(childCateList.toString()
								.replace("[", "").replace("]", "")
								.replace(",", "/"));
						info.setChildCateList(childCateList);
						myData_dianping.add(info);
					}
				} catch (JSONException e) {

				}
				count++;
				if (count % 2 == 0) {
					handler.sendEmptyMessage(COMPLETE);
				}
			};
		}.start();

	}

	private void getHistory() {

		if (myData != null && myData.size() > 0) {
			lastID = (int) myData.get(myData.size() - 1).getId();
			firstID = (int) myData.get(0).getId();
		}

		api.getGoodsCata(rowNum, 1, lastID, new RequestListener() {

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
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {
							JSONArray cjar = json.optJSONArray("goodsCataList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								GoodsCata info = new GoodsCata(temp);
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

		for (int i = 0; i < myData.size(); i++) {
			if (position == i) {
				myData.get(i).setChecked(true);
			} else {
				myData.get(i).setChecked(false);
			}
		}
		Bundle args = new Bundle();
		args.putSerializable("cataData", myData);
		Fragment fragment = new ProductCataListActivity();
		fragment.setArguments(args);
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

}
