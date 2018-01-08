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
		
		<div class="tab-pane active ctlist pagination_common" id="view">
			<table   class='assingTable table-hover' id="assign-table"  
				data-page-list="[10, 20, 30]" data-toolbar="#custom-toolbar" data-show-refresh="true">
				<thead>
				    <tr>
				   		<th data-formatter="fun122" class="data-input" data-width="1%">
				    		<label class='highlighCheckbox_th'>
				    				<input type="checkbox" name="">
				    		</label> 
				    	</th>
			        	<th data-field="projectName"  data-formatter="projectInfo" data-width="10%">项目名称</th>
			        	<th data-field="project_type" data-formatter="typeFormat"    data-width="8%">项目类型</th>
			        	<th data-field="finance_status" data-formatter="financeStatusFormat"   data-width="8%">融资状态</th>
			        	<th data-field="project_progress" data-formatter="projectProgress"   data-width="8%">项目进度</th>
			        	<th data-field="project_status" data-formatter="projectStatusFormat"   data-width="8%">项目状态</th>
			        	<th data-field="faFlag" data-formatter="projectFaFormat"   data-width="8%">项目来源</th>
			        	<th data-field="projectCareerline"   data-width="12%">事业部</th>
			        	<th data-field="createUname"   data-width="14%">投资经理</th>
			        	<th data-field="created_time" data-formatter="createdFormat"    data-width="8%">创建日期</th>
			        	<th data-field="updated_time" data-formatter="updateFormat"   data-width="8%">最后编辑时间</th>
 					</tr>	
 				</thead>
			</table> 
		
 			<!-- <tbody>
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
 						<td>人事尽职调查报告</td>s
 						<td>人事尽职调查报告</td>
 					</tr>
 		
 				</tbody> -->
			
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
	
	function fun(value,row,index){
		return  options = "<a href='javascript:;' onclick='editRow(event)'>编辑</a>&nbsp;&nbsp;<a href='javascript:;' onclick='deleteRow(event)'>删除</a>";
		
	}
	var initPageSize = 10;
	$('#assign-table').bootstrapTable({
		queryParamsType: 'size|page',
		pageSize:initPageSize,
		showRefresh : false,
		url : 'http://fx.local.galaxyinternet.com/sop/galaxy/project/search',
		sidePagination: 'server',
		method : 'post',
		sortOrder : 'desc',
		sortName : 'updated_time',
		pagination: true,
        search: false,
        singleSelect:true,
        //返回附带参数功能代码
        queryParams : function(param){
        	if(getCookieValue("backProjectList")!=''){
        		initParams = cookieOperator.pullCookie({_paramKey : 'projectList',_path : "/"});
        		deleteCookie("backProjectList","/");
        	}else{
        		initParams=undefined;
        	}
        	if(typeof(initParams) !== 'undefined'){
    			param.pageNum = initParams.pageNum - 1;
        		param.pageSize = initParams.pageSize;
        		if(initParams.projectType != ''){
        			param.projectType = initParams.projectType;
        			$("select[name='projectType']").val(initParams.projectType);
        		}
        		if(initParams.financeStatus != ''){
        			param.financeStatus = initParams.financeStatus;
        			$("select[name='financeStatus']").val(initParams.financeStatus);
        		}
        		if(initParams.projectProgress != ''){
        			param.projectProgress = initParams.projectProgress;
        			$("select[name='projectProgress']").val(initParams.projectProgress);
        		}
        		if(initParams.faFlag != ''){
        			param.faFlag = initParams.faFlag;
        			$("select[name='faFlag']").val(initParams.faFlag);
        		}
        		if(initParams.financeStatus != ''){
        			param.financeStatus = initParams.financeStatus;
        			$("select[name='financeStatus']").val(initParams.financeStatus);
        		}
        		param.projectDepartid = initParams.projectDepartid;
        		$("select[name='projectDepartid']").val(initParams.projectDepartid);
        		createUserOptions_All(platformUrl.getUserList+initParams.projectDepartid, "createUid", 1);
        		param.createUid = initParams.createUid;
        		$("select[name='createUid']").val(initParams.createUid);
        		if(initParams.nameCodeLike !=''){
        			 param.nameCodeLike = initParams.nameCodeLike;
 	        		$("input[name='nameCodeLike']").val(initParams.nameCodeLike); 
        		}
        		if(initParams.projectPerson !=''){
        			param.projectPerson = initParams.projectPerson;
        			$("input[name='projectPerson']").val(initParams.projectPerson); 
        		}
        		var options = $("#data-table").bootstrapTable('getOptions');
 	        	options.pageNumber = initParams.pageNum - 1; 
    		}
        	return param;
        },
        onLoadSuccess: function (data) {
        	if($("#showResetBtn").val() == '1'){
    			$("#resetBtn").removeClass("none");
    		}
        	if($("select[name='faFlag']").val()=="projectSource:1"){
        		$("input[name='faName']").show();
        	}
        	
        	if(typeof(initParams) !== 'undefined' && initParams.pageNum != ''){
    			if(initParams.pageNum==1){
    				return;
    			}else{
    				$('.pagination li').removeClass('active');
    				if($('.pagination .page-number').length< initParams.pageNum)
    				{
    					var len = $('.pagination .page-number').length;
    					var totalPages = $("#project-table").bootstrapTable('getOptions').totalPages;
    					var end = initParams.pageNum + Math.floor(len/2);
    					if(end>totalPages)
						{
    						end = totalPages;
						}
    					
    					for(var i=len-1; i>=0; i--)
    					{
    						$('.pagination .page-number').eq(i).html('<a href="javascript:void(0)">'+ end-- +'</a>');
    					}
    				}

    				$('.pagination li').each(function(){
    	    			if($(this).text()==initParams.pageNum){
    	    				$(this).click();
    	    				 return false;
    	    				//$(this).addClass('active')
    	    			}
    				});
    			}
    		}
        	initPageSize=10;
        }
	});
	
	
	
	function typeFormat(value,row,index){
		return row.type;
	}
	/**
	 * 项目FA格式化
	 * @version 2016-06-21
	 */
	function projectFaFormat(value,row,index){
		var retStr = '-';
		if(!row.faFlag)
		{
			return '-';
		}
		if(row.faName)
		{
			if(row.faName.length>4){
				var faName=row.faName.substring(0,4);
				retStr="<div title='"+row.faFlagStr+'-'+row.faName+"'>"+row.faFlagStr+'-'+faName+"</div>";
			}else{
				retStr="<div title='"+row.faFlagStr+'-'+row.faName+"'>"+row.faFlagStr+'-'+row.faName+"</div>";
			}
			
		}else{
			retStr="<div title='"+row.faFlagStr+"'>"+row.faFlagStr+"</div>";
		}
		return retStr;
		
	}
	/**
	 * 项目状态格式化
	 * @version 2016-06-21
	 */
	function projectStatusFormat(value,row,index){
		return row.projectStatusDs;
	}
	
	
	
	
	
	
	
	
	
	
	
 
})


</script>
