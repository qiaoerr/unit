package com.tixa.industry.config;

public class InterfaceURL {
	// 接口服务器地址
	// public static final String WEBDOMAIN = Constants.isIntranet ?
	// "http://172.20.0.100:9090/clientInterface/"
	// : "http://b2c.tixaapp.com/clientInterface/";
	public static final String WEBDOMAIN = Constants.isIntranet ? "http://192.168.0.7:9090/clientInterface/"
			: "http://b2c.tixaapp.com/clientInterface/";
	// 图片服务器地址
	public static final String IMGIP = Constants.isIntranet ? "http://192.168.0.7:9090/"
			: "http://b2c.tixaapp.com/";

	// 异常邮件处理地址
	public static final String ERROR = WEBDOMAIN + "error/error.jsp";

	// 检测更新地址
	public static final String CHECK_VERSION_URL = IMGIP
			+ "clientAPK/Enter/%s/version.xml";

	// samulu图片
	public static final String SUMULUIMG = "http://p.samulu.com/";

	// 首页 参数 appID=2
	public static final String GET_INDEX = WEBDOMAIN + "indexName.jsp";
	//
	// 人才招聘详情 参数recruitInfoID
	public static final String GET_RECRUITINFO = WEBDOMAIN
			+ "personnel/getRecruitInfoById.jsp";

	// 人才招聘列表 appID=1&number=3&direct=-1&lastID=38
	public static final String GET_RECRUITINFOS = WEBDOMAIN
			+ "personnel/getRecruitInfos.jsp";

	// 求购列表 appID，number（每页显示几条数据），direct=1表示向前翻，为-1表示向后翻，lastID指的是当前的ID
	// imagePath：产品图片 subject:标题 goodsPrice：产品的价格0代表面议 goodsDesc：产品详情
	public static final String GET_BUYS = WEBDOMAIN + "goods/buyInfoList.jsp";

	// 求购详情参数 id
	public static final String GET_BUY_DETAIL = WEBDOMAIN
			+ "goods/buyInfoById.jsp";

	// 供应列表 appID=1&number=5&direct=-1&lastID=1
	public static final String GET_SELLS = WEBDOMAIN + "goods/goodsList.jsp";

	// 橱窗列表
	public static final String GET_ShowCases = WEBDOMAIN
			+ "goods/getCaseGoods.jsp";

	// 供应详情 参数 id
	public static final String GET_SELL_DETAIL = WEBDOMAIN
			+ "goods/goodsById.jsp";

	// 得到关于我们，使用帮助，关于软件等 信息 appID=1
	public static final String GET_APP_INFO = WEBDOMAIN + "app/appInfo.jsp";

	// 登录 ?appID=1&username=zhuyeye&password=123
	public static final String LOGIN = WEBDOMAIN
			+ "user_member/MemberUser_Login.jsp";

	// 资讯列表
	// ?appID=1&number=5&direct=-1&lastID=1&typeID=12
	public static final String GET_ARTICLES = WEBDOMAIN
			+ "article/articleList.jsp";

	// 资讯详情 ?ID=49
	public static final String GET_ARTICLE_DETAIL = WEBDOMAIN
			+ "article/articleDetail.jsp";

	// 验证手机验证码是否正确
	// ?mobile=18310488169&code=9931";
	// 大于0 // 成功 小于等于0 //失败 -2,//验证码失效 -3,//验证码错误
	public static final String GET_CHECKCODE = WEBDOMAIN
			+ "user_member/getCheckCode.jsp";

	// 验证手机号?mobile=18310488169
	// 返回值： 大于0 // 成功 小于等于0 //失败
	// 0,未知错误 -3,格式错误 ,-4,该号已注册
	public static final String CHECK_MOBILE = WEBDOMAIN
			+ "user_member/checkMobile.jsp";

	// 手机号注册
	// ?mobile=18310488169&appID=1&name=yeye&password=123456
	// -10 手机号已注册， -2密码位数少，0,//未知错误 -3,//格式错误
	public static final String MOBILE_REGISTER = WEBDOMAIN
			+ "user_member/mobileRegister.jsp";

	// 邮箱注册
	// appID=1&name=yeye&email=11@qq.com&password=123456
	// 0,//未知错误 -3,//格式错误 -5,//邮箱已注册
	public static final String ENAIL_REGISTER = WEBDOMAIN
			+ "user_member/emailRegister.jsp";

