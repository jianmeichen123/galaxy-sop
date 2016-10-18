<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>繁星</title>
</head>
<body>
<div class="pagebox clearfix">
	<%
		String[] modules = {"task","dataChart","div_duration_gg","div_performance_gg","idea_summary","message_tip","projectProgress","matterPreview"};
		for(String module : modules)
		{
			String url = "/galaxy/desktop/"+module;
			//if(!hasPermission) break;
	%>
			<div><!-- 模块标签 -->
			<jsp:include page="<%=url %>" flush="true"></jsp:include>
			</div>
	<%
			
		}
	%>
</div>
</html>

