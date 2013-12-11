package com.star.middleframework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.start.jdzchina.R;

public class BigPicTureActivity extends Activity {
	private ImageView bigPic;
	private ImageView close;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigpicture_layout);
		initView();

	}

	private void initView() {
		bigPic = (ImageView) findViewById(R.id.bigPic);
		bigPic.setImageResource(getIntent().getIntExtra("resId", 0));
		close = (ImageView) findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
