package com.tixa.industry.activity;


import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 *  创建求购信息
  * @ClassName: CreateBuyInfoActivity
  * @Description: TODO
  * @author shengy
  * @date 2013-7-23 上午9:24:48
  *
 */
public class CreateBuyInfoActivity extends Activity {
	private TopBar topbar;
	private TopBarUtil util;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private String memberID;
	private String modularName;
	private Activity context;
	private PageConfig config;
	private int pageStyle;
	private int pageStatus;
	private int naviStyle;
	private int navi; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_buyino);
		
		initData();
		initView();
	}

	private void initView() {
		topbar = (TopBar) findViewById(R.id.topbar);
		util = new TopBarUtil(false, naviStyle, topbar, modularName,
				null, context, application.getTemplateId(),
				false, navi);
		util.showConfig();
		
		util.showButton3("提交", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initData() {
		context = this;
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		memberID = application.getMemberID()+"" ;
		
		PageConfigParser p = new PageConfigParser(context,
				"layout/DemandLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
		naviStyle = config.getNavi().getBackItem();
		navi = config.getNavi().getType();
		modularName = "创建供应信息";		
	}
	
}
