package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CommentItemDetailAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.Comment;
import com.tixa.industry.modelCustom.ProvideAndNeed;
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
public class CommentDetailActivity extends Activity {
	private TopBar topbar;
	private Context context;
	private IndustryApplication application;
	private ProvideAndNeed goodsDetail;
	private String goodsName;
	private String appID;
	private HttpApi api;
	private ListView listView;
	protected ArrayList<Comment> dataList = new ArrayList<Comment>();
	private int type;
	private String GoodorBuyInfoID;
	private int lastID;
	private int direct;
	private int number;
	private LoadView loadView;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NONETWORK:
				loadView.noNetWork(new OnClickListener() {
					@Override
					public void onClick(View v) {
						getCommentData();
					}
				});
				break;
			case Data.FULLDATA:
				loadView.close();
				CommentItemDetailAdapter adapter = new CommentItemDetailAdapter(
						dataList, context);
				listView.setAdapter(adapter);
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
		goodsDetail = (ProvideAndNeed) getIntent().getSerializableExtra(
				"goodsDetail");
		goodsName = goodsDetail.getGoodsName();
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		type = 1;
		GoodorBuyInfoID = goodsDetail.getId() + "";
		lastID = 1;
		direct = 0;
		number = 1000;
	}

	private void initView() {
		loadView = (LoadView) findViewById(R.id.loadview);
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("点评详情", goodsName, R.drawable.top_back,
				R.drawable.write);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {
				if (application.getMemberID() <= 0) {
					Toast.makeText(context, "使用相关功能，请先登陆", 1).show();
					Intent intent = new Intent(Constants.LOGIN_ACTION);
					sendBroadcast(intent);
				} else {
					Intent intent = new Intent(context, AddComment.class);
					intent.putExtra("goodsDetail", goodsDetail);
					startActivity(intent);
					finish();
				}
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

		api.getBuysellCommentList(type, GoodorBuyInfoID, lastID, direct,
				number, new RequestListener() {

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
								if (!jsonObject.optJSONObject(
										"GoodorBuyInfoListAndCount").has(
										"buySellCommentList")) {
									handler.sendEmptyMessage(Data.NODATA);
								} else {
									JSONArray goodsArray = jsonObject
											.optJSONObject(
													"GoodorBuyInfoListAndCount")
											.optJSONArray("buySellCommentList");
									dataList.clear();
									for (int i = 0; i < goodsArray.length(); i++) {
										JSONObject json = goodsArray
												.optJSONObject(i);
										Comment comment = new Comment(json);
										comment.setName(json.optJSONObject(
												"memberUser").optString("Name"));
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
