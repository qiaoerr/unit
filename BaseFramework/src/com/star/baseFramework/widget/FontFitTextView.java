/**
  @Title: FontFitTextView.java
  @Package com.star.efficientdevelop.widget
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-5 下午05:31:06
  @version V1.0
 */

package com.star.baseFramework.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontFitTextView extends TextView {
	// Attributes
	private Paint testPaint;
	private float minTextSize;
	private float originalTextSize;
	private float scale;

	public FontFitTextView(Context context) {
		super(context);
		initialise();
	}

	public FontFitTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise();
	}

	private void initialise() {
		testPaint = new Paint();
		testPaint.set(this.getPaint());
		// 获取设置的Text的尺寸
		originalTextSize = this.getTextSize();
		minTextSize = 2;
		scale = getContext().getResources().getDisplayMetrics().density;
	}

	private void refitText(String text, int textWidth) {
		textWidth = getWidth();
		// System.out.println("textWidth:  " + textWidth);
		if (textWidth > 0) {
			int availableWidth = textWidth - this.getPaddingLeft()
					- this.getPaddingRight();
			float trySize = originalTextSize;
			testPaint.setTextSize(trySize);
			if (testPaint.measureText(text) > availableWidth) {
				// 缩小字体
				while ((trySize > minTextSize)
						&& (testPaint.measureText(text) > availableWidth)) {
					trySize -= 1;
					if (trySize <= minTextSize) {
						trySize = minTextSize;
						break;
					}
					testPaint.setTextSize(trySize * scale);
				}
			} else if (testPaint.measureText(text) < availableWidth - 10) {
				// 放大字体
				while (testPaint.measureText(text) < availableWidth - 10) {
					trySize += 1;
					testPaint.setTextSize(trySize * scale);
				}
			}
			this.setTextSize(trySize);
		}
	}

	@Override
	protected void onTextChanged(final CharSequence text, final int start,
			final int before, final int after) {
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w != oldw) {
			refitText(this.getText().toString(), w);
		}
	}
}