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
		<!-- bootstrap-table
		<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css" type="text/css">
		 -->
		<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
	</head>

<body>

<div class="floatBox fl">
	<!-- 绩效考核 -->
	<dl resource-mark="div_performance_gg" class="r_news executive_last">
		<dt>
			<h3 class="ico t10">绩效考核</h3>
			<span class="more null position_0" id="platform_jxkh_more" style="cursor: pointer;">more</span>
		</dt>
		<dd style="position:relative;">
			<span class="highchartsAxisName name1">项目数(个)</span>
			<span class="highchartsAxisName name2">过会率(%)</span>
			<div id="container_kpi" style="min-width:300px; height: 200px;padding-top:5px;"></div>
		</dd>
	</dl>
</div>



<script src="<%=path %>/js/charts/indexKpi.js"></script>
<script type="text/javascript">

//项目历时
$(function() {
	load_data_chart_kpi();
});
	
</script>

</body>
</html>

