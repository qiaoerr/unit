/**
 * @Title: ProductListDazhongActivity.java
 * @Package com.tixa.industry.activity
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京天下互联科技有限公司
 * 
 * @author Comsys-Administrator
 * @date 2013-7-31 下午04:32:13
 * @version V1.0
 */

package com.tixa.industry.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.DianpingAdapter;
import com.tixa.industry.api.DianpingApi;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.Advert;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.GoodsFromDZ;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.BannerView;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.PushListView;
import com.tixa.industry.widget.PushListView.OnRefreshListener;
import com.tixa.industry.widget.TopBar;

/**
 * @ClassName: ProductListDazhongActivity
 * @Description: TODO 大众点评商品列表页
 * @author Comsys-Administrator
 * @date 2013-7-31 下午04:32:13
 * 
 */

public class ProductListDazhongActivity extends Fragment implements
		OnItemClickListener {

	private boolean isDestroy;
	private Activity context;
	private String latitude;
	private String longitude;
	private String category;
	private String keyword;
	private String format;// 返回数据格式，可选值为json或xml，如不传入，默认值为json
	private int radius;// 搜索半径，单位为米，最小值1，最大值5000，如不传入默认为1000
	private int sort;// 结果排序，1:默认，2:星级高优先，3:产品评价高优先，4:环境评价高优先，5:服务评价高优先，6:点评数量多优先，7:离传入经纬度坐标距离近优先
	private int page;// 页码，如不传入默认为1，即第一页
	private int limit;// 每页返回的商户结果条目数上限，最小值1，最大值20，如不传入默认为20
	private int has_coupon;// 根据是否有优惠券来筛选返回的商户，1:有，0:没有
	private int has_deal;// 根据是否有团购来筛选返回的商户，1:有，0:没有
	private View view;
	private PushListView listView;
	private TopBar topBar;
	private LoadView view_loading;
	private TextView dialogText;
	private IndustryApplication application;
	private IndexData indexData;
	private ArrayList<Advert> adList;
	private PageConfig config;
	private int pageStyle;
	private int pageStatus;
	private int listStyle;
	private BannerView banner;
	private TopBarUtil util;
	private ArrayList<GoodsFromDZ> myData;
	private DianpingAdapter adapter;
	private int rowNum = Data.DATA_NUM;
	private String appID;
	private final static String DIANPING_LIST = "goods_dianping_list.tx";
	private Map<String, String> map;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			listView.onRefreshComplete();
			if (isDestroy) {
				return;
			}
			switch (msg.what) {
			case Data.FULLDATA:
				view_loading.close();
				ArrayList<GoodsFromDZ> tempData = (ArrayList<GoodsFromDZ>) msg.obj;
				myData.clear();
				myData.addAll(tempData);
				adapter.notifyDataSetChanged();
				saveToLocal(myData, DIANPING_LIST);
				break;
			case Data.NODATA:
				view_loading.showNodataView();
				break;
			default:
				break;
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_product_list, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		category = getArguments().getString("category");
		keyword = getArguments().getString("keyword");
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		indexData = application.getMainData();
		adList = indexData.getSubAdList();
		PageConfigParser p = new PageConfigParser(context,
				"layout/SortLayout.xml");
		config = p.parser();
		pageStyle = config.getAd().getType();
		pageStatus = config.getAd().getShow();
		listStyle = config.getBlock().getShowType();
		map = new HashMap<String, String>();
		longitude = application.getmBdLocation().getLongitude() + "";
		latitude = application.getmBdLocation().getLatitude() + "";
		radius = 5000;
		sort = 7;
		page = 1;
		limit = 20;
		has_coupon = 1;
		has_deal = 1;
	}

	private void initView() {
		listView = (PushListView) view.findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		topBar = (TopBar) view.findViewById(R.id.topbar);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		dialogText = (TextView) view.findViewById(R.id.dialogText);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		util = new TopBarUtil(false, naviStyle, topBar, "产品分类",
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();

		myData = getFromLocal(DIANPING_LIST);

		if (myData == null) {
			myData = new ArrayList<GoodsFromDZ>();
			upData();
		} else {
			view_loading.close();
		}

		initBanner();

		adapter = new DianpingAdapter(context, myData, listStyle, rowNum);
		listView.setAdater(adapter, Constants.CACHE_DIR + appID + "/"
				+ category + keyword + "/" + DIANPING_LIST);

		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				upData();
			}

		});
	}

	private void upData() {
		new Thread() {
			public void run() {
				try {
					ArrayList<GoodsFromDZ> temp = new ArrayList<GoodsFromDZ>();
					map.put("latitude", latitude);
					map.put("longitude", longitude);
					map.put("category", category);
					map.put("keyword", keyword);
					map.put("format", "json");
					map.put("radius", radius + "");
					map.put("sort", sort + "");
					map.put("page", page + "");
					map.put("limit", limit + "");
					// map.put("has_coupon", has_coupon + "");
					// map.put("has_deal", has_deal + "");
					String response = DianpingApi.requestApi(
							InterfaceURL.find_businesses_by_coordinate,
							Constants.appkey_dianping,
							Constants.appSecret_dianping, map);
					L.d("http", response);
					JSONObject json = new JSONObject(response);
					int total_count = json.optInt("total_count");
					int count = json.optInt("count");
					if (count == 0) {
						handler.sendEmptyMessage(Data.NODATA);
						return;
					}
					JSONArray array = json.optJSONArray("businesses");
					for (int i = 0; i < array.length(); i++) {
						JSONObject tem = array.optJSONObject(i);
						GoodsFromDZ goods = new GoodsFromDZ(tem);
						if (total_count > count) {
							goods.setHasMore(true);
						} else {
							goods.setHasMore(false);
						}
						temp.add(goods);
						// myData.add(goods);
					}
					Message msg = new Message();
					msg.obj = temp;
					msg.what = Data.FULLDATA;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}

	private void initBanner() {
		if (pageStatus == ConfigData.SHOW_AD && adList.size() > 0) {// 显示广告
			banner = new BannerView(context, adList, pageStyle);
			banner.show();
			listView.addHeaderView(banner);
		}
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	// 缓存数据
	private void saveToLocal(ArrayList<GoodsFromDZ> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + category + keyword
					+ "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<GoodsFromDZ> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + category
				+ keyword + "/" + fileName;
		ArrayList<GoodsFromDZ> data = (ArrayList<GoodsFromDZ>) FileUtil
				.getFile(filePath);
		return data;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - listView.getHeaderViewsCount();
		Intent intent = new Intent(context, WebActivity.class);
		intent.putExtra("url", myData.get(position).getBusiness_url());
		startActivity(intent);

	}
}
