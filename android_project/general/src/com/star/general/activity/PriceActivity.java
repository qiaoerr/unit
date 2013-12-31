package com.star.general.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.model.ServicePrice;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class PriceActivity extends Activity {

	private Context context;
	private RelativeLayout container;
	private int screenWidth;
	private LayoutParams params;
	private ButtomBar buttomBar;
	private float scale;
	private ArrayList<ServicePrice> cataOne;
	private ArrayList<ServicePrice> cataTwo;
	private ArrayList<ServicePrice> cataThree;
	private ArrayList<ServicePrice> cataFour;
	private ArrayList<ServicePrice> cataFive;
	private ArrayList<ServicePrice> cataSix;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.price_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		scale = BaseCommonUtil.getScale(context);
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		cataOne = new ArrayList<ServicePrice>();
		cataTwo = new ArrayList<ServicePrice>();
		cataThree = new ArrayList<ServicePrice>();
		cataFour = new ArrayList<ServicePrice>();
		cataFive = new ArrayList<ServicePrice>();
		cataSix = new ArrayList<ServicePrice>();
		// testData
		ServicePrice tem = new ServicePrice();
		tem.setServiceName("创意总监");
		tem.setServicePrice("￥180");
		cataOne.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("首席发型师");
		tem.setServicePrice("￥120");
		cataOne.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("高级发型师");
		tem.setServicePrice("￥50");
		cataOne.add(tem);
		//
		tem = new ServicePrice();
		tem.setServiceName("日本梦特蓓妮烫发");
		tem.setServicePrice("￥580");
		cataTwo.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("德国施华蔻烫发");
		tem.setServicePrice("￥530");
		cataTwo.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("巴黎欧莱雅烫发");
		tem.setServicePrice("￥480");
		cataTwo.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("美琪丝烫发");
		tem.setServicePrice("￥430");
		cataTwo.add(tem);
		//
		tem = new ServicePrice();
		tem.setServiceName("日本梦特蓓妮染发");
		tem.setServicePrice("￥480");
		cataThree.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("德国施华蔻染发");
		tem.setServicePrice("￥430");
		cataThree.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("巴黎欧莱雅染发");
		tem.setServicePrice("￥380");
		cataThree.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("美琪丝染发");
		tem.setServicePrice("￥330");
		cataThree.add(tem);
		//
		tem = new ServicePrice();
		tem.setServiceName("海旎头部SPA");
		tem.setServicePrice("￥398");
		cataFour.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("丝泉家庭护理");
		tem.setServicePrice("￥298");
		cataFour.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("思雅专业护理");
		tem.setServicePrice("￥330");
		cataFour.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("卡诗专业头皮护理");
		tem.setServicePrice("￥380");
		cataFour.add(tem);
		//
		tem = new ServicePrice();
		tem.setServiceName("手部护理");
		tem.setServicePrice("￥70");
		cataFive.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("美甲");
		tem.setServicePrice("￥58");
		cataFive.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("盘发造型");
		tem.setServicePrice("￥50");
		cataFive.add(tem);
		//
		cataSix.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("罗瑞塔发蜡2.5");
		tem.setServicePrice("￥198");
		cataSix.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("罗瑞塔发蜡4.0");
		tem.setServicePrice("￥298");
		cataSix.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("罗瑞塔发蜡6.5");
		tem.setServicePrice("￥398");
		cataSix.add(tem);
		tem = new ServicePrice();
		tem.setServiceName("碧若丝强力定型喷雾");
		tem.setServicePrice("￥168");
		cataSix.add(tem);
	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		ScrollView scrollView = new ScrollView(context);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 18, 0, screenWidth / 18, 0);
		params.addRule(RelativeLayout.ABOVE, 10002);
		scrollView.setLayoutParams(params);
		container.addView(scrollView);
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(linearLayout);
		// cataone_textView
		TextView cataone_textView = getCataTextview("洗吹剪");
		linearLayout.addView(cataone_textView);
		// item
		listDetailItem(linearLayout, cataOne);
		// catatwo_textView
		TextView catatwo_textView = getCataTextview("烫发");
		linearLayout.addView(catatwo_textView);
		// item
		listDetailItem(linearLayout, cataTwo);
		// cataThree_textView
		TextView cataThree_textView = getCataTextview("染发");
		linearLayout.addView(cataThree_textView);
		// item
		listDetailItem(linearLayout, cataThree);
		// cataFour_textView
		TextView cataFour_textView = getCataTextview("护理");
		linearLayout.addView(cataFour_textView);
		// item
		listDetailItem(linearLayout, cataFour);
		// catafive_textView
		TextView catafive_textView = getCataTextview("其他");
		linearLayout.addView(catafive_textView);
		// item
		listDetailItem(linearLayout, cataFive);
		// cataSix_textView
		TextView cataSix_textView = getCataTextview("外卖");
		linearLayout.addView(cataSix_textView);
		// item
		listDetailItem(linearLayout, cataSix);
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

	private void listDetailItem(LinearLayout linearLayout,
			ArrayList<ServicePrice> dataList) {
		android.widget.LinearLayout.LayoutParams params_linear;
		for (int i = 0; i < dataList.size(); i++) {
			RelativeLayout rel = new RelativeLayout(context);
			linearLayout.addView(rel);
			TextView serviceName = new TextView(context);
			params = new LayoutParams(-2, -2);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			serviceName.setLayoutParams(params);
			serviceName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			serviceName.setPadding((int) (10 * scale), (int) (10 * scale),
					(int) (10 * scale), (int) (10 * scale));
			serviceName.setTextColor(context.getResources().getColor(
					R.color.black));
			rel.addView(serviceName);
			serviceName.setText(dataList.get(i).getServiceName());
			TextView price_text = new TextView(context);
			params = new LayoutParams(-2, -2);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			price_text.setLayoutParams(params);
			price_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			price_text.setPadding((int) (10 * scale), (int) (10 * scale),
					(int) (10 * scale), (int) (10 * scale));
			price_text.setTextColor(context.getResources()
					.getColor(R.color.red));
			price_text.setText(dataList.get(i).getServicePrice());
			rel.addView(price_text);
			if (i != dataList.size() - 1) {
				// view
				View view = new View(context);
				params_linear = new android.widget.LinearLayout.LayoutParams(
						-1, 1);
				params_linear.setMargins(10, 0, 10, 0);
				view.setLayoutParams(params_linear);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.divider_color));
				linearLayout.addView(view);
			}
		}
	}

	private TextView getCataTextview(String cataString) {
		TextView textView = new TextView(context);
		textView.setBackgroundColor(context.getResources().getColor(
				R.color.grey));
		android.widget.LinearLayout.LayoutParams params_linear = new android.widget.LinearLayout.LayoutParams(
				-1, -2);
		textView.setLayoutParams(params_linear);
		textView.setPadding((int) (10 * scale), (int) (5 * scale),
				(int) (10 * scale), (int) (5 * scale));
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		textView.setText(cataString);
		return textView;
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
