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
<script type=text/javascript>
 function check(form){
	 if(form.supplierid.value==""){
		alert("��Ų���Ϊ��"); 
		return false;
	 }
	 if(form.suppliername.value==""){
		alert("��Ӧ�����Ʋ���Ϊ��"); 
		return false;
	 }
	 if(form.supplierdescription.value==""){
		alert("��Ӧ����������Ϊ��"); 
		return false;
	 }
	 if(form.linkedman.value==""){
		alert("��ϵ�˲���Ϊ��"); 
		return false;
	 }
	 if(form.telphone.value==""){
		alert("�绰����Ϊ��"); 
		return false;
	 }
	 if(form.address.value==""){
		alert("��ַ����Ϊ��"); 
		return false;
	 }
	 if(!window.confirm("ȷ�����ģ�")){
		 return false;
	 }
	 return true;
 }
</script>

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
   request.setCharacterEncoding("gbk");
   long supplierid=Long.valueOf(request.getParameter("supplierid"));
   String suppliername=request.getParameter("suppliername");
   String supplierdescription=request.getParameter("supplierdescription");
   String linkedman=request.getParameter("linkedman");
   String telphone=request.getParameter("telphone");
   String address=request.getParameter("address");
   
   pageContext.setAttribute("supplierid",supplierid);
   pageContext.setAttribute("suppliername",suppliername);
   pageContext.setAttribute("supplierdescription",supplierdescription);
   pageContext.setAttribute("linkedman",linkedman);
   pageContext.setAttribute("telphone",telphone);
   pageContext.setAttribute("address",address);
   
  
   if(request.getParameter("change")!=null){
	   Supplier supplier= new Supplier();
	   supplier.setSupplierid(supplierid);
	   supplier.setSuppliername(suppliername);
	   supplier.setSupplierdescription(supplierdescription);
	   supplier.setLinkedman(linkedman);
	   supplier.setTelphone(telphone);
	   supplier.setAddress(address);
	   
	  boolean consequence= new SupplierOperation().supplierinfochange(supplier);
	  if(consequence){
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"���³ɹ���\");");
		  out.println("open(\"suppliermanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }else{
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"����ʧ�ܣ�\");");
		  out.println("open(\"suppliermanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }
   }
   

%>
<body>
 <form action=supplierinfochange.jsp method=post onSubmit="return check(this);">
  <input type=hidden name=change />
  <div style="border:10px solid #4169E1;width:80%;"  >
   <table>
     <tr><td><pre> </pre></td><td><input name=supplierid type="hidden" value=${supplierid} /></td></tr>
     <tr><td>��Ӧ������:</td><td><input name=suppliername type="text" value=${suppliername} /></td></tr>
    <tr><td>��Ӧ������:</td><td><textarea name=supplierdescription>${supplierdescription}</textarea></td></tr>
     <tr><td>��ϵ��:</td><td><input name=linkedman type="text" value=${linkedman} /></td></tr>
     <tr><td>�绰:</td><td><input name=telphone type="text" value=${telphone} /></td></tr>
     <tr><td>��ַ:</td><td><textarea name=address >${address}</textarea></td></tr>
   </table>
  </div>
  <a href=suppliermanage.jsp><input type="button" value=���� /></a><input type="submit" value=�޸�����  /><a href=supplierdelete.jsp?supplierid=${supplierid}><input type=button value=ɾ�� /></a>
</form>
</body>
</html>