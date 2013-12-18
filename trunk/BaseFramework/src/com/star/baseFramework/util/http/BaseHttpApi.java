/**
  @Title: HttpApi.java
  @Package com.tixa.fvexpress.api
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-16 下午03:03:59
  @version V1.0
 */

package com.star.baseFramework.util.http;

import java.util.HashMap;

import com.star.baseFramework.util.CustomException;

/**
 * @ClassName: HttpApi
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-16 下午03:03:59
 * 
 */
@SuppressWarnings("unused")
public class BaseHttpApi {
	private static int num = 111111;

	public void getTest_POST(String code) {

		ParamsWrapper map = new ParamsWrapper();
		map.put("cataCode", "0");
		map.put("zoneCode", "0");
		map.put("childTypeID", "0");
		map.put("appID", 261);
		map.put("number", "20");
		map.put("direct", "-1");
		map.put("lastID", "0");
		doPost(BaseIntenetUrl.TEST, map, new RequestListener() {
			@Override
			public void onError(Throwable exp) {
			}

			@Override
			public void onResponse(String content, String url, String token) {
				System.out.println("gggggggggggggggggggg: " + url);
				System.out.println("hhhhhhhhhhhhhhhhhhhh: " + token);
			}
		}, num + "", false);
		num++;
	}

	public void getTest_GET(String code) {
		ParamsWrapper map = new ParamsWrapper();
		map.put("cataCode", "0");
		map.put("zoneCode", "0");
		map.put("childTypeID", "0");
		map.put("appID", 261);
		map.put("number", "20");
		map.put("direct", "-1");
		map.put("lastID", "0");
		doGet(BaseIntenetUrl.TEST, map, new RequestListener() {
			@Override
			public void onError(Throwable exp) {
			}

			@Override
			public void onResponse(String content, String url, String token) {
				System.out.println("gggggggggggggggggggg: " + url);
				System.out.println("hhhhhhhhhhhhhhhhhhhh: " + token);
			}
		}, num + "");
		num++;
	}

	/*Post默认GBK编码，不带token*/
	private void doPost(final String url, final ParamsWrapper params,
			RequestListener listener) {
		doPost(url, params, listener, "", true);
	}

	/*Get默认GBK编码,不带token*/
	private void doGet(final String url, final ParamsWrapper params,
			RequestListener listener) {
		doGet(url, params, listener, "", true);
	}

	/*Post默认GBK编码，带token*/
	private void doPost(final String url, final ParamsWrapper params,
			RequestListener listener, String token) {
		doPost(url, params, listener, token, true);
	}

	/*Get默认GBK编码,带token*/
	private void doGet(final String url, final ParamsWrapper params,
			RequestListener listener, String token) {
		doGet(url, params, listener, token, true);
	}

	/*Get指定编码,不带token*/
	private void doGet(final String url, final ParamsWrapper params,
			RequestListener listener, Boolean isGBK) {
		doGet(url, params, listener, "", isGBK);
	}

	/*Post指定编码，不带token*/
	private void doPost(final String url, final ParamsWrapper params,
			RequestListener listener, Boolean isGBK) {
		doPost(url, params, listener, "", isGBK);
	}

	/*Post指定编码,带token*/
	private void doPost(final String url, final ParamsWrapper params,
			final RequestListener listener, final String token, Boolean isGBK) {
		requestServer(url, params, BaseHttpManager.HTTP_POST, listener, isGBK,
				token);
	}

	/*Get指定编码,带token*/
	private void doGet(final String url, final ParamsWrapper params,
			final RequestListener listener, final String token, Boolean isGBK) {
		requestServer(url, params, BaseHttpManager.HTTP_GET, listener, isGBK, token);
	}

	private void requestServer(final String url, final ParamsWrapper params,
			final String RequestMethod, final RequestListener listener,
			final Boolean isGBK, final String token) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String resp = BaseHttpManager.requestUrl(url, RequestMethod,
							params, isGBK);
					listener.onResponse(resp, url, token);
				} catch (CustomException e) {
					e.printStackTrace();
					listener.onError(e);
				}
			}
		}).start();
	}

	public static class ParamsWrapper extends HashMap<String, String> {
		private static final long serialVersionUID = 1L;

		public void put(String key, int value) {
			put(key, String.valueOf(value));
		};

		public void put(String key, long value) {
			put(key, String.valueOf(value));
		};

		public void put(String key, double value) {
			put(key, String.valueOf(value));
		};

		public void put(String key, float value) {
			put(key, String.valueOf(value));
		};

	}

	public static interface RequestListener {

		public void onError(Throwable exp);

		public void onResponse(String content, String url, String token);

	}

}
