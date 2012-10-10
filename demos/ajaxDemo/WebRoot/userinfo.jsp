<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function getUser(ouser){
  	var uname=ouser.value;
  	if(!uname){
  		alert("请选择查询用户");
  		ouser.focus;
  	}
  	//发送请求到服务器，判断用户名是否存在
  	//Ajax代码实现
  	 // 发送请求到服务器，判断用户名是否存在
	// 请求字符串
	var url = "servlet/doGetU?uname="+uname; 
	// 1. 创建XMLHttpRequest组件
	xmlHttpRequest = createXmlHttpRequest();
	// 2. 设置回调函数
	xmlHttpRequest.onreadystatechange = haoLeJiaoWo; 
	// 3. 初始化XMLHttpRequest组件
	xmlHttpRequest.open("GET",url,true); 
	// 4. 发送请求
	xmlHttpRequest.send(null);
 	} 
	function haoLeJiaoWo(){
       if(  xmlHttpRequest.readyState == 4     
	         && xmlHttpRequest.status == 200){
              var dom = xmlHttpRequest.responseXML;
              if(dom){
              	var userNodes=dom.getElementsByTagName("username");
              	if(userNodes.length>0){
              		var username=userNodes[0].firstChild.nodeValue;
              		document.getElementById("username").value=username;
              	}
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
	</script>
  </head>
  
  <body>
    <form action="" method="post" >
    	<table align="center">
    		<tr>
    		<select name="user" onchange="getUser(this);" style="width: 150px">
    			<option value="">请选择</option>
    			<option value="hello">hello</option>
    			<option value="world">world</option>
    		</select>
    		</tr>
    		<tr>
    			<td><p>用户账户：<input type="text" id="username" style="width: 150px"/></p></td>
    		</tr>
    		<tr>
    			<td><p>用户密码：<input type="password" id="passwd" style="width: 150px"/></p></td>
    		</tr>
    		<tr>
    			<td><p>重复密码：<input type="password" id="passwdtwo" style="width: 150px"/></p></td>
    		</tr>
    		
    	</table> 	
    </form>
  </body>
</html>
