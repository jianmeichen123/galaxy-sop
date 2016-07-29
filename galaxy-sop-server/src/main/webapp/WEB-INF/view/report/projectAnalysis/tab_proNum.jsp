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
		<h2>项目分析</h2>
		
		<div class="tabtable project_analysis">
			<!-- tab标签 -->
			<ul class="tablink">
				<li><a href="javascript:;" onclick="showCheckTabs(1)">项目总览</a></li>   
				<li  class="on"><a href="javascript:;" onclick="showCheckTabs(2)">项目数统计</a></li>
				<li><a href="javascript:;" onclick="showCheckTabs(3)">项目完成增长率统计</a></li>
				<li><a href="javascript:;" onclick="showCheckTabs(4)">过会率统计</a></li>
				<li><a href="javascript:;" onclick="showCheckTabs(5)">投决率统计</a></li>
			</ul>
			
			
			
			
			<!-- 项目数统计 -->
			<div class="tabtable_con">
				<div class="search_box searchall" id="custom-toolbar-xmstj">
					<dl class="fmdl fmmr clearfix">
						<dt>项目类型：</dt>
						<dd>
							<select name="projectType" id="xmstj_projectType">
								<option value="">全部</option>
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
							<a href="javascript:;" class="bluebtn ico tj" action="querySearch">统计</a>
						</dd>
					</dl>
				</div>
				
				<!--柱状图部分-->
				<div class="chartbox">
					<div id="container_xmstj" class="chart_m" style="min-width:800px;"></div>
				</div>
				
				
				<div class="middle clearfix">
					<div class="middle_l">
						<table id="data-table-xmstj"  data-toolbar="#custom-toolbar-xmstj"
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
			
		</div>
	</div>
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


<script src="<%=path %>/js/cookie.js" type="text/javascript"></script>
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

<script src="<%=path%>/js/charts/projectAnalysis.js"></script>

<script>


var url = platformUrl.gglinechart;
if(!isGG) url = platformUrl.hhrlinechart;


$(function () {
	//左侧菜单
	createMenus(8);
	
	init(); //页面初始化
	
	//绑定querySearch事件
	$('#data-table-xmstj').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
        url: url,
        onLoadSuccess: function(backdata){
        	queryParamsJson = eval("("+backdata.queryParamsJsonStr+")");
        	
        	if(!isGG) $('#data-table-xmstj').bootstrapTable('showColumn', 'realName');
        	
        	var options = $('#data-table-xmstj').bootstrapTable('getOptions');
        	//var data = $('#data-table-userkpi').bootstrapTable('getData');
        	var data = options.data;
        	var projectType = $("select[name='projectType']").val();
        	pageNum = options.pageNumber;
        	if(pageNum == 1){
        		//加载项目数统计图
        		var zj = [];
        		var tz = [];
        		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i==10){
        				break;
        			}
        			zj.push( data[i].zjCompleted);
        			tz.push( data[i].wbCompleted);
        			categories.push(isGG ? data[i].departmentName : data[i].realName);
        		}
        		containerXmstjOptions.series[0].data = ((projectType==''|| projectType=='projectType:2' || typeof(projectType)=='undefined') ? zj : [] );
        		containerXmstjOptions.series[1].data = ((projectType==''|| projectType=='projectType:1' || typeof(projectType)=='undefined') ? tz : [] );
        		containerXmstjOptions.xAxis.categories = categories;
        		var chart = new Highcharts.Chart(containerXmstjOptions);
        		
        		
        		//加载已完成项目占比（自建、投资）饼图
        		var zj_completed = 0;
        		var wb_completed = 0;
        		var zj_rate = 0;
        		var wb_rate = 0;
        		for(var i=0;i<data.length;i++){
        			if(i==10){
        				break;
        			}
        			zj_completed+=data[i].zjCompleted;
        			wb_completed+=data[i].wbCompleted;
        		}
        		zj_rate = ( projectType=='projectType:2' || projectType=='') ? ( zj_completed/(zj_completed+wb_completed) ) * 100 : 0;
        		wb_rate = ( projectType=='projectType:1' || projectType=='') ? ( wb_completed/(zj_completed+wb_completed) ) * 100 : 0;
        		containerXmstjBtOptions.series[0].data = [ 
        		                                           {name:"外部投资",y:parseFloat(wb_rate.toFixed(2)),num:wb_completed,color:"#6fbdeb"},
        		                                           {name:"内部创建",y:parseFloat(zj_rate.toFixed(2)),num:zj_completed,color:"#529be2"} 
        		                                         ];
        		var chart = new Highcharts.Chart(containerXmstjBtOptions);
        	}
        }
	});
});	


function init(){
	//表单日期初始化
	var currDate = new Date();
	var sdate = currDate.format("yyyy-01-01");
	var edate = currDate.format("yyyy-MM-dd");
	$("input[name='sdate']").val(sdate);
	$("input[name='edate']").val(edate);
}
	 


 
//格式化比率
function rate_format(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return "0%";
	} else if(value=='-'){
		return value;
	}else{
		value = value * 100;
		return value.toFixed(2)+"%";
	}
}

/****************************************************************************
 * 项目数统计弹出层
 ***************************************************************************/
