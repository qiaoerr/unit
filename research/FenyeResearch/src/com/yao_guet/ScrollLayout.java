package com.yao_guet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的类
 * 
 * @author Yao.GUET blog: http://blog.csdn.net/Yao_GUET date: 2011-05-04
 */

public class ScrollLayout extends ViewGroup {

	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;

	private int mCurScreen;
	private int mDefaultScreen = 0;

	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;

	private static final int SNAP_VELOCITY = 600;

	private int mTouchState = TOUCH_STATE_REST;
	private int mTouchSlop;
	private float mLastMotionX;
	private float mLastMotionY;

	public ScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		System.out.println("onLayout_out");
		if (changed) {
			System.out.println("onLayout_in");
			int childLeft = 0;
			final int childCount = getChildCount();

			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0, childLeft + childWidth,
							childView.getMeasuredHeight());
					childLeft += childWidth;
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.out.println("onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		// final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// if (widthMode != MeasureSpec.EXACTLY) {
		// throw new IllegalStateException(
		// "ScrollLayout only canmCurScreen run at EXACTLY mode!");
		// }
		// final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		// if (heightMode != MeasureSpec.EXACTLY) {
		// throw new IllegalStateException(
		// "ScrollLayout only can run at EXACTLY mode!");
		// }
		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		System.out.println("moving to screen :" + mCurScreen);
		scrollTo(mCurScreen * width, 0);
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * 
	 * page.getScrollx() 表示view的左边向左移出的距离为正,向右移出的距离为负。
	 */

	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
		// System.out.println("screenWidth: " + screenWidth);
		// System.out.println("getScrollX(): " + getScrollX());
		// System.out.println("destScreen: " + destScreen);
	}

	public void snapToScreen(int whichScreen) {
		// get the valid layout page
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {

			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,
					Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			// invalidate(); // Redraw the layout
			computeScroll();
		}
	}

	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();// 会导致computeScroll()被调用
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();
		final float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;

		case MotionEvent.ACTION_MOVE:
			// deltaX 的值用之前的坐标值减去现在的坐标值
			int deltaX = (int) (mLastMotionX - x);
			mLastMotionX = x;
			scrollBy(deltaX, 0);
			break;

		case MotionEvent.ACTION_UP:
			mVelocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) mVelocityTracker.getXVelocity();
			System.out.println("velocityX: " + velocityX);
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
				// Fling enough to move left
				snapToScreen(mCurScreen - 1);
				System.out.println("move left ");
			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {
				// Fling enough to move right
				snapToScreen(mCurScreen + 1);
				System.out.println("move right");
			} else {
				snapToDestination();
			}
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// System.out.println("onInterceptTouchEvent" + mTouchState);
		final int action = ev.getAction();
		if ((mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		// case MotionEvent.ACTION_CANCEL:
		// case MotionEvent.ACTION_UP:
		// mTouchState = TOUCH_STATE_REST;
		// break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

}
