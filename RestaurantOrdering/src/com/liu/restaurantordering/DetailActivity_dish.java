package com.liu.restaurantordering;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity_dish extends Activity {
	private Bundle bundle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		bundle = getIntent().getExtras();
		setContentView(getView());
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		TextView textView = (TextView) findViewById(R.id.text);
		textView.setText(bundle.getString("dishName"));
	}

	private View getView() {
		LayoutInflater inflater = LayoutInflater.from(this);
		FrameLayout frameLayout = (FrameLayout) inflater.inflate(
				R.layout.detail_dish, null, false);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int x = dm.widthPixels;
		int y = dm.heightPixels;
		ImageView bigImage = (ImageView) frameLayout
				.findViewById(R.id.big_imageview);
		bigImage.setLayoutParams(new FrameLayout.LayoutParams((x - 60),
				y * 2 / 5));
		byte[] bytes = bundle.getByteArray("bitmap");
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		bigImage.setImageBitmap(bitmap);
		setLocation(bigImage, 30, 30);

		ImageView smallImage = (ImageView) frameLayout
				.findViewById(R.id.small_imageview);
		setLocation(smallImage, x * 3 / 4, y / 14);

		float xs = smallImage.getTranslationX();
		float ys = smallImage.getTranslationY();
		TextView likeNum = (TextView) frameLayout.findViewById(R.id.likeNum);
		setLocation(likeNum, xs + 6, ys + 9);
		setData(likeNum, "likeNum");

		Button btn = (Button) frameLayout.findViewById(R.id.btn);
		setLocation(btn, (x - 60) / 2, y * 42 / 100);
		// Îª°´Å¥ÉèÖÃ¼àÌýÆ÷
		btn.setOnClickListener(new MyListener());

		LinearLayout linearLayout = (LinearLayout) frameLayout
				.findViewById(R.id.detail_of_dish);
		linearLayout.setLayoutParams(new FrameLayout.LayoutParams((x - 60),
				y * 9 / 20));
		setLocation(linearLayout, 30, y * 2 / 5 + 30);

		TextView dishinf = (TextView) frameLayout.findViewById(R.id.dishinf);
		setData(dishinf, "northwest");

		TextView dishinfprice = (TextView) frameLayout
				.findViewById(R.id.dishinfprice);
		setData(dishinfprice, "northeast");

		TextView dishinfres = (TextView) frameLayout
				.findViewById(R.id.dishinfres);
		setData(dishinfres, "southwest");

		TextView dishinfaddress = (TextView) frameLayout
				.findViewById(R.id.dishinfaddress);
		setData(dishinfaddress, "address");

		TextView dishinftaste = (TextView) frameLayout
				.findViewById(R.id.dishinftaste);
		dishinftaste.setMovementMethod(new ScrollingMovementMethod());
		setData(dishinftaste, "comment");

		return frameLayout;

	}

	private void setLocation(View view, float translationx, float translationy) {
		view.setTranslationX(translationx);
		view.setTranslationY(translationy);
	}

	private void setData(TextView view, String key) {
		if (key.endsWith("northwest")) {
			view.setText(bundle.getString(key)
					+ getResources().getString(R.string.xiangqing));
		} else if (key.endsWith("northeast")) {
			view.setText(getResources().getString(R.string.price)
					+ bundle.getString(key));
		} else if (key.endsWith("southwest")) {
			view.setText(getResources().getString(R.string.restaurant)
					+ bundle.getString(key));
		} else if (key.endsWith("address")) {
			view.setText(getResources().getString(R.string.address)
					+ bundle.getString(key));
		} else if (key.endsWith("comment")) {
			view.setText(getResources().getString(R.string.comment)
					+ bundle.getString(key));
		} else if (key.equals("likeNum")) {
			view.setText(bundle.getString(key));
		}
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {

		}
	}
}
