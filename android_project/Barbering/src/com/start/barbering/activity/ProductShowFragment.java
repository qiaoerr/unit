package com.start.barbering.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.start.barbering.R;
import com.start.barbering.adapter.PagerAdapter;

public class ProductShowFragment extends Fragment implements OnClickListener,
		OnPageChangeListener {
	private Context context;
	private View view;
	private ImageView one;
	private ImageView two;
	private ImageView three;
	private FragmentManager fm;
	public static LinearLayout productShow;
	private ViewPager myViewPager;
	private ArrayList<Fragment> fragments;

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
		fm = getFragmentManager();
		fragments = new ArrayList<Fragment>();
		OneFragment one = new OneFragment();
		TwoFragment two = new TwoFragment();
		ThreeFragment three = new ThreeFragment();
		fragments.add(one);
		fragments.add(two);
		fragments.add(three);
	}

	private void initView() {
		productShow = (LinearLayout) view.findViewById(R.id.productShow);
		// productShow.setBackgroundResource(R.drawable.default_car);
		myViewPager = (ViewPager) view.findViewById(R.id.myViewPager);
		myViewPager.setOnPageChangeListener(this);
		PagerAdapter adapter = new PagerAdapter(fm, fragments);
		myViewPager.setAdapter(adapter);
		one = (ImageView) view.findViewById(R.id.one);
		two = (ImageView) view.findViewById(R.id.two);
		three = (ImageView) view.findViewById(R.id.three);
		one.setOnClickListener(this);
		one.setSelected(true);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.one:
			myViewPager.setCurrentItem(0);
			break;
		case R.id.two:
			myViewPager.setCurrentItem(1);
			break;
		case R.id.three:
			myViewPager.setCurrentItem(2);
			break;

		default:
			break;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < 3; i++) {
			if (arg0 == 0) {
				one.setSelected(true);
				two.setSelected(false);
				three.setSelected(false);
			} else if (arg0 == 1) {
				one.setSelected(false);
				two.setSelected(true);
				three.setSelected(false);
			} else if (arg0 == 2) {
				one.setSelected(false);
				two.setSelected(false);
				three.setSelected(true);
			}
		}
	}

}
