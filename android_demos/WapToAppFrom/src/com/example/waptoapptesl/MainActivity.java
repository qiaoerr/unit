package com.example.waptoapptesl;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadHtml();
	}

	public void loadHtml() {
		WebView webview = new WebView(this);
		WebSettings wSet = webview.getSettings();
		wSet.setJavaScriptEnabled(true);
		webview.loadUrl("file:///android_asset/a.html");
		setContentView(webview);
	}
}
