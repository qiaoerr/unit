<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>   
 
<title>���й���ϵͳ</title>
<link href='css/unit2_b.css' rel="stylesheet" type="text/css"/>
</head>

<body>
 <div class=top>
  <span id=title> ���й���ϵͳ</span>
  <span id=user>��ӭ����${sessionScope.username}</span>
 </div>
 <div class=bottom>
  <table width=100% >
   <tr>
    <td width=20% valign=top>
     <ul>
      <li id=left_a><a href="billmanage.jsp" target=myframe><img src=picture/left1.jpg />�˵�����</a></li>
      <li id=left_b><a href="suppliermanage.jsp" target=myframe><img src=picture/left2.jpg />��Ӧ�̹���</a></li>
      <li id=left_c><a href="usermanage.jsp" target=myframe><img src=picture/left3.jpg />�û�����</a></li>
      <li id=left_d><a href="exit.jsp"><img src=picture/left4.jpg />�˳�ϵͳ</a></li>
     </ul>
    </td>
    <td width=1%><div class=bar></div></td>
    <td width=79%>
     <iframe src=welcome.jsp name="myframe"  width=100% height=490px  frameborder="1" scrolling="yes" > 
     </iframe>
    </td>
   </tr>
  </table>
 </div>
</body>
</html>
