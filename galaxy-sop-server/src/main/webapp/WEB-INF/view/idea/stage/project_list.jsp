<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
 <!--按钮-->
<div class="btnbox_f btnbox_f1 btnbox_m clearfix">
    <button class="pubbtn fffbtn lpubbtn" href="idea/showEditProjectDialog" data-btn="edit_name">编辑项目名称</button>
</div>

<!--表格内容-->
<div id="project-toolbar">
	<input type="hidden" name="id" value="${ideaId }">
</div>
<table id="project-table" data-url="idea/search" data-toolbar="#project-toolbar">
    <thead>
        <tr>
            <th data-field="ideaCode" data-align="center" class="data-input">创意编码</th>
            <th data-field="ideaName" data-align="center" class="data-input">创意名称</th>
            <th data-field="projectName" data-align="center" class="data-input">关联项目</th>
            <th data-field="departmentDesc" data-align="center" class="data-input">投资事业线</th>
            <th data-field="projectProgressDesc" data-align="center" class="data-input">项目进度</th>
            <th data-field="hhrName" data-align="center" class="data-input">合伙人</th>
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
