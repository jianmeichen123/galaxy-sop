/**
 * 发送post请求
 * 
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonObj
 *            请求json对象
 *@param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendPostRequestByJsonObj(reqUrl, jsonObj, callbackFun) {
	$.ajax({
		url : reqUrl,
		type : "POST",
		data : JSON.stringify(jsonObj),
		dataType : "json",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		async : false,
		error : function(request) {
			//alert("connetion error");
		},
		success : function(data) {
			if(data.hasOwnProperty("result")&&data.result.errorCode=="3"){
				location.href = platformUrl.toLoginPage;
			}
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}

/**
 * 发送post请求
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonStr
 *            请求json字符串
 * @param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendPostRequestByJsonStr(reqUrl, jsonStr, callbackFun) {
	sendPostRequestByJsonObj(reqUrl, JSON.parse(jsonStr), callbackFun);
}

/**
 * 发送get请求
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonObj
 *            请求json对象
 * @param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendGetRequest(reqUrl, jsonObj, callbackFun) {
	$.ajax({
		url : reqUrl,
		type : "GET",
		data : jsonObj,
		dataType : "json",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		async : false,
		error : function(request) {
			//alert("connetion error");
		},
		success : function(data) {
			
			if(data.hasOwnProperty("result")&&data.result.errorCode=="3"){
				location.href = platformUrl.toLoginPage;
			}
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}

/**
 * 发送post请求,不带json数据
 * 
 * @param reqUrl
 *            请求地址
 * @param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendPostRequest(reqUrl, callbackFun) {
	$.ajax({
		url : reqUrl,
		type : "POST",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		async : false,
		error : function(request) {
			//alert("connetion error");
		},
		success : function(data) {
			if(data.hasOwnProperty("result")&&data.result.errorCode=="3"){
				location.href = platformUrl.toLoginPage;
			}
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}



/**
 * url:统一跳转url  
 * 
 */
function forwardWithHeader(url){
	if(url.indexOf("?")==-1){
		window.location.href = url+"?sid="+sessionId+"&guid="+userId;
	}else{
		window.location.href = url+"&sid="+sessionId+"&guid="+userId;
	}
}





/**
 * 将序列化参数字符串转为json格式
 */
$.fn.serializeObject = function(){
    var obj = {};
    var count = 0;
    $.each( this.serializeArray(), function(i,o){
        var n = o.name, v = o.value;
        count++;
        obj[n] = obj[n] === undefined ? v
        : $.isArray( obj[n] ) ? obj[n].concat( v )
        : [ obj[n], v ];
    });
    obj.nameCounts = count + "";//表单name个数
    return JSON.stringify(obj);
};

/**
 * 使用localstage存储数据 <br/>
 * 注意：IE、Firefox测试的时候需要把文件上传到服务器上（或者localhost），直接点开本地的HTML文件，是不行的。
 * 
 * DataStrore.
 */
DataStrore = {
	storage : window.localStorage,
	checkBrowerSupport : function() {
		if (window.localStorage) {
			alert('This browser supports localStorage');
			return true;
		} else {
			alert('This browser does not support localStorage');
			return false;
		}
	},
	addElement : function(key, value) {
		dataStrore.stroage.setItem(key, value);
	},
	getElement : function(key) {
		dataStrore.stroage.getItem(key);
	},
	removeElement : function(key) {
		dataStrore.stroage.removeItem(key);
	},
	removeAll : function() {
		dataStrore.stroage.clear();
	},
	showKeysAndValues : function() {
		for (var i = 0; i < dataStrore.storage.length; i++) {
			document.write(storage.key(i) + " : "
					+ storage.getItem(storage.key(i)) + "<br>");
		}
	}
}
Number.prototype.toDate = function(){
	return new Date(this);
}

Date.prototype.format = function(fmt){
	var o = {
			"M+" : this.getMonth()+1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth()+3)/3),
			"S"  : this.getMilliseconds()
		  };   
		  if(/(y+)/.test(fmt))
		  {
			  fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		  }
		  for(var k in o)
		  {
			  if(new RegExp("("+ k +")").test(fmt))
			  {
				  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			  }
		  }
		  return fmt;   
}


/**
 * 文件上传
 * 参数：
 * fileurl-上传文件的后台接口地址
 * selectBtnId-选择文件的button的id
 * fileInputId-type="file"的input的id
 * submitBtnId-点击上传的按钮的id
 * paramsFunction-获取其他表单值得函数
 * 注意：
 * 1.再引入plupload.full.min.js后，一定要在页面加载时就初始化调用该函数
 */
function toinitUpload(fileurl,pid,selectBtnId,fileInputId,submitBtnId,paramsFunction) {
	//上传对象初始化
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#" + selectBtnId)[0], // you can pass in id...
		url : fileurl,
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
				$("#" + submitBtnId).click(function(){
					var file = $("#" + fileInputId).val();
					uploader.start();
					//传到后台的参数
					//uploader.multipart_params = { id : "12345" };
					return false;
				});
			},
			//添加上传文件后，把文件名 赋值 给 input
			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					/*document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';*/
					$("#" + fileInputId).val(file.name);
				});
			},
			//上传进度
			UploadProgress: function(up, file) {},
			//文件上传后， 返回值  赋值,  再ajax 保存入库
			FileUploaded: function(up, files, rtn) {
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					alert("error "+response.result.message);
					return false;
				}
				
				layer.msg("保存成功");
				$("#powindow,#popbg").remove();
				info(pid);
				
				//location.reload(true);
				
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
				up.settings.multipart_params = paramsFunction();
			},
			Error: function(up, err) {
				alert("错误"+err);
			}
		}
	});
	uploader.init();
}
//紧急任务
function totalUrgent() {
	sendGetRequest(platformUrl.totalUrgent, null, totalUrgentCallback);
}
//待办任务
function totalMission() {
	sendGetRequest(platformUrl.totalMission, null, totalMissionCallback);
}
function totalUrgentCallback(data) {
	var total = 0 ;
	if (data.total != null) {
		total =data.total;
	}
	$('.bubble').html(total);
}

function totalMissionCallback(data) {
	var total = 0 ;
	if (data.total != null) {
		total =data.total;
	}
	$('.totalUrgent').html(total)
}

function fillHeaderdata() {
	//totalUrgent();
	    totalMission();
	/*setInterval(function() {
		totalUrgent();
	    totalMission();

	}, 300000);*/
}








//附件点击下载
function filedown(fileid , filekey){
	try {
		var url = platformUrl.downLoadFile+"/"+fileid;
		window.location.href=url+"?sid="+sessionId+"&guid="+userId;
	} catch (e) {
		console.log(e);
		alert("下载失败");
	}
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
 * @param meetTypeName
 *            传入会议类型值， 或radio name    
 * @param meetResultName
 *            结果radio name
 * @param meetNotesId
 *            记录id
 */
//验证获取参数
function getMeetCondition(hasProid,projectId,
		meetDateId,
		hasMeetType,meetTypeName,
		meetResultName,
		meetNotesId
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
		var meetingType = $.trim($('input:radio[name="'+meetTypeName+'"]:checked').val());
	}
	var meetingResult = $.trim($('input:radio[name="'+meetResultName+'"]:checked').val());
	var um = UM.getEditor(meetNotesId);
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

