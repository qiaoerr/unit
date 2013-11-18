package com.tixa.industry.activity;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.model.SlideMenuConfig;
import com.tixa.industry.parser.ModularParser;
import com.tixa.industry.parser.RootParser;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;

public class EntranceActivity extends Activity {
	private Context context;
	private RelativeLayout relative;
	private ImageView logo;
	private ProgressBar pgbar;
	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private SlideMenuConfig slide;
	private SparseArray<ModularConfig> modularMap; // 模块对应关系

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.FULLDATA:
				IndexData data = (IndexData) msg.obj;
				config.setMainData(data);
				if (data != null) {
					saveMainData(appID, data);
				}
				jumpToMain();
				break;
			case Data.NODATA:
				if (getMainData()) {
					jumpToMain();
				}
				break;
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				if (getMainData()) {
					jumpToMain();
				} else {
					finish();
				}
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initData();
		long time1 = new Date().getTime();
		getRootConfig();
		long time2 = new Date().getTime();
		L.e("解析时间为" + (time2 - time1) + "ms");
		init();
		getDataFormWeb();
	}

	// 跳转主页
	protected void jumpToMain() {
		// Intent jumpIntent =new Intent(EntranceActivity.this,
		// MainTabHost.class);
		Intent jumpIntent = new Intent(EntranceActivity.this,
				MainActivity.class);
		startActivity(jumpIntent);
		finish();
	}

	// 保存数据到SD卡
	private void saveMainData(final String appID, final IndexData data) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String dic = Constants.CACHE_DIR + appID + "/";
				String fileName = "maindata.tx";
				try {
					FileUtil.saveFile(dic, fileName, data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	// 读取部分XML数据
	private void getRootConfig() {
		RootParser parser = new RootParser(context);
		slide = parser.parse();
		if (slide != null) {
			config.setIsSlide(slide.getIsSlide());
			config.setConfig(slide);
		}
		ModularParser parse = new ModularParser(context);
		modularMap = parse.parse();
		if (modularMap != null) {
			config.setModularMap(modularMap);
		}

		/*
		 * PageConfigParser p = new PageConfigParser(context,
		 * "layout/HomeLayout.xml"); PageConfig con = p.parser();
		 * L.d("web :"+con.getWeb().getType()+" "+con.getWeb().getUrl());
		 * L.d("navi :" +con.getNavi().getShow()+" "+con.getNavi().getType()+
		 * " " +con.getNavi().getBackItem());
		 * L.d("search :"+con.getSearch().getShow()+" "+
		 * con.getSearch().getType()); L.d("ad :"+con.getAd().getShow()+" "+
		 * con.getAd().getType()); L.d("tabBar :"+con.getTabBar().getShow()+" "+
		 * con.getTabBar().getType());
		 * L.d("block :"+con.getBlock().getSecmenuShow()+" "+
		 * con.getBlock().getSecmenuType()+" "+con.getBlock().getTableType());
		 */}

	// 从SD卡取得数据
	private boolean getMainData() {
		try {
			String dic = Constants.CACHE_DIR + appID + "/maindata.tx";
			IndexData info = (IndexData) FileUtil.getFile(dic);
			if (info != null) {
				config.setMainData(info);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void initData() {
		context = this;
		config = IndustryApplication.getInstance();
		appID = config.getAppID();
		api = new HttpApi(appID);

	}

	private void getDataFormWeb() {

		api.getIndex(new RequestListener() {
			@Override
			public void onIOException(IOException e) {
				T.shortT(context, "未知错误:" + e.getMessage());
			}

			@Override
			public void onError(TixaException e) {
				// T.shortT(context,
				// "未知错误:"+e.getMessage()+" "+e.getStatusCode());
				L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
				handler.sendEmptyMessage(Data.NONETWORK);
			}

			@Override
			public void onComplete(String response) {
				try {
					JSONObject obj = new JSONObject(response);
					data = new IndexData(obj);
					Message msg = new Message();
					if (data != null) {
						msg.what = Data.FULLDATA;
					} else {
						msg.what = Data.NODATA;
					}
					msg.obj = data;
					handler.sendMessage(msg);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
	}

	private void init() {
		relative = (RelativeLayout) findViewById(R.id.entranceActivity_bgId);
		logo = (ImageView) findViewById(R.id.logo);
		relative.setBackgroundResource(R.drawable.loading);
		pgbar = (ProgressBar) findViewById(R.id.progressBar);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 0.8f);
		animation.setDuration(3000);
		relative.setAnimation(animation);
	}
}
