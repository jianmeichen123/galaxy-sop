<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>绩效考核</title>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->

<!-- bootstrap-table -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>

<jsp:include page="../../common/taglib.jsp" flush="true"></jsp:include>
</head>


<body>

<jsp:include page="../../common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
		<h2>绩效考核</h2>

		<div class="tabtable assessment">
			<!-- tab标签 -->
            <ul class="tablink tablinks">
				<li class="on"><a href="javascript:;" onclick="toperKpi()">个人绩效考核</a></li>
				<li><a href="javascript:;" onclick="toteamKpi()">团队绩效考核</a></li>
            </ul>

		<div class="tabtable_con">
			<div class="search_box searchall" id="custom-toolbasr-userkpi">
				<dl class="fmdl fmmr clearfix">
					<dt>投资事业线：</dt>
					<dd>
						<select name="deptid" id="userkpi_deptid">
							<option value="">全部</option>
						</select>
					</dd>
				</dl>
				<dl class="fmdl fmmr clearfix">
					<dt>项目类型：</dt>
					<dd>
						<select name="projectType" id="userkpi_projectType">
							<option value="">全部</option>
							<option value="projectType:2">内部创建</option>
							<option value="projectType:1">外部投资</option>
						</select>
					</dd>
				</dl>
				<dl class="fmdl fmmr clearfix">
					<dt>项目创建时间：</dt>
					<dd>
						<input type="text" class="txt time datepicker" name="sdate" id="userkpi_sdate" value="" /> 
						<span>至</span> 
						<input type="text" class="txt time datepicker" name="edate" id="userkpi_edate" value="" />
					</dd>
					<dd>
						<a href="javascript:;" class="bluebtn ico tj" action="querySearch">统计</a>
					</dd>
				</dl>
			</div>


			<!--柱状图部分-->
			<div class="chartbox">
				<div id="container_userkpi"></div>
			</div>
			
			
			<!--表格内容-->
			<table id="data-table-userkpi" data-toolbar="#custom-toolbasr-userkpi"
				width="100%" cellspacing="0" cellpadding="0" class="table_m" >
				<thead>
					<tr>
						<th data-field=realName  		 class="data-input">	姓名			</th>
						<th data-field="departmentName"  class="data-input">	投资事业线	</th>
						<th data-field="target"  		 class="data-input">	目标数		</th>
						<th data-field="completed"  	 class="data-input">	项目数		</th>
						<th data-field="completedAll"    class="data-input">	累计已完成数	</th>
						<th data-field="companyRank"     class="data-input">	公司排名		</th>   <!--  data-formatte="com_ranking" -->
						<th data-field="deptRank"  		 class="data-input">	部门排名		</th>
						<th data-field="totalRate"  	 class="data-input" data-formatter="rate_format">公司完成数占比</th>
						<th data-field="deptRate" 	     class="data-input" data-formatter="rate_format">部门完成数占比</th>
						<th data-field="lxhPnumber"  	 class="data-input">	立项会通过数	</th>
						<th data-field="tjhPnumber"  	 class="data-input">	投资决策会通过数</th>
						<th data-field="ghlRate"  		 class="data-input" data-formatter="rate_format">过会率</th>
						<th data-field="tjlRate"  		 class="data-input" data-formatter="rate_format">投决率</th>
					</tr>
				</thead>
			</table>
			
		</div>
	</div>
        
    </div>
 
</div>

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>


<script src="<%=request.getContextPath() %>/js/cookie.js" type="text/javascript"></script>
<!-- table分页 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- highcharts -->
<script src="<%=request.getContextPath() %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/highcharts_ext.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>



<script>

var url = platformUrl.userkpi;
var pageNum = 1;

$(function () {
	//左侧菜单
	createMenus(8);
	
	init(); //页面初始化
	
	//绑定querySearch事件
	$('#data-table-userkpi').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		pageList : [10, 20, 30 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		
		pagination: true,
        search: false,
        url: url,
        onLoadSuccess: function(backdata){
        	var options = $('#data-table-userkpi').bootstrapTable('getOptions');
        	//var data = $('#data-table-userkpi').bootstrapTable('getData');
        	var data = options.data;
        	pageNum = options.pageNumber;
        	if(pageNum == 1){
        		var re = [];
    	   		var categories = [];
        		for(var i=0;i<data.length;i++){
        			if(i==10){
        				break;
        			}
       	   			re.push(data[i].completed);
       	   			categories.push(data[i].realName);
            	}
    	   		containerUserKpiOptions.series[0].data = re;
    	   		containerUserKpiOptions.xAxis.categories = categories;
    	   		var chart = new Highcharts.Chart(containerUserKpiOptions);
        	}
        }
	});
});	


function init(){
	//表单日期初始化
	var currDate = new Date();
	var sdate = currDate.format("yyyy-01-01");
	var edate = currDate.format("yyyy-MM-dd");
	$("input[name='sdate']").val(sdate);
	$("input[name='edate']").val(edate);
	
	//表单事业线下拉初始化
	createCareelineOptions(platformUrl.getCareerlineList,"deptid","");
	
}
	 

function toperKpi(){
	forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/touserkpi");
}
 
function toteamKpi(){
	forwardWithHeader(Constants.sopEndpointURL+"/galaxy/kpireport/toteamkpi");
} 
 
//格式化比率
function rate_format(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return "0%";
	} else if(value=='-'){
		return value;
	}else{
		value = value * 100;
		return value.toFixed(2)+"%";
	}
}




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



/* 
function cat_deptkpi(value, row, index) {
	var id = row.dept_id;
	var options = "<a href='#' onclick='deptkpiprojectList(" + id + ")' class='blue'>"
			+ value + "</a>";
	return options;
}
function deptkpiprojectList(id) {
	var _url = path + '/galaxy/report/deptkpiprojectlist';
	$.getHtml({
		url : _url,//模版请求地址	
		data : "",//传递参数
		okback : function() {
			$("#deptkpi_projectlist_projectType").val(
			$("#deptkpi_projectType").val()); //项目类型
			$("#deptkpi_projectlist_deptid").val(id); //部门
			$("#deptkpi_projectlist_sdate").val($("#deptkpi_sdate").val()); //项目创建日期
			$("#deptkpi_projectlist_edate").val($("#deptkpi_edate").val()); //项目创建日期
			//console.log("给hidden赋值");
			$('#data-table-deptkpi-projectlist').bootstrapTable({
				queryParamsType : 'size|page', // undefined
				pageSize : 10,
				showRefresh : false,
				sidePagination : 'server',
				method : 'post',
				pagination : true,
				search : false,
				//dataType: 'html',
				onLoadSuccess : function(result) {
					//console.log(result)
				}
			});
		}
	});
	return false;
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
 */
	
</script>
</html>

