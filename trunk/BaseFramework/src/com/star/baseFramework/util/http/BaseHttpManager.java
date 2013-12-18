/**
  @Title: HttpManager.java
  @Package com.tixa.fvexpress.util
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2013-12-16 下午03:02:42
  @version V1.0
 */

package com.star.baseFramework.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.database.Cursor;
import android.net.Uri;

import com.star.baseFramework.BaseApplication;
import com.star.baseFramework.util.CustomException;
import com.star.baseFramework.util.L;

/**
 * @ClassName: HttpManager
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2013-12-16 下午03:02:42
 * 
 */

public class BaseHttpManager {
	private static int CONNECTION_TIMEOUT = 5 * 1000;
	private static int SOCKET_TIMEOUT = 10 * 1000;
	private static String CODE = "GBK";// 编码格式
	private static HttpClient client;
	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";

	public static String requestUrl(String url, String method,
			Map<String, String> paramsMap, Boolean isGBK)
			throws CustomException {
		HttpUriRequest request = null;
		if (isGBK) {
			CODE = "GBK";
		} else {
			CODE = "UTF-8";
		}
		try {
			String result = "";
			if (client == null) {
				client = getNewHttpClient();
			}
			if (!CODE.equals("GBK")) {
				/*client.getParams().setParameter("contentCharset", HTTP.UTF_8);*/
				HttpProtocolParams.setContentCharset(client.getParams(),
						HTTP.UTF_8);
			}
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
			Iterator<String> it = paramsMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = paramsMap.get(key);
				list.add(new BasicNameValuePair(key, value));
			}
			/* GET方式*/
			if (method.equals(HTTP_GET)) {
				// 对参数编码
				String param = URLEncodedUtils.format(list, CODE);
				url = url + "?" + param;
				HttpGet get = new HttpGet(url);
				request = get;
				L.d("http", "url=" + url);
				/*post方式*/
			} else if (method.equals(HTTP_POST)) {
				HttpPost post = new HttpPost(url);
				request = post;
				// 客户端提交给服务器文本内容的编码方式 是URL编码
				post.setHeader("Content-Type",
						"application/x-www-form-urlencoded");
				HttpEntity entity = new UrlEncodedFormEntity(list, CODE);
				// 将参数填入POST Entity中
				post.setEntity(entity);
				L.d("http",
						"url=" + url + "?" + URLEncodedUtils.format(list, CODE));
			} else if (method.equals("DELETE")) {
				request = new HttpDelete(url);
			}
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();
			if (statusCode != 200) {
				result = readHttpResponse(response);
				throw new CustomException(result, statusCode);
			}
			result = readHttpResponse(response);
			L.d("http", "result: " + result);
			return result;
		} catch (IOException e) {
			throw new CustomException(e);
		}

	}

	private static String readHttpResponse(HttpResponse response) {
		String result = "";
		HttpEntity entity = response.getEntity();
		Header header = response.getFirstHeader("Content-Encoding");
		InputStream inputStream = null;
		ByteArrayOutputStream content = null;
		if (header != null
				&& header.getValue().toLowerCase(Locale.CHINA).indexOf("gzip") > -1) {
			try {
				inputStream = entity.getContent();
				content = new ByteArrayOutputStream();
				inputStream = new GZIPInputStream(inputStream);
				int readBytes = 0;
				byte[] sBuffer = new byte[512];
				while ((readBytes = inputStream.read(sBuffer)) != -1) {
					content.write(sBuffer, 0, readBytes);
				}
				result = new String(content.toByteArray(), CODE);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			} finally {
				try {
					inputStream.close();
					content.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				// 只有当从entity中无法获得字符编码集时采用CODE
				return EntityUtils.toString(entity, CODE);
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
		}
	}

	/**
	 * 配置HttpClient
	 */
	private static HttpClient getNewHttpClient() {
		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params,
					CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			if (!CODE.equals("GBK")) {
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			}
			/* 设置代理*/
			// if (getAPN() != null) {
			// params.setParameter(ConnRoutePNames.DEFAULT_PROXY, getAPN());
			// }
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			HttpClient client = new DefaultHttpClient(ccm, params);
			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	/**
	 * 取到当前的APN 待修复后使用
	 */
	public static HttpHost getAPN() {
		HttpHost proxy = null;
		Uri uri = Uri.parse("content://telephony/carriers/preferapn");
		Cursor mCursor = BaseApplication.getInstance().getContentResolver()
				.query(uri, null, null, null, null);
		while (mCursor.moveToNext()) {
			System.out.println("proxy: "
					+ mCursor.getString(mCursor.getColumnIndex("proxy")));

		}
		if (mCursor != null && mCursor.moveToFirst()) {

			String proxyStr = mCursor
					.getString(mCursor.getColumnIndex("proxy"));
			if (proxyStr != null && proxyStr.trim().length() > 0) {
				proxy = new HttpHost(proxyStr, 80);
			}
			mCursor.close();
		}
		return proxy;
	}
}
