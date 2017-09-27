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
   var infoTableModelList = new Array();
   $("#win_ok_btn").click(function(){
	   if($('#deliver_form')){
		   if(!$("#deliver_form").validate().form()){
			   return
		   }
	   }else{
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
		var fields = $.find("input[type='text'][data-type],textarea");
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
				infoMode[name] = field.val();
			}
		});
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
       /*if(Number(valInput)>((Number(totalMoneyPart)*10000-sum*10000)/10000).toFixed(4)){
        	layer.msg("分期注资金额之和大于总注资金额");
			   return;
        }*/
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		$("body").showLoading();
		sendPostRequestByJsonObjNoCache(sendFileUrl,params,false,function(dataParam){
			//进行上传文件
			var result = dataParam.result.status;
			if(result == "OK"){
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
							if (result == 'OK') {
								$("body").hideLoading();
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
					max_file_size : '2mb',
					mime_types: [
							{title : "Image files", extensions : "jpg,png,gif,bmp"}
					]
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
							$("#"+fileListId).append("<tr id='"+file.id+"tr'><td>"+file.name+"</td><td>"+plupload.formatSize(file.size)+"</td><td><button type='button' id='"+file.id+"btn' onclick=delPart('"+file.id+"','"+file.name+"','"+fieInputId+"','partDelFile')>删除</button> </td><td id='"+file.id+"_progress'></td></tr>"); 
							params.newFileName = file.id;
							up.settings.multipart_params = params;
							uploader.start();
						
						});
						
					},
					UploadProgress: function(up, file) {
						 var percent = parseInt(file.percent)
						 if(percent > 10){
							 percent = parseInt(file.percent)-10;
							 $("#"+file.id+"_progress").html('<span>'+ percent + "%</span>"); 
						 }
					},
					UploadComplete: function(up, files){//所有都上传完成
				    },
					Error: function(up, err) {
						if(err.code==-600){
							layer.msg("图片不能大于2M");
						}
					}
					
				}
			});
			uploader.init();
		}
