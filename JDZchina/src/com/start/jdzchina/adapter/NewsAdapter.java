package com.start.jdzchina.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.start.jdzchina.R;
import com.start.jdzchina.model.NewsDataModel;
import com.start.jdzchina.util.AsyncImageLoader;
import com.start.jdzchina.util.DateUtil;
import com.start.jdzchina.util.FileUtil;

public class NewsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NewsDataModel> dataLlist;
	private LayoutInflater inflater;

	public NewsAdapter(Context context, ArrayList<NewsDataModel> dataLlist) {
		super();
		this.context = context;
		this.dataLlist = dataLlist;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return dataLlist.size();
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
		NewsDataModel news = dataLlist.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.newslist_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.news_title);
			holder.createTime = (TextView) convertView
					.findViewById(R.id.create_time);
			holder.img = (ImageView) convertView.findViewById(R.id.product_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(news.getTitle());
		holder.createTime.setText(DateUtil.parseDate(new Date(news
				.getCreateTime())));
		FileUtil.setImage(holder.img, news.getImgUrl(), new AsyncImageLoader(),
				R.drawable.default_loading);
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView createTime;
		ImageView img;

	}
}
