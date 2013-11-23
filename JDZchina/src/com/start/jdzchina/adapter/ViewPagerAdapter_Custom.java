package com.start.jdzchina.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter_Custom extends PagerAdapter {
	private ArrayList<View> views;

	public ViewPagerAdapter_Custom(ArrayList<View> views) {
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
		// System.out.println("views.get(position).getLayoutParams().width"
		// + views.get(position).getLayoutParams().width);
		// System.out.println("views.get(position).getLayoutParams().height"
		// + views.get(position).getLayoutParams().height);
		// viewpagerAdapter返回给viewPager的view的layoutparams默认为-1，-1
		return views.get(position);
	}

}
