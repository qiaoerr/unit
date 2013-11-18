package com.tixa.industry.modelCustom;

import java.io.Serializable;

import org.json.JSONObject;

public class Announcement implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO 消息实体类
	 */

	private static final long serialVersionUID = 1L;
	private String title;
	private String content;
	private long endTime;

	public Announcement(JSONObject json) {
		this.title = json.optString("title");
		this.content = json.optString("content");
		this.endTime = json.optLong("endTime");
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
