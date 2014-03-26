package com.example.androidtest123;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button onclick;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("MAIN_onCreate: " + getTaskId());
		setContentView(R.layout.activity_main);
		context = this;
		onclick = (Button) findViewById(R.id.onclick);
		onclick.setText("MAIN");
		// startActivity(new Intent(context, A.class));
	}

	public void next(View view) {
		startActivity(new Intent(context, A.class));
	}

	@Override
	protected void onDestroy() {
		System.out.println("MAIN_onDestroy");
		super.onDestroy();
	}

}