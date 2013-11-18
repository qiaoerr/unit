package com.tixa.industry.adapter;

import java.util.ArrayList;
import com.tixa.industry.R;
import com.tixa.industry.model.GoodsCata;
import com.tixa.industry.model.NewsCatagory;
import com.tixa.industry.util.AsyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsCataAdapter extends BaseAdapter {
	
	private ArrayList<NewsCatagory> myData;
	private LayoutInflater mInflater;
	private Context context;
	private int rowNum ;
	private int count ;
	
	public NewsCataAdapter(Context context, ArrayList<NewsCatagory> myData,int rowNum) {
		this.context = context;
		this.myData = myData;
		this.rowNum =  rowNum;
		this.count = rowNum;
		mInflater = LayoutInflater.from(context);
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setData(ArrayList<NewsCatagory> myData) {
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
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.news_cata_list_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(myData.get(position).getCataName().trim());	
		return convertView;
	}
	
	class ViewHolder {
		TextView title;	
	}
	
}
