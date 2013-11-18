package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.modelCustom.ProvideAndNeed;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class ProviderNeedListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<ProvideAndNeed> dataList;
	private AsyncImageLoader loader;
	private int type;

	public ProviderNeedListAdapter(Context context,
			ArrayList<ProvideAndNeed> dataList) {
		super();
		this.context = context;
		this.dataList = dataList;
		this.inflater = LayoutInflater.from(context);
		this.loader = new AsyncImageLoader();
		this.type = dataList.get(0).getType();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
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
			convertView = inflater.inflate(R.layout.layout_provide_need, null);
			holder.goodsName = (TextView) convertView
					.findViewById(R.id.goodsName);
			holder.goodsPrice = (TextView) convertView
					.findViewById(R.id.goodsPrice);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.goodsImg = (ImageView) convertView
					.findViewById(R.id.goodsImg);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.goodsRating);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (type == 0) {
			holder.goodsName.setText(dataList.get(position).getGoodsName());
			holder.goodsPrice.setText("人均:￥"
					+ dataList.get(position).getGoodsPrice() + "");
			holder.address.setText(dataList.get(position).getAddressDetail());
			holder.ratingBar.setRating(dataList.get(position).getExpectScore());
			// FileUtil.setImage(holder.goodsImg,
			// InterfaceURL.IMGIP + dataList.get(position).getGoodsImg(),
			// loader, R.drawable.load);
			FileUtil.setImage(holder.goodsImg, dataList.get(position)
					.getGoodsImg(), loader, R.drawable.load);
		} else if (type == 1) {
			holder.goodsName.setText(dataList.get(position).getName());
			holder.goodsPrice.setVisibility(View.GONE);
			holder.address.setText(dataList.get(position).getAddress());
			holder.ratingBar.setVisibility(View.GONE);
			FileUtil.setImage(holder.goodsImg, dataList.get(position)
					.getGoodsImg_daz(), loader, R.drawable.load);
		}
		return convertView;
	}

	class ViewHolder {
		TextView goodsName;
		TextView goodsPrice;
		TextView address;
		ImageView goodsImg;
		RatingBar ratingBar;
	}
}
