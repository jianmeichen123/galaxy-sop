var searchRiseRatePanel = {
		init : function(){
			//初始化日期选择
			var formdata = {
					title : "项目创建日期：",
					day : true,
					month : true,
					domid : "search_datepicker"
			}
			datepickerSelecter.init(formdata);
			//初始化日期
			datePickerInitByHandler("search_rise_rate_form");
			
			$("#search_rise_rate_form").find("#search_date_choose_day").find("#search_start_time").datepicker("setDate",DateUtils.getFirstDayByMonth());
			var currentDate = new Date();
			var params = {
					year : currentDate.getFullYear()-1,
					month : currentDate.getMonth()+1,
					day : currentDate.getDate()
			}
			$("#search_rise_rate_form").find("#search_date_choose_month").find("#search_start_time").datepicker("setDate",DateUtils.getEarliestDay(params));
			
			
			$("#search_rise_rate_form").find("#search_end_time").datepicker("setDate",DateUtils.getEarliestDay());
			//初始化投资事业线
			sendGetRequest(platformUrl.getDepartMentDict+"/department",null,function(data){
				var _dom;
				_dom = $("#search_rise_rate_form").find("#search_project_depart_id");
				utils.each(data,_dom,"all");
			});
			
			chartRiseRateUtils.init();
		}
}

var queryRiseRateUtils = {
		getQuery : function(){
			var form = $("#search_rise_rate_form").serializeObject();
			form = jQuery.parseJSON(form);
			
			if(form.departmentId=='all'){
				form.departmentId = undefined; 
			}
			if(form.projectType=='all'){
				form.projectType = undefined; 
			}
			if(form.projectProgress=='all'){
				form.projectProgress = undefined; 
			}
			
			form.startTime = $("#search_rise_rate_form").find("#search_date_choose_" + form.chooseDate).find("#search_start_time").val();
			
			form.endTime = $("#search_rise_rate_form").find("#search_date_choose_" + form.chooseDate).find("#search_end_time").val();
			
			if(form.chooseDate=='day'){
				if(form.startTime && form.startTime.trim() != ''){
					form.startTime = DateUtils.getTime(form.startTime+' 00:00:00');
				}else{
					form.startTime = undefined;
				}
				if(form.endTime && form.endTime.trim() != ''){
					form.endTime = DateUtils.getTime(form.endTime+' 23:59:59');
				}else{
					form.endTime = undefined;
				}
			}else if(form.chooseDate=='month'){
				if(form.startTime && form.startTime.trim() != ''){
					form.startTime = DateUtils.getTime(form.startTime+'-01 00:00:00');
				}else{
					form.startTime = undefined;
				}
				if(form.endTime && form.endTime.trim() != ''){
					var lastDay = DateUtil.getLastDayByMonth();
					form.endTime = DateUtils.getTime(form.endTime+'-'+lastDay+' 23:59:59');
				}else{
					form.endTime = undefined;
				}
			}
			
			
			return form;
		},
		query : function(){
			projectGrid.research = true;
			//departmentId,projectType,startTime,endTime
			chartOverviewUtils.init();
			$('#grid_overview').bootstrapTable('refresh',projectGrid.queryParams);
		}
}

