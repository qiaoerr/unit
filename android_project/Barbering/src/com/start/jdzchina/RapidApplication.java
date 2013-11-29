package com.start.jdzchina;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.start.jdzchina.config.Constants;
import com.start.jdzchina.model.BaseInforModel;
import com.start.jdzchina.util.BDLocationUtil;
import com.start.jdzchina.util.BDLocationUtil.LocationSuccessListener;
import com.start.jdzchina.widget.MyRouteMapView;

/**
 * @ClassName: ExpressApplication
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-10-21 下午04:27:55
 * 
 */

public class RapidApplication extends Application {
	private static RapidApplication expressApplication;
	public boolean m_bKeyRight = true;
	BMapManager mBMapManager = null;
	private BDLocation bdLocation = null;
	private MyRouteMapView mapView;
	private BaseInforModel baseInfor;

	public static RapidApplication getInstance() {
		return expressApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		expressApplication = this;
		showScreenInfor();
		// 初始化极光推送
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		// CrashHandler.init(this);
		// 判断是否开启百度地图
		if (Constants.openBDmap) {
			initEngineManager(this);
		}
		// 判断是否开启进入app即开始定位
		if (Constants.EnterLocation) {
			BDLocationUtil.getLocation(expressApplication,
					new LocationSuccessListener() {

						@Override
						public void dealWithLocationData(BDLocation location) {
							bdLocation = location;
						}
					});
		}
		// init baseinfor
		initBaseInfor();
	}

	private void showScreenInfor() {
		System.out.println("densityDpi: "
				+ this.getResources().getDisplayMetrics().densityDpi);
		System.out.println("widthPixels: "
				+ this.getResources().getDisplayMetrics().widthPixels);
		System.out.println("heightPixels: "
				+ this.getResources().getDisplayMetrics().heightPixels);
		String phoneInfo = "Product: " + android.os.Build.PRODUCT;
		phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
		phoneInfo += ", TAGS: " + android.os.Build.TAGS;
		phoneInfo += ", VERSION_CODES.BASE: "
				+ android.os.Build.VERSION_CODES.BASE;
		phoneInfo += ", MODEL: " + android.os.Build.MODEL;
		phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK_INT;
		phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
		phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
		phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
		phoneInfo += ", BRAND: " + android.os.Build.BRAND;
		phoneInfo += ", BOARD: " + android.os.Build.BOARD;
		phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
		phoneInfo += ", ID: " + android.os.Build.ID;
		phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
		phoneInfo += ", USER: " + android.os.Build.USER;
		System.out.println("phoneInfor:  " + phoneInfo);

	}

	private void initBaseInfor() {
		baseInfor = new BaseInforModel();
		baseInfor.setAddress("北京市海淀区上地十街十号");
		baseInfor.setLatitude(40.056885f);
		baseInfor.setLongitude(116.30815f);
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager
				.init(Constants.Key_baidu_map, new MyGeneralListener())) {
			Toast.makeText(expressApplication, "BMapManager  初始化错误!",
					Toast.LENGTH_LONG).show();
		} else {
			// mBMapManager.start();
			// mapView = new MyRouteMapView(expressApplication);
		}

	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(expressApplication, "您的网络出错啦！",
						Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(expressApplication, "输入正确的检索条件！",
						Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(expressApplication,
						"请在 DemoApplication.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				expressApplication.m_bKeyRight = false;
			}
		}
	}

	public BDLocation getBdLocation() {
		return bdLocation;
	}

	public BMapManager getmBMapManager() {
		return mBMapManager;
	}

	public MyRouteMapView getMapView() {
		return mapView;
	}

	public void setMapView(MyRouteMapView mapView) {
		this.mapView = mapView;
	}

	public BaseInforModel getBaseInfor() {
		return baseInfor;
	}

}
