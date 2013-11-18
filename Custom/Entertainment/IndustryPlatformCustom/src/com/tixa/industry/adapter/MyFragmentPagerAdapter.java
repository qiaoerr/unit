package com.tixa.industry.adapter;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

	  private ArrayList<Fragment> fragmentsList;

	    public MyFragmentPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }

	    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
	        super(fm);
	        this.fragmentsList = fragments;
	    }
	    
	    public void setData (ArrayList<Fragment> fragmentsList) {
	    	this.fragmentsList = fragmentsList;
	    }
	    
	    
	    @Override
	    public int getCount() {
	        return fragmentsList.size();
	    }

	    @Override
	    public Fragment getItem(int arg0) {
	        return fragmentsList.get(arg0);
	    }

	    @Override
	    public int getItemPosition(Object object) {
	        return super.getItemPosition(object);
	    }

	    
	    

}
