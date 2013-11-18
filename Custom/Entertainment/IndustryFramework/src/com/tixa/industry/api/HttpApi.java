package com.tixa.industry.api;

import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.util.HttpManager;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TixaParameters;

/**
 * 网络请求接口封装
 * 
 * @author shengy
 * 
 */
public class HttpApi {
	public static final String HTTPMETHOD_POST = "POST"; // POST
	public static final String HTTPMETHOD_GET = "GET"; // GET
	private String appID;

	public HttpApi(String appID) {
		this.appID = appID;
	}

	/**
	 * 获取首页数据
	 * 
	 * @param listener
	 */
	public void getIndex(RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		doGet(InterfaceURL.GET_INDEX, params, listener);
	}

	/**
	 * 人才招聘
	 * 
	 * @param recruitInfoID
	 * @param listener
	 */
	public void getRecruitInfoById(String recruitInfoID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("recruitInfoID", recruitInfoID);
		doGet(InterfaceURL.GET_RECRUITINFO, params, listener);
	}

	/**
	 * 人才招聘列表
	 * 
	 * @param appID
	 *            企业编号
	 * @param number
	 *            每次获取的数量
	 * @param direct
	 *            标识符，1 取最新的数据 -1 去历史数据
	 * @param lastID
	 *            最后ID, 取最新数据时maxID,取历史数据时lastID
	 * @param listener
	 */
	public void getRecruitInfos(int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		doGetForList(InterfaceURL.GET_RECRUITINFOS, params, number, direct,
				lastID, listener);
	}

	/**
	 * 求购列表
	 * 
	 * @param appID
	 *            企业编号
	 * @param number
	 *            每次获取的数量
	 * @param direct
	 *            标识符，1 取最新的数据 -1 去历史数据
	 * @param lastID
	 *            最后ID, 取最新数据时maxID,取历史数据时lastID
	 * @param listener
	 */
	public void getAllBuys(int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		doGetForList(InterfaceURL.GET_BUYS, params, number, direct, lastID,
				listener);
	}

	/**
	 * 供应列表
	 * 
	 * @param appID
	 *            企业编号
	 * @param number
	 *            每次获取的数量
	 * @param direct
	 *            标识符，1 取最新的数据 -1 去历史数据
	 * @param lastID
	 *            最后ID, 取最新数据时maxID,取历史数据时lastID
	 * @param listener
	 */
	public void getAllSells(int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		doGetForList(InterfaceURL.GET_SELLS, params, number, direct, lastID,
				listener);
	}

	/**
	 * 得到所有的商品列表或橱窗列表
	 * 
	 * @param number
	 * @param direct
	 * @param lastID
	 * @param childType
	 * @param listener
	 */
	public void getAllSellsOrShowCases(int number, int direct, int lastID,
			String childType, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		if (!childType.equals(0 + "")) { // 橱窗类型

			L.e("!childType.equals 0");
			doGetForList(InterfaceURL.GET_ShowCases, params, number, direct,
					lastID, listener);
		} else { // 产品

			L.e("childType.equals 0");
			doGetForList(InterfaceURL.GET_SELLS, params, number, direct,
					lastID, listener);
		}
	}

	/**
	 * 求购详情
	 * 
	 * @param id
	 *            供求id
	 * @param listener
	 */
	public void getBuyDetail(String id, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("id", id);
		doGet(InterfaceURL.GET_BUY_DETAIL, params, listener);
	}

	/**
	 * 求购详情
	 * 
	 * @param id
	 *            供求id
	 * @param listener
	 */
	public void getSellDetail(String id, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("id", id);
		doGet(InterfaceURL.GET_SELL_DETAIL, params, listener);
	}

	/**
	 * 得到关于我们，使用帮助，关于软件等 信息
	 * 
	 * @param id
	 * @param listener
	 */
	public void getAppInfo(RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		doGet(InterfaceURL.GET_APP_INFO, params, listener);
	}

	/**
	 * 登录
	 * 
	 * @param appID
	 * @param userName
	 * @param password
	 * @param listener
	 */
	public void login(String username, String password, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("username", username);
		params.add("password", password);
		params.add("clientType", 1);
		doPost(InterfaceURL.LOGIN, params, listener);
	}

	/**
	 * 资讯列表
	 * 
	 * @param appID
	 * @param number
	 * @param direct
	 * @param lastID
	 * @param typeID
	 *            咨询分类
	 * @param listener
	 */
	public void getArtices(int number, int direct, int lastID, String typeID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("typeID", typeID);
		doGetForList(InterfaceURL.GET_ARTICLES, params, number, direct, lastID,
				listener);
	}

