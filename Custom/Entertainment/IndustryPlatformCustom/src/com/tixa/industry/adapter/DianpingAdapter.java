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
import com.tixa.industry.model.GoodsFromDZ;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class DianpingAdapter extends BaseAdapter {
	private ArrayList<GoodsFromDZ> myData;
	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private int listStyle;
	private int rowNum;
	private int count;

	public DianpingAdapter(Context context, ArrayList<GoodsFromDZ> myData,
			int listStyle, int rowNum) {
		this.context = context;
		this.myData = myData;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
		this.listStyle = listStyle;
		this.rowNum = rowNum;
		this.count = rowNum;
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
		// if(convertView == null) {
		convertView = mInflater.inflate(R.layout.common_list_item, null);
		holder = new ViewHolder();
		holder.imageDetail = (ImageView) convertView
				.findViewById(R.id.imageDetail);
		holder.companyName = (TextView) convertView.findViewById(R.id.title);
		// holder.price = (TextView) convertView.findViewById(R.id.price);
		holder.textDetail = (TextView) convertView
				.findViewById(R.id.textDetail);
		convertView.setTag(holder);

		String imgpath = "";
		String title = "";
		String price = "";
		String textDetail = "";

		GoodsFromDZ goods = myData.get(position);
		imgpath = goods.getGoodsImg();
		title = goods.getName();
		// textDetail = goods.getCoupon_description();
		textDetail = goods.getAddress();
		FileUtil.setImage(holder.imageDetail, imgpath, loader, R.drawable.load);
		holder.companyName.setText(title);
		holder.textDetail.setText(textDetail);
		return convertView;

	}

	private static class ViewHolder {
		public TextView companyName;
		public TextView price;
		public TextView textDetail;
		public ImageView imageDetail;
	}

}
