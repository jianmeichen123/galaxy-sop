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
</head>
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
</script>


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
</html>

