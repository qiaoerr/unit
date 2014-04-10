package com.example.androidtest123;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class D extends Activity {
	Button onclick;
	Button info;
	Context context;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("D_onCreate: " + getTaskId());
		context = this;
		onclick = (Button) findViewById(R.id.onclick);
		info = (Button) findViewById(R.id.info);
		onclick.setText("D");
		/*if (isTaskRoot()) {
			System.out.println("moveTaskToBack(false);");
			moveTaskToBack(false);
		} else {
			System.out.println("moveTaskToBack(true);");
			moveTaskToBack(true);
		}*/

	}

	public void next(View view) {
		Intent intent = new Intent(context, D.class);
		intent.putExtra("info", "activity" + i++);
		// 在所有flag与launchMode中，只有FLAG_ACTIVITY_SINGLE_TOP 等同singleTop ，
		// 而且如果manifest和intent中都设置了activity的该启动模式，则intent中设置的优先级高。
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("onNewIntentD");
		setIntent(intent);
		info.setText(intent.getStringExtra("info"));
		super.onNewIntent(intent);
	}

	@Override
	protected void onDestroy() {
		System.out.println("D_onDestroy");
		super.onDestroy();
	}
}
