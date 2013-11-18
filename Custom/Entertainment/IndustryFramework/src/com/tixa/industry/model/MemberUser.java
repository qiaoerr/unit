package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;

/**
 * 用户
 * 
 * @author Administrator
 * 
 */
public class MemberUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4765889554661426306L;
	private long memberID;
	private long enterpriseID;
	private long appID;
	private long siteID;
	private long typeID;
	private long groupID;
	private String userName;// 注册邮箱或者手机号
	private String password;
	private int sex;
	private String name;// 昵称
	private String email;
	private String tel;
	private String Photo;
	private String Position;
	private String Introduction;
	private long snsID;
	private String snsName;
	private String snsPhoto;
	private String sinawebo;
	private int weiboType;
	private int followersCount;
	private String CreateTim;
	private String lastLoginTime;
	private int status;

	public MemberUser() {
	};

	public MemberUser(JSONObject json) {
		this.memberID = json.optLong("MemberID");
		this.enterpriseID = json.optLong("EnterpriseID");
		this.appID = json.optLong("appID");
		this.siteID = json.optLong("siteID");
		this.typeID = json.optLong("typeID");
		this.groupID = json.optLong("GroupID");
		this.userName = json.optString("UserName");
		this.password = json.optString("Password");
		this.sex = json.optInt("sex");
		this.name = json.optString("Name");
		this.email = json.optString("email");
		this.tel = json.optString("tel");
		this.Photo = InterfaceURL.IMGIP + json.optString("Photo");
		this.Position = json.optString("Position");
		this.Introduction = json.optString("Introduction");
		this.snsID = json.optLong("snsID");
		this.snsName = json.optString("snsName");
		this.snsPhoto = InterfaceURL.IMGIP + json.optString("snsPhoto");
		this.sinawebo = json.optString("sinawebo");
		this.weiboType = json.optInt("weiboType");
		this.followersCount = json.optInt("followersCount");
		this.CreateTim = json.optString("CreateTim");
		this.lastLoginTime = json.optString("lastLoginTime");
		this.status = json.optInt("status");
	}

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public long getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(long enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	public long getAppID() {
		return appID;
	}

	public void setAppID(long appID) {
		this.appID = appID;
	}

	public long getSiteID() {
		return siteID;
	}

	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}

	public long getTypeID() {
		return typeID;
	}

	public void setTypeID(long typeID) {
		this.typeID = typeID;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getIntroduction() {
		return Introduction;
	}

	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}

	public long getSnsID() {
		return snsID;
	}

	public void setSnsID(long snsID) {
		this.snsID = snsID;
	}

	public String getSnsName() {
		return snsName;
	}

	public void setSnsName(String snsName) {
		this.snsName = snsName;
	}

	public String getSnsPhoto() {
		return snsPhoto;
	}

	public void setSnsPhoto(String snsPhoto) {
		this.snsPhoto = snsPhoto;
	}

	public String getSinawebo() {
		return sinawebo;
	}

	public void setSinawebo(String sinawebo) {
		this.sinawebo = sinawebo;
	}

	public int getWeiboType() {
		return weiboType;
	}

	public void setWeiboType(int weiboType) {
		this.weiboType = weiboType;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public String getCreateTim() {
		return CreateTim;
	}

	public void setCreateTim(String createTim) {
		CreateTim = createTim;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	};

}
