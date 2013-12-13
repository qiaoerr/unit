package com.start.barbering.config;

import com.star.baseFramework.config.BaseConstants;

public class Constants extends BaseConstants {
	public static final String APP_SITE = "barBering";
	// Json返回数据标识
	public static final int FULLDATA = 1001;
	public static final int NODATA = 1002;
	public static final int NONETWORK = 1003;

	// 百度地图相关
	public static final boolean EnterLocation = true;// 进入就定位
	// 长宽比例配置
	public static final float ratio_gridView = 159f / 287;// 若不加f 则值为0
}
