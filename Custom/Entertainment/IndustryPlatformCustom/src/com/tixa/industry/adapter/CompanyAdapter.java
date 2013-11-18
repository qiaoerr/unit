package com.tixa.industry.adapter;

import java.util.ArrayList;
import com.tixa.industry.R;
import com.tixa.industry.model.EnterMember;
import com.tixa.industry.util.AsyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyAdapter extends BaseAdapter {
	
	private ArrayList<EnterMember> myData;
	private LayoutInflater mInflater;
	private Context context;
	private int rowNum ;
	private int count ;
	
	public CompanyAdapter(Context context, ArrayList<EnterMember> myData,int rowNum) {
		this.context = context;
		this.myData = myData;
		this.rowNum =  rowNum;
		this.count = rowNum;
		mInflater = LayoutInflater.from(context);
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	
	@Override
	public int getCount() {
		return myData.size() < rowNum ? myData.size() : count;
	}

	@Override
	public Object getItem(int position) {
		return myData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		convertView = mInflater.inflate(R.layout.common_list_item_no_pic, null);
		holder = new ViewHolder();

		holder.title = (TextView) convertView.findViewById(R.id.title);
		holder.textDetail = (TextView) convertView
				.findViewById(R.id.textDetail);
		holder.time = (TextView) convertView.findViewById(R.id.time);
		holder.lineartime = (LinearLayout) convertView
				.findViewById(R.id.lineartime);
		holder.lineartime.setVisibility(View.GONE);

		holder.title.setText(myData.get(position).getName());
		holder.textDetail.setText(myData.get(position).getDes());
		return convertView;
	}
	
	class ViewHolder {
		TextView title;
		TextView textDetail;
		TextView time;
		ImageView imageDetail;
		LinearLayout lineartime;
	}
	
}
