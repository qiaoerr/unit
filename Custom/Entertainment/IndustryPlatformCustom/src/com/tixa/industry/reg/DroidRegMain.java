package com.tixa.industry.reg;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.tixa.industry.R;
import com.tixa.industry.reg.DroidReg.ClickListener;
import com.tixa.industry.reg.DroidReg2.ClickListener2;
import com.tixa.industry.reg.DroidReg3.ClickListener3;
import com.tixa.industry.reg.DroidReg5.ClickListener5;
import com.tixa.industry.reg.DroidReg6.ClickListener6;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;

public class DroidRegMain extends FragmentActivity implements
		OnCheckedChangeListener {
	private Context context;
	private RadioButton radioBtn1, radioBtn2;
	private RadioGroup rg;
	private TopBar topbar;
	private ImageView slideBg;
	private LinearLayout fragment;
	private int begin, left, right, one;
	private int currIndex = 1;
	private FragmentManager fm;
	private DroidReg regFragment1;
	private DroidReg2 regFragment2;
	private DroidReg3 regFragment3;
	private DroidReg4 regFragment4;
	private DroidReg5 regFragment5;
	private DroidReg6 regFragment6;
	private int appType;
	public String mobileNum = "";
	private boolean curFragment = false;
	private final static int COUNTRY_CODE = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reg_main);
		context = this;
		Intent intent = getIntent();
		appType = intent.getIntExtra("appType", 1);
		fm = getSupportFragmentManager();
		initView();
	}

	private void initView() {
		regFragment1 = new DroidReg();
		regFragment2 = new DroidReg2();
		regFragment3 = new DroidReg3();
		regFragment4 = new DroidReg4();
		regFragment5 = new DroidReg5();
		regFragment6 = new DroidReg6();
		regFragment1.setClickListener(new ClickListener() {
			@Override
			public void onclick(View view, String mobile) {
				Bundle bundle = new Bundle();
				bundle.putString("mobile", mobile);
				regFragment2.setArguments(bundle);
				addFragment(fragment.getId(), regFragment2);
			}
		});
		regFragment2.setClickListener(new ClickListener2() {
			@Override
			public void onclick(View view, String mobile) {
				Bundle bundle = new Bundle();
				bundle.putString("mobile", mobile);
				regFragment3.setArguments(bundle);
				addFragment(fragment.getId(), regFragment3);
			}
		});
		regFragment3.setClickListener(new ClickListener3() {
			@Override
			public void onclick(View view, String mobile, String password) {
				Bundle bundle = new Bundle();
				bundle.putString("mobile", mobile);
				bundle.putString("password", password);
				regFragment4.setArguments(bundle);
				addFragment(fragment.getId(), regFragment4);
			}
		});
		regFragment5.setClickListener(new ClickListener5() {

			@Override
			public void onclick(View view, String email) {
				Bundle bundle = new Bundle();
				bundle.putString("email", email);
				regFragment6.setArguments(bundle);
				addFragment(fragment.getId(), regFragment6);
			}
		});
		regFragment6.setClickListener(new ClickListener6() {
			@Override
			public void onclick(View view, String email) {
				String url = "mail.";
				String temp = "";
				try {
					temp = email.substring(email.lastIndexOf('@') + 1);
				} catch (Exception e) {
					temp = "staff.tixa.com";
				}
				Uri u = Uri.parse(url + temp);
				Intent it = new Intent();
				it.setData(u);
				it.setAction(Intent.ACTION_VIEW);
				it.setClassName("com.android.browser",
						"com.android.browser.BrowserActivity");
				context.startActivity(it);
				finish();
			}
		});
		radioBtn1 = (RadioButton) findViewById(R.id.button1);
		radioBtn2 = (RadioButton) findViewById(R.id.button2);
		rg = (RadioGroup) findViewById(R.id.segment_control);
		rg.setOnCheckedChangeListener(this);
		fragment = (LinearLayout) findViewById(R.id.fregmentFrame);
		topbar = (TopBar) findViewById(R.id.topbar);
		
		topbar.showConfig("注册", true, false, false, false);
		topbar.showButtonImage(R.drawable.top_back, 0, 0);
		// topbar.setBackgroundImage(appType);
		topbar.setmListener(new TopBarListener() {
			@Override
			public void onButton3Click(View view) {
			}

			@Override
			public void onButton2Click(View view) {
			}

			@Override
			public void onButton1Click(View view) {
				finish();
			}
		});
		
		
		Drawable drawable1 = getResources().getDrawable(
				R.drawable.btn_reg_slider1);
		Drawable drawable2 = getResources().getDrawable(
				R.drawable.btn_reg_slider2);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable1.getMinimumHeight());
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
				drawable2.getMinimumHeight());
		radioBtn1.setText("手机注册");
		radioBtn1.setCompoundDrawables(drawable1, null, null, null);
		radioBtn2.setText("邮箱注册");
		radioBtn2.setCompoundDrawables(drawable2, null, null, null);
		radioBtn1.setTextColor(getResources()
				.getColor(R.color.space_text_color));
		radioBtn2.setTextColor(getResources().getColor(R.color.black));
		addFragment(fragment.getId(), regFragment1);
		initAnimView();
	}

	private void initAnimView() {
		slideBg = (ImageView) findViewById(R.id.bg_cursor);
		slideBg.setImageResource(R.drawable.slider_space);
		initAnimate();
	}

	private void initAnimate() {
		int bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.slider_space).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		int offset = (screenW / 2 - bmpW) / 2;
		begin = offset;
		left = offset;
		right = screenW - bmpW - offset;
		one = offset * 2 + bmpW;
		onPageSelected(0);
	}

	public void onPageSelected(int i) {
		Animation animation = null;
		switch (i) {
		case 0:
			animation = new TranslateAnimation(0, begin, 0, 0);
			break;
		case 1:
			if (currIndex == 2) {
				animation = new TranslateAnimation(right, left, 0, 0);
			}
			break;
		case 2:
			if (currIndex == 1) {
				animation = new TranslateAnimation(left, right, 0, 0);
			}
			break;
		}
		currIndex = i;
		animation.setFillAfter(true);
		if (i == 0) {
			animation.setDuration(200);
		} else {
			animation.setDuration(800);
		}
		slideBg.startAnimation(animation);
	}

	private void checked(int i) {
		if (i == 1) {
			radioBtn1.setTextColor(getResources().getColor(
					R.color.space_text_color));
			radioBtn2.setTextColor(getResources().getColor(R.color.black));
			currIndex = 2;
			onPageSelected(1);
		} else {
			radioBtn2.setTextColor(getResources().getColor(
					R.color.space_text_color));
			radioBtn1.setTextColor(getResources().getColor(R.color.black));
			currIndex = 1;
			onPageSelected(2);
		}
	}

	private void addFragment(int container, Fragment frag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(container, frag);
		fragmentTransaction.commit();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == COUNTRY_CODE) {
			regFragment1.setName(data.getStringExtra("CountryName"));
			regFragment1.setCode(data.getStringExtra("CountryCode"));
			regFragment1.getUserMobie().setText(
					regFragment1.getName() + " " + regFragment1.getCode());
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		if (id == radioBtn1.getId()) {
			checked(1);
			addFragment(fragment.getId(), regFragment1);
			// curFragment = true;
		} else if (id == radioBtn2.getId()) {
			checked(2);
			addFragment(fragment.getId(), regFragment5);
			// if (curFragment) {
			// AlertDialog.Builder alter = new AlertDialog.Builder(this);
			// alter.setTitle("��ʾ");
			// alter.setMessage("ע��δ��ɣ��Ƿ����?");
			// alter.setPositiveButton("ȷ��",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int whichButton) {
			// checked(2);
			// addFragment(fragment.getId(), regFragment5);
			// curFragment = false;
			// }
			// });
			// alter.setNegativeButton("ȡ��",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int whichButton) {
			// }
			// });
			// alter.show();
			// }else{
			// checked(2);
			// addFragment(fragment.getId(), regFragment5);
			// }
		}
	}
}
