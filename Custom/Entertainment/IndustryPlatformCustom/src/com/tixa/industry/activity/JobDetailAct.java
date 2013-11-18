package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.model.RecruitingInfo;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.PushListView;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;

public class JobDetailAct extends Activity {
	private Activity context;
	private TextView companyName;
	private TextView time;
	private TopBar topbar;
	private RecruitingInfo recruitingInfo;
	private TextView company_hangye;
	private TextView company_zhiwei;
	private TextView company_xinzi;
	private TextView company_jieshao;
	private TextView company_detail;
	private IndustryApplication application;
	private PageConfig config;
	private TopBarUtil util;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_detail);
		context = this;
		recruitingInfo =(RecruitingInfo)getIntent().getSerializableExtra("recruitingInfo");
		getPageConfig();
		initView();
	}

	private void getPageConfig() {
		application = IndustryApplication.getInstance();
		PageConfigParser p = new PageConfigParser(context,
				"layout/JobDetailLayout.xml");
		config = p.parser();
	}
	
	private void initView() {
		companyName = (TextView) findViewById(R.id.companyName);
		company_hangye = (TextView) findViewById(R.id.company_hangye);
		time = (TextView) findViewById(R.id.time);
		company_zhiwei = (TextView) findViewById(R.id.company_zhiwei);
		company_xinzi = (TextView) findViewById(R.id.company_xinzi);
		company_jieshao = (TextView) findViewById(R.id.company_jieshao);
		company_detail = (TextView) findViewById(R.id.company_detail);
		topbar = (TopBar) findViewById(R.id.topbar);
		time.setText(recruitingInfo.getCreateTime());
		companyName.setText(recruitingInfo.getHiringCompany());
		company_hangye.setText("公司行业："+recruitingInfo.getZoneCode());
		company_zhiwei.setText("职位："+recruitingInfo.getPositionCode());
		company_xinzi.setText("薪资："+recruitingInfo.getSalary());
		company_detail.setText(recruitingInfo.getCompanyProfile());
		
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, "招聘详情", null , context,
				application.getTemplateId() , true, navi);
		util.showConfig();
	}

}
