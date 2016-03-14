$(function () {
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:10,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
	});
});

function tiggerTable(e,pageSize){
	e.bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:pageSize,
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
	});
}




//移除第一层弹框
function removePop1(){
	$(".pop").remove();
	$("#popbg ").remove();
}


//移除第二层弹框
function removePop2(){
	$(".popzx").remove();
}


//检查录入数据 字节 长度
function getLength(val){
	var len = 0;
	for (var i = 0; i < val.length; i++) {
		if (val.charCodeAt(i) >= 0x4e00 && val.charCodeAt(i) <= 0x9fa5){ 
			len += 2;
		}else {
			len++;
		}
	}
	return len;
}



/**
 * 获取 访谈表格数据，返回 jsonObj 对象
 * 
 * @param hasProid
 *            是否传入项目id值，'y':是
 * @param projectId
 *            传入项目id值， 或选择器id
 * @param viewDateId
 *            时间id
 * @param viewTargetId
 *            目标id
 * @param viewNotesId
 *            记录id
 */
function getInterViewCondition(hasProid,projectId,
		viewDateId,
		viewTargetId,
		viewNotesId){
	var	condition = {};
	
	if(hasProid == "y" ){
		var projectId = $.trim(projectId);
	}else{
		var projectId = $("#"+projectId).val();
	}
	var viewDateStr = $("#"+viewDateId).val();
	var viewTarget = $.trim($("#"+viewTargetId).val());
	var um = UM.getEditor(viewNotesId);
	var viewNotes = $.trim(um.getContent());
	
	if(projectId == null || projectId == ""){
		alert("项目不能为空");
		return false;
	}
	if(viewDateStr == null ||  viewDateStr == ""){
		alert("日期不能为空");
		return false;
	}
	if(viewTarget == null ||  viewTarget == ""){
		alert("对象不能为空");
		return false;
	}else{
		if(getLength(viewTarget) > 50){
			alert("对象长度最大50字节");
			return false;
		}
	}
	if(viewNotes == null || viewNotes== ""){
		alert("记录不能为空");
		return false;
	}else{
		if(getLength(viewTarget) > 500){
			alert("记录长度最大500字节");
			return false;
		}
	}
	
	if(fileId != null && fileId!= ""){
		condition.fileId = fileId;
	}
	
	condition.projectId = projectId;
	condition.viewDateStr = viewDateStr;
	condition.viewTarget = viewTarget;
	condition.viewNotes = viewNotes;
	
	return condition;
}




/**
 * 获取 会议表格数据，返回 jsonObj 对象
 * 
 * @param hasProid
 *            是否传入项目id值，'y':是
 * @param projectId
 *            传入项目id值， 或选择器id
 * @param meetDateId
 *            时间id
 * @param hasMeetType
 *            是否传入会议类型值，'y':是
 * @param meetTypeId
 *            传入会议类型值， 或选择器id    
 * @param meetResultId
 *            结果id
 * @param mmeetNotesId
 *            记录id
 */
//验证获取参数
function getMeetCondition(hasProid,projectId,
		meetDateId,
		hasMeetType,meetTypeId,
		meetResultId,
		mmeetNotesId
		){
	var	condition = {};
	
	if(hasProid == "y" ){
		var projectId = $.trim(projectId);
	}else{
		var projectId = $("#"+projectId).val();
	}
	var meetingDateStr = $.trim($("#"+meetDateId).val());
	if(hasMeetType == "y" ){
		var meetingType = $.trim(meetTypeId);
	}else{
		var meetingType = $.trim($('input:radio[name="'+meetTypeId+'"]:checked').val());
	}
	var meetingResult = $.trim($('input:radio[name="'+meetResultId+'"]:checked').val());
	var um = UM.getEditor('mmeetNotesId');
	var meetingNotes = $.trim(um.getContent());
	
	if(projectId == null || projectId == ""){
		alert("项目不能为空");
		return false;
	}
	
	if(meetingDateStr == null ||  meetingDateStr == ""){
		alert("日期不能为空");
		return false;
	}
	
	if(meetingType == null ||  meetingType == ""){
		alert("类型不能为空");
		return false;
	}
	
	if(meetingResult == null ||  meetingResult == ""){
		alert("结果不能为空");
		return false;
	}
	
	if(meetingNotes == null || meetingNotes== ""){
		alert("记录不能为空");
		return false;
	}else{
		if(getLength(meetingNotes) > 500){
			alert("记录长度最大500字节");
			return false;
		}
	}
	
	condition.projectId = projectId;
	condition.meetingDateStr = meetingDateStr;
	condition.meetingType = meetingType;
	condition.meetingResult = meetingResult;
	condition.meetingNotes = meetingNotes;
	return condition;
}



//interview table format
function intervierInfoFormat(value, row, index){
	var fileinfo = "" ;
	var rc = "";
	if( row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	rc = "<div style=\"text-align:left;margin-left:20%;\">"+
				"访谈日期："+row.viewDateStr+
				"</br>访谈对象："+row.viewTarget+
				"</br>访谈录音："+fileinfo+
			"</div>" ;
	return rc;
}


//meet table format
function meetInfoFormat(value, row, index){
	var fileinfo = "";
	var rc = "";
	if(row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	rc = "<div style=\"text-align:left;margin-left:20%;\">"+
				"会议日期："+row.meetingDateStr+
				"</br>会议结论："+row.meetingResultStr+
				"</br>会议录音："+fileinfo+
			"</div>" ;
	return rc;
}

//meet table format
function meetProInfoFormat(value, row, index){
	return row.proName+"</br>"+row.meetingTypeStr;
}




























