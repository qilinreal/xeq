<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
<script type="text/javascript" src="js/jquery-latest.js"></script>
<script type="text/javascript" src="js/GooFunc.js"></script>
<script type="text/javascript" src="js/my.js"></script>
<script type="text/javascript" src="js/flow.js"></script>
<title>详细信息</title>
</head>
<body>
	<p>名字：${info.name }</p>
	<p>用户ID：${info.userId }</p>
	<p>节点数量：${info.flowNum }</p>
	<p>详细信息：</p>
	<p>${info.bpmn }</p>
</body>
</html>
