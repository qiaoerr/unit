package com.tixa.industry.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.model.EnterMember;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

public class CompanyDetailAct extends Activity {
	private Activity context;
	private TextView companyName;
	private TextView contact_people;
	private TextView contact_phone;
	private TextView company_detail;
	private EnterMember enterMember;
	private TopBar topbar;
	private PageConfig config;
	private TopBarUtil util;
	private IndustryApplication application;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_detail);
		context = this;
		enterMember = (EnterMember)getIntent().getSerializableExtra("enterMember");
		getPageConfig();
		initView();
	}
	private void getPageConfig() {
		application = IndustryApplication.getInstance();
		PageConfigParser p = new PageConfigParser(context,
				"layout/CompanyDetailLayout.xml");
		config = p.parser();
	}
	private void initView() {
		companyName = (TextView) findViewById(R.id.companyName);
		contact_people = (TextView) findViewById(R.id.contact_people);
		contact_phone = (TextView) findViewById(R.id.contact_phone);
		company_detail = (TextView) findViewById(R.id.company_detail);
		companyName.setText(enterMember.getName());
		contact_people.setText("公司地址："+enterMember.getAddress());
		contact_phone.setText("联系电话："+enterMember.getMobile());
		company_detail.setText(enterMember.getDes());
		
		topbar = (TopBar)findViewById(R.id.topbar);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, "企业黄页", null , context,
				application.getTemplateId() , true, navi);
		util.showConfig();
	}

}
