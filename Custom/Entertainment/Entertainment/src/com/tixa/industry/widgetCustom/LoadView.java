package com.tixa.industry.widgetCustom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tixa.industry.R;

public class LoadView extends LinearLayout {
	private Context context;
	private LayoutInflater inflater;
	private ProgressBar progressBar;
	private ImageView load_image;
	private ImageView load_image_no;
	private ImageView load_no_network;
	private Button retry;

	public LoadView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public LoadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.loading_layout, this);
		progressBar = (ProgressBar) findViewById(R.id.load_progressBar);
		load_image = (ImageView) findViewById(R.id.load_image);
		load_image_no = (ImageView) findViewById(R.id.load_image_no);
		load_no_network = (ImageView) findViewById(R.id.load_no_network);
		retry = (Button) findViewById(R.id.retry);
	}

	public void loading() {
		this.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		load_image.setVisibility(View.VISIBLE);
		load_image_no.setVisibility(View.GONE);
		load_no_network.setVisibility(View.GONE);
		retry.setVisibility(View.GONE);
	}

	public void close() {
		this.setVisibility(View.GONE);
	}

	public void showNodataView() {
		this.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		load_image.setVisibility(View.GONE);
		load_image_no.setVisibility(View.VISIBLE);
		load_no_network.setVisibility(View.GONE);
		retry.setVisibility(View.GONE);
	}

	public void noNetWork(android.view.View.OnClickListener listener) {
		this.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		load_image.setVisibility(View.GONE);
		load_image_no.setVisibility(View.GONE);
		load_no_network.setVisibility(View.VISIBLE);
		retry.setVisibility(View.VISIBLE);
		retry.setOnClickListener(listener);
	}
}
