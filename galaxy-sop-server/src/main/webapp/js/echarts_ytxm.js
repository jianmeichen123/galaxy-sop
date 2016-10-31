    // 路径配置
        require.config({
            paths: {
                echarts: '/sop/js'
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
                var myChart = ec.init(document.getElementById('charts_Joint'));                 
                var option = {
                		 tooltip : {
                		        trigger: 'axis'
                		    },
                		    dataZoom: {
                		        show: true,
                		        start : 0,
                		        end : 80,
                		       // handleSize:"5",
                		        handleColor:"#539be2",
                		        height:12,
                		        y:175,
                		    },
                		    calculable : true,
                		    legend:{
                		        show:true,
                		        orient:'horizontal',
                		        data:[
                		            {
                		                name:'创建',
                		                textStyle:{
                		                    color:'#7a8798'
                		                },
                		                icon:'stack'
                		            },
                		            {
                		                name:'投资',
                		                textStyle:{
                		                    color:'#7a8798'
                		                },
                		                icon:'stack'
                		            }
                		        ]
                		    },
                		    grid: {
                                borderWidth: 0,
                                y: 30,
                                y2: 55,
                                x:40,
                                x2:0
                            },
                		    xAxis : [
                		        {
                		        	 type : 'category',
                                     data : ["数字娱乐","互联网金融","互联网餐饮","云计算大数据","互联网医疗","互联网旅游","互联网教育"],
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
                		    series : [
                		        {
                		            name:'创建',
                		            type:'bar',
                		            stack:"项目数",
                		            "barWidth":"20",//柱图宽度
                		            data:[60,50, 40,30, 20,10,5],
                		            itemStyle: {
                                        normal: {
                                            color: '#9dd2fc',
                                            label: {  
                                                show: false,
                                            }
                                        }
                                    },
                		        },
                		        {
                		            name:'投资',
                		            type:'bar',
                		            stack:"项目数",
                		            "barWidth":"20",//柱图宽度
                		            tooltip : {trigger: 'item'},
                		            data:[90,70, 50,40, 30,20,10],
                		            itemStyle: {
                                        normal: {
                                            color: '#51d7cc',
                                            label: {  
                                                show: false,
                                            }
                                        }
                                    },
                		        },
                		    ]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('charts_rk'));                 
                var option = {
                		 tooltip : {
                		        trigger: 'axis'
                		    },
                		    dataZoom: {
                		        show: true,
                		        start : 0,
                		        end : 80,
                		       // handleSize:"5",
                		        handleColor:"#539be2",
                		        height:12,
                		        y:175,
                		    },
                		    calculable : true,
                		    legend:{
                		        show:true,
                		        orient:'horizontal',
                		        data:[
                		            {
                		                name:'创建',
                		                textStyle:{
                		                    color:'#7a8798'
                		                },
                		                icon:'stack'
                		            },
                		            {
                		                name:'投资',
                		                textStyle:{
                		                    color:'#7a8798'
                		                },
                		                icon:'stack'
                		            }
                		        ]
                		    },
                		    grid: {
                                borderWidth: 0,
                                y: 30,
                                y2: 55,
                                x:40,
                                x2:0
                            },
                		    xAxis : [
                		        {
                		        	 type : 'category',
                                     data : ["数字娱乐1","互联网金融1","互联网餐饮","云计算大数据","互联网医疗","互联网旅游","互联网教育"],
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
                		    series : [
                		        {
                		            name:'创建',
                		            type:'bar',
                		            stack:"项目数",
                		            "barWidth":"20",//柱图宽度
                		            data:[60,50, 40,30, 20,10,5],
                		            itemStyle: {
                                        normal: {
                                            color: '#9dd2fc',
                                            label: {  
                                                show: false,
                                            }
                                        }
                                    },
                		        },
                		        {
                		            name:'投资',
                		            type:'bar',
                		            stack:"项目数",
                		            "barWidth":"20",//柱图宽度
                		            tooltip : {trigger: 'item'},
                		            data:[90,70, 50,40, 30,20,10],
                		            itemStyle: {
                                        normal: {
                                            color: '#51d7cc',
                                            label: {  
                                                show: false,
                                            }
                                        }
                                    },
                		        },
                		    ]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );

        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('charts_cbl'));                 
                var option = {
                		 tooltip : {
                		        trigger: 'axis'
                		    },
                		    dataZoom: {
                		        show: true,
                		        start : 0,
                		        end : 80,
                		       // handleSize:"5",
                		        handleColor:"#539be2",
                		        height:12,
                		        y:175,
                		    },
                		    calculable : true,
                		    legend:{
                		        show:true,
                		        orient:'horizontal',
                		        data:[
                		            {
                		                name:'创建',
                		                textStyle:{
                		                    color:'#7a8798'
                		                },
                		                icon:'stack'
                		            },
                		            {
                		                name:'投资',
                		                textStyle:{
                		                    color:'#7a8798'
                		                },
                		                icon:'stack'
                		            }
                		        ]
                		    },
                		    grid: {
                                borderWidth: 0,
                                y: 30,
                                y2: 55,
                                x:40,
                                x2:0
                            },
                		    xAxis : [
                		        {
                		        	 type : 'category',
                                     data : ["数字娱乐2","互联网金融2","互联网餐饮","云计算大数据","互联网医疗","互联网旅游","互联网教育"],
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
                		    series : [
                		        {
                		            name:'创建',
                		            type:'bar',
                		            stack:"项目数",
                		            "barWidth":"20",//柱图宽度
                		            data:[60,50, 40,30, 20,10,5],
                		            itemStyle: {
                                        normal: {
                                            color: '#9dd2fc',
                                            label: {  
                                                show: false,
                                            }
                                        }
                                    },
                		        },
                		        {
                		            name:'投资',
                		            type:'bar',
                		            stack:"项目数",
                		            "barWidth":"20",//柱图宽度
                		            tooltip : {trigger: 'item'},
                		            data:[90,70, 50,40, 30,20,10],
                		            itemStyle: {
                                        normal: {
                                            color: '#51d7cc',
                                            label: {  
                                                show: false,
                                            }
                                        }
                                    },
                		        },
                		    ]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );

