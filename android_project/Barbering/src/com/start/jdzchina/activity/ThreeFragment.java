/**
  @Title: OneFragment.java
  @Package com.start.jdzchina.activity
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-26 下午10:38:10
  @version V1.0
 */

package com.start.jdzchina.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.start.jdzchina.R;
import com.start.jdzchina.adapter.MyExpandableAdapter;

/**
 * @ClassName: OneFragment
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-26 下午10:38:10
 * 
 */

public class ThreeFragment extends Fragment {
	private View view;
	private Context context;
	private ExpandableListView expand_list;
	private ExpandableListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.three_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		adapter = new MyExpandableAdapter(context);
	}

	private void initView() {
		expand_list = (ExpandableListView) view.findViewById(R.id.expand_list);
		expand_list.setAdapter(adapter);
		expand_list.setGroupIndicator(null);
		// 展开所有list
		int num = expand_list.getCount();
		for (int i = 0; i < num; i++) {
			expand_list.expandGroup(i);
		}
		// 不能写成下面这种形式，因为expand_list每展开一次,items owned by the Adapter associated
		// with this AdapterView都会增加
		// for (int i = 0; i < expand_list.getCount(); i++) {
		// expand_list.expandGroup(i);
		// }
	}
}
