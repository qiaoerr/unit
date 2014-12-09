package com.example.androidtesto;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidtesto.CustomSiftView.DealwithOnItemClick;
import com.example.androidtesto.CustomSiftView.TabShowRule;

public class MainActivity extends Activity {
	CustomSiftView customSiftView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InputStream inputStream = getResources().openRawResource(R.drawable.logo);
		Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		((ImageView) findViewById(R.id.iv_image)).setImageBitmap(bitmap);

		customSiftView = (CustomSiftView) findViewById(R.id.customSiftView);
		ArrayList<SiftSoftItem> datas1 = new ArrayList<SiftSoftItem>();
		for (int i = 0; i < 8; i++) {
			datas1.add(new SiftSoftItem("tab1" + i));
		}
		ArrayList<SiftSoftItem> datas2 = new ArrayList<SiftSoftItem>();
		for (int i = 0; i < 8; i++) {
			datas2.add(new SiftSoftItem("tab2" + i));
		}
		ArrayList<SiftSoftItem> datas3 = new ArrayList<SiftSoftItem>();
		for (int i = 0; i < 8; i++) {
			datas3.add(new SiftSoftItem("tab3" + i));
		}
		ArrayList<SiftSoftItem> datas4 = new ArrayList<SiftSoftItem>();
		for (int i = 0; i < 8; i++) {
			datas4.add(new SiftSoftItem("tab4" + i));
		}
		customSiftView.setData(datas1, datas2, datas3, datas4);
		customSiftView.setListBackView(R.drawable.soft_left, R.drawable.soft_second,
				R.drawable.soft_third, R.drawable.soft_right);
		customSiftView.setTabContent("one", "two", "three", "four");
		customSiftView.setMaskView(findViewById(R.id.over_view));
		customSiftView.setTabShowRule(new TabShowRule() {

			@Override
			public String getTabShowContent(String str, int index) {
				if (index == 0) {
					return str + "@";
				} else {
					return str;
				}
			}
		});
		customSiftView.setDealwithOnItemClick(new DealwithOnItemClick() {

			@Override
			public void dealwith(int datasIndex, int position) {
				Toast.makeText(MainActivity.this,
						"datasIndex:" + datasIndex + "" + "position:" + position, 1).show();
			}
		});

	}

	public void func(View v) {
		startActivityForResult(new Intent(MainActivity.this, A.class), 100);

	}

	/**
	 * @Description: TODO
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			System.out.println("RESULT_OK");
		} else if (resultCode == RESULT_CANCELED) {
			System.out.println("RESULT_CANCELED");
		} else if (resultCode == RESULT_FIRST_USER) {
			System.out.println("RESULT_CANCELED");
		}
		System.out.println("requestCode: " + requestCode);
		System.out.println("resultCode: " + resultCode);
	}
}
