<%@ page language="java" pageEncoding="UTF-8"%>
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
<div class="pagebox">
	<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
	<div class="ritmin"> 
		<div class="pageTop clearfix">
			<div class="buttonGroup">				
				<div class="form-group">
			      <select class="selectpicker">
					  <option>Mustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>MustardMustardMustardMustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>Mustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			 	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>Mustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			    </div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>Mustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>

				<div class="form-group">
			      <select class="selectpicker">
					  <option>Mustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div>
				<div class="form-group">
			      <select class="selectpicker">
					  <option>Mustard</option>
					  <option>Ketchup</option>
					  <option>Relish</option>
					</select>
			  	</div> 
		  	</div>
		  	<div class="input-group">
		      <input type="text" class="form-control" placeholder="Search for...">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button">Go!</button>
		      </span>
		    </div>
		</div> 
		<div class="tab-pane active ctlist" id="view">	
			<table id="assign-table" data-url="project/search" data-page-list="[10, 20, 30]"  data-show-refresh="true">
				<thead>
				    <tr>
				    	<th data-field="projectName"  class="data-input" data-width="16%">
				    	<input type="checkbox" name="">
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
         				<c:if test="${fx:hasRole(4)}">
			        	<th  class="col-md-2" data-class="noborder" data-width="6%">操作</th>
 						</c:if>
 					</tr>	
 				</thead>
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

	
	
	
})


</script>
