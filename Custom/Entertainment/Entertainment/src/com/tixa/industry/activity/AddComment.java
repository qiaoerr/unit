package com.tixa.industry.activity;

import java.io.IOException;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

public class AddComment extends Activity {
	private Context context;
	private TopBar topbar;
	private TextView remain_word;
	private RatingBar ratingBar;
	private EditText averageText;
	private EditText comment;
	private Button commit;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private int type;
	private String itemID;
	private String MemberID;
	private String itemMemberID;
	private int score;
	private String price;
	private String GoodsComment;
	private RelativeLayout progressBar;
	private static final int SUCCESS = 111;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NONETWORK:
				progressBar.setVisibility(View.GONE);
				Toast.makeText(context, "评论失败，请重新提交", 1).show();
				break;
			case SUCCESS:
				progressBar.setVisibility(View.GONE);
				Toast.makeText(context, "评论成功", 1).show();
				finish();
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_addcomment);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		ProvideAndNeed goodsDetail = (ProvideAndNeed) getIntent()
				.getSerializableExtra("goodsDetail");
		itemID = goodsDetail.getId() + "";
		itemMemberID = goodsDetail.getMemberID() + "";
		MemberID = application.getMemberID() + "";

	}

	private void initView() {
		progressBar = (RelativeLayout) findViewById(R.id.progressbar);
		ratingBar = (RatingBar) findViewById(R.id.goodsRating);
		averageText = (EditText) findViewById(R.id.average_consume);
		commit = (Button) findViewById(R.id.commit_btn);
		remain_word = (TextView) findViewById(R.id.remain_words);
		comment = (EditText) findViewById(R.id.goods_comment);
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
				int num = 300 - temp.length();
				remain_word.setText(num + "/300");
			}
		});
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				type = 1;
				score = (int) ratingBar.getRating();
				price = averageText.getText().toString().trim();
				GoodsComment = comment.getText().toString().trim();
				if (GoodsComment.equals("")) {
					Toast.makeText(context, "请填写评论", 1).show();
					return;
				}
				progressBar.setVisibility(View.VISIBLE);
				api.buysellComment(type, itemID, MemberID, score, price,
						GoodsComment, itemMemberID, new RequestListener() {

							@Override
							public void onIOException(IOException e) {
								T.shortT(context, "未知错误" + e.getMessage());

							}

							@Override
							public void onError(TixaException e) {
								L.e("未知错误" + e.getMessage() + " "
										+ e.getStatusCode());
								handler.sendEmptyMessage(Data.NONETWORK);

							}

							@Override
							public void onComplete(String response) {

								if (StrUtil.isHttpException(response)) {
									handler.sendEmptyMessage(Data.NONETWORK);
								} else {
									handler.sendEmptyMessage(SUCCESS);
								}
							}
						});
			}
		});
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("添加评论", R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {
			}

			@Override
			public void btnLeftOnClick() {
				finish();
			}
		});

	}
}