var chartRiseRateUtils = {
		chartRiseRateOptions : {
				chart: {
					renderTo:'container_xmzzl',type: 'line',//margin: [ 50, 50, 100, 80]
		        },
		        title: {
		            text: '项目完成率分析',
		            align:'left',  
		            style:{
		                fontSize:'18px',
		                fontFamily:'微软雅黑',
		                color:'#3e4351',
		            },        
		        },        //去除版权
		        credits: {enabled:false},
		        //去除右上角导出图标
		        exporting: {enabled:false},
		        xAxis: {
		        	lineWidth: 1,
		            lineColor: "#e9ebf2",
		            tickWidth: 0,
		            //categories: ['2015-01','2015-02','2015-03','2015-04','2015-05','2015-06','2015-07','2015-08','2015-09','2015-10','2015-11','2015-12' ],
		            labels: {
		                rotation: -45,
		                align: 'right',
		                //step: 5,
		                //staggerLines: 1,
		                formatter:function(){
		                	return this.value;
		                },
		                style: {fontSize: '12px',fontFamily: '宋体'}
		            }
		        },
		        yAxis: [{ // Primary yAxis
		            labels: {
		                format: '{value}%',
		                style: {color: '#606060'}
		            },
		            title: {
		                text: '环比 (%)',
		            }
		        }, { // Secondary yAxis
		            title: {
		                text: '完成项目数（个）',
		            },
		            labels: {
		                format: '{value} 个',
		                style: {color: '#606060'}
		            },
		            gridLineColor: '#e9ebf2',
		            gridLineWidth: 1,
		            min: 0,
		            opposite: true,
		        }],
		        legend: {
		            floating: true,
		            verticalAlign: 'top',
		            align: 'center',
		            backgroundColor: '#FFFFFF',
		            itemStyle:{
		                fontWeight:'normal',
		                color:'#525662',
		            },

		        },
		        tooltip: {shared: true},
		        series: [{
		            name: '环比',
		            color: '#65ade7',
		            //data: [15.3,18.6,10.1,5.9,1.8,-14.9,-9.3,-3.8,2.5,8.9,-2.5,19.8],
		            tooltip: {
		               valueSuffix: ' %'
		            },
		            dataLabels: {
		                enabled: false,
		                rotation: 0,
		                color: '#FFFFFF',
		                align: 'center',
		                x: 0,
		                y: 20,
		                style: {
		                    fontSize: '12px',
		                    fontFamily: '宋体',
		                    textShadow: '0 0 3px black'
		                },
		                formatter:function(){
		         			return this.point.y + '%';
		       
		    			}
		            }
		        },{
		            name: '完成项目数',
		            color: '#ff9c89',
		            //data: [30,55,61,65,66,56,50,48,48,52,51,61],
		            tooltip: {
		               valueSuffix: ' 个'
		            },
		            yAxis: 1,
		            dataLabels: {
		                enabled: false,
		                rotation: 0,
		                color: '#FFFFFF',
		                align: 'center',
		                x: 0,
		                y: 10,
		                style: {
		                    fontSize: '12px',
		                    fontFamily: '宋体',
		                    textShadow: '0 0 3px black'
		                },
		                formatter:function(){
		         			return this.point.y;
		       
		    			},
		            }
		        }]	
		},
		/*
		 * 项目增长率
		 * formdata 暂时没用后期会加入一些控制图表显示及显示位置(domid)的参数
		 * 
		 * */
		init : function(formdata){
			
			var form = queryRiseRateUtils.getQuery();
			
			sendPostRequestByJsonObj(platformUrl.searchRiseRate,form,function(data){
				if(data.result.status=='OK'){
					if(data.entityList){
						var projectCountArr = new Array();
			    		var riseRateArr = new Array();
			    		var projectDateArr = new Array();
			    		$.each(data.entityList,function(){
			    			projectCountArr.push(this.projectCount);
//			    			projectDateArr.push(entityList[i].biz_date.replace(/-/g,''));
			    			projectDateArr.push(this.projectDate);
			    			riseRateArr.push(parseFloat(this.riseRate));
						});
			    		chartRiseRateUtils.chartRiseRateOptions.series[0].data = riseRateArr;
			    		chartRiseRateUtils.chartRiseRateOptions.series[1].data = projectCountArr;
			    		chartRiseRateUtils.chartRiseRateOptions.xAxis.categories = projectDateArr;
			    		if(riseRateArr.length<=13){
			    			chartRiseRateUtils.chartRiseRateOptions.xAxis.labels.align='center';
			    			chartRiseRateUtils.chartRiseRateOptions.xAxis.labels.rotation=0;
			    		}
			    		var chart = new Highcharts.Chart(chartRiseRateUtils.chartRiseRateOptions);
					}else{
						layer.msg('后端查询数据为空');
					}
					
				}else{
					layer.msg(data.result.errorCode);
				}
			});	
		}
}