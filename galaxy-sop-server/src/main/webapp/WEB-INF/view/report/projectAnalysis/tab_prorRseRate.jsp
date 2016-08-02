<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>繁星</title>
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
	<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
	<!-- 日历插件 -->
	<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
	<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
	<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <script src="<%=path %>/js/init.js"></script>
</head>



<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">

	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
		<h2>项目分析</h2>
		
		<div class="tabtable project_analysis">
			<!-- tab标签 -->
			<ul class="tablink">
				<li><a href="javascript:;" onclick="showCheckTabs(1)">项目总览</a></li>   
				<li><a href="javascript:;" onclick="showCheckTabs(2)">项目数统计</a></li>
				<li class="on"><a href="javascript:;" onclick="showCheckTabs(3)">项目完成增长率统计</a></li>
				<li><a href="javascript:;" onclick="showCheckTabs(4)">过会率统计</a></li>
				<li><a href="javascript:;" onclick="showCheckTabs(5)">投决率统计</a></li>
			</ul>
			
			
			<!-- 项目数完成增长率统计 -->
				<div class="tabtable_con">
					<form id="search_rise_rate_form">
					<div class="search_box searchall">
						<dl class="fmdl fmmr clearfix">
							<dt>投资事业线：</dt>
							<dd>
								<select name="departmentId" id="search_project_depart_id"></select>
							</dd>
						</dl>
						<dl class="fmdl fmmr clearfix">
							<dt>项目类型：</dt>
							<dd>
								<select name="projectType" id="search_project_type">
									<option value="all">全部</option>
									<option value="projectType:2">内部创建</option>
									<option value="projectType:1">外部投资</option>
								</select>
							</dd>
						</dl>
						<div id= "search_datepicker">					
						</div>
						<dl class="fmdl fmmr clearfix">
							<dd>
								<a href="javascript:;" class="bluebtn ico tj"
									id="search_btn">统计</a>
							</dd>
						</dl>
					</div>
					</form>
					<!--柱状图部分-->
					<div class="chartbox">
						<div id="chart_rise_rate" class="chart chart_m"></div>
					</div>
					<!--表格内容-->
					<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
					<table id="grid_rise_rate" width="100%" cellspacing="0"
						cellpadding="0">
					</table>
				</div>
				
				
			
		</div>
	</div>
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


</body>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datePicker-handler-init.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-selecter.js"></script>

<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=path%>/js/charts/tabAnalysisOverview.js"></script>
<script src="<%=path%>/js/charts/tabAnalysisRiseRate.js"></script>
<script src="<%=path%>/js/charts/projectAnalysis.js"></script>

<script type="text/javascript">

$(function(){
	
	riseRateInit();
});
</script>

</html>

