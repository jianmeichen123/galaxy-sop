<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">


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


<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/charts/indexProjectDuration.js"></script>
<script type="text/javascript">

load_data_chart_project_time();
noDataProTimeDiv();
	
</script>



