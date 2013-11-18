package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.map.MyPoiOverlay;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

/**
 * @author administrator
 * @version
 * 
 */
public class AroundActivity extends Activity implements OnClickListener {

	private Context context;
	private TopBar topbar;
	private MapView mapView;
	private MapController mMapController = null;
	private ProvideAndNeed goodsDetail;
	private MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private BDLocation bdLocation;
	private MyLocationOverlay locationOverlay;
	private LocationData locationData;
	private RelativeLayout input_context_out;
	private EditText input_context;
	private ImageView search;
	private GeoPoint p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_around);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		goodsDetail = (ProvideAndNeed) getIntent().getSerializableExtra(
				"goodsDetail");
		bdLocation = IndustryApplication.getInstance().getmBdLocation();
		locationData = new LocationData();
		locationData.latitude = bdLocation.getLatitude();
		locationData.longitude = bdLocation.getLongitude();
		locationData.accuracy = bdLocation.getRadius();
		locationData.direction = bdLocation.getDerect();
		mSearch = new MKSearch();
		mSearch.init(IndustryApplication.getInstance().getmBMapManager(),
				new MKSearchListener() {
					@Override
					public void onGetPoiResult(MKPoiResult res, int arg1,
							int arg2) {
						if (arg2 != 0 || res == null) {
							Toast.makeText(context, "抱歉，未找到结果",
									Toast.LENGTH_LONG).show();
							return;
						}
						if (res.getCurrentNumPois() > 0) {
							MyPoiOverlay poiOverlay = new MyPoiOverlay(
									AroundActivity.this, mapView, mSearch);
							poiOverlay.setData(res.getAllPoi());
							mapView.getOverlays().clear();// //////b/u/g
							mapView.getOverlays().add(locationOverlay);
							mapView.getOverlays().add(poiOverlay);
							mapView.refresh();
							// 当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
							for (MKPoiInfo info : res.getAllPoi()) {
								if (info.pt != null) {
									mapView.getController().animateTo(info.pt);
									break;
								}
							}
						}

					}

					@Override
					public void onGetPoiDetailSearchResult(int arg0, int arg1) {
						if (arg1 != 0) {
							Toast.makeText(context, "抱歉，未找到结果",
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onGetWalkingRouteResult(
							MKWalkingRouteResult arg0, int arg1) {
					}

					@Override
					public void onGetTransitRouteResult(
							MKTransitRouteResult arg0, int arg1) {
					}

					@Override
					public void onGetSuggestionResult(MKSuggestionResult arg0,
							int arg1) {
					}

					@Override
					public void onGetDrivingRouteResult(
							MKDrivingRouteResult arg0, int arg1) {

					}

					@Override
					public void onGetBusDetailResult(MKBusLineResult arg0,
							int arg1) {
					}

					@Override
					public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
					}
				});

	}

	private void initView() {
		initTopbar();
		input_context_out = (RelativeLayout) findViewById(R.id.input_context_out);
		input_context = (EditText) findViewById(R.id.input_context);
		search = (ImageView) findViewById(R.id.search);
		search.setOnClickListener(this);
		mapView = (MapView) findViewById(R.id.mapView);
		mMapController = mapView.getController();
		mMapController = mapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(14);
		mapView.setBuiltInZoomControls(true);
		locationOverlay = new MyLocationOverlay(mapView);
		locationOverlay.setData(locationData);
		locationOverlay.enableCompass();
		mapView.getOverlays().add(locationOverlay);
		p = new GeoPoint((int) (bdLocation.getLatitude() * 1E6),
				(int) (bdLocation.getLongitude() * 1E6));
		mMapController.setCenter(p);
		mSearch.poiSearchNearBy("餐厅", p, 5000);
		// mSearch.poiSearchInCity(bdLocation.getCity(), "餐厅");
		// mSearch.poiSearchInCity("北京", "餐厅");
	}

	private void initTopbar() {
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("周边信息", R.drawable.top_back, R.drawable.write);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {
				if (input_context_out.getVisibility() == View.VISIBLE) {
					input_context_out.setVisibility(View.GONE);
				} else if (input_context_out.getVisibility() == View.GONE) {
					input_context_out.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});
	}

	@Override
	protected void onPause() {
		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		/**
		 * MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		/**
		 * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
		mapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onClick(View v) {

		String temp = input_context.getText().toString().trim();
		if (temp.equals("")) {
			input_context.setHint("您还没有输入内容");
		} else {
			input_context_out.setVisibility(View.GONE);
			mSearch.poiSearchNearBy(temp, p, 5000);
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(input_context.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}
}
