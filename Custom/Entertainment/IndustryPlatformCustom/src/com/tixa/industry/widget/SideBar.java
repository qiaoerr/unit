package com.tixa.industry.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SideBar extends View {

	private char[] l;
	private SectionIndexer sectionIndexter = null;
	private ListView list;
	private TextView dialogText;
	private Handler handler;
	private int selectIndex = -1;
	private int mHight;

	private Context context;

	public SideBar(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		handler = new Handler();
		l = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '#' };
	}

	public TextView getDialogText() {
		return dialogText;
	}

	public void setDialogText(TextView dialogText) {
		this.dialogText = dialogText;
	}

	public void setListView(ListView _list) {
		list = _list;
		try {
			HeaderViewListAdapter listAdapter = (HeaderViewListAdapter) _list
					.getAdapter();
			sectionIndexter = (SectionIndexer) listAdapter.getWrappedAdapter();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("side", "side is exception");
		}

	}

	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		float y = event.getY();
		// 计算手指位置，找到对应的段，让mList移动段开头的位置上
		selectIndex = (int) (y / (mHight / l.length));
		if (selectIndex < 0 || selectIndex > l.length - 1) {// 防止越界
			selectIndex = -1;
			return super.onTouchEvent(event);
		}
		if (dialogText != null)
			dialogText.setText(l[selectIndex] + "");
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					if (dialogText != null
							&& dialogText.getVisibility() == View.GONE) {
						dialogText.setVisibility(View.VISIBLE);
					}
				}
			});
			setBackgroundColor(0x44aaaaaa);
			if (sectionIndexter == null) {
				sectionIndexter = (SectionIndexer) list.getAdapter();
				return true;
			}
			int position = sectionIndexter.getPositionForSection(selectIndex);
			if (position == -1) {
				return true;
			}
			list.setSelection(position + list.getHeaderViewsCount());
			invalidate();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			selectIndex = -1;
			setBackgroundColor(Color.TRANSPARENT);
			handler.post(new Runnable() {

				@Override
				public void run() {
					if (dialogText != null
							&& dialogText.getVisibility() == View.VISIBLE) {
						dialogText.setVisibility(View.GONE);
					}
				}
			});
			invalidate();
		} else {
			setBackgroundColor(Color.TRANSPARENT);
		}
		return true;
	}

	Paint paint = new Paint();

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mHight = getHeight();
		int width = getWidth();
		int singleHeight = mHight / l.length;
		for (int i = 0; i < l.length; i++) {
			paint.setColor(Color.rgb(176, 176, 176));
			Resources r;
			if (context == null) {
				r = Resources.getSystem();
			} else {
				r = context.getResources();
			}
			float tsize = TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 14, r.getDisplayMetrics());
			paint.setTextSize(tsize);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			if (i == selectIndex) {
				paint.setColor(Color.parseColor("#00BFFF"));// 滑动时按下的字母颜色
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(l[i] + "") / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(l[i] + "", xPos, yPos, paint);
			paint.reset();
		}
	}
}
