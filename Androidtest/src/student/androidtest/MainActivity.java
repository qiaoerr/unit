package student.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View loginButton = findViewById(R.id.login);
		loginButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		findViewById(R.id.pw).startAnimation(shake);
	}

}
