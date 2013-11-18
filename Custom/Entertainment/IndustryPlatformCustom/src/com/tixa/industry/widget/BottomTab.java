package com.tixa.industry.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.util.StrUtil;

public class BottomTab extends RelativeLayout implements OnClickListener {

	public static final boolean DEBUG = true;
	public static final String TAG = "BottomTab";
	private Context mContext;
	private RelativeLayout mTab;
	// 文字
	private TextView tab_text;
	// 新消息
	private TextView tab_new;
	// icon
	private ImageView tab_icon;

	private ClickListener mClickListener;
	private RefreshListener mRefreshListener;
	private BottomTabBar parent;
	private boolean canCheck = true;
	// 是否选中
	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		if (!this.canCheck) {
			return;
		}
		tab_icon.setSelected(checked);
		if (this.checked) {
			if (tab_text != null) {
				tab_text.setTextColor(getResources().getColor(R.color.white));
			}
			setBackgroundResource(R.drawable.tab_check);
		} else {
			if (tab_text != null) {
				tab_text.setTextColor(getResources().getColor(
						R.color.maintab_uncheck_text_color));
			}
			setBackgroundColor(mContext.getResources().getColor(
					R.color.transparent));
		}

	}

	// public void onChekedChange( BottomTab checkBt ) {
	// for (int i = 0; i < parent.getChildCount(); i++) {
	// BottomTab bt = (BottomTab) parent.getChildAt(i);
	// if ( checkBt.getId() == bt.getId()) {
	// bt.setChecked(true);
	// } else {
	// bt.setChecked(false);
	// }
	// }
	// }
	public ClickListener getmClickListener() {
		return mClickListener;
	}

	public void setmClickListener(ClickListener mClickListener) {
		this.mClickListener = mClickListener;
	}

	public BottomTab(Context context) {
		this(context, null);
	}

	public BottomTab(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BottomTab(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.bottom_tab, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(44,
				LayoutParams.MATCH_PARENT, 1);
		this.setLayoutParams(lp);
		mTab = (RelativeLayout) findViewById(R.id.bottom_tab);
		mTab.setGravity(Gravity.CENTER_HORIZONTAL);
		tab_text = (TextView) findViewById(R.id.tab_text);
		tab_new = (TextView) findViewById(R.id.tab_new);
		tab_icon = (ImageView) findViewById(R.id.tab_icon);
		mTab.setOnClickListener(this);
		tab_text.setTextColor(getResources().getColor(
				R.color.maintab_uncheck_text_color));
		mTab.setBackgroundColor(getResources().getColor(R.color.transparent));
		if (this.getParent() != null
				&& this.getParent() instanceof BottomTabBar) {
			parent = (BottomTabBar) this.getParent();
		}
	}

	public BottomTab(Activity context, BottomTabBar bb, String title,
			int resId, boolean canCheck, ClickListener clistener) {
		this(context, null);
		setText(title);
		setIcon(resId);
		this.canCheck = canCheck;
		setmClickListener(clistener);
		parent = bb;
		parent.addView(this);
	}

	public BottomTab(Activity context, BottomTabBar bb, String title,
			int resId, ClickListener clistener) {
		this(context, bb, title, resId, true, clistener);
	}

	public void setTextColor(int color) {
		if (tab_text != null) {
			tab_text.setTextColor(color);
		}
	}

	public void setText(String text) {
		
		if(text == null) {
			tab_text.setVisibility(View.GONE);
		}else{		
			if (tab_text != null) {
				tab_text.setText(text);
			}
		}
	}

	public void setTextVisib(int visibility) {
		if (tab_text != null) {
			tab_text.setVisibility(visibility);
		}
	}

	public void setText(int resId) {
		if (tab_text != null) {
			tab_text.setText(getResources().getText(resId));
		}
	}

	public void setIcon(int resid) {
		if (tab_icon != null) {
			tab_icon.setBackgroundResource(resid);
		}
	}

	public void setIcon(Drawable d) {
		if (tab_icon != null) {
			tab_icon.setBackgroundDrawable(d);
		}
	}

	public void setNewVisible(int type) {
		if (tab_new != null) {
			tab_new.setVisibility(type);
			tab_new.setBackgroundResource(R.drawable.star0);
		}
	}

	public void setNewNum(int num) {
		if (tab_new != null) {
			if (num > 0) {
				tab_new.setText(num + "");
				tab_new.setBackgroundResource(R.drawable.p_count);
				tab_new.setVisibility(View.VISIBLE);
			} else {
				tab_new.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void setNewText(String text) {
		if (tab_new != null) {
			if (StrUtil.isNotEmpty(text)) {
				tab_new.setText(text);
			} else {
				tab_new.setBackgroundResource(R.drawable.p_count_new);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.bottom_tab) {
			click();
		}
	}

	public void refresh() {
		if (parent != null) {
			for (int i = 0; i < parent.getChildCount(); i++) {
				BottomTab bt = (BottomTab) parent.getChildAt(i);
				if (bt == this) {
					bt.setChecked(true);
				} else {
					bt.setChecked(false);
				}
			}
			if (parent.getOnCheckedChangeListener() != null) {
				parent.getOnCheckedChangeListener()
						.onCheckedChanged(this, true);
			}
		}
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh();
		}
	}

	public void click() {
		if (parent != null) {
			for (int i = 0; i < parent.getChildCount(); i++) {
				BottomTab bt = (BottomTab) parent.getChildAt(i);
				if (bt == this) {
					bt.setChecked(true);
				} else {
					bt.setChecked(false);
				}
			}
			if (parent.getOnCheckedChangeListener() != null) {
				parent.getOnCheckedChangeListener()
						.onCheckedChanged(this, true);
			}
		}
		if (mClickListener != null) {
			mClickListener.onClick();
		}
	}

	public interface RefreshListener {
		public abstract void onRefresh();
	}

	public interface ClickListener {
		public abstract void onClick();
	}

	public boolean isCanCheck() {
		return canCheck;
	}

	public void setCanCheck(boolean canCheck) {
		this.canCheck = canCheck;
	}

	public RefreshListener getmRefreshListener() {
		return mRefreshListener;
	}

	public void setmRefreshListener(RefreshListener mRefreshListener) {
		this.mRefreshListener = mRefreshListener;
	}
}
