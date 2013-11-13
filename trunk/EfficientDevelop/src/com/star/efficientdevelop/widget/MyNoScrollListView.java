package com.star.efficientdevelop.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 可兼容横向滑动控件的NoScrollListView
 * 
 * @author DONG
 * 
 */
public class MyNoScrollListView extends ListView {
	private float xDistance, yDistance, lastX, lastY;

	public MyNoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyNoScrollListView(Context context) {
		super(context);
	}

	public MyNoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
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
			if (xDistance > yDistance)
				return false;
		}

		return super.onInterceptTouchEvent(ev);
	}
}
