package com.tixa.industry.config;

import java.io.File;

import android.os.Environment;

import com.tixa.industry.R;

public class Constants {
	public static final String APP_NAME = "行业电商平台";

	public static final String APP_SITE = "Industry";

	// 是否是开发人员（true则不发送邮件）

	public static boolean isCorder = false;
	// 是否是内网
	public static final boolean isIntranet = false;

	public static final String TESTNAME = !isCorder ? "" : "18600368546";
	public static final String TESTPASSWORD = !isCorder ? "" : "123456";
	// 是否打印日志
	public static final boolean DEBUG = true;
	// 是否把日志写入SD卡
	public static final boolean PERSISTLOG = false;
	public static final String MY_LOGIN_SUCCESS_ACTION = "com.tixa.action.loginSuccessAction";
	public static final String MY_LOGOUT_SUCCESS_ACTION = "com.tixa.action.logOutSuccessAction";
	// 注册成功广播
	public static final String MY_REGISTER_SUCCESS_ACTION = "com.tixa.action.registerSuccessAction";

	// 缓存文件名
	public static final String SP_NAME = APP_SITE;
	// 默认logcat打印log
	public static final String DEFAULT_LOG_TAG = APP_SITE;
	// 缓存SD卡主路径
	public static final String EXTERNAL_DIR = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ APP_SITE
			+ File.separator;
	// 缓存目录
	public static final String CACHE_DIR = EXTERNAL_DIR + "cache"
			+ File.separator;
	// log主目录
	public static final String LOGS_DIR = EXTERNAL_DIR + "logs"
			+ File.separator;
	// 缓存图片主目录
	public static final String IMG_DIR = EXTERNAL_DIR + "images"
			+ File.separator;
	// 缓存浏览记录
	public static final String SKIM_DIR = "skimRecoderCache";
	// 保存登陆用户信息

	public static final String LOGINTABLENAME = "logininfo";
	public static final String DBNAME = "hy_db";

	public static final String DATABASE_NAME = "tx_db";
	public static final int DATABASE_VERSION = 1;

	// 新闻背景颜色
	public static int article_colors[] = { R.color.news_violet,
			R.color.news_blue, R.color.news_green, R.color.news_orange,
			R.color.news_yellow };

	public static int nav_images[] = { R.drawable.nav1, R.drawable.nav2,
			R.drawable.nav3, R.drawable.nav4, R.drawable.nav5, R.drawable.nav6,
			R.drawable.nav7, R.drawable.nav8, R.drawable.nav9,
			R.drawable.nav10, R.drawable.nav11, R.drawable.nav12,
			R.drawable.nav13, R.drawable.nav14 };
	// 辨别供应商品与求购商品
	public static final int SELL = 1;// 供应商品
	public static final int BUY = 2;// 求购商品
	// 淘宝
	public static final String appkey = "12228835";
	public static final String appSecret = "1737d0b5cd6de2e534dbb88466a86f48";
	public static final int TYEP_BRAND = 0;// 品牌
	public static final int TYEP_CAT = 1;// 分类

	// 屏幕参考尺寸
	public static final double WIDTH = 320;
	public static final double HEIGHT = 410;
	public static final String LOGIN_ACTION = "android.intent.action.login";// 登陆
	// 大众点评
	public static final String appkey_dianping = "61028930";
	public static final String appSecret_dianping = "2883dace75cf44e1a41487c17f979604";
}
