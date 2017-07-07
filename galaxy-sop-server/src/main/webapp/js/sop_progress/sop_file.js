

//fileWorktype
var filesCondition = {};

//var tagProgress = 'projectProgress:6'; // projectId  progress


function initFileShow(){
	filesCondition = {};
	
	var tagProgress = flow[i-1]; 
	
	var toShowFlows = ['projectProgress:4','projectProgress:5','projectProgress:6','projectProgress:8','projectProgress:9'];
	if($.inArray(tagProgress, toShowFlows) != -1){
		//fileTabToggle(true);
		
		var condition = {};
		condition.id = projectId;
		condition.projectProgress = tagProgress;
		sendPostRequestByJsonObj(Constants.sopEndpointURL + "/galaxy/progressT/showProFlowFiles",condition,backForShowFiles);
	}else{
		//fileTabToggle(false);
	}
}


/*function fileTabToggle(mark){
	if(mark == true){
		$(".tab_2").show();
		$(".file_list").hide();
	}else{
		$(".tab_2").css("display","none");
		$(".file_list").css("display","none");
	}
}*/

function backForShowFiles(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		layer.msg(data.result.message);
		return;
	}
	$(".file_list").empty();
	
	filesCondition = data.userData;
	var str = "";
	for(var filetype in filesCondition){
		str = '<li>' + getFileShowStr(filesCondition[filetype]) + '</li>';
		
		$(".file_list").append(str);
		if(filesCondition[filetype].canOpt){
			fileUpBuild(
					Constants.sopEndpointURL + "/galaxy/progressT/optProFlowFiles",
					null,
					filesCondition[filetype].fileWorktype.replace(":","_") + '_up',
					null,
					filesCondition[filetype].fileWorktype.replace(":","_") + '_save');
		}
	}
	
}



/**
 * @param addFileUrl
 *            上传文件调用后台的url      eg:"/interconnect/inFile.htm"
 * @param paramsCondition
 *            上传文件时传到后台的数据         eg:{'code':'hulian_2'}
 * @param selectId
 *            页面上传按钮，选择文件的图标   eg:$("#selectId")
 * @param showFileId
 *            选择文件后，页面回显选择的文件名的  id
 * @param saveFileId
 *            页面提交保存按钮的id
 */
function fileUpBuild(addFileUrl,paramsCondition,selectId,showFileId,saveFileId){ 
	var fileUploader = new plupload.Uploader({
		runtimes : 'html5,html4,flash,silverlight',
	    browse_button : selectId,
	    url : addFileUrl,
	    multipart:true,//true：multipart/form-data； false：以二进制的格式来上传文件
		multi_selection:false,
		filters : {   //只支持PDF和jpeg、png格式，大小限定为25M。
			mime_types : [
	           { title : "Image files", extensions : "jpg,jpeg,png,JPG,JPEG,PNG" }, 
	           { title : "PDF files", extensions : "pdf,PDF" }
	        ],
			max_file_size : '25mb',
			prevent_duplicates : false //true 不允许选取重复文件
		},
	});
	
	fileUploader.init();  
	
	fileUploader.bind('FilesAdded',function(uploader,files){
		//解决多次文件选择后，文件都存入upload
		if(uploader.files.length >= 1){
			uploader.splice(0, uploader.files.length-1);
		}
		
		if(showFileId != null){
			plupload.each(files, function(file) {
				$("#"+showFileId).text(file.name);
			});
		}
		
		var fileWorktype = $("#"+selectId).attr("data-type");
		paramsCondition = filesCondition[fileWorktype];
		var fileType = getFileTypeByName(files[0].name);
		paramsCondition.fileType = fileType;
		paramsCondition.projectId  = projectId;
		
		uploader.settings.multipart_params = paramsCondition;
		
		var saveObj = $("#"+saveFileId);
		if(!saveObj  || saveObj == null ||saveObj.length == 0 ){
			uploader.start();
		}else{
			tosaveToggle('toShow',selectId,files[0].name);
		}
		
		//selectFile($("#"+selectId),files[0].name);
	});
	
	fileUploader.bind('UploadProgress',function(uploader,files){
		
	});
	
	fileUploader.bind('PostInit',function(uploader){
		var saveObj = $("#"+saveFileId);
		if(saveObj && saveObj!=null && saveObj.length > 0){
			$("#"+saveFileId).click(function(){
				uploader.start();
			});
		}
	});
	
	fileUploader.bind('BeforeUpload',function(uploader){
//		开始上传
		tosaveToggle('toLoad',selectId);
		
		//$("#"+selectId).siblings(".file_box").find("span").hide();
		//$("#"+selectId).siblings(".file_box").find("p").show();
	});
	
	fileUploader.bind('FileUploaded',function(uploader,files,rtn){
//		上传成功
		/*$("#"+selectId).closest("li").hideLoading();*/
		uploader.splice(0, uploader.files.length);
		var response = $.parseJSON(rtn.response);
		var result = response.result;
		if(result.status == "OK"){
			//项目阶段按钮状态刷新
			refreshButton();
			var fileWorktype = $("#"+saveFileId).attr("data-type");
			var fi = response.entity;
			filesCondition[fileWorktype] = fi;
			
			var liObj = $("#"+selectId).closest("li");
			var filestr = getFileShowStr(fi);
			
			liObj.empty();
			liObj.append(filestr);
//			liObj——li重新渲染
			fileUpBuild(
					Constants.sopEndpointURL + "/galaxy/progressT/optProFlowFiles",
					null,
					fi.fileWorktype.replace(":","_") + '_up',
					null,
					fi.fileWorktype.replace(":","_") + '_save');
			
			
		}else{
			tosaveToggle('toHide',selectId);
			layer.msg(result.message);
		}
	});
	
	fileUploader.bind('Error',function(uploader,err){
//		上传出错
		/*$("#"+selectId).closest("li").hideLoading();*/
		tosaveToggle('toHide',selectId);
		layer.msg(err.message);
		uploader.splice(0, uploader.files.length);
	});
}




