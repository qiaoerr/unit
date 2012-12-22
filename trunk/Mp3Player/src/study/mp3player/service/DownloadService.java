package study.mp3player.service;

import study.model.Mp3Info;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Mp3Info mp3Info = (Mp3Info) intent.getExtras().get("mp3info");
		Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3info");
		new MyThread(mp3Info).start();
		return super.onStartCommand(intent, flags, startId);

	}

	class MyThread extends Thread {
		private Mp3Info mp3Info = null;

		public MyThread(Mp3Info mp3Info) {
			this.mp3Info = mp3Info;
		}

		@Override
		public void run() {
			// String path=
			// HttpDownLoader httpDownLoader=new HttpDownLoader(path);
		}
	}
}
