package com.star.middleframework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.star.middleframework.R;

public class MyDialogActivity extends Activity implements OnClickListener {
	private Context context;
	private ImageView close;
	private ImageView btn_spec_0;
	private ImageView btn_spec_1;
	private ImageView btn_spec_2;
	private Intent intent;
	private Bundle args;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		close = (ImageView) findViewById(R.id.btn_close);
		btn_spec_0 = (ImageView) findViewById(R.id.btn_spec_0);
		btn_spec_1 = (ImageView) findViewById(R.id.btn_spec_1);
		btn_spec_2 = (ImageView) findViewById(R.id.btn_spec_2);
		close.setOnClickListener(this);
		btn_spec_0.setOnClickListener(this);
		btn_spec_1.setOnClickListener(this);
		btn_spec_2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_close) {
			finish();
		} else if (v.getId() == R.id.btn_spec_0) {
			intent = new Intent(context, DialogEnter0neActivity.class);
			args = new Bundle();
			args.putString("title", "车型介绍");
			intent.putExtras(args);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_spec_1) {
			intent = new Intent(context, DialogEnter0neActivity.class);
			args = new Bundle();
			args.putString("title", "性能参数");
			intent.putExtras(args);
			startActivity(intent);
		} else if (v.getId() == R.id.btn_spec_2) {
			intent = new Intent(context, DialogEnter0neActivity.class);
			args = new Bundle();
			args.putString("title", "详细配置");
			intent.putExtras(args);
			startActivity(intent);
		} else {
		}

	}

}
