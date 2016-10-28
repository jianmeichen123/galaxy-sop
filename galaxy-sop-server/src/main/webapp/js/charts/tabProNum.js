

var pronum_url = platformUrl.gglinechart;
if(!isGG) pronum_url = platformUrl.hhrlinechart;

var pageNum_pronum = 1;
var queryParamsJson_pronum = {};

$("#querySearch_xmstj").on('click',function(){
	$("#data-table-xmstj").bootstrapTable('destroy');
	pro_num_init();
});


function pro_num_init(){
	//绑定querySearch事件
	$('#data-table-xmstj').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getToobarQueryParams('custom-toolbar-xmstj'));
		},
		pagination: true,
        search: false,
        url: pronum_url,
        onLoadSuccess: function(backdata){
        	queryParamsJson_pronum = eval("("+backdata.queryParamsJsonStr+")");
        	
        	if(!isGG) $('#data-table-xmstj').bootstrapTable('showColumn', 'realName');
        	
        	var options = $('#data-table-xmstj').bootstrapTable('getOptions');
        	//var data = $('#data-table-userkpi').bootstrapTable('getData');
        	var data = options.data;
        	var projectType = $("#custom-toolbar-xmstj select[name='projectType']").val();
        	pageNum_pronum = options.pageNumber;
        	if(pageNum_pronum == 1){
        		//加载项目数统计图
        		var zj = [];
        		var tz = [];
        		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i>=10){
        				break;
        			}else{
        				zj.push( data[i].zjCompleted);
            			tz.push( data[i].wbCompleted);
            			categories.push(isGG ? data[i].departmentName : data[i].realName);
        			}
        			
        		}
        		containerXmstjOptions.series[0].data = ((projectType==''|| projectType=='projectType:2' || typeof(projectType)=='undefined') ? zj : [] );
        		containerXmstjOptions.series[1].data = ((projectType==''|| projectType=='projectType:1' || typeof(projectType)=='undefined') ? tz : [] );
        		containerXmstjOptions.xAxis.categories = categories;
        		var chart = new Highcharts.Chart(containerXmstjOptions);
        		
        		
        		//加载已完成项目占比（自建、投资）饼图
        		var zj_completed = 0;
        		var wb_completed = 0;
        		var zj_rate = 0;
        		var wb_rate = 0;
        		for(var i=0;i<data.length;i++){
        			if(i==10){
        				break;
        			}
        			zj_completed+=data[i].zjCompleted;
        			wb_completed+=data[i].wbCompleted;
        		}
        		zj_rate = ( projectType=='projectType:2' || projectType=='') ? ( zj_completed/(zj_completed+wb_completed) ) * 100 : 0;
        		wb_rate = ( projectType=='projectType:1' || projectType=='') ? ( wb_completed/(zj_completed+wb_completed) ) * 100 : 0;
        		containerXmstjBtOptions.series[0].data = [ 
        		                                           {name:"投资",y:parseFloat(wb_rate.toFixed(2)),num:wb_completed,color:"#6fbdeb"},
        		                                           {name:"创建",y:parseFloat(zj_rate.toFixed(2)),num:zj_completed,color:"#529be2"} 
        		                                         ];
        		var chart = new Highcharts.Chart(containerXmstjBtOptions);
        	}
        }
	});
	
}




/****************************************************************************
 * 项目数统计弹出层
 ***************************************************************************/
function pro_num_format(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return 0;
	} else{
		var projectType = $("#custom-toolbar-xmstj select[name='projectType']").val();
		//var projectType = $("select[name='projectType']").val();
		if(projectType==''){
			value = row.completed;
		} else if(projectType=='projectType:2'){
			value = row.zjCompleted;
		}else{
			value = row.wbCompleted;
		}
		/* 
		var id = row.userId;
		if(!isGG) id = row.departmentId;
		
		var id = isGG?row.departmentId:row.userId;
		*/
		var userid = row.userId;
		var deptid = row.departmentId;
		
		var options = "<a href='#' onclick='xmstjprojectList("+userid+","+deptid+")' class='blue'>"+value+"</a>";
		return options;
	}
	
	
}

function xmstjprojectList(userid,deptid){
	
	$.getHtml({
		url:platformUrl.topronumProjectlist,//模版请求地址	
		data:"",//传递参数
		okback : function() {
			if(isGG){
				queryParamsJson_pronum.deptid = deptid;
			}else{
				queryParamsJson_pronum.userId = userid;
				queryParamsJson_pronum.deptid = deptid;
			}
			
			$("#tc_title_name").html("项目数统计 ");
			
			$('#data-table-xmstj-projectlist').bootstrapTable({
				queryParamsType : 'size|page', // undefined
				pageSize : 10,
				showRefresh : false,
				sidePagination : 'server',
				method : 'post',
				pagination : true,
				search : false,
				url: platformUrl.proNumProjectlist,
				queryParams:function(params){
					return json_2_1(queryParamsJson_pronum,params);
				},
				onLoadSuccess : function(result) {
					$('#data-table-xmstj-projectlist').bootstrapTable('showColumn', 'departmentName');
				}
			});
		}
	});
	return false;
}


/**************************************************************************
 * 图表配置项
 **************************************************************************/
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
         name: '创建',
         color:'#4fd7cd',
         //data: [216, 210, 200, 180, 165, 155, 135, 125, 120, 100]
     }, {
         name: '投资',
         color:'#95b0c0',
         //data: [106, 106, 100, 90, 88, 66, 55, 44, 33, 22]
     }]
 };
 //项目数统计－－已完成项目占比饼图option
 var containerXmstjBtOptions = {
 	chart: {
 		renderTo:'container_xmstj_bt',
         plotBackgroundColor: null,
         plotBorderWidth: null,
         plotShadow: false,
         height :300,
         width:400
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
         pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b><br/>项目数: <b>{point.num}</b>'
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
                 name:'投资',
                 y:5,
                 num:98,
                 color:"#86c664"
             },
             { name:'创建', y: 20,num:98,color:"#ff955b"},
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
 
 