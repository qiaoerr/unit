package com.tixa.industry.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 页面代码块设置
 * @author shengy
 *
 */
public class Block implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int secmenuShow ; //二级菜单0-不显示 否则显示 
	private int secmenuType;  //0-自定义 1-... 
	private int tableType;   //0-自定义 1-列表形式 2-九宫格形式 布局块类型 各页面自定义 
	private int showType; // 页面显示格式
	private ArrayList<Button> buttons; //首页显示的button
	private Icon icon; //首页logo
	private Banner banner; //首页广告
	
	
	public Block () {
		this.secmenuType = -1;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}



	public int getSecmenuShow() {
		return secmenuShow;
	}
	public void setSecmenuShow(int secmenuShow) {
		this.secmenuShow = secmenuShow;
	}
	public int getSecmenuType() {
		return secmenuType;
	}
	public void setSecmenuType(int secmenuType) {
		this.secmenuType = secmenuType;
	}
	public int getTableType() {
		return tableType;
	}
	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	@Override
	public String toString() {
		return "Block [secmenuShow=" + secmenuShow + ", secmenuType="
				+ secmenuType + ", tableType=" + tableType + ", showType="
				+ showType + ", buttons=" + buttons + ", icon=" + icon
				+ ", banner=" + banner + "]";
	}
	
	
}
