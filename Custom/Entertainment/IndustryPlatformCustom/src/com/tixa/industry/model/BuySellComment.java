package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 商品评论
 * 
 * @author Administrator
 * 
 */
public class BuySellComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8106632595132883880L;
	private long id;
	private long appID;
	private long enterpriseID;
	private int type;
	private long itemID;
	private int score;
	private int price;
	private long MemberID;
	private String Goods_Comment;
	private int status;
	private long CreateTimeL;
	private String userName;

	public BuySellComment() {

	};

	public BuySellComment(JSONObject json) {
		this.id = json.optLong("ID");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("EnterpriseID");
		this.type = json.optInt("type");
		this.itemID = json.optLong("itemID");
		this.score = json.optInt("score");
		this.price = json.optInt("price");
		this.MemberID = json.optLong("MemberID");
		this.Goods_Comment = json.optString("Goods_Comment");
		this.status = json.optInt("status");
		this.CreateTimeL = json.optLong("CreateTimeL");
		if (json.has("memberUser")) {
			JSONObject memberUser = json.optJSONObject("memberUser");
			this.userName = memberUser.optString("UserName");
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTimeL() {
		return CreateTimeL;
	}

	public void setCreateTimeL(long createTimeL) {
		CreateTimeL = createTimeL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getItemID() {
		return itemID;
	}

	public void setItemID(long itemID) {
		this.itemID = itemID;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getMemberID() {
		return MemberID;
	}

	public void setMemberID(long memberID) {
		MemberID = memberID;
	}

	public String getGoods_Comment() {
		return Goods_Comment;
	}

	public void setGoods_Comment(String goods_Comment) {
		Goods_Comment = goods_Comment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
