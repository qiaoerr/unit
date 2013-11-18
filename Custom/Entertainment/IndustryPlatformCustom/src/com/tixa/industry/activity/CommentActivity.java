package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.BuySellComment;
import com.tixa.industry.model.Data;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;

public class CommentActivity extends Activity {
	private Context context;
	private TopBar topBar;
	private RatingBar ratingBar;
	private EditText comment;
	private TextView remain;
	private Button commit;
	private IndustryApplication application;
	private HttpApi api;
	private String MemberID;
	private static final int COMMENT_SUCCESS = 1;
	private static final int COMMENT_FAIL = 0;
	private String itemID;
	private String price;
	private int type;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case COMMENT_SUCCESS:
				finish();
				break;
			case COMMENT_FAIL:
				T.shortT(context, "提交失败，请您重新提交");
				break;

			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_comment);
		context = this;
		application = IndustryApplication.getInstance();
		String appID = application.getAppID();
		MemberID = application.getMemberID() + "";
		// MemberID = 2 + "";
		api = new HttpApi(appID);
		itemID = getIntent().getStringExtra("itemID");
		price = getIntent().getStringExtra("price");
		type = getIntent().getIntExtra("type", 1);
		iniTopBar();
		initView();

	}

	private void iniTopBar() {
		topBar = (TopBar) findViewById(R.id.topbar);
		topBar.showConfig("评论", true, false, false, false);
		topBar.showButtonImage(R.drawable.top_back, 0, 0);
		topBar.setmListener(new TopBarListener() {
			@Override
			public void onButton3Click(View view) {
			}

			@Override
			public void onButton2Click(View view) {

			}

			@Override
			public void onButton1Click(View view) {
				finish();
			}
		});
	}

	private void initView() {
		ratingBar = (RatingBar) findViewById(R.id.myratingbar);
		comment = (EditText) findViewById(R.id.mycomment);
		remain = (TextView) findViewById(R.id.remain);
		commit = (Button) findViewById(R.id.commit);
		comment.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				temp = s;

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int num = 150 - temp.length();
				remain.setText(num + "/150");

			}
		});
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int score = (int) ratingBar.getRating();
				String GoodsComment = comment.getText().toString();
				commit(type, itemID, MemberID, score, price, GoodsComment);
			}
		});
	}

	private void commit(int type, String itemID, String MemberID, int score,
			String price, String GoodsComment) {
		api.buysellComment(type, itemID, MemberID, score, price, GoodsComment,
				new RequestListener() {

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
						ArrayList<BuySellComment> tempData = new ArrayList<BuySellComment>();
						try {
							if (StrUtil.isHttpException(response)) {
								handler.sendEmptyMessage(Data.NONETWORK);
							} else {
								JSONObject json = new JSONObject(response);
								String res = "";
								if (json.has("buySellComment")) {
									res = json.optString("buySellComment");
									if (res.equals("none")) {
										handler.sendEmptyMessage(Data.NODATA);
									} else {
										L.e("--------------------");
										L.e("res=" + res);
										JSONObject temp = json
												.optJSONObject("buySellComment");

										BuySellComment info = new BuySellComment(
												temp);
										Message msg = new Message();
										if (info.getId() > 0) {
											handler.sendEmptyMessage(COMMENT_SUCCESS);
										} else {
											handler.sendEmptyMessage(COMMENT_FAIL);
										}
									}
								} else {
									handler.sendEmptyMessage(COMMENT_FAIL);
								}
							}
						} catch (Exception e) {
							L.e(e.toString());
							handler.sendEmptyMessage(COMMENT_FAIL);
						}

					}
				});
	}

}
