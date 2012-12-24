package study.mp3player.service;

import study.download.HttpDownLoader;
import study.model.Mp3Info;
import study.mp3player.AppConstant;
import study.mp3player.R;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DownloadService extends Service {
	private Handler handler = new Handler();

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
			HttpDownLoader httpDownLoader = new HttpDownLoader();
			String ip = getResources().getString(R.string.server_ip);
			String url = "http://" + ip + ":8080/mp3/" + mp3Info.getMp3Name();
			final int result = httpDownLoader.downLoad(url, "mp3",
					mp3Info.getMp3Name());
			Log.i("System.test",
					mp3Info.getMp3Name() + Integer.toString(result));
			// Ä¬ÈÏÏÂÔØlrc¸è´Ê
			String url_lrc = "http://" + ip + ":8080/mp3/"
					+ mp3Info.getLrcName();
			httpDownLoader.downLoad(url_lrc, "mp3", mp3Info.getLrcName());
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					if (result == AppConstant.SUCCESS) {
						Toast.makeText(getApplicationContext(),
								mp3Info.getMp3Name() + "download success",
								Toast.LENGTH_SHORT).show();
					} else if (result == AppConstant.EXIST) {
						Toast.makeText(getApplicationContext(),
								mp3Info.getMp3Name() + "exist",
								Toast.LENGTH_SHORT).show();
					} else if (result == AppConstant.FAILED) {
						Toast.makeText(getApplicationContext(),
								mp3Info.getMp3Name() + "download failed",
								Toast.LENGTH_SHORT).show();
					}
				}
			};
			handler.post(runnable);
		}
	}
}
