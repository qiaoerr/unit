/**
  @Title: PagingViewGroup.java
  @Package com.start.jdzchina.widget
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-4 下午05:58:31
  @version V1.0
 */

package com.start.jdzchina.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.start.jdzchina.R;

/**
 * @ClassName: PagingViewGroup
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-4 下午05:58:31
 * 
 */

public class PagingViewGroup extends ViewGroup {
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	private static final int STATE_REST = 0;
	private static final int STATE_SCROLLING = 1;
	private int state = STATE_REST;
	private final int orientation;

	public PagingViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.PagingViewGroup);
		orientation = typedArray.getInteger(
				R.styleable.PagingViewGroup_oriention, HORIZONTAL);
		typedArray.recycle();

	}

	public PagingViewGroup(Context context, int orientation) {
		super(context);
		this.orientation = orientation;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (state == STATE_SCROLLING) {
			return true;
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;

		default:
			break;
		}
		return state == STATE_SCROLLING;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

}
