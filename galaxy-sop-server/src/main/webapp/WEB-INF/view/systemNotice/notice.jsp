 <%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>星河投</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
	<link href="<%=path%>/css/axure.css" type="text/css" rel="stylesheet" />
	<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
	<!--[if lt IE 9]><link href="css/lfie8.css" type="text/css" rel="stylesheet"/><![endif]-->
	<jsp:include page="../common/taglib.jsp"></jsp:include>
	<script src="<%=request.getContextPath()%>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
</head>

<body> 
	<div class="pagebox clearfix">
		<jsp:include page="../common/menu.jsp" flush="true"></jsp:include>
		<div class="ritmin">
			<h2 class='system_inform'>系统通知<span class='fr add_pro_common add_system'>新建</span></h2>	
			<div class="tableSearch">
				<div class="searchTerm">
					<label>发送时间：</label>
					<input class='' readonly='readonly' type='text' name="formationStart"/>
					<span class='system_arrive'>至</span>					
					<input  class='' readonly='readonly' type='text' name="formationEnd"/> 
					<label class='system_status'>状态：</label>
					<select>
						<option>1</option>
						<option>1</option>
						<option>1</option>
						<option>1</option>
					</select>
					<span class='system_querySearch'>查询</span>
				</div>
				<table   class='assingTable table-hover systemtTable' id="noticeTable" style='table-layout:fixed;'>
					<thead>
					    <tr> 
				        	<th data-field="projectName"  data-width="14%" data-align="left">通知内容</th>
				        	<th data-field="projectName"  data-width="14%" data-align="left">创建人</th>
				        	<th data-field="projectName"  data-width="14%" data-align="left">创建时间</th>
				        	<th data-field="projectName"  data-width="18%" data-align="left">推送通知时间</th>
				        	<th data-field="projectName"  data-width="12%" data-align="left">状态</th>
				        	<th data-field="projectName"  data-width="14%" data-align="left">发送平台</th>
				        	<th data-field="projectName"  data-width="14%" data-align="left">操作</th>
	 					</tr>	
	 				</thead>
	 				<tbody>
	 					<tr>
	 						<td>2233333333333333333333</td>
	 						<td>22</td>
	 						<td>22</td>
	 						<td>22</td>
	 						<td>22</td>
	 						<td>22</td>
	 						<td>
	 							<span class='system_close'>关闭</span>
	 							<span class='system_delete'>编辑</span>
	 							<span class='system_edit'>删除</span>
	 						</td>
	 						
	 					</tr>
	 				</tbody>
				</table> 
			
			
			
			</div>	
		</div>
	</div>
</body>
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="../common/footer.jsp"></jsp:include></body>
<jsp:include page="../common/uploadwin.jsp"></jsp:include>

</html>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js"></script>
<script>
	createMenus(5);
	//日期选择
	$('.searchTerm input[name="formationStart"]').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    defaultDate : Date,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now'
	});
	$('.searchTerm input[name="formationEnd"]').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    defaultDate : Date,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now'
	});
	$('.system_querySearch').click(function(){
		var startTime = $('input[name="formationStart"]').val();
		var endTime = $('input[name="formationEnd"]').val();
		if(startTime>endTime){
			layer.msg('开始时间不能大于结束时间')
		}
		
	})
	
/* 	$('#noticeTable').bootstrapTable({
		method:'post',
		url:'33',
		columns:[
			{
				title:'标注编号',
				field:'sss'
			}
			
		]
		
		
	}) */
	
	
	
	
</script>