	/**
	 * 资讯详情
	 * 
	 * @param ID
	 * @param listener
	 */
	public void getArticleDetail(String ID, String originURL, int searchType,
			String title, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("ID", ID);
		params.add("originURL", originURL);
		params.add("searchType", searchType);
		params.add("title", title);
		doGet(InterfaceURL.GET_ARTICLE_DETAIL, params, listener);
	}

	/**
	 * 验证手机验证码是否正确
	 * 
	 * @param mobile
	 * @param code
	 * @param listener
	 *            返回值 大于0 // 成功 小于等于0 //失败 -2,//验证码失效 -3,//验证码错误
	 */
	public void getCheckcode(String mobile, String code,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("mobile", mobile);
		params.add("code", code);
		params.add("appID", appID);
		doPost(InterfaceURL.GET_CHECKCODE, params, listener);
	}

	/**
	 * 验证手机号是否已被注册
	 * 
	 * @param mobile
	 * @param listener
	 *            返回值： 大于0 // 成功 小于等于0 //失败 //0,未知错误 -3,格式错误 ,-4,该号已注册
	 */
	public void checkMobile(String mobile, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("mobile", mobile);
		params.add("appID", appID);
		doPost(InterfaceURL.CHECK_MOBILE, params, listener);
	}

	/**
	 * 手机号码方式注册
	 * 
	 * @param appID
	 * @param mobile
	 * @param name
	 * @param password
	 * @param listener
	 *            返回值 -10 手机号已注册， -2密码位数少，0,//未知错误 -3,//格式错误
	 */
	public void mobileRegister(String mobile, String name, String password,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("name", name);
		params.add("password", password);
		params.add("mobile", mobile);
		doPost(InterfaceURL.MOBILE_REGISTER, params, listener);
	}

	/**
	 * 邮箱方式注册
	 * 
	 * @param appID
	 * @param email
	 * @param name
	 * @param password
	 * @param listener
	 *            返回值 0,//未知错误 -3,//格式错误 -5,//邮箱已注册
	 */
	public void emailRegister(String email, String name, String password,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("name", name);
		params.add("password", password);
		params.add("email", email);
		doPost(InterfaceURL.ENAIL_REGISTER, params, listener);
	}

	/**
	 * 获取产品分类
	 * 
	 * @param appID
	 * @param number
	 * @param direct
	 * @param lastID
	 * @param listener
	 */
	public void getGoodsCata(int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		doGetForList(InterfaceURL.GET_GOODSCATA, params, number, direct,
				lastID, listener);
	}

	/**
	 * 获取产品分类子类
	 * 
	 * @param appID
	 * @param parent
	 * @param number
	 * @param direct
	 * @param lastID
	 * @param listener
	 */
	public void getSubGoodsCata(String parent, int number, int direct,
			int lastID, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("parent", parent);
		doPostForList(InterfaceURL.GET_SUB_GOODSCATA, params, number, direct,
				lastID, listener);
	}

	/**
	 * 产品搜索
	 * 
	 * @param appID
	 * @param keyWord
	 * @param number
	 * @param direct
	 * @param lastID
	 * @param listener
	 */
	public void searchGoods(String keyWord, int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("keyWord", keyWord);
		doPostForList(InterfaceURL.SEARCH_GOODS, params, number, direct,
				lastID, listener);
	}

	/**
	 * 添加留言 返回：成功：content 失败 ：-1 姓名 默认显示 默认显示 手机 默认显示 默认显示 留言 默认显示 默认显示必填的
	 * sex:1男 0女 memberID 会员id
	 * appID=1&name=yeye&sex=1&MemberID=1&mobile=15116330217
	 * &email=123@qq.com&content=hahahh
	 */
	public void createGuessBook(String name, int sex, final String memberID,
			String mobile, String email, String content, String address,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("name", name);
		params.add("sex", sex);
		params.add("MemberID", memberID);
		params.add("mobile", mobile);
		params.add("email", email);
		params.add("address", address);
		params.add("content", content);
		doPost(InterfaceURL.CREATEGUESTBOOK, params, listener);
	}

	/**
	 * 获取反馈表单的项
	 * 
	 * @param listener
	 *            成功返回对象
	 */
	public void getAppForm(RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		doGet(InterfaceURL.GET_APPFORM, params, listener);
	}

	/**
	 * 供求（商品）/求购收藏 参数：type=1时，itemID是供应（商品）ID type=2时，itemID 是求购ID 返回 成功
	 * 返回对象，失败返回-1 appID=1&MemberID=1&type=1&itemID=1
	 */
	public void collectProduct(int type, String itemID, String MemberID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("type", type);
		params.add("MemberID", MemberID);
		params.add("itemID", itemID);
		doPost(InterfaceURL.COLLECT_PRODUCT, params, listener);
	}

	/**
	 * 商品收藏列表
	 */
	public void getCollectProductList(String MemberID, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("memberID", MemberID);
		// params.add("appID", appID);
		doGet(InterfaceURL.COLLECT_PRODUCT_LIST, params, listener);
		// doPost(InterfaceURL.COLLECT_PRODUCT_LIST, params, listener);

	}

