package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.modelCustom.Collection;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;

public class CollectionAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Collection> dataList;

	public CollectionAdapter(Context context, ArrayList<Collection> dataList) {
		super();
		this.context = context;
		this.dataList = dataList;
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
			convertView = inflater.inflate(R.layout.layout_collection_item,
					null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.brief = (TextView) convertView.findViewById(R.id.brief);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Collection collection = dataList.get(position);
		if (collection.getType() == 1) {
			FileUtil.setImage(holder.img,
					InterfaceURL.IMGIP + collection.getGoodsImg(),
					new AsyncImageLoader(), R.drawable.default_ad);
			holder.name.setText(collection.getGoodsName());
			holder.brief.setText(collection.getGoodsBrief());
		} else {
			FileUtil.setImage(holder.img,
					InterfaceURL.IMGIP + collection.getImagePath(),
					new AsyncImageLoader(), R.drawable.default_ad);
			holder.name.setText(collection.getSubject());
			holder.brief.setText(collection.getGoodsDescStr());
		}

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView name;
		TextView brief;
	}
}
