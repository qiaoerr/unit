package com.star.efficientdevelop.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/*用于BannerView*/
public class ViewPagerAdapter extends PagerAdapter {
	private ArrayList<View> views;

	public ViewPagerAdapter(ArrayList<View> views) {
		super();
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		container.addView(views.get(position));
		/*views.get(position).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("position" + position);
			}
		});*/
		return views.get(position);
	}

}
