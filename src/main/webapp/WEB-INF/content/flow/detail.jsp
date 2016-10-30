<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
