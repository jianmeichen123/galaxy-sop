<%@ page language="java" pageEncoding="UTF-8"
import="com.galaxyinternet.framework.core.oss.OSSConstant"
%>
<% 
String path = request.getContextPath();
String endpoint = (String)application.getAttribute(OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
String reportEndpoint = map.get("galaxy.project.report.endpoint");
%>
<jsp:include page="common/header_report.jsp" flush="true"></jsp:include>
<!--右中部内容-->
<div class="pagebox clearfix">
	<jsp:include page="common/menu.jsp" flush="true"></jsp:include>
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
				<div class="search_box searchall" id="custom-toolbasr-xmzl">
					<dl class="fmdl fmmr clearfix">
						<dt>投资事业线：</dt>
						<dd>
							<select name="deptid" id="xmzl_deptid"></select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="xmzl_projectType">
								<option value="-1">全部</option>
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

							<input type="text" class="txt time datepicker" id="xmzl_sdate" name="sdate" readonly value="" /> 
							<span>至</span> 
							<input type="text" class="txt time datepicker" id="xmzl_edate" name="edate" readonly value="" />
						</dd>
						<dd>
							<input type="hidden" name="projectProgress" id="xmstj_projectProgress" value="-1"/>
							<a href="javascript:;" class="bluebtn ico tj" id="querySearch_xmzl">统计</a>
						</dd>
					</dl>
				</div>
				<!--柱状图部分-->
				<div class="chartbox">
					<div>
						<h2 class="chart_name">项目进度分布图</h2>
					</div>
					<div id="histogram"></div>
				</div>
				<!--表格内容-->
				<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
				<table id="data-table-xmzl" width="100%" cellspacing="0"
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
				<div class="search_box searchall" id="custom-toolbar-xmzzl">
					<dl class="fmdl fmmr clearfix">
						<dt>投资事业线：</dt>
						<dd>
							<select name="deptid" id="xmzzl_deptid"></select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="xmzzl_projectType">
								<option value="-1">全部</option>
								<option value="projectType:2">内部创建</option>
								<option value="projectType:1">外部投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建日期：</dt>
						<dd>
							<input type="radio" name="radio_xmzzl" value="1"
								checked="checked" />日 <input type="radio" name="radio_xmzzl"
								value="3" />月
						</dd>
						<dd id="xmzzl_dd_day">
							<input type="text" class="txt time datepicker" id="xmzzl_sdate"
								name="sdate" value="" /> <span>至</span> <input type="text"
								class="txt time datepicker" id="xmzzl_edate" name="edate"
								value="" />
						</dd>
						<dd id="xmzzl_dd_month" style="display: none;">
							<input type="text" class="txt time change_month" id="xmzzl_sym"
								name="sym" value="" /> <span>至</span> <input type="text"
								class="txt time change_month" id="xmzzl_eym" name="eym" value="" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj"
								id="querySearch_xmzzl">统计</a>
						</dd>
					</dl>
				</div>
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


<jsp:include page="common/footer.jsp" flush="true"></jsp:include>

<script type="text/javascript">
	function getforwardProgress(){
		return ${forwardProgress};
	}
	var forwardProgress = getforwardProgress();
</script>
<!-- datetimepicker --> 
<script src="<%=request.getContextPath() %>/js/report/projectAnalysis.js" type="text/javascript"></script>
<%-- <script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script> --%>
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=request.getContextPath() %>/ueditor/lang/zh-cn/zh-cn.js"></script>

<script>
$(function(){
	/* $("[data-toggle='tooltip']").tooltip();
	console.log('start');
	$("[data-toggle='tooltip']").on('click',function(){
     	console.log('click');
     });
	
	console.log('end'); */

	//console.log($("[data-toggle='tooltip']"));
	//console.log($("[data-toggle='tooltip']").attr('data-toggle'));
});
</script>
<script>
/****************************************************************************
 * 项目数统计弹出层
 ***************************************************************************/
function cat_xmstj(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return 0;
	} else{
		var id=isHHR=='true' ? row.user_id : row.department_id;
		var options = "<a href='#' onclick='xmstjprojectList(" + id + ")' class='blue'>"+value+"</a>";
		return options;
	}
}
function xmstjprojectList(id){
	var _url= path + '/galaxy/report/paprojectlist';
	$.getHtml({
		url:_url,//模版请求地址	
		data:"",//传递参数
		okback:function(){
			$("#xmstj_projectlist_sdate").val( $("#xmstj_sdate").val() );
 			$("#xmstj_projectlist_edate").val( $("#xmstj_edate").val() );
 			$("#xmstj_projectlist_projectType").val( $("#xmstj_projectType").val() );
			isHHR == 'true' ? $("#xmstj_projectlist_userid").val(id) : $("#xmstj_projectlist_deptid").val(id);
			var obj = {url: platformUrl.projectlist,toolbar:'#custom-toolbasr_xmstj_projectlist'}
			$('#data-table-xmstj-projectlist').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
		}
	});
	return false;
}





/****************************************************************************
 * 过会率统计弹出层
 ***************************************************************************/
 function cat_ghl(value, row, index){
	var id= isHHR=='true' ? row.user_id : row.dept_id;
	if(value=='undefined' || value==0 || !value){
		return "0%";
	}else{
		value = value * 100;
		var options = "<a href='#' onclick='ghlprojectList(" + id + ")' class='blue'>"+value.toFixed(2)+"%</a>";
		return options;	
	}
}
 function ghlprojectList(id){
	var _url= path + '/galaxy/report/ghlprojectlist';
	$.getHtml({
		url:_url,//模版请求地址	
		data:"",//传递参数
		okback:function(){
			$("#ghl_projectlist_sdate").val( $("#ghl_sdate").val() );
 			$("#ghl_projectlist_edate").val( $("#ghl_edate").val() );
			isHHR=='true' ? $("#ghl_projectlist_userid").val(id) : $("#ghl_projectlist_deptid").val(id);
			var obj = {url: platformUrl.projectlist,toolbar:'#custom-toolbasr_ghl_projectlist'}
			$('#data-table-ghl-projectlist').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
		}
	});
	return false;
}
 /****************************************************************************
  * 投决率统计弹出层
  ***************************************************************************/
  function cat_tjl(value, row, index){
 	var id= isHHR=='true' ? row.user_id : row.dept_id;
 	if(value=='undefined' || value==0 || !value){
		return "0%";
	}else{
		value = value * 100;
	 	var options = value==0 ? '0%' : "<a href='#' onclick='tjlprojectList(" + id + ")' class='blue'>"+value.toFixed(2)+"%</a>";
	 	return options;
	}
 }
  function tjlprojectList(id){
 	var _url= path + '/galaxy/report/tjlprojectlist';
 	$.getHtml({
 		url:_url,//模版请求地址	
 		data:"",//传递参数
 		okback:function(){
 			$("#tjl_projectlist_sdate").val( $("#tjl_sdate").val() );
 			$("#tjl_projectlist_edate").val( $("#tjl_edate").val() );
 			isHHR=='true' ? $("#tjl_projectlist_userid").val(id) : $("#tjl_projectlist_deptid").val(id);
 			var obj = {url: platformUrl.projectlist,toolbar:'#custom-toolbasr_tjl_projectlist'}
 			$('#data-table-tjl-projectlist').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
 		}
 	});
 	return false;
 }  
  
</script>