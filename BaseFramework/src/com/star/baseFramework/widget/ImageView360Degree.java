package com.star.baseFramework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 
 * @ClassName: MyImageView
 * @Description:在一个imageView中，通过左右滑动屏幕触发，展示从各个角度拍摄的图片
 * @author Comsys-Administrator
 * @date 2013-11-21 上午10:29:22
 * 
 */
public class ImageView360Degree extends ImageView {
	private float coordinationX_start;
	private float move_distance;
	private int[] imgs;
	private int index = 100000;
	private float threshold = 100;

	public ImageView360Degree(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			coordinationX_start = event.getX();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			move_distance = event.getX() - coordinationX_start;
			if (Math.abs(move_distance) > threshold) {
				coordinationX_start = event.getX();
			}
		}
		int tem = (int) (move_distance / threshold);
		index += tem;
		this.setBackgroundResource(imgs[index % imgs.length]);
		return true;
	}

	public void setImgs(int[] imgs) {
		this.imgs = imgs;
		index = (int) (imgs.length * (1.0e5));
	}

}
