package com.tixa.industry.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * HTTP链接管理类
 * 
 * @author shengy
 * 
 */
public class HttpManager {
	private static final String HTTPMETHOD_POST = "POST";
	private static final String HTTPMETHOD_GET = "GET";
	private static final int SET_CONNECTION_TIMEOUT = 5 * 1000;
	private static final int SET_SOCKET_TIMEOUT = 20 * 1000;
	private static String CODE = "GBK"; // 接口的编码

	public static String openUrl(String url, String method,
			TixaParameters params, Boolean isGBK) throws TixaException {

		L.e("isGBK  =" + isGBK);
		L.e("CODE  =" + CODE);

		if (!isGBK) {
			CODE = "UTF-8";
		} else {
			CODE = "GBK";
		}

		L.e("CODE isGBK =" + CODE);
		try {
			String result = "";
			HttpClient client = getNewHttpClient();
			HttpUriRequest request = null;
			ByteArrayOutputStream bos = null;
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					NetStateManager.getAPN());
			if (method.equals(HTTPMETHOD_GET)) { // GET
				url = url + "?" + Utility.encodeUrl(params);
				L.d("http", "url=" + url);
				HttpGet get = new HttpGet(url);
				request = get;
			} else if (method.equals(HTTPMETHOD_POST)) {// post方式
				L.d("http", "url=" + url);
				HttpPost post = new HttpPost(url);
				request = post;
				byte[] data = null;
				String _contentType = params.getValue("content-type");
				bos = new ByteArrayOutputStream();
				if (_contentType != null) {
					params.remove("content-type");
					post.setHeader("Content-Type", _contentType);
				} else {
					post.setHeader("Content-Type",
							"application/x-www-form-urlencoded");
				}

				String postParam = "";
				if (CODE.equals("GBK")) {
					postParam = Utility.encodeParametersGBK(params);
					L.d("http", "postParam=" + postParam);
					data = postParam.getBytes("GBK");
				} else {
					postParam = Utility.encodeParameters(params);
					data = postParam.getBytes("UTF-8");
				}

				bos.write(data);

				data = bos.toByteArray();
				bos.close();
				ByteArrayEntity formEntity = new ByteArrayEntity(data);
				post.setEntity(formEntity);
			} else if (method.equals("DELETE")) {
				request = new HttpDelete(url);
			}
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();
			if (statusCode != 200) {
				result = readHttpResponse(response);
				throw new TixaException(result, statusCode);
			}
			result = readHttpResponse(response);
			return result;
		} catch (IOException e) {
			throw new TixaException(e);
		}

	}

	/**
	 * 配置HttpClient
	 * 
	 * @return
	 */
	private static HttpClient getNewHttpClient() {
		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 10000);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			if (!CODE.equals("GBK")) {
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			}
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);
			HttpConnectionParams.setConnectionTimeout(params,
					SET_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SET_SOCKET_TIMEOUT);
			HttpClient client = new DefaultHttpClient(ccm, params);
			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	private static String readHttpResponse(HttpResponse response) {
		String result = "";
		HttpEntity entity = response.getEntity();

		InputStream inputStream;
		try {
			inputStream = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();
			Header header = response.getFirstHeader("Content-Encoding");
			if (header != null
					&& header.getValue().toLowerCase().indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}
			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			result = new String(content.toByteArray(), CODE);
			return result;

		} catch (IllegalStateException e) {
		} catch (IOException e) {
		}
		return result;
	}
}
