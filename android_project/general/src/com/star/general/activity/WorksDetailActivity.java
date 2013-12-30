/**
  @Title: WorksDetailActivity.java
  @Package com.star.general.activity
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-30 下午08:07:42
  @version V1.0
 */

package com.star.general.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.baseFramework.widget.BannerView;
import com.star.general.R;
import com.star.general.model.Works;
import com.star.general.util.ResourceUtil;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

/**
 * @ClassName: WorksDetailActivity
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-30 下午08:07:42
 * 
 */

public class WorksDetailActivity extends Activity {

	private Context context;
	private RelativeLayout container;
	private int screenWidth;
	private int screenHeight;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private TextView title;
	private float scale;
	private String prefix;
	private ArrayList<BannerModel> models;
	private Works works;
	private float ratio = 94f / 294;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worksdetail_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		models = new ArrayList<BannerModel>();
		scale = BaseCommonUtil.getScale(context);
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		screenHeight = BaseCommonUtil.getScreenHeight(context);
		works = (Works) getIntent().getSerializableExtra("works");
		prefix = works.getPrefix();
		ArrayList<Integer> prefix_imgs = ResourceUtil.getPrefixImages(context,
				prefix);
		for (int i = 0; i < prefix_imgs.size(); i++) {
			BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
					prefix_imgs.get(i));
			models.add(model);
		}

	}

	private void initView() {
		title = (TextView) findViewById(R.id.title);
		title.setText(works.getTitle());
		container = (RelativeLayout) findViewById(R.id.container);
		BannerView bannerView = new BannerView(context, models,
				screenWidth * 17 / 18,
				(int) ((screenHeight - screenWidth / 6 - scale * 80) * 12 / 13));
		bannerView.hideIndex();
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 18, 0, screenWidth / 18, 0);
		params.addRule(RelativeLayout.ABOVE, 10002);
		bannerView.setLayoutParams(params);
		container.addView(bannerView);
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
