package com.star.general.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.star.baseFramework.util.AndroidUtil;
import com.star.general.R;
import com.star.general.config.Constants;

public class PreloadingActivity extends Activity {
	private TextView loading;
	private Context context;
	private int count = 1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (count % 4 == 0) {
					loading.setText("系统初始化中，请稍后...");
				} else if (count % 4 == 3) {
					loading.setText("系统初始化中，请稍后..");
				} else if (count % 4 == 2) {
					loading.setText("系统初始化中，请稍后.");
				} else {
					loading.setText("系统初始化中，请稍后");
				}
				count++;
				handler.sendEmptyMessageDelayed(1, 800);
				break;
			case Constants.FULLDATA:
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				finish();
				break;
			case Constants.NONETWORK:

				break;
			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		// 打印输出手机信息
		AndroidUtil.showPhoneInfor(context);
		setContentView(R.layout.preloading);
		loading = (TextView) findViewById(R.id.load_prompt);
		getData();
		handler.sendEmptyMessage(1);
	}

	private void getData() {
		// 获取后台数据
		handler.sendEmptyMessageDelayed(Constants.FULLDATA, 4000);
	}

	@Override
	protected void onDestroy() {
		if (handler != null) {
			handler.removeMessages(1);
		}
		handler = null;
		super.onDestroy();
	}

}
