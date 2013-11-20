package com.start.jdzchina.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.start.jdzchina.R;
import com.start.jdzchina.adapter.ProductGalleryAdapter;

public class ProductShowsInGallery extends Fragment {

	private Context context;
	private View view;
	private ImageView close;
	private Gallery mygallery;
	private int[] imgs;
	private BaseAdapter galleryAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.productshowingallery_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		imgs = new int[] { R.drawable.pic01, R.drawable.pic02,
				R.drawable.pic03, R.drawable.pic04, R.drawable.pic05,
				R.drawable.pic06, R.drawable.pic07, R.drawable.pic08,
				R.drawable.pic09, R.drawable.pic10, R.drawable.pic11,
				R.drawable.pic12 };
	}

	private void initView() {
		close = (ImageView) view.findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		mygallery = (Gallery) view.findViewById(R.id.mygallery);
		galleryAdapter = new ProductGalleryAdapter(imgs, context);
		mygallery.setAdapter(galleryAdapter);
		mygallery.setSelection(50);

	}

}
