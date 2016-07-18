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
				     <h2 class="chart_name">项目完成率分析</h2>
						<div id="chartBrief4" class="chart chart_m"></div>
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
	var sym = year +'-01';
	var eym = year + '-12';
	// 项目完成率分析
	var chartBriefOptions4 = {
	    chart: {
	    	renderTo:'chartBrief4',
            zoomType: 'xy'
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
        xAxis: [{
            /*categories: ['物联网', '互联网钢铁', '互联网服装', '互联网金融', '互联网工业', '互联网房地产',
                '大数据云计算', '互联网工农业', '智能设备', 'o2o及电商'],*/
            lineWidth: 1,
	        lineColor: "#e9ebf2",
	        tickWidth: 0,
            labels: {
                //rotation: -45,
                align: 'center',
                style: {
                    fontSize: '12px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        }],
        yAxis: [{ // Primary yAxis
        	gridLineColor: '#e9ebf2',
	        gridLineWidth: 1,
            labels: {
                format: '{value} %',
                style: {
                    color: '#89A54E'
                }
            },
            title: {
                text: '项目完成率',
                style: {
                    color: '#606060'
                }
            }
        }, { // Secondary yAxis
            title: {
                text: '完成项目数',
            },
            labels: {
                format: '{value} 个',
                style: {
                    color: '#606060'
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        plotOptions: {
            series: {
                pointPadding: 0, //数据点之间的距离值
                groupPadding: 0, //分组之间的距离值
                borderWidth: 0,
                shadow: false,
                pointWidth:20 //柱子之间的距离值
            }
        },
        legend: {
            floating: true,
            y:20,
            backgroundColor: '#FFFFFF',
            itemStyle:{
                fontWeight:'normal',
                color:'#525662',
            },

        },
        series: [{
            name: '完成项目数',
            color: '#587edd',
            type: 'column',
            yAxis: 1,
            //data: [7, 6, 9, 14, 18, 21, 25, 26, 23, 18],
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
            tooltip: {
                valueSuffix: ' 个'
            }

        }, {
            name: '项目完成率',
            color: '#ff9c89',
            type: 'spline',
            //data: [49.5, 61.8, 59.1, 92.9, 87.3, 56.7, 78.4, 90.8, 65.3, 88.4],
            tooltip: {
                valueSuffix: '%'
            }
        }]
	};

	//获取数据，加载图表
	var chartBriefOptionsObj4 ={url:reportEndpointURL+"/demo/indexXMWCLFXData"};
	chartBriefOptionsObj4.contentType="application/json";
	chartBriefOptionsObj4.data = {sym:sym,eym:eym}
	ajaxCallback(chartBriefOptionsObj4,function(data){
		//console.log(data);
		var result = data.result;
		var list = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var ywc = [];
		var wcl = [];
		var categories = [];
		for(var i=0;i<list.length;i++){
			var rate = list[i].rate*100*12;
			ywc.push( list[i].completed);
			wcl.push( parseFloat(rate.toFixed(2)));
			categories.push(list[i].biz_date.replace('-',''));
		}
		//console.log(re);
		chartBriefOptions4.series[0].data = ywc;
		//briefProjectLineOptions.series[0].name = '目标';
		chartBriefOptions4.series[1].data = wcl;
		//briefProjectLineOptions.series[0].name = '已完成';
		chartBriefOptions4.xAxis[0].categories = categories;
		var chart = new Highcharts.Chart(chartBriefOptions4);
	});
	
});

</script>