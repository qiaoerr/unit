<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript">
  	function checkUser(ouser){
  	var uname=ouser.value;
  	if(!uname){
  		alert("用户名不能为空");
  		ouser.focus;
  		return;
  	}
  	//发送请求到服务器，判断用户名是否存在
  	//Ajax代码实现
  	 // 发送请求到服务器，判断用户名是否存在
	// 请求字符串
	//var url = "servlet/doReg?uname="+uname; //GET 方式
	var url = "servlet/doReg";  //POST 方式
	var userinfo="uname="+uname;
	// 1. 创建XMLHttpRequest组件
	xmlHttpRequest = createXmlHttpRequest();
	// 2. 设置回调函数
	xmlHttpRequest.onreadystatechange = haoLeJiaoWo; 
	// 3. 初始化XMLHttpRequest组件
	//xmlHttpRequest.open("GET",url,true); //GET方式
	xmlHttpRequest.open("POST",url,true);//POST方式
	xmlHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//POST方式需要设置
	// 4. 发送请求
	xmlHttpRequest.send(userinfo);
 	} 
	function haoLeJiaoWo(){
       if(  xmlHttpRequest.readyState == 4     
	         && xmlHttpRequest.status == 200){
               var b = xmlHttpRequest.responseText;
               alert(b);
   			   b=b.replace(/(^\s*)|(\s*$)/g,"");
               if(b=="true"){
            	   document.getElementById("test").innerHTML="用户名已经存在";
               }else{
            	   document.getElementById("test").innerHTML="用户名可以使用";
               }
      	 }
	}
  function createXmlHttpRequest(){
  	if(window.XMLHttpRequest){//新版本IE浏览器（IE7及以上版本）或其他浏览器
		return new XMLHttpRequest();
	}else {                   //老版本IE浏览器（包括IE5和IE6）
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
  }
  function func(){
	  document.getElementById("test").innerHTML="<b>hello</b>";
  }
  </script>
  </head>
  
  <body>
    <form action="#" method="post" >
    	<table align="center">
    		<tr>
    			<td><p>用户账户：<input type="text" name="username" style="width: 150px" onblur="checkUser(this);" onfocus="func();"/><span id="test"></span></p></td>
    		</tr>
    		<tr>
    			<td><p>用户密码：<input type="password" name="passwd" style="width: 150px"/></p></td>
    		</tr>
    		<tr>
    			<td><p>重复密码：<input type="password" name="passwd" style="width: 150px"/></p></td>
    		</tr>
    		<tr>
    			<td><input type="submit" value="提交" /></td>
    		</tr>
    	</table> 	
    </form>
  </body>
</html>
