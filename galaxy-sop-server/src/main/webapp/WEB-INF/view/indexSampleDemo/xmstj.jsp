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
				    <!--  <div>
						<h2 class="chart_name">项目数统计top10</h2>
					 </div> -->
					 <div id="container_xmstj" class="chart_m" style＝“min-width:800px;”></div>
					 <div id="container_xmstj_bt"></div>
				</div>
			</div>
	   </div>
		
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	//项目数统计--柱状图option
	var containerXmstjOptions={
		chart: {
			renderTo:'container_xmstj',
			type: 'column'
		},	
		title: {
	        text: '项目数统计TOP10',
	        align:'left',
	        style:{
	            fontSize:'18px',
	            fontFamily:'微软雅黑',
	            color:'#3e4351',
	        },
	    },
	    xAxis: {
	    	    lineWidth: 1,
	            lineColor: "#e9ebf2",
	            allowDecimals:false, //不显示小数
	            tickWidth: 0,
	        //categories: ['物联网', '互联网钢铁', '互联网服装', '互联网金融', '互联网工业', '互联网房地产', '大数据云计算', '互联网工农业', '智能设备','o2o及电商']
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
	    	gridLineColor: '#e9ebf2',
	        gridLineWidth: 1,
	        min: 0,
	        allowDecimals:false, //不显示小数
	        title: {
	            text: '项目 (个)'
	        }
	    },
	       legend: {
	            backgroundColor: '#FFFFFF',
	            itemStyle:{
	                fontWeight:'normal',
	                color:'#525662',
	            },
	        }, 
	       tooltip: {
	        pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b>个 <br/>',
	        shared: true
	    },
	        plotOptions: {
	            column: {
	            	 stacking: 'normal',
	                 borderWidth: 0,
	                 pointWidth: 20,
	            }
	        },
	        series: [{
	        name: '内部创建',
	        color:'#4fd7cd',
	        //data: [216, 210, 200, 180, 165, 155, 135, 125, 120, 100]
	    }, {
	        name: '外部投资',
	        color:'#95b0c0',
	        //data: [106, 106, 100, 90, 88, 66, 55, 44, 33, 22]
	    }]
	};
	var toobarParams={};
	toobarParams["projectType"] ='-1';
	var obj ={url:reportEndpointURL+"/demo/indexXMTJData"};
	obj.contentType="application/json";
	obj.data =toobarParams;
	ajaxCallback(obj,function(data){
    	var result = data.result;
		var mapList = data.pageList.content;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		//加载项目数统计图
		var zj = [];
		var tz = [];
		var categories = [];
		for(var i=0;i<mapList.length;i++){
			zj.push( mapList[i].zj_completed);
			tz.push( mapList[i].wb_completed);
			categories.push(isHHR=='true' ? mapList[i].real_name : mapList[i].department_name);
		}
		containerXmstjOptions.series[0].data = ( toobarParams.projectType=='projectType:2' || toobarParams.projectType=='-1' ? zj : [] );
		containerXmstjOptions.series[1].data =( toobarParams.projectType=='projectType:1' || toobarParams.projectType=='-1' ? tz : [] );
		containerXmstjOptions.xAxis.categories = categories;
		var chart = new Highcharts.Chart(containerXmstjOptions);
		//加载已完成项目占比（自建、投资）饼图
		var zj_completed = 0;
		var wb_completed = 0;
		var zj_rate = 0;
		var wb_rate = 0;
		for(var i=0;i<mapList.length;i++){
			zj_completed+=mapList[i].zj_completed;
			wb_completed+=mapList[i].wb_completed;
		}
		//console.log(mapList);
		//console.log(zj_completed);
		//console.log(wb_completed);
		zj_rate = ( toobarParams.projectType=='projectType:2' || toobarParams.projectType=='-1' ) ? ( zj_completed/(zj_completed+wb_completed) ) * 100 : 0;
		wb_rate = ( toobarParams.projectType=='projectType:1' || toobarParams.projectType=='-1' ) ? ( wb_completed/(zj_completed+wb_completed) ) * 100 : 0;
		containerXmstjBtOptions.series[0].data = [ {name:"外部投资",y:parseFloat(wb_rate.toFixed(2)),num:wb_completed,color:"#6fbdeb"},{name:"内部创建",y:parseFloat(zj_rate.toFixed(2)),num:zj_completed,color:"#529be2"} ];
		//containerXmstjBtOptions.series[0].data[1]={name:"内部创建",y:zj_rate.toFixed(2),num:zj_completed,color:"#ff955b"};
		var chart = new Highcharts.Chart(containerXmstjBtOptions);
	});
	

});
//项目数统计－－已完成项目占比饼图option
var containerXmstjBtOptions = {
	chart: {
		renderTo:'container_xmstj_bt',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        height :340,
        width:450
    },
    title: {
        text: '已完成项目占比',
        align:'left',  
        style:{
            fontSize:'18px',
            fontFamily:'微软雅黑',
            color:'#808e9b',
        },
    },
    //去除版权
    credits: {
        enabled:false
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/> 项目数: <b>{point.num}</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            depth: 35,
            dataLabels: {
                color:'black',
                rotation: -90,
                enabled: true,
                formatter:function(){
                    return this.point.percentage.toFixed(1)+"%";
                    //return this.point.percentage.toFixed(1)+"%" + " , " +this.point.num + "个";
                },
                connectorWidth:0,
                connectorPadding:0,
                distance:-30
            },
            showInLegend: true
        }
    },
    legend: {
    	 layout: 'vertical',
        align: 'right',
        verticalAlign: 'center',
        x: 10,
        y: 100,
        floating: true,
        backgroundColor: '#FFFFFF',
        itemStyle:{
            fontWeight:'normal',
            color:'#525662',
        },
    },
    series: [{
        type: 'pie',
        name: '完成占比',
        /*data: [
            {
                name:'外部投资',
                y:5,
                num:98,
                color:"#86c664"
            },
            { name:'内部创建', y: 20,num:98,color:"#ff955b"},
        ],*/
        dataLabels: {
            enabled: true,
            rotation: 0,
            color: '#FFFFFF',
            verticalAlign: 'middle',
            distance:-50,
            align: 'center',
            x: 0, y: 0,
            style: {
                fontSize: '12px',
                fontFamily: '宋体',
                textShadow: '0 0 3px black'
            }
        }
    }]
};
</script>