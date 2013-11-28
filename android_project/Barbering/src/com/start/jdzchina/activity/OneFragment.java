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

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.start.jdzchina.R;
import com.start.jdzchina.adapter.GridViewAdapter;
import com.start.jdzchina.model.ShowModel;
import com.start.jdzchina.util.CommonUtil;

/**
 * @ClassName: OneFragment
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-26 下午10:38:10
 * 
 */

public class OneFragment extends Fragment implements OnItemClickListener {
	private View view;
	private Context context;
	private RelativeLayout gridViewContainer;
	private LayoutParams params;
	private int containerWidth;
	private float scale;
	private GridViewAdapter adapter;
	private ArrayList<ShowModel> dataList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.one_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		scale = CommonUtil.getScale(context);
		containerWidth = (int) (CommonUtil.getWidthPx(context) - 80 * scale);
		dataList = new ArrayList<ShowModel>();
		// test
		for (int i = 0; i < 6; i++) {
			ShowModel show = new ShowModel();
			show.setImgResID(R.drawable.vh_distribute + i);
			dataList.add(show);
		}
	}

	private void initView() {
		gridViewContainer = (RelativeLayout) view
				.findViewById(R.id.gridViewContainer);
		gridViewContainer.setPadding(0, 0, 0, 0);
		GridView gridView = new GridView(context);
		gridView.setPadding(0, 0, 0, 0);
		// gridView.setBackgroundColor(Color.BLUE);// ///
		gridView.setVerticalScrollBarEnabled(false);
		gridView.setOnItemClickListener(this);
		params = new LayoutParams(containerWidth, -2);
		params.setMargins((int) (20 * scale), (int) (20 * scale),
				(int) (0 * scale), (int) (20 * scale));
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		gridView.setLayoutParams(params);
		gridViewContainer.addView(gridView);

		gridView.setGravity(Gravity.CENTER_HORIZONTAL);
		gridView.setNumColumns(2);
		// 在4.0.4之前的版本中水平间距是一个和另一个之间的间距，在4.1.2及其以后的版本是一个item两边的间距之和
		gridView.setHorizontalSpacing((int) (10 * scale));
		gridView.setVerticalSpacing((int) (10 * scale));
		if (getSdkVersion() > 15) {
			adapter = new GridViewAdapter(context,
					(int) (containerWidth - 40 * scale) / 2, dataList);
		} else {
			adapter = new GridViewAdapter(context,
					(int) (containerWidth - 30 * scale) / 2, dataList);
		}

		gridView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	private int getSdkVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}
}
