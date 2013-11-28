/**
  @Title: GridViewAdapter.java
  @Package com.start.jdzchina.adapter
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-27 上午11:09:08
  @version V1.0
 */

package com.start.jdzchina.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.start.jdzchina.R;
import com.start.jdzchina.config.Constants;
import com.start.jdzchina.model.ShowModel;
import com.start.jdzchina.util.AsyncImageLoader;
import com.start.jdzchina.util.FileUtil;
import com.start.jdzchina.util.RoundCornerImageUtil;

/**
 * @ClassName: GridViewAdapter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-27 上午11:09:08
 * 
 */

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private int sizeBase;
	LayoutParams params;
	private ArrayList<ShowModel> dataList;

	public GridViewAdapter(Context context, int sizeBase,
			ArrayList<ShowModel> dataList) {
		super();
		this.context = context;
		this.sizeBase = sizeBase;
		this.dataList = dataList;
		// 设置gridView里的图片的长宽比例
		params = new LayoutParams(sizeBase,
				(int) (sizeBase * Constants.ratio_gridView));

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
				dataList.get(position).getImgResID(), 50));
		// img.setImageResource(dataList.get(position).getImgResID());
		FileUtil.setImage(img, "", new AsyncImageLoader(), R.drawable.vh_long);
		img.setBackgroundColor(Color.rgb(255, 204, 204));
		return img;
	}

}
