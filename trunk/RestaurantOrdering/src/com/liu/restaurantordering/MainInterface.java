package com.liu.restaurantordering;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainInterface extends Activity {

	FrameLayout frameLayout = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_interface);
		frameLayout = (FrameLayout) findViewById(R.id.mainframe);

		getFragmentManager().beginTransaction().add(R.id.mainframe, fragment);
	}

}
