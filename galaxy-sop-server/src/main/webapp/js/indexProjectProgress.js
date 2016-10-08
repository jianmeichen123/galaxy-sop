/**
 * 
 */
function noDataProGressDiv(){
	//项目进度无数据样式
	if($("#container_progress .highcharts-title tspan").text()=="0个" || $("#container_progress .highcharts-title span").text()=="0个"){
		$(".mask_platform_progress").show();
		$("#more_progress").hide();
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

var chartIndexPProgressUtils = {
		chartIndexPProgressOptions : {
			chart: {
				renderTo :'histogram',
		        type: 'column'
		    },
		    title: {
		        text: ''
		    },
		    credits: {
		        enabled:false
		    },
		    exporting: {
		    	enabled:false
		    },
		    xAxis: {
		        //categories: ['朱玟','牟敏','关屿','赵广智','陈丛翀','王飞韵','蔡燕','王晓宇'],
		        tickWidth:0,
		    	labels: {
		            rotation:-45,
		            //align: 'right',
		            //staggerLines:2,
		            style: {
		                fontSize: '12px',
		                fontFamily: 'Verdana, sans-serif'
		                //writingMode:'tb-rl'   //文字竖排样式,
		            },
		        }
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text:''
		        }
		    },
		    legend: {
		        enabled: false,
		        x:5,
		    },
		    tooltip: {
		    	/*useHTML: true,
		    	formatter: function(){
		    		return this.point.x +'<br/>项目数:'+ this.point.y +'个';
		    	}*/
		    	enabled:true
		    },
		    series: [{
		        name: '项目数',
		        //data: [8,5,4,3,3,2,2,2,2],
		        dataLabels: {
		            enabled: false,
		            rotation: 0,
		            color: '#FFFFFF',
		            align: 'center',
		            x: 0,
		            y: 25,
		            style: {
		                fontSize: '13px',
		                fontFamily: 'Verdana, sans-serif',
		                textShadow: '0 0 3px black'
		            },
		            /*formatter:function(){
		     			return this.point.y;
					},*/
		        }
		    }]
		},
		init : function(formdata){
			
			if(formdata.domid){
				chartIndexPProgressUtils.chartIndexPProgressOptions.chart.renderTo = formdata.domid;
			}
			var form = {
					createUid : formdata.createUid ? formdata.createUid : userId,
					startTime :  DateUtils.getTime(DateUtils.getYearFirstDay())
			};		
			sendPostRequestByJsonObj(platformUrl.searchOverView,form,function(data){
				if(data.result.status == 'OK'){
					if(data.entityList){
						var projectCountArr = new Array();
						var projectProgressArr = new Array();
						$.each(data.entityList,function(){
							projectCountArr.push(this.projectCount);
							projectProgressArr.push(this.projectProgressName);
						});
						chartIndexPProgressUtils.chartIndexPProgressOptions.series[0].data = projectCountArr;
						chartIndexPProgressUtils.chartIndexPProgressOptions.xAxis.categories = projectProgressArr;
						var chart = new Highcharts.Chart(chartIndexPProgressUtils.chartIndexPProgressOptions);
					}else{
						layer.msg('后端数据为空');
					}
				}else{
					layer.msg(data.result.errorCode);
				}
			});
		}
}