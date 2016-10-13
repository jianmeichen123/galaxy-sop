

//===
var teamkpi_url = platformUrl.deptkpi;
var teamkpi_pageNum = 1;

function partner_kpi_init(){
	
	
	//星期几
	var now = new Date();
	var nowYear = now.getYear();//当前年
	var nowMonth = now.getMonth();//当前月
	
	var nowDay = now.getDate();//当前日
	var nowDayOfWeek = now.getDay();//今天本周的第几天
	
	
	 //获得本周的开始日期
    var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -1);
    var getWeekStartDate =  formatDate(getWeekStartDate);
    //获得本周的结束日期
    var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek-1));
    var getWeekEndDate =  formatDate(getWeekEndDate);
    
    //alert("本周开始日期:"+getWeekStartDate);
    //alert("本周结束日期:"+getWeekEndDate);
	
	//获得上周的开始日期
    var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -7 -1);
    var getUpWeekStartDate =  formatDate(getUpWeekStartDate);
    
    //alert("上周开始日期:"+getUpWeekStartDate);

    //获得上周的结束日期
    var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 7 -1));
    var getUpWeekEndDate =  formatDate(getUpWeekEndDate);
	
	
    //alert("上周结束日期:"+getUpWeekEndDate);
    
    
    
    
    
    
    //格式化日期：yyyy-MM-dd
    function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth()+1;
        var myweekday = date.getDate();

        if(mymonth < 10){
            mymonth = "0" + mymonth;
        }
        if(myweekday < 10){
            myweekday = "0" + myweekday;
        }
        return (myyear+"-"+mymonth + "-" + myweekday);
    }
	
	
	/*$("#querySearch_teamkpi").on('click',function(){
		$('#data-table-parterkpi').bootstrapTable('refresh',getToobarQueryParams('custom-toolbasr-deptkpi')); 
	});
	
	//绑定querySearch事件
	$('#data-table-parterkpi').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getToobarQueryParams('custom-toolbasr-parterkpi'));
		},
		pagination: true,
        search: false,
        url: teamkpi_url,
        onLoadSuccess: function(backdata){
        	queryParamsJson = eval("("+backdata.queryParamsJsonStr+")");
        	var options = $('#data-table-parterkpi').bootstrapTable('getOptions');
        	var data = options.data;
        	teamkpi_pageNum = options.pageNumber;
        	if(teamkpi_pageNum == 1){
        		var re = [];
    	   		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i>=10){
        				break;
        			}else{
        				re.push(data[i].completed);
           	   			categories.push(data[i].departmentName);
        			}
            	}
        		containerDeptKpiOptions.series[0].data = re;
	    		containerDeptKpiOptions.xAxis.categories = categories;
    	   		var chart = new Highcharts.Chart(containerDeptKpiOptions);
        	}
        }
	});
}


function cat_deptkpi(value, row, index) {
	var id = row.departmentId;
	var options = "<a href='#' onclick='deptkpiprojectList(" + id + ")' class='blue'>"+ value + "</a>";
	return options;
}
function deptkpiprojectList(id) {
	var _url = platformUrl.deptkpiprojectlist;
	$.getHtml({
		url : _url,//模版请求地址	
		data : "",//传递参数
		okback : function() {
			queryParamsJson.deptid = id;
			$("#tc_title_name").html("团队绩效考核 ");
			$('#data-table-deptkpi-projectlist').bootstrapTable({
				queryParamsType : 'size|page', // undefined
				pageSize : 10,
				showRefresh : false,
				sidePagination : 'server',
				method : 'post',
				pagination : true,
				search : false,
				//dataType: 'html',
				url: platformUrl.userkpi,
				queryParams:function(params){
					return json_2_1(queryParamsJson,params);
				},
				onLoadSuccess : function(result) {
					//console.log(result)
				}
			});
		}
	});
	return false;
}


*//**************************************************************************
 * 图表配置项
 **************************************************************************//*
 //团队绩效，图表配置项
 var containerDeptKpiOptions = {
 	chart: {
 		renderTo:'container_deptkpi',
         type: 'column',
         margin: [ 50, 50, 100, 80],
         height :340
         //width:1200
     },
     title: {
         text: '团队项目数TOP10',
         align:'left',  
         style:{
             fontSize:'18px',
             fontFamily:'微软雅黑',
             color:'#3e4351',
         },        
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
         //categories: ['物联网','互联网钢铁','互联网服装','互联网金融','互联网工业','互联网房地产','互联网大数据','互联网教育','互联网工农业'],
         labels: {
             rotation: 0,
             align: 'center',
             style: {
                 fontSize: '12px',
                 fontFamily: '宋体'
             }
         }
     },
     yAxis: {
     	gridLineColor: '#e9ebf2',
         gridLineWidth: 1,
         min: 0,
         title: {
             text: '项目数 (个)'
         }
     },
     legend: {
         enabled: false
     },
     tooltip: {
         pointFormat: '项目数: <b>{point.y} </b>',
     },
     plotOptions: {
         column: {
             pointWidth:20
         }
     },
     series: [{
         name: 'Population',
         color:'#9dd2fc',
         //data:  [30,23,20,15,13,10,6,6,5],
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
             formatter:function(){
                 return this.point.y;

             },
         }
     }]	*/
 };

 