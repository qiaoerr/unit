package com.tixa.industry.widget;

import com.tixa.industry.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadView extends LinearLayout {
	private Context mContext;
	private ProgressBar bar;
	private ImageView loadImage;
	private ImageView loadNodataImage;
	private Button retry;
	private ImageView load_no_network;
	private TextView prompt; //文字提示

	public LoadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public LoadView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	private void init() {	
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.loading_layout, this);
		bar = (ProgressBar) findViewById(R.id.load_progressBar);
		loadImage = (ImageView) findViewById(R.id.load_image);
		loadNodataImage = (ImageView) findViewById(R.id.load_image_no);	
		retry = (Button) findViewById(R.id.retry);
		load_no_network = (ImageView) findViewById(R.id.load_no_network);
		prompt = (TextView) findViewById(R.id.prompt);
	}
	
	//提示消息
	public void noDataShowPromept(String promept,String buttonName,OnClickListener listener) {
		this.setVisibility(View.VISIBLE);
		bar.setVisibility(View.INVISIBLE);
		loadImage.setVisibility(View.VISIBLE);
		prompt.setVisibility(View.VISIBLE);
		prompt.setText(promept);
		loadNodataImage.setVisibility(View.GONE);
		load_no_network.setVisibility(View.GONE);
		retry.setVisibility(View.VISIBLE);
		retry.setText(buttonName);
		retry.setOnClickListener(listener);
	}
	
	
	
	//没有网络
	public void noNetWork(OnClickListener listener) {
		this.setVisibility(View.VISIBLE);
		bar.setVisibility(View.GONE);		
		loadImage.setVisibility(View.GONE);
		loadNodataImage.setVisibility(View.GONE);
		prompt.setVisibility(View.GONE);
		retry.setVisibility(View.VISIBLE);
		load_no_network.setVisibility(View.VISIBLE);
		retry.setOnClickListener(listener);		
	}
	
	public void loading() {		
		this.setVisibility(View.VISIBLE);
		prompt.setVisibility(View.GONE);
		bar.setVisibility(View.VISIBLE);		
		loadImage.setVisibility(View.VISIBLE);
		loadNodataImage.setVisibility(View.GONE);
		retry.setVisibility(View.GONE);
		load_no_network.setVisibility(View.GONE);
	}
	
	public void close() {
		this.setVisibility(View.GONE);
	}
	
	public void showNodataView() {		
		this.setVisibility(View.VISIBLE);
		prompt.setVisibility(View.GONE);
		bar.setVisibility(View.GONE);		
		loadImage.setVisibility(View.GONE);
		loadNodataImage.setVisibility(View.VISIBLE);
		retry.setVisibility(View.GONE);
		load_no_network.setVisibility(View.GONE);
	}

}