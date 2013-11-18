package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;


/**
 * 市场行情
 * @author shengy
 * 
 */
public class MarketMessage implements Serializable {

	private static final long serialVersionUID = 3196987000675308082L;
	private int id;
	private String title;
	private String desc;
	private String content;
	private String url;
	private String appID;
	private String  createTime;
	
	public MarketMessage() {
		
	}
	
	public MarketMessage(JSONObject obj) {
		this.id = obj.optInt("ID");
		this.title = obj.optString("aText");
		this.desc = obj.optString("describe");
		this.content = obj.optString("content1");
		this.url = obj.optString("url");
		this.appID = obj.optString("appID");
		this.createTime = obj.optString("createTime");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppID() {
		return appID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}
	
	
	
}
