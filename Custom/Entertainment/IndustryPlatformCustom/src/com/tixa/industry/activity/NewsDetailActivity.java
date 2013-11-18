package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.AppInfo;
import com.tixa.industry.model.Article;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.TopBar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class NewsDetailActivity extends Activity {
	private View view;
	private Activity context;
	private TopBar topbar;
	private ImageView interviewDetails_Images;
	private TextView title;
	private TextView time;
	private TextView detail;
	private Article article;
	private String str = "";
	private HttpApi api;
	private String appID;
	private AppInfo appInfo;
	private PageConfig config;
	private TopBarUtil util;
	private IndustryApplication application;
	private LoadView loadView;
	private AsyncImageLoader loader;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Data.NONETWORK:
				loadView.noNetWork(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						loadView.loading();
						getData();
					}
				});
				break;
			case Data.FULLDATA:
				Article a = (Article) msg.obj;
				title.setText(article.getTitle());
				detail.setText(a.getContentStr());
				time.setText(a.getCreateTime());
				if(a.getImgPath()!= null && !a.getImgPath().equals(InterfaceURL.IMGIP + null)) {
					FileUtil.setImage(interviewDetails_Images, a.getImgPath() , loader, R.drawable.default_ad);
				}				
				loadView.close();
				break;
				
			case Data.NODATA:
				loadView.showNodataView();				
				break;
			default:
				break;
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail_more);
		context = this;
		article = (Article) getIntent().getSerializableExtra("article");				
		getPageConfig();
		str  = getIntent().getStringExtra("str");
		initData();
		initView();	
		
	}
	private void getPageConfig() {
		application = IndustryApplication.getInstance();
		PageConfigParser p = new PageConfigParser(context,
				"layout/NewsDetailLayout.xml");
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
		loadView = (LoadView) findViewById(R.id.loadView);
		interviewDetails_Images = (ImageView) findViewById(R.id.interviewDetails_Images);
		
		int height = (int) ((CommonUtil.getWidthPx(context)-CommonUtil.dip2px(context, 40))/3) * 2 ;
		LayoutParams pars = new LayoutParams(LayoutParams.MATCH_PARENT , height);
		interviewDetails_Images.setLayoutParams(pars);
		
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, article.getTitle().length()>8?article.getTitle().substring(0,7)+"...":article.getTitle(),  null , context,
				application.getTemplateId() , true, navi);
		util.showConfig();
		loadView.loading();
		getData();
	}
	
	private void getData() {
		api.getArticleDetail(article.getId()+"", article.getOriginURL(), article.getSearchType(),
				article.getTitle(), new RequestListener() {				
					@Override
					public void onIOException(IOException e) {			
					}
					
					@Override
					public void onError(TixaException e) {
						handler.sendEmptyMessage(Data.NONETWORK);
					}
					
					@Override
					public void onComplete(String response) {
						try {
							JSONObject obj = new JSONObject(response);
							if(obj.has("article")) {
								JSONObject o = obj.optJSONObject("article");
								Article a = new Article(o);
								Message m = new Message();
								m.what = Data.FULLDATA;
								m.obj = a;
								handler.sendMessage(m);
							}else{
								handler.sendEmptyMessage(Data.NODATA);
							}
							
						} catch (JSONException e) {
							handler.sendEmptyMessage(Data.NODATA);
							e.printStackTrace();
						}		
					}
				});
		
	}
	
}
