/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2012-09-29 08:19:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.database.*;
import com.servlet.*;
import com.bean.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public final class billinfochange_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\" />\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\" />\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\" />   \r\n");
      out.write(" \r\n");
      out.write("<title>超市管理系统</title>\r\n");
      out.write("<script type=text/javascript>\r\n");
      out.write(" function check(form){\r\n");
      out.write("\t if(form.goodsname.value==\"\"){\r\n");
      out.write("\t\talert(\"商品名称不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.goodsnum.value==\"\"){\r\n");
      out.write("\t\talert(\"商品数量不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.money.value==\"\"){\r\n");
      out.write("\t\talert(\"交易金额不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.ispay.value==\"\"){\r\n");
      out.write("\t\talert(\"是否付款不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.suppliername.value==\"\"){\r\n");
      out.write("\t\talert(\"供应商名称不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.gooddescription.value==\"\"){\r\n");
      out.write("\t\talert(\"商品描述不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(form.billtime.value==\"\"){\r\n");
      out.write("\t\talert(\"账单时间不能为空\"); \r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t if(!window.confirm(\"确定修改\")){\r\n");
      out.write("\t\treturn false;\r\n");
      out.write("\t }\r\n");
      out.write("\t return true;\r\n");
      out.write(" }\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
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

   request.setCharacterEncoding("GBK");
if(request.getParameter("delete")==null){
   long billid=Long.valueOf(request.getParameter("billid"));
   String goodsname=request.getParameter("goodsname");
   int goodsnum=Integer.valueOf(request.getParameter("goodsnum"));
   double money=Double.valueOf(request.getParameter("money"));
   String ispay=request.getParameter("ispay");
   String suppliername=request.getParameter("suppliername");
   String gooddescription=request.getParameter("gooddescription");
   String billtime=request.getParameter("billtime");
   
   pageContext.setAttribute("billid",billid);
   pageContext.setAttribute("goodsname",goodsname);
   pageContext.setAttribute("goodsnum",goodsnum);
   pageContext.setAttribute("money",money);
   pageContext.setAttribute("ispay",ispay);
   pageContext.setAttribute("suppliername",suppliername);
   pageContext.setAttribute("gooddescription",gooddescription);
   pageContext.setAttribute("billtime",billtime);
   
   ArrayList<Supplier> suppliers=new SupplierOperation().getSupplier();
   pageContext.setAttribute("suppliers",suppliers);
   
   if(request.getParameter("changebill")!=null){
	  Bill bill=new Bill();
	  bill.setBillid(billid);
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
				out.println("open(\"billmanage.jsp\",\"_self\");");
				out.println("</script>");
				return;
			}
			
		}
	  
	  boolean consequence= new BillOperation().billupdate(bill);
	  if(consequence){
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"更新成功！\");");
		  out.println("open(\"billmanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }else{
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"更新失败！\");");
		  out.println("open(\"billmanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }
   }
 }else{
	 int i= JOptionPane.showConfirmDialog(null,"您确认删除此条数据吗","确认",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
	 if(i==0){
		 long billid=Long.valueOf(request.getParameter("billid"));
		 Bill bill=new Bill();
		 bill.setBillid(billid);
	    boolean consequence= new BillOperation().billdelete(bill);
	 	  if(consequence){
	 		  out.println("<script type=\"text/javascript\">");
	 		  out.println("alert(\"删除成功！\");");
	 		  out.println("open(\"billmanage.jsp\",\"_self\");");
	 	      out.println("</script>");
	 		  return;
	 	  }else{
	 		  out.println("<script type=\"text/javascript\">");
	 		  out.println("alert(\"删除失败！\");");
	 		  out.println("open(\"billmanage.jsp\",\"_self\");");
	 	      out.println("</script>");
	 		  return;
	 	  }
	    }else{
	 	   out.println("<script type=\"text/javascript\">");
	 	   out.println("open(\"billmanage.jsp\",\"_self\");");
	 	   out.println("</script>");
	 	   return;
	    }  
 }


      out.write("\r\n");
      out.write("<body>\r\n");
      out.write(" <form action=billinfochange.jsp method=post onSubmit=\"return check(this);\">\r\n");
      out.write("  <input type=hidden name=changebill />\r\n");
      out.write("  <div style=\"border:10px solid #4169E1;width:80%;\"  >\r\n");
      out.write("   <table>\r\n");
      out.write("     <tr><td><pre> </pre></td><td><input name=billid type=\"hidden\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${billid }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("     <tr><td>商品名称:</td><td><input name=goodsname type=\"text\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodsname }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("     <tr><td>商品数量:</td><td><input name=goodsnum type=\"text\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${goodsnum }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("     <tr><td>交易金额:</td><td><input name=money type=\"text\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${money }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("     <tr><td>是否付款:</td><td><input name=ispay type=\"text\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ispay }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("     <tr><td>供应商名称:</td>\r\n");
      out.write("     <td><select name=suppliername>\r\n");
      out.write("     ");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("     </select> </td>\r\n");
      out.write("     </tr>\r\n");
      out.write("      <tr><td>商品描述:</td><td><input name=gooddescription type=\"text\" value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${gooddescription}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("     <tr><td>账单时间:</td><td><input type=text name=billtime value=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${billtime }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write(" /></td></tr>\r\n");
      out.write("   </table>\r\n");
      out.write("  </div>\r\n");
      out.write("  <input onclick=\"window.open('billmanage.jsp','_self')\" type=\"button\" value=返回 /><input type=\"submit\" value=修改数据  /><input onclick=\"window.open('billinfochange.jsp?billid=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${billid}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("&delete=");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${1}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("&','_self')\" type=button value=删除 />\r\n");
      out.write("</form>\r\n");
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
    // /billinfochange.jsp(172,5) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVar("supplier");
    // /billinfochange.jsp(172,5) name = items type = java.lang.Object reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${suppliers}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("      ");
          if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write(" \r\n");
          out.write("      ");
          if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
            return true;
          out.write("\r\n");
          out.write("     ");
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

  private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    // /billinfochange.jsp(173,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.suppliername==suppliername}", java.lang.Boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("       <option selected value=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.supplierid }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
        out.write('>');
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.suppliername}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
        out.write("</option>\r\n");
        out.write("      ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fforEach_005f0, javax.servlet.jsp.PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fforEach_005f0);
    // /billinfochange.jsp(176,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.suppliername!=suppliername}", java.lang.Boolean.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("       <option value=");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.supplierid }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
        out.write('>');
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${supplier.suppliername}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
        out.write("</option>\r\n");
        out.write("      ");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }
}
