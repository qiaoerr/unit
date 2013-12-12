package com.star.middleframework.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.CommonUtil;
import com.star.baseFramework.widget.twoWayGridView.TwoWayGridView;
import com.star.middleframework.MiddleApplication;
import com.star.middleframework.R;
import com.star.middleframework.adapter.PicStoresAdapter;

public class PictureStoreFragment extends Fragment {

	private Context context;
	private View view;
	private FrameLayout picStoreContainer;
	private ImageView close;
	private ArrayList<BannerModel> bannerModels;
	private String res_prefix;
	private TwoWayGridView horizontalGridView;
	private ArrayList<Integer> pics;

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
		res_prefix = MiddleApplication.getInstance().getRes_prefix();
		pics = new ArrayList<Integer>();
		bannerModels = new ArrayList<BannerModel>();
		for (int i = 1; i < 100; i++) {
			String resname = "poster" + i;
			if (getResId(resname) != 0) {
				BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
						getResId(resname));
				bannerModels.add(model);
				pics.add(getResId(resname));
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
		// BannerView_Custom bannerView = new BannerView_Custom(context,
		// bannerModels, CommonUtil.getWidthPx(context) * 2 / 3,
		// CommonUtil.getHeightPx(context) / 2);
		// picStoreContainer.addView(bannerView);
		PicStoresAdapter adapter = new PicStoresAdapter(pics, context,
				CommonUtil.getScreenHeight(context) / 2);
		horizontalGridView = new TwoWayGridView(context);
		// horizontalGridView.setBackgroundColor(Color.YELLOW);
		// 设置picStore的长�?
		LayoutParams params = new LayoutParams(
				CommonUtil.getScreenWidth(context) * 2 / 3,
				CommonUtil.getScreenHeight(context) / 2);
		horizontalGridView.setLayoutParams(params);
		horizontalGridView.setScrollingCacheEnabled(false);
		horizontalGridView.setNumRows(2);
		horizontalGridView
				.setScrollDirectionLandscape(TwoWayGridView.SCROLL_HORIZONTAL);
		horizontalGridView
				.setScrollDirectionPortrait(TwoWayGridView.SCROLL_HORIZONTAL);
		horizontalGridView.setHorizontalSpacing(5);
		horizontalGridView.setVerticalSpacing(5);
		horizontalGridView.setAdapter(adapter);
		picStoreContainer.addView(horizontalGridView);

	}

	private int getResId(String resname) {
		return getResources().getIdentifier(res_prefix + resname, "drawable",
				context.getPackageName());
	}
}
