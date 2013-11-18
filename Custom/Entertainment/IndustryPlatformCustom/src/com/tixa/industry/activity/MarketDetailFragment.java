package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.AppInfo;
import com.tixa.industry.model.Article;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.MarketMessage;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.TopBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MarketDetailFragment extends Fragment {
	private View view;
	private FragmentActivity context;
	private TopBar topbar;
	private ImageView interviewDetails_Images;
	private TextView title;
	private TextView time;
	private TextView detail;
	private MarketMessage article;
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
				MarketMessage a = (MarketMessage) msg.obj;
				title.setText(a.getTitle());
				detail.setText(a.getContent());
				time.setText(a.getCreateTime());
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		view = inflater.inflate(R.layout.news_detail, null);	
		context = getActivity();
		article = (MarketMessage) getArguments().getSerializable("market");
		getPageConfig();
		str = getArguments().getString("str");
		initData();

		initView();		
		return view;

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
		title = (TextView) view.findViewById(R.id.title);
		time = (TextView) view.findViewById(R.id.time);
		detail = (TextView) view.findViewById(R.id.detail);
		topbar = (TopBar) view.findViewById(R.id.topbar);
		loadView = (LoadView) view.findViewById(R.id.loadView);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, article.getTitle().length()>8?article.getTitle().substring(0,7)+"...":article.getTitle(),  getFragmentManager() , context,
				application.getTemplateId() , true, navi);
		util.showConfig();
		
		
		loadView.loading();
		getData();
		
		/*title.setText(article.getTitle());
		detail.setText(article.getContentStr());
		time.setText(article.getCreateTime());*/
	}
	
	private void getData() {
		
		api.getMarketMessageDetail(article.getId()+"", new RequestListener() {				
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
							if(obj.has("MarketMessage")) {
								JSONObject o = obj.optJSONObject("MarketMessage");
								MarketMessage a = new MarketMessage(o);
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
