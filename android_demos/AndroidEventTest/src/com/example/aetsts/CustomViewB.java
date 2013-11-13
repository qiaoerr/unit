/**
  @Title: CustomView.java
  @Package com.example.aetsts
  @Description: TODO
  Copyright: Copyright (c) 2011 
  Company:�������»����Ƽ����޹�˾
  
  @author Comsys-Administrator
  @date 2013-11-1 ����11:36:09
  @version V1.0
 */

package com.example.aetsts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * @ClassName: CustomView
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-1 ����11:36:09
 * 
 */

public class CustomViewB extends Button {
	private boolean bool;

	public CustomViewB(Context context) {
		super(context);

		// TODO Auto-generated constructor stub
	}

	public CustomViewB(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("onclick===CustomViewB===onclick");
			}
		});

		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("onTouch===CustomViewB===onTouch");
				return false;
			}
		});
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub
		super.invalidate();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		System.out.println("CustomViewB-----dispatchTouchEvent  ");
		bool = super.dispatchTouchEvent(event);
		System.out.println("CustomViewB-----dispatchTouchEvent  " + bool);
		return bool;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("CustomViewB-----onTouchEvent  ");
		bool = super.onTouchEvent(event);
		System.out.println("CustomViewB-----onTouchEvent  " + bool);
		return bool;
	}

}
