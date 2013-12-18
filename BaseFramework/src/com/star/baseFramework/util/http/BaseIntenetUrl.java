/**
  @Title: IntenetUrl.java
  @Package com.tixa.fvexpress.config
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-16 下午03:03:24
  @version V1.0
 */

package com.star.baseFramework.util.http;

/**
 * @ClassName: IntenetUrl
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-16 下午03:03:24
 * 
 */

public class BaseIntenetUrl {
	// 接口服务器地址 http://172.20.0.100:9090
	public static final String WEBDOMAIN = "http://b2c.tixaapp.com/clientInterface/";
	// 图片服务器地址
	public static final String IMGIP = "http://b2c.tixaapp.com/";

	// 异常邮件处理地址
	public static final String ERROR = WEBDOMAIN + "error/error.jsp";

	// 检测更新地址
	public static final String CHECK_VERSION_URL = IMGIP
			+ "clientAPK/Enter/%s/version.xml";

	// test
	public static final String TEST = WEBDOMAIN + "goods/goodsList.jsp";
}
