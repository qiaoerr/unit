package com.star.baseFramework.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import android.util.Log;

import com.star.baseFramework.config.BaseConstants;

/**
 * 日志工具类
 */
public class L {

	public static void d(String text) {
		print("", text, Log.DEBUG);
	}

	public static void d(String TAG, String text) {
		print(TAG, text, Log.DEBUG);
	}

	public static void i(String text) {
		print("", text, Log.INFO);
	}

	public static void i(String TAG, String text) {
		print(TAG, text, Log.INFO);
	}

	public static void e(String text) {
		print("", text, Log.ERROR);
	}

	public static void e(String TAG, String text) {
		print(TAG, text, Log.ERROR);
	}

	public static void e(String TAG, String text, Throwable throwable) {
		print(TAG, text + "#message:" + throwable.getMessage(), Log.ERROR);
		StackTraceElement[] elements = throwable.getStackTrace();
		for (StackTraceElement e : elements) {
			print(TAG, e.toString(), Log.ERROR);
		}
	}

	private static void print(String TAG, String text, int level) {
		if (StrUtil.isEmpty(text)) {
			return;
		}
		if (StrUtil.isEmpty(TAG)) {
			// TAG未填写时，采用默认的
			TAG = BaseConstants.DEFAULT_LOG_TAG;
		}
		if (BaseConstants.DEBUG) {
			switch (level) {
			case Log.VERBOSE:
				Log.v(TAG, text);
				break;
			case Log.DEBUG:
				Log.d(TAG, text);
				break;
			case Log.INFO:
				Log.i(TAG, text);
				break;
			case Log.WARN:
				Log.w(TAG, text);
				break;
			case Log.ERROR:
				Log.e(TAG, text);
				break;
			}
			// 是否在System.out中也打印日志
			if (BaseConstants.SYSTEM_OUT) {
				System.out.println(text);
			}
		}
		if (BaseConstants.PERSISTLOG) {
			writeLog(text, level);
		}

	}

	/**
	 * 把日志写入到SD卡中
	 */
	private static synchronized void writeLog(String text, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append("[" + DateUtil.dateFormat(new Date(), "HH:mm:ss") + "]");
		switch (level) {
		case Log.VERBOSE:
			sb.append("[VERBOSE]\t");
			break;
		case Log.DEBUG:
			sb.append("[DEBUG]\t");
			break;
		case Log.INFO:
			sb.append("[INFO ]\t");
			break;
		case Log.WARN:
			sb.append("[WARN ]\t");
			break;
		case Log.ERROR:
			sb.append("[ERROR]\t");
			break;
		}

		RandomAccessFile raf = null;
		try {
			String fileName = BaseConstants.LOGS_DIR + "log.txt";
			File logFile = new File(fileName);
			if (!logFile.exists()) {
				File logs = new File(BaseConstants.LOGS_DIR);
				if (!logs.exists()) {
					logs.mkdirs();
				}
				logFile.createNewFile();
			}
			raf = new RandomAccessFile(fileName, "rw");
			raf.seek(raf.length());
			raf.writeBytes(sb.toString() + text + "\r\n");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
