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
 
<title>超市管理系统</title>
<link href=css/unit2_c.css rel=stylesheet type=css/text/>
</head>

  <%
  request.setCharacterEncoding("GBK");
  ArrayList<User> users=null;
 // System.out.println(session.getAttribute("userid").toString());
   if(request.getParameter("action")!=null){
   //更改操作
	  if(request.getParameter("action").equals("modify")){
		  long userid=Long.valueOf(request.getParameter("userid"));//在用户界面隐藏掉
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
		      alert("更新成功！");
		      parent.document.getElementById("user").innerHTML="欢迎您: ${sessionScope.username}";
			  </script>
		     
			  <% 
			  //out.println("<script type=\"text/javascript\">");
	          //out.println("alert(\"更新成功！\");");
	          //out.println("parent.document.getElementById('user').innerHTML='欢迎您：" + name + "'");//name是在服务器端进行的处理
	          //out.println("</script>");
		     }else{
			  out.println("<script type=\"text/javascript\">");
	          out.println("alert(\"更新失败！\");");
	          out.println("</script>");
		      }
		  }else{
		      if(new User_Operation().userupdate(user)){
			  out.println("<script type=\"text/javascript\">");
	          out.println("alert(\"更新成功！\");");
	          out.println("</script>");
		      }else{
			  out.println("<script type=\"text/javascript\">");
	          out.println("alert(\"更新失败！\");");
	          out.println("</script>");
		      }
		 }
		
		  
	  }else{
	  ///////////////////删除操作
		  if(!new User_Operation().getPower(session.getAttribute("username").toString()).equals("管理员")){
			  out.println("<script type=\"text/javascript\">");
		      out.println("alert(\"您无权进行此操作\");");
		      out.println("open(\"usermanage.jsp\",\"_self\");");
		      out.println("</script>");
		      return;
		  }else if(request.getParameter("userid").equals("1")){
		      out.println("<script type=\"text/javascript\">");
		      out.println("alert(\"你是管理员，不能删除自己\");");
		      out.println("open(\"usermanage.jsp\",\"_self\");");
		      out.println("</script>");
		      return;
		  }
		 int i= JOptionPane.showConfirmDialog(null,"您确认删除此条数据吗","确认",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
		 if(i==0){
			 long userid=Long.valueOf(request.getParameter("userid"));
			 User user =new User();
			 user.setUserid(userid);
			 boolean consequence =new User_Operation().userdelete(user);
			 if(consequence){
				 out.println("<script type=\"text/javascript\">");
		         out.println("alert(\"删除成功！\");");
		         out.println("</script>");
			 }else{
				 out.println("<script type=\"text/javascript\">");
		         out.println("alert(\"删除失败！\");");
		         out.println("</script>");
			 }
		 }else{
			 //用户选着了取消
		 }

	  }
   }
     String name=request.getParameter("lookfor");
  	 if(name!=null){
  	 	 name=Tools.getstr(request.getParameter("lookfor"));
  	 }
  
   ////////////分页
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
   
  
     if(!(new User_Operation().getPower(session.getAttribute("username").toString()).equals("管理员"))){
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
<%if(new User_Operation().getPower(session.getAttribute("username").toString()).equals("管理员")){%>
  <form action=usermanage.jsp method=get>
        用户名：<input name=lookfor type=text value="${name}"/>
   <input type=submit value=查询 />
  </form>
  <div class=middle>用户管理>><span> </span><a href="userinsert.jsp">增加数据</a></div>
  <hr size=2 color=#99FFFF;/>
  <div class=table>
   <table width=100% border=10px bordercolor=#4169E1>
    <tr>
     <td>用户编号</td>
     <td>用户名</td>
     <td>性别</td>
     <td>年龄</td>
     <td>电话</td>
     <td>住址</td>
     <td>权限</td>
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
    <p>第一页
    <a href="usermanage.jsp?page_no=2&lookfor=${name}">下一页</a>
    <a href="usermanage.jsp?page_no=${totalpage}&lookfor=${name}">最后一页</a>
    </p>  
  </c:if>
  <c:if test="${presentpage>1&&presentpage<totalpage}">
    <p>第${presentpage} / ${totalpage}页
    <a href="usermanage.jsp?page_no=${presentpage-1}&lookfor=${name}">上一页</a>
    <a href="usermanage.jsp?page_no=${presentpage+1}&lookfor=${name}">下一页</a>
    </p>  
  </c:if>
    <c:if test="${presentpage==totalpage&&presentpage!=1}">
    <p>最后一页
    <a href="usermanage.jsp?page_no=${presentpage-1}&lookfor=${name}">上一页</a>
    <a href="usermanage.jsp?page_no=${1}&lookfor=${name}">第一页</a>
    </p>  
  </c:if>
   <c:if test="${presentpage==totalpage&&presentpage==1}">
   <p>只有一页</p>  
  </c:if>
  <%}else{ %>
     <div class=table>
   <table width=100% border=10px bordercolor=#4169E1>
    <tr>
     <td>用户编号</td>
     <td>用户名</td>
     <td>性别</td>
     <td>年龄</td>
     <td>电话</td>
     <td>住址</td>
     <td>权限</td>
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