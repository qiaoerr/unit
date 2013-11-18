package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.Modular;

public class HomeListViewAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Modular> listData;
	private String templateId;

	public HomeListViewAdapter(Context context, ArrayList<Modular> myData) {
		super();
		this.listData = myData;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		templateId = context.getResources().getString(R.string.templateid);
	}

	@Override
	public int getCount() {
		if ("1003".equals(templateId)) {
			return listData.size() > 6 ? 6 : listData.size();
		} else if ("1001".equals(templateId) || "1014".equals(templateId)) {
			return listData.size() > 8 ? 8 : listData.size();
		}
		return listData.size();
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
		convertView = inflater.inflate(R.layout.list_item, null);
		TextView item = (TextView) convertView.findViewById(R.id.mylist_item);
		if ("1002".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.black));
			item.setPadding(40, 0, 0, 0);
			item.setTextSize(18);
		} else if ("1001".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.black));
			if (position % 2 == 0) {
				item.setBackgroundResource(R.drawable.item1);
			} else {
				item.setBackgroundResource(R.drawable.item2);
			}
			item.setGravity(Gravity.LEFT + Gravity.CENTER);
			item.setPadding(40, 0, 0, 0);
			item.setTextSize(12);
		} else if ("1003".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			item.setBackgroundResource(R.drawable.item1 + position);
			item.setTextSize(15);
		} else if ("1004".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			// item.setPadding(20, 0, 0, 0);
			item.setTextSize(15);
		} else if ("1009".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			item.setPadding(30, 0, 0, 0);
			item.setTextSize(15);
		} else if ("1010".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			item.setPadding(10, 0, 0, 0);
			item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.item1, 0,
					0, 0);
			item.setCompoundDrawablePadding(1);
			item.setBackgroundColor(Color.TRANSPARENT);
			item.setTextSize(15);
		} else if ("1014".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			item.setGravity(Gravity.LEFT + Gravity.CENTER_VERTICAL);
			item.setCompoundDrawablePadding(10);
			item.setCompoundDrawablesWithIntrinsicBounds(R.drawable.module1
					+ position, 0, 0, 0);
			item.setPadding(30, 0, 0, 0);
			item.setTextSize(15);
		} else if ("1018".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.black));
			item.setGravity(Gravity.LEFT + Gravity.CENTER_VERTICAL);
			item.setPadding(30, 0, 0, 0);
			item.setTextSize(15);
		} else if ("1019".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			item.setPadding(0, 0, 30, 0);
			item.setTextSize(15);
		} else if ("1020".equals(templateId)) {
			item.setTextColor(context.getResources().getColor(R.color.white));
			item.setPadding(0, 0, 30, 0);
			item.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
			item.setTextSize(15);
		}
		String itemContent = listData.get(position).getModularName();
		if ("1010".equals(templateId)) {
			if (itemContent.length() > 5) {
				itemContent = itemContent.substring(0, 5);
			}
		}
		item.setText(itemContent);
		return convertView;
	}
}
