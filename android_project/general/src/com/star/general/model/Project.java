/**
  @Title: Project.java
  @Package com.star.general.model
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-30 下午06:14:41
  @version V1.0
 */

package com.star.general.model;

import java.io.Serializable;

/**
 * @ClassName: Project
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-30 下午06:14:41
 * 
 */

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String subTitle;
	private String imageUrl;
	private String projectDetail;

	public String getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(String projectDetail) {
		this.projectDetail = projectDetail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
