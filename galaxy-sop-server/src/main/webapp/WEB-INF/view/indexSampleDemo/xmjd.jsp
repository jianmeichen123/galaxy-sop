<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<%
String endpoint = (String)application.getAttribute(com.galaxyinternet.framework.core.oss.OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
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
	    <h2 class="chart_name">项目进度</h2>
	    <center>
		<div>
		   <div class="mask_platform_progress"></div>
		   <div id="container_progress"
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
	load_data_chart_progress();

	function load_data_chart_progress(){
    	var obj ={url: reportEndpointURL+"/demo/indexProgressData"};
    	obj.contentType="application/json";
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.mapList;
    		var re = [];
    		var color=['#587edd','#49ceff','#00bdf4','#88dfd8','#4490d2','#bee6d5','#6ebdea','#ff9c89','#62d1b0','#a3e394'];
    		//var selectedPie = "";
    		var totalNum = 0;
    		for(var i=0;i<entityList.length;i++){
    			var rate = entityList[i].rate*100;
    			var tmp = {
    					name : entityList[i].name,
    					color :color[i],
    					y : entityList[i].c,
    					rate : parseFloat(rate.toFixed(1))
    			};
    			if(i==0){
    				//tmp.sliced=true;
    				//tmp.selected=true;
    				//selectedPie = {num:tmp.y,rate:rate};
    			}
    			re.push(tmp);
    			totalNum += entityList[i].c;
    			
    		}
    		totalNum_all =totalNum;
    		containerProgressOptions.series[0].data = re;
    		containerProgressOptions.title.text = "<span style='color:#4490d2'>"+ totalNum +"个</span>"+"<br/>";
    		containerProgressOptions.plotOptions.pie.events.click = function(e){
    			//console.log(e.point);
    			chart.setTitle(
    					{
    						text: "<span style='color:#4490d2'>"+ e.point.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.point.percentage.toFixed(1)) +"%</span>",
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
        						text: "<span style='color:#4490d2'>"+ totalNum +"个</span>"+"<br/>",
        						y:5,
        						x:-95
        					}
        			);
    			}
    			judgeProgress($.trim(e.point.name),'progress');
    		};
    		containerProgressOptions.plotOptions.pie.point.events.legendItemClick = function(e){
    			
    			//return false;
    			//console.log(e.target.name);
    			//chart.setTitle({text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>"});
        		//chart.redraw();
    			chart.setTitle(
    					{
    						text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>",
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
        						text: "<span style='color:#4490d2'>"+ totalNum +"个</span>"+"<br/>",
        						y:5,
        						x:-95
        					}
        			);
    			}
    			judgeProgress($.trim(e.target.name),'progress');
    			//e.target.show();
    			return false;
    		};
    		var chart = new Highcharts.Chart(containerProgressOptions);
    	});
    }	

});

//项目进度配置
var containerProgressOptions = {
    chart: {
    	renderTo:'container_progress',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        backgroundColor: 'rgba(255, 255, 255, 0)'
    },
    title: {
        text: "<span style='color:#4490d2'>"+'0个'+"</span>"+"<br/>"+"<span>"+'0%'+"</span>",
        verticalAlign:'middle',
        y:5,
        x:-95,
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
	    itemWidth:90,
	    width:200,
	    padding:-25,
	    //minHeight:100,
	    itemStyle:{
	        fontWeight:'normal',
	        color:'#7a8798',
	    }
	    //x:0,
	},            
    series: [{
        type: 'pie',
        size:'140%',
        innerSize :'70%',
        name: '项目进度占比',
        /*data: [
            {name:'接触访谈',color:'#c5b33b',y:8},
            {name: '内部评审',color:'#cbc63a',y: 10},
            {name:'CEO评审',color:'#bac73b',y:16},
            {name:'立项会',color:'#a6cb2b',y:20},
            {name:'投资意向书',color:'#69bf56',y: 30},
            {name:'尽职调查',color:'#58b260',y:40},
            {name:'投决会',color:'#36afa2',y:50},
            {name:'投资协议',color:'#159196',y:55},
            {name:'股权交割',color:'#4790d2',y:60},
            {name:'投后运营',color:'#3c84c6',y:90},
        ],*/
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