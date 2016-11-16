<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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

	<link rel="stylesheet" type="text/css" href="css/default.css">
	<link rel="stylesheet" type="text/css" href="css/my.css">

	<script type="text/javascript" src="js/jquery-latest.js"></script>
	<script type="text/javascript" src="js/GooFunc.js"></script>
	<script type="text/javascript" src="js/my.js"></script>
	<script type="text/javascript" src="js/flow.js"></script>
	<title>${create }流程</title>

	<style>
		#flowTip {
			display: none;
			width: 200px;
			height: 200px;
		}
	</style>
</head>
<body>

<div id="flowTip">
	<p>id is ${id }</p>
	<p>流程名字：${info.name }</p>
	<p>可用工具数量：${fn:length(tools) }</p>
	<p>工具种类：${fn:length(toolTypes) }</p>
	<p>组别种类：${fn:length(groups) }</p>
</div>

<div id='flow'>
</div>

<div id='flowInfo' style="display: none;">${info.flow }</div>
<script type="text/javascript">
	var flow = document.getElementById('flowInfo').innerHTML.replace(/\+/g, '%20');
	flow = decodeURIComponent(flow);
	var instance = new Flow('flow', flow, true, '${info.name }');
	instance.setToolTipInfo('flowTip');
</script>
</body>
</html>
