<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">


	<!-- 项目进度 -->
	<dl resource-mark="div_project_progress_gg" >
		<dt>
			<h3 class="ico t8">项目进度</h3>
			<a href="javascript:;" class="more position_0 null" id="more_progress">more</a>
		</dt>
		<dd style="position: relative;">
			<div class="mask_platform_progress"></div>
			<div id="container_progress"
				style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
		</dd>
	</dl>


<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/charts/projectProgress.js"></script>
<script type="text/javascript">

//项目进度
var progressFormdata = {
		domid : 'container_progress'
}
chartProjectProgressUtils.init(progressFormdata);
noDataProGressDiv();

//项目进度图表默认加载链接
$("#container_progress .highcharts-title tspan").click(function(){
	var url = platformUrl.projectAnalysis;
	if(forwardParam.progressParam){
		url += "?forwardProgress=" + forwardParam.progressParam ;
	}
	forwardWithHeader(url);
});

</script>

</body>
</html>

