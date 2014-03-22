package com.example.androidtest123;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class A extends Activity {
	Button onclick;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("A_onCreate: " + getTaskId());
		setContentView(R.layout.activity_main);
		context = this;
		onclick = (Button) findViewById(R.id.onclick);
		onclick.setText("A");
		// startActivity(new Intent(context, B.class));
	}

	public void next(View view) {
		startActivity(new Intent(context, B.class));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntentA");
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		System.out.println("A_onDestroy");
		super.onDestroy();
	}
}
