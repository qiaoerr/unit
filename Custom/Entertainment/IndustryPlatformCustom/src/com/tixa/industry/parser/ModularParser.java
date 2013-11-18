package com.tixa.industry.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.SparseArray;

import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.util.L;

/**
 * 解析 Modular.xml
 * 
 * @author shengy
 * 
 */
public class ModularParser {
	private Context mContext;

	public ModularParser(Context context) {
		this.mContext = context;
	}

	public SparseArray<ModularConfig> parse() {
		XmlPullParser parser = null;
		SparseArray<ModularConfig> map = null;
		ModularConfig config = null;
		InputStream is = null;
		try {
			AssetManager manager = mContext.getAssets();
			is = manager.open("Modular.xml");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					map = new SparseArray<ModularConfig>();
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("modular")) {
						config = new ModularConfig();
					} else if (config != null) {
						if (parser.getName().equalsIgnoreCase("id")) {
							config.setType(Integer.parseInt(parser.nextText()));
						} else if (parser.getName().equalsIgnoreCase("type")) {
							config.setTypeName(parser.nextText());
						} else if (parser.getName().equalsIgnoreCase("login")) {
							config.setLogin(Integer.parseInt(parser.nextText()));
						}
					}
					break;

				case XmlPullParser.END_TAG:
					if (parser.getName().equals("modular") && config != null) {
						map.put(config.getType(), config);
						config = null;
					}
					break;
				}
				eventType = parser.next();
			}
			return map;
		} catch (Exception e) {
			L.e("Modular.xml解析失败 e=" + e.toString());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
