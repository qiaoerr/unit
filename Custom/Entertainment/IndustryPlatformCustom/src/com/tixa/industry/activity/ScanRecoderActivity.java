package com.tixa.industry.activity;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.CollectAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.PushListView;
import com.tixa.industry.widget.PushListView.OnRefreshListener;
import com.tixa.industry.widget.TopBar;

public class ScanRecoderActivity extends Fragment implements
		OnItemClickListener {

	private View view;
	private FragmentActivity context;
	private IndustryApplication application;
	private String appID;
	private String MemberID;
	private HttpApi api;
	private PageConfig config;
	private TopBar topbar;
	private TopBarUtil util;
	private ListView listView;
	private ArrayList<Goods> skimRecoderCache;
	private int listStyle;
	private LoadView view_loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_collect_list, null);
		context = getActivity();
		getPageConfig();
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		MemberID = application.getMemberID() + "";
		api = new HttpApi(appID);
		skimRecoderCache = application.getSkimRecoder();
		initTopBar();
		initView();
		return view;
	}

	private void getPageConfig() {
		PageConfigParser p = new PageConfigParser(context,
				"layout/SupplyLayout.xml");
		config = p.parser();
		listStyle = config.getBlock().getShowType();
	}

	private void initTopBar() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(false, naviStyle, topbar, "浏览记录",
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();
	}

	private void initView() {
		listView = (ListView) view.findViewById(R.id.collect_list);
		view_loading = (LoadView) view.findViewById(R.id.loadView);
		listView.setDivider(null);
		listView.setOnItemClickListener(this);
		if(skimRecoderCache == null || skimRecoderCache.size()==0) {
			view_loading.showNodataView();
		}else{
			view_loading.close();
			CollectAdapter adapter = new CollectAdapter(context,skimRecoderCache,listStyle,2000);			
			listView.setAdapter(adapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		position = position - listView.getHeaderViewsCount();
		Intent intent = new Intent(context, SupplyDetailAct.class);
		intent.putExtra("goods", skimRecoderCache.get(position));
		startActivity(intent);

	}

}
