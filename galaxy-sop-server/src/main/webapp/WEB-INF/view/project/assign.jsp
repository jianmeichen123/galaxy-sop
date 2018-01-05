<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]--> 
<jsp:include page="../common/taglib.jsp" flush="true"></jsp:include> 
<link href="<%=path %>/css/axure.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/css/infoEnter.css" type="text/css" rel="stylesheet"/> 
<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
</head>

<body >
<div class="pagebox assign_project" >
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<div class='content_task'>
		<div class='title_top'>
			<h3>指派项目</h3>
			<span class='operate_project' data-code='transfer-task'>指派项目</span>
			<span class='operate_project' data-code='abandon-task'>移交项目</span>
		</div>
		<div class="pageTop clearfix">
			<div class="buttonGroup clearfix">				
				<div class="form-group">
			      <select class="selectpicker">
					  <option>全部事业部</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>投资经理</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>项目进度</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			 	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>项目状态</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			    </div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>融资状态</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>项目来源</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>
				
		  	</div>
		  	<div class="input-group">
			  	<di class='input-content'>
			  		<input type="text" class="form-control" placeholder="请输入项目名称">
			  		<span></span>
			  	</di>
		      <!-- <span class="input-group-btn">
		        <button class="btn btn-default" type="button"></button>
		      </span> -->
		      <span class='reset_search'>重置</span>
		    </div>
		</div> 
	</div>
	<div class="ritmin"> 
		
		<div class="">	
			<table class='assingTable' id="assign-table" data-url="project/search" data-page-list="[10, 20, 30]"  data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="projectName"  class="data-input" data-width="16%">
				    		<label class='highlighCheckbox_th'>
				    			<input type="checkbox" name="">
				    		</label>
				    	</th>
			        	<th data-field="projectName"  class="data-input" data-width="16%">项目名称</th>
			        	<th data-field="project_type" class="data-input sort" data-width="8%">项目类型<span></span></th>
			        	<th data-field="finance_status" class="data-input sort"  data-width="8%">融资状态<span></span></th>
			        	<th data-field="project_progress" class="data-input sort"  data-width="8%">项目进度<span></span></th>
			        	<th data-field="project_status" class="data-input sort"  data-width="8%">项目状态<span></span></th>
			        	<th data-field="faFlag"   data-width="8%">项目来源<span></span></th>
			        	<th data-field="projectCareerline"  class="data-input" data-width="12%">事业部</th>
			        	<th data-field="createUname"  class="data-input" data-width="14%">投资经理</th>
			        	<th data-field="created_time"  class="data-input sort"  data-width="8%">创建日期<span></span></th>
			        	<th data-field="updated_time" class="data-input sort"  data-width="5%">最后编辑时间<span></span></th>
 					</tr>	
 				</thead>
 				<tbody>
 					<tr>
 						<td>
 							<label class='highlighCheckbox'>
				    			<input type="checkbox" name="">
				    		</label>
				    	</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 					</tr>
 					<tr>
 						<td>
 							<label class='highlighCheckbox'>
				    			<input type="checkbox" name="">
				    		</label>
				    	</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 					</tr>
 					<tr>
 						<td>
 							<label class='highlighCheckbox'>
				    			<input type="checkbox" name="">
				    		</label>
				    	</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 						<td>人事尽职调查报告</td>
 					</tr>
 				</tbody>
			</table>
       </div>
	</div>
</div>
</body> 
<jsp:include page="../common/header.jsp" flush="true"></jsp:include>
<jsp:include page="../common/footer.jsp" flush="true"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp" flush="true"></jsp:include>
	  
</html>
<script>
$(function(){
	
	//导航
	createMenus(5);
 $('.selectpicker').selectpicker({
  style: 'btn-info',
  size: 4
});

 detailHeaderWidth();
 function detailHeaderWidth(){
 	  var  w_lft=$(".lft").width();
 	  	$('.content_task').css({'margin-left':w_lft});
 }
 $(window).resize(function(){
 	detailHeaderWidth();
 })	

 /* checkbox 点击 */
 $('.highlighCheckbox').click(function(event){
	 $(this).toggleClass('highlighCheckbox_checked');
	 event.preventDefault(); 
	 
 });
 //全选
 $('.highlighCheckbox_th').click(function(event){
	 $(this).toggleClass('highlighCheckbox_checked');
	 $('.highlighCheckbox').addClass('highlighCheckbox_checked');
	 if(!$(this).hasClass('highlighCheckbox_checked')){
		 $('.highlighCheckbox').removeClass('highlighCheckbox_checked');
	 }
	 event.preventDefault(); 
 })
 
 
 
 	/*指派项目弹窗点击事件*/
	$('.title_top span').click(function(){
		/* var rows = $("#task-table").bootstrapTable('getSelections');
		if(rows.length==0)
		{
			layer.msg('请至少选择一条待办任务');
			return;
		} */
		//var index = $(this).index();
		
		var code = $(this).attr("data-code");
		$.getHtml({
			url:getDetailUrl(code)
		});
		$('.close').addClass('tast-close')//添加关闭按钮
		$('.pop').addClass('task-pop');//去掉圆角
	});
	
	//页面请求地址
	function getDetailUrl(code)
	{
		if(code =='transfer-task')
		{	
			return '<%=path%>/html/assign_project.html';
		}else if(code === 'abandon-task'){
			return '<%=path%>/html/handover_project.html';
		}	
		return "";
	}
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
	
})


</script>
