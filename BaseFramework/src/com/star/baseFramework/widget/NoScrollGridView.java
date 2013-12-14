package com.star.baseFramework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.star.baseFrameworkC.R;

public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NoScrollGridView(Context context) {
		super(context);
		init();
	}

	public NoScrollGridView(Context context, int numColumns) {
		super(context);
		this.setNumColumns(numColumns);
		init();
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		this.setSelector(R.color.transparent);
		this.setCacheColorHint(R.color.black);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
