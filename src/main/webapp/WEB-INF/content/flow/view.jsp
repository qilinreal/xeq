<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程基本信息</title>
</head>
<body>
	<p>全部用户信息</p>
	<ul>
	<c:forEach items="${infos }" var="info">
		<li><pre>名字：${info.name }    用户ID：${info.userId }   节点数量：${info.flowNum }   <a target="_blank" href="flow/detail.action?id=${info.id}">json详情</a></pre></li>
	</c:forEach>
	</ul>
</body>
</html>
