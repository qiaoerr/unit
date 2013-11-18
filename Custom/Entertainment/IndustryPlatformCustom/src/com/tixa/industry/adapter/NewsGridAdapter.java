package com.tixa.industry.adapter;

import java.util.ArrayList;

import com.tixa.industry.R;
import com.tixa.industry.activity.NewsDetailActivity;
import com.tixa.industry.activity.NewsDetailFragment;
import com.tixa.industry.model.Article;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


/**
 * 资讯类通用适配
 *
 */
public class NewsGridAdapter extends BaseAdapter {
	private ArrayList<ArrayList<Article>> myData;
	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader loader;
	private int listStyle;
	private int rowNum ;
	private int count ;
	private int height = 0; //图片的高度
	
	//private Drawable drawable ;

	public NewsGridAdapter(Context context, ArrayList<ArrayList<Article>> myData ,int listStyle,int rowNum) {
		this.context = context;
		this.myData = myData;
		mInflater = LayoutInflater.from(context);
		loader = new AsyncImageLoader();
		this.listStyle = listStyle;
		this.rowNum =  rowNum;
		this.count = rowNum;
		this.height = (int) ((CommonUtil.getWidthPx(context)/2-CommonUtil.dip2px(context, 30))/3) * 2 ;
		L.e("height is"+height);
		//this.drawable = context.getResources().getDrawable(R.drawable.default_ad);
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setData(ArrayList<ArrayList<Article>> myData) {
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
		convertView = mInflater.inflate(R.layout.common_grid_picture_item, null);
		final ArrayList<Article> arts = myData.get(position);
		holder.title = (TextView) convertView.findViewById(R.id.title);
		holder.title1 = (TextView) convertView.findViewById(R.id.title1);
		holder.imageDetail = (ImageView) convertView.findViewById(R.id.imageDetail);
		holder.imageDetail1 = (ImageView) convertView.findViewById(R.id.imageDetail1);
		holder.linear1 = (LinearLayout) convertView.findViewById(R.id.linear1);
		holder.linear = (LinearLayout) convertView.findViewById(R.id.linear);
		
		LayoutParams pars = new LayoutParams(LayoutParams.MATCH_PARENT , height);
		holder.imageDetail.setLayoutParams(pars);
		holder.imageDetail1.setLayoutParams(pars);
		
		holder.linear.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(context, NewsDetailActivity.class);
				intent.putExtra("article", arts.get(0));
				context.startActivity(intent);	
				
				
				/*FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
				Bundle b = new Bundle();
				b.putSerializable("article", arts.get(0));
				Fragment f = new NewsDetailFragment();
				FragmentTransaction transaction = fm.beginTransaction();
				f.setArguments(b);
				transaction.add(R.id.fragment, f);
				transaction.addToBackStack(null);
				transaction.commit();			*/	
			}
		});
		
		holder.linear1.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(context, NewsDetailActivity.class);
				intent.putExtra("article", arts.get(1));
				context.startActivity(intent);
				
				/*FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
				Bundle b = new Bundle();
				b.putSerializable("article", arts.get(1));
				Fragment f = new NewsDetailFragment();
				FragmentTransaction transaction = fm.beginTransaction();
				f.setArguments(b);
				transaction.add(R.id.fragment, f);
				transaction.addToBackStack(null);
				transaction.commit();		*/
			}
		});
		
		if(arts.size() < 2) {
			holder.linear1.setVisibility(View.INVISIBLE);
			holder.title.setText(arts.get(0).getTitle());
			FileUtil.setImage(holder.imageDetail, arts.get(0).getImgPath(), loader, R.drawable.default_ad);			
		}else{
			holder.title.setText(arts.get(0).getTitle());
			holder.title1.setText(arts.get(1).getTitle());
			FileUtil.setImage(holder.imageDetail, arts.get(0).getImgPath(), loader, R.drawable.default_ad);
			FileUtil.setImage(holder.imageDetail1, arts.get(1).getImgPath(), loader, R.drawable.default_ad);
		}
		return convertView;
	}
	

	private static class ViewHolder {
		TextView title;
		TextView title1;
		ImageView imageDetail1;
		ImageView imageDetail;
		LinearLayout linear1;
		LinearLayout linear;
	}
}

