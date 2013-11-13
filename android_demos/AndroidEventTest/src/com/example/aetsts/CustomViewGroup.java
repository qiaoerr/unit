/**
  @Title: CustomViewF.java
  @Package com.example.aetsts
  @Description: TODO
  Copyright: Copyright (c) 2011 
  Company:�������»����Ƽ����޹�˾
  
  @author Comsys-Administrator
  @date 2013-11-1 ����11:36:41
  @version V1.0
 */

package com.example.aetsts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @ClassName: CustomViewF
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-1 ����11:36:41
 * 
 */

public class CustomViewGroup extends RelativeLayout {
	private boolean bool;

	public CustomViewGroup(Context context) {
		super(context);
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*ÿ�ε������ʱ�򶼻ᴥ��һϵ�е�ACTION_DOWN��ACTION_MOVE��ACTION_UP���¼���������Ҫע�⣬
	�������ִ��ACTION_DOWN��ʱ�򷵻���false������һϵ��������action�Ͳ����ٵõ�ִ���ˡ��򵥵�˵��
	���ǵ�dispatchTouchEvent�ڽ����¼��ַ���ʱ��ֻ��ǰһ��action����true���Żᴥ����һ��action��*/
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		System.out.println("RelativeLayout-----dispatchTouchEvent  ");
		bool = super.dispatchTouchEvent(ev);
		System.out.println("RelativeLayout-----dispatchTouchEvent  " + bool);
		return bool;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		System.out.println("RelativeLayout-----onInterceptTouchEvent  ");
		// bool = super.onInterceptTouchEvent(ev);
		// System.out.println("RelativeLayout-----onInterceptTouchEvent  " +
		// bool);
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("RelativeLayout-----onTouchEvent  ");
		bool = super.onTouchEvent(event);
		System.out.println("RelativeLayout-----onTouchEvent  " + bool);
		return bool;
	}

}
