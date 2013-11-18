package com.tixa.industry.modelCustom;

import java.io.Serializable;

import org.json.JSONObject;

public class MyComment implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO 我的评论实体类
	 */

	private static final long serialVersionUID = 8348555392956458425L;
	private String goodsName;
	private String goodsImg;
	private int goodsPrice;
	private long CreateTimeL;
	private int score;
	private String Goods_Comment;
	private String goodsBrief;

	public MyComment(JSONObject json) {
		if (json.has("goods")) {
			this.goodsName = json.optJSONObject("goods").optString("goodsName");
			this.goodsImg = json.optJSONObject("goods").optString("goodsImg");
			this.goodsBrief = json.optJSONObject("goods").optString(
					"goodsBrief");
			this.goodsPrice = json.optJSONObject("goods").optInt("goodsPrice");
		}
		this.score = json.optInt("score");
		this.CreateTimeL = json.optLong("CreateTimeL");
		this.Goods_Comment = json.optString("Goods_Comment");
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

	public int getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public long getCreateTimeL() {
		return CreateTimeL;
	}

	public void setCreateTimeL(long createTimeL) {
		CreateTimeL = createTimeL;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getGoods_Comment() {
		return Goods_Comment;
	}

	public void setGoods_Comment(String goods_Comment) {
		Goods_Comment = goods_Comment;
	}

}
