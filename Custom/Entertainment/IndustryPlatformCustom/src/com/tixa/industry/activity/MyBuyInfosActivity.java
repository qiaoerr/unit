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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.BuySellInfoAdapter;
import com.tixa.industry.adapter.ProductCommonAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.BuyInfo;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Goods;
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

/**
 * 我的求购列表
  * @ClassName: MyBuyInfosActivity
  * @Description: 我的求购列表
  * @author shengy
  * @date 2013-7-22 下午5:53:40
  *
 */
public class MyBuyInfosActivity extends Fragment implements OnItemClickListener {

	private Activity context;
	private View view;
	private BuySellInfoAdapter adapter;
	private TopBar topbar;
	private PushListView listView;
	private ArrayList<BuyInfo> myData;
	private IndustryApplication application;
	private PageConfig config;
	private HttpApi api;
	private String appID;
	private boolean isDestroy = false;
	private ArrayList<Advert> adList;
	private IndexData indexData;
	private int pageStyle;
	private int pageStatus;
	private ImageView emptyimgview;
	private String titleName;
	private String modularName;
	private boolean isNav = false;
	private int lastID = 0;
	private int firstID = 0;
	private TopBarUtil util;
	private int listStyle;
	private final static String DEMAND_LIST = "buysell_list.tx";	
	private int rowNum = Data.DATA_NUM;
	private BannerView banner;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private ProgressBar seeMore_progressBar;
	private TextView loadView;
	private boolean isHttpRunning = false;
	private TextView dialogText;
	private String memberID;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ArrayList<BuyInfo> tempData = (ArrayList<BuyInfo>) msg.obj;
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
				
				if(myData == null || myData.size()==0) {
					view_loading.noDataShowPromept("暂时没有数据，请添加数据", "添加", new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setClass(context, CreateBuyInfoActivity.class);
							startActivity(intent);						
						}
					});
				}
				
				break;
				
			case Data.CLOSE_POPUP:
				dialogText.setVisibility(View.GONE);
				break;
				
			case Data.FULLDATA:				
				isHttpRunning = false;
				view_loading.close();
				if (tempData != null && tempData.size() > 0) {
					myData.addAll(0, tempData);			
					dialogText.setVisibility(View.VISIBLE);
					dialogText.setText("您有" + tempData.size() + "条更新");
					postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(Data.CLOSE_POPUP);
						}
					}, 3000);
					
					saveToLocal(myData, DEMAND_LIST);
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
				adapter.setData(myData);
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
						myData = new ArrayList<BuyInfo>();
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
		//typeID = (int) getArguments().getLong("typeID");
		isNav = getArguments().getBoolean("isNav");
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		indexData = application.getMainData();
		adList = indexData.getSubAdList();
		PageConfigParser p = new PageConfigParser(context,
				"layout/DemandLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
		listStyle = config.getBlock().getShowType();
		memberID = application.getMemberID()+"";
		
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
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		listView = (PushListView) view.findViewById(R.id.list);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		
		listView.setOnItemClickListener(this);
		listView.setDivider(null);

		if (StrUtil.isEmpty(modularName)) {
			titleName = "求购";
		} else {
			titleName = modularName;
		}

		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				false, navi);
		util.showConfig();
		
		myData = getFromLocal(DEMAND_LIST);
		if (myData == null) {
			myData = new ArrayList<BuyInfo>();
		}else{
			view_loading.close();
		}
		
		initBanner();
		
		adapter = new BuySellInfoAdapter(context, myData, rowNum);
		listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/"+ memberID+ "/"+DEMAND_LIST);
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				upData();
			}
		});
		
		
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

	private ArrayList<BuyInfo> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + memberID
				+ "/"  + fileName;
		ArrayList<BuyInfo> data = (ArrayList<BuyInfo>) FileUtil

				.getFile(filePath);
		return data;
	}

	private void saveToLocal(ArrayList<BuyInfo> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/"
					+ memberID + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upData() {
		if (myData != null && myData.size() > 0) {
			lastID = (int) (myData.get(myData.size() - 1)).getId();
			firstID = (int) (myData.get(0)).getId();
		}
		api.getAllBuyInfos(rowNum, -1, firstID, memberID, new RequestListener() {

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
				ArrayList<Object> tempData = new ArrayList<Object>();
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("MygoodsBuyInfoList")) {
							res = json.optString("MygoodsBuyInfoList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
		
							JSONArray cjar = json
									.optJSONArray("MygoodsBuyInfoList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								BuyInfo info = new BuyInfo(temp);
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
			lastID = (int) (myData.get(myData.size() - 1)).getId();
			firstID = (int) (myData.get(0)).getId();
		}
		
		api.getAllBuyInfos(rowNum, 1, lastID, memberID, new RequestListener() {

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
				ArrayList<Object> tempData = new ArrayList<Object>();

				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("MygoodsBuyInfoList")) {
							res = json.optString("MygoodsBuyInfoList");
						}
						if (res.equals("none")) {
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {
							JSONArray cjar = json
									.optJSONArray("MygoodsBuyInfoList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								BuyInfo info = new BuyInfo(temp);
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
		/*// Intent intent = new Intent(context, DemandDetailAct.class);
		// intent.putExtra("buyInfo", myData.get(position));
		// startActivity(intent);
		Intent intent = new Intent(context, SupplyDetailAct.class);
		BuyInfo buyInfo = (BuyInfo)myData.get(position);
		Goods goods = convertToGood(buyInfo);
		goods.setType(2);// 2表示求购商品
		intent.putExtra("goods", goods);
		startActivity(intent);*/
	}
	
}