function pro_num_format(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return 0;
	} else{
		var projectType = $("select[name='projectType']").val();
		if(projectType==''){
			value = row.completed;
		} else if(projectType=='projectType:2'){
			value = row.zjCompleted;
		}else{
			value = row.wbCompleted;
		}
		/* 
		var id = row.userId;
		if(!isGG) id = row.departmentId;
		
		var id = isGG?row.departmentId:row.userId;
		*/
		var userid = row.userId;
		var deptid = row.departmentId;
		
		var options = "<a href='#' onclick='xmstjprojectList("+userid+","+deptid+")' class='blue'>"+value+"</a>";
		return options;
	}
	
	
}

function xmstjprojectList(userid,deptid){
	var _url= platformUrl.topronumProjectlist;
	
	$.getHtml({
		url:_url,//模版请求地址	
		data:"",//传递参数
		okback : function() {
			if(isGG){
				queryParamsJson.deptid = deptid;
			}else{
				queryParamsJson.userId = userid;
				queryParamsJson.deptid = deptid;
			}
			
			$('#data-table-xmstj-projectlist').bootstrapTable({
				queryParamsType : 'size|page', // undefined
				pageSize : 10,
				showRefresh : false,
				sidePagination : 'server',
				method : 'post',
				pagination : true,
				search : false,
				url: platformUrl.proNumProjectlist,
				queryParams:function(){
					return queryParamsJson;
				},
				onLoadSuccess : function(result) {
					//console.log(result)
				}
			});
		}
	});
	return false;
}


/**************************************************************************
 * 图表配置项
 **************************************************************************/
//项目数统计--柱状图option
 var containerXmstjOptions={
 	chart: {
 		renderTo:'container_xmstj',
 		type: 'column'
 	},	
 	title: {
         text: '项目数统计TOP10',
         align:'left',
         style:{
             fontSize:'18px',
             fontFamily:'微软雅黑',
             color:'#3e4351',
         },
     },
     xAxis: {
     	    lineWidth: 1,
             lineColor: "#e9ebf2",
             allowDecimals:false, //不显示小数
             tickWidth: 0,
         //categories: ['物联网', '互联网钢铁', '互联网服装', '互联网金融', '互联网工业', '互联网房地产', '大数据云计算', '互联网工农业', '智能设备','o2o及电商']
     },
     //去除版权
     credits: {
         enabled:false
     },
     //去除右上角导出图标
     exporting: {
         enabled:true
     },
     yAxis: {
     	gridLineColor: '#e9ebf2',
         gridLineWidth: 1,
         min: 0,
         allowDecimals:false, //不显示小数
         title: {
             text: '项目 (个)'
         }
     },
        legend: {
             backgroundColor: '#FFFFFF',
             itemStyle:{
                 fontWeight:'normal',
                 color:'#525662',
             },
         }, 
        tooltip: {
         pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>个 <br/>',
         shared: true
     },
         plotOptions: {
             column: {
             	 stacking: 'normal',
                  borderWidth: 0,
                  pointWidth: 20,
             }
         },
         series: [{
         name: '内部创建',
         color:'#4fd7cd',
         //data: [216, 210, 200, 180, 165, 155, 135, 125, 120, 100]
     }, {
         name: '外部投资',
         color:'#95b0c0',
         //data: [106, 106, 100, 90, 88, 66, 55, 44, 33, 22]
     }]
 };
 //项目数统计－－已完成项目占比饼图option
 var containerXmstjBtOptions = {
 	chart: {
 		renderTo:'container_xmstj_bt',
         plotBackgroundColor: null,
         plotBorderWidth: null,
         plotShadow: false,
         height :340,
         width:450
     },
     title: {
         text: '已完成项目占比',
         align:'left',  
         style:{
             fontSize:'18px',
             fontFamily:'微软雅黑',
             color:'#808e9b',
         },
     },
     //去除版权
     credits: {
         enabled:false
     },
     tooltip: {
         pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/>项目数: <b>{point.num}</b>'
     },
     plotOptions: {
         pie: {
             allowPointSelect: true,
             cursor: 'pointer',
             depth: 35,
             dataLabels: {
                 color:'black',
                 rotation: -90,
                 enabled: true,
                 formatter:function(){
                     return this.point.percentage.toFixed(1)+"%";
                     //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                 },
                 connectorWidth:0,
                 connectorPadding:0,
                 distance:-30
             },
             showInLegend: true
         }
     },
     legend: {
     	 layout: 'vertical',
         align: 'right',
         verticalAlign: 'center',
         x: 10,
         y: 100,
         floating: true,
         backgroundColor: '#FFFFFF',
         itemStyle:{
             fontWeight:'normal',
             color:'#525662',
         },
     },
     series: [{
         type: 'pie',
         name: '完成占比',
         /*data: [
             {
                 name:'外部投资',
                 y:5,
                 num:98,
                 color:"#86c664"
             },
             { name:'内部创建', y: 20,num:98,color:"#ff955b"},
         ],*/
         dataLabels: {
             enabled: true,
             rotation: 0,
             color: '#FFFFFF',
             verticalAlign: 'middle',
             distance:-50,
             align: 'center',
             x: 0, y: 0,
             style: {
                 fontSize: '12px',
                 fontFamily: '宋体',
                 textShadow: '0 0 3px black'
             }
         }
     }]
 };

	
</script>
</html>

