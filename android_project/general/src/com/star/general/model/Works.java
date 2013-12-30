/**
  @Title: Works.java
  @Package com.star.general.model
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-30 下午08:53:51
  @version V1.0
 */

package com.star.general.model;

import java.io.Serializable;

/**
 * @ClassName: Works
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-30 下午08:53:51
 * 
 */

public class Works implements Serializable {

	private static final long serialVersionUID = -4131742665243837911L;
	private String title;
	private String imageUrl;
	private int imageId;
	private String prefix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}
