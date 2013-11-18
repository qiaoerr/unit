package com.tixa.industry.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.tixa.industry.R;

public class BottomTabBar extends LinearLayout {

	public static final String TAG = "BottomTabBar";
	private Context mContext;
	// 选中模式 0：没有单选效果 1：有单选效果
	public static final int NOSINCHECKSTYLE = 0;
	public static final int SINCHECKSTYLE = 1;
	public final int checkModel = 0;

	public BottomTabBar(Context context) {
		this(context, null);
	}

	public BottomTabBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.bottomtabbar, null);
	}

	public void setChecked(int checked) {
		for (int i = 0; i < this.getChildCount(); i++) {
			BottomTab bt = (BottomTab) this.getChildAt(i);
			if (checked == i) {
				bt.setChecked(true);
			} else {
				bt.setChecked(false);
			}
		}
	}

	public void onclick(int checked) {
		for (int i = 0; i < this.getChildCount(); i++) {
			BottomTab bt = (BottomTab) this.getChildAt(i);
			if (checked == i) {
				bt.click();
			}
		}
	}

	private CheckedChangeListener onCheckedChangeListener;

	public interface CheckedChangeListener {
		public abstract void onCheckedChanged(BottomTab bt, boolean isChecked);
	}

	public CheckedChangeListener getOnCheckedChangeListener() {
		return onCheckedChangeListener;
	}

	public void setOnCheckedChangeListener(
			CheckedChangeListener onCheckedChangeListener) {
		this.onCheckedChangeListener = onCheckedChangeListener;
	}

}
