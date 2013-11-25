package com.start.jdzchina.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.start.jdzchina.R;
import com.start.jdzchina.adapter.NewsAdapter;
import com.start.jdzchina.model.NewsDataModel;

public class NewsListFragment extends Fragment implements OnItemClickListener {
	private Context context;
	private View view;
	private ListView listView;
	private ArrayList<NewsDataModel> newsList;
	private NewsAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.aftersale_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		// 本地测试数据
		newsList = new ArrayList<NewsDataModel>();
		for (int i = 0; i < 8; i++) {
			NewsDataModel newsDataModel = new NewsDataModel();
			newsDataModel.setTitle("鹅鹅鹅曲项向天歌白毛浮绿水红掌拨清波");
			newsDataModel
					.setImgUrl("http://b.hiphotos.baidu.com/pic/w%3D230/sign=1198de96faf2b211e42e824dfa816511/a6efce1b9d16fdfaaef17e0bb58f8c5495ee7bb1.jpg");
			newsDataModel.setCreateTime(System.currentTimeMillis());
			// int m = new Random().nextInt(4);
			// if (m == 0) {
			// newsDataModel.setImgUrls(new String[] {});
			// } else if (m == 1) {
			// newsDataModel.setImgUrls(new String[] { "" });
			// } else if (m == 2) {
			// newsDataModel.setImgUrls(new String[] { "", "" });
			// } else {
			// newsDataModel.setImgUrls(new String[] { "", "", "" });
			// }
			newsList.add(newsDataModel);
		}

	}

	private void initView() {
		listView = (ListView) view.findViewById(R.id.news_list);
		listView.setOnItemClickListener(this);
		adapter = new NewsAdapter(context, newsList);
		listView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment fragment = new NewsDtailFragment();
		transaction.addToBackStack(null);
		transaction.add(R.id.container, fragment);
		transaction.commitAllowingStateLoss();
	}

}
