package com.study;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyView extends TextView {
	private String mString = "Welcome to Kesion's blog";

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context
				.obtainStyledAttributes(attrs, R.styleable.MyView);
		int textColor = a.getColor(R.styleable.MyView_textColor, 0XFFF);
		float textSize = a.getDimension(R.styleable.MyView_textSize, 36);
		float testSize = textSize
				/ (getResources().getDisplayMetrics().density);
		mString = a.getString(R.styleable.MyView_title);
		System.out.println("textColor" + textColor + "  textSize" + testSize
				+ "  mString :" + mString);
		String str = a.getString(R.styleable.MyView_qing);
		System.out.println(str);
		String str1 = a.getString(R.styleable.MyView_qingqing);
		System.out.println(str1);
		setText(mString);
		setTextSize(textSize);
		setTextColor(textColor);
		System.out.println("hello");
		a.recycle();
	}
}