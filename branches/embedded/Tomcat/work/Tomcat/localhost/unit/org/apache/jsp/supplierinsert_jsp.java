/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2012-09-29 02:09:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.bean.*;
import com.database.*;
import com.util.*;

public final class supplierinsert_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\"/>   \r\n");
      out.write("\r\n");
      out.write("<title>增加用户</title>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write(" function check(form){\r\n");
      out.write("\t if(form.suppliername.value==\"\"){\r\n");
      out.write("\t\t alert(\"姓名不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.supplierdescription.value==\"\"){\r\n");
      out.write("\t\t alert(\"供应商描述不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.linkedman.value==\"\"){\r\n");
      out.write("\t\t alert(\"联系人不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.telphone.value==\"\"){\r\n");
      out.write("\t\t alert(\"电话不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.address.value==\"\"){\r\n");
      out.write("\t\t alert(\"地址不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t return true;\r\n");
      out.write(" }\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
 if( session.getAttribute("username")==null){
	 out.println("<script type=\"text/javascript\">");
     out.println("alert(\"您还没有登陆！请登陆！\");");
	 out.println("open(\"index.jsp\",\"_parent\");");
	 out.println("</script>");
	 return;
}

      out.write('\r');
      out.write('\n');

 request.setCharacterEncoding("gbk");
 if(request.getParameter("supplierinsert")!=null){
	 String suppliername=request.getParameter("suppliername");
	 String supplierdescription=request.getParameter("supplierdescription");
	 String linkedman =request.getParameter("linkedman");
	 String telphone=request.getParameter("telphone");
	 String address=request.getParameter("address");
	 
	 Supplier supplier=new Supplier();
	 supplier.setSuppliername(suppliername);
	 supplier.setSupplierdescription(supplierdescription);
	 supplier.setLinkedman(linkedman);
	 supplier.setTelphone(telphone);
	 supplier.setAddress(address);
	 
	 boolean consequence =new SupplierOperation().supplierinsert(supplier);
	 if(consequence){
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"数据插入成功\");");
		 out.println("open(\"suppliermanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return;
	 }else{
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"数据插入失败\");");
		 out.println("open(\"suppliermanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return; 
	 }
 }

      out.write("\r\n");
      out.write("<body>\r\n");
      out.write(" <form action=\"supplierinsert.jsp\" method=post onSubmit=\"return check(this);\">\r\n");
      out.write("  <input name=supplierinsert type=\"hidden\"/>\r\n");
      out.write("   <div style=\"border:10px solid #4169E1;width:95%;height:360px;\">\r\n");
      out.write("    <table style=margin-top:30px >\r\n");
      out.write("     <tr><td>供应商名称：</td><td><input name=suppliername type=\"text\" value=\"\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>供应商描述：</td><td><input name=supplierdescription type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>联系人：</td><td><input name=linkedman type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>电话：</td><td><input name=telphone size=30 /><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>地址：</td><td><input name=address type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("    </table>\r\n");
      out.write("   </div> \r\n");
      out.write("   <input style=margin-left:100px;margin-top:20px; type=\"submit\" value=数据提交 /><input onclick=\"window.open('suppliermanage.jsp','_self')\" style=margin-left:50px; type=\"button\" value=返回 />\r\n");
      out.write(" </form>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
