package com.start.jdzchina.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.start.jdzchina.util.CommonUtil;

public class ProductGalleryAdapter extends BaseAdapter {
	private int[] imgs;
	private Context context;

	public ProductGalleryAdapter(int[] imgs, Context context) {
		super();
		this.imgs = imgs;
		this.context = context;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
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

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		// 设置当前图像的图像（position为当前图像列表的位置）
		imageView.setImageResource(imgs[position % imgs.length]);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(CommonUtil
				.getWidthPx(context), CommonUtil.getHeightPx(context)));
		// 设置Gallery组件的背景风格
		// imageView.setBackgroundResource(mGalleryItemBackground);
		return imageView;
	}

}
