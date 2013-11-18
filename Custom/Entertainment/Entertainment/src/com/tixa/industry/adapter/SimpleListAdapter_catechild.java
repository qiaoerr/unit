package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.modelCustom.GoodsCata;

public class SimpleListAdapter_catechild extends BaseAdapter {
	private Context context;
	private ArrayList<GoodsCata> dataList;
	private float scale;

	public SimpleListAdapter_catechild(Context context,
			ArrayList<GoodsCata> dataList) {
		this.context = context;
		this.dataList = dataList;
		scale = context.getResources().getDisplayMetrics().density;
	}

	@Override
	public int getCount() {
		return dataList.size();
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
		textView.setText(dataList.get(position).getCataName());
		textView.setPadding(20, 0, 0, 0);
		if (dataList.get(position).isChecked()) {
			textView.setTextColor(context.getResources().getColor(
					R.color.orange1));
		} else {
			textView.setTextColor(context.getResources()
					.getColor(R.color.gray3));
		}
		textView.setGravity(Gravity.CENTER_VERTICAL);
		LayoutParams params = new LayoutParams(-1, (int) (40 * scale));
		textView.setLayoutParams(params);
		return textView;
	}
}
