<!-- 处理删除操作返回信息 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function show(i) { //更新列表
	var total = self.parent.frames["left"].document.getElementById("tr").value;
	var count  =parseInt(total)-parseInt(i);
	self.parent.frames["left"].document.getElementById("tr").value  = count;
	self.parent.frames["left"].document.getElementById("pager").submit();
}

function warn(i){
	alert("已删除"+i+"条数据"); //提示删除数据条数
	window.location.href='/QAS2/jsp/answerContent.jsp'; //再次定位到答案内容页
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>deleteShow</title>
</head>
<body>
<%
Integer i = (Integer)request.getAttribute("count");
%>
<script type="text/javascript">	show("<%=i%>")</script>
<script type="text/javascript">	warn("<%=i%>")</script>
</body>
</html>