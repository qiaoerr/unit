package com.tixa.industry.activity;

import java.util.ArrayList;

import com.tixa.industry.R;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Article;
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
import android.widget.TextView;


public class TableNewsFragment extends Fragment {
	private View view;
	private ArrayList<Article> myData;
	private TextView textView;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	private TextView textView6;
	private View line1;
	private View line2;
	private FragmentActivity context;
	private int color;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		view = inflater.inflate(R.layout.slidingnews, null);
		initView();	
		initData();	
		return view;
	}
	
	private void initView() {
		textView = (TextView) view.findViewById(R.id.title);
		textView1 = (TextView) view.findViewById(R.id.title1);
		textView2 = (TextView) view.findViewById(R.id.title2);
		textView3 = (TextView) view.findViewById(R.id.title3);
		textView4 = (TextView) view.findViewById(R.id.title4);
		textView5 = (TextView) view.findViewById(R.id.title5);
		textView6 = (TextView) view.findViewById(R.id.title6);
		line1 = view.findViewById(R.id.line1);
		line2 = view.findViewById(R.id.line2);	
		
		color = getArguments().getInt("color");	
		if(color > 4) {
			color = 4;
		}
		textView.setBackgroundResource(Constants.article_colors[color]);
		
	}
	
	private void initData() {
		context = getActivity();
		myData = (ArrayList<Article>) getArguments().getSerializable("data");
		L.e("myData size is "+myData.size());
		Article a = null;
		if(myData.size()>0) {
			a = myData.get(0);
			textView.setText(a.getTitle().trim());
			textView.setOnClickListener(new MyClickListener(a,context));
		}
		if(myData.size()>1) {
			a = myData.get(1);
			textView1.setVisibility(View.VISIBLE);
			textView1.setText(a.getTitle().trim());
			textView1.setOnClickListener(new MyClickListener(a,context));
			line1.setVisibility(View.VISIBLE);
		}
		
		if(myData.size()>2) {
			a = myData.get(2);
			textView2.setVisibility(View.VISIBLE);
			textView2.setText(a.getTitle().trim());
			textView2.setOnClickListener(new MyClickListener(a,context));
		
		}
		if(myData.size()>3) {
			a = myData.get(3);
			textView3.setVisibility(View.VISIBLE);
			textView3.setText(a.getTitle().trim());
			textView3.setOnClickListener(new MyClickListener(a,context));
			line2.setVisibility(View.VISIBLE);
		} 
		if(myData.size()>4) {
			a = myData.get(4);
			textView4.setVisibility(View.VISIBLE);
			textView4.setText(a.getTitle().trim());
			textView4.setOnClickListener(new MyClickListener(a,context));
			
		} 
		if(myData.size()>5) {
			a = myData.get(5);
			textView5.setVisibility(View.VISIBLE);
			textView5.setText(a.getTitle().trim());
			textView5.setOnClickListener(new MyClickListener(a,context));
		} 
		if(myData.size()>6) {
			a = myData.get(6);
			textView6.setVisibility(View.VISIBLE);
			textView6.setText(a.getTitle().trim());
			textView6.setOnClickListener(new MyClickListener(a,context));	
		}
		
		
	}
	
	class MyClickListener implements OnClickListener {
		private final Article a;
		private final Context context;

		public MyClickListener(Article a ,Context context) {
			this.a = a;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			/*FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
			Bundle b = new Bundle();
			b.putSerializable("article", a);
			Fragment f = new NewsDetailFragment();
			FragmentTransaction transaction = fm.beginTransaction();
			f.setArguments(b);
			transaction.add(R.id.fragment, f);
			transaction.addToBackStack(null);
			transaction.commit();*/
			
			Intent intent = new Intent();
			intent.setClass(context, NewsDetailActivity.class);
			intent.putExtra("article", a);
			startActivity(intent);			
		}
		
		
	}

	
}
