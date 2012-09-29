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
 
<title>���й���ϵͳ</title>
<link href=css/unit2_c.css rel=stylesheet type=css/text/>

</head>
<% if( session.getAttribute("username")==null){
	 out.println("<script type=\"text/javascript\">");
     out.println("alert(\"����û�е�½�����½��\");");
	 out.println("open(\"index.jsp\",\"_parent\");");
	 out.println("</script>");
	 return;
}
%>
<%
//request.setCharacterEncoding("GBK");//���ڹ������н���������
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



////////////��ҳ
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
        ��Ʒ���ƣ�<input name=goodname value="${goodname}" type=text />
        �Ƿ񸶿
        <c:if test="${ispay==null||ispay==''}">
         <select name=ispay><option value=��>��</option><option value=��>��</option><option value="" selected>��ѡ��</option></select>
        </c:if>
        <c:if test="${ispay=='��'}">
        <select name=ispay><option  selected value=��>��</option><option value=��>��</option><option value="" >��ѡ��</option></select>
        </c:if>
        <c:if test="${ispay=='��'}">
         <select name=ispay><option value=��>��</option><option selected value=��>��</option><option value="" >��ѡ��</option></select>
        </c:if>  
   <input style=margin-left:60px type=submit value=ģ����ѯ />
   
  </form>
  <div class=middle>�˵�����>><span> </span><a href="billinsert.jsp">��������</a></div>
  <hr size=2 color=#99FFFF;/>
  <div class=table>
   <table width=100% border=10px bordercolor=#4169E1 >
    <tr>
     <td>�˵����</td>
     <td>��Ʒ����</td>
     <td>��Ʒ����</td>
     <td>���׽��</td>
     <td>�Ƿ񸶿�</td>
     <td>��Ӧ������</td>
     <td>��Ʒ����</td>
     <td>�˵�ʱ��</td>
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
    <p>��һҳ
    <a href="billmanage.jsp?page_no=2&goodname=${goodname}&ispay=${ispay}">��һҳ</a>
    <a href="billmanage.jsp?page_no=${totalpage}&goodname=${goodname}&ispay=${ispay}">���һҳ</a>
    </p>  
  </c:if>
  <c:if test="${presentpage>1&&presentpage<totalpage}">
    <p>��${presentpage} / ${totalpage}ҳ
    <a href="billmanage.jsp?page_no=${presentpage-1}&goodname=${goodname}&ispay=${ispay}">��һҳ</a>
    <a href="billmanage.jsp?page_no=${presentpage+1}&goodname=${goodname}&ispay=${ispay}">��һҳ</a>
    </p>  
  </c:if>
    <c:if test="${presentpage==totalpage&&presentpage!=1}">
    <p>���һҳ
    <a href="billmanage.jsp?page_no=${presentpage-1}&goodname=${goodname}&ispay=${ispay}">��һҳ</a>
    <a href="billmanage.jsp?page_no=${1}&goodname=${goodname}&ispay=${ispay}">��һҳ</a>
    </p>  
  </c:if>
   <c:if test="${presentpage==totalpage&&presentpage==1}">
   <p>ֻ��һҳ</p>  
  </c:if>
</body>
</html>