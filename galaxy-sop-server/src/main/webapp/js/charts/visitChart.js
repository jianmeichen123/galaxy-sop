$(function(){

	var datePeriod;
	var dateVisitPeriod;
//	季度展示
	function time_show(){
		var str;
		var v_str;
		var three_str;
		var val = $("input[name='periodType']:checked").next().text();
		if(val == "季度"){
			str = $("#quarterly_start_data").val()+$('#s_quarterly option:selected').text();
			three_str="季度";
			v_str = str;
			loadTrendData(1);
		}
		if(val == "月"){
			str = $("#month_start_data").val();
			three_str="月";
			v_str = str;
			loadTrendData(1);
		}
		if(val == "周"){
			loadTrendData(0);
			str = dateVisitPeriod.startTime.split(" ")[0]+"至"+dateVisitPeriod.endTime.split(" ")[0];
			v_str = dateVisitPeriod.startTime.split(" ")[0]+"至"+dateVisitPeriod.endTime.split(" ")[0];
			
			three_str="周";
			
		}
		$(".visit_period_desc").text('('+str+')');
		$(".period_desc").text('('+v_str+')');
		$(".visit_three .period_desc").text('('+three_str+')');
	}
	time_show();
	$("button[action='querySearch']").click(function(){
		time_show();
//		判断 是否为项目拜访
		var input_radio = $(this).parents(".search_adjust").siblings(".search_adjust").find("input[type='radio']")
		console.log(input_radio);
		$.each(input_radio,function(){
			if($(this).attr("checked")!=undefined&&$(this).val()=="on"){
				$('.visit_two ul').attr("class","fl_three clearfix");
				$('.visit_two ul li').show();
				return false;
			}else if($(this).attr("checked")=="checked"&&$(this).val()==1){
				$('.visit_two ul').attr("class","fl_two clearfix");
				$('.visit_two ul li').show();
				$('.visit_two ul li:first-child').hide();
				return false;
			}else if($(this).attr("checked")=="checked"&&$(this).val()==0){
				$('.visit_two ul').attr("class","fl_one clearfix");
				$('.visit_two ul li').hide();
				$('.visit_two ul li:last-child').show();
				return false;
			}
		})
		//判断是否查看投资经理
		var createdIdVal=$("select[name=createdId] option:selected").val();
		if(createdIdVal!="0"){
			$(".visit_four").hide();
		}else{
			$(".visit_four").show();
		}
		
	});
   var trend = {};
   loadTrendData(1);
   function getDatePeriod()
   {
	   var periodType = $("input[name='periodType']:checked").val();
	   //console.log("periodType="+periodType);
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
	   //console.log('getQuarterlyPeriod');
	   var endYear = $("#quarterly_start_data").val().replace('年','');
	   var startYear = endYear-3;
	   
	   var quarterly = $("select[name='s_quarterly']").val();
	   var startTime = new Date();
	   var endTime = new Date();
	   
	   startTime.setFullYear(startYear);
	   startTime.setDate(1);
	   
	   endTime.setFullYear(endYear);
	   
	   if(quarterly == 1)
	   {
		   startTime.setMonth(0);
		   
		   endTime.setMonth(2,31);
		  // endTime.setDate(31);
	   }
	   else if(quarterly == 2)
	   {
		   startTime.setMonth(3);
		   
		   endTime.setMonth(5,30);
		  // endTime.setDate(30);
	   }
	   else if(quarterly == 3)
	   {
		   startTime.setMonth(6);
		   
		   endTime.setMonth(8,30);
		   //endTime.setDate(30);
	   }
	   else if(quarterly == 4)
	   {
		   startTime.setMonth(9);
		   
		   endTime.setMonth(11,31);
		   //endTime.setDate(31);
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
	   endTime.setMonth(endDate.split('-')[1]-1,endDate.split('-')[2]);
	  // endTime.setDate(endDate.split('-')[2]);
	   endTime.setHours(23);
	   endTime.setMinutes(59);
	   endTime.setSeconds(59);
	   endTime.setMilliseconds(999);
	   
	   var startTime = endTime.getTime()- (9*7*24*3600*1000) +1;
	   
	   return {startTime:new Date(startTime).format('yyyy-MM-dd hh:mm:ss'), endTime:endTime.format('yyyy-MM-dd hh:mm:ss')};
   }
  
   function loadTrendData(status)
   {   //status=1,为季/月  ；0为周
	   bftjt.initTable();
	   
	   datePeriod = getDatePeriod();
	   dateVisitPeriod = getVisitDatePeriod();
	   trend = {
			periods:new Array(),
			plan : new Array(),
			complete : new Array()
	   };
	   var query = {
			startTimeFrom:datePeriod.startTime,
			startTimeThrough:datePeriod.endTime,
			startTime:dateVisitPeriod.startTime,
			endTime:dateVisitPeriod.endTime,
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

			   //计算计划拜访量总和  
			  			   var i=0;
			  			   var sumPlan=0;
			  			   for(var i=0;i<data.userData.tendency.length;i++){
			  				   sumPlan+=data.userData.tendency[i].count;
			  			   }
			   
			   
			   query.complete = 1;
			   
			   sendPostRequestByJsonObj(platformUrl.visitTrend,query,function(data){
				   if(data.userData.tendency)
				   {
					   var yData2 = new Array();
					   var j=0;
					   $.each(data.userData.tendency,function(){
						   var period = this.period;
						   var count = this.count;
						   var len = trend.periods.length;
						   var sum=0;
						   for(var i=0;i<len;i++)
						   {
							   if(trend.periods[i] == period)
							   {
								   trend.complete[i] = count;
							   }
							   sum+=trend.complete[i];
						   }
						   if(sum+sumPlan==0){
							   j++;
						   }
					   });
					   if(j>=trend.periods.length){
						   $(".visit_three .empty_data5").remove();
						   $("#visitTrend").hide();
						   $(".visit_three").append("<div class='vertical empty_data5'><p class='visit_nocon'>没有找到匹配的记录</p></div>")
						    
					   }else{
						   $(".visit_three .empty_data5").remove();
						   $("#visitTrend").show();
					   }
					   visitTrend(status);
				   }
				   
			   });
		   }
	   });
	  //拜访统计图在计划拜访tab查询,隐藏已完成拜访量图
	if($(".vertical_tab li.last").is(".active")){
		          $("#visitCompleted").hide();
				}
	//IE8兼容
	 if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
	  { 
		 $("#project_visit>div").remove();
		 $("#project_visit_round>div").remove();
		 $("#project_visit_miss>div").remove();
		 $("#visitTrend>div").remove();
		 visitTrend(status);
	  }
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
						 if(map.visitRate.split('.')[1].length==2){
							 var  persend =map.visitRate.split('.');
							 persend[1].split('');
 							 map.visitRate=persend[0]+'.'+persend[1].split('')[0]+"0%";
						 }
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
						         {value:map.isNoVisit, name:'非项目拜访'}
						  ];
						 if(map.isProVisit == 0){
							 sec1_data=[
						         {value:map.isNoVisit, name:'非项目拜访'}
							  ]
						 }else if(map.isNoVisit == 0){
							 sec1_data=[
						         {value:map.isProVisit, name:'项目拜访'}
							  ]
						 }
						// sec1
						 if(map.isProVisit==0&&map.isNoVisit==0){
							 $("#project_visit p").remove();
							 $("#project_visit div").remove();
							 $("#project_visit").addClass("empty_data1");
							 $("#project_visit").append("<p>没有找到匹配的记录</p>")
						 }else{
							 $("#project_visit p").remove();
							 $("#project_visit").removeClass("empty_data1");
							 var color_array1 = ['#90e6fb','#ff94b1'];
							 var normal=new Object();
							 normal.borderWidth=3;
							 normal.borderColor="#fff";
			                 var border_none =new Object();
							 if(sec1_data.length==1&&sec1_data[0].value!=0){
								 data_pie("project_visit","#5ceaf0",color_array1,sec1_data,sec1_radius,false,border_none);
							 }else{
								 data_pie("project_visit","#5ceaf0",color_array1,sec1_data,sec1_radius,false,normal);
							 }
						 }					   
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
							 if(str.name!=undefined&&str.value!=0){
								 sec2_data.push(str);
								 }
						 }
						 if(sec2_data.length<=0){
							 $("#project_visit_round p").remove();
							 $("#project_visit_round div").remove();
							 $("#project_visit_round").addClass("empty_data2");
							 $("#project_visit_round").append("<p>没有找到匹配的记录</p>")
						 }else{
							 $("#project_visit_round p").remove();
							 $("#project_visit_round").removeClass("empty_data2");
							 var color_array = ['#8cecf8','#f9a4cf','#60dcff','#9ea7ff','#4fc3f9','#fcaccb','#91a9ff','#ffb4b3'];
							 var normal=new Object();
							 normal.borderWidth=3;
							 normal.borderColor="#fff";
			                 var border_none =new Object();
							 if(sec2_data.length==1&&sec2_data[0].value!=0){
								 data_pie("project_visit_round","#5ceaf0",color_array,sec2_data,sec2_radius,"area",border_none);
							 }else{
								 data_pie("project_visit_round","#5ceaf0",color_array,sec2_data,sec2_radius,"area",normal);
							 }
							 
						 }
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
							 if(map.part == 0){
								 sec3_data=[
							         {value:map.nopart, name:'记录缺失'}
								  ]
							 }else if(map.nopart == 0){
								 sec3_data=[
							         {value:map.part, name:'记录未缺失'}
								  ]
							 }
						 if((map.part == 0 && map.nopart == 0)||map.part==undefined||map.nopart==undefined){
							 $("#project_visit_miss p").remove();
							 $("#project_visit_miss div").remove();
							 $("#project_visit_miss").addClass("empty_data3");
							 $("#project_visit_miss").append("<p>没有找到匹配的记录</p>")
						 }else{
							 $("#project_visit_miss p").remove();
							 $("#project_visit_miss").removeClass("empty_data3");
							 var normal=new Object();
							 normal.borderWidth=3;
							 normal.borderColor="#fff";
			                 var border_none =new Object();
							 if(sec3_data.length==1&&sec3_data[0].value!=0){
								 data_pie("project_visit_miss","#aaa9fe",['#afabff','#ddd'],sec3_data,sec3_radius,false,border_none);
							 }else{
								 data_pie("project_visit_miss","#aaa9fe",['#afabff','#ddd'],sec3_data,sec3_radius,false,normal);
							 }
							 
						 }
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
          borderRadius:2,
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
              /*itemWidth:10,
              itemHeight:10,*/
              itemGap:40,
              orient:'horizontal',
              y:'bottom',
              data:[
                    {
                        name:'已完成拜访量',
                        textStyle:{
                            color:'#666',
                            fontFamily:"宋体"
                        },
                        icon:'stack'
                        //icon:'image:///sop/img/legend01.png'
                    },
                    {
                        name:'计划拜访量',
                        textStyle:{
                            color:'#666',
                            fontFamily:"宋体"
                        },
                        icon:'stack'
                        //icon:'image:///sop/img/legend02.png'
                    }
                ]
            },
        calculable : true,
        grid:{
  			x:80,
  			y:30,
  			x2:50,
  			y2:60
  		},
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
                        nameGap:45,
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
                                  if(flag==mark){
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
    if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
	  { 
		option.tooltip.backgroundColor="#fff";
	  }
    myChart.setOption(option, true);
  }
 


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
function data_pie(data_id,too_color,data_color,pie_data,data_radius,rose,border){
    var option = {
    tooltip: {
        trigger: 'item',
        formatter: "{b}:<br/> {c} ({d}%)",
        backgroundColor:"rgba(255,255,255,0.9)",
        borderColor:too_color,
        borderWidth:1,
        borderRadius:2,
        shadowColor:'rgba(0,0,0,0.9)',
        padding:8,
        minAngle:10,
        textStyle :{
            color :"#333333",
            fontSize:"12",
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
            itemStyle:{
            	"normal":border
            },
            data:pie_data,
            roseType:rose,
             label: {
                normal: {
                    formatter:"{b}",
                    position:"outside",
                    textStyle: {
                        color: '#666'
                    }
                },
            },
            labelLine: {
                normal: {
                    smooth: 0.2,
                    length: 10,
                    length2: 15,
                }
            },
        }
    ],
    color:data_color

    };
if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
	  { 
		option.tooltip.backgroundColor="#fff";
	  }
var sdata_id = echarts.init(document.getElementById(data_id));
sdata_id.setOption(option, true);
}

  //导航 日期切换
  $("input[type='radio']").click(function(event) {
	  var sib_text= $(this).siblings("span").html()
	  if(sib_text=="季度"||sib_text=="月"||sib_text=="周"){
	    var myyear = new Date().getFullYear();
		var mymonth = new Date().getMonth()+1;
		$("#quarterly_start_data").val(myyear+"年");
		$("select[name='s_quarterly']").val(Math.ceil(mymonth/3));
		$(".change_month_visit").val(myyear+"年"+mymonth+"月");
		setDateRange(new Date(),"INIT");
	  }
	
	    
	$(this).attr("checked",true);
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












});





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
		   
		   endTime.setMonth(2,31);
		   //endTime.setDate(31);
	   }
	   else if(quarterly == 2)
	   {
		   startTime.setMonth(3);
		   
		   endTime.setMonth(5,30);
		   //endTime.setDate(30);
	   }
	   else if(quarterly == 3)
	   {
		   startTime.setMonth(6);
		   
		   endTime.setMonth(8,30);
		   //endTime.setDate(30);
	   }
	   else if(quarterly == 4)
	   {
		   startTime.setMonth(9);
		   
		   endTime.setMonth(11,31);
		   //endTime.setDate(31);
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
	   endTime.setMonth(endDate.split('-')[1]-1,endDate.split('-')[2]);
	   //endTime.setDate(endDate.split('-')[2]);
	   endTime.setHours(23);
	   endTime.setMinutes(59);
	   endTime.setSeconds(59);
	   endTime.setMilliseconds(999)
	   
	   var startTime = endTime.getTime()- (9*7*24*3600*1000) +1;
	   
	   return {startTime:new Date(startTime).format('yyyy-MM-dd hh:mm:ss'), endTime:endTime.format('yyyy-MM-dd hh:mm:ss')};
}

