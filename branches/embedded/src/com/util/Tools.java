package com.util;

import java.io.UnsupportedEncodingException;

public class Tools {
	public static String getstr(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return str;
	}
}
