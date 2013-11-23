package com.start.jdzchina.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.start.jdzchina.R;
import com.start.jdzchina.RapidApplication;
import com.start.jdzchina.model.BannerModel;
import com.start.jdzchina.util.CommonUtil;
import com.start.jdzchina.widget.BannerView_Custom;

public class PictureStoreFragment extends Fragment {

	private Context context;
	private View view;
	private FrameLayout picStoreContainer;
	private ImageView close;
	private ArrayList<BannerModel> bannerModels;
	private String res_prefix;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.picturestore_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		res_prefix = RapidApplication.getInstance().getRes_prefix();
		bannerModels = new ArrayList<BannerModel>();
		for (int i = 1; i < 100; i++) {
			String resname = "poster" + i;
			if (getResId(resname) != 0) {
				BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
						getResId(resname));
				bannerModels.add(model);
			} else {
				break;
			}
		}

	}

	private void initView() {
		picStoreContainer = (FrameLayout) view
				.findViewById(R.id.picStoreContainer);
		close = (ImageView) view.findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		BannerView_Custom bannerView = new BannerView_Custom(context,
				bannerModels, CommonUtil.getWidthPx(context) * 2 / 3,
				CommonUtil.getHeightPx(context) / 2);
		picStoreContainer.addView(bannerView);
	}

	private int getResId(String resname) {
		return getResources().getIdentifier(res_prefix + resname, "drawable",
				context.getPackageName());
	}
}
