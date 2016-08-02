<%@ page language="java" pageEncoding="UTF-8"%>

<div class="xmstjtc">
	<div id="custom-toolbasr_ghl_projectlist">
		<input type="hidden" name="deptid" id="ghl_projectlist_deptid" value="-1">
		<input type="hidden" name="secLxh" value="1">
		<input type="hidden" name="userid" id="ghl_projectlist_userid" value="-1">
		<input type="hidden" name="sdate" id="ghl_projectlist_sdate" value="">
		<input type="hidden" name="edate" id="ghl_projectlist_edate" value="">
	</div>
	<table id="data-table-ghl-projectlist"
		data-url="<%=request.getContextPath()%>/galaxy/report/projectlist"
		data-page-list="[10, 20, 50]" width="100%" width="100%"
		cellspacing="0" cellpadding="0"
		data-toolbar="#custom-toolbasr_ghl_projectlist">
		<thead>
			<tr>
				<th data-field="project_name"  class="data-input">项目名称</th>
				<th data-field="partner"  class="data-input">合伙人</th>
				<th data-field="user_name"  class="data-input">投资经理</th>
				<th data-field="project_type"  class="data-input">项目类型</th>
				<th data-field="created_time"  class="data-input">创建时间</th>
				<th data-field="project_progress_name"  class="data-input">项目进度</th>				
				<th data-field="duration_day"  class="data-input">总历时(天)</th>
			</tr>
		</thead>
	</table>
</div>

