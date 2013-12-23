package com.example.androidaa.Rr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

public class ImageTool {
	private static final int NO_COLOR = 1;
	private static final String TEMPLE_CACHE_FOLDER = "tmp_cache";
	private static final String TEMPLE_SHARING_IMAGE = "remotesharingimage.jpg";
	private static final int MaxPieceSize = 51200;// 50kb

	/*如果是9.png图片，则处理9.png图片的边线问题*/
	public static Bitmap decodeFromStream(InputStream paramInputStream)
			throws Exception {

		Bitmap bitmap = BitmapFactory.decodeStream(paramInputStream);
		byte[] chunk = bitmap.getNinePatchChunk();
		boolean result = NinePatch.isNinePatchChunk(chunk);
		NinePatchDrawable patchy = new NinePatchDrawable(bitmap, chunk,
				new Rect(), null);

		Bitmap localBitmap1 = BitmapFactory.decodeStream(paramInputStream);
		byte[] arrayOfByte = readChunk(localBitmap1);
		boolean bool = NinePatch.isNinePatchChunk(arrayOfByte);
		// byte[] chunk = localBitmap1.getNinePatchChunk();
		// boolean bool = NinePatch.isNinePatchChunk(chunk);
		if (bool) {
			Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 1, 1,
					localBitmap1.getWidth() - 2, localBitmap1.getHeight() - 2);
			localBitmap1.recycle();
			Field localField = localBitmap2.getClass().getDeclaredField(
					"mNinePatchChunk");
			localField.setAccessible(true);
			localField.set(localBitmap2, arrayOfByte);
			// localField.set(localBitmap2, chunk);
			return localBitmap2;
		}
		return localBitmap1;
	}

	/*decodeFromFile*/
	public static Bitmap decodeFromFile(String paramString) throws Exception {
		FileInputStream localFileInputStream = new FileInputStream(paramString);
		Bitmap localBitmap = decodeFromStream(localFileInputStream);
		localFileInputStream.close();
		return localBitmap;
	}

	/*decodeFromFile  会缩放图片*/
	public static Bitmap decodeFromFile(String paramString, int paramInt1,
			int paramInt2) throws Exception {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(paramString, localOptions);
		int i = Math.round(localOptions.outWidth / paramInt1);
		int j = Math.round(localOptions.outHeight / paramInt2);
		localOptions.inJustDecodeBounds = false;
		localOptions.inSampleSize = Math.max(i, j);
		Bitmap localBitmap = BitmapFactory
				.decodeFile(paramString, localOptions);
		return localBitmap;
	}

	/*decodeFromByteArray  会缩放图片*/
	public static Bitmap decodeFromByteArray(byte[] paramArrayOfByte,
			int paramInt1, int paramInt2) throws Exception {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(paramArrayOfByte, 0,
				paramArrayOfByte.length, localOptions);
		int i = Math.round(localOptions.outWidth / paramInt1);
		int j = Math.round(localOptions.outHeight / paramInt2);
		localOptions.inJustDecodeBounds = false;
		localOptions.inSampleSize = Math.max(i, j);
		Bitmap localBitmap = BitmapFactory.decodeByteArray(paramArrayOfByte, 0,
				paramArrayOfByte.length, localOptions);
		return localBitmap;
	}

	/*	decodeRemoteImage  网络上的图片*/
	public static byte[] decodeRemoteImage(Context paramContext,
			String paramString) {
		if ((null != paramString) && (paramString.length() > 0)
				&& (null != paramContext)) {
			byte[] arrayOfByte1 = null;
			InputStream localInputStream = null;
			try {
				URL localURL = new URL(paramString);
				HttpURLConnection localHttpURLConnection = (HttpURLConnection) localURL
						.openConnection();
				localHttpURLConnection.setConnectTimeout(5000);
				localHttpURLConnection.setRequestMethod("GET");
				localHttpURLConnection.setDoInput(true);
				ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
				localInputStream = localHttpURLConnection.getInputStream();
				byte[] arrayOfByte2 = new byte[MaxPieceSize];
				int i = 0;
				while ((i = localInputStream.read(
				/*把数据读到arrayOfByte2中来*/arrayOfByte2, 0, arrayOfByte2.length)) > 0)
					localByteArrayOutputStream.write(arrayOfByte2, 0, i);/*把数据写到localByteArrayOutputStream中去*/
				arrayOfByte1 = localByteArrayOutputStream.toByteArray();
				localByteArrayOutputStream.close();
				localInputStream.close();
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			return arrayOfByte1;
		}
		return null;
	}

	/*	decodeRemoteImage 网络上的图片 缩放*/
	public static Bitmap decodeRemoteImage(Context paramContext,
			String paramString, int paramInt1, int paramInt2) {
		Bitmap localBitmap = null;
		if ((null != paramString) && (paramString.length() > 0)
				&& (null != paramContext)) {
			File localFile1 = paramContext.getDir(TEMPLE_CACHE_FOLDER, 0);
			String str = new StringBuilder()
					.append(localFile1.getAbsolutePath())
					.append(File.separator).append(TEMPLE_SHARING_IMAGE)
					.toString();
			RandomAccessFile localRandomAccessFile = null;
			InputStream localInputStream = null;
			try {
				URL localURL = new URL(paramString);
				HttpURLConnection localHttpURLConnection = (HttpURLConnection) localURL
						.openConnection();
				localHttpURLConnection.setDoInput(true);
				localHttpURLConnection.connect();
				localInputStream = localHttpURLConnection.getInputStream();
				localRandomAccessFile = new RandomAccessFile(str, "rw");
				byte[] arrayOfByte = new byte[MaxPieceSize];
				int i = 0;
				while ((i = localInputStream.read(arrayOfByte, 0,
						arrayOfByte.length)) > 0)
					localRandomAccessFile.write(arrayOfByte, 0, i);
				localBitmap = decodeFromFile(str, paramInt1, paramInt2);
			} catch (Exception localFile3) {
			} finally {
				try {
					if (null != localRandomAccessFile)
						localRandomAccessFile.close();
				} catch (IOException localIOException5) {
					localIOException5.printStackTrace();
				}
				try {
					if (null != localInputStream)
						localInputStream.close();
				} catch (IOException localIOException6) {
					localIOException6.printStackTrace();
				}
				File localFile4 = new File(str);
				localFile4.delete();
			}
		}
		return localBitmap;
	}

	/*用于asset目录下9.png图片(NinePatchDrawable)的处理*/
	public static Drawable decodeDrawableFromAsset(Context paramContext,
			String paramString) throws Exception {
		Bitmap localBitmap = decodeFromAsset(paramContext, paramString);
		if (null == localBitmap.getNinePatchChunk()) {
			BitmapDrawable localObject = new BitmapDrawable(
					paramContext.getResources(), localBitmap);
			return localObject;
		}
		Object localObject = new Rect();
		readPaddingFromChunk(localBitmap.getNinePatchChunk(),
				(Rect) localObject);
		NinePatchDrawable localNinePatchDrawable = new NinePatchDrawable(
				paramContext.getResources(), localBitmap,
				localBitmap.getNinePatchChunk(), (Rect) localObject, null);
		return (Drawable) localNinePatchDrawable;
	}

	/*用于asset目录下非9.png图片(Bitmap)的处理*/
	public static Bitmap decodeFromAsset(Context paramContext,
			String paramString) throws Exception {
		InputStream localInputStream = paramContext.getAssets().open(
				paramString);
		Bitmap localBitmap = decodeFromStream(localInputStream);
		localInputStream.close();
		return localBitmap;
	}

	public static void readPaddingFromChunk(byte[] paramArrayOfByte,
			Rect paramRect) {
		paramRect.left = getInt(paramArrayOfByte, 12);
		paramRect.right = getInt(paramArrayOfByte, 16);
		paramRect.top = getInt(paramArrayOfByte, 20);
		paramRect.bottom = getInt(paramArrayOfByte, 24);
	}

	public static byte[] readChunk(Bitmap paramBitmap) throws IOException {
		int i = paramBitmap.getWidth();
		int j = paramBitmap.getHeight();
		int k = 0;
		int m = 0;
		int n = 0;
		int i1 = 0;
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		for (int i2 = 0; i2 < 32; i2++)
			localByteArrayOutputStream.write(0);
		int[] arrayOfInt = new int[i - 2];
		paramBitmap.getPixels(arrayOfInt, 0, i, 1, 0, i - 2, 1);
		int i4 = arrayOfInt[0] == -16777216 ? 1 : 0;
		int i5 = arrayOfInt[(arrayOfInt.length - 1)] == -16777216 ? 1 : 0;
		int i6 = 0;
		int i7 = 0;
		int i8 = arrayOfInt.length;
		while (i7 < i8) {
			if (i6 != arrayOfInt[i7]) {
				k++;
				writeInt(localByteArrayOutputStream, i7);
				i6 = arrayOfInt[i7];
			}
			i7++;
		}
		if (i5 != 0) {
			k++;
			writeInt(localByteArrayOutputStream, arrayOfInt.length);
		}
		n = k + 1;
		if (i4 != 0)
			n--;
		if (i5 != 0)
			n--;
		arrayOfInt = new int[j - 2];
		paramBitmap.getPixels(arrayOfInt, 0, 1, 0, 1, 1, j - 2);
		i4 = arrayOfInt[0] == -16777216 ? 1 : 0;
		i5 = arrayOfInt[(arrayOfInt.length - 1)] == -16777216 ? 1 : 0;
		i6 = 0;
		i7 = 0;
		i8 = arrayOfInt.length;
		while (i7 < i8) {
			if (i6 != arrayOfInt[i7]) {
				m++;
				writeInt(localByteArrayOutputStream, i7);
				i6 = arrayOfInt[i7];
			}
			i7++;
		}
		if (i5 != 0) {
			m++;
			writeInt(localByteArrayOutputStream, arrayOfInt.length);
		}
		i1 = m + 1;
		if (i4 != 0)
			i1--;
		if (i5 != 0)
			i1--;
		for (int i3 = 0; i3 < n * i1; i3++)
			writeInt(localByteArrayOutputStream, 1);
		byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
		arrayOfByte[0] = 1;
		arrayOfByte[1] = (byte) k;
		arrayOfByte[2] = (byte) m;
		arrayOfByte[3] = (byte) (n * i1);
		dealPaddingInfo(paramBitmap, arrayOfByte);
		return arrayOfByte;
	}

	private static void dealPaddingInfo(Bitmap paramBitmap,
			byte[] paramArrayOfByte) {
		int[] arrayOfInt = new int[paramBitmap.getWidth() - 2];
		paramBitmap.getPixels(arrayOfInt, 0, arrayOfInt.length, 1,
				paramBitmap.getHeight() - 1, arrayOfInt.length, 1);
		for (int i = 0; i < arrayOfInt.length; i++) {
			if (-16777216 != arrayOfInt[i])
				continue;
			writeInt(paramArrayOfByte, 12, i);
			break;
		}
		for (int i = arrayOfInt.length - 1; i >= 0; i--) {
			if (-16777216 != arrayOfInt[i])
				continue;
			writeInt(paramArrayOfByte, 16, arrayOfInt.length - i - 2);
			break;
		}
		arrayOfInt = new int[paramBitmap.getHeight() - 2];
		paramBitmap.getPixels(arrayOfInt, 0, 1, paramBitmap.getWidth() - 1, 0,
				1, arrayOfInt.length);
		for (int i = 0; i < arrayOfInt.length; i++) {
			if (-16777216 != arrayOfInt[i])
				continue;
			writeInt(paramArrayOfByte, 20, i);
			break;
		}
		for (int i = arrayOfInt.length - 1; i >= 0; i--) {
			if (-16777216 != arrayOfInt[i])
				continue;
			writeInt(paramArrayOfByte, 24, arrayOfInt.length - i - 2);
			break;
		}
	}

	private static void writeInt(OutputStream paramOutputStream, int paramInt)
			throws IOException {
		paramOutputStream.write(paramInt >> 0 & 0xFF);
		paramOutputStream.write(paramInt >> 8 & 0xFF);
		paramOutputStream.write(paramInt >> 16 & 0xFF);
		paramOutputStream.write(paramInt >> 24 & 0xFF);
	}

	private static void writeInt(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2) {
		paramArrayOfByte[(paramInt1 + 0)] = (byte) (paramInt2 >> 0);
		paramArrayOfByte[(paramInt1 + 1)] = (byte) (paramInt2 >> 8);
		paramArrayOfByte[(paramInt1 + 2)] = (byte) (paramInt2 >> 16);
		paramArrayOfByte[(paramInt1 + 3)] = (byte) (paramInt2 >> 24);
	}

	private static int getInt(byte[] paramArrayOfByte, int paramInt) {
		int i = paramArrayOfByte[(paramInt + 0)];
		int j = paramArrayOfByte[(paramInt + 1)];
		int k = paramArrayOfByte[(paramInt + 2)];
		int m = paramArrayOfByte[(paramInt + 3)];
		int n = i | j << 8 | k << 16 | m << 24;
		return n;
	}

	public static void printChunkInfo(Bitmap paramBitmap) {
		byte[] arrayOfByte = paramBitmap.getNinePatchChunk();
		if (null == arrayOfByte) {
			System.out.println(new StringBuilder()
					.append("can't find chunk info from this bitmap(")
					.append(paramBitmap).append(")").toString());
			return;
		}
		int i = arrayOfByte[1];
		int j = arrayOfByte[2];
		int k = arrayOfByte[3];
		StringBuilder localStringBuilder = new StringBuilder();
		int m = getInt(arrayOfByte, 12);
		int n = getInt(arrayOfByte, 16);
		int i1 = getInt(arrayOfByte, 20);
		int i2 = getInt(arrayOfByte, 24);
		localStringBuilder.append(new StringBuilder().append("peddingLeft=")
				.append(m).toString());
		localStringBuilder.append("\r\n");
		localStringBuilder.append(new StringBuilder().append("paddingRight=")
				.append(n).toString());
		localStringBuilder.append("\r\n");
		localStringBuilder.append(new StringBuilder().append("paddingTop=")
				.append(i1).toString());
		localStringBuilder.append("\r\n");
		localStringBuilder.append(new StringBuilder().append("paddingBottom=")
				.append(i2).toString());
		localStringBuilder.append("\r\n");
		localStringBuilder.append("x info=");
		int i4;
		for (int i3 = 0; i3 < i; i3++) {
			i4 = getInt(arrayOfByte, 32 + i3 * 4);
			localStringBuilder.append(new StringBuilder().append(",")
					.append(i4).toString());
		}
		localStringBuilder.append("\r\n");
		localStringBuilder.append("y info=");
		for (int i3 = 0; i3 < j; i3++) {
			i4 = getInt(arrayOfByte, i * 4 + 32 + i3 * 4);
			localStringBuilder.append(new StringBuilder().append(",")
					.append(i4).toString());
		}
		localStringBuilder.append("\r\n");
		localStringBuilder.append("color info=");
		for (int i3 = 0; i3 < k; i3++) {
			i4 = getInt(arrayOfByte, i * 4 + j * 4 + 32 + i3 * 4);
			localStringBuilder.append(new StringBuilder().append(",")
					.append(i4).toString());
		}
		System.err.println(new StringBuilder().append("")
				.append(localStringBuilder).toString());
	}
}
