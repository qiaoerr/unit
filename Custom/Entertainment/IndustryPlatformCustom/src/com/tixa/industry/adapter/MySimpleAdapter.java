package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.TmallBrand_Cat;

public class MySimpleAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<TmallBrand_Cat> mydata;
	private LayoutInflater inflater;

	public MySimpleAdapter(Context context, ArrayList<TmallBrand_Cat> mydata) {
		super();
		this.mydata = mydata;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mydata.size();
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
		convertView = inflater.inflate(R.layout.simple_item, null);
		TextView title = (TextView) convertView.findViewById(R.id.cata_name);
		title.setText(mydata.get(position).getname());
		return convertView;
	}

}
