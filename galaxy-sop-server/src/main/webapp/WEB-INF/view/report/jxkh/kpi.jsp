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
<title>绩效考核</title>

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
		<h2>绩效考核</h2>

		<div class="tabtable assessment">
            <ul class="tablink tablinks">
            	<li data-tab="nav"><a href="javascript:;">个人绩效考核</a></li>
            	<li data-tab="nav" id="team_kpi_li" style="display:none;"><a href="javascript:;">团队绩效考核</a></li>
            	<li data-tab="nav"><a href="javascript:;">合伙人日常业务绩效评分表</a></li>
            	
            </ul>

			<!-- 个人绩效考核   高管、合伙人都可以查看  -->
			<div class="tabtable_con" data-tab="con">
				<div class="search_box searchall" id="custom-toolbasr-userkpi">
					<dl class="fmdl fmmr clearfix">
						<dt>投资事业线：</dt>
						<dd>
							<select name="deptid" id="userkpi_deptid">
								<option value="">全部</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="userkpi_projectType">
								<option value="">全部</option>
								<option value="projectType:2">内部创建</option>
								<option value="projectType:1">外部投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建时间：</dt>
						<dd>
							<input type="text" class="txt time datepicker" name="sdate" id="userkpi_sdate" value="" /> 
							<span>至</span> 
							<input type="text" class="txt time datepicker" name="edate" id="userkpi_edate" value="" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj" id="querySearch_perkpi">统计</a>
						</dd>
					</dl>
				</div>
	
				<!--柱状图部分-->
				<div class="chartbox">
					<div id="container_userkpi"></div>
				</div>
				
				<!--表格内容-->
				<table id="data-table-userkpi"
					width="100%" cellspacing="0" cellpadding="0" class="table_m" >
					<thead>
						<tr>
							<th data-field=realName  		 class="data-input">	姓名			</th>
							<th data-field="departmentName"  class="data-input">	投资事业线	</th>
							<th data-field="target"  		 class="data-input">	目标数		</th>
							<th data-field="completed"  	 class="data-input">	项目数		</th>
							<th data-field="completedAll"    class="data-input">	累计已完成数	</th>
							<th data-field="companyRank"     class="data-input">	公司排名		</th>   <!--  data-formatte="com_ranking" -->
							<!-- <th data-field="deptRank"  		 class="data-input">	部门排名		</th> -->
							<th data-field="totalRate"  	 class="data-input" data-formatter="rate_format">公司完成数占比</th>
							<th data-field="deptRate" 	     class="data-input" data-formatter="rate_format">部门完成数占比</th>
							<th data-field="lxhPnumber"  	 class="data-input">	立项会通过数	</th>
							<th data-field="tjhPnumber"  	 class="data-input">	投资决策会通过数</th>
							<th data-field="ghlRate"  		 class="data-input" data-formatter="rate_format">过会率</th>
							<th data-field="tjlRate"  		 class="data-input" data-formatter="rate_format">投决率</th>
						</tr>
					</thead>
				</table>
			</div>
			
			
			<!-- 部门绩效考核   高管可以查看  -->
			<div class="tabtable_con" data-tab="con">
				<div class="search_box searchall" id="custom-toolbasr-deptkpi">
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="deptkpi_projectType">
								<option value="">全部</option>
								<option value="projectType:2">内部创建</option>
								<option value="projectType:1">外部投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建时间：</dt>
						<dd>
							<input type="text" class="txt time datepicker" id="deptkpi_sdate" name="sdate" value="" /> 
							<span>至</span> 
							<input type="text" class="txt time datepicker" id="deptkpi_edate" name="edate" value="" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj" id="querySearch_teamkpi">统计</a>  <!-- id="querySearch_deptkpi" -->
						</dd>
					</dl>
				</div>
	
				<!--柱状图部分-->
				<div class="chartbox">
					<div id="container_deptkpi"></div>
				</div>
				
				<!--表格内容-->
				<table id="data-table-deptkpi"
					width="100%" cellspacing="0" cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="realName"  			class="data-input">合伙人</th>
							<th data-field="departmentName"  	class="data-input">投资事业线</th>
							<th data-field="target"  			class="data-input">目标数</th>
							<th data-field="completed"  		class="data-input" data-formatter="cat_deptkpi">项目数</th>
							<th data-field="completedAll"  		class="data-input">累计已完成数</th>
							<th data-field="companyRank"  		class="data-input">公司排名</th>
							<th data-field="zjRate"  		class="data-input" data-formatter="rate_format">内部创建项目占比</th>
							<th data-field="totalRate"  	class="data-input" data-formatter="rate_format">公司完成数占比</th>
							<th data-field="lxhPnumber"  	class="data-input">立项会通过数</th>
							<th data-field="tjhPnumber"  	class="data-input">投资决策会通过数</th>
							<th data-field="ghlRate"  		class="data-input" data-formatter="rate_format">过会率</th>
							<th data-field="tjlRate"  		class="data-input" data-formatter="rate_format">投决率</th>
						</tr>
					</thead>
				</table>
			</div>
			
			<!-- 合伙人日常绩效考核   高管可以查看  -->
			<div class="tabtable_con" data-tab="con">
				<div class="search_box searchall" id="custom-toolbasr-partnerkpi">
					<dl class="fmdl fmmr clearfix">
						<dt>查询时间：</dt>
						<dd>
							<input id="week" type="radio" name="week" value="" checked/>周报
							<input id="defined" type="radio" name="week" value=""/>自定义
						</dd>
						
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dd id="weekType">
							<input type="text" class="txt time weekStartDatepicker" id="partnerkpi_sdate" name="parter_sdate" value="" /> 
							<span>至</span> 
							<input type="text" class="txt time weekEndDatepicker" id="partnerkpi_edate" name="parter_edate" value="" />
						</dd>
						
						<dd id="definedType" style="display:none">
							<input type="text" class="txt time datepicker" name="sdate" id="partnerkpi_sdate" value="" /> 
							<span>至</span> 
							<input type="text" class="txt time datepicker" name="edate" id="partnerkpi_edate" value="" />
						</dd>
						
						<dd>
							<a href="javascript:;" class="bluebtn ico tj" id="querySearch_teamkpi">查询</a>  <!-- id="querySearch_deptkpi" -->
						</dd>
					</dl>
				</div>
				<div>
				    <a href="javascript:;" class="bluebtn ico tj" id="querySearch_teamkpi">导出</a>
				</div>
				<!--表格内容-->
				<table id="data-table-partnerkpi"
					width="100%" cellspacing="0" cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="departmentName"  	class="data-input">投资事业线</th>
							<th data-field="target"  			class="data-input">分数/生成项目 </th>
							<th data-field="completed"  		class="data-input" data-formatter="cat_deptkpi">分数/通过CEO评审</th>
							<th data-field="completedAll"  		class="data-input">分数/通过立项会</th>
							<th data-field="companyRank"  		class="data-input">总分数</th>
							<th data-field="zjRate"  		class="data-input" data-formatter="rate_format">过会率/CEO评审会</th>
						</tr>
					</thead>
				</table>
			</div>
		
		
		</div>
        
    </div>
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


<script src="<%=request.getContextPath() %>/js/cookie.js" type="text/javascript"></script>
<!-- table分页 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- highcharts -->
<script src="<%=request.getContextPath() %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts_ext.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForWeek.js"></script>

<!-- kpi js -->
<script src="<%=path%>/js/kpi/tab_partnerkpi.js"></script>
<script src="<%=path%>/js/kpi/tab_perkpi.js"></script>
<script src="<%=path%>/js/kpi/tab_teamkpi.js"></script>
<script src="<%=path%>/js/kpi/kpi_tag.js"></script>

<script>

//周报|自定义选择切换
$("#week").on('click',function(){
	$("#weekType").show();
	$("#definedType").hide();
});

$("#defined").on('click',function(){
	$("#weekType").hide();
	$("#definedType").show();
});

	
</script>
</html>

