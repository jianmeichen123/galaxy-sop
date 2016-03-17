$(function(){
	createMenus(6);
	
	$('#data-table').bootstrapTable({
		queryParamsType: 'size|page', // undefined
		pageSize:5,
		pageList : [5, 10, 20 ],
		showRefresh : false ,
		sidePagination: 'server',
		method : 'post',
		pagination: true,
        search: false,
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
	//condition.progress = "projectProgress:1";
	sendGetRequest(platformUrl.getUserPro,condition,setProSelect);
	//sendPostRequestByJsonObj("http://127.0.0.1:8080/galaxy/project/progress/queryPerProPage",{pageNum:0,pageSize:5},setProSelect);
}


//设置项目下拉框
function setProSelect(data){
	
	var result = data.result.status;
	
	if(result == "ERROR"){ //OK, ERROR
		alert("error "+data.result.message);
		$(".pop").remove();
		$("#popbg").remove();	
		//$("#popbg,#pop").remove();
		return;
	}
	
	var entityList = data.entityList;
	if(entityList.length == 0 ){
		alert("无相关项目可添加记录");
		$(".pop").remove();
		$("#popbg").remove();	
		//$("#popbg,.pop").remove();
		return;
	}else{
		for(var i=0;i<data.entityList.length;i++){
	    	$("#projectId").append("<option value='"+data.entityList[i].id+"'>"+data.entityList[i].projectName+"</option>");
	    } 
	}
}


//plupload上传对象初始化,   绑定保存
function initUpload() {
	// 定义 上传插件 方法 、  plupload 上传对象初始化
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#file-select-btn")[0], // you can pass in id...
		url : platformUrl.saveViewFile,
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
				location.reload(true);
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

	uploader.init();
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
		alert("error "+data.result.message);
		return;
	}
	alert("保存成功");
	//$("#popbg,#pop").remove();
	location.reload(true);
}









