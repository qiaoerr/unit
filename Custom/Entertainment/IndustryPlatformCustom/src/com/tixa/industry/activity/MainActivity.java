package com.tixa.industry.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.Modular;
import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.T;
import com.tixa.industry.widget.BottomTab;
import com.tixa.industry.widget.BottomTab.ClickListener;
import com.tixa.industry.widget.BottomTabBar;

public class MainActivity extends FragmentActivity {
	private IndustryApplication application;
	private IndexData indexData;
	private SparseArray<ModularConfig> map;
	private ArrayList<Modular> navList;
	private BottomTabBar bottombar;
	private Activity context;
	private int curIndex = 0;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private MainReceiver receiver;
	private long exitTime = 0;
	private RootFragment rootFragment = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.fragmentact_bottombar);
		initData();
		initView();
	}

	private void initData() {
		fragmentManager = getSupportFragmentManager();
		application = IndustryApplication.getInstance();
		CommonUtil.initApplication(context, application);
		indexData = application.getMainData();
		map = application.getModularMap();
		navList = indexData.getNavList();
		registerIntentReceivers();
	}

	private void initView() {
		bottombar = (BottomTabBar) findViewById(R.id.bottombar);
		initBottomTab();
		initFirstFrg();
	}

	private void initFirstFrg() {
		String className = "";
		if (navList != null && navList.size() > 0) {
			try {
				className = "com.tixa.industry.activity."
						+ map.get(navList.get(0).getType()).getTypeName()
						+ "Activity";
			} catch (Exception e) {
				e.printStackTrace();
				className = HomeActivity.class.getName();
			}
			if (className.equals("")) {
				className = HomeActivity.class.getName();
			}
			fragmentTransaction = fragmentManager.beginTransaction();
			rootFragment = new RootFragment();
			Bundle args = CommonUtil.packageNaviModileBundle(navList.get(0), className, 
					0, context);
			rootFragment.setArguments(args);
			fragmentTransaction.add(R.id.container, rootFragment);
			fragmentTransaction.commit();
		}

	}

	private void initBottomTab() {
		if (navList != null && navList.size() > 0) {
			for (int i = 0; i < navList.size(); i++) {
				final int cur = i;
				final Modular m = navList.get(i);
				final String name = m.getModularName();
				final long showType = m.getShowType();
				if (map == null) {
					continue;
				}
				ModularConfig config = map.get(m.getType());

				if (config == null) {
					continue;
				}
			
				final int isLogin = config.getLogin();
				String className = CommonUtil.formatClassName(application, config, showType);
				final String fragmentName = className;

				int image = Constants.nav_images[1];
				try {
					image = Constants.nav_images[m.getType() - 1];
				} catch (Exception e) {
					image = Constants.nav_images[1];
				}
				BottomTab tab = new BottomTab(context, bottombar, name, image,
						new ClickListener() {
							@Override
							public void onClick() {
								// 需要登录
								if (isLogin == 1
										&& IndustryApplication.getInstance()
												.getMemberID() <= 0) {
									Intent intent = new Intent(context,
											LoginActivity.class);
									intent.putExtra("position", cur);
									startActivity(intent);
								} else {
									fragmentTransaction = fragmentManager
											.beginTransaction();
									try {
										RootFragment fragment = new RootFragment();										
										Bundle args = CommonUtil.packageNaviModileBundle(m, fragmentName, 
												cur, context);
										
										fragment.setArguments(args);

										fragmentTransaction.replace(
												R.id.container, fragment);
										fragmentTransaction
												.commitAllowingStateLoss();
										curIndex = cur;
										getCurrentFragment().clearBackStack();
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
								if (curIndex != cur) {
									bottombar.setChecked(curIndex);
								}
							}
						});
				if (i == 0) {
					tab.setChecked(true);
				}
			}
		}

	}

	private RootFragment getCurrentFragment() {
		return (RootFragment) getSupportFragmentManager().findFragmentById(
				R.id.container);
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// Fragment fragment = getSupportFragmentManager().findFragmentById(
	// R.id.fragment);
	// fragment.onActivityResult(requestCode, resultCode, data);
	// System.out.println("requestCode:" + requestCode);
	// Toast.makeText(context, data.getStringExtra("result") + ":",
	// 1).show();
	// }

	@Override
	public void onBackPressed() {
		/*
		 * if (getCurrentFragment()!=null &&
		 * !getCurrentFragment().popBackStack()) { super.onBackPressed(); }else{
		 * finish(); }
		 */
		if (getCurrentFragment() != null) {

			boolean result1 = getCurrentFragment().popBackStack();
			if (!result1) {
				boolean result = getSupportFragmentManager()
						.popBackStackImmediate();
				if (!result) {
					if ((System.currentTimeMillis() - exitTime) > 2000) {
						T.shortT(context, "再按一次退出程序");
						exitTime = System.currentTimeMillis();
					} else {
						finish();
						System.exit(0);
					}
				}
			}
		}

		return;
	}

	@Override
	protected void onDestroy() {
		L.e("---main onDestroy--------------");
		unregisterIntentReceivers();
		super.onDestroy();
	}

	private void registerIntentReceivers() {
		receiver = new MainReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.MY_LOGIN_SUCCESS_ACTION);
		filter.addAction(Constants.MY_LOGOUT_SUCCESS_ACTION);
		filter.addAction(Constants.MY_REGISTER_SUCCESS_ACTION);
		registerReceiver(receiver, filter);
	}

	private void unregisterIntentReceivers() {
		try {
			unregisterReceiver(receiver);
		} catch (Exception e) {

		}
	}

	class MainReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.MY_LOGIN_SUCCESS_ACTION)) {
				int position = intent.getIntExtra("position", -1);
				if (position > 0) {
					bottombar.onclick(position);
				}
			} else if (action.equals(Constants.MY_LOGOUT_SUCCESS_ACTION)) {
				L.e("退出登录了！");
				bottombar.onclick(0);
			} else if(action.equals(Constants.MY_REGISTER_SUCCESS_ACTION)) {
				L.e("注册成功！");
				T.shortT(context, "注册成功");
				bottombar.onclick(0);
			}
			
			
		}
	}

	public void func(View view) {
	}
}
