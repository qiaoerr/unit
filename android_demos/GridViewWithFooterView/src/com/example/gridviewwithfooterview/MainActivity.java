package com.example.gridviewwithfooterview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import com.example.gridviewwithfooterview.DataLoader.OnCompletedListener;

public class MainActivity extends Activity implements OnCompletedListener,
		OnClickListener {
	private GridView gridview;
	private DataLoader loader;
	private int page = 1;
	public final static int PAGE_SIZE = 10; // 每次加载10个item
	private final static int COUNT = 215;// 最多加载215个，确定加载数。
	private ItemAdapter adapter;
	private List<String> list = new ArrayList<String>();
	private boolean isLoadFinished;
	private HashMap<String, String> loaderMap = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_gridview);
		gridview = (GridView) findViewById(R.id.gridview);

		initGridView();

		loaderMap.put("page", page + "");
		loaderMap.put("page_size", PAGE_SIZE + "");

		loader = new DataLoader(MainActivity.this);
		loader.setOnCompletedListerner(this);
		loader.startLoading(loaderMap);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.foot_view_layout:
			if (adapter != null
					&& adapter.getFooterView().getStatus() == FooterView.MORE) {
				loadMoreData();
			}
			break;
		case R.id.footview_button:
			loadMoreData();
			break;
		}

	}

	private void loadMoreData() {
		if (loader != null) {

			page = page + 1;
			loaderMap.put("page", page + "");
			if (adapter != null) {
				adapter.setFooterViewStatus(FooterView.LOADING);
			}

			loader.startLoading(loaderMap);
		}
	}

	private void initGridView() {
		adapter = new ItemAdapter(MainActivity.this, list);
		adapter.setOnFooterViewClickListener(MainActivity.this);
		gridview.setAdapter(adapter);
		gridview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == (view.getCount() - 1)
							&& !isLoadFinished
							&& adapter.getFooterView().getStatus() != FooterView.LOADING) {
						loadMoreData();

					}
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	@Override
	public void onCompletedSucceed(List<String> l) {

		// 在添加数据之前删除最后的伪造item
		if (adapter.isFooterViewEnable()) {
			list.remove(list.get(list.size() - 1));
		}

		// 分页加载
		if (l.size() < PAGE_SIZE || list.size() + l.size() == COUNT) {
			// 如果加载出来的数目小于指定条数，可视为已全部加载完成
			isLoadFinished = true;
			list.addAll(l);
			adapter.setFootreViewEnable(false);
			adapter.notifyDataSetChanged();
		} else {
			// 还有数据可加载。
			list.addAll(l);
			// 伪造一个空项来构造一个footerview;
			list.add(null);
			adapter.setFootreViewEnable(true);
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onCompletedFailed(String str) {
		Toast.makeText(MainActivity.this, "出错啦！", Toast.LENGTH_LONG).show();

	}

	@Override
	public void getCount(int count) {
		// TODO Auto-generated method stub

	}

}
