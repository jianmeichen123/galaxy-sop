<%@ page language="java" pageEncoding="UTF-8"
import="com.galaxyinternet.framework.core.oss.OSSConstant,com.galaxyinternet.model.user.User"
%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://www.galaxyinternet.com/tags/acl" prefix="acl" %>
<% 

String path = request.getContextPath();
String endpoint = (String)application.getAttribute(OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
String reportEndpoint = map.get("galaxy.project.report.endpoint");

%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>繁星</title>
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
<!-- jsp文件头和头部 -->
<link href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<!-- 表格插件 -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<jsp:include page="./common/taglib.jsp" flush="true"></jsp:include>
<script src="<%=path%>/js/bootstrap-v3.3.6.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path%>/js/sopUserSchedule.js" type="text/javascript"></script>
<script src="<%=path%>/js/fileindex.js" type="text/javascript"></script>
<script src="<%=path%>/js/index.js" type="text/javascript"></script>
<link href="<%=path %>/css/calendar.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/calendarnew.js"></script>


<script src="<%=path %>/js/highcharts.js" type="text/javascript"></script>
<script src="<%=path %>/js/highcharts_ext.js" type="text/javascript"></script>


<script src="<%=path %>/js/time.js" type="text/javascript"></script>
</head>

<body>

<jsp:include page="./common/header.jsp" flush="true"></jsp:include>

<div class="pagebox clearfix">
	<!--右侧-->
    <div class="rit rit_executive">
        <!--时间-->
        <div class="top"  resource-mark="shedule_list" style="height: 178px;display:none">
        	<b class="sj ico null">三角</b>
            <div class="tody ico">
            	<p class="time"></p>
                <p class="date"></p>
            </div>
            <div id="top">
            </div>
            <div class="morebox">
               <!-- <a href="javascript:;" class="add_schedule blue"  onclick="shecudle();">添加1日程</a> -->
                <a href="<%=path %>/html/shecudle_list.html" data-btn="shecudle_list" class="add_schedule blue">添加日程</a>
            </div>
        </div>
        <!--投决会排期-->
        <acl:acl resourceMark="shedule_tjh">
        <dl class="tjh_block" style="position:relative;">
        	<dt>投决会排期</dt>
            <dd>
            	<table width="100%" cellspacing="0" cellpadding="0" >
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>项目名称</th>
                            <th>上次过会时间</th>
                            <th>过会次数</th>
                        </tr>
                    </thead>
                    <tbody id="tbody">
                        
                    </tbody>
                </table>
            </dd>
            <dd class="clearfix position">
                <a href="<%=path %>/html/voteMeeting.html" data-btn="vote"  class="more null">more</a>
            </dd>
            <c:if test="${fx:hasRole(4)}">
        	<dd><a href="javascript:;" class="blue paiqidate" onclick="paiqidate('meetingType:4');">排期时间</a></dd>
        	</c:if>
        </dl>
        </acl:acl>
        <!--立项排期会-->
        <acl:acl resourceMark="shedule_lxh">
        <dl style="position:relative;">
        	<dt>立项会排期</dt>
            <dd>
            	<table id="projectMeeting" width="100%" cellspacing="0" cellpadding="0" class="index">
                    <thead>
                        <tr>
                            <th>序号</th>
                            <th>项目名称</th>
                            <th>上次过会时间</th>
                            <th>过会次数</th>
                        </tr>
                    </thead>
                    <tbody id="tlbody">
                    </tbody>
                </table>
            </dd>
            <dd class="clearfix position">           
              <a href="<%=path %>/html/projectMeeting.html" data-btn="project" class="more null">more</a>
            </dd>
            
            <c:if test="${fx:hasRole(4)}">
        	 <dd><a href="javascript:;" class="blue paiqidate" onclick="paiqidate('meetingType:3');">排期时间</a></dd>
        	</c:if>
        	
        </dl>
        </acl:acl>
        <div id="position_7">
	        <acl:acl resourceMark="shedule_ceo">
	        <!--CEO评审排期  -->
	        <div class="top top_rit_executive"  id="ceo_cat">
	        	<!-- <b class="sj ico null">三角</b> -->
	        	<dl id="ceo_p">
					<dt id="title_ceopq">CEO评审排期</dt>
					<dd>
						<table id="ceopsMeeting" width="100%" cellspacing="0"
							cellpadding="0" class="index">
							<thead>
								<tr>
									<th>序号</th>
									<th>项目名称</th>
									<th>上次过会时间</th>
									<th>过会次数</th>
								</tr>
							</thead>
							<tbody id="ceopsbodytop">
							</tbody>
						</table>
					</dd>
					<dd class="clearfix position">
						<a href="<%=path %>/html/ceopsMeeting.html" data-btn="ceops" class="more null">more</a>
					</dd>
				</dl> 
			</div>
			</acl:acl>
		</div>
    </div>
    
    
	<jsp:include page="./common/menu.jsp" flush="true"></jsp:include>
	
	
    <!--中部内容-->
    <div class="min">
        <!--表格列表-->
        <div class="tablist clearfix">
        	<!--左侧列表-->
            <div class="l l_executive">
               <acl:acl resourceMark="task_list">
            	<dl style="position:relative;">
                	<dt><h3 class="ico t1">待办任务</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>优先级</th>
                                    <th>任务类型</th>
                                    <th>任务名称</th>
                                    <th>任务状态</th>
                                    <th>所属项目</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="sopStak">
                               
                            </tbody>
                        </table>
                    </dd>
                    <dd class="clearfix position">
                    	<a href="javascript:;"  onclick="dealtTask()" class="more null">more</a>
                    </dd>
                </dl>
                </acl:acl>
                <dl resource-mark="project_view_module" style="display:none">
                	<dt><h3 class="ico t3">数据报表</h3></dt>
                    <dd class="zzbox">
                    	<div id="histogram" class="histogram" style="height:160px"></div>
                    </dd>
                  <!--    <dd class="clearfix">
                    	<a href="<<%=path %>/galaxy/report/dataBriefing" class="more null">more</a>
                    </dd>
                    -->
                </dl>
                
                 <dl style="position:relative;display:none;" class="Creative_library" resource-mark="idea_summary">
						<!-- <img src="<%=path%>/img/sy.png" alt=""> -->
						
						<dt>
							<h3 class="ico t2">项目创意</h3>
						</dt>
						
						<dd>
							<table width="100%" cellspacing="0" cellpadding="0">
								<thead>
									<tr>
										<th>创意编号</th>
										<th>创意名称</th>
										<th>所属行业</th>
										<th>创建时间</th>
										<th>最后编辑时间</th>
										<th>创建人</th>
										<th>状态</th>
									</tr>
								</thead>

								<tbody id="cy_index">
									<!--  <tr>
                                    <td>cy001</td>
                                    <td>美好生活</td>
                                    <td>o2o电商</td>
                                    <td>高</td>
                                    <td>2016-1-29</td>
                                    <td>2016-1-21</td>
                                    <td>徐茂栋</td>
                                    <td><span class="blue">待认领</span></td>
                                </tr> -->
								</tbody>
							</table>
						</dd>

						<dd class="clearfix position">
							<a href="javascript:;" onclick="toCyPage()" class="more null">more</a>
							<!--  <a href="/html/voteMeeting.html" data-btn="vote"  class="more null">more</a> -->
						</dd>

					</dl>
					<!-- 事项预览 -->
						<dl resource-mark="div_matter_preview_gg" style="display:none">
							<dt>
								<h3 class="ico t7">事项预览</h3>
							</dt>
							<dd>
								<div class="l_previewTop">
								    <div style="margin-left:25%; display:none"><span vertical-align:middle>今日会议总数<a id= "meeting_number_today" href="javascript:;" >0</a>个</span></div>
									<span class='bj_pagebox'>今日：立项会　<a id="lxh_number_today" href="javascript:;">0</a>个</span> 
									<span class='bj_pagebox bj_pagebox_left'>投决会　<a id="tjh_number_today" href="javascript:;">0</a>个</span>
									<span class='bj_pagebox bj_pagebox_left'>评审会　<a id="psh_number_today" href="javascript:;">0</a>个</span>
								</div>
								<div class="l_previewBottom clearfix">
									<div class="l_previewBottom_l l_previewBottom_b">
										<span><p>立项会</p><p>排期等待</p></span><a href="javascript:;" id="lxh_eduling_wait"></a>
									</div>
									<div class="l_previewBottom_r l_previewBottom_b">
										<span><p>投决会</p><p>排期等待</p></span><a href="javascript:;" id="tjh_eduling_wait"></a>
									</div>
									<div class="l_previewBottom_r l_previewBottom_b">
										<span><p>评审会</p><p>排期等待</p></span><a href="javascript:;" id="psh_eduling_wait"></a>
									</div>
								</div>
							</dd>
						</dl>
					<!-- 项目进度 -->
						<dl resource-mark="div_project_progress_gg" style="display:none">
							<dt>
								<h3 class="ico t8">项目进度</h3>
								<a href="javascript:;" class="more position_0 null" id="more_progress">more</a>
							</dt>
							<dd style="position: relative;">
								<div class="mask_platform_progress"></div>
								<div id="container_progress"
									style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
							</dd>
						</dl>
					<!-- 投资资金  删 -->
						<!-- <dl class="executive_last" resource-mark="div_investment_gg" style="display:none">
							<dt>
								<h3 class="ico t9">投资资金</h3>
							</dt>
							<dd>
								<div id="charts_investment"
									style="min-width:300px; height: 200px;padding-top:5px;"></div>
							</dd>
						</dl> -->
						<!-- 已投项目分析 -->
						<dl class="executive_last ytxm_block" resource-mark="div_project_post_analysis_gg" style="display:none">
							<dt>
								<h3 class="ico t9">已投项目分析<span class="Htips">（截止至当前）</span></h3>
								<ul class="ytxm_tab position_tab clearfix">
									<li data-tab="nav">联合创业</li>
									<li data-tab="nav">融快</li>
									<li data-tab="nav">创保联</li>
								</ul>
							</dt>
							<dd>
								<div id="charts_Joint" data-tab="con" style="min-width:300px; height: 200px;padding-top:5px;"></div>
								<div id="charts_rk" data-tab="con" style="min-width:300px; height: 200px;padding-top:5px;"></div>
								<div id="charts_cbl" data-tab="con" style="min-width:300px; height: 200px;padding-top:5px;"></div>
							</dd>
						</dl>						
            </div>
            <!--右侧列表-->
            <div class="r r_executive">
            	<dl resource-mark="div_tip_message" class="r_news" style="display:none;position:relative;">
                	<dt><h3 class="ico t4">消息提醒</h3></dt>
                    <dd>
                    	<!-- <table width="100%" cellspacing="0"  cellpadding="0">
                            <thead>
                                <tr>
                                    <th>日期时间</th>
                                    <th>消息</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table> -->
                        <table id="message-data-table" data-url="operationMessageQueryList" data-page-size="3" data-page-list="[3,20,30]" data-show-refresh="true">
							<thead>
							    <tr>
						        	<th data-field="createdTime" data-align="left" data-width="35%" data-formatter="longTimeFormat_Chines" >日期时间</th>
						        	<th data-field="content" data-align="left"  data-width="65%" data-formatter="projectNameLineFormat">消息</th>
			 					</tr>	
			 				</thead>
						</table>
                    </dd>
                    <dd class="clearfix position">
                    	<a href="<%=path %>/galaxy/operationMessage/index" class="more null">more</a>
                    </dd>
                </dl>
                
                <dl resource-mark="div_pro_doc" id="dan_k" style="display:none;position:relative;">
                	<dt><h3 class="ico t5">项目文档</h3></dt>
                    <dd>
                    	<table width="100%" cellspacing="0"  cellpadding="0" id="file_gird_index">
                            
                        </table>
                    </dd>
                    <dd class="clearfix position">
                    	<a  href="javascript:void(0)" onclick="Sopfile()"  class="more null" id="file_gird_more">more</a>
                    </dd>
                </dl>
            <div id="position_5">
            	
            </div>
            
			
			<acl:acl resourceMark="div_normal_tool">
                <dl  class="tool_radius">
                	<dt><h3 class="ico t6">常用工具</h3></dt>
                    <dd class="tool">
		        	<c:choose>
			        	<c:when test="${fx:hasRole(20)}">
						<a href="javascript:;" class="light_gray "><b class="b4 ico null">ico</b>新增访谈</a>
                    	<a href="javascript:;" class="light_gray"><b class="b3 ico null">ico</b>新增会议</a>
			        	
			        	</c:when>
			        	<c:otherwise>
			        	
                        <a href="<%=path %>/galaxy/project/progress/interViewAdd" data-btn="interview"><b class="b4 ico null">ico</b>新增访谈</a>
                    	<a href="<%=path %>/galaxy/project/progress/meetAddView" data-btn="meeting"><b class="b3 ico null">ico</b>新增会议</a>
			        	</c:otherwise>
		        	</c:choose>
                    	
                    	<a href="javascript:;" class="light_gray"><b class="b1 ico null">ico</b>通讯录</a>
                        <a href="javascript:;" class="light_gray"><b class="b2 ico null">ico</b>估值计算</a>
                        <a href="javascript:;" class="light_gray"><b class="b5 ico null">ico</b>发邮件</a>
                        <a href="javascript:;" style="text-decoration:none;cursor:default;">&nbsp;</a>
                    </dd>
                </dl>
            </acl:acl>
                
                <!-- 绩效考核位置下移-->
				<!-- 	<dl resource-mark="div_performance_gg" style="display:none" class="r_news">
						<dt>
							<h3 class="ico t10">绩效考核</h3>
							<span class="more null position_0" id="platform_jxkh_more" style="cursor: pointer;">more</span>
						</dt>
						<dd>
							<div id="container_kpi" style="min-width:300px; height: 162px;padding-top:5px;"></div>
						</dd>
					</dl> -->
                <!-- 项目健康度 -->
                <dl class="r_news" resource-mark="div_health_gg" style="display:none">
					<dt>
						<h3 class="ico t13">项目健康度<span class="Htips">（截止至当前）</span></h3>
						<span class="more null position_0" id="platform_health_more" style="cursor: pointer;">more</span>
					</dt>
					<dd>
						<div id="container_health" style="width:100%; height: 162px;padding-top:5px;"></div>
					</dd>
				</dl>
                
                <!-- 项目历时 -->
					<dl resource-mark="div_duration_gg" style="display:none">
						<dt>
							<h3 class="ico t11">项目历时</h3>
						</dt>
						<dd style="position: relative;">
							<div class="mask_platform_time"></div>
							<div id="container_time"
								style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
						</dd>
					</dl>
                
                <!-- 项目运营 -->
					<%-- <dl resource-mark="div_operation_gg" style="display:none" class="tool_radius executive_last">
						<img src="<%=request.getContextPath()%>/img/sy.png" alt="" />
						<dt>
							<h3 class="ico t12">项目运营</h3>
						</dt>
						<dd>
							<div style='margin-left:auto; margin-right:auto; width:450px;'>
							<div id="container_operation" style="min-width:440px;  height: 200px;padding-top:5px;"></div>
							</div>
						</dd>
					</dl> --%>
					<!-- 绩效考核 -->
					<dl resource-mark="div_performance_gg" style="display:none" class="r_news executive_last">
						<dt>
							<h3 class="ico t10">绩效考核</h3>
							<span class="more null position_0" id="platform_jxkh_more" style="cursor: pointer;">more</span>
						</dt>
						<dd>
							<div id="container_kpi" style="min-width:300px; height: 200px;padding-top:5px;"></div>
						</dd>
					</dl>
                
                    
            </div>
        </div>
    </div>
 
</div>

<jsp:include page="./common/footer.jsp" flush="true"></jsp:include>
<jsp:include page="./common/uploadwin.jsp" flush="true"></jsp:include>
<jsp:include page="/galaxy/sopFile/showMailDialog" flush="true"></jsp:include>



<!-- file -->
<%-- <script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script> --%>
<%-- <script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script> --%>
<%-- <script src="<%=path %>/js/init.js"></script> --%>
<%-- <script src="<%=path %>/js/sop.js" type="text/javascript"></script> --%>


<!-- 项目流程弹窗引用 -->
<!-- 富文本编辑器 -->
<script id="d" type="text/javascript" charset="utf-8" src="<%=map.get("galaxy.project.sop.endpoint") %>ueditor/umeditor.min.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=map.get("galaxy.project.sop.endpoint") %>ueditor/umeditor.config.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=map.get("galaxy.project.sop.endpoint") %>ueditor/dialogs/map/map.js"></script>
<script id="e" type="text/javascript" src="<%=map.get("galaxy.project.sop.endpoint") %>ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="<%=map.get("galaxy.project.sop.endpoint") %>js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=map.get("galaxy.project.sop.endpoint") %>js/plupload/zh_CN.js" type="text/javascript"></script>
<script src="<%=map.get("galaxy.project.sop.endpoint") %>js/init.js"></script>
<script src="<%=map.get("galaxy.project.sop.endpoint") %>js/base64.js" type="text/javascript"></script>
<script src="<%=map.get("galaxy.project.sop.endpoint") %>js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=map.get("galaxy.project.sop.endpoint") %>js/teamSheetNew.js"></script>
<script type="text/javascript" src="<%=map.get("galaxy.project.sop.endpoint") %>js/filerepository.js"></script>
<script src="<%=map.get("galaxy.project.sop.endpoint") %>js/sop.js" type="text/javascript"></script>


<script type="text/javascript">
var transferingIds = "${fx:getTransferingPids()}".split(",");
</script>

<!-- clude -->
<script src="<%=path %>/js/meeting.js" type="text/javascript"></script>
<script src="<%=path %>/js/interview.js" type="text/javascript"></script>
<script src="<%=path %>/js/indexProjectProgress.js" type="text/javascript"></script>
<script src="<%=path %>/js/indexMsResource.js" type="text/javascript"></script>


<!-- charts -->
<script src="<%=path %>/js/charts/projectInvestment.js"></script>
<script src="<%=path %>/js/charts/projectProgress.js"></script>
<script src="<%=path %>/js/charts/indexKpi.js"></script>
<script src="<%=path %>/js/charts/indexProjectDuration.js"></script>
<script src="<%=path %>/js/charts/indexMatterPreview.js"></script>




<script type="text/javascript">
var forwardParam = {
		progressParam : undefined,
		timeParam : undefined
};
	$(function(){	
		
		$(".pagebox .rit .top .tody").today();
		top5ProjectMeeting();
		ProjectVoteWill();
		selectSopTask();
		selectCyIndex();
		createMenus(1);
		top5Message();
		top5CeoPsMeeting();
		/* if(isContainResourceByMark("shedule_list")){
	       $('div[resource-mark="shedule_list"]').css("display","block");
		}
		if(isContainResourceByMark("project_view_module")){
		       $('dl[resource-mark="project_view_module"]').css("display","block");
			}
		if(isContainResourceByMark("idea_summary")){
		       $('dl[resource-mark="idea_summary"]').css("display","block");
			}
		loadAjaxSopUserSchedule(platformUrl.sheduleMoreThree); 
		
		 */
		
		msResourceUtils.specialDeal();
		$.each(allResourceToUser, function(index, element){
			
			 $('[resource-mark="' + element.resourceMark + '"]').css("display","block");
			 
		});
		
		
		
		//事项预览
		if($('dl[resource-mark="div_matter_preview_gg"]').css("display") == 'block'){
			matterPreviewUtils.init();
		}
		//绩效考核图表
		if($('dl[resource-mark="div_performance_gg"]').css("display") == 'block'){
			load_data_chart_kpi();
		}
		
		
		/*新版项目进度图表*/
		if($('dl[resource-mark="div_project_progress_gg"]').css("display") == 'block'){
			var progressFormdata = {
					domid : 'container_progress'
			}
			chartProjectProgressUtils.init(progressFormdata);
			
			//项目进度图表默认加载链接
			$("#container_progress .highcharts-title tspan").click(function(){
				var url = platformUrl.projectAnalysis;
				if(forwardParam.progressParam){
					url += "?forwardProgress=" + forwardParam.progressParam ;
				}
				forwardWithHeader(url);
			});
		}
		
		/*新版投资资金图表*/
		if($('dl[resource-mark="div_investment_gg"]').css("display") == 'block'){
			var investFormdata = {
					domid : 'charts_investment'
			};
			chartsInvestmentUtils.init(investFormdata);
		}
		
		//项目历时
		if($('dl[resource-mark="div_duration_gg"]').css("display") == 'block'){
			load_data_chart_project_time();
			noDataProTimeDiv();
		}
		
		
		loadAjaxSopUserSchedule(platformUrl.sheduleMoreThree); 
		
		
	});
	
</script>
<script>
$(function(){
	var formdata = {
			domid : "histogram"
	}
	chartIndexPProgressUtils.init(formdata);
	$('#message-data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:4,
		showRefresh : false ,
		url : platformUrl[$('#message-data-table').attr("data-url")],
		sidePagination: 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'updated_time',
		pagination: true,
        search: false,
        onLoadSuccess: function (data){
        	if(data.pageList.total<4){
        		$(".r_news .more").css("display","none");
        	}
        }
	});
});


function paiqidate(type){
	forwardWithHeader(platformUrl.popupMeetingList + type);
}
$("#platform_health_more").click(function(){
	forwardWithHeader(platformUrl.toHealthChartDetail+"?urlFlag=null");
})


</script>
<script src="<%=path %>/js/echarts.js" type="text/javascript"></script>
<script src="<%=path %>/js/echarts_health.js" type="text/javascript"></script>
<%-- <script src="<%=path %>/js/echarts_ytxm.js" type="text/javascript"></script> --%>
<script src="<%=path %>/js/charts/projectPostAnalysis.js" type="text/javascript"></script>
<%-- <jsp:include page="./common/sop.jsp" flush="true"></jsp:include> --%>
</html>

