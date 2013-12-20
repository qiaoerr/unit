package com.example.androidaa.Rr;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

public class DrawableUtils {
	private static HashMap<String, String> strings;
	private static float density;
	private static BitmapDrawable localObject;

	public static String getString(Context paramContext, String paramString) {
		if (strings == null)
			initString();
		String str = null;
		if (strings != null)
			str = (String) strings.get(paramString);
		return str == null ? "" : str;
	}

	public static Drawable getDrawable(Context paramContext, String paramString) {
		Bitmap localBitmap = getBitmap(paramContext, paramString);
		if ((localBitmap == null) || (localBitmap.isRecycled()))
			return null;
		if (localBitmap.getNinePatchChunk() == null) {
			localObject = new BitmapDrawable(paramContext.getResources(),
					localBitmap);
			return localObject;
		}
		Object localObject = new Rect();
		ImageTool.readPaddingFromChunk(localBitmap.getNinePatchChunk(),
				(Rect) localObject);
		NinePatchDrawable localNinePatchDrawable = new NinePatchDrawable(
				paramContext.getResources(), localBitmap,
				localBitmap.getNinePatchChunk(), (Rect) localObject, null);
		return (Drawable) localNinePatchDrawable;
	}

	public static Bitmap getBitmap(Context paramContext, String paramString) {
		try {
			String str = "/" + DrawableUtils.class.getName().replace('.', '/');
			str = str.substring(0, str.length() - 13);
			InputStream localInputStream = DrawableUtils.class
					.getResourceAsStream(str + paramString + ".png");
			if (localInputStream == null)
				localInputStream = DrawableUtils.class.getResourceAsStream(str
						+ paramString + ".9.png");
			if (localInputStream == null)
				localInputStream = DrawableUtils.class.getResourceAsStream(str
						+ paramString + ".jpg");
			Bitmap localBitmap = ImageTool.decodeFromStream(localInputStream);
			localInputStream.close();
			return localBitmap;
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return null;
	}

	public static Bitmap getOriBitmap(Context paramContext, String paramString) {
		try {
			String str = "/" + DrawableUtils.class.getName().replace('.', '/');
			str = str.substring(0, str.length() - 13);
			InputStream localInputStream = DrawableUtils.class
					.getResourceAsStream(str + paramString + ".png");
			if (localInputStream == null)
				localInputStream = DrawableUtils.class.getResourceAsStream(str
						+ paramString + ".9.png");
			if (localInputStream == null)
				localInputStream = DrawableUtils.class.getResourceAsStream(str
						+ paramString + ".jpg");
			Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
			localInputStream.close();
			return localBitmap;
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
		}
		return null;
	}

	private static void initString() {
		try {
			strings = new HashMap<String, String>();
			XmlPullParserFactory localXmlPullParserFactory = XmlPullParserFactory
					.newInstance();
			localXmlPullParserFactory.setNamespaceAware(true);
			XmlPullParser localXmlPullParser = localXmlPullParserFactory
					.newPullParser();
			String str1 = "/" + DrawableUtils.class.getName().replace('.', '/');
			str1 = str1.substring(0, str1.length() - 13);
			InputStream localInputStream = DrawableUtils.class
					.getResourceAsStream(str1 + "strings.xml");
			localXmlPullParser.setInput(localInputStream, "utf-8");
			for (int i = localXmlPullParser.getEventType(); i != 1; i = localXmlPullParser
					.next()) {
				if ((i != 2)
						|| (!"string".equals(localXmlPullParser.getName())))
					continue;
				String str2 = localXmlPullParser.getAttributeValue(0);
				i = localXmlPullParser.next();
				String str3 = null;
				if (i == 4)
					str3 = localXmlPullParser.getText();
				strings.put(str2, str3);
			}
			localInputStream.close();
		} catch (Throwable localThrowable) {
			localThrowable.printStackTrace();
			strings = null;
		}
	}

	/*更改图片大小*/
	public static Drawable resizeImage(Context paramContext,
			String paramString, int paramInt1, int paramInt2) {
		Bitmap localBitmap1 = getOriBitmap(paramContext, paramString);
		int i = localBitmap1.getWidth();
		int j = localBitmap1.getHeight();
		int k = dipToPx(paramContext, paramInt1);
		int m = dipToPx(paramContext, paramInt2);
		float f1 = (float) k / i;
		float f2 = (float) m / j;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(f1, f2);
		Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, i, j,
				localMatrix, true);
		return new BitmapDrawable(paramContext.getResources(), localBitmap2);
	}

	/*通过图片文件路径获取图片*/
	public static Bitmap getBitmap(File paramFile) throws Throwable {
		FileInputStream localFileInputStream = new FileInputStream(paramFile);
		Bitmap localBitmap = getBitmap(localFileInputStream);
		localFileInputStream.close();
		return localBitmap;
	}

	/*通过图片文件路径获取图片*/
	public static Bitmap getBitmap(String paramString) throws Throwable {
		return getBitmap(new File(paramString));
	}

	private static int dipToPx(Context paramContext, int paramInt) {
		if (density <= 0.0F)
			density = paramContext.getResources().getDisplayMetrics().density;
		return (int) (paramInt * density + 0.5F);
	}

	private static Bitmap getBitmap(InputStream paramInputStream) {
		BitmapFactory.Options localOptions = new BitmapFactory.Options();
		localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		localOptions.inPurgeable = true;
		localOptions.inInputShareable = true;
		return BitmapFactory.decodeStream(paramInputStream, null, localOptions);
	}

}
