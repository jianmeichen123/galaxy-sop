var totalNum_all='';
/**
 * 项目进度
 */
function noDataProGressDiv(){
	//项目进度无数据样式
	if($("#container_progress .highcharts-title tspan").text()=="0个" || $("#container_progress .highcharts-title span").text()=="0个"){
		$(".mask_platform_progress").show();
		$('#container_progress').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                backgroundColor: 'rgba(255, 255, 255, 0)',
            },
            title: {
                text: "<span style='color:#e9ebf2'>"+'0个'+"</span>",
                verticalAlign:'middle',
                y:5,
                x:-95,
                style:{
                    fontFamily:'微软雅黑',
                    color:'#e9ebf2',
                    fontWeight:'bold',
                },
            },
            //去除版权
            credits: {
              enabled:false
            },
            //去除右上角导出图标
            exporting: {
                enabled:true
            },
            plotOptions: {
            pie: {
                borderWidth: 0,
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:120
                },
                showInLegend: true
            }
        },

        legend: {                                                 
            layout: 'horizontal', 
            floating: false,                                       
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemWidth:90,
            width:200,
            padding:-25,
            minHeight:100,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
            //x:0,
        },            

            series: [{
                type: 'pie',
                size:'140%',
                innerSize :'70%',
                name: '项目退出占比',
                data: [
                    {name:'接触访谈',color:'#e9ebf2',y:8},
                    {name: '内部评审',color:'#e9ebf2',y: 10},
                    { name:'CEO评审',color:'#e9ebf2',y:16},
                    { name:'立项会',color:'#e9ebf2',y:20},
                    { name:'投资意向书',color:'#e9ebf2',y: 30},
                    { name:'尽职调查',color:'#e9ebf2',y:40},
                    { name:'投决会',color:'#e9ebf2',y:50},
                    { name:'投资协议',color:'#e9ebf2',y:55},
                    { name:'股权交割',color:'#e9ebf2',y:60},
                    { name:'投后运营',color:'#e9ebf2',y:90},
                ],
                dataLabels: {
                    enabled: false, 
                }
            }]
        });
	}
};


