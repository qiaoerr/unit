package com.tixa.industry.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;

/**
 * 取得首页所有数据
 * 
 * @author shengy
 * 
 */
public class IndexData implements Serializable {
	private static final long serialVersionUID = 647228613378639285L;
	private ArrayList<Advert> adList; // 广告列表
	private ArrayList<Advert> subAdList; // 列表页广告位
	private ArrayList<Modular> modularList; // 模块列表
	private ArrayList<Modular> navList; // 导航列表
	private String appIcon;// logo路径
	private static final int ADTYPE = 4; // 主页广告类型
	private static final int SUBADTYPE = 5; // 列表页广告类型

	public ArrayList<Advert> getSubAdList() {
		return subAdList;
	}

	public void setSubAdList(ArrayList<Advert> subAdList) {
		this.subAdList = subAdList;
	}

	public ArrayList<Advert> getAdList() {
		return adList;
	}

	public void setAdList(ArrayList<Advert> adList) {
		this.adList = adList;
	}

	public ArrayList<Modular> getModularList() {
		return modularList;
	}

	public void setModularList(ArrayList<Modular> modularList) {
		this.modularList = modularList;
	}

	public ArrayList<Modular> getNavList() {
		return navList;
	}

	public void setNavList(ArrayList<Modular> navList) {
		this.navList = navList;
	}

	public String getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(String appIcon) {
		this.appIcon = appIcon;
	}

	public IndexData(JSONObject obj) {
		this.adList = new ArrayList<Advert>();
		this.subAdList = new ArrayList<Advert>();
		if (obj.has("adList")) {
			JSONArray ads = obj.optJSONArray("adList");
			if (ads != null) {
				for (int i = 0; i < ads.length(); i++) {
					JSONObject o = ads.optJSONObject(i);
					Advert ad = new Advert(o);
					if (ad.getPosition() == ADTYPE) { // 首页广告
						adList.add(ad);
					} else if (ad.getPosition() == SUBADTYPE) { // 二级页面广告
						subAdList.add(ad);
					}
				}
			}
		}
		this.modularList = new ArrayList<Modular>();
		if (obj.has("nameNoNavList")) {
			JSONArray ads = obj.optJSONArray("nameNoNavList");
			if (ads != null) {
				for (int i = 0; i < ads.length(); i++) {
					JSONObject o = ads.optJSONObject(i);
					Modular ad = new Modular(o);
					modularList.add(ad);
				}
			}
		}
		this.navList = new ArrayList<Modular>();
		if (obj.has("nameIsNavList")) {
			JSONArray ads = obj.optJSONArray("nameIsNavList");
			if (ads != null) {
				for (int i = 0; i < ads.length(); i++) {
					JSONObject o = ads.optJSONObject(i);
					Modular ad = new Modular(o);
					navList.add(ad);
				}
			}
		}
		if (obj.has("appIcon")) {
			if(obj.optString("appIcon") == null || obj.optString("appIcon").equals("")) {
				appIcon = "";
			}else{
				appIcon = obj.optString("appIcon");
			}
		}
	}
}
