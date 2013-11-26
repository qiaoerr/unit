package com.start.jdzchina.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.start.jdzchina.R;

public class ProductShowFragment extends Fragment implements OnClickListener {
	private Context context;
	private View view;
	private ImageView one;
	private ImageView two;
	private ImageView three;
	private FragmentManager fm;
	public static LinearLayout productShow;
	private ViewPager myViewPager;

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
	}

	private void initView() {
		productShow = (LinearLayout) view.findViewById(R.id.productShow);
		productShow.setBackgroundResource(R.drawable.default_car);
		myViewPager = (ViewPager) view.findViewById(R.id.myViewPager);
		one = (ImageView) view.findViewById(R.id.one);
		two = (ImageView) view.findViewById(R.id.two);
		three = (ImageView) view.findViewById(R.id.three);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.one:
			break;
		case R.id.two:
			break;
		case R.id.three:
			break;

		default:
			break;
		}

	}

	private void jumpToFragment(Fragment fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.add(R.id.container, fragment);
		transaction.addToBackStack(null);
		transaction.commitAllowingStateLoss();
	}

}
