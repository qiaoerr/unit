package com.tixa.industry.util;

import android.content.Context;
import android.widget.Toast;

public class T {
	
	public static void shortT (Context context,String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void longT (Context context,String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
}
