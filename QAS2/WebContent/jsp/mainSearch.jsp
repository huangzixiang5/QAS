<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索主界面</title>
</head>
<script type="text/javascript">
	
</script>
<frameset rows="15%,*">
	<frame src="<%=request.getContextPath() %>/jsp/searchTop.jsp" name="top" >
	<frameset cols="30%,*">
	<frame src="<%=request.getContextPath()%>/jsp/showList.jsp" name="left">
	<frame src="<%=request.getContextPath()%>/jsp/answerContent.jsp" name="right" id="right">
	</frameset>
</frameset>
<body>
</body>
</html>