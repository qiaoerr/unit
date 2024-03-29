package com.star.baseFramework.widget;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.star.baseFramework.adapter.ViewPagerAdapter;
import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.AsyncImageLoader;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.baseFramework.util.FileUtil;
import com.star.baseFrameworkC.R;

/**
 * 
 * @ClassName: BannerView
 * @Description: 图片横幅展示类
 * @author Comsys-Administrator
 * @date 2013-11-21 上午10:28:35
 * 
 */
public class BannerView extends RelativeLayout implements OnPageChangeListener {
	private Context context;
	private LayoutParams params;
	private ArrayList<View> views;
	private LinearLayout viewGroup;
	private ArrayList<BannerModel> bannerModels;
	private int bannerWidth;
	private int bannerHight;
	private View[] indexs;
	private ViewPager viewPager;
	private boolean isContinue = true;
	private AtomicInteger atomicInteger = null;
	private int speed = 4;
	public final static int left_bottom = 0;
	public final static int middle_bottom = 1;
	public final static int right_bottom = 2;
	private int possition = 0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int item = msg.what;
			viewPager.setCurrentItem(item);
		};
	};

	public BannerView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public BannerView(Context context, ArrayList<BannerModel> bannerModels,
			int bannerWidth, int bannerHight) {
		super(context);
		this.context = context;
		this.bannerModels = bannerModels;
		this.bannerWidth = bannerWidth;
		this.bannerHight = bannerHight;
		init();
	}

	public BannerView(Context context, ArrayList<BannerModel> bannerModels,
			int bannerWidth, int bannerHight, int possition) {
		super(context);
		this.context = context;
		this.bannerModels = bannerModels;
		this.bannerWidth = bannerWidth;
		this.bannerHight = bannerHight;
		this.possition = possition;
		init();
	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		if (bannerModels.size() <= 0) {
			return;
		}
		initData();
		initView();
		startMove();
	}

	private void initData() {
		atomicInteger = new AtomicInteger(0);
		if (bannerWidth == 0) {
			bannerWidth = BaseCommonUtil.getScreenWidth(context);
		}
		if (bannerHight == 0) {
			bannerHight = bannerWidth / 2;
		}
		views = new ArrayList<View>();
		for (int i = 0; i < bannerModels.size(); i++) {
			BannerModel teModel = bannerModels.get(i);
			ImageView img = new ImageView(context);
			img.setScaleType(ScaleType.CENTER_CROP);
			// img.setScaleType(ScaleType.FIT_XY);
			if (teModel.getType() == BannerModel.TYPE_NET) {
				FileUtil.setImage(img, teModel.getImgUrl(),
						new AsyncImageLoader(), R.drawable.default_loading);
			} else {
				img.setImageResource(teModel.getImgResourceId());
			}
			views.add(img);
		}
		indexs = new View[views.size()];
	}

	private void initView() {
		viewPager = new ViewPager(context);
		params = new LayoutParams(bannerWidth, bannerHight);
		viewPager.setLayoutParams(params);
		viewPager.setOnPageChangeListener(this);
		this.addView(viewPager);
		viewGroup = new LinearLayout(context);
		params = new LayoutParams(-2, -2);
		// viewGroup的位置
		if (possition == left_bottom) {
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		} else if (possition == middle_bottom) {
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			viewGroup.setPadding(0, 0, 0, 25);
		} else if (possition == right_bottom) {
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}

		viewGroup.setLayoutParams(params);
		this.addView(viewGroup);
		// viewpager
		ViewPagerAdapter adapter = new ViewPagerAdapter(views);
		viewPager.setAdapter(adapter);
		// viewGroup
		for (int i = 0; i < views.size(); i++) {
			// out(RelativeLayout)里面放置一个textView作为引索index
			RelativeLayout out = new RelativeLayout(context);
			indexs[i] = out;
			TextView index = new TextView(context);
			index.setTextColor(Color.WHITE);
			index.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			index.setText(i + "");
			params = new LayoutParams(-2, -2);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			index.setLayoutParams(params);
			out.addView(index);
			if (i == 0) {
				out.setBackgroundResource(R.drawable.slider_focus);
			} else {
				out.setBackgroundResource(R.drawable.slider_normal);
			}
			android.widget.LinearLayout.LayoutParams params_linear = new android.widget.LinearLayout.LayoutParams(
					BaseCommonUtil.dip2px(context, 30), BaseCommonUtil.dip2px(
							context, 15));
			if (i != 0) {
				params_linear.setMargins(-BaseCommonUtil.dip2px(context, 8), 0,
						0, 0);
			}
			out.setLayoutParams(params_linear);
			viewGroup.addView(out);
		}
	}

	/*隐藏indexViewGroup*/
	public void hideIndex() {
		if (viewGroup != null) {
			viewGroup.setVisibility(View.GONE);
		}
	}

	private void startMove() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (isContinue) {
					int what = atomicInteger.get() % views.size();
					handler.sendEmptyMessage(what);
					atomicInteger.incrementAndGet();
				}
			}
		};
		timer.schedule(task, 2000, speed * 1000);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < views.size(); i++) {
			if (i == arg0) {
				indexs[i].setBackgroundResource(R.drawable.slider_focus);
			} else {
				indexs[i].setBackgroundResource(R.drawable.slider_normal);
			}
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			isContinue = false;
			break;
		case MotionEvent.ACTION_UP:
			isContinue = true;
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
