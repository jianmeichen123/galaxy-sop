$(function(){

	createMenus(7);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		
	});
	
});

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




//plupload上传对象初始化,   绑定保存
function initUpload() {
	// 定义 上传插件 方法 、  plupload 上传对象初始化
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], // you can pass in id...
		url : "/galaxy/project/progress/addfilemeet",
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '10mb',
			mime_types: [
			    {title : "YP files", extensions : "mp3,avi"},
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip,rar"},
				{title : "Offices files", extensions : "doc,docx,excel"}
			]
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function() {
				$("#savemeet").click(function(){
					var file = $("#fileName").val();
					if(file.length > 0){
						uploader.start();
					}else{
						saveMeet();
					}
					return false;
				});
			},
			
			//添加上传文件后，把文件名 赋值 给 input
			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					/*document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';*/
					$("#fileName").val(file.name);
				});
			},
			
			//上传进度
			UploadProgress: function(up, file) {
			},
			
			//文件上传后， 返回值  赋值,  再ajax 保存入库
			FileUploaded: function(up, files, rtn) {
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					alert("error "+data.result.message);
					return;
				}
				alert("保存成功");
				location.reload(true);
			},
			BeforeUpload:function(up){
				//表单函数提交
				//alert(JSON.stringify(getMeetCondition()));
				up.settings.multipart_params = getMeetCondition();
			},
			Error: function(up, err) {
				alert("错误"+err);
				//document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
			}
		}
	});

	uploader.init();
}




