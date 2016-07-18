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
	    <h2 class="chart_name">绩效考核</h2>
	    <center>
		<div>
		    <div id="container_kpi" style="min-width:300px; height: 162px;padding-top:5px;"></div>
		</div>
		</center>
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	load_data_chart_kpi();//绩效考核图表

	function load_data_chart_kpi(){
    	var obj ={url:reportEndpointURL+"/demo/indexKpiData"};
    	obj.contentType="application/json";
    	obj.data = {pageNum:0,pageSize:5},
    	ajaxCallback(obj,function(data){
    		//console.log(data);
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.pageList.content;
    		var isHHR = data.pageList.isHHR;
    		var re = [];
    		var ghl = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			var rate = entityList[i].ghl_rate*100;
    			re.push(entityList[i].completed);
    			ghl.push(parseFloat(rate.toFixed(2)));
    			categories.push(isHHR ? entityList[i].real_name : entityList[i].dept_name);
    		}
    		//console.log(re);
    		containerKpiOptions.series[0].data = re;
    		containerKpiOptions.series[1].data = ghl;
    		containerKpiOptions.xAxis.categories = categories;
    		//containerKpiOptions.xAxis.labels.staggerLines = (categories.length>3) ? 2 : 1;
    		var chart = new Highcharts.Chart(containerKpiOptions);
    		if(re==''&& ghl==''){
    			$('#container_kpi').html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
    		}
    	});
    }
	cutStr(5,'cutstr');
});

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

</script>