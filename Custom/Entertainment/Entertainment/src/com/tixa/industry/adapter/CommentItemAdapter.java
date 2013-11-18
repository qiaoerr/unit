package com.tixa.industry.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.modelCustom.Comment;
import com.tixa.industry.util.DateUtil;

/**
 * @author administrator
 * @version
 * 
 */
public class CommentItemAdapter extends BaseAdapter {
	private ArrayList<Comment> dataList;
	private Context context;
	private LayoutInflater inflater;

	public CommentItemAdapter(ArrayList<Comment> dataList, Context context) {
		super();
		this.dataList = dataList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_comment_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.userName);
			holder.time = (TextView) convertView
					.findViewById(R.id.comment_time);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.goodsRating);
			holder.comment = (TextView) convertView
					.findViewById(R.id.comment_detail);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(dataList.get(position).getName());
		Date date = new Date(dataList.get(position).getCreate_Time());
		holder.time.setText(DateUtil.parseDateForDay(date));
		holder.comment.setText(dataList.get(position).getGoods_Comment());
		holder.ratingBar.setRating(dataList.get(position).getScore());
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView time;
		TextView comment;
		RatingBar ratingBar;
	}
}
