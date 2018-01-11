<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 		<div class='one pagination_common'>
			<table id='searchTable' data-url="<%=path %>/galaxy/infoDanao/queryXhtProjectPage" class='createProject table table-hover' style="width:100%;">
				<thead>
					<th data-field="projectName">项目</th>
					<th data-field="finance_status" data-formatter="financeStatusFormat">融资状态</th>
					<th data-field="project_progress" data-formatter="projectProgress">项目进度</th>
					<th data-field="createUname">投资经理</th>
					<th data-field="project_status" data-formatter="projectStatusFormat">项目状态</th>
					<th data-field="updated_time" data-formatter="updateFormat"  data-sortable="true">最后编辑时间</th>
				</thead> 
				 <tbody>
			
					
				
					</tbody>
				</table> 
		</div>
</div>

<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
<script type="text/javascript">

$(function(){
	function queryParams(param){
		param.keyword = "";
		param.pageNo = 0;
		param.pageSize =10;
		param.pageSearchInfo = 'xhtProject';
		param.direction='desc';
		param.property ='updated_time'
		return param;
	}
	
	$('#searchTable').bootstrapTable({
		pageSize:10,
		pageNumber:0,
		method:'post',
		pagination:true,
		pageList:[10,20,30],
		sortOrder : 'desc',
		sortName : 'updated_time',
		sidePagination:'server',
		queryParams:queryParams,
		onLoadSuccess:function(data){
			
		}
	
	
	
	
})


	
	
})

/**
 * 更新时间格式化
 * @version 2016-06-21
 */
function updateFormat(value,row,index){
	return row.updateDate;
}
/**
 * 项目进度格式化
 * @version 2016-06-21
 */
 function projectProgress(value,row,index){
	var projectPro = row.projectProgress;
	var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
	var proStatus = row.projectStatus;
	var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);

	return row.progress;
}
/**
 * 融资状态格式化
 * @version 2016-06-21
 */
function financeStatusFormat(value,row,index){
	return row.financeStatusDs;
}
/**
 * 项目状态格式化
 * @version 2016-06-21
 */
function projectStatusFormat(value,row,index){
	return row.projectStatusDs;
}
	
	
</script>

