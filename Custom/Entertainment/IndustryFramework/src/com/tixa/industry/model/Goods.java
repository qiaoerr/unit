package com.tixa.industry.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.tixa.industry.config.InterfaceURL;

/**
 * 商品//搜索
 * 
 * @author Administrator
 * 
 */

public class Goods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8323093960386756469L;
	private long id;
	private int type;// 1为供应商品；2为求购商品
	private String goodsName;
	private String goodsCode;
	private String cataCode;
	private long brandID;
	private String goodsPrice;
	private int orderPrice;
	private String goodsUnit;
	private int discount;
	private int presentInte;
	private int presentLevel;
	private int buyIntegral;
	private String goodsThumb;
	private String goodsImg;
	private int goodsWeight;
	private String WeightUnit;
	private int goodsNumber;
	private int isBest;
	private int isNew;
	private int isHot;
	private int isGoods;
	private int status;
	private String keywords;
	private String goodsBrief;
	private String goodsDescStr;
	private int warnNumber;
	private String customerService;
	private String packList;
	private int clickCount;
	private int orderNum;
	private long appID;
	private long enterpriseID;
	private String subject;
	private String createTime;
	private String goodsStandard;
	private String contact;
	private String tel;
	private String address;
	private String email;
	private String imagePath;
	private long collectID;
	private int expectScore;
	private int buySellCommentCount;
	private String goodsParameters; //规格参数
	private int searchType; //抓取商品
	
	
	private ArrayList<Goods> LinkedGoods;

	public Goods() {
	};

	// 供应商品构造方法
	public Goods(JSONObject json) {
		this.type = 1;
		this.id = json.optLong("ID");
		this.goodsName = json.optString("goodsName");
		this.goodsCode = json.optString("goodsCode");
		this.cataCode = json.optString("cataCode");
		this.brandID = json.optLong("brandID");
		this.goodsPrice = json.optInt("goodsPrice") + "";
		this.orderPrice = json.optInt("orderPrice");
		this.goodsUnit = json.optString("goodsUnit");
		this.discount = json.optInt("discount");
		this.presentInte = json.optInt("presentInte");
		this.presentLevel = json.optInt("presentLevel");
		this.buyIntegral = json.optInt("buyIntegral");
		this.goodsParameters = json.optString("GoodsParameters");
		this.searchType = json.optInt("searchType");
		
		if(searchType == 1) { //傻目录图片
			this.goodsThumb = InterfaceURL.SUMULUIMG + json.optString("goodsThumb");
			this.goodsImg = InterfaceURL.SUMULUIMG + json.optString("goodsThumb");
		}else if(searchType == 2){ //阿里巴巴图片
			this.goodsThumb = json.optString("goodsThumb");
			this.goodsImg = json.optString("goodsThumb");
		}else{	
			this.goodsThumb = InterfaceURL.IMGIP + json.optString("goodsThumb");
			this.goodsImg = InterfaceURL.IMGIP + json.optString("goodsImg");
		}
		
		this.goodsWeight = json.optInt("goodsWeight");
		this.WeightUnit = json.optString("WeightUnit");
		this.goodsNumber = json.optInt("goodsNumber");
		this.isBest = json.optInt("isBest");
		this.isNew = json.optInt("isNew");
		this.isHot = json.optInt("isHot");
		this.isGoods = json.optInt("isGoods");
		this.status = json.optInt("status");
		this.keywords = json.optString("keywords");
		this.goodsBrief = json.optString("goodsBrief");
		this.goodsDescStr = json.optString("goodsDescStr");
		this.warnNumber = json.optInt("warnNumber");
		this.customerService = json.optString("customerService");
		this.packList = json.optString("packList");
		this.clickCount = json.optInt("clickCount");
		this.orderNum = json.optInt("orderNum");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
		this.expectScore = json.optInt("expectScore");
		this.LinkedGoods = new ArrayList<Goods>();
		
		this.buySellCommentCount = json.optInt("buySellCommentCount");
		
		if(json.has("LinkedGoods")) {
			JSONArray arr = json.optJSONArray("LinkedGoods");
			if(arr != null) {
				for(int i=0;i<arr.length();i++) {
					JSONObject o = arr.optJSONObject(i);
					Goods g = new Goods(o);
					LinkedGoods.add(g);
				}
			}
		}	
		
	}

	// 求购商品构造方法
	public Goods(Context context, JSONObject json) {
		this.type = 2;
		this.id = json.optLong("ID");
		this.cataCode = json.optString("cataCode");
		this.status = json.optInt("status");
		this.subject = json.optString("subject");
		this.createTime = json.optString("createTime");
		this.goodsNumber = json.optInt("goodsNumber");
		this.packList = json.optString("packList");
		this.goodsPrice = json.optString("goodsPrice");
		this.goodsStandard = json.optString("goodsStandard");
		this.contact = json.optString("contact");
		this.tel = json.optString("tel");
		this.address = json.optString("address");
		this.email = json.optString("email");
		this.imagePath = InterfaceURL.IMGIP + json.optString("imagePath");
		this.goodsDescStr = json.optString("goodsDescStr");
		this.appID = json.optLong("appID");
		this.enterpriseID = json.optLong("enterpriseID");
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}
	
	public String getGoodsParameters() {
		return goodsParameters;
	}

	public void setGoodsParameters(String goodsParameters) {
		this.goodsParameters = goodsParameters;
	}

	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public int getBuySellCommentCount() {
		return buySellCommentCount;
	}

	public void setBuySellCommentCount(int buySellCommentCount) {
		this.buySellCommentCount = buySellCommentCount;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getCataCode() {
		return cataCode;
	}

	public void setCataCode(String cataCode) {
		this.cataCode = cataCode;
	}

	public long getBrandID() {
		return brandID;
	}

	public void setBrandID(long brandID) {
		this.brandID = brandID;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPresentInte() {
		return presentInte;
	}

	public void setPresentInte(int presentInte) {
		this.presentInte = presentInte;
	}

	public int getPresentLevel() {
		return presentLevel;
	}

	public ArrayList<Goods> getLinkedGoods() {
		return LinkedGoods;
	}

	public void setLinkedGoods(ArrayList<Goods> linkedGoods) {
		LinkedGoods = linkedGoods;
	}

	public void setPresentLevel(int presentLevel) {
		this.presentLevel = presentLevel;
	}

	public int getBuyIntegral() {
		return buyIntegral;
	}

	public void setBuyIntegral(int buyIntegral) {
		this.buyIntegral = buyIntegral;
	}

	public String getGoodsThumb() {
		return goodsThumb;
	}

	public void setGoodsThumb(String goodsThumb) {
		this.goodsThumb = goodsThumb;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public int getExpectScore() {
		return expectScore;
	}

	public void setExpectScore(int expectScore) {
		this.expectScore = expectScore;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public int getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(int goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public String getWeightUnit() {
		return WeightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		WeightUnit = weightUnit;
	}

	public int getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public int getIsBest() {
		return isBest;
	}

	public void setIsBest(int isBest) {
		this.isBest = isBest;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public int getIsGoods() {
		return isGoods;
	}

	public void setIsGoods(int isGoods) {
		this.isGoods = isGoods;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getGoodsBrief() {
		return goodsBrief;
	}

	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}

	public String getGoodsDescStr() {
		return goodsDescStr;
	}

	public void setGoodsDescStr(String goodsDescStr) {
		this.goodsDescStr = goodsDescStr;
	}

	public int getWarnNumber() {
		return warnNumber;
	}

	public void setWarnNumber(int warnNumber) {
		this.warnNumber = warnNumber;
	}

	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	public String getPackList() {
		return packList;
	}

	public void setPackList(String packList) {
		this.packList = packList;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public long getAppID() {
		return appID;
	}

	public void setAppID(long appID) {
		this.appID = appID;
	}

	public long getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(long enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	public long getCollectID() {
		return collectID;
	}

	public void setCollectID(long collectID) {
		this.collectID = collectID;
	};

}