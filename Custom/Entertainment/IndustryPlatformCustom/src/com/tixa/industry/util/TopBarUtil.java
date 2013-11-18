package com.tixa.industry.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.tixa.industry.R;
import com.tixa.industry.activity.MoreActivity;
import com.tixa.industry.activity.SearchActivity;
import com.tixa.industry.activity.SortActivity;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;

public class TopBarUtil {
	private boolean isNav; // 是否是tobbar点击过来的，tobbar点击过来的不显示返回键
	private int naviStyle; // 导航栏类型 1 文字 2 图片
	private int naviType; // 1 默认 2带搜索栏
	private TopBar topbar;
	private String modularName; // 显示栏目名称
	private FragmentManager fm;
	private Activity context;
	private String templateId; // 模版的id
	private boolean isShowBack; // 当模版为二时显示返回键 还是显示类别 true显示返回键 false显示类别
	private OnClickListener listener;

	public TopBarUtil(boolean isNav, int naviStyle, TopBar topbar,
			String modularName, FragmentManager fm, Activity context,
			String templateId, boolean isShowBack, int naviType) {
		this.isNav = isNav;
		this.topbar = topbar;
		this.naviStyle = naviStyle;
		this.modularName = modularName;
		this.fm = fm;
		this.context = context;
		this.templateId = templateId;
		this.isShowBack = isShowBack;
		this.naviType = naviType;
	}

	/**
	 * 右边按钮
	 * 
	 * @param title
	 * @param listener
	 */
	public void showButton3(String title, OnClickListener listener) {
		topbar.imageConfig(0, 0, R.drawable.top_right);
		topbar.showButtonText("", "", title);
		topbar.showButton3(true);
		topbar.getButton3().setOnClickListener(listener);
	}
	
	public void showProgressBar(boolean isShow) {
		topbar.showProgressBar(isShow);
	}
	

	public void showConfig() {
		if (naviType == ConfigData.TOPBAR_TYPE_SEARCH) { // 带搜索栏头部
			topbar.showSearch(modularName, true);
			final EditText edit = topbar.getSearchEditText();
			topbar.imageConfig(0, 0, R.drawable.search_button);
			topbar.showButton3(false);
			edit.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int arg1, int arg2,
						int arg3) {
					if (StrUtil.isNotEmpty(topbar.getSearchKeyword())) {
						topbar.showButton3(true);
					} else {
						topbar.showButton3(false);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				@Override
				public void afterTextChanged(Editable s) {

				}
			});

			topbar.setmListener(new TopBarListener() {

				@Override
				public void onButton3Click(View view) {
					AndroidUtil.collapseSoftInputMethod(context, edit);
					Fragment fragment = new SearchActivity();
					Bundle args = new Bundle();
					args.putString("modularName", "搜索");
					args.putLong("typeID", 8);
					args.putBoolean("isNav", false);
					args.putString("searchKey", topbar.getSearchKeyword());
					fragment.setArguments(args);
					FragmentTransaction fragmentTransaction = fm
							.beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.fragment, fragment);
					fragmentTransaction.commit();
				}

				@Override
				public void onButton2Click(View view) {
				}

				@Override
				public void onButton1Click(View view) {
				}
			});

		} else if ((!isNav && naviStyle != 0) || templateId.equals(2 + "")) {
			if (naviStyle == ConfigData.TOPBAR_BACKBUTTON_TYPE_CHARACTERS) {
				topbar.showConfig(modularName, true, false, false, false);
				topbar.imageConfig(R.drawable.back, 0, 0);
				topbar.showButtonText("返回", "", "");
			} else if (naviStyle == ConfigData.TOPBAR_BACKBUTTON_TYPE_PIC) { // 图片
				if (templateId.equals(1 + "")) {
					topbar.showConfig(modularName, true, false, false, false);
					topbar.showButtonImage(R.drawable.top_back, 0, 0);
				} else if (templateId.equals(2 + "")) {

					if (fm == null) {
						topbar.showConfig(modularName, true, false, false,
								false);
						topbar.showButtonImage(R.drawable.top_back, 0, 0);
					} else {
						topbar.showConfig(modularName, true, false, true, false);
						if (isShowBack) {
							topbar.showButtonImage(R.drawable.top_back, 0,
									R.drawable.top_set);
						} else {
							topbar.showButtonImage(R.drawable.top_cata, 0,
									R.drawable.top_set);
						}
					}

				} else {
					topbar.showConfig(modularName, true, false, false, false);
					topbar.showButtonImage(R.drawable.top_back, 0, 0);
				}
			}

			topbar.setmListener(new TopBarListener() {
				@Override
				public void onButton3Click(View view) {
					if (templateId.equals(2 + "")) {
						Fragment fragment = new MoreActivity();
						Bundle args = new Bundle();
						args.putString("modularName", "设置");
						args.putLong("typeID", 13);
						args.putBoolean("isNav", false);
						fragment.setArguments(args);
						FragmentTransaction fragmentTransaction = fm
								.beginTransaction();
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.replace(R.id.fragment, fragment);
						fragmentTransaction.commit();
					}
				}

				@Override
				public void onButton2Click(View view) {

				}

				@Override
				public void onButton1Click(View view) {
					if (templateId.equals(2 + "") && !isShowBack) {
						Fragment fragment = new SortActivity();
						Bundle args = new Bundle();
						args.putString("modularName", "行业分类");
						args.putLong("typeID", 1);
						args.putBoolean("isNav", false);
						fragment.setArguments(args);
						FragmentTransaction fragmentTransaction = fm
								.beginTransaction();
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.replace(R.id.fragment, fragment);
						fragmentTransaction.commit();
					} else {
						if (fm != null) {
							fm.popBackStack();
						} else {
							context.finish();
						}
					}

				}
			});

		} else {
			topbar.showConfig(modularName, false, false, false, false);
		}
	}

}
