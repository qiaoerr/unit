package com.star.general.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.star.baseFramework.adapter.ViewPagerAdapter;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.general.R;
import com.star.general.adapter.GridViewAdapter;
import com.star.general.config.Constants;
import com.star.general.model.ShowModel;
import com.star.general.util.CommentUtil;

public class ProductShowFragment extends Fragment implements
		OnPageChangeListener {
	private Context context;
	private View view;
	public static LinearLayout productShow;
	private ViewPager myViewPager;
	private ArrayList<RelativeLayout> views;
	private ArrayList<ShowModel> dataList;
	private int column_space = 10;// gridView行和列的间距
	private int column = 2;// gridView的列数
	private int cellWidth;// gridview中每一个元素的宽度
	private int cellHight;// gridview中每一个元素的高度
	private int perPager;// 每页显示的个数
	private int margin = 20;
	private float scale;
	private GridViewAdapter adapter;
	private LayoutParams params;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.productshow_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		scale = BaseCommonUtil.getScale(context);
		views = new ArrayList<RelativeLayout>();
		// 在4.0.4之前的版本中水平间距是一个和另一个之间的间距，在4.1.2及其以后的版本是一个item两边的间距之和
		if (getSdkVersion() > 15) {
			cellWidth = (int) (BaseCommonUtil.getScreenWidth(context) - (column
					* column_space + 2 * margin)
					* scale)
					/ column;
			cellHight = (int) (cellWidth * Constants.ratio_gridView);
			perPager = getPerPager();
		} else {
			cellWidth = (int) (BaseCommonUtil.getScreenWidth(context) - ((column - 1)
					* column_space + 2 * margin)
					* scale)
					/ column;
			cellHight = (int) (cellWidth * Constants.ratio_gridView);
			perPager = getPerPager();
		}
		dataList = new ArrayList<ShowModel>();
		// test
		ArrayList<Integer> arrayList = CommentUtil.getPrefixImages(context,
				"model_");
		for (int i = 0; i < arrayList.size(); i++) {
			ShowModel show = new ShowModel();
			show.setImgResID(arrayList.get(i));
			dataList.add(show);
		}
		initViewsInViewPager();
	}

	private void initViewsInViewPager() {
		int pagerNumber = (int) Math.ceil(dataList.size() / (double) perPager);
		for (int i = 0; i < pagerNumber; i++) {
			final ArrayList<ShowModel> temp = new ArrayList<ShowModel>();
			for (int j = 0; j < perPager; j++) {
				try {
					temp.add(dataList.get(j + i * perPager));
				} catch (Exception e) {
					break;
				}
			}
			RelativeLayout rel_out = new RelativeLayout(context);
			// rel_out.setBackgroundColor(Color.RED);
			GridView gridView = new GridView(context);
			// gridView.setBackgroundColor(Color.BLUE);
			params = new LayoutParams(-2, -2);
			params.setMargins((int) (margin * scale), 0,
					(int) (margin * scale), 0);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			gridView.setLayoutParams(params);
			rel_out.addView(gridView);
			gridView.setNumColumns(column);
			gridView.setHorizontalSpacing((int) (column_space * scale));
			gridView.setVerticalSpacing((int) (column_space * scale));
			adapter = new GridViewAdapter(context, cellWidth, cellHight, temp);
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// 添加点击事件
				}
			});
			views.add(rel_out);
		}
	}

	private void initView() {
		productShow = (LinearLayout) view.findViewById(R.id.productShow);
		myViewPager = (ViewPager) view.findViewById(R.id.myViewPager);
		myViewPager.setOnPageChangeListener(this);
		ViewPagerAdapter adapter = new ViewPagerAdapter(views);
		myViewPager.setAdapter(adapter);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {

	}

	private int getSdkVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	private int getPerPager() {
		return column
				* (int) ((BaseCommonUtil.getScreenHeight(context) / (cellHight + column_space
						* scale)));
	}
}
