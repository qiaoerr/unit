/**
  @Title: PagingViewGroup.java
  @Package com.start.jdzchina.widget
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-4 下午05:58:31
  @version V1.0
 */

package com.star.efficientdevelop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.star.efficientdevelop.R;

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
	private Context context;
	private int state = STATE_REST;
	private final int orientation;
	private Scroller scroller;
	private int touchSlop;
	private float mLastMotionX;
	private float mLastMotionY;
	private VelocityTracker velocityTracker;
	private static final int SNAP_VELOCITY = 600;
	private int currentIndex = 0;

	public PagingViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.PagingViewGroup);
		orientation = typedArray.getInteger(
				R.styleable.PagingViewGroup_oriention, HORIZONTAL);
		typedArray.recycle();
		init();
	}

	public PagingViewGroup(Context context, int orientation) {
		super(context);
		this.context = context;
		this.orientation = orientation;
		init();
	}

	private void init() {
		scroller = new Scroller(context);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		velocityTracker = VelocityTracker.obtain();
		System.out.println(orientation);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (orientation == HORIZONTAL) {
			int childLeft = 0;
			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				childLeft += childWidth;
			}
		} else {
			int childTop = 0;
			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				int childHight = child.getMeasuredHeight();
				child.layout(0, childTop, child.getMeasuredWidth(), childTop
						+ childHight);
				childTop += childHight;
			}
		}
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setToScreen(int index) {
		int temp = Math.max(0, Math.min(index, getChildCount() - 1));
		snapToScreen(temp);
	}

	private void snapTodestination() {
		int temp;
		if (orientation == HORIZONTAL) {
			temp = (int) (getScrollX() + getWidth() / 2) / getWidth();
		} else {
			temp = (int) (getScrollY() + getHeight() / 2) / getHeight();
		}
		snapToScreen(Math.max(0, Math.min(temp, getChildCount() - 1)));
	}

	private void snapToScreen(int index) {
		currentIndex = index;
		if (orientation == HORIZONTAL) {
			int dx = getWidth() * index - getScrollX();
			scroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);
			computeScroll();
		} else {
			int dy = getHeight() * index - getScrollY();
			scroller.startScroll(0, getScrollY(), 0, dy, Math.abs(dy) * 2);
			computeScroll();
		}
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			invalidate();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		velocityTracker.addMovement(event);
		velocityTracker.computeCurrentVelocity(1000);
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!scroller.isFinished()) {
				scroller.abortAnimation();
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (orientation == HORIZONTAL) {
				int deltaX = (int) (mLastMotionX - x);
				scrollBy(deltaX, 0);
			}
			if (orientation == VERTICAL) {
				int deltaY = (int) (mLastMotionY - y);
				scrollBy(0, deltaY);
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
			if (orientation == HORIZONTAL) {
				if (velocityTracker.getXVelocity() > SNAP_VELOCITY
						&& currentIndex > 0) {
					snapToScreen(currentIndex - 1);
				} else if (velocityTracker.getXVelocity() < -SNAP_VELOCITY
						&& currentIndex < getChildCount() - 1) {
					snapToScreen(currentIndex + 1);
				} else {
					snapTodestination();
				}
			} else {
				if (velocityTracker.getYVelocity() > SNAP_VELOCITY
						&& currentIndex > 0) {
					snapToScreen(currentIndex - 1);
				} else if (velocityTracker.getYVelocity() < -SNAP_VELOCITY
						&& currentIndex < getChildCount() - 1) {
					snapToScreen(currentIndex + 1);
				} else {
					snapTodestination();
				}
			}
			state = STATE_REST;
			if (velocityTracker != null) {
				velocityTracker.recycle();
			}
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (state == STATE_SCROLLING) {
			return true;
		}
		float x = ev.getX();
		float y = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!scroller.isFinished()) {
				state = STATE_SCROLLING;
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (orientation == HORIZONTAL) {
				float deltaX = mLastMotionX - x;
				if (Math.abs(deltaX) > touchSlop) {
					state = STATE_SCROLLING;
				}
			} else {
				float deltaY = mLastMotionY - y;
				if (Math.abs(deltaY) > touchSlop) {
					state = STATE_SCROLLING;
				}
			}
			break;
		// case MotionEvent.ACTION_UP:
		// break;
		default:
			break;
		}
		return state == STATE_SCROLLING;
	}
}
