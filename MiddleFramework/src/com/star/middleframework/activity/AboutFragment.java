package com.star.middleframework.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.start.jdzchina.R;

public class AboutFragment extends Fragment {
	private Context context;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.about_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();

	}

	private void initView() {
		// TODO Auto-generated method stub

	}

}
