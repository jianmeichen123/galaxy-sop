$(function(){

	createMenus(6);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		
	});
	
});
//访谈记录查询个人项目
function queryPerPro(){
	var condition = {};
	
	//模糊查询proName
	var proName = $.trim($("#proName").val());
	if(proName != null && proName != ""){
		condition.nameLike = proName;
	}
	
	condition.progress = "projectProgress:1";
	sendGetRequest(platformUrl.getUserPro,condition,setProSelect,null);
}


//设置项目下拉框
function setProSelect(data){
	
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		$("#popbg,#pop").remove();
		return;
	}
	
	var entityList = data.entityList;
	
	if(entityList.length == 0 ){
		alert("无相关项目可添加记录");
		$("#popbg,#pop").remove();
		return;
	}else{
		for(var i=0;i<data.entityList.length;i++){
	    	$("#projectId").append("<option value='"+data.entityList[i].id+"'>"+data.entityList[i].projectName+"</option>");
	    } 
	}
	
}


//保存访谈记录
function saveInterView(){
	var	condition =  getSaveCondition();
	if(condition == false || condition == "false"){
		return;
	}
	
	sendPostRequestByJsonObj(platformUrl.saveInteverView,condition,saveCallBack,null);
}


//保存成功回调
function saveCallBack(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		
		return;
	}
	
	alert("保存成功");
	$("#popbg,#pop").remove();
}




//验证获取保存参数
function getSaveCondition(){
	
	var	condition = {};
	
	var projectId = $("#projectId").val();
	var viewDateStr = $("#viewDate").val();
	var viewTarget = $.trim($("#viewTarget").val());
	var viewNotes = $.trim($("#viewNotes").val());
	var fileId = $("#viewfileID").val();
	
	if(projectId == null || projectId == ""){
		alert("项目不能为空");
		return false;
	}else{
		condition.projectId = projectId;
	}
	
	if(viewDateStr == null ||  viewDateStr == ""){
		alert("日期不能为空");
		return false;
	}else{
		condition.viewDateStr = viewDateStr;
	}
	
	if(viewTarget == null ||  viewTarget == ""){
		alert("对象不能为空");
		return false;
	}else{
		condition.viewTarget = viewTarget;
	}
	
	if(viewNotes != null && viewNotes!= ""){
		condition.viewNotes = viewNotes;
	}
	
	if(fileId != null && fileId!= ""){
		condition.fileId = fileId;
	}
	/*var	condition = {
		"projectId" : projectId,
		"viewDate" : viewDate,
		"viewTarget" : viewTarget,
		"viewNotes" : viewNotes,
		"fileId" : fileId
	};*/
	
	return condition;
}



//查询
function selectViewPage(){
	$("#interVierTable").bootstrapTable('refresh',
		{
	      url : platformUrl.selectViewPage,
	      queryParams:getQueryCondition
		}
	);
}


//验证获取保存参数
function getQueryCondition(){
	
	var	condition = {};
	
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var proNameCode = $.trim($("#proNameCode").val());

	
	if(startTime != null && startTime!= ""){
		condition.startTime = startTime;
	}
	
	if(endTime != null && endTime!= ""){
		condition.endTime = endTime;
	}
	
	if(proNameCode != null && proNameCode!= ""){
		condition.proNameCode = proNameCode;
	}

	
	return condition;
}


//table format
function dateFormat(value, row, index){
	return "<fmt:formatDate value='"+value+"' pattern='yyyy-MM-dd'/>";
}
function fileFormat(value, row, index){
	return "";
}

