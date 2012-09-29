<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.database.*" %>
<%@ page import="com.servlet.*" %>
<%@ page import="com.bean.*" %>
<%@ page import="com.util.*" %>
<%@ page import="javax.swing.JOptionPane" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />   
 
<title>���й���ϵͳ</title>
<script type=text/javascript>
 function check(form){
	 if(form.goodsname.value==""){
		alert("��Ʒ���Ʋ���Ϊ��"); 
		return false;
	 }
	 if(form.goodsnum.value==""){
		alert("��Ʒ��������Ϊ��"); 
		return false;
	 }
	 if(form.money.value==""){
		alert("���׽���Ϊ��"); 
		return false;
	 }
	 if(form.ispay.value==""){
		alert("�Ƿ񸶿��Ϊ��"); 
		return false;
	 }
	 if(form.suppliername.value==""){
		alert("��Ӧ�����Ʋ���Ϊ��"); 
		return false;
	 }
	 if(form.gooddescription.value==""){
		alert("��Ʒ��������Ϊ��"); 
		return false;
	 }
	 if(form.billtime.value==""){
		alert("�˵�ʱ�䲻��Ϊ��"); 
		return false;
	 }
	 if(!window.confirm("ȷ���޸�")){
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
   request.setCharacterEncoding("GBK");
if(request.getParameter("delete")==null){
   long billid=Long.valueOf(request.getParameter("billid"));
   String goodsname=Tools.getstr(request.getParameter("goodsname"));
   int goodsnum=Integer.valueOf(request.getParameter("goodsnum"));
   double money=Double.valueOf(request.getParameter("money"));
   String ispay=Tools.getstr(request.getParameter("ispay"));
   String suppliername=Tools.getstr(request.getParameter("suppliername"));
   String gooddescription=Tools.getstr(request.getParameter("gooddescription"));
   String billtime=Tools.getstr(request.getParameter("billtime"));
   
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
				//ת��Ϊsql�е�date����
				java.sql.Date sqlBillTime=new java.sql.Date(BT.getTime());
				bill.setBillTime(sqlBillTime);
			}catch (ParseException e){
				out.println("<script type=\"text/javascript\">");
				out.println("alert(\"����������� ��\");");
				out.println("open(\"billmanage.jsp\",\"_self\");");
				out.println("</script>");
				return;
			}
			
		}
	  
	  boolean consequence= new BillOperation().billupdate(bill);
	  if(consequence){
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"���³ɹ���\");");
		  out.println("open(\"billmanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }else{
		  out.println("<script type=\"text/javascript\">");
		  out.println("alert(\"����ʧ�ܣ�\");");
		  out.println("open(\"billmanage.jsp\",\"_self\");");
	      out.println("</script>");
		  return;
	  }
   }
 }else{
	 int i= JOptionPane.showConfirmDialog(null,"��ȷ��ɾ������������","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
	 if(i==0){
		 long billid=Long.valueOf(request.getParameter("billid"));
		 Bill bill=new Bill();
		 bill.setBillid(billid);
	    boolean consequence= new BillOperation().billdelete(bill);
	 	  if(consequence){
	 		  out.println("<script type=\"text/javascript\">");
	 		  out.println("alert(\"ɾ���ɹ���\");");
	 		  out.println("open(\"billmanage.jsp\",\"_self\");");
	 	      out.println("</script>");
	 		  return;
	 	  }else{
	 		  out.println("<script type=\"text/javascript\">");
	 		  out.println("alert(\"ɾ��ʧ�ܣ�\");");
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

%>
<body>
 <form action=billinfochange.jsp method=get onSubmit="return check(this);">
  <input type=hidden name=changebill />
  <div style="border:10px solid #4169E1;width:80%;"  >
   <table>
     <tr><td><pre> </pre></td><td><input name=billid type="hidden" value=${billid } /></td></tr>
     <tr><td>��Ʒ����:</td><td><input name=goodsname type="text" value=${goodsname } /></td></tr>
     <tr><td>��Ʒ����:</td><td><input name=goodsnum type="text" value=${goodsnum } /></td></tr>
     <tr><td>���׽��:</td><td><input name=money type="text" value=${money } /></td></tr>
     <tr><td>�Ƿ񸶿�:</td><td><input name=ispay type="text" value=${ispay } /></td></tr>
     <tr><td>��Ӧ������:</td>
     <td><select name=suppliername>
     <c:forEach var="supplier" items="${suppliers}">
      <c:if test="${supplier.suppliername==suppliername}">
       <option selected value=${supplier.supplierid }>${supplier.suppliername}</option>
      </c:if> 
      <c:if test="${supplier.suppliername!=suppliername}">
       <option value=${supplier.supplierid }>${supplier.suppliername}</option>
      </c:if>
     </c:forEach>
     </select> </td>
     </tr>
      <tr><td>��Ʒ����:</td><td><input name=gooddescription type="text" value=${gooddescription} /></td></tr>
     <tr><td>�˵�ʱ��:</td><td><input type=text name=billtime value=${billtime } /></td></tr>
   </table>
  </div>
  <input onclick="window.open('billmanage.jsp','_self')" type="button" value=���� /><input type="submit" value=�޸�����  /><input onclick="window.open('billinfochange.jsp?billid=${billid}&delete=${1}&','_self')" type=button value=ɾ�� />
</form>
</body>
</html>