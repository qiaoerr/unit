package com.star.efficientdevelop.util;

import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AndroidUtil {
	/**
	 * whether the mobile phone network is Connecting
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnectInternet(Context context) {

		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}

	public static String getDiviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * Create shortcut
	 * 
	 * @param context
	 * @param sourceId
	 */
	public static void addShortcut(Context context, int nameSourceId,
			int iconRecourceId) {
		String appName = context.getApplicationContext().getResources()
				.getString(nameSourceId);
		addShortcut(context, appName, iconRecourceId);
	}

	public static void addShortcut(Context context, String shortName,
			int iconRecourceId) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// SHORTCUT_NAME
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortName);
		shortcut.putExtra("duplicate", false); // not allow Repeat
		// current Activity shortcuts to launch objects:such as
		// //com.everest.video.VideoPlayer
		// Note: the ComponentName second parameters must be coupled with a dot
		// (. ), or a shortcut to start the corresponding procedures
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

	// add someone's shortcut
	public static void addShortcut(Context context, String name, String mobile,
			int iconRecourceId) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// SHORTCUT_NAME
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		shortcut.putExtra("duplicate", false);
		Intent extraIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel://"
				+ mobile));
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, extraIntent);
		// SHORTCUT_icon
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				context, iconRecourceId);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		context.sendBroadcast(shortcut);
	}

	public static String getUserAgent() {
		String user_agent = "";
		try {
			String model = Build.MODEL;
			String sdkNum = Build.VERSION.SDK;
			String frameNum = Build.VERSION.RELEASE;
			// user_agent = "android_" + Build.MODEL + "_" + frameNum + "_"
			// + versonNum + ",SDKNum ";
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

	public static int ScreenOrient(Activity activity) {
		int orient = activity.getRequestedOrientation();
		if (orient != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				&& orient != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			WindowManager wm = activity.getWindowManager();
			Display display = wm.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			// height>width ? Vertical screen 锟斤拷Horizontal screen
			orient = screenWidth < screenHeight ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
					: ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		}
		return orient;
	}

	public static void AutoBackGround(Activity activity, View view,
			int backGround_v, int backGround_h) {
		int orient = ScreenOrient(activity);
		if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {// Vertical
			view.setBackgroundResource(backGround_v);
		} else {// Horizontal
			view.setBackgroundResource(backGround_h);
		}
	}

	public static int getSDKVersionNumber() {
		int sdkVersion = 0;
		try {

			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK_INT);

		} catch (NumberFormatException e) {

			sdkVersion = 0;
		}
		return sdkVersion;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// get ListView's Adapter
		if (listView == null) {
			return;
		}
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷�?
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // Calculate childView's height and width
			totalHeight += listItem.getMeasuredHeight(); // Calculate all
															// childView's
															// height
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight() get divder's height
		// params.height get the final height that ListView can show completely
		listView.setLayoutParams(params);
	}

	/**
	 * collapseSoftInput
	 * 
	 * @param context
	 * @param view
	 */
	public static void collapseSoftInputMethod(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		/*
		 * if(imm.isActive(view)) {
		 * imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
		 * InputMethodManager.HIDE_NOT_ALWAYS); }
		 */
		try {
			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.toString();
		}
		/*
		 * imm.hideSoftInputFromWindow(view.getWindowToken(),
		 * InputMethodManager.HIDE_IMPLICIT_ONLY);
		 */
	}

	/**
	 * getVersion
	 * 
	 */
	public static String getVersion(Context context) {
		String version = "0.0.0";

		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			version = packageInfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return version;
	}

	/**
	 * 获取包信
	 * 
	 * @param context
	 * @return
	 */
	public static String getPakageName(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			// int versionCode = info.versionCode;
			// String versionName = info.versionName;
			String packageName = info.packageName;
			return packageName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "获取包名失败";
		}
	}

	/**
	 * 获取包信�?
	 * 
	 * @param context
	 * @return
	 */
	public static String getPakageInfo(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			int versionCode = info.versionCode;
			String versionName = info.versionName;
			String packageName = info.packageName;
			return "程序包名:" + packageName + ",程序版本�?" + versionCode
					+ ",程序版本名称:" + versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "获取包名失败";
		}
	}
}
