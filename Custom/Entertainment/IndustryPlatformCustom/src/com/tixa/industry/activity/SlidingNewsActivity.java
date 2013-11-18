package com.tixa.industry.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.adapter.MyFragmentPagerAdapter;
import com.tixa.industry.adapter.NewsCommonAdapter;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Article;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RandomUtils;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.LoadView;
import com.tixa.industry.widget.TopBar;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SlidingNewsActivity extends Fragment {
	private View view;
	private  ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private MyFragmentPagerAdapter padapter;
	private FragmentActivity mContext;
	
	private int dataNum = 70; //一次取70条数据
	private String typeID;
	private ArrayList<Article> myData;
	private IndustryApplication application;
	private PageConfig config;
	private HttpApi api;
	private ViewGroup group;
	private String appID;
	private String titleName;
	private String modularName;
	private LoadView view_loading;
	private final static String NEWS_LIST = "news_list.tx";
	private TopBarUtil util;
	private boolean isNav;
	private IndexData indexData;
	private TopBar topbar;
	private int lastID;
	private int firstID;
	private ArrayList<ArrayList<Article>> pageData;
	private TextView sumnum;
	private TextView num;
	private boolean isHttpRunning = false;
	private boolean isCanLoadNew = false;
	
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if(!isHttpRunning) {
				return;
			}
			
			ArrayList<Article> tempData = (ArrayList<Article>) msg.obj;
			switch (msg.what) {
			case Data.NODATA:
				isHttpRunning = false;
				view_loading.showNodataView();
				break;
				
			case Data.FULLDATA:	
				if(tempData != null && tempData.size()>0) {
					
					myData = tempData;
					if(padapter == null) {
						pageData = formatData(myData);
						for(int i=0;i< pageData.size();i++) {
							TableNewsFragment fragment = new TableNewsFragment();
							Bundle bundle = new Bundle();
							bundle.putSerializable("data", pageData.get(i));
							bundle.putInt("color", RandomUtils.getRandom(5));
							fragment.setArguments(bundle);
							fragmentsList.add(fragment);				
						}
						
						sumnum.setText(fragmentsList.size()+"");
						num.setText(1+"");						
						padapter = new MyFragmentPagerAdapter(getFragmentManager(),fragmentsList);			
						mPager.setAdapter(padapter);
						mPager.setCurrentItem(0);
						view_loading.close();
						isHttpRunning = false;
					}
				}
				break;
			
			case Data.NONETWORK:
				isHttpRunning = false;
				view_loading.noNetWork(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						view_loading.loading();						
						if(isCanLoadNew) {
							getHistory();
						}else{
							upData();
						}
					}
				});
				
				
				break;
			case Data.MOREDATA:			
				if(tempData == null || tempData.size()==0) {
					isHttpRunning = false;
					util.showProgressBar(false);
					return;					
				}
				if(myData != null && myData.size()>0) {
					myData.addAll(tempData);					
				}else{
					myData = tempData;
				}
				
				pageData = formatData(tempData);
				for(int i=0;i< pageData.size();i++) {
					TableNewsFragment fragment = new TableNewsFragment();
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", pageData.get(i));
					bundle.putInt("color", RandomUtils.getRandom(5));
					fragment.setArguments(bundle);
					fragmentsList.add(fragment);				
				}

				sumnum.setText(fragmentsList.size()+"");				
				padapter.setData(fragmentsList);

				padapter.notifyDataSetChanged();
				isHttpRunning = false;
				isCanLoadNew = false;
				util.showProgressBar(false);
				break;
				
			default:
				break;
			}
			
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = inflater.inflate(R.layout.common_scollview_viewpager_layout, null);		
		initData();
		initTopbar();
		initView();
		return view;
	}
	
	private void initData() {
		mContext = getActivity();
		isNav = getArguments().getBoolean("isNav");
		typeID = getArguments().getString("typeID");
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		modularName = getArguments().getString("modularName");
		api = new HttpApi(appID);
		indexData = application.getMainData();
		PageConfigParser p = new PageConfigParser(mContext,
				"layout/NewsLayout.xml");
		config = p.parser();
		fragmentsList = new ArrayList<Fragment>();
		//myData = getFromLocal(NEWS_LIST);
	}
	
	private void initTopbar() {
		topbar = (TopBar) view.findViewById(R.id.topbar);
		if (StrUtil.isEmpty(modularName)) {
			titleName = "资讯";
		} else {
			titleName = modularName;
		}
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), mContext, application.getTemplateId(),
				false, navi);
		util.showConfig();
	}
	
	private void initView() {
		view_loading = (LoadView) view.findViewById(R.id.loadView);	
		mPager = (ViewPager) view.findViewById(R.id.container);
		sumnum = (TextView) view.findViewById(R.id.sumnum);
		num = (TextView) view.findViewById(R.id.num);
		
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				num.setText((arg0+1)+"");
				if(arg0 == padapter.getCount()-1) {
					if(myData.size() % dataNum == 0) { //当数据是所有分页数据的倍数时加载新数据
						L.e("----------------应该加载新数据-------------------");
						isCanLoadNew = true;
						getHistory();
					}					
				}else{
					isCanLoadNew = false;
				}	
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if(arg0 == 0) {
					if(isCanLoadNew && !isHttpRunning) {
						L.e("----------------onPageScrolled加载新数据-------------------");
						getHistory();
					}
				}
			}
		});

		if (myData == null) {
			myData = new ArrayList<Article>();
		} else {
			view_loading.close();
			if(padapter == null) {
				pageData = formatData(myData);
				for(int i=0;i< pageData.size();i++) {
					TableNewsFragment fragment = new TableNewsFragment();
					Bundle bundle = new Bundle();
					bundle.putSerializable("data", pageData.get(i));
					fragment.setArguments(bundle);
					fragmentsList.add(fragment);				
				}
				padapter = new MyFragmentPagerAdapter(getFragmentManager(),fragmentsList);			
				mPager.setAdapter(padapter);
				mPager.setCurrentItem(0);
			}
		}		
		if (myData == null || myData.size() == 0) {
			upData();
		}
		
	}





	public ArrayList<ArrayList<Article>> formatData(ArrayList<Article> arts) {
		
		ArrayList<ArrayList<Article>> result = new ArrayList<ArrayList<Article>>();
		if(arts == null || arts.size() == 0) {
			return result;
		}
		int j = 0;
		ArrayList<Article> art = null;
		for(int i = 0;i<arts.size();i++) {
			if(art == null) {
				art = new ArrayList<Article>();
			}
			Article a = arts.get(i);
			art.add(a);
			j ++ ;
			if(j == 7 || arts.size() == i+1) {
				result.add(art);
				art = null;
				j = 0;
			}
		}	
		return result;
	}	
	
