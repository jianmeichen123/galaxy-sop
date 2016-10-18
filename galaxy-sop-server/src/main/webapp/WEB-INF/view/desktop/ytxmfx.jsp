<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<% 
String path = request.getContextPath();
%>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include>
<!-- 已投项目分析 -->
<dl class="executive_last ytxm_block">
	<dt>
		<h3 class="ico t9">
			已投项目分析<span class="Htips">（截止至当前）</span>
		</h3>
		<c:if test="${!fx:hasRole(3)}">
			<ul class="ytxm_tab position_tab clearfix">
				<li data-tab="nav">联合创业</li>
				<li data-tab="nav">融快</li>
				<li data-tab="nav">创保联</li>
			</ul>
		</c:if>
	</dt>
	<dd>
		<div id="charts_Joint" data-tab="con" style="min-width: 300px; height: 200px; padding-top: 5px; z-index: 0;"></div>
		<div id="charts_rk" data-tab="con" style="min-width: 300px; height: 200px; padding-top: 5px; z-index: 0;"></div>
		<div id="charts_cbl" data-tab="con" style="min-width: 300px; height: 200px; padding-top: 5px; z-index: 0;"></div>
	</dd>
</dl>
<script src="<%=path %>/js/echarts.js" type="text/javascript"></script>
<script src="<%=path %>/js/charts/projectPostAnalysis.js" type="text/javascript"></script>
