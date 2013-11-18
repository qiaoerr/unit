package com.tixa.industry.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * @author shengy
 *
 */
public class Utility {
	
	/**
	 * 把url格式化为Bundle对象
	 * @param s
	 * @return
	 */	
	public static Bundle parseUrl(String url) {
			try {
				URL u = new URL(url);
				//u.getQuery() 取得URL的查询部分
				//u.getRef() 获取此 URL 的锚点
				Bundle b = decodeUrl(u.getQuery());
				b.putAll(decodeUrl(u.getRef()));
				return b;
			} catch (MalformedURLException e) {
				return new Bundle();
			}
		}
	
	/**
	 * 把url(?之后的内容)格式化为Bundle对象
	 * @param s
	 * @return
	 */
	public static Bundle decodeUrl(String s) {
			Bundle params = new Bundle();
			if (s != null) {
				String array[] = s.split("&");
				for (String parameter : array) {
					String v[] = parameter.split("=");
					params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
				}
			}
			return params;
		}
	
	/**
	 * 把Parameters对象 格式化为标准URL
	 * @param parameters
	 * @return
	 */
	public static String encodeUrl(TixaParameters parameters) {
		if (parameters == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int loc = 0; loc < parameters.size(); loc++) {
			if (first){
			    first = false;
			}
			else{
			    sb.append("&");
			}
			String _key=parameters.getKey(loc);
			String _value=parameters.getValue(_key);
			if(_value==null){
			    L.i("encodeUrl", "key:"+_key+" 's value is null");
			}
			else{
			    sb.append(URLEncoder.encode(parameters.getKey(loc)) + "="
                        + URLEncoder.encode(parameters.getValue(loc)));
			}
			
		}
		return sb.toString();
	}

	/**
	 * 把Parameters对象 格式化为标准URL(UTF-8编码)
	 * @param parameters
	 * @return
	 */
	public static String encodeParameters(TixaParameters httpParams) {
		if (null == httpParams || isBundleEmpty(httpParams)) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		int j = 0;
		for (int loc = 0; loc < httpParams.size(); loc++) {
			String key = httpParams.getKey(loc);
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(key, "UTF-8")).append("=")
						.append(URLEncoder.encode(httpParams.getValue(key), "UTF-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
			j++;
		}
		return buf.toString();

	}
	
	/**
	 * 把Parameters对象 格式化为标准URL(GBK编码)
	 * @param parameters
	 * @return
	 */
	public static String encodeParametersGBK(TixaParameters httpParams) {
		if (null == httpParams || isBundleEmpty(httpParams)) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		int j = 0;
		for (int loc = 0; loc < httpParams.size(); loc++) {
			String key = httpParams.getKey(loc);
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(key, "GBK")).append("=")
						.append(URLEncoder.encode(httpParams.getValue(key), "GBK"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
			j++;
			
		}
		return buf.toString();

	}
	
	/**
	 * 判断bundle对象是否为空
	 * @param bundle
	 * @return
	 */
	private static boolean isBundleEmpty(TixaParameters bundle) {
		if (bundle == null || bundle.size() == 0) {
			return true;
		}
		return false;
	}


}
