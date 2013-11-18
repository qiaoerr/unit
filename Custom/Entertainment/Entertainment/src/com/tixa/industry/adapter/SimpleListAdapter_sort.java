package com.tixa.industry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tixa.industry.R;

public class SimpleListAdapter_sort extends BaseAdapter {
	private Context context;
	private String[] strs;
	private float scale;
	private int index;

	public SimpleListAdapter_sort(Context context, String[] strs, int index) {
		this.context = context;
		this.strs = strs;
		this.index = index;
		scale = context.getResources().getDisplayMetrics().density;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strs.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setPadding(20, 0, 0, 0);
		textView.setText(strs[position]);
		if (position == index) {
			textView.setTextColor(context.getResources().getColor(
					R.color.orange1));
		} else {
			textView.setTextColor(Color.BLACK);
		}
		textView.setGravity(Gravity.CENTER_VERTICAL);
		LayoutParams params = new LayoutParams(-1, (int) (40 * scale));
		textView.setLayoutParams(params);
		return textView;
	}
}
