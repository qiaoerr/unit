<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.bean.*" %>
<%@ page import="com.database.*" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>   

<title>�����û�</title>
<script type="text/javascript">
 function check(form){
	 if(form.suppliername.value==""){
		 alert("��������Ϊ��");
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
		 out.println("alert(\"���ݲ���ɹ�\");");
		 out.println("open(\"suppliermanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return;
	 }else{
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"���ݲ���ʧ��\");");
		 out.println("open(\"suppliermanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return; 
	 }
 }
%>
<body>
 <form action="supplierinsert.jsp" method=post onSubmit="return check(this);">
  <input name=supplierinsert type="hidden"/>
   <div style="border:10px solid #4169E1;width:95%;height:360px;">
    <table style=margin-top:30px >
     <tr><td>��Ӧ�����ƣ�</td><td><input name=suppliername type="text" value="" size=30/><font color=red>*</font></td></tr>
     <tr><td>��Ӧ��������</td><td><input name=supplierdescription type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>��ϵ�ˣ�</td><td><input name=linkedman type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>�绰��</td><td><input name=telphone size=30 /><font color=red>*</font></td></tr>
     <tr><td>��ַ��</td><td><input name=address type="text" size=30/><font color=red>*</font></td></tr>
    </table>
   </div> 
   <input style=margin-left:100px;margin-top:20px; type="submit" value=�����ύ /><a href=suppliermanage.jsp><input style=margin-left:50px; type="button" value=���� /></a>
 </form>
</body>
</html>