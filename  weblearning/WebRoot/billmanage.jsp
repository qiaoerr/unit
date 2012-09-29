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
<% if( session.getAttribute("username")==null){
	 out.println("<script type=\"text/javascript\">");
     out.println("alert(\"您还没有登陆！请登陆！\");");
	 out.println("open(\"index.jsp\",\"_parent\");");
	 out.println("</script>");
	 return;
}
%>
<%
//request.setCharacterEncoding("GBK");//已在过滤器中进行了设置
String goodname="";
String ispay="";
goodname=request.getParameter("goodname");
ispay=request.getParameter("ispay");
if(goodname==null){
   goodname="";
}
if(ispay==null){
   ispay="";
}



////////////分页
{  int presentpage=0;
	  if(request.getParameter("page_no")==null){
	   presentpage=1;
	  }else{
	   presentpage=Integer.valueOf(request.getParameter("page_no"));
	  }
	  int ppage=3;
	  int totalbill=0;
	  totalbill=new BillOperation().billnum(goodname,ispay);
	  int totalpage=0;
	  if(totalbill%ppage==0){
		  totalpage=totalbill/ppage;
	  }else{
		  totalpage=totalbill/ppage+1;
	  }
	  pageContext.setAttribute("totalpage",totalpage);
	  pageContext.setAttribute("presentpage",presentpage);
	  pageContext.setAttribute("goodname",goodname);
	  pageContext.setAttribute("ispay",ispay);
	  ArrayList<Bill> bills=new BillOperation().billlookfor(presentpage,ppage,goodname,ispay);
	  pageContext.setAttribute("bills",bills);

}
 
%>
<body>
  <form action=billmanage.jsp method=post>
   <input name=lookfor type=hidden />
        商品名称：<input name=goodname value="${goodname}" type=text />
        是否付款：
        <c:if test="${ispay==null||ispay==''}">
         <select name=ispay><option value=是>是</option><option value=否>否</option><option value="" selected>请选择</option></select>
        </c:if>
        <c:if test="${ispay=='是'}">
        <select name=ispay><option  selected value=是>是</option><option value=否>否</option><option value="" >请选择</option></select>
        </c:if>
        <c:if test="${ispay=='否'}">
         <select name=ispay><option value=是>是</option><option selected value=否>否</option><option value="" >请选择</option></select>
        </c:if>  
   <input style=margin-left:60px type=submit value=模糊查询 />
   
  </form>
  <div class=middle>账单管理>><span> </span><a href="billinsert.jsp">增加数据</a></div>
  <hr size=2 color=#99FFFF;/>
  <div class=table>
   <table width=100% border=10px bordercolor=#4169E1 >
    <tr>
     <td>账单编号</td>
     <td>商品名称</td>
     <td>商品数量</td>
     <td>交易金额</td>
     <td>是否付款</td>
     <td>供应商名称</td>
     <td>商品描述</td>
     <td>账单时间</td>
    </tr>
    <c:forEach var="bill" items="${pageScope.bills}">
     <tr>
      <td>${bill.billid}</td>
      <td><a href="billinfochange.jsp?billid=${bill.billid}&goodsname=${bill.goodsname}&goodsnum=${bill.goodsnum}&
      &money=${bill.money}&ispay=${bill.ispay}&suppliername=${bill.suppliername}&gooddescription=${bill.gooddescription}&billtime=${bill.billTime}">${bill.goodsname}</a></td>
      <td>${bill.goodsnum}</td>
      <td>${bill.money}</td>
      <td>${bill.ispay}</td>
      <td>${bill.suppliername}</td>
      <td>${bill.gooddescription}</td>
      <td>${bill. billTime}</td>
     </tr>
    </c:forEach>
   </table>
  </div>
    <c:if test="${presentpage==1&&presentpage<totalpage}">
    <p>第一页
    <a href="billmanage.jsp?page_no=2&goodname=${goodname}&ispay=${ispay}">下一页</a>
    <a href="billmanage.jsp?page_no=${totalpage}&goodname=${goodname}&ispay=${ispay}">最后一页</a>
    </p>  
  </c:if>
  <c:if test="${presentpage>1&&presentpage<totalpage}">
    <p>第${presentpage} / ${totalpage}页
    <a href="billmanage.jsp?page_no=${presentpage-1}&goodname=${goodname}&ispay=${ispay}">上一页</a>
    <a href="billmanage.jsp?page_no=${presentpage+1}&goodname=${goodname}&ispay=${ispay}">下一页</a>
    </p>  
  </c:if>
    <c:if test="${presentpage==totalpage&&presentpage!=1}">
    <p>最后一页
    <a href="billmanage.jsp?page_no=${presentpage-1}&goodname=${goodname}&ispay=${ispay}">上一页</a>
    <a href="billmanage.jsp?page_no=${1}&goodname=${goodname}&ispay=${ispay}">第一页</a>
    </p>  
  </c:if>
   <c:if test="${presentpage==totalpage&&presentpage==1}">
   <p>只有一页</p>  
  </c:if>
</body>
</html>