var searchRiseRatePanel = {
		init : function(){
			//初始化日期选择
			var _formdata = {
					title : "项目创建日期：",
					day : true,
					month : true,
					domid : "search_datepicker"
			}
			datepickerSelecter.init(_formdata);
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
			sendGetRequest(platformUrl.getDepartMentDict + "/" + departmentId,null,function(data){
				var _dom;
				_dom = $("#search_rise_rate_form").find("#search_project_depart_id");
				utils.each(data,_dom,"all");
			});
			
			$("#search_rise_rate_form").find("#search_btn").click(function(){
				queryRiseRateUtils.query();
			});  
			
			chartRiseRateUtils.init();
			var formdata = {
					domid : "grid_rise_rate"
			}
			riseRateGrid.init(formdata);
			
		}
}

var riseRateGrid = {
		init : function(formdata){
			if(!formdata.domid){
				layer.msg("参数domid不能为空");
				return;
			}
			$('#'+formdata.domid).bootstrapTable({
			      url : platformUrl.searchRiseRateGrid,     //请求后台的URL（*）
			      queryParamsType: 'size|page', // undefined
			      showRefresh : false ,
			      search: false,
			      method : 'post',           //请求方式（*）
//			      toolbar: '#toolbar',        //工具按钮用哪个容器
//			      striped: true,           //是否显示行间隔色
			      cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			      pagination: true,          //是否显示分页（*）
			      sortable: false,           //是否启用排序
			      sortOrder: "asc",          //排序方式
			      queryParams: riseRateGrid.queryParams,//传递参数（*）
			      sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
			      pageNumber:1,            //初始化加载第一页，默认第一页
			      pageSize: 10,            //每页的记录行数（*）
			      pageList: [10, 20],    //可供选择的每页的行数（*）
			      strictSearch: true,
			      clickToSelect: true,        //是否启用点击选中行
//			      height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			      uniqueId: "id",           //每一行的唯一标识，一般为主键列
			      cardView: false,          //是否显示详细视图
			      detailView: false,          //是否显示父子表
			      columns: [
					{
			        field: 'projectDate',
			        title: '时间'
			      },{
				    field: 'departmentName',
				    title: '投资事业线'
				  },{
			        field: 'projectTypeName',
			        title: '项目类型'	
			      }, {
			        field: 'createUname',
			        title: '投资经理'
			      }, {
			        field: 'projectCount',
			        title: '项目数'
			      }, {
			        field: 'riseRate',
			        title: '环比'
			      }]
			    });
		},
		queryParams : function(params){
			var form = queryRiseRateUtils.getQuery();
			params.departmentId = form.departmentId;
//			params.projectProgress = form.projectProgress;
			params.projectType = form.projectType;
			
			params.startTime = form.startTime;
			params.endTime = form.endTime;
			params.dateType = form.dateType;
			return params;
			
		},
		nameFormatter : function(value, row, index){
			return ['<a class="projectNameLink blue" id="projectName" href="javascript:void(0)">',
			        value,
			        '</a>  '
			        ].join('');
		},
		nameEvents : {
			'click .filedownloadlink': function (e, value, row, index) {
				window.location.href = platformUrl.showProject + "/" + row.id + "/1";
			}
		},
		research : false
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
				form.dateType = '%Y-%m-%d';
				if(form.startTime && $.trim(form.startTime) != ''){
					form.startTime = DateUtils.getTime(form.startTime+' 00:00:00');
				}else{
					form.startTime = undefined;
				}
				if(form.endTime && $.trim(form.endTime) != ''){
					form.endTime = DateUtils.getTime(form.endTime+' 23:59:59');
				}else{
					form.endTime = undefined;
				}
			}else if(form.chooseDate=='month'){
				form.dateType = '%Y-%m';
				if(form.startTime && $.trim(form.startTime) != ''){
					form.startTime = DateUtils.getTime(form.startTime+'-01 00:00:00');
				}else{
					form.startTime = undefined;
				}
				if(form.endTime && $.trim(form.endTime) != ''){
					var lastDay = DateUtils.getLastDayByMonth();
					form.endTime = DateUtils.getTime(form.endTime+'-'+lastDay+' 23:59:59');
				}else{
					form.endTime = undefined;
				}
			}
			
			
			return form;
		},
		query : function(){
			riseRateGrid.research = true;
			//departmentId,projectType,startTime,endTime
			chartRiseRateUtils.init();
			$('#grid_rise_rate').bootstrapTable('refresh',riseRateGrid.queryParams);
		}
}

var chartRiseRateUtils = {
		chartRiseRateOptions : {
				chart: {
					renderTo:'chart_rise_rate',type: 'line',//margin: [ 50, 50, 100, 80]
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
						//layer.msg('后端查询数据为空');
					}
					
				}else{
					layer.msg(data.result.errorCode);
				}
			});	
		}
}