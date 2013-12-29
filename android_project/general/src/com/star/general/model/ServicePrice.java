package com.star.general.model;

import java.io.Serializable;

public class ServicePrice implements Serializable {
	private static final long serialVersionUID = 1L;
	private String serviceName;
	private String servicePrice;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

}
