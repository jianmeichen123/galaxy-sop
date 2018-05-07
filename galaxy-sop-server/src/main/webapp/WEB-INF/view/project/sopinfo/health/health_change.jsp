<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>
<!-- 高管/投资经理 -->
<c:set var="aclViewProject" value="${fx:hasRole(1) || fx:hasRole(2) || (fx:hasRole(3) && fx:inOwnDepart('project',projectId)) || fx:hasRole(18)||fx:hasRole(19)|| fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isCreatedByUser" value="${fx:isCreatedByUser('project',projectId)  }" scope="request"/>
<c:set var="isEditable" value="${fx:isCreatedByUser('project',projectId) && !fx:isTransfering(projectId)}" scope="request"/>
<c:set var="isThyy" value="${fx:hasRole(20)}" scope="request"/>
<style>
.bars{margin:0 !important;}
</style>
<div class="addmentc tab2Con"> 
	
    <div class="form clearfix" >
    
		<c:if test="${isEditable}">
			<div class="btnbox_f">
				<a href="javascript:void(0)"  class="new_blueBtn bluebtn" data-btn="health_status" data-name='健康状况' style="width:90px;"></a>
			</div>
		</c:if>
		
		<div class="  scroll_table"  id="health_case_scroll">
			 <table class="health_case table table_health_case">
				<thead>
					<tr>
						<th width="10%">健康状况　</th>
						<th width="13%">风险点</th>
						<th width="27%">分析人</th>
						<th width="20%">分析日期</th>
						<th width="30%">操作</th>
					</tr>
				</thead>
			</table> 
			<table id="project_health_table" class="health_case healthCommTable"
				data-page-list="[5, 10, 20]" 
				data-url="<%=path%>/galaxy/health/queryhealthpage" 
				data-id-field="id"
				data-toolbar="#health-custom-toolbar">
				<thead>
					<tr>
						<th data-width="10%" data-field="healthStateStr"  >健康状况　</th>
						<th data-width="13%" data-field="rematk">风险点</th>
						<th data-width="27%" data-field="userName">分析人</th>
						<th data-width="20%" data-field="createdTime" data-formatter="longTime_Format" >分析日期</th>
						<th data-width="30%"  data-formatter="editor" >操作</th>
					</tr>
				</thead>
			</table>
		</div>
        </div>
    
    
  	
</div>
<script src="<%=path %>/js/axure.js" type="text/javascript"></script>
<script src="<%=path %>/js/common.js" type="text/javascript"></script> 
<!-- 分页二css+四js -->
<link rel="stylesheet" href="<%=path %>/bootstrap/bootstrap-table/bootstrap-table.css"  type="text/css">
<script src="<%=path %>/js/init.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/bootstrap-table-xhhl.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<!-- file -->
<script src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/datePicker-handler-init.js"></script>
<script type="text/javascript" src="<%=path%>/js/tabPostMeetingAnlysis.js"></script>
<script src="<%=path %>/js/batchUpload.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
 
/**
* 健康记录  添加
*/
function show_health_status(){
	$("[data-btn='health_status']").text("添加");
	$("[data-btn='health_status']").on("click",function(){
		if($(this).hasClass('limits_gray'))
		{
			return;
		}
		var $self = $(this);
		var _name= $self.attr("data-name");
		var _url = Constants.sopEndpointURL + '/galaxy/health/toaddhealth';
		$.getHtml({
			url:_url,
			data:"",
			okback:function(){
				$("#popup_name").html(_name);
				$("#health_form [name='projectId']").val(proid);
			}
		});
		return false;
	});
}
function save_health(){
	var content = JSON.parse($("#health_form").serializeObject());
	var _url =  Constants.sopEndpointURL + '/galaxy/health/addhealth'
	sendPostRequestByJsonObj(_url, content, function(data){
		if (data.result.status=="OK") { 
			layer.msg("保存成功");	
			$.popupTwoClose();
			//init_bootstrapTable('project_health_table',5);
			$("#project_health_table").bootstrapTable('refresh',{
			    query: 
			    {
			    	projectId:projectId, 
			    }
			});
			//启用滚动条
			 $(document.body).css({
			   "overflow-x":"auto",
			   "overflow-y":"auto"
			 });
			//刷新投后运营简报信息
			setThyyInfo();
			
			$("#project_delivery_table").bootstrapTable('refresh',{
			    query: 
			    {
			    	projectId:projectId, 
			    }
			});
		} else {
			layer.msg(data.result.message);
		}
	});
	
}
show_health_status();
var parm ={
    	projectId:projectId, 
};
init_bootstrapTable('project_health_table',5 ,parm);
var isflag= "${fx:hasRole(4)}";
function editor(value,row,index){
	var btns = "";
	if(isCreatedByUser == "true" && isTransfering != 'true')
	{
		btns += '<a class="health_edit blue" onclick="health_edit('+row.id+')"  href="javascript:void(0)">编辑</a>  ';
		btns += '<a class="health_delete blue" onclick="health_delete('+row.id+')"  href="javascript:void(0)">删除</a>  ';
		btns += '<a class="health_look blue" onclick="health_look('+row.id+')"  href="javascript:void(0)">查看</a>  '
	}
	return btns;
}
var _url = Constants.sopEndpointURL + '/galaxy/health/toaddhealth';
function health_edit(id){
	var _name="编辑健康度"
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#popup_name").html(_name);
			$("#health_form [name='projectId']").val(proid);
			var reqUrl=Constants.sopEndpointURL+'/galaxy/health/getDetail/'+id;
			sendGetRequest(reqUrl,null,function(data){
				var result=data.result.status;
				if(result=="OK"){
				 var obj=data.entity;
				
				}
				
				
				
			})
		}
	});
	
}

function health_delete(id){
	
	var reqUrl=Constants.sopEndpointURL + '/galaxy/health/deleteDetail/'+id;
	sendGetRequest(reqUrl,null,function(data){
		var result=data.result.status;
		
		
	})
	
}

function health_look(id){
	var _name="查看健康度"
	$.getHtml({
		url:_url,
		data:"",
		okback:function(){
			$("#popup_name").html(_name);
			$("#health_form [name='projectId']").val(proid);
			var reqUrl=Constants.sopEndpointURL+'/galaxy/health/getDetail/'+id;
			sendGetRequest(reqUrl,null,function(data){
				var result=data.result.status;
				
				
			})
		}
	});
}

	

	

</script>
