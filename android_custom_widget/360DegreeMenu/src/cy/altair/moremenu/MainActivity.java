package cy.altair.moremenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import cy.altair.view.MenuItemView;
import cy.altair.view.MyAnimations;
import cy.altair.view.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private MenuItemView myViewLT;
	private ImageView imgPlusLT;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		findViewById(R.id.relLayLT).setOnClickListener(this);
		// 获得对象
		myViewLT = (MenuItemView) findViewById(R.id.myViewLT);
		// 设置半径
		myViewLT.setRadius(130);
		imgPlusLT = (ImageView) findViewById(R.id.imgPlusLT);
		imgPlusLT = (ImageView) findViewById(R.id.imgPlusLT);
		setMenuItemView();
	}

	private void setMenuItemView() {
		ImageView img1 = new ImageView(this);
		img1.setBackgroundResource(R.drawable.composer_music);
		ImageButton imgBtn2 = new ImageButton(this);
		imgBtn2.setBackgroundResource(R.drawable.composer_camera);
		ImageButton imgBtn3 = new ImageButton(this);
		imgBtn3.setBackgroundResource(R.drawable.composer_place);
		ImageButton imgBtn4 = new ImageButton(this);
		imgBtn4.setBackgroundResource(R.drawable.composer_sleep);
		ImageButton imgBtn5 = new ImageButton(this);
		imgBtn5.setBackgroundResource(R.drawable.composer_sun);
		ImageButton imgBtn6 = new ImageButton(this);
		imgBtn6.setBackgroundResource(R.drawable.composer_thought);
		ImageButton imgBtn7 = new ImageButton(this);
		imgBtn7.setBackgroundResource(R.drawable.composer_music);
		ImageButton imgBtn8 = new ImageButton(this);
		imgBtn8.setBackgroundResource(R.drawable.composer_camera);
		ImageButton imgBtn9 = new ImageButton(this);
		imgBtn9.setBackgroundResource(R.drawable.composer_place);
		ImageButton imgBtn10 = new ImageButton(this);
		imgBtn10.setBackgroundResource(R.drawable.composer_sleep);
		ImageButton imgBtn11 = new ImageButton(this);
		imgBtn11.setBackgroundResource(R.drawable.composer_sun);
		ImageButton imgBtn12 = new ImageButton(this);
		imgBtn12.setBackgroundResource(R.drawable.composer_thought);
		ImageButton imgBtn13 = new ImageButton(this);
		imgBtn13.setBackgroundResource(R.drawable.composer_music);
		ImageButton imgBtn14 = new ImageButton(this);
		imgBtn14.setBackgroundResource(R.drawable.composer_camera);
		ImageButton imgBtn15 = new ImageButton(this);
		imgBtn15.setBackgroundResource(R.drawable.composer_place);
		ImageButton imgBtn16 = new ImageButton(this);
		imgBtn16.setBackgroundResource(R.drawable.composer_sleep);
		ImageButton imgBtn17 = new ImageButton(this);
		imgBtn17.setBackgroundResource(R.drawable.composer_sun);
		myViewLT.addView(img1);
		myViewLT.addView(imgBtn2);
		myViewLT.addView(imgBtn3);
		myViewLT.addView(imgBtn4);
		myViewLT.addView(imgBtn5);
		myViewLT.addView(imgBtn6);
		myViewLT.addView(imgBtn7);
		myViewLT.addView(imgBtn8);
		myViewLT.addView(imgBtn9);
		// myViewLT.addView(imgBtn10);
		// myViewLT.addView(imgBtn11);
		// myViewLT.addView(imgBtn12);
		// myViewLT.addView(imgBtn13);
		// myViewLT.addView(imgBtn14);
		// myViewLT.addView(imgBtn15);
		// myViewLT.addView(imgBtn16);
		// myViewLT.addView(imgBtn17);

	}

	@Override
	public void onclick(int item) {
		if (toast == null) {
			toast = Toast.makeText(MainActivity.this, "" + item, 1);
		} else {
			toast.setText(item + "");
		}

		toast.show();
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		switch (vid) {
		case R.id.relLayLT:
			MyAnimations.getRotateAnimation(imgPlusLT, 0f, 270f, 300);
			MyAnimations.startAnimations(MainActivity.this, myViewLT, 400);
			break;
		}
	}

}
