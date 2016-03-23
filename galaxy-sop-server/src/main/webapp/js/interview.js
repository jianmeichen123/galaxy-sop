

//访谈记录查询个人项目
function queryViewPerPro(){
	var condition = {};
	
	//模糊查询proName
	var proName = $.trim($("#proName").val());
	if(proName != null && proName != ""){
		condition.nameLike = proName;
	}
	//condition.progress = "projectProgress:1";
	sendGetRequest(platformUrl.getUserPro,condition,setViewProSelect);
	//sendPostRequestByJsonObj("http://127.0.0.1:8080/galaxy/project/progress/queryPerProPage",{pageNum:0,pageSize:5},setProSelect);
}


//设置项目下拉框
function setViewProSelect(data){
	
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert(data.result.message);
		removePop1();
		return;
	}
	
	var entityList = data.entityList;
	if(entityList.length == 0 ){
		alert("无相关项目可添加记录");
		removePop1();
		return;
	}else{
		for(var i=0;i<data.entityList.length;i++){
	    	$("#projectId").append("<option value='"+data.entityList[i].id+"'>"+data.entityList[i].projectName+"</option>");
	    } 
	}
}


//plupload上传对象初始化,   绑定保存
function initViewUpload() {
	// 定义 上传插件 方法 、  plupload 上传对象初始化
	var viewuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], // you can pass in id...
		url : platformUrl.saveViewFile,
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
				$("#saveInterView").click(function(){
					var file = $("#fileName").val();
					if(file.length > 0){
						viewuploader.start();
					}else{
						saveInterView();
					}
					//传到后台的参数
					//viewuploader.multipart_params = { id : "12345" };
					return false;
				});
			},
			
			//添加上传文件后，把文件名 赋值 给 input
			FilesAdded: function(up, files) {
				if(viewuploader.files.length >= 2){
					viewuploader.splice(0, viewuploader.files.length-1)
				}
				plupload.each(files, function(file) {
					//document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
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
					return false;
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
				//alert(JSON.stringify(getSaveCondition()));
				var res = getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");
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

	viewuploader.init();
}



//保存访谈记录
function saveInterView(){
	var	condition =  getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");
	if(condition == false || condition == "false"){
		return;
	}
	sendPostRequestByJsonObj(platformUrl.saveView,condition,saveCallBack);
}


//保存成功回调
function saveCallBack(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert(data.result.message);
		return;
	}
	alert("保存成功");
	//$("#popbg,#pop").remove();
	var _this = $("#data-table");
	if(_this == null || _this.length == 0 || _this == undefined){
		removePop1();
	}else{
		$("#data-table").bootstrapTable('refresh');
		removePop1();
	}
	//location.reload(true);
}









