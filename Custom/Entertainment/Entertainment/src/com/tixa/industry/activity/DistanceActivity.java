package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.tixa.industry.R;

public class DistanceActivity extends Activity implements
		OnCheckedChangeListener {
	private Context context;
	private CheckBox checkBox_500;
	private CheckBox checkBox_1k;
	private CheckBox checkBox_2k;
	private CheckBox checkBox_5k;
	private CheckBox checkBox_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_distance);
		context = this;
		initView();
		initData();
	}

	private void initView() {
		checkBox_500 = (CheckBox) findViewById(R.id.checkBox_one);
		checkBox_1k = (CheckBox) findViewById(R.id.checkBox_two);
		checkBox_2k = (CheckBox) findViewById(R.id.checkBox_three);
		checkBox_5k = (CheckBox) findViewById(R.id.checkBox_four);
		checkBox_all = (CheckBox) findViewById(R.id.checkBox_five);
		checkBox_500.setOnCheckedChangeListener(this);
		checkBox_1k.setOnCheckedChangeListener(this);
		checkBox_2k.setOnCheckedChangeListener(this);
		checkBox_5k.setOnCheckedChangeListener(this);
		checkBox_all.setOnCheckedChangeListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}

	public void func(View view) {
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView.getId() == R.id.checkBox_five) {
			if (isChecked) {
				checkBox_500.setChecked(true);
				checkBox_1k.setChecked(true);
				checkBox_2k.setChecked(true);
				checkBox_5k.setChecked(true);
				Intent intent = new Intent();
				intent.putExtra("result", "all");
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
		if (buttonView.getId() == R.id.checkBox_four) {
			if (isChecked) {
				checkBox_500.setChecked(true);
				checkBox_1k.setChecked(true);
				checkBox_2k.setChecked(true);
				Intent intent = new Intent();
				intent.putExtra("result", "5k");
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				checkBox_all.setChecked(false);
			}
		}
		if (buttonView.getId() == R.id.checkBox_three) {
			if (isChecked) {
				checkBox_500.setChecked(true);
				checkBox_1k.setChecked(true);
				Intent intent = new Intent();
				intent.putExtra("result", "2k");
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				checkBox_5k.setChecked(false);
				checkBox_all.setChecked(false);
			}
		}
		if (buttonView.getId() == R.id.checkBox_two) {
			if (isChecked) {
				checkBox_500.setChecked(true);
				Intent intent = new Intent();
				intent.putExtra("result", "1k");
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				checkBox_2k.setChecked(false);
				checkBox_5k.setChecked(false);
				checkBox_all.setChecked(false);
			}
		}
		if (buttonView.getId() == R.id.checkBox_one) {
			if (isChecked) {
				Intent intent = new Intent();
				intent.putExtra("result", "500");
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				checkBox_1k.setChecked(false);
				checkBox_2k.setChecked(false);
				checkBox_5k.setChecked(false);
				checkBox_all.setChecked(false);
			}
		}

	}
}
