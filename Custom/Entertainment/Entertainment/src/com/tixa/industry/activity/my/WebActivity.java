package com.tixa.industry.activity.my;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tixa.industry.R;
import com.tixa.industry.widgetCustom.LoadView;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

public class WebActivity extends Activity {
	private Context context;
	private TopBar topbar;
	private WebView webView;
	private LoadView view_loading;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_layout1);
		context = this;
		url = getIntent().getStringExtra("url");
		inintopbar();
		initView();
	}

	private void initView() {
		view_loading = (LoadView) findViewById(R.id.loadView);
		view_loading.close();
		int screenDensity = getResources().getDisplayMetrics().densityDpi;
		WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		if (screenDensity <= DisplayMetrics.DENSITY_LOW) {
			zoomDensity = WebSettings.ZoomDensity.CLOSE;
		} else if (screenDensity >= DisplayMetrics.DENSITY_LOW
				&& screenDensity <= DisplayMetrics.DENSITY_HIGH) {
			zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		} else if (screenDensity >= DisplayMetrics.DENSITY_HIGH) {
			zoomDensity = WebSettings.ZoomDensity.FAR;
		}
		webView = (WebView) findViewById(R.id.myWebView);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setDefaultZoom(zoomDensity);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webView.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				view_loading.loading();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view_loading.close();
			}
		});
		webView.loadUrl(url);
	}

	private void inintopbar() {
		// TODO Auto-generated method stub

		topbar = (TopBar) findViewById(R.id.topbar1);
		topbar.setShowConfig("详情", R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});
	}
}
