/**
  @Title: Talent.java
  @Package com.star.general.model
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-30 下午08:22:27
  @version V1.0
 */

package com.star.general.model;

import java.io.Serializable;

/**
 * @ClassName: Talent
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-30 下午08:22:27
 * 
 */

public class Talent implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String job;
	private String imageUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
