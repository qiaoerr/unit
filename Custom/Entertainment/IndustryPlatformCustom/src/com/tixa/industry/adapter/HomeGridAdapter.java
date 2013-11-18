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
import com.tixa.industry.model.Modular;
import com.tixa.industry.util.AsyncImageLoader;

public class HomeGridAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Modular> listData;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private String templateId;

	public HomeGridAdapter(Context context, ArrayList<Modular> listData) {
		super();
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.listData = listData;
		this.templateId = context.getResources().getString(R.string.templateid);

	}

	@Override
	public int getCount() {
		if ("1005".equals(templateId)) {
			return listData.size() > 8 ? 8 : listData.size();
		} else if ("1006".equals(templateId) || "1011".equals(templateId)
				|| "1016".equals(templateId)) {
			return listData.size() > 6 ? 6 : listData.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			if ("1005".equals(templateId)) {
				convertView = mInflater.inflate(R.layout.grid_item_1005, null);
			} else if ("1006".equals(templateId)) {
				convertView = mInflater.inflate(R.layout.grid_item_1006, null);
			} else if ("1011".equals(templateId)) {
				convertView = mInflater.inflate(R.layout.grid_item_1011, null);
			} else if ("1016".equals(templateId)) {
				convertView = mInflater.inflate(R.layout.grid_item_1016, null);
			}

			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.main_image01);
			holder.textView = (TextView) convertView
					.findViewById(R.id.main_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		loader = new AsyncImageLoader();
		Modular mod = listData.get(position);
		holder.textView.setText(mod.getModularName());
		holder.imageView.setImageResource(R.drawable.module1 + position);
		// FileUtil.setImage(holder.imageView, mod.getModularIcon(), loader,
		// R.drawable.module1);
		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView textView;
	}
}
