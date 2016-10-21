<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<dl id="project-progress-div">
	<dt>
		<h3 class="ico t4">项目进度</h3>
	</dt>
	<dd>
		<table id="project-progress-table">
			<thead>
				<tr>
					<th data-field="projectName" data-align="left" >项目名称</th>
					<th data-field="projectCareerline" data-align="left">事业线</th>
					<th data-field="progress" data-align="left">目前进度</th>
				</tr>
			</thead>
		</table>
	</dd>
	<dd class="clearfix position">
		<a href="<%=path%>/galaxy/mpl" class="more null">more</a>
	</dd>
</dl>

<script>

	var url = Constants.sopEndpointURL + "/galaxy/mpl?sid="+sessionId+"&guid="+userId;
	$("#project-progress-div .more").attr('href',url);
	$('#project-progress-table').bootstrapTable({
		queryParamsType : 'size|page', // undefined
		pageSize : 3,
		showRefresh : false,
		url : Constants.sopEndpointURL + '/galaxy/project/search',
		sidePagination : 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'updated_time',
		search : false,
		queryParams:function(param){
			param.pageNum=0;
			param.pageSize=3;
			return param;
		},
		onLoadSuccess : function(data) {
			if (data.pageList.total < 3) {
				$("#project-progress-div .more").css("display", "none");
			}
		}
	});

</script>