<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="common/header_report.jsp" flush="true"></jsp:include>

<!--右中部内容-->
<div class="pagebox clearfix">
	<jsp:include page="common/menu.jsp" flush="true"></jsp:include>
	<div class="ritmin">
		<h2>绩效考核</h2>
		<div class="tabtable assessment">
			<!-- tab标签 -->
			<ul class="tablink">
				<li data-tab="nav"><a href="javascript:;">个人绩效考核</a></li>
			</ul>
			<!-- 投资经理绩效考核部分 -->
			<div class="tabtable_con" data-tab="con">
				<div class="search_box searchall" id="custom-toolbasr-userkpi">
					<dl class="fmdl fmmr clearfix">
						<dt>投资事业线：</dt>
						<dd>
							<select name="deptid" id="userkpi_deptid"></select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="userkpi_projectType">
								<option value="-1">全部</option>
								<option value="projectType:2">内部创建</option>
								<option value="projectType:1">外部投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建时间：</dt>
						<dd>
							<input type="text" class="txt time datepicker" name="sdate"
								id="userkpi_sdate" value="" /> <span>至</span> <input
								type="text" class="txt time datepicker" name="edate"
								id="userkpi_edate" value="" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj"
								id="querySearch_userkpi">统计</a>
						</dd>
					</dl>
				</div>
				<!--柱状图部分-->
				<div class="chartbox">
					<div id="container_userkpi"></div>
				</div>
				<!--表格内容-->
				<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
				<table id="data-table-userkpi" width="100%" cellspacing="0"
					cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="real_name"  class="data-input">姓名</th>
							<th data-field="dept_name"  class="data-input">投资事业线</th>
							<th data-field="target"  class="data-input">目标数</th>
							<th data-field="completed"  class="data-input">项目数</th>
							<th data-field="completed_all"  class="data-input">累计已完成数</th>
							<th data-field="company_rank"  class="data-input" data-formatte="com_ranking">公司排名</th>
							<!-- <th data-field="dept_rank"  class="data-input">部门排名</th> -->
							<th data-field="total_rate"  class="data-input" data-formatter="rate_format">公司完成数占比</th>
							<th data-field="dept_rate"  class="data-input" data-formatter="rate_format">部门完成数占比</th>
							<th data-field="lxh_pnumber"  class="data-input">立项会通过数</th>
							<th data-field="tjh_pnumber"  class="data-input">投资决策会通过数</th>
							<th data-field="ghl_rate"  class="data-input" data-formatter="rate_format">过会率</th>
							<th data-field="tjl_rate"  class="data-input" data-formatter="rate_format">投决率</th>
						</tr>
					</thead>
				</table>
			</div>
			<!-- 团队绩效考核部分 -->
			<div class="tabtable_con" data-tab="con">
				<div class="search_box searchall" id="custom-toolbasr-deptkpi">
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="deptkpi_projectType">
								<option value="-1">全部</option>
								<option value="projectType:2">内部创建</option>
								<option value="projectType:1">外部投资</option>
							</select>
						</dd>
					</dl>
					<dl class="fmdl fmmr clearfix">
						<dt>项目创建时间：</dt>
						<dd>
							<input type="text" class="txt time datepicker" id="deptkpi_sdate"
								name="sdate" value="" /> <span>至</span> <input type="text"
								class="txt time datepicker" id="deptkpi_edate" name="edate"
								value="" />
						</dd>
						<dd>
							<a href="javascript:;" class="bluebtn ico tj"
								id="querySearch_deptkpi">统计</a>
						</dd>
					</dl>
				</div>
				<!--柱状图部分-->
				<div class="chartbox">
					<div id="container_deptkpi"></div>
				</div>
				<!--表格内容-->
				<!-- <a href="javascript:;" class="pubbtn bluebtn export">导出</a> -->
				<table id="data-table-deptkpi" width="100%" cellspacing="0"
					cellpadding="0" class="table_m">
					<thead>
						<tr>
							<th data-field="hhr"  class="data-input">合伙人</th>
							<th data-field="dept_name"  class="data-input">投资事业线</th>
							<th data-field="dept_target"  class="data-input">目标数</th>
							<th data-field="completed"  class="data-input" data-formatter="cat_deptkpi">项目数</th>
							<th data-field="completed_all"  class="data-input">累计已完成数</th>
							<th data-field="company_rank"  class="data-input">公司排名</th>
							<th data-field="zj_rate"  class="data-input" data-formatter="rate_format">内部创建项目占比</th>
							<th data-field="total_rate"  class="data-input" data-formatter="rate_format">公司完成数占比</th>
							<th data-field="lxh_pnumber"  class="data-input">立项会通过数</th>
							<th data-field="tjh_pnumber"  class="data-input">投资决策会通过数</th>
							<th data-field="ghl_rate"  class="data-input" data-formatter="rate_format">过会率</th>
							<th data-field="tjl_rate"  class="data-input" data-formatter="rate_format">投决率</th>
						</tr>
					</thead>
				</table>
			</div>

		</div>
	</div>
</div>
<jsp:include page="common/footer.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath()%>/js/report/kpi.js"
	type="text/javascript"></script>
<script>
	/****************************************************************************
	 * 团队绩效弹出层
	 ***************************************************************************/
	 if(!(isHHR == true ||isHHR == 'true')) {
		 $(".tablink").append('<li data-tab="nav" id="gg_jxkh_u"><a href="javascript:;">团队绩效考核</a></li>');
	 }
	 function cat_deptkpi(value, row, index) {
		var id = row.dept_id;
		var options = "<a href='#' onclick='deptkpiprojectList(" + id + ")' class='blue'>"
				+ value + "</a>";
		return options;
	}
	function deptkpiprojectList(id) {
		var _url = path + '/galaxy/report/deptkpiprojectlist';
		$.getHtml({
			url : _url,//模版请求地址	
			data : "",//传递参数
			okback : function() {
				$("#deptkpi_projectlist_projectType").val(
				$("#deptkpi_projectType").val()); //项目类型
				$("#deptkpi_projectlist_deptid").val(id); //部门
				$("#deptkpi_projectlist_sdate").val($("#deptkpi_sdate").val()); //项目创建日期
				$("#deptkpi_projectlist_edate").val($("#deptkpi_edate").val()); //项目创建日期
				//console.log("给hidden赋值");
				$('#data-table-deptkpi-projectlist').bootstrapTable({
					queryParamsType : 'size|page', // undefined
					pageSize : 10,
					showRefresh : false,
					sidePagination : 'server',
					method : 'post',
					pagination : true,
					search : false,
					//dataType: 'html',
					onLoadSuccess : function(result) {
						//console.log(result)
					}
				});
			}
		});
		return false;
	}
	
    $(document).ready(function(){
    	if(isHHR!='true'){
    		var url=document.location.href;
            //console.log(basePath_noport);
            //console.log(url);
            //console.log(basePath_noport + "/galaxy/report/kpi?guid="+userId+"&sid="+sessionId)
            if(url==basePath_noport + "/report/galaxy/report/kpi?guid="+userId+"&sid="+sessionId){
                $(".assessment").tabchange3();
                //默认加载项目总览数据
                load_data_chart_deptkpi();
                load_data_deptkpi();
            } ;
    	}
      })
      //团队绩效考核
   	 function load_data_deptkpi(){
   			var obj = {url: platformUrl.deptkpi,toolbar:'#custom-toolbasr-deptkpi'};
   			$('#data-table-deptkpi').bootstrapTable('destroy');
   			$('#data-table-deptkpi').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
   		}
      function load_data_chart_deptkpi(){
    		   	var deptkpi_sdate = $("#deptkpi_sdate").val();
    		   	var deptkpi_edate = $("#deptkpi_edate").val();
    		   	var deptkpi_projectType = $("#deptkpi_projectType").val();
    		   	//加载图表
    		   	var obj ={url:platformUrl.deptkpi};
    		   	obj.contentType="application/json";
    		   	//obj.data = getToobarQueryParams('custom-toolbasr-deptkpi');
    		   	ajaxCallback(obj,function(data){
    		   		var result = data.result;
    		   		if(result.status=='ERROR'){
    		   			$.popup(100,'消息',result.message);
    		   			return false;
    		   		}
    		   		var entityList = data.pageList.content;
    		   		var re = [];
    		   		var categories = [];
    		   		for(var i=0;i<entityList.length;i++){
    		   			re.push(entityList[i].completed);
    		   			categories.push(entityList[i].dept_name);
    		   		}
    		   		containerDeptKpiOptions.series[0].data = re;
    		   		containerDeptKpiOptions.xAxis.categories = categories;
    		   		var chart = new Highcharts.Chart(containerDeptKpiOptions);
    		   	});
    		}
</script>