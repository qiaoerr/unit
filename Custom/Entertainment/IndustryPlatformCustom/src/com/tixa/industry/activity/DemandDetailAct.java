package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.BuyInfo;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

public class DemandDetailAct extends Activity implements OnClickListener {
	private Activity context;
	private TextView companyName;
	private TextView time;
	private ImageView imageDetail;
	private TextView textDetail;
	private TopBar topbar;
	private TextView price;
	private TextView companyposition;
	private BuyInfo buyInfo;
	private AsyncImageLoader loader;
	private TextView companytelephone;
	private HorizontalScrollView horizontalScrollView;
	private LinearLayout ll;
	private boolean isLogin;
	private ProgressDialog pd;
	private HttpApi api;
	private IndustryApplication application;
	private static final int NET_ERROR = 0;
	private static final int COLLECT_SUCCESS = 1;
	private static final int COLLECT_FAIL = 2;
	private static final int COLLECT_ISEXIT = 3;
	private Button collectionButton;
	private Button shareButton;
	private Button commentButton;
	private PageConfig config;
	private TopBarUtil util;


	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pd != null) {
				pd.dismiss();
			}
			switch (msg.what) {
			case NET_ERROR:
				Toast.makeText(DemandDetailAct.this, "网络异常", 3000).show();
				break;
			case COLLECT_SUCCESS:
				Toast.makeText(DemandDetailAct.this, "收藏成功", 2000).show();
				break;
			case COLLECT_FAIL:
				Toast.makeText(DemandDetailAct.this, "收藏失败", 2000).show();
				break;
			case COLLECT_ISEXIT:
				Toast.makeText(DemandDetailAct.this, "该商品已经在我的收藏里了", 2000)
						.show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = IndustryApplication.getInstance();
		String appID = application.getAppID();
		api = new HttpApi(appID);
		setContentView(R.layout.produce_detail);
		context = this;
		buyInfo = (BuyInfo) getIntent().getSerializableExtra("buyInfo");
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
		topbar = (TopBar) findViewById(R.id.topbar);
		collectionButton = (Button) findViewById(R.id.collect);
		shareButton = (Button) findViewById(R.id.share);
		commentButton = (Button) findViewById(R.id.comment);
		collectionButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		commentButton.setOnClickListener(this);
		loader = new AsyncImageLoader();
		companyName = (TextView) findViewById(R.id.companyName);
		// time = (TextView) findViewById(R.id.time);
		textDetail = (TextView) findViewById(R.id.textDetail);
		imageDetail = (ImageView) findViewById(R.id.imageDetail);
		// horizontalScrollView = (HorizontalScrollView)
		// findViewById(R.id.horizontalScrollView);
		price = (TextView) findViewById(R.id.price);
		// companyposition = (TextView) findViewById(R.id.companyposition);
		// companytelephone = (TextView) findViewById(R.id.companytelephone);
		// imageDetail = (ImageView) findViewById(R.id.imageDetail);
		// initViewPager();
		topbar = (TopBar) findViewById(R.id.topbar);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		util = new TopBarUtil(false, naviStyle, topbar, "商品详情", null, context,
				application.getTemplateId(), true, navi);
		util.showConfig();
		companyName.setText(buyInfo.getContact());
		textDetail.setText(buyInfo.getGoodsDescStr());
		price.setText("￥" + buyInfo.getGoodsPrice());
		FileUtil.setImage(imageDetail, buyInfo.getImagePath(), loader,
				R.drawable.default_ad);
	}

	// 收藏商品
	private void GoodsCollect(String uid, String itemId) {
		pd = ProgressDialog.show(context, "请稍候", "收藏中. . .", true, true);
		api.collectProduct(2, itemId, uid, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				L.e("sendOpinion onIOException =" + e.toString());

			}

			@Override
			public void onError(TixaException e) {
				L.e("sendOpinion onError statusCode=" + e.getStatusCode()
						+ " msg=" + e.getMessage());
				handler.sendEmptyMessage(NET_ERROR);

			}

			@Override
			public void onComplete(String response) {
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("buySellCollect")) {
							res = json.optString("buySellCollect");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
							L.e("--------------------");
							L.e("res=" + res);
							JSONObject obj = new JSONObject(res);

							Goods temp = new Goods(obj);
							L.e("------------222--------");
							L.e("aboutas=" + temp.getId());
							Message msg = new Message();
							msg.what = Data.FULLDATA;
							msg.obj = temp;

							handler.sendEmptyMessage(COLLECT_SUCCESS);

						}

					}
				} catch (Exception e) {
					L.e(e.toString());
					handler.sendEmptyMessage(Data.NONETWORK);
				}
			}
		});

	}

	// 分享
	private void share() {
		String shareContent = "";
		try {
			shareContent = "helloworld";
		} catch (Exception e) {
			shareContent = "嗨，我正在使用天下互联客户端，赶快来试试吧！！";
		}
		if (shareContent == null || shareContent.equals("")) {
			shareContent = "嗨，我正在使用天下互联客户端，赶快来试试吧！！";
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
		intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "软件分享"));
	}


	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.collect) {

			isLogin = true;
			if (!isLogin) {
				// 没有登录跳转到登录页
				Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);
			} else {
				// 加入收藏操作
				Long userId = 8l;
				String uid = String.valueOf(userId);
				long goodsId = buyInfo.getId();
				String itemId = String.valueOf(goodsId);
				GoodsCollect(uid, itemId);
			}

		} else if (v.getId() == R.id.share) {
			share();
		} else if (v.getId() == R.id.comment) {

		}

	}
}
