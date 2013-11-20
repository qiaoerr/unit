package com.start.jdzchina.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	private float coordinationX_start;
	private float move_distance;
	private int[] imgs;

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("event.getX():" + event.getX());
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			coordinationX_start = event.getX();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			move_distance = event.getX() - coordinationX_start;
		}
		if (move_distance / 100) {

		}
		return true;
	}

	public void setImgs(int[] imgs) {
		this.imgs = imgs;
	}

}
