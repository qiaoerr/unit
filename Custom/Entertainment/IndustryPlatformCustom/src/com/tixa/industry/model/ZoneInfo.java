package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/** 
  * 地域基础类
  * @ClassName: ZoneInfo
  * @Description: 显示地域
  * @author shengy
  * @date 2013-7-21 下午5:54:56
  *
 */
public class ZoneInfo implements Serializable{

	private static final long serialVersionUID = -8289038682643943608L;
	private String zoneCode; //地区编号
	private String zoneName; //地区名
	private String parentCode; //上一级地狱的code
	private int zoneLevel; //该地域的级别
	private String zonenameCH; //地区的名字的 汉语拼音
	private String telCode; //该地区的电话区号
	
	public ZoneInfo() {
		
	}
	
	public ZoneInfo(JSONObject obj){
		this.parentCode = obj.optString("parentCode");
		this.telCode = obj.optString("telCode");
		this.zoneCode = obj.optString("zoneCode");
		this.zoneLevel = obj.optInt("zoneLevel");
		this.zoneName = obj.optString("zoneName");
		this.zonenameCH = obj.optString("zonenameCH");	
	}
	
	public String getZoneCode() {
		return zoneCode;
	}
	
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getZoneLevel() {
		return zoneLevel;
	}

	public void setZoneLevel(int zoneLevel) {
		this.zoneLevel = zoneLevel;
	}

	public String getZonenameCH() {
		return zonenameCH;
	}

	public void setZonenameCH(String zonenameCH) {
		this.zonenameCH = zonenameCH;
	}

	public String getTelCode() {
		return telCode;
	}

	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	@Override
	public String toString() {
		return "ZoneInfo [zoneCode=" + zoneCode + ", zoneName=" + zoneName
				+ ", parentCode=" + parentCode + ", zoneLevel=" + zoneLevel
				+ ", zonenameCH=" + zonenameCH + ", telCode=" + telCode + "]";
	}
	
}
