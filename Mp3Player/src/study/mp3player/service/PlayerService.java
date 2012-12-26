package study.mp3player.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Queue;

import study.lrc.LrcProcessor;
import study.model.Mp3Info;
import study.mp3player.AppConstant;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

public class PlayerService extends Service {
	private MediaPlayer mediaPlayer = null;
	private long startTime = 0;
	private Long standardTime = null;
	private Handler handler = new Handler();
	private ArrayList<Queue<?>> timeLyric = null;
	private Queue<?> times = null;
	private Queue<?> lyrics = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String msg = intent.getStringExtra("msg");
		if (msg.endsWith(AppConstant.START)) {
			Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
			play(mp3Info);
		} else if (msg.endsWith(AppConstant.PAUSE)) {
			pause();
		} else if (msg.endsWith(AppConstant.STOP)) {
			stop();
		} else if (msg.endsWith(AppConstant.NEXT)) {
			Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
			next(mp3Info);
		} else if (msg.endsWith(AppConstant.LAST)) {
			Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
			last(mp3Info);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void last(Mp3Info mp3Info) {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		String musicPath = getMusicPath(mp3Info);
		mediaPlayer = MediaPlayer.create(getApplicationContext(),
				Uri.parse(musicPath));
		mediaPlayer.start();
	}

	private void next(Mp3Info mp3Info) {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		String musicPath = getMusicPath(mp3Info);
		mediaPlayer = MediaPlayer.create(getApplicationContext(),
				Uri.parse(musicPath));
		mediaPlayer.start();
	}

	private void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	private void pause() {
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			} else {
				mediaPlayer.start();
			}
		}
	}

	private void play(Mp3Info mp3Info) {
		String musicPath = getMusicPath(mp3Info);
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(getApplicationContext(),
					Uri.parse(musicPath));
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
			startTime = System.currentTimeMillis();
			new LrcThread(mp3Info).start();
		} else {
			mediaPlayer.release();
			mediaPlayer = MediaPlayer.create(getApplicationContext(),
					Uri.parse(musicPath));
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		}
	}

	private String getMusicPath(Mp3Info mp3Info) {
		String SDPath = Environment.getExternalStorageDirectory().toString();
		String musicPath = SDPath + File.separator + "mp3" + File.separator
				+ mp3Info.getMp3Name();
		return musicPath;
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			long currentTime = System.currentTimeMillis();
			if (currentTime - startTime >= standardTime) {
				String lyric = (String) lyrics.poll();
				standardTime = (Long) times.poll();
				if (standardTime == null) {
					handler.removeCallbacks(runnable);
				}
				Intent intent = new Intent(AppConstant.LRC_INTENT_ACTION);
				intent.putExtra("lyric", lyric);
				sendBroadcast(intent);
				System.out.println("lyricsend");
				handler.postDelayed(runnable, 10);
			}
		}
	};

	private class LrcThread extends Thread {
		private Mp3Info mp3Info = null;

		public LrcThread(Mp3Info mp3Info) {
			this.mp3Info = mp3Info;
		}

		public void run() {
			timeLyric = new LrcProcessor().process(mp3Info);
			times = timeLyric.get(0);
			lyrics = timeLyric.get(1);
			standardTime = (Long) times.poll();
			System.out.println("run");
			handler.post(runnable);
		};
	}
}
