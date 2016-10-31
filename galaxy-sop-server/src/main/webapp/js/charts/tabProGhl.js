var gul_url = platformUrl.meetingrate;
if(!isGG) gul_url = platformUrl.meetingRateUser;

var pageNum_ghl = 1;
var queryParamsJson_ghl = {};

$("#querySearch_ghl").on('click',function(){
	$("#data-table-ghl").bootstrapTable('destroy');
	pro_ghl_init();
});

function pro_ghl_init(){
	//绑定querySearch事件
	$('#data-table-ghl').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getToobarQueryParams('custom-toolbar-ghl'));
		},
		pagination: true,
        search: false,
        url: gul_url,
        onLoadSuccess: function(backdata){
        	queryParamsJson_ghl = eval("("+backdata.queryParamsJsonStr+")");
        	
        	if(!isGG) $('#data-table-ghl').bootstrapTable('showColumn', 'realName');
        	var options = $('#data-table-ghl').bootstrapTable('getOptions');
        	var data = options.data;
        	pageNum_ghl = options.pageNumber;
        	if(pageNum_ghl == 1){
        		var re = [];
        		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i>=10){
        				break;
        			}else{
        				re.push(data[i].rate*100);
            			categories.push(isGG ? data[i].departmentName : data[i].realName);
        			}
        		}
        		containerGhOptions.series[0].data = re;
        		containerGhOptions.xAxis.categories = categories;
        		var chart = new Highcharts.Chart(containerGhOptions);
        	}
        }
	});
}


	 
 
/****************************************************************************
 * 过会率统计弹出层
 ***************************************************************************/
 function cat_ghl(value, row, index){
	var id= row.userId;
	if(value=='undefined' || value==0 || !value){
		return "0%";
	}else{
		var userid = row.userId;
		var deptid = row.departmentId;
		value = value * 100;
		var options = "<a href='#' onclick='ghlprojectList("+userid+","+deptid+")' class='blue'>"+value.toFixed(2)+"%</a>";
		return options;	
	}
}
 function ghlprojectList(userid,deptid){
	 $.getHtml({
			url:platformUrl.topronumProjectlist,//模版请求地址	
			data:"",//传递参数
			okback : function() {
				if(isGG){
					queryParamsJson_ghl.deptid = deptid;
				}else{
					queryParamsJson_ghl.userId = userid;
					queryParamsJson_ghl.deptid = deptid;
				}
				
				$("#tc_title_name").html("过会率统计 ");
				
				$('#data-table-xmstj-projectlist').bootstrapTable({
					queryParamsType : 'size|page', // undefined
					pageSize : 10,
					showRefresh : false,
					sidePagination : 'server',
					method : 'post',
					pagination : true,
					search : false,
					url: platformUrl.meetRateProjectlist,
					queryParams:function(params){
						return json_2_1(queryParamsJson_ghl,params);
					},
					onLoadSuccess : function(result) {
						//console.log(result)
					}
				});
			}
		});
	 /* 
	$.getHtml({
		url:_url,//模版请求地址	
		data:"",//传递参数
		okback:function(){
			$("#ghl_projectlist_sdate").val( $("#ghl_sdate").val() );
 			$("#ghl_projectlist_edate").val( $("#ghl_edate").val() );
			isHHR=='true' ? $("#ghl_projectlist_userid").val(id) : $("#ghl_projectlist_deptid").val(id);
			var obj = {url: platformUrl.projectlist,toolbar:'#custom-toolbasr_ghl_projectlist'}
			$('#data-table-ghl-projectlist').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
		}
	}); */
	return false;
}


/**************************************************************************
 * 图表配置项
 **************************************************************************/
//过会率－－图表配置项
 var containerGhOptions = {
 	chart: {
 		renderTo:'container_ghl',
         type: 'column',
         margin: [ 50, 50, 100, 80],
         height :340
         //width:1200
     },
     title: {
     	text:''
         //text: '过会率TOP10'
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
         	pointPadding: 0.2,
             borderWidth: 0,
             pointWidth: 20
         }
     },
     xAxis: {
     	lineWidth: 1,
         lineColor: "#e9ebf2",
         tickWidth:'0',
     	categories:{},
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
             text: '过会率 (%)'
         }
     },
     legend: {
         enabled: false
     },
     tooltip: {
     	formatter:function(){
     		return '过会率: ' + this.point.y.toFixed(2) +"%";
         }
     },
     series: [{
         name: 'Population',
         color:'#587edd',
         //data: [9,8,15,4,23,3,2,30,20],
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
             	return this.point.y.toFixed(2) +"%";
             },
         }
     }]
 };