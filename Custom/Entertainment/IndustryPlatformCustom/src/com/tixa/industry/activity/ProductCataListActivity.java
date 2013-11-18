package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CataProductAdapter;
import com.tixa.industry.adapter.ProductLeftListAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.GoodsCata;
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
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.PushListView;
import com.tixa.industry.widget.PushListView.OnRefreshListener;
import com.tixa.industry.widget.TopBar;

public class ProductCataListActivity extends Fragment implements
		OnItemClickListener {
	private boolean isDestroy = false;
	private FragmentActivity context;
	private View view;
	private ListView leftList;
	private PushListView listView;
	private ArrayList<GoodsCata> goodsCatasList;
	private ArrayList<GoodsCata> goodsCataChildList;
	private ArrayList<Goods> goodsChildList;
	private ProductLeftListAdapter leftAdapter;
	private RightListAdapter rightAdapter;
	private CataProductAdapter rightProductAdapter;
	private int listStyle;
	private TopBar topbar;
	private int firstID = 0;
	private int lastID = 0;
	private HttpApi api;
	private String appID;
	// private int typeID;
	private String parentCode;
	private String currentCode;
	private PageConfig config;
	private IndustryApplication application;
	private LayoutInflater inflater;
	private TopBarUtil util;
	private final static String SORT_LIST = "sort_child_list.tx";
	private final static String PRODUCT_LIST = "product_list.tx";
	private int rowNum = Data.DATA_NUM;
	private LoadView view_loading;
	private LinearLayout loadingLayout;
	private ProgressBar seeMore_progressBar;
	private TextView loadView;
	private boolean isHttpRunning = false;
	private TextView dialogText;
	private int isExistChild;
	private int index;

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {

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

				if (isExistChild == 1) {

					if (goodsCataChildList == null
							|| goodsCataChildList.size() == 0) {
						view_loading.noNetWork(new OnClickListener() {
							@Override
							public void onClick(View v) {
								view_loading.loading();
								upData(true);
							}
						});
					} else {
						T.shortT(context,
								getResources().getString(R.string.nonetwork));
					}

				} else {
					if (goodsChildList == null || goodsChildList.size() == 0) {
						view_loading.noNetWork(new OnClickListener() {
							@Override
							public void onClick(View v) {
								view_loading.loading();
								upData(true);
							}
						});
					} else {
						T.shortT(context,
								getResources().getString(R.string.nonetwork));
					}

				}

				break;

			case Data.OTHERLOCALDATA:
				ArrayList<Goods> tempProData = (ArrayList<Goods>) msg.obj;
				L.e("tempProData =" + tempProData);

				if (tempProData == null || tempProData.size() == 0) {
					goodsChildList.clear();
					upData(true); // 本地数据为空则从网络获取
				} else {

					for (int i = 0; i < tempProData.size(); i++) {
						L.e("img =" + tempProData.get(i).getGoodsImg());
					}

					view_loading.close();
					goodsChildList = tempProData;

					if (goodsChildList.size() >= rowNum) {
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

					rightProductAdapter = new CataProductAdapter(context,
							goodsChildList, listStyle, rowNum);
					listView.setAdapter(rightProductAdapter);
				}

				break;

			case Data.LOCALDATA:
				ArrayList<GoodsCata> tempData = (ArrayList<GoodsCata>) msg.obj;
				if (tempData == null || tempData.size() == 0) {
					goodsCataChildList.clear();
					upData(true); // 本地数据为空则从网络获取
				} else {
					view_loading.close();
					goodsCataChildList = tempData;

					if (goodsCataChildList.size() >= rowNum) {
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

					rightAdapter = new RightListAdapter(context, rowNum);
					listView.setAdapter(rightAdapter);
					/*
					 * rightAdapter.setCount(goodsCataChildList.size() > rowNum
					 * ? rowNum : goodsCataChildList .size());
					 * rightAdapter.notifyDataSetChanged();
					 */
				}
				break;

			case Data.NODATA:
				listView.onRefreshComplete();
				if (isExistChild == 1) {
					if (goodsCataChildList == null
							|| goodsCataChildList.size() == 0) {
						view_loading.showNodataView();
					}

				} else {
					if (goodsChildList == null || goodsChildList.size() == 0) {
						view_loading.showNodataView();
					}
				}
				break;

			case Data.CLOSE_POPUP:
				dialogText.setVisibility(View.GONE);
				break;

			case Data.OTHERFULLDATA:
				tempProData = (ArrayList<Goods>) msg.obj;
				isHttpRunning = false;
				view_loading.close();
				if (tempProData != null && tempProData.size() > 0) {
					goodsChildList.addAll(0, tempProData);
					dialogText.setVisibility(View.VISIBLE);
					dialogText.setText("您有" + tempProData.size() + "条更新");
					postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(Data.CLOSE_POPUP);
						}
					}, 3000);
					saveProductToLocal(goodsChildList, PRODUCT_LIST);
				}

				if (goodsChildList.size() >= rowNum) {
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
				rightProductAdapter
						.setCount(goodsChildList.size() > rowNum ? rowNum
								: goodsChildList.size());
				rightProductAdapter.notifyDataSetChanged();
				if (msg.arg1 == 1) {
					listView.onRefreshComplete(true, 0);
				} else {
					listView.onRefreshComplete(false, goodsChildList.size());
				}

				break;
			case Data.FULLDATA:
				tempData = (ArrayList<GoodsCata>) msg.obj;
				isHttpRunning = false;
				view_loading.close();
				if (tempData != null && tempData.size() > 0) {
					goodsCataChildList.addAll(0, tempData);
					dialogText.setVisibility(View.VISIBLE);
					dialogText.setText("您有" + tempData.size() + "条更新");
					postDelayed(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(Data.CLOSE_POPUP);
						}
					}, 3000);
					parentCode = goodsCataChildList.get(0).getParentCode();
					saveToLocal(goodsCataChildList, SORT_LIST);
				}

				if (goodsCataChildList.size() >= rowNum) {
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

				rightAdapter
						.setCount(goodsCataChildList.size() > rowNum ? rowNum
								: goodsCataChildList.size());
				rightAdapter.notifyDataSetChanged();
				if (msg.arg1 == 1) {
					listView.onRefreshComplete(true, 0);
				} else {
					listView.onRefreshComplete(false, goodsCataChildList.size());
				}

				break;
			case Data.MOREDATA:
				isHttpRunning = false;
				view_loading.close();
				tempData = (ArrayList<GoodsCata>) msg.obj;
				if (tempData != null && tempData.size() > 0) {
					if (goodsCataChildList == null)
						goodsCataChildList = new ArrayList<GoodsCata>();
					goodsCataChildList.addAll(tempData);
				}
				seeMore_progressBar.setVisibility(View.GONE);
				rightAdapter.setCount(goodsCataChildList.size());
				rightAdapter.notifyDataSetChanged();
				if (tempData != null && tempData.size() == 0) {
					loadView.setVisibility(View.GONE);
				} else if (tempData != null && tempData.size() >= 0) {
					loadView.setText("查看更多");
				}
				break;

			case Data.OTHERMOREDATA:
				isHttpRunning = false;
				view_loading.close();
				tempProData = (ArrayList<Goods>) msg.obj;
				if (tempProData != null && tempProData.size() > 0) {
					if (goodsChildList == null)
						goodsChildList = new ArrayList<Goods>();
					goodsChildList.addAll(tempProData);
				}
				seeMore_progressBar.setVisibility(View.GONE);
				rightProductAdapter.setCount(goodsChildList.size());
				rightProductAdapter.notifyDataSetChanged();
				if (tempProData != null && tempProData.size() == 0) {
					loadView.setVisibility(View.GONE);
				} else if (tempProData != null && tempProData.size() >= 0) {
					loadView.setText("查看更多");
				}
				break;

			default:
				break;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.product_list, null);
		initData();
		initView();
		initTopBar();
		return view;

	}

	private void initData() {
		context = getActivity();
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		PageConfigParser p = new PageConfigParser(context,
				"layout/SortLayout.xml");
		config = p.parser();
		listStyle = config.getBlock().getShowType();
		goodsCatasList = (ArrayList<GoodsCata>) getArguments().getSerializable(
				"cataData");
		for (int i = 0; i < goodsCatasList.size(); i++) {
			if (goodsCatasList.get(i).isChecked()) {
				parentCode = goodsCatasList.get(i).getCataCode();
				isExistChild = goodsCatasList.get(i).getIsExistChild();
				index = i;
			}
		}

	}

	private void initTopBar() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(false, naviStyle, topbar, "产品分类",
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);

		util.showConfig();
	}

	// 从缓存读取数据
	private void getFromLocal() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<GoodsCata> obj = getFromLocal(SORT_LIST);
				Message msg = new Message();
				msg.obj = obj;
				msg.what = Data.LOCALDATA;
				handler.sendMessage(msg);
			}
		}).start();
	}

	// 从缓存读取数据
	private void getProductFromLocal() {
		L.e("-----------getProductFromLocal----------");
		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Goods> obj = getProductFromLocal(PRODUCT_LIST);
				Message msg = new Message();
				msg.obj = obj;
				msg.what = Data.OTHERLOCALDATA;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private void initView() {
		leftList = (ListView) view.findViewById(R.id.left);
		leftAdapter = new ProductLeftListAdapter(goodsCatasList, context);
		leftList.setAdapter(leftAdapter);
		leftList.setSelection(index);
		listView = (PushListView) view.findViewById(R.id.right);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		listView.setOnItemClickListener(this);
		listView.setDivider(null);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				upData(true);
			}
		});
		leftList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for (int i = 0; i < arg0.getCount(); i++) {
					if (i == arg2) {
						goodsCatasList.get(i).setChecked(true);
						if (goodsCatasList.get(i).getId() == 0) {
							index = arg2;
							view_loading.close();
							listView.onRefreshComplete();
							if (goodsCataChildList == null) {
								goodsCataChildList = new ArrayList<GoodsCata>();
							} else {
								goodsCataChildList.clear();
							}
							ArrayList<String> childCateList = goodsCatasList
									.get(i).getChildCateList();
							for (int j = 0; j < childCateList.size(); j++) {
								GoodsCata goodsCata = new GoodsCata();
								goodsCata.setCataName(childCateList.get(j));
								goodsCataChildList.add(goodsCata);
							}
							rightAdapter.notifyDataSetChanged();
							isExistChild = 2;// 区别大众点评的子分类
							enableFresh(false);
						} else {
							parentCode = goodsCatasList.get(arg2).getCataCode();
							view_loading.loading();
							isExistChild = goodsCatasList.get(arg2)
									.getIsExistChild();
							if (isExistChild == 1) { // 有子分类
								getFromLocal();
							} else { // 没有子分类
								getProductFromLocal();
							}
							enableFresh(true);
						}
					} else {
						goodsCatasList.get(i).setChecked(false);
					}
				}
				leftAdapter.notifyDataSetChanged();
				// for (int i = 0; i < arg0.getChildCount(); i++) {
				// if (arg0.getChildAt(i) == arg1) {
				//
				// parentCode = goodsCatasList.get(arg2).getCataCode();
				// view_loading.loading();
				// isExistChild = goodsCatasList.get(arg2)
				// .getIsExistChild();
				//
				// L.e("isExistChild is" + isExistChild);
				//
				// if (isExistChild == 1) { // 有子分类
				// getFromLocal();
				// } else { // 没有子分类
				// getProductFromLocal();
				// }
				// }
				// }

			}
		});

		if (isExistChild == 1) { // 有子分类
			goodsCataChildList = getFromLocal(SORT_LIST);
			if (goodsCataChildList == null) {
				goodsCataChildList = new ArrayList<GoodsCata>();
			} else {
				view_loading.close();
			}
			rightAdapter = new RightListAdapter(context, rowNum);
			listView.setAdater(rightAdapter, Constants.CACHE_DIR + appID + "/"
					+ parentCode + "/" + SORT_LIST);
			if (goodsCataChildList == null || goodsCataChildList.size() == 0) {
				upData(true);
			}

		} else if (isExistChild == 2) { // 大众点评
			goodsCataChildList = new ArrayList<GoodsCata>();
			ArrayList<String> childCateList = goodsCatasList.get(index)
					.getChildCateList();
			for (int j = 0; j < childCateList.size(); j++) {
				GoodsCata goodsCata = new GoodsCata();
				goodsCata.setCataName(childCateList.get(j));
				goodsCataChildList.add(goodsCata);
			}
			view_loading.close();
			rightAdapter = new RightListAdapter(context, rowNum);
			listView.setAdater(rightAdapter, Constants.CACHE_DIR + appID + "/"
					+ parentCode + "/" + SORT_LIST);
			enableFresh(false);
		} else { // 没有子分类
			goodsChildList = getProductFromLocal(PRODUCT_LIST);
			if (goodsChildList == null) {
				goodsChildList = new ArrayList<Goods>();
			} else {
				view_loading.close();
			}
			rightProductAdapter = new CataProductAdapter(context,
					goodsChildList, listStyle, rowNum);
			listView.setAdater(rightProductAdapter, Constants.CACHE_DIR + appID
					+ "/" + parentCode + "/" + PRODUCT_LIST);

			if (goodsChildList == null || goodsChildList.size() == 0) {
				upData(true);
			}
		}

		initListBottom();
	}

	protected void enableFresh(boolean b) {
		if (b) {
			listView.setonRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					upData(true);
				}
			});
		} else {
			listView.setonRefreshListener(null);
		}

	}

	private void initListBottom() {
		if (isExistChild == 1) { // 有子分类
			if (goodsCataChildList.size() >= rowNum) {
				if (loadingLayout == null) {
					initFooter();
					listView.addFooterView(loadingLayout);
				} else {
					loadView.setText("查看更多");
					loadView.setVisibility(View.VISIBLE);
				}
			}
		} else if (isExistChild == 2) {
			if (loadingLayout == null) {
				initFooter();
				listView.addFooterView(loadingLayout);
				loadView.setVisibility(View.GONE);
			}
		} else {
			if (goodsChildList.size() >= rowNum) {
				if (loadingLayout == null) {
					initFooter();
					listView.addFooterView(loadingLayout);
				} else {
					loadView.setText("查看更多");
					loadView.setVisibility(View.VISIBLE);
				}
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

		if (isExistChild == 1) { // 有子分类
			loadingLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (rightAdapter.getCount() + rowNum <= goodsCataChildList
							.size()) {
						rightAdapter.setCount(rightAdapter.getCount() + rowNum);
						rightAdapter.notifyDataSetChanged();
					} else {

						if (rightAdapter.getCount() == goodsCataChildList
								.size()) {
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
							rightAdapter.setCount(goodsCataChildList.size());
							rightAdapter.notifyDataSetChanged();
						}
					}
				}
			});
		} else {
			loadingLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (rightProductAdapter.getCount() + rowNum <= goodsChildList
							.size()) {
						rightProductAdapter.setCount(rightProductAdapter
								.getCount() + rowNum);
						rightProductAdapter.notifyDataSetChanged();
					} else {

						if (rightProductAdapter.getCount() == goodsChildList
								.size()) {
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
							rightProductAdapter.setCount(goodsChildList.size());
							rightProductAdapter.notifyDataSetChanged();
						}
					}
				}
			});
		}

	}

	// isFresh 表示否为下拉刷新
	private void upData(boolean isFresh) {

		if (isExistChild == 1) {
			if (isFresh && goodsCataChildList != null
					&& goodsCataChildList.size() > 0) {
				lastID = (int) goodsCataChildList.get(
						goodsCataChildList.size() - 1).getId();
				firstID = (int) goodsCataChildList.get(0).getId();
				parentCode = goodsCataChildList.get(0).getParentCode();
			} else {
				lastID = 0;
				firstID = 0;
			}
			// T.shortT(context, parentCode);
			api.getSubGoodsCata(parentCode, rowNum, -1, firstID,
					new RequestListener() {

						@Override
						public void onIOException(IOException e) {
							T.shortT(context, "未知错误:" + e.getMessage());

						}

						@Override
						public void onError(TixaException e) {
							L.e("未知错误:" + e.getMessage() + " "
									+ e.getStatusCode());
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
									if (json.has("goodsCataChildList")) {
										res = json
												.optString("goodsCataChildList");
										if (res.equals("none")) {
											handler.sendEmptyMessage(Data.NODATA);
										} else {
											JSONArray cjar = json
													.optJSONArray("goodsCataChildList");
											for (int i = 0; i < cjar.length(); i++) {
												JSONObject temp = cjar
														.optJSONObject(i);
												GoodsCata info = new GoodsCata(
														temp);
												tempData.add(info);
											}
											Message msg = new Message();
											msg.obj = tempData;
											msg.what = Data.FULLDATA;
											handler.sendMessage(msg);
										}
									}
								}
							} catch (Exception e) {
								L.e(e.toString());
								handler.sendEmptyMessage(Data.NONETWORK);
							}
						}
					});
		} else {
			if (isFresh && goodsChildList != null && goodsChildList.size() > 0) {
				lastID = (int) goodsChildList.get(goodsChildList.size() - 1)
						.getId();
				firstID = (int) goodsChildList.get(0).getId();
			} else {
				lastID = 0;
				firstID = 0;
			}

			api.getCataProductList(rowNum, -1, firstID, parentCode,
					new RequestListener() {

						@Override
						public void onIOException(IOException e) {
							T.shortT(context, "未知错误:" + e.getMessage());

						}

						@Override
						public void onError(TixaException e) {
							L.e("未知错误:" + e.getMessage() + " "
									+ e.getStatusCode());
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
									if (json.has("goodsListByCata")) {
										res = json.optString("goodsListByCata");
										if (res.equals("none")) {
											handler.sendEmptyMessage(Data.NODATA);
										} else {

											JSONArray cjar = json
													.optJSONArray("goodsListByCata");
											for (int i = 0; i < cjar.length(); i++) {
												JSONObject temp = cjar
														.optJSONObject(i);
												Goods info = new Goods(temp);
												tempData.add(info);
											}
											Message msg = new Message();
											msg.obj = tempData;
											msg.what = Data.OTHERFULLDATA;
											handler.sendMessage(msg);
										}
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

	private void getHistory() {

		if (isExistChild == 1) {

			if (goodsCataChildList != null && goodsCataChildList.size() > 0) {
				lastID = (int) goodsCataChildList.get(
						goodsCataChildList.size() - 1).getId();
				firstID = (int) goodsCataChildList.get(0).getId();
				parentCode = goodsCataChildList.get(0).getParentCode();
			}

			api.getSubGoodsCata(parentCode, rowNum, 1, lastID,
					new RequestListener() {

						@Override
						public void onIOException(IOException e) {
							T.shortT(context, "未知错误:" + e.getMessage());
						}

						@Override
						public void onError(TixaException e) {
							L.e("未知错误:" + e.getMessage() + " "
									+ e.getStatusCode());
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
									if (json.has("goodsCataChildList")) {
										res = json
												.optString("goodsCataChildList");
									}
									if (res.equals("none")) {
										Message msg = new Message();
										msg.obj = tempData;
										msg.what = Data.MOREDATA;
										handler.sendMessage(msg);
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
							} catch (Exception e) {
								L.e(e.toString());
								handler.sendEmptyMessage(Data.NONETWORK);
							}

						}
					});
		} else {

			if (goodsChildList != null && goodsChildList.size() > 0) {
				lastID = (int) goodsChildList.get(goodsChildList.size() - 1)
						.getId();
				firstID = (int) goodsChildList.get(0).getId();
			}

			api.getCataProductList(rowNum, 1, lastID, parentCode,
					new RequestListener() {
						@Override
						public void onIOException(IOException e) {
							T.shortT(context, "未知错误:" + e.getMessage());

						}

						@Override
						public void onError(TixaException e) {
							L.e("未知错误:" + e.getMessage() + " "
									+ e.getStatusCode());
							handler.sendEmptyMessage(Data.NONETWORK);
						}

						@Override
						public void onComplete(String response) {
							ArrayList<Goods> tempData = new ArrayList<Goods>();
							try {
								if (StrUtil.isHttpException(response)) {
									Message msg = new Message();
									msg.obj = tempData;
									msg.what = Data.OTHERMOREDATA;
									handler.sendMessage(msg);
								} else {
									JSONObject json = new JSONObject(response);
									String res = "";
									if (json.has("goodsListByCata")) {
										res = json.optString("goodsListByCata");
										if (res.equals("none")) {
											Message msg = new Message();
											msg.obj = tempData;
											msg.what = Data.OTHERMOREDATA;
											handler.sendMessage(msg);
										} else {
											JSONArray cjar = json
													.optJSONArray("goodsListByCata");
											for (int i = 0; i < cjar.length(); i++) {
												JSONObject temp = cjar
														.optJSONObject(i);
												Goods info = new Goods(temp);
												tempData.add(info);
											}
											Message msg = new Message();
											msg.obj = tempData;
											msg.what = Data.OTHERMOREDATA;
											handler.sendMessage(msg);
										}
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

	// 缓存数据
	private void saveToLocal(ArrayList<GoodsCata> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + parentCode + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveProductToLocal(ArrayList<Goods> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + parentCode + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<GoodsCata> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + parentCode + "/"
				+ fileName;
		ArrayList<GoodsCata> data = (ArrayList<GoodsCata>) FileUtil
				.getFile(filePath);
		return data;
	}

	private ArrayList<Goods> getProductFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + parentCode + "/"
				+ fileName;
		ArrayList<Goods> data = (ArrayList<Goods>) FileUtil.getFile(filePath);
		return data;
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int possition,
			long arg3) {
		possition = possition - listView.getHeaderViewsCount();
		if (isExistChild == 1) {
			GoodsCata goodsCata = goodsCataChildList.get(possition);
			Bundle args = new Bundle();
			args.putString("appID", goodsCata.getAppID() + "");
			args.putString("cataCode", goodsCata.getCataCode());
			FragmentManager fragmentManager = context
					.getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			Fragment fragment = new ProductListActivity();
			fragment.setArguments(args);
			transaction.add(R.id.fragment, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		} else if (isExistChild == 2) {
			GoodsCata goodsCata = goodsCatasList.get(index);
			GoodsCata goodsChildCata = goodsCataChildList.get(possition);
			Bundle args = new Bundle();
			args.putString("category", goodsCata.getCataName());
			args.putString("keyword", goodsChildCata.getCataName());
			L.d("System.out", "category:" + args.getString("category"));
			L.d("System.out", "keyword:" + args.getString("keyword"));
			FragmentManager fragmentManager = context
					.getSupportFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			Fragment fragment = new ProductListDazhongActivity();
			fragment.setArguments(args);
			transaction.add(R.id.fragment, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		} else {
			Intent intent = new Intent(context, SupplyDetailAct.class);
			Goods goods = goodsChildList.get(possition);
			goods.setType(1);// 1是供应商品
			intent.putExtra("goods", goods);
			startActivity(intent);
		}
	}

	class RightListAdapter extends BaseAdapter {

		private Context context;
		private LayoutInflater mInflater;
		private AsyncImageLoader loader;
		private int rowNum;
		private int count;

		public void setCount(int count) {
			this.count = count;
		}

		public RightListAdapter(Context context, int rowNum) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
			loader = new AsyncImageLoader();
			this.rowNum = rowNum;
			this.count = rowNum;
		}

		@Override
		public int getCount() {
			return goodsCataChildList.size() < rowNum ? goodsCataChildList
					.size() : count;
		}

		@Override
		public Object getItem(int position) {
			return goodsCataChildList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			inflater = LayoutInflater.from(context);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.right, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.title);
			title.setText(goodsCataChildList.get(position).getCataName());
			return convertView;
		}
	}

}
