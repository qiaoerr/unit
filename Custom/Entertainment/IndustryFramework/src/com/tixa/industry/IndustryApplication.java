package com.tixa.industry;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.AppInfo;
import com.tixa.industry.model.Goods;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.MemberUser;
import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.model.SlideMenuConfig;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;

public class IndustryApplication extends Application {
	private static IndustryApplication instantce;
	private String enterpriseID; // 企业id
	private String appID; // 客户端唯一编号
	private String templateId; // 模版id
	private IndexData mainData;
	private SlideMenuConfig config; // 滑动设置
	private int isSlide; // 是否左右滑动
	private SparseArray<ModularConfig> modularMap; // 模块对应关系
	private AppInfo info;
	private ArrayList<Goods> skimRecoder;
	private MemberUser memberUser;
	private LocationClient mLocationClient;
	private BDLocation mBdLocation;

	// baidumap
	private BMapManager mBMapManager = null;
	public static final String strKey = "AB39EEB5B8081625224CB1A366F8DBDF10E5CEC2";

	public ArrayList<Goods> getSkimRecoder() {
		return skimRecoder;
	}

	public void setSkimRecoder(ArrayList<Goods> skimRecoder) {
		this.skimRecoder = skimRecoder;
	}

	public BMapManager getmBMapManager() {
		return mBMapManager;
	}

	public void setmBMapManager(BMapManager mBMapManager) {
		this.mBMapManager = mBMapManager;
	}

	public MemberUser getMemberUser() {
		return memberUser;
	}

	public void setMemberUser(MemberUser memberUser) {
		this.memberUser = memberUser;
	}

	public AppInfo getInfo() {
		return info;
	}

	public void setInfo(AppInfo info) {
		this.info = info;
	}

	public IndexData getMainData() {
		return mainData;
	}

	public void setMainData(IndexData mainData) {
		this.mainData = mainData;
	}

	public String getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(String enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public SlideMenuConfig getConfig() {
		return config;
	}

	public void setConfig(SlideMenuConfig config) {
		this.config = config;
	}

	public int getIsSlide() {
		return isSlide;
	}

	public void setIsSlide(int isSlide) {
		this.isSlide = isSlide;
	}

	public static IndustryApplication getInstance() {
		return instantce;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instantce = this;
		skimRecoder = getFromLocal("skimCache");
		if (skimRecoder == null) {
			skimRecoder = new ArrayList<Goods>();
		}
		initData();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		initAccountInfo();
		// 定位
		mLocationClient = new LocationClient(instantce);
		mLocationClient.setLocOption(getLocOption());
		mLocationClient.registerLocationListener(new MyLocationListener());
		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			L.d("LocSDK3", "locClient is null or not started");
		// init BMapManager
		initEngineManager(this);
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(
					IndustryApplication.getInstance().getApplicationContext(),
					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	private void initAccountInfo() {
		if (this.memberUser == null)
			this.memberUser = new MemberUser(getLoginJson());
	}

	private JSONObject getLoginJson() {
		try {
			SharedPreferences sf = getSharedPreferences("userMessage", 0);
			String loginStr = sf.getString("userMessage", "");
			L.d("test", "loginStr=" + loginStr);
			return new JSONObject(loginStr);
		} catch (JSONException e) {
			return new JSONObject();
		}
	}

	public void initData() {
		this.enterpriseID = this.getResources().getText(R.string.enterpriseid)
				.toString()
				+ "";
		this.appID = this.getResources().getText(R.string.appid).toString()
				+ "";
		this.templateId = this.getResources().getString(R.string.templateid)
				.toString()
				+ "";
	}

	public SparseArray<ModularConfig> getModularMap() {
		return modularMap;
	}

	public void setModularMap(SparseArray<ModularConfig> modularMap) {
		this.modularMap = modularMap;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public long getMemberID() {
		if (memberUser != null)
			return memberUser.getMemberID();
		else
			return 0;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	private ArrayList<Goods> getFromLocal(String fileName) {
		String filePath = Constants.CACHE_DIR + appID + "/"
				+ Constants.SKIM_DIR + "/" + fileName;
		ArrayList<Goods> data = (ArrayList<Goods>) FileUtil.getFile(filePath);
		return data;
	}

	private LocationClientOption getLocOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		return option;
	}

	public BDLocation getmBdLocation() {
		return mBdLocation;
	}

	public void setmBdLocation(BDLocation mBdLocation) {
		this.mBdLocation = mBdLocation;
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// location.getCity();
			}
			setmBdLocation(location);
			// System.out.println(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			// System.out.println(sb.toString());
		}
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						IndustryApplication.getInstance()
								.getApplicationContext(), "您的网络出错啦！",
						Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						IndustryApplication.getInstance()
								.getApplicationContext(), "输入正确的检索条件！",
						Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(
						IndustryApplication.getInstance()
								.getApplicationContext(),
						"请在 DemoApplication.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				// IndustryApplication.getInstance().m_bKeyRight = false;
			}
		}
	}
}
