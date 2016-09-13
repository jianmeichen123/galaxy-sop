    // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('container_health'));                 
                var option = {
                    tooltip: {
                        show: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : ["高于预期","健康","健康预警"],
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#7a8798'
                                }
                            },
                            axisLine:{
                            	show:true,
                            	lineStyle:{
                            		color: '#f6f7fa',
                            	    width: 1,
                            	    type: 'solid'
                            	}
                            },
                            axisTick:{
                            	show:false
                            },
                            splitLine:{   //去网格线
				                            　　　　show:false
				                            　　}
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#999'
                                }
                            },
                            axisLine:{
                            	show:false
                            },
                            axisTick:{
                            	show:false
                            },
                            splitLine:{   
				                            　　　　lineStyle:{  //网格线样式
			                        color: '#f6f7fa',
			                        width: 1,
			                        type: 'solid'
			                      }
				                            　　}
                        }
                    ],
                    grid: {
                        borderWidth: 0,
                        y: 10,
                        y2: 30,
                        x:40,
                        x2:0
                    },
                  
                    series : [
                        {
                            "name":"项目数",
                            "type":"bar",
                            "data":[5, 40, 10],
                            "barWidth":"20",//柱图宽度
                            itemStyle: {
                                normal: {
                                    color: function(params) {
                                        var colorList = [
                                          '#a3e394','#62d1a1','#ff9c89'
                                        ];
                                        return colorList[params.dataIndex]
                                    },
                                    label: {  
                                        show: false,
                                    }
                                }
                            },
                        }
                    ]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
                 //下面是需要添加的方法内容
            //点击柱状图跳转相应页面的功能，其中param.name参数为横坐标的值 
            var ecConfig = require('echarts/config');
            function eConsole(param) {
                if (typeof param.seriesIndex != 'undefined') {
                    switch (param.name) {
                        case "高于预期": 
                        	window.location.href = "http://www.sina.com";                           
                            break;
                        case "健康":  
                            window.location.href = "http://www.baidu.com";
                            break;
                        case "健康预警":  
                            window.location.href = "http://www.qq.com";
                            break;
                        default:
                            break;
                    }
                }
            }
            myChart.on(ecConfig.EVENT.CLICK, eConsole);
            }
        );
