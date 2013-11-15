package com.start.jdzchina;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @ClassName: CrashHandler
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-10-21 下午04:29:28
 * 
 */

public class CrashHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private static CrashHandler crashHandler;
	private RapidApplication application;
	private static UncaughtExceptionHandler defaultUncaughtExceptionHandler;

	/**
	 * 创建�?��新的实例 CrashHandler.
	 */

	public CrashHandler(RapidApplication application) {
		this.application = application;
	}

	public static void init(RapidApplication application) {
		crashHandler = new CrashHandler(application);
		defaultUncaughtExceptionHandler = Thread
				.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(crashHandler);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// deal with uncaughtException
	}

}
