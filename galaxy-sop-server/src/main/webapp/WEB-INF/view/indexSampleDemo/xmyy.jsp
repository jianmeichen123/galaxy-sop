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
	    <h2 class="chart_name">项目运营</h2>
	    <center>
		<div>
		  <div id="container_operation" style="min-width:300px; height: 200px;padding-top:5px;"></div>
		</div>
		</center>
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	load_data_chart_yuying_money();//投资资金图表

	function load_data_chart_yuying_money(){
		var currDate = new Date();
		var year = currDate.getFullYear();
		var sym = year +'-01';
		var eym = year + '-12';
		
    	var obj ={url:reportEndpointURL+"/demo/indexXMYYData"};
    	obj.data={sym:sym,eym:eym,projectProgress:'projectProgress:10'};
    	obj.contentType="application/json";
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.mapList;
    		var you = []; //优
    		var liang = []; //良
    		var cha = [];//差
    		var categories = [];
    		var youtotal=0;
    		var liangtotal=0;
    		var chatotal=0;
    		for(var i=0;i<entityList.length;i++){
    			youtotal = entityList[i].you;
    			liangtotal = entityList[i].liang;
    			chatotal = entityList[i].cha;
    			var biz_month = entityList[i].year.replace('-','');
    			
    			you.push(youtotal);
    			liang.push(liangtotal);
    			cha.push(chatotal);
    			categories.push(biz_month);
    		}
    		containerTzMoneyOptions.series[0].data = you;
    		containerTzMoneyOptions.series[1].data = liang;
    		containerTzMoneyOptions.series[2].data = cha;
    		containerTzMoneyOptions.xAxis.categories = categories;
    		if(categories.length==1){ //当只有一条数据的时候，显示点标记
    			containerTzMoneyOptions.plotOptions.series.marker.enabled=true;
    		}
    		var chart = new Highcharts.Chart(containerTzMoneyOptions);
    		if(gz==''&& tz==''){
    			$('#container_operation').html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
    		}
    	});
    }
	cutStr(5,'cutstr');
});

//投资资金
var containerTzMoneyOptions = {
	    chart: {
	    	renderTo: 'container_operation',
            type: 'column'
        },
        title: {
            text: ''
        },
        xAxis: {
            lineWidth: 1,
            lineColor: "#edeff5",
            tickWidth: 0,
            labels: {
                y: 20, //x轴刻度往下移动20px
                style: {
                    color: '#999',//颜色
                    fontFamily:'宋体',  //字体
                }
            },
            //categories: ['201501', '201502', '201503', '201504', '201505', '201506', '201507', '201508', '201509', '201510', '201511', '201512']
        },
        //去除版权
        credits: {
            enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        yAxis: {
            gridLineColor: '#f6f7fa',
            gridLineWidth: 1,
            labels: {
                x: -10, //y轴刻度往左移动10px
                style: {
                    color: '#999',//颜色
                    fontFamily:'宋体',  //字体
                }
            },            
            title: {
                text: '项目数'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }],
            //tickPositions: [0, 50, 100, 150, 200, 250, 300]
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
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>个<br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'normal',
                borderWidth: 0,
            }
        },
            series: [ {
                color:'#9dd2fc',
                name: '优',
                //data: [180,150,120,100,70,70,50,40,40,30,10,10]
            },
            {
                color:'#4fd7cc',
                name: '良',
                //data: [30,20,120,30,40,130,30,120,140,120,10,130]
            },
            {
            color:'#8bb3ca',
            name: '差',
            //data: [10,100,100,90,80,50,30,20,60,100,50,30]
        }]
};

</script>