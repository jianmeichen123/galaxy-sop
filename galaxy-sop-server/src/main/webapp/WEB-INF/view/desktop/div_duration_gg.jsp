<%@ page language="java" pageEncoding="UTF-8"
	import="com.galaxyinternet.framework.core.oss.OSSConstant,com.galaxyinternet.model.user.User"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.galaxyinternet.com/tags/acl" prefix="acl"%>
<% 
	String path = request.getContextPath();
	String endpoint = (String)application.getAttribute(OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
	java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
	String reportEndpoint = map.get("galaxy.project.report.endpoint");
%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="renderer" content="webkit" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		
		<title>繁星</title>
		
		<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet" />
		<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
		
		<!-- bootstrap-table -->
		<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
		 
		<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
	</head>

<body>

<div class="floatBox fl">
	<!-- 项目历时 -->
	<dl resource-mark="div_duration_gg" >
		<dt>
			<h3 class="ico t11">项目历时</h3>
		</dt>
		<dd style="position: relative;">
			<div class="mask_platform_time"></div>
			<div id="container_time"
				style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
		</dd>
	</dl>
</div>



<script src="<%=path %>/js/charts/indexProjectDuration.js"></script>
<script type="text/javascript">

//项目历时
$(function() {
	load_data_chart_project_time();
	noDataProTimeDiv();
});
	
</script>

</body>
</html>

