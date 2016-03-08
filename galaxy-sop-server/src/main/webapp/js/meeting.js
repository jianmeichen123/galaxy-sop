$(function(){
	createMenus(7);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
	});
	
});



//编辑框初始化
function umInit(){
	var um = UM.getEditor('meetingNotes');
	try {
		um.setContent("");
	} catch (e) {
		return;
	}
}

//查询个人项目
function queryPerPro(){
	var condition = {};
	
	//模糊查询proName
	var proName = $.trim($("#proName").val());
	if(proName != null && proName != ""){
		condition.nameLike = proName;
	}
	
	var meetingType=$('input:radio[name="meetingType"]:checked').val();
	if(meetingType!=null && meetingType!=""){
		condition.meetingType = meetingType;
	}
	
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


//保存记录
function saveMeet(){
	var	condition =  getMeetCondition();
	if(condition == false || condition == "false"){
		return;
	}
	sendPostRequestByJsonObj(platformUrl.saveMeet,condition,saveMeetCallBack,null);
}


//保存成功回调
function saveMeetCallBack(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		
		return;
	}
	
	alert("保存成功");
	$("#popbg,#pop").remove();
}


//验证获取参数
function getMeetCondition(){
	var	condition = {};
	
	var projectId = $("#projectId").val();
	var meetingDateStr = $.trim($("#meetingDateStr").val());
	var meetingType = $.trim($('input:radio[name="meetingType"]:checked').val());
	var meetingResult = $.trim($('input:radio[name="meetingResult"]:checked').val());
	//var meetingNotes = $.trim($("#meetingNotes").val());
	
	var um = UM.getEditor('meetingNotes');
	var meetingNotes = $.trim(um.getContent());
	
	var fileId = $("#meetfileID").val();
	
	if(projectId == null || projectId == ""){
		alert("项目不能为空");
		return false;
	}else{
		condition.projectId = projectId;
	}
	
	if(meetingDateStr == null ||  meetingDateStr == ""){
		alert("日期不能为空");
		return false;
	}else{
		condition.meetingDateStr = meetingDateStr;
	}
	
	if(meetingType == null ||  meetingType == ""){
		alert("类型不能为空");
		return false;
	}else{
		condition.meetingType = meetingType;
	}
	
	if(meetingResult == null ||  meetingResult == ""){
		alert("结果不能为空");
		return false;
	}else{
		condition.meetingResult = meetingResult;
	}
	
	if(meetingNotes != null && meetingNotes!= ""){
		condition.meetingNotes = meetingNotes;
	}
	
	if(fileId != null && fileId!= ""){
		condition.fileId = fileId;
	}
	
	return condition;
}



