package com.star.general.model;

import java.io.Serializable;

import org.json.JSONObject;

public class NewsDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String detail;
	private long createTime;
	private String imgUrl;
	private String[] imgUrls;

	public NewsDataModel() {
		super();
	}

	public NewsDataModel(JSONObject json) {
		super();
		this.title = json.optString("title");
		this.detail = json.optString("detail");
		this.createTime = json.optLong("createTime");
		if (json.has("imgs")) {
			// 图片链接
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String[] getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String[] imgUrls) {
		this.imgUrls = imgUrls;
	}

}
