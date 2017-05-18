$(function(){
   visitTrend(1);
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
              data : ['2014年Q4','2015年Q1','2015年Q2','2015年Q3','2015年Q4','2016年Q1','2016年Q2','2016年Q3','2016年Q4','2017年Q1','2017年Q2','2017年Q3','2017年Q4'],
              axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#666',
                                fontFamily:'微软雅黑',
                                align:'center'
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
              data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3,33],
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
    visitTrend(1);
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
//  sec1
var sec1_radius=[0,"60%"];
var sec1_data=[
        {value:335, name:'项目拜访'},
        {value:679, name:'非项目拜访访'},
      ]
data_pie("project_visit","#5ceaf0",['#90e6fb','#ff94b1'],sec1_data,sec1_radius,false);

// sec2
var sec2_radius=["20%","60%"];
var sec2_data=[
                {value:335, name:'尚未融资1'},
                {value:310, name:'已被收购3'},
                {value:234, name:'preBsdfd'},
                {value:135, name:'不明确'},
                {value:200, name:'preIPO'},
                {value:50, name:'新三板ff'},
                {value:335, name:'尚未融资f'},
                {value:310, name:'已被收购g'},
                {value:234, name:'preBc'},
                {value:135, name:'不明确q'},
                {value:200, name:'preIPO'},
                {value:50, name:'新三板d'},
                {value:335, name:'尚未融资b'},
                {value:310, name:'已被收购v'},
                {value:234, name:'preB'},
                {value:135, name:'不明确s'},
                {value:200, name:'preIPObb'},
                {value:50, name:'新三板'}
              ]
var color_array = ['#8cecf8','#f9a4cf','#60dcff','#9ea7ff','#4fc3f9','#fcaccb','#91a9ff','#ffb4b3'];
data_pie("project_visit_round","#5ceaf0",color_array,sec2_data,sec2_radius,"area");

// sec3
var sec3_radius=["28%","60%"];
var sec3_data=[
                {value:335, name:'记录未缺失'},
                {value:100, name:'记录缺失'}
              ]
data_pie("project_visit_miss","#aaa9fe",['#afabff','#ddd'],sec3_data,sec3_radius,false);
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