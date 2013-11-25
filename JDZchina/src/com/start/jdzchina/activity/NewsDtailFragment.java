package com.start.jdzchina.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.start.jdzchina.R;
import com.start.jdzchina.model.NewsDataModel;

public class NewsDtailFragment extends Fragment {

	private Context context;
	private View view;
	private ImageView close;
	private NewsDataModel news;
	private TextView news_title;

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
	}

}
