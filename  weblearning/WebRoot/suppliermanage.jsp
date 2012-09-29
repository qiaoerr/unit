<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.database.*" %>
<%@ page import="com.servlet.*" %>
<%@ page import="com.bean.*" %>
<%@ page import="javax.swing.JOptionPane" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />   
 
<title>超市管理系统</title>
<link href=css/unit2_c.css rel=stylesheet type=css/text/>

</head>

<%
 request.setCharacterEncoding("GBK");//////////////为什么一定要放在这儿.而放在下面不行??????????
 if(new User_Operation().getPower(session.getAttribute("username").toString()).equals("普通用户")){
	 out.println("<script type=\"text/javascript\">");
     out.println("alert(\"您无权进行此操作\");");
	 out.println("open(\"welcome.jsp\",\"_self\");");
	 out.println("</script>");
	 return;//return语句必须要有，否则会顺序一直往下执行
 }
 String suppliername="";
 String supplierdescription="";
 //System.out.println(suppliername+" dddd "+supplierdescription);
//request.setCharacterEncoding("GBK");////////////////////////
 suppliername=request.getParameter("suppliername");
 supplierdescription=request.getParameter("supplierdescription");
 if(suppliername==null){
     suppliername="";
 }
  if(supplierdescription==null){
     supplierdescription="";
 }

 ////////////分页
 {  int presentpage=0;
	  if(request.getParameter("page_no")==null){
	   presentpage=1;
	  }else{
	   presentpage=Integer.valueOf(request.getParameter("page_no"));
	  }
	  int ppage=3;
	  int totalsupplier=0;
	  totalsupplier=new SupplierOperation().suppliernum(suppliername,supplierdescription);
	  int totalpage=0;
	  if(totalsupplier%ppage==0){
		  totalpage=totalsupplier/ppage;
	  }else{
		  totalpage=totalsupplier/ppage+1;
	  }
	  pageContext.setAttribute("totalpage",totalpage);
	  pageContext.setAttribute("presentpage",presentpage);
	  pageContext.setAttribute("suppliername",suppliername);
	  pageContext.setAttribute("supplierdescription",supplierdescription);
	
	  ArrayList<Supplier> suppliers=new SupplierOperation().supplierlookfor(presentpage,ppage,suppliername,supplierdescription);
	  pageContext.setAttribute("suppliers",suppliers);

 }

%>
<body>
  <form action=suppliermanage.jsp method=post>

        供应商名称：<input name=suppliername value="${suppliername}" type=text />供应商描述：<input name=supplierdescription value="${supplierdescription}" type=text />
   <input style=margin-left:60px type=submit value=模糊查询 />
  </form>
  <div class=middle>供应商管理>><span> </span><a href="supplierinsert.jsp">增加数据</a></div>
  <hr size=2 color=#99FFFF;/>
  <div class=table>
   <table width=100% border=10px bordercolor=#4169E1 >
    <tr>
     <td>编号</td>
     <td>供应商名称</td>
     <td>供应商描述</td>
     <td>联系人</td>
     <td>电话</td>
     <td>地址</td>
    </tr>
    <c:forEach var="supplier" items="${pageScope.suppliers}">
     <tr>
      <td>${supplier.supplierid}</td>
      <td><a href="supplierinfochange.jsp?supplierid=${supplier.supplierid}&suppliername=${supplier.suppliername}&supplierdescription=${supplier.supplierdescription}&
      &linkedman=${supplier.linkedman}&telphone=${supplier.telphone}&address=${supplier.address}">${supplier.suppliername}</a></td>
      <td>${supplier.supplierdescription}</td>
      <td>${supplier.linkedman}</td>
      <td>${supplier.telphone}</td>
      <td>${supplier.address}</td>
     </tr>
    </c:forEach>
   </table>
  </div>
  <c:if test="${presentpage==1&&presentpage<totalpage}">
    <p>第一页
    <a href="suppliermanage.jsp?page_no=2&suppliername=${suppliername}&supplierdescription=${supplierdescription}">下一页</a>
    <a href="suppliermanage.jsp?page_no=${totalpage}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">最后一页</a>
    </p>  
  </c:if>
  <c:if test="${presentpage>1&&presentpage<totalpage}">
    <p>第${presentpage} / ${totalpage}页
    <a href="suppliermanage.jsp?page_no=${presentpage-1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">上一页</a>
    <a href="suppliermanage.jsp?page_no=${presentpage+1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">下一页</a>
    </p>  
  </c:if>
    <c:if test="${presentpage==totalpage&&presentpage!=1}">
    <p>最后一页
    <a href="suppliermanage.jsp?page_no=${presentpage-1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">上一页</a>
    <a href="suppliermanage.jsp?page_no=${1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">第一页</a>
    </p>  
  </c:if>
   <c:if test="${presentpage==totalpage&&presentpage==1}">
   <p>只有一页</p>  
  </c:if>
</body>
</html>