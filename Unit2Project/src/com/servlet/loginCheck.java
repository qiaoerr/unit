package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.User;
import com.database.User_Operation;

public class loginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		request.setCharacterEncoding("gbk");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		ArrayList<User> users = new User_Operation().getUser();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().equals(username)
					&& users.get(i).getPassword().equals(password)) {
				session.setAttribute("userid", users.get(i).getUserid());
				request.getRequestDispatcher("mainpanel.jsp").forward(request,
						response);
				return;
			}
		}
		out.println("<script>");
		out.println("alert('µÇÂ¼Ê§°Ü£¬ÇëÖØÐÂµÇÂ¼£¡');");
		out.println("open('index.jsp','_self');");
		out.println("</script>");
		out.flush();
		out.close();
	}
}
