<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />   
 
<title>���й���ϵͳ</title>
<link href=css/unit2_.css rel=stylesheet type=css/text/>
<script type=text/javascript>
  function check(form){
	 if(form.userid.value==""){
     alert("�û���Ų���Ϊ�գ�");
     return false;
    }
    if(form.name.value==""){
     alert("�û�������Ϊ�գ�");
     return false;
    }
     if(form.age.value==""){
     alert("�û����䲻��Ϊ�գ�");
     return false;
    }
    if(form.telphone.value==""){
     alert("�绰����Ϊ�գ�");
     return false;
    }
    var martch=/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/
    if(!martch.test(form.telphone.value)){
     alert("�绰��������");
     return false;
    }
     if(form.address.value==""){
     alert("��ַ����Ϊ�գ�");
     return false;
    }
    if(!window.confirm("ȷ���޸ģ�")){
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
 // System.out.println(sex.equals("��      "));
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
     <td>�û����ƣ�</td>
     <td><input name=name type=text value=${name} /></td>
    </tr>
    <tr>
     <td>�û��Ա�</td>
     <%if(request.getParameter("sex").equals("��")){%>
        <td><select name=sex><option value="��" selected>��</option>
        <option value=Ů >Ů</option></select></td>
     <%}else{%>
        <td><select name=sex><option value=�� >��</option>
        <option value=Ů selected>Ů</option></select></td>
     <%} %>
     <option value=Ů >Ů</option></select></td>
    </tr>
    <tr>
     <td>�û�����:</td>
     <td><input name=age type=text value=${age} /></td>
    </tr>
    <tr>
     <td>�û��绰:</td>
     <td><input name=telphone type=text value=${telphone} /></td>
    </tr><tr>
     <td>�û�סַ:</td>
     <td><textarea name=address>${address}</textarea></td>
    </tr>
    </tr><tr>
     <td>�û�Ȩ��:</td>
   <c:if test="${sessionScope.userid==1}">
     <c:if test="${power=='��ͨ�û�'}">
     <td><input type=radio name=power value=${power} checked />��ͨ�û�<input type=radio name=power value=${"����"} />����</td>
     </c:if>
      <c:if test="${power=='����'}">
      <td><input type=radio name=power value=${"��ͨ�û�"} />��ͨ�û�<input type=radio name=power value=${"����"} checked />����</td>
      </c:if>
      <c:if test="${power=='����Ա'}">
      <td>${power}<input type=hidden name=power value=${power} /> </td>
      </c:if>
    </c:if>
    <c:if test="${sessionScope.userid!=1}">
     <td>${power}<input type=hidden name=power value=${power} /></td>
    </c:if>
   </tr>
   </table>
  </div>
  <a href="usermanage.jsp"><input style=margin-left:80px;float:left; type="button" value=���� /></a>
  <input style=margin-left:30px;float:left; type="submit" value=�޸�  />
 </form>
 <form action="usermanage.jsp?action=delete" method=post>
  <input name=userid type=hidden value=${userid} />
  <input style=margin-left:30px type="submit" value=ɾ�� />
  <a href="userpwdchange.jsp?userid=${userid}&name=${name}&password=${password}"><input style=margin-left:30px type="button" value=�޸����� /></a>
 </form>
 
</body>
</html>