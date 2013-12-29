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

import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.baseFramework.widget.BannerView;
import com.star.general.R;
import com.star.general.adapter.NewsItemAdapter;
import com.star.general.model.News;
import com.star.general.util.ResourceUtil;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class NewsActivity extends Activity implements OnItemClickListener {
	private Context context;
	private RelativeLayout container;
	private float ratio = 142f / 300;
	private int screenWidth;
	private LayoutParams params;
	private ArrayList<BannerModel> models;
	private ButtomBar buttomBar;
	private ListView listView;
	private NewsItemAdapter adapter;
	private ArrayList<News> news;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		models = new ArrayList<BannerModel>();
		news = new ArrayList<News>();
		ArrayList<Integer> top_imgs = ResourceUtil.getPrefixImages(context,
				"top_");
		for (int i = 0; i < top_imgs.size(); i++) {
			BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
					top_imgs.get(i));
			models.add(model);
		}
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		// 添加本地数据
		News news1 = new News();
		news1.setTitle("Joico嘉珂细节发型席卷暖冬");
		news1.setDetail("Joico嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬");
		news.add(news1);
		News news2 = new News();
		news2.setTitle("Joico嘉珂细节发型席卷暖冬");
		news2.setDetail("Joico嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬");
		news.add(news2);
		News news3 = new News();
		news3.setTitle("Joico嘉珂细节发型席卷暖冬");
		news3.setDetail("Joico嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬");
		news.add(news3);
		News news4 = new News();
		news4.setTitle("Joico嘉珂细节发型席卷暖冬");
		news4.setDetail("Joico嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬");
		news.add(news4);
		News news5 = new News();
		news5.setTitle("Joico嘉珂细节发型席卷暖冬");
		news5.setDetail("Joico嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬");
		news.add(news5);
		News news6 = new News();
		news6.setTitle("Joico嘉珂细节发型席卷暖冬");
		news6.setDetail("Joico嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬嘉珂细节发型席卷暖冬");
		news.add(news6);

	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		BannerView bannerView = new BannerView(context, models, screenWidth
				- screenWidth / 14, (int) (screenWidth * ratio) - screenWidth
				/ 14);
		bannerView.hideIndex();
		listView = new ListView(context);
		listView.setOnItemClickListener(this);
		listView.setVerticalScrollBarEnabled(false);
		listView.setScrollingCacheEnabled(false);
		listView.setDivider(context.getResources().getDrawable(
				R.color.divider_color));
		listView.setDividerHeight(2);
		listView.addHeaderView(bannerView);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 14, 0, screenWidth / 14, 0);
		params.addRule(RelativeLayout.ABOVE, 10001);
		listView.setLayoutParams(params);
		adapter = new NewsItemAdapter(news, context);
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
		News temp = news.get(position);
		Intent intent = new Intent(context, NewsDetailActivity.class);
		intent.putExtra("news", temp);
		startActivity(intent);
	}

}
