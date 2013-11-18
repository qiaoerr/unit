package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tixa.industry.R;
import com.tixa.industry.model.MarketMessage;
import com.tixa.industry.util.AsyncImageLoader;

public class MarketAdapter extends BaseAdapter {
	private ArrayList<MarketMessage> myData;
	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private int rowNum ;
	private int count ;

	public MarketAdapter(Context context, ArrayList<MarketMessage> myData ,int rowNum) {
		this.context = context;
		this.myData = myData;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
		this.rowNum =  rowNum;
		this.count = rowNum;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setData(ArrayList<MarketMessage> myData) {
		this.myData = myData;
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
		holder = new ViewHolder();
		convertView = mInflater.inflate(R.layout.market_list_item, null);
		holder.title = (TextView) convertView.findViewById(R.id.title);
		holder.textDetail = (TextView) convertView.findViewById(R.id.textDetail);
		
		MarketMessage message = myData.get(position);
	/*	L.e("holder.myData ="+a);
		L.e("holder.getTitle ="+a.getTitle());*/
		
		holder.title.setText(message.getTitle());
		holder.textDetail.setText(message.getDesc());		
		return convertView;
	}
	

	private static class ViewHolder {
		TextView title;
		TextView textDetail;
		ImageView imageDetail;
	}
}
