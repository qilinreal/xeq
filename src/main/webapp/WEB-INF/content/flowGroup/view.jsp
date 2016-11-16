<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>流程组基本信息</title>
	<style>
		.item {
			cursor: pointer;
		}
	</style>
</head>
<body>
<p>全部组 <input type="button" value="新增" idd="add"><input type="button" value="删除" idd="delete"></p>
<ul>
	<c:forEach items="${groups }" var="group">
		<li class="item" id="${group.id }">
			<pre>ID: ${group.id }    名字：${group.name }    介绍：${group.intro }   <a target="_blank"
																				  href="flow/detail.action?id=${group.id}">json详情</a></pre>
		</li>
	</c:forEach>
</ul>
<script type="text/javascript">
	function dblclick(e) {
		console.log(e);
	}
	var items = document.getElementsByClassName('item');
	for (var i = 0; i < items.length; i++) {
		items[i].addEventListener('dblclick', dblclick);
	}
</script>
</body>
</html>
