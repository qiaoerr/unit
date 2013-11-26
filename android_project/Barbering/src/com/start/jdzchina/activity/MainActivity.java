package com.start.jdzchina.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.start.jdzchina.R;
import com.start.jdzchina.RapidApplication;
import com.start.jdzchina.widget.MyRouteMapView;

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
	private long exitTime;

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
			fragment = new NewsListFragment();
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

	@Override
	public void onBackPressed() {
		boolean result = fm.popBackStackImmediate();
		if (!result) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// Toast.makeText(context, "再按一次退出程序",
				// Toast.LENGTH_SHORT).show();
				Toast toast = new Toast(context);
				toast.setDuration(Toast.LENGTH_SHORT);
				TextView textView = new TextView(context);
				textView.setPadding(8, 6, 8, 6);
				textView.setText("再按一次退出程序");
				textView.setBackgroundColor(getResources().getColor(
						R.color.black));
				textView.setTextColor(getResources().getColor(R.color.white));
				toast.setView(textView);
				toast.setGravity(Gravity.BOTTOM, 0, 30);
				toast.show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
		}
		return;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			Rect rect = new Rect();
			topMenuBar.getHitRect(rect);
			if (!rect.contains((int) ev.getX(), (int) ev.getY())) {
				if (isExpand) {
					closeAnim();
					isExpand = false;
				}
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onPause() {
		/**
		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		 */
		MyRouteMapView mMapView = RapidApplication.getInstance().getMapView();
		if (mMapView != null) {
			mMapView.onPause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		/**
		 * MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		 */
		MyRouteMapView mMapView = RapidApplication.getInstance().getMapView();
		if (mMapView != null) {
			mMapView.onResume();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		/**
		 * MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		 */
		MyRouteMapView mMapView = RapidApplication.getInstance().getMapView();
		if (mMapView != null) {
			mMapView.destroy();
		}
		RapidApplication.getInstance().setMapView(null);
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		MyRouteMapView mMapView = RapidApplication.getInstance().getMapView();
		if (mMapView != null) {
			mMapView.onSaveInstanceState(outState);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		MyRouteMapView mMapView = RapidApplication.getInstance().getMapView();
		if (mMapView != null && savedInstanceState != null) {
			mMapView.onRestoreInstanceState(savedInstanceState);
		}
	}
}
