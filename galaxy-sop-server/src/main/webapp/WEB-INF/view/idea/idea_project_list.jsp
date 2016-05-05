<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
	<div style=" width:1200px" class="ritmin">

        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="idea-project-table-toolbar">
			<input type="hidden" name="ideaProgress" value="ideaProgress:6">
			<div class="bottom searchall clearfix search_adjust">
				<dl class="fmdl fmdll clearfix">
					<dt>关键字：</dt>
					<dd>
						<input type="text" class="txt" name="keyword" placeholder="创意名称或编码" />
					</dd>
				</dl>
				<dl class="fmdl fml fmdll clearfix">
	              <dt>所属事业线：</dt>
	              <dd>
	                <select name="departmentId">
	                  <option value="">全部</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	            	<dt>提出时间：</dt>
	            	<dd>
	            		<input type="text" class="datepicker txt time" readonly id="createdDate" name="createdDate"  style="height:23px;"/>
	            	</dd>
	            	<dd>
						<button type="submit" class="bluebtn cx" action="querySearch">搜索</button>
	            	</dd>
	            </dl>
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="idea-project-table" data-url="idea/search" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#idea-project-table-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="ideaCode" data-align="center" class="data-input">创意编码</th>
			        	<th data-field="ideaName" data-align="center" class="data-input">创意名称</th>
			        	<th data-field="projectName" data-align="center" class="data-input">关联项目</th>
			        	<th data-field="departmentDesc" data-align="center" class="data-input">所属事业线</th>
			        	<th data-field="ideaProgress" data-align="center" class="data-input" data-formatter="progressFormatter">进度</th>
			        	<th data-field="hhrName" data-align="center" class="data-input">合伙人</th>
			        	<th data-field="claimantUname" data-align="center" class="data-input">投资经理</th>
			        	<th data-field="createdTime" data-align="center" class="data-input" data-formatter="dateFormatter">提出时间</th>
 					</tr>	
 				</thead>
			</table>
           </div>



</div>

<script type="text/javascript">
	$('#idea-project-table').bootstrapTable({
		url:'idea/search',
		queryParamsType: 'size|page', 
		pageSize:10,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
	    search: false,
	});
	getDepartment($("#idea-project-table-toolbar [name='departmentId']"));
</script>


