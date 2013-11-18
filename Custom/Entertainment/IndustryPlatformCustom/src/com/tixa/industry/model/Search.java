package com.tixa.industry.model;

import java.io.Serializable;


public class Search implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4346586069330473708L;
	private int show; //0-不显示 否则显示
	private int type; //0-自定义
	public int getShow() {
		return show;
	}
	public void setShow(int show) {
		this.show = show;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