function getVisitDatePeriod()
{
	   var periodType = $("input[name='periodType']:checked").val();
	   //console.log("periodType="+periodType);
	   if(periodType == 3)
	   {
		   return getVisitWeeklyPeriod();
	   }
	   else if(periodType == 2)
	   {
		   return getVisitMonthlyPeriod();
	   }
	   return getVisitQuarterlyPeriod();
}
/**
 * 获取季度起止时间
 * @returns
 */
function getVisitQuarterlyPeriod()
{
	   //console.log('getQuarterlyPeriod');
	   var endYear = $("#quarterly_start_data").val().replace('年','');
	   var startYear = endYear;
	   var quarterly = $("select[name='s_quarterly']").val();
	   var startTime = new Date();
	   var endTime = new Date();
	   
	   startTime.setFullYear(startYear);
	   startTime.setDate(1);
	   
	   endTime.setFullYear(endYear);
	   
	   if(quarterly == 1)
	   {
		   startTime.setMonth(0);
		   
		   endTime.setMonth(2,31);
		   //endTime.setDate(31);
	   }
	   else if(quarterly == 2)
	   {
		   startTime.setMonth(3);
		   
		   endTime.setMonth(5,30);
		   //endTime.setDate(30);
	   }
	   else if(quarterly == 3)
	   {
		   startTime.setMonth(6);
		   
		   endTime.setMonth(8,30);
		   //endTime.setDate(30);
	   }
	   else if(quarterly == 4)
	   {
		   startTime.setMonth(9);
		   
		   endTime.setMonth(11,31);
		   //endTime.setDate(31);
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
function getVisitMonthlyPeriod()
{
	   console.log('getMonthlyPeriod');
	   var selected = $("#month_start_data").val().replace('月','');
	   var endYear = selected.split('年')[0];
	   var startYear = endYear;
	   
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
function getVisitWeeklyPeriod(){
	   console.log('getWeeklyPeriod');
	   var endDate = $('.visitweekEndDatepicker').val();
	   var endTime = new Date();
	   endTime.setFullYear(endDate.split('-')[0]);
	   endTime.setMonth(endDate.split('-')[1]-1,endDate.split('-')[2]);
	   //endTime.setDate(endDate.split('-')[2]);
	   endTime.setHours(23);
	   endTime.setMinutes(59);
	   endTime.setSeconds(59);
	   endTime.setMilliseconds(999);
	   
	   var startDate = $('.visitweekStartDatepicker').val();
	   var startTime = new Date();
	   startTime.setFullYear(startDate.split('-')[0]);
	   startTime.setMonth(startDate.split('-')[1]-1);
	   startTime.setDate(startDate.split('-')[2]);
	   startTime.setHours(00);
	   startTime.setMinutes(00);
	   startTime.setSeconds(00);
	   startTime.setMilliseconds(00);
	   
	   //var startTime = endTime.getTime()- (9*7*24*3600*1000) +1;
	   
	   return {startTime:startTime.format('yyyy-MM-dd hh:mm:ss'), endTime:endTime.format('yyyy-MM-dd hh:mm:ss')};
}

