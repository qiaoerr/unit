package com.liu.restaurantordering;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class Testone extends Activity implements OnClickListener {
	EditText url;
	WebView mWebView;
	Button goButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testone);
		setTitle("WebBrowser Made by Zhenghaibo");
		setControl();
		setWebStyle();
	}

	private void setControl() {
		url = (EditText) findViewById(R.id.urltext);
		mWebView = (WebView) findViewById(R.id.webshow);
		goButton = (Button) findViewById(R.id.GoBtn);
		goButton.setOnClickListener(this);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setWebStyle() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mWebView.requestFocus();

		mWebView.loadUrl("http://www.baidu.com");
		mWebView.setWebViewClient(new MyWebViewClient());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.GoBtn:
			String url_text;
			String url_head = "http://";
			url_text = url.getText().toString();
			if (!url_text.contains("http://")) {
				url_text = url_head.concat(url_text);

			}
			mWebView.loadUrl(url_text);
			break;
		}
	}

	class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url_) {
			view.loadUrl(url_);
			url.setText(url_);
			return true;
		}
	}
}