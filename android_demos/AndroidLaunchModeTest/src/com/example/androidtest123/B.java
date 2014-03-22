package com.example.androidtest123;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class B extends Activity {
	Button onclick;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("B_onCreate: " + getTaskId());
		context = this;
		onclick = (Button) findViewById(R.id.onclick);
		onclick.setText("B");
		// startActivity(new Intent(context, C.class));
	}

	public void next(View view) {
		startActivity(new Intent(context, C.class));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntentB");
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		System.out.println("B_onDestroy");
		super.onDestroy();
	}
}
