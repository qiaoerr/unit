package com.star.baseFramework.config;

import java.io.File;

import android.os.Environment;

public class BaseConstants {
	// 作为默认的LOG_TAG，缓存子目录等
	public static final String APP_SITE = "baseFramework";
	// 是否开发人员
	public static final boolean ISCODER = true;
	// 是否启用System.out输出日志
	public static final boolean SYSTEM_OUT = false;
	// 是否打印日志(日志输出开关)
	public static final boolean DEBUG = true;
	// 是否把日志写入SD卡
	public static final boolean PERSISTLOG = false;
	// 缓存SD卡主路径
	public static final String EXTERNAL_DIR = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ APP_SITE
			+ File.separator;
	// SD卡上log主目录
	public static final String LOGS_DIR = EXTERNAL_DIR + "logs"
			+ File.separator;
	// 默认logcat打印log的tag
	public static final String DEFAULT_LOG_TAG = APP_SITE;
	// 缓存目录
	public static final String CACHE_DIR = EXTERNAL_DIR + "cache"
			+ File.separator;
	// 缓存图片主目录
	public static final String IMG_DIR = EXTERNAL_DIR + "images"
			+ File.separator;
	// 百度地图相关
	public static final boolean openBDmap = true;
	public static final String Key_baidu_map = "AB39EEB5B8081625224CB1A366F8DBDF10E5CEC2";
}
