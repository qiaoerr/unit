package com.star.middleframework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.start.jdzchina.R;

public class DialogEnter0neActivity extends Activity {
	private TextView title_dia;
	private String titleString;
	private ImageView close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogener0nelayout);
		initData();
		initView();

	}

	private void initData() {
		titleString = getIntent().getStringExtra("title");

	}

	private void initView() {
		title_dia = (TextView) findViewById(R.id.title_dia);
		title_dia.setText(titleString);
		close = (ImageView) findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
