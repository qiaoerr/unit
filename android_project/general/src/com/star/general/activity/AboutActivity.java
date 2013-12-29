package com.star.general.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class AboutActivity extends Activity {
	private Context context;
	private RelativeLayout container;
	private int screenWidth;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private float scale;
	private float ratio = 94f / 294;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		scale = BaseCommonUtil.getScale(context);
		screenWidth = BaseCommonUtil.getScreenWidth(context);
	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		ScrollView scrollView = new ScrollView(context);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 17, 0, screenWidth / 18, 0);
		params.addRule(RelativeLayout.ABOVE, 10002);
		scrollView.setLayoutParams(params);
		container.addView(scrollView);
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(linearLayout);
		// image
		ImageView img = new ImageView(context);
		img.setBackgroundResource(R.drawable.about_logo);
		android.widget.LinearLayout.LayoutParams params_linear = new android.widget.LinearLayout.LayoutParams(
				screenWidth * 8 / 9, (int) (screenWidth * ratio) * 8 / 9);
		img.setLayoutParams(params_linear);
		linearLayout.addView(img);
		// content_textView
		TextView content_textView = new TextView(context);
		params_linear = new android.widget.LinearLayout.LayoutParams(-1, -2);
		content_textView.setLayoutParams(params_linear);
		content_textView.setPadding((int) (10 * scale), (int) (15 * scale),
				(int) (10 * scale), (int) (10 * scale));
		// content_textView.setGravity(Gravity.CENTER);
		content_textView.setTextColor(Color.BLACK);
		content_textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		content_textView.setText("     "
				+ context.getResources().getString(R.string.Salon_profile));
		linearLayout.addView(content_textView);
		// buttomBar
		buttomBar = new ButtomBar(context);
		buttomBar.setConfig(R.drawable.bottom_return, R.drawable.bottom_share,
				R.drawable.bottom_setting, new OnclickListener() {

					@Override
					public void rightClick() {
						Intent intent = new Intent(context,
								SettingActivity.class);
						startActivity(intent);
					}

					@Override
					public void midClick() {
						share();
					}

					@Override
					public void leftClick() {
						finish();
					}
				});
		buttomBar.setId(10002);
		params = new LayoutParams(-2, -2);
		params.setMargins(0, 0, 0, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttomBar.setLayoutParams(params);
		container.addView(buttomBar);
	}

	// 分享
	private void share() {
		String shareContent = "";
		if (shareContent == null || shareContent.equals("")) {
			shareContent = "嗨，我正在使用星火客户端，赶快来试试吧！！";
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
		intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "软件分享"));
	}
}
