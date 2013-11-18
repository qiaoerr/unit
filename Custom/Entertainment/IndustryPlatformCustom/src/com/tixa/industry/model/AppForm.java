package com.tixa.industry.model;

import org.json.JSONObject;

/**
 * 留言表单显示项
 * @author shengy
 *
 */
public class AppForm {
	private int isMailShow; //是否显示email
	private int isMailCheck; //是否必填项
	private int isQqShow;
	private int isQqCheck;
	private int isAddressShow;
	private int isAddressCheck;
	private int isSexShow;
	private int isSexCheck;

	public AppForm() {
		
	}
	
	public AppForm(JSONObject json) {
		this.isAddressCheck = json.optInt("isAddressCheck");
		this.isAddressShow = json.optInt("isAddressShow"); 
		this.isMailShow = json.optInt("isMailShow"); 
		this.isMailCheck = json.optInt("isMailCheck"); 
		this.isQqShow = json.optInt("isQqShow"); 
		this.isQqCheck = json.optInt("isQqCheck"); 
		this.isSexShow = json.optInt("isSexShow"); 
		this.isSexCheck = json.optInt("isSexCheck"); 		
	}
	
	
	public int getIsMailShow() {
		return isMailShow;
	}
	public void setIsMailShow(int isMailShow) {
		this.isMailShow = isMailShow;
	}
	public int getIsMailCheck() {
		return isMailCheck;
	}
	public void setIsMailCheck(int isMailCheck) {
		this.isMailCheck = isMailCheck;
	}
	public int getIsQqShow() {
		return isQqShow;
	}
	public void setIsQqShow(int isQqShow) {
		this.isQqShow = isQqShow;
	}
	public int getIsQqCheck() {
		return isQqCheck;
	}
	public void setIsQqCheck(int isQqCheck) {
		this.isQqCheck = isQqCheck;
	}
	public int getIsAddressShow() {
		return isAddressShow;
	}
	public void setIsAddressShow(int isAddressShow) {
		this.isAddressShow = isAddressShow;
	}
	public int getIsAddressCheck() {
		return isAddressCheck;
	}
	public void setIsAddressCheck(int isAddressCheck) {
		this.isAddressCheck = isAddressCheck;
	}
	public int getIsSexShow() {
		return isSexShow;
	}
	public void setIsSexShow(int isSexShow) {
		this.isSexShow = isSexShow;
	}
	public int getIsSexCheck() {
		return isSexCheck;
	}
	public void setIsSexCheck(int isSexCheck) {
		this.isSexCheck = isSexCheck;
	}
	
	
	
}
