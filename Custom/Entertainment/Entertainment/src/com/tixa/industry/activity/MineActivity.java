package com.tixa.industry.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

public class MineActivity extends Fragment implements OnClickListener {

	private View view;
	private Context context;
	private String modularName;
	private IndustryApplication application;
	private ImageView headImg;
	private TextView useName;
	private TextView comments;
	private TextView collect;
	private TextView message;
	private RelativeLayout comments_out;
	private RelativeLayout collect_out;
	private RelativeLayout message_out;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_mine, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		modularName = getArguments().getString("modularName");
		application = IndustryApplication.getInstance();

	}

	private void initView() {
		TopBar topbar = (TopBar) view.findViewById(R.id.topbar);
		topbar.setShowConfig(modularName, 0, R.drawable.singout);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {
				SharedPreferences sp = context.getSharedPreferences(
						"userMessage", 0);
				sp.edit().clear().commit();
				application.setMemberUser(null);
				context.sendBroadcast(new Intent(
						Constants.MY_LOGOUT_SUCCESS_ACTION));
			}

			@Override
			public void btnLeftOnClick() {
				// TODO Auto-generated method stub

			}
		});
		headImg = (ImageView) view.findViewById(R.id.head_img);
		FileUtil.setImage(headImg, InterfaceURL.IMGIP
				+ application.getMemberUser().getPhoto(),
				new AsyncImageLoader(), R.drawable.menber);
		useName = (TextView) view.findViewById(R.id.useName);
		useName.setText(application.getMemberUser().getName());
		comments = (TextView) view.findViewById(R.id.comment);
		collect = (TextView) view.findViewById(R.id.collection);
		message = (TextView) view.findViewById(R.id.message);
		comments_out = (RelativeLayout) view.findViewById(R.id.comment_out);
		collect_out = (RelativeLayout) view.findViewById(R.id.collection_out);
		message_out = (RelativeLayout) view.findViewById(R.id.message_out);
		comments_out.setOnClickListener(this);
		collect_out.setOnClickListener(this);
		message_out.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.comment_out) {
			Intent intent = new Intent(context, MyCommentActivity.class);
			startActivity(intent);

		} else if (v.getId() == R.id.collection_out) {
			Intent intent = new Intent(context, MyCollectionActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.message_out) {
			Intent intent = new Intent(context, MyMessageActivity.class);
			startActivity(intent);
		}

	}

}
