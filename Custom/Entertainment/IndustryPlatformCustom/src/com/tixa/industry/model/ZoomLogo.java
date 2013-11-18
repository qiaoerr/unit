package com.tixa.industry.model;

import java.io.Serializable;

public class ZoomLogo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3597686390098075868L;

	private int logoMarginLeft; //logo距离左边偏移量
	private int logoMarginTop;  //logo距离顶部偏移量
	private int logoWidth;  //logo的宽度
	private int logoHight;  //logo的高度

	public int getLogoMarginLeft() {
		return logoMarginLeft;
	}
	public void setLogoMarginLeft(int logoMarginLeft) {
		this.logoMarginLeft = logoMarginLeft;
	}
	public int getLogoMarginTop() {
		return logoMarginTop;
	}
	public void setLogoMarginTop(int logoMarginTop) {
		this.logoMarginTop = logoMarginTop;
	}
	public int getLogoWidth() {
		return logoWidth;
	}
	public void setLogoWidth(int logoWidth) {
		this.logoWidth = logoWidth;
	}
	public int getLogoHight() {
		return logoHight;
	}
	public void setLogoHight(int logoHight) {
		this.logoHight = logoHight;
	}

}
