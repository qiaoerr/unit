package com.star.baseFramework.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;

public class ImageUtils {

	/* Options used internally. */
	private static final int OPTIONS_NONE = 0x0;
	private static final int OPTIONS_SCALE_UP = 0x1;
	public static final int OPTIONS_RECYCLE_INPUT = 0x2;

	/**
	 * 根据url下载图片
	 * */
	public static Bitmap loadImageFromUrl(String url) throws Exception {
		final DefaultHttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);

		HttpResponse response = client.execute(getRequest);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			L.e("PicShow", "Request URL failed, error code =" + statusCode);
		}

		HttpEntity entity = response.getEntity();
		if (entity == null) {
			Log.e("PicShow", "HttpEntity is null");
		}
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = entity.getContent();
			byte[] buf = new byte[1024];
			int readBytes = -1;
			while ((readBytes = is.read(buf)) != -1) {
				baos.write(buf, 0, readBytes);
			}
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (is != null) {
				is.close();
			}
		}
		byte[] imageArray = baos.toByteArray();
		return BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
	}

	/**
	 * 获得圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * 按图片大小(字节大小)压缩图片
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap fitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		return resizeBmp;
	}

	/**
	 * 按图片大小(字节大小)压缩图片
	 * 
	 * @param uri
	 * @return
	 */
	public static Bitmap fitSizeImg(URI uri) {

		if (uri == null)
			return null;
		File file = new File(uri);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		return resizeBmp;
	}

	/**
	 * 缩放图片
	 * 
	 * @param source
	 *            源文件(Bitmap类型)
	 * @param width
	 *            输出缩略图的宽度
	 * @param height
	 *            输出缩略图的高度
	 * @return
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
		return extractThumbnail(source, width, height, OPTIONS_NONE);
	}

	/**
	 * 缩放图片
	 * 
	 * @param source
	 *            源文件(Bitmap类型)
	 * @param width
	 *            输出缩略图的宽度
	 * @param height
	 *            输出缩略图的高度
	 * @param options
	 *            如果options定义为OPTIONS_RECYCLE_INPUT,则回收@param source这个资源文件
	 *            (除非缩略图等于@param source)
	 * @return
	 */
	public static Bitmap extractThumbnail(Bitmap source, int width, int height,
			int options) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap thumbnail = transform(matrix, source, width, height,
				OPTIONS_SCALE_UP | options);
		return thumbnail;
	}

	/**
	 * Transform source Bitmap to targeted width and height.
	 */
	private static Bitmap transform(Matrix scaler, Bitmap source,
			int targetWidth, int targetHeight, int options) {
		boolean scaleUp = (options & OPTIONS_SCALE_UP) != 0;
		boolean recycle = (options & OPTIONS_RECYCLE_INPUT) != 0;

		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			* In this case the bitmap is smaller, at least in one dimension,
			* than the target.  Transform it by placing as much of the image
			* as possible into the target and leaving the top/bottom or
			* left/right (or both) black.
			*/
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			if (recycle) {
				source.recycle();
			}
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		if (recycle && b1 != source) {
			source.recycle();
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);

		if (b2 != b1) {
			if (recycle || b1 != source) {
				b1.recycle();
			}
		}

		return b2;
	}

	/*
	 * 获取缩略图
	 */
	public static Bitmap createVideoThumbnail(ContentResolver cr, String dataPah) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.Media._ID },
				MediaStore.Video.Media.DATA + "='" + dataPah + "'", null, null);
		if (cursor == null || cursor.getCount() == 0) {
			L.e("MM_VideoImageView", "数据库中cursor为空");
			return null;
		}
		cursor.moveToFirst();
		String videoId = cursor.getString(cursor
				.getColumnIndex(MediaStore.Video.Media._ID)); // image id in
																// image table.s
		if (videoId == null) {
			L.e("MM_VideoImageView", "数据库中videoId为空");
			return null;
		}
		cursor.close();
		long videoIdLong = Long.parseLong(videoId);
		L.e("MM_VideoImageView", "数据库中videoIdLong=" + videoIdLong);
		bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
				Images.Thumbnails.MINI_KIND, options);
		return bitmap;
	}

}
