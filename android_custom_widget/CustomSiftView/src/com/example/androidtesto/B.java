/**
 * @Title: B.java
 * @Package com.example.androidtesto
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:搜房无线
 * 
 * @author liu
 * @date 2014-12-2 上午10:41:43
 * @version V1.0
 */

package com.example.androidtesto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @ClassName: B
 * @Description: TODO
 * 
 */

public class B extends Activity {
	/**
	 * @Description: TODO
	 * @param savedInstanceState
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b);
	}

	public void func(View v) {
		startActivity(new Intent(B.this, C.class));
	}
}
