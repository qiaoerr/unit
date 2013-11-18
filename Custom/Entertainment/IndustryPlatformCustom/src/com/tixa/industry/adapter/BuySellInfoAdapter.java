package com.tixa.industry.adapter;

import java.util.ArrayList;

import com.tixa.industry.R;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.model.BuyInfo;
import com.tixa.industry.model.Goods;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.StrUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BuySellInfoAdapter extends BaseAdapter {
	private ArrayList<BuyInfo> myData;
	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private int rowNum ;
	private int count ;
	
	public BuySellInfoAdapter(Context context, ArrayList<BuyInfo> myData , int rowNum) {
		this.context = context;
		this.myData = myData;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
		this.rowNum =  rowNum;
		this.count = rowNum;
	}
	
	@Override
	public int getCount() {
		return myData.size() < rowNum ? myData.size() : count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setData(ArrayList<BuyInfo> myData) {
		this.myData = myData;
	}
	
	@Override
	public Object getItem(int position) {
		return myData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		//if(convertView == null) {
		convertView = mInflater.inflate(R.layout.buyinfo_list_item, null);
		holder = new ViewHolder();
		holder.imageDetail =(ImageView) convertView
				.findViewById(R.id.imageDetail);
		holder.companyName = (TextView) convertView
				.findViewById(R.id.shopname);
		holder.price = (TextView) convertView.findViewById(R.id.price);
		holder.buySellName = (TextView) convertView
				.findViewById(R.id.title);
		convertView.setTag(holder);
		/*}else{
			holder = (ViewHolder) convertView.getTag();
		}*/
		
		BuyInfo info = myData.get(position);
		String imgpath = "";
		String title = "";
		String price = "";
		String companyName = "";
		
	
		price = info.getGoodsPrice();
		imgpath = info.getImagePath();
		if(imgpath == null) {
			imgpath = InterfaceURL.IMGIP+imgpath;
		}
		title = info.getSubject();
		companyName = info.getBusinessName();
		FileUtil.setImage(holder.imageDetail, imgpath, loader, R.drawable.default_ad);
		holder.companyName.setText(companyName);
		holder.price.setText("￥"+ price);
		holder.buySellName.setText(title);
		
		return convertView;
		
	}
	
	private static class ViewHolder {
		public TextView companyName;
		public TextView price;
		public TextView buySellName;
		public ImageView imageDetail;
	}
	
}
