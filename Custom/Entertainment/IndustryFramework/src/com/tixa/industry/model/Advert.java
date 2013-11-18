package com.tixa.industry.model;

import java.io.Serializable;
import org.json.JSONObject;
import com.tixa.industry.config.InterfaceURL;

/**
 * 首页广告
 * @author shengy
 * 
 */

public class Advert implements Serializable {

	private static final long serialVersionUID = 5862555707684580509L;
	private int id;
	private int position;
	private long appID;
	private long enterpriseID;
	private String name;
	private int type;
	private String imgPath;
	private int status;
	private String addressName;
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	private int coding;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCoding() {
		return coding;
	}

	public void setCoding(int coding) {
		this.coding = coding;
	}

	public Advert(JSONObject json) {
		this.id = json.optInt("id");
		this.addressName = json.optString("addressName");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
		this.coding = json.optInt("coding");
		this.imgPath = InterfaceURL.IMGIP + json.optString("imgPath");
		this.name = json.optString("name");
		this.position = json.optInt("position");
		this.status = json.optInt("status");
		this.type = json.optInt("type");
	}
}
