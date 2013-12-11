/**
  @Title: BDMapCommentUtil.java
  @Package com.start.jdzchina.util
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-11-27 下午04:59:39
  @version V1.0
 */

package com.star.efficientdevelop.util.map;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.star.efficientdevelop.BaseApplication;

/**
 * @ClassName: BDMapCommentUtil
 * @百度地图地理编码与反地理编码工具类
 * @author Comsys-Administrator
 * @date 2013-11-27 下午04:59:39
 * 
 */

public class BDMapCommentUtil {
	private MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private Context context;
	private SearchSuccessListener listener;

	public BDMapCommentUtil(Context context, SearchSuccessListener listener) {
		super();
		this.context = context;
		this.listener = listener;
		init();
	}

	private void init() {
		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		mSearch.init(BaseApplication.getInstance().getmBMapManager(),
				new MKSearchListener() {
					@Override
					public void onGetPoiDetailSearchResult(int type, int error) {
					}

					public void onGetAddrResult(MKAddrInfo res, int error) {
						if (error != 0) {
							String str = String.format("错误号：%d", error);
							Toast.makeText(context, str, Toast.LENGTH_LONG)
									.show();
							return;
						}
						if (res.type == MKAddrInfo.MK_GEOCODE) {
							// 地理编码：通过地址检索坐标点
							String strInfo = String.format("纬度：%f 经度：%f",
									res.geoPt.getLatitudeE6() / 1e6,
									res.geoPt.getLongitudeE6() / 1e6);
							// Toast.makeText(context, strInfo,
							// Toast.LENGTH_LONG)
							// .show();
							if (listener != null) {
								listener.dealWith(res, strInfo);
							}
						}
						if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
							// 反地理编码：通过坐标点检索详细地址及周边poi
							String strInfo = res.strAddr;
							// Toast.makeText(context, strInfo,
							// Toast.LENGTH_LONG)
							// .show();
							if (listener != null) {
								listener.dealWith(res, strInfo);
							}

						}
					}

					public void onGetPoiResult(MKPoiResult res, int type,
							int error) {

					}

					public void onGetDrivingRouteResult(
							MKDrivingRouteResult res, int error) {
					}

					public void onGetTransitRouteResult(
							MKTransitRouteResult res, int error) {
					}

					public void onGetWalkingRouteResult(
							MKWalkingRouteResult res, int error) {
					}

					public void onGetBusDetailResult(MKBusLineResult result,
							int iError) {
					}

					@Override
					public void onGetSuggestionResult(MKSuggestionResult res,
							int arg1) {
					}

				});
	}

	public void getReverseGeo(float lat, float lon) {
		GeoPoint ptCenter = new GeoPoint((int) (lat * 1e6), (int) (lon * 1e6));
		mSearch.reverseGeocode(ptCenter);
	}

	public void getGeo(String strAddr, String city) {
		mSearch.geocode(strAddr, city);
	}

	public interface SearchSuccessListener {
		public void dealWith(MKAddrInfo res, String str);
	}
}
