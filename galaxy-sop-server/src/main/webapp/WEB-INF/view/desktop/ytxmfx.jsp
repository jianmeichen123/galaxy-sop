<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
String path = request.getContextPath();
%>
<!-- 已投项目分析 -->
<dl class="executive_last ytxm_block">
	<dt>
		<h3 class="ico t9">
			已投项目分析<span class="Htips" style="font-size:12px;font-family:'宋体';margin-left:5px;">（截止到当前）</span>
		</h3>
			<ul class="ytxm_tab position_tab clearfix">
				<li data-tab="nav">联合创业</li>
				<li data-tab="nav">成长投资</li>
				<li data-tab="nav">创保联</li>
			</ul>
	</dt>
	<dd>
		<div id="charts_Joint" data-tab="con" style="height: 180px; z-index: 0;"></div>
		<div id="charts_rk" data-tab="con" style="height: 180px; z-index: 0;"></div>
		<div id="charts_cbl" data-tab="con" style="height: 180px; z-index: 0;"></div>
	</dd>
</dl>
<script src="<%=path %>/js/charts/projectPostAnalysis.js" type="text/javascript"></script>
<<script type="text/javascript">
if(roleId==3){
	$(".ytxm_tab").hide();
}
</script>
