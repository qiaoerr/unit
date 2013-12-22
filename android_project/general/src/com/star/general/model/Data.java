package com.star.general.model;

/**
 * 关于数据的各种参数及配置
 * 
 */
public class Data {
	public static final int FULLDATA = 1001;
	public static final int NODATA = 1002;
	public static final int NOMOREDATA = 1005;// 获取更多没有数据
	public static final int LOCALDATA = 1006;// 获取本地数据
	public static final int MOREDATA = 1004; // 获取更多
	public static final int TYPE_NEW = 1; // 取最新的值
	public static final int TYPE_HISTORY = -1; // 取历史数据
	public static final int DATA_NUM = 20; // 每次展示的数据
	public static final int NONETWORK = 1003; // 网络错误
	public static final int NOMORENETWORK = 1013; // 网络错误
	public static final int SUCCESS = 1007; // 操作成功
	public static final int FAILED = 1008; // 操作失败
	public static final int CLOSE_POPUP = 1010;// 关闭提示框
	public static final int OTHERLOCALDATA = 1011;// 获取本地数据
	public static final int OTHERFULLDATA = 1012;
	public static final int OTHERMOREDATA = 1014;
	public static final int ISEXIT = 1015;

}
