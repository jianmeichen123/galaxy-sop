<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>

<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/beautify.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/style.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/operationMessage.js" type="text/javascript"></script>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/my_ext.js"></script>
<script src="<%=path %>/js/my.js"></script>
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">

<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->


        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="custom-toolbar">
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
	              <dt>进度：</dt>
	              <dd>
	                <select name="ideaProgress">
	                  <option value="">全部</option>
	                  <option value="ideaProgress:1">待认领</option>
	                  <option value="ideaProgress:2">调研</option>
	                  <option value="ideaProgress:3">创建立项会</option>
	                  <option value="ideaProgress:4">搁置</option>
	                  <option value="ideaProgress:5">创建项目</option>
	                  <option value="ideaProgress:6">立项会</option>
	                  <option value="ideaProgress:7">投决会</option>
	                </select>
	              </dd>
	            </dl>
	            <dl class="fmdl fml fmdll clearfix">
	            	<dt>提出时间：</dt>
	            	<dd>
	            	<input type="text" class="datepicker txt time" readonly id="createdDate" name="createdDate"  style="height:23px;"/>
	            	</dd>
	            	<dd>
						<button type="submit" class="bluebtn ico cx" action="querySearch">搜索</button>
				    </dd>
	            </dl>
				
			</div>
		</div>
		<div class="tab-pane active" id="view">	
			<table id="idea-project-table" data-url="idea/search" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
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




<script src="<%=path %>/js/idea-common.js"></script>
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
	getDepartment();
</script>


