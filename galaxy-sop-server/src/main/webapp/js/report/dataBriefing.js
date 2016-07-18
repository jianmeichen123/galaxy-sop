/**
 * Created by wangkun on 16/3/3.
 */
$(function () {
	createMenus(22);
	
	var currDate = new Date();
	var year = currDate.getFullYear();
	var sdate = year + '-01-01';
	var edate = year + '-12-31';
	var sym = year +'-01';
	var eym = year + '-12';
	
	// 项目数目标追踪(个)
	var chartBriefOptions1 = {
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
	    };
	
	// 项目数目标追踪(个)
	var obj ={url:platformUrl.databriefchart};
	obj.data = {type:1}
	ajaxCallback(obj,function(data){
		//console.log(data);
		var result = data.result;
		var list = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		
		var target = list[0].target; 
		var above = list[0].above;
		var completed = list[0].completed;
		var notcompleted = list[0].notcompleted;
		
		if(above>0){
			completed =  target;
			notcompleted = 0;
			chartBriefOptions1.series[0] = {name:'超额',data:[above],color:'#587edd'};
			if(completed>0){
				chartBriefOptions1.series[2] = {name:'已完成',data:[completed],color:'#6fbdeb'};
			}
			chartBriefOptions1.series[1] = {name:'未完成',data:[notcompleted],color:'#ff9c89'};
		}else{
			chartBriefOptions1.series[0] = {name:'超额',data:[above],color:'#587edd'};
			chartBriefOptions1.series[1] = {name:'未完成',data:[notcompleted],color:'#ff9c89'};
			chartBriefOptions1.series[2] = {name:'已完成',data:[completed],color:'#6fbdeb'};
		}
		
		//chartBriefOptions1.yAxis.max = entityList[0].target*2;
		chartBriefOptions1.yAxis.plotLines[0].value=target;
		var chart = new Highcharts.Chart(chartBriefOptions1);
		
		$("#target").html(list[0].target);
		$("#completed").html(list[0].completed);
		$("#notcompleted").html(list[0].notcompleted + '/' + list[0].above);
	});
	
	// 个人完成项目占比
	var chartBriefOptions2 = {
	        chart: {
	        	renderTo:'chartBrief2',
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false,
	            height :340,
	            width:500
	        },
	        title: {
	            text: '项目进度分布',
	            style: {
	            	color: "#fff",
	            },
	        },
	        //去除版权
	        credits: {
	            enabled:false
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
	        },
	        /*plotOptions: {
	         pie: {
	         allowPointSelect: true,
	         cursor: 'pointer',
	         dataLabels: {
	         enabled: false
	         },
	         showInLegend: true
	         }
	         },*/
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
	            x: 0,
	            y: 90,
	            floating: true,
	            backgroundColor: '',
	            itemStyle:{
	                fontWeight:'normal',
	                color:'#525662',
	            },

	        },
	        series: [{
                type: 'pie',
                size:'80%',
                innerSize :'70%',
                name: '项目占比',
	            /*data: [
	                {
	                    name:'接触访谈',
	                    y:5,
	                    num:98,
	                    color:"#86c664"
	                },
	                { name:'内部评审', y: 20,num:98,color:"#ff955b"},
	                { name:'立项会', y: 6,num:98,color:"#4a97da"},
	                { name:'投资意向书', y: 0,num:98,color:"#f26363"},
	                { name:'尽职调查', y: 23,num:98,color:"#7e91d2"},
	                { name:'投资决策会', y: 0,num:98,color:"#5cdaba"},
	                { name:'投资协议', y: 15,num:98,color:"#e09b50"},
	                { name:'股权交割', y: 0,num:98,color:"#796f65"},
	                { name:'投后运营', y: 6,num:98,color:"#ffbf9f"},
	            ],*/
	            dataLabels: {
	                enabled: true,
	                rotation: 0,
	                color: '#FFFFFF',
	                verticalAlign: 'center',
	                distance:-10,
	                align: 'center',
	                x: 0, y: 0,
	                style: {
	                    fontSize: '12px',
	                    fontFamily: 'Verdana, sans-serif',
	                    textShadow: '0 0 3px black',
	                }
	            }
	        }]
	    };
	
	//获取数据，加载图表
	/*var chartBriefOptionsObj2 ={url:path+"/galaxy/report/databriefchart"};
	chartBriefOptionsObj2.data = {type:2};
	ajaxCallback(chartBriefOptionsObj2,function(data){
		//console.log(data);
		var result = data.result;
		var list = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var re = [];
		for(var i=0;i<list.length;i++){
			re.push({ name:list[i].name, y: list[i].rate*100 ,num:list[i].c});
		}
		//console.log(re);
		
		chartBriefOptions2.series[0].data = re;
		var chart = new Highcharts.Chart(chartBriefOptions2);
	});*/
	
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
	            /*categories: [
                '物联网',
                '互联网钢铁',
                '互联网服装',
                '互联网金融',
                '互联网工业',
                '互联网房地产',
                '大数据云计算',
                '互联网工农业',
                '智能设备',
                'o2o及电商'
	            ],*/
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
//					{
//						gridLineColor: '#e9ebf2',
//					    gridLineWidth: 1,
//					    min: 0,
//					    title: {
//					        text: '已完成 (个)'
//					    },
//					    
//					}
//				,
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
	           // data: [49, 71, 106, 129, 144, 176, 135, 148, 216, 194]
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
	            //data: [83, 78, 98, 93, 106, 84, 105, 104, 91, 83]
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
	var chartBriefOptionsObj3 ={url:platformUrl.databriefchart};
	chartBriefOptionsObj3.data = {type:3}
	ajaxCallback(chartBriefOptionsObj3,function(data){
		//console.log(data);
		var result = data.result;
		var list = isHHR=='true' ? data.pageList.content : data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var mb = [];
		var ywc = [];
		var categories = [];
		for(var i=0;i<list.length;i++){
			mb.push( list[i].target);
			ywc.push( list[i].completed);
			categories.push( isHHR=='true' ? list[i].real_name : list[i].name);
		}
		//console.log(re);
		chartBriefOptions3.series[0].data = mb;
		//briefProjectLineOptions.series[0].name = '目标';
		chartBriefOptions3.series[1].data = ywc;
		//briefProjectLineOptions.series[0].name = '已完成';
		chartBriefOptions3.xAxis.categories = categories;
		var chart = new Highcharts.Chart(chartBriefOptions3);
	});
	
	// 项目完成率分析
	var chartBriefOptions4 = {
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
	};

	//获取数据，加载图表
	var chartBriefOptionsObj4 ={url:platformUrl.rateRiseMonthChart};
	chartBriefOptionsObj4.contentType="application/json";
	chartBriefOptionsObj4.data = {sym:sym,eym:eym}
	ajaxCallback(chartBriefOptionsObj4,function(data){
		//console.log(data);
		var result = data.result;
		var list = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var ywc = [];
		var wcl = [];
		var categories = [];
		for(var i=0;i<list.length;i++){
			var rate = list[i].rate*100*12;
			ywc.push( list[i].completed);
			wcl.push( parseFloat(rate.toFixed(2)));
			categories.push(list[i].biz_date.replace('-',''));
		}
		//console.log(re);
		chartBriefOptions4.series[0].data = ywc;
		//briefProjectLineOptions.series[0].name = '目标';
		chartBriefOptions4.series[1].data = wcl;
		//briefProjectLineOptions.series[0].name = '已完成';
		chartBriefOptions4.xAxis[0].categories = categories;
		var chart = new Highcharts.Chart(chartBriefOptions4);
	});
});