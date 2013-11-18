package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.activity.ProvideActivity;
import com.tixa.industry.modelCustom.GoodsCata;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class SimpleListAdapter_cate extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<GoodsCata> dataList;
	private float scale;
	private AsyncImageLoader loader;
	private int m;

	public SimpleListAdapter_cate(Context context, ArrayList<GoodsCata> dataList) {
		this.context = context;
		this.dataList = dataList;
		this.inflater = LayoutInflater.from(context);
		scale = context.getResources().getDisplayMetrics().density;
		loader = new AsyncImageLoader();
		m = ProvideActivity.number;
	}

	@Override
	public int getCount() {
		// return dataList.size() > 6 ? 6 : dataList.size();
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
		LinearLayout view = (LinearLayout) inflater.inflate(
				R.layout.layout_cate, null);
		RelativeLayout relativeLayout = (RelativeLayout) view
				.findViewById(R.id.goodsCate);
		if (dataList.get(position).isChecked()) {
			relativeLayout.setBackgroundColor(context.getResources().getColor(
					R.color.eee));
		}
		ImageView img = (ImageView) view.findViewById(R.id.goodsIcon);
		TextView cateName = (TextView) view.findViewById(R.id.goodsCateName);
		if (dataList.get(position).getCataImg() != null) {
			FileUtil.setImage(img, dataList.get(position).getCataImg(), loader,
					R.drawable.cate);
		} else {
			if (R.drawable.dianp1 + position - m > R.drawable.dianp90) {
				img.setImageResource(R.drawable.dianp1);
			} else {
				img.setImageResource(R.drawable.dianp1 + position - m);
			}
		}

		cateName.setText(dataList.get(position).getCataName());
		return view;
	}

}
