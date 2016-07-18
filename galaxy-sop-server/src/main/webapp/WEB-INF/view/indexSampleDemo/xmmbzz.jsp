<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<%
String endpoint = (String)application.getAttribute(com.galaxyinternet.framework.core.oss.OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
// java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
// System.out.println("------------------>>" + endpointMap.get("galaxy.project.sop.endpoint"));
%>
<style>
.pagebox .ritRre {
/*     float: right;
    width: 17.8125%;
    margin-top: 20%;
    margin-right: 40%; */
}
</style>
<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="demoMenu.jsp" flush="true"></jsp:include>
	<!--右侧-->
	<div class="ritmin">
	   <div class="tabtable">
			<!-- tab内容 -->
			<div class="tabtable_con tabtable_con_brif">
				<!--目标完成情况部分-->
				<div class="chartbox">
				        <!-- 项目总目标完成跟踪 -->
						<div class="chartbox_li">
							<!-- 条形图展示  -->
							<h2 class="chart_name">项目目标追踪</h2>
							<div id="chartBrief1" class="chart" style="min-width:800px;"></div>
							<div class="statistic">
								<ul>
									<li>本年度目标项目数:<span id="target"></span></li>
									<li>截至目前完成数:<span id="completed"></span></li>
									<li>截至目前未完成/超额完成数:<span id="notcompleted"></span></li>
								</ul>
							</div>
						</div>
				</div>
			</div>
	   </div>
		
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	
	var currDate = new Date();
	var year = currDate.getFullYear();
	var sdate = year + '-01-01';
	var edate = year + '-12-31';
	var sym = year +'-01';
	var eym = year + '-12';
	
	// 项目数目标追踪(个)
	var chartBriefOptions1 = {
	        chart: {
	            type: 'bar',
	            renderTo:'chartBrief1',
	            height :200,
	        },
	        title: {
	        	text:'',
	        	//text: '项目目标追踪',
	             style: {
	                    color: "#fff",
	                },
	        },
	        subtitle: {
        	    text: '单位:个',
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
	            categories: ['项目数']
	        },
	        yAxis: {
	            min: 0,
	            //max:120,
	            title: {text: ''},
	            gridLineColor:'#EEE',
	            allowDecimals:false, //不显示小数
	            plotLines: [{
	                color: '#FF0000',
	                width: 2,
	                value: 100
	            }]
	        },
	        legend: {
	            backgroundColor: '#FFFFFF',
	            reversed: true,
	            itemStyle:{
	                fontWeight:'normal',
	                color:'#525662',
	            },
	        },
        plotOptions: {
            series: {
                stacking: 'normal'
            },
            bar: {
                pointPadding: 0.5,
                groupPadding: 0.2,
                borderWidth: 0,
                pointWidth: 15,
                dataLabels: {
                    enabled: true,
                    style:{
                        color:'#fff',
                        fontWeight:'normal',
                        textShadow:'none',
                        fontFamily:'宋体'
                    },
                }
            }
        },
	        series: [{
	            name: '超额',
	            //data: [0],
	            color: '#587edd'
	        }, {
	            name: '未完成',
	            //data: [20],
	            color: '#ff9c89'
	        }, {
	            name: '已完成',
	            //data: [80],
	            color:'#6fbdeb'
	        }]
	    };
	
	// 项目数目标追踪(个)
	var obj ={url:reportEndpointURL+"/demo/indexTZJEZZData"};
	obj.data = {type:1}
	ajaxCallback(obj,function(data){
		//console.log(data);
		var result = data.result;
		var list = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		
		var target = list[0].target; 
		var above = list[0].above;
		var completed = list[0].completed;
		var notcompleted = list[0].notcompleted;
		
		if(above>0){
			completed =  target;
			notcompleted = 0;
			chartBriefOptions1.series[0] = {name:'超额',data:[above],color:'#587edd'};
			if(completed>0){
				chartBriefOptions1.series[2] = {name:'已完成',data:[completed],color:'#6fbdeb'};
			}
			chartBriefOptions1.series[1] = {name:'未完成',data:[notcompleted],color:'#ff9c89'};
		}else{
			chartBriefOptions1.series[0] = {name:'超额',data:[above],color:'#587edd'};
			chartBriefOptions1.series[1] = {name:'未完成',data:[notcompleted],color:'#ff9c89'};
			chartBriefOptions1.series[2] = {name:'已完成',data:[completed],color:'#6fbdeb'};
		}
		
		//chartBriefOptions1.yAxis.max = entityList[0].target*2;
		chartBriefOptions1.yAxis.plotLines[0].value=target;
		var chart = new Highcharts.Chart(chartBriefOptions1);
		
		$("#target").html(list[0].target);
		$("#completed").html(list[0].completed);
		$("#notcompleted").html(list[0].notcompleted + '/' + list[0].above);
	});
});

</script>