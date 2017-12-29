<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 	<div class="taskDetail-ritmin">
 		 <div class='taskDetail-mesage pagination_common'>
 		 
	        	<!--操作日志table  -->
	        	<table class='opretion-log no-radius table_new_style' width='100%' cellspacing='0' cellpadding='0' border='0' data-url="<%=request.getContextPath() %>/galaxy/soptask/${taskId}/logs">
	        		<thead>
	        			<tr>
	        				<th data-field="createdTime" data-formatter="longTimeFormat">时间</th>
	        				<th data-field="uname">操作者</th>
	        				<th data-field="operationType">动作</th>
	        				<th data-field="operationContent">对象</th>
	        				<th data-field="projectName" data-formatter="projectName">项目</th>
	        				<th data-field="reason" data-formatter='reason'>原因</th>
	        				<th data-field="sopstage">业务</th>
	        			</tr>
	        		</thead>
	        	</table>
        	</div>
        
        </div>
	</div>
</div>

<script type="text/javascript">
$(".opretion-log").bootstrapTable({
	queryParamsType: 'size|page', // undefined
	pageSize:10,
	showRefresh : false ,
	sidePagination: 'server',
	method : 'post',
	sortOrder : 'desc',
	sortName : 'created_time',
	pagination: true,
    search: false,
    queryParams: function(params){
    	params.page = params.pageNum;
    	params.size = params.pageSize;
    	return params;
    }
});
function reason(value,row,index){
    var id=row.id;
	var str=row.reason;
	if(str){
		if(str.length>12){
			subStr = str.substring(0,12);
			var options = '<span title="'+str+'">'+subStr+'</span>';
			return options;
		}
		else{
			var options = '<span title="'+str+'">'+str+'</span>';
			return options;
		}
	}else{
		return '-';
	}
	
}
function projectName(value,row,index){
    var id=row.id;
	var str=row.projectName;
	if(str){
		var options = '<span title="'+str+'">'+str+'</span>';
		return options;
	}else{
		return '-';
	}
	
}
</script>

