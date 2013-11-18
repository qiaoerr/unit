package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

/**
 * 账户信息
 * 
 * @author yanyi
 * 
 */
public class AccountInfo implements Serializable {

	private static final long serialVersionUID = -6571554737638762957L;
	private long accountId;
	private String mtName;
	private String passWord;
	private String userName;
	private String apiCode;
	private long mtId;
	private long industryUid;
	private long areaUid;
	private JSONArray inviteInfo;
	private JSONObject spaceInfo;
	private JSONObject lxShowJson;
	private String mMobile;

	public AccountInfo() {
	};

	public AccountInfo(Context context, JSONObject json) {

		this.accountId = json.optLong("accountId");
		this.mtName = json.optString("mtName");
		this.apiCode = json.optString("apiCode");
		this.mtId = json.optLong("mtId");
		this.passWord = json.optString("passWord");
		this.userName = json.optString("userName");
		this.spaceInfo = json.optJSONObject("spaceInfo");
		this.inviteInfo = json.optJSONArray("invitationInfo");
		this.industryUid = json.optLong("industryUid");
		this.areaUid = json.optLong("areaUid");
		this.lxShowJson = json.optJSONObject("lxShow");
		this.mMobile = json.optString("mobile");
	}

	public void clearLoginInfo() {
		this.accountId = 0;
		this.mtName = "";
	}

	public JSONArray getInviteInfo() {
		return inviteInfo;
	}

	public void setInviteInfo(JSONArray inviteInfo) {
		this.inviteInfo = inviteInfo;
	}

	public JSONObject getSpaceInfo() {
		return spaceInfo;
	}

	public void setSpaceInfo(JSONObject spaceInfo) {
		this.spaceInfo = spaceInfo;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getMtName() {
		return mtName;
	}

	public void setMtName(String mtName) {
		this.mtName = mtName;
	}


	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	public long getMtId() {
		return mtId;
	}

	public void setMtId(long mtId) {
		this.mtId = mtId;
	}

	public long getIndustryUid() {
		return industryUid;
	}

	public void setIndustryUid(long industryUid) {
		this.industryUid = industryUid;
	}

	public long getAreaUid() {
		return areaUid;
	}

	public void setAreaUid(long areaUid) {
		this.areaUid = areaUid;
	}


	public JSONObject getLxShowJson() {
		return lxShowJson;
	}

	public void setLxShowJson(JSONObject lxShowJson) {
		this.lxShowJson = lxShowJson;
	}

	public String getmMobile() {
		return mMobile;
	}

	public void setmMobile(String mMobile) {
		this.mMobile = mMobile;
	}

}
