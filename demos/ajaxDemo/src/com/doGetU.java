package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class doGetU extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public doGetU() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("uname");
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		StringBuilder bd = new StringBuilder();
		bd.append("<userinfo>");
		bd.append("<username>");
		if (uname == null || uname.length() == 0) {
			bd.append("«Î—°‘Ò”√ªß");
		} else {
			bd.append(uname);
		}
		bd.append("</username>");
		bd.append("</userinfo>");
		out.println(bd.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
