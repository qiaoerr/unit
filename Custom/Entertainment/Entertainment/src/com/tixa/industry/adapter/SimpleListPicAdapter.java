package com.tixa.industry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.tixa.industry.R;
import com.tixa.industry.activity.RouteListActivity;

/**
 * @author administrator
 * @version
 * 
 */
public class SimpleListPicAdapter<T extends ItemizedOverlay<?>> extends
		BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private String type;
	private T mOverlay;

	public SimpleListPicAdapter(Context context, T mOverlay, String type) {
		super();
		this.context = context;
		this.mOverlay = mOverlay;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mOverlay.size() - 1;
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

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater
					.inflate(R.layout.layout_simplelist_pic, null);
			holder.img = (ImageView) convertView.findViewById(R.id.route_img);
			holder.detail = (TextView) convertView
					.findViewById(R.id.route_detail);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String temp = mOverlay.getItem(position).getTitle();
		if (position == 0) {
			holder.detail.setText("开始");
			holder.img.setBackgroundResource(R.drawable.tran_go);
		} else {
			if (type.equals(RouteListActivity.WALK)) {
				holder.img.setBackgroundResource(R.drawable.tran_go);
			} else if (type.equals(RouteListActivity.DRIVER)) {
				holder.img.setBackgroundResource(R.drawable.tran_type_driving);
			} else if (type.equals(RouteListActivity.TRANSIT)) {
				if (temp.contains("地铁")) {
					holder.img
							.setBackgroundResource(R.drawable.tran_type_train);
				} else if (temp.contains("步行")) {
					holder.img.setBackgroundResource(R.drawable.tran_go);
				} else {
					holder.img.setBackgroundResource(R.drawable.tran_type_bus);
				}
			}
			holder.detail.setText(temp);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView detail;
	}
}
