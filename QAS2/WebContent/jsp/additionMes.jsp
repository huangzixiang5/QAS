<%@page import="data.QuestionAnswer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function commit(i) {	
	if(i == 1)
		document.getElementById("cover").value = "true"; //设置覆盖原有数据
	if(i == 2)
		document.getElementById("cover").value = "false"; //设置保存原有数据
	document.getElementById("sub").submit();
}

function warn(i){
	alert("已成功添加"+i+"条数据"); //提示删除数据条数
	window.location.href='/QAS2/jsp/addition.jsp'; //再次定位到添加页
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加信息</title>
</head>
<body>
<%
	 
	Integer count = (Integer)request.getAttribute("count");
	if(count != null && count >0)//添加数据成功
	{
		out.print("<script type=\"text/javascript\">warn("+count+")</script>");
	}
	else //添加数据失败（由于库中已有相似数据，需要进一步判断）
	{
		QuestionAnswer existingQA = (QuestionAnswer)request.getAttribute("existingQA");
		QuestionAnswer currentQA = (QuestionAnswer)request.getAttribute("currentQA");
		%>
		库中已有相似问题：请选择相应操作<br>
		question:<%=existingQA.getQuestion() %> <br>
		answer:<%=existingQA.getAnswer() %><hr>
		<form method=POST action="<%=request.getContextPath() %>/AdditionServlet" id="sub" onsubmit="return false;">
		<div align="center"><textarea  id="q" name="q"class="text1" ><%=existingQA.getQuestion() %></textarea></div>
		<div align="center"><textarea  id="a" name="a"class="text2"><%=currentQA.getAnswer() %></textarea></div>
		<input type="hidden" id="cover" name="cover" value="">
		<br>
		<center><input type="button" value="提交覆盖" title="将删除库中相似记录" onclick="commit(1)"></center>
		<center><input type="button" value="提交合并" title="不会删除库中相似记录" onclick="commit(2)"></center>
	</form>
		
		<%
	}
	
%>

</body>
</html>