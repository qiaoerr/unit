package com.tixa.industry.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import com.tixa.industry.config.Constants;

import android.util.Log;


/**
 * 日志工具类
 * @author shengy
 *
 */
public class L {

	/**
	 * System.out.println 打印日志
	 * @param log
	 */
	public static void p(String log)
	{
		if(Constants.DEBUG)
		{
			System.out.println(log);
		}
		L.d(log);
	}

	public static void v(String text)
	{
		print("",text, Log.VERBOSE);
	}
	
	public static void v(String TAG, String text) {
		print(TAG ,text, Log.VERBOSE);
	}
	
	
	public static void d(String text)
	{
		print("", text, Log.DEBUG);
	}
	
	public static void d(String TAG, String text) {
		print(TAG ,text, Log.DEBUG);
	}
	
	public static void i(String text)
	{
		print("",text, Log.INFO);
	}
	
	public static void i(String TAG, String text)
	{
		print(TAG, text, Log.INFO);
	}
	
	public static void w(String text)
	{
		print("",text, Log.WARN);
	}
	
	public static void w(String TAG, String text)
	{
		print(TAG,text, Log.WARN);
	}
	
	public static void e(String text)
	{
		print("",text, Log.ERROR);
	}
	
	public static void e(String TAG, String text)
	{
		print(TAG,text, Log.ERROR);
	}
	
	public static void e(String text,Throwable throwable)
	{
		print("",text+"#message:"+throwable.getMessage(),Log.ERROR);
		StackTraceElement[] elements = throwable.getStackTrace();
		for(StackTraceElement e : elements)
		{
			print("",e.toString(), Log.ERROR);
		}
	}
	
	public static void e(String TAG, String text,Throwable throwable)
	{
		print(TAG,text+"#message:"+throwable.getMessage(),Log.ERROR);
		StackTraceElement[] elements = throwable.getStackTrace();
		for(StackTraceElement e : elements)
		{
			print(TAG,e.toString(), Log.ERROR);
		}
	}
	
	private static synchronized void print(String TAG, final String text, final int level) {
		if(StrUtil.isEmpty(text)) {
			return;
		}		
		if(StrUtil.isEmpty(TAG)) {
			TAG = Constants.DEFAULT_LOG_TAG;
		}
		
		if(Constants.DEBUG) {
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
		}
		
		if(Constants.PERSISTLOG) {
			ThreadPoolUtil.execute(new Runnable() {					
				@Override
				public void run() {
					writeLog(text, level);
				}
			});
		}
			
		
	}
	
	/**
	 * 把日志写入到SD卡中
	 * @param text
	 * @param level
	 */
	private static synchronized void writeLog(String text, int level )
	{
		StringBuilder sb = new StringBuilder();
		sb.append("["+DateUtil.dateFormat(new Date(), "HH:mm:ss") +"]");
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
			String fileName = Constants.LOGS_DIR +"log.txt";
			File logFile = new File(fileName);
			if(!logFile.exists())
			{	
				File logs = new File(Constants.LOGS_DIR);
				if(!logs.exists())
				{
					logs.mkdirs();
				}
				logFile.createNewFile();
			}
			raf = new RandomAccessFile(fileName, "rw");
			raf.seek(raf.length());
			raf.writeBytes(sb.toString()+text+"\r\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(raf!=null)
			{
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	
	
}
