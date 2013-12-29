package com.star.general.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.star.general.R;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class ConnectActivity extends Activity {
	private Context context;
	private ButtomBar buttomBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connect_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {

	}

	private void initView() {
		buttomBar = (ButtomBar) findViewById(R.id.bottomBar);
		buttomBar.setConfig(R.drawable.bottom_return, R.drawable.bottom_share,
				R.drawable.bottom_setting, new OnclickListener() {

					@Override
					public void rightClick() {
						Intent intent = new Intent(context,
								SettingActivity.class);
						startActivity(intent);
					}

					@Override
					public void midClick() {
						share();
					}

					@Override
					public void leftClick() {
						finish();
					}
				});
	}

	// 分享
	private void share() {
		String shareContent = "";
		if (shareContent == null || shareContent.equals("")) {
			shareContent = "嗨，我正在使用星火客户端，赶快来试试吧！！";
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
		intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "软件分享"));
	}
}
