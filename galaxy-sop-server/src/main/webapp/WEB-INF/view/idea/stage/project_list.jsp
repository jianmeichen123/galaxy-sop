<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<% 
	String path = request.getContextPath(); 
%>
<script>
var isTransfering = "${fx:isTransfering(projectId) }";
if(isTransfering == 'true'){
	$("#edit_name").remove();
}
</script>
 <!--按钮-->
<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
    <button class="pubbtn fffbtn lpubbtn" href="idea/showEditProjectDialog" id="edit_name" data-btn="edit_name">编辑项目名称</button>
</div>

<!--表格内容-->
<div id="project-toolbar">
	<input type="hidden" name="id" value="${ideaId }">
</div>
<table id="project-table" data-url="idea/search" data-toolbar="#project-toolbar">
    <thead>
        <tr>
            <th data-field="ideaCode" data-align="left" class="data-input">创意编码</th>
            <th data-field="ideaName" data-align="left" class="data-input">创意名称</th>
            <th data-field="projectName" data-align="left" class="data-input">关联项目</th>
            <th data-field="departmentDesc" data-align="left" class="data-input">投资事业线</th>
            <th data-field="projectProgressDesc" data-align="left" class="data-input">项目进度</th>
            <th data-field="hhrName" data-align="left" class="data-input">合伙人</th>
            <th data-field="claimantUname" data-align="left" class="data-input">投资经理</th>
            <th data-field="createdTime" data-align="left" class="data-input" data-formatter="dateFormatter">提出时间</th>
        </tr>
    </thead>                                                                                                                                    
</table>
<script>
$(function(){
	$('#project-table').bootstrapTable({
		queryParamsType: 'size|page', 
		pageSize:10,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
	    search: false,
	});
});


</script>
<!--

//-->
</script>
