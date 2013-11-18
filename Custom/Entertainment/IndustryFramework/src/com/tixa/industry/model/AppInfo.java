package com.tixa.industry.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.tixa.industry.config.InterfaceURL;
import com.tixa.industry.util.L;

/**
 * 软件相关
 * @author shengy
 *
 */
public class AppInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5008537732904159877L;
	private long appID;  //appID
	private long enterpriseID; //公司编号
	private long templateID; //模板编号
	private long colorID; //颜色名称
	private String apkName; //APK名称
	private String apkIcon; //APK图标
	private String apkUrl; //APKUrl
	private String aboutUs; //关于我们
	private String tel; //联系电话
	private String email; //客服邮箱
	private String weibo; //微博
	private String protocol; //服务条款协议
	private double longitude; //经度
	private double latitude; //纬度
	private String appIntroduce; //软件介绍
	private String useIntroduce; //使用帮助
	
	public AppInfo(JSONObject obj) {
		this.appID = obj.optLong("APKID");
		this.enterpriseID = obj.optLong("EnterpriseID");
		this.templateID = obj.optLong("templateID");
		this.colorID = obj.optLong("colorID");
		this.apkName = obj.optString("APKName");
		this.apkIcon = InterfaceURL.IMGIP + obj.optString("APKIcon");
		this.apkUrl = obj.optString("apkUrl");
		this.aboutUs = obj.optString("aboutUS");
		this.tel = obj.optString("tel");
		this.email = obj.optString("email");
		this.weibo = obj.optString("weibo");
		this.protocol = obj.optString("protocol");
		this.longitude = obj.optDouble("longitude");
		this.latitude = obj.optDouble("latitude");
		this.appIntroduce = obj.optString("AppIntroduce");
		this.useIntroduce = obj.optString("UseIntroduce");
		
	}
	
	public AppInfo() {
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
	public long getTemplateID() {
		return templateID;
	}
	public void setTemplateID(long templateID) {
		this.templateID = templateID;
	}
	public long getColorID() {
		return colorID;
	}
	public void setColorID(long colorID) {
		this.colorID = colorID;
	}
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public String getApkIcon() {
		L.d("test", "apkIcon1="+apkIcon);
		return apkIcon;
	}
	public void setApkIcon(String apkIcon) {
		this.apkIcon = apkIcon;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	public String getAboutUs() {
		return aboutUs;
	}
	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeibo() {
		return weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getAppIntroduce() {
		L.d("test", "appIntroduce="+appIntroduce);
		return appIntroduce;
	}
	public void setAppIntroduce(String appIntroduce) {
		this.appIntroduce = appIntroduce;
	}
	public String getUseIntroduce() {
		return useIntroduce;
	}
	public void setUseIntroduce(String useIntroduce) {
		this.useIntroduce = useIntroduce;
	}

}	
