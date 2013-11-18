package com.tixa.industry.modelCustom;

import java.io.Serializable;

import org.json.JSONObject;

public class ProvideAndNeed implements Serializable {
	private static final long serialVersionUID = -3483124715384555858L;
	private long id;
	private String goodsName;
	private String addressDetail;
	private int goodsPrice;
	private float expectScore;
	private String goodsBrief;
	private String goodsThumb;
	private String goodsImg;
	private String mobile;
	private long memberID;
	private double lng;
	private double lat;
	private int searchType;
	// 大众点评
	private String name;
	private String goodsImg_daz;
	private String coupon_description;
	private String business_url;
	private String address;
	private boolean hasMore;
	private int type;// 0 数据来自于天下 1 数据来自于大众点评

	public ProvideAndNeed(JSONObject json) {
		this.id = json.optLong("ID");
		this.goodsName = json.optString("goodsName");
		this.addressDetail = json.optString("addressDetail");
		this.goodsBrief = json.optString("goodsBrief");
		this.goodsPrice = json.optInt("goodsPrice");
		this.expectScore = json.optInt("expectScore");
		this.goodsThumb = json.optString("goodsThumb");
		this.goodsImg = json.optString("goodsImg");
		this.mobile = json.optString("mobile");
		this.memberID = json.optLong("MemberID");
		this.lng = json.optDouble("lng");
		this.lat = json.optDouble("lat");
		this.searchType = json.optInt("searchType");
		// 大众点评
		this.name = json.optString("name");
		this.goodsImg_daz = json.optString("s_photo_url");
		this.coupon_description = json.optString("coupon_description");
		this.business_url = json.optString("business_url");
		this.address = json.optString("address");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsImg_daz() {
		return goodsImg_daz;
	}

	public void setGoodsImg_daz(String goodsImg_daz) {
		this.goodsImg_daz = goodsImg_daz;
	}

	public String getCoupon_description() {
		return coupon_description;
	}

	public void setCoupon_description(String coupon_description) {
		this.coupon_description = coupon_description;
	}

	public String getBusiness_url() {
		return business_url;
	}

	public void setBusiness_url(String business_url) {
		this.business_url = business_url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsThumb() {
		return goodsThumb;
	}

	public void setGoodsThumb(String goodsThumb) {
		this.goodsThumb = goodsThumb;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public int getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public float getExpectScore() {
		return expectScore;
	}

	public void setExpectScore(float expectScore) {
		this.expectScore = expectScore;
	}

	public String getGoodsBrief() {
		return goodsBrief;
	}

	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}

}
