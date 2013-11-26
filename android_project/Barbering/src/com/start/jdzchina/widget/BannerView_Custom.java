package com.start.jdzchina.widget;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.start.jdzchina.R;
import com.start.jdzchina.adapter.ViewPagerAdapter_Custom;
import com.start.jdzchina.model.BannerModel;
import com.start.jdzchina.util.AsyncImageLoader;
import com.start.jdzchina.util.CommonUtil;
import com.start.jdzchina.util.FileUtil;

public class BannerView_Custom extends RelativeLayout {
	private Context context;
	private LayoutParams params;
	private ArrayList<View> views;
	private ArrayList<BannerModel> bannerModels;
	private int bannerWidth;
	private int bannerHight;
	private ViewPager viewPager;

	public BannerView_Custom(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public BannerView_Custom(Context context,
			ArrayList<BannerModel> bannerModels, int bannerWidth,
			int bannerHight) {
		super(context);
		this.context = context;
		this.bannerModels = bannerModels;
		this.bannerWidth = bannerWidth;
		this.bannerHight = bannerHight;
		init();
	}

	public BannerView_Custom(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		initData();
		initView();
	}

	private void initData() {
		if (bannerWidth == 0) {
			bannerWidth = CommonUtil.getWidthPx(context);
		}
		if (bannerHight == 0) {
			bannerHight = bannerWidth / 2;
		}
		views = new ArrayList<View>();
		for (int i = 0; i < bannerModels.size(); i++) {
			BannerModel teModel = bannerModels.get(i);
			//
			ImageView img = new ImageView(context);
			img.setScaleType(ScaleType.CENTER_INSIDE);
			if (teModel.getType() == BannerModel.TYPE_NET) {
				FileUtil.setImage(img, teModel.getImgUrl(),
						new AsyncImageLoader(), R.drawable.default_loading);
			} else {
				img.setImageResource(teModel.getImgResourceId());
			}
			//
			// LinearLayout liear_out = new LinearLayout(context);
			views.add(img);

		}
	}

	private void initView() {
		viewPager = new ViewPager(context);
		params = new LayoutParams(bannerWidth, bannerHight);
		viewPager.setLayoutParams(params);
		this.addView(viewPager);
		// viewpager
		ViewPagerAdapter_Custom adapter = new ViewPagerAdapter_Custom(views);
		viewPager.setAdapter(adapter);
	}

}
