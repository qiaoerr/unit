package com.star.baseFramework.model;

import java.io.Serializable;

public class BannerModel implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_LOCAL = 1;// 本地的
	public static final int TYPE_NET = 2;// 网络的

	private String imgUrl;
	private int type;
	private int imgResourceId;

	public BannerModel(int type) {
		super();
		this.type = type;
	}

	public BannerModel(String imgUrl, int type) {
		super();
		this.imgUrl = imgUrl;
		this.type = type;
	}

	public BannerModel(int type, int imgResourceId) {
		super();
		this.type = type;
		this.imgResourceId = imgResourceId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getImgResourceId() {
		return imgResourceId;
	}

	public void setImgResourceId(int imgResourceId) {
		this.imgResourceId = imgResourceId;
	}

}
