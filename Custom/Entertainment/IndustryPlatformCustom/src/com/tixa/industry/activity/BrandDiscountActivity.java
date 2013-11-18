package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.MySimpleAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.model.TmallBrand_Cat;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.DateUtil;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.util.Util;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.TopBar;

public class BrandDiscountActivity extends Fragment implements
		OnItemClickListener {

	private boolean isDestroy = false;
	private FragmentActivity context;
	private TopBar topbar;
	private ListView listView;
	private View view;
	private LoadView view_loading;
	private String modularName;
	private String typeID;
	private boolean isNav;
	private IndustryApplication application;
	private String appID;
	private HttpApi api;
	private PageConfig config;
	private int pageStyle;
	private int pageStatus;
	private TopBarUtil util;
	private ArrayList<TmallBrand_Cat> brands = new ArrayList<TmallBrand_Cat>();

	Handler handler = new Handler() {

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
				MySimpleAdapter adapter = new MySimpleAdapter(context, brands);
				listView.setAdapter(adapter);
				break;
			case 2:
				view_loading.showNodataView();
				// Toast.makeText(context, "程序运行异常", 0).show();
				break;

			}
		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.discount_layout, null);
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
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();
		getDataFromWeb();

	}

	private void getDataFromWeb() {

		// view_loading.close();
		String url = InterfaceURL.TAOBAO;
		String cat = "50100982";
		String method = "tmall.items.discount.search";
		String app_key = Constants.appkey;
		String format = "json";
		String sign_method = "md5";
		String timestamp = DateUtil.getFomatDate(new Date());
		String v = "2.0";
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("cat", cat);
		treeMap.put("method", method);
		treeMap.put("app_key", app_key);
		treeMap.put("format", format);
		treeMap.put("sign_method", sign_method);
		treeMap.put("timestamp", timestamp);
		treeMap.put("v", v);
		String sign = Util.md5Signature(treeMap, Constants.appSecret);
		api.getBrandDiscount(url, cat, method, app_key, format, sign_method,
				timestamp, v, sign, new RequestListener() {

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
									.optJSONObject("brand_list");

							JSONArray array = object
									.optJSONArray("tmall_brand");
							for (int i = 0; i < array.length(); i++) {
								TmallBrand_Cat tmallBrand = new TmallBrand_Cat(
										array.getJSONObject(i),
										Constants.TYEP_BRAND);
								brands.add(tmallBrand);
							}
							handler.sendEmptyMessage(1);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});

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
				"layout/BrandDiscountLayout.xml");
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
		Bundle args = new Bundle();
		args.putString("brand", brands.get(position).getid() + "");
		Fragment fragment = new GoodsListActivity();
		fragment.setArguments(args);
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(R.id.fragment, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
