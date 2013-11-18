package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.tixa.industry.R;
import com.tixa.industry.model.Advert;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class PicListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Advert> adList;
	private AsyncImageLoader loader;
	private int imageHeight;

	public PicListAdapter(Context context, ArrayList<Advert> adList) {
		super();
		this.context = context;
		this.adList = adList;
		loader = new AsyncImageLoader(context);
		calculateHeight();
	}

	private void calculateHeight() {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		float scale = dm.density;
		imageHeight = (int) ((screenHeight - 130 * scale) / 2);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return adList.size();
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
		ImageView imageView = new ImageView(context);
		AbsListView.LayoutParams params = new LayoutParams(
				android.widget.AbsListView.LayoutParams.MATCH_PARENT,
				imageHeight);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.FIT_XY);
		FileUtil.setImage(imageView, adList.get(position).getImgPath(), loader,
				R.drawable.item2);
		return imageView;
	}

}
