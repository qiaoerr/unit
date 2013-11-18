package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.AppInfo;
import com.tixa.industry.model.Article;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Exhibition;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;

public class TradeDetailAct extends Activity {
	private Activity context;
	private TopBar topbar;
	private ImageView interviewDetails_Images;
	private TextView title;
	private TextView time;
	private TextView detail;
	private Exhibition exhibition;
	private String str = "";
	private HttpApi api;
	private String appID;
	private AsyncImageLoader loader;
	private PageConfig config;
	private TopBarUtil util;
	private IndustryApplication application;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				break;
			case Data.FULLDATA:

//				appInfo = (AppInfo) msg.obj;
//				FileUtil.setImage(interviewDetails_Images,
//						appInfo.getApkIcon(), loader, R.drawable.default_ad);
			
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.news_detail);
		super.onCreate(savedInstanceState);
		context = (Activity) this;
		exhibition = (Exhibition)getIntent().getSerializableExtra("exhibition");
		getPageConfig();
	
		initData();

		initView();
//		getData();

	}

	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/TradeLayout.xml");
		config = p.parser();
	}
	
	private void initData() {
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
	}

	private void initView() {
		loader = new AsyncImageLoader(context);
		title = (TextView) findViewById(R.id.title);
		time = (TextView) findViewById(R.id.time);
		detail = (TextView) findViewById(R.id.detail);
		
		topbar = (TopBar) findViewById(R.id.topbar);
		
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, exhibition.getExName() , null , context,
				application.getTemplateId() , true, navi);
		util.showConfig();
		
		title.setText(exhibition.getExName());
		detail.setText(exhibition.getDetail());
		time.setText(exhibition.getCreateTime());
	}

	private void getData() {
		AppInfo appInfo = application.getInfo();
		if (appInfo == null) {
			api.getAppInfo(new RequestListener() {

				@Override
				public void onIOException(IOException e) {
					T.shortT(context, "未知错误:" + e.getMessage());
				}

				@Override
				public void onError(TixaException e) {
					L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
					handler.sendEmptyMessage(Data.NONETWORK);
				}

				@Override
				public void onComplete(String response) {
					try {
						if (StrUtil.isHttpException(response)) {
							handler.sendEmptyMessage(Data.NONETWORK);
						} else {
							JSONObject json = new JSONObject(response);
							String res = "";
							if (json.has("clientAPPInfo")) {
								res = json.optString("clientAPPInfo");
							}
							if (res.equals("none")) {
								handler.sendEmptyMessage(Data.NODATA);
							} else {
								L.e("--------------------");
								L.e("res=" + res);
								JSONObject obj = new JSONObject(res);

								AppInfo temp = new AppInfo(obj);
								L.e("------------222--------");
								L.e("aboutas=" + temp.getAboutUs());
								Message msg = new Message();
								msg.what = Data.FULLDATA;
								msg.obj = temp;
								handler.sendMessage(msg);

							}

						}
					} catch (Exception e) {
						L.e(e.toString());
						handler.sendEmptyMessage(Data.NONETWORK);
					}

				}
			});
		} else {
		
		}

	}

}
