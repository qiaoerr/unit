package com.tixa.industry.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.tixa.industry.model.MemberUser;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.L;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

public class UserActivityOld extends Fragment implements OnClickListener {
	private FragmentActivity context;
	private View view;
	private AsyncImageLoader loader;
	private TextView name;
	private TopBar topbar;
	private TextView myCollect;
	private ImageView imageDetail;
	private ImageView message;
	private MemberUser memberUser;
	private long memberID = 0;
	private PageConfig config;
	private String titleName;
	private String modularName;
	private IndustryApplication application;
	private String typeID;
	private boolean isNav = false;
	private TopBarUtil util;
	private FragmentTransaction transaction;
	private FragmentManager manager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		application = IndustryApplication.getInstance();
		view = inflater.inflate(R.layout.my_detail, null);
		memberID = application.getMemberID();
		memberUser = application.getMemberUser();
		modularName = getArguments().getString("modularName");
		typeID = getArguments().getString("typeID");
		
		isNav = getArguments().getBoolean("isNav");
		getPageConfig();
		initView();
		return view;
	}

	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/UserLayout.xml");
		config = p.parser();
	}

	public void initView() {
		manager = context.getSupportFragmentManager();
		// transaction = context.getSupportFragmentManager().beginTransaction();
		loader = new AsyncImageLoader();
		imageDetail = (ImageView) view.findViewById(R.id.imageDetail);
		message = (ImageView) view.findViewById(R.id.message);
		name = (TextView) view.findViewById(R.id.name);
		myCollect = (TextView) view.findViewById(R.id.myCollect);

		myCollect.setOnClickListener(this);
		// FileUtil.setImage(imageDetail, memberUser.getPhoto(), loader,
		// R.drawable.default_ad);
		name.setText(memberUser.getName());
		topbar = (TopBar) view.findViewById(R.id.topbar);
		if (StrUtil.isEmpty(modularName)) {
			titleName = "我的";
		} else {
			titleName = modularName;
		}

		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(isNav, naviStyle, topbar, titleName,
				getFragmentManager(), context, application.getTemplateId(),
				false, navi);
		util.showConfig();
		util.showButton3("注销", new OnClickListener() {			
			@Override
			public void onClick(View v) {
				SharedPreferences sp = context.getSharedPreferences("userMessage", 0);
				sp.edit().clear().commit();
				application.setMemberUser(null);
				context.sendBroadcast(new Intent(Constants.MY_LOGOUT_SUCCESS_ACTION));
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.myCollect) {
			transaction = manager.beginTransaction();
			Fragment fragment = new CollectListActivity();

			Bundle b = new Bundle();
			b.putString("modularName", modularName);
			b.putString("typeID", typeID);
			b.putBoolean("isNav", isNav);
			fragment.setArguments(b);
			
			transaction.replace(R.id.fragment, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}

}
