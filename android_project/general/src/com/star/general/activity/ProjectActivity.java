package com.star.general.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.adapter.ProjectAdapter;
import com.star.general.model.Project;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class ProjectActivity extends Activity implements OnItemClickListener {

	private Context context;
	private RelativeLayout container;
	private float ratio = 142f / 300;
	private int screenWidth;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private ListView listView;
	private ProjectAdapter adapter;
	private ArrayList<Project> projects;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		projects = new ArrayList<Project>();
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		// 添加本地数据
		Project project = new Project();
		project.setTitle("日本梦特蓓妮营养烫发");
		project.setSubTitle("剪发+烫发六折优惠");
		project.setImageUrl("");
		project.setProjectDetail("自11月1日-12月31日，凡在本店年度累计消费超过千元的顾客，都可以享受剪发+日本梦特蓓妮营养烫发6折优惠");
		projects.add(project);
		//
		project = new Project();
		project.setTitle("染发+头皮护理");
		project.setSubTitle("染发+头皮护理=830元");
		project.setImageUrl("");
		project.setProjectDetail("使用进口染膏染发的顾客，均可以免费享用一次头皮护理一次。祛除残留在头皮上的染膏的化学物质。");
		projects.add(project);
		//
		project = new Project();
		project.setTitle("年末发型大变身");
		project.setSubTitle("剪发+染发+烫发=1200元");
		project.setImageUrl("");
		project.setProjectDetail("为了迎接元旦、春节，本沙龙推出一个剪发+烫发+染发的套餐供各位顾客选择。仅需要1200元，店内的所有产品任意选择。让做一次华丽的变身。");
		projects.add(project);
		//
		project = new Project();
		project.setTitle("会员岁末大回馈");
		project.setSubTitle("赠送专业家庭护理套装");
		project.setImageUrl("");
		project.setProjectDetail("凡是在本店年度累计消费满2000元的顾客，均可以在本店免费领取价值370元的专业家庭护理套装。共计350套，送完为止。");
		projects.add(project);
		//
		project = new Project();
		project.setTitle("星期二剪发特惠日");
		project.setSubTitle("高级发型师剪发九折");
		project.setImageUrl("");
		project.setProjectDetail("每星期二来店，并且选择高级发型师进行剪发的顾客，均可以享受剪发价格9折优惠。");
		projects.add(project);

	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		listView = new ListView(context);
		listView.setOnItemClickListener(this);
		listView.setVerticalScrollBarEnabled(false);
		listView.setScrollingCacheEnabled(false);
		listView.setDivider(context.getResources().getDrawable(
				R.color.divider_color));
		listView.setDividerHeight(1);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 14, 0, screenWidth / 14, 0);
		params.addRule(RelativeLayout.ABOVE, 10001);
		listView.setLayoutParams(params);
		adapter = new ProjectAdapter(projects, context);
		listView.setAdapter(adapter);
		container.addView(listView);
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
		buttomBar.setId(10001);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - listView.getHeaderViewsCount();
		Project temp = projects.get(position);
		Intent intent = new Intent(context, ProjectDetailActivity.class);
		intent.putExtra("project", temp);
		startActivity(intent);
	}

}
