package com.example.fragmentdemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * 声音界面的Activity，加入了sound_activity的布局。
 * 
 * @author guolin
 */
public class SoundActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sound_activity);
	}

}
