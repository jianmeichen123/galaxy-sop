<%@ page language="java" pageEncoding="UTF-8"%>

<div class="xmstjtc margin_45" >
	<div class="title_bj" id="tc_title_name"></div>
	<div id="custom-toolbasr_deptkpi_projectlist">
	</div>
	<table id="data-table-deptkpi-projectlist"
		data-page-list="[10, 20, 50]" width="100%" width="100%"
		cellspacing="0" cellpadding="0" data-toolbar="#custom-toolbasr_deptkpi_projectlist">
		<thead>
			<tr>
				<th data-field=realName  		 class="data-input">	姓名			</th>
				<th data-field="departmentName"  class="data-input">	投资事业部	</th>
				<th data-field="target"  		 class="data-input">	目标数		</th>
				<th data-field="completed"  	 class="data-input">	项目数		</th>
				<th data-field="completedAll"    class="data-input">	累计已完成数	</th>
				<!-- <th data-field="companyRank"     class="data-input">	公司排名		</th>  -->  <!--  data-formatte="com_ranking" -->
				<th data-field="totalRate"  	 class="data-input" data-formatter="rate_format">公司完成数占比</th>
				<th data-field="deptRate" 	     class="data-input" data-formatter="rate_format">部门完成数占比</th>
				<th data-field="lxhPnumber"  	 class="data-input">	立项会通过数	</th>
				<th data-field="tjhPnumber"  	 class="data-input">	投资决策会通过数</th>
				<th data-field="ghlRate"  		 class="data-input" data-formatter="rate_format">过会率</th>
				<th data-field="tjlRate"  		 class="data-input" data-formatter="rate_format">投决率</th>
			</tr>
		</thead>
	</table>
</div>