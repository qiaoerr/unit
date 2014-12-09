package com.example.androidtesto;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

public class SiftSoftItemAdapter extends BaseListAdapter<SiftSoftItem> {
	private int style = 0;// 0 居左 1居中
	private float scale;

	public SiftSoftItemAdapter(Context context, List<SiftSoftItem> values, int style) {
		super(context, values);
		this.style = style;
		scale = context.getResources().getDisplayMetrics().density;
	}

	@Override
	protected View getItemView(View convertView, int position) {
		SiftSoftItem item = mValues.get(position);
		TextView textView = (TextView) convertView;
		if (textView == null) {
			textView = new TextView(mContext);
			LayoutParams params = new LayoutParams(-1, -2);
			textView.setLayoutParams(params);
			textView.setPadding((int) (15 * scale), (int) (12 * scale), (int) (12 * scale),
					(int) (12 * scale));
			if (style == 0) {
				textView.setGravity(Gravity.LEFT);
				textView.setGravity(Gravity.CENTER_VERTICAL);
			} else if (style == 1) {
				textView.setGravity(Gravity.CENTER);
			}
		}
		if (item.checkStatus == 0) {
			textView.setTextColor(Color.BLACK);
		} else {
			textView.setTextColor(Color.parseColor("#df3031"));
		}
		textView.setText(item.itemName);
		return textView;
	}

}
