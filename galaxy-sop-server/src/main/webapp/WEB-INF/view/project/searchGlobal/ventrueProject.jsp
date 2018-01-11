<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<link href="<%=path %>/bootstrap/css/bootstrap-select.css" type="text/css" rel="stylesheet"/>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 		<div class='one pagination_common'>
			<table id='searchTable' data-url="<%=path %>/galaxy/infoDanao/queryXhtProjectPage" class='createProject table table-hover' style="width:100%;">
				<thead>
					<th data-field="projectName" data-formatter="projectInfo">项目</th>
					<th data-field="finance_status" data-formatter="financeStatusFormat">融资状态</th>
					<th data-field="project_progress" data-formatter="projectProgress">项目进度</th>
					<th data-field="createUname">投资经理</th>
					<th data-field="project_status" data-formatter="projectStatusFormat">项目状态</th>
					<th data-field="updated_time" data-formatter="updateFormat"  data-sortable="true">最后编辑时间</th>
				</thead> 
				 <tbody>
			
					
				
					</tbody>
				</table> 
		</div>
</div>

<script src="<%=request.getContextPath() %>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path %>/bootstrap/js/bootstrap-select.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=request.getContextPath() %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>  
<script type="text/javascript">

$(function(){
	function queryParams(params){
		return {
			pageNum:params.offset/params.limit+1,
			pageSize:params.limit,
			keyword:'',
			pageSearchInfo:'xhtProject',
			direction:'desc',
			property:'updated_time'
		}
	}
	
	$('#searchTable').bootstrapTable({
		pageSize:10,
		pageNumber:1,
		method:'post',
		//contentType: "application/x-www-form-urlencoded",
		pagination:true,
		pageList:[10,20,30],
		sortOrder : 'desc',
		sortName : 'updated_time',
		sidePagination:'server',
		queryParams:queryParams,
		onLoadSuccess:function(data){
			//console.log(data)
		}
	
	
	
	
})


	
	
})

/**
 * 更新时间格式化
 * @version 2016-06-21
 */
function updateFormat(value,row,index){
	return row.updateDate;
}
/**
 * 项目进度格式化
 * @version 2016-06-21
 */
 function projectProgress(value,row,index){
	var projectPro = row.projectProgress;
	var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
	var proStatus = row.projectStatus;
	var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);

	return row.progress;
}
/**
 * 融资状态格式化
 * @version 2016-06-21
 */
function financeStatusFormat(value,row,index){
	return row.financeStatusDs;
}
/**
 * 项目状态格式化
 * @version 2016-06-21
 */
function projectStatusFormat(value,row,index){
	return row.projectStatusDs;
}
/* 

*项目名称格式化
*
*
*/
function projectInfo(value,row,index){
    var id=row.id;
	var str=row.projectName;
	if(str.length>10){
		subStr = str.substring(0,10);
		var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+subStr+'</a>';
		return options;
	}
	else{
		var options = '<a href="#" class="blue" data-btn="myproject" onclick="proInfo(' + id + ')" title="'+str+'">'+str+'</a>';
		return options;
	}
}

function proInfo(id){
	//项目详情页返回地址
	setCookie("project_detail_back_path", Constants.sopEndpointURL + 'galaxy/mpl',6,'/');
	//返回附带参数功能代码
	var options = $("#project-table").bootstrapTable('getOptions');
	var tempPageSize = options.pageSize ? options.pageSize : 10;
	var tempPageNum = options.pageNumber ? options.pageNumber : 1;
	var projectType = $("select[name='projectType']").val();
	var financeStatus = $("select[name='financeStatus']").val();
	var projectProgress = $("select[name='projectProgress']").val();
	var projectStatus = $("select[name='projectStatus']").val();
	var projectDepartid = $("select[name='projectDepartid']").val();
	var createUid = $("select[name='createUid']").val();
	var nameCodeLike = $("input[name='nameCodeLike']").val();
	var projectPerson = $("input[name='projectPerson']").val();
	var faFlag = $("select[name='faFlag']").val();
	
	var formdata = {
			_paramKey : 'projectList',
			_url : Constants.sopEndpointURL + "/galaxy/project/detail/" + id,
			_path : "/",
			_param : {
				pageNum : tempPageNum,
        		pageSize : tempPageSize,
        		projectType : projectType,
        		financeStatus : financeStatus,
        		projectProgress : projectProgress,
        		projectStatus : projectStatus,
        		projectDepartid : projectDepartid,
        		createUid : createUid,
        		nameCodeLike : nameCodeLike,
        		projectPerson:projectPerson,
        		faFlag:faFlag
			}
	}
	var href_url=window.location;
	setCookie("href_url", href_url,24,'/');
	cookieOperator.forwardPushCookie(formdata);
}
	
</script>

