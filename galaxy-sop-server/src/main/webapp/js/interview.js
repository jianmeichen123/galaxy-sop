

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
		layer.msg(data.result.message);
		removePop1();
		return;
	}
	var entityList = data.entityList;
	if(entityList.length == 0 ){
		layer.msg("无相关项目可添加记录");
		removePop1();
		$(body).css("overflow-y","auto");
		return;
	}else{
		for(var i=0;i<data.entityList.length;i++){
			if(typeof(transferingIds) != 'undefined' && transferingIds.contains(data.entityList[i].id))
			{
				continue;
			}
	    	$("#projectId").append("<option value='"+data.entityList[i].id+"'>"+data.entityList[i].projectName+"</option>");
	    } 
	}
}




function initViewAliOss(){
	data = {
		mustHasFile : false,
		chooseBtn : "file-select-btn",
		uploadBtn : "saveInterView",
		fileShowText : "fileName",
		mimeTypes : paramsFilter(1),
		localUrl : platformUrl.saveViewFile,   //ossUrl
		ossUrl : platformUrl.saveViewFile,
		noFileOper : function(jsonObj){
			sendPostRequestByJsonObj(platformUrl.saveViewFile,jsonObj,function(data){
				var result = data.result.status;
				if(result == "ERROR"){ //OK, ERROR
					$("#saveInterView").removeClass("disabled");
					layer.msg(data.result.message);
					return;
				}else{
					layer.msg("保存成功", {time : 500});
					var _this = $("#data-table");
					if(_this == null || _this.length == 0 || _this == undefined){
						removePop1();
					}else{
						$("#data-table").bootstrapTable('refresh');
						removePop1();
					}
				}
			});
		},
		validateForm : function(){ return getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");  },
		getLocalFormParam : function(up,file,fileKey){
			var res = getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");
			if(res == false || res == "false"){
				$("#saveInterView").removeClass("disabled");
				return false;
			}
			return res;
		},
		getOssFormParam : function(up,file,fileKey){ //文件上传后，获取参数
			var res = getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");
			if(res == false || res == "false"){
				$("#saveInterView").removeClass("disabled");
				return false;
			}
			res["fname"] = file.name;
			res["fileLength"] = file.size;
			res["fkey"] = fileKey
			return res;
		},
		successCallBack:function(up, file, result){
			var response = $.parseJSON(result.response);
			var rs = response.result.status;
			if(rs == "ERROR"){ //OK, ERROR
				$("#saveInterView").removeClass("disabled");
				$("#fileName").val("");
				viewuploader.splice(0, meetuploader.files.length)
				layer.msg(response.result.message);
				return false;
			}else{
				layer.msg("保存成功", {time : 500});
				var _this = $("#data-table");
				if(_this == null || _this.length == 0 || _this == undefined){
					removePop1();
				}else{
					$("#data-table").bootstrapTable('refresh');
					removePop1();
				}
			}
		}
	}
	ossClient.uploadInit(data)
}



//plupload上传对象初始化,   绑定保存
function initViewUpload() {
	var viewuploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], 
		url : platformUrl.saveViewFile,
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter(1)
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				$("#saveInterView").click(function(){
					$("#saveInterView").addClass("disabled");
					var res = getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");
					if(res == false || res == "false"){
						up.stop();
						$("#saveInterView").removeClass("disabled");
						return;
					}
					
					var file = $("#fileName").val();
					if(file.length > 0){
						up.settings.multipart_params = res;  //viewuploader.multipart_params = { id : "12345" };
						viewuploader.start();
					}else{
						sendPostRequestByJsonObj(platformUrl.saveViewFile,res,function(data){
							var result = data.result.status;
							if(result == "ERROR"){ //OK, ERROR
								$("#saveInterView").removeClass("disabled");
								layer.msg(data.result.message);
								return;
							}else{
								layer.msg("保存成功", {time : 500});
								var _this = $("#data-table");
								if(_this == null || _this.length == 0 || _this == undefined){
									removePop1();
								}else{
									$("#data-table").bootstrapTable('refresh');
									removePop1();
								}
								//启用滚动条
								 $(document.body).css({
								   "overflow-x":"auto",
								   "overflow-y":"auto"
								 });
							}
						});
					}
					return false;
				});
			},
			
			FilesAdded: function(up, files) {
				if(viewuploader.files.length >= 2){
					viewuploader.splice(0, viewuploader.files.length-1)
				}
				plupload.each(files, function(file) {
					$("#fileName").val(file.name);
				});
			},
			
			UploadProgress: function(up, file) { 
			},
			
			FileUploaded: function(up, files, rtn) {  //上传回调
				$("#powindow").hideLoading();
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					$("#saveInterView").removeClass("disabled");
					$("#fileName").val("");
					viewuploader.splice(0, meetuploader.files.length)
					layer.msg(response.result.message);
					return false;
				}else{
					layer.msg("保存成功", {time : 500});
					var _this = $("#data-table");
					if(_this == null || _this.length == 0 || _this == undefined){
						removePop1();
					}else{
						$("#data-table").bootstrapTable('refresh');
						removePop1();
					}
				}
			},
			
			BeforeUpload:function(up){
				$("#powindow").showLoading(
						 {
						    'addClass': 'loading-indicator'						
						 });
			},
			
			Error: function(up, err) {
				$("#powindow").hideLoading();
				$("#saveInterView").removeClass("disabled");
				$("#fileName").val("");
				layer.msg(err.message);
			}
		}
	});

	viewuploader.init();
}








//保存访谈记录
function saveInterView(){
	var	condition =  getInterViewCondition(null,"projectId", "viewDate", "viewTarget", "viewNotes");
	if(condition == false || condition == "false"){
		//$("#saveInterView").removeClass("disabled");
		return;
	}
	sendPostRequestByJsonObj(platformUrl.saveView,condition,saveCallBack);
}


//保存成功回调
function saveCallBack(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		//$("#saveInterView").removeClass("disabled");
		layer.msg(data.result.message);
		return;
	}
	layer.msg("保存成功", {
		time : 500
	});
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









