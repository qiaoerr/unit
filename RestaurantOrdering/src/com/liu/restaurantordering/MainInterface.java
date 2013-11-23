package com.liu.restaurantordering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainInterface extends FragmentActivity {
	private FrameLayout frameLayout = null;
	private ImageButton iButton = null;
	private LayoutInflater inflater = null;
	private Dialog mDialog = null;
	private ParseObject restaurant = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 加载Parse特性
		Parse.initialize(this, "Kkh2kigwgxrrzaeNpizAU8tGPYXpCAkdup3Aa2aC",
				"BwGMGDTLNoe3M0VVNfgiohCBsrtyBmF6x14O6ygC");
		setContentView(R.layout.activity_main_interface);
		frameLayout = (FrameLayout) findViewById(R.id.mainframe);
		iButton = (ImageButton) findViewById(R.id.btn);
		iButton.setOnClickListener(new MyListener());
		// 填充开始logo画面
		inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.start, frameLayout, true);

	}

	class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			// 调用语音识别

			RecognizerDialog isrDialog = new RecognizerDialog(
					MainInterface.this, "appid=50e3ee9c");
			isrDialog.setEngine("sms", null, null);
			isrDialog.setListener(recognizeListener);
			isrDialog.show();

			// 不调用语音识别

			// String input = "绿意";
			// String input = "北京饭店";
			// queryFromParse(input);
			// showWaitingAnim();

			// 不连paarse

			// Bundle data = new Bundle();
			// data.putInt("restaurantId", 1);
			// data.putBoolean("isRestaurant", true);
			// data.putInt("bigImageID", R.drawable.two);
			// data.putString("likeNum", 55 + "");
			// data.putString("northwest", "name");
			// data.putString("southwest", "address");
			// data.putString("southeast", " 浏览：" + 2226);
			// data.putString("comment", "info");
			// ResultFragment rf = new ResultFragment();
			// rf.setArguments(data);
			// FragmentTransaction ft = getSupportFragmentManager()
			// .beginTransaction();
			// ft.replace(R.id.mainframe, rf);
			// ft.addToBackStack(null);
			// ft.commit();
		}
	}

	RecognizerDialogListener recognizeListener = new RecognizerDialogListener() {
		// 识别结果回调接口
		public void onResults(ArrayList<RecognizerResult> results,
				boolean isLast) {
			String input = "";
			// 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加.
			for (int i = 0; i < results.size(); i++) {
				input += results.get(i).text;
			}
			// System.out.println(text);
			if (input.equals("。")) {
				return;
			}
			queryFromParse(input);
			showWaitingAnim();

		}

		// 会话结束回调接口.
		public void onEnd(SpeechError error) {
			// error为null表示会话成功，可在此处理text结果，error不为null，表示发生错误，对话框停留
			// 在错误页面
		}
	};

	private void queryFromParse(String input) {
		final Bundle data = new Bundle();
		// 从parse中查询数据
		if (input.contains("饭店")) {
			ParseQuery query = new ParseQuery("Restaurant");
			query.whereEqualTo("name", input);
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> restaurants, ParseException e) {
					if (restaurants.size() == 0) {
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(),
								WebBrowser.class);
						startActivity(intent);
						Log.i("System.out", "没有相关内容");
						mDialog.dismiss();
						return;
					}
					Iterator<ParseObject> iterator = restaurants.iterator();
					while (iterator.hasNext()) {
						restaurant = (ParseObject) iterator.next();
						ParseFile parseFile = (ParseFile) restaurant
								.get("picture");
						parseFile.getDataInBackground(new GetDataCallback() {

							@Override
							public void done(byte[] bytes, ParseException e) {

								data.putInt("restaurantId",
										restaurant.getInt("restaurantId"));
								data.putBoolean("isRestaurant", true);
								data.putByteArray("bitmap", bytes);
								data.putString("likeNum",
										restaurant.getInt("likeNum") + "");
								data.putString("northwest",
										restaurant.getString("name"));
								data.putString("southwest",
										restaurant.getString("address"));
								data.putString("southeast",
										" 浏览：" + restaurant.getInt("scanNum"));
								// 浏览次数加1
								restaurant.increment("scanNum");
								restaurant.saveInBackground();
								data.putString("comment",
										restaurant.getString("info"));
								// 将fragment替换到R.id.mainframe
								ResultFragment rf = new ResultFragment();
								rf.setArguments(data);
								FragmentTransaction ft = getSupportFragmentManager()
										.beginTransaction();
								ft.replace(R.id.mainframe, rf);
								ft.addToBackStack(null);
								ft.commit();
								mDialog.dismiss();

							}
						});
					}
				}
			});
		} else {
			ParseQuery query = new ParseQuery("Foods");
			query.whereEqualTo("name", input);
			query.findInBackground(new FindCallback() {

				@Override
				public void done(List<ParseObject> foods, ParseException e) {
					if (foods.size() == 0) {
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(),
								WebBrowser.class);
						startActivity(intent);
						Log.i("System.out", "没有相关内容");
						mDialog.dismiss();
						return;
					}
					Iterator<ParseObject> iterator = foods.iterator();
					while (iterator.hasNext()) {
						final ParseObject food = (ParseObject) iterator.next();
						final int restaurantId = food.getInt("restaurantId");
						final ParseQuery query = new ParseQuery("Restaurant");
						query.whereEqualTo("restaurantId", restaurantId);
						// 获取图片
						ParseFile parseFile = (ParseFile) food.get("picture");
						parseFile.getDataInBackground(new GetDataCallback() {

							@Override
							public void done(final byte[] bytes,
									ParseException e) {
								query.findInBackground(new FindCallback() {

									@Override
									public void done(
											List<ParseObject> restaurants,
											ParseException e) {
										ParseObject restaurant = restaurants
												.get(0);
										data.putInt("restaurantId",
												restaurantId);
										data.putBoolean("isRestaurant", false);
										data.putByteArray("bitmap", bytes);
										data.putString("likeNum",
												food.getInt("likeNum") + "");
										data.putString("northwest",
												food.getString("name"));
										data.putString("southwest",
												restaurant.getString("name"));
										data.putString("northeast",
												food.getInt("price") + "￥");
										data.putString("southeast", " 浏览："
												+ food.getInt("scanNum"));
										food.increment("scanNum");
										food.saveInBackground();
										data.putString("address",
												restaurant.getString("address"));
										data.putString("comment",
												food.getString("info"));
										ResultFragment rf = new ResultFragment();
										rf.setArguments(data);
										FragmentTransaction ft = getSupportFragmentManager()
												.beginTransaction();
										ft.replace(R.id.mainframe, rf);
										ft.addToBackStack(null);
										ft.commit();
										mDialog.dismiss();
									}
								});
							}
						});
					}
				}
			});
		}
	}

	// 显示等待动画
	private void showWaitingAnim() {
		mDialog = new AlertDialog.Builder(MainInterface.this).create();
		mDialog.show();
		// 注意此处要放在show之后 否则会报异常
		mDialog.setContentView(R.layout.loading_process_dialog_icon);
	}

}
