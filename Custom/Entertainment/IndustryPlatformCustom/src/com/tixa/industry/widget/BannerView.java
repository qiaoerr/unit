package com.tixa.industry.widget;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.adapter.PAdapter;
import com.tixa.industry.config.ConfigData;
import com.tixa.industry.model.Advert;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.CommonUtil;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;

/**
 * 
 * @author banner 广告
 * 
 */
public class BannerView extends LinearLayout {
	private Context mContext;
	private ViewGroup group;
	private ViewPager viewPager;
	private AsyncImageLoader loader;
	private int pageStyle; // 广告类型 1 无边框 2 有边框 3 有标题
	private ArrayList<View> mListViews;
	private ArrayList<Advert> adList;
	private View header;
	private ImageView imageView = null;
	private ImageView[] imageViews = null;
	private LinearLayout pagertextliner;
	private PAdapter pAdapter;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	private int speed = 3;
	private int width = 0;
	private int height = 0;

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

	public BannerView(Context context, ArrayList<Advert> adList, int pageStyle) {
		super(context);
		this.mContext = context;
		this.pageStyle = pageStyle;
		this.adList = adList;
		init();
	}

	public BannerView(Context context, ArrayList<Advert> adList, int pageStyle,
			int height) {
		super(context);
		this.mContext = context;
		this.pageStyle = pageStyle;
		this.adList = adList;
		this.height = height;
		init();
	}

	public BannerView(Context context, ArrayList<Advert> adList, int pageStyle,
			int width, int height) {
		super(context);
		this.mContext = context;
		this.pageStyle = pageStyle;
		this.adList = adList;
		this.width = width;
		this.height = height;
		init();
	}

	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.banner_view, this);

		loader = new AsyncImageLoader();
		group = (ViewGroup) view.findViewById(R.id.viewGroup);

		viewPager = (ViewPager) view.findViewById(R.id.ad_advpager);
		if (width == 0) {
			width = CommonUtil.getWidthPx(mContext);
		}
		// height = CommonUtil.getHeightPx(mContext);
		// height = (int) (width/3)*2 ; // 2/3
		if (height == 0) {
			height = (int) width / 2; // 1/2
		}
		L.e("分辨率是  width= " + width + " height= " + height);
		LinearLayout.LayoutParams pars = new LayoutParams(width, height);

		viewPager.setLayoutParams(pars);

		mListViews = new ArrayList<View>();
		if (adList != null && adList.size() > 0) {
			for (int i = 0; i < adList.size(); i++) {
				Advert ad = adList.get(i);
				if (pageStyle == ConfigData.AD_SHOWTYPE_FRAME) {
					header = (View) LayoutInflater.from(mContext).inflate(
							R.layout.view_paper2, null);
				} else {
					header = (View) LayoutInflater.from(mContext).inflate(
							R.layout.view_paper3, null);
					pagertextliner = (LinearLayout) header
							.findViewById(R.id.pagertextliner);
				}
				if (pageStyle == ConfigData.AD_SHOWTYPE_DEFAULT) {
					pagertextliner.setVisibility(View.GONE);
				}
				ImageView img = (ImageView) header
						.findViewById(R.id.imageView1);
				if (img != null) {
					FileUtil.setImage(img, ad.getImgPath(), loader,
							R.drawable.default_ad);
				}
				TextView number = (TextView) header.findViewById(R.id.number);
				if (number != null) {
					number.setText(i + 1 + "/" + adList.size());
				}
				mListViews.add(header);
			}
		}

		imageViews = new ImageView[mListViews.size()];
		if (pageStyle != ConfigData.AD_SHOWTYPE_TITLE) {
			for (int i = 0; i < mListViews.size(); i++) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new LayoutParams(10, 10));
				imageView.setPadding(5, 5, 5, 5);
				imageViews[i] = imageView;

				if (i == 0) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_focus);
				} else {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);
				}
				group.addView(imageViews[i]);
			}
		}

		pAdapter = new PAdapter(mListViews);
		viewPager.setAdapter(pAdapter);
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});

	}

	// 使广告动起来
	public void show() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}

		}).start();
	}

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > adList.size() - 1) {
			what.getAndAdd(-adList.size());
		}
		try {
			Thread.sleep(speed * 1000); // 轮换速度
		} catch (InterruptedException e) {

		}
	}

	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			if (pageStyle != ConfigData.AD_SHOWTYPE_TITLE) {
				for (int i = 0; i < imageViews.length; i++) {
					imageViews[arg0]
							.setBackgroundResource(R.drawable.banner_dian_focus);
					if (arg0 != i) {
						imageViews[i]
								.setBackgroundResource(R.drawable.banner_dian_blur);
					}
				}
			}
		}

	}

}
