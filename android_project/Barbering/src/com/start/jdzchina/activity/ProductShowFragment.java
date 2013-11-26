package com.start.jdzchina.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.start.jdzchina.R;
import com.start.jdzchina.RapidApplication;

public class ProductShowFragment extends Fragment implements OnClickListener {
	private Context context;
	private View view;
	private ImageView one;
	private ImageView two;
	private ImageView three;
	private ImageView four;
	private Fragment fragment;
	private FragmentManager fm;
	private Intent intent;
	public static RelativeLayout productShow;
	private String res_prefix;
	private Bundle args;
	private ImageView change_product;

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
		res_prefix = RapidApplication.getInstance().getRes_prefix();
	}

	private void initView() {
		productShow = (RelativeLayout) view.findViewById(R.id.productShow);
		int res_id = getResources().getIdentifier(res_prefix + "car",
				"drawable", context.getPackageName());
		productShow.setBackgroundResource(res_id);
		one = (ImageView) view.findViewById(R.id.one);
		two = (ImageView) view.findViewById(R.id.two);
		three = (ImageView) view.findViewById(R.id.three);
		four = (ImageView) view.findViewById(R.id.four);
		change_product = (ImageView) view.findViewById(R.id.change_product);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		change_product.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.one:
			fragment = new ProductShowsIn360();
			args = new Bundle();
			args.putString("prefix", res_prefix);
			jumpToFragment(fragment);
			break;
		case R.id.two:
			fragment = new PictureStoreFragment();
			args = new Bundle();
			args.putString("prefix", res_prefix);
			fragment.setArguments(args);
			jumpToFragment(fragment);
			break;
		case R.id.three:
			intent = new Intent(context, MyDialogActivity.class);
			intent.putExtra("prefix", res_prefix);
			startActivity(intent);
			break;
		case R.id.four:

			break;
		case R.id.change_product:
			fragment = new ChangeProductFragment();
			jumpToFragment(fragment);
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
