package com.start.barbering.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.CommonUtil;
import com.star.baseFramework.widget.BannerView;
import com.start.astore.R;

public class ActivityFragment extends Fragment {
	private Context context;
	private View view;
	private RelativeLayout actContainer;
	private ArrayList<BannerModel> bannerModels;
	private BannerView bannerView;

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
		bannerView = new BannerView(context, bannerModels,
				CommonUtil.getScreenWidth(context),
				CommonUtil.getScreenHeight(context));
		actContainer.addView(bannerView);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
