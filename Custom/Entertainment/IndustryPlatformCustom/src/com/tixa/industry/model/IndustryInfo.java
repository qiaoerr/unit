package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 行业
 * @author shengy
 *
 */
public class IndustryInfo implements Serializable {
	
	private static final long serialVersionUID = -3869470880930896146L;
	private String positionCode; //行业编号
	private String positionName; //行业名称
	
	public IndustryInfo() {
		
	}
	
	public IndustryInfo(JSONObject obj) {
		this.positionCode = obj.optString("PositionCode");
		this.positionName = obj.optString("PositionName");
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	@Override
	public String toString() {
		return "IndustryInfo [positionCode=" + positionCode + ", positionName="
				+ positionName + "]";
	}

}
