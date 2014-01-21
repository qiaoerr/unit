/**
  @Title: ResourceUtils.java
  @Package com.example.androidaa.Rr
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-1-10 下午04:18:12
  @version V1.0
 */

package com.example.androidaa.Rr;

import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.graphics.drawable.BitmapDrawable;

/**
 * @ClassName: ResourceUtils
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2014-1-10 下午04:18:12
 * 
 */

public class ResourceUtils {
	private static HashMap<String, String> strings;
	private static float density;
	private static BitmapDrawable localObject;

	public static String getString(String paramString) {
		if (strings == null)
			initString();
		String str = null;
		if (strings != null)
			str = (String) strings.get(paramString);
		return str == null ? "" : str;
	}

	private static void initString() {
		try {
			strings = new HashMap<String, String>();
			XmlPullParserFactory localXmlPullParserFactory = XmlPullParserFactory
					.newInstance();
			localXmlPullParserFactory.setNamespaceAware(true);
			XmlPullParser localXmlPullParser = localXmlPullParserFactory
					.newPullParser();
			String str1 = "/" + ResourceUtils.class.getName().replace('.', '/');
			str1 = str1.substring(0, str1.length() - 13);
			InputStream localInputStream = ResourceUtils.class
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
}
