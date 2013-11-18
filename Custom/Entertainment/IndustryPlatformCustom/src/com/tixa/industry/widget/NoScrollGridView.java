package com.tixa.industry.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.tixa.industry.R;

public class NoScrollGridView extends GridView {
	private int numColumns;

	// private String templateId;

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// templateId = context.getResources().getString(R.string.templateid);
		init();
	}

	public NoScrollGridView(Context context) {
		super(context);
		init();
	}

	public NoScrollGridView(Context context, int numColumns) {
		super(context);
		this.numColumns = numColumns;
		init();
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		if (numColumns == 0) {
			numColumns = 4;
		}
		this.setNumColumns(numColumns);
		this.setSelector(R.color.transparent);
		this.setCacheColorHint(R.color.default_circle_indicator_page_color);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
