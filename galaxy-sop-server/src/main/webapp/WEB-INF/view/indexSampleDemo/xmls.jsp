<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<%
String endpoint = (String)application.getAttribute(com.galaxyinternet.framework.core.oss.OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
// java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
// System.out.println("------------------>>" + endpointMap.get("galaxy.project.sop.endpoint"));
%>
<style>
.pagebox .ritRre {
    float: right;
    width: 17.8125%;
    margin-top: 20%;
    margin-right: 40%;
}
</style>
<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="demoMenu.jsp" flush="true"></jsp:include>
	<!--右侧-->
	<div class="ritRre rit_executive">
	    <h2 class="chart_name">项目历时</h2>
	    <center>
		<div>
		    <div class="mask_platform_time"></div>
			<div id="container_time"
				style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -95%"></div>
		</div>
		</center>
	</div>
</div>
<%-- <script src="<%=request.getContextPath() %>/js/common.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts_ext.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/init.js" type="text/javascript"></script> --%>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
load_data_chart_project_time();//项目历时

function load_data_chart_project_time(){
	//var obj ={url:platformUrl.progressDurationList};
	var obj ={url:reportEndpointURL+"/demo/progressDurationList"};
	obj.data={model:2,sdate:'-1',edate:'-1',projectProgress:'projectProgress:10',projectStatus:-2};
	obj.contentType="application/json";
	ajaxCallback(obj,function(data){
		var result = data.result;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var entityList = data.mapList;
		var re = [];
		var color=['#587edd','#49ceff','#00bdf4','#88dfd8','#4490d2','#bee6d5','#6ebdea','#ff9c89','#62d1b0'];
		//var selectedPie = "";
		var totalDay = 0;
		for(var i=0;i<entityList.length;i++){
			totalDay += entityList[i].duration_day;
		}
		totalDay_all=totalDay;
		for(var i=0;i<entityList.length;i++){
			var rate = entityList[i].duration_day/totalDay;
			var tmp = {
					name : entityList[i].project_progress_name,
					color :color[i],
					y : entityList[i].duration_day,
					rate : parseFloat(rate.toFixed(1))
			};
			if(i==0){
				//tmp.sliced=true;
				//tmp.selected=true;
				//selectedPie = {num:tmp.y,rate:rate};
			}
			re.push(tmp);
		}
		containerProjectTimeOptions.series[0].data = re;
		containerProjectTimeOptions.title.text = "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>";
		containerProjectTimeOptions.plotOptions.pie.events.click = function(e){
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
	});
}	
//cutStr(5,'cutstr');

});

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
    
//项目历时无数据样式
if($("#container_time .highcharts-title tspan").text()=="0天" || $("#container_time .highcharts-title span").text()=="0天"){
	$(".mask_platform_time").show();
	$('#container_time').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            backgroundColor: 'rgba(255, 255, 255, 0)',
        },
        title: {
            text: "<span style='color:#e9ebf2'>"+'0天'+"</span>",
            verticalAlign:'middle',
            y:5,
            x:-95,
            style:{
                fontFamily:'微软雅黑',
                color:'#e9ebf2',
                fontWeight:'bold',
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
        tooltip: false,
        plotOptions: {
        pie: {
            borderWidth: 0,
            allowPointSelect: false,
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
            showInLegend: true
        }
    },
    legend: {                                                
        layout: 'horizontal',                                  
        floating: false,                                       
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0,
        itemWidth:90,
        width:200,
        padding:-25,
        minHeight:100,
        itemStyle:{
            fontWeight:'normal',
            color:'#7a8798',
        },
    },            

        series: [{
            type: 'pie',
            size:'140%',
            innerSize :'70%',
            name: '项目退出占比',
            data: [
                {name:'接触访谈',color:'#e9ebf2',y:8},
                {name: '内部评审',color:'#e9ebf2',y: 0},
                { name:'CEO评审',color:'#e9ebf2',y:0},
                { name:'立项会',color:'#e9ebf2',y:0},
                { name:'投资意向书',color:'#e9ebf2',y: 0},
                { name:'尽职调查',color:'#e9ebf2',y:0},
                { name:'投决会',color:'#e9ebf2',y:0},
                { name:'投资协议',color:'#e9ebf2',y:0},
                { name:'股权交割',color:'#e9ebf2',y:0},
            ],
            dataLabels: {
                enabled: false, 
            }
        }]
    });
	
}
function judgeProgress(name,flag){
	
	var param;
	if(typeof(name) != 'undefined'){
		if(name == '接触访谈'){
			param = 1;
		}else if(name == 'CEO评审'){
			param = 3;
		}else if(name == '投资意向书'){
			param = 5;
		}else if(name == '投资决策会'){
			param = 7;
		}else if(name == '股权交割'){
			param = 9;
		}else if(name == '内部评审'){
			param = 2;
		}else if(name == '立项会'){
			param = 4;
		}else if(name == '尽职调查'){
			param = 6;
		}else if(name == '投资协议'){
			param = 8;
		}else if(name=="投后运营"){
			param = 10;
		}
	}
	
	if(flag=='progress'){
		forwardParam.progressParam = param;
		$("#container_progress .highcharts-title tspan").click(function(){
			
			if($("#container_progress .highcharts-title tspan").text()== (totalNum_all+'个')){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.progressParam){
					url += "?forwardProgress=" + forwardParam.progressParam ;
				}
				forwardWithHeader(url);
			}
			
		})
		$("#container_progress .highcharts-title span").click(function(){
			if($("#container_progress .highcharts-title span").text()== (totalNum_all+'个')){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.progressParam){
					url += "?forwardProgress=" + forwardParam.progressParam ;
				}
				forwardWithHeader(url);
			}
		})
	}/*else{
		forwardParam.timeParam = param;
		$("#container_time .highcharts-title tspan").click(function(){
			if($("#container_time .highcharts-title tspan").text()==(totalDay_all+"天")){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.timeParam){
					url += "?forwardProgress=" + forwardParam.timeParam ;
				}
				forwardWithHeader(url);
				
			}
			
			
			
			
		})
		$("#container_time .highcharts-title span").click(function(){
			if($("#container_time .highcharts-title span").text()==(totalDay_all+"天")){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.timeParam){
					url += "?forwardProgress=" + forwardParam.timeParam ;
				}
				forwardWithHeader(url);
				
			}
		})
	}*/
	
	
	
}
</script>