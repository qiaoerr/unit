/**
 * @Title: GoodsFromDZ.java
 * @Package com.tixa.industry.model
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京天下互联科技有限公司
 * 
 * @author Comsys-Administrator
 * @date 2013-7-31 下午06:23:36
 * @version V1.0
 */

package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * @ClassName: GoodsFromDZ
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-7-31 下午06:23:36
 * 
 */

public class GoodsFromDZ implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 3792153105876051415L;
	private String name;
	private String goodsImg;
	private String coupon_description;
	private String business_url;
	private String address;
	private boolean hasMore;

	public GoodsFromDZ(JSONObject json) {
		this.name = json.optString("name");
		this.goodsImg = json.optString("s_photo_url");
		this.coupon_description = json.optString("coupon_description");
		this.business_url = json.optString("business_url");
		this.address = json.optString("address");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
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

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

}
