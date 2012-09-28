package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class Encodingfilter implements Filter {

	protected FilterConfig filterConfig;
	private String targetEncoding="gb2312";
	public void destroy() {
		this.filterConfig=null;
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
		FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		request.setCharacterEncoding(targetEncoding);
		arg2.doFilter(arg0, arg1);
	}
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig=arg0;
		this.targetEncoding=arg0.getInitParameter("encoding");
	}
}
