package com.tixa.industry.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtil {
	public static void show(Context context , CharSequence text ,int duration){
		Toast.makeText(context, text, duration).show();
	}
	public static void show(Context context , CharSequence text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	public static void log(String tag ,String msg ){
		Log.v(tag, msg);
	}
	public static void log(String tag ,String msg ,String type){
		if(type==null){
			Log.v(tag, msg);
		}else if(type.equals("d")){
			Log.d(tag, msg);
		}else if(type.equals("i")){
			Log.i(tag, msg);
		}else if(type.equals("e")){
			Log.e(tag, msg);
		}else {
			Log.v(tag, msg);
		}
	}
}
