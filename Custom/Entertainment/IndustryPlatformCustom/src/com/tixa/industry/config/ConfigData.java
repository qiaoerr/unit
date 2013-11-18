package com.tixa.industry.config;

/**
 * 配置文件相关参数
 * @author shengy
 *
 */
public interface ConfigData {
	
	/**
	 * 广告相关
	 */
	public static final int SHOW_AD = 1;  //显示广告
	public static final int HIDDEN_AD = 0; //不显示广告
	public static final int AD_SHOWTYPE_DEFAULT = 1; //默认方式 铺满
	public static final int AD_SHOWTYPE_FRAME = 2 ; //有边框
	public static final int AD_SHOWTYPE_TITLE = 3 ; //有标题的广告
	
	
	/**
	 * 二级菜单相关
	 */
	public static final int SHOW_SECOND_MENU = 1; //显示二级菜单
	public static final int HIDDEN_SECOND_MENU = 0; //隐藏二级菜单
	
	/**
	 * 列表相关
	 */
	public static final int TABLE_LIST_VIEW = 1; //列表格式
	public static final int TABLE_GRID_VIEW = 2; //九宫格格式
	
	/**
	 * topbar 相关
	 */
	public static final int SHOW_TOPBAR = 1;//显示导航栏 即topbar
	public static final int HIDDEN_TOPBAR = 0;//隐藏导航栏 即topbar
	public static final int TOPBAR_TYPE_COMMON = 1; //普通默认topbar 
	public static final int TOPBAR_TYPE_SEARCH = 2; //含有搜索栏的topbar
	public static final int TOPBAR_BACKBUTTON_TYPE_NULL = 0; //没有返回按钮
	public static final int TOPBAR_BACKBUTTON_TYPE_CHARACTERS = 1 ; //文字返回键
	public static final int TOPBAR_BACKBUTTON_TYPE_PIC = 2 ; //图片返回键
}
