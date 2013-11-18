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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.NewsCommonAdapter;
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

public class SupplyAndDemandActivity extends Fragment implements
		OnItemClickListener {

	private Activity context;
	private View view;
	private ProductCommonAdapter adapter;
	private TopBar topbar;
	private PushListView listView;
	private ArrayList<Object> myData;
	private IndustryApplication application;
	private PageConfig config;
	private int listStyle;
	private HttpApi api;
	private String appID;
	private RadioGroup radioGroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
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
	private final static String SUPPLY_AND_LIST = "supply_and_list.tx";
	private final static String DEMAND_AND_LIST = "demand_and_list.tx";
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
			ArrayList<Object> tempData = (ArrayList<Object>) msg.obj;
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
								if (radioButton1.isChecked()) {
									upData();
								} else if (radioButton2.isChecked()) {
									upData1();
								}					
							}
						});
					}else{				
						T.shortT(context, getResources().getString(R.string.nonetwork));
					}
				
				break;
				case Data.LOCALDATA:
					
					if(tempData == null || tempData.size() == 0) {
						myData.clear();
						
						if (radioButton1.isChecked()) {
							upData();
						} else if (radioButton2.isChecked()) {
							upData1();
						} //本地数据为空则从网络获取			
					}else{
						view_loading.close();
						myData = tempData;			
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
						
						
					adapter = new ProductCommonAdapter(context, myData, listStyle, rowNum);
					listView.setAdapter(adapter);

					}	
					break;
				
			case Data.NODATA:
				listView.onRefreshComplete();
				if(myData == null || myData.size()==0) {
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
					
					myData.addAll(0, tempData);			
					dialogText.setVisibility(View.VISIBLE);
					dialogText.setText("您有" + tempData.size() + "条更新");
					postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(Data.CLOSE_POPUP);
						}
					}, 3000);
					
					if (radioButton1.isChecked()) {
						saveToLocal(myData, SUPPLY_AND_LIST);
					} else if (radioButton2.isChecked()) {
						saveToLocal(myData, DEMAND_AND_LIST);
					}
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
						myData = new ArrayList<Object>();
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
		view = inflater.inflate(R.layout.listview_radiobutton, null);
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
				"layout/SupplyAndDemandLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
		listStyle = config.getBlock().getShowType();
	}
	
	private void initBanner() {
		if(pageStatus == ConfigData.SHOW_AD && adList.size() > 0) {//显示广告
			banner = new BannerView(context, adList, pageStyle);
			banner.show();
			listView.addHeaderView(banner);
		}
	}
	
	private void initView() {
		
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
		radioButton1.setTextColor(getResources().getColor(R.color.white));
		radioButton2.setTextColor(getResources().getColor(R.color.black));
		
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {			
	
				if(checkedId == R.id.radioButton1) {
					radioButton1.setTextColor(getResources().getColor(R.color.white));
					radioButton2.setTextColor(getResources().getColor(R.color.black));
				}else{
					radioButton1.setTextColor(getResources().getColor(R.color.black));
					radioButton2.setTextColor(getResources().getColor(R.color.white));
				}
				view_loading.loading();
				getLocalData();
			}
		});
		
		
		
		topbar = (TopBar) view.findViewById(R.id.topbar);
		listView = (PushListView) view.findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		listView.setDivider(null);
	
		if (StrUtil.isEmpty(modularName)) {
			titleName = "供应";
		} else {
			titleName = modularName;
		}
		
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName, getFragmentManager(), context,
				application.getTemplateId() , false,navi);
		util.showConfig();	
		
		if (radioButton1.isChecked()) {
			myData = getFromLocal(SUPPLY_AND_LIST);
		} else if (radioButton2.isChecked()) {
			myData = getFromLocal(DEMAND_AND_LIST);
		}
		
		if (myData == null) {
			myData = new ArrayList<Object>();
		}else{
			view_loading.close();
		}	
		
		initBanner();
		
		adapter = new ProductCommonAdapter(context, myData, listStyle, rowNum);
		
		if (radioButton1.isChecked()) {
			listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/"+ typeID+ "/"+SUPPLY_AND_LIST);
		} else if (radioButton2.isChecked()) {
			listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/"+ typeID+ "/"+DEMAND_AND_LIST);
		}
		
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (radioButton1.isChecked()) {
					upData();
				} else if (radioButton2.isChecked()) {
					upData1();
				}
			}
		});
		
		if (myData == null || myData.size() == 0) {
			if (radioButton1.isChecked()) {
				upData();
			} else if (radioButton2.isChecked()) {
				upData1();
			}
		}
		if (myData.size() > 0) {
			if (myData.get(myData.size() - 1) instanceof Goods) { 
				lastID = (int) ((Goods)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((Goods)myData.get(0)).getId();
			}else if(myData.get(myData.size() - 1) instanceof BuyInfo) {
				lastID = (int) ((BuyInfo)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((BuyInfo)myData.get(0)).getId();
			}
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
								if (radioButton1.isChecked()) {
									getHistory();
								} else if (radioButton2.isChecked()) {
									getHistory1();
								}
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
	
	
	
	private void getLocalData() {

		if (radioButton1.isChecked()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					ArrayList<Object> obj = getFromLocal(SUPPLY_AND_LIST);
					Message msg = new Message();
					msg.obj = obj;
					msg.what = Data.LOCALDATA;
					handler.sendMessage(msg);
				}
			}).start();

		} else if (radioButton2.isChecked()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					ArrayList<Object> obj = getFromLocal(DEMAND_AND_LIST);
					Message msg = new Message();
					msg.obj = obj;
					msg.what = Data.LOCALDATA;
					handler.sendMessage(msg);
				}
			}).start();

		}
	}


	private ArrayList<Object> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/"
				+ typeID + "/" + fileName;
		ArrayList<Object> data = (ArrayList<Object>) FileUtil.getFile(filePath);
		return data;
	}

	private void saveToLocal(ArrayList<Object> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/"
					+ typeID + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void upData() {
		if (myData != null && myData.size() > 0) {
			if (myData.get(myData.size() - 1) instanceof Goods) { 
				lastID = (int) ((Goods)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((Goods)myData.get(0)).getId();
			}else if(myData.get(myData.size() - 1) instanceof BuyInfo) {
				lastID = (int) ((BuyInfo)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((BuyInfo)myData.get(0)).getId();
			}		
		}
		api.getAllSells(rowNum, -1, firstID, new RequestListener() {

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
						if (json.has("goodsAllList")) {
							res = json.optString("goodsAllList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
							L.e("--------------------");
							L.e("res=" + res);
							JSONArray cjar = json.optJSONArray("goodsAllList");
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

	private void getHistory() {

		if (myData != null && myData.size() > 0) {
			if (myData.get(myData.size() - 1) instanceof Goods) { 
				lastID = (int) ((Goods)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((Goods)myData.get(0)).getId();
			}else if(myData.get(myData.size() - 1) instanceof BuyInfo) {
				lastID = (int) ((BuyInfo)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((BuyInfo)myData.get(0)).getId();
			}
		}else{
			lastID = 0;
		}

		api.getAllSells(rowNum, 1, lastID, new RequestListener() {

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
						if (json.has("goodsAllList")) {
							res = json.optString("goodsAllList");
						}
						if (res.equals("none")) {
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {

							JSONArray cjar = json.optJSONArray("goodsAllList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								Goods info = new Goods(temp);
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

	private void upData1() {
		if (myData != null && myData.size() > 0) {
			if (myData.get(myData.size() - 1) instanceof Goods) { 
				lastID = (int) ((Goods)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((Goods)myData.get(0)).getId();
			}else if(myData.get(myData.size() - 1) instanceof BuyInfo) {
				lastID = (int) ((BuyInfo)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((BuyInfo)myData.get(0)).getId();
			}
		}else{
			firstID = 0;
		}
		api.getAllBuys(rowNum, -1, firstID, new RequestListener() {

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
								BuyInfo info = new BuyInfo(temp);
								Goods good = convertToGood(info);
								tempData.add(good);
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

	private void getHistory1() {

		if (myData != null && myData.size() > 0) {
			if (myData.get(myData.size() - 1) instanceof Goods) { 
				lastID = (int) ((Goods)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((Goods)myData.get(0)).getId();
			}else if(myData.get(myData.size() - 1) instanceof BuyInfo) {
				lastID = (int) ((BuyInfo)myData.get(myData.size() - 1)).getId();
				firstID =(int) ((BuyInfo)myData.get(0)).getId();
			}
		}

		api.getAllBuys(rowNum, 1, lastID, new RequestListener() {

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
						if (json.has("goodsBuyInfoList")) {
							res = json.optString("goodsBuyInfoList");
						}
						if (res.equals("none")) {
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {
							JSONArray cjar = json
									.optJSONArray("goodsBuyInfoList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								
								BuyInfo info = new BuyInfo(temp);
								Goods good = convertToGood(info);
								tempData.add(good);
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


	private Goods convertToGood(BuyInfo buyInfo) {
		Goods goods = new Goods();
		goods.setId(buyInfo.getId());
		goods.setCataCode(buyInfo.getCataCode());
		goods.setStatus(buyInfo.getStatus());
		goods.setSubject(buyInfo.getSubject());
		goods.setCreateTime(buyInfo.getCreateTime());
		goods.setGoodsNumber(buyInfo.getGoodsNumber());
		goods.setGoodsPrice(buyInfo.getGoodsPrice());
		goods.setGoodsStandard(buyInfo.getGoodsStandard());
		goods.setContact(buyInfo.getContact());
		goods.setContact(buyInfo.getContact());
		goods.setAddress(buyInfo.getAddress());
		goods.setEmail(buyInfo.getEmail());
		goods.setImagePath(buyInfo.getImagePath());
		goods.setGoodsDescStr(buyInfo.getGoodsDescStr());
		goods.setAppID(buyInfo.getAppID());
		goods.setEnterpriseID(buyInfo.getEnterpriseID());
		goods.setType(2);
		return goods;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		position = position - listView.getHeaderViewsCount();
		Goods goods = (Goods) myData.get(position);
		Fragment fragment;
		if(goods.getSearchType() >0) {//傻目录抓取
			
			Bundle args = new Bundle();
			args.putString("id",  goods.getId() +"");
			args.putSerializable("goods", goods);
			fragment = new SupplySamuluDetailFragment();
			fragment.setArguments(args);
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.add(R.id.fragment, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
	
		}else{

			Intent intent = new Intent(context, SupplyDetailAct.class);	
			intent.putExtra("goods", goods);
			startActivity(intent);
		
		}
		
	}

}
