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

</head>
<% if( session.getAttribute("username")==null){
	 out.println("<script type=\"text/javascript\">");
     out.println("alert(\"����û�е�½�����½��\");");
	 out.println("open(\"index.jsp\",\"_parent\");");
	 out.println("</script>");
	 return;
}
%>

<body>
<% int i= JOptionPane.showConfirmDialog(null,"ɾ����Ӧ�̽�ɾ����֮��صĶ�����Ϣ����ȷ��ɾ������������","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
if(i==0){
   request.setCharacterEncoding("gbk");
   long supplierid=Long.valueOf(request.getParameter("supplierid"));
   Supplier supplier= new Supplier();
   supplier.setSupplierid(supplierid);
   
   boolean consequence1= new BillOperation().billdelete1(supplierid);
   boolean consequence= new SupplierOperation().supplierdelete(supplier);
	  if(consequence&&consequence1){
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"ɾ���ɹ���\");");
		  out.println("open(\"suppliermanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }else{
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"ɾ��ʧ�ܣ�\");");
		  out.println("open(\"suppliermanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }
   }else{
	   out.println("<script type=\"text/javascript\">");
	   out.println("open(\"suppliermanage.jsp\",\"_self\");");
	   out.println("</script>");
	   //return;///////////////////////////////////????????
   }  
%>
</body>
</html>