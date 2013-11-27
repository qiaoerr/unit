package com.start.jdzchina.config;

import java.io.File;

import android.os.Environment;

public class Constants {
	public static final String APP_SITE = "Star";
	// Json返回数据标识
	public static final int FULLDATA = 1001;
	public static final int NODATA = 1002;
	public static final int NONETWORK = 1003;
	// 缓存SD卡主路径
	public static final String EXTERNAL_DIR = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ APP_SITE
			+ File.separator;
	// 是否开发人员
	public static final boolean ISCODER = true;
	// 是否打印日志
	public static final boolean DEBUG = true;
	// 是否把日志写入SD卡
	public static final boolean PERSISTLOG = false;
	// 默认logcat打印log
	public static final String DEFAULT_LOG_TAG = APP_SITE;
	// log主目录
	public static final String LOGS_DIR = EXTERNAL_DIR + "logs"
			+ File.separator;
	// 图片服务器地址
	public static final String IMGIP = "http://b2c.tixaapp.com/";
	// 缓存目录
	public static final String CACHE_DIR = EXTERNAL_DIR + "cache"
			+ File.separator;
	// 缓存图片主目录
	public static final String IMG_DIR = EXTERNAL_DIR + "images"
			+ File.separator;
	// 广播action
	public static final String HOME = "com.tixa.express.home";

	// 百度地图相关
	public static final boolean openBDmap = true;
	public static final String Key_baidu_map = "AB39EEB5B8081625224CB1A366F8DBDF10E5CEC2";
	public static final boolean EnterLocation = true;// 进入就定位
	// 长宽比例配置
	public static final float ratio_gridView = 2f / 3;
}