/*	private ArrayList<Article> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/" + typeID + "/"
				+ fileName;
		ArrayList<Article> data = (ArrayList<Article>) FileUtil
				.getFile(filePath);
		return data;
	}

	private void saveToLocal(ArrayList<Article> data, String fileName) {
		try {
			String dic = Constants.CACHE_DIR + appID + "/" + typeID + "/";
			FileUtil.saveFile(dic, fileName, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	private void upData() {
		isHttpRunning = true;
		
		if (myData != null && myData.size() > 0) {
			lastID = (int) myData.get(myData.size() - 1).getId();
			firstID = (int) myData.get(0).getId();
		}
		api.getArtices(dataNum, -1, firstID, typeID, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				T.shortT(mContext, "未知错误:" + e.getMessage());
			}

			@Override
			public void onError(TixaException e) {
				L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
				handler.sendEmptyMessage(Data.NONETWORK);
			}

			@Override
			public void onComplete(String response) {
				ArrayList<Article> tempData = new ArrayList<Article>();
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("articleList")) {
							res = json.optString("articleList");
						}
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.NODATA);
						} else {

							JSONArray cjar = json.optJSONArray("articleList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								Article info = new Article(temp);
								tempData.add(info);
							}
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.FULLDATA;
							handler.sendMessage(msg);
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
		
		isHttpRunning = true;
		util.showProgressBar(true);
		if (myData != null && myData.size() > 0) {
			lastID = (int) myData.get(myData.size() - 1).getId();
			firstID = (int) myData.get(0).getId();
		}

		api.getArtices(dataNum, 1, lastID, typeID, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				T.shortT(mContext, "未知错误:" + e.getMessage());
			}

			@Override
			public void onError(TixaException e) {
				L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
				handler.sendEmptyMessage(Data.NONETWORK);
			}

			@Override
			public void onComplete(String response) {
				ArrayList<Article> tempData = new ArrayList<Article>();

				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(Data.NONETWORK);
					} else {
						JSONObject json = new JSONObject(response);
						String res = "";
						if (json.has("articleList")) {
							res = json.optString("articleList");
						}
						if (res.equals("none")) {
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						} else {
							JSONArray cjar = json.optJSONArray("articleList");
							for (int i = 0; i < cjar.length(); i++) {
								JSONObject temp = cjar.optJSONObject(i);
								Article info = new Article(temp);
								tempData.add(info);
							}
							Message msg = new Message();
							msg.obj = tempData;
							msg.what = Data.MOREDATA;
							handler.sendMessage(msg);
						}

					}
				} catch (Exception e) {
					L.e(e.toString());
					handler.sendEmptyMessage(Data.NONETWORK);
				}

			}
		});
	}

	@Override
	public void onDestroy() {
		isHttpRunning = false;
		super.onDestroy();
	}
	
}
