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
<title>更改密码</title>	
</head> 
 <script type="text/javascript">
  function check(){
     var opwd=document.getElementById("opwd").value;
	 if(opwd==""){
          alert("旧密码不能为空！");
	 return false;   
	 }
     if(opwd!=document.getElementById("password").value){
        alert("旧密码输入错误！");
     return   false;
		 }

	 var pwd =document.getElementById("pwd").value;
	 if(opwd==pwd){
      alert("新密码与旧密码一样，请重新输入新密码！");
    return false;
		 }
	 if(pwd==""){
	 alert("新密码不能为空！");
	return  false;
	 }
	 if(pwd.length<1){
	 alert("密码不能少于1位");
	 return  false;	 
	 }
	  var rpwd=document.getElementById("rpwd").value;
	  if(rpwd!=pwd){
	  alert("两次密码输入不一致!");
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
				 out.println("alert('密码更新成功,请重新登录');");
				 out.println("open('index.jsp','_parent');");
				 out.println("</script>"); 
			 }else{
				 out.println("<script>");
				 out.println("alert('密码更新失败');");
				 out.println("open('usermanage.jsp','_self');");
				 out.println("</script>");  
			 }
		}else{
			 if(consequence){
				 out.println("<script>");
				 out.println("alert('密码更新成功');");
				 out.println("open('usermanage.jsp','_self');");
				 out.println("</script>"); 
			 }else{
				 out.println("<script>");
				 out.println("alert('密码更新失败');");
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
  <tr><td>旧密码</td>
  <td><input id="opwd" type="password" name="oldPassWord" ></td>

  </tr>
   
  <tr><td>新密码</td>
  <td><input id="pwd" type="password" name="passWord" ></td>
  </tr>

  <tr><td>确认密码</td>
  <td><input id="rpwd" type="password" name="NewPassWord" ></td>
  </tr>
    
  <tr>
  
  <td><input onclick="window.open('usermanage.jsp','_self')" type="button" value="返回"></td>
  <td><input type="submit" name="提交" value="提交"></td>
  </tr>
  </table>
  </form>  
  </body>
</html>