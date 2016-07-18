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
	    <h2 class="chart_name">投资金额</h2>
	    <center>
		<div>
		   <div id="container_investmentFunds"
							style="min-width:300px; height: 200px;padding-top:5px;"></div>
		</div>
		</center>
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	load_data_chart_tz_money();//投资资金图表

	function load_data_chart_tz_money(){
		var currDate = new Date();
		var year = currDate.getFullYear();
		var sym = year +'-01';
		var eym = year + '-12';
		
    	var obj ={url:reportEndpointURL+"/demo/indexTZData"};
    	obj.data={sym:sym,eym:eym,projectProgress:'projectProgress:10'};
    	obj.contentType="application/json";
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.mapList;
    		var gz = []; //初始估值
    		var tz = []; //初始投资额
    		var categories = [];
    		var valuations=0;
    		var contribution=0;
    		for(var i=0;i<entityList.length;i++){
    			valuations += (entityList[i].project_valuations/100);
    			contribution += (entityList[i].project_contribution/100);
    			var biz_month = entityList[i].biz_date.replace('-','');
    			
    			gz.push(parseFloat(valuations.toFixed(1)));
    			tz.push(parseFloat(contribution.toFixed(1)));
    			categories.push(biz_month);
    		}
    		containerTzMoneyOptions.series[0].data = gz;
    		containerTzMoneyOptions.series[1].data = tz;
    		containerTzMoneyOptions.xAxis.categories = categories;
    		if(categories.length==1){ //当只有一条数据的时候，显示点标记
    			containerTzMoneyOptions.plotOptions.series.marker.enabled=true;
    		}
    		var chart = new Highcharts.Chart(containerTzMoneyOptions);
    		if(gz==''&& tz==''){
    			$('#container_investmentFunds').html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
    		}
    	});
    }
	cutStr(5,'cutstr');
});

//投资资金
var containerTzMoneyOptions = {
    chart: {
    	renderTo: 'container_investmentFunds',
        type: 'line'
    },
    title: {
        text: '',
        x: -20 //center
    },
    //去除版权
    credits: {
        enabled:false
    },
    //去除右上角导出图标
    exporting: {
        enabled:true
    },
    xAxis: {
        lineWidth: 1,
        lineColor: "#edeff5",
        tickWidth: 0,
        labels: {
            y: 20, //x轴刻度往下移动20px
            style: {
                color: '#7a8798',//颜色
                fontFamily:'宋体',
            }
        },
        //categories: ['201501', '201502', '201503', '201504', '201505', '201506', '201507', '201508', '201509', '201510', '201511', '201512']
    },
    yAxis: {
        gridLineColor: '#f6f7fa',
        gridLineWidth: 1,
        labels: {
            format: '{value} M',
            x: -10, //y轴刻度往左移动10px
            style: {
                color: '#999',//颜色
                fontFamily:'宋体',  //字体
            }
        },
        title: {
            text: '金额(百万)'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }],
        //tickPositions: [0, 500, 1000, 1500, 2000, 2500, 3000]
    },
    plotOptions: {
        series: {
            marker: {
                enabled: false
            }
        },
    },
    tooltip: {
        valueSuffix: '百万'
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
        lineWidth: 1.5,
        name: '估值',
        color:'#65ade7',
        //data: [10, 100, 500, 750,800, 900, 1000,1200, 1500, 1800,2000,3000]
    }, {
        lineWidth: 1.5,
        name: '投资金额',
        color:'#ff9c89',
        //data: [0, 90, 300, 700,700, 800, 900,1100, 1400, 1600,2000,2500]
    }]
};

</script>