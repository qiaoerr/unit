package study.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import study.mp3player.AppConstant;

public class HttpDownLoader {
	private String path = null;
	private BufferedReader br = null;
	private StringBuffer sb = null;

	public HttpDownLoader(String path) {
		this.path = path;
	}

	public HttpDownLoader() {

	}

	public String downLoader() {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			try {
				conn.connect();
			} catch (Exception e) {
				return "";
			}

			InputStream in = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			sb = new StringBuffer();
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public int downLoad(String url, String path, String fileName) {
		FileUtils fileUtils = new FileUtils();
		File file = null;
		if (fileUtils.isFileExist(fileName, path)) {
			return AppConstant.EXIST;
		} else {
			InputStream input = getInputStream(url);
			file = fileUtils.write2SDFromInput(path, fileName, input);
		}
		if (file == null) {
			return AppConstant.FAILED;
		} else {
			return AppConstant.SUCCESS;
		}
	}

	private InputStream getInputStream(String urlparam) {
		InputStream input = null;
		try {
			URL url = new URL(urlparam);
			input = ((HttpURLConnection) url.openConnection()).getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

}
