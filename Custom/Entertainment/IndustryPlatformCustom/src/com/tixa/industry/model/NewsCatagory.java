package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 资讯分类
 * @author shengy
 *
 */
public class NewsCatagory implements Serializable {

	private static final long serialVersionUID = -2068901668043764762L;
	private long id;
	private long parentID;
	private String cataName;
	private int status;
	private int orderNum;
	private int creatorID;
	private long appID;
	private long enterpriseID;
	
	public NewsCatagory() {
		
	}
	
	public NewsCatagory(JSONObject json) {
		this.id = json.optLong("ID");
		this.appID = json.optLong("appID");
		this.parentID = json.optLong("parentID");
		this.cataName = json.optString("cataName");
		this.status = json.optInt("status");
		this.orderNum = json.optInt("orderNum");
		this.creatorID = json.optInt("creatorID");
		this.enterpriseID = json.optLong("enterpriseID");		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentID() {
		return parentID;
	}

	public void setParentID(long parentID) {
		this.parentID = parentID;
	}

	public String getCataName() {
		return cataName;
	}

	public void setCataName(String cataName) {
		this.cataName = cataName;
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

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
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

}
