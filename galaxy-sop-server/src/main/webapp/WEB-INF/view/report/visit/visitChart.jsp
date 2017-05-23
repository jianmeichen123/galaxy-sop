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
                    <select name="departmentId"><option value="">全部</option></select>
                    </dd>
                </dl>
                
                <dl class="fmdl fmdll clearfix">
                    <dt>投资经理：</dt>
                    <dd>
                    <select name="createdId"><option value="">全部</option></select>
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
                        <select name="s_quarterly" style="display:inline-block ">
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
                <span>(2017年第2季度)</span>
            </h3>
            <ul class="fl_three clearfix">
                <li>
                    <div>
                        <img src="<%=path %>/img/visit_one.png" alt="">
                        <section>
                            <p>5000</p>
                            <span>计划拜访量</span>
                        </section>
                    </div>
                </li>
                <li>
                    <div>
                        <img src="<%=path %>/img/visit_two.png" alt="">
                        <section>
                            <p>3500</p>
                            <span>已完成拜访量</span>
                        </section>
                    </div>
                </li>
                <li>
                    <div>
                        <img src="<%=path %>/img/visit_three.png" alt="">
                        <section>
                            <p>70%</p>
                            <span>访谈完成率</span>
                        </section>
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
                        <span>(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit"></div>
                </li>
                <li>
                    <div class="round_title">
                        <p>项目拜访轮次占比</p>
                        <span>(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_round"></div>
                </li>
                <li>
                    <div class="round_title">
                        <p>访谈记录缺失占比</p>
                        <span>(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_miss"></div>
                </li>
            </ul>
           <!--  <ul class="fl_two clearfix">
                <li>
                    <div class="round_title">
                        <p>项目拜访轮次占比</p>
                        <span>(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_round"></div>
                </li>
                <li>
                    <div class="round_title">
                        <p>访谈记录缺失占比</p>
                        <span>(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_miss"></div>
                </li>
            </ul>
            <ul class="fl_one clearfix">
                <li>
                    <div class="round_title">
                        <p>访谈记录缺失占比</p>
                        <span>(2017年第2季度)</span>
                    </div>
                    <div class="round_data" id="project_visit_miss"></div>
                </li>
            </ul> -->
        </div>
        <!-- section3 -->
        <div class="visit_box visit_three">
            <h3>
                <img src="<%=path %>/img/section_2.png" alt="">
                <p>拜访趋势图</p>
                <span class="period_desc">(季度)</span>
            </h3>
            <div class="vertical" id="visitTrend" style="height: 370px;"></div>
        </div>
        <div class="visit_box visit_four">
            <h3>
                <img src="<%=path %>/img/section_3.png" alt="">
                <p>拜访统计图</p>
                <span>(2017年第2季度)</span>
            </h3>
            <ul class="vertical_tab clearfix">
                <li class="active">已完成拜访量</li>
                <li class="last">计划拜访量</li>
            </ul>
            <div class="vertical" id="visitCompleted" style="height: 370px;"></div>
            <div class="vertical" id="visitPlan" style="height: 370px;"></div>
            <!-- 排名列表 -->
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
</script>
</html>

