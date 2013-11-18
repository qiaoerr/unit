package com.tixa.industry.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.modelCustom.Announcement;
import com.tixa.industry.util.DateUtil;

public class AnnouncementAdapter extends BaseAdapter {
	private ArrayList<Announcement> dataList;
	private Context context;
	private LayoutInflater inflater;

	public AnnouncementAdapter(ArrayList<Announcement> dataList, Context context) {
		super();
		this.dataList = dataList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_announcement, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.endTime = (TextView) convertView.findViewById(R.id.endTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Announcement announcement = dataList.get(position);
		holder.title.setText(announcement.getTitle());
		holder.content.setText(announcement.getTitle());
		String temp = DateUtil.parseDate(new Date(announcement.getEndTime()));
		holder.endTime.setText(temp);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView content;
		TextView endTime;
	}
}
