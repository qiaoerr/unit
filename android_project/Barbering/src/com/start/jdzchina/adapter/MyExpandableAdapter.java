/**
  @Title: MyExpandableAdapter.java
  @Package com.start.jdzchina.adapter
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-27 下午03:37:19
  @version V1.0
 */

package com.start.jdzchina.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.start.jdzchina.R;
import com.start.jdzchina.util.CommonUtil;

/**
 * @ClassName: MyExpandableAdapter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-27 下午03:37:19
 * 
 */

public class MyExpandableAdapter extends BaseExpandableListAdapter {
	private Context context;
	private LayoutParams params;
	private String[] generalsTypes;
	private String[][] generals;
	private int[] logos;
	private int[][] generallogos;

	public MyExpandableAdapter(Context context) {
		super();
		this.context = context;
		initData();
	}

	private void initData() {
		// 设置组视图的图片
		logos = new int[] { R.drawable.new_2, R.drawable.new_2,
				R.drawable.new_2 };
		// 设置组视图的显示文字
		generalsTypes = context.getResources().getStringArray(R.array.level);
		// 子视图显示文字
		generals = new String[][] {
				context.getResources().getStringArray(R.array.one_item),
				context.getResources().getStringArray(R.array.two_item),
				context.getResources().getStringArray(R.array.three_item) };
		// 子视图图片
		generallogos = new int[][] {
				{ R.drawable.icon, R.drawable.icon, R.drawable.icon,
						R.drawable.icon, R.drawable.icon, R.drawable.icon },
				{ R.drawable.icon, R.drawable.icon, R.drawable.icon,
						R.drawable.icon, R.drawable.icon, R.drawable.icon },
				{ R.drawable.icon, R.drawable.icon, R.drawable.icon,
						R.drawable.icon, R.drawable.icon } };
	}

	private TextView getTextView() {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 64);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setPadding(36, 0, 0, 0);
		textView.setTextSize(20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}

	@Override
	public int getGroupCount() {
		return generalsTypes.length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return generalsTypes[groupPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return generals[groupPosition].length;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return generals[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(0);
		ImageView logo = new ImageView(context);
		logo.setScaleType(ScaleType.FIT_XY);
		params = new LayoutParams((int) (50 * CommonUtil.getScale(context)),
				(int) (50 * CommonUtil.getScale(context)));
		logo.setLayoutParams(params);
		logo.setBackgroundColor(Color.BLUE);
		logo.setImageResource(logos[groupPosition]);
		ll.addView(logo);
		TextView textView = getTextView();
		textView.setTextColor(Color.BLACK);
		textView.setText(getGroup(groupPosition).toString());
		ll.addView(textView);
		return ll;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(0);
		ImageView generallogo = new ImageView(context);
		params = new LayoutParams((int) (50 * CommonUtil.getScale(context)),
				(int) (50 * CommonUtil.getScale(context)));
		generallogo.setLayoutParams(params);
		generallogo
				.setImageResource(generallogos[groupPosition][childPosition]);
		ll.addView(generallogo);
		TextView textView = getTextView();
		textView.setText(getChild(groupPosition, childPosition).toString());
		ll.addView(textView);
		return ll;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
