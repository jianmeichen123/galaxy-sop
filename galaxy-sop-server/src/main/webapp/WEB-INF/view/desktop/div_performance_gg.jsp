<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">


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


<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/charts/indexKpi.js"></script>
<script type="text/javascript">

//项目历时
load_data_chart_kpi();

</script>

</body>
</html>

