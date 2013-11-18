package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CollectAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Advert;
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
import com.tixa.industry.widget.LXProgressDialog;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.PushListView;
import com.tixa.industry.widget.PushListView.OnRefreshListener;
import com.tixa.industry.widget.TopBar;

public class CollectListActivity extends Fragment implements
		OnItemClickListener, OnItemLongClickListener {

	private View view;
	private FragmentActivity context;
	private TopBar topbar;
	private PageConfig config;
	private IndustryApplication application;
	private HttpApi api;
	private PushListView listView;
	private String appID;
	private String MemberID;
	private ArrayList<Goods> myData = new ArrayList<Goods>();
	private boolean isDestroy;
	private CollectAdapter adapter;
	private String SORT_LIST = "collect_goods_list.tx";
	private int listStyle;
	private int rowNum = Data.DATA_NUM;
	private TopBarUtil util;
	private BannerView banner;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private ProgressBar seeMore_progressBar;
	private TextView loadView;
	private boolean isHttpRunning = false;
	private TextView dialogText;
	private String titleName;
	private String modularName;
	private String typeID;
	private boolean isNav = false;
	private ArrayList<Advert> adList;
	private IndexData indexData;
	private int pageStyle;
	private int pageStatus;
	private LXProgressDialog dialog;
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ArrayList<Goods> tempData = (ArrayList<Goods>) msg.obj;
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
				myData = tempData;				
				saveToLocal(myData, SORT_LIST);
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
			
			adapter = new CollectAdapter(context, myData, listStyle, rowNum);
			listView.setAdapter(adapter);
		/*	adapter.setCount(myData.size() > rowNum ? rowNum : myData
					.size());
			adapter.notifyDataSetChanged();*/
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
					myData = new ArrayList<Goods>();
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
		case Data.SUCCESS: //删除成功
			if(dialog != null) {
				dialog.dismiss();
			}
			view_loading.loading();
			upData();
			
			break;
		case Data.FAILED: //删除失败	
			if(dialog != null) {
				dialog.dismiss();
			}
			T.shortT(context,"删除失败，请稍候再试");
			break;
		default:
			break;
		}

		}
	};
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_collect_list, null);
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
		MemberID = application.getMemberID() + "";
		indexData = application.getMainData();
		adList = indexData.getSubAdList();
		PageConfigParser p = new PageConfigParser(context,
				"layout/SupplyLayout.xml");
		config = p.parser();
		listStyle = config.getBlock().getShowType();
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
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		listView = (PushListView) view.findViewById(R.id.collect_list);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setDivider(null);
		if(StrUtil.isEmpty(modularName)){
			titleName="收藏列表";
		}else{
			titleName=modularName;		
		}
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName, getFragmentManager(), context,
				application.getTemplateId() , false,navi);
		util.showConfig();
		myData = getFromLocal(SORT_LIST);
		if (myData == null) {
			myData = new ArrayList<Goods>();
		}else{
			view_loading.close();
		}
		
		initBanner();
		
		adapter = new CollectAdapter(context, myData, listStyle, rowNum);
		listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/"
				+ "collection" + "/"+SORT_LIST);
		
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

		api.getCollectProductList(MemberID, new RequestListener() {

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
						if (json.has("BuySellCollectList")) {
							res = json.optString("BuySellCollectList");
							if (res.equals("none")) {
								handler.sendEmptyMessage(Data.NODATA);
							} else {
								JSONArray cjar = json
										.optJSONArray("BuySellCollectList");
								for (int i = 0; i < cjar.length(); i++) {
									JSONObject temp = cjar.optJSONObject(i);
									Goods info = null;
									if (temp.optString("type").equals("1")) {
										JSONObject goods = temp
												.optJSONObject("goods");
										
										info = new Goods(goods);// 供应商品
										info.setCollectID(temp.getLong("ID"));
									} else {
										JSONObject goods = temp
												.optJSONObject("goodsBuyInfo");
										info = new Goods(context, goods);// 求购
										info.setCollectID(temp.getLong("ID"));
									}
									tempData.add(info);
								}
								Message msg = new Message();
								msg.obj = tempData;
								msg.what = Data.FULLDATA;
								handler.sendMessage(msg);
							}
						} else {
							handler.sendEmptyMessage(Data.NONETWORK);
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
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		position = position - listView.getHeaderViewsCount();
		Intent intent = new Intent(context, SupplyDetailAct.class);
		Goods goods = myData.get(position);
		intent.putExtra("goods", goods);
		startActivity(intent);
	}

	// 缓存数据
	private void saveToLocal(ArrayList<Goods> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID +"/"
					+ "collection" + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Goods> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/"
				+ "collection" + "/" + fileName;
		ArrayList<Goods> data = (ArrayList<Goods>) FileUtil.getFile(filePath);
		return data;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		arg2 = arg2 - listView.getHeaderViewsCount();
		final long _id = myData.get(arg2).getCollectID();

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您确定要取消收藏吗？")
                        .setTitle("友情提示")
                        .setNegativeButton("取消",
                                        new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface arg0,
                                                                int arg1) {
                                                        arg0.cancel();
                                                }
                                        })
                        .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                      deleteCollect(_id);
                                                }
                                        });
        AlertDialog alert = builder.create();
        alert.show();
		
		
		
		return true;
	}

	private void deleteCollect(long id) {

		dialog = new LXProgressDialog(context, "正在取消收藏");
		dialog.show();
		api.deleteCollect(id+"", new RequestListener() {			
			@Override
			public void onIOException(IOException e) {
			
			}			
			@Override
			public void onError(TixaException e) {
				handler.sendEmptyMessage(Data.FAILED);
			}
			
			@Override
			public void onComplete(String response) {
				try {
					JSONObject obj = new JSONObject(response);
					boolean result = obj.optBoolean("deleteBuySellCollect");
					if(result) {
						handler.sendEmptyMessage(Data.SUCCESS);
					}else{
						handler.sendEmptyMessage(Data.FAILED);
					}					
				} catch (JSONException e) {
					handler.sendEmptyMessage(Data.FAILED);
					e.printStackTrace();
				}
				
			}
		});

	}
}
