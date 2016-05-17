<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
	<div style=" width:1200px" class="ritmin">

        <!-- 搜索条件 -->
		<div class="min_document clearfix" id="idea-project-table-toolbar">
		
			<input type="hidden" name="ideaProgress" value="${ideaProgress}">
			
			<div class="bottom searchall clearfix search_adjust">
			
				<dl class="fmdl fmdll clearfix">
					<dt>关键字：</dt>
					<dd>
						<input type="text" class="txt" name="keyword" placeholder="创意名称或编码" />
					</dd>
				</dl>
				
				<dl class="fmdl fmdll clearfix">
	              <dt>所属事业线：</dt>
	              <dd>
	                <select name="departmentId">
	                  <option value="">全部</option>
	                </select>
	              </dd>
	            </dl>
	            
	            <dl class="fmdl fmdll clearfix">
	            	<dt>提出时间：</dt>
	            	<dd>
	            		<input type="text" class="ideadatepicker txt time" name="createdDateFrom"  style="height:23px;"/>至
	            		<input type="text" class="ideadatepicker txt time" name="createdDateThrough"  style="height:23px;"/>
	            	</dd>
	            </dl>
	            <span class="fmdl fmdll clearfix">
	            	<button type="submit" class="bluebtn cx" action="querySearch">搜索</button>
	            </span>
	            
			</div>
		</div>
		
		
		<div class="tab-pane active" id="view">	
			<table id="idea-project-table" data-url="idea/search" data-height="555" 
				data-page-list="[10, 20, 30]" data-toolbar="#idea-project-table-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="ideaCode" data-align="center" class="data-input">创意编码</th>
			        	<th data-field="ideaName" data-align="center" class="data-input" data-formatter="ideaNameLinkFormatter2">创意名称</th>
			        	<th data-field="projectName" data-align="center" class="data-input" data-formatter="proNameLinkFormatter">关联项目</th>
			        	<th data-field="departmentDesc" data-align="center" class="data-input">所属事业线</th>
			        	<th data-field="projectProgressDesc" data-align="center" class="data-input">进度</th>
			        	<th data-field="hhrName" data-align="center" class="data-input">合伙人</th>
			        	<th data-field="claimantUname" data-align="center" class="data-input">投资经理</th>
			        	<th data-field="createdTime" data-align="center" class="data-input" data-formatter="datetimeFormatter">提出时间</th>
 					</tr>	
 				</thead>
			</table>
           </div>



</div>

<script type="text/javascript">
	$('.ideadatepicker').datepicker({
		format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    clearBtn:true,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:true
	});
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