	/**
	 * 供求（商品）/求购评论 type=1时，itemID是供应（商品）ID type=2时，itemID 是求购ID score
	 * 1,2,3,4,5为1分--5分 GoodsComment 评论内容 返回 成功 返回对象，失败返回-1
	 * appID=2&MemberID=2&type=2&itemID=2&score=3&price=1020&GoodsComment=good
	 */

	public void buysellComment(int type, String itemID, String MemberID,
			int score, String price, String GoodsComment, String itemMemberID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("type", type);
		params.add("MemberID", MemberID);
		params.add("itemMemberID", itemMemberID);
		params.add("itemID", itemID);
		params.add("score", score);
		params.add("price", price);
		params.add("GoodsComment", GoodsComment);
		doPost(InterfaceURL.BUYSELL_COMMENT, params, listener);
	}

	public void buysellComment(int type, String itemID, String MemberID,
			int score, String price, String GoodsComment,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("type", type);
		params.add("MemberID", MemberID);
		params.add("itemID", itemID);
		params.add("score", score);
		params.add("price", price);
		params.add("GoodsComment", GoodsComment);
		doPost(InterfaceURL.BUYSELL_COMMENT, params, listener);
	}

	/**
	 * 供求（商品）/求购评论列表
	 */
	public void getBuysellCommentList(int type, String GoodorBuyInfoID,
			int lastID, int direct, int number,

			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("type", type);
		params.add("GoodorBuyInfoID", GoodorBuyInfoID);
		doGetForList(InterfaceURL.BUYSELL_COMMENT_LIST, params, number, direct,
				lastID, listener);
	}

	/**
	 * 订阅行情列表
	 * 
	 * @param appID
	 * @param number
	 * @param direct
	 * @param lastID
	 *            appID=1&number=3&direct=-1&lastID=15
	 * @param listener
	 */
	public void getMarketMessageList(int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		doGetForList(InterfaceURL.MARKET_MESSAGE_LIST, params, number, direct,
				lastID, listener);
	}

	/**
	 * 订阅行情详细
	 * 
	 * @param listener
	 * 
	 */
	public void getMarketMessageDetail(String MarketMessageID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("MarketMessageID", MarketMessageID);
		doGet(InterfaceURL.MARKET_MESSAGE_DETAIL, params, listener);
	}

	/**
	 * 展会列表
	 * 
	 * @param appID
	 * @param number
	 * @param direct
	 * @param lastID
	 *            appID=1&number=3&direct=-1&lastID=15
	 * @param listener
	 */
	public void getExhibitionList(int number, int direct, int lastID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		doGetForList(InterfaceURL.EXHIBITION_LIST, params, number, direct,
				lastID, listener);
	}

	/**
	 * 展会详细
	 * 
	 * @param listener
	 * 
	 */
	public void getExhibitionDetail(String id, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("id", id);
		doGet(InterfaceURL.EXHIBITION_DETAIL, params, listener);
	}

	/**
	 * 资讯分类列表
	 * 
	 * @param types
	 *            栏目的所有分类id，以,分隔
	 * @param listener
	 */
	public void getNewsCata(String types, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("types", types);
		params.add("appID", appID);
		doPost(InterfaceURL.NEWS_CATA, params, listener);
	}

	/**
	 * 企业黄页
	 * 
	 * @param listener
	 */
	public void searchEnterprise(RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		doGet(InterfaceURL.SEARCH_ENTER, params, listener);
	}

	/**
	 * 删除收藏
	 * 
	 * @param id
	 * @param listener
	 */
	public void deleteCollect(String id, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("ID", id);
		doPost(InterfaceURL.DELETE_COLLECT, params, listener);
	}

	/**
	 * 分类产品列表
	 * 
	 * @param appID
	 * @param number
	 * @param direct
	 * @param lastID
	 *            appID=1&number=3&direct=-1&lastID=15&cataCode=00010001
	 * @param listener
	 */
	public void getCataProductList(int number, int direct, int lastID,
			String cataCode, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("cataCode", cataCode);
		params.add("appID", appID);
		doGetForList(InterfaceURL.CATA_PRODUCT_LIST, params, number, direct,
				lastID, listener);
	}

