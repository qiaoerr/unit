package com.bean;

import java.sql.Date;

public class Bill {
	private  Long billid;
	private String goodsname;
	private int goodsnum;
	private  double money;
	private String ispay;
	private String suppliername;
	private String gooddescription;
	private Date billTime;
	public Long getBillid() {
		return billid;
	}
	public void setBillid(Long billid) {
		this.billid = billid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public int getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(int goodsnum) {
		this.goodsnum = goodsnum;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getIspay() {
		return ispay;
	}
	public void setIspay(String ispay) {
		this.ispay = ispay;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getGooddescription() {
		return gooddescription;
	}
	public void setGooddescription(String gooddescription) {
		this.gooddescription = gooddescription;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	
	
}
