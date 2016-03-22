

//查询个人项目
function queryMeetPerPro(){
	var condition = {};
	
	//模糊查询proName
	var proName = $.trim($("#proName").val());
	if(proName != null && proName != ""){
		condition.nameLike = proName;
	}
	
	/*name 赋值 和 主页面 重了*/
	 var meetingType=$('input:radio[name="meetingTypeTc"]:checked').val();
	if(meetingType!=null && meetingType!=""){
		condition.meetingType = meetingType;
	}
	
	sendGetRequest(platformUrl.getUserPro,condition,setMeetProSelect);
}


//设置项目下拉框
function setMeetProSelect(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert(data.result.message);
		$(".pop").remove();
		$("#popbg").remove();	
		return;
	}
	
	var entityList = data.entityList;
	if(entityList.length == 0 ){
		alert("无相关项目可添加记录");
		$(".pop").remove();
		$("#popbg").remove();	
		return;
	}else{
		for(var i=0;i<data.entityList.length;i++){
	    	$("#projectId").append("<option value='"+data.entityList[i].id+"'>"+data.entityList[i].projectName+"</option>");
	    } 
	}
}



/*<dt>会议类型：</dt>
<dd class="clearfix">
    <label><input type="radio" name="meetingTypeTc" value="meetingType:1"/>内评会</label>
    <label><input type="radio" name="meetingType" value="meetingType:2"/>CEO评审</label>
    <label><input type="radio" name="meetingType" value="meetingType:3"/>立项会</label>
    <label><input type="radio" name="meetingType" value="meetingType:4"/>投决会</label>
</dd>*/

//get meettype radios, parentCode 
// Map<parentCode,Map<code,name>>
function getMeetTypes(){
	sendPostRequestByJsonObj(platformUrl.saveMeet,condition,setMeetTypes);
}
function setMeetTypes(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		alert(data.result.message);
		return;
	}
	var mapcodename = data.result.message;
}



//保存记录
function saveMeet(){
	var	condition = getMeetCondition(null,"projectId", "meetingDateStr", 
			null,"meetingTypeTc", "meetingResult","meetingNotes");
	if(condition == false || condition == "false"){
		return;
	}
	sendPostRequestByJsonObj(platformUrl.saveMeet,condition,saveMeetCallBack);
}


//保存成功回调
function saveMeetCallBack(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert(data.result.message);
		return;
	}
	alert("保存成功");
	var _this = $("#data-table");
	if(_this == null || _this.length == 0 || _this == undefined){
		removePop1();
	}else{
		$("#data-table").bootstrapTable('refresh');
		removePop1();
	}
	//location.reload(true);
}




//plupload上传对象初始化,   绑定保存
function initMeetUpload() {
	// 定义 上传插件 方法 、  plupload 上传对象初始化
	var meetuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], // you can pass in id...
		url : platformUrl.saveMeetFile,
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: [
			    {title : "audio files", extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r"}
			]
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function() {
				$("#savemeet").click(function(){
					var file = $("#fileName").val();
					if(file.length > 0){
						meetuploader.start();
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
					alert(response.result.message);
					return;
				}
				alert("保存成功");
				var _this = $("#data-table");
				if(_this == null || _this.length == 0 || _this == undefined){
					removePop1();
				}else{
					$("#data-table").bootstrapTable('refresh');
					removePop1();
				}
				//location.reload(true);
			},
			BeforeUpload:function(up){
				//表单函数提交
				//alert(JSON.stringify(getMeetCondition()));
				var res = getMeetCondition(null,"projectId", "meetingDateStr", 
						null,"meetingTypeTc", "meetingResult","meetingNotes");
				if(res == false || res == "false"){
					up.stop();
					return;
				}
				up.settings.multipart_params = res;
			},
			Error: function(up, err) {
				alert("错误"+err);
				//document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
			}
		}
	});

	meetuploader.init();
}





