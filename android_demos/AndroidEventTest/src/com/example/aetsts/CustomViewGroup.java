/**
  @Title: CustomViewF.java
  @Package com.example.aetsts
  @Description: TODO
  Copyright: Copyright (c) 2011 
  Company:北京天下互联科技有限公司
  
  @author Comsys-Administrator
  @date 2013-11-1 上午11:36:41
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
 * @date 2013-11-1 上午11:36:41
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

	/*每次点击它的时候都会触发一系列的ACTION_DOWN，ACTION_MOVE，ACTION_UP等事件。这里需要注意，
	如果你在执行ACTION_DOWN的时候返回了false，后面一系列其它的action就不会再得到执行了。简单的说，
	就是当dispatchTouchEvent在进行事件分发的时候，只有前一个action返回true，才会触发后一个action。*/
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
