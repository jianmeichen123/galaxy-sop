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
				<li><a href="javascript:;" >项目总览</a></li>
				<li><a href="javascript:;" onclick="toproNum()">项目数统计</a></li>
				<li><a href="javascript:;" >项目完成增长率统计</a></li>
				<li class="on"><a href="javascript:;" onclick="toGhlSum()">过会率统计</a></li>
				<li><a href="javascript:;" onclick="toTjlSum()">投决率统计</a></li>
			</ul>
			
			
			 <!-- 过会率统计 -->
			 <div class="tabtable_con" >
				<div class="search_box searchall" id="custom-toolbar-ghl">
					<dl class="fmdl fmmr clearfix">
						<dt>会议时间：</dt>
						<dd>
							<input type="text" id="ghl_sdate" name="sdate" class="txt time datepicker" value="" /> 
							<span>至</span> 
							<input type="text" id="ghl_edate" name="edate" class="txt time datepicker" value="" />
						</dd>
						<dd>
							<input type="hidden" name="meetingType" value="meetingType:3" /> 
							<a href="javascript:;" class="bluebtn ico tj" action="querySearch">统计</a>
						</dd>
					</dl>
				</div>
				
				<!--柱状图部分-->
				<div class="chartbox">
					<h2 class="chart_name">过会率TOP10</h2>
					<div id="container_ghl"></div>
				</div>
				
				
				<!--表格内容-->
				<table id="data-table-ghl"  data-toolbar="#custom-toolbar-ghl"
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



<script>

function toproNum(){
	forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/paprojectlist");
}

function toGhlSum(){
	forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toGhlSum");
} 

function toTjlSum(){
	forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toTjlSum");
} 


var isGG = true;
if(roleId == '1' || roleId == 1 || roleId == '2' || roleId == 2){
	isGG = true;
}else{
	isGG = false;
}

var pageNum = 1;
var queryParamsJson = {};

var url = platformUrl.meetingrate;
if(!isGG) url = platformUrl.meetingRateUser;


$(function () {
	//左侧菜单
	createMenus(8);
	
	init(); //页面初始化
	
	//绑定querySearch事件
	$('#data-table-ghl').bootstrapTable({
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
        	
        	if(!isGG) $('#data-table-ghl').bootstrapTable('showColumn', 'realName');
        	var options = $('#data-table-ghl').bootstrapTable('getOptions');
        	var data = options.data;
        	pageNum = options.pageNumber;
        	if(pageNum == 1){
        		var re = [];
        		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i==10){
        				break;
        			}
        			re.push(data[i].rate*100);
        			categories.push(isGG ? data[i].departmentName : data[i].realName);
        		}
        		containerGhOptions.series[0].data = re;
        		containerGhOptions.xAxis.categories = categories;
        		var chart = new Highcharts.Chart(containerGhOptions);
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
	 


 
/****************************************************************************
 * 过会率统计弹出层
 ***************************************************************************/
 function cat_ghl(value, row, index){
	var id= row.userId;
	if(value=='undefined' || value==0 || !value){
		return "0%";
	}else{
		var userid = row.userId;
		var deptid = row.departmentId;
		value = value * 100;
		var options = "<a href='#' onclick='ghlprojectList("+userid+","+deptid+")' class='blue'>"+value.toFixed(2)+"%</a>";
		return options;	
	}
}
 function ghlprojectList(userid,deptid){
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
					url: platformUrl.meetRateProjectlist,
					queryParams:function(){
						return queryParamsJson;
					},
					onLoadSuccess : function(result) {
						//console.log(result)
					}
				});
			}
		});
	 /* 
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
	}); */
	return false;
}


/**************************************************************************
 * 图表配置项
 **************************************************************************/
//过会率－－图表配置项
 var containerGhOptions = {
 	chart: {
 		renderTo:'container_ghl',
         type: 'column',
         margin: [ 50, 50, 100, 80],
         height :340
         //width:1200
     },
     title: {
     	text:''
         //text: '过会率TOP10'
     },
     //去除版权
     credits: {
         enabled:false
     },
     //去除右上角导出图标
     exporting: {
     	enabled:false
     },
     plotOptions: {
         column: {
         	pointPadding: 0.2,
             borderWidth: 0,
             pointWidth: 20
         }
     },
     xAxis: {
     	lineWidth: 1,
         lineColor: "#e9ebf2",
         tickWidth:'0',
     	categories:{},
         labels: {
             rotation: 0,
             align: 'center',
             style: {
                 fontSize: '12px',
                 fontFamily: '宋体'
             }
         }
     },
     yAxis: {
     	gridLineColor: '#e9ebf2',
         gridLineWidth: 1,
         min: 0,
         title: {
             text: '过会率 (%)'
         }
     },
     legend: {
         enabled: false
     },
     tooltip: {
     	formatter:function(){
     		return '过会率: ' + this.point.y.toFixed(2) +"%";
         }
     },
     series: [{
         name: 'Population',
         color:'#587edd',
         //data: [9,8,15,4,23,3,2,30,20],
         dataLabels: {
             enabled: true,
             rotation: 0,
             color: '#6b799f',
             align: 'center',
             x: 0,
             y: 0,
             style: {
                 fontSize: '12px',
                 fontFamily: 'Verdana, sans-serif',
                 textShadow: '0 0 0px #fff',
                 fontWeight:'normal',
             },
             formatter:function(){
             	return this.point.y.toFixed(2) +"%";
             },
         }
     }]
 };
	
</script>
</html>

