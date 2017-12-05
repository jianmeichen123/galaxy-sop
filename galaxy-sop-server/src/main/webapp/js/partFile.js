   String.prototype.trim = function (char, type) {
	   if (char) {
	     if (type == 'left') {
	       return this.replace(new RegExp('^\\'+char+'+', 'g'), '');
	     } else if (type == 'right') {
	       return this.replace(new RegExp('\\'+char+'+$', 'g'), '');
	     }
	     return this.replace(new RegExp('^\\'+char+'+|\\'+char+'+$', 'g'), '');
	   }
	   return this.replace(/^\s+|\s+$/g, '');
	 };  


var data = {};
var  showbar;
   var infoTableModelList = new Array();
  
   $("#win_ok_btn").click(function(){
	   $("#form_edit_actual_dialog").validate({});
	   if($('#deliver_form').length>0){  //交割前事项
		   if(!$("#deliver_form").validate().form()){
			   return
		   }
	   }else{//注资信息
		   if($("#form_edit_actual_dialog").size() == 0){
			   if(!$("#actual_aging_form").validate().form()){
				   return
			   }
		   }else{
			   if(!$("#form_edit_actual_dialog").validate().form()){
				   return
			   }
		   }
	   }
	    var key = keyJSON["b_part"];
		var deleteids = deleteJSON["partDelFile"];
		var projectId = $("#projectId").val();
		data.projectId = projectId;
		
		var fileLength1 = $("#filelist tr").length;
		var fs = "";
		$('#filelist tr').each(function(){
			 var id = $(this).attr("id");
			 if(id != undefined)
			 {
				 id = id.replace("tr", "");
				 if(!isNaN(id)){
					 if(fs){
						 fs = fs +","+id;
				     }else{
				    	 fs = id;
				     }
				 }
			 }
			
		});
		var fields = $.find("input[type='text'][data-type],input[type='hidden'][data-type],input:checked,textarea,radio");
		var id =  $("#grantId").val();
		var parentId = $("#parentId").val();
		var code = $("#code").val();
		var titleId = "3022";
		var subCode = "grant-part";
		if($("#titleId").val()){
			titleId = $("#titleId").val();
			subCode = null;
		}
		if(!id){
			id = null;
		}
		if(!parentId){
			parentId = null;
		}
		if(!code){
			code = null;
		}
		var infoMode = {
				titleId	: titleId,
				subCode:subCode,
				id:id,
				rowId:id,
				field5:parseInt(fileLength1) - 1,
				code:code,
				parentId:parentId
		};
		$.each(fields,function(){
			var field = $(this);
			var type = field.data('type');
			var name = field.data('name');
			
			if(type==19)
			{	
				if(name=="field1"&&field.val()==""){
					infoMode[name]="实际注资";
					if(field.closest("dd").prev("dt").text().indexOf("分拨")>-1){
						infoMode[name]="分拨注资";
					}
				}else {
					infoMode[name] = field.val();
				}
			}
		});
		if(code=='grant-actual'){  
			//验证实际注资计划拨款金额是否大于剩余金额
	        var valInput=$(".moeny_all input").val();
	        var trs=$("#actual-table tbody").find("tr");
	        var sum=0;
			 $.each(trs,function(){ 
				 sum+=Number($(this).find("td:nth-child(2)").text());
			 })
			 var valtrActual=$("#valtrActual").val();   //当前编辑行的金额
			 var totalMoneyActual=$("#totalRemainMoneyActual").val();
			if(Number(valInput)>((Number(totalMoneyActual)*10000-(sum-Number(valtrActual))*10000)/10000).toFixed(4)){
	         	layer.msg("实际注资金额之和大于分期注资金额");
				   return;
	         }
		}else{   
			//验证分期计划拨款金额是否大于剩余金额
	        var valInput=$(".moeny_all input").val();
	        var trs=$(".approp_table tbody").find("tr");
	        var sum=0;
			 $.each(trs,function(){ 
				 sum+=Number($(this).find("td:nth-child(3)").text());
			 })
			 var valtr=$("#valtr").val();   //当前编辑行的金额
			 var totalMoneyPart=$("#totalMoneyPart").val();
			 if(Number(valInput)>((Number(totalMoneyPart)*10000-(sum-Number(valtr))*10000)/10000).toFixed(4)){
	         	layer.msg("分期注资金额之和大于总注资金额");
				   return;
	         }
		}
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		layer.load(2,{time:15000});
		if(key){
			
			var i=0;
			var isComplete = true;
			showbar=setInterval("setbar()",1000);  
			console.log(i)
			if(100 -i >= 15){
				 i+=15; 
			}
		    if(!isComplete)  
		    {   
		    	window.clearInterval(showbar);  
		    }  
		    $("#filelist").find("tr").each(function(index,value){
		    	if(index != 0){
				    $(this).children().eq(3).html('<span>'+ i + "%</span>"); 
			    }
			});
		}
		sendPostRequestByJsonObjNoCache(sendFileUrl,params,true,function(dataParam){
			//进行上传文件
			var result = dataParam.result.status;
			if(result == "OK"){
				isComplete = false;
				i = 0;
				window.clearInterval(showbar);  
				$("#filelist").find("tr").each(function(index,value){
				    	if(index != 0){
						    $(this).children().eq(3).html('<span>'+ 100 + "%</span>"); 
					    }
				});
				var fids = dataParam.entity.fids;
				if(fids != null && fids != undefined){
						fs=fs+","+fids;
						if(fs.indexOf(',') != -1){
							fs = fs.trim(',');
						}
				}
				if(fs){
					infoMode.relateFileId = fs;
				}
				if (infoMode != null) {
					infoTableModelList.push(infoMode);
			    } 
				data.infoTableModelList = infoTableModelList;
				sendPostRequestByJsonObjNoCache(
						platformUrl.saveOrUpdateInfo , 
						data,
						true,
						function(data) {
							var result = data.result.status;
							layer.closeAll('loading');
							if (result == 'OK') {
								if(code == null){
									$("#powindow").remove();
									$("#popbg").remove();
									if(subCode){
										initTabAppropriation(pId);
									}else{
										initTabDelivery();
									}
								}else{
									 $("#popbg01").remove();
									 $.popupTwoClose();
									 $('#actual-table').bootstrapTable('refresh');
								}
							} else {
								layer.msg("操作失败!");
							}
					});
			}else{
				layer.msg("操作失败!");
			}
			
		});
   	
   });
   
   function toBachPartUpload(fileurl,sendFileUrl,fieInputId,selectBtnId,submitBtnId,containerId,fileListId,paramsFunction,deliver_form,callBackFun,id_code) {
	   var params = {};
			var uploader = new plupload.Uploader({
				runtimes : 'html5,flash,silverlight,html4',
				browse_button : selectBtnId, // you can pass an id...
				//container: containerId, // ... or DOM Element itself
				//multi_selection:false,
				url : fileurl,
				rename : true,
				unique_names:true,
				filters : {
					max_file_size : '25mb'
					/*mime_types: [
							{title : "Image files", extensions : "jpg,png,gif,bmp"}
					]*/
				},
				init: {
					PostInit: function(up) {
						params = paramsFunction;
					},
					BeforeUpload:function(up,file){
						var name = file.name.replace(/\s+/g,"");
						params["fileName"] = name;
					},
					FileUploaded:function(up,file,rtn){
						layer.closeAll('loading');
		             }, 
					FilesAdded: function(up, files) {
						var max_files = 10;
						plupload.each(files, function(file) {
							var fileLength = $("#"+fileListId+" tr:gt(0)").length;
							var fileLength1 = $("#"+fileListId+" tr").length;
							//console.log(fileLength1)
							if(fileLength1>0){
								$("#"+fileListId).css("display","block")
							}
							/**
							 * 最多只能上传10个文件
							 */
		                    if (fileLength >= max_files) {
		                    	uploader.removeFile(file);
		                    	layer.msg("最多只能传10个文件!");
								return;
							}
		                    /**
		                     * 生成上传文件的列表
		                     */
						    var name =countSameSubFile(file,fileListId);
						    file.name = name.replace(/\s+/g,"");;
							$("#"+fieInputId).val($("#"+fieInputId).val()+" "+file.name);
							$("#"+fileListId).append("<tr id='"+file.id+"tr'><td>"+file.name+"</td><td>"+plupload.formatSize(file.size)+"</td><td><button type='button' id='"+file.id+"btn' onclick=delPart('"+file.id+"','"+file.name+"','"+fieInputId+"','partDelFile')>删除</button> </td><td id='"+file.id+"tr_progress'></td></tr>"); 
							params.newFileName = file.id;
							up.settings.multipart_params = params;
							uploader.start();
							layer.load(2);
						
						});
						
					},
					UploadProgress: function(up, file) {
					
					},
					UploadComplete: function(up, files){//所有都上传完成
				    },
					Error: function(up, err) {
						if(err.code==-600){
							layer.msg("文件不能大于25M");
						}
					}
					
				}
			});
			uploader.init();
		}
