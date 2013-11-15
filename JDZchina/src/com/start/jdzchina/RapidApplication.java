package com.start.jdzchina;

import android.app.Application;

/**
 * @ClassName: ExpressApplication
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-10-21 下午04:27:55
 * 
 */

public class RapidApplication extends Application {
	private static RapidApplication expressApplication;

	public static RapidApplication getInstance() {
		return expressApplication;
	}

	@Override
	public void onCreate() {
		expressApplication = this;
		super.onCreate();
		// CrashHandler.init(this);
	}

}
