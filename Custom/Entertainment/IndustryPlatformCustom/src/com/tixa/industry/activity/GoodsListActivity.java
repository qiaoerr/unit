package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.GoodsListAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.model.TmallGoodsDetail;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.DateUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.util.Util;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.TopBar;

public class GoodsListActivity extends Fragment implements OnItemClickListener {
	private FragmentActivity context;
	private boolean isDestroy = false;
	private TopBar topbar;
	private ListView listView;
	private LoadView view_loading;
	private GoodsListAdapter adapter;
	private View view;
	private ArrayList<TmallGoodsDetail> goodslist = new ArrayList<TmallGoodsDetail>();
	private String modularName;
	private String typeID;
	private boolean isNav;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private PageConfig config;
	private TopBarUtil util;
	// private String brand;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (isDestroy) {
				return;
			}
			switch (msg.what) {
			case 0:
				// Toast.makeText(context, "网络故障,请检查您的网络连接", 0).show();
				view_loading.noNetWork(new OnClickListener() {

					@Override
					public void onClick(View v) {
						view_loading.loading();
						getDataFromWeb();
					}
				});
				break;
			case 1:
				// Toast.makeText(context, "数据获取成功", 0).show();
				view_loading.close();
				adapter = new GoodsListAdapter(context, goodslist);
				listView.setAdapter(adapter);
				break;
			case 2:
				Toast.makeText(context, "程序运行异常", 0).show();
				break;
			case 3:
				// Toast.makeText(context, "没有数据", 0).show();
				view_loading.showNodataView();
				break;

			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.goods_list_layout, null);
		initData();
		initView();
		return view;
	}

	private void initView() {
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		listView = (ListView) view.findViewById(R.id.mylistview);
		listView.setOnItemClickListener(this);
		topbar = (TopBar) view.findViewById(R.id.topbar);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		util = new TopBarUtil(isNav, naviStyle, topbar, "商品列表",
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();
		getDataFromWeb();

	}

	private void getDataFromWeb() {
		final String url = InterfaceURL.TAOBAO;
		if (getArguments().getString("brand") != null) {
			String brand = getArguments().getString("brand");
			String start = "0";
			String method = "tmall.items.discount.search";
			String app_key = Constants.appkey;
			String format = "json";
			String sign_method = "md5";
			String timestamp = DateUtil.getFomatDate(new Date());
			String v = "2.0";
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			treeMap.put("brand", brand);
			treeMap.put("start", start);
			treeMap.put("method", method);
			treeMap.put("app_key", app_key);
			treeMap.put("format", format);
			treeMap.put("sign_method", sign_method);
			treeMap.put("timestamp", timestamp);
			treeMap.put("v", v);
			String sign = Util.md5Signature(treeMap, Constants.appSecret);
			api.getGoodsList_Brand(url, brand, start, method, app_key, format,
					sign_method, timestamp, v, sign, new RequestListener() {

						@Override
						public void onIOException(IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(TixaException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onComplete(String result) {
							try {
								if (StrUtil.isHttpException(result)) {
									handler.sendEmptyMessage(0);
								}
								JSONObject object;

								object = new JSONObject(result).optJSONObject(
										"tmall_items_search_response")
										.optJSONObject("item_list");

								JSONArray array = object
										.optJSONArray("tmall_search_item");
								for (int i = 0; i < array.length(); i++) {
									TmallGoodsDetail goodsDetail = new TmallGoodsDetail(
											array.getJSONObject(i));
									goodslist.add(goodsDetail);
								}
								handler.sendEmptyMessage(1);
							} catch (Exception e) {
								handler.sendEmptyMessage(2);
								e.printStackTrace();
							}
						}
					});
		}
		if (getArguments().getString("cat") != null) {
			String cat = getArguments().getString("cat").trim();
			String start = "0";
			String method = "tmall.temai.items.search";
			String app_key = Constants.appkey;
			String format = "json";
			String sign_method = "md5";
			String timestamp = DateUtil.getFomatDate(new Date());
			String v = "2.0";
			TreeMap<String, String> treeMap = new TreeMap<String, String>();
			treeMap.put("app_key", app_key);
			treeMap.put("cat", cat);
			treeMap.put("format", format);
			treeMap.put("method", method);
			treeMap.put("start", start);
			treeMap.put("sign_method", sign_method);
			treeMap.put("timestamp", timestamp);
			treeMap.put("v", v);
			String sign = Util.md5Signature(treeMap, Constants.appSecret);
			L.e("cat=" + cat);
			L.e("start=" + start);
			L.e("method=" + method);
			L.e("app_key=" + app_key);
			L.e("format=" + format);
			L.e("sign_method=" + sign_method);
			L.e("timestamp=" + timestamp);
			L.e("v=" + v);
			L.e("sign=" + sign);
			api.getGoodsList_TaoBao(url, cat, start, method, app_key, format,
					sign_method, timestamp, v, sign, new RequestListener() {

						@Override
						public void onIOException(IOException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(TixaException e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onComplete(String result) {

							L.e("result =" + result);

							try {
								if (StrUtil.isHttpException(result)) {
									handler.sendEmptyMessage(0);
								}
								JSONObject object = new JSONObject(result)
										.optJSONObject(
												"tmall_temai_items_search_response")
										.optJSONObject("item_list");
								if (object == null) {
									handler.sendEmptyMessage(3);
									return;
								}
								JSONArray array = object
										.optJSONArray("tmall_search_tm_item");
								for (int i = 0; i < array.length(); i++) {
									TmallGoodsDetail goodsDetail = new TmallGoodsDetail(
											array.getJSONObject(i));
									goodslist.add(goodsDetail);
								}
								handler.sendEmptyMessage(1);
							} catch (Exception e) {
								handler.sendEmptyMessage(3);// to-modify
								e.printStackTrace();
							}
						}
					});
		}
	}

	private void initData() {
		context = getActivity();
		modularName = getArguments().getString("modularName");
		typeID = getArguments().getString("typeID");
		isNav = getArguments().getBoolean("isNav");
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		PageConfigParser p = new PageConfigParser(context,
				"layout/GoodsListLayout.xml");
		config = p.parser();
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - listView.getHeaderViewsCount();
		Intent intent = new Intent(context, WebActivity.class);
		if (getArguments().getString("brand") != null) {
			intent.putExtra("url", goodslist.get(position).getUrl());
		} else {
			intent.putExtra("url", goodslist.get(position).getDetail_url());
		}
		startActivity(intent);

	}

}
