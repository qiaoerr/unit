package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;

/**
 *  求购信息
  * @ClassName: BuyInfo
  * @Description: TODO
  * @author shengy
  * @date 2013-7-23 上午9:07:40
  *
 */
public class BuyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7566791069908928078L;
	private long id;
	private String cataCode;
	private int status;
	private String subject;
	private String createTime;
	private int goodsNumber;
	private String packList;
	private String goodsPrice;
	private String goodsStandard;
	private String contact;
	private String tel;
	private String zoneCode;
	private String mobile; //手机
	private long memberID; //会员编号
	private String address;
	private String email;
	private String imagePath;
	private String goodsDescStr;
	private long appID;
	private long enterpriseID;
	private String businessName; //公司名

	public BuyInfo() {

	};
	
	public BuyInfo(JSONObject json) {		
		this.zoneCode = json.optString("ZoneCode");
		this.mobile = json.optString("mobile");
		this.memberID = json.optLong("memberID");
		this.businessName = json.optString("businessName");		
		this.id = json.optLong("ID");
		this.cataCode = json.optString("cataCode");
		this.status = json.optInt("status");
		this.subject = json.optString("subject");
		this.createTime = json.optString("createTime");
		this.goodsNumber = json.optInt("goodsNumber");
		this.packList = json.optString("packList");
		this.goodsPrice = json.optString("goodsPrice");
		this.goodsStandard = json.optString("goodsStandard");
		this.contact = json.optString("contact");
		this.tel = json.optString("tel");
		this.address = json.optString("address");
		this.email = json.optString("email");
		this.imagePath = InterfaceURL.IMGIP + json.optString("imagePath");
		this.goodsDescStr = json.optString("goodsDescStr");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCataCode() {
		return cataCode;
	}
	
	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getPackList() {
		return packList;
	}

	public void setPackList(String packList) {
		this.packList = packList;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getGoodsDescStr() {
		return goodsDescStr;
	}

	public void setGoodsDescStr(String goodsDescStr) {
		this.goodsDescStr = goodsDescStr;
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

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
