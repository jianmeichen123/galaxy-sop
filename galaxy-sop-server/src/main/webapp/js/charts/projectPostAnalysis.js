/**
 * 
 */
var chartPostAnalysisUtils = {
		postAnalysisOptions : {
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
		                name:'内部创建',
		                textStyle:{
		                    color:'#7a8798'
		                },
		                icon:'stack'
		            },
		            {
		                name:'外部投资',
		                textStyle:{
		                    color:'#7a8798'
		                },
		                icon:'stack'
		            }
		        ]
		    },
		    grid: {
             borderWidth: 0,
             y : 30,
             y2 : 55,
             x : 40,
             x2 : 0
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
                  		color : '#f6f7fa',
                  	    width : 1,
                  	    type : 'solid'
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
		            name:'内部创建',
		            type:'bar',
		            stack:"项目数",
		            "barWidth":"20",//柱图宽度
		            data:[60,50,40,30,20,10,5],
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
		            name:'外部投资',
		            type:'bar',
		            stack:"项目数",
		            "barWidth":"20",//柱图宽度
		            tooltip : {trigger: 'item'},
		            data:[90,70,50,40,30,20,10],
		            itemStyle: {
                     normal: {
                         color: '#51d7cc',
                         label: {  
                             show: false,
                         }
                     }
                 },
		        }
		    ]
		},
		/**
		 * echarts头后项目分析图表
		 * params
		 * 	domid : 需渲染的ID
		 * 	belongType : 业务线分类 1:联合创业 2：融快 3：创宝联
		 */
		init : function(formdata){
			var form = {
					belongType : formdata.belongType
			}
			sendPostRequestByJsonObj(platformUrl.searchPostAnalysis,form,function(data){
				if(data.result.status=="OK"){
					require(
				            [
				                'echarts',
				                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
				            ],function(ec){
				            	var myChart = ec.init($('#' + formdata.domid)[0]); 
				            	var departmentArr = new Array();
				            	var nbCountArr = new Array();
				            	var wbCountArr = new Array();
				            	$.each(data.entityList,function(){
				            		var departmentName=this.departmentName.replace(/(^(融快\-))|(^(创保联\-))/g, "");  //融快、创保联去掉前缀
				            		departmentArr.push(this.departmentName ? departmentName : this.createUname);
				            		nbCountArr.push(parseFloat(this.nbCount));
				            		wbCountArr.push(parseFloat(this.wbCount));
				            	})
				            	chartPostAnalysisUtils.postAnalysisOptions.xAxis[0].data = departmentArr;
				            	var departmentArrNum=departmentArr.length
				            	//默认显示5条数据，不足5条，显示全部
				            	if(departmentArrNum>5){
				            		chartPostAnalysisUtils.postAnalysisOptions.dataZoom.end=5/departmentArrNum*100;
				            	}else{
				            		chartPostAnalysisUtils.postAnalysisOptions.dataZoom.end=100;
				            	}
					            //内部
					            chartPostAnalysisUtils.postAnalysisOptions.series[0].data = nbCountArr;
					            //外部
					            chartPostAnalysisUtils.postAnalysisOptions.series[1].data = wbCountArr;
					            window.onresize = myChart.resize; 
					            myChart.setOption(chartPostAnalysisUtils.postAnalysisOptions);
				            });
				
				}else{
					layer.msg(data.result.errorCode);
				}
			});
		}
}

function init(){
	var formdata1 = {
			domid : 'charts_Joint',
			belongType : 1
	}
	var formdata2 = {
			domid : 'charts_rk',
			belongType : 2
			
	}
	var formdata3 = {
			domid : 'charts_cbl',
			belongType : 3
			
	}
	chartPostAnalysisUtils.init(formdata1);
	chartPostAnalysisUtils.init(formdata2);
	chartPostAnalysisUtils.init(formdata3);
}

$(document).ready(init());

