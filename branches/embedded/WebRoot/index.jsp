<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>   
 
<title>超市管理系统</title>
<link href="css/unit2_a.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
  function check(form){
    if(form.username.value==""){
     alert("用户名不能为空！");
     return false;
    }
    if(form.password.value==""){
     alert("密码不能为空！");
     return false;
    }
    return true;
  }
</script>
</head>

<body>
  <div class='middle'>
  <%--在html中标签的属性的大小写不影响什么，大小写都可以--%>
  <form id="formtest" action="login" method=post onsubmit="return check(this);" >
   <table >
     <tr>
       <td>用户名：</td>
       <td><input name=username type=text value="" size=22 /></td>
     </tr>
     <tr>
       <td>密&nbsp;&nbsp;码：</td>
       <td><input name=password type=password value="" size=22 /></td>
     </tr>
     <tr>
       <td ><input type=submit value=确认 /></td>
       <td ><input type=reset value=重置 /></td>
     </tr>
   </table>
  </form>
  </div>
  <div class=bottom>
  <marquee  behavior=alternate>欢迎您使用超市管理系统！</marquee>
  </div>
</body>
</html>
