package com.start.barbering.activity;

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

import com.start.astore.R;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private Context context;
	private RelativeLayout topMenuBar;
	private ImageView switch_img;
	private float factor;
	private boolean isExpand = false;
	private FragmentManager fm;
	private FragmentTransaction transaction;
	private RelativeLayout menu1;
	private RelativeLayout menu2;
	private RelativeLayout menu3;
	private RelativeLayout menu4;
	private RelativeLayout menu5;
	private Fragment fragment1;
	private Fragment fragment2;
	private Fragment fragment3;
	private Fragment fragment4;
	private Fragment fragment5;
	private Animation animation = null;
	private long exitTime;
	private Fragment[] fragments;

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
		fragments = new Fragment[5];
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
		fragment1 = new ActivityFragment();
		fragments[0] = fragment1;
		transaction.replace(R.id.container, fragment1);
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
			if (fragment1 == null) {
				fragment1 = new ActivityFragment();
				fragments[0] = fragment1;
			}
			hideOrShow(fragment1);
			break;
		case R.id.menu2:
			closeAnim();
			if (fragment2 == null) {
				fragment2 = new ProductShowFragment();
				fragments[1] = fragment2;
			}
			hideOrShow(fragment2);
			break;
		case R.id.menu3:
			closeAnim();
			if (fragment3 == null) {
				fragment3 = new NewsListFragment();
				fragments[2] = fragment3;
			}
			hideOrShow(fragment3);
			break;
		case R.id.menu4:
			closeAnim();
			if (fragment4 == null) {
				fragment4 = new MapFragment();
				fragments[3] = fragment4;
			}
			hideOrShow(fragment4);
			break;
		case R.id.menu5:
			closeAnim();
			if (fragment5 == null) {
				fragment5 = new AboutFragment();
				fragments[4] = fragment5;
			}
			hideOrShow(fragment5);
			break;

		default:
			break;
		}
	}

	private void hideOrShow(Fragment fragment) {
		transaction = fm.beginTransaction();
		if (!fragment.isAdded()) {
			transaction.add(R.id.container, fragment);
		}
		for (int i = 0; i < fragments.length; i++) {
			if (fragments[i] != null) {
				if (fragments[i] != null && fragments[i] == fragment) {
					transaction.show(fragments[i]);
				} else {
					transaction.hide(fragments[i]);
				}
			}
		}
		fm.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
}
