package com.tixa.industry.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.util.StrUtil;

public class TopBar extends RelativeLayout implements OnClickListener {
	private LayoutInflater inflater;
	private Context mContext;
	private Button button1;
	private Button button2;
	private Button button3;
	private ImageView i1, i2, i3;
	private EditText search;
	private TextView searchText;

	public Button getButton1() {
		return button1;
	}

	public Button getButton2() {
		return button2;
	}

	public Button getButton3() {
		return button3;
	}

	private TextView titleView;
	// private ImageView star;
	private ProgressBar progress;
	private String title;

	public TopBar(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.mContext = context;
		init();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		titleView.setText(title);
	}

	public void init() {
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_topbar_1, this);
		i1 = (ImageView) findViewById(R.id.button_image1);
		i2 = (ImageView) findViewById(R.id.button_image2);
		i3 = (ImageView) findViewById(R.id.button_image3);
		titleView = (TextView) findViewById(R.id.cloud_top_text1);
		button1 = (Button) findViewById(R.id.cloud_top_left_botton1);
		button2 = (Button) findViewById(R.id.cloud_top_left_second_botton);
		button3 = (Button) findViewById(R.id.cloud_top_right_botton1);
		progress = (ProgressBar) findViewById(R.id.cloud_top_right_progressBar);
		search = (EditText) findViewById(R.id.search);
		searchText = (TextView) findViewById(R.id.searchText);
		// star = (ImageView) findViewById(R.id.star);
		titleView.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		progress.setOnClickListener(this);
	}

	public void showSearch(String title, boolean show) {
		searchText.setText(title);
		titleView.setTextSize(20);
		this.setBackgroundResource(R.drawable.bg_header);
		if (show) {
			search.setVisibility(View.VISIBLE);
		} else {
			search.setVisibility(View.GONE);
		}
	}

	public void enableButton3(boolean enable) {
		if (enable) {
			i3.setVisibility(View.GONE);
		} else {
			i3.setVisibility(View.VISIBLE);
		}
	}

	public String getSearchKeyword() {
		return search.getText().toString();
	}

	public EditText getSearchEditText() {
		return search;
	}

	public void showConfig(String title, boolean enable1, boolean enable2,
			boolean enable3, boolean enable4) {

		titleView.setText(title);
		this.setBackgroundResource(R.drawable.bg_header);
		titleView.setGravity(Gravity.CENTER);
		titleView.setTextColor(getResources().getColor(R.color.topbar_color));
		titleView.setTextSize(20);

		if (enable1) {
			button1.setVisibility(View.VISIBLE);
		} else {
			button1.setVisibility(View.GONE);
		}
		if (enable2) {
			button2.setVisibility(View.VISIBLE);
		} else {
			button2.setVisibility(View.GONE);
		}
		if (enable3) {
			button3.setVisibility(View.VISIBLE);
		} else {
			button3.setVisibility(View.GONE);
		}
		if (enable4) {
			progress.setVisibility(View.VISIBLE);
		} else {
			progress.setVisibility(View.GONE);
		}
	}

	/** */
	public void showButtonImage(int res1, int res2, int res3) {
		if (res1 > 0) {
			i1.setImageResource(res1);
			button1.setText("");
			button1.setBackgroundColor(Color.TRANSPARENT);
		}
		if (res2 > 0) {
			i2.setImageResource(res2);
			button2.setText("");
			button2.setBackgroundColor(Color.TRANSPARENT);
		}
		if (res3 > 0) {
			i3.setImageResource(res3);
			button3.setText("");
			button3.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	public void showButtonText(String text1, String text2, String text3) {

		if (StrUtil.isNotEmpty(text1)) {
			button1.setText(text1);
		}
		if (StrUtil.isNotEmpty(text2)) {
			button2.setText(text2);
		}
		if (StrUtil.isNotEmpty(text3)) {
			button3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			button3.setText(text3);
		}
	}

	// public void showStar(boolean enable) {
	// if (enable) {
	// star.setVisibility(View.VISIBLE);
	// } else {
	// star.setVisibility(View.GONE);
	// }
	// }

	public void showButton1(boolean enable) {
		if (enable) {
			button1.setVisibility(View.VISIBLE);
		} else {
			button1.setVisibility(View.GONE);
		}
	}

	public TopBarListener getmListener() {
		return mListener;
	}

	public void setmListener(TopBarListener mListener) {
		this.mListener = mListener;
	}

	public void showButton2(boolean enable) {
		if (enable) {
			button2.setVisibility(View.VISIBLE);
		} else {
			button2.setVisibility(View.GONE);
		}
	}

	public void showButton3(boolean enable) {
		if (enable) {
			button3.setVisibility(View.VISIBLE);
		} else {
			button3.setVisibility(View.GONE);
		}
	}

	public void showProgressBar(boolean enable) {
		if (enable) {
			progress.setVisibility(View.VISIBLE);
			// i3.setVisibility((View.GONE));
		} else {
			progress.setVisibility(View.GONE);
			// i3.setVisibility((View.VISIBLE));
		}
	}

	public void imageConfig(int res1, int res2, int res3) {
		if (res1 > 0)
			button1.setBackgroundResource(res1);
		if (res2 > 0)
			button2.setBackgroundResource(res2);
		if (res3 > 0)
			button3.setBackgroundResource(res3);
	}

	public void imageConfig(String info1, String info2, String info3) {
		if (!"".equals(info1))
			button1.setText(info1);
		if (!"".equals(info2))
			button2.setText(info2);
		if (!"".equals(info3))
			button3.setText(info3);
	}

	public void performButton1Click(View view) {
		if (mListener != null) {
			mListener.onButton1Click(view);
		}
	}

	public void performButton2Click(View view) {
		if (mListener != null) {
			mListener.onButton2Click(view);
		}
	}

	public void performButton3Click(View view) {
		if (mListener != null) {
			mListener.onButton3Click(view);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == titleView.getId()) {
		} else if (v.getId() == button1.getId()) {
			performButton1Click(button1);
		} else if (v.getId() == button2.getId()) {
			performButton2Click(button2);
		} else if (v.getId() == button3.getId()) {
			performButton3Click(button3);
		} else if (v.getId() == progress.getId()) {
		}
	}

	private TopBarListener mListener;

	public interface TopBarListener {
		public void onButton1Click(View view);

		public void onButton2Click(View view);

		public void onButton3Click(View view);
	}

}
