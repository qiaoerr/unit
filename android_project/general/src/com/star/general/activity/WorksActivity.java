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
import com.star.general.adapter.WorksAdapter;
import com.star.general.model.Works;
import com.star.general.util.ResourceUtil;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class WorksActivity extends Activity implements OnItemClickListener {

	private Context context;
	private RelativeLayout container;
	private float ratio = 142f / 300;
	private int screenWidth;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private ListView listView;
	private WorksAdapter adapter;
	private ArrayList<Works> worksList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.works_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		worksList = new ArrayList<Works>();
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		// 添加本地数据
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("短发 Short");
		arrayList.add("中发 Medium");
		arrayList.add("长发 Long");
		arrayList.add("男式发型 Man");
		arrayList.add("其他 Other");
		ArrayList<String> prefixs = new ArrayList<String>();
		prefixs.add("short");
		prefixs.add("medium");
		prefixs.add("long");
		prefixs.add("man");
		prefixs.add("other");
		ArrayList<Integer> style_imgs = ResourceUtil.getPrefixImages(context,
				"style_");
		for (int i = 0; i < style_imgs.size(); i++) {
			Works works = new Works();
			works.setTitle(arrayList.get(i));
			works.setPrefix(prefixs.get(i));
			works.setImageId(style_imgs.get(i));
			worksList.add(works);
		}
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
		params.setMargins(screenWidth / 14, 0, screenWidth / 14, 20);
		params.addRule(RelativeLayout.ABOVE, 10001);
		listView.setLayoutParams(params);
		adapter = new WorksAdapter(worksList, context);
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
		Works temp = worksList.get(position);
		Intent intent = new Intent(context, WorksDetailActivity.class);
		intent.putExtra("works", temp);
		startActivity(intent);
	}

}