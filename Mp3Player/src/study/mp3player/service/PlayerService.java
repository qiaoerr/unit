package study.mp3player.service;

import java.io.File;

import study.model.Mp3Info;
import study.mp3player.AppConstant;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

public class PlayerService extends Service {
	private MediaPlayer mediaPlayer = null;

	// private boolean isPlaying = false;

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
		} else {
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
		}

	}

	private String getMusicPath(Mp3Info mp3Info) {
		String SDPath = Environment.getExternalStorageDirectory().toString();
		String musicPath = SDPath + File.separator + "mp3" + File.separator
				+ mp3Info.getMp3Name();
		return musicPath;
	}

}
