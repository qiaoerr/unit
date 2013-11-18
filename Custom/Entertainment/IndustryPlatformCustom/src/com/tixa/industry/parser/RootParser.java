package com.tixa.industry.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;

import com.tixa.industry.model.SlideMenuConfig;
import com.tixa.industry.util.L;

/**
 * 解析Root.xml文件
 * @author shengy
 *
 */
public class RootParser {
	private Context mContext;
	
	public RootParser(Context context) {
		this.mContext = context;
	}
	
	public SlideMenuConfig parse() {	
		XmlPullParser parser = null;
		SlideMenuConfig config = null;
		InputStream is = null;
		try {
			AssetManager manager = mContext.getAssets();
			is = manager.open("Root.xml");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setInput(is,"UTF-8");
			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType){
					case XmlPullParser.START_DOCUMENT :
						config = new SlideMenuConfig();
						break;
					case XmlPullParser.START_TAG:
						 if(parser.getName().equalsIgnoreCase("type")) {
							 config.setIsSlide(Integer.parseInt(parser.nextText()));
						 } else if(parser.getName().equalsIgnoreCase("leftModular")) {
							 config.setLeftModular(Integer.parseInt(parser.nextText()));
						 } else if(parser.getName().equalsIgnoreCase("rightModular")) {
							 config.setRightModular(Integer.parseInt(parser.nextText()));
						 } else if(parser.getName().equalsIgnoreCase("leftLedge")) {
							 config.setLeftLedge(Double.parseDouble(parser.nextText()));
						 } else if(parser.getName().equalsIgnoreCase("rightLedge")) {
							 config.setRightLedge(Double.parseDouble(parser.nextText()));
						 } 
		
					break;
				}		
				eventType = parser.next();
			}
			return config;
			
		} catch (Exception e) {
			e.printStackTrace();
			L.e("Root.xml解析失败 e="+e.toString());
		} finally {
			try {
				if(is !=  null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
