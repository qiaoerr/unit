/**
  @Title: OneFragment.java
  @Package com.start.jdzchina.activity
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-26 下午10:38:10
  @version V1.0
 */

package com.start.barbering.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.star.baseFramework.util.CommonUtil;
import com.star.baseFramework.widget.PagingViewGroup;
import com.start.astore.R;
import com.start.barbering.adapter.GridViewAdapter;
import com.start.barbering.config.Constants;
import com.start.barbering.model.ShowModel;

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
	private int rightBarWidth = 80;// ProductShowFragment的布局文件的right_tab的宽度
	private int column_space = 10;// gridView行和列的间距
	private int column = 2;// gridView的列数
	private int cellWidth;// gridview中每一个元素的宽度
	private int cellHight;// gridview中每一个元素的高度
	private int perPager;// 每页显示的个数
	private int margin = 20;
	private int pagingGroupHight;

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
		containerWidth = (int) (CommonUtil.getScreenWidth(context) - rightBarWidth
				* scale);
		// 在4.0.4之前的版本中水平间距是一个和另一个之间的间距，在4.1.2及其以后的版本是一个item两边的间距之和
		if (getSdkVersion() > 15) {
			cellWidth = (int) (containerWidth - (column * column_space + margin)
					* scale)
					/ column;
			cellHight = (int) (cellWidth * Constants.ratio_gridView);
			perPager = getPerPager();
			pagingGroupHight = (perPager / column)
					* (cellHight + (int) (column_space * scale));
		} else {
			cellWidth = (int) (containerWidth - ((column - 1) * column_space + margin)
					* scale)
					/ column;
			cellHight = (int) (cellWidth * Constants.ratio_gridView);
			perPager = getPerPager();
			pagingGroupHight = (int) ((perPager / column) * cellHight + column_space
					* ((perPager / column) - 1) * scale);
		}

		dataList = new ArrayList<ShowModel>();
		// test
		for (int i = 0; i < 6; i++) {
			ShowModel show = new ShowModel();
			show.setImgResID(R.drawable.model_1 + i);
			dataList.add(show);
		}
	}

	private int getPerPager() {
		return column
				* (int) ((CommonUtil.getScreenHeight(context) - 2 * margin
						* scale) / (cellHight + column_space * scale));
	}

	private void initView() {
		gridViewContainer = (RelativeLayout) view
				.findViewById(R.id.gridViewContainer);
		PagingViewGroup pagingViewGroup = new PagingViewGroup(context,
				PagingViewGroup.VERTICAL);
		params = new LayoutParams(-2, pagingGroupHight);
		params.setMargins((int) (margin * scale), (int) (margin * scale),
				(int) (0 * scale), (int) (margin * scale));
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		pagingViewGroup.setLayoutParams(params);
		gridViewContainer.addView(pagingViewGroup);
		int pagerNumber = (int) Math.ceil(dataList.size() / (double) perPager);
		for (int i = 0; i < pagerNumber; i++) {
			ArrayList<ShowModel> temp = new ArrayList<ShowModel>();
			for (int j = 0; j < perPager; j++) {
				try {
					temp.add(dataList.get(j + i * perPager));
				} catch (Exception e) {
					break;
				}
			}
			GridView gridView = new GridView(context);
			gridView.setNumColumns(column);
			gridView.setHorizontalSpacing((int) (column_space * scale));
			gridView.setVerticalSpacing((int) (column_space * scale));
			adapter = new GridViewAdapter(context, cellWidth, cellHight, temp);
			gridView.setAdapter(adapter);
			pagingViewGroup.addView(gridView);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	private int getSdkVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}
}
