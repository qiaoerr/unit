package com.tixa.industry.modelCustom;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * @author administrator
 * @version
 * 
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = -3015658606594832022L;
	private String name;
	private long create_Time;
	private int score;
	private String goods_Comment;

	public Comment(JSONObject json) {
		this.goods_Comment = json.optString("Goods_Comment");
		this.create_Time = json.optLong("CreateTimeL");
		this.score = json.optInt("score");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(long create_Time) {
		this.create_Time = create_Time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getGoods_Comment() {
		return goods_Comment;
	}

	public void setGoods_Comment(String goods_Comment) {
		this.goods_Comment = goods_Comment;
	}

}
