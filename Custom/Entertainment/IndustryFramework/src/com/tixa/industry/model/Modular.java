package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;

/**
 * 模块
 * 
 * @author shengy
 * 
 */
public class Modular implements Serializable {
	private static final long serialVersionUID = -1678490235745809940L;

	private long id;
	private int templateId;
	private long appID;
	private long enterpriseId;
	private String modularName;
	private int type;
	private String childType;
	private int showType;
	private int groupNum;
	private int isJump;
	private int isHidden;
	private int orderNum;
	private int status;
	private int isNav;
	private String modularIcon;

	public Modular(JSONObject json) {
		this.id = json.optLong("ID");
		this.templateId = json.optInt("TemplateID");
		this.appID = json.optLong("appID");
		this.enterpriseId = json.optLong("EnterpriseID");
		this.modularName = json.optString("modularName");
		this.type = json.optInt("type");
		this.childType = json.optString("childType");
		this.showType = json.optInt("showType");
		this.groupNum = json.optInt("groupNum");
		this.isJump = json.optInt("isJump");
		this.isHidden = json.optInt("isHidden");
		this.orderNum = json.optInt("orderNum");
		this.status = json.optInt("status");
		this.isNav = json.optInt("isNav");
		this.modularIcon = InterfaceURL.IMGIP + json.optString("modularIcon");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public long getAppID() {
		return appID;
	}

	public void setAppID(long appID) {
		this.appID = appID;
	}

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getModularName() {
		return modularName;
	}

	public String getModularIcon() {
		return modularIcon;
	}

	public void setModularIcon(String modularIcon) {
		this.modularIcon = modularIcon;
	}

	public void setModularName(String modularName) {
		this.modularName = modularName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getChildType() {
		return childType;
	}

	public void setChildType(String childType) {
		this.childType = childType;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public int getIsJump() {
		return isJump;
	}

	public void setIsJump(int isJump) {
		this.isJump = isJump;
	}

	public int getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(int isHidden) {
		this.isHidden = isHidden;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsNav() {
		return isNav;
	}

	public void setIsNav(int isNav) {
		this.isNav = isNav;
	}

}
