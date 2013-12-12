package com.star.middleframework;

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
	private MiddleApplication application;
	private static UncaughtExceptionHandler defaultUncaughtExceptionHandler;

	/**
	 * 创建新的实例 CrashHandler.
	 */

	public CrashHandler(MiddleApplication application) {
		this.application = application;
	}

	public static void init(MiddleApplication application) {
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
