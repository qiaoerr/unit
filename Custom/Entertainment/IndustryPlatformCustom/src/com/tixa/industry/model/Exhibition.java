package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 展会信息
 * 
 * @author Administrator
 * 
 */
public class Exhibition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2164572618651194511L;
	private long id;
	private String exName;
	private long cataID;
	private String openTime;
	private String closeTime;
	private String exCity;
	private String exStadium;
	private String hostUnit;
	private String Organizer;
	private String exTime;
	private int exType;
	private int exSpace;
	private int preExCount;
	private int preAudiCount;
	private String detail;
	private String publisher;
	private int status;
	private long appID;
	private long enterpriseID;
	private String createTime;

	public Exhibition() {
	};

	public Exhibition(JSONObject json) {
		this.id = json.optLong("ID");
		this.exName = json.optString("exName");
		this.cataID = json.optLong("cataID");
		this.openTime = json.optString("openTime");
		this.closeTime = json.optString("closeTime");
		this.exCity = json.optString("exCity");
		this.exStadium = json.optString("exStadium");
		this.hostUnit = json.optString("hostUnit");
		this.Organizer = json.optString("Organizer");
		this.exTime = json.optString("exTime");
		this.exType = json.optInt("exType");
		this.exSpace = json.optInt("exSpace");
		this.preExCount = json.optInt("preExCount");
		this.preAudiCount = json.optInt("preAudiCount");
		this.detail = json.optString("detail");
		this.publisher = json.optString("publisher");
		this.status = json.optInt("status");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
		this.createTime = json.optString("createTime");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public long getCataID() {
		return cataID;
	}

	public void setCataID(long cataID) {
		this.cataID = cataID;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getExCity() {
		return exCity;
	}

	public void setExCity(String exCity) {
		this.exCity = exCity;
	}

	public String getExStadium() {
		return exStadium;
	}

	public void setExStadium(String exStadium) {
		this.exStadium = exStadium;
	}

	public String getHostUnit() {
		return hostUnit;
	}

	public void setHostUnit(String hostUnit) {
		this.hostUnit = hostUnit;
	}

	public String getOrganizer() {
		return Organizer;
	}

	public void setOrganizer(String organizer) {
		Organizer = organizer;
	}

	public String getExTime() {
		return exTime;
	}

	public void setExTime(String exTime) {
		this.exTime = exTime;
	}

	public int getExType() {
		return exType;
	}

	public void setExType(int exType) {
		this.exType = exType;
	}

	public int getExSpace() {
		return exSpace;
	}

	public void setExSpace(int exSpace) {
		this.exSpace = exSpace;
	}

	public int getPreExCount() {
		return preExCount;
	}

	public void setPreExCount(int preExCount) {
		this.preExCount = preExCount;
	}

	public int getPreAudiCount() {
		return preAudiCount;
	}

	public void setPreAudiCount(int preAudiCount) {
		this.preAudiCount = preAudiCount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	};

}
