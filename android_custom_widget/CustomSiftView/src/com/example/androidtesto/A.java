/**
 * @Title: A.java
 * @Package com.example.androidtesto
 * @Description: TODO
 * Copyright: Copyright (c) 2014
 * Company:�ѷ�����
 * 
 * @author liu
 * @date 2014-12-2 ����10:41:36
 * @version V1.0
 */

package com.example.androidtesto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @ClassName: A
 * @Description: TODO
 * 
 */

public class A extends Activity {

	/**
	 * @Description: TODO
	 * @param savedInstanceState
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a);
	}

	public void func(View v) {
		startActivity(new Intent(A.this, B.class));
		setResult(RESULT_OK);
	}

	/**
	 * @Description: TODO
	 */

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