function tosaveToggle(mark,selectId,fileName){
	
	var selectObj = $("#"+selectId);
	var liObj = selectObj.closest("li");	
	var typeObj=liObj.find("input[data-type]")[0];
	var type = $(typeObj).attr("data-type");
	
	if(mark == 'toShow'){
		// to show save and cancle btn;   for add a file
		var imgStr = getImageOrPdf(fileName);
		
		liObj.find(".cover_box").show();
		liObj.find(".file_btn").hide();
		if(type && type == 'blank'){
			liObj.find('.file_box').find('img').removeClass("add_img");
			//liObj.find('.file_box').find('img').attr("src", imgStr);
		}
		liObj.find('.file_box').find('img').attr("src", imgStr);
	}else if(mark == 'toLoad'){
		// to show load...;  for adding file
		
		liObj.find(".file_box").find("span").hide();
		liObj.find(".file_box").find("p").show();
		
	}else if(mark == 'toHide'){
		// to hide...;  for error
		var fileWorktype = selectObj.attr("data-type");
		var file = filesCondition[fileWorktype];
		imgStr = getImageOrPdf(file);
		
		liObj.find(".cover_box").hide();
		liObj.find(".file_btn").show();
		if(type && type == 'blank'){
			liObj.find('.file_box').find('img').addClass("add_img");
			liObj.find('.file_box').find('img').attr("src", Constants.sopEndpointURL + '/img/sop_progress/plus_icon.png');
		}else{
			liObj.find('.file_box').find('img').attr("src", imgStr);
		}
	}
} 






function getFileShowStr(file){
	var str = '';
	if(file.taskStatusStr && file.taskStatusStr !=null){
		if(file.fileKey == null){
			str += create_task_nofile_area(file);  // data-type="task_nofile"
		}else{
			str += create_task_file_area(file);   // data-type="task_file"
		}
	}else{      
		if(file.fileKey == null){
			str += create_blank_area(file);  //  data-type="blank"  空白可上传区域
		}else{
			str += create_file_area(file);  //  data-type="file"    有文档操作
		}
	}
	return str;
}



function getImageOrPdf(file){
	// fileType:1  文档    // fileType:4  图片
	var fileType = getFileType(file);
	var imgstr = "";
	if(fileType == "fileType:1"){
		imgstr = Constants.sopEndpointURL + "/img/sop_progress/pdf.png";   //pdf
	}else if(fileType == "fileType:4"){
		imgstr = Constants.sopEndpointURL + "/img/sop_progress/image.png"; //图片
	}
	return imgstr;
}
/*
 * fileType:1  pdf
 * fileType:4  图片
*/
function getFileType(file){
	if(typeof(file) == 'string'){
		var fileType = getFileTypeByName(file);
		return fileType;
	}else{
		var fileType = getFileTypeByExt(file.fileSuffix);
		return fileType;
	}
	
}

function getOptionStr(file,type){
	var optStr = "";
	var optOption = "";
	if(file.canOpt){
		optOption += '<span class="reupload_'+type+'" id="'+ file.fileWorktype.replace(":","_") +'_up"  data-type="' + file.fileWorktype + '"></span> ';
	}
	if(file.canDown){
		optOption += '<span class="downlond_'+type+'" id="'+ file.fileWorktype.replace(":","_") +'_down" onclick="filedown(\''+file.id+'\')"></span>';
	}
	if(optOption != '' && optOption.length > 0){
		optStr = 
			'<div class="file_btn">' +
				optOption +
			'</div>' ;
	}
		
	return optStr;
}



