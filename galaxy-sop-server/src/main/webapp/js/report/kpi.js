/**
 * Created by wangkun on 16/3/1.
 */
$(function () {
	
	init(); //页面初始化
	
	function init(){
		//左侧菜单
		createMenus(7);
		//表单日期初始化
		var currDate = new Date();
		var sdate = currDate.format("yyyy-01-01");
		var edate = currDate.format("yyyy-MM-dd");
		$("#userkpi_sdate").val(sdate);
		$("#userkpi_edate").val(edate);
		$("#deptkpi_sdate").val(sdate);
		$("#deptkpi_edate").val(edate);
		//表单事业线下拉初始化
		var targetIdObj = ['userkpi_deptid'];
		var type = 1;
		getDeptOptionsList(targetIdObj,type);
		//默认加载项目总览数据
		load_data_userkpi();
		load_data_chart_userkpi()
		//切换tab，加载目标tab数据
		$('.tablink').tabchange({
			onchangeSuccess:function(index){
				switch(index){
					case 0: loadDataUserKpi(); break; //标签0:投资经理绩效
					case 1: loadDataDetpKpi(); break; //标签1:团队绩效
					default: return false;
				}
			}
		});
	}
	//绑定querySearch事件
	$("#querySearch_userkpi").bind('click',function(){ loadDataUserKpi(); });
	$("#querySearch_deptkpi").bind('click',function(){ loadDataDetpKpi(); });
	//加载数据方法
	function loadDataUserKpi(){
		load_data_chart_userkpi();
		load_data_userkpi();
	}
	function loadDataDetpKpi(){
		load_data_chart_deptkpi();
		load_data_deptkpi();
	}

	/**************************************************************************
	 * tab切换，数据加载方法实现
	 **************************************************************************/
	//投资经理绩效考核
	function load_data_userkpi(){
		var obj = {url: platformUrl.userkpi,toolbar:'#custom-toolbasr-userkpi'};
		$('#data-table-userkpi').bootstrapTable('destroy');
		$('#data-table-userkpi').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
	}
	function load_data_chart_userkpi(){
    	var userkpi_sdate = $("#userkpi_sdate").val();
    	var userkpi_edate = $("#userkpi_edate").val();
    	var userkpi_projectType = $("#userkpi_projectType").val();
    	var userkpi_deptid = $("#userkpi_deptid").val();
    	//加载图表
    	var obj ={url:platformUrl.userkpi};
    	obj.contentType="application/json";
    	obj.data = getToobarQueryParams('custom-toolbasr-userkpi');
    	obj.data.datatype=-1;
    	obj.data.pageNum=0;
    	obj.data.pageSize=10;
    	ajaxCallback(obj,function(data){
    		//console.log(data);
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.pageList.content;
    		var re = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			re.push(entityList[i].completed);
    			categories.push(entityList[i].real_name);
    		}
    		//console.log(re);
    		containerUserKpiOptions.series[0].data = re;
    		containerUserKpiOptions.xAxis.categories = categories;
    		var chart = new Highcharts.Chart(containerUserKpiOptions);
    	});
    }
	
	//团队绩效考核
	function load_data_deptkpi(){
		var obj = {url: platformUrl.deptkpi,toolbar:'#custom-toolbasr-deptkpi'};
		$('#data-table-deptkpi').bootstrapTable('destroy');
		$('#data-table-deptkpi').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
	}
	function load_data_chart_deptkpi(){
    	var deptkpi_sdate = $("#deptkpi_sdate").val();
    	var deptkpi_edate = $("#deptkpi_edate").val();
    	var deptkpi_projectType = $("#deptkpi_projectType").val();
    	//加载图表
    	var obj ={url:platformUrl.deptkpi};
    	obj.contentType="application/json";
    	obj.data = getToobarQueryParams('custom-toolbasr-deptkpi');
    	ajaxCallback(obj,function(data){
    		//console.log(data);
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.pageList.content;
    		var re = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			re.push(entityList[i].completed);
    			categories.push(entityList[i].dept_name);
    		}
    		//console.log(re);
    		containerDeptKpiOptions.series[0].data = re;
    		containerDeptKpiOptions.xAxis.categories = categories;
    		var chart = new Highcharts.Chart(containerDeptKpiOptions);
    	});
    }
	
	//根据toobar id 获取表单参数
	function getToobarQueryParams(ToolbarId){
		var toolbar = $("#"+ToolbarId);
		var query = {};
    	toolbar.find("input[name][type!='radio']").each(function(){
    		var input = $(this);
    		var name = input.attr("name");
    		var val = input.val();
    		if(val!=''){
    			query[name]=val;
    		}
    	});
    	toolbar.find("input[type='radio']").each(function(){
    		var input = $(this);
    		var name = input.attr("name");
    		if(input.attr("checked")=="checked"||input.prop("checked")==true){
    			var val = input.val();
        		if(val!=''){
        			query[name]=val;
        		}
    		}
    	});
    	toolbar.find("select[name]").each(function(){
    		var select = $(this);
    		var name = select.attr("name");
    		var val = select.val();
    		if(val!=''){
    			query[name]=val;
    		}
    	});
    	
    	return query;
	}
});

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
    }]	
};