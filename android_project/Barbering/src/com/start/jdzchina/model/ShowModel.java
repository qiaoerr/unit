/**
  @Title: ShowModel.java
  @Package com.start.jdzchina.model
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-27 上午11:12:37
  @version V1.0
 */

package com.start.jdzchina.model;

import java.io.Serializable;

/**
 * @ClassName: ShowModel
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-27 上午11:12:37
 * 
 */

public class ShowModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String imgUrl;
	private int imgResID;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getImgResID() {
		return imgResID;
	}

	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}

}
