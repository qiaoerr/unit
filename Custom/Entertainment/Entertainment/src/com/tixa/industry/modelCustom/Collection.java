package com.tixa.industry.modelCustom;

import java.io.Serializable;

import org.json.JSONObject;

public class Collection implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = -5042392332552848716L;
	private long id;
	private int type;
	// 供应商品
	private String goodsName;
	private String goodsBrief;
	private String goodsImg;
	// 求购
	private String subject;
	private String goodsDescStr;
	private String imagePath;

	public Collection(JSONObject json) {
		this.id = json.optLong("ID");
		this.type = json.optInt("type");
		if (json.has("goods")) {
			this.goodsName = json.optJSONObject("goods").optString("goodsName");
			this.goodsBrief = json.optJSONObject("goods").optString(
					"goodsBrief");
			this.goodsImg = json.optJSONObject("goods").optString("goodsImg");
		}
		if (json.has("goodsBuyInfo")) {
			this.subject = json.optJSONObject("goodsBuyInfo").optString(
					"subject");
			this.goodsDescStr = json.optJSONObject("goodsBuyInfo").optString(
					"goodsDescStr");
			this.imagePath = json.optJSONObject("goodsBuyInfo").optString(
					"imagePath");
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsBrief() {
		return goodsBrief;
	}

	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGoodsDescStr() {
		return goodsDescStr;
	}

	public void setGoodsDescStr(String goodsDescStr) {
		this.goodsDescStr = goodsDescStr;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