	/**
	 * 
	 * 取得求购信息列表
	 * 
	 * @Title: getAllBuyInfos
	 * @Description: TODO
	 * @param @param number 页面容量
	 * @param @param direct
	 * @param @param lastID
	 * @param @param memberID 用户ID
	 * @param @param listener 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void getAllBuyInfos(int number, int direct, int lastID,
			String memberID, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("MemberID", memberID);
		params.add("appID", appID);
		doGetForList(InterfaceURL.GET_ALL_BUYINFOS, params, number, direct,
				lastID, listener);
	}

	/**
	 * 取列表数据时使用封装分页数据
	 * 
	 * @param url
	 * @param params
	 * @param listener
	 */
	private void doGetForList(final String url, final TixaParameters params,
			final int number, final int direct, final int lastID,
			RequestListener listener) {
		params.add("appID", appID);
		params.add("number", number);
		params.add("direct", direct);
		params.add("lastID", lastID);
		requestServer(url, params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 取列表数据时使用封装分页数据
	 * 
	 * @param url
	 * @param params
	 * @param listener
	 */
	private void doPostForList(final String url, final TixaParameters params,
			final int number, final int direct, final int lastID,
			RequestListener listener) {
		params.add("appID", appID);
		params.add("number", number);
		params.add("direct", direct);
		params.add("lastID", lastID);
		requestServer(url, params, HTTPMETHOD_POST, listener);
	}

	// 淘宝
	// 品牌折扣
	public void getBrandDiscount(String url, String cat, String method,
			String app_key, String format, String sign_method,
			String timestamp, String v, String sign, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("cat", cat);
		getDataFromTaobao(url, method, app_key, format, sign_method, timestamp,
				v, sign, params, listener);
	}

	// 品牌折扣商品
	public void getGoodsList_Brand(String url, String brand, String start,
			String method, String app_key, String format, String sign_method,
			String timestamp, String v, String sign, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("brand", brand);
		params.add("start", start);
		getDataFromTaobao(url, method, app_key, format, sign_method, timestamp,
				v, sign, params, listener);
	}

	// 淘宝折扣商品
	public void getGoodsList_TaoBao(String url, String cat, String start,
			String method, String app_key, String format, String sign_method,
			String timestamp, String v, String sign, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("cat", cat);
		params.add("start", start);
		getDataFromTaobao(url, method, app_key, format, sign_method, timestamp,
				v, sign, params, listener);
	}

	// 休闲娱乐
	public void getGoodsListByDistance(String excataCode, String distance,
			String lat, String lng, String sortType, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("excataCode", excataCode);
		params.add("distance", distance);
		params.add("lat", lat);
		params.add("lng", lng);
		params.add("sortType", sortType);
		doPost(InterfaceURL.GOODSLISTBYDISTANCE, params, listener);
	}

	// 我的评论
	public void getMyPageBuysellCommentList(String memberID,
			RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("memberID", memberID);
		doPost(InterfaceURL.MYPAGEBUYSELLCOMMENTLIST, params, listener);
	}

	// 我的消息
	public void getMyMessageList(String memberID, RequestListener listener) {
		TixaParameters params = new TixaParameters();
		params.add("appID", appID);
		params.add("memberID", memberID);
		doPost(InterfaceURL.MYPAGEINFORMATION, params, listener);
	}

	private void getDataFromTaobao(String url, String method, String app_key,
			String format, String sign_method, String timestamp, String v,
			String sign, TixaParameters params, RequestListener listener) {
		params.add("method", method);
		params.add("app_key", app_key);
		params.add("format", format);
		params.add("sign_method", sign_method);
		params.add("timestamp", timestamp);
		params.add("v", v);
		params.add("sign", sign);
		doPost(url, params, listener, true);
	}

	// POST方式
	private void doPost(final String url, final TixaParameters params,
			RequestListener listener) {
		requestServer(url, params, HTTPMETHOD_POST, listener);
	}

	// POST-utf-8方式
	private void doPost(final String url, final TixaParameters params,
			RequestListener listener, Boolean isUTF) {
		requestServer(url, params, HTTPMETHOD_POST, listener, true);
	}

	// GET方式
	private void doGet(final String url, final TixaParameters params,
			RequestListener listener) {
		requestServer(url, params, HTTPMETHOD_GET, listener);
	}

	/**
	 * 异步访问服务器
	 * 
	 * @param url
	 * @param params
	 * @param httpMethod
	 * @param listener
	 */
	private void requestServer(final String url, final TixaParameters params,
			final String httpMethod, final RequestListener listener) {
		new Thread() {
			@Override
			public void run() {
				try {
					String resp = HttpManager.openUrl(url, httpMethod, params,
							true);
					L.d("http", "resp=" + resp);
					listener.onComplete(resp);
				} catch (TixaException e) {
					listener.onError(e);
				}
			}
		}.start();
	}

	private void requestServer(final String url, final TixaParameters params,
			final String httpMethod, final RequestListener listener,
			boolean isUTF) {
		new Thread() {
			@Override
			public void run() {
				try {
					String resp = HttpManager.openUrl(url, httpMethod, params,
							false);
					L.d("http", "resp=" + resp);
					listener.onComplete(resp);
				} catch (TixaException e) {
					listener.onError(e);
				}
			}
		}.start();
	}
}
