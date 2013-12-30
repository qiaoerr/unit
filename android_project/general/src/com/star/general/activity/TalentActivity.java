package com.star.general.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.adapter.TalentAdapter;
import com.star.general.model.Talent;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class TalentActivity extends Activity {

	private Context context;
	private RelativeLayout container;
	private float ratio = 142f / 300;
	private int screenWidth;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private ListView listView;
	private TalentAdapter adapter;
	private ArrayList<Talent> talents;
	private float scale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.talent_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		scale = BaseCommonUtil.getScale(context);
		talents = new ArrayList<Talent>();
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		// 添加本地数据
		Talent talent = new Talent();
		talent.setName("Jay");
		talent.setJob("创意总监");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("MIYAKI");
		talent.setJob("创意总监");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("亮亮");
		talent.setJob("首席发型师");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("天天");
		talent.setJob("首席发型师");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("Cindy");
		talent.setJob("高级理发师");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("石头");
		talent.setJob("高级理发师");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("郭涛");
		talent.setJob("高级理发师");
		talent.setImageUrl("");
		talents.add(talent);
		//
		talent = new Talent();
		talent.setName("田亮");
		talent.setJob("高级理发师");
		talent.setImageUrl("");
		talents.add(talent);

	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		listView = new ListView(context);
		listView.setVerticalScrollBarEnabled(false);
		listView.setScrollingCacheEnabled(false);
		listView.setDivider(context.getResources().getDrawable(
				R.color.divider_color));
		listView.setDividerHeight(1);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 15, 0, screenWidth / 15, 0);
		params.addRule(RelativeLayout.ABOVE, 10001);
		listView.setLayoutParams(params);
		adapter = new TalentAdapter(talents, context);
		listView.setAdapter(adapter);
		container.addView(listView);
		// 查看职位
		ImageView imgview = new ImageView(context);
		imgview.setId(10001);
		imgview.setBackgroundResource(R.drawable.employment_button);
		params = new LayoutParams(221, 57);
		params.setMargins(0, (int) (5 * scale), 0, (int) (5 * scale));
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.addRule(RelativeLayout.ABOVE, 10002);
		imgview.setLayoutParams(params);
		container.addView(imgview);
		imgview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, TalentRecuitActivity.class);
				startActivity(intent);
			}
		});
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
