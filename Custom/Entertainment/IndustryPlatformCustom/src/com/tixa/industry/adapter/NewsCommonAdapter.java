package com.tixa.industry.adapter;

import java.util.ArrayList;

import com.tixa.industry.R;
import com.tixa.industry.model.Article;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 资讯类通用适配
 * @author shengy
 *
 */
public class NewsCommonAdapter extends BaseAdapter {
	private ArrayList<Article> myData;
	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private int listStyle;
	private int rowNum ;
	private int count ;
	private int childType;
	private int height = 0; //图片的高度
	
	//private Drawable drawable ;

	public NewsCommonAdapter(Context context, ArrayList<Article> myData ,int listStyle,int rowNum ,int childType) {
		this.context = context;
		this.myData = myData;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
		this.listStyle = listStyle;
		this.rowNum =  rowNum;
		this.count = rowNum;
		this.childType = childType;
		this.height = (int) ((CommonUtil.getWidthPx(context)-CommonUtil.dip2px(context, 60))/3) * 2 ;
		L.e("height is"+height);
		//this.drawable = context.getResources().getDrawable(R.drawable.default_ad);
	}
	
	public NewsCommonAdapter(Context context, ArrayList<Article> myData ,int listStyle,int rowNum) {
		this.context = context;
		this.myData = myData;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
		this.listStyle = listStyle;
		this.rowNum =  rowNum;
		this.count = rowNum;
		this.childType = 0;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setData(ArrayList<Article> myData) {
		this.myData = myData;
	}
	

	@Override
	public int getCount() {
		return myData.size() < rowNum ? myData.size() : count;
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
		holder = new ViewHolder();
		if(childType == 0) {
			if (listStyle == 1 ) {// 无图片有时间	
				convertView = mInflater.inflate(R.layout.common_list_item_no_pic, null);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.time.setText(myData.get(position).getCreateTime());
				holder.lineartime = (LinearLayout) convertView.findViewById(R.id.lineartime);
				if(myData.get(position).getCreateTime() == null || myData.get(position).getCreateTime().equals("")) {
					holder.lineartime.setVisibility(View.GONE);
				}
				
				holder.textDetail = (TextView) convertView.findViewById(R.id.textDetail);
			} else if (listStyle == 2) {// 有图片无时间
				convertView = mInflater.inflate(R.layout.common_list_item, null);
				holder.imageDetail = (ImageView) convertView.findViewById(R.id.imageDetail);
				FileUtil.setImage(holder.imageDetail, myData.get(position).getImgPath(), loader, R.drawable.default_ad);
				holder.textDetail = (TextView) convertView.findViewById(R.id.textDetail);
			}
		}else if(childType == 2){ //图片文字列表格式
			convertView = mInflater.inflate(R.layout.common_list_item_with_line, null);
			holder.imageDetail = (ImageView) convertView.findViewById(R.id.imageDetail);			
			//FileUtil.setRoundedCornerImage(holder.imageDetail, myData.get(position).getImgPath(), loader, drawable);
			FileUtil.setImage(holder.imageDetail, myData.get(position).getImgPath(), loader, R.drawable.default_ad);
		}else if(childType == 3) { //单张图片列表格式 
			convertView = mInflater.inflate(R.layout.common_list_picture_item, null);
			holder.imageDetail = (ImageView) convertView.findViewById(R.id.imageDetail);
			FrameLayout.LayoutParams pars = new LayoutParams(LayoutParams.MATCH_PARENT , height);
			holder.imageDetail.setLayoutParams(pars);
			FileUtil.setImage(holder.imageDetail, myData.get(position).getImgPath(), loader, R.drawable.default_ad);
		}

		holder.title = (TextView) convertView.findViewById(R.id.title);
		Article article =  myData.get(position);
		
		if(holder.title != null) {
			holder.title.setText(article.getTitle());	
		}
		
		if(holder.textDetail !=null) {
			holder.textDetail.setText(article.getBrief());		
		}
		return convertView;
	}
	

	private static class ViewHolder {
		TextView title;
		TextView textDetail;
		TextView time;
		ImageView imageDetail;
		LinearLayout lineartime;
	}
}

