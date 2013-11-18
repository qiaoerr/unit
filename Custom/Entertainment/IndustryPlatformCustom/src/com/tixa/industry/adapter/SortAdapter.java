package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.GoodsCata;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class SortAdapter extends BaseAdapter {

	private ArrayList<GoodsCata> myData;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private Context context;
	private int rowNum;
	private int count;
	private long type;
	private int m = 5;

	public SortAdapter(Context context, ArrayList<GoodsCata> myData,
			int rowNum, long type) {
		this.context = context;
		this.myData = myData;
		this.rowNum = rowNum;
		this.count = rowNum;
		this.type = type;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int getCount() {
		// return myData.size() < rowNum ? myData.size() : count;
		return myData.size();
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
		final ViewHolder holder;
		// if (convertView == null) {

		convertView = mInflater.inflate(R.layout.sort_list_item, null);
		holder = new ViewHolder();
		holder.title = (TextView) convertView.findViewById(R.id.title);
		holder.textDetail = (TextView) convertView
				.findViewById(R.id.textDetail);
		holder.img = (ImageView) convertView.findViewById(R.id.img);
		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }
		holder.title.setText(myData.get(position).getCataName());
		holder.textDetail.setText(myData.get(position).getChildCataName());
		if (type == 1) {
			holder.img.setVisibility(View.VISIBLE);
			if (myData.get(position).getCataImg() != null) {
				FileUtil.setImage(holder.img,
						myData.get(position).getCataImg(), loader,
						R.drawable.load);
				// System.out.println("myData.get(position).getCataImg()"
				// + myData.get(position).getCataImg());
			} else {
				if (R.drawable.dianp1 + position - m > R.drawable.dianp90) {
					holder.img.setImageResource(R.drawable.dianp1);
				} else {
					holder.img.setImageResource(R.drawable.dianp1 + position
							- m);
				}

			}

		}
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView textDetail;
		TextView time;
		ImageView imageDetail;
		LinearLayout lineartime;
		ImageView img;
	}

}
