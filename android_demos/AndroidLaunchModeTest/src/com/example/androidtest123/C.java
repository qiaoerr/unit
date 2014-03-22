package com.example.androidtest123;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class C extends Activity {
	Button onclick;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("C_onCreate: " + getTaskId());
		context = this;
		onclick = (Button) findViewById(R.id.onclick);
		onclick.setText("C");
		if (isTaskRoot()) {
			System.out.println("moveTaskToBack(false);");
			moveTaskToBack(false);
		} else {
			System.out.println("moveTaskToBack(true);");
			moveTaskToBack(true);
		}
	}

	public void next(View view) {
		startActivity(new Intent(context, D.class));
	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntentC");
		setIntent(intent);
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		System.out.println("C_onDestroy");
		super.onDestroy();
	}
}
