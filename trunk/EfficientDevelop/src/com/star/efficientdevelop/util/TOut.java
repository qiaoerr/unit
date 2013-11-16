package com.star.efficientdevelop.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.widget.Toast;

public class TOut {
	private int count;
	private Handler handler;
	private Looper looper;

	public static void shortT(final Context context, final String message) {
		new TOut().shortImp(context, message, 0);
	}

	public static void longT(Context context, String message) {
		new TOut().shortImp(context, message, 1);
	}

	private void shortImp(final Context context, final String message,
			int status) {
		final int state = status;
		Looper.prepare();
		looper = Looper.myLooper();
		Looper.myQueue().addIdleHandler(new IdleHandler() {
			@Override
			public boolean queueIdle() {
				count++;
				System.out.println("looper.quit():" + count);
				// 这里的两次分别是1.handler发送空消息（handler.sendEmptyMessage(1);）启动Toast,然后idle
				// 2、Toast通过全局系统广播将该messageQueue发送message,handler处理完该message（即
				// 显示出Toast），idle
				// 注意Toast这个UI组件不在UI线程的视图层次关系树中。所以它可以再非UI线程中进行处理。
				if (count == 2) {
					if (state == 0) {
						handler.sendEmptyMessageDelayed(2, 5000);
					} else {
						handler.sendEmptyMessageDelayed(2, 10000);
					}
				}
				return true;
			}
		});
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (state == 0) {
						Toast.makeText(context, message, Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(context, message, Toast.LENGTH_LONG)
								.show();
					}
					break;
				case 2:
					looper.quit();
					break;
				default:
					break;
				}
			}
		};
		handler.sendEmptyMessage(1);
		Looper.loop();
	}
}
