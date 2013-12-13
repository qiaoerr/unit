package com.start.jdzchina.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.star.baseFramework.widget.twoWayGridView.TwoWayAbsListView.LayoutParams;
import com.start.jdzchina.activity.BigPicTureActivity;

public class PicStoresAdapter extends BaseAdapter {
	private ArrayList<Integer> dataList;
	private Context context;
	private int hight;
	private LayoutParams params;

	public PicStoresAdapter(ArrayList<Integer> dataList, Context context,
			int hight) {
		super();
		this.dataList = dataList;
		this.context = context;
		this.hight = hight;
		// 图片的比例是1:2 imageScaleType CENTER_CROP
		params = new LayoutParams((hight - 5), (hight - 5) / 2);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setBackgroundColor(Color.BLUE);
		imageView.setImageResource(dataList.get(position));
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, BigPicTureActivity.class);
				intent.putExtra("resId", dataList.get(position));
				context.startActivity(intent);
			}
		});
		return imageView;
	}

}
