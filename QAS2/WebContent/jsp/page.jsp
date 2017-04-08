<%@page import="data.Pager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
function submit(tp,cp,np) {
	if(cp != np && 0<np<=tp)
		{
		document.getElementById("pager").cp.value = np;
		 document.getElementById("pager").submit();
		}	
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
#pagelist  li a { color:#333 !important; text-decoration:none;}
ul { list-style:none;}
#pagelist  li { float:left; border:1px solid #5d9cdf; height:20px; line-height:20px; margin:0px 2px;}
#pagelist  li{ padding:0px 6px; background:#e6f2fe;}


</style>
</head>
<body>
<%	
	String q = (String)request.getAttribute("q"); //获取查询的问题
	if(q ==null)
	{
		q="";
	}
	Integer type1 = (Integer)request.getAttribute("type"); //获取查询的类型（查询问题库还是解答库）
	int type = 2;
	if(type1 != null)
	{
		type = type1;
	}
	
	Pager page2 = (Pager)request.getAttribute("page"); //获取相关分页信息
	int totalPage = 1; //总页数
	int currentPage = 1; //当前页
	int totalRecords = 0; //总记录数
	if(page2 != null)
	{
		totalRecords = page2.getTotalRecords();
		 totalPage = page2.getTotalPages();
		 currentPage = page2.getCurrentPage();
	}
%>
<div id="pagelist">
  <ul>
    <li><a href="javascript:submit(<%=totalPage%>,<%=currentPage %>,1)">首页</a></li>
    <li><a href="javascript:submit(<%=totalPage%>,<%=currentPage %>,<%=currentPage-1%>)">上页</a></li>
    <li title="当前页"><%=currentPage %></li>
    <li><a href="javascript:submit(<%=totalPage%>,<%=currentPage %>,<%=currentPage+1%>)">下页</a></li>
    <li><a href="javascript:submit(<%=totalPage%>,<%=currentPage %>,<%=totalPage%>)">尾页</a></li>
  </ul>
  共<%=totalRecords+"条记录/"+totalPage+"页"%>
</div>
<form action="<%=request.getContextPath()%>/SearchList" id="pager" method="post">
	<input type="hidden" name="q" value="<%=q %>">
	<input type="hidden" name="type" value="<%=type%>">
	<input type="hidden" id="tr" name="tr" value="<%=totalRecords%>">
	<input type="hidden" name="cp" value="<%=currentPage%>">
</form>

</body>
</html>
