package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.BuySellComment;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.DateUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.TopBar;

public class SupplyDetailAct extends Activity implements OnClickListener {
	private Activity context;
	private TextView companyName;
	private TextView time;
	private ImageView imageDetail;
	private TextView textDetail;
	private TopBar topbar;
	private TextView price;
	private TextView companyposition;
	private Goods goods;
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
	private RelativeLayout openCloseView;
	private ListView pushListView;
	private int number = Data.DATA_NUM;
	private String GoodorBuyInfoID;
	private int type;
	private String appID;
	private int firstID = 0;
	private int lastID = 0;
	private ArrayList<BuySellComment> comments = new ArrayList<BuySellComment>();
	private boolean isExpand;
	private ListAdapter adapter;
	private PageConfig config;
	private TopBarUtil util;
	private Button commentButton;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (pd != null) {
				pd.dismiss();
			}
			switch (msg.what) {
			case NET_ERROR:
				Toast.makeText(SupplyDetailAct.this, "网络异常", 3000).show();
				break;
			case COLLECT_SUCCESS:
				Toast.makeText(SupplyDetailAct.this, "收藏成功", 2000).show();
				break;
			case COLLECT_FAIL:
				Toast.makeText(SupplyDetailAct.this, "收藏失败", 2000).show();
				break;
			case COLLECT_ISEXIT:
				Toast.makeText(SupplyDetailAct.this, "已收藏", 2000).show();
				break;

			default:
				break;
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.produce_detail);
		context = this;
		getPageConfig();
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		goods = (Goods) getIntent().getSerializableExtra("goods");
		GoodorBuyInfoID = goods.getId() + "";
		type = goods.getType();
		initView();
		addToCache(goods);
	}

	private void addToCache(Goods goods) {
		ArrayList<Goods> skimRecoderCache = application.getSkimRecoder();
		if (skimRecoderCache.size() == 0) {
			skimRecoderCache.add(goods);
		} else {
			Goods temp = null;
			for (Goods goods2 : skimRecoderCache) {
				if (goods2.getId() == goods.getId()) {
					temp = goods2;
				}
			}
			skimRecoderCache.remove(temp);
			skimRecoderCache.add(0, goods);
		}
		saveToLocal(skimRecoderCache, "skimCache");
	}

	// 缓存浏览记录 fileName=skimCache
	private void saveToLocal(ArrayList<Goods> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + Constants.SKIM_DIR
					+ "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/SupplyLayout.xml");
		config = p.parser();
	}

	private void initView() {
		initCommentList();
		// upData();
		openCloseView = (RelativeLayout) findViewById(R.id.textDetail_loyout);
		openCloseView.setOnClickListener(this);
		topbar = (TopBar) findViewById(R.id.topbar);
		collectionButton = (Button) findViewById(R.id.collect);
		shareButton = (Button) findViewById(R.id.share);
		commentButton = (Button) findViewById(R.id.comment_btn);
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

		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(false, naviStyle, topbar, "商品详情", null, context,
				application.getTemplateId(), true, navi);
		util.showConfig();

		companyName.setText(goods.getGoodsName());
		if (type == 2) {
			companyName.setText(goods.getSubject());
		}

		try {
			textDetail.setText(goods.getGoodsDescStr());
		} catch (Exception e) {

		}

		price.setText("￥" + goods.getGoodsPrice());
		if (type == Constants.SELL) {
			FileUtil.setImage(imageDetail, goods.getGoodsImg(), loader,
					R.drawable.load);

		} else {
			FileUtil.setImage(imageDetail, goods.getImagePath(), loader,
					R.drawable.load);
		}

	}

	private void initCommentList() {
		pushListView = (ListView) findViewById(R.id.comment_list);

		pushListView.setDivider(null);

	}

	private void upData() {
		// if (comments != null && comments.size() > 0) {
		// lastID = (int) comments.get(comments.size() - 1).getId();
		// firstID = (int) comments.get(0).getId();
		// }
		// T.shortT(context, parentCode);
		api.getBuysellCommentList(type, GoodorBuyInfoID, firstID, -1, number,
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
								if (json.has("GoodorBuyInfoList")) {
									res = json.optString("GoodorBuyInfoList");
									if (res.equals("none")) {
										handler.sendEmptyMessage(Data.NODATA);
									} else {
										JSONArray cjar = json
												.optJSONArray("GoodorBuyInfoList");
										for (int i = 0; i < cjar.length(); i++) {
											JSONObject temp = cjar
													.optJSONObject(i);
											BuySellComment info = new BuySellComment(
													temp);
											tempData.add(info);
										}
										Message msg = new Message();
										msg.obj = tempData;
										msg.what = Data.FULLDATA;
										handler.sendMessage(msg);
									}
								}
							}
						} catch (Exception e) {
							L.e(e.toString());
							handler.sendEmptyMessage(Data.NONETWORK);
						}

					}
				});
	}

	private void getHistory() {

		if (comments != null && comments.size() > 0) {
			lastID = (int) comments.get(comments.size() - 1).getId();
			firstID = (int) comments.get(0).getId();
		}
		api.getBuysellCommentList(type, GoodorBuyInfoID, lastID, 1, number,
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
								if (json.has("GoodorBuyInfoList")) {
									res = json.optString("GoodorBuyInfoList");
									if (res.equals("none")) {
										handler.sendEmptyMessage(Data.NODATA);
									} else {
										L.e("--------------------");
										L.e("res=" + res);
										JSONArray cjar = json
												.optJSONArray("GoodorBuyInfoList");
										for (int i = 0; i < cjar.length(); i++) {
											JSONObject temp = cjar
													.optJSONObject(i);
											BuySellComment info = new BuySellComment(
													temp);
											tempData.add(info);
										}
										Message msg = new Message();
										msg.obj = tempData;
										msg.what = Data.FULLDATA;
										handler.sendMessage(msg);
									}
								}
							}
						} catch (Exception e) {
							L.e(e.toString());
							handler.sendEmptyMessage(Data.NONETWORK);
						}

					}
				});
	}

	// 收藏商品
	private void GoodsCollect(String uid, String itemId) {
		pd = ProgressDialog.show(context, "请稍候", "收藏中. . .", true, true);
		api.collectProduct(type, itemId, uid, new RequestListener() {

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
							if (res.equals("-1")) {
								handler.sendEmptyMessage(COLLECT_FAIL);
							} else if (res.equals("-2")) {
								handler.sendEmptyMessage(COLLECT_ISEXIT);
							} else {
								handler.sendEmptyMessage(COLLECT_SUCCESS);
							}
						} else {
							handler.sendEmptyMessage(Data.NONETWORK);
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
			shareContent = "嗨，我正在使用天下互联客户端，赶快来试试吧！！";
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
		if (v.getId() == R.id.textDetail_loyout) {
			// 文本展开与收起
			TextView textView = (TextView) findViewById(R.id.textDetail);
			TextView textStatus = (TextView) findViewById(R.id.status);
			ImageView imageView = (ImageView) findViewById(R.id.img_status);
			if (isExpand) {
				textView.setMaxLines(5);
				textStatus.setText("展开");
				imageView.setBackgroundResource(R.drawable.open);
				isExpand = false;
			} else {
				textView.setMaxLines(100);
				textStatus.setText("收起");
				imageView.setBackgroundResource(R.drawable.close);
				isExpand = true;
			}
			return;
		}

		if (IndustryApplication.getInstance().getMemberID() > 0) {
			isLogin = true;
		} else {
			isLogin = false;
		}
		if (!isLogin) {
			// 没有登录跳转到登录页
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
		} else {
			if (v.getId() == R.id.collect) {

				// 加入收藏操作
				Long userId = IndustryApplication.getInstance().getMemberID();
				String uid = String.valueOf(userId);
				long goodsId = goods.getId();
				String itemId = String.valueOf(goodsId);
				GoodsCollect(uid, itemId);

			} else if (v.getId() == R.id.share) {
				share();
			} else if (v.getId() == R.id.comment_btn) {
				Intent intent = new Intent(context, CommentActivity.class);
				intent.putExtra("price", goods.getGoodsPrice());
				intent.putExtra("itemID", goods.getId() + "");
				intent.putExtra("type", type);
				startActivity(intent);
			}
		}

	}

	@Override
	protected void onResume() {
		upData();
		super.onResume();
	}

	class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public ListAdapter() {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {

			return comments.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			BuySellComment comment = comments.get(position);
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(R.layout.comment_item, null);
				viewHolder.userName = (TextView) convertView
						.findViewById(R.id.userName);
				viewHolder.comment = (TextView) convertView
						.findViewById(R.id.comment);
				viewHolder.ratingbar = (RatingBar) convertView
						.findViewById(R.id.myratingbar);
				viewHolder.createTime = (TextView) convertView
						.findViewById(R.id.createTime);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			String useName = comment.getUserName().trim();
			Pattern pattern = Pattern.compile("^\\d{11}$");
			Matcher m = pattern.matcher(useName);
			if (m.matches()) {
				useName = useName.substring(0, 3) + "****"
						+ useName.substring(7, 11);
			}
			viewHolder.userName.setText(useName);
			viewHolder.comment.setText(comment.getGoods_Comment());
			long lTime = comment.getCreateTimeL();
			String sTime = DateUtil.parseDate1(new Date(lTime));
			viewHolder.createTime.setText(sTime);
			viewHolder.ratingbar.setRating(comment.getScore());
			return convertView;
		}

		class ViewHolder {
			TextView userName;
			RatingBar ratingbar;
			TextView score;
			TextView comment;
			TextView createTime;
		}

	}

}
