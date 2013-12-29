/**
  @Title: NewsItemAdapter.java
  @Package com.star.general.adapter
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-29 下午04:07:24
  @version V1.0
 */

package com.star.general.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.star.general.R;
import com.star.general.model.News;

/**
 * @ClassName: NewsItemAdapter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-29 下午04:07:24
 * 
 */

public class NewsItemAdapter extends BaseAdapter {
	private ArrayList<News> datas;
	private Context context;
	private LayoutInflater inflater;

	public NewsItemAdapter(ArrayList<News> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.news_item_layout, null);
		TextView news_tile = (TextView) convertView
				.findViewById(R.id.news_title);
		news_tile.setText(datas.get(position).getTitle());
		return convertView;
	}

}
