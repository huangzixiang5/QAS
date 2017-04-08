<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

function submit() {
	document.getElementById("modify").submit() ;
}

</script>
<style type="text/css">
        html
        {
        width:100%;
         height:100%;
         margin:0;
        }
        body
        {
        	width:100%;
            height:100%;
            margin:0; 
        }
        .text1{
        	min-height:50px;
        	height:100%;
        	width:800px;
        	margin:10px 20px 0  0 ; 
        }
         .text2{     
        	min-height:200px;
        	height:100%;
        	width:800px;
        	margin:10px 20px 0 0; 
        }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内容</title>
</head>
<body >

	<form method=POST action="<%=request.getContextPath() %>/AdditionServlet" id="modify">
		<div align="center"><textarea  id="q" name="q"class="text1" ></textarea></div>
		<div align="center"><textarea  id="a" name="a"class="text2"></textarea></div>
		<br>
		<center><input type="button" value="提交" onclick="submit()"></center>
	</form>
</body>
</html>