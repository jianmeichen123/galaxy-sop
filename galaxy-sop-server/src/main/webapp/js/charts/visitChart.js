$(function(){

	
	$("button[action='querySearch']").click(function(){
		loadTrendData();
		$(".period_desc").text('('+$("input[name='periodType']:checked").next().text()+')');
	});
   var trend = {};
   loadTrendData();
   function getDatePeriod()
   {
	   var periodType = $("input[name='periodType']:checked").val();
	   console.log("periodType="+periodType);
	   if(periodType == 3)
	   {
		   return getWeeklyPeriod();
	   }
	   else if(periodType == 2)
	   {
		   return getMonthlyPeriod();
	   }
	   return getQuarterlyPeriod();
   }
   /**
    * 获取季度起止时间
    * @returns
    */
   function getQuarterlyPeriod()
   {
	   console.log('getQuarterlyPeriod');
	   var endYear = $("#quarterly_start_data").val().replace('年','');
	   var startYear = endYear-3;
	   
	   var quarterly = $("select[name='s_quarterly']").val();
	   console.log("quarterly="+quarterly);
	   var startTime = new Date();
	   var endTime = new Date();
	   
	   startTime.setFullYear(startYear);
	   startTime.setDate(1);
	   
	   endTime.setFullYear(endYear);
	   
	   if(quarterly == 1)
	   {
		   startTime.setMonth(0);
		   
		   endTime.setMonth(2);
		   endTime.setDate(31);
	   }
	   else if(quarterly == 2)
	   {
		   startTime.setMonth(3);
		   
		   endTime.setMonth(5);
		   endTime.setDate(30);
	   }
	   else if(quarterly == 3)
	   {
		   startTime.setMonth(6);
		   
		   endTime.setMonth(8);
		   endTime.setDate(30);
	   }
	   else if(quarterly == 4)
	   {
		   startTime.setMonth(9);
		   
		   endTime.setMonth(11);
		   endTime.setDate(31);
	   }
	   startTime.setHours(0);
	   startTime.setMinutes(0);
	   startTime.setSeconds(0);
	   startTime.setMilliseconds(0)
	   
	   endTime.setHours(23);
	   endTime.setMinutes(59);
	   endTime.setSeconds(59);
	   endTime.setMilliseconds(999)
	   
	   return {startTime:startTime.format('yyyy-MM-dd hh:mm:ss'), endTime:endTime.format('yyyy-MM-dd hh:mm:ss')};
   }
   /**
    * 获取月度起止时间
    * @returns
    */
   function getMonthlyPeriod()
   {
	   console.log('getMonthlyPeriod');
	   var selected = $("#month_start_data").val().replace('月','');
	   var endYear = selected.split('年')[0];
	   var startYear = endYear-1;
	   
	   var month = selected.split('年')[1];
	   
	   var startTime = new Date();
	   var endTime = new Date();
	   
	   startTime.setFullYear(startYear);
	   startTime.setMonth(month-1);
	   startTime.setDate(1);
	  
	   endTime.setFullYear(endYear);
	   endTime.setMonth(month-1);
	   
	   if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
	   {
		   endTime.setDate(31);
	   }
	   else if(month == 2)
	   {
		   if(endYear%400 ==0 || endYear%4 == 0)
		   {
			   endTime.setDate(29);
		   }
		   else
		   {
			   endTime.setDate(28);
		   }
	   }
	   else 
	   {
		   endTime.setDate(30);
	   }
	   
	   startTime.setHours(0);
	   startTime.setMinutes(0);
	   startTime.setSeconds(0);
	   startTime.setMilliseconds(0)
	   
	   endTime.setHours(23);
	   endTime.setMinutes(59);
	   endTime.setSeconds(59);
	   endTime.setMilliseconds(999)
	   
	   return {startTime:startTime.format('yyyy-MM-dd hh:mm:ss'), endTime:endTime.format('yyyy-MM-dd hh:mm:ss')};
   }
   
   /**
    * 获取周起止时间
    * @returns
    */
   function getWeeklyPeriod()
   {
	   console.log('getWeeklyPeriod');
	   var endDate = $('.visitweekEndDatepicker').val();
	   var endTime = new Date();
	   endTime.setFullYear(endDate.split('-')[0]);
	   endTime.setMonth(endDate.split('-')[1]-1);
	   endTime.setDate(endDate.split('-')[2]);
	   endTime.setHours(23);
	   endTime.setMinutes(59);
	   endTime.setSeconds(59);
	   endTime.setMilliseconds(999)
	   
	   var startTime = endTime.getTime()- (9*7*24*3600*1000) +1;
	   
	   return {startTime:new Date(startTime).format('yyyy-MM-dd hh:mm:ss'), endTime:endTime.format('yyyy-MM-dd hh:mm:ss')};
   }
   
   function loadTrendData()
   {
	   var datePeriod = getDatePeriod();
	   console.log(datePeriod);
	   trend = {
			periods:new Array(),
			plan : new Array(),
			complete : new Array()
	   };
	   var query = {
			startTimeFrom:datePeriod.startTime,
			startTimeThrough:datePeriod.endTime,
			departmentId: $("select[name='departmentId']").val(),
			createdId: $("select[name='createdId']").val(),
			isProject: $("input[name='isProject']:checked").val(),
			periodType: $("input[name='periodType']:checked").val()
	   };
	   
	sendPostRequestByJsonObj(platformUrl.visitTrend,query,function(data){
		   if(data.userData.tendency)
		   {
			   $.each(data.userData.tendency,function(){
				   trend.periods.push(this.period);
				   trend.plan.push(this.count);
				   trend.complete.push(0);
			   });
			   
			   
			   query.complete = 1;
			   
			   sendPostRequestByJsonObj(platformUrl.visitTrend,query,function(data){
				   if(data.userData.tendency)
				   {
					   var yData2 = new Array();
					   $.each(data.userData.tendency,function(){
						   var period = this.period;
						   var count = this.count;
						   var len = trend.periods.length;
						   for(var i=0;i<len;i++)
						   {
							   if(trend.periods[i] == period)
							   {
								   trend.complete[i] = count;
							   }
						   }
					   });
					   visitTrend(1);
				   }
				   
			   });
		   }
	   });
	   visit(query);
	   projectVisit(query);
	   visitFanceStatus(query);
	   recordVisit(query);
   }
   //拜访计划
   function visit(query){
	    var url = Constants.sopEndpointURL+"galaxy/visit/getVisitStatistics";
	    sendPostRequestByJsonObj(url,query,function(data){
					    var json = eval(data);
						 var map = json.userData;
						 $("#planVisit").html(map.visitCount);
						 $("#completeVisit").html(map.completedVisitCount);
						 $("#interviewRate").html(map.visitRate);
					   
				   });
	  
	  
	  } 
   //项目拜访
   function projectVisit(query){
	    var url = Constants.sopEndpointURL+"galaxy/visit/getProjectVisit";
	    sendPostRequestByJsonObj(url,query,function(data){
					    var json = eval(data);
						 var map = json.userData;
						 //  sec1
						 var sec1_radius=[0,"60%"];
						 var sec1_data=[
						         {value:map.isProVisit, name:'项目拜访'},
						         {value:map.isNoVisit, name:'非项目拜访访'},
						  ]
						// sec1
						 data_pie("project_visit","#5ceaf0",['#90e6fb','#ff94b1'],sec1_data,sec1_radius,false);

					   
				   });
	  
	  
	  } 
   //融资轮次
   function visitFanceStatus(query){
	    var url = Constants.sopEndpointURL+"galaxy/visit/getVisitFanceStatus";
	    sendPostRequestByJsonObj(url,query,function(data){
					     var json = eval(data);
						 var arr = json.entityList;
						 // sec2
						 var sec2_radius=["20%","60%"];
						 var sec2_data=[];
						 for(var item in arr){
							 var str = {};
							 str.value = arr[item].countVisit;
							 str.name = arr[item].fanceStatus;
							 sec2_data.push(str);
						 }
						 console.log(sec2_data);
						 var color_array = ['#8cecf8','#f9a4cf','#60dcff','#9ea7ff','#4fc3f9','#fcaccb','#91a9ff','#ffb4b3'];
						 data_pie("project_visit_round","#5ceaf0",color_array,sec2_data,sec2_radius,"area");
				   });
	  
	  
	  } 
   //访谈记录是否缺失
   function recordVisit(query){
	    var url = Constants.sopEndpointURL+"galaxy/visit/getRecordVisit";
	    sendPostRequestByJsonObj(url,query,function(data){
					     var json = eval(data);
						 var map = json.userData;
						// sec3
						 var sec3_radius=["28%","60%"];
						 var sec3_data=[
						                 {value:map.part, name:'记录未缺失'},
						                 {value:map.nopart, name:'记录缺失'}
						               ]
						 data_pie("project_visit_miss","#aaa9fe",['#afabff','#ddd'],sec3_data,sec3_radius,false);
				   });
	  
	  
	  } 
  function visitTrend(mark){
    var myChart = echarts.init(document.getElementById('visitTrend')); 
    var flag=1; 
    var option = {
      tooltip : {
          trigger: 'axis',
          axisPointer:{  //删除中轴线
              type:'none'
          },
          padding:8,
          backgroundColor:'rgba(255,255,255,0.9)',
          borderColor:'#d0d7fb',
          borderWidth:1,
          borderRadius:1,
          shadowColor:'rgba(0,0,0,0.9)',
          textStyle:{
              color:'#555',
              fontFamily:'微软雅黑',
              fontSize:'12'
          },
          /*formatter: function (params) {
              console.log(params)
                        var tar = params[0];
                        var tar1 =params[1];
                        var num=tar.value;
                        var num1=tar1.value;
                        if(num==undefined){
                            num=0;
                        }
                        return tar.name + '<br/>' + tar.seriesName + ' : ' + num+'<br/>' + tar1.seriesName + ' : ' + num1;
                    }*/
      },
      legend:{
              show:true,
              itemWidth:10,
              itemHeight:10,
              itemGap:40,
              orient:'horizontal',
              y:'bottom',
              data:[
                  {
                      name:'已完成拜访量',
                      textStyle:{
                          color:'#666'
                      },
                      icon:'stack'
                  },
                  {
                      name:'计划拜访量',
                      textStyle:{
                          color:'#666'
                      },
                      icon:'stack'
                  }
              ]
          },
      calculable : true,
      xAxis : [
          {
              type : 'category',
              data: trend.periods,
              axisLabel: {
                            show: true,
                            interval:0,
                            textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑',
                                align:'center'
                            },
                            formatter:function(item){
                            	var periodType = $("input[name='periodType']:checked").val();
                            	if(periodType==3 && item.indexOf('-')>=0)
                        		{
                            		var from = item.split('-')[0].substr(5);
                            		var through = item.split('-')[1].substr(5);
                            		return from+"-"+through;
                        		}
                            	return item;
                            }
                        },
                        axisLine:{
                          show:true,
                          lineStyle:{
                              color: '#f1f1f1',
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
                        name:'拜访数量（个）',
                        position:'left',
                        nameLocation:'middle',
                        nameGap:40,
                        nameRotate:90,
                        nameTextStyle:{
                          color:"#666",
                          fontFamily:'微软雅黑'
                        },
                        type : 'value',
                        axisLabel: {
                            show: true,
                           textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑'
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
              name:'已完成拜访量',
              type:'bar',
              barWidth:"10",//柱图宽度
              data: trend.complete,
              itemStyle: {
                          normal: {
                              color: function(params) {
                                  if(flag==mark){
                                      var colorList = [
                                       '#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#9d9cf4'
                                  ];
                              }else{
                                      var colorList = [
                                        '#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#d2d7fb','#9d9cf4'
                                  ];
                              }
                                     
                                   return colorList[params.dataIndex]
                                                    }
                          },
                          emphasis:{
                              color:'#aaaef6'
                          }
                      },
          },
          {
              name:'计划拜访量',
              type:'bar',
              barWidth:"10",//柱图宽度
              data:trend.plan,
              itemStyle: {
                          normal: {
                              color: function(params) {
                                  if(flag==1){
                                      var colorList = [
                                      '#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#67d8ef'
                                  ];
                              }else{
                                      var colorList = [
                                      '#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#b0f2f9','#67d8ef'
                                  ];
                              }
                                     
                                   return colorList[params.dataIndex]
                                                    }
                          },
                            emphasis:{
                                  color:'#56e3f2'
                              }
                      },
          }
      ]
    };
    myChart.setOption(option, true);
  }
 
  //已完成拜访量
  var myChart = echarts.init(document.getElementById('visitCompleted')); 
  var option = {
      tooltip : {
          trigger: 'axis',
          axisPointer:{  //删除中轴线
              type:'none'
          },
          padding:8,
          backgroundColor:'rgba(255,255,255,0.9)',
          borderColor:'#d0d7fb',
          borderWidth:1,
          borderRadius:1,
          shadowColor:'rgba(0,0,0,0.9)',
          textStyle:{
              color:'#555',
              fontFamily:'微软雅黑',
              fontSize:'12'
          }
      },
      calculable : true,
      xAxis : [
          {
              type : 'category',
              data : ['电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务'],
              axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑',
                                align:'center'
                            },
                             formatter:function(value)  
                                 {  
                                     return value.split("").join("\n");  
                                 } 
                        },
                        axisLine:{
                          show:true,
                          lineStyle:{
                              color: '#f1f1f1',
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
                        name:'拜访数量（个）',
                        position:'left',
                        nameLocation:'middle',
                        nameGap:40,
                        nameRotate:90,
                        nameTextStyle:{
                          color:"#666",
                          fontFamily:'微软雅黑'
                        },
                        type : 'value',
                        axisLabel: {
                            show: true,
                           textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑'
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
              name:'已完成拜访量',
              type:'bar',
              barWidth:"10",//柱图宽度
              data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,55],
              itemStyle: {
                            normal: {
                              color: '#949af4'
                            },
                          emphasis:{
                              color:'#aaaef6'
                          }
                        },
          }
      ]
  };
   myChart.setOption(option, true);
  //计划拜访量
  var myChart = echarts.init(document.getElementById('visitPlan')); 
  var option = {
      tooltip : {
          trigger: 'axis',
          axisPointer:{  //删除中轴线
              type:'none'
          },
          padding:8,
          backgroundColor:'rgba(255,255,255,0.9)',
          borderColor:'#b0f2f9',
          borderWidth:1,
          borderRadius:1,
          shadowColor:'rgba(0,0,0,0.9)',
          textStyle:{
              color:'#555',
              fontFamily:'微软雅黑',
              fontSize:'12'
          }
      },
      calculable : true,
      xAxis : [
          {
              type : 'category',
              data : ['电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务','电子商务'],
              axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑',
                                align:'center'
                            },
                            formatter:function(value)  
                                 {  
                                     return value.split("").join("\n");  
                                 } 
                        },
                        axisLine:{
                          show:true,
                          lineStyle:{
                              color: '#f1f1f1',
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
                        name:'拜访数量（个）',
                        position:'left',
                        nameLocation:'middle',
                        nameGap:40,
                        nameRotate:90,
                        nameTextStyle:{
                          color:"#666",
                          fontFamily:'微软雅黑'
                        },
                        type : 'value',
                        axisLabel: {
                            show: true,
                           textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑'
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
              name:'计划拜访量',
              type:'bar',
              barWidth:"10",//柱图宽度
              data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3,55],
              itemStyle: {
                            normal: {
                              color: '#b0f2f9'
                            },
                          emphasis:{
                              color:'#56e3f2'
                          }
                        },
          }
      ]
  };
  myChart.setOption(option, true);
  $("#visitPlan").hide();



  $(window).resize(function(event) {
    //visitTrend(1);
  });
// 圆饼图 方法
//data_id  id
//too_color  划过框边框颜色
//data_color  圆环色块
//line_color 
//pie_data 显示数据 data
//data_radius 圆环圆饼类型
// rose  是否为玫瑰图
function data_pie(data_id,too_color,data_color,pie_data,data_radius,rose){
    var option = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)",
        backgroundColor:"rgba(255,255,255,0.9)",
        borderColor:too_color,
        borderWidth:"1px",
        padding:8,
        minAngle:10,
        textStyle :{
            color :"#666",
            fontSize:"10px",
        },
        extraCssText :'box-shadow:0 2px 4px 0 rgba(0,0,0,0.10);',
        extraCssText :'text-align:left;'
    },

    series: [
        {
            name:'项目拜访',
            type:'pie',
            selectedMode: 'single',
            selectedOffset :0,
            radius: data_radius,
            startAngle:0,
            "itemStyle":{
                "normal":{
                    "borderWidth":3,
                    "borderColor":"#fff",
                }
            },
            data:pie_data,
            roseType:rose,
             label: {
                normal: {
                    formatter:"{b}",
                    position:"outside",
                    textStyle: {
                        color: 'rgba(0, 0, 0, 1)'
                    }
                },
            },
            labelLine: {
                normal: {
                    smooth: 0.2,
                    length: 10,
                    length2: 15
                }
            },
        }
    ],
    color:data_color

    };
var sdata_id = echarts.init(document.getElementById(data_id));
sdata_id.setOption(option, true);
}


// tab点击事件
  $(".vertical_tab li").click(function(event) {
    $(".vertical_tab li").removeClass("active");
    $(this).addClass("active");
      if ($(this).hasClass("last")) {
      $("#visitCompleted").hide();
      $("#visitPlan").show();
    }else{
      $("#visitCompleted").show();
      $("#visitPlan").hide();
    }
  });

  //导航 日期切换
  $("input[type='radio']").click(function(event) {
  $(this).parent().siblings().find("input").removeAttr("checked");
  });
$('.quarterly_btn input').click(function(event) {
  $('.visitdata_quarterly ').show();
  $('.visitdata_quarterly').siblings("dd").hide();
});
$('.month_btn input').click(function(event) {
  $('.visitdata_month').show();
  $('.visitdata_month').siblings("dd").hide();
});
$('.week_btn input').click(function(event) {
  $('.visitdata_week').show();
  $('.visitdata_week').siblings("dd").hide();
});

// 周选择












})
