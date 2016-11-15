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
<script type="text/javascript" src="js/jquery-latest.js"></script>
<script type="text/javascript" src="js/GooFunc.js"></script>
<script type="text/javascript" src="js/my.js"></script>
<script type="text/javascript" src="js/flow.js"></script>
<title>${create }流程</title>
</head>
<body>
	<p>id is ${id }</p>
	<p>${info.bpmn }</p>
	<p>${tools }</p>
	<p>${toolTypes }</p>
	<p>${groups }</p>
	
	<div id='flow'>
	</div>

	<script type="text/javascript">
    	var instance = new Flow('flow', "${info.bpmn}");
	</script>
</body>
</html>
