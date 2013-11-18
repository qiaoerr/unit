package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CommentItemAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.Comment;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.utilCustom.ListViewUtil;
import com.tixa.industry.widgetCustom.BottomBar;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

/**
 * @author administrator
 * @version 1
 */
public class ProvideNeedDetail extends Activity {

	private TopBar topbar;
	private Context context;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private BottomBar bottombar;
	private TextView goodsName;
	private ImageView goodsImg;
	private RatingBar goodsRating;
	private TextView goodsPrice;
	private TextView introduce;
	private TextView introduce_head;
	private boolean isExpand = false;
	private TextView address;
	private TextView telphone;
	private ProvideAndNeed goodsDetail;
	private AsyncImageLoader loader;
	private String GoodorBuyInfoID;
	private int lastID;
	private int direct;
	private int number;
	private String price;
	private String GoodsComment;
	protected ArrayList<Comment> dataList = new ArrayList<Comment>();
	private ListView listView_comment;
	private TextView listView_comment_head;
	private static final int COLLECT_SUCCESS = 101;
	private static final int COLLECT_FAILD = 102;
	private static final int COLLECT_EXIST = 103;
	private int type = 1;
	private String itemID;
	private String MemberID;
	private String name;
	private LinearLayout picGroup;
	private float scale;
	private ImageView fullScreenPic;
	private RelativeLayout fullScreenPic_out;
	private float screen_x;
	private float screen_y;
	private DisplayMetrics dm;
	private ScaleAnimation scaleAnim_exit = null;
	private int searchType;
	private int tabNum = 5;
	private ImageView img;
	private Handler handler = new Handler() {
		private LinearLayout linearLayout;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NONETWORK:
				listView_comment_head.setVisibility(View.GONE);
				break;
			case Data.NODATA:
				listView_comment_head.setVisibility(View.GONE);
				break;
			case Data.FULLDATA:
				listView_comment_head.setVisibility(View.VISIBLE);
				CommentItemAdapter adapter = new CommentItemAdapter(dataList,
						context);
				if (linearLayout == null) {
					linearLayout = new LinearLayout(context);
					linearLayout.setBackgroundResource(R.color.gray);
					linearLayout.setGravity(Gravity.CENTER);
					LayoutParams params = new LayoutParams(-2, 70);
					TextView textView = new TextView(context);
					textView.setGravity(Gravity.CENTER);
					textView.setLayoutParams(params);
					textView.setTextColor(Color.WHITE);
					textView.setText("查看点评详情");
					linearLayout.addView(textView);
					listView_comment.addFooterView(linearLayout);
					linearLayout.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context,
									CommentDetailActivity.class);
							intent.putExtra("goodsDetail", goodsDetail);
							startActivity(intent);
						}
					});
				}
				listView_comment.setAdapter(adapter);
				ListViewUtil.setListViewHeightBasedOnChildren(listView_comment);

				break;
			case COLLECT_SUCCESS:
				Toast.makeText(context, "收藏成功", 1).show();
				break;
			case COLLECT_EXIST:
				Toast.makeText(context, "已收藏", 1).show();
				break;
			case COLLECT_FAILD:
				Toast.makeText(context, "收藏失败", 1).show();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provide_need_detail);
		initData();
		initView();
	}

	private void initData() {
		context = this;
		dm = getResources().getDisplayMetrics();
		scale = getResources().getDisplayMetrics().density;
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		goodsDetail = (ProvideAndNeed) getIntent().getSerializableExtra(
				"goodsDetail");
		loader = new AsyncImageLoader();
		name = goodsDetail.getGoodsName();
		GoodorBuyInfoID = goodsDetail.getId() + "";
		MemberID = application.getMemberID() + "";
		itemID = goodsDetail.getId() + "";
		lastID = 1;
		direct = 0;
		number = 5;
		searchType = goodsDetail.getSearchType();
		if (searchType == 0) {
			tabNum = 5;
		} else if (searchType == 1) {
			tabNum = 3;
		}
	}

	private void initView() {
		fullScreenPic = (ImageView) findViewById(R.id.fullScreenPic);
		fullScreenPic_out = (RelativeLayout) findViewById(R.id.fullScreenPic_out);
		picGroup = (LinearLayout) findViewById(R.id.pic_skim_linearLayout);

		for (int i = 0; i < 3; i++) {
			final ImageView imageView = new ImageView(context);
			LayoutParams params = new LayoutParams((int) (120 * scale),
					(int) (80 * scale));
			params.setMargins(10, 0, 10, 0);
			imageView.setImageResource(R.drawable.back_1014);
			imageView.setLayoutParams(params);
			FileUtil.setImage(imageView, goodsDetail.getGoodsImg(),
					new AsyncImageLoader(), R.drawable.load);
			picGroup.addView(imageView);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					FileUtil.setImage(fullScreenPic, goodsDetail.getGoodsImg(),
							new AsyncImageLoader(), R.drawable.logo);
					ScaleAnimation scaleAnim_start = new ScaleAnimation(0f, 1f,
							0f, 1f, Animation.RELATIVE_TO_PARENT, screen_x,
							Animation.RELATIVE_TO_PARENT, screen_y);
					scaleAnim_start.setDuration(800);// 设置动画持续时间
					scaleAnim_start.setFillAfter(true);
					fullScreenPic.setVisibility(View.VISIBLE);
					fullScreenPic_out.setVisibility(View.VISIBLE);
					fullScreenPic.startAnimation(scaleAnim_start);
					//
					scaleAnim_exit = new ScaleAnimation(1f, 0f, 1f, 0f,
							Animation.RELATIVE_TO_PARENT, screen_x,
							Animation.RELATIVE_TO_PARENT, screen_y);
					scaleAnim_exit.setDuration(500);// 设置动画持续时间
				}
			});
			fullScreenPic.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// ScaleAnimation scaleAnim_exit = new ScaleAnimation(1f,
					// 0f,
					// 1f, 0f, Animation.RELATIVE_TO_PARENT, screen_x,
					// Animation.RELATIVE_TO_PARENT, screen_y);
					// scaleAnim_exit.setDuration(1000);// 设置动画持续时间
					scaleAnim_exit
							.setAnimationListener(new AnimationListener() {
								@Override
								public void onAnimationStart(Animation animation) {
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									fullScreenPic.setVisibility(View.GONE);
									fullScreenPic_out.setVisibility(View.GONE);
								}
							});
					fullScreenPic.startAnimation(scaleAnim_exit);
				}
			});

		}
		listView_comment = (ListView) findViewById(R.id.goodsComment);
		listView_comment_head = (TextView) findViewById(R.id.goodsComment_head);
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("详情", name, R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});
		introduce = (TextView) findViewById(R.id.introduce);
		introduce_head = (TextView) findViewById(R.id.introduce_head);
		img = (ImageView) findViewById(R.id.more_text);
		if (goodsDetail.getGoodsBrief().trim().equals("")) {
			introduce_head.setVisibility(View.GONE);
			img.setVisibility(View.GONE);
		} else {
			introduce.setText(goodsDetail.getGoodsBrief());
		}
		goodsName = (TextView) findViewById(R.id.goodsname);
		goodsName.setText(goodsDetail.getGoodsName());
		goodsImg = (ImageView) findViewById(R.id.goodsImg);
		// FileUtil.setImage(goodsImg,
		// InterfaceURL.IMGIP + goodsDetail.getGoodsImg(), loader,
		// R.drawable.logo);
		FileUtil.setImage(goodsImg, goodsDetail.getGoodsImg(), loader,
				R.drawable.load);
		goodsRating = (RatingBar) findViewById(R.id.goodsRating);
		goodsRating.setRating(goodsDetail.getExpectScore());
		goodsPrice = (TextView) findViewById(R.id.goodsPrice);
		goodsPrice.setText(goodsDetail.getGoodsPrice() + "");
		address = (TextView) findViewById(R.id.address);
		address.setText(goodsDetail.getAddressDetail());
		address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, RouteListActivity.class);
				intent.putExtra("goodsDetail", goodsDetail);
				startActivity(intent);
			}
		});
		telphone = (TextView) findViewById(R.id.telphone);
		telphone.setText(goodsDetail.getMobile());
		// bottombar
		bottombar = (BottomBar) findViewById(R.id.bottom);
		if (searchType == 0) {
			bottombar.setTabNum(tabNum);
			bottombar.setTabsName("到这里", "收藏", "分享", "点评", "周边");
			bottombar.setResourceId(R.drawable.destination,
					R.drawable.collection, R.drawable.share,
					R.drawable.comments, R.drawable.around);
			bottombar.setTabListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// System.out.println("到这里");
					Intent intent = new Intent(context, RouteListActivity.class);
					intent.putExtra("goodsDetail", goodsDetail);
					startActivity(intent);
				}

			}, new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// System.out.println("收藏" + application.getMemberID());
					if (application.getMemberID() <= 0) {
						Toast.makeText(context, "使用相关功能，请先登陆", 1).show();
						Intent intent = new Intent(Constants.LOGIN_ACTION);
						sendBroadcast(intent);
					} else {
						collectGoods();
					}

				}

			}, new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
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

			}, new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// System.out.println("点评");
					if (application.getMemberID() <= 0) {
						Toast.makeText(context, "使用相关功能，请先登陆", 1).show();
						Intent intent = new Intent(Constants.LOGIN_ACTION);
						sendBroadcast(intent);
					} else {
						Intent intent = new Intent(context, AddComment.class);
						intent.putExtra("goodsDetail", goodsDetail);
						startActivity(intent);
					}
				}

			}, new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// System.out.println("周边");
					Intent intent = new Intent(context, AroundActivity.class);
					intent.putExtra("goodsDetail", goodsDetail);
					startActivity(intent);
				}

			});
		} else if (searchType == 1) {

			bottombar.setTabNum(tabNum);
			bottombar.setTabsName("到这里", "分享", "周边");
			bottombar.setResourceId(R.drawable.destination, R.drawable.share,
					R.drawable.around);
			bottombar.setTabListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// System.out.println("到这里");
					Intent intent = new Intent(context, RouteListActivity.class);
					intent.putExtra("goodsDetail", goodsDetail);
					startActivity(intent);
				}

			}, new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
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

			}, new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// System.out.println("周边");
					Intent intent = new Intent(context, AroundActivity.class);
					intent.putExtra("goodsDetail", goodsDetail);
					startActivity(intent);
				}

			});

		}

	}

	// 收藏商品
	private void collectGoods() {
		api.collectProduct(type, itemID, MemberID, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				handler.sendEmptyMessage(COLLECT_FAILD);
			}

			@Override
			public void onError(TixaException e) {
				handler.sendEmptyMessage(COLLECT_FAILD);
			}

			@Override
			public void onComplete(String response) {
				if (StrUtil.isHttpException(response)) {
					handler.sendEmptyMessage(COLLECT_FAILD);

				} else {
					try {
						JSONObject jsonObject = new JSONObject(response);
						if (jsonObject.optString("buySellCollect").equals("-2")) {
							handler.sendEmptyMessage(COLLECT_EXIST);
						} else {
							handler.sendEmptyMessage(COLLECT_SUCCESS);
						}
					} catch (JSONException e) {
						handler.sendEmptyMessage(COLLECT_FAILD);
						e.printStackTrace();
					}

				}
			}
		});
	}

	private void getCommentData() {

		api.getBuysellCommentList(type, GoodorBuyInfoID, lastID, direct,
				number, new RequestListener() {

					@Override
					public void onIOException(IOException e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(TixaException e) {
						// TODO Auto-generated method stub

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

	@Override
	protected void onResume() {
		super.onResume();
		// 获取评论数据
		if (searchType == 0) {
			getCommentData();
		}

	}

	public void ExpandIntroduce(View view) {
		if (isExpand) {
			img.setImageResource(R.drawable.text_down);
			introduce.setMaxLines(4);
			isExpand = false;
		} else {
			img.setImageResource(R.drawable.text_up);
			introduce.setMaxLines(400);
			isExpand = true;
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		screen_x = event.getX() / dm.widthPixels;
		screen_y = (event.getY() - 67 * scale) / (dm.heightPixels - 67 * scale);
		return super.dispatchTouchEvent(event);
	}
}
