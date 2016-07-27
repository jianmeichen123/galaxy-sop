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
<title>数据简报</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>

<!-- highcharts -->
<script src="<%=request.getContextPath() %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts_ext.js" type="text/javascript"></script>
<script>


var isGG = true;
if(roleId == '1' || roleId == 1 || roleId == '2' || roleId == 2){
	isGG = true;
}else{
	isGG = false;
}

var currDate = new Date();
var year = currDate.getFullYear();
var sdate = year + '-01-01';
var edate = year + '-12-31';
var sym = year +'-01';
var eym = year + '-12';

$(function () {
	createMenus(22);
	
});

</script>


</head>


<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
 
 	<div class="ritmin">
		<h2>数据简报</h2>
		<div class="tabtable">
		
			<div class="tabtable_con tabtable_con_brif">
			
				<div class="chartbox">
				
					<!-- 项目总目标完成跟踪 -->
					<div class="chartbox_li">
						<!-- 条形图展示  -->
						<h2 class="chart_name">项目目标追踪</h2>
						<div id="chartBrief1" class="chart" style="min-width:800px;"></div>
						<div class="statistic">
							<ul>
								<li>本年度目标项目数:<span id="target"></span></li>
								<li>截至目前完成数:<span id="completed"></span></li>
								<li>截至目前未完成/超额完成数:<span id="notcompleted"></span></li>
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




					<!-- 投资事业线目标完成对比  -->
					<div class="center top clearfix">
						<h2 class="chart_name">投资事业线目标完成对比</h2>
						<div id="chartBrief3"  style="min-width:800px;"></div>
					</div>
					
					<script type="text/javascript">
					// 投资事业线目标完成对比
					var chartBriefOptions3 = {
					        chart: {
					            type: 'column',
					            renderTo:'chartBrief3',
					            //zoomType: 'xy'
					            //height :340,
					            //width:800
					        },
					        title: {
					            text: ''
					        },
					        //去除版权
					        credits: {
					          enabled:false
							},
					        //去除右上角导出图标
					        exporting: {
					            enabled:false
							},
					        xAxis: {
						        lineWidth: 1,
					            lineColor: "#e9ebf2",
					            tickWidth: 0,
					            allowDecimals:false, //不显示小数
				            labels: {
				                rotation: 0,
				                align: 'center',
				                y:30,
				                style: {
				                    fontSize: '12px',
				                    fontFamily: 'Verdana, sans-serif',
				                    color:'#7a8798',
				                }
				            }
				        },
					        yAxis: [ 
//									{
//										gridLineColor: '#e9ebf2',
//									    gridLineWidth: 1,
//									    min: 0,
//									    title: {
//									        text: '已完成 (个)'
//									    },
//									    
//									}
//								,
					        	{
					        		gridLineColor: '#e9ebf2',
						            gridLineWidth: 1,
						            min: 0,
						            title: {
						                text: '项目数(个)'
						            },
						            //opposite: true
					        	}
					        ],
					        tooltip: {
					            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
					            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
					                			 '<td style="padding:0"><b>{point.y} 个</b></td></tr>',
					            footerFormat: '</table>',
					            shared: true,
					            useHTML: true
					        },
				        legend: {
				            backgroundColor: '#FFFFFF',
				            //reversed: true,
				            itemStyle:{
				                fontWeight:'normal',
				                color:'#525662',
				            },
				        },	        
				        plotOptions: {
					            column: {
					            	pointPadding: 0.2,
					                borderWidth: 0,
					                pointWidth: 20
					            }
					        },
					        series: [{
					        	type: 'column',
					        	//yAxis: 1,
					            name: '目标数',
					            color:'#587edd',
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
				            },
					            
					        },{
					        	type: 'column',
					            name: '已完成',
					            color:'#00bdf4',
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
				            },
					        }]

					    };
					
					//获取数据，加载图表
					var url = platformUrl.deptProTarget;
					if(!isGG) url = platformUrl.tzjlProTarget;
					
					sendPostRequestByJsonObj(url,null,function(data){
						var result = data.result.status;
						if(result == "ERROR"){ //OK, ERROR
							layer.msg(data.result.message);
							return;
						}else{
							var list = data.pageList.content;
							var mb = [];
							var ywc = [];
							var categories = [];
							for(var i=0;i<list.length;i++){
								mb.push( list[i].target);
								ywc.push( list[i].completed);
								categories.push(isGG ? list[i].departmentName : list[i].realName);
							}
							chartBriefOptions3.series[0].data = mb;
							chartBriefOptions3.series[1].data = ywc;
							chartBriefOptions3.xAxis.categories = categories;
							var chart = new Highcharts.Chart(chartBriefOptions3);
						}
					});
					</script>
					
					
					
					
					
					<!-- 项目完成跟踪图 -->
					<div class="bottom">
						<h2 class="chart_name">项目完成率分析</h2>
						<div id="chartBrief4" class="chart chart_m"></div>
					</div>

				</div>
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
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>


</html>