//创建空白可上传区域
//fileKey=null taskStatusStr == null canopt=true 
function create_blank_area(file){
	console.log(file)
	var selectopt = "";
	var imgHtm = "";
	if(file.canOpt){
		selectopt = '<input type="file" title="" id="'+ file.fileWorktype.replace(":","_") +'_up" data-type="' + file.fileWorktype + '">';
		imgHtm = '<img src="' + Constants.sopEndpointURL + '/img/sop_progress/plus_icon.png" class="add_img" alt="">';
	}
	
	var str = 
		//'<li>' +
			'<input type="hidden" data-type="blank">' +
			selectopt +
			'<div class="file_box">' +
				imgHtm +
				'<div class="cover_box">' +
					'<span class="cancel" onclick="tosaveToggle(\'toHide\',\'' + file.fileWorktype.replace(":","_") +"_up" + '\')" >取消</span>'  +
					'<span class="up_load" id="'+ file.fileWorktype.replace(":","_") +'_save" >上传</span>' +
					'<p>loading…</p>' +
				'</div>' +
			'</div>' +
			'<span>'+ file.fWorktype +'</span>' ;
		//'</li>';
	
	return str;
}


//fileKey=null taskStatusStr == null 
function create_file_area(file){
	//获取上传的文件类型，显示不同上传下载图表
	var type=file.fileSuffix;
	if(type=="jpg" || type=="jpeg" || type=="png" || type=="JPG" || type=="JPEG" || type=="PNG"){
		type="jpg"
	}else{
		type=="pdf"
	}
	var imgstr = getImageOrPdf(file);
	var optStr = getOptionStr(file,type);
	var str = 
		//'<li>' +
			'<input type="hidden" data-type="file">' +
			'<div class="file_box file_img">' +
				'<img class="bg_img" src="' + imgstr + '" ftype="'+file.fileSuffix+'" furl="'+file.filUri+'" fid="'+file.id+'" onclick="view_file(this)" alt="" />' +
				optStr +
				'<div class="cover_box">' +
					'<span class="cancel" onclick="tosaveToggle(\'toHide\',\'' + file.fileWorktype.replace(":","_") +"_up" + '\')" >取消</span>'  +
					'<span class="up_load" id="'+ file.fileWorktype.replace(":","_") +'_save" >上传</span>' +
					'<p>loading…</p>' +
				'</div>' +
			'</div>' +
			'<span>'+ file.fWorktype +'</span>' ;
		//'</li>';
	
	return str;
}

//创建任务显示区域  fWorktype
//taskStatusStr!=null    fileKey=null
function create_task_nofile_area(file){
	var lin = file.taskStatusStr;
	if(file.taskUname != null && file.taskUname.length > 0){
		lin += '<br/>(' + file.taskUname + ')';
	}
	
	var str = 
		//'<li>' +
			'<input type="hidden" data-type="task_nofile">' +
			'<div class="file_box">' +
				'<p class="center_text" style="margin-top: -18px;">' +
					lin +
				'</p>' +
				'<div class="cover_box">' +
					'<span class="cancel" onclick="tosaveToggle(\'toHide\',\'' + file.fileWorktype.replace(":","_") +"_up" + '\')" >取消</span>'  +
					'<span class="up_load" id="'+ file.fileWorktype.replace(":","_") +'_save" >上传</span>' +
					'<p>loading…</p>' +
				'</div>' +
			'</div>' +
			'<span>'+ file.fWorktype +'</span>' ;
		//'</li>';
	
	return str;
}

//taskStatusStr!=null    fileKey!=null
function create_task_file_area(file){
	var type=file.fileSuffix;
	var imgstr = getImageOrPdf(file);
	var optStr = getOptionStr(file,type);
	
	var lin = file.taskStatusStr;
	if(file.taskUname != null && file.taskUname.length > 0){
		lin += '<br/>(' + file.taskUname + ')';
	}
		
	var str = 
		//'<li>' +
			'<input type="hidden" data-type="task_file">' +
			'<div class="file_box file_img">' +
				'<img class="bg_img" src="' + imgstr + '" ftype="'+file.fileSuffix+'" furl="'+file.filUri+'" fid="'+file.id+'"  onclick="view_file(this)"  alt="" />' +
				'<p class="center_text" style="margin-top: -18px;">' +
					lin +
				'</p>' +
				optStr +
				'<div class="cover_box">' +
					'<span class="cancel" onclick="tosaveToggle(\'toHide\',\'' + file.fileWorktype.replace(":","_") +"_up" + '\')" >取消</span>'  +
					'<span class="up_load" id="'+ file.fileWorktype.replace(":","_") +'_save" >上传</span>' +
					'<p>loading…</p>' +
				'</div>' +
			'</div>' +
			'<span>'+ file.fWorktype +'</span>' ;
		//'</li>';
	
	return str;
}




