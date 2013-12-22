package com.star.general.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.baseFramework.widget.BannerView;
import com.star.general.R;
import com.star.general.util.CommentUtil;

public class ActivityFragment extends Fragment {
	private Context context;
	private View view;
	private RelativeLayout actContainer;
	private ArrayList<BannerModel> bannerModels;
	private BannerView bannerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		bannerModels = new ArrayList<BannerModel>();
		ArrayList<Integer> arrayList = CommentUtil.getPrefixImages(context,
				"activity");
		for (int i = 0; i < arrayList.size(); i++) {
			BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
					arrayList.get(i));
			bannerModels.add(model);
		}

	}

	private void initView() {
		actContainer = (RelativeLayout) view.findViewById(R.id.actContainer);
		bannerView = new BannerView(context, bannerModels,
				BaseCommonUtil.getScreenWidth(context),
				BaseCommonUtil.getScreenHeight(context),
				BannerView.right_bottom);
		actContainer.addView(bannerView);
		Toast.makeText(context, "按住屏幕，可暂停左右滚动", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
