package com.tixa.industry.model;

import java.io.Serializable;

/**
 * 左右滑动栏目设置
 * 
 */
public class SlideMenuConfig implements Serializable{

	private static final long serialVersionUID = 9091908071166793563L;
	private int isSlide; //是否策划
	private int leftModular; //左视图 栏目ID
	private int rightModular; //右视图 栏目ID
	private double leftLedge; //左宽度(像素)
	private double rightLedge;//右宽度(像素)
	
	public int getIsSlide() {
		return isSlide;
	}
	public void setIsSlide(int isSlide) {
		this.isSlide = isSlide;
	}
	public int getLeftModular() {
		return leftModular;
	}
	public void setLeftModular(int leftModular) {
		this.leftModular = leftModular;
	}
	public int getRightModular() {
		return rightModular;
	}
	public void setRightModular(int rightModular) {
		this.rightModular = rightModular;
	}
	public double getLeftLedge() {
		return leftLedge;
	}
	public void setLeftLedge(double leftLedge) {
		this.leftLedge = leftLedge;
	}
	public double getRightLedge() {
		return rightLedge;
	}
	public void setRightLedge(double rightLedge) {
		this.rightLedge = rightLedge;
	}
	
	
}
