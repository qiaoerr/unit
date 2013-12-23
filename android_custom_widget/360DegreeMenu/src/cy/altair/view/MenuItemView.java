package cy.altair.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MenuItemView extends ViewGroup {
	public final static int STATUS_CLOSE = 0;
	public final static int STATUS_OPEN = 1;
	public final static int EXACTLY = 0;
	public final static int UNSPECIFIED = 1;
	private float radius;
	private int status;
	private Context context;
	private int screenWidth;
	private int modle = EXACTLY;
	private int percentage = 5;// 决定menuItem图标的大小，值越大，menuItem图标的值越小

	public MenuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MenuItemView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		this.status = STATUS_CLOSE;
		this.screenWidth = context.getResources().getDisplayMetrics().widthPixels;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		for (int index = 0; index < getChildCount(); index++) {
			final View child = getChildAt(index);
			// measure
			if (modle == UNSPECIFIED) {
				child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			} else if (modle == EXACTLY) {
				int widthMeasureSp = MeasureSpec.makeMeasureSpec(screenWidth
						/ percentage, MeasureSpec.EXACTLY);
				int heightMeasureSp = MeasureSpec.makeMeasureSpec(screenWidth
						/ percentage, MeasureSpec.EXACTLY);
				child.measure(widthMeasureSp, heightMeasureSp);
			}
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (radius == 0)
			throw new RuntimeException(
					"RadiusUnknow!Use method setRadiusByDP to set the radius first!");
		if (changed) {
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				View childView = getChildAt(i);
				childView.setVisibility(View.GONE);
				int width = childView.getMeasuredWidth();
				int height = childView.getMeasuredHeight();
				int x = getMeasuredWidth()
						/ 2
						- (int) (MyAnimations.dip2px(context, radius) * Math
								.cos(Math.toRadians(i * (360 / count))));
				int y = getMeasuredHeight()
						/ 2
						- (int) (MyAnimations.dip2px(context, radius) * Math
								.sin(Math.toRadians(i * (360 / count))));
				childView.layout(x - width / 2, y - height / 2, x + width / 2,
						y + height / 2);
				if (i == 0) {
					System.out.println("x: " + x);
				}
			}
		}
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getModle() {
		return modle;
	}

	public void setModle(int modle) {
		this.modle = modle;
	}

}
