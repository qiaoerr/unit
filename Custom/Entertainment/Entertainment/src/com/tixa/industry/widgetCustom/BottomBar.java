package com.tixa.industry.widgetCustom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.R;

/**
 * @author administrator
 * @version 创建时间：2013-7-16 下午02:12:46
 * 
 */
public class BottomBar extends LinearLayout {

	private Context context;
	private int tabNum;
	private LayoutParams params_out;
	private RelativeLayout.LayoutParams params_inside;
	private int[] resourceId;
	private String[] tabNames;
	private float scale_font;

	public BottomBar(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();

	}

	private void init() {
		this.setBackgroundResource(R.drawable.meaubar);
		params_out = new LayoutParams(-1, -1);
		params_out.weight = 1;
		params_inside = new RelativeLayout.LayoutParams(-2, -2);
		params_inside.addRule(RelativeLayout.CENTER_IN_PARENT);
		scale_font = context.getResources().getDisplayMetrics().scaledDensity;

	}

	public void setTabNum(int tabNum) {
		this.tabNum = tabNum;
	}

	public void setResourceId(int... res) {
		resourceId = res;
	}

	public void setTabsName(String... names) {
		tabNames = names;
	}

	public void setTabListener(
			android.view.View.OnClickListener... onClickListeners) {

		for (int i = 0; i < tabNum; i++) {
			RelativeLayout out = new RelativeLayout(context);
			out.setLayoutParams(params_out);
			TextView textView = new TextView(context);
			textView.setTextColor(Color.WHITE);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			textView.setTextSize(8 * scale_font);
			textView.setLayoutParams(params_inside);
			if (tabNames.length > i && tabNames[i] != null) {
				textView.setText(tabNames[i]);
			}
			if (resourceId.length > i) {
				textView.setCompoundDrawablesWithIntrinsicBounds(0,
						resourceId[i], 0, 0);
			}

			out.addView(textView);
			this.addView(out);
			if (onClickListeners.length > i && onClickListeners[i] != null) {
				textView.setOnClickListener(onClickListeners[i]);
			}
		}
	}
}
