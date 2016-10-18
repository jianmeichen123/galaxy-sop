<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=path %>/js/echarts_health.js" type="text/javascript"></script>
<dl class="r_news" resource-mark="div_health_gg" style="display:none">
	<dt>
		<h3 class="ico t13">项目健康度<span class="Htips">（截止至当前）</span></h3>
		<span class="more null position_0" id="platform_health_more" style="cursor: pointer;">more</span>
	</dt>
	<dd>
		<div id="container_health" style="width:100%; height: 162px;padding-top:5px;z-index:0;"></div>
	</dd>
</dl>

<script>
$("#platform_health_more").click(function(){
	forwardWithHeader(platformUrl.toHealthChartDetail+"?urlFlag=null");
})
</script>