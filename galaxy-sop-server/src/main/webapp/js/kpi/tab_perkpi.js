


//====
var userkpi_url = platformUrl.userkpi;
var userkpi_pageNum = 1;


function per_kpi_init(){
	//表单事业线下拉初始化
	createCareelineOptions(platformUrl.getCareerlineListByRole,"deptid","");
	
	$("#querySearch_perkpi").on('click',function(){
		$('#data-table-userkpi').bootstrapTable('refresh',getToobarQueryParams('custom-toolbar-userkpi')); 
	});
	
	//绑定querySearch事件
	$('#data-table-userkpi').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		queryParams:function(params){
			return json_2_1(params,getToobarQueryParams('custom-toolbasr-userkpi'));
		},
		pagination: true,
        search: false,
        url: userkpi_url,
        onLoadSuccess: function(backdata){
        	var options = $('#data-table-userkpi').bootstrapTable('getOptions');
        	//var data = $('#data-table-userkpi').bootstrapTable('getData');
        	var data = options.data;
        	userkpi_pageNum = options.pageNumber;
        	if(userkpi_pageNum == 1){
        		var re = [];
    	   		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i>=10){
        				break;
        			}else{
        				re.push(data[i].completed);
           	   			categories.push(data[i].realName);
        			}
            	}
    	   		containerUserKpiOptions.series[0].data = re;
    	   		containerUserKpiOptions.xAxis.categories = categories;
    	   		var chart = new Highcharts.Chart(containerUserKpiOptions);
        	}
        }
	});
}

//===========




/**************************************************************************
 * 图表配置项
 **************************************************************************/
//投资经理绩效，图表配置项
var containerUserKpiOptions = {
    chart: {
    	renderTo:'container_userkpi',
        type: 'column',
        margin: [ 50, 50, 100, 80],
        height :340,
    },
    title: {
        text: '个人项目数TOP10',
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
        //categories: ['李晓雨','李一雨','李二雨','李三雨','李四雨','李五雨','李六雨','李雨','李晓'],
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
    series: [{
        name: 'Population',
        color:'#9dd2fc',
        //data: [30,23,20,15,13,10,6,6,5],
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
    }]
};    
