package study.mp3player.service;

import study.model.Mp3Info;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlayerService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");

		return super.onStartCommand(intent, flags, startId);
	}

}
