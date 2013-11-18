package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CollectionAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.modelCustom.Collection;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widgetCustom.LoadView;
import com.tixa.industry.widgetCustom.PushListView;
import com.tixa.industry.widgetCustom.PushListView.OnRefreshListener;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

public class MyCollectionActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {
	private TopBar topbar;
	private Context context;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private PushListView listView;
	protected ArrayList<Collection> dataList;
	private LoadView loadView;
	private String memberID;
	private final static String SORT_LIST = "collect_list.tx";
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NONETWORK:
				listView.onRefreshComplete();
				loadView.noNetWork(new OnClickListener() {
					@Override
					public void onClick(View v) {
						loadView.loading();
						getData();
					}
				});
				break;
			case Data.NODATA:
				listView.onRefreshComplete();
				loadView.showNodataView();
				break;
			case Data.FULLDATA:
				listView.onRefreshComplete();
				loadView.close();
				CollectionAdapter adapter = new CollectionAdapter(context,
						dataList);
				listView.setAdapter(adapter);
				break;
			case Data.SUCCESS: // 删除成功
				loadView.loading();
				getData();
				break;
			case Data.FAILED: // 删除失败
				loadView.close();
				T.shortT(context, "删除失败，请稍候再试");
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_collect);
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
		topbar.setShowConfig("收藏", R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});

		listView = (PushListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				getData();

			}
		});
		dataList = getFromLocal(SORT_LIST);
		if (dataList == null) {
			dataList = new ArrayList<Collection>();
		}
		if (dataList.size() <= 0) {
			getData();
		} else {
			handler.sendEmptyMessage(Data.FULLDATA);
		}

	}

	private void getData() {
		api.getCollectProductList(memberID, new RequestListener() {

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
						if (jsonObject.optString("BuySellCollectList").equals(
								"none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {
							JSONArray goodsArray = jsonObject
									.optJSONArray("BuySellCollectList");
							dataList.clear();
							for (int i = 0; i < goodsArray.length(); i++) {
								JSONObject json = goodsArray.optJSONObject(i);
								Collection collection = new Collection(json);
								dataList.add(collection);
							}
							handler.sendEmptyMessage(Data.FULLDATA);
							saveToLocal(dataList, SORT_LIST);
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
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		position = position - listView.getHeaderViewsCount();
		final long _id = dataList.get(position).getId();

		Builder builder = new Builder(context);
		builder.setMessage("确认取消收藏吗?").setTitle("友情提示")
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteCollect(_id);
					}
				}).setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	private void deleteCollect(long id) {
		loadView.loading();
		api.deleteCollect(id + "", new RequestListener() {
			@Override
			public void onIOException(IOException e) {

			}

			@Override
			public void onError(TixaException e) {
				handler.sendEmptyMessage(Data.FAILED);
			}

			@Override
			public void onComplete(String response) {
				try {
					JSONObject obj = new JSONObject(response);
					boolean result = obj.optBoolean("deleteBuySellCollect");
					if (result) {
						handler.sendEmptyMessage(Data.SUCCESS);
					} else {
						handler.sendEmptyMessage(Data.FAILED);
					}
				} catch (JSONException e) {
					handler.sendEmptyMessage(Data.FAILED);
					e.printStackTrace();
				}

			}
		});

	}

	// 缓存数据
	@SuppressWarnings("unused")
	private void saveToLocal(ArrayList<Collection> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + "collection" + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private ArrayList<Collection> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + "collection"
				+ "/" + fileName;
		ArrayList<Collection> data = (ArrayList<Collection>) FileUtil
				.getFile(filePath);
		return data;
	}
}
