package com.star.general.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class MapActivity extends Activity {
	private Context context;
	private RelativeLayout container;
	private int screenWidth;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private float scale;
	private MapController mMapController;
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		scale = BaseCommonUtil.getScale(context);
		screenWidth = BaseCommonUtil.getScreenWidth(context);
	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 17, 0, screenWidth / 18, 0);
		params.addRule(RelativeLayout.ABOVE, 10002);
		container.addView(linearLayout, params);
		// mapView
		mapView = new MapView(context);
		mMapController = mapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		/*	MyLocationOverlay locationOverlay = new MyLocationOverlay(mapView);
			LocationData locationData = new LocationData();
			locationData.latitude = location.getLatitude();
			locationData.longitude = location.getLongitude();
			locationOverlay.setData(locationData);
			locationOverlay.enableCompass();
			mapView.getOverlays().add(locationOverlay);
			mapView.refresh();
			mMapController.animateTo(new GeoPoint(
					(int) (bdLocation.getLatitude() * 1e6), (int) (bdLocation
							.getLongitude() * 1e6)));*/
		android.widget.LinearLayout.LayoutParams params_linear = new android.widget.LinearLayout.LayoutParams(
				screenWidth * 8 / 9, -1);
		params_linear.weight = 1;
		mapView.setLayoutParams(params_linear);
		linearLayout.addView(mapView);
		// linear_address
		LinearLayout linear_address = new LinearLayout(context);
		linear_address.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout.addView(linear_address);
		TextView address = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-2, -2);
		address.setLayoutParams(params_linear);
		address.setText("公司地址:");
		address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		address.setPadding((int) (10 * scale), (int) (10 * scale), 0,
				(int) (5 * scale));
		address.setTextColor(context.getResources().getColor(R.color.black));
		linear_address.addView(address);
		TextView address_detail = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-2, -2);
		params_linear.setMargins(10, 0, 0, 0);
		address_detail.setLayoutParams(params_linear);
		address_detail.setText("北京市海淀区西三环中路19号");
		address_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		address_detail.setPadding(0, (int) (10 * scale), (int) (10 * scale),
				(int) (5 * scale));
		address_detail.setTextColor(context.getResources().getColor(
				R.color.black));
		linear_address.addView(address_detail);
		// connection
		LinearLayout linear_connect = new LinearLayout(context);
		linear_connect.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout.addView(linear_connect);
		TextView connect = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-2, -2);
		connect.setLayoutParams(params_linear);
		connect.setText("联系方式:");
		connect.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		connect.setPadding((int) (10 * scale), (int) (5 * scale), 0,
				(int) (5 * scale));
		connect.setTextColor(context.getResources().getColor(R.color.black));
		linear_connect.addView(connect);
		TextView connect_detail = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-2, -2);
		params_linear.setMargins(10, 0, 0, 0);
		connect_detail.setLayoutParams(params_linear);
		connect_detail.setText("18612364334");
		connect_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		connect_detail.setPadding(0, (int) (5 * scale), (int) (10 * scale),
				(int) (5 * scale));
		connect_detail.setTextColor(context.getResources().getColor(
				R.color.blue));
		linear_connect.addView(connect_detail);
		// email
		LinearLayout linear_email = new LinearLayout(context);
		linear_email.setGravity(Gravity.CENTER_VERTICAL);
		linearLayout.addView(linear_email);
		TextView email = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-2, -2);
		email.setLayoutParams(params_linear);
		email.setText("公司地址:");
		email.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		email.setPadding((int) (10 * scale), (int) (5 * scale), 0,
				(int) (10 * scale));
		email.setTextColor(context.getResources().getColor(R.color.black));
		linear_email.addView(email);
		TextView email_detail = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-2, -2);
		params_linear.setMargins(10, 0, 0, 0);
		email_detail.setLayoutParams(params_linear);
		email_detail.setText("appdian@qq.com");
		email_detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		email_detail.setPadding(0, (int) (5 * scale), (int) (10 * scale),
				(int) (10 * scale));
		email_detail
				.setTextColor(context.getResources().getColor(R.color.blue));
		linear_email.addView(email_detail);
		// buttomBar
		buttomBar = new ButtomBar(context);
		buttomBar.setConfig(R.drawable.bottom_return, R.drawable.bottom_share,
				R.drawable.bottom_setting, new OnclickListener() {

					@Override
					public void rightClick() {
						Intent intent = new Intent(context,
								SettingActivity.class);
						startActivity(intent);
					}

					@Override
					public void midClick() {
						share();
					}

					@Override
					public void leftClick() {
						finish();
					}
				});
		buttomBar.setId(10002);
		params = new LayoutParams(-2, -2);
		params.setMargins(0, 0, 0, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttomBar.setLayoutParams(params);
		container.addView(buttomBar);
	}

	// 分享
	private void share() {
		String shareContent = "";
		if (shareContent == null || shareContent.equals("")) {
			shareContent = "嗨，我正在使用星火客户端，赶快来试试吧！！";
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
		intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "软件分享"));
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

}
