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
<title>星河投</title>
	
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

<jsp:include page="../../common/footer.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">

	<!--左侧导航-->
	<jsp:include page="../../common/menu.jsp" flush="true"></jsp:include>
     
    <!--右中部内容-->
 	<div class="ritmin">
 	<h2>拜访统计</h2>
		 <div class="tabtable visitChartCon">
        <div class="bars visit_bars"><div class="min_document clearfix" id="zixun-custom-toolbar">
        
            <input type="hidden" name="property" value="updated_time">
            <input type="hidden" name="direction" value="DESC">
            
            <div class="bottom searchall clearfix search_adjust idea_list_searchall">
                <dl class="fmdl fmdll clearfix">
                    <dt>投资事业线：</dt>
                    <dd>
                    <select name="departmentId"><option value="0">全部</option></select>
                    </dd>
                </dl>
                
                <dl class="fmdl fmdll clearfix">
                    <dt>投资经理：</dt>
                    <dd>
                    <select name="createdId"><option value="0">全部</option></select>
                    </dd>
                </dl>
                
                <dl class="fmdl fmdll clearfix">
                  <dt>是否为项目拜访：</dt>
                  <dd>
                   <ul class="radios clearfix">
                        <li><input type="radio" checked="">全部</li>
                        <li><input type="radio" name="isProject" value="1">是</li>
                        <li><input type="radio" name="isProject" value="0">否</li></ul>
                  </dd>
                </dl>
                
            </div>
            
            <div class="bottom searchall clearfix search_adjust idea_list_searchall">
                <dl class="fmdl fmdll clearfix">
                    <dt>&nbsp;&nbsp;&nbsp;统计时间：</dt>
                    <dt>
                       <ul class="radios clearfix">
                           <li class="quarterly_btn"><input type="radio" name="periodType" value="1" checked><span>季度</span></li>
                           <li class="month_btn"><input type="radio" name="periodType" value="2"><span>月</span></li>
                           <li class="week_btn"><input type="radio" name="periodType" value="3"><span>周</span></li>
                       </ul>
                    </dt>
                    <dd class="visitdata_quarterly">
                        <input type="text" class="txt time" id="quarterly_start_data" name="" readonly style="height:23px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <select id="s_quarterly" name="s_quarterly" style="display:inline-block ">
                            <option value="">请选择</option>
                            <option value='1'>第一季度</option>
                            <option value='2'>第二季度</option>
                            <option value='3'>第三季度</option>
                            <option value='4'>第四季度</option>
                        </select>
                    </dd>
                    <!-- change_week -->
                    <dd class="visitdata_month">
                        <input type="text" class=" change_month_visit txt time" id="month_start_data" name="" readonly style="height:23px;">                    </dd>
                    <dd class="visitdata_week">
                        <input type="text" class="visitweekStartDatepicker txt time" name="" readonly style="height:23px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="text" class="visitweekEndDatepicker txt time" name="" readonly style="height:23px;">
                    </dd>
                </dl>
                <dl class="fmdl fmdll clearfix" style="width:150px;">
                    <button type="submit" class="bluebtn ico tj" action="querySearch">统计</button>
                </dl>
            </div>
            </div>
        </div>

        <!-- section1 -->
        <div class="visit_box visit_one">
            <h3>
                <img src="<%=path %>/img/section_1.png" alt="">
                <p>拜访量统计</p>
                <span class="visit_period_desc">(2017年第2季度)</span>
            </h3>
      <ul class="fl_three clearfix">
                <li>
                    <div class="fl_three_son">
                        <img src="<%=path %>/img/visit_one.png" alt="">
                        <div class="_section">
                            <p id="planVisit">5000</p>
                            <span>计划拜访量</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="fl_three_son">
                        <img src="<%=path %>/img/visit_two.png" alt="">
                        <div class="_section">
                            <p id="completeVisit">3500</p>
                            <span>已完成拜访量</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="fl_three_son">
                        <img src="<%=path %>/img/visit_three.png" alt="">
                        <div class="_section">
                            <p id="interviewRate">70%</p>
                            <span>访谈完成率</span>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <!-- section2 -->
        <div class="visit_box visit_two">
            <ul class="fl_three clearfix">
                <li>
                    <div class="round_title">
                        <p>项目拜访占比</p>
                        <span class="visit_period_desc">(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit"  style="z-index:0;"></div>
                </li>
                <li>
                    <div class="round_title">
                        <p>项目拜访轮次占比</p>
                        <span class="visit_period_desc">(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_round"  style="z-index:0;"></div>
                </li>
                <li>
                    <div class="round_title">
                        <p>访谈记录缺失占比</p>
                        <span class="visit_period_desc">(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_miss"  style="z-index:0;"></div>
                </li>
            </ul>
        </div>
        <!-- section3 -->
        <div class="visit_box visit_three">
            <h3>
                <img src="<%=path %>/img/section_2.png" alt="">
                <p>拜访趋势图</p>
                <span class="period_desc">(季度)</span>
            </h3>
            <div class="vertical" id="visitTrend" style="height: 310px;z-index:0;"></div>
        </div>
        <div class="visit_box visit_four" id="bftjt">
            <h3>
                <img src="<%=path %>/img/section_3.png" alt="">
                <p>拜访统计图</p>
                <span class="visit_period_desc">(2017年第2季度)</span>
            </h3>
            <ul class="vertical_tab clearfix">
                <li class="active">已完成拜访量</li>
                <li class="last">计划拜访量</li>
            </ul>
            <div class="vertical" id="visitCompleted" style="height: 310px;z-index:0;"></div>
            <div class="vertical" id="visitPlan" style="height: 310px;z-index:0;"></div>
            <!-- 排名列表 -->
            <!--表格内容-->
			<table id="data-table-bftjt" width="100%" cellspacing="0" cellpadding="0"  >
			</table>
        </div>



    </div>
	</div>
</div>



</body>
<!-- table分页 -->
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path %>/js/init.js"></script>
    
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDate.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/visit_dataforweek.js"></script>
<script src="<%=path %>/js/echarts.js" type="text/javascript"></script>
<script src="<%=path %>/js/charts/visitChart.js" type="text/javascript"></script>
<script type="text/javascript">
createMenus(6);
/**
 * 查询事业线
 * @version 2016-06-21
 */
createCareelineOptions(platformUrl.getCareerlineList,"departmentId");
/**
 * 根据事业线查询相应的投资经理
 * @version 2016-06-21
 */
createUserOptions_All(platformUrl.getUserList+$('select[name="departmentId"]').val(), "createdId", 0);
/**
 * 改变事业线时获取该事业线下的投资经理
 * @version 2016-06-21
 */
$('select[name="departmentId"]').change(function(){
	var did = $('select[name="departmentId"]').val();
    createUserOptions_All(platformUrl.getUserList+did, "createdId", 1);
});


//拜访统计图
var bftjt = {
		
	dataAllSum : [],
	dataAllName : [],
	
	dataComSum : [],
	dataComName : [],
	
	initTable : function() {
		$('#data-table-bftjt').bootstrapTable('destroy');
		
		
	    var toolbarQ = bftjt.queryParams({});  
		if(toolbarQ.createdId != null && toolbarQ.createdId != '0'){
			$("#bftjt").hide();
			return;
		}else{
			$("#bftjt").show();
			$("#visitCompleted").show();
		}
		
		var nameFormat = "";
		if (toolbarQ.departmentId != null && toolbarQ.departmentId != '0'){
			nameFormat = "投资经理";
		} else {
			nameFormat = "投资事业线";
		}
		
		$('#data-table-bftjt').bootstrapTable({
			url : Constants.sopEndpointURL + "/galaxy/visit/bftjt",  //请求后台的URL（*）
			queryParamsType : 'size|page',
			showRefresh : false,
			search : false,
			method : 'post',                //请求方式（*）
			//toolbar: '#toolbar',          //工具按钮用哪个容器
			//striped: true,                //是否显示行间隔色
			cache : false,                  //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true,              //是否显示分页（*）
			sidePagination : "client",      //分页方式：client客户端分页，server 服务端分页（*）
			sortable : true,                //是否启用排序
			sortName : "allSum",
			sortOrder : "desc",              //排序方式
			queryParams : bftjt.queryParams,     //传递参数（*）
			pageNumber : 1,                      //初始化加载第一页，默认第一页
			pageSize : 10,                       //每页的记录行数（*）
			pageList : [ 10, 20 ],               //可供选择的每页的行数（*）
			strictSearch : true,
			clickToSelect : false,               //是否启用点击选中行
			//height: 460,                       //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			uniqueId : "id",                     //每一行的唯一标识，一般为主键列
			cardView : false,                    //是否显示详细视图
			detailView : false,                  //是否显示父子表
			
			columns : [ {
				field : 'index',
				title : '排名',
				sortable : false,
				formatter : function(value, row, index) {
					return index + 1;
				}
			}, {
				field : 'name',
				title : nameFormat,
				sortable : false
				
			}, {
				field : 'allSum',
				title : '计划拜访量',
				sortable : true
			}, {
				field : 'completeSum',
				title : '已完成拜访量',
				
				sortable : true
			} ],
			onLoadSuccess: function(backdata){

				if (backdata.result.status == "ERROR") {
					layer.msg(backdata.result.message);
				} else {
					//var dataList = backdata.pageList.content;
					var dataList = backdata.entityList;
					bftjt.dataAllSum=[];
					bftjt.dataAllName=[];
					bftjt.dataComSum=[];
					bftjt.dataComName=[];
					if(dataList !=null && dataList.length >0){
						dataList.sort(bftjt.compare);
						$.each(dataList,function(){
							if(this.completeSum != 0){
								bftjt.dataComName.push(this.name);
								bftjt.dataComSum.push(this.completeSum);
							}
						});
						dataList.sort(bftjt.allCompare);
						$.each(dataList,function(){
							bftjt.dataAllName.push(this.name);
							bftjt.dataAllSum.push(this.allSum);
						});
					}else{
						bftjt.dataAllSum=[];
						bftjt.dataAllName=[];
						bftjt.dataComSum=[];
						bftjt.dataComName=[];
					}
				}
				$(".th-inner.sortable").append("<span></span>")
				
				//已完成拜访量
				completedChart();
				function completedChart(){
					var completedChart = echarts.init(document.getElementById('visitCompleted'));
					bftjt.completedOption.xAxis[0].data = bftjt.dataComName;
					bftjt.completedOption.series[0].data = bftjt.dataComSum;
					if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
					  { 
						bftjt.completedOption.tooltip.backgroundColor="#fff";
					  }
					completedChart.setOption(bftjt.completedOption, true);
					
					window.onresize = completedChart.resize; 
					if(bftjt.dataComSum.length==0){
						$("#visitCompleted").children().hide();
						 $("#visitCompleted").addClass("empty_data6");
						 $("#visitCompleted").append("<p class='visit_nocon'>没有找到匹配的记录</p>")
					}else{
						$("#visitCompleted").children().show();
						 $("#visitCompleted").removeClass("empty_data6");
						 $("#visitCompleted").find(".visit_nocon").remove();
					}
				}
				

				//计划拜访量
				planChart();
				function planChart(){
					var planChart = echarts.init(document.getElementById('visitPlan'));
					bftjt.planOption.xAxis[0].data = bftjt.dataAllName;
					bftjt.planOption.series[0].data = bftjt.dataAllSum;
					if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
					  { 
						bftjt.planOption.tooltip.backgroundColor="#fff";
					  }
					planChart.setOption(bftjt.planOption, true);
					
					window.onresize = planChart.resize; 
					//$("#visitPlan").hide();
					if(bftjt.dataAllSum.length==0){
						$("#visitPlan").children().hide();
						 $("#visitPlan").addClass("empty_data6");
						 $("#visitPlan").append("<p class='visit_nocon'>没有找到匹配的记录</p>")
					}else{
						$("#visitPlan").children().show();
						 $("#visitPlan").removeClass("empty_data6");
						 $("#visitPlan").find(".visit_nocon").remove();
					}
				}
				
				//tab点击事件
				$(".vertical_tab li").click(function(event) {
				  $(".vertical_tab li").removeClass("active");
				  $(this).addClass("active");
				    if ($(this).hasClass("last")) {
				    $("#visitCompleted").hide();
				    $("#visitPlan").show();
					 $("#visitPlan>div").remove();
				    planChart();
				  }else{
				    $("#visitCompleted").show();
				    $("#visitPlan").hide();
				    $("#visitCompleted>div").remove();
				    completedChart();
				  }
				});
				
				//IE8兼容
				  if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0") 
				  { 
					 $("#visitCompleted>div").remove();
					 $("#visitPlan>div").remove();
					 completedChart();
					 planChart();
				  } 

			}
		});

	},
	
	lengthFormatter : function(value, row, index) {
		if (row.projectName.length > 12) {
			var str = row.projectName.substring(0, 12);
			var options = '<span title="'+row.projectName+'">' + str + '</span>'
		} else {
			var options = '<span title="'+row.projectName+'">' + row.projectName + '</span>'
		}
		return options;
	},
	queryParams : function(params) {
		/* startTimeFrom:datePeriod.startTime.toLocaleString,
		startTimeThrough:datePeriod.endTime,
		departmentId: $("select[name='departmentId']").val(),
		createdId: $("select[name='createdId']").val(),
		isProject: $("input[name='isProject']:checked").val(),
		periodType: $("input[name='periodType']:checked").val()
		 */
		var dateVisitPeriod = getVisitDatePeriod();
		var startTimeFrom = dateVisitPeriod.startTime;    //开始起始时间
		var startTimeThrough = dateVisitPeriod.endTime;   //开始结束时间
		var departmentId = $("select[name='departmentId']").val();    //dept id
		var createdId = $("select[name='createdId']").val();          //选择到人
		var isProject = $("input[name='isProject']:checked").val();   //是否为关联项目    null:全部   1：是   0:否
		var periodType = $("input[name='periodType']:checked").val(); //统计   1:季   2:月   3:周
		
		params.startTimeFrom = startTimeFrom;
		params.startTimeThrough = startTimeThrough;
		params.departmentId = departmentId;
		params.createdId = createdId;
		params.isProject = isProject;
		params.periodType = periodType;
			
		return params;
	},
	
	compare : function (obj1, obj2) {
	    var val1 = obj1.completeSum;
	    var val2 = obj2.completeSum;
	    if (val1 < val2) {
	        return 1;
	    } else if (val1 > val2) {
	        return -1;
	    } else {
	        return 0;
	    }            
	},
	allCompare : function (obj1, obj2) {
	    var val1 = obj1.allSum;
	    var val2 = obj2.allSum;
	    if (val1 < val2) {
	        return 1;
	    } else if (val1 > val2) {
	        return -1;
	    } else {
	        return 0;
	    }            
	},
	
	//已完成拜访量
	completedOption : {
		tooltip : {
			trigger : 'axis',
			axisPointer : { //删除中轴线
				type : 'none'
			},
			padding : 8,
			backgroundColor : 'rgba(255,255,255,0.9)',
			borderColor : '#d0d7fb',
			borderWidth : 1,
			borderRadius : 1,
			shadowColor : 'rgba(0,0,0,0.9)',
			textStyle : {
				color : '#555',
				fontFamily : '微软雅黑',
				fontSize : '12'
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : [ '电子商务', '电子商务', '电子商务', '电子商务', '电子商务', '电子商务' ],
			axisLabel : {
				show : true,
				textStyle : {
					color : '#666',
					fontFamily : '微软雅黑',
					align : 'center'
				},
				interval:0,
				formatter : function(value) {
					//return value.split("").join("\n");
					return value;
				}
			},
			axisLine : {
				show : true,
				lineStyle : {
					color : '#f1f1f1',
					width : 1,
					type : 'solid'
				}
			},
			axisTick : {
				show : false
			},
			splitLine : { //去网格线
				show : false
			}
		} ],
		yAxis : [ {
			name : '拜访数量（个）',
			position : 'left',
			nameLocation : 'middle',
			nameGap : 40,
			nameRotate : 90,
			nameTextStyle : {
				color : "#666",
				fontFamily : '微软雅黑'
			},
			type : 'value',
			axisLabel : {
				show : true,
				textStyle : {
					color : '#666',
					fontFamily : '微软雅黑'
				}
			},
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			splitLine : {
				lineStyle : { //网格线样式
					color : '#f6f7fa',
					width : 1,
					type : 'solid'
				}
			}
		} ],
		series : [ {
			name : '已完成拜访量',
			type : 'bar',
			barWidth : "10",//柱图宽度
			data : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7],
			itemStyle : {
				normal : {
					color : '#949af4'
				},
				emphasis : {
					color : '#aaaef6'
				}
			},
		} ]
	},

	//计划拜访量
	planOption : {
		tooltip : {
			trigger : 'axis',
			axisPointer : { //删除中轴线
				type : 'none'
			},
			padding : 8,
			backgroundColor : 'rgba(255,255,255,0.9)',
			borderColor : '#b0f2f9',
			borderWidth : 1,
			borderRadius : 1,
			shadowColor : 'rgba(0,0,0,0.9)',
			textStyle : {
				color : '#555',
				fontFamily : '微软雅黑',
				fontSize : '12'
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			data : [ '电子商务', '电子商务', '电子商务', '电子商务', '电子商务', '电子商务'],
			axisLabel : {
				show : true,
				textStyle : {
					color : '#666',
					fontFamily : '微软雅黑',
					align : 'center'
				},
				interval:0,
				formatter : function(value) {
					//return value.split("").join("\n");
					return value;
				}
			},
			axisLine : {
				show : true,
				lineStyle : {
					color : '#f1f1f1',
					width : 1,
					type : 'solid'
				}
			},
			axisTick : {
				show : false
			},
			splitLine : { //去网格线
				show : false
			}
		} ],
		yAxis : [ {
			name : '拜访数量（个）',
			position : 'left',
			nameLocation : 'middle',
			nameGap : 40,
			nameRotate : 90,
			nameTextStyle : {
				color : "#666",
				fontFamily : '微软雅黑'
			},
			type : 'value',
			axisLabel : {
				show : true,
				textStyle : {
					color : '#666',
					fontFamily : '微软雅黑'
				}
			},
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			splitLine : {
				lineStyle : { //网格线样式
					color : '#f6f7fa',
					width : 1,
					type : 'solid'
				}
			}
		} ],
		series : [ {
			name : '计划拜访量',
			type : 'bar',
			barWidth : "10",//柱图宽度
			data : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7 ],
			itemStyle : {
				normal : {
					color : '#b0f2f9'
				},
				emphasis : {
					color : '#56e3f2'
				}
			},
		} ]
	}
};
</script>
</html>

