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

<%
 request.setCharacterEncoding("GBK");//////////////Ϊʲôһ��Ҫ�������.���������治��??????????
 if(new User_Operation().getPower(session.getAttribute("username").toString()).equals("��ͨ�û�")){
	 out.println("<script type=\"text/javascript\">");
     out.println("alert(\"����Ȩ���д˲���\");");
	 out.println("open(\"welcome.jsp\",\"_self\");");
	 out.println("</script>");
	 return;//return������Ҫ�У������˳��һֱ����ִ��
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

 ////////////��ҳ
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

        ��Ӧ�����ƣ�<input name=suppliername value="${suppliername}" type=text />��Ӧ��������<input name=supplierdescription value="${supplierdescription}" type=text />
   <input style=margin-left:60px type=submit value=ģ����ѯ />
  </form>
  <div class=middle>��Ӧ�̹���>><span> </span><a href="supplierinsert.jsp">��������</a></div>
  <hr size=2 color=#99FFFF;/>
  <div class=table>
   <table width=100% border=10px bordercolor=#4169E1 >
    <tr>
     <td>���</td>
     <td>��Ӧ������</td>
     <td>��Ӧ������</td>
     <td>��ϵ��</td>
     <td>�绰</td>
     <td>��ַ</td>
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
    <p>��һҳ
    <a href="suppliermanage.jsp?page_no=2&suppliername=${suppliername}&supplierdescription=${supplierdescription}">��һҳ</a>
    <a href="suppliermanage.jsp?page_no=${totalpage}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">���һҳ</a>
    </p>  
  </c:if>
  <c:if test="${presentpage>1&&presentpage<totalpage}">
    <p>��${presentpage} / ${totalpage}ҳ
    <a href="suppliermanage.jsp?page_no=${presentpage-1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">��һҳ</a>
    <a href="suppliermanage.jsp?page_no=${presentpage+1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">��һҳ</a>
    </p>  
  </c:if>
    <c:if test="${presentpage==totalpage&&presentpage!=1}">
    <p>���һҳ
    <a href="suppliermanage.jsp?page_no=${presentpage-1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">��һҳ</a>
    <a href="suppliermanage.jsp?page_no=${1}&suppliername=${suppliername}&supplierdescription=${supplierdescription}">��һҳ</a>
    </p>  
  </c:if>
   <c:if test="${presentpage==totalpage&&presentpage==1}">
   <p>ֻ��һҳ</p>  
  </c:if>
</body>
</html>