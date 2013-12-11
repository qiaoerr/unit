package com.star.middleframework.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.start.jdzchina.R;
import com.start.jdzchina.RapidApplication;
import com.start.jdzchina.widget.MyImageView;

public class ProductShowsIn360 extends Fragment {

	private Context context;
	private View view;
	private ImageView close;
	private int[] imgs;
	private MyImageView myImageView;
	private String res_prefix;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.productshowingallery_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		res_prefix = RapidApplication.getInstance().getRes_prefix();
		context = getActivity();
		imgs = new int[] { getResId("pic01"), getResId("pic02"),
				getResId("pic03"), getResId("pic04"), getResId("pic05"),
				getResId("pic06"), getResId("pic07"), getResId("pic08"),
				getResId("pic09"), getResId("pic10"), getResId("pic11"),
				getResId("pic12") };
	}

	private void initView() {
		close = (ImageView) view.findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		myImageView = (MyImageView) view.findViewById(R.id.myImageView);
		myImageView.setBackgroundResource(imgs[0]);
		myImageView.setImgs(imgs);

	}

	private int getResId(String resname) {
		return getResources().getIdentifier(res_prefix + resname, "drawable",
				context.getPackageName());
	}

}