	// 产品分类
	// appID=1&number=5&lastID=1&direct=-1
	public static final String GET_GOODSCATA = WEBDOMAIN
			+ "goods/goodsCataList.jsp";

	// 产品分类子类
	// parent=0004&appID=1&number=5&lastID=1
	public static final String GET_SUB_GOODSCATA = WEBDOMAIN
			+ "goods/goodsCataChildList.jsp";

	// 产品搜索
	// appID=1&number=5&direct=1&lastID=126&keyWord=8
	public static final String SEARCH_GOODS = WEBDOMAIN
			+ "goods/searchGoods.jsp";

	// 留言
	// appID=1&name=yeye&sex=1&MemberID=1&mobile=15116330217&email=123@qq.com&content=hahahh
	// 添加留言 sex:1男 0女 memberID 会员id
	public static final String CREATEGUESTBOOK = WEBDOMAIN
			+ "user_member/CreateMemberGuestBook.jsp";

	// 获取反馈表单 ?appID=6
	public static final String GET_APPFORM = WEBDOMAIN
			+ "app/GetAppFormByAppID.jsp";

	// 供求（商品）/求购收藏
	public static final String COLLECT_PRODUCT = WEBDOMAIN
			+ "goods/CreateBuySellCollect.jsp";
	// 商品收藏列表
	public static final String COLLECT_PRODUCT_LIST = WEBDOMAIN
			+ "goods/BuySellCollectList.jsp";

	// 取消收藏
	// http://172.20.0.21:9090/clientInterface/goods/deleteBuySellCollectByID.jsp?ID=12
	public static final String DELETE_COLLECT = WEBDOMAIN
			+ "goods/deleteBuySellCollectByID.jsp";

	// 供求（商品）/求购评论
	public static final String BUYSELL_COMMENT = WEBDOMAIN
			+ "goods/CreateBuySellComment.jsp";
	// 供求（商品）/求购评论列表
	public static final String BUYSELL_COMMENT_LIST = WEBDOMAIN
			+ "goods/BuySellCommentList.jsp";

	// 订阅行情列表
	public static final String MARKET_MESSAGE_LIST = WEBDOMAIN
			+ "market/MarketMessageList.jsp";

	// 订阅行情详细
	public static final String MARKET_MESSAGE_DETAIL = WEBDOMAIN
			+ "market/getMarketMessageById.jsp";

	// 展会列表
	public static final String EXHIBITION_LIST = WEBDOMAIN
			+ "exhibition/exhibitionList.jsp";

	// 展会详情
	public static final String EXHIBITION_DETAIL = WEBDOMAIN
			+ "exhibition/exhibitionById.jsp";

	// 分类产品列表
	public static final String CATA_PRODUCT_LIST = WEBDOMAIN
			+ "goods/goodsByCataCode.jsp";

	// 企业黄页
	public static final String SEARCH_ENTER = WEBDOMAIN
			+ "enterMember/searchEnter.jsp";
	// 淘宝
	public static final String TAOBAO = "http://gw.api.taobao.com/router/rest";

	// 资讯分类
	public static final String NEWS_CATA = WEBDOMAIN
			+ "article/articleCata.jsp";

	// 求购列表
	public static final String GET_ALL_BUYINFOS = WEBDOMAIN
			+ "member/MyGoodsBuyInfoListByEnterMemberID.jsp";

	// 休闲娱乐
	public static final String GOODSLISTBYDISTANCE = WEBDOMAIN
			+ "goods/goodsListByDistance.jsp";
	// 我的评论
	public static final String MYPAGEBUYSELLCOMMENTLIST = WEBDOMAIN
			+ "user_member/MyPageBuysellCommentList.jsp";
	// 我的消息列表
	public static final String MYPAGEINFORMATION = WEBDOMAIN
			+ "user_member/MyPageInformation.jsp";
	// 大众点评
	// 获取支持商户搜索的最新分类列表
	public static final String categories_with_businesses = "http://api.dianping.com/v1/metadata/get_categories_with_businesses";
	public static final String find_businesses_by_coordinate = "http://api.dianping.com/v1/business/find_businesses_by_coordinate";

}
