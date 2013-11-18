package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

public class TmallBrand_Cat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 948203862181536085L;
	private long id;
	private String name;

	public TmallBrand_Cat(JSONObject json, int type) {
		if (type == 0) {// type=0 表示品牌 1表示分类
			this.id = json.optLong("brand_id");
			this.name = json.optString("brand_name");
		} else if (type == 1) {
			this.id = json.optLong("sub_cat_id");
			this.name = json.optString("sub_cat_name");
		}

	}

	public long getid() {
		return id;
	}

	public void setid(long id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

}
