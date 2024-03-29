package com.star.baseFramework.util;

import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AndroidUtil {
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	/**
	 * 检测网络链接
	 */
	public static boolean isConnectInternet(Context context) {

		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();
		}
		return false;
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public int getNetworkType(Context context) {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StrUtil.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	/**
	 * 获取唯一设备id
	 */
	public static String getDiviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 为本app添加到桌面快捷方式
	 */
	public static void addShortcut(Context context, String shortName,
			int iconRecourceId) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// SHORTCUT_NAME
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortName);
		shortcut.putExtra("duplicate", false); // not allow Repeat
		/* current Activity shortcuts to launch objects:such as
		com.everest.video.VideoPlayer
		Note: the ComponentName second parameters must be coupled with a dot
		(. ), or a shortcut to start the corresponding procedures*/
		ComponentName comp = new ComponentName(context.getPackageName(),
				".ui.MainAct");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
				Intent.ACTION_MAIN).setComponent(comp));
		// SHORTCUT_ICON
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				context, iconRecourceId);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		context.sendBroadcast(shortcut);
	}

	/**
	 * 为targetClassName的app添加到桌面快捷方式
	 */
	public static void addShortcut(Context context, String appName,
			int appLogoId, String targetClassName) {
		try {
			Intent shortcut = new Intent(
					"com.android.launcher.action.INSTALL_SHORTCUT");
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
			shortcut.putExtra("duplicate", false); // not allow Repeat
			Intent extraIntent = new Intent(context,
					Class.forName(targetClassName));
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, extraIntent);
			ShortcutIconResource iconRes = Intent.ShortcutIconResource
					.fromContext(context, appLogoId);
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
			context.sendBroadcast(shortcut);
		} catch (ClassNotFoundException e) {
		}
	}

	/**
	 * 将某人的电话号码添加到桌面快捷方式
	 */
	public static void addShortcut(Context context, String name, String mobile,
			int iconRecourceId) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// SHORTCUT_NAME
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		shortcut.putExtra("duplicate", false); // not allow Repeat
		Intent extraIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://"
				+ mobile));
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, extraIntent);
		// SHORTCUT_icon
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				context, iconRecourceId);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		context.sendBroadcast(shortcut);
	}

	/**
	 * 获取用户终端设备信息,输出到控制台
	 */
	public static void showPhoneInfor(Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		L.i("densityDpi: " + displayMetrics.densityDpi);
		L.i("widthPixels: " + displayMetrics.widthPixels);
		L.i("heightPixels: " + displayMetrics.heightPixels);
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
		L.i("phoneInfor:  " + phoneInfo);
	}

	/**
	 * 获取用户终端设备信息
	 */
	public static String getUserAgent() {
		String user_agent = "";
		try {
			String model = Build.MODEL;
			int sdkNum = Build.VERSION.SDK_INT;
			String frameNum = Build.VERSION.RELEASE;
			user_agent = "Mobile Model-->" + model + "\n SDK Model-->" + sdkNum
					+ "\n System Model-->" + frameNum
					+ "\n Version information-->0.5";
			return user_agent;
		} catch (Exception e) {
		}
		return user_agent;
	}

	public static boolean hasLocalChina() {
		boolean has = false;
		Locale locale[] = Locale.getAvailableLocales();
		for (int i = 0; i < locale.length; i++) {
			if (locale[i].equals(Locale.CHINA)) {
				has = true;
				break;
			}
		}
		return has;
	}

	/**
	 * 获取屏幕方向横屏还是竖屏
	 */
	public static int ScreenOrient(Activity activity) {
		int orient = activity.getRequestedOrientation();
		if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				&& orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			DisplayMetrics displayMetrics = activity.getResources()
					.getDisplayMetrics();
			int screenWidth = displayMetrics.widthPixels;
			int screenHeight = displayMetrics.heightPixels;
			orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
					: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		}
		return orient;
	}

	/**
	 * 根据屏幕方向设置背景
	 */
	public static void AutoBackGround(Activity activity, View view,
			int backGround_v, int backGround_h) {
		int orient = ScreenOrient(activity);
		if /*Vertical*/(orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			view.setBackgroundResource(backGround_v);
		} /* Horizontal*/else {
			view.setBackgroundResource(backGround_h);
		}
	}

	/**
	 * 获取SDK的版本号
	 */
	public static int getSDKVersionNumber() {

		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 收起软键盘
	 */
	public static void collapseSoftInputMethod(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		try {
			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.toString();
		}
	}

	/**
	 * 获取包名
	 */
	public static String getPakageName(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			/* int versionCode = info.versionCode;
			 String versionName = info.versionName;*/
			String packageName = info.packageName;
			return packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "获取包名失败";
		}
	}

	/**
	 * 
	 * 检测该包名所对应的应用是否存在(安装)
	 */
	public static boolean checkInstall(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			context.getPackageManager().getPackageInfo(packageName, 0);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

}
