package com.tixa.industry.widgetCustom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.R;

/**
 * TopBar
 * 
 * @author administrator
 * @version 1.0
 */
public class TopBar extends RelativeLayout implements OnClickListener {
	private Context context;
	private ImageView btn_left;
	private TextView text_mid;
	private TextView text_mid_down;
	private ImageView btn_right;
	private TopBarListener topBarListener;

	public TopBar(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.layout_topbar, this);
		btn_left = (ImageView) findViewById(R.id.btn_left);
		text_mid = (TextView) findViewById(R.id.text_mid);
		text_mid_down = (TextView) findViewById(R.id.text_mid_down);
		btn_right = (ImageView) findViewById(R.id.btn_right);
		btn_left.setOnClickListener(this);
		btn_right.setOnClickListener(this);
	}

	public void setShowConfig(String title, int res1, int res2) {
		this.setBackgroundResource(R.drawable.bg_header);
		text_mid.setText(title);
		text_mid.setTextColor(getResources().getColor(R.color.topbar_color));
		if (res1 > 0)

			btn_left.setImageResource(res1);
		if (res2 > 0)

			btn_right.setImageResource(res2);
	}

	public void setShowConfig(String title, String title_down, int res1,
			int res2) {
		this.setBackgroundResource(R.drawable.bg_header);
		text_mid.setText(title);
		text_mid.setTextColor(getResources().getColor(R.color.topbar_color));
		text_mid_down.setText(title_down);
		text_mid_down.setTextColor(getResources()
				.getColor(R.color.topbar_color));
		text_mid_down.setVisibility(View.VISIBLE);
		if (res1 > 0)

			btn_left.setImageResource(res1);
		if (res2 > 0)

			btn_right.setImageResource(res2);
	}

	public void setTopBarListener(TopBarListener topBarListener) {
		this.topBarListener = topBarListener;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_left) {
			if (topBarListener != null)
				topBarListener.btnLeftOnClick();
		}
		if (v.getId() == R.id.btn_right) {
			if (topBarListener != null)
				topBarListener.btnRightOnClick();
		}
	}

	public interface TopBarListener {
		public void btnLeftOnClick();

		public void btnRightOnClick();
	}
}
