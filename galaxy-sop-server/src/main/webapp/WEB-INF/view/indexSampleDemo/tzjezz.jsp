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
				       <!--  投资金额总目标完成跟踪图 -->
						<div class="chartbox_li">
							<!-- 此处添加水印 -->
							<div id="container_111" class="chart" style="min-width:800px;"></div>
							<div class="statistic">
								<ul>
									<li><label>本年度目标投资金额:</label><span id="chaoe">￥100000</span></li>
									<li><label>截至目前已使用金额:</label><span id="yishiyong">￥80000</span></li>
									<li><label>截至目前未使用/超额使用金额:</label><span id="weishiyong">￥20000/0</span></li>
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
	var chartBriefOptions11 = {
	        chart: {
	            type: 'bar',
	            renderTo:'container_111',
	            height :200,
	        },
	        title: {
	            text: '投资金额追踪',
	            align:'left',  
	            style:{
	                fontSize:'18px',
	                fontFamily:'微软雅黑',
	                color:'#3e4351',
	            },        
	        },
	        subtitle: {
	            text: '单位:百万',
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
	            categories: ['投资额'],
	           /* labels: {
	                rotation: 0,
	                align: 'center',
	                style: {
	                    fontSize: '12px',
	                    fontFamily: 'Verdana, sans-serif',
	                    color:'#7a8798',
	                }
	            }*/
	        },
	        yAxis: {
	            min: 0,
	            max:120000,
	            title: {text: ''},
	            gridLineColor:'#EEE',
	            plotLines: [{
	                color: '#FF0000',
	                width: 2,
	                value: 100000,
	            }]
	        },
	        legend: {
	            backgroundColor: '#FFFFFF',
	            reversed: true,
	            itemStyle:{
	                fontWeight:'normal',
	                color:'#7a8798',
	                fontFamily:'宋体'
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
	            name: '未使用',
	            //data: [20000],
	            color: '#ff9c89'
	        }, {
	            name: '已使用',
	            //data: [80000],
	            color:'#6fbdeb'
	        }]
	};
	
	// 项目数目标追踪(个)
	var obj ={url:reportEndpointURL+"/demo/indexTZJEZZData"};
	obj.data = {type:1}
	ajaxCallback(obj,function(data){
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
			chartBriefOptions11.series[0] = {name:'超额',data:[above],color:'#587edd'};
			if(completed>0){
				chartBriefOptions11.series[2] = {name:'已使用',data:[completed],color:'#6fbdeb'};
			}
			chartBriefOptions11.series[1] = {name:'未使用',data:[notcompleted],color:'#ff9c89'};
		}else{
			chartBriefOptions11.series[0] = {name:'超额',data:[above],color:'#587edd'};
			chartBriefOptions11.series[1] = {name:'未使用',data:[notcompleted],color:'#ff9c89'};
			chartBriefOptions11.series[2] = {name:'已使用',data:[completed],color:'#6fbdeb'};
		}
		
		chartBriefOptions11.yAxis.plotLines[0].value=target;
		var chart = new Highcharts.Chart(chartBriefOptions11);
		
		$("#chaoe").html(list[0].target);
		$("#yishiyong").html(list[0].completed);
		$("#weishiyong").html(list[0].notcompleted + '/' + list[0].above);
	});
	
});

</script>