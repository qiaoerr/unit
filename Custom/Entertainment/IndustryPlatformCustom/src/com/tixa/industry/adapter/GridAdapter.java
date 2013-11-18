package com.tixa.industry.adapter;

import java.util.ArrayList;

import com.tixa.industry.R;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.Modular;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Modular> listData ;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader ;	

	public GridAdapter(Context context , ArrayList<Modular> listData) {
		super();
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.listData = listData;

	}

	@Override
	public int getCount() {
		return listData.size();
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
			convertView = mInflater.inflate(R.layout.grid_item_layout, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.main_image01);
			holder.textView = (TextView) convertView.findViewById(R.id.main_text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
	
		loader = new AsyncImageLoader() ;
		Modular mod = listData.get(position);
		holder.textView.setText(mod.getModularName());
		
		FileUtil.setImage(holder.imageView,  mod.getModularIcon() , loader, R.drawable.default_modular);				
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView imageView;
		TextView textView;
	}
}	
