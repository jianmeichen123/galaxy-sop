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
			<table id='searchTable'  class='createProject table table-hover' style="width:100%;">
				<thead>
					<th data-field="projectName">项目</th>
					<th data-field="FinaceStatus" >融资状态</th>
					<th data-field="projectName">项目进度</th>
					<th data-field="projectName">投资经理</th>
					<th data-field="projectName">项目状态</th>
					<th data-field="projectName">最后编辑时间</th>
				</thead> 
				 <tbody>
					<!-- <tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr>
					<tr>
						<td>蚂蚁优厂</td>
						<td>天使</td>
						<td>内部评审</td>
						<td>陆亲<span>|</span>互联网旅游</td>
						<td>跟进中</td>
						<td>2017-08-21</td>
						
					</tr> -->
					
				
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
		param.keyword = "星";
		param.pageNo = 0;
		param.pageSize =10;
		param.pageSearchInfo = 'xhtProject'
		return param;
	}
	
	$('#searchTable').bootstrapTable({
		pageSize:10,
		pageNumber:0,
		url:'http://fx.local.galaxyinternet.com/sop/galaxy/infoDanao/searchGlobalInfo',
		method:'post',
		pagination:true,
		pageList:[10,20,30],
		sidePagination:'server',
		queryParams:queryParams,
		onLoadSuccess:function(data){
			
		}
	
	
	
	
})

	
	
	
})


	
	
	
</script>

