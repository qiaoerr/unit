package com.star.middleframework.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.start.jdzchina.R;
import com.start.jdzchina.RapidApplication;
import com.start.jdzchina.model.BaseInforModel;
import com.start.jdzchina.util.BDLocationUtil;
import com.start.jdzchina.util.BDLocationUtil.LocationSuccessListener;
import com.start.jdzchina.util.BMapUtil;
import com.start.jdzchina.widget.LXProgressDialog;
import com.start.jdzchina.widget.MyRouteMapView;

public class MapFragment extends Fragment implements OnClickListener {
	public static final String BUS = "bus";
	public static final String DRIVER = "driver";
	public static final String WALK = "walk";
	private FragmentActivity context;
	private View view;
	private MyRouteMapView mMapView;
	private LXProgressDialog progressDialog;
	private MapController mMapController;
	private BDLocation bdLocation;
	private ImageButton mBtnPre;
	private ImageButton mBtnNext;
	private TextView popupText;
	private PopupOverlay pop;
	private int position = 0;// 表示第一个方�?
	private View viewCache;
	private String type = "";
	private TransitOverlay transitOverlay;
	private RouteOverlay routeOverlay;
	MKRoute route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用
	int nodeIndex = 0;// 节点索引,供浏览节点时使用
	private MKDrivingRouteResult drivingRouteResult;
	private MKWalkingRouteResult walkingRouteResult;
	private MKTransitRouteResult transitRouteResult;
	private MKMapViewListener mapViewListener;
	private ImageView bbus;
	private ImageView bwalk;
	private ImageView bcar;
	private ImageView navigate_style;
	private Animation animation_bbus_show;
	private Animation animation_bcar_show;
	private Animation animation_bwalk_show;
	private Animation animation_bbus_hide;
	private Animation animation_bcar_hide;
	private Animation animation_bwalk_hide;
	private MKSearch mkSearch;
	private MKPlanNode start;
	private MKPlanNode end;
	private LinearLayout search_result_detail_linearlayout;
	private RelativeLayout mapContainer;
	private boolean isShow = false;
	private TextView start_point;
	private TextView end_point;
	private BaseInforModel baseInfor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.map_layout, null);
		start_point = (TextView) view.findViewById(R.id.start_point);
		initData();
		initAnim();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		baseInfor = RapidApplication.getInstance().getBaseInfor();
		mMapView = RapidApplication.getInstance().getMapView();
		progressDialog = new LXProgressDialog(context, "定位�?..");
		progressDialog.show();
		BDLocationUtil.getLocation(context, new LocationSuccessListener() {

			@Override
			public void dealWithLocationData(BDLocation location) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				if (location == null) {
					Toast.makeText(context, "定位失败...", Toast.LENGTH_SHORT)
							.show();
				} else {
					bdLocation = location;
					start_point.setText(location.getAddrStr());
					MyLocationOverlay locationOverlay = new MyLocationOverlay(
							mMapView);
					LocationData locationData = new LocationData();
					locationData.latitude = location.getLatitude();
					locationData.longitude = location.getLongitude();
					locationOverlay.setData(locationData);
					locationOverlay.enableCompass();
					mMapView.getOverlays().add(locationOverlay);
					mMapView.refresh();
					mMapController.animateTo(new GeoPoint((int) (bdLocation
							.getLatitude() * 1e6), (int) (bdLocation
							.getLongitude() * 1e6)));
				}
			}
		});
		mkSearch = new MKSearch();
		mkSearch.init(RapidApplication.getInstance().getmBMapManager(),
				new MKSearchListener() {
					@Override
					public void onGetWalkingRouteResult(
							MKWalkingRouteResult arg0, int arg1) {
						progressDialog.dismiss();
						if (arg1 == 0) {
							walkingRouteResult = arg0;
							initOverlay();
						} else {
							Toast.makeText(context, "步行路线查询失败",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onGetTransitRouteResult(
							MKTransitRouteResult arg0, int arg1) {
						progressDialog.dismiss();
						if (arg1 == 0) {
							transitRouteResult = arg0;
							initOverlay();
						} else {
							Toast.makeText(context, "公交路线查询失败",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onGetDrivingRouteResult(
							MKDrivingRouteResult arg0, int arg1) {
						progressDialog.dismiss();
						if (arg1 == 0) {
							drivingRouteResult = arg0;
							initOverlay();
						} else {
							Toast.makeText(context, "自驾路线查询失败",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onGetSuggestionResult(MKSuggestionResult arg0,
							int arg1) {
					}

					@Override
					public void onGetPoiResult(MKPoiResult arg0, int arg1,
							int arg2) {
					}

					@Override
					public void onGetPoiDetailSearchResult(int arg0, int arg1) {
					}

					@Override
					public void onGetBusDetailResult(MKBusLineResult arg0,
							int arg1) {
					}

					@Override
					public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
					}
				});

		mapViewListener = new MKMapViewListener() {

			@Override
			public void onMapMoveFinish() {
				/**
				 * 地图完成带动画的操作（如: animationTo()）后，此回调被触�?
				 */
			}

			@Override
			public void onMapAnimationFinish() {

			}

			@Override
			public void onGetCurrentMap(Bitmap arg0) {
				/**
				 * 当调用过 mMapView.getCurrentMap()后，此回调会被触�?可在此保存截图至存储设备
				 */
			}

			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				/**
				 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
				 * mMapController.enableClick(true); 时，此回调才能被触发
				 * 
				 */
				String title = "";
				if (mapPoiInfo != null) {
					title = mapPoiInfo.strText;
					Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
					if (mMapController != null) {
						mMapController.animateTo(mapPoiInfo.geoPt);
					}
				}
			}
		};
		// mMapView.regMapViewListener(RapidApplication.getInstance()
		// .getmBMapManager(), mapViewListener);
	}

	private void initView() {
		end_point = (TextView) view.findViewById(R.id.end_point);
		end_point.setText(baseInfor.getAddress());
		mapContainer = (RelativeLayout) view.findViewById(R.id.mapContainer);
		search_result_detail_linearlayout = (LinearLayout) view
				.findViewById(R.id.search_result_detail_linearlayout);
		if (mMapView == null) {
			mMapView = new MyRouteMapView(context);
			RapidApplication.getInstance().setMapView(mMapView);
		}
		mapContainer.addView(mMapView);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		mBtnPre = (ImageButton) view.findViewById(R.id.pre);
		mBtnNext = (ImageButton) view.findViewById(R.id.next);
		mBtnPre.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		navigate_style = (ImageView) view.findViewById(R.id.navigate_style);
		bbus = (ImageView) view.findViewById(R.id.bbus);
		bcar = (ImageView) view.findViewById(R.id.bcar);
		bwalk = (ImageView) view.findViewById(R.id.bwalk);
		navigate_style.setOnClickListener(this);
		bbus.setOnClickListener(this);
		bcar.setOnClickListener(this);
		bwalk.setOnClickListener(this);
		// 隐藏
		startAnim_hide();
		createPaopao();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pre:
		case R.id.next:
			nodeClick(v);
			break;
		case R.id.navigate_style:
			if (isShow) {
				startAnim_hide();
			} else {
				startAnim_show();
			}
			break;
		case R.id.bbus:
			type = BUS;
			startMKsearch(BUS);
			startAnim_hide();
			break;
		case R.id.bcar:
			type = DRIVER;
			startMKsearch(DRIVER);
			startAnim_hide();
			break;
		case R.id.bwalk:
			type = WALK;
			startMKsearch(WALK);
			startAnim_hide();
			break;
		default:
			break;
		}

	}

	private void startMKsearch(String type) {
		if (bdLocation == null) {
			BDLocation temp = RapidApplication.getInstance().getBdLocation();
			if (temp == null) {
				Toast.makeText(context, "无法获取当前位置，请�?��手机网络设置",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				bdLocation = temp;
			}
		}
		start = new MKPlanNode();
		start.pt = new GeoPoint((int) (bdLocation.getLatitude() * 1e6),
				(int) (bdLocation.getLongitude() * 1e6));
		end = new MKPlanNode();
		end.pt = new GeoPoint((int) (baseInfor.getLatitude() * 1e6),
				(int) (baseInfor.getLongitude() * 1e6));
		if (type.equals(BUS)) {
			mkSearch.transitSearch(bdLocation.getCity(), start, end);
		} else if (type.equals(DRIVER)) {
			mkSearch.drivingSearch("", start, "", end);
		} else if (type.equals(WALK)) {
			mkSearch.walkingSearch("", start, "", end);
		}
		progressDialog = new LXProgressDialog(context, "搜索�?..");
		progressDialog.show();
	}

	private void startAnim_show() {
		isShow = true;
		bbus.startAnimation(animation_bbus_show);
		bcar.startAnimation(animation_bcar_show);
		bwalk.startAnimation(animation_bwalk_show);
	}

	private void startAnim_hide() {
		isShow = false;
		bbus.startAnimation(animation_bbus_hide);
		bcar.startAnimation(animation_bcar_hide);
		bwalk.startAnimation(animation_bwalk_hide);
	}

	private void initAnim() {
		// show
		animation_bbus_show = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		animation_bbus_show.setFillAfter(true);
		animation_bbus_show.setDuration(400);
		animation_bbus_show.setStartOffset(0);
		animation_bcar_show = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		animation_bcar_show.setFillAfter(true);
		animation_bcar_show.setDuration(400);
		animation_bcar_show.setStartOffset(200);
		animation_bwalk_show = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		animation_bwalk_show.setFillAfter(true);
		animation_bwalk_show.setDuration(400);
		animation_bwalk_show.setStartOffset(400);
		// hide
		animation_bbus_hide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		animation_bbus_hide.setFillAfter(true);
		animation_bbus_hide.setDuration(400);
		animation_bbus_hide.setStartOffset(0);
		animation_bcar_hide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		animation_bcar_hide.setFillAfter(true);
		animation_bcar_hide.setDuration(400);
		animation_bcar_hide.setStartOffset(200);
		animation_bwalk_hide = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		animation_bwalk_hide.setFillAfter(true);
		animation_bwalk_hide.setDuration(400);
		animation_bwalk_hide.setStartOffset(400);

	}

	public void createPaopao() {

		// 泡泡点击响应回调
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
				// Toast.makeText(context, "click", 1).show();
			}
		};
		pop = new PopupOverlay(mMapView, popListener);
		mMapView.setPop(pop);

	}

	/**
	 * 节点浏览
	 * */
	public void nodeClick(View v) {
		viewCache = LayoutInflater.from(context).inflate(
				R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);

		if (type.equals(DRIVER) || type.equals(WALK)) {
			// 驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
			if (type.equals(DRIVER)) {
				route = drivingRouteResult.getPlan(0).getRoute(0);
			} else {
				route = walkingRouteResult.getPlan(0).getRoute(0);
			}

			if (nodeIndex < -1 || route == null
					|| nodeIndex >= route.getNumSteps())
				return;

			// 上一个节�?
			if (mBtnPre.equals(v) && nodeIndex > 0) {
				// 索引�?
				nodeIndex--;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
			// 下一个节�?
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps() - 1)) {
				// 索引�?
				nodeIndex++;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
		} else {
			// 公交换乘使用的数据结构与其他不同，因此单独处理节点浏�?
			if (nodeIndex < -1 || transitOverlay == null
					|| nodeIndex >= transitOverlay.getAllItem().size())
				return;

			// 上一个节�?
			if (mBtnPre.equals(v) && nodeIndex > 1) {
				// 索引�?
				nodeIndex--;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						transitOverlay.getItem(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(), 5);
			}
			// 下一个节�?
			if (mBtnNext.equals(v)
					&& nodeIndex < (transitOverlay.getAllItem().size() - 2)) {
				// 索引�?
				nodeIndex++;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						transitOverlay.getItem(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(), 5);
			}
		}
	}

	private void initOverlay() {
		if (type.equals(BUS)) {
			transitOverlay = new TransitOverlay(context, mMapView);
			transitOverlay.setData(transitRouteResult.getPlan(position));
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(transitOverlay);
			mMapView.refresh();
			mMapController.zoomToSpan(transitOverlay.getLatSpanE6(),
					transitOverlay.getLonSpanE6());
			mMapController.animateTo(transitRouteResult.getStart().pt);
		} else {
			routeOverlay = new RouteOverlay(context, mMapView);
			if (type.equals(DRIVER)) {
				routeOverlay.setData(drivingRouteResult.getPlan(0).getRoute(
						position));
			} else if (type.equals(WALK)) {
				routeOverlay.setData(walkingRouteResult.getPlan(0).getRoute(
						position));
			}
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
			mMapController.zoomToSpan(routeOverlay.getLatSpanE6(),
					routeOverlay.getLonSpanE6());
			if (drivingRouteResult != null) {
				mMapController.animateTo(drivingRouteResult.getStart().pt);
			} else {
				mMapController.animateTo(walkingRouteResult.getStart().pt);
			}

		}
		nodeIndex = 0;
		search_result_detail_linearlayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		mapContainer.removeView(mMapView);
		if (pop != null) {
			pop.hidePop();
		}
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// mMapView.destroy();
		// mMapView = null;
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		// mMapView.onPause();
		// mMapView.destroy();
		// mMapView = null;
	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// mMapView.onSaveInstanceState(outState);
	// }
	//
	// protected void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onSaveInstanceState(savedInstanceState);
	// mMapView.onRestoreInstanceState(savedInstanceState);
	// }

}
