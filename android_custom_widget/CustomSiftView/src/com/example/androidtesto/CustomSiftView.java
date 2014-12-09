/**
 * @Title: CustomSiftView.java
 * @Package com.example.androidtesto
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:soufun
 * 
 * @author liu
 * @date 2014-12-8 下午4:26:52
 * @version V1.0
 */

package com.example.androidtesto;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: CustomSiftView
 * @Description: tab high 45dp
 * 
 */

public class CustomSiftView extends RelativeLayout implements AnimationListener,
		OnItemClickListener {
	private Context mContext;
	private LinearLayout top_sift;
	private int eleNum = 3;// 默认值3 最大值6
	private TextView[] tvViews;
	private ListView[] lvViews;
	private ArrayList<ArrayList<SiftSoftItem>> dataLists;
	private BaseAdapter[] adapters;
	private View maskView;
	private ScaleAnimation enterAnimation;
	private ScaleAnimation exitAnimation;
	private AlphaAnimation alphaEnterAnimation;
	private AlphaAnimation alphaExitAnimation;
	private long aniEnterDuration = 300;
	private long aniExitDuration = 300;
	private TabShowRule tabShowRule;
	private DealwithOnItemClick dealwithOnItemClick;

	public CustomSiftView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		initEleNum(context, attrs);
		initData();
		initView();
	}

	public CustomSiftView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		initEleNum(context, attrs);
		initData();
		initView();
	}

	public CustomSiftView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		initData();
		initView();
	}

	/**
	 * @Description: TODO
	 */

	private void initData() {
		tvViews = new TextView[eleNum];
		lvViews = new ListView[eleNum];
		dataLists = new ArrayList<ArrayList<SiftSoftItem>>();
		adapters = new BaseAdapter[eleNum];
		// default tabShowRule
		tabShowRule = new TabShowRule() {

			@Override
			public String getTabShowContent(String str, int index) {
				return str;
			}
		};
		// init Animation
		enterAnimation = new ScaleAnimation(1, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		enterAnimation.setAnimationListener(this);
		enterAnimation.setInterpolator(new DecelerateInterpolator());
		enterAnimation.setDuration(aniEnterDuration);
		exitAnimation = new ScaleAnimation(1, 1, 1, 0, Animation.RELATIVE_TO_SELF, 1,
				Animation.RELATIVE_TO_SELF, 0);
		exitAnimation.setAnimationListener(this);
		exitAnimation.setInterpolator(new AccelerateInterpolator());
		exitAnimation.setDuration(aniExitDuration);
		alphaEnterAnimation = new AlphaAnimation(0f, 0.75f);
		alphaEnterAnimation.setFillAfter(true);
		alphaExitAnimation = new AlphaAnimation(0.75f, 0f);
		alphaEnterAnimation.setDuration(aniEnterDuration);
		alphaExitAnimation.setDuration(aniExitDuration);
	}

	/**
	 * @throws Exception
	 * @Description: 设置下拉list的数据
	 */
	public void setData(ArrayList<SiftSoftItem>... datas) {
		if (datas.length < eleNum) {
			try {
				throw new Exception("setData 方法设置的数据有误");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dataLists.clear();
		for (int i = 0; i < datas.length; i++) {
			dataLists.add(i, datas[i]);
		}
		fillViewData();
		setListBackView();
	}

	/**
	 * @throws Exception
	 * @Description: 设置初始Tab显示的内容
	 */
	public void setTabContent(String... tabTitle) {
		if (tabTitle.length < eleNum) {
			try {
				throw new Exception("setTabContent 方法设置的数据有误");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < eleNum; i++) {
			tvViews[i].setText(tabTitle[i]);
		}
	}

	/**
	 * @throws Exception
	 * @Description: 设置下拉list的背景
	 */
	private void setListBackView() {
		switch (eleNum) {
		case 6:
			setListBackView(R.drawable.soft_left, R.drawable.soft_second_sec,
					R.drawable.soft_second, R.drawable.soft_third, R.drawable.soft_third_thi,
					R.drawable.soft_right);
			break;
		case 5:
			setListBackView(R.drawable.soft_left, R.drawable.soft_second_sec,
					R.drawable.soft_middle, R.drawable.soft_third_thi, R.drawable.soft_right);
			break;
		case 4:
			setListBackView(R.drawable.soft_left, R.drawable.soft_second, R.drawable.soft_third,
					R.drawable.soft_right);
			break;
		case 3:
			setListBackView(R.drawable.soft_left, R.drawable.soft_middle, R.drawable.soft_right);
			break;
		case 2:
			setListBackView(R.drawable.soft_left, R.drawable.soft_right);
			break;
		case 1:
			setListBackView(R.drawable.soft_middle);
			break;
		default:
			break;
		}

	}

	/**
	 * @throws Exception
	 * @Description: 设置下拉list的背景
	 */
	private void setListBackView(int... resourceId) {
		for (int i = 0; i < resourceId.length; i++) {
			if (i >= eleNum) {
				break;
			}
			lvViews[i].setBackgroundResource(resourceId[i]);
		}

	}

	/**
	 * @Description: 设置MaskView
	 */
	public void setMaskView(View view) {
		maskView = view;
		maskView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < eleNum; i++) {
					if (lvViews[i].getVisibility() == View.VISIBLE) {
						lvViews[i].startAnimation(exitAnimation);
						break;
					}
				}
			}
		});
	}

	/**
	 * @Description: 自定义tab显示的内容
	 */
	public void setTabShowRule(TabShowRule rule) {
		this.tabShowRule = rule;
	}

	/**
	 * @Description:TODU
	 */
	public void setDealwithOnItemClick(DealwithOnItemClick onItemClick) {
		this.dealwithOnItemClick = onItemClick;
	}

	/**
	 * @Description: TODO
	 */

	private void fillViewData() {
		for (int i = 0; i < eleNum; i++) {
			adapters[i] = new SiftSoftItemAdapter(mContext, dataLists.get(i), 1);
			lvViews[i].setAdapter(adapters[i]);
		}

	}

	/**
	 * @Description: TODO
	 * @param context
	 * @param attrs
	 */

	private void initEleNum(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSiftView);
		eleNum = a.getInteger(R.styleable.CustomSiftView_eleNum, 3);
		eleNum = eleNum > 6 ? 6 : eleNum;
		a.recycle();
	}

	/**
	 * @Description: TODO
	 */
	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.custom_sift_view, this, true);
		top_sift = (LinearLayout) findViewById(R.id.top_sift);
		switch (eleNum) {
		case 6:
			createTabElement(5);
		case 5:
			createTabElement(4);
		case 4:
			createTabElement(3);
		case 3:
			createTabElement(2);
		case 2:
			createTabElement(1);
		case 1:
			createTabElement(0);
		default:
			break;
		}
	}

	/**
	 * @Description: TODO
	 */

	private void createTabElement(int index) {
		// 分割View
		android.widget.LinearLayout.LayoutParams lp_params = new android.widget.LinearLayout.LayoutParams(
				getFixDem(1.0f), getFixDem(30), 0);
		lp_params.gravity = Gravity.CENTER_VERTICAL;
		if (index >= 0 && index < eleNum - 1) {
			View vertical_line = new View(mContext);
			vertical_line.setBackgroundColor(Color.parseColor("#d9d9d9"));
			top_sift.addView(vertical_line, 0, lp_params);
		}
		// RelativeLayout
		RelativeLayout rl_temp = new RelativeLayout(mContext);
		lp_params = new android.widget.LinearLayout.LayoutParams(0,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 1);
		top_sift.addView(rl_temp, 0, lp_params);
		// register Listener
		rl_temp.setOnClickListener(new MyCustomListener(index));
		// TextView
		tvViews[index] = new TextView(mContext);
		tvViews[index].setText("tab" + index);
		tvViews[index].setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sift_down_arrow, 0);
		tvViews[index].setCompoundDrawablePadding(getFixDem(5));
		tvViews[index].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		LayoutParams rl_params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		rl_params.addRule(RelativeLayout.CENTER_IN_PARENT);
		rl_temp.addView(tvViews[index], rl_params);
		// listView
		rl_params = new LayoutParams(LayoutParams.MATCH_PARENT, getFixDem(320));
		rl_params.addRule(RelativeLayout.BELOW, R.id.hori_middle_line);
		lvViews[index] = new ListView(mContext);
		lvViews[index].setVerticalScrollBarEnabled(false);
		lvViews[index].setVisibility(View.GONE);
		this.addView(lvViews[index], rl_params);
		// register Listener
		lvViews[index].setOnItemClickListener(this);
	}

	private int getFixDem(float input) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input, getResources()
				.getDisplayMetrics());
	}

	/**
	 * @Description: TODO
	 * @param animation
	 */
	@Override
	public void onAnimationStart(Animation animation) {
		if (animation == enterAnimation) {
			if (maskView != null) {
				maskView.setVisibility(View.VISIBLE);
				maskView.startAnimation(alphaEnterAnimation);
			}

		} else {
			if (maskView != null) {
				maskView.setVisibility(View.GONE);
				maskView.startAnimation(alphaExitAnimation);
			}
		}
	}

	/**
	 * @Description: TODO
	 * @param animation
	 */
	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == exitAnimation) {
			for (int i = 0; i < eleNum; i++) {
				lvViews[i].setVisibility(View.GONE);
			}
		}
	}

	/**
	 * @Description: TODO
	 * @param animation
	 */
	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	/**
	 * @Description: TODO
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		for (int i = 0; i < eleNum; i++) {
			if (parent == lvViews[i]) {
				ArrayList<SiftSoftItem> temp = dataLists.get(i);
				for (int j = 0; j < temp.size(); j++) {
					SiftSoftItem sItem = temp.get(j);
					if (position == j) {
						sItem.checkStatus = 1;
						tvViews[i].setText(tabShowRule.getTabShowContent(sItem.itemName, i));
						if (dealwithOnItemClick != null) {
							dealwithOnItemClick.dealwith(i, position);
						}
					} else {
						sItem.checkStatus = 0;
					}
				}
				adapters[i].notifyDataSetChanged();
				lvViews[i].startAnimation(exitAnimation);
				break;
			}
		}
	}

	public interface TabShowRule {
		public String getTabShowContent(String str, int index);
	}

	public interface DealwithOnItemClick {
		public void dealwith(int datasIndex, int position);
	}

	class MyCustomListener implements OnClickListener {
		private int index = 0;

		public MyCustomListener(int index) {
			super();
			this.index = index;
		}

		/**
		 * @Description: TODO
		 * @param v
		 */

		@Override
		public void onClick(View v) {
			for (int i = 0; i < eleNum; i++) {
				if (index == i) {
					if (lvViews[i].getVisibility() == View.VISIBLE) {
						lvViews[i].startAnimation(exitAnimation);
					} else {
						lvViews[i].setVisibility(View.VISIBLE);
						lvViews[i].startAnimation(enterAnimation);
					}
				} else {
					lvViews[i].setVisibility(View.GONE);
				}
			}
		}
	}

}
