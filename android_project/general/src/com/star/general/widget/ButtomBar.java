package com.star.general.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;

public class ButtomBar extends RelativeLayout implements OnClickListener {
	private LayoutParams params;
	private int screenWidth;
	private Context context;
	private ImageView img_left;
	private ImageView img_mid;
	private ImageView img_right;
	private OnclickListener listener;

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
		RelativeLayout out_container = new RelativeLayout(context);
		out_container.setBackgroundResource(R.drawable.buttombar);
		params = new LayoutParams(LayoutParams.MATCH_PARENT, screenWidth / 6);
		out_container.setLayoutParams(params);
		this.addView(out_container);
		// left
		img_left = new ImageView(context);
		img_left.setId(101);
		img_left.setOnClickListener(this);
		// img_left.setBackgroundResource(R.drawable.bottom_return);
		params = new LayoutParams(screenWidth / 12, screenWidth / 12);
		params.setMargins(screenWidth / 10, 0, 0, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		img_left.setLayoutParams(params);
		out_container.addView(img_left);
		// mid
		img_mid = new ImageView(context);
		img_mid.setId(102);
		img_mid.setOnClickListener(this);
		// img_mid.setBackgroundResource(R.drawable.bottom_share);
		params = new LayoutParams(screenWidth / 12, screenWidth / 12);
		params.setMargins(screenWidth / 10, 0, 0, 0);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		img_mid.setLayoutParams(params);
		out_container.addView(img_mid);
		// right
		img_right = new ImageView(context);
		img_right.setId(103);
		img_right.setOnClickListener(this);
		// img_right.setBackgroundResource(R.drawable.bottom_setting);
		params = new LayoutParams(screenWidth / 12, screenWidth / 12);
		params.setMargins(0, 0, screenWidth / 10, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		img_right.setLayoutParams(params);
		out_container.addView(img_right);

	}

	public void setConfig(int resId_left, int resId_mid, int resId_right,
			OnclickListener listener) {
		this.listener = listener;
		if (resId_left > 0) {
			img_left.setBackgroundResource(resId_left);
		} else {
			img_left.setVisibility(View.GONE);
		}
		if (resId_mid > 0) {
			img_mid.setBackgroundResource(resId_mid);
		} else {
			img_mid.setVisibility(View.GONE);
		}
		if (resId_right > 0) {
			img_right.setBackgroundResource(resId_right);
		} else {
			img_right.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 101:
			if (listener != null) {
				listener.leftClick();
			}
			break;
		case 102:
			if (listener != null) {
				listener.midClick();
			}
			break;
		case 103:
			if (listener != null) {
				listener.rightClick();
			}
			break;
		default:
			break;
		}

	}

	public interface OnclickListener {
		public void leftClick();

		public void midClick();

		public void rightClick();
	}
}
