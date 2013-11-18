package com.tixa.industry.model;

import java.io.Serializable;

import com.tixa.industry.util.PinyinUtil;
import com.tixa.industry.util.StrUtil;

public class CountryCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2195864411037126820L;
	private String countryName;
	private String countryCode;
	private char countryName_pinyin;
	
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
		try {
			if(StrUtil.isEmpty(countryName)){
				setCountryName_pinyin('#');
			}else {
				String sortKey = PinyinUtil.getSortKey(this.countryName);
				if(StrUtil.isNotEmpty(sortKey) && sortKey.length()>0){
					setCountryName_pinyin(sortKey.charAt(0));
				}else {
					setCountryName_pinyin('#');
				}
			}
		} catch (Exception e) {
			setCountryName_pinyin('#');
		}
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public char getCountryName_pinyin() {
		return countryName_pinyin;
	}

	public void setCountryName_pinyin(char c) {
		this.countryName_pinyin = c;
	}
}
