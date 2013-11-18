package com.tixa.industry.reg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tixa.industry.R;

public class DroidReg6 extends Fragment implements OnClickListener {
	private TextView hint;
	private ImageView img;
	private Button checkBtn;
	private Activity context;
	private View view;
	private String onOkActivity;
	private String emailAddress, pwd;
	private ClickListener6 clickListener;
	private String email;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_reg_4, container, false);
		context = getActivity();
		onOkActivity = context.getIntent().getStringExtra("onOkActivity");
		initView();
		return view;
	}

	private void initView() {
		checkBtn = (Button) view.findViewById(R.id.regComplete);
		checkBtn.setOnClickListener(this);
		hint = (TextView) view.findViewById(R.id.hint_text);
		hint.setGravity(Gravity.LEFT);
		hint.setText("恭喜你注册成功，已向您的邮箱发送一封电子邮件，现在就去验证吧！");
		img = (ImageView) view.findViewById(R.id.success_img);
		// img.setVisibility(View.GONE);
		email = getArguments().getString("email");
	}

	public ClickListener6 getClickListener() {
		return clickListener;
	}

	public void setClickListener(ClickListener6 clickListener) {
		this.clickListener = clickListener;
	}

	public interface ClickListener6 {
		public void onclick(View view, String email);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.regComplete) {
			if (clickListener != null)
				clickListener.onclick(checkBtn, email);
		}
	}
}