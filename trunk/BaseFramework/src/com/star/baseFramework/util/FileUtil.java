package com.star.baseFramework.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.star.baseFramework.util.AsyncImageLoader.ImageCallback;

public class FileUtil {
	// 文件后缀和MIME的对应关系（待补充）
	public final static String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".amr", "audio/amr" }, { ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
			{ ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".exe", "application/octet-stream" }, { ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" },
			{ ".h", "text/plain" }, { ".htm", "text/html" },
			{ ".html", "text/html" }, { ".jar", "application/java-archive" },
			{ ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" },
			{ ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" }, { ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" },
			{ ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" }, { ".zip", "application/zip" },
			{ "", "*/*" } };

	/**
	 * 手机能否打开这个文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean canOpenFile(String fName) {
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return false;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return false;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0])) {
				return true;
			}
		}
		return false;
	}

	public static boolean canOpenFile(File file) {
		// 获取后缀名前的分隔符"."在fName中的位置。
		String fName = file.getName();
		return canOpenFile(fName);
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 */
	public static String getMIMEType(File file) {
		String fName = file.getName();
		return getMIMEType(fName);
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 */
	public static String getMIMEType(String fName) {
		String type = "*/*";
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	/**
	 * 打开文件
	 */
	public static void openFile(File file, Context context) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转
		context.startActivity(intent);
	}

	/**
	 * 序列化对象
	 */
	public static void saveFile(String dic, String fileName, Object obj)
			throws Exception {
		L.d("file", "saveFile path = " + dic + fileName);
		File file = new File(dic);
		if (!file.exists()) {
			file.mkdirs();
		}
		File newFile = new File(dic + fileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				newFile));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	/**
	 * 反序列化对象
	 */
	public static Object getFile(String fileName) {
		L.d("file", "getFile path = " + fileName);
		try {
			File file = new File(fileName);
			if (file.exists() && !file.isDirectory()) {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(file));
				Object obj = ois.readObject();
				ois.close();
				return obj;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static int copyfile(File fromFile, File toFile, Boolean rewrite) {
		if (!fromFile.exists()) {
			return -1;
		}
		if (!fromFile.isFile()) {
			return -2;
		}
		if (!fromFile.canRead()) {
			return -3;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}
		try {
			java.io.FileInputStream fosfrom = new java.io.FileInputStream(
					fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
			fosfrom.close();
			fosto.close();
			return 1;
		} catch (Exception ex) {
			L.e("file", "错误", ex);
			return -4;
		}
	}

	public static void delloading(String folderPath) {
		File file = new File(folderPath);
		if (file.exists()) {
			file.delete();
		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 上传图片到服务器
	 */
	public static String uploadFile(String actionUrl, String fileName,
			byte[] uploadContent) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file\";filename=\"" + fileName + "\"" + end);
			ds.writeBytes(end);
			// 将上传的流写入
			ds.write(uploadContent);
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			ds.flush();
			InputStream is = con.getInputStream();
			int ch;
			ByteArrayOutputStream content = new ByteArrayOutputStream();
			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = is.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			String result = new String(content.toByteArray(), "GBK");

			/*
			 * StringBuffer b = new StringBuffer(); while ((ch = is.read()) !=
			 * -1) { b.append((char) ch); }
			 */
			ds.close();
			return result;
			// return b.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 上传图片到服务器
	 */
	public static String uploadFile(String actionUrl, int fileType,
			String fileName, byte[] uploadContent) {
		String url = actionUrl;// + "?ID=0&PicName="+fileName+"&type=0";
		return uploadFile(url, fileName, uploadContent);
	}

	public static String uploadFile(String actionUrl, int fileType,
			String fileName, String filePath) {
		byte[] uploadContent = readFile(filePath);
		return uploadFile(actionUrl, fileType, fileName, uploadContent);
	}

	/**
	 * 获取文件转换为输出流
	 */
	public static byte[] readFile(String filename) {
		try {
			// 获得输入流
			FileInputStream inStream = new FileInputStream(new File(filename));
			// new一个缓冲区
			byte[] buffer = new byte[1024];
			int len = 0;
			// 使用ByteArrayOutputStream类来处理输出流
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while ((len = inStream.read(buffer)) != -1) {
				// 写入数据
				outStream.write(buffer, 0, len);
			}
			// 得到文件的二进制数据
			byte[] data = outStream.toByteArray();
			// 关闭流
			outStream.close();
			inStream.close();
			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将bitmap转成byte数组
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		if (bitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 将Bitmap读到文件中去，注意这个是压缩，那个100是压缩比，0-100，越大质量越好
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] mContent = baos.toByteArray();
			return mContent;
		} else {
			return null;
		}
	}

	// 图片缓存
	public static void setImage(ImageView view, String url,
			AsyncImageLoader loader, final int defaultLogo) {
		if (StrUtil.isEmpty(url)) {
			view.setImageResource(defaultLogo);
		} else {
			Drawable cachedImage = loader.loadDrawable(url, view,
					new ImageCallback() {

						@Override
						public void imageLoaded(Drawable imageDrawable,
								ImageView imageView, String imageUrl) {
							if (imageDrawable != null) {
								imageView.setImageDrawable(imageDrawable);
							} else {
								imageView.setImageResource(defaultLogo);
							}
						}

					});
			if (cachedImage == null) {
				view.setImageResource(defaultLogo);
			} else {
				view.setImageDrawable(cachedImage);
			}
		}
	}

	// 图片异步加载
	public static void setImage_asyn(ImageView view, String url,
			AsyncImageLoader loader, final int defaultLogo) {
		if (StrUtil.isEmpty(url)) {
			view.setImageResource(defaultLogo);
		} else {
			Drawable cachedImage = loader.loadDrawable_asyn(url, view,
					new ImageCallback() {

						@Override
						public void imageLoaded(Drawable imageDrawable,
								ImageView imageView, String imageUrl) {
							if (imageDrawable != null) {
								imageView.setImageDrawable(imageDrawable);
							} else {
								imageView.setImageResource(defaultLogo);
							}
						}

					});
			if (cachedImage == null) {
				view.setImageResource(defaultLogo);
			} else {
				view.setImageDrawable(cachedImage);
			}
		}
	}

	/**
	 * 设置圆角图片
	 * 
	 * @param view
	 * @param url
	 * @param loader
	 * @param drawable
	 */
	public static void setRoundedCornerImage(ImageView view, String url,
			AsyncImageLoader loader, final Drawable drawable) {

		final Bitmap defaultLogo = drawableToBitmap(drawable);
		if (StrUtil.isEmpty(url)) {
			view.setImageBitmap(toRoundCorner(defaultLogo, 20));
		} else {
			Drawable cachedImage = loader.loadDrawable(url, view,
					new ImageCallback() {

						@Override
						public void imageLoaded(Drawable imageDrawable,
								ImageView imageView, String imageUrl) {
							if (imageDrawable != null) {
								Bitmap logo = drawableToBitmap(imageDrawable);

								imageView
										.setImageBitmap(toRoundCorner(logo, 20));
							} else {
								imageView.setImageBitmap(toRoundCorner(
										defaultLogo, 20));
							}
						}

					});
			if (cachedImage == null) {
				view.setImageBitmap(toRoundCorner(defaultLogo, 20));
			} else {
				Bitmap logo = drawableToBitmap(cachedImage);
				view.setImageBitmap(toRoundCorner(logo, 20));
			}
		}
	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap需要修改的图片
	 * @param pixels圆角的弧度
	 * @return 圆角图片
	 */

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);

		return bitmap;

	}

	public static Bitmap getBitmapFromUrl(String imgUrl) {
		URL url;
		Bitmap bitmap = null;
		try {
			url = new URL(imgUrl);
			InputStream is = url.openConnection().getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bitmap = BitmapFactory.decodeStream(bis);
			bis.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}
}
