package com.tixa.industry.adapter;

import java.util.ArrayList;

import com.tixa.industry.R;
import com.tixa.industry.model.RecruitingInfo;
import com.tixa.industry.util.AsyncImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JobAdapter extends BaseAdapter {
	
	private ArrayList<RecruitingInfo> myData;
	private LayoutInflater mInflater;

	private Context context;
	private int rowNum ;
	private int count ;
	
	public JobAdapter(Context context, ArrayList<RecruitingInfo> myData,int rowNum) {
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
		convertView = mInflater.inflate(R.layout.job_list_item, null);
		holder = new ViewHolder();
		holder.companyName = (TextView) convertView
				.findViewById(R.id.companyName);
		holder.time = (TextView) convertView.findViewById(R.id.time);
		holder.company_hangye = (TextView) convertView
				.findViewById(R.id.company_hangye);
		holder.company_zhiwei = (TextView) convertView
				.findViewById(R.id.company_zhiwei);
		holder.company_xinzi = (TextView) convertView
				.findViewById(R.id.company_xinzi);
		
		holder.time.setText(myData.get(position).getCreateTime());
		holder.companyName.setText(myData.get(position).getHiringCompany());
		holder.company_hangye.setText("公司行业："+myData.get(position).getZoneCode());
		holder.company_zhiwei.setText("职位："+myData.get(position).getPositionCode());
		holder.company_xinzi.setText("薪资："+myData.get(position).getSalary());

		return convertView;
	}
	
	class ViewHolder {
		public TextView companyName;
		public TextView time;
		public TextView company_hangye;
		public TextView company_zhiwei;
		public TextView company_xinzi;
	}
	
}
