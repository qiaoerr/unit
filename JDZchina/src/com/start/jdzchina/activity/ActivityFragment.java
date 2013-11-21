package com.start.jdzchina.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.start.jdzchina.R;
import com.start.jdzchina.model.BannerModel;
import com.start.jdzchina.util.CommonUtil;
import com.start.jdzchina.widget.BannerView;

public class ActivityFragment extends Fragment {
	private Context context;
	private View view;
	private RelativeLayout actContainer;
	private ArrayList<BannerModel> bannerModels;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		bannerModels = new ArrayList<BannerModel>();
		for (int i = 0; i < 3; i++) {
			BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
					R.drawable.temp1 + i);
			bannerModels.add(model);
		}

	}

	private void initView() {
		actContainer = (RelativeLayout) view.findViewById(R.id.actContainer);
		BannerView bannerView = new BannerView(context, bannerModels,
				CommonUtil.getHeightPx(context), CommonUtil.getWidthPx(context));
		actContainer.addView(bannerView);

	}

}