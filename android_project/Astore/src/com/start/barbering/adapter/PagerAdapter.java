/**
  @Title: PagerAdapter.java
  @Package com.start.jdzchina.adapter
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-26 下午10:35:27
  @version V1.0
 */

package com.start.barbering.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * @ClassName: PagerAdapter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-26 下午10:35:27
 * 
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<Fragment> fragments;

	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);

	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {
		return super.instantiateItem(arg0, arg1);
	}
}
