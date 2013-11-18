package com.tixa.industry.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;

/**
 * 产品分类
 * 
 * @author Administrator
 * 
 */
public class GoodsCata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5805700471822624176L;
	private long id;
	private String cataName;
	private String cataCode;
	private String parentCode;
	private int cataLevel;
	private String cataDesc;
	private int status;
	private String cataImg;
	private long appID;
	private long enterpriseID;
	private String createTime;
	private boolean isChecked;
	private int isExistChild;
	private String childCataName;
	private ArrayList<String> childCateList;

	public GoodsCata() {
		// this.cataImg = "";
	};

	public GoodsCata(JSONObject json) {
		this.id = json.optLong("ID");
		this.cataName = json.optString("cataName");
		this.cataCode = json.optString("cataCode");
		this.parentCode = json.optString("parentCode");
		this.cataLevel = json.optInt("cataLevel");
		this.cataDesc = json.optString("cataDesc");
		this.status = json.optInt("status");
		this.cataImg = InterfaceURL.IMGIP + json.optString("cataImg");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
		this.createTime = json.optString("createTime");
		this.childCataName = json.optString("childCataName");
		this.isExistChild = json.optInt("isExistChild");
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public ArrayList<String> getChildCateList() {
		return childCateList;
	}

	public void setChildCateList(ArrayList<String> childCateList) {
		this.childCateList = childCateList;
	}

	public String getChildCataName() {
		return childCataName;
	}

	public void setChildCataName(String childCataName) {
		this.childCataName = childCataName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCataName() {
		return cataName;
	}

	public void setCataName(String cataName) {
		this.cataName = cataName;
	}

	public String getCataCode() {
		return cataCode;
	}

	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getCataLevel() {
		return cataLevel;
	}

	public void setCataLevel(int cataLevel) {
		this.cataLevel = cataLevel;
	}

	public String getCataDesc() {
		return cataDesc;
	}

	public void setCataDesc(String cataDesc) {
		this.cataDesc = cataDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCataImg() {
		return cataImg;
	}

	public void setCataImg(String cataImg) {
		this.cataImg = cataImg;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getIsExistChild() {
		return isExistChild;
	}

	public void setIsExistChild(int isExistChild) {
		this.isExistChild = isExistChild;
	};

}
