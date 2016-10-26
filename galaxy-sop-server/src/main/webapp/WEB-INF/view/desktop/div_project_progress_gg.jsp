<%@ page language="java" pageEncoding="UTF-8" %>
<% 
	String path = request.getContextPath();
%>
	<!-- 项目进度 -->
	<dl resource-mark="div_project_progress_gg" >
		<dt>
			<h3 class="ico t8">项目进度</h3>
			<a href="javascript:;" class="more position null" id="more_progress">more</a>
		</dt>
		<dd style="position: relative;">
			<div class="mask_platform_progress"></div>
			<div id="container_progress"
				style="height: 145px; margin-left: -5%"></div>
		</dd>
	</dl>


<script src="<%=path %>/js/charts/projectProgress.js"></script>



