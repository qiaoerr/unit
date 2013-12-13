package com.start.barbering.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.start.barbering.R;
import com.start.barbering.model.NewsDataModel;

public class NewsDetaiActivity extends Activity {
	private ImageView close;
	private NewsDataModel news;
	private TextView news_title;
	private Context context;
	private String[] imgs;
	private LayoutParams params;
	private LinearLayout imgContainer;
	private TextView news_detail;
	private View horizontal_line;

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
		imgs = news.getImgUrls();
	}

	private void initView() {
		close = (ImageView) findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		horizontal_line = findViewById(R.id.horizontal_line);
		news_title = (TextView) findViewById(R.id.news_title);
		news_title.setText(news.getTitle());
		news_detail = (TextView) findViewById(R.id.news_detail);
		if (imgs.length == 0) {
			horizontal_line.setVisibility(View.GONE);
		}
		// news_detail.setText(news.getDetail());
		imgContainer = (LinearLayout) findViewById(R.id.imgContainer);
		for (int i = 0; i < imgs.length; i++) {
			ImageView img = new ImageView(context);
			params = new LayoutParams(180, 100);
			img.setLayoutParams(params);
			img.setImageResource(R.drawable.loading);
			imgContainer.addView(img);
		}
	}

}
