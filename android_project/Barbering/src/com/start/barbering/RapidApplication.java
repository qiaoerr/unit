package com.start.barbering;

import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.star.baseFramework.BaseApplication;
import com.star.baseFramework.util.map.BDLocationUtil;
import com.star.baseFramework.util.map.BDLocationUtil.LocationSuccessListener;
import com.start.barbering.config.Constants;
import com.start.barbering.model.BaseInforModel;

/**
 * @ClassName: ExpressApplication
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-10-21 下午04:27:55
 * 
 */

public class RapidApplication extends BaseApplication {
	private static RapidApplication expressApplication;
	private BDLocation bdLocation = null;
	private BaseInforModel baseInfor;

	public static RapidApplication getInstance() {
		return expressApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		expressApplication = this;
		// 初始化极光推送
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		// CrashHandler.init(this);
		// 判断是否开启进入app即开始定位
		if (Constants.EnterLocation) {
			BDLocationUtil.getLocation(expressApplication,
					new LocationSuccessListener() {

						@Override
						public void dealWithLocationData(BDLocation location) {
							bdLocation = location;
						}
					});
		}
		// init baseinfor
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

	public BaseInforModel getBaseInfor() {
		return baseInfor;
	}

}
