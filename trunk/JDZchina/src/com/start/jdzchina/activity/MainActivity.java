package com.start.jdzchina.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.start.jdzchina.R;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private Context context;
	private RelativeLayout topMenuBar;
	private ImageView switch_img;
	private float factor;
	private boolean isExpand = false;
	private FragmentManager fm;
	private FragmentTransaction transaction;
	private Fragment fragment;
	private RelativeLayout menu1;
	private RelativeLayout menu2;
	private RelativeLayout menu3;
	private RelativeLayout menu4;
	private RelativeLayout menu5;
	private Animation animation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		fm = getSupportFragmentManager();
		factor = getResources().getDisplayMetrics().density;

	}

	private void initView() {
		topMenuBar = (RelativeLayout) findViewById(R.id.topMenuBar);
		TranslateAnimation tempAnimation = new TranslateAnimation(0, 0, 0,
				-(int) (factor * 40));
		tempAnimation.setDuration(1);
		tempAnimation.setFillAfter(true);
		topMenuBar.startAnimation(tempAnimation);
		switch_img = (ImageView) findViewById(R.id.switch_img);
		switch_img.setOnClickListener(this);
		menu1 = (RelativeLayout) findViewById(R.id.menu1);
		menu2 = (RelativeLayout) findViewById(R.id.menu2);
		menu3 = (RelativeLayout) findViewById(R.id.menu3);
		menu4 = (RelativeLayout) findViewById(R.id.menu4);
		menu5 = (RelativeLayout) findViewById(R.id.menu5);
		disableMenu();
		//
		transaction = fm.beginTransaction();
		ActivityFragment fragment = new ActivityFragment();
		transaction.replace(R.id.container, fragment);
		// transaction.addToBackStack(null);
		transaction.commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_img:
			if (isExpand) {
				closeAnim();
			} else {
				openAnim();
			}
			break;
		default:
			break;
		}
	}

	private void closeAnim() {
		animation = new TranslateAnimation(0, 0, 0, -(int) (factor * 40));
		disableMenu();
		// switch_img.setEnabled(true);
		isExpand = false;
		animation.setFillAfter(true);
		animation.setDuration(800);
		topMenuBar.startAnimation(animation);
	}

	private void openAnim() {
		animation = new TranslateAnimation(0, 0, -(int) (factor * 40), 0);
		enableMenu();
		// switch_img.setEnabled(false);
		isExpand = true;
		animation.setFillAfter(true);
		animation.setDuration(800);
		topMenuBar.startAnimation(animation);
	}

	private void disableMenu() {
		menu1.setEnabled(false);
		menu2.setEnabled(false);
		menu3.setEnabled(false);
		menu4.setEnabled(false);
		menu5.setEnabled(false);
	}

	private void enableMenu() {
		menu1.setEnabled(true);
		menu2.setEnabled(true);
		menu3.setEnabled(true);
		menu4.setEnabled(true);
		menu5.setEnabled(true);
	}

	public void menuClick(View v) {
		switch (v.getId()) {
		case R.id.menu1:
			closeAnim();
			fragment = new ActivityFragment();
			ClearBackStackAndReplace(fragment);
			break;
		case R.id.menu2:
			closeAnim();
			fragment = new ProductShowFragment();
			ClearBackStackAndReplace(fragment);
			break;
		case R.id.menu3:
			closeAnim();
			fragment = new MapFragment();
			ClearBackStackAndReplace(fragment);
			break;
		case R.id.menu4:
			closeAnim();
			fragment = new AfterSaleFragment();
			ClearBackStackAndReplace(fragment);
			break;
		case R.id.menu5:
			closeAnim();
			fragment = new AboutFragment();
			ClearBackStackAndReplace(fragment);
			break;

		default:
			break;
		}
	}

	private void ClearBackStackAndReplace(Fragment fragment) {
		int count = fm.getBackStackEntryCount();
		System.out.println("fm.getBackStackEntryCount: "
				+ fm.getBackStackEntryCount());
		for (int i = 0; i < count; i++) {
			fm.popBackStack();
		}
		transaction = fm.beginTransaction();
		transaction.replace(R.id.container, fragment);
		transaction.commitAllowingStateLoss();
	}
}
