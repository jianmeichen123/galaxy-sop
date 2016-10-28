<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<script src="<%=path %>/js/echarts_health.js" type="text/javascript"></script>
<dl class="r_news">
	<dt>
		<h3 class="ico t13">项目健康度<span class="Htips"></span></h3>
		<span class="more null position" id="platform_health_more" style="cursor: pointer;">more</span>
	</dt>
	<dd>
		<div id="container_health" style="min-width:300px;height: 162px;padding-top:5px;z-index:0;"></div>
	</dd>
</dl>

<script>
if(roleId!=1 && roleId!=2 && roleId!=3){
	$("#platform_health_more").hide();
}else{
	$("#platform_health_more").click(function(){
		forwardWithHeader(platformUrl.toHealthChartDetail+"?urlFlag=null");
	})
}

</script>