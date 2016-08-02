/**
 * Created by wangkun on 16/3/1.
 */
var one_click_number='';
$(function () {
	init(); //页面初始化
	
	function init(){
		//左侧菜单
		createMenus(6);
		//表单日期初始化
		var currDate = new Date();
		var sdate = currDate.format("yyyy-01-01");
		var edate = currDate.format("yyyy-MM-dd");
		$("#xmzl_sdate").val(sdate);
		$("#xmzl_edate").val(edate);	
		$("#xmstj_sdate").val(sdate);
		$("#xmstj_edate").val(edate);
		$("#ghl_sdate").val(sdate);
		$("#ghl_edate").val(edate);
		$("#tjl_sdate").val(sdate);
		$("#tjl_edate").val(edate);
		$("#xmzzl_sdate").val(currDate.format("yyyy-MM-01"));
		$("#xmzzl_edate").val(currDate.format("yyyy-MM-dd"));
		$("#xmzzl_eym").val(currDate.format("yyyy-MM"));
		currDate.setMonth(currDate.getMonth()-12)
		$("#xmzzl_sym").val(currDate.format("yyyy-MM"));
		//表单事业线下拉初始化
		getDeptOptionsList(['xmzl_deptid','xmzzl_deptid'],1);
		//默认加载项目总览数据
		loadDataXmzl();
		//切换tab，加载目标tab数据
		$('.tablink').tabchange({
			onchangeSuccess:function(index){
				switch(index){
					case 0: loadDataXmzl();  break;  //标签0:项目总览
					case 1: loadDataXmstj(); break;  //标签1:项目数统计
					case 2: loadDataXmzzl(); break;  //标签2:项目完成增长率统计
					case 3: loadDataGhl();   break;  //标签3:过会率统计
					case 4: loadDataTjl();   break;  //标签4:投决率统计
					default: return false;
				}
			}
		});
		//返回页面加载
		/*if(getCookieValue("backProjectList")!=''){
			$('#xmzl_deptid').val(getCookieValue('one_invest'))
			$('#xmzl_projectType').val(getCookieValue('one_project'));
			$('#xmzl_sdate').val(getCookieValue('one_time_1'));
			$('#xmzl_edate').val(getCookieValue('one_time_2'));
			
			$('#querySearch_xmzl').click(); //统计按钮
			
			
		}*/
		
		
	}
	//绑定querySearch事件
	$("#querySearch_xmzl").bind('click',function(){ loadDataXmzl();});
	$("#querySearch_xmstj").bind('click',function(){ loadDataXmstj(); });
	$("#querySearch_xmzzl").bind('click',function(){ loadDataXmzzl(); });
	$("#querySearch_ghl").bind('click',function(){ loadDataGhl(); });
	$("#querySearch_tjl").bind('click',function(){ loadDataTjl(); });
	//加载数据方法
	function loadDataXmzl(){
		var one_invest=$('#xmzl_deptid').val()
		var one_project=$('#xmzl_projectType').val();
		var one_time_1=$('#xmzl_sdate').val();
		var one_time_2=$('#xmzl_edate').val();
		//ie兼容
		/*setCookie("one_invest", one_invest,24,'/');
		setCookie("one_project", one_project,24,'/');
		setCookie("one_time_1one_time_1", one_time_1,24,'/');
		setCookie("one_time_2", one_time_2,24,'/');
		*/
		$("#xmstj_projectProgress").val('-1');
		load_data_chart_xmzl();
		
		
		if(forwardProgress && !forwardProgress==""){
			showDetails(forwardProgress);
		}else{
			load_data_xmzl();
		}
		
		
	}
	function loadDataXmstj(){
		load_data_xmstj();
		load_data_chart_xmstj();
	}
	function loadDataXmzzl(){
		load_data_xmzzl();
		load_data_chart_xmzzl();
	}
	function loadDataGhl(){
		load_data_ghl();
		load_data_chart_ghl();
	}
	function loadDataTjl(){
		load_data_chart_tjl();
		load_data_tjl();
	}
	
	
	/******************************************************************************************************************************
	 * tab切换，数据加载方法实现
	 ******************************************************************************************************************************/
	
	//加载表格数据－－项目总览
	function load_data_xmzl(){
		var obj = {
				url: platformUrl.projectlist,
				toolbar:'#custom-toolbasr-xmzl',
				pageSize: cookies_szie(),
				columns: [
				          {field:'project_code',align:'left',"class":"data-input",title:'项目编号'},
				          {field:'project_name',align:'left',"class":'data-name',title:'项目名称',formatter:'projectNameFormatter'},
				          {field:'project_careerline',align:'left',"class":'data-input',title:'投资事业线'},
				          {field:'project_progress_name',align:'left',"class":'data-input',title:'项目进度'},  //formatter:'progressFormatter'
				          {field:'partner',align:'left',"class":'data-input',title:'合伙人'},
				          {field:'user_name',align:'left',"class":'data-input',title:'投资经理'},
				          {field:'project_type',align:'left',"class":'data-input',title:'项目类型'},
				          {field:'project_contribution',align:'left',"class":'data-input',title:'投资金额（万）',formatter:'money_format'},
				          {field:'unit',align:'left',"class":'data-input',title:'资金单位'},
				          {field:'created_time',align:'left',"class":'data-input',title:'创建时间'},
				          {field:'updated_time',align:'left',"class":'data-input',title:'最后修改时间'},
				          {field:'ft_count',align:'left',"class":'data-input',title:'访谈记录数',visible:false},
				          {field:'np_meeting_count',align:'left',"class":'data-input',title:'评审次数',visible:false},
				          {field:'np_meeting_success_rate',align:'left',"class":'data-input',title:'成功率',formatter:'rate_format',visible:false},
				          {field:'lxh_meeting_count',align:'left',"class":'data-input',title:'过会次数',visible:false},
				          {field:'lxh_return_status',align:'left',"class":'data-input',title:'会议结论',visible:false},
				          {field:'lxh_meeting_date',align:'left',"class":'data-input',title:'会议时间',visible:false},
				          {field:'lxh_duration_day',align:'left',"class":'data-input',title:'会议历时',visible:false},
				          {field:'tzyxs_status',align:'left',"class":'data-input',title:'状态',visible:false},
				          {field:'tzyxs_duration_hours',align:'left',"class":'data-input',title:'历时时间(H)',visible:false},
				          {field:'tjh_meeting_count',align:'left',"class":'data-input',title:'过会次数',visible:false},
				          {field:'tjh_return_status',align:'left',"class":'data-input',title:'会议结论',visible:false},
				          {field:'tjh_meeting_date',align:'left',"class":'data-input',title:'会议时间',visible:false},
				          {field:'th_bk_count',align:'left',"class":'data-input',title:'拨款次数',visible:false},
				          {field:'th_bk_amount_all',align:'left',"class":'data-input',title:'拨款总额',visible:false}
				],onLoadSuccess:function(){
					//显示页码
					if(getCookieValue("one_click_number")==''){
						if(getCookieValue("tempPageNum")!=''){
							$('.pagination li').removeClass('active');
							if($('.pagination .page-number').length< getCookieValue("tempPageNum")){
								for(var i=$('.pagination .page-number').length; i>0; i--){
									$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+222+'</a>');
								}
							}

							$('.pagination li').each(function(){
			        			if($(this).text()==getCookieValue("tempPageNum")){
			        				$(this).click();
			        				//$(this).addClass('active')
			        			}
							})
							deleteCookie("PageSize_ab",'/');
							deleteCookie("tempPageNum",'/');
						}
						
						deleteCookie("backProjectList","/");
					}
					if(getCookieValue("one_click_number")!=''){
						showDetails(getCookieValue("one_click_number"));
						
						
					}
					
                }
		};
		$('#data-table-xmzl').bootstrapTable('destroy');
		$('#data-table-xmzl').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));	
	}
	function load_data_chart_xmzl(){
    	var obj ={url: platformUrl.projectprogress};
		obj.contentType="application/json";
		obj.data = getToobarQueryParams('custom-toolbasr-xmzl');
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
	}
	
	//加载数据－－项目数统计
	function load_data_xmstj(){
		var obj = {
			url: isHHR=='true' ? platformUrl.linehhrchart : platformUrl.gglinechart,
			toolbar:'#custom-toolbar-xmstj',
			columns: 
				isHHR=='true' ? 
					[
			          {field:'real_name',align:'left',"class":'data-input',title:'投资经理'},
			          {field:'department_name',align:'left',"class":'data-input',title:'投资事业线'},
			          {field:'rate',align:'left',"class":'data-input',title:'完成率',formatter:'rate_format'},
			          {field:'target',align:'left',"class":'data-input',title:'目标数'},
			          {field:'completed',align:'left',"class":'data-input',title:'项目数',formatter:'cat_xmstj'},
			          {field:'notcompleted',align:'left',"class":'data-input',title:'未完成'}
					]:
					[
			          {field:'department_name',align:'left',"class":'data-input',title:'投资事业线'},
			          {field:'rate',align:'left',"class":'data-input',title:'完成率',formatter:'rate_format'},
			          {field:'target',align:'left',"class":'data-input',title:'目标数'},
			          {field:'completed',align:'left',"class":'data-input',title:'项目数',formatter:'cat_xmstj'},
			          {field:'notcompleted',align:'left',"class":'data-input',title:'未完成'}
					]
		};
		$('#data-table-xmstj').bootstrapTable('destroy');
		$('#data-table-xmstj').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));

	}
	function load_data_chart_xmstj(){
    	var toobarParams=getToobarQueryParams('custom-toolbar-xmstj');
    	toobarParams.model=1;
    	toobarParams.pageNum=0;
    	toobarParams.pageSize=10;
    	var obj ={url:isHHR=='true' ? platformUrl.linehhrchart : platformUrl.gglinechart};
    	obj.contentType="application/json";
    	obj.data = toobarParams;
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
    		containerXmstjOptions.series[1].data = ( toobarParams.projectType=='projectType:1' || toobarParams.projectType=='-1' ? tz : [] );
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
    }
	
	//加载数据－－项目完成增长率统计
	$("input[name='radio_xmzzl']").bind('change',function(){
		var xmzzl_radio_type = $("input[name='radio_xmzzl']:checked").val();
		if(xmzzl_radio_type==1){ //日
			$("#xmzzl_dd_day").show();
			$("#xmzzl_dd_month").hide();
		}else if(xmzzl_radio_type==2){//周
			
		}else if(xmzzl_radio_type==3){//月
			$("#xmzzl_dd_day").hide();
			$("#xmzzl_dd_month").show();
		}else{
			return false;
		}
	});
	
	//项目增长率
	function load_data_xmzzl(){
		var obj = {url:'',toolbar:'#custom-toolbar-xmzzl'};
		var radio_xmzzl_type = $("input[name='radio_xmzzl']:checked").val();
		if(radio_xmzzl_type==1) {//日报
			obj.url = platformUrl.rateRiseD;
		}else if(radio_xmzzl_type==2){//周报
			
		} else if(radio_xmzzl_type==3){ //月报
			obj.url = platformUrl.rateRiseM;
		}else{
			return false;
		}
		$('#data-table-xmzzl').bootstrapTable('destroy');
		$('#data-table-xmzzl').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
	}
	function load_data_chart_xmzzl(){
		var obj = {contentType:"application/json"};
		var radio_xmzzl_type = $("input[name='radio_xmzzl']:checked").val();
		if(radio_xmzzl_type==1) {//日报
			obj.url = platformUrl.rateRiseDChart;
		}else if(radio_xmzzl_type==2){//周报
			
		} else if(radio_xmzzl_type==3){//月报
			obj.url = platformUrl.rateRiseMChart;
		}else{
			return false;
		}
    	obj.data = getToobarQueryParams('custom-toolbar-xmzzl');
    	ajaxCallback(obj,function(data){
    		//console.log(data);
    		var result = data.result;
    		var entityList = data.mapList;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var completed = [];
    		var riserate = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			completed.push(entityList[i].completed);
    			categories.push(entityList[i].biz_date.replace(/-/g,''));
    			
    			var pre_completed = ( i==0?0:entityList[i-1].completed ) ;
    			var rate = ( i==0 ? 0 : ((entityList[i].completed-pre_completed)/pre_completed )*100 );
    			riserate.push( parseFloat( rate.toFixed(2) ));
    		}
    		containerXmzzlOptions.series[0].data = riserate;
    		containerXmzzlOptions.series[1].data = completed;
    		containerXmzzlOptions.xAxis.categories = categories;
    		if(categories.length<=13){
    			containerXmzzlOptions.xAxis.labels.align='center';
    			containerXmzzlOptions.xAxis.labels.rotation=0;
    		}
    		var chart = new Highcharts.Chart(containerXmzzlOptions);
    	});
    }
	
	//加载数据－－过会率统计
	function load_data_ghl(){
		var obj = {
				url: isHHR=='true' ? platformUrl.meetingRateUser : platformUrl.meetingrate,
				toolbar:'#custom-toolbar-ghl',
				columns: 
					isHHR=='true' ? 
						[
				          {field:'real_name',align:'left',"class":'data-input',title:'投资经理'},
				          {field:'dept_name',align:'left',"class":'data-input',title:'投资事业线'},
				          {field:'prate',align:'left',"class":'data-input',title:'过会率',formatter:'cat_ghl'},
				          {field:'total',align:'left',"class":'data-input',title:'过会项目数'},
				          {field:'pnum',align:'left',"class":'data-input',title:'通过数'},
				          {field:'fnum',align:'left',"class":'data-input',title:'失败数'},
				          {field:'snum',align:'left',"class":'data-input',title:'待定数'}
						]:
						[
				          {field:'dept_name',align:'left',"class":'data-input',title:'投资事业线'},
				          {field:'prate',align:'left',"class":'data-input',title:'过会率',formatter:'cat_ghl'},
				          {field:'total',align:'left',"class":'data-input',title:'过会项目数'},
				          {field:'pnum',align:'left',"class":'data-input',title:'通过数'},
				          {field:'fnum',align:'left',"class":'data-input',title:'失败数'},
				          {field:'snum',align:'left',"class":'data-input',title:'待定数'}
						]
		};
		$('#data-table-ghl').bootstrapTable('destroy');
		$('#data-table-ghl').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
		
		
	}
	function load_data_chart_ghl(){
    	var obj ={
    			url: (isHHR=='true' ? platformUrl.meetingRateUser : platformUrl.meetingrate),
    			contentType : "application/json",
    			data : getToobarQueryParams('custom-toolbar-ghl')
    	};
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.pageList.content;
    		var re = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			re.push(entityList[i].prate*100);
    			categories.push(isHHR=='true'?entityList[i].real_name:entityList[i].dept_name);
    		}
    		containerGhOptions.series[0].data = re;
    		containerGhOptions.xAxis.categories = categories;
    		var chart = new Highcharts.Chart(containerGhOptions);
    	});
    }
	
	//加载数据－－投决率统计
	function load_data_tjl(){
		var obj = {
				url: isHHR=='true' ? platformUrl.meetingRateUser : platformUrl.meetingrate,
				toolbar:'#custom-toolbar-tjl',
				columns: 
					isHHR=='true' ? 
						[
				          {field:'real_name',align:'left',"class":'data-input',title:'投资经理'},
				          {field:'dept_name',align:'left',"class":'data-input',title:'投资事业线'},
				          {field:'prate',align:'left',"class":'data-input',title:'过会率',formatter:'cat_tjl'},
				          {field:'total',align:'left',"class":'data-input',title:'过会项目数'},
				          {field:'pnum',align:'left',"class":'data-input',title:'通过数'},
				          {field:'fnum',align:'left',"class":'data-input',title:'失败数'},
				          {field:'snum',align:'left',"class":'data-input',title:'待定数'}
						]:
						[
				          {field:'dept_name',align:'left',"class":'data-input',title:'投资事业线'},
				          {field:'prate',align:'left',"class":'data-input',title:'过会率',formatter:'cat_tjl'},
				          {field:'total',align:'left',"class":'data-input',title:'过会项目数'},
				          {field:'pnum',align:'left',"class":'data-input',title:'通过数'},
				          {field:'fnum',align:'left',"class":'data-input',title:'失败数'},
				          {field:'snum',align:'left',"class":'data-input',title:'待定数'}
						]
		};
		
		$('#data-table-tjl').bootstrapTable('destroy');
		$('#data-table-tjl').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
	}
	function load_data_chart_tjl(){
    	var obj ={url: isHHR=='true' ? platformUrl.meetingRateUser : platformUrl.meetingrate};
    	obj.contentType="application/json";
    	obj.data = getToobarQueryParams('custom-toolbar-tjl');
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.pageList.content;
    		var re = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			re.push(entityList[i].prate*100);
    			categories.push(isHHR=='true'?entityList[i].real_name:entityList[i].dept_name);
    		}
    		containerTjlOptions.series[0].data = re;
    		containerTjlOptions.xAxis.categories = categories;
    		var chart = new Highcharts.Chart(containerTjlOptions);
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




/******************************************************************************************************************************
 * 图表配置项定义
 ******************************************************************************************************************************/
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
//                fontSize: '13px',
//                fontFamily: '宋体',
//                textShadow: '0 0 3px black'
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

//项目完成增长率－－图表配置项
var containerXmzzlOptions = {
		chart: {
			renderTo:'container_xmzzl',type: 'line',//margin: [ 50, 50, 100, 80]
        },
        title: {
            text: '项目完成率分析',
            align:'left',  
            style:{
                fontSize:'18px',
                fontFamily:'微软雅黑',
                color:'#3e4351',
            },        
        },        //去除版权
        credits: {enabled:false},
        //去除右上角导出图标
        exporting: {enabled:false},
        xAxis: {
        	lineWidth: 1,
            lineColor: "#e9ebf2",
            tickWidth: 0,
            //categories: ['2015-01','2015-02','2015-03','2015-04','2015-05','2015-06','2015-07','2015-08','2015-09','2015-10','2015-11','2015-12' ],
            labels: {
                rotation: -45,
                align: 'right',
                //step: 5,
                //staggerLines: 1,
                formatter:function(){
                	return this.value;
                },
                style: {fontSize: '12px',fontFamily: '宋体'}
            }
        },
        yAxis: [{ // Primary yAxis
            labels: {
                format: '{value}%',
                style: {color: '#606060'}
            },
            title: {
                text: '环比 (%)',
            }
        }, { // Secondary yAxis
            title: {
                text: '完成项目数（个）',
            },
            labels: {
                format: '{value} 个',
                style: {color: '#606060'}
            },
            gridLineColor: '#e9ebf2',
            gridLineWidth: 1,
            min: 0,
            opposite: true,
        }],
        legend: {
            floating: true,
            verticalAlign: 'top',
            align: 'center',
            backgroundColor: '#FFFFFF',
            itemStyle:{
                fontWeight:'normal',
                color:'#525662',
            },

        },
        tooltip: {shared: true},
        series: [{
            name: '环比',
            color: '#65ade7',
            //data: [15.3,18.6,10.1,5.9,1.8,-14.9,-9.3,-3.8,2.5,8.9,-2.5,19.8],
            tooltip: {
               valueSuffix: ' %'
            },
            dataLabels: {
                enabled: false,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 20,
                style: {
                    fontSize: '12px',
                    fontFamily: '宋体',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
         			return this.point.y + '%';
       
    			}
            }
        },{
            name: '完成项目数',
            color: '#ff9c89',
            //data: [30,55,61,65,66,56,50,48,48,52,51,61],
            tooltip: {
               valueSuffix: ' 个'
            },
            yAxis: 1,
            dataLabels: {
                enabled: false,
                rotation: 0,
                color: '#FFFFFF',
                align: 'center',
                x: 0,
                y: 10,
                style: {
                    fontSize: '12px',
                    fontFamily: '宋体',
                    textShadow: '0 0 3px black'
                },
                formatter:function(){
         			return this.point.y;
       
    			},
            }
        }]	
}

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


//项目总览，点击图表categories切换下方表格
function showDetails(dict_code){
	/*缓存个数*/
	//ie兼容
	one_click_number=dict_code;
	
	/*var hidden_field=['created_time','updated_time','ft_count','np_meeting_count','np_meeting_success_rate','lxh_meeting_count','lxh_return_status','lxh_meeting_date',
	                  'lxh_duration_day','tzyxs_status','tzyxs_duration_hours','tjh_meeting_count','tjh_return_status','tjh_meeting_date','th_bk_count','th_bk_amount_all'];
	for(f in hidden_field){
		$('#data-table-xmzl').bootstrapTable('hideColumn',hidden_field[f]);
	}*/
	
	var columns = [{field:'project_code',align:'left',"class":'data-input',title:'项目编号'},
			       	  {field:'project_name',align:'left',"class":'data-name',title:'项目名称',formatter:'projectNameFormatter'},
			          {field:'project_careerline',align:'left',"class":'data-input',title:'投资事业线'},
			          {field:'project_progress_name',align:'left',"class":'data-input',title:'项目进度'}, //,formatter:'progressFormatter'
			          {field:'partner',align:'left',"class":'data-input',title:'合伙人'},
			          {field:'user_name',align:'left',"class":'data-input',title:'投资经理'},
			          {field:'project_type',align:'left',"class":'data-input',title:'项目类型'},
			          {field:'project_contribution',align:'left',"class":'data-input',title:'投资金额（万）',formatter:'money_format'},
			          {field:'unit',align:'left',"class":'data-input',title:'资金单位'}];
	
	if(dict_code==1){//接触访谈
		columns.push({field:'created_time',align:'left',"class":'data-input',title:'创建时间'});
		columns.push({field:'ft_count',align:'left',"class":'data-input',title:'访谈记录数'});
		$("#xmstj_projectProgress").val('projectProgress:1');
	}else if(dict_code==2){//内部评审
		columns.push({field:'np_meeting_count',align:'left',"class":'data-input',title:'评审次数'});
		//columns.push({field:'np_meeting_success_rate',align:'left',"class":'data-input',title:'成功率',formatter:'rate_format'});
		$("#xmstj_projectProgress").val('projectProgress:2');
	}else if(dict_code==3){//ceo评审
		columns.push({field:'ceops_meeting_count',align:'left',"class":'data-input',title:'评审次数'});
		///columns.push({field:'ceops_meeting_success_rate',align:'left',"class":'data-input',title:'成功率',formatter:'rate_format'});
		$("#xmstj_projectProgress").val('projectProgress:3');
	}else if(dict_code==4){//立项会
		columns.push({field:'lxh_meeting_count',align:'left',"class":'data-input',title:'过会次数'});
		columns.push({field:'lxh_return_status',align:'left',"class":'data-input',title:'会议结论'});
		columns.push({field:'lxh_meeting_date',align:'left',"class":'data-input',title:'会议时间'});
		$("#xmstj_projectProgress").val('projectProgress:4');
	}else if(dict_code==5){//投资意向书
		columns.push({field:'tzyxs_status',align:'left',"class":'data-input',title:'状态'});
		columns.push({field:'tzyxs_duration_hours',align:'left',"class":'data-input',title:'历时时间(H)'});
		$("#xmstj_projectProgress").val('projectProgress:5');
	}else if(dict_code==6){//尽职调查
		columns.push({field:'jzdc_completed_rate',align:'left',"class":'data-input',title:'完成度',formatter:'rate_format'});
		columns.push({field:'jzdc_duration_hour',align:'left',"class":'data-input',title:'历时时间(H)'});
		$("#xmstj_projectProgress").val('projectProgress:6');
	}else if(dict_code==7){//投资决策会
		columns.push({field:'tjh_meeting_count',align:'left',"class":'data-input',title:'过会次数'});
		columns.push({field:'tjh_return_status',align:'left',"class":'data-input',title:'会议结论'});
		columns.push({field:'tjh_meeting_date',align:'left',"class":'data-input',title:'会议时间'});
		$("#xmstj_projectProgress").val('projectProgress:7');
	}else if(dict_code==8){//投资协议
		columns.push({field:'tzxy_status',align:'left',"class":'data-input',title:'状态'});
		columns.push({field:'tzxy_duration_hour',align:'left',"class":'data-input',title:'历时时间(H)'});
		$("#xmstj_projectProgress").val('projectProgress:8');
	}else if(dict_code==9){//股权交割
		columns.push({field:'gqjg_status',align:'left',"class":'data-input',title:'状态'});
		columns.push({field:'gqjg_duration_hour',align:'left',"class":'data-input',title:'历时时间(H)'});
		$("#xmstj_projectProgress").val('projectProgress:9');
	}else if(dict_code==10){//投后运营
		columns.push({field:'th_bk_count',align:'left',"class":'data-input',title:'拨款次数'});
		columns.push({field:'th_bk_amount_all',align:'left',"class":'data-input',title:'拨款总额'});
		$("#xmstj_projectProgress").val('projectProgress:10');
	}
	
	load_data_xmzl2(columns);
}   

function load_data_xmzl2(columns){
	var obj = {
		url: platformUrl.projectlist,
		toolbar:'#custom-toolbasr-xmzl',
		pageSize: cookies_szie(),
		columns: columns,
		onLoadSuccess:function(){
		//显示页码
		if(getCookieValue("one_click_number")!=''){
			$('.pagination li').removeClass('active');
			if($('.pagination .page-number').length< getCookieValue("tempPageNum")){
				for(var i=$('.pagination .page-number').length; i>0; i--){
					$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+i+'</a>');
				}
			}

			$('.pagination li').each(function(){
    			if($(this).text()==getCookieValue("tempPageNum")){
    				$(this).click();
    				//$(this).addClass('active')
    			}
			})
			deleteCookie("tempPageNum",'/');
			deleteCookie("one_click_number","/");
			one_click_number='';
		}

		deleteCookie("PageSize_ab","/");
		deleteCookie("backProjectList","/");

    }
		};
	$('#data-table-xmzl').bootstrapTable('destroy');
	$('#data-table-xmzl').bootstrapTable($.extend({},DefaultBootstrapTableOptions,obj));
}
function projectNameFormatter(val,row,index)
{
	if(val.length>10){
		var str=val.substring(0,10);
		return '<a href="#" class="blue" title="'+val+'" onclick="showProjectDetail(\'' + row.project_id+  '\')">'+str+'</a>';
	}
	else{
		return '<a href="#" class="blue" title="'+val+'" onclick="showProjectDetail(\'' + row.project_id+  '\')">'+val+'</a>';
	}
	
}
function progressFormatter(val,row,index)
{
	return '<a href="#" class="blue" onclick="showProjectStage(\'' + row.project_id + '\')">'+val+'</a>'
}
function showProjectDetail(projectId)
{
	
	var options = $("#data-table-xmzl").bootstrapTable('getOptions');
	var PageSize_ab = options.pageSize ? options.pageSize : 10;
	var tempPageNum = options.pageNumber ? options.pageNumber : 1;
	
	//ie兼容
	setCookie("PageSize_ab", PageSize_ab,24,'/');
	setCookie("tempPageNum", tempPageNum,24,'/');
	//清除返回的页码
	deleteCookie("number_on","/");
	//哪一个
	setCookie("one_click_number", one_click_number,24,'/');

	window.location.href = platformUrl.projectDetail+projectId  +'?mark=m';
}
function showProjectStage(projectId)
{
	var _url=platformUrl.projectStage4Manager+projectId;
	$.getHtml({
		url:_url,//模版请求地址
		data:""
	});
}