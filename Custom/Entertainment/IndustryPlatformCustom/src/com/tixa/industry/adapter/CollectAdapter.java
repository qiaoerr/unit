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
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Goods;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.StrUtil;

public class CollectAdapter extends BaseAdapter {
	private ArrayList<Goods> myData;
	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private int listStyle;
	private int rowNum;
	private int count;

	public CollectAdapter(Context context, ArrayList<Goods> myData,
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
		holder.price = (TextView) convertView.findViewById(R.id.price);
		holder.textDetail = (TextView) convertView
				.findViewById(R.id.textDetail);
		convertView.setTag(holder);

		String imgpath = "";
		String title = "";
		String price = "";
		String textDetail = "";

		Goods goods = myData.get(position);

		if (goods.getType() == Constants.SELL) {
			imgpath = goods.getGoodsImg();
			title = goods.getGoodsName();
		} else {
			imgpath = goods.getImagePath();
			title = goods.getSubject();
		}
		price = goods.getGoodsPrice();

		textDetail = goods.getGoodsDescStr();

		if (StrUtil.isEmpty(price) || listStyle == 2) {
			holder.price.setVisibility(View.GONE);
		} else {
			holder.price.setVisibility(View.VISIBLE);
		}

		FileUtil.setImage(holder.imageDetail, imgpath, loader, R.drawable.load);
		holder.companyName.setText(title);
		holder.price.setText("￥" + price);
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
