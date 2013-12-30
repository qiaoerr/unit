/**
  @Title: ProjectAdapter.java
  @Package com.star.general.adapter
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-30 下午06:22:04
  @version V1.0
 */

package com.star.general.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.star.baseFramework.util.AsyncImageLoader;
import com.star.baseFramework.util.FileUtil;
import com.star.general.R;
import com.star.general.model.Talent;

/**
 * @ClassName: ProjectAdapter
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-30 下午06:22:04
 * 
 */

public class TalentAdapter extends BaseAdapter {
	private ArrayList<Talent> datas;
	private Context context;
	private LayoutInflater inflater;

	public TalentAdapter(ArrayList<Talent> datas, Context context) {
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
		ViewHolder holder;
		Talent temp = datas.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.talent_item_layout, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.project_title = (TextView) convertView
					.findViewById(R.id.project_title);
			holder.project_subtitle = (TextView) convertView
					.findViewById(R.id.project_subtitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FileUtil.setImage(holder.img, temp.getImageUrl(),
				new AsyncImageLoader(), R.drawable.head_default_male);
		holder.project_title.setText(temp.getName());
		holder.project_subtitle.setText(temp.getJob());
		return convertView;
	}

	private static class ViewHolder {
		ImageView img;
		TextView project_title;
		TextView project_subtitle;
	}
}
