var uploader; 
/**
 * 
 * @param fileurl 上传文件url
 * @param pid 项目id
 * @param fieInputId 文件输入的id
 * @param selectBtnId 选择文件按钮的id
 * @param submitBtnId 上传按钮的id
 * @param containerId 上传文件区域的id
 * @param fileListId  文件列表显示id
 * @param paramsFunction 上传所需要的附加参数
 * @param deliver_form 数据的表单form id
 * @param callBackFun 回调函数
 */
function toBachUpload(fileurl,sendFileUrl,fieInputId,selectBtnId,submitBtnId,containerId,fileListId,paramsFunction,deliver_form,callBackFun) {
	var params = {};
	uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : selectBtnId, // you can pass an id...
		container: containerId, // ... or DOM Element itself
		url : fileurl,
		rename : true,
		unique_names:true,
		filters : {
			max_file_size : '25mb'
			/*mime_types: [
				{title : "Image files", extensions : "jpg,gif,png,txt,docx,doc"},
				{title : "Zip files", extensions : "zip"}
			]
	       prevent_duplicates : true //不允许选取重复文件*/
		},
		init: {
			PostInit: function(up) {
				$("#" + submitBtnId).click(function(){
					params = paramsFunction();
					var isFlag = params;
					if(isFlag == false || isFlag == "false"){
						up.stop();
						return;
					}
					$(".pop").showLoading(
					 {
					    'addClass': 'loading-indicator'						
					 });
					if(up.files.length == 0){
							sendPostRequestByJsonObj(sendFileUrl,params,function(data){
								var result = data.result.status;
								$(".pop").hideLoading();
								if(result == "OK"){
									if(callBackFun && typeof(callBackFun) == "function"){
										callBackFun(data);
									}else{
										callBack(data);
									}
								}else{
								    layer.msg(data.result.errorCode);
								}
							});
					}else{
						up.settings.multipart_params = params;
						uploader.start();
					}
					return false;
				});
			},
			BeforeUpload:function(up,file){
				params["fileName"] = file.name;
			},
			FileUploaded:function(up,file,rtn){
				var response = $.parseJSON(rtn.response);
				var rs = response.status;
				if(rs == "error"){ //OK, ERROR
					$("#"+fieInputId).val($("#"+fieInputId).val().replace(file.name,""));
					//回滚删除信息
					$("#"+file.id+"_progress").html('<span>'+"上传失败!"+"</span>");
					return false;
				}
             }, 
			FilesAdded: function(up, files) {
				var max_files = 10;
				plupload.each(files, function(file) {
					/**
					 * 最多只能上传10个文件
					 */
                    if (up.files.length > max_files) {
                    	uploader.removeFile(file);
						return;
					}
                    /**
                     * 生成上传文件的列表
                     */
				    var name =countSameFile(file,fileListId);
				    file.name = name;
					$("#"+fieInputId).val($("#"+fieInputId).val()+" "+file.name);
					$("#"+fileListId).append("<tr id='"+file.id+"tr'><td>"+file.name+"</td><td>"+plupload.formatSize(file.size)+"</td><td><button type='button' id='"+file.id+"btn' onclick=del('"+file.id+"','"+file.name+"','"+fieInputId+"')>删除</button> </td><td id='"+file.id+"_progress'></td></tr>"); 
				});
			},
			UploadProgress: function(up, file) {
				 var percent = parseInt(file.percent)-10;
				 $("#"+file.id+"_progress").html('<span>'+ percent + "%</span>"); 
			},
			UploadComplete: function(up, files){//所有都上传完成
			
				if($("#"+fieInputId).val().trim()){
					sendPostRequestByJsonObj(sendFileUrl,params,function(data){
					var result = data.result.status;
					$(".pop").hideLoading();
					if(result == "OK"){
						$.each(files, function(i) {     
						    $("#"+files[i].id+"_progress").html('<span>'+ files[i].percent + "%</span>"); 
						}); 
						if(callBackFun && typeof(callBackFun) == "function"){
							callBackFun(data);
						}else{
							callBack(data);
						}
					}else{
					    $.each(files, function(i) {     
						    $("#"+files[i].id+"_progress").html('<span>'+"上传失败!"+"</span>"); 
						}); 
					    $("#"+fieInputId).val('');
					    alert(data.result.message);
					}
					
				});
				}
		    },
			Error: function(up, err) {
				alert(err.message);
			}
			
		}
	});
	uploader.init();
}
/**
 * 删除文件列表的文件
 * @param id
 * @param name
 * @param fieInputId
 */
function del(id,name,fieInputId){
	$("#"+fieInputId).val($("#"+fieInputId).val().replace(name,""));
    uploader.removeFile(id);
    $("#"+id+"tr").remove();
}


function countSameFile(file,fileList){
	var name = file.name;
	var count = 0;
	$("#"+fileList+" tr:gt(0)").each(function(){
	  var inputValue = $(this).find("td").eq(0).text();
	  if(inputValue == name){
	    count++;
	    var  subname = name.substring(0,name.lastIndexOf("."));
	    var fix =  name.substring(name.lastIndexOf(".")+1,name.length);
	    name = subname+"-"+count+"."+fix;
	  }
    });
	return name;
}
function callBack(data){
}