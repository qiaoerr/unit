package study.mp3player;

import java.util.List;

import study.model.Mp3Info;
import study.mp3player.service.PlayerService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
		pause.setOnClickListener(new PauseListener());
		stop.setOnClickListener(new StopListener());
		last.setOnClickListener(new LastListener());
		next.setOnClickListener(new NextListener());
	}

	class NextListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			List<Mp3Info> mp3Infos = LocalListFragment.getMp3Infos();
			Intent intent = new Intent();
			int reference = getIntent().getIntExtra("index", 0) + 1;
			if (reference <= mp3Infos.size() - 1) {
				getIntent().putExtra("index", reference);
				Mp3Info mp3Info = mp3Infos.get(reference);
				intent.putExtra("mp3Info", mp3Info);
				intent.putExtra("msg", AppConstant.NEXT);
				intent.setClass(getApplicationContext(), PlayerService.class);
				startService(intent);
			} else {
				getIntent().putExtra("index", reference - 1);
				Toast.makeText(getApplicationContext(), "没有下一首歌曲了",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	class LastListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			List<Mp3Info> mp3Infos = LocalListFragment.getMp3Infos();
			Intent intent = new Intent();
			int reference = getIntent().getIntExtra("index", 0) - 1;
			if (reference >= 0) {
				getIntent().putExtra("index", reference);
				Mp3Info mp3Info = mp3Infos.get(reference);
				intent.putExtra("mp3Info", mp3Info);
				intent.putExtra("msg", AppConstant.LAST);
				intent.setClass(getApplicationContext(), PlayerService.class);
				startService(intent);
			} else {
				getIntent().putExtra("index", 0);
				Toast.makeText(getApplicationContext(), "已经是第一首歌曲了",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	class PauseListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),
					PlayerService.class);
			intent.putExtra("msg", AppConstant.PAUSE);
			startService(intent);
		}

	}

	class StopListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(),
					PlayerService.class);
			intent.putExtra("msg", AppConstant.STOP);
			startService(intent);
		}

	}

	class StartListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			List<Mp3Info> mp3Infos = LocalListFragment.getMp3Infos();
			int index = getIntent().getIntExtra("index", 0);
			Mp3Info mp3Info = mp3Infos.get(index);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("msg", AppConstant.START);
			intent.setClass(getApplicationContext(), PlayerService.class);
			startService(intent);
		}

	}
}
