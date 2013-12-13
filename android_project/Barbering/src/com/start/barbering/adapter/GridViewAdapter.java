/**
  @Title: GridViewAdapter.java
  @Package com.start.jdzchina.adapter
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-27 上午11:09:08
  @version V1.0
 */

package com.start.barbering.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.star.baseFramework.util.image.RoundCornerImageUtil;
import com.start.barbering.model.ShowModel;

/**
 * @ClassName: GridViewAdapter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-27 上午11:09:08
 * 
 */

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private int height;
	private int width;
	LayoutParams params;
	private ArrayList<ShowModel> dataList;

	public GridViewAdapter(Context context, int width, int heihgt,
			ArrayList<ShowModel> dataList) {
		super();
		this.context = context;
		this.height = heihgt;
		this.width = width;
		this.dataList = dataList;
		// 设置gridView里的图片的长宽比例
		params = new LayoutParams(width, heihgt);

	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView img = new ImageView(context);
		img.setScaleType(ScaleType.FIT_XY);
		img.setLayoutParams(params);
		img.setImageBitmap(RoundCornerImageUtil.getRoundCornerImage(context,
				dataList.get(position).getImgResID(), 15));
		// img.setImageResource(dataList.get(position).getImgResID());
		// img.setBackgroundColor(Color.rgb(255, 204, 204));
		return img;
	}

}
