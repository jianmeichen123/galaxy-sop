<%@ page language="java" pageEncoding="UTF-8"%>

<div class="xmstjtc">
	<div id="custom-toolbasr_deptkpi_projectlist">
		<input type="hidden" name="projectType" id="deptkpi_projectlist_projectType" value=""> 
		<input type="hidden" name="deptid" id="deptkpi_projectlist_deptid" value="">
		<input type="hidden" name="sdate" id="deptkpi_projectlist_sdate" value=""> 
		<input type="hidden" name="edate" id="deptkpi_projectlist_edate" value="">
	</div>
	<table id="data-table-deptkpi-projectlist"
		data-url="<%=request.getContextPath()%>/galaxy/report/userkpi"
		data-page-list="[10, 20, 50]" width="100%" width="100%"
		cellspacing="0" cellpadding="0"
		data-toolbar="#custom-toolbasr_deptkpi_projectlist">
		<thead>
			<tr>
				<th data-field="real_name"  class="data-input">姓名</th>
				<th data-field="dept_name"  class="data-input">投资事业线</th>
				<th data-field="target"  class="data-input">目标数</th>
				<th data-field="completed"  class="data-input">项目数</th>
				<th data-field="completed_all"  class="data-input">累计已完成数</th>
				<th data-field="company_rank"  class="data-input">公司排名</th>
				<!-- <th data-field="dept_rank"  class="data-input">部门排名</th> -->
				<th data-field="total_rate"  class="data-input" data-formatter="rate_format">公司完成数占比</th>
				<th data-field="dept_rate"  class="data-input" data-formatter="rate_format">部门完成数占比</th>
				<th data-field="lxh_pnumber"  class="data-input">立项会通过数</th>
				<th data-field="tjh_pnumber"  class="data-input">投资决策会通过数</th>
				<th data-field="ghl_rate"  class="data-input" data-formatter="rate_format">过会率</th>
				<th data-field="tjl_rate"  class="data-input" data-formatter="rate_format">投决率</th>
			</tr>
		</thead>
	</table>
</div>