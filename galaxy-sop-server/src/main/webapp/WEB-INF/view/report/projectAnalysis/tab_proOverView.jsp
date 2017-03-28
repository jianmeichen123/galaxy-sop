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
<title>星河投</title>
	
	<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
	<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
	<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	
	<!-- bootstrap-table -->
	<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
	<!-- 日历插件 -->
	<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
	
	<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>

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
				<li data-tab="nav"><a href="javascript:;">项目总览</a></li>
				<li data-tab="nav"><a href="javascript:;">项目数统计</a></li>
				<li data-tab="nav"><a href="javascript:;">项目完成增长率统计</a></li>
				<li data-tab="nav"><a href="javascript:;">过会率统计</a></li>
				<li data-tab="nav"><a href="javascript:;">投决率统计</a></li>
			</ul>
			
			
			<!-- 项目总揽部分 -->
			<div class="tabtable_con tabtable_con_overview" data-tab="con">
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
								<option value="projectType:2">创建</option>
								<option value="projectType:1">投资</option>
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
			<div class="tabtable_con"  data-tab="con">
				<div class="search_box searchall" id="custom-toolbar-xmstj">
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="xmstj_projectType">
								<option value="">全部</option>
								<option value="projectType:2">创建</option>
								<option value="projectType:1">投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建日期：</dt>
						<dd>
							<input type="text" class="datepicker txt time" readonly value="" id="xmstj_sdate" name="sdate" />
							<span>至</span> 
							<input type="text" class="datepicker txt time" readonly value="" id="xmstj_edate" name="edate" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj" id="querySearch_xmstj" >统计</a>
						</dd>
					</dl>
				</div>
				
				<!--柱状图部分-->
				<div class="chartbox">
					<div id="container_xmstj" class="chart_m" style="min-width:800px;"></div>
				</div>
				
				
				<div class="middle clearfix">
					<div class="middle_l">
						<table id="data-table-xmstj" 
							width="100%" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th data-field="realName"  		 class="data-input" data-visible="false">	投资经理</th> 
									<th data-field="departmentName"  class="data-input">	投资事业线	</th>
									<th data-field="rate"  		 	 class="data-input" data-formatter="rate_format">完成率</th>
									<th data-field="target"  		 class="data-input" >目标数</th>
									<th data-field="completed"  	 class="data-input" data-formatter="pro_num_format">	项目数		</th>
									<th data-field="notCompleted"  	 class="data-input">	未完成		</th>
								</tr>
							</thead>
							
						</table>
					</div>
					
					<div class="middle_r">
						<!-- 环形图展示  -->
						<div id="container_xmstj_bt"></div>
					</div>
				</div>
				
			</div>
			
			
			
			
			<!-- 项目数完成增长率统计 -->
			<div class="tabtable_con"  data-tab="con">
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
									<option value="projectType:2">创建</option>
									<option value="projectType:1">投资</option>
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
			
			
			
			
			 <!-- 过会率统计 -->
			 <div class="tabtable_con"   data-tab="con">
				<div class="search_box searchall" id="custom-toolbar-ghl">
					<dl class="fmdl fmmr clearfix">
						<dt>会议时间：</dt>
						<dd>
							<input type="text" id="ghl_sdate" name="sdate" class="datepicker txt time" value="" /> 
							<span>至</span> 
							<input type="text" id="ghl_edate" name="edate" class="datepicker txt time" value="" />
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
				<table id="data-table-ghl"
					width="100%" cellspacing="0" cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="realName"  		 class="data-input" data-visible="false"> 投资经理</th> 
							<th data-field="departmentName"  class="data-input"> 投资事业线	</th>
							<th data-field="rate"  		 	 class="data-input" data-formatter="cat_ghl">过会率</th>
							<th data-field="proMeetNum"  	 class="data-input" >过会项目数</th>
							<th data-field="passMeetProNumber"  	 class="data-input" >通过数</th>
							<th data-field="vetoMeetProNumber"  	 class="data-input" >失败数</th>
							<th data-field="waitMeetProNumber"  	 class="data-input" >待定数</th>
						</tr>
					</thead>
				</table>
			</div>
			
			
			
			<!-- 投决率统计 -->
			 <div class="tabtable_con"   data-tab="con">
				<div class="search_box searchall" id="custom-toolbar-tjl">
					<dl class="fmdl fmmr clearfix">
						<dt>会议时间：</dt>
						<dd>
							<input type="text" id="ghl_sdate" name="sdate" class="datepicker txt time" value="" /> 
							<span>至</span> 
							<input type="text" id="ghl_edate" name="edate" class="datepicker txt time" value="" />
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
				<table id="data-table-tjl" 
					width="100%" cellspacing="0" cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="realName"  		 class="data-input" data-visible="false"> 投资经理</th> 
							<th data-field="departmentName"  class="data-input"> 投资事业线	</th>
							<th data-field="rate"  		 	 class="data-input" data-formatter="cat_tjl">过会率</th>
							<th data-field="proMeetNum"  	 class="data-input" >过会项目数</th>
							<th data-field="passMeetProNumber"  	 class="data-input" >通过数</th>
							<th data-field="vetoMeetProNumber"  	 class="data-input" >失败数</th>
							<th data-field="waitMeetProNumber"  	 class="data-input" >待定数</th>
						</tr>
					</thead>
				</table>
			</div>
			
			
			
		</div>
	</div>
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


</body>
<script type="text/javascript">
	function getProjectProgress(){
		return ${projectProgress};
	}
</script>
<script src="<%=path %>/js/split.js" type="text/javascript"></script>
<script src="<%=path %>/js/cookie.js" type="text/javascript"></script>
<!-- table分页 -->
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>
    
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-selecter.js"></script>

<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datePicker-handler-init.js"></script>

<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>

<script src="<%=path%>/js/charts/projectAnalysis.js"></script>
<script src="<%=path%>/js/charts/tabAnalysisOverview.js"></script>
<script src="<%=path%>/js/charts/tabProNum.js"></script>
<script src="<%=path%>/js/charts/tabAnalysisRiseRate.js"></script>
<script src="<%=path%>/js/charts/tabProGhl.js"></script>
<script src="<%=path%>/js/charts/tabProTjl.js"></script>

</html>

