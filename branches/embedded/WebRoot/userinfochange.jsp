<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />   
 
<title>超市管理系统</title>
<link href=css/unit2_.css rel=stylesheet type=css/text/>
<script type=text/javascript>
  function check(form){
	 if(form.userid.value==""){
     alert("用户编号不能为空！");
     return false;
    }
    if(form.name.value==""){
     alert("用户名不能为空！");
     return false;
    }
     if(form.age.value==""){
     alert("用户年龄不能为空！");
     return false;
    }
    if(form.telphone.value==""){
     alert("电话不能为空！");
     return false;
    }
    var martch=/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/
    if(!martch.test(form.telphone.value)){
     alert("电话输入有误！");
     return false;
    }
     if(form.address.value==""){
     alert("地址不能为空！");
     return false;
    }
    if(!window.confirm("确定修改？")){
      return false;
    }
    return true;
	  
  }
</script>
</head>

<body>
  <%
 // String sex=request.getParameter("sex");
 // System.out.println(sex.length());
 // System.out.println(sex.equals("男      "));
  %>
 <%
   pageContext.setAttribute("userid",request.getParameter("userid"));
   pageContext.setAttribute("name",request.getParameter("name"));
   pageContext.setAttribute("sex",request.getParameter("sex"));
   pageContext.setAttribute("age",request.getParameter("age"));
   pageContext.setAttribute("telphone",request.getParameter("telphone"));
   pageContext.setAttribute("address",request.getParameter("address"));
   pageContext.setAttribute("power",request.getParameter("power"));
   pageContext.setAttribute("password",request.getParameter("password"));
 %>
 <form action="usermanage.jsp?action=modify" method=post onsubmit="return check(this);">
  <div style="width:80%;height:320px ;border:10px solid #4169E1">
   <table width=60% style=padding-top:40px >
    <tr>
     <td><input name=userid type=hidden value=${userid} /></td>
    </tr>
    <tr>
     <td>用户名称：</td>
     <td><input name=name type=text value=${name} /></td>
    </tr>
    <tr>
     <td>用户性别：</td>
     <%if(request.getParameter("sex").equals("男")){%>
        <td><select name=sex><option value="男" selected>男</option>
        <option value=女 >女</option></select></td>
     <%}else{%>
        <td><select name=sex><option value=男 >男</option>
        <option value=女 selected>女</option></select></td>
     <%} %>
     <option value=女 >女</option></select></td>
    </tr>
    <tr>
     <td>用户年龄:</td>
     <td><input name=age type=text value=${age} /></td>
    </tr>
    <tr>
     <td>用户电话:</td>
     <td><input name=telphone type=text value=${telphone} /></td>
    </tr><tr>
     <td>用户住址:</td>
     <td><textarea name=address>${address}</textarea></td>
    </tr>
    </tr><tr>
     <td>用户权限:</td>
   <c:if test="${sessionScope.userid==1}">
     <c:if test="${power=='普通用户'}">
     <td><input type=radio name=power value=${power} checked />普通用户<input type=radio name=power value=${"经理"} />经理</td>
     </c:if>
      <c:if test="${power=='经理'}">
      <td><input type=radio name=power value=${"普通用户"} />普通用户<input type=radio name=power value=${"经理"} checked />经理</td>
      </c:if>
      <c:if test="${power=='管理员'}">
      <td>${power}<input type=hidden name=power value=${power} /> </td>
      </c:if>
    </c:if>
    <c:if test="${sessionScope.userid!=1}">
     <td>${power}<input type=hidden name=power value=${power} /></td>
    </c:if>
   </tr>
   </table>
  </div>
  <a href="usermanage.jsp"><input style=margin-left:80px;float:left; type="button" value=返回 /></a>
  <input style=margin-left:30px;float:left; type="submit" value=修改  />
 </form>
 <form action="usermanage.jsp?action=delete" method=post>
  <input name=userid type=hidden value=${userid} />
  <input style=margin-left:30px type="submit" value=删除 />
  <a href="userpwdchange.jsp?userid=${userid}&name=${name}&password=${password}"><input style=margin-left:30px type="button" value=修改密码 /></a>
 </form>
 
</body>
</html>