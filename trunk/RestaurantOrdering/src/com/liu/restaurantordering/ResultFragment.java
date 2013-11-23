package com.liu.restaurantordering;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ResultFragment extends android.support.v4.app.Fragment {
	private Bundle bundle = null;
	private boolean isclicked = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle = getArguments();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		FrameLayout frameLayout = (FrameLayout) inflater.inflate(
				R.layout.result, null, false);
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
		// Ìí¼ÓÏêÇé¼àÌýÆ÷
		bigImage.setOnClickListener(new MyListener());

		ImageView smallImage = (ImageView) frameLayout
				.findViewById(R.id.small_imageview);
		setLocation(smallImage, x * 3 / 4, y / 14);

		float xs = smallImage.getTranslationX();
		float ys = smallImage.getTranslationY();
		// float sImageX = smallImage.getPivotX();
		// float sImageY = smallImage.getPivotY();
		// int sImageHeight = smallImage.getHeight();
		// int sImageWidth = smallImage.getWidth();
		// System.out.println(xs + "  " + ys + "  " + sImageX + "  " + sImageY
		// + "  " + sImageHeight + "  " + sImageWidth);
		final TextView likeNum = (TextView) frameLayout
				.findViewById(R.id.likeNum);
		setLocation(likeNum, xs + 6, ys + 9);
		setData(likeNum, "likeNum");

		smallImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isclicked && bundle.getBoolean("isRestaurant")) {
					bundle.putString("likeNum",
							Integer.parseInt(bundle.getString("likeNum")) + 1
									+ "");
					setData(likeNum, "likeNum");
					ParseQuery query = new ParseQuery("Restaurant");
					query.whereEqualTo("restaurantId",
							bundle.getInt("restaurantId"));
					query.findInBackground(new FindCallback() {

						@Override
						public void done(List<ParseObject> objects,
								ParseException e) {
							ParseObject restaurant = objects.get(0);
							restaurant.put("likeNum", Integer.parseInt(bundle
									.getString("likeNum")));
							restaurant.saveInBackground();
						}
					});
					isclicked = true;
				} else if (!isclicked && !bundle.getBoolean("isRestaurant")) {

					bundle.putString("likeNum",
							Integer.parseInt(bundle.getString("likeNum")) + 1
									+ "");
					setData(likeNum, "likeNum");
					ParseQuery query = new ParseQuery("Foods");
					query.whereEqualTo("restaurantId",
							bundle.getInt("restaurantId"));
					query.whereEqualTo("name", bundle.getString("northwest"));
					query.findInBackground(new FindCallback() {

						@Override
						public void done(List<ParseObject> objects,
								ParseException e) {
							ParseObject food = objects.get(0);
							food.put("likeNum", Integer.parseInt(bundle
									.getString("likeNum")));
							food.saveInBackground();
						}
					});
					isclicked = true;

				}
			}
		});

		View panel = frameLayout.findViewById(R.id.panel);
		panel.setLayoutParams(new FrameLayout.LayoutParams((x - 60), y * 2 / 5));
		setLocation(panel, 30, (y * 2 / 5) + 30);

		Button btn = (Button) frameLayout.findViewById(R.id.btn);
		setLocation(btn, (x - 60) / 2, y * 42 / 100);
		// Îª°´Å¥ÉèÖÃ¼àÌýÆ÷
		btn.setOnClickListener(new MyListener());

		TextView dishName = (TextView) frameLayout.findViewById(R.id.dishName);
		setLocation(dishName, 40, y * 10 / 20 - 10);
		setData(dishName, "northwest");

		TextView restaurantName = (TextView) frameLayout
				.findViewById(R.id.restaurantName);
		setLocation(restaurantName, 40, y * 11 / 20);
		setData(restaurantName, "southwest");

		if (!bundle.getBoolean("isRestaurant")) {
			TextView price = (TextView) frameLayout.findViewById(R.id.price);
			setLocation(price, x - 120, y * 10 / 20);
			setData(price, "northeast");
		}

		TextView scanNum = (TextView) frameLayout.findViewById(R.id.scanNum);
		setLocation(scanNum, x - 120, y * 11 / 20);
		setData(scanNum, "southeast");

		return frameLayout;
	}

	private void setLocation(View view, float translationx, float translationy) {
		view.setTranslationX(translationx);
		view.setTranslationY(translationy);
	}

	private void setData(TextView view, String key) {
		view.setText(bundle.getString(key));
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtras(bundle);
			if (!bundle.getBoolean("isRestaurant")) {
				intent.setClass(getActivity(), DetailActivity_dish.class);
			} else {
				intent.setClass(getActivity(), DetailActivity_restaurant.class);
			}
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.zoomin,
					R.anim.zoomout);
		}
	}
}
