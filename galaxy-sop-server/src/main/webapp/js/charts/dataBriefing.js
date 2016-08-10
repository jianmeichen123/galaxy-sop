function init(){
	createMenus(26);
	console.log("数据简报");
	//数据目标追踪
	sendPostRequestByJsonObj(platformUrl.searchTargetTracking,null,function(data){
		console.log("项目目标追踪回掉");
		if(data.result.status=='OK'){
			var formdata = {
					data : data,
					dom_target_id : "show_target_count",
					dom_project_id : "show_project_count",
					dom_not_id : "show_not_completed_count"
			}
			chartTargetTrackingUtils.init(formdata);
		}else{
			layer.msg(data.result.errorCode);
		}
	});
	
	sendPostRequestByJsonObj(platformUrl.searchProjectCompletion,null,function(data){
		console.log("项目完成率分析");
		if(data.result.status=='OK'){
			var formdata = {
					data : data
			}
			chartProjectCompletionUtils.init(formdata);
		}else{
			layer.msg(data.result.errorCode);
		}
		
	})
	
	
	
}

//项目数目标追踪(个) 
	var chartTargetTrackingUtils = {
			chartTargetTrackingOptions : {
		        chart: {
		            type: 'bar',
		            renderTo:'chartBrief1',
		            height :200,
		        },
		        title: {
		        	text:'',
		        	//text: '项目目标追踪',
		             style: {
		                    color: "#fff",
		                },
		        },
		        subtitle: {
		    	    text: '单位:个',
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
		            categories: ['项目数']
		        },
		        yAxis: {
		            min: 0,
		            //max:120,
		            title: {text: ''},
		            gridLineColor:'#EEE',
		            allowDecimals:false, //不显示小数
		            plotLines: [{
		                color: '#FF0000',
		                width: 2,
		                value: 100
		            }]
		        },
		        legend: {
		            backgroundColor: '#FFFFFF',
		            reversed: true,
		            itemStyle:{
		                fontWeight:'normal',
		                color:'#525662',
		            },
		        },
		    plotOptions: {
		        series: {
		            stacking: 'normal'
		        },
		        bar: {
		            pointPadding: 0.5,
		            groupPadding: 0.2,
		            borderWidth: 0,
		            pointWidth: 15,
		            dataLabels: {
		                enabled: true,
		                style:{
		                    color:'#fff',
		                    fontWeight:'normal',
		                    textShadow:'none',
		                    fontFamily:'宋体'
		                },
		            }
		        }
		    },
		        series: [{
		            name: '超额',
		            //data: [0],
		            color: '#587edd'
		        }, {
		            name: '未完成',
		            //data: [20],
		            color: '#ff9c89'
		        }, {
		            name: '已完成',
		            //data: [80],
		            color:'#6fbdeb'
		        }]
		    },
		   /*
		    * 项目目标跟踪
		    * param
		    * dom_target_id 目标数显示domId
		    * dom_project_id 完成数显示目标Id
		    * dom_not_id 未完成数显示目标Id
		    * data 后台封装图表数据
		    * 
		    * */
		    init : function(formdata){
		    	if(formdata.data){
		    		var targetCount = formdata.data.entity.targetCount ? formdata.data.entity.targetCount : "0"; 
					var aboveCount = formdata.data.entity.aboveCount ? formdata.data.entity.aboveCount : "0";
					var projectCount = formdata.data.entity.projectCount ? formdata.data.entity.projectCount : "0";
					var notcompletedCount = formdata.data.entity.notCompleteCount ? formdata.data.entity.notCompleteCount : "0";
					chartTargetTrackingUtils.chartTargetTrackingOptions.series[0] = {name:'超额',data:[aboveCount],color:'#587edd'};
					chartTargetTrackingUtils.chartTargetTrackingOptions.series[1] = {name:'未完成',data:[notcompletedCount],color:'#ff9c89'};
					chartTargetTrackingUtils.chartTargetTrackingOptions.series[2] = {name:'已完成',data:[projectCount],color:'#6fbdeb'};
					//chartBriefOptions1.yAxis.max = entityList[0].target*2;
					chartTargetTrackingUtils.chartTargetTrackingOptions.yAxis.plotLines[0].value=targetCount;
					var chart = new Highcharts.Chart(chartTargetTrackingUtils.chartTargetTrackingOptions);
					if(formdata.dom_target_id){
						$("#" + formdata.dom_target_id).html(targetCount);
					}
					if(formdata.dom_project_id){
						$("#" + formdata.dom_project_id).html(projectCount);
					}
					if(formdata.dom_not_id){
						$("#" + formdata.dom_not_id).html(notcompletedCount + "/" + aboveCount);
					}
		    	}else{
		    		layer.msg("初始化图表参数为空");
		    	}
		    	
		    }
	}
	
	var chartProjectCompletionUtils = {
			chartProjectCompletionOptions : {

				    chart: {
				    	renderTo:'chartBrief4',
			            zoomType: 'xy'
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
			        xAxis: [{
			            /*categories: ['物联网', '互联网钢铁', '互联网服装', '互联网金融', '互联网工业', '互联网房地产',
			                '大数据云计算', '互联网工农业', '智能设备', 'o2o及电商'],*/
			            lineWidth: 1,
				        lineColor: "#e9ebf2",
				        tickWidth: 0,
			            labels: {
			                //rotation: -45,
			                align: 'center',
			                style: {
			                    fontSize: '12px',
			                    fontFamily: 'Verdana, sans-serif'
			                }
			            }
			        }],
			        yAxis: [{ // Primary yAxis
			        	gridLineColor: '#e9ebf2',
				        gridLineWidth: 1,
			            labels: {
			                format: '{value} %',
			                style: {
			                    color: '#89A54E'
			                }
			            },
			            title: {
			                text: '项目完成率',
			                style: {
			                    color: '#606060'
			                }
			            }
			        }, { // Secondary yAxis
			            title: {
			                text: '完成项目数',
			            },
			            labels: {
			                format: '{value} 个',
			                style: {
			                    color: '#606060'
			                }
			            },
			            opposite: true
			        }],
			        tooltip: {
			            shared: true
			        },
			        plotOptions: {
			            series: {
			                pointPadding: 0, //数据点之间的距离值
			                groupPadding: 0, //分组之间的距离值
			                borderWidth: 0,
			                shadow: false,
			                pointWidth:20 //柱子之间的距离值
			            }
			        },
			        legend: {
			            floating: true,
			            y:20,
			            backgroundColor: '#FFFFFF',
			            itemStyle:{
			                fontWeight:'normal',
			                color:'#525662',
			            },

			        },
			        series: [{
			            name: '完成项目数',
			            color: '#587edd',
			            type: 'column',
			            yAxis: 1,
			            //data: [7, 6, 9, 14, 18, 21, 25, 26, 23, 18],
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
			            tooltip: {
			                valueSuffix: ' 个'
			            }

			        }, {
			            name: '项目完成率',
			            color: '#ff9c89',
			            type: 'spline',
			            //data: [49.5, 61.8, 59.1, 92.9, 87.3, 56.7, 78.4, 90.8, 65.3, 88.4],
			            tooltip: {
			                valueSuffix: '%'  
			            }
			        }]
			},
			init : function(formdata){
				if(formdata.data){
					//完成项目数
					var projectCountArr = new Array();
					//完成项目率
					var completedRateArr = new Array();
					//时间
					var projectDateArr = new Array();
					
					$.each(formdata.data.entityList,function(){
						projectCountArr.push(this.projectCount);
						completedRateArr.push(parseFloat(this.completedRate));
						projectDateArr.push(this.projectDate);
					});
					
					chartProjectCompletionUtils.chartProjectCompletionOptions.series[0].data = projectCountArr;
					chartProjectCompletionUtils.chartProjectCompletionOptions.series[1].data = completedRateArr;
					//横轴日期
					chartProjectCompletionUtils.chartProjectCompletionOptions.xAxis[0].categories = projectDateArr;
					var chart = new Highcharts.Chart(chartProjectCompletionUtils.chartProjectCompletionOptions);
				}else{
					layer.msg("初始化图表参数为空");
				}
				
			}
	}
$(document).ready(init());