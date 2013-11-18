package com.tixa.industry;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Looper;
import android.widget.Toast;

import com.tixa.industry.config.Constants;
import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.util.AndroidUtil;
import com.tixa.industry.util.HttpManager;
import com.tixa.industry.util.L;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TixaParameters;

public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler 实例
	private static CrashHandler INSTANCE = new CrashHandler();
	private IndustryApplication app;
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CrashHandler() {

	}

	public CrashHandler(IndustryApplication context,
			Thread.UncaughtExceptionHandler handler) {
		this.app = context;
		this.mDefaultHandler = handler;
	}

	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处?
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				L.e(TAG, "error : ", e);
				// Log.v(TAG, "error : ", e);
			}
			// �?��程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	private boolean handleException(final Throwable ex) {
		// TODO Auto-generated method stub
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信�?
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				if (Constants.isCorder) {
					Toast.makeText(app, "很抱歉，程序出现异常，即将退出.", Toast.LENGTH_LONG)
							.show();
				}
				Looper.loop();
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				try {
					sendCrashInfo2Mail(ex, !Constants.isCorder);
				} catch (TixaException e) {
					e.printStackTrace();
				}
				Looper.loop();
			}
		}.start();
		return true;
	}

	private void sendCrashInfo2Mail(Throwable ex, boolean isSendError)
			throws TixaException {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		long timestamp = System.currentTimeMillis();
		Date date = new Date(timestamp);
		String time = formatter.format(date);
		StringBuilder sb = new StringBuilder();
		sb.append("行业平台" + "\r\n");
		sb.append("" + AndroidUtil.getPakageInfo(this.app) + "\r\n");
		sb.append("时间:" + time);
		sb.append("手机信息:" + AndroidUtil.getUserAgent() + "\r\n");
		sb.append("程序版本:" + AndroidUtil.getVersion(this.app) + "\r\n");
		sb.append("异常信息:" + sw.toString() + "\r\n");
		L.e(TAG, sb.toString());
		if (isSendError) {
			// ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			// params.add(new BasicNameValuePair("content", sb.toString()));
			TixaParameters params = new TixaParameters();
			params.add("content", sb.toString());
			HttpManager.openUrl(InterfaceURL.ERROR, "POST", params, true);
			// HttpUtil.doPost(app, InterfaceURL.ERROR, params);
		}
	}

	public void init(IndustryApplication context) {
		this.app = context;
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设定CrashHandler为当前默认异常handler
		Thread.setDefaultUncaughtExceptionHandler(CrashHandler.this);
	}

}
