package com.start.jdzchina.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.start.jdzchina.R;
import com.start.jdzchina.model.NewsDataModel;

public class NewsDetaiActivity extends Activity {
	private ImageView close;
	private NewsDataModel news;
	private TextView news_title;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetail_layout);
		initData();
		initView();
	}

	private void initData() {
		context = this;
		news = (NewsDataModel) getIntent().getSerializableExtra("news");
	}

	private void initView() {
		close = (ImageView) findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		news_title = (TextView) findViewById(R.id.news_title);
		news_title.setText(news.getTitle());
	}
}
