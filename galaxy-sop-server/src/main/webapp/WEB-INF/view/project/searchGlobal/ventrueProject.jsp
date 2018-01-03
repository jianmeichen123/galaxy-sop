<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 	<div class="taskDetail-ritmin">
 		 <div class='taskDetail-mesage'>
 		 	<div class='taskDetail-mesage-top'>
 		 		<div class='message-detail-top'>
 		 			<div class='task-item task-item-left'>
		        		<ul>
		        			<li>项目名称：<span id="projectName"></span></li>
		        			<li>创建时间：<span id="createDate"></span></li>
		        			<li>事业部总经理：<span id="hhrName"></span></li>
		        		</ul>
		        	</div>
		        	<ul class='task-item'>
		        		<li>项目类型：<span id="type"></span></li>
		       			<li>投资事业线：<span id="projectCareerline"></span></li>
		       			<li>公司名称：<span id="projectCompany"></span></li>
		        	</ul>
		        	<ul class='task-item task-item-right'>
		        		<li>项目编码：<span id="projectCode"></span></li>
		       			<li>投资经理：<span id="createUname"></span></li>
		       			<c:if test="${ sessionScope.galax_session_user.id != task.assignUid }">
		       			<li>认领人：<span>${assignUname }</span></li>
		       			</c:if>
		        	</ul> 
 		 		</div>
	        	<a href='<%=path %>/galaxy/project/detail/${projectId}?mark=t' class='pro-detail'>项目详细信息 ></a>
        	</div>
        	
        	<div class='taskDetail-mesage-table'>
	        	<c:if test="${showIgnore }">
	        	<div class='task-no-need'><label class='task-no-label'></label><input type='checkbox' value=''/>不需要提供</div>
	        	</c:if>
	        	<table width='100%' class='task-detail-table table_new_style' border='0' cellspacing='0' cellpadding='0'>
	        		<thead>
	        			<tr>
	        				<th>创建日期</th>
	        				<th>存储类型</th>
	        				<th>更新日期</th>
	        				<th>档案状态</th>
	        				<th>操作</th>
	        			</tr>
	        		</thead>
	        		<tbody>
	        			<tr>
	        				<td>2017-04-09</td>
	        				<td>图片</td>
	        				<td>2017-08-04</td>
	        				<td>已上传</td>
	        				<td class='task-operation'>
	        					<a href='#'>查看</a>
	        					<a href='#'>查看历史</a>
	        				</td>
	        				
	        			</tr>
	        		</tbody>
	        	
	        	</table>
        	</div>
        	<!-- 处理任务 -->
        	<c:if test="${'taskStatus:2' == task.taskStatus and fx:hasPremission('task_dispose') }">
        	<div class='taskDetail-mesage-update'>
        		<a href='javascript:;' class="upate-task bluebtn_new" id="file-upload-btn">${btnTxt }</a>
        		<a href='javascript:;'  class='upate-task submit-success disabled' id="complete-task-btn" disabled="disabled">提交完成</a>
        		 <span class='more-task fr'>更多操作</span>
		         <ul class='task-toggle more-operateOne'>
		         	<li data-code='transfer-task'>移交任务</li>
	          		<li data-code='abandon-task'>放弃任务</li>
	          	</ul>
        	</div>
        	</c:if>
        	<!-- 已完成任务再处理 -->
        	<c:if test="${'taskStatus:3' == task.taskStatus and fx:hasPremission('task_redispose') }">
        	<div class='taskDetail-mesage-update'>
        		<a href='javascript:;' class="upate-task bluebtn_new" id="file-upload-btn">${btnTxt }</a>
        		<span class='more-task fr'>更多操作</span>
		         <ul class='task-toggle more-operateOne'>
		         	<li data-code='transfer-task'>移交任务</li>
	          		<li data-code='abandon-task'>放弃任务</li>
	          	</ul>
        	</div>
        	</c:if>
        </div>
	</div>
</div>
<script type="text/javascript">

	
	
	
</script>

