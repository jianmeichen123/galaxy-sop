<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="common/header_report.jsp" flush="true"></jsp:include>
<%
String endpoint = (String)application.getAttribute(com.galaxyinternet.framework.core.oss.OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
// java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
// System.out.println("------------------>>" + endpointMap.get("galaxy.project.sop.endpoint"));
%>
<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="common/menu.jsp" flush="true"></jsp:include>
	<!--右侧-->
	<div class="rit rit_executive">
		<div class="top top_rit_executive" style="display:none" id="ceo_cat">
			<!-- <b class="sj ico null">三角</b> -->
			<dl>
				<dt>CEO评审排期 
					<a href="/report/html/ceopsMeeting.html"  data-btn="ceops" class="more null position_10">more</a>
				
				</dt>
				<dd>
					<table id="ceopsMeeting" width="100%" cellspacing="0"
						cellpadding="0" class="index">
						<thead>
							<tr>
								<th>序号</th>
								<th>项目名称</th>
								<th>上次过会时间</th>
								<th>过会次数</th>
							</tr>
						</thead>
						<tbody id="ceopsbodytop">
						</tbody>
					</table>
				</dd>
				<!-- <dd class="clearfix">
					<a href="/report/html/ceopsMeeting.html" data-btn="ceops" class="more null">more</a>
				</dd> -->
			</dl>
		</div>
		<!--时间-->
        <div class="top"   style="height: 178px;" id="dsz_cat">
        	<b class="sj ico null">三角</b>
            <div class="tody ico">
            	<p class="time"></p>
                <p class="date"></p>
            </div>
          	<div id="top">
          		<div style="font-size:12px;font-family:'宋体';text-align:center;color:#7a8798;hight:50px;line-height:50px">无日程安排</div>
          	</div>
          	<div class="morebox"><a href="javascript:;" class="add_schedule blue"  onclick="shecudle();">添加日程</a></div>
        </div>
		 
		<!--立项排期会-->
		<dl>
			<dt>立项会排期
				<a href="/report/html/projectMeeting.html" data-btn="project"
					class="more position_11 null">more</a>
			</dt>
			<dd>
				<table id="projectMeeting" width="100%" cellspacing="0"
					cellpadding="0" class="index">
					<thead>
						<tr>
							<th>序号</th>
							<th>项目名称</th>
							<th>上次过会时间</th>
							<th>过会次数</th>
						</tr>
					</thead>
					<tbody id="tlbody">
					</tbody>
				</table>
			</dd>
			
		</dl>
		<!--投决会排期-->
		<dl class="tjh_block">
			<dt>投决会排期
				 <a href="/report/html/voteMeeting.html" data-btn="vote"  class="more position_12 null">more</a>
			</dt>
			<dd>
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>序号</th>
							<th>项目名称</th>
							<th>上次过会时间</th>
							<th>过会次数</th>
						</tr>
					</thead>
					<tbody id="tbody">

					</tbody>
				</table>
			</dd>
		</dl>
	</div>
	
	
	
	<!--中部内容-->
	<div class="min">
		<!--表格列表-->
		<div class="tablist clearfix">
			<!--左侧列表-->
			<div class="l l_executive">
				<dl>
					<dt>
						<h3 class="ico t7">事项预览</h3>
					</dt>
					<dd>
						<div class="l_previewTop">
						    <div style="margin-left:25%; display:none"><span vertical-align:middle>今日会议总数<a id= "meeting_number_today" href="javascript:;" >0</a>个</span></div>
							<span class='bj_pagebox'>今日：立项会　<a id="lxh_number_today" href="javascript:;">0</a>个</span> 
							<span class='bj_pagebox bj_pagebox_left'>投决会　<a id="tjh_number_today" href="javascript:;">0</a>个</span>
							<span class='bj_pagebox bj_pagebox_left'>评审会　<a id="psh_number_today" href="javascript:;">0</a>个</span>
						</div>
						<div class="l_previewBottom clearfix">
							<div class="l_previewBottom_l l_previewBottom_b">
								<span><p>立项会</p><p>排期等待</p></span><a href="javascript:;" id="lxh_eduling_wait"></a>
							</div>
							<div class="l_previewBottom_r l_previewBottom_b">
								<span><p>投决会</p><p>排期等待</p></span><a href="javascript:;" id="tjh_eduling_wait"></a>
							</div>
							<div class="l_previewBottom_r l_previewBottom_b">
								<span><p>评审会</p><p>排期等待</p></span><a href="javascript:;" id="psh_eduling_wait"></a>
							</div>
						</div>
					</dd>
				</dl>
				
				
				<dl>
					<dt>
						<h3 class="ico t8">项目进度</h3>
						<a href="javascript:;" class="more position_0 null" id="more_xmjd">more</a>
					</dt>
					<dd style="position: relative;">
						<div class="mask_platform_progress"></div>
						<div id="container_progress"
							style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
					</dd>
				</dl>
				
				
				<dl class="executive_last">
					<dt>
						<h3 class="ico t9">投资资金</h3>
					</dt>
					<dd>
						<div id="charts_investment"
							style="min-width:300px; height: 200px;padding-top:5px;"></div>
					</dd>
				</dl>
			</div>
			
			
			
			<!--右侧列表-->
			<div class="r r_executive">
				<dl class="r_news">
					<dt>
						<h3 class="ico t10">绩效考核</h3>
						<span class="more null position_0" id="platform_jxkh_more" style="cursor: pointer;">more</span>
					</dt>
					<dd>
						<div id="container_kpi" style="min-width:300px; height: 162px;padding-top:5px;"></div>
					</dd>
				</dl>
				
				
				
				<dl>
					<dt>
						<h3 class="ico t11">项目历时</h3>
					</dt>
					<dd style="position: relative;">
						<div class="mask_platform_time"></div>
						<div id="container_time"
							style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
					</dd>
				</dl>
				
				
				<dl class="tool_radius executive_last">
					<img src="<%=request.getContextPath()%>/img/sy.png" alt="" />
					<dt>
						<h3 class="ico t12">项目运营</h3>
					</dt>
					<dd>
						<div id="container_operation" style="min-width:300px; height: 200px;padding-top:5px;"></div>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</div>
<jsp:include page="common/footer.jsp" flush="true"></jsp:include>



<script type="text/javascript">
	var forwardParam = {
			progressParam : undefined,
			timeParam : undefined
	};
</script>

<script src="<%=request.getContextPath() %>/js/charts/projectInvestment.js"></script>
<script src="<%=request.getContextPath() %>/js/charts/projectProgress.js"></script>
<script src="<%=request.getContextPath() %>/js/report/platform.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/init.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/sopUserSchedule.js" type="text/javascript"></script>


<script>

var isGG = true;
if(roleId == '1' || roleId == 1 || roleId == '2' || roleId == 2){
	isGG = true;
}else{
	isGG = false;
}

var kpiurl = platformUrl.deptkpi;
if(!isGG)  kpiurl = platformUrl.userkpi;

$(function() {
	
	$('#platform_jxkh_more').click(function(){
	    window.location.href=path + "/galaxy/kpireport/touserkpi?guid="+userId+"&sid="+sessionId;
	    //$("#platform_jxkh_more").attr('href' , path + "/galaxy/report/kpi?guid="+userId+"&sid="+sessionId +"#gg_jxkh_u");//绩效考核链接
	})
});


function load_data_chart_kpi(){
	var obj = {pageNum:0,pageSize:5};
	sendPostRequestByJsonObj(kpiurl,obj,function(data){
		var result = data.result.status;
		if(result == "ERROR"){ //OK, ERROR
			layer.msg(data.result.message);
			return;
		}else{
			var entityList = data.pageList.content;
			var re = [];
	   		var ghl = [];
	   		var categories = [];
	   		for(var i=0;i<entityList.length;i++){
	   			var rate = entityList[i].ghlRate*100;
	   			re.push(entityList[i].completed);
	   			ghl.push(parseFloat(rate.toFixed(2)));
	   			categories.push(isGG ? entityList[i].departmentName : entityList[i].realName);
	   		}
	   		containerKpiOptions.series[0].data = re;
	   		containerKpiOptions.series[1].data = ghl;
	   		containerKpiOptions.xAxis.categories = categories;
	   		//containerKpiOptions.xAxis.labels.staggerLines = (categories.length>3) ? 2 : 1;
	   		var chart = new Highcharts.Chart(containerKpiOptions);
			if(re==''&& ghl==''){
	   			$('#container_kpi').html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
	   		}
		}
   	});
}



function load_data_chart_project_time(){
	sendPostRequestByJsonObj(platformUrl.progressDurationList,null,function(data){
		var result = data.result.status;
		if(result == "ERROR"){ //OK, ERROR
			layer.msg(data.result.message);
			return;
		}else{
			var entityList = data.pageList.content;
			
			var re = [];
			var color=['#587edd','#49ceff','#00bdf4','#88dfd8','#4490d2','#bee6d5','#6ebdea','#ff9c89','#62d1b0'];
			//var selectedPie = "";
			var totalDay = 0;
			for(var i=0;i<entityList.length;i++){
				totalDay += entityList[i].dayLine;
			}
			totalDay_all=totalDay;
			for(var i=0;i<entityList.length;i++){
				var rate = entityList[i].dayLine/totalDay;
				var tmp = {
						name : entityList[i].progressName,
						color :color[i],
						y : entityList[i].dayLine,
						rate : parseFloat(rate.toFixed(1))
				};
				if(i==0){
					//tmp.sliced=true;
					//tmp.selected=true;
					//selectedPie = {num:tmp.y,rate:rate};
				}
				re.push(tmp);
			}
			//console.log(re);
			//console.log(totalDay);
			containerProjectTimeOptions.series[0].data = re;
			containerProjectTimeOptions.title.text = "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>";
			containerProjectTimeOptions.plotOptions.pie.events.click = function(e){
				//console.log(e.point.name);
				//console.log(chart.title);
				chart.setTitle(
						{
							text: "<span style='color:#4490d2'>"+ e.point.y +"天</span>"+"<br/>"+"<span>"+ parseFloat(e.point.percentage.toFixed(1)) +"%</span>",
							y:-5,
							x:-95
						}
				);
				chart.redraw();
				e.point.select();
				
				//如果没有pie块被选择，返回到只显示数量状态。
				var selected_curr = chart.getSelectedPoints();
				if(selected_curr.length==0){
					chart.setTitle(
	    					{
	    						text: "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>",
	    						y:5,
	    						x:-90
	    					}
	    			);
				}
				judgeProgress($.trim(e.point.name),'time');
				
			};
			containerProjectTimeOptions.plotOptions.pie.point.events.legendItemClick = function(e){
				//console.log(e);
				//chart.setTitle({text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>"});
	    		//chart.redraw();
				chart.setTitle(
						{
							text: "<span style='color:#4490d2'>"+ e.target.y +"天</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>",
							y:-5,
							x:-95
						}
				);       		
				chart.redraw();
				e.target.select();
				var selected_curr = chart.getSelectedPoints();
				//console.log(selected_curr)
				if(selected_curr.length==0){
					chart.setTitle(
	    					{
	    						text: "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>",
	    						y:5,
	    						x:-95
	    					}
	    			);
				}
				judgeProgress($.trim(e.target.name),'time');
				return false;
				//如果没有pie块被选择，返回到只显示数量状态。
			
			};
			var chart = new Highcharts.Chart(containerProjectTimeOptions);
		}
   	});
	
}



//绩效考核，前5
var containerKpiOptions = {
    chart: {
    	renderTo:'container_kpi',
        zoomType: 'xy',
        backgroundColor: 'rgba(255, 255, 255, 0)',
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
//    	 plotLines: [{
//             value: -1,
//             width: 1,
//             color: '#808080'
//         }],
        lineWidth: 1,
        lineColor: "#edeff5",
        tickWidth: 0,
        labels: {
            y: 20, //x轴刻度往下移动20px
            staggerLines:1,
            style: {
                color: '#7a8798',//颜色
                fontFamily:'宋体',
            }
        }
        //categories: ['物联网', 'O2O事业部', '互联网服装', '互联网金融', '互联网工业']
    },
    yAxis: [
			{ // Secondary yAxis
			    gridLineColor: '#f6f7fa',
			    gridLineWidth: 1,
			    title: {
			        text: '项目数(个)',
			        style: {
			            color: '#7a8798'
			        }
			    },
			    labels: {
			        style: {
			            color: '#4572A7'
			        }
			    },
			},
            { // Primary yAxis
        gridLineColor: '#f6f7fa',
        gridLineWidth: 1,
        opposite: true,
        min:0,
        max:100,
        //lineColor: "#edeff5",
        //lineWidth: 1,
        labels: {
            format: '{value} %',
            style: {
                color: '#999',//颜色
                fontFamily:'宋体',  //字体
            }
        },
           title: {
                text: '过会率(%)',
                style: {
                    color: '#7a8798'
                }
            }
        }],
            plotOptions: {
        column: {
            pointWidth: 20
        },
    },
    tooltip: {
        shared: true
    },
    legend: {
        itemMarginTop:-10,
        itemMarginBottom:-10,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'top',
        borderWidth: 0,
        itemStyle:{
            fontWeight:'normal',
            color:'#7a8798',
        },
    },
    series: [{
        name: '项目数',
        color: '#9dd2fc',
        type: 'column',
        //data: [20, 18, 9, 7, 18],
        tooltip: {
            valueSuffix: '个'
        }
    },{
        lineWidth:3,
        name: '过会率',
        yAxis: 1,
        color: '#88dfd8',
        type: 'spline',
        //data: [10.5, 15.8, 60.1, 50.9, 30.3],
        tooltip: {
            valueSuffix: '%'
        }
    }]
};


//项目历时
var containerProjectTimeOptions = {
     chart: {
     	renderTo: 'container_time',
         plotBackgroundColor: null,
         plotBorderWidth: null,
         plotShadow: false,
         backgroundColor: 'rgba(255, 255, 255, 0)',
     },
     title: {
         text: "<span style='color:#4490d2'>"+'60天'+"</span>"+"<br/>"+"<span>"+'45%'+"</span>",
         verticalAlign:'middle',
         y:5,
         x:-90,
         style:{
             fontFamily:'微软雅黑',
             color:'#4490d2',
             fontWeight:'bold',
             cursor:'pointer'
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
     tooltip: {
         pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
	             formatter:function(){
	                 return this.point.percentage.toFixed(1)+"%";
	             },
	             connectorWidth:0,
	             connectorPadding:0,
	             distance:120
	         },
	         showInLegend: true,
	         events :{
		      	click : function(e){
		      		
		      	}
		     },
		     point:{
		      	events:{
			       	click: function(e){
			       		//console.log(e.point.name);
			       		//this.title="123";
			       	},
			       	legendItemClick : function(e){
			       	}
				}	
		     }
	     }
	 },
	 legend: {                                                
	     layout: 'horizontal',                                  
	     floating: false,                                       
	     align: 'right',
	     verticalAlign: 'middle',
	     borderWidth: 0,
	     width:200,
	     padding:-25,
	     itemWidth:90,
	     //minHeight:100,
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
         name: '项目历时',
         data: [
             {name:'接触访谈',color:'#c5b33b',y:8},
             {name: '内部评审',color:'#cbc63a',y: 10},
             { name:'CEO评审',color:'#bac73b',y:16},
             { name:'立项会',color:'#a6cb2b',y:20},
             { name:'投资意向书',color:'#69bf56',y: 30},
             { name:'尽职调查',color:'#58b260',y:40},
             { name:'投决会',color:'#36afa2',y:50},
             { name:'投资协议',color:'#159196',y:55},
             { name:'股权交割',color:'#4790d2',y:60},
         ],
         dataLabels: {
             enabled: false, 
         }
     }]
 };
    
</script>