package com.tixa.industry.model;

import java.io.Serializable;

/**
 * 模块
 * @author shengy
 *
 */
public class ModularConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int type; //模块类型
	private String typeName; //模块类型名字
	private int login; //是否需要登录 0 不需要  1需要
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getLogin() {
		return login;
	}
	public void setLogin(int login) {
		this.login = login;
	}

}