var chartProjectProgressUtils = {
		chartProjectProgressOptions : {
		    chart: {
		    	renderTo:'container_progress',
		        plotBackgroundColor: null,
		        plotBorderWidth: null,
		        plotShadow: false,
		        backgroundColor: 'rgba(255, 255, 255, 0)'
		    },
		    title: {
		        text: "<span style='color:#4490d2'>"+'0个'+"</span>"+"<br/>"+"<span>"+'0%'+"</span>",
		        verticalAlign:'middle',
		        y:5,
		        x:-95,
		        style:{
		            fontFamily:'微软雅黑',
		            color:'#4490d2',
		            fontWeight:'bold',
		            cursor:'pointer'
		        },
		    },
		    //去除版权
		    credits: {
		      enabled:false
		    },
		    //去除右上角导出图标
		    exporting: {
		        enabled:true
		    },
		    tooltip: {
		        pointFormat: '{series.name}: <span>{point.percentage:.1f}%</span>',
		        backgroundColor: 'rgba(255,255,255,0.9)',   // 背景颜色
		         borderColor: '#9dd2fc',         // 边框颜色
		         borderRadius: 1,             // 边框圆角
		         borderWidth: 1,               // 边框宽度
		         shadow: false,                 // 是否显示阴影
		         animation: true,               // 是否启用动画效果
		         style: {                      // 文字内容相关样式
		             color: "#555",
		             fontSize: "12px",
		             fontFamily: "Courir new"
		         }
		    },
		    plotOptions: {
			    pie: {
			        borderWidth: 0,
			        allowPointSelect: true,
			        cursor: 'pointer',
			        depth: 35,
			        dataLabels: {
			            color:'black',
			            rotation: -90,
			            enabled: true,
			            formatter:function(){
			                return this.point.percentage.toFixed(1)+"%";
			            },
			            connectorWidth:0,
			            connectorPadding:0,
			            distance:120
			        },
			        showInLegend: true,
			        events :{
			        	click : function(e){
			        		
			        	}
			        },
			        point:{
			        	events:{
				        	click: function(e){
				        		//console.log(e.point.name);
				        		//this.title="123";
				        	},
				        	legendItemClick : function(e){

				        	}
				        }	
			        }
			    }
		    },
			legend: {                                                 
			    layout: 'horizontal', 
			    floating: false,                                       
			    align: 'right',
			    verticalAlign: 'middle',
			    borderWidth: 0,
			    itemWidth:90,
			    width:200,
			    padding:-25,
			    //minHeight:100,
			    itemStyle:{
			        fontWeight:'normal',
			        color:'#7a8798',
			    }
			    //x:0,
			},            
		    series: [{
		        type: 'pie',
		        size:'140%',
		        innerSize :'70%',
		        name: '项目进度占比',
		        /*data: [
		            {name:'接触访谈',color:'#c5b33b',y:8},
		            {name: '内部评审',color:'#cbc63a',y: 10},
		            {name:'CEO评审',color:'#bac73b',y:16},
		            {name:'立项会',color:'#a6cb2b',y:20},
		            {name:'投资意向书',color:'#69bf56',y: 30},
		            {name:'尽职调查',color:'#58b260',y:40},
		            {name:'投决会',color:'#36afa2',y:50},
		            {name:'投资协议',color:'#159196',y:55},
		            {name:'股权交割',color:'#4790d2',y:60},
		            {name:'投后运营',color:'#3c84c6',y:90},
		        ],*/
		        dataLabels: {
		            enabled: false, 
		        }
		    }]
		},
		init : function(formdata){
			if(formdata.domid){
				chartProjectProgressUtils.chartProjectProgressOptions.chart.renderTo = formdata.domid;
			}
			var form = {
					startTime : DateUtils.getTime(DateUtils.getYearFirstDay())
			}
			sendPostRequestByJsonObj(platformUrl.searchOverView,form,function(data){
				if(data.result.status=='OK'){
					if(data.entityList){
						
			    		var color=['#587edd','#49ceff','#00bdf4','#88dfd8','#4490d2','#bee6d5','#6ebdea','#ff9c89','#62d1b0','#a3e394'];

			    		var seriesArr = new Array();
			    		var totalCount = 0;
			    		var i = 0;
			    		$.each(data.entityList,function(){
			    			//这里的颜色可配置在数据库中啊
			    			var temp = {
			    					name : this.projectProgressName,
			    					color : color[i],
			    					y : this.projectCount,
			    					rate : parseFloat(this.projectRate)
			    			}
			    			seriesArr.push(temp);
			    			totalCount = this.totalCount;
			    			i++;
			    		});
			    		chartProjectProgressUtils.chartProjectProgressOptions.series[0].data = seriesArr;
			    		chartProjectProgressUtils.chartProjectProgressOptions.title.text = "<span style='color:#4490d2'>"+ totalCount +"个</span>"+"<br/>";
			    		chartProjectProgressUtils.chartProjectProgressOptions.plotOptions.pie.events.click = function(e){
			    			//console.log(e.point);
			    			chart.setTitle(
			    					{
			    						text: "<span style='color:#4490d2'>"+ e.point.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.point.percentage.toFixed(1)) +"%</span>",
			    						y:-5,
			    						x:-95
			    					}
			    			);
			    			chart.redraw();
			    			e.point.select();
			    			
			    			//如果没有pie块被选择，返回到只显示数量状态。
			    			var selected_curr = chart.getSelectedPoints();
			    			if(selected_curr.length==0){
			    				chart.setTitle(
			        					{
			        						text: "<span style='color:#4490d2'>"+ totalCount +"个</span>"+"<br/>",
			        						y:5,
			        						x:-95
			        					}
			        			);
			    			}
			    			chartProjectProgressUtils.judgeProgress($.trim(e.point.name),'progress',totalCount);
			    		};
			    		chartProjectProgressUtils.chartProjectProgressOptions.plotOptions.pie.point.events.legendItemClick = function(e){
			    			
			    			//return false;
			    			//console.log(e.target.name);
			    			//chart.setTitle({text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>"});
			        		//chart.redraw();
			    			chart.setTitle(
			    					{
			    						text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>",
			    						y:-5,
			    						x:-95
			    					}
			    			);
			    			chart.redraw();
			    			e.target.select();
			    			var selected_curr = chart.getSelectedPoints();
			    			//console.log(selected_curr)
			    			if(selected_curr.length==0){
			    				chart.setTitle(
			        					{
			        						text: "<span style='color:#4490d2'>"+ totalCount +"个</span>"+"<br/>",
			        						y:5,
			        						x:-95
			        					}
			        			);
			    			}
			    			chartProjectProgressUtils.judgeProgress($.trim(e.target.name),'progress',totalCount);
			    			//e.target.show();
			    			return false;
			    		};
			    		var chart = new Highcharts.Chart(chartProjectProgressUtils.chartProjectProgressOptions);
			    		
			    		
			    		//项目进度图表默认加载链接
			    		$("#container_progress .highcharts-title tspan").click(function(){
			    			var url = platformUrl.projectAnalysis;
			    			if(chartProjectProgressUtils.forwardParam.progressParam){
			    				url += "?forwardProgress=" + chartProjectProgressUtils.forwardParam.progressParam ;
			    			}
			    			forwardWithHeader(url);
			    		});
			    		//项目进度图表默认加载链接----兼容ie8
			    		$("#container_progress .highcharts-title span").click(function(){
			    			var url = platformUrl.projectAnalysis;
			    			if(chartProjectProgressUtils.forwardParam.progressParam){
			    				url += "?forwardProgress=" + chartProjectProgressUtils.forwardParam.progressParam ;
			    			}
			    			forwardWithHeader(url);
			    		});
			    		//more 链接
			    		$("#more_progress").click(function(){
			    			forwardWithHeader(platformUrl.projectAnalysis);
			    		});
					}else{
						layer.msg('后端查询数据为空');
					}
				}else{
					layer.msg(data.result.errorCode);
				}
				
				
			});
	
		},
		forwardParam : {
			progressParam : undefined,
			timeParam : undefined
			},
		judgeProgress : function(name,flag,totalCount){
			var param;
			if(typeof(name) != 'undefined'){
				if(name == '接触访谈'){
					param = 1;
				}else if(name == 'CEO评审'){
					param = 3;
				}else if(name == '投资意向书'){
					param = 5;
				}else if(name == '投资决策会'){
					param = 7;
				}else if(name == '股权交割'){
					param = 9;
				}else if(name == '内部评审'){
					param = 2;
				}else if(name == '立项会'){
					param = 4;
				}else if(name == '尽职调查'){
					param = 6;
				}else if(name == '投资协议'){
					param = 8;
				}else if(name=="投后运营"){
					param = 10;
				}
			}
			
			if(flag=='progress'){
				chartProjectProgressUtils.forwardParam.progressParam = param;
				$("#container_progress .highcharts-title tspan").click(function(){
					
					if($("#container_progress .highcharts-title tspan").text()== (totalCount+'个')){
						var url = platformUrl.projectAnalysis;
						forwardWithHeader(url);
					}else{
						var url = platformUrl.projectAnalysis;
						if(chartProjectProgressUtils.forwardParam.progressParam){
							url += "?forwardProgress=" + chartProjectProgressUtils.forwardParam.progressParam ;
						}
						forwardWithHeader(url);
					}
					
				})
				$("#container_progress .highcharts-title span").click(function(){
					if($("#container_progress .highcharts-title span").text()== (totalNum_all+'个')){
						var url = platformUrl.projectAnalysis;
						forwardWithHeader(url);
					}else{
						var url = platformUrl.projectAnalysis;
						if(chartProjectProgressUtils.forwardParam.progressParam){
							url += "?forwardProgress=" + chartProjectProgressUtils.forwardParam.progressParam ;
						}
						forwardWithHeader(url);
					}
				})
			}		
		}
}