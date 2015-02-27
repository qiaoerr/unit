/**
 * @Title: JumptoActivity.java
 * @Package com.example.waptoapptestto
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:搜房无线
 * 
 * @author liu
 * @date 2015-2-27 下午5:24:47
 * @version V1.0
 */

package com.example.waptoapptestto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @ClassName: JumptoActivity
 * @Description: TODO
 * 
 */

public class JumptoActivity extends Activity {

	TextView tv_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_show = (TextView) findViewById(R.id.tv_show);
		Intent intent = getIntent();
		String action = intent.getAction();
		if (Intent.ACTION_VIEW.equals(action)) {
			Uri uri = intent.getData();
			if (uri != null) {
				String data1 = uri.getQueryParameter("data1");
				String data2 = uri.getQueryParameter("data2");
				android.util.Log.v("data1", data1);
				android.util.Log.v("data2", data2);
			}
		}
		String data = intent.getDataString();
		System.out.println(data);
		tv_show.setText(data);

	};
}
