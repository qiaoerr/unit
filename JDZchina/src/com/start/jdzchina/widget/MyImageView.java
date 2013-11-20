package com.start.jdzchina.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	private float coordinationX_start;
	private float move_distance;
	private int[] imgs;
	private int index = 120;

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
		int tem = (int) (move_distance / 100);
		switch (tem) {
		case 1:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 2:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 3:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 4:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 5:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 6:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 7:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case 8:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -1:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -2:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -3:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -4:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -5:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -6:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -7:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;
		case -8:
			index += tem;
			this.setBackgroundResource(imgs[index % imgs.length]);
			break;

		default:
			break;
		}

		return true;
	}

	public void setImgs(int[] imgs) {
		this.imgs = imgs;
	}

}
