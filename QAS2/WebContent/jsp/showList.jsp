<%@page import="data.QuestionAnswer"%>
<%@page import="javafx.scene.paint.Color"%>
<%@page import="org.apache.http.client.utils.URLEncodedUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/showList.js">    
</script>
<style type="text/css">
table{
   	width:380px;
    table-layout:fixed;/* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
}
.question{
    word-break:keep-all;/* 不换行 */
    white-space:nowrap;/* 不换行 */
    overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
    text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body style="color: graytext;">


<form action="<%=request.getContextPath()%>/DeleteServlet" method="post" id="delete" target="right">
	<%
	String path = request.getContextPath();
		@SuppressWarnings("unchecked")
			List<QuestionAnswer> answers = (List<QuestionAnswer>) request.getAttribute("answers");
		if (answers != null && answers.size() > 0)
		{
			out.print("<table><tr><th width=\"20px\">序号</th><th width=\"20px\">选择</th><th>问题</th></tr>");

			for (int i = 0; i < answers.size(); i++)
			{
				int j = answers.get(i).getId();
				String q = answers.get(i).getQuestion();
				String a = answers.get(i).getAnswer();
				String select = "<input type=\"checkbox\" name=\"id\" value="+j+">";
				String href ="<a href=\"javascript:show("+j+",'"+q+"','"+a+"');\" title=\""+q+"\">"+q+"</a>";
				if(i == 0)//默认显示第一条记录
				{
					out.print("<script type=\"text/javascript\">show("+j+",'"+q+"','"+a+"')</script>");
				}
				if (i % 2 == 0)
				{
					out.print("<tr bgcolor=\"#e6f2fe\"><td >"+(i+1)+"</td><td>"+select+"</td><td class=\"question\">" + href + "</td></tr>");
				} else
				{
					out.print("<tr><td >"+(i+1)+"</td><td>"+select+"</td><td class=\"question\">" + href + "</td></tr>");
				}
				
			}
			out.print("</table>");
			out.print("	<br><center><input type=\"button\" value=\"批量删除\" onclick=\"isChecked()\"></center>");
		}
		else
		{
			out.print("未找到相关问题");
		}
	%>
</form>
<jsp:include page="page.jsp" ></jsp:include>
</body>
</html>