package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 企业黄页
 * 
 * @author Administrator
 * 
 */
public class EnterMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2226620847537638198L;
	private String mobile; // 企业电话
	private String address;// 企业地址
	private String url;// 企业主页
	private String email;// 企业邮箱
	private String name;// 企业名称
	private String des;// 企业介绍

	public EnterMember() {

	};

	public EnterMember(JSONObject json) {
		this.mobile = json.optString("mobile");
		this.address = json.optString("address");
		this.url = json.optString("url");
		this.email = json.optString("email");
		this.name = json.optString("name");
		this.des = json.optString("des");

	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
