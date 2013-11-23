package com.liu.restaurantordering;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebBrowser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getWebView());
	}

	@SuppressLint("SetJavaScriptEnabled")
	private WebView getWebView() {
		WebView webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.requestFocus();
		webView.loadUrl("http://www.baidu.com");
		return webView;
	}
}
