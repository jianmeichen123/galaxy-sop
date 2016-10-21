/**
 * 
 */

var chartsInvestmentUtils = {
		chartsInvestmentOptions : {
			    chart: {
			    	renderTo: 'chart_investment',
			        type: 'line'
			    },
			    title: {
			        text: '',
			        x: -20 //center
			    },
			    //去除版权
			    credits: {
			        enabled:false
			    },
			    //去除右上角导出图标
			    exporting: {
			        enabled:true
			    },
			    xAxis: {
			        lineWidth: 1,
			        lineColor: "#edeff5",
			        tickWidth: 0,
			        labels: {
			            y: 20, //x轴刻度往下移动20px
			            style: {
			                color: '#7a8798',//颜色
			                fontFamily:'宋体',
			            }
			        },
			        //categories: ['201501', '201502', '201503', '201504', '201505', '201506', '201507', '201508', '201509', '201510', '201511', '201512']
			    },
			    yAxis: {
			        gridLineColor: '#f6f7fa',
			        gridLineWidth: 1,
			        labels: {
			            format: '{value} M',
			            x: -10, //y轴刻度往左移动10px
			            style: {
			                color: '#999',//颜色
			                fontFamily:'宋体',  //字体
			            }
			        },
			        title: {
			            text: '金额(百万)'
			        },
			        plotLines: [{
			            value: 0,
			            width: 1,
			            color: '#808080'
			        }],
			        //tickPositions: [0, 500, 1000, 1500, 2000, 2500, 3000]
			    },
			    plotOptions: {
			        series: {
			            marker: {
			                enabled: false
			            }
			        },
			    },
			    tooltip: {
			        valueSuffix: '百万'
			    },
			    legend: {
			        itemMarginTop:-10,
			        itemMarginBottom:-10,
			        layout: 'horizontal',
			        align: 'center',
			        verticalAlign: 'top',
			        borderWidth: 0,
			        itemStyle:{
			            fontWeight:'normal',
			            color:'#7a8798',
			        },
			    },
			    series: [{
			        lineWidth: 1.5,
			        name: '估值',
			        color:'#65ade7',
			        //data: [10, 100, 500, 750,800, 900, 1000,1200, 1500, 1800,2000,3000]
			    }, {
			        lineWidth: 1.5,
			        name: '投资金额',
			        color:'#ff9c89',
			        //data: [0, 90, 300, 700,700, 800, 900,1100, 1400, 1600,2000,2500]
			    }]
		},
		init : function(formdata){
			
			if(formdata.domid){
				chartsInvestmentUtils.chartsInvestmentOptions.chart.renderTo = formdata.domid;
			}
			var form = {
					startTime : DateUtils.getTime(DateUtils.getYearFirstDay())
			};
			sendPostRequestByJsonObj(platformUrl.searchInvestmentGroupDate,form,function(data){
				if(data.result.status=='OK'){
					if(data.entityList){	
			    		var appraisementArr = new Array(); //初始估值
			    		var investArr = new Array(); //初始投资额
			    		var projectDateArr = new Array();
			    		$.each(data.entityList,function(){
			    			appraisementArr.push(parseFloat(this.appraisement)/100);
			    			investArr.push(parseFloat(this.invest)/100);
			    			projectDateArr.push(this.projectDate);
			    		});
			    		chartsInvestmentUtils.chartsInvestmentOptions.series[0].data = appraisementArr;
			    		chartsInvestmentUtils.chartsInvestmentOptions.series[1].data = investArr;
			    		chartsInvestmentUtils.chartsInvestmentOptions.xAxis.categories = projectDateArr;
			    		if(projectDateArr.length==1){ //当只有一条数据的时候，显示点标记
			    			chartsInvestmentUtils.chartsInvestmentOptions.plotOptions.series.marker.enabled=true;
			    		}
			    		var chart = new Highcharts.Chart(chartsInvestmentUtils.chartsInvestmentOptions);
			    		if(appraisementArr==''&& investArr==''){
			    			$('#'+formdata.domid).html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
			    		}
					}else{
						//layer.msg('后端查询数据为空');
					}
					
				}else{
					layer.msg(data.result.errorCode);
				}
			});
			
			
			
			
			
		}
		
}