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
	
	 if(form.name.value==""){
		 alert("�û���������Ϊ��");
		 return false;
	 }
	 if(form.password.value==""){
		 alert("�û����벻��Ϊ��");
		 return false;
	 }
	 if(form.rpassword.value!=form.password.value){
		 alert("���ε����벻һ��");
		 return false;
	 }
	 if(form.age.value==""){
		 alert("�û����䲻��Ϊ��");
		 return false;
	 }
	 if(form.telphone.value==""){
		 alert("�绰����Ϊ��");
		 return false;
	 }
	 if(form.address.value==""){
		 alert("�û���ַ����Ϊ��");
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
 if(request.getParameter("userinsert")!=null){
	
	 String name=request.getParameter("name");
	 
	 //�ж�userid��username�Ƿ��ظ�
	 ArrayList<User> users =null;
	 users=new User_Operation().getUser();
	 
	 
	 String password=request.getParameter("password");
	 String sex=request.getParameter("sex");
	 int age=Integer.valueOf(request.getParameter("age"));
	 String telphone=request.getParameter("telphone");
	 String address=request.getParameter("address");
	 String power=request.getParameter("power");
	 
	 User user=new User();


	 user.setName(name);
	 user.setPassword(password);
	 user.setSex(sex);
	 user.setAge(age);
	 user.setTelphone(telphone);
	 user.setAddress(address);
	 user.setPower(power);
	 boolean consequence =new User_Operation().userinsert(user);
	 if(consequence){
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"���ݲ���ɹ�\");");
		 out.println("open(\"usermanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return;
	 }else{
		 out.println("<script type=\"text/javascript\">");
		 out.println("alert(\"���ݲ���ʧ��\");");
		 out.println("open(\"usermanage.jsp\",\"_self\");");
		 out.println("</script>");
		 return; 
	 }
 }
%>
<body>
 <form action="userinsert.jsp" method=post onSubmit="return check(this);">
  <input name=userinsert type="hidden"/>
   <div style="border:10px solid #4169E1;width:95%;height:360px;">
    <table style=margin-top:30px >
     <tr><td>�û����ƣ�</td><td><input name=name type="text" value="" size=30/><font color=red>*</font></td></tr>
     <tr><td>�û����룺</td><td><input name=password type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>ȷ�����룺</td><td><input name=rpassword type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>�û��Ա�</td><td><select name=sex><option value=��>��</option><option value=Ů>Ů</option></select></td></tr>
     <tr><td>�û����䣺</td><td><input name=age type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>�û��绰��</td><td><input name=telphone type="text" size=30/><font color=red>*</font></td></tr>
     <tr><td>�û�סַ��</td><td><textarea name=address rows=3 cols=28></textarea><font color=red>*</font></td></tr>
     <tr><td>�û�Ȩ�ޣ�</td><td><input name=power type="radio" value=��ͨ�û� checked/>��ͨ�û�<input name=power type="radio" value=���� />����</td></tr>
    </table>
   </div> 
   <input style=margin-left:100px;margin-top:20px; type="submit" value=�����ύ /><input onclick="window.open('usermanage.jsp','_self')" style=margin-left:50px; type="button" value=���� />
 </form>

</body>
</html>