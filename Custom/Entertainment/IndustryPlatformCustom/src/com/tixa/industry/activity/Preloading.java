package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tixa.industry.R;

public class Preloading extends Activity {
	private Context context;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				startActivity(new Intent(context, EntranceActivity.class));
				finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		layout.setBackgroundResource(R.drawable.proloading);
		setContentView(layout, params);
		loading();
	}

	private void loading() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessageDelayed(1, 3000);
			}
		}).start();
	}
}
