package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.MycommentAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.MyComment;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widgetCustom.LoadView;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

/**
 * @author administrator
 * @version
 * 
 */
public class MyCommentActivity extends Activity {

	private TopBar topbar;
	private Context context;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private ListView listView;
	protected ArrayList<MyComment> dataList = new ArrayList<MyComment>();
	private LoadView loadView;
	private String memberID;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NONETWORK:
				loadView.noNetWork(new OnClickListener() {
					@Override
					public void onClick(View v) {
						loadView.loading();
						getCommentData();
					}
				});
				break;
			case Data.FULLDATA:
				loadView.close();
				MycommentAdapter adapter = new MycommentAdapter(context,
						dataList);
				listView.setAdapter(adapter);
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
		setContentView(R.layout.layout_comment_detail);
		initData();
		initView();
	}

	private void initData() {
		context = this;
		application = IndustryApplication.getInstance();
		memberID = application.getMemberID() + "";
		appID = application.getAppID();
		api = new HttpApi(appID);
	}

	private void initView() {
		loadView = (LoadView) findViewById(R.id.loadview);
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("我的评论", R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});

		listView = (ListView) findViewById(R.id.list);
		getCommentData();

	}

	private void getCommentData() {
		api.getMyPageBuysellCommentList(memberID, new RequestListener() {

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
						JSONObject jsonObject = new JSONObject(response);
						if (jsonObject.optString("buysellcommentList").equals(
								"none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
							JSONArray goodsArray = jsonObject
									.optJSONArray("buysellcommentList");
							dataList.clear();
							for (int i = 0; i < goodsArray.length(); i++) {
								JSONObject json = goodsArray.optJSONObject(i);
								MyComment comment = new MyComment(json);
								dataList.add(comment);
							}
							handler.sendEmptyMessage(Data.FULLDATA);
						}
					}
				} catch (JSONException e) {
					L.e(e.toString());
					handler.sendEmptyMessage(Data.NONETWORK);
				}
			}
		});

	}

}
