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






//编辑框初始化
function umInit(){
	var um = UM.getEditor('viewNotes');
	try {
		um.setContent("");
	} catch (e) {
		return;
	}
}

//plupload上传对象初始化,   绑定保存
function initUpload() {
	// 定义 上传插件 方法 、  plupload 上传对象初始化
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], // you can pass in id...
		url : "/galaxy/project/progress/addFileInterview",
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
				$("#saveInterView").click(function(){
					var file = $("#fileName").val();
					if(file.length > 0){
						uploader.start();
					}else{
						saveInterView();
					}
					//传到后台的参数
					//uploader.multipart_params = { id : "12345" };
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
					return false;
				}
				alert("保存成功");
				location.reload(true);s
				
				/*$("#popTxt input[name='fileKey']").val(result.fileKey);
				$("#popTxt input[name='fileLength']").val(result.fileLength);
				$form = $("#popTxt #upload-form");
				
				//表单数据 json 格式化
				var data = JSON.parse($form .serializeObject());
				var url = ""+platformUrl.tempSave
				
				sendPostRequestByJsonObj( url, data, function(data){ alert("上传成功."); loadTempList(); } );*/
			},
			BeforeUpload:function(up){
				//表单函数提交
				//alert(JSON.stringify(getSaveCondition()));
				up.settings.multipart_params = getSaveCondition();
			},
			Error: function(up, err) {
				alert("错误"+err);
				//document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
			}
		}
	});

	uploader.init();
}



//保存访谈记录
function saveInterView(){
	var	condition =  getSaveCondition();
	if(condition == false || condition == "false"){
		return;
	}
	alert("no file save");
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
	location.reload(true);
}




//验证获取保存参数
function getSaveCondition(){
	
	var	condition = {};
	
	var projectId = $("#projectId").val();
	var viewDateStr = $("#viewDate").val();
	var viewTarget = $.trim($("#viewTarget").val());
	//var viewNotes = $.trim($("#viewNotes").val());
	var um = UM.getEditor('viewNotes');
	var viewNotes = $.trim(um.getContent());
	
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

