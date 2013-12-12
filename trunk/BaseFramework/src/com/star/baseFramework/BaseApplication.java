package com.star.baseFramework;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.star.baseFramework.config.Constants;
import com.star.baseFramework.util.L;

/**
 * @ClassName: BaseApplication
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-10-21 下午04:27:55
 * 
 */

public class BaseApplication extends Application {
	private static BaseApplication baseApplication;
	public boolean m_bKeyRight = true;
	private BMapManager mBMapManager = null;

	public static BaseApplication getInstance() {
		return baseApplication;
	}

	/**
	 * 打印手机屏幕信息 、判断是否开启百度地图、以及CrashHandler.init
	 */
	@Override
	public void onCreate() {
		baseApplication = this;
		super.onCreate();
		showScreenInfor();
		// CrashHandler.init(this);
		// 判断是否开启百度地图
		if (Constants.openBDmap) {
			initEngineManager(baseApplication);
		}
	}

	private void showScreenInfor() {
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		L.i("densityDpi: " + displayMetrics.densityDpi);
		L.i("widthPixels: " + displayMetrics.widthPixels);
		L.i("heightPixels: " + displayMetrics.heightPixels);
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}
		if (!mBMapManager
				.init(Constants.Key_baidu_map, new MyGeneralListener())) {
			Toast.makeText(baseApplication, "BMapManager  初始化错误!",
					Toast.LENGTH_LONG).show();
		}
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(baseApplication, "您的网络出错啦！", Toast.LENGTH_LONG)
						.show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(baseApplication, "输入正确的检索条件！", Toast.LENGTH_LONG)
						.show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(baseApplication,
						"请在 DemoApplication.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				baseApplication.m_bKeyRight = false;
			}
		}
	}

	public BMapManager getmBMapManager() {
		return mBMapManager;
	}

}
