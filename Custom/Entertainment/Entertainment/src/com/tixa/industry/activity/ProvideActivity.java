package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.activity.my.WebActivity;
import com.tixa.industry.adapter.ProviderNeedListAdapter;
import com.tixa.industry.adapter.SimpleListAdapter_cate;
import com.tixa.industry.adapter.SimpleListAdapter_catechild;
import com.tixa.industry.adapter.SimpleListAdapter_sort;
import com.tixa.industry.api.DianpingApi;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.GoodsCata;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widgetCustom.LoadView;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

public class ProvideActivity extends Fragment implements OnClickListener,
		OnItemClickListener, OnCheckedChangeListener {
	private Context context;
	private View view;
	private String modularName;
	private TopBar topbar;
	private ListView listView;
	private RelativeLayout relativeLayout_distance;
	private RelativeLayout relativeLayout_category;
	private RelativeLayout relativeLayout_order;
	private CheckBox checkBox_500;
	private CheckBox checkBox_1k;
	private CheckBox checkBox_2k;
	private CheckBox checkBox_5k;
	private CheckBox checkBox_all;
	private ScaleAnimation anim_enter;
	private ScaleAnimation anim_exit;
	private RelativeLayout relativeLayout;
	private RelativeLayout panel_category;
	private RelativeLayout panel_sort;
	private ListView listView_sort;
	// private final static int requestCode = 1;
	private TextView distance_textView;
	private TextView category_textView;
	private TextView sort_textView;
	private boolean isLoading = false;
	private LoadView loadView;
	private boolean isDestroy = false;
	private IndustryApplication application;
	private String appID;
	private ArrayList<ProvideAndNeed> dataList = new ArrayList<ProvideAndNeed>();
	private HttpApi api;
	private String excataCode;
	private String distance;
	private String lat;
	private String lng;
	private String sortType;
	private String[] strs;
	private int index;
	private ListView listView_left;
	private ListView listView_right;
	private ArrayList<GoodsCata> goodsCatas = new ArrayList<GoodsCata>();
	private ArrayList<GoodsCata> goodsCatasChild = new ArrayList<GoodsCata>();
	private final static int GETCATEGORYDATA = 1111;
	private SimpleListAdapter_catechild adapter_catechild;
	private SimpleListAdapter_cate adapter_cate;
	private RelativeLayout progressView;
	private BDLocation bdLocation;
	private View dark_bg;
	private ArrayList<GoodsCata> temp;
	private int count;
	private Map<String, String> map;
	private ArrayList<GoodsCata> myData_dianping;
	private final static int COMPLETE = 1000;
	public static int number;
	private boolean isDaz = false;
	private ArrayList<GoodsCata> tempData;
	private String cateDaz;

	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (isDestroy) {
				return;
			}
			switch (msg.what) {
			case Data.NONETWORK:
				isLoading = false;
				// loadView.noNetWork(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// getData();
				// }
				// });
				break;
			case Data.NODATA:
				isLoading = false;
				loadView.showNodataView();
				break;
			case Data.FULLDATA:
				isLoading = false;
				loadView.close();
				ProviderNeedListAdapter adapter = new ProviderNeedListAdapter(
						context, dataList);
				listView.setAdapter(adapter);
				break;
			case COMPLETE:
				goodsCatas.addAll(myData_dianping);
				break;
			case GETCATEGORYDATA:
				progressView.setVisibility(View.GONE);
				temp = (ArrayList<GoodsCata>) msg.obj;
				adapter_catechild = new SimpleListAdapter_catechild(context,
						temp);
				listView_right.setAdapter(adapter_catechild);
				break;
			default:
				break;
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_listview, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		strs = getResources().getStringArray(R.array.sortArray);
		context = getActivity();
		modularName = getArguments().getString("modularName");
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		distance = "-1";// 默认距离为全部
		sortType = "1";// 默认排序 按时间
		excataCode = "0";// 显示所有的商品
		Log.d("System.out", "appID" + appID);
		Log.d("System.out", "userID" + application.getMemberID());
		myData_dianping = new ArrayList<GoodsCata>();

	}

	private void initView() {
		dark_bg = view.findViewById(R.id.dark_bg);
		progressView = (RelativeLayout) view.findViewById(R.id.progressView);
		listView_left = (ListView) view.findViewById(R.id.listview_left);
		listView_right = (ListView) view.findViewById(R.id.listview_right);
		distance_textView = (TextView) view.findViewById(R.id.distance);
		category_textView = (TextView) view.findViewById(R.id.category);
		sort_textView = (TextView) view.findViewById(R.id.sort);
		listView = (ListView) view.findViewById(R.id.mylistView);
		listView.setOnItemClickListener(this);
		listView_sort = (ListView) view.findViewById(R.id.list_sort);
		topbar = (TopBar) view.findViewById(R.id.topbar);
		topbar.setShowConfig(modularName, R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				getFragmentManager().popBackStack();

			}
		});
		loadView = (LoadView) view.findViewById(R.id.loadview);
		relativeLayout_distance = (RelativeLayout) view.findViewById(R.id.one);
		relativeLayout_category = (RelativeLayout) view.findViewById(R.id.two);
		relativeLayout_order = (RelativeLayout) view.findViewById(R.id.three);
		relativeLayout_distance.setOnClickListener(this);
		relativeLayout_category.setOnClickListener(this);
		relativeLayout_order.setOnClickListener(this);
		checkBox_500 = (CheckBox) view.findViewById(R.id.checkBox_one);
		checkBox_1k = (CheckBox) view.findViewById(R.id.checkBox_two);
		checkBox_2k = (CheckBox) view.findViewById(R.id.checkBox_three);
		checkBox_5k = (CheckBox) view.findViewById(R.id.checkBox_four);
		checkBox_all = (CheckBox) view.findViewById(R.id.checkBox_five);
		checkBox_500.setOnCheckedChangeListener(this);
		checkBox_1k.setOnCheckedChangeListener(this);
		checkBox_2k.setOnCheckedChangeListener(this);
		checkBox_5k.setOnCheckedChangeListener(this);
		checkBox_all.setOnCheckedChangeListener(this);
		getData();
		// 获取一级分类的数据
		getParentCategory();
		//
		relativeLayout = (RelativeLayout) view
				.findViewById(R.id.checkPannel_distance);
		panel_category = (RelativeLayout) view
				.findViewById(R.id.checkPannel_category);
		panel_sort = (RelativeLayout) view.findViewById(R.id.checkPannel_sort);
		anim_enter = (ScaleAnimation) AnimationUtils.loadAnimation(context,
				R.anim.enter_anim);
		anim_exit = (ScaleAnimation) AnimationUtils.loadAnimation(context,
				R.anim.exit_anim);
		anim_exit.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				relativeLayout.setVisibility(View.GONE);
				panel_category.setVisibility(View.GONE);
				panel_sort.setVisibility(View.GONE);
				dark_bg.setBackgroundColor(Color.TRANSPARENT);
			}
		});

	}

	private void getData() {
		isLoading = true;
		loadView.loading();
		// 每次请求前获取lat&lng
		bdLocation = application.getmBdLocation();
		if (bdLocation == null) {
			lat = "39.9";
			lng = "116.4";
		} else {
			lat = bdLocation.getLatitude() + "";
			lng = bdLocation.getLongitude() + "";
			Log.d("System.out", "lat:" + lat + "lng:" + lng);
		}
		api.getGoodsListByDistance(excataCode, distance, lat, lng, sortType,
				new RequestListener() {

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
						try {
							if (StrUtil.isHttpException(response)) {
								handler.sendEmptyMessage(Data.NONETWORK);
							} else {
								JSONObject jsonObject = new JSONObject(response);
								String res = "";
								if (jsonObject.has("goodsList")) {
									res = jsonObject.optString("goodsList");
								}
								if (res.equals("none")) {
									handler.sendEmptyMessage(Data.NODATA);
								} else {
									JSONArray goodsArray = jsonObject
											.optJSONArray("goodsList");
									dataList.clear();
									for (int i = 0; i < goodsArray.length(); i++) {
										JSONObject json = goodsArray
												.optJSONObject(i);
										ProvideAndNeed goods = new ProvideAndNeed(
												json);
										goods.setType(0);
										dataList.add(goods);
									}
									handler.sendEmptyMessage(Data.FULLDATA);
								}
							}
						} catch (JSONException e) {
							L.e(e.toString());
							handler.sendEmptyMessage(Data.NONETWORK);
						}
					}
				});

	}

	private void getParentCategory() {
		// 获取分类数据
		api.getGoodsCata(100, 0, 1, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				T.shortT(context, "未知错误:" + e.getMessage());
			}

			@Override
			public void onError(TixaException e) {
				L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
				// handler.sendEmptyMessage(Data.NONETWORK);
			}

			@Override
			public void onComplete(String response) {
				try {
					if (StrUtil.isHttpException(response)) {
						// handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject jsonObject = new JSONObject(response);
						String res = "";
						if (jsonObject.has("goodsCataList")) {
							res = jsonObject.optString("goodsCataList");
						}
						if (res.equals("none")) {
							// handler.sendEmptyMessage(Data.NODATA);
						} else {
							JSONArray goodsArray = jsonObject
									.optJSONArray("goodsCataList");
							goodsCatas.clear();
							for (int i = 0; i < goodsArray.length(); i++) {
								JSONObject json = goodsArray.optJSONObject(i);
								GoodsCata goodsCata = new GoodsCata(json);
								goodsCatas.add(goodsCata);
							}
							GoodsCata goodsCata_all = new GoodsCata();
							goodsCata_all.setCataName("全部");
							goodsCata_all.setCataCode("all");
							goodsCata_all.setCataImg("");
							goodsCatas.add(0, goodsCata_all);
							goodsCatas.get(0).setChecked(true);
							number = goodsCatas.size();
							count++;
							if (count % 2 == 0) {
								handler.sendEmptyMessage(COMPLETE);
							}
						}
					}
				} catch (JSONException e) {
					L.e(e.toString());
					// handler.sendEmptyMessage(Data.NONETWORK);
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
						JSONArray jsonArray = temObject
								.optJSONArray("subcategories");
						ArrayList<String> childCateList = new ArrayList<String>();
						for (int j = 0; j < jsonArray.length(); j++) {
							String t = jsonArray.optJSONObject(j).optString(
									"category_name");
							childCateList.add(t);
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

	private void getCategory(String parentCode) {
		// loadView.loading();
		if (parentCode.equals("all")) {
			GoodsCata goodsCata = new GoodsCata();
			goodsCata.setCataCode("0");
			goodsCata.setCataName("全部");
			goodsCata.setCataImg("");
			goodsCatasChild.clear();
			goodsCatasChild.add(goodsCata);
			Message msg = new Message();
			msg.what = GETCATEGORYDATA;
			msg.obj = goodsCatasChild;
			handler.sendMessage(msg);
		} else {
			progressView.setVisibility(View.VISIBLE);
			api.getSubGoodsCata(parentCode, 100, 0, 1, new RequestListener() {

				@Override
				public void onIOException(IOException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(TixaException e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onComplete(String response) {
					try {
						if (StrUtil.isHttpException(response)) {
							// handler.sendEmptyMessage(Data.NONETWORK);
						} else {
							JSONObject jsonObject = new JSONObject(response);
							String res = "";
							if (jsonObject.has("goodsCataChildList")) {
								res = jsonObject
										.optString("goodsCataChildList");
							}
							if (res.equals("none")) {
								// handler.sendEmptyMessage(Data.NODATA);
							} else {
								JSONArray goodsArray = jsonObject
										.optJSONArray("goodsCataChildList");
								goodsCatasChild.clear();
								for (int i = 0; i < goodsArray.length(); i++) {
									JSONObject json = goodsArray
											.optJSONObject(i);
									GoodsCata goodsCata = new GoodsCata(json);
									goodsCatasChild.add(goodsCata);
								}
								Message msg = new Message();
								msg.what = GETCATEGORYDATA;
								msg.obj = goodsCatasChild;
								handler.sendMessage(msg);
							}
						}
					} catch (JSONException e) {
						L.e(e.toString());
						// handler.sendEmptyMessage(Data.NONETWORK);
					}
				}
			});

		}

	}

	// ///////////////////////////////////////////////////////////////////////////
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.one) {
			// Intent intent = new Intent(context, DistanceActivity.class);
			// startActivityForResult(intent, requestCode);
			// System.out.println("requestCode:" + requestCode);

			relativeLayout.setVisibility(View.VISIBLE);
			relativeLayout.startAnimation(anim_enter);
			dark_bg.setBackgroundColor(getResources().getColor(R.color.dark_bg));
			relativeLayout.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					relativeLayout.startAnimation(anim_exit);
					return true;
				}
			});

		} else if (v.getId() == R.id.two) {
			if (count > 0 && count % 2 == 0) {
				if (goodsCatasChild.size() <= 0 && goodsCatas.size() > 0) {
					getCategory(goodsCatas.get(0).getCataCode());
				}

				panel_category.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						panel_category.startAnimation(anim_exit);
						return true;
					}
				});
				adapter_cate = new SimpleListAdapter_cate(context, goodsCatas);
				listView_left.setAdapter(adapter_cate);
				panel_category.setVisibility(View.VISIBLE);
				panel_category.startAnimation(anim_enter);
				dark_bg.setBackgroundColor(getResources().getColor(
						R.color.dark_bg));
				listView_left.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						position = position
								- listView_left.getHeaderViewsCount();
						if (position < number) {
							isDaz = false;
							String parentCode = "";
							for (int i = 0; i < goodsCatas.size(); i++) {
								if (i == position) {
									goodsCatas.get(position).setChecked(true);
									parentCode = goodsCatas.get(position)
											.getCataCode();
								} else {
									goodsCatas.get(i).setChecked(false);
								}
							}
							getCategory(parentCode);
							if (adapter_cate != null) {
								adapter_cate.notifyDataSetChanged();
							}
						} else {
							isDaz = true;
							ArrayList<String> childCateList = goodsCatas.get(
									position).getChildCateList();
							cateDaz = goodsCatas.get(position).getCataName();
							for (int i = 0; i < goodsCatas.size(); i++) {
								if (i == position) {
									goodsCatas.get(position).setChecked(true);
								} else {
									goodsCatas.get(i).setChecked(false);
								}
							}
							if (adapter_cate != null) {
								adapter_cate.notifyDataSetChanged();
							}
							tempData = new ArrayList<GoodsCata>();
							for (int j = 0; j < childCateList.size(); j++) {
								GoodsCata goodsCata = new GoodsCata();
								goodsCata.setCataName(childCateList.get(j));
								tempData.add(goodsCata);
							}
							adapter_catechild = new SimpleListAdapter_catechild(
									context, tempData);
							listView_right.setAdapter(adapter_catechild);

						}
					}
				});
				listView_right
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								if (!isDaz) {
									String temp = "";
									for (int i = 0; i < goodsCatasChild.size(); i++) {
										if (i == position) {
											goodsCatasChild.get(position)
													.setChecked(true);
											temp = goodsCatasChild
													.get(position)
													.getCataName();
											excataCode = goodsCatasChild.get(
													position).getCataCode();
											getData();
										} else {
											goodsCatasChild.get(i).setChecked(
													false);
										}
									}
									// listView_right.invalidate();
									if (adapter_catechild != null) {
										adapter_catechild
												.notifyDataSetChanged();
									}
									temp = temp.length() > 3 ? temp.substring(
											0, 3) + "..." : temp;
									category_textView.setText(temp);
									panel_category.startAnimation(anim_exit);
								} else {
									String temp = "";
									for (int i = 0; i < tempData.size(); i++) {
										if (i == position) {
											tempData.get(position).setChecked(
													true);
											temp = tempData.get(i)
													.getCataName();
										} else {
											tempData.get(i).setChecked(false);
										}
									}
									if (adapter_catechild != null) {
										adapter_catechild
												.notifyDataSetChanged();
									}
									String keyword = temp;
									temp = temp.length() > 3 ? temp.substring(
											0, 3) + "..." : temp;
									category_textView.setText(temp);
									panel_category.startAnimation(anim_exit);
									upData(cateDaz, keyword);
									// System.out.println("cateDaz:" + cateDaz
									// + "  keyword: " + keyword);
								}
							}
						});
			}
		} else if (v.getId() == R.id.three) {
			panel_sort.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					panel_sort.startAnimation(anim_exit);
					return true;
				}
			});
			panel_sort.setVisibility(View.VISIBLE);
			panel_sort.startAnimation(anim_enter);
			dark_bg.setBackgroundColor(getResources().getColor(R.color.dark_bg));
			SimpleListAdapter_sort adapter_sort = new SimpleListAdapter_sort(
					context, strs, index);
			listView_sort.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			listView_sort.setAdapter(adapter_sort);
			listView_sort.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					for (int i = 0; i < parent.getChildCount(); i++) {
						if (i == position) {
							((TextView) view).setTextColor(getResources()
									.getColor(R.color.orange1));
							sortType = (position + 1) + "";
							index = position;
							sort_textView
									.setText(getResources().getStringArray(
											R.array.sortArray_1)[position]);
							getData();
						} else {
							((TextView) parent.getChildAt(i))
									.setTextColor(Color.BLACK);
						}
					}
					panel_sort.startAnimation(anim_exit);

				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(context, data.getStringExtra("result") + "--provide", 1)
				.show();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (buttonView.getId() == R.id.checkBox_five) {
			if (!isLoading) {
				if (isChecked) {
					distance = "-1";
					distance_textView.setText("全部");
				} else {
					distance = "-1";
					distance_textView.setText("全部");
				}
				isLoading = true;
				getData();
			}
			if (isChecked) {
				checkBox_500.setChecked(false);
				checkBox_1k.setChecked(false);
				checkBox_2k.setChecked(false);
				checkBox_5k.setChecked(false);
				relativeLayout.startAnimation(anim_exit);

			} else {
				// checkBox_all.setChecked(true);
				relativeLayout.startAnimation(anim_exit);

			}
		}
		if (buttonView.getId() == R.id.checkBox_four) {
			if (!isLoading) {
				if (isChecked) {
					distance = "5";
					distance_textView.setText("5km");
				} else {
					distance = "-1";
					distance_textView.setText("全部");
				}
				isLoading = true;
				getData();
			}
			if (isChecked) {
				checkBox_500.setChecked(false);
				checkBox_1k.setChecked(false);
				checkBox_2k.setChecked(false);
				checkBox_all.setChecked(false);
				relativeLayout.startAnimation(anim_exit);
			} else {
				// checkBox_5k.setChecked(true);
				// checkBox_all.setChecked(false);
				relativeLayout.startAnimation(anim_exit);
			}
		}
		if (buttonView.getId() == R.id.checkBox_three) {

			if (!isLoading) {
				if (isChecked) {
					distance = "2";
					distance_textView.setText("2km");
				} else {
					distance = "-1";
					distance_textView.setText("全部");
				}
				isLoading = true;
				getData();
			}
			if (isChecked) {
				checkBox_500.setChecked(false);
				checkBox_1k.setChecked(false);
				checkBox_5k.setChecked(false);
				checkBox_all.setChecked(false);
				relativeLayout.startAnimation(anim_exit);
			} else {
				// checkBox_2k.setChecked(true);

				relativeLayout.startAnimation(anim_exit);
			}
		}
		if (buttonView.getId() == R.id.checkBox_two) {

			if (!isLoading) {
				if (isChecked) {
					distance = "1";
					distance_textView.setText("1km");
				} else {
					distance = "-1";
					distance_textView.setText("全部");
				}
				isLoading = true;
				getData();
			}
			if (isChecked) {
				checkBox_500.setChecked(false);
				checkBox_2k.setChecked(false);
				checkBox_5k.setChecked(false);
				checkBox_all.setChecked(false);
				relativeLayout.startAnimation(anim_exit);
			} else {
				// checkBox_1k.setChecked(true);

				relativeLayout.startAnimation(anim_exit);
			}
		}
		if (buttonView.getId() == R.id.checkBox_one) {

			if (!isLoading) {
				if (isChecked) {
					distance = "0.5";
					distance_textView.setText("500米");
				} else {
					distance = "-1";
					distance_textView.setText("全部");
				}
				isLoading = true;
				getData();
			}
			if (isChecked) {
				checkBox_1k.setChecked(false);
				checkBox_2k.setChecked(false);
				checkBox_5k.setChecked(false);
				checkBox_all.setChecked(false);
				relativeLayout.startAnimation(anim_exit);
			} else {
				// checkBox_500.setChecked(true);

				relativeLayout.startAnimation(anim_exit);
			}
		}
		if (!checkBox_500.isChecked() && !checkBox_1k.isChecked()
				&& !checkBox_2k.isChecked() && !checkBox_5k.isChecked()
				&& !checkBox_all.isChecked()) {
			checkBox_all.setChecked(true);
		}
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	private void upData(final String category, final String keyword) {
		isLoading = true;
		loadView.loading();
		new Thread() {
			public void run() {
				try {
					map.put("latitude", lat);
					map.put("longitude", lng);
					map.put("category", category);
					map.put("keyword", keyword);
					map.put("format", "json");
					map.put("radius", 5000 + "");
					String response = DianpingApi.requestApi(
							InterfaceURL.find_businesses_by_coordinate,
							Constants.appkey_dianping,
							Constants.appSecret_dianping, map);
					L.d("http", response);
					JSONObject json = new JSONObject(response);
					int total_count = json.optInt("total_count");
					int count = json.optInt("count");
					if (count == 0) {
						handler.sendEmptyMessage(Data.NODATA);
						return;
					}
					JSONArray array = json.optJSONArray("businesses");
					dataList.clear();
					for (int i = 0; i < array.length(); i++) {
						JSONObject tem = array.optJSONObject(i);
						ProvideAndNeed goods = new ProvideAndNeed(tem);
						if (total_count > count) {
							goods.setHasMore(true);
						} else {
							goods.setHasMore(false);
						}
						goods.setType(1);
						dataList.add(goods);
					}
					handler.sendEmptyMessage(Data.FULLDATA);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ProvideAndNeed provideAndNeed = (ProvideAndNeed) parent
				.getItemAtPosition(position);
		Intent intent = null;
		if (provideAndNeed.getType() == 0) {
			intent = new Intent(context, ProvideNeedDetail.class);
			intent.putExtra("goodsDetail", provideAndNeed);
		} else if (provideAndNeed.getType() == 1) {
			intent = new Intent(context, WebActivity.class);
			intent.putExtra("url", provideAndNeed.getBusiness_url());
		}
		startActivity(intent);
	}

}
