/**
 * 加密Ajax请求
 * jsonStr:json字符串
 * jsonObj:json对象
 */
function sendPostRequestBySignJsonStr(reqUrl, jsonStr, callbackFun, TOKEN) {
	sendPostRequestBySignJsonObj(reqUrl, JSON.parse(jsonStr), callbackFun, TOKEN);
}
function sendPostRequestBySignJsonObj(reqUrl, jsonObj, callbackFun, TOKEN) {
	$.ajax({
		url : reqUrl,
		type : "POST",
		data : b.encode(JSON.stringify(jsonObj)),
		dataType : "text",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			/**清楚浏览器缓存**/
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");

			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
			if(TOKEN){
				xhr.setRequestHeader("TOKEN", TOKEN);
			}
		},
		async : false,
		error : function(request) {},
		success : function(data) {
			data = JSON.parse(b.decode(data));
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}

/**
 * 非加密Ajax请求
 * jsonStr:json字符串
 * jsonObj:json对象
 */
function sendPostRequestByJsonStr(reqUrl, jsonStr, callbackFun) {
	sendPostRequestByJsonObj(reqUrl, JSON.parse(jsonStr), callbackFun);
}
function sendPostRequestByJsonObj(reqUrl, jsonObj, callbackFun) {
	$.ajax({
		url : reqUrl,
		type : "POST",
		data : JSON.stringify(jsonObj),
		dataType : "json",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			/**清楚浏览器缓存**/
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");

			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		async : false,
		error : function(request) {},
		success : function(data) {
			if(data){
				var type =typeof(data);
				if(type=='string'){
					if(data.indexOf("<!DOCTYPE html>")){
						location.href = platformUrl.toLoginPage;
					}
				}
			}
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}


/**
 * 加密Ajax请求
 * jsonObj:json对象
 */
function sendGetRequestByJsonObj(reqUrl, jsonObj, callbackFun) {
	$.ajax({
		url : reqUrl,
		type : "GET",
		data : b.encode(JSON.stringify(jsonObj)),
		dataType : "text",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			/**清楚浏览器缓存**/
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		async : false,
		error : function(request) {},
		success : function(data) {
			data = JSON.parse(b.decode(data));
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}

/**
 * 非加密Ajax请求
 * jsonObj:json对象
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
			/**清楚浏览器缓存**/
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		async : false,
		error : function(request) {},
		success : function(data) {
			if(data){
				var type =typeof(data);
				if(type=='string'){
					if(data.indexOf("<!DOCTYPE html>")){
						location.href = platformUrl.toLoginPage;
					}
				}
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
		/**清楚浏览器缓存**/
	    xhr.setRequestHeader("If-Modified-Since","0"); 
	    xhr.setRequestHeader("Cache-Control","no-cache");
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
			if(data){
				var type =typeof(data);
				if(type=='string'){
					if(data.indexOf("<!DOCTYPE html>")){
						location.href = platformUrl.toLoginPage;
					}
				}
			}
			/*if(data.hasOwnProperty("result")&&data.result.errorCode=="3"){
				location.href = platformUrl.toLoginPage;
			}*/
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
 * fileType-存儲類型select的id
 * 注意：
 * 1.再引入plupload.full.min.js后，一定要在页面加载时就初始化调用该函数
 */
//文档
var fileType_1 = {title : "fileType:1", extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt,pages,key,numbers,DOC,DOCX,PPT,PPTX,PPS,XLS,XLSX,PDF,TXT,PAGES,KEY,NUMBER"};
//音频
var fileType_2 = {title : "fileType:2", extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r,MP3,MP4,AVI,WAV,WMA,AAC,M4A,M4R"};
//视频
var fileType_3 = {title : "fileType:3", extensions : "avi,AVI"};
//图片
var fileType_4 = {title : "fileType:4", extensions : "bmp,jpg,gif,png,jpeg,BMP,JPG,GIF,PNG,JPEG"};
//zip
var fileType_5 = {title : "Zip files", extensions : "zip,rar,ZIP,RAR"};

function paramsFilter(indexNum){
	var filtersparams= new Array();
	if(indexNum!=null){
		//1\2\3\4\7  访谈、会议
		if(indexNum == 1 || indexNum == '1' || indexNum == 2 || indexNum == '2' || indexNum == 3 || indexNum == '3' 
			|| indexNum == 4 || indexNum == '4' || indexNum == 7 || indexNum == '7'){
			filtersparams.push(fileType_2);
		}
	}else{
		filtersparams= new Array(fileType_1,fileType_2,fileType_3,fileType_4);
	}
	return filtersparams;
}

/**
 * 
 * @param file-文件
 * @param fileType-存储类型id
 */
function attrFileType(fileType,file){
	
	var type=getFileExt(file.name);
	var filtersparams=paramsFilter(null);
	for(var i=0;i<filtersparams.length;i++){
		var value=filtersparams[i];
		var valueExt=value.extensions;
		if(valueExt.indexOf(type) >= 0 ){
			var myvalue=value.title;
			//$("#"+fileType+" option[value='"+myvalue+"']").attr("selected",true);
			if(fileType instanceof jQuery){
				fileType.val(myvalue);
			}else{
				$("#"+fileType).val(myvalue);
			}
			
			break;
			
		}
	}
}
/**
 * 获取后缀名
 * @param fileName
 * @returns {String}
 */
function getFileExt(fileName)
{
	var type="";
	var dotIndex = fileName.lastIndexOf(".");
	if(dotIndex >- 1)
	{
		type = fileName.substr(dotIndex+1);
		type = type.toLowerCase()
	}
	return type;
}

/**
 * 根据后缀名确定文档类型
 * @param ext
 * @returns {String}
 */
function getFileTypeByExt(ext)
{
	var type = "";
	var filtersparams = paramsFilter(null);
	for(var i=0;i<filtersparams.length;i++)
	{
		var value = filtersparams[i];
		var valueExt=value.extensions;
		if(valueExt.indexOf(ext) >= 0 ){
			type=value.title;
			break;
		}
	}
	return type;
}
function getFileTypeByName(fileName)
{
	var type = "";
	var ext = getFileExt(fileName);
	if(ext != null && ext != "")
	{
		type = getFileTypeByExt(ext);
	}
	return type;
}

function toinitUpload(fileurl,pid,selectBtnId,fileInputId,submitBtnId,fileType,paramsFunction,indexNum,success) {
	

	
	//上传对象初始化
	var uploader = new plupload.Uploader({
		runtimes : 'html5,html4,flash,silverlight',
		browse_button : $("#" + selectBtnId)[0], // you can pass in id...
		url : fileurl,
		multipart:true,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter(indexNum)
		},
		init: {
			//上传按钮点击事件 - 开始上传
			PostInit: function(up) {
				$("#" + submitBtnId).click(function(){
					/**添加验证**/
					if(beforeSubmit()){
						var file = $("#" + fileInputId).val();
						var param = paramsFunction();
						if(up.files.length == 0){
							sendPostRequestByJsonObj(platformUrl.stageChange,param,function(data){
								var result = data.result.status;
								if(result == "OK"){
									if($.isFunction(success))
									{
										success.call();
									}
									layer.msg(data.result.message);
									$("#powindow,#popbg").remove();
									info(pid);
								}else{
									layer.msg(data.result.message);
								}
								
								//contentType:"multipart/form-data"
							});
						}else{
							up.settings.multipart_params = param;
							uploader.start();
						}
						return false;
					}
				});
			},
			//添加上传文件后，把文件名 赋值 给 input
			FilesAdded: function(up, files) {
				//解决多次文件选择后，文件都存入upload
				if(uploader.files.length >= 1){
					uploader.splice(0, uploader.files.length-1)
				}
				plupload.each(files, function(file) {
					/*document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';*/
					$("#" + fileInputId).val(file.name);
					/***存储类型被选中***/
					if(fileType){
						attrFileType(fileType,file);//定位选中存储类型
					}
					
					
				});
			},
			//上传进度
			UploadProgress: function(up, file) {},
			//文件上传后， 返回值  赋值,  再ajax 保存入库
			FileUploaded: function(up, files, rtn) {
				var response = $.parseJSON(rtn.response);
				var rs = response.result.status;
				if(rs == "ERROR"){ //OK, ERROR
					layer.msg(response.result.message);
					$("div[data-id='popid1']").remove();
					return false;
				}
				if($.isFunction(success))
				{
					success.call();
				}
				layer.msg(response.result.message);
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
			},
			Error: function(up, err) {
				//alert("错误"+err);
				layer.msg("上传格式等错误,请重新选择文件!");
				
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
	   // totalUrgent();
	    totalMission();
}


//附件点击下载
function filedown(fileid , filekey, type){
	try {
		var url = platformUrl.downLoadFile+"/"+fileid;
		var typeparam = "";
		if(typeof(type) != 'undefined')
		{
			typeparam = "&type="+type;
		}
		layer.msg('正在下载，请稍后...',{time:2000});
		window.location.href=url+"?sid="+sessionId+"&guid="+userId+typeparam;
	} catch (e) {
		layer.msg("下载失败");
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
	
	if(!beforeSubmit()){
		return false;
	}
	if(hasProid == "y" ){
		var projectId = $.trim(projectId);
	}else{
		var projectId = $("#"+projectId).val();
	}
	var viewDateStr = $("#"+viewDateId).val();
	var viewTarget = $.trim($("#"+viewTargetId).val());
	var um = UM.getEditor(viewNotesId);
	var viewNotes = $.trim(um.getContent());
	//var notes = $("#"+viewNotesId).text();
	
	if(projectId == null || projectId == ""){
		layer.msg("项目不能为空");
		return false;
	}
	/*if(viewDateStr == null ||  viewDateStr == ""){
		layer.msg("访谈日期不能为空");
		return false;
	}
	else{
		var clock = getNowDay("-");
		if((new Date(viewDateStr)) > (new Date(clock))){
			layer.msg("访谈日期不能超过今天");
			return false;
         }
	 }*/
	
	if(viewTarget == null ||  viewTarget == ""){
		layer.msg("对象不能为空");
		return false;
	}else{
		if(getLength(viewTarget) > 100){
			layer.msg("对象长度最大100字节");
			return false;
		}
	}
	
	if(viewNotes != null && viewNotes.length > 0){
		if(getLength(viewNotes) > 9000){
			layer.msg("访谈记录长度最大9000字符");
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
	
	if(!beforeSubmit()){
		return false;
	}
	
	var projectIdVal = null;
	if(hasProid == "y" ){
		projectIdVal = $.trim(projectId);
	}else{
		projectIdVal = $("#"+projectId).val();
	}
	
	var meetingDateStr = $.trim($("#"+meetDateId).val());
	
	var meetingTypeVal = null;
	if(hasMeetType == "y" ){
		meetingTypeVal = $.trim(meetTypeName);
	}else{
		meetingTypeVal = $.trim($('input:radio[name="'+meetTypeName+'"]:checked').val());
	}
	
	var meetingResult = $.trim($('input:radio[name="'+meetResultName+'"]:checked').val());
	
	var um = UM.getEditor(meetNotesId);
	var meetingNotes = $.trim(um.getContent());
	
	if(projectIdVal == null || projectIdVal == ""){
		layer.msg("项目不能为空");
		return false;
	}
	
	/*if(meetingDateStr == null ||  meetingDateStr == ""){
		layer.msg("会议日期不能为空");
		return false;
	}else{
		var clock = getNowDay("-");
		if((new Date(meetingDateStr)) > (new Date(clock))){
			layer.msg("会议日期不能超过今天");
			return false;
         }
	 }*/
	
	if(meetingTypeVal == null ||  meetingTypeVal == ""){
		layer.msg("类型不能为空");
		return false;
	}
	
	if(meetingResult == null ||  meetingResult == ""){
		layer.msg("结果不能为空");
		return false;
	}
	
	if(meetingNotes != null && meetingNotes.length > 0){
		if(getLength(meetingNotes) > 9000){
			layer.msg("会议记录长度最大9000字节");
			return false;
		}
	}
	
	condition.projectId = projectIdVal;
	condition.meetingDateStr = meetingDateStr;
	condition.meetingType = meetingTypeVal;
	condition.meetingResult = meetingResult;
	condition.meetingNotes = meetingNotes;
	return condition;
}



/**
 * sopinfo  访谈页面
 * 访谈概况 , 访谈对象-长度控制
 */
function intervierInfoFormat(value, row, index){
	var fileinfo = "" ;
	var rc = "";
	if( row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	
	var targetStr = row.viewTarget;
	var subStr = "";
	var targerHtml="";
	if(targetStr.length>8){
		subStr = targetStr.substring(0,8)+"...";
		targerHtml = "</br>访谈对象：<span title="+targetStr+">"+subStr+"</span>";
	}else{
		targerHtml = "</br>访谈对象："+targetStr;
	}
	
	rc = "<div style=\"text-align:left;margin-left:10%;padding:10px 0;\">"+
				"访谈时间："+row.viewDateStr+
				targerHtml+
				"</br>访谈录音："+fileinfo+
			"</div>" ;
	return rc;
}


//meet table format
function meetProInfoFormat(value, row, index){
	return row.proName+"</br>"+row.meetingTypeStr;
}

/**
 * sopinfo  会议页面
 * 会议概况
 */
function metcolumnFormat(value, row, index){
	var fileinfo = "";
	var rc = "";
	if(row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	rc = "<div style=\"text-align:left;margin-left:10%;padding:10px 0;\">"+
				"会议日期："+row.meetingDateStr+
				"</br>会议结论："+row.meetingResultStr+
				"</br>会议录音："+fileinfo+
			"</div>" ;
	return rc;
}

/*function meetInfoFormat(value, row, index){
	var fileinfo = "";
	var rc = "";
	if(row.fname!=null && row.fname!=undefined && row.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:filedown("+row.fileId+","+row.fkey+");\" class=\"blue\" >"+row.fname+"</a>"
	}
	rc = "<div style=\"text-align:left;margin-left:10%;padding:10px 0;\">"+
				"会议日期："+row.meetingDateStr+
				"</br>会议结论："+row.meetingResultStr+
				"</br>会议录音："+fileinfo+
			"</div>" ;
	return rc;
}*/


/**
 * 格式化富文本，记录中附加详情,不附加 title
 */	
function viewNotesFormat(value,row,index){
	var len=0;
	var str= "";
	if(!(!value)){
		str=delHtmlTag($.trim(value))
	}
	if(str!="" && typeof(str)!="undefined"){
		//len = getLength(str);
		len = str.length;
	}
	if(len>120){
		var subValue =str.substring(0,120); 
		var rc = "<div id=\"log\" style=\"text-align:left;\" class=\"text-overflow1\">"+
					subValue+ "..."+
					"<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail("+row.id+")\" >详情<a>"+    
				'</div>';
		return rc;
	}else {
		return str+"<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail("+row.id+")\" >详情<a>";
	}
	
}
function tc_viewNotesFormat(value,row,index){
	var len=0;
	var str= "";
	if(!(!value)){
		str=delHtmlTag($.trim(value))
	}
	if(str!="" && typeof(str)!="undefined"){
		len = str.length;
	}
	if(len>90){
		var subValue =str.substring(0,90); 
		var rc = "<div id=\"log\" style=\"text-align:left;\" class=\"text-overflow1\">"+
					subValue+ "..."+
					"<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail("+row.id+")\" >详情<a>"+    
				'</div>';
		return rc;
	}else {
		return str+"<a href=\"javascript:;\" class=\"blue option_item_mark\"  onclick=\"showLogdetail("+row.id+")\" >详情<a>";
	}
	
}

/**
 * 格式化富文本，记录中不附加详情,不附加 title
 */	
function viewNotesFormat_noinfo(value,row,index){
	var len=0;
	var str= "";
	if(!(!value)){
		str=delHtmlTag($.trim(value))
	}
	if(str!="" && typeof(str)!="undefined"){
		len = str.length;
	}
	if(len>120){
		var subValue =str.substring(0,120); 
		var rc = "<div id=\"log\" style=\"text-align:left;\" class=\"text-overflow1\">"+
					subValue+ "..."+
				'</div>';
		return rc;
	}else {
		return str;
	}
}
function tc_viewNotesFormat_noinfo(value,row,index){
	var len=0;
	var str= "";
	if(!(!value)){
		str=delHtmlTag($.trim(value))
	}
	if(str!="" && typeof(str)!="undefined"){
		len = str.length;
	}
	if(len>90){
		var subValue =str.substring(0,90); 
		var rc = "<div id=\"log\" style=\"text-align:left;\" class=\"text-overflow1\">"+
					subValue+ "..."+
				'</div>';
		return rc;
	}else {
		return str;
	}
}

/**
 * 格式化富文本 只附加 title
 */	
function formatLog(value,row,index){
	var len=0;
	var str= "";
	if(!(!value)){
		str=delHtmlTag($.trim(value))
	}
	if(str!="" && typeof(str)!="undefined"){
		len = str.length;
	}
	
	if(len>120){
		var subValue1 = str.substring(0,120);
		var rc = "<div id=\"log\" style=\"text-align:left;padding:10px 0;\" class=\"text-overflow1\" title='"+str+"'>" +
					subValue1+'...' +
				'</div>';
		return rc;
	}else{
		return str;
	}
}
function tc_formatLog(value,row,index){
	var len=0;
	var str= "";
	if(!(!value)){
		str=delHtmlTag($.trim(value))
	}
	if(str!="" && typeof(str)!="undefined"){
		len = str.length;
	}
	
	if(len>90){
		var subValue1 = str.substring(0,90);
		var rc = "<div id=\"log\" style=\"text-align:left;padding:10px 0;\" class=\"text-overflow1\" title='"+str+"'>" +
					subValue1+'...' +
				'</div>';
		return rc;
	}else{
		return str;
	}
}





//LONG time format
function longTimeFormat(value, row, index){
	return Number(value).toDate().format("yyyy/MM/dd")
}
function longTimeFormatChines(value, row, index){
	return Number(value).toDate().format("yyyy年MM月dd日 hh:mm:ss")
}
function getVal(val,defaultValIfNull)
{
	if(val == "" || val == null || val == 'undefined')
	{
		return defaultValIfNull;
	}
	return val;
}




function replaceStr(str){
	if(str){
		var result=str.replace(/(\n)/g, "");
		result = result.replace(/(\t)/g, "");
		result = result.replace(/(\r)/g, "");
		result = result.replace(/<\/?[^>]*>/g, "");
		result = result.replace(/\s*/g, "");
		return result;
	}

}

function delHtmlTag(str)
{
	if(str){
		return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
	}


}

function getNowDay(fg){
	var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();
	var clock = year + fg;
	if(month < 10) clock += "0";
	clock += month + fg;
	if(day < 10) clock += "0";
	clock += day;
	return clock;
}

function getFileSize(size)
{
	if(size>1000000)
	{
		return size/1000000 + 'M';
	}
	else 
	{
		return size/1000 + 'K';
	}
}
//批量截取 td内字符长度
function cutStr(len,target){
    var obj=$('.'+target);
        for (i=0;i<obj.length;i++){
                //obj[i].innerHTML=obj[i].innerHTML.substring(0,len)+'…';
                    	obj[i].innerHTML=obj[i].innerHTML.substring(0,len);
         }
}

if (!Array.prototype.indexOf){
		Array.prototype.indexOf = function(elt /*, from*/){
    var len = this.length >>> 0;
    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;
    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}

function initTcVal(){
	$("#projectId").val(interviewSelectRow.projectId).attr("disabled","desabled");
	$("#viewDate").val(interviewSelectRow.viewDateStr).attr("disabled","desabled");
	$("#viewTarget").val(interviewSelectRow.viewTarget).attr("readonly","readonly");
	interviewEditor.setContent(interviewSelectRow.viewNotes); 
	
	var fileinfo = "";
	if(interviewSelectRow.fname!=null && interviewSelectRow.fname!=undefined && interviewSelectRow.fname!="undefined" ){
		fileinfo = "<a href=\"javascript:;\" onclick=\"filedown("+interviewSelectRow.fileId+","+interviewSelectRow.fkey+");\" class=\"blue\" >"+interviewSelectRow.fname+"</a>"
	}
	$("#fileNotBeUse").html("");
	$("#fileNotBeUse").html("访谈录音："+fileinfo);
	
	$("#btnNotBeUse").html("");
	$("#btnNotBeUse").html("<a href=\"javascript:;\" class=\"pubbtn fffbtn\" data-close=\"close\">关闭</a>");
	
}

/**
 * 根据parentCode获取数据字典的子项集
 * @param url   请求地址
 * @param name  select的name属性值
 */
function createDictionaryOptions(url, name){
	sendGetRequest(url,null, function(data){
		var options = [];
		$.each(data.entityList, function(i, value){
			options.push('<option index="'+i+'" value="'+value.code+'">'+value.name+'</option>');
		});
		$('select[name="'+name+'"]').append(options.join(''));
	});
}

/**
 * 查询事业线
 * @param url   请求地址
 * @param name  select的name属性值
 */
function createCareelineOptions(url, name){
	sendGetRequest(url,null, function(data){
		var options = [];
		$.each(data.entityList, function(i, value){
			options.push('<option value="'+value.id+'" '+(value.isCurrentUser ? 'back="link"' : '') +'>'+value.name+'</option>');
		});
		$('select[name="'+name+'"]').append(options.join(''));
		$('select[name="'+name+'"]').find('option[back="link"]').attr("selected",true);
	});
}

/**
 * 根据事业线查询相应的投资经理
 * @param url   请求地址
 * @param name  select的name属性值
 */
function createUserOptions(url, name, mark){
	sendGetRequest(url, null, function(data){
		var options = [];
		if(mark == 1){
			options.push('<option value="0">全部</option>');
		}
		$.each(data.entityList, function(i, value){
			options.push('<option value="'+value.id+'" '+(value.isCurrentUser ? 'selected="selected"' : '')+'>'+value.realName+'</option>');
		});
		if(mark == 1){
			$('select[name="'+name+'"]').html(options.join(''));
		}else{
			$('select[name="'+name+'"]').append(options.join(''));
		}
	});
}


var cookieOperator = {
		paramKey : 'parameter',
		/*
		 * 将信息保存到cookie然后提交
		 * param : 
		 * 		_paramKey : 参数键 (默认：parameter)
		 * 		_url : url
		 * 		_param : 参数值
		 * 
		 * */
		forwardPushCookie : function(formdata){
			var cookietime = new Date(); 
			cookietime.setTime(cookietime.getTime() + (60 * 60 * 1000));//coockie保存一小时 
			if(!formdata._param){
				layer.msg('参数信息为空');
				return;
			}
			var tempParamKey;
			if(formdata._paramKey){
				tempParamKey = formdata._paramKey;
			}else{
				tempParamKey = cookieOperator.paramKey;
			}
			$.cookie(tempParamKey, JSON.stringify(formdata._param),{expires:cookietime,path:Constants.sopEndpointURL}); 
			forwardWithHeader(formdata._url);
		},
		/*
		 * 将信息保存到cookie然后提交
		 * param : 
		 * 		_paramKey : 参数键 (默认：parameter)
		 * */
		pullCookie : function(formdata){
			var tempParamKey;
			if(formdata._paramKey){
				tempParamKey = formdata._paramKey;
			}else{
				tempParamKey = cookieOperator.paramKey;
			}
			var retStr = $.cookie(tempParamKey);
			if(retStr == '[object Object]'){
				$.removeCookie(tempParamKey);
				return;
			}
			if(retStr){
				$.removeCookie(tempParamKey);
				return jQuery.parseJSON(retStr);
			}
			return;
			
		}
}



