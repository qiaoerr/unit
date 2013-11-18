package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 反馈
 * 
 * @author Administrator
 * 
 */
public class Feedback implements Serializable {

	private long id;
	private long appID;
	private int isMailShow;
	private int isMailCheck;
	private int isQqShow;
	private int isQqCheck;
	private int isAddressShow;
	private int isAddressCheck;
	private int isSexShow;
	private int isSexCheck;
	private String createTime;

	public Feedback() {

	};

	public Feedback(JSONObject json) {
		this.id = json.optLong("ID");
		this.appID = json.optLong("AppID");
		this.isMailShow = json.optInt("isMailShow");
		this.isMailCheck = json.optInt("isMailCheck");
		this.isQqShow = json.optInt("isQqShow");
		this.isQqCheck = json.optInt("isQqCheck");
		this.isAddressShow = json.optInt("isAddressShow");
		this.isAddressCheck = json.optInt("isAddressCheck");
		this.isSexShow = json.optInt("isSexShow");
		this.isSexCheck = json.optInt("isSexCheck");
		this.createTime = json.optString("CreateTime");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppID() {
		return appID;
	}

	public void setAppID(long appID) {
		this.appID = appID;
	}

	public int getIsMailShow() {
		return isMailShow;
	}

	public void setIsMailShow(int isMailShow) {
		this.isMailShow = isMailShow;
	}

	public int getIsMailCheck() {
		return isMailCheck;
	}

	public void setIsMailCheck(int isMailCheck) {
		this.isMailCheck = isMailCheck;
	}

	public int getIsQqShow() {
		return isQqShow;
	}

	public void setIsQqShow(int isQqShow) {
		this.isQqShow = isQqShow;
	}

	public int getIsQqCheck() {
		return isQqCheck;
	}

	public void setIsQqCheck(int isQqCheck) {
		this.isQqCheck = isQqCheck;
	}

	public int getIsAddressShow() {
		return isAddressShow;
	}

	public void setIsAddressShow(int isAddressShow) {
		this.isAddressShow = isAddressShow;
	}

	public int getIsAddressCheck() {
		return isAddressCheck;
	}

	public void setIsAddressCheck(int isAddressCheck) {
		this.isAddressCheck = isAddressCheck;
	}

	public int getIsSexShow() {
		return isSexShow;
	}

	public void setIsSexShow(int isSexShow) {
		this.isSexShow = isSexShow;
	}

	public int getIsSexCheck() {
		return isSexCheck;
	}

	public void setIsSexCheck(int isSexCheck) {
		this.isSexCheck = isSexCheck;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	};

}
