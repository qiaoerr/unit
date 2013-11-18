package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.TmallGoodsDetail;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class GoodsListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<TmallGoodsDetail> mydata;
	private AsyncImageLoader loader;

	public GoodsListAdapter(Context context, ArrayList<TmallGoodsDetail> mydata) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.mydata = mydata;
		loader = new AsyncImageLoader();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mydata.size();
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
			convertView = inflater.inflate(R.layout.goods_list_iterm, null);
			holder.img = (ImageView) convertView.findViewById(R.id.goods_img);
			holder.nick = (TextView) convertView.findViewById(R.id.nick);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.price_with_rate = (TextView) convertView
					.findViewById(R.id.price_with_rate);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TmallGoodsDetail tmallGoodsDetail = mydata.get(position);
		if (tmallGoodsDetail.getPic_path() != null
				&& !tmallGoodsDetail.getPic_path().equals("")) {
			FileUtil.setImage(holder.img, tmallGoodsDetail.getPic_path(),
					loader, R.drawable.logo1);
			holder.nick.setText(tmallGoodsDetail.getNick());
			holder.title.setText(tmallGoodsDetail.getTitle());
			holder.price_with_rate.setText(tmallGoodsDetail
					.getPrice_with_rate());
			holder.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.price.setText(tmallGoodsDetail.getPrice());
		} else {
			FileUtil.setImage(holder.img, tmallGoodsDetail.getPic_url(),
					loader, R.drawable.logo1);
			holder.nick.setText(tmallGoodsDetail.getBrand_name());
			holder.title.setText(tmallGoodsDetail.getTitle());
			holder.price_with_rate.setText(tmallGoodsDetail
					.getPromotion_price());
			holder.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.price.setText(tmallGoodsDetail.getPrice());
		}

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView nick;
		TextView title;
		TextView price_with_rate;
		TextView price;
	}
}
