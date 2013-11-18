package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONObject;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.AppInfo;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Fragment {
	private View view;
	private Activity context;
	private TopBar topbar;
	private ImageView interviewDetails_Images;
	private TextView interviewDetails_SmallTextId;
	private int type = 0; // 1关于软件 2.使用帮助 3关于我们
	private String str = "";
	private HttpApi api;
	private String appID;
	private IndexData data;
	private AppInfo appInfo;
	private PageConfig config;
	private TopBarUtil util;
	private LoadView view_loading;
	private IndustryApplication application;
	private AsyncImageLoader loader;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Data.NONETWORK:
				view_loading.noNetWork(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						view_loading.loading();
						getData();						
					}
				});
				break;
			case Data.FULLDATA:
				view_loading.close();
				appInfo = (AppInfo) msg.obj;
				
				if(appInfo != null) {
					application.setInfo(appInfo);
				}
				
				FileUtil.setImage(interviewDetails_Images,
						appInfo.getApkIcon(), loader, R.drawable.default_ad);
				if (type == 1) {
					interviewDetails_SmallTextId.setText(appInfo
							.getAppIntroduce());
				} else if (type == 2) {
					interviewDetails_SmallTextId.setText(appInfo
							.getUseIntroduce());
				} else if (type == 3) {
					interviewDetails_SmallTextId.setText(appInfo.getAboutUs());
				}
				break;
			case Data.NODATA:
				view_loading.showNodataView();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		view = inflater.inflate(R.layout.about, null);
		context = getActivity();
		getPageConfig();
		type = getArguments().getInt("type",0);
		str = getArguments().getString("str");
		initData();
		initView();
		getData();	
		return view;
	}
	
	private void initData() {
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
	}
	
	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/MoreLayout.xml");
		config = p.parser();
	}
	
	private void initView() {
		loader = new AsyncImageLoader(context);
		interviewDetails_Images = (ImageView) view.findViewById(R.id.interviewDetails_Images);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		interviewDetails_SmallTextId = (TextView) view.findViewById(R.id.interviewDetails_SmallTextId);
		topbar = (TopBar) view.findViewById(R.id.topbar);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();		
		util = new TopBarUtil(false, naviStyle, topbar, str, getFragmentManager() , context,
				application.getTemplateId() , true, navi);
		util.showConfig();

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
								JSONObject obj = new JSONObject(res);
								AppInfo temp = new AppInfo(obj);
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
			view_loading.close();
			FileUtil.setImage(interviewDetails_Images, appInfo.getApkIcon(),
					loader, R.drawable.default_ad);
			if (type == 1) {
				interviewDetails_SmallTextId.setText(appInfo.getAppIntroduce());
			} else if (type == 2) {
				interviewDetails_SmallTextId.setText(appInfo.getUseIntroduce());
			} else if (type == 3) {
				interviewDetails_SmallTextId.setText(appInfo.getAboutUs());
			}
		}

	}
}	
