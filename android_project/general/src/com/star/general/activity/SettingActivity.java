package com.star.general.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.star.general.R;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class SettingActivity extends Activity {
	private Context context;
	private ButtomBar buttomBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {

	}

	private void initView() {
		buttomBar = (ButtomBar) findViewById(R.id.bottomBar);
		buttomBar.setConfig(R.drawable.bottom_return, 0, 0,
				new OnclickListener() {

					@Override
					public void rightClick() {

					}

					@Override
					public void midClick() {
					}

					@Override
					public void leftClick() {
						finish();
					}
				});
	}

}
