package com.star.general.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;

public class ButtomBar extends RelativeLayout {
	private LayoutParams params;
	private int screenWidth;
	private Context context;
	private ImageView img_left;
	private ImageView img_mid;
	private ImageView img_right;

	public ButtomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.screenWidth = BaseCommonUtil.getScreenWidth(context);
		initView();
	}

	public ButtomBar(Context context) {
		super(context);
		this.context = context;
		this.screenWidth = BaseCommonUtil.getScreenWidth(context);
		initView();
	}

	private void initView() {
		this.setBackgroundColor(Color.BLUE);
		RelativeLayout out_container = new RelativeLayout(context);
		out_container.setBackgroundResource(R.drawable.buttombar);
		params = new LayoutParams(LayoutParams.MATCH_PARENT, screenWidth / 6);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.setMargins(0, 0, 0, -10);
		out_container.setLayoutParams(params);
		this.addView(out_container);
		// this.setBackgroundColor(Color.BLUE);
		// left
		img_left = new ImageView(context);
		img_left.setBackgroundResource(R.drawable.bottom_return);
		params = new LayoutParams(screenWidth / 12, screenWidth / 12);
		params.setMargins(screenWidth / 10, 0, 0, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		img_left.setLayoutParams(params);
		out_container.addView(img_left);
		// mid
		img_mid = new ImageView(context);
		img_mid.setBackgroundResource(R.drawable.bottom_share);
		params = new LayoutParams(screenWidth / 12, screenWidth / 12);
		params.setMargins(screenWidth / 10, 0, 0, 0);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		img_mid.setLayoutParams(params);
		out_container.addView(img_mid);
		// right
		img_right = new ImageView(context);
		img_right.setBackgroundResource(R.drawable.bottom_setting);
		params = new LayoutParams(screenWidth / 12, screenWidth / 12);
		params.setMargins(0, 0, screenWidth / 10, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		img_right.setLayoutParams(params);
		out_container.addView(img_right);

	}
}
