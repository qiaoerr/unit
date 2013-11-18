/**
 * @Title: SupplyDetailActivity.java
 * @Package com.tixa.industry.activity
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京天下互联科技有限公司
 * 
 * @author Comsys-Administrator
 * @date 2013-8-15 上午08:51:52
 * @version V1.0
 */

package com.tixa.industry.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.activity.SupplyDetailAct.ListAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.BuySellComment;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

/**
 * @ClassName: SupplyDetailActivity
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-8-15 上午08:51:52
 * 
 */

public class SupplyDetailActivity extends Fragment implements OnClickListener {
	private TextView companyName;
	private TextView time;
	private ImageView imageDetail;
	private TextView textDetail;
	private TopBar topbar;
	private TextView price;
	private TextView companyposition;
	private AsyncImageLoader loader;
	private TextView companytelephone;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout ll;
	private boolean isLogin;
	private ProgressDialog pd;
	private Button shareButton;
	private RelativeLayout openCloseView;
	private ListView pushListView;
	private int number = Data.DATA_NUM;
	private int firstID = 0;
	private int lastID = 0;
	private ArrayList<BuySellComment> comments = new ArrayList<BuySellComment>();
	private boolean isExpand;
	private ListAdapter adapter;
	private TopBarUtil util;
	private View view;
	private Activity context;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private Goods goods;
	private String GoodorBuyInfoID;
	private int type;
	private PageConfig config;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.produce_detail1, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		getPageConfig();
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		goods = (Goods) getArguments().getSerializable("goods");
		GoodorBuyInfoID = goods.getId() + "";
		type = goods.getType();
		initView();

	}

	private void initView() {

		initCommentList();
		openCloseView = (RelativeLayout) view
				.findViewById(R.id.textDetail_loyout);
		openCloseView.setOnClickListener(this);
		topbar = (TopBar) view.findViewById(R.id.topbar);
		shareButton = (Button) view.findViewById(R.id.share);
		loader = new AsyncImageLoader();
		companyName = (TextView) view.findViewById(R.id.companyName);
		textDetail = (TextView) view.findViewById(R.id.textDetail);
		imageDetail = (ImageView) view.findViewById(R.id.imageDetail);
		price = (TextView) view.findViewById(R.id.price);

		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(false, naviStyle, topbar, "商品详情",
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();

		companyName.setText(goods.getGoodsName());
		if (type == 2) {
			companyName.setText(goods.getSubject());
		}

		try {
			textDetail.setText(goods.getGoodsDescStr());
		} catch (Exception e) {

		}

		price.setText("￥" + goods.getGoodsPrice());
		if (type == Constants.SELL) {
			FileUtil.setImage(imageDetail, goods.getGoodsImg(), loader,
					R.drawable.load);

		} else {
			FileUtil.setImage(imageDetail, goods.getImagePath(), loader,
					R.drawable.load);
		}

	}

	private void initCommentList() {
		pushListView = (ListView) view.findViewById(R.id.comment_list);

		pushListView.setDivider(null);

	}

	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/SupplyLayout.xml");
		config = p.parser();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.textDetail_loyout) {
			// 文本展开与收起
			TextView textView = (TextView) view.findViewById(R.id.textDetail);
			TextView textStatus = (TextView) view.findViewById(R.id.status);
			ImageView imageView = (ImageView) view
					.findViewById(R.id.img_status);
			if (isExpand) {
				textView.setMaxLines(5);
				textStatus.setText("展开");
				imageView.setBackgroundResource(R.drawable.open);
				isExpand = false;
			} else {
				textView.setMaxLines(100);
				textStatus.setText("收起");
				imageView.setBackgroundResource(R.drawable.close);
				isExpand = true;
			}
			return;
		}

	}

}
