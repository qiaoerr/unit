<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.bean.*" %>
<%@ page import="com.database.*" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>   

<title>增加用户</title>
<script type="text/javascript">
 function check(form){
	 if(form.goodsname.value==""){
		 alert("商品名称不能为空");
		 return false;
	 }
	 if(form.goodsnum.value==""){
		 alert("商品数量不能为空");
		 return false;
	 }
	 if(form.money.value==""){
		 alert("交易金额不能为空");
		 return false;
	 }
	 if(form.gooddescription.value==""){
		 alert("商品描述不能为空");
		 return false;
	 }
	 if(form.billtime.value==""){
		 alert("账单时间不能为空");
		 return false;
	 }
	 return true;
 }
</script>
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
%>
<body>
 <form action="billinsert.jsp" method=post onSubmit="return check(this);">
  <input name=bill type="hidden"/>
   <div style="border:10px solid #4169E1;width:95%;height:360px;">
    <table style=margin-top:30px >
     <tr><td>商品名称：</td><td><input name=goodsname type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>商品数量：</td><td><input name=goodsnum type="text" value="" size=30/><font color=red>*</font></td></tr>
     <tr><td>交易金额：</td><td><input name=money type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>是否付款：</td><td><select name=ispay><option value=是>是</option><option value=否>否</option></select></td></tr>
     <tr><td>供应商名称：</td><td><select name=suppliername><c:forEach var="supplier" items="${suppliers}"><option value=${supplier.supplierid}>${supplier.suppliername}</option></c:forEach></select></td></tr>
     <tr><td>商品描述：</td><td><input name=gooddescription type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>账单时间：</td><td><input name=billtime type="text" size=30/><font color=red>*</font></td></tr>
    </table>
   </div> 
   <input style=margin-left:100px;margin-top:20px; type="submit" value=数据提交 /><input onclick="window.open('billmanage.jsp','_self')" style=margin-left:50px; type="button" value=返回 />
 </form>
</body>
</html>