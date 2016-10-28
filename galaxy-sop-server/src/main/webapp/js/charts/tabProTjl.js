var url_tjl = platformUrl.meetingrate;
if(!isGG) url_tjl = platformUrl.meetingRateUser;


var pageNum_tjl = 1;
var queryParamsJson_tjl = {};

$("#querySearch_tjl").on('click',function(){
	$("#data-table-tjl").bootstrapTable('destroy');
	pro_tjl_init();
});

function pro_tjl_init(){
	//绑定querySearch事件
	$('#data-table-tjl').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getToobarQueryParams('custom-toolbar-tjl'));
		},
		pagination: true,
        search: false,
        url: url_tjl,
        onLoadSuccess: function(backdata){
        	queryParamsJson_tjl = eval("("+backdata.queryParamsJsonStr+")");
        	
        	if(!isGG) $('#data-table-tjl').bootstrapTable('showColumn', 'realName');
        	var options = $('#data-table-tjl').bootstrapTable('getOptions');
        	var data = options.data;
        	pageNum_tjl = options.pageNumber;
        	if(pageNum_tjl == 1){
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
        		containerTjlOptions.series[0].data = re;
        		containerTjlOptions.xAxis.categories = categories;
        		var chart = new Highcharts.Chart(containerTjlOptions);
        	}
        }
	});
}	


 
/****************************************************************************
 * 投决率统计弹出层
 ***************************************************************************/
 function cat_tjl(value, row, index){
	 var id= row.userId;
	if(value=='undefined' || value==0 || !value){
		return "0%";
	}else{
		var userid = row.userId;
		var deptid = row.departmentId;
		value = value * 100;
	 	var options = value==0 ? '0%' : "<a href='#' onclick='tjlprojectList("+userid+","+deptid+")' class='blue'>"+value.toFixed(2)+"%</a>";
	 	return options;
	}
}
 function tjlprojectList(userid,deptid){
	 $.getHtml({
			url:platformUrl.topronumProjectlist,//模版请求地址	
			data:"",//传递参数
			okback : function() {
				if(isGG){
					queryParamsJson_tjl.deptid = deptid;
				}else{
					queryParamsJson_tjl.userId = userid;
					queryParamsJson_tjl.deptid = deptid;
				}
				$("#tc_title_name").html("投决率统计 ");
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
						return json_2_1(queryParamsJson_tjl,params);
					},
					onLoadSuccess : function(result) {
						//console.log(result)
					}
				});
			}
		});
	return false;
}  


/**************************************************************************
 * 图表配置项
 **************************************************************************/
//投决率－－图表配置项
 var containerTjlOptions = {
 	chart: {
 		renderTo:'container_tjl',
         type: 'column',
         margin: [ 50, 50, 100, 80],
         height :340
         //width:1200
     },
     title: {
     	text:''
         //text: '投决率TOP10'
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
             text: '投决率 (%)'
         }
     },
     legend: {
         enabled: false
     },
     tooltip: {
 		 formatter:function(){
              return '投决率: ' + this.point.y.toFixed(2) +"%";
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
	