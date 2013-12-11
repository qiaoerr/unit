/**
  @Title: BaseInforModel.java
  @Package com.start.jdzchina.model
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-27 下午05:30:23
  @version V1.0
 */

package com.star.middleframework.model;

import java.io.Serializable;

/**
 * @ClassName: BaseInforModel
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-11-27 下午05:30:23
 * 
 */

public class BaseInforModel implements Serializable {

	private static final long serialVersionUID = -9177868699039660668L;
	private String companyName;
	private String address;
	private float latitude;
	private float longitude;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}
