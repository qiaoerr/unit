package com.example.aetsts;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private boolean bool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	}

	@Override
	public void onUserInteraction() {

		System.out.println("onUserInteraction");
		// super.onUserInteraction();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("onTouchEvent");
		bool = super.onTouchEvent(event);
		System.out.println("onTouchEvent " + bool);
		return bool;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		System.out.println("dispatchTouchEvent");
		System.out.println(ev.getAction());
		bool = super.dispatchTouchEvent(ev);
		System.out.println("dispatchTouchEvent " + bool);
		return bool;
		// 此方法不管是true or false 后续的MotionEvent都会触发进行。重写此方法的目的一般用来获取MotionEvent
		// ev中的信息
		// 而不是用来中断MotionEvent
		// ev的传递，只有ViewGroup类的onInterceptTouchEvent方法可以用来中断MotionEvent的层级传递
	}

}
