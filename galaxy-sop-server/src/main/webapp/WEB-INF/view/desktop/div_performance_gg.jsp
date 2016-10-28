<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>

	<!-- 绩效考核 -->
	<dl>
		<dt>
			<h3 class="ico t10">绩效考核</h3>
			<span class="more null position" id="platform_jxkh_more" style="cursor: pointer;">more</span>
		</dt>
		<dd style="position:relative;">
			<span class="highchartsAxisName name1">项目数(个)</span>
			<span class="highchartsAxisName name2">过会率(%)</span>
			<div id="container_kpi" style="height: 162px;"></div>
		</dd>
	</dl>


<script src="<%=path %>/js/charts/indexKpi.js"></script>
<script type="text/javascript">

load_data_chart_kpi();
if(roleId!=1 && roleId!=2 && roleId!=3){
	$("#platform_jxkh_more").hide();
	//$("#platform_jxkh_more").show();
}
</script>


