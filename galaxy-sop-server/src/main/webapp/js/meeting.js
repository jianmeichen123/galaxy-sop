


//会议类型切换事件
function meetTypeValChangeFun(){
	$(".tip-yellowsimple").remove();
	var meetingResultVal = $('input:radio[name="meetingResult"]:checked').val();
	
	var meetingTypeVal = $('input:radio[name="meetingTypeTc"]:checked').val();
	
	if(meetingTypeVal =='meetingType:4' && meetingResultVal =='meetingResult:1'){
		$("#finalContribution").attr({allowNULL:"no",valtype:"LIMIT_11_NUMBER",msg:"<font color=red>*</font>不能为空且最大支持四位小数"});
		$("#finalValuations").attr({allowNULL:"no",valtype:"LIMIT_11_NUMBER",msg:"<font color=red>*</font>不能为空且最大支持四位小数"});
		$("#finalShareRatio").attr({allowNULL:"no",valtype:"OTHER",msg:"<font color=red>*</font>0到100之间的四位小数"});
		$("#serviceCharge").attr({allowNULL:"no",valtype:"OTHER",msg:"<font color=red>*</font>0到100之间的四位小数"});
		
		$('.toShow_tjh').show();
	}else{
		$("#finalContribution").removeAttr('allowNULL').removeAttr('valType').removeAttr('msg');
		$("#finalValuations").removeAttr('allowNULL').removeAttr('valType').removeAttr('msg');
		$("#finalShareRatio").removeAttr('allowNULL').removeAttr('valType').removeAttr('msg');
		$("#serviceCharge").removeAttr('allowNULL').removeAttr('valType').removeAttr('msg');
		
		$('.toShow_tjh').hide();
	}
}



//查询个人项目
function queryMeetPerPro(){
	var condition = {};
	
	//模糊查询proName
	var proName = $.trim($("#proName").val());
	if(proName != null && proName != ""){
		condition.nameLike = proName;
	}
	
	/*name 赋值 和 主页面 重了
	 var meetingType=$('input:radio[name="meetingTypeTc"]:checked').val();
	if(meetingType!=null && meetingType!=""){
		condition.meetingType = meetingType;
	}*/
	
	sendGetRequest(platformUrl.getUserPro,condition,setMeetProSelect);
}


//设置项目下拉框
function setMeetProSelect(data){
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		$(".pop").remove();
		$("#popbg").remove();	
		return;
	}
	
	var entityList = data.entityList;
	if(entityList.length == 0 ){
		layer.msg("无相关项目可添加记录");
		$(".pop").remove();
		$("#popbg").remove();
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
		layer.msg(data.result.message);
		return;
	}
	var mapcodename = data.result.message;
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
			mime_types: paramsFilter(1)
		},

		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				$("#savemeet").click(function(){
					$("#savemeet").addClass("disabled");
					var res = getMeetCondition(null,"projectId", "meetingDateStr", null,"meetingTypeTc", "meetingResult","meetingNotes");
					if(res == false || res == "false"){
						up.stop();
						$("#savemeet").removeClass("disabled");
						return;
					}
					
					console.log("menue meet condition :" + JSON.stringify(res));
					
					var file = $("#fileName").val(); //up.files.length
					if(file.length > 0){
						up.settings.multipart_params = res;
						meetuploader.start();
					}else{
						sendPostRequestByJsonObj(platformUrl.saveMeetFile,res,function(data){
							var result = data.result.status;
							if(result == "ERROR"){ //OK, ERROR
								$("#savemeet").removeClass("disabled");
								layer.msg(data.result.message);
								return;
							}else{
								layer.msg("保存成功", {time : 500});
								//启用滚动条
								 $(document.body).css({
								   "overflow-x":"auto",
								   "overflow-y":"auto"
								 });
								var _this = $("#data-table");
								if(_this == null || _this.length == 0 || _this == undefined){
									removePop1();
								}else{
									$("#data-table").bootstrapTable('refresh');
									removePop1();
								}
							}
						});
					}
					return false;
				});
			},
			
			FilesAdded: function(up, files) {
				if(meetuploader.files.length >= 2){
					meetuploader.splice(0, meetuploader.files.length-1)  //解决多次文件选择后，文件都存入upload
				}
				plupload.each(files, function(file) {
					$("#fileName").val(file.name);
				});
			},
			
			UploadProgress: function(up, file) {
			},
			
			
			FileUploaded: function(up, files, rtn) {  //文件上传后回掉
				$("#powindow").hideLoading();
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					$("#savemeet").removeClass("disabled");
					$("#fileName").val("");
					meetuploader.splice(0, meetuploader.files.length)
					layer.msg(response.result.message);
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
			},
			BeforeUpload:function(up){
				$("#powindow").showLoading(
						 {
						    'addClass': 'loading-indicator'						
						 });
			},
			Error: function(up, err) {
				$("#powindow").hideLoading();
				$("#savemeet").removeClass("disabled");
				$("#fileName").val("");
				layer.msg(err.message);
			}
		}
	});

	meetuploader.init();
}






//保存记录
function saveMeet(){
	var	condition = getMeetCondition(null,"projectId", "meetingDateStr", 
			null,"meetingTypeTc", "meetingResult","meetingNotes");
	if(condition == false || condition == "false"){
		//$("#savemeet").removeClass("disabled");
		return;
	}
	sendPostRequestByJsonObj(platformUrl.saveMeet,condition,saveMeetCallBack);
}

//保存成功回调
function saveMeetCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		$("#savemeet").removeClass("disabled");
		layer.msg(data.result.message);
		return;
	}
	layer.msg("保存成功", {time : 500});
	var _this = $("#data-table");
	if(_this == null || _this.length == 0 || _this == undefined){
		removePop1();
	}else{
		$("#data-table").bootstrapTable('refresh');
		removePop1();
	}
	//location.reload(true);
}


