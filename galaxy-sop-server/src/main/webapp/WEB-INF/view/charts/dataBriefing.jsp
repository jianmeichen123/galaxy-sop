<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>星河投</title>
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<!-- jsp文件头和头部 -->
	<%@ include file="/WEB-INF/view/common/taglib.jsp"%>
	<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
	<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
	<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>
    
</head>
<body>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
<!--右中部内容-->
	<div class="pagebox clearfix">
		<div class="ritmin">
			<h2>数据简报</h2>
			<div class="tabtable">
				<!-- tab内容 -->
				<div class="tabtable_con tabtable_con_brif">
					<!--目标完成情况部分-->
					<div class="chartbox">
						<!-- 项目总目标完成跟踪 -->
						<div class="chartbox_li">
							<!-- 条形图展示  -->
							<h2 class="chart_name">项目目标追踪</h2>
							<div id="chartBrief1" class="chart" style="min-width:800px;"></div>
							<div class="statistic">
								<ul>
									<li>本年度目标项目数:<span id="show_target_count"></span></li>
									<li>截至目前完成数:<span id="show_project_count"></span></li>
									<li>截至目前未完成/超额完成数:<span id="show_not_completed_count"></span></li>
								</ul>
							</div>
						</div>
						<div class="line"></div>
						<!--  投资金额总目标完成跟踪图 -->
						<div class="chartbox_li">
							<div class="chart_default"></div>
							<!-- 此处添加水印 -->
							<div id="container_11" class="chart" style="min-width:800px;"></div>
							<div class="statistic">
								<ul>
									<li><label>本年度目标投资金额:</label><span>￥100000</span></li>
									<li><label>截至目前已使用金额:</label><span>￥80000</span></li>
									<li><label>截至目前未使用/超额使用金额:</label><span>￥20000/0</span></li>
								</ul>
							</div>
						</div>
	
<!-- 						<div class="center top clearfix"> -->
<!-- 							平台项目状态分布图  -->
							
<!-- 								<h2 class="chart_name">投资事业线目标完成对比</h2> -->
<!-- 								<div id="chartBrief3"  style="min-width:800px;"></div> -->
						
<!-- 						</div> -->
						<jsp:include page="../report/dataSimpleRep/div_dataBriefing.jsp" flush="true"></jsp:include>
						<!-- 项目完成跟踪图 -->
						<div class="bottom">
							<h2 class="chart_name">项目完成率分析</h2>
							<div id="chartBrief4" class="chart chart_m"></div>
						</div>
	
					</div>
	
					<!-- 绩效报告部分 -->
					<div class="chartbox"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
</body>
<script src="<%=path%>/js/charts/dataBriefing.js"></script>
</html>