package com.tixa.industry.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.modelCustom.MyComment;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.DateUtil;
import com.tixa.industry.util.FileUtil;

public class MycommentAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<MyComment> dataList;
	private LayoutInflater inflater;
	private AsyncImageLoader loader;

	public MycommentAdapter(Context context, ArrayList<MyComment> dataList) {
		super();
		this.context = context;
		this.dataList = dataList;
		this.inflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.layout_mycomment_list, null);
			holder.goodsImg = (ImageView) convertView
					.findViewById(R.id.goodImg);
			holder.goodsName = (TextView) convertView
					.findViewById(R.id.goodsName);
			holder.goodsPrice = (TextView) convertView
					.findViewById(R.id.goodsPrice);
			holder.goodsBrief = (TextView) convertView
					.findViewById(R.id.goodsBrief);
			holder.time = (TextView) convertView
					.findViewById(R.id.comment_time);
			holder.score = (RatingBar) convertView
					.findViewById(R.id.goodsRating);
			holder.comment = (TextView) convertView
					.findViewById(R.id.comment_detail);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyComment myComment = dataList.get(position);
		FileUtil.setImage(holder.goodsImg,
				InterfaceURL.IMGIP + myComment.getGoodsImg(), loader,
				R.drawable.logo);
		holder.goodsName.setText(myComment.getGoodsName());
		holder.goodsPrice.setText("商品价格:￥  " + myComment.getGoodsPrice());
		holder.goodsBrief.setText(myComment.getGoodsBrief());
		String temp = DateUtil.parseDate(new Date(myComment.getCreateTimeL()));
		holder.time.setText(temp);
		holder.score.setRating(myComment.getScore());
		holder.comment.setText(myComment.getGoods_Comment());
		return convertView;
	}

	class ViewHolder {
		ImageView goodsImg;
		TextView goodsName;
		TextView goodsPrice;
		TextView goodsBrief;
		TextView time;
		RatingBar score;
		TextView comment;
	}
}
