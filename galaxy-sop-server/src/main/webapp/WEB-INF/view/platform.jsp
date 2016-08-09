<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:include page="common/header_report.jsp" flush="true"></jsp:include>
<%
String endpoint = (String)application.getAttribute(com.galaxyinternet.framework.core.oss.OSSConstant.GALAXYINTERNET_FX_ENDPOINT);
// java.util.Map<String, String> map = new com.google.gson.Gson().fromJson(endpoint,new com.google.gson.reflect.TypeToken<java.util.Map<String, String>>() {}.getType());
// System.out.println("------------------>>" + endpointMap.get("galaxy.project.sop.endpoint"));
%>
<div class="pagebox clearfix">
	<!--左侧导航-->
	<jsp:include page="common/menu.jsp" flush="true"></jsp:include>
	<!--右侧-->
	<div  class="rit rit_executive">
		<div resource-mark="div_ceo_review_shedule_gg" class="top top_rit_executive"  id="ceo_cat" >
			<!-- <b class="sj ico null">三角</b> -->
			<dl>
				<dt>CEO评审排期 
					<a href="/report/html/ceopsMeeting.html"  data-btn="ceops" class="more null position_10">more</a>
				
				</dt>
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
				<!-- <dd class="clearfix">
					<a href="/report/html/ceopsMeeting.html" data-btn="ceops" class="more null">more</a>
				</dd> -->
			</dl>
		</div>
		<!--时间-->
        <div resource-mark="div_shedule_gg" class="top"   style="display:none;height: 178px;" id="dsz_cat">
        	<b class="sj ico null">三角</b>
            <div class="tody ico">
            	<p class="time"></p>
                <p class="date"></p>
            </div>
          	<div id="top">
          		<div style="font-size:12px;font-family:'宋体';text-align:center;color:#7a8798;hight:50px;line-height:50px">无日程安排</div>
          	</div>
          	<div class="morebox"><a href="javascript:;" class="add_schedule blue"  onclick="shecudle();">添加日程</a></div>
        </div>
		 
		<!--立项排期会-->
		<div resource-mark="div_schdule_lxh_gg" style="display:none">	
		<dl>
			<dt>立项会排期
				<a href="/report/html/projectMeeting.html" data-btn="project"
					class="more position_11 null">more</a>
			</dt>
			<dd>
				<table id="projectMeeting" width="100%" cellspacing="0"
					cellpadding="0" class="index">
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
			
		</dl>
		</div>
		<!--投决会排期-->
		<div resource-mark="div_schdule_tjh_gg" style="display:none">
		<dl class="tjh_block">
			<dt>投决会排期
				 <a href="/report/html/voteMeeting.html" data-btn="vote"  class="more position_12 null">more</a>
			</dt>
			<dd>
				<table width="100%" cellspacing="0" cellpadding="0">
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
		</dl>
		</div>
	</div>
	
	
	
	<!--中部内容-->
	<div class="min">
		<!--表格列表-->
		<div class="tablist clearfix">
			<!--左侧列表-->
			<div class="l l_executive">
				<div resource-mark="div_matter_preview_gg" style="display:none">
				<dl>
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
				</div>
				
				<div resource-mark="div_project_progress_gg" style="display:none">
				<dl>
					<dt>
						<h3 class="ico t8">项目进度</h3>
						<a href="javascript:;" class="more position_0 null" id="more_xmjd">more</a>
					</dt>
					<dd style="position: relative;">
						<div class="mask_platform_progress"></div>
						<div id="container_progress"
							style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
					</dd>
				</dl>
				</div>
				<div resource-mark="div_investment_gg" style="display:none">
				<dl class="executive_last">
					<dt>
						<h3 class="ico t9">投资资金</h3>
					</dt>
					<dd>
						<div id="charts_investment"
							style="min-width:300px; height: 200px;padding-top:5px;"></div>
					</dd>
				</dl>
				</div>
			</div>
			
			
			
			<!--右侧列表-->
			<div class="r r_executive">
				<div resource-mark="div_performance_gg" style="display:none">
				<dl class="r_news">
					<dt>
						<h3 class="ico t10">绩效考核</h3>
						<span class="more null position_0" id="platform_jxkh_more" style="cursor: pointer;">more</span>
					</dt>
					<dd>
						<div id="container_kpi" style="min-width:300px; height: 162px;padding-top:5px;"></div>
					</dd>
				</dl>
				</div>
				
				<div resource-mark="div_duration_gg" style="display:none">
				<dl>
					<dt>
						<h3 class="ico t11">项目历时</h3>
					</dt>
					<dd style="position: relative;">
						<div class="mask_platform_time"></div>
						<div id="container_time"
							style="min-width:300px; height: 145px; padding-top: 15px; margin-left: -5%"></div>
					</dd>
				</dl>
				</div>
				<div resource-mark="div_operation_gg" style="display:none">
				<dl class="tool_radius executive_last">
					<img src="<%=request.getContextPath()%>/img/sy.png" alt="" />
					<dt>
						<h3 class="ico t12">项目运营</h3>
					</dt>
					<dd>
						<div id="container_operation" style="min-width:300px; height: 200px;padding-top:5px;"></div>
					</dd>
				</dl>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="common/footer.jsp" flush="true"></jsp:include>



<script type="text/javascript">
	var forwardParam = {
			progressParam : undefined,
			timeParam : undefined
	};
</script>

<script src="<%=request.getContextPath() %>/js/charts/projectInvestment.js"></script>
<script src="<%=request.getContextPath() %>/js/charts/projectProgress.js"></script>
<script src="<%=request.getContextPath() %>/js/report/platform.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/init.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/sopUserSchedule.js" type="text/javascript"></script>


<script>


$(function() {
	
	$('#platform_jxkh_more').click(function(){
	    window.location.href=path + "/galaxy/kpireport/touserkpi?guid="+userId+"&sid="+sessionId;
	    //$("#platform_jxkh_more").attr('href' , path + "/galaxy/report/kpi?guid="+userId+"&sid="+sessionId +"#gg_jxkh_u");//绩效考核链接
	})
});
   
</script>