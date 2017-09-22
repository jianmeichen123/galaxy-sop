   var data = {};
   var infoTableModelList = new Array();
   $("#win_ok_btn").click(function(){
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
				titleId	: "3022",
				subCode:"grant-part",
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
		
		var sendFileUrl = Constants.sopEndpointURL+'galaxy/informationFile/operInformationFile';
		var params = {};
		params.projectId =  projectInfo.id;
		params.fileReidsKey = key;
		params.deleteids = deleteids;
		$("body").showLoading();
		console.log("保存的 data"+JSON.stringify(params));
		console.log(data);
		sendPostRequestByJsonObjNoCache(sendFileUrl,params,false,function(dataParam){
			//进行上传
			var result = dataParam.result.status;
			if(result == "OK"){
				var fids = dataParam.entity.fids;
				if(fids != null && fids != undefined){
						fs=fs+","+fids;
						if(fs.indexOf(',') != -1){
							fs=fs.substring(fs.indexOf(',')+1, fs.length);
						}
				}
				if(fs){
					infoMode.relateFileId = fs;
				}
				
				if (infoMode != null) {
					infoTableModelList.push(infoMode);
			    } 
				data.infoTableModelList = infoTableModelList;
				console.log("测试测试566:"+JSON.stringify(data));
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
									initTabAppropriation(pId);
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