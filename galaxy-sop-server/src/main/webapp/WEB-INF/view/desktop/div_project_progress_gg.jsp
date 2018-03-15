<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
	<!-- 项目进度 -->
	<dl resource-mark="div_project_progress_gg" >
		<dt>
			<h3 class="ico t8">项目进度</h3>
			<ul class="ytxm_tab position_tab clearfix project_tab">
				<li data-tab="nav">全部项目</li>
				<li data-tab="nav">负责项目</li>
				<li data-tab="nav">协作项目</li>
			</ul>
			<!-- <a href="javascript:;" class="more position null" id="more_progress">more</a> -->
		</dt>
		<dd style="position: relative;">
			<div class="mask_platform_progress"></div>
			<div id="container_progress"
				style="height: 145px; margin-left: -8%"></div>
		</dd>
	</dl>


<script src="<%=path %>/js/charts/projectProgress.js"></script>
<script>
if(departmentId==2){
	$('.project_tab').hide()
}else{
	$('.project_tab').show()
}

</script>


