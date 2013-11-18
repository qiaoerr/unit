package com.tixa.industry.model;

import java.io.Serializable;

/**
 * Activity页面的配置
 * @author shengy
 *
 */
public class PageConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1192725227812477752L;
	private Web web;
	private Navi navi;
	private Search search;
	private AD ad;
	private TabBar tabBar;
	private Block block;
	
	public Web getWeb() {
		return web;
	}
	public void setWeb(Web web) {
		this.web = web;
	}
	public Navi getNavi() {
		return navi;
	}
	public void setNavi(Navi navi) {
		this.navi = navi;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public AD getAd() {
		return ad;
	}
	public void setAd(AD ad) {
		this.ad = ad;
	}
	public TabBar getTabBar() {
		return tabBar;
	}
	public void setTabBar(TabBar tabBar) {
		this.tabBar = tabBar;
	}
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block) {
		this.block = block;
	}
	@Override
	public String toString() {
		return "PageConfig [web=" + web + ", navi=" + navi + ", search="
				+ search + ", ad=" + ad + ", tabBar=" + tabBar + ", block="
				+ block + "]";
	}
	
	
	
}
