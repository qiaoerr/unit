/*package com.start.jdzchina.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.start.jdzchina.R;
import com.start.jdzchina.model.NewsDataModel;

public class NewsDtailFragment extends Fragment {

	private Context context;
	private View view;
	private ImageView close;
	private NewsDataModel news;
	private TextView news_title;
	private String[] imgs;
	private LayoutParams params;
	private LinearLayout imgContainer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.newsdetail_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		news = (NewsDataModel) getArguments().get("news");
		imgs = news.getImgUrls();
	}

	private void initView() {
		close = (ImageView) view.findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		news_title = (TextView) view.findViewById(R.id.news_title);
		news_title.setText(news.getTitle());
		for (int i = 0; i < imgs.length; i++) {
			ImageView img = new ImageView(context);
			params = new LayoutParams(120, 70);
			img.setLayoutParams(params);

		}

	}

}
*/