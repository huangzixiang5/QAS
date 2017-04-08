<%@page import="teacherAgent.TeacherAgent"%>
<%@page import="com.scienjus.smartqq.Application"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="js/checkinput.js">    
</script>

<link href="css/input.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%

	request.setCharacterEncoding("utf-8");
%>
 <center style="background-color: gray;">
 <img alt="" src="images/k.png" >
  <hr>
 </center>
 <center>
  <form action="jsp/checkLogin.jsp" method="post" onsubmit="return checkform()">
  	<table >
  		<tr>
  			<td >用户名：</td>
  			<td ><input type="text"  name="userName" value="" id="in1" ></td>
  		</tr>
  		<tr>
  			<td>密码：</td>
  			<td ><input type="password" name="password" value="" id="in2"></td>
  		</tr> 	
  	</table>
	<br/>
	<input type="submit" value="提交" >
</form>
 </center>


	
				
</body>
</html>