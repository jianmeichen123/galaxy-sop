<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>

	<!-- 绩效考核 -->
	<dl>
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


<script src="<%=path %>/js/charts/indexKpi.js"></script>
<script type="text/javascript">

//项目历时
load_data_chart_kpi();

</script>


