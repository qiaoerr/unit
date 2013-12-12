package com.star.middleframework;

import com.baidu.location.BDLocation;
import com.star.baseFramework.BaseApplication;
import com.star.baseFramework.util.map.BDLocationUtil;
import com.star.baseFramework.util.map.BDLocationUtil.LocationSuccessListener;
import com.star.middleframework.config.Constants;
import com.star.middleframework.model.BaseInforModel;
import com.star.middleframework.widget.MyRouteMapView;

/**
 * @ClassName: ExpressApplication
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-10-21 下午04:27:55
 * 
 */

public class MiddleApplication extends BaseApplication {
	private static MiddleApplication middleApplication;
	private BDLocation bdLocation = null;
	private MyRouteMapView mapView;
	private String res_prefix = "default_";
	private BaseInforModel baseInfor;

	public static MiddleApplication getInstance() {
		return middleApplication;
	}

	/**
	 * 判断是否进入app就定位
	 */
	@Override
	public void onCreate() {
		middleApplication = this;
		super.onCreate();
		// CrashHandler.init(this);
		// 判断是否开启进入app即开始定位
		if (Constants.EnterLocation) {
			BDLocationUtil.getLocation(middleApplication,
					new LocationSuccessListener() {

						@Override
						public void dealWithLocationData(BDLocation location) {
							bdLocation = location;
						}
					});
		}
		// 初始化baseinfor （待修改）
		initBaseInfor();
	}

	private void initBaseInfor() {
		baseInfor = new BaseInforModel();
		baseInfor.setAddress("北京市海淀区上地十街十号");
		baseInfor.setLatitude(40.056885f);
		baseInfor.setLongitude(116.30815f);
	}

	public BDLocation getBdLocation() {
		return bdLocation;
	}

	public MyRouteMapView getMapView() {
		return mapView;
	}

	public void setMapView(MyRouteMapView mapView) {
		this.mapView = mapView;
	}

	public String getRes_prefix() {
		return res_prefix;
	}

	public void setRes_prefix(String res_prefix) {
		this.res_prefix = res_prefix;
	}

	public BaseInforModel getBaseInfor() {
		return baseInfor;
	}

}
