<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>   
 
<title>���й���ϵͳ</title>
<link href="css/unit2_a.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
  function check(form){
    if(form.username.value==""){
     alert("�û�������Ϊ�գ�");
     return false;
    }
    if(form.password.value==""){
     alert("���벻��Ϊ�գ�");
     return false;
    }
    return true;
  }
</script>
</head>

<body>
  <div class='middle'>
  <%--��html�б�ǩ�����ԵĴ�Сд��Ӱ��ʲô����Сд������--%>
  <form id="formtest" action="login" method=post onsubmit="return check(this);" >
   <table >
     <tr>
       <td>�û�����</td>
       <td><input name=username type=text value="" size=22 /></td>
     </tr>
     <tr>
       <td>��&nbsp;&nbsp;�룺</td>
       <td><input name=password type=password value="" size=22 /></td>
     </tr>
     <tr>
       <td ><input type=submit value=ȷ�� /></td>
       <td ><input type=reset value=���� /></td>
     </tr>
   </table>
  </form>
  </div>
  <div class=bottom>
  <marquee  behavior=alternate>��ӭ��ʹ�ó��й���ϵͳ��</marquee>
  </div>
</body>
</html>
