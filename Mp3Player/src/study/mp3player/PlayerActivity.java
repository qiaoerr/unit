package study.mp3player;

import study.mp3player.service.PlayerService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayerActivity extends Activity {
	private TextView textView = null;
	private ImageButton start = null;
	private ImageButton pause = null;
	private ImageButton stop = null;
	private ImageButton last = null;
	private ImageButton next = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		textView = (TextView) findViewById(R.id.musicText);
		start = (ImageButton) findViewById(R.id.start);
		pause = (ImageButton) findViewById(R.id.pause);
		stop = (ImageButton) findViewById(R.id.stop);
		last = (ImageButton) findViewById(R.id.last);
		next = (ImageButton) findViewById(R.id.next);
		start.setOnClickListener(new StartListener());
	}

	class StartListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = getIntent();
			intent.putExtra("msg", AppConstant.START);
			intent.setClass(getApplicationContext(), PlayerService.class);
			startService(intent);
		}

	}
}
