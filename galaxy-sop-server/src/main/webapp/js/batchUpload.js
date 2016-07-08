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
 */
function toBachUpload(fileurl,sendFileUrl,fieInputId,selectBtnId,submitBtnId,containerId,fileListId,paramsFunction,deliver_form) {
	var params = paramsFunction();
	uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : selectBtnId, // you can pass an id...
		container: containerId, // ... or DOM Element itself
		url : fileurl,
		filters : {
			max_file_size : '10mb',
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png,txt,docx,doc"},
				{title : "Zip files", extensions : "zip"}
			]
		},
		init: {
			PostInit: function(up) {
				//$("#"+fileListId).html('');
				$("#" + submitBtnId).click(function(){
					var isFlag = params;
					if(isFlag == false || isFlag == "false"){
						up.stop();
						$("#show_up_file").html('');
						return;
					}
					if(up.files.length == 0){
							sendPostRequestByJsonObj(sendFileUrl,params,function(data){
								var result = data.result.status;
								if(result == "OK"){
									removePop1();
									$("#project_delivery_table").bootstrapTable('refresh');
								}else{
								    layer.msg(data.result.message);
								}
							});
						}else{
							uploader.start();
						}
					return false;
				});
			},
			BeforeUpload:function(up){
				params = paramsFunction();
				
				up.settings.multipart_params = params;
				/*var $form =$("#"+deliver_form);
				var data = JSON.parse($form.serializeObject());
				data['fileReidsKey']=params['fileReidsKey'];
				params = data;*/
				
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
				plupload.each(files, function(file) {
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
					if(result == "OK"){
						$.each(files, function(i) {     
						    $("#"+files[i].id+"_progress").html('<span>'+ files[i].percent + "%</span>"); 
						}); 
						removePop1();
						$("#project_delivery_table").bootstrapTable('refresh');
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