<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>


	<!-- 项目历时 -->
	<dl resource-mark="div_duration_gg" >
		<dt>
			<h3 class="ico t11">项目历时</h3>
		</dt>
		<dd style="position: relative;">
			<div class="mask_platform_time"></div>
			<div id="container_time"
				style="width:100%; height: 145px; margin-left: -5%"></div>
		</dd>
	</dl>


<script src="<%=path %>/js/charts/indexProjectDuration.js"></script>
<script type="text/javascript">

load_data_chart_project_time();
noDataProTimeDiv();
	
</script>



