package com.example.androidaa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.androidaa.Rr.DrawableUtils;
import com.example.androidaa.Rr.ImageTool;

public class MainActivity extends Activity {
	private Context context;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		initView();
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		btn = (Button) findViewById(R.id.btn);
		btn.setText(DrawableUtils.getString(context, "kaixin"));
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		// imageView1.setImageBitmap(DrawableUtils.getBitmap(context, "test"));
		// imageView1.setBackgroundDrawable(new BitmapDrawable(getResources(),
		// DrawableUtils.getBitmap(context, "test")));
		try {
			// imageView1.setBackgroundDrawable(ImageTool.decodeDrawableFromAsset(
			// context, "test.9.png"));
			// imageView2.setBackgroundDrawable(new
			// BitmapDrawable(getResources(),
			// ImageTool.decodeFromAsset(context, "test.9.png")));
			// imageView1.setImageBitmap(ImageTool.decodeFromAsset(context,
			// "test.9.png"));
			imageView1.setImageDrawable(ImageTool.decodeDrawableFromAsset(
					context, "test4.9.png"));
			// imageView2.setImageDrawable(new BitmapDrawable(getResources(),
			// ImageTool.decodeFromAsset(context, "test.9.png")));
			imageView2.setImageBitmap(ImageTool.decodeFromAsset(context,
					"test4.9.png"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			imageView3.setImageBitmap(DrawableUtils.getBitmap("psb.png"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void func(View v) {
		System.out.println("enter");
		Intent intent = new Intent(Intent.ACTION_EDIT);
		// sendBroadcast(intent);
		startActivity(intent);
	}
}
