package com.star.efficientdevelop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class StrUtil {
	/**
	 * 是否为数字
	 */
	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}
		String regex = "^((0|[1-9]|[1-9][0-9]+))$";
		boolean bol = str.matches(regex);
		if (bol == true) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否电话号码
	 */
	public static boolean isMoblie(String str) {
		if (StrUtil.isEmpty(str))
			return false;
		String regex = "^((\\+{0,1}86){0,1})1[0-9]{10}$";
		boolean bol = str.matches(regex);
		if (bol == true) {
			return true;
		} else {
			return false;
		}
	}

	public static String lineHight(String str) {
		SpannableString s = new SpannableString(str);
		Pattern p = Pattern.compile("abc", 0);
		Matcher m = p.matcher(s);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			s.setSpan(new ForegroundColorSpan(Color.RED), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return s.toString();
	}

	/**
	 * 是否http请求异常
	 */
	public static boolean isHttpException(String result) {
		if (isEmpty(result) || "-1".equals(result) || "-2".equals(result)
				|| "-6".equals(result) || "-7".equals(result)) {
			return true;
		}
		return false;
	}

	/**
	 * 限定字符串长度
	 */
	public static String formatNameLen(String name) {
		if (isEmpty(name)) {
			return "";
		} else if (name.length() <= 3) {
			return name;
		} else {
			return name.substring(0, 2) + "...";
		}
	}

	/**
	 * 格式化图片地址
	 */
	public static String formatPic(String pic, String domin) {
		if (isEmpty(pic))
			return "";
		if (pic.startsWith("http://")) {
			return pic;
		} else {
			return domin + pic.replace("/opt", "");
		}
	}

}
