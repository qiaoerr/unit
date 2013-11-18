package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.util.L;

/**
 * 资讯
 * 
 * @author Administrator
 * 
 */
public class Article implements Serializable {

	private static final long serialVersionUID = -2440373205583029266L;
	private long id;
	private long typeID;
	private int articleType;
	private String title;
	private String brief; //摘要
	private String author;
	private String keywords;
	private String contentStr;
	private String originURL;
	private String imgPath;
	private String tag;
	private int clickNum;
	private int status;
	private int orderNum;
	private long creatorID;
	private String createTime;
	private long modifierID;
	private long appID;
	private long enterpriseID;
	private int searchType;


	public Article() {

	};

	public Article(JSONObject json) {
		this.id = json.optLong("ID");
		this.typeID = json.optLong("typeID");
		this.articleType = json.optInt("articleType");
		this.title = json.optString("title");
		this.brief = json.optString("brief");
		this.author = json.optString("author");
		this.keywords = json.optString("keywords");
		this.contentStr = json.optString("contentStr");
		this.originURL = json.optString("originURL");
		this.imgPath = InterfaceURL.IMGIP + json.optString("imgPath");
		this.tag = json.optString("tag");
		this.clickNum = json.optInt("clickNum");
		this.status = json.optInt("status");
		this.orderNum = json.optInt("orderNum");
		this.creatorID = json.optLong("creatorID");
		this.appID = json.optLong("appID");
		this.searchType = json.optInt("searchType");
		this.enterpriseID = json.optLong("enterpriseID");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public long getTypeID() {
		return typeID;
	}

	public void setTypeID(long typeID) {
		this.typeID = typeID;
	}

	public int getArticleType() {
		return articleType;
	}

	public void setArticleType(int articleType) {
		this.articleType = articleType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getContentStr() {
		return contentStr;
	}

	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}

	public String getOriginURL() {
		return originURL;
	}

	public void setOriginURL(String originURL) {
		this.originURL = originURL;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public long getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(long creatorID) {
		this.creatorID = creatorID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long getModifierID() {
		return modifierID;
	}

	public void setModifierID(long modifierID) {
		this.modifierID = modifierID;
	}

	public long getAppID() {
		return appID;
	}

	public void setAppID(long appID) {
		this.appID = appID;
	}

	public long getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(long enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", typeID=" + typeID + ", articleType="
				+ articleType + ", title=" + title + ", brief=" + brief
				+ ", author=" + author + ", keywords=" + keywords
				+ ", contentStr=" + contentStr + ", originURL=" + originURL
				+ ", imgPath=" + imgPath + ", tag=" + tag + ", clickNum="
				+ clickNum + ", status=" + status + ", orderNum=" + orderNum
				+ ", creatorID=" + creatorID + ", createTime=" + createTime
				+ ", modifierID=" + modifierID + ", appID=" + appID
				+ ", enterpriseID=" + enterpriseID + "]";
	}
	
	

}
