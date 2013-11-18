package com.tixa.industry.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import com.tixa.industry.model.AD;
import com.tixa.industry.model.Banner;
import com.tixa.industry.model.Block;
import com.tixa.industry.model.Button;
import com.tixa.industry.model.Icon;
import com.tixa.industry.model.Navi;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.model.Search;
import com.tixa.industry.model.TabBar;
import com.tixa.industry.model.Web;
import com.tixa.industry.util.L;
import android.content.Context;
import android.content.res.AssetManager;

/**
 * 解析页面配置类
 * @author shengy
 *
 */
public class HomeParser {
	private Context mContext;
	private String xml;
	
	public HomeParser(Context context) {
		this.mContext = context;
		this.xml = "layout/HomeLayout.xml";
	}
	
	public PageConfig parser() {
		XmlPullParser parser = null;
		PageConfig config = null;
		InputStream is = null;
		Web web = null;
		Navi navi = null;
		Search search = null;
		AD ad = null;
		TabBar tabBar = null;
		Block block = null;
		Icon icon = null;
		Banner banner = null;
		Button button = null;
		ArrayList<Button> buttons = null;
		
		try{
			AssetManager manager = mContext.getAssets();
			is = manager.open(xml);
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			parser.setInput(is,"UTF-8");
			int eventType = parser.getEventType();
			int isHasPara = 0;
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType){
					case XmlPullParser.START_DOCUMENT :
						config = new PageConfig();
						break;
					case XmlPullParser.START_TAG:
						if(parser.getName().equalsIgnoreCase("web")) {
							web = new Web();
						}else if(parser.getName().equalsIgnoreCase("navi")) {
							navi = new Navi();
						}else if(parser.getName().equalsIgnoreCase("search")) {
							search = new Search();
						}else if(parser.getName().equalsIgnoreCase("ad")) {
							ad = new AD();
						}else if(parser.getName().equalsIgnoreCase("tabBar")) {
							tabBar = new TabBar();
						}else if(parser.getName().equalsIgnoreCase("blocks")) {
							block = new Block();
						}
						else if(parser.getName().equalsIgnoreCase("type")) {
							int type = Integer.parseInt(parser.nextText());
							if(web != null) {
								web.setType(type);
							}else if(navi != null) {
								navi.setType(type);
							}else if(search != null) {
								search.setType(type);
							}else if(ad !=null)	{
								ad.setType(type);
							}else if(tabBar !=null) {
								tabBar.setType(type);
							}else if(block !=null && block.getSecmenuType() == -1) {
								block.setSecmenuType(type);
							}else if(block !=null && block.getSecmenuType() != -1) {
								block.setTableType(type);
							}
											
						} else if(parser.getName().equalsIgnoreCase("url")) {
							if(web != null) {
								web.setUrl(parser.nextText());
							}
						} else if(parser.getName().equalsIgnoreCase("show")) {
							int show = Integer.parseInt(parser.nextText());
							if(navi !=null) {
								navi.setShow(show);
							}else if(search != null) {
								search.setShow(show);
							}else if(ad != null) {
								ad.setShow(show);
							}else if(tabBar !=null ) {
								tabBar.setShow(show);
							}else if(block != null) {
								block.setSecmenuShow(show);
							}							
						} else if(parser.getName().equalsIgnoreCase("backItem")) {
							if(navi != null) {
								navi.setBackItem(Integer.parseInt(parser.nextText()));
							}
						} else if(block!=null && parser.getName().equalsIgnoreCase("param_"+block.getTableType())) {
							isHasPara = 1;
						} else if(parser.getName().equalsIgnoreCase("showType") && isHasPara==1) {
							block.setShowType(Integer.parseInt(parser.nextText()));
							isHasPara = 0;
						} else if(parser.getName().equalsIgnoreCase("scrollImageView")) {
							banner = new Banner();
						} else if(parser.getName().equalsIgnoreCase("frame")) {
							if(banner != null) {
								banner.setFrame(parser.nextText());
							}else if(button != null) {
								button.setFrame(parser.nextText());
							}else if(icon != null ) {
								icon.setFrame(parser.nextText());
							}							
						} else if(parser.getName().equalsIgnoreCase("frameIndside")) {
							if(banner != null) {
								banner.setFrameIndside(parser.nextText());
							}
						} else if(parser.getName().equalsIgnoreCase("button")) {
							
							L.e("-----button开始-------");
							button = new Button();
						} else if(parser.getName().equalsIgnoreCase("edge")) {
							if(button != null) {
								button.setEdge(parser.nextText());
							}
							
						} else if(parser.getName().equalsIgnoreCase("numberOfLines")) {
							if(button != null) {
								button.setNumberOfLines(Integer.parseInt(parser.nextText()));
							}
						} else if(parser.getName().equalsIgnoreCase("textColor")) {
							if(button != null) {
								button.setTextColor(parser.nextText());
							}
						} else if(parser.getName().equalsIgnoreCase("icon")) {
							icon = new Icon();
						} 

							
					break;
					
					case XmlPullParser.END_TAG:
						if(parser.getName().equals("web") && web != null) {
							config.setWeb(web);
							web = null;
						}else if(parser.getName().equals("navi") && navi != null) {
							config.setNavi(navi);
							navi = null;
						}else if(parser.getName().equals("search") && search != null) {
							config.setSearch(search);
							search = null;
						}else if(parser.getName().equals("ad") && ad != null) {
							config.setAd(ad);
							ad = null;
						}else if(parser.getName().equals("tabBar") && tabBar != null) {
							config.setTabBar(tabBar);
							tabBar = null;
						}else if(parser.getName().equals("blocks") && block != null) {
							config.setBlock(block);
							block = null;
						}else if(parser.getName().equalsIgnoreCase("scrollImageView")) {
							block.setBanner(banner);
							banner = null;
						} else if(parser.getName().equalsIgnoreCase("button")) {
							if(buttons == null) {
								buttons = new ArrayList<Button>();
							}
							L.e("-----button getFrame-------"+button.getFrame());
							buttons.add(button);
							button = null;							
							L.e("-----button结束-------");
						} else if(parser.getName().equalsIgnoreCase("icon")) {
							block.setButtons(buttons);
							block.setIcon(icon);	
							L.e("-----buttons 结束-------"+buttons);
							buttons = null;
							icon = null;
						}						
						break;
				}		
				eventType = parser.next();
			}
			
			return config;
						
		}catch(Exception e) {
			L.e(xml+"解析失败 e="+e.toString());
		}finally{
			if(is !=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		
		return null;
	}	
}