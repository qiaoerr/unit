/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2012-09-29 08:19:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\r\n");
      out.write("<meta http-equiv=\"Pragma\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"Expires\" content=\"0\"/>   \r\n");
      out.write(" \r\n");
      out.write("<title>超市管理系统</title>\r\n");
      out.write("<link href=\"css/unit2_a.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("  function check(form){\r\n");
      out.write("    if(form.username.value==\"\"){\r\n");
      out.write("     alert(\"用户名不能为空！\");\r\n");
      out.write("     return false;\r\n");
      out.write("    }\r\n");
      out.write("    if(form.password.value==\"\"){\r\n");
      out.write("     alert(\"密码不能为空！\");\r\n");
      out.write("     return false;\r\n");
      out.write("    }\r\n");
      out.write("    return true;\r\n");
      out.write("  }\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("  <div class='middle'>\r\n");
      out.write("  ");
      out.write("\r\n");
      out.write("  <form id=\"formtest\" action=\"login\" method=post onsubmit=\"return check(this);\" >\r\n");
      out.write("   <table >\r\n");
      out.write("     <tr>\r\n");
      out.write("       <td>用户名：</td>\r\n");
      out.write("       <td><input name=username type=text value=\"\" size=22 /></td>\r\n");
      out.write("     </tr>\r\n");
      out.write("     <tr>\r\n");
      out.write("       <td>密&nbsp;&nbsp;码：</td>\r\n");
      out.write("       <td><input name=password type=password value=\"\" size=22 /></td>\r\n");
      out.write("     </tr>\r\n");
      out.write("     <tr>\r\n");
      out.write("       <td ><input type=submit value=确认 /></td>\r\n");
      out.write("       <td ><input type=reset value=重置 /></td>\r\n");
      out.write("     </tr>\r\n");
      out.write("   </table>\r\n");
      out.write("  </form>\r\n");
      out.write("  </div>\r\n");
      out.write("  <div class=bottom>\r\n");
      out.write("  <marquee  behavior=alternate>欢迎您使用超市管理系统！</marquee>\r\n");
      out.write("  </div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
