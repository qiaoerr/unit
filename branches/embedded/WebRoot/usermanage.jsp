<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.database.*" %>
<%@ page import="com.servlet.*" %>
<%@ page import="com.bean.*" %>
<%@ page import="com.util.*" %>
<%@ page import="javax.swing.JOptionPane" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />   
 
<title>���й���ϵͳ</title>
<link href=css/unit2_c.css rel=stylesheet type=css/text/>
</head>

  <%
  request.setCharacterEncoding("GBK");
  ArrayList<User> users=null;
 // System.out.println(session.getAttribute("userid").toString());
   if(request.getParameter("action")!=null){
   //���Ĳ���
	  if(request.getParameter("action").equals("modify")){
		  long userid=Long.valueOf(request.getParameter("userid"));//���û��������ص�
		  String name=request.getParameter("name");
		  String sex=request.getParameter("sex");
		  int age=Integer.valueOf(request.getParameter("age"));
		  String telphone=request.getParameter("telphone");
		  String address=request.getParameter("address");
		  String power=request.getParameter("power");
		  
		  User user=new User();
		  user.setUserid(userid);
		  user.setName(name);
		  user.setSex(sex);
		  user.setAge(age);
		  user.setTelphone(telphone);
		  user.setAddress(address);
		  user.setPower(power);
		  if(session.getAttribute("userid").toString().equals(request.getParameter("userid"))){
		      session.setAttribute("username",name);
		      if(new User_Operation().userupdate(user)){%>
		      
		      <script type="text/javascript">
		      alert("���³ɹ���");
		      parent.document.getElementById("user").innerHTML="��ӭ��: ${sessionScope.username}";
			  </script>
		     
			  <% 
			  //out.println("<script type=\"text/javascript\">");
	          //out.println("alert(\"���³ɹ���\");");
	          //out.println("parent.document.getElementById('user').innerHTML='��ӭ����" + name + "'");//name���ڷ������˽��еĴ���
	          //out.println("</script>");
		     }else{
			  out.println("<script type=\"text/javascript\">");
	          out.println("alert(\"����ʧ�ܣ�\");");
	          out.println("</script>");
		      }
		  }else{
		      if(new User_Operation().userupdate(user)){
			  out.println("<script type=\"text/javascript\">");
	          out.println("alert(\"���³ɹ���\");");
	          out.println("</script>");
		      }else{
			  out.println("<script type=\"text/javascript\">");
	          out.println("alert(\"����ʧ�ܣ�\");");
	          out.println("</script>");
		      }
		 }
		
		  
	  }else{
	  ///////////////////ɾ������
		  if(!new User_Operation().getPower(session.getAttribute("username").toString()).equals("����Ա")){
			  out.println("<script type=\"text/javascript\">");
		      out.println("alert(\"����Ȩ���д˲���\");");
		      out.println("open(\"usermanage.jsp\",\"_self\");");
		      out.println("</script>");
		      return;
		  }else if(request.getParameter("userid").equals("1")){
		      out.println("<script type=\"text/javascript\">");
		      out.println("alert(\"���ǹ���Ա������ɾ���Լ�\");");
		      out.println("open(\"usermanage.jsp\",\"_self\");");
		      out.println("</script>");
		      return;
		  }
		 int i= JOptionPane.showConfirmDialog(null,"��ȷ��ɾ������������","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
		 if(i==0){
			 long userid=Long.valueOf(request.getParameter("userid"));
			 User user =new User();
			 user.setUserid(userid);
			 boolean consequence =new User_Operation().userdelete(user);
			 if(consequence){
				 out.println("<script type=\"text/javascript\">");
		         out.println("alert(\"ɾ���ɹ���\");");
		         out.println("</script>");
			 }else{
				 out.println("<script type=\"text/javascript\">");
		         out.println("alert(\"ɾ��ʧ�ܣ�\");");
		         out.println("</script>");
			 }
		 }else{
			 //�û�ѡ����ȡ��
		 }

	  }
   }
     String name=request.getParameter("lookfor");
  	 if(name!=null){
  	 	 name=Tools.getstr(request.getParameter("lookfor"));
  	 }
  
   ////////////��ҳ
   {  int presentpage=0;
	  if(request.getParameter("page_no")==null){
	   presentpage=1;
	  }else{
	   presentpage=Integer.valueOf(request.getParameter("page_no")); 
	  }
	  int ppage=5;
	  int totaluser=0;
	  if(name==null||name.length()==0){
		  totaluser=new User_Operation().usernum();
	  }else{
		  totaluser=new User_Operation().usernum1(name);
		  
	  }
	  int totalpage=0;
	  if(totaluser%ppage==0){
		  totalpage=totaluser/ppage;
	  }else{
		  totalpage=totaluser/ppage+1;
	  }
	  pageContext.setAttribute("totalpage",totalpage);
	  pageContext.setAttribute("presentpage",presentpage);
	  pageContext.setAttribute("name",name);
	
	 users=new User_Operation().querybypage(presentpage,ppage,name);
	// users=new User_Operation().lookforuser(name);
   }
   
  
     if(!(new User_Operation().getPower(session.getAttribute("username").toString()).equals("����Ա"))){
    	 users=new User_Operation().getUser();
    	 ArrayList<User> user1=new ArrayList<User>();
    	 for(User user:users){
    		 if(user.getName().equals(session.getAttribute("username").toString())){
    			 user1.add(user);
    		 }
    	 }
    	 users=user1;
     }
   
  
     pageContext.setAttribute("users",users); 
     
  %>
<body>
<%if(new User_Operation().getPower(session.getAttribute("username").toString()).equals("����Ա")){%>
  <form action=usermanage.jsp method=get>
        �û�����<input name=lookfor type=text value="${name}"/>
   <input type=submit value=��ѯ />
  </form>
  <div class=middle>�û�����>><span> </span><a href="userinsert.jsp">��������</a></div>
  <hr size=2 color=#99FFFF;/>
  <div class=table>
   <table width=100% border=10px bordercolor=#4169E1>
    <tr>
     <td>�û����</td>
     <td>�û���</td>
     <td>�Ա�</td>
     <td>����</td>
     <td>�绰</td>
     <td>סַ</td>
     <td>Ȩ��</td>
    </tr>
    <c:forEach var="user" items="${pageScope.users}">
     <tr>
      <td>${user.userid}</td>
      <td><a href="userinfochange.jsp?userid=${user.userid}&name=${user.name}&sex=${user.sex}&
      &age=${user.age}&telphone=${user.telphone}&address=${user.address}&power=${user.power}&password=${user.password}">${user.name}</a></td>
      <td>${user.sex}</td>
      <td>${user.age}</td>
      <td>${user.telphone}</td>
      <td>${user.address}</td>
      <td>${user.power}</td>
     </tr>
    </c:forEach>
   </table>
  </div>
  <c:if test="${presentpage==1&&presentpage<totalpage}">
    <p>��һҳ
    <a href="usermanage.jsp?page_no=2&lookfor=${name}">��һҳ</a>
    <a href="usermanage.jsp?page_no=${totalpage}&lookfor=${name}">���һҳ</a>
    </p>  
  </c:if>
  <c:if test="${presentpage>1&&presentpage<totalpage}">
    <p>��${presentpage} / ${totalpage}ҳ
    <a href="usermanage.jsp?page_no=${presentpage-1}&lookfor=${name}">��һҳ</a>
    <a href="usermanage.jsp?page_no=${presentpage+1}&lookfor=${name}">��һҳ</a>
    </p>  
  </c:if>
    <c:if test="${presentpage==totalpage&&presentpage!=1}">
    <p>���һҳ
    <a href="usermanage.jsp?page_no=${presentpage-1}&lookfor=${name}">��һҳ</a>
    <a href="usermanage.jsp?page_no=${1}&lookfor=${name}">��һҳ</a>
    </p>  
  </c:if>
   <c:if test="${presentpage==totalpage&&presentpage==1}">
   <p>ֻ��һҳ</p>  
  </c:if>
  <%}else{ %>
     <div class=table>
   <table width=100% border=10px bordercolor=#4169E1>
    <tr>
     <td>�û����</td>
     <td>�û���</td>
     <td>�Ա�</td>
     <td>����</td>
     <td>�绰</td>
     <td>סַ</td>
     <td>Ȩ��</td>
    </tr>
    <c:forEach var="user" items="${pageScope.users}">
     <tr>
      <td>${user.userid}</td>
      <td><a href="userinfochange.jsp?userid=${user.userid}&name=${user.name}&sex=${user.sex}&
      &age=${user.age}&telphone=${user.telphone}&address=${user.address}&power=${user.power}&password=${user.password}">${user.name}</a></td>
      <td>${user.sex}</td>
      <td>${user.age}</td>
      <td>${user.telphone}</td>
      <td>${user.address}</td>
      <td>${user.power}</td>
     </tr>
    </c:forEach>
   </table>
  </div>
  <%} %>
</body>
</html>