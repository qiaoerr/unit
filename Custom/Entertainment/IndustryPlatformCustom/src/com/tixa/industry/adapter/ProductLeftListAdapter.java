package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.GoodsCata;

public class ProductLeftListAdapter extends BaseAdapter {
	private ArrayList<GoodsCata> data;
	private Context context;
	private LayoutInflater inflater;

	public ProductLeftListAdapter(ArrayList<GoodsCata> data, Context context) {
		super();
		this.data = data;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
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
			convertView = inflater.inflate(R.layout.left, null);
			holder.title = (TextView) convertView.findViewById(R.id.name);
			holder.img = (ImageView) convertView.findViewById(R.id.fun_arrow);
			convertView.setTag(holder);
		} else {
			convertView.requestLayout();
			holder = (ViewHolder) convertView.getTag();
		}
		GoodsCata goodsCata = data.get(position);
		String cataName = goodsCata.getCataName();
		// if (cataName.length() > 4) {
		// cataName = cataName.substring(0, 4);
		// }
		holder.title.setText(cataName);
		if (goodsCata.isChecked()) {
			holder.img.setVisibility(View.VISIBLE);
			holder.title.setTextColor(Color.BLACK);
		} else {
			holder.title.setTextColor(context.getResources().getColor(
					R.color.list_color));
			holder.img.setVisibility(View.GONE);
		}
		return convertView;
	}

	public class ViewHolder {
		TextView title;
		ImageView img;
	}

}
