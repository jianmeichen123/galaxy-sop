$(function(){
	$("#container_health").css("width","100%");
	var myChart = echarts.init(document.getElementById('container_health'));   
	  var option = {
	          tooltip: {
	              show: true,
	              trigger:'axis',
	              axisPointer:{  //删除中轴线
	              	type:'none'
	              },
	              //tooltip样式
	              backgroundColor:'rgba(255,255,255,0.9)',
	              borderColor:'#9dd2fc',
	              borderWidth:1,
	              borderRadius:1,
	              textStyle:{
	            	  color:'#555',
	            	  fontFamily:'宋体',
	            	  fontSize:'12'
	              },
	              formatter: function (params) {
	                  var tar = params[0];
	                  var num=tar.value;
	                  if(num==undefined){
	                	  num=0;
	                  }
	                  return tar.name + '<br/>' +'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#a3e394"></span>'+ tar.seriesName + ' : ' + num;
	              }
	          },
	          xAxis : [
	              {
	                  type : 'category',
	                  data : ["高于预期","健康","健康预警"],
	                  axisLabel: {
	                      show: true,
	                      textStyle: {
	                          color: '#7a8798',
	                          fontFamily:'宋体'
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
	            	  //name样式
	            	  name:'项目数（个）\n',
	            	  position:'left',
	            	  nameLocation:'middle',
	            	  nameTextStyle:{
	            		color:"#7a8798",
	            		fontFamily:'宋体'
	            	  },
	                  type : 'value',
	                  axisLabel: {
	                      show: true,
	                      textStyle: {
	                          color: '#7a8798'
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
	              x:50,
	              x2:10
	          },
	        
	          series : [
	              {
	                  "name":"项目数",
	                  "type":"bar",
	                  "data":[],
	                  "barWidth":"18",//柱图宽度
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
	  var obj={};
	   sendPostRequestByJsonObj(platformUrl.getHealthyCharts,obj,function(data){
			var result = data.result.status;
			if(result == "ERROR"){ //OK, ERROR
				layer.msg(data.result.message);
				return;
			}else{
				var userData = data.userData;
		
				option.series[0].data[0] =userData.healthHighNum;
				option.series[0].data[1] =userData.healthGoodNum;
				option.series[0].data[2] =userData.healthWarnNum;
				console.log(userData.healthGoodNum)
				//无数据显示
				var healthTotalNum=userData.healthHighNum+userData.healthGoodNum+userData.healthWarnNum;
				if(healthTotalNum==0){
					$("#platform_health_more").hide();
					$('#container_health').html('<div  class="no_info_div" style="padding-top:65px;"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
				}
			}
	  	});
	   window.onresize = myChart.resize; 
	   myChart.setOption(option); 
	 //下面是需要添加的方法内容
	   //点击柱状图跳转相应页面的功能，其中param.name参数为横坐标的值 
	   myChart.on('click', function (param) {
		   if (typeof param.seriesIndex != 'undefined') {
	           switch (param.name) {
	               case "高于预期": 
	               	setCookie("href_url", window.location,24,'/');
	               	window.location.href = platformUrl.toHealthChartDetail+"?flagUrl=healthHighNum";                           
	                   break;
	               case "健康":  
	               	setCookie("href_url", window.location,24,'/');
	                   window.location.href = platformUrl.toHealthChartDetail+"?flagUrl=healthGoodNum";
	                   break;
	               case "健康预警":
	               	setCookie("href_url", window.location,24,'/');
	                   window.location.href = platformUrl.toHealthChartDetail+"?flagUrl=healthWarnNum";
	                   break;
	               default:
	                   break;
	           }
	       }
	});


})  


