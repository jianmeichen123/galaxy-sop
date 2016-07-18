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
				     <div>
						<h2 class="chart_name">项目进度分布图</h2>
					 </div>
					 <div id="histogram"></div>
				</div>
			</div>
	   </div>
		
	</div>
</div>

<jsp:include page="../common/footer.jsp" flush="true"></jsp:include>
<script>
$(function(){
	//项目总览柱状图option
	var containerXmzlOptions={
		chart: {
			renderTo :'histogram',
	        type: 'column',
	        margin: [ 50, 50, 100, 80]
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
	    plotOptions: {
	    	 column: {
	             pointWidth:20//设置柱状图宽度
	         },
	        series: {
	            cursor: 'pointer',
	            events: {
	                click: function (event) {
	                    console.log(event.point.category);
	                    var temp = event.point.category.split('-');
	                    if(temp!=null && temp!='' && temp!='undefind'){
	                    	var dict_code = temp[1].split(':');
	                    	showDetails(dict_code[1])                    	
	                    }
	                }
	            }
	        }
	    },
	    xAxis: {
	    	lineWidth: 1,
	        lineColor: "#e9ebf2",
	        tickWidth: 0,
	        allowDecimals:false, //不显示小数
	        //categories: ['<a href="http://wwww.baidu.com" target="_blank">Jan</a>','朱玟','牟敏','关屿','赵广智','陈丛翀','王飞韵','蔡燕','王晓宇'],
	    	labels: {
	    		useHTML:true,
	    		/*formatter:function(){
	    			var temp = new Array();
	    			temp = this.value.split('-');
	    			return '<a href="javascript:;" onclick="showDetails("'+temp[1]+'");">' + temp[0] + '</a>';
	    		},*/
	            rotation: 0,
	            align: 'center',
	            style: {
	                fontSize: '13px',
	                fontFamily: '宋体'
	            },
	        }
	    },
	    yAxis: {
	        gridLineColor: '#e9ebf2',
	        gridLineWidth: 1,
	        min: 0,
	        allowDecimals:false, //不显示小数
	        title: {
	            //text: '项目数 (个)'
	            text:''
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	    	useHTML: true,
	    	formatter: function(){
	    		var temp = this.x.split('-');
	    		return temp[0] +'<br/>项目数:'+ this.y +'个';
	    	}
	    },
	    series: [{
	        name: 'Population',
	        color:'#587edd',
	        //data: [9,8,5,4,3,3,2,2,2,2],
	        dataLabels: {
	            enabled: true,
	            rotation: 0,
	            color: '#6b799f',
	            align: 'center',
	            x: 0,
	            y: 0,
	            style: {
//	                fontSize: '13px',
//	                fontFamily: '宋体',
//	                textShadow: '0 0 3px black'
	            	fontSize: '12px',
	                fontFamily: 'Verdana, sans-serif',
	                textShadow: '0 0 0px #fff',
	                fontWeight:'normal',
	            },
	            formatter:function(){
	     			return this.point.y;
				},
	        }
	    }]
	};

    	var obj ={url: reportEndpointURL+"/demo/indexXMJDFTData"};
		obj.contentType="application/json";
		//obj.data = getToobarQueryParams('custom-toolbasr-xmzl');
		ajaxCallback(obj,function(data){
			//console.log(data);
			var result = data.result;
			var mapList = data.mapList;
			if(result.status=='ERROR'){
				$.popup(100,'消息',result.message);
				return false;
			}
			var re = [];
			var categories = [];
			for(var i=0;i<mapList.length;i++){
				re.push( mapList[i].c);
				categories.push(mapList[i].name + "-" + mapList[i].dict_code);
			}
			//console.log(categories);
			containerXmzlOptions.series[0].data = re;
			containerXmzlOptions.xAxis.categories = categories;
			containerXmzlOptions.xAxis.labels.useHTML = true;
			containerXmzlOptions.xAxis.labels.formatter = function(){
				var temp = new Array();
				temp = this.value.split('-');
				switch(temp[1]){
					case "projectProgress:1": 
						return "<a href='javascript:;' onclick='showDetails(1);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:2": 
						return "<a href='javascript:;' onclick='showDetails(2);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:3": 
						return "<a href='javascript:;' onclick='showDetails(3);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:4": 
						return "<a href='javascript:;' onclick='showDetails(4);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:5": 
						return "<a href='javascript:;' onclick='showDetails(5);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:6": 
						return "<a href='javascript:;' onclick='showDetails(6);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:7": 
						return "<a href='javascript:;' onclick='showDetails(7);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:8": 
						return "<a href='javascript:;' onclick='showDetails(8);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:9": 
						return "<a href='javascript:;' onclick='showDetails(9);' class='blue'>" + temp[0] + "</a>";
						break;
					case "projectProgress:10": 
						return "<a href='javascript:;' onclick='showDetails(10);' class='blue'>" + temp[0] + "</a>";
						break;
				}
			};
			var chart = new Highcharts.Chart(containerXmzlOptions);
		});
	
});

</script>