/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2012-09-19 12:43:38 UTC
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
import java.text.SimpleDateFormat;
import java.text.ParseException;

public final class billinsert_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
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
      out.write("\t if(form.goodsname.value==\"\"){\r\n");
      out.write("\t\t alert(\"商品名称不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.goodsnum.value==\"\"){\r\n");
      out.write("\t\t alert(\"商品数量不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.money.value==\"\"){\r\n");
      out.write("\t\t alert(\"交易金额不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.gooddescription.value==\"\"){\r\n");
      out.write("\t\t alert(\"商品描述不能为空\");\r\n");
      out.write("\t\t return false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.billtime.value==\"\"){\r\n");
      out.write("\t\t alert(\"账单时间不能为空\");\r\n");
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
 ArrayList<Supplier> suppliers=new SupplierOperation().getSupplier();
 pageContext.setAttribute("suppliers",suppliers);
 
 if(request.getParameter("bill")!=null){
	 
	 String goodsname=request.getParameter("goodsname");
	 int goodsnum=Integer.valueOf(request.getParameter("goodsnum"));
	 double money =Double.valueOf(request.getParameter("money"));
	 String ispay=request.getParameter("ispay");
	 String suppliername=request.getParameter("suppliername");
	 String gooddescription=request.getParameter("gooddescription");
	 String billtime=request.getParameter("billtime");
	 
	 Bill bill=new Bill();
	  bill.setGoodsname(goodsname);
	  bill.setGoodsnum(goodsnum);
	  bill.setMoney(money);
	  bill.setIspay(ispay);
	  bill.setSuppliername(suppliername);
	  bill.setGooddescription(gooddescription);
	  
	 {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			try{
				java.util.Date BT=sdf.parse(billtime); 
				//转换为sql中的date类型
				java.sql.Date sqlBillTime=new java.sql.Date(BT.getTime());
				bill.setBillTime(sqlBillTime);
			}catch (ParseException e){
				out.println("<script type=\"text/javascript\">");
				out.println("alert(\"日期输入错误 ！\");");
				out.println("open(\"billinsert.jsp\",\"_self\");");
				out.println("</script>");
				return;
			}
			
		}
	 
	 boolean consequence =new BillOperation().billinsert(bill);
	 if(consequence){
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"数据插入成功\");");
		 out.println("open(\"billmanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return;
	 }else{
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"数据插入失败\");");
		 out.println("open(\"billmanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return; 
	 }
 }

      out.write("\r\n");
      out.write("<body>\r\n");
      out.write(" <form action=\"billinsert.jsp\" method=post onSubmit=\"return check(this);\">\r\n");
      out.write("  <input name=bill type=\"hidden\"/>\r\n");
      out.write("   <div style=\"border:10px solid #4169E1;width:95%;height:360px;\">\r\n");
      out.write("    <table style=margin-top:30px >\r\n");
      out.write("     <tr><td>商品名称：</td><td><input name=goodsname type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>商品数量：</td><td><input name=goodsnum type=\"text\" value=\"\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>交易金额：</td><td><input name=money type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>是否付款：</td><td><select name=ispay><option value=是>是</option><option value=否>否</option></select></td></tr>\r\n");
      out.write("     <tr><td>供应商名称：</td><td><select name=suppliername>");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("</select></td></tr>\r\n");
      out.write("     <tr><td>商品描述：</td><td><input name=gooddescription type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("     <tr><td>账单时间：</td><td><input name=billtime type=\"text\" size=30/><font color=red>*</font></td></tr>\r\n");
      out.write("    </table>\r\n");
      out.write("   </div> \r\n");
      out.write("   <input style=margin-left:100px;margin-top:20px; type=\"submit\" value=数据提交 /><a href=billmanage.jsp ><input style=margin-left:50px; type=\"button\" value=返回 /></a>\r\n");
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

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    // /billinsert.jsp(115,54) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fforEach_005f0.setVar("supplier");
    // /billinsert.jsp(115,54) name = items type = java.lang.Object reqTime = true required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${suppliers}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("<option value=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.supplierid}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.suppliername}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</option>");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }
}
