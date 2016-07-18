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
				      <h2 class="chart_name">投资事业线目标完成对比</h2>
				      <div id="chartBrief3"  style="min-width:800px;"></div>
				</div>
			</div>
	   </div>
		
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	// 投资事业线目标完成对比
	var chartBriefOptions3 = {
	        chart: {
	            type: 'column',
	            renderTo:'chartBrief3',
	            //zoomType: 'xy'
	            //height :340,
	            //width:800
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
		        lineWidth: 1,
	            lineColor: "#e9ebf2",
	            tickWidth: 0,
	            allowDecimals:false, //不显示小数
	            /*categories: [
                '物联网',
                '互联网钢铁',
                '互联网服装',
                '互联网金融',
                '互联网工业',
                '互联网房地产',
                '大数据云计算',
                '互联网工农业',
                '智能设备',
                'o2o及电商'
	            ],*/
            labels: {
                rotation: 0,
                align: 'center',
                y:30,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    color:'#7a8798',
                }
            }
        },
	        yAxis: [ 
//					{
//						gridLineColor: '#e9ebf2',
//					    gridLineWidth: 1,
//					    min: 0,
//					    title: {
//					        text: '已完成 (个)'
//					    },
//					    
//					}
//				,
	        	{
	        		gridLineColor: '#e9ebf2',
		            gridLineWidth: 1,
		            min: 0,
		            title: {
		                text: '项目数(个)'
		            },
		            //opposite: true
	        	}
	        ],
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y} 个</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
        legend: {
            backgroundColor: '#FFFFFF',
            //reversed: true,
            itemStyle:{
                fontWeight:'normal',
                color:'#525662',
            },
        },	        
        plotOptions: {
	            column: {
	            	pointPadding: 0.2,
	                borderWidth: 0,
	                pointWidth: 20
	            }
	        },
	        series: [{
	        	type: 'column',
	        	//yAxis: 1,
	            name: '目标数',
	            color:'#587edd',
	           // data: [49, 71, 106, 129, 144, 176, 135, 148, 216, 194]
	           dataLabels: {
                enabled: true,
                rotation: 0,
                color: '#6b799f',
                align: 'center',
                x: 0,
                y: 0,
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 0px #fff',
                    fontWeight:'normal',
                },
            },
	            
	        },{
	        	type: 'column',
	            name: '已完成',
	            //data: [83, 78, 98, 93, 106, 84, 105, 104, 91, 83]
	            color:'#00bdf4',
	            dataLabels: {
	                enabled: true,
	                rotation: 0,
	                color: '#6b799f',
	                align: 'center',
	                x: 0,
	                y: 0,
	                style: {
	                    fontSize: '12px',
	                    fontFamily: 'Verdana, sans-serif',
	                    textShadow: '0 0 0px #fff',
	                    fontWeight:'normal',
	                },
            },
	        }]

	    };
	
	//获取数据，加载图表
	var chartBriefOptionsObj3 ={url:reportEndpointURL+"/demo/indexXMMBWCBData"};
	chartBriefOptionsObj3.data = {type:3}
	ajaxCallback(chartBriefOptionsObj3,function(data){
		var result = data.result;
		var list = isHHR=='true' ? data.pageList.content : data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var mb = [];
		var ywc = [];
		var categories = [];
		for(var i=0;i<list.length;i++){
			mb.push( list[i].target);
			ywc.push( list[i].completed);
			categories.push( isHHR=='true' ? list[i].real_name : list[i].name);
		}
		chartBriefOptions3.series[0].data = mb;
		//briefProjectLineOptions.series[0].name = '目标';
		chartBriefOptions3.series[1].data = ywc;
		//briefProjectLineOptions.series[0].name = '已完成';
		chartBriefOptions3.xAxis.categories = categories;
		var chart = new Highcharts.Chart(chartBriefOptions3);
	});
	
});

</script>