package com.star.general.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.widget.pathMenu.MenuItemView;
import com.star.general.widget.pathMenu.MyAnimations;
import com.star.general.widget.pathMenu.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private Context context;
	private long exitTime;
	private Handler handler;
	private MenuItemView menuView;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		handler = new Handler();
	}

	private void initView() {
		// 获得对象
		menuView = (MenuItemView) findViewById(R.id.menuView);
		// 设置半径
		menuView.setRadius(BaseCommonUtil.getScreenWidth(context) / 4.5f);
		menuView.setOnClickListener(this);
		setMenuItemView();
	}

	private void setMenuItemView() {
		ImageView item_1 = new ImageView(this);
		item_1.setBackgroundResource(R.drawable.item_1);
		ImageView item_2 = new ImageView(this);
		item_2.setBackgroundResource(R.drawable.item_2);
		ImageView item_3 = new ImageView(this);
		item_3.setBackgroundResource(R.drawable.item_3);
		ImageView item_4 = new ImageView(this);
		item_4.setBackgroundResource(R.drawable.item_4);
		ImageView item_5 = new ImageView(this);
		item_5.setBackgroundResource(R.drawable.item_5);
		ImageView item_6 = new ImageView(this);
		item_6.setBackgroundResource(R.drawable.item_6);
		ImageView item_7 = new ImageView(this);
		item_7.setBackgroundResource(R.drawable.item_7);
		ImageView item_8 = new ImageView(this);
		item_8.setBackgroundResource(R.drawable.item_8);
		menuView.addView(item_1);
		menuView.addView(item_2);
		menuView.addView(item_3);
		menuView.addView(item_4);
		menuView.addView(item_5);
		menuView.addView(item_6);
		menuView.addView(item_7);
		menuView.addView(item_8);
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		switch (vid) {
		case R.id.menuView:
			MyAnimations.startAnimations(MainActivity.this, menuView, 400);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast toast = new Toast(context);
			toast.setDuration(Toast.LENGTH_SHORT);
			TextView textView = new TextView(context);
			textView.setPadding(8, 6, 8, 6);
			textView.setText("再按一次退出程序");
			textView.setBackgroundColor(getResources().getColor(R.color.black));
			textView.setTextColor(getResources().getColor(R.color.white));
			toast.setView(textView);
			toast.setGravity(Gravity.BOTTOM, 0, 30);
			toast.show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
		return;
	}

	@Override
	public void onclick(int item) {
		/*if (toast == null) {
			toast = Toast.makeText(MainActivity.this, "" + item, 1);
		} else {
			toast.setText(item + "");
		}
		toast.show();*/
		switch (item) {
		case 0:
			jumpToActivity(NewsActivity.class);
			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;
		case 7:

			break;

		default:
			break;
		}

	}

	private void jumpToActivity(final Class<NewsActivity> clazz) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(context, clazz);
				startActivity(intent);

			}
		};
		handler.postDelayed(runnable, 500);
	}
}
