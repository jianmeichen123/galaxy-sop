/**
 * 
 */
var chartPostAnalysisUtils = {
		postAnalysisOptions : {
			tooltip : {
				trigger: 'axis',
				axisPointer:{  //删除中轴线
	              	type:'none'
	              },
	              //tooltip样式
	              backgroundColor:'rgba(255,255,255,0.7)',
	              borderColor:'#9dd2fc',
	              borderWidth:1,
	              borderRadius:1,
	              textStyle:{
	            	  color:'#333',
	            	  fontFamily:'宋体',
	            	  fontSize:'12'
	              }
					},
		    dataZoom: {
		        show: true,
		        start : 0,
		        end : 80,
		       // handleSize:"5",
		        handleColor:"#539be2",
		        dataBackgroundColor:'#e9f3fb',
		        fillerColor:'#e9f3fb',
		        handleSize:5,
		        height:12,
		        y:175,
		        zoomLock:true,
		        showDetail:false
		    },
		    calculable : true,
		    legend:{
		        show:true,
		        orient:'horizontal',
		        textStyle:{
		        	 fontFamily:'宋体'
		        },
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
		        		interval:0,
		        		textStyle: {
		        			color: '#7a8798',
		        			fontFamily:'宋体'
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
		            "barWidth":"18",//柱图宽度
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
		            "barWidth":"18",//柱图宽度
		            tooltip : {trigger: 'item'},
		            data:[90,70,50,40,30,20,10],
		            itemStyle: {
                     normal: {
                         color: '#51d7cc',
                         label: {  
                             show: true,
                             position: 'top',
                             formatter: function (params) {
                                 for (var i = 0, l =chartPostAnalysisUtils.postAnalysisOptions.xAxis[0].data.length; i < l; i++) {
                                     if (chartPostAnalysisUtils.postAnalysisOptions.xAxis[0].data[i] == params.name) {
                                         return chartPostAnalysisUtils.postAnalysisOptions.series[0].data[i] + params.value;
                                     }
                                 }
                             },
                             textStyle: {
                                 color: '#999'
                             }
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
					
				            	var myChart = echarts.init($('#' + formdata.domid)[0]); 
				            	var departmentArr = new Array();
				            	var nbCountArr = new Array();
				            	var wbCountArr = new Array();
				            	$.each(data.entityList,function(){
				            		var departmentName=this.departmentName.replace(/(^(融快\-))|(^(创保联\-))/g, "");  //融快、创保联去掉前缀
				            		departmentArr.push(this.departmentName ? departmentName : this.createUname);
				            		nbCountArr.push(parseFloat(this.nbCount));
				            		wbCountArr.push(parseFloat(this.wbCount));
				            		//console.log(this.nbCount)
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
					            var sum_nb=0;
					            for(var i=0;i<nbCountArr.length;i++){
					            	sum_nb+=nbCountArr[i];
					            }
					            //外部
					            chartPostAnalysisUtils.postAnalysisOptions.series[1].data = wbCountArr;
					            var sum_wb=0;
					            for(var i=0;i<nbCountArr.length;i++){
					            	sum_wb+=nbCountArr[i];
					            }
					            //无数据显示
					            if(sum_nb==0 && sum_wb==0){
					            	$('#' + formdata.domid).html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
					            }
					            window.onresize = myChart.resize; 
					            myChart.setOption(chartPostAnalysisUtils.postAnalysisOptions);
				           
				
				}else{
					layer.msg(data.result.errorCode);
				}
			});
		}
}

function init(){
	$(".ytxm_block").tabchange2({
		onchangeSuccess:function(index){
			var formdata;
			switch (index) {
			case 0:
				formdata = {
					domid : 'charts_Joint',
					belongType : 1
				};
				chartPostAnalysisUtils.init(formdata);
				break;
			case 1 :
				formdata = {
					domid : 'charts_rk',
					belongType : 2
					
				};
				chartPostAnalysisUtils.init(formdata);
				break;
			case 2 : 
				var formdata = {
					domid : 'charts_cbl',
					belongType : 3
					
				};
				chartPostAnalysisUtils.init(formdata);
				break;
			default:
				console.log("错误");
				break;
			}
		}
	});
	
	var formdata = {
			domid : 'charts_Joint',
			belongType : 1
		};
	chartPostAnalysisUtils.init(formdata);
	
	

}

$(function(){init()});

