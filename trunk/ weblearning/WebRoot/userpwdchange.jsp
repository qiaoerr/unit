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
<title>��������</title>	
</head> 
 <script type="text/javascript">
  function check(){
     var opwd=document.getElementById("opwd").value;
	 if(opwd==""){
          alert("�����벻��Ϊ�գ�");
	 return false;   
	 }
     if(opwd!=document.getElementById("password").value){
        alert("�������������");
     return   false;
		 }

	 var pwd =document.getElementById("pwd").value;
	 if(opwd==pwd){
      alert("�������������һ�������������������룡");
    return false;
		 }
	 if(pwd==""){
	 alert("�����벻��Ϊ�գ�");
	return  false;
	 }
	 if(pwd.length<1){
	 alert("���벻������1λ");
	 return  false;	 
	 }
	  var rpwd=document.getElementById("rpwd").value;
	  if(rpwd!=pwd){
	  alert("�����������벻һ��!");
	 return  false;
	  }
	 return true;
	  
  }
  </script>
  
  <%
  request.setCharacterEncoding("gbk");
   long userid =Long.valueOf(request.getParameter("userid"));
   pageContext.setAttribute("userid",userid);
   String name =request.getParameter("name");
   pageContext.setAttribute("name",name);
 
   
   if(request.getParameter("action")!=null){
	      String password=request.getParameter("passWord");
		  User user=new User();
		  user.setUserid(userid);
		  user.setPassword(password);
		  boolean consequence=new User_Operation().changepwd(user);
		  if(session.getAttribute("username").equals(name)){
			 if(consequence){
				 out.println("<script>");
				 out.println("alert('������³ɹ�,�����µ�¼');");
				 out.println("open('index.jsp','_parent');");
				 out.println("</script>"); 
			 }else{
				 out.println("<script>");
				 out.println("alert('�������ʧ��');");
				 out.println("open('usermanage.jsp','_self');");
				 out.println("</script>");  
			 }
		}else{
			 if(consequence){
				 out.println("<script>");
				 out.println("alert('������³ɹ�');");
				 out.println("open('usermanage.jsp','_self');");
				 out.println("</script>"); 
			 }else{
				 out.println("<script>");
				 out.println("alert('�������ʧ��');");
				 out.println("open('usermanage.jsp','_self');");
				 out.println("</script>");  
			 }
		 }
	  }
    String password=request.getParameter("password");
    pageContext.setAttribute("password",password);
  %>
  <body> 
  <form action="userpwdchange.jsp" onSubmit="return check();">
  <table>
  <tr><td><input type="hidden" name="action" value=""></td>
  <td> <input type="hidden" name="userid" value="${pageScope.userid}">
  <input id="password" type="hidden"  value="${pageScope.password}">
  <input type="hidden" name="name" value="${pageScope.name}"></td>
  </tr>
  <tr><td>������</td>
  <td><input id="opwd" type="password" name="oldPassWord" ></td>

  </tr>
   
  <tr><td>������</td>
  <td><input id="pwd" type="password" name="passWord" ></td>
  </tr>

  <tr><td>ȷ������</td>
  <td><input id="rpwd" type="password" name="NewPassWord" ></td>
  </tr>
    
  <tr>
  
  <td><input onclick="window.open('usermanage.jsp','_self')" type="button" value="����"></td>
  <td><input type="submit" name="�ύ" value="�ύ"></td>
  </tr>
  </table>
  </form>  
  </body>
</html>