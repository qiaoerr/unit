package com.tixa.industry.model;

import java.io.Serializable;

/**
 * 导航栏配置
 * @author shengy
 *
 */
public class Navi implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int show; //0-不显示 否则显示
	private int type; //0-自定义 1-默认 2-携带搜索栏
	private int backItem;  //<!-- 0-无 1-文字 2-图片 -->
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
	public int getBackItem() {
		return backItem;
	}
	public void setBackItem(int backItem) {
		this.backItem = backItem;
	}
}
