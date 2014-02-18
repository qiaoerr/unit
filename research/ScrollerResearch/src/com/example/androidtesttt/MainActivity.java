package com.example.androidtesttt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class MainActivity extends Activity {
	LinearLayout lay1, lay2, lay0;
	private Scroller mScroller;
	// private Scroller mScroller2;
	boolean open = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mScroller = new Scroller(this);
		// mScroller2 = new Scroller(this);
		lay1 = new MyLinearLayout(this);
		lay2 = new MyLinearLayout(this);

		/*lay1.setBackgroundColor(this.getResources().getColor(
				android.R.color.darker_gray));
		lay2.setBackgroundColor(this.getResources().getColor(
				android.R.color.white));*/
		lay1.setBackgroundResource(R.drawable.logo_sinaweibo);
		lay2.setBackgroundResource(R.drawable.logo_weixin);
		lay0 = new ContentLinearLayout(this);
		lay0.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams p0 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		this.setContentView(lay0, p0);

		LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		p1.weight = 1;
		lay0.addView(lay1, p1);
		LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		p2.weight = 1;
		lay0.addView(lay2, p2);
		final MyButton btn1 = new MyButton(this);
		btn1.setId(11);
		// MyButton btn2 = new MyButton(this);
		// btn2.setId(22);
		// btn2.setText("btn in layout2");
		TextView textView = new TextView(this);
		textView.setBackgroundColor(Color.BLUE);
		textView.setId(22);
		textView.setPadding(10, 15, 10, 15);
		textView.setText("btn in layout2");
		btn1.setText("btn in layout1");
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (open) {
					// �ƻ���
					mScroller.startScroll(-80, -80, 80, 80, 15000);
					open = false;
				} else {
					// �Ƴ�ȥ
					mScroller.startScroll(0, 0, -80, -80, 15000);
					open = true;
				}
			}
		});
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mScroller.startScroll(0, 0, -90, -30, 5000);
				lay2.computeScroll();// mScroller.startScroll�������ᴥ��computeScroll��������������֮���Բ�����һ�䣬Ҳ�ܴ���
				// ����ΪButton����󱳾���ı䣬������computeScroll������
			}
		});
		lay1.addView(btn1);
		lay2.addView(textView);
		// mScroller.startScroll(0, 0, -80, -80, 10000);
	}

	// MyButton
	class MyButton extends Button {
		public MyButton(Context ctx) {
			super(ctx);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			System.out.println("MyButton  " + " onDraw");
		}
	}

	// MyLinearLayout
	class MyLinearLayout extends LinearLayout {
		public MyLinearLayout(Context ctx) {
			super(ctx);
		}

		@Override
		/**
		 * Called by a parent to request that a child update its values for mScrollX
		 * and mScrollY if necessary. This will typically be done if the child is
		 * animating a scroll using a {@link android.widget.Scroller Scroller}
		 * object.
		 */
		public void computeScroll() {
			System.out.println("enter0000000000000000000000000000000");
			// ���mScroller�Ƿ�����Чʱ���ڣ�mScroller.timePassed()��ȡelapsed time��
			if (mScroller.computeScrollOffset()) {
				// ��Ϊ����computeScroll��������MyLinearLayoutʵ���ģ�
				// ���Ե���scrollTo�ƶ��Ľ��Ǹ�ʵ���ĺ��ӣ�Ҳ����MyButtonʵ��,������MyLinearLayout��ʵ����
				scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
				System.out.println("getCurrX = " + mScroller.getCurrX());
				// ������ϵͳ�ػ�(�Ż�������������)
				invalidate();
			}

		}

		@Override
		protected void dispatchDraw(Canvas canvas) {
			System.out.println("MyLinearLayout    " + " dispatchDraw");
			super.dispatchDraw(canvas);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			System.out.println("MyLinearLayout    " + "onDraw");
			super.onDraw(canvas);
		}
	}

	// ContentLinearLayout
	class ContentLinearLayout extends LinearLayout {
		public ContentLinearLayout(Context ctx) {
			super(ctx);
		}

		@Override
		protected void dispatchDraw(Canvas canvas) {
			System.out.println("ContentLinearLayout    " + " dispatchDraw");
			super.dispatchDraw(canvas);
		}
	}
}