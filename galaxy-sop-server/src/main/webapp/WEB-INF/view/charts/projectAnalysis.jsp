<%@ page language="java" pageEncoding="UTF-8"%>
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
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<div class="pagebox clearfix">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<!--右中部内容-->
	<div class="pagebox clearfix">
		<div class="ritmin">
			<h2>项目分析</h2>
			<div class="tabtable project_analysis">
				<!-- tab标签 -->
				<ul class="tablink">
					<li data-tab="nav"><a href="javascript:;">项目总览</a></li>
					<li data-tab="nav"><a href="javascript:;">项目数统计</a></li>
					<li data-tab="nav"><a href="javascript:;">项目完成增长率统计</a></li>
					<li data-tab="nav"><a href="javascript:;">过会率统计</a></li>
					<li data-tab="nav"><a href="javascript:;">投决率统计</a></li>
				</ul>
				
				
				
				<!-- 项目总揽部分 -->
				<div class="tabtable_con" data-tab="con">
				  	<form id="search_overview_form">
					<div class="search_box searchall" id="custom-toolbasr-xmzl">
						<dl class="fmdl fmmr clearfix">
							<dt>投资事业线：</dt>
							<dd>
								<select name="departmentId" id="search_department_id"></select>
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
						<dl class="fmdl fmmr clearfix">
							<dt>项目创建日期：</dt>
							<dd>
								<!-- <input type="text" class="txt time sdate" id="" value=""  />
	                  	<input type="hidden" id="handle"/>
	                  	<input type="hidden" id="week_start" />
	                  	<a href="javascript:;;" id="week_start_calendar" data-date="2016-03-12">20160312<b class="caret"></b></a> -->
	
								<input type="text" class="txt time datepicker" id="search_start_time" name="startTime" readonly value="" /> 
								<span>至</span> 
								<input type="text" class="txt time datepicker" id="search_end_time" name="endTime" readonly value="" />
							</dd>
							<dd>
								<input type="hidden" name="projectProgress" id="search_project_progress" value="all"/>
								<a href="javascript:;" class="bluebtn ico tj" id="search_btn">统计</a>
							</dd>
						</dl>
					</div>
					</form>
					<!--柱状图部分-->
					<div class="chartbox">
						<div>
							<h2 class="chart_name">项目进度分布图</h2>
						</div>
						<div id="chart_overview"></div>
					</div>
					<!--表格内容-->
					<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
					<table id="grid_overview" width="100%" cellspacing="0"
						cellpadding="0"></table>
				</div>
				
				
				
				
				<!-- 项目数统计 -->
				<div class="tabtable_con" data-tab="con">
					<div class="search_box searchall" id="custom-toolbar-xmstj">
						<input type="hidden" name="model" value="1" />
						<dl class="fmdl fmmr clearfix">
							<dt>项目类型：</dt>
							<dd>
								<select name="projectType" id="xmstj_projectType">
									<option value="-1">全部</option>
									<option value="projectType:2">内部创建</option>
									<option value="projectType:1">外部投资</option>
								</select>
							</dd>
						</dl>
						<dl class="fmdl fmmr clearfix">
							<dt>项目创建日期：</dt>
							<dd>
								<input type="text" class="txt time datepicker" value="" id="xmstj_sdate" name="sdate" /> 
								<span>至</span> 
								<input type="text" class="txt time datepicker" value="" id="xmstj_edate" name="edate" />
							</dd>
							<dd>
								<a href="javascript:;" class="bluebtn ico tj" id="querySearch_xmstj">统计</a>
							</dd>
						</dl>
					</div>
					<!--柱状图部分-->
					<div class="chartbox">
						<div id="container_xmstj" class="chart_m" style＝“min-width:800px;”></div>
					</div>
					
					<div class="middle clearfix">
						<div class="middle_l">
							<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
							<table id="data-table-xmstj" width="100%" cellspacing="0" cellpadding="0">
							</table>
						</div>
						<div class="middle_r">
							<!-- 环形图展示  -->
							<div id="container_xmstj_bt"></div>
						</div>
					</div>
				</div>
				
				
				
				
				
				<!-- 项目数完成增长率统计 -->
				<div class="tabtable_con" data-tab="con">
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
						<div id="container_xmzzl" class="chart chart_m"></div>
					</div>
					<!--表格内容-->
					<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
					<table id="data-table-xmzzl" width="100%" cellspacing="0"
						cellpadding="0">
						<thead>
							<tr>
								<th data-field="biz_date"  class="data-input">时间</th>
								<th data-field="dept_name"  class="data-input">投资事业线</th>
								<th data-field="project_type_name" 
									class="data-input">项目类型</th>
								<th data-field="real_name"  class="data-input">投资经理</th>
								<th data-field="completed"  class="data-input">项目数</th>
								<th data-field="rise_rate"  class="data-input"
									data-formatter="rate_format">环比</th>
							</tr>
						</thead>
					</table>
				</div>
				
				
				
				
				
				<!-- 过会率统计 -->
				<div class="tabtable_con" data-tab="con">
					<div class="search_box searchall" id="custom-toolbar-ghl">
						<dl class="fmdl fmmr clearfix">
							<dt>会议时间：</dt>
							<dd>
								<input type="text" id="ghl_sdate" name="sdate"
									class="txt time datepicker" value="" /> <span>至</span> <input
									type="text" id="ghl_edate" name="edate"
									class="txt time datepicker" value="" />
							</dd>
							<dd>
								<input type="hidden" name="meetingType" value="meetingType:3" /> 
								<a href="javascript:;" class="bluebtn ico tj" id="querySearch_ghl">统计</a>
							</dd>
						</dl>
					</div>
					<!--柱状图部分-->
					<div class="chartbox">
						<h2 class="chart_name">过会率TOP10</h2>
						<div id="container_ghl"></div>
					</div>
					<!--表格内容-->
					<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
					<table id="data-table-ghl" width="100%" cellspacing="0" cellpadding="0" class="table_m">
					</table>
				</div>
				
				<!-- 投决率统计 -->
				<div class="tabtable_con" data-tab="con">
					<div class="search_box searchall" id="custom-toolbar-tjl">
						<dl class="fmdl fmmr clearfix">
							<dt>会议时间：</dt>
							<dd>
								<input type="text" id="tjl_sdate" name="sdate" class="txt time datepicker" value="" /> 
								<span>至</span> 
								<input type="text" id="tjl_edate" name="edate" class="txt time datepicker" value="" />
							</dd>
							<dd>
								<input type="hidden" name="meetingType" value="meetingType:4" /> 
								<a href="javascript:;" class="bluebtn ico tj" id="querySearch_tjl">统计</a>
							</dd>
						</dl>
					</div>
					<!--柱状图部分-->
					<div class="chartbox">
						<h2 class="chart_name">投决率TOP10</h2>
						<div id="container_tjl"></div>
					</div>
					<!--表格内容-->
					<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
					<table id="data-table-tjl" width="100%" cellspacing="0" cellpadding="0" class="table_m">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>

</body>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datePicker-handler-init.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-selecter.js"></script>
<script type="text/javascript">
// 	function getforwardProgress(){
// 		return ${forwardProgress};
// 	}
// 	var forwardProgress = getforwardProgress();
	
	var utils = {
		each : function(_data,_dom,type){
			_dom.empty();
			if(type=="all"){
				_dom.append("<option value='all'>全部</option>");
			}
			$.each(_data.entityList,function(){
				if(this.code){
					_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
				}else{
					_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
				}
				
			});
		},
		confident : function(value,tem){
			if(value==tem){
				return;
			}else{
				return value;
			}
		}
		
}
	
	
	
</script>
<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=path%>/js/charts/tabAnalysisOverview.js"></script>
<script src="<%=path%>/js/charts/tabAnalysisRiseRate.js"></script>
<script src="<%=path%>/js/charts/projectAnalysis.js"></script>
</html>







