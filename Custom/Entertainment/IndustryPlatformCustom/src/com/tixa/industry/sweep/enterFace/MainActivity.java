package com.tixa.industry.sweep.enterFace;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.tixa.industry.R;
import com.tixa.industry.activity.WebActivity;
import com.tixa.industry.sweep.BeepManager;
import com.tixa.industry.sweep.FinishListener;
import com.tixa.industry.sweep.InactivityTimer;
import com.tixa.industry.sweep.ResultHandler;
import com.tixa.industry.sweep.ResultHandlerFactory;
import com.tixa.industry.sweep.camera.CameraManager;
import com.tixa.industry.sweep.view.ViewfinderView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends MyMainActivity implements
		SurfaceHolder.Callback {

	private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES = EnumSet
			.of(ResultMetadataType.ISSUE_NUMBER,
					ResultMetadataType.SUGGESTED_PRICE,
					ResultMetadataType.ERROR_CORRECTION_LEVEL,
					ResultMetadataType.POSSIBLE_COUNTRY);

	private CameraManager cameraManager;// 相机管理者
	private CaptureActivityHandler handler;// 主activity的handler
	private Result savedResultToShow;// core包中的结果类

	private Result lastResult;// 最后的结果
	private boolean hasSurface;

	private IntentSource source;// intent的资源

	private Collection<BarcodeFormat> decodeFormats;// 编码格式化集合
	private String characterSet;

	private InactivityTimer inactivityTimer;// 用于电量不足，activity自动关闭的管理
	private BeepManager beepManager;// 声音管理者

	private ViewfinderView viewfinderView;// 相机的view
	private TextView statusView;// 状态text

	// 获取view
	ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	// 获取handler
	public Handler getHandler() {
		return handler;
	}

	// 获取相机管理者
	public CameraManager getCameraManager() {
		return cameraManager;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		// 保持屏幕高亮
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		hasSurface = false;
		// 获取电量管理者
		inactivityTimer = new InactivityTimer(this);
		// 获取声音管理者
		beepManager = new BeepManager(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 获取相机管理者
		cameraManager = new CameraManager(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);
		// 找到状态text
		statusView = (TextView) findViewById(R.id.status_view);
		handler = null;
		lastResult = null;

		// 重置状态text
		resetStatusView();
		// 找到surfaceview
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		// 获取surfaceholder
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		// 判断是否已经获取了surfaceholder
		if (hasSurface) {
			// 已经有了就直接用
			initCamera(surfaceHolder);
		} else {
			// 注册callback
			surfaceHolder.addCallback(this);
			// surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		beepManager.updatePrefs();
		inactivityTimer.onResume();

		source = IntentSource.NONE;
		decodeFormats = null;
		characterSet = null;
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:// 返回键
			if (source == IntentSource.NONE && lastResult != null) {
				restartPreviewAfterDelay(0L);
				return true;
			}
			break;
		case KeyEvent.KEYCODE_CAMERA:// 相机键
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:// 音量
			cameraManager.setTorch(false);
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:// 音量
			cameraManager.setTorch(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 转码或存储bitmap
	private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
		if (handler == null) {
			savedResultToShow = result;
		} else {
			if (result != null) {
				savedResultToShow = result;
			}
			if (savedResultToShow != null) {
				Message message = Message.obtain(handler,
						R.id.decode_succeeded, savedResultToShow);
				handler.sendMessage(message);
			}
			savedResultToShow = null;
		}
	}

	// surface的callback方法
	// surface创建的时候调用即findViewById
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
		}
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	// surface销毁时调用
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	// surface变化时调用
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	// 处理编码1
	public void handleDecode(Result rawResult, Bitmap barcode) {
		inactivityTimer.onActivity();
		lastResult = rawResult;
		ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(
				this, rawResult);

		// 判断是否从拍照而来
		boolean fromLiveScan = barcode != null;
		if (fromLiveScan) {
			// beepManager.playBeepSoundAndVibrate();
		}
		handleDecodeInternally(rawResult, resultHandler, barcode);
	}

	// 处理内部结果，将其显示出来2
	private void handleDecodeInternally(Result rawResult,
			ResultHandler resultHandler, Bitmap barcode) {
		statusView.setVisibility(View.GONE);
		viewfinderView.setVisibility(View.GONE);// 设置对应界面消失

		// 设置类型
		String type = resultHandler.getType().toString();

		// 设置扫描的具体内容
		final CharSequence displayContents = resultHandler.getDisplayContents();
		System.out.println("type:  " + type);
		if (type.equalsIgnoreCase("uri")) {
			Builder builder = new Builder(this).setTitle("确认打开如下链接吗")
					.setMessage(displayContents)
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(MainActivity.this,
									WebActivity.class);
							intent.putExtra("url", displayContents);
							startActivity(intent);
							finish();
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							restartPreviewAfterDelay(0L);

						}
					});
			builder.show();
		} else {
			Builder builder = new Builder(this).setMessage(displayContents)
					.setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							restartPreviewAfterDelay(0L);

						}
					}).setPositiveButton("复制到粘贴板", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
							clipboardManager.setText(displayContents);
							finish();
						}
					});
			builder.show();
		}
	}

	// 重新启动

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
		resetStatusView();
	}

	// 初始化照相机
	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		// 如果照相机没有打开
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			// 打开驱动,设置照相机预览的界面为surfaceHolder
			cameraManager.openDriver(surfaceHolder);
			if (handler == null) {
				// 生成handler
				handler = new CaptureActivityHandler(this, decodeFormats,
						characterSet, cameraManager);
			}
			decodeOrStoreSavedBitmap(null, null);
		} catch (IOException ioe) {
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			displayFrameworkBugMessageAndExit();
		}
	}

	// 显示错误信息
	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.app_name));
		builder.setMessage(getString(R.string.msg_camera_framework_bug));
		builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

	// 重置状态view
	private void resetStatusView() {
		statusView.setText(R.string.msg_default_status);
		statusView.setVisibility(View.VISIBLE);
		viewfinderView.setVisibility(View.VISIBLE);
		lastResult = null;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}
}
