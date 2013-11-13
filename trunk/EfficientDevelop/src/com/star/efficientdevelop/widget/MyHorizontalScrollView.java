/**
  @Title: MyHorizontalScrollView.java
  @Package com.star.efficientdevelop.widget
  @Description: TODO
  Copyright: Copyright (c) 2011 
  Company:北京天下互联科技有限公司
  
  @author Comsys-Administrator
  @date 2013-11-11 下午05:45:57
  @version V1.0
 */

package com.star.efficientdevelop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * @ClassName: MyHorizontalScrollView
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-11 下午05:45:57
 * 
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
	private float xDistance, yDistance, lastX, lastY;

	public MyHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyHorizontalScrollView(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			lastX = ev.getX();
			lastY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();
			xDistance += Math.abs(curX - lastX);
			yDistance += Math.abs(curY - lastY);
			lastX = curX;
			lastY = curY;
			if (xDistance < yDistance)
				return false;
		}

		return super.onInterceptTouchEvent(ev);
	}
}
