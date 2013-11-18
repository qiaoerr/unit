package com.tixa.industry.model;

import java.io.Serializable;

/**
 * 配置文件模块
 * 页面是否使用webview显示
 * @author shengy
 *
 */
public class Web implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 637377127497935628L;
	private int type;  //0 不使用  1 使用
	private String url;	 //url地址
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
}
