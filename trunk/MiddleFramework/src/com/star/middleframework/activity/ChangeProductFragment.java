package com.star.middleframework.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.star.middleframework.MiddleApplication;
import com.star.middleframework.R;

public class ChangeProductFragment extends Fragment implements OnClickListener {

	private Context context;
	private View view;
	private ImageView close;
	private ImageView imageView0;
	private ImageView imageView1;
	private MiddleApplication application;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.changeproduct_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		application = MiddleApplication.getInstance();

	}

	private void initView() {
		close = (ImageView) view.findViewById(R.id.btn_close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		imageView0 = (ImageView) view.findViewById(R.id.goods_img0);
		imageView1 = (ImageView) view.findViewById(R.id.goods_img1);
		imageView0.setOnClickListener(this);
		imageView1.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.goods_img0) {
			application.setRes_prefix("default_");
			changeBG("default_");
			getFragmentManager().popBackStack();
		} else if (v.getId() == R.id.goods_img1) {
			application.setRes_prefix("s6_");
			changeBG("s6_");
			getFragmentManager().popBackStack();
		} else {
		}

	}

	private void changeBG(String prefix) {
		ProductShowFragment.productShow.setBackgroundResource(getResources()
				.getIdentifier(prefix + "car", "drawable",
						context.getPackageName()));
	}
}
