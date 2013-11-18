package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

public class TmallGoodsDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4805550718737026252L;
	private String title;
	private String url;
	private String pic_path;
	private String price;
	private String nick;
	private String price_with_rate;
	private String item_id;
	private String promotion_price;
	private String pic_url;
	private String detail_url;
	private String brand_name;

	public TmallGoodsDetail(JSONObject json) {
		this.title = json.optString("title");
		this.url = json.optString("url");
		this.pic_path = json.optString("pic_path");
		this.price = json.optString("price");
		this.nick = json.optString("nick");
		this.price_with_rate = json.optString("price_with_rate");
		this.item_id = json.optString("item_id");
		this.promotion_price = json.optString("promotion_price");
		this.pic_url = json.optString("pic_url");
		this.detail_url = json.optString("detail_url");
		this.brand_name = json.optString("brand_name");
	}

	public String getPromotion_price() {
		return promotion_price;
	}

	public void setPromotion_price(String promotion_price) {
		this.promotion_price = promotion_price;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPrice_with_rate() {
		return price_with_rate;
	}

	public void setPrice_with_rate(String price_with_rate) {
		this.price_with_rate = price_with_rate;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

}
