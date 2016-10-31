var ossClient = {
	uploader : undefined, // 初始化上传插件
	client : undefined,

	mustHasFile : undefined, // 文件是否必须 true:必须 false:非必需
	noFileOper : undefined, // 不带文件的操作反法
	chooseBtn : "chooseBtn", // file select button
	uploadBtn : "pluploadUploadBtn", // save button
	fileShowText : "fileShowText", // file name to show
	uploadObject : undefined,
	ossUrl : undefined,
	getOssFormParam : undefined,
	localUrl : undefined,
	getLocalFormParam : undefined,
	mimeTypes : undefined, // 文件类型过滤，数组 [mp3...]
	validateForm : undefined, // form表单元素校验反法， return false ：校验失败

	successCallBack : undefined,

	signatureUrl : function(data){			  
		  var objectKey = data.fileKey;
	      //需先判断浏览器类型,对data.fileName进行编码
	      var result = ossClient.client.signatureUrl(objectKey, {
	    	  expires: 3600,
	    	  response: {
	    	    'content-disposition' : 'attachment; filename="' + data.fileName + '"'
	    	  }
	      });
	      
	      return result;
	  },
	
	uploadInit : function(data) {
		// data.fileKey
		// data.uploadBtn
		// data.fileShowText
		// data.ossUrl,
		// data.getOssFormParam,
		// data.localUrl,
		// data.getLocalFormParam
		if (data) {
			ossClient.chooseBtn = data.chooseBtn;
			ossClient.uploadBtn = data.uploadBtn;
			ossClient.fileShowText = data.fileShowText;
			ossClient.ossUrl = data.ossUrl;
			ossClient.getOssFormParam = data.getOssFormParam;
			ossClient.localUrl = data.localUrl;
			ossClient.getLocalFormParam = data.getLocalFormParam;
			ossClient.mimeTypes = data.mimeTypes;
			ossClient.mustHasFile = data.mustHasFile;
			ossClient.noFileOper = data.noFileOper;
			ossClient.validateForm = data.validateForm;
			ossClient.successCallBack = data.successCallBack;
			if (!data.fileKey) {
				data.fileKey = undefined;
			}
			sendGetRequest(platformUrl.getPolicy + "/" + data.fileKey, null,
					ossClient.uploadInitCallBack);
		}
	},
	
	uploadInitCallBack : function(data) {
		ossClient.uploadObject = data.userData;
		ossClient.pluploadInit();
	},
	downLoadInit : function(data) {
		sendGetRequest(platformUrl.getPolicy + "/" + undefined, null,
				ossClient.downloadInitCallBack);
	},
	downloadInitCallBack : function(data) {
		ossClient.uploadObject = data.userData;
		ossClient.client = new OSS({
			region : data.userData.region,
			accessKeyId : data.userData.accessid,
			accessKeySecret : data.userData.accesskeysecret,
			// stsToken: '<Your securityToken(STS)>',
			bucket : data.userData.bucket
		});
	},
	
	PostInit : function(up) {
		if (!(!ossClient.mimeTypes)) {
			up.settings.filters.mime_types = ossClient.mimeTypes;
		}

		$("#" + ossClient.uploadBtn).click(function() { // 上传按钮点击事件开始上传
			// 校验参数
			if (!(!ossClient.validateForm)) {
				if (ossClient.validateForm() == false) {
					up.stop();
					return;
				}
			}

			// 获取参数 formParams
			var formParams = "";
			if (ossClient.uploadObject.uploadMode == "oss") {
				if (up.files.length != 0) {
					formParams = {
						'Filename' : up.files[0].name,
						'key' : ossClient.uploadObject.fileKey,
						'policy' : ossClient.uploadObject.policy,
						'OSSAccessKeyId' : ossClient.uploadObject.accessid,
						'success_action_status' : '200', // 让服务端返回200,不然，默认会返回204
						'signature' : ossClient.uploadObject.signature,
						"Content-Disposition" : "attachment;filename=" + up.files[0].name,
						"Content-Length" : up.files[0].size
					};

				}
			} else {
				/*if (!ossClient.getLocalFormParam) {
					formParams = {
							"fileSource" : $(_this.id).find("input[name='win_fileSource']:checked").val(),
							"fileType" : $(_this.id).find("#win_fileType").val(),
							"fileWorktype" : $(_this.id).find("#win_fileWorkType").val(),
							"projectId" : $(_this.id).find("#win_sopProjectId").data("tid"),
							"isProve" : $(_this.id).find("#win_isProve").attr("checked"),
							"remark" : $(_this.id).find("#win_FILELIST").val()
					};
				} else {
					formParams = ossClient.getLocalFormParam(
							up, up.files[0], ossClient.uploadObject.fileKey);
				}
				*/
				if (!(!ossClient.getLocalFormParam)) {
					formParams = ossClient.getLocalFormParam(up, up.files[0], ossClient.uploadObject.fileKey);
				}
				
			}

			// 校验 formParams
			if (formParams == false || formParams.length == 0) {
				up.stop();
				return;
			}

			if (up.files.length == 0) {
				if (ossClient.mustHasFile == false) {
					if (!ossClient.noFileOper) {
						layer.msg("未传入不带文件处理方法");
					} else {
						ossClient.noFileOper(formParams);
					}
				}
			} else {
					if (ossClient.uploadObject.uploadMode == "oss") {
						up.settings.url = ossClient.uploadObject.host;
					} else {
						up.settings.url = ossClient.localUrl;
					}
					up.settings.multipart_params = formParams;
					ossClient.uploader.start();
					return false;
			}
		})
	},
	
	filesAdded : function(up, files) {

		// 解决多次文件选择后，文件都存入upload
		if (ossClient.uploader.files.length >= 2) {
			ossClient.uploader.splice(0, ossClient.uploader.files.length - 1)
		}
		plupload.each(files, function(file) {
			$("#" + ossClient.fileShowText).val(file.name);
		});
	},
	
	UploadProgress : function(up, file) {
	},
	
	FileUploaded : function(up, file, result) {
		if (result.status == 200) {
			if (ossClient.uploadObject.uploadMode != "oss") {
				// 定制本地上传结束后返回事件
				if (ossClient.successCallBack) {
					ossClient.successCallBack(up, file, result);
				} else {
					layer.msg("上传成功");
				}
			} else {
				/* var form;
				if (!ossClient.ossUrl) {
					ossClient.ossUrl = platformUrl.fileCallBack;
				}
				
				if (!ossClient.getOssFormParam) {
					form = {

					}
				} else {
					form = ossClient.getOssFormParam(up, file,uploadObject.fileKey);
				}
				*/
				
				if(!(!ossClient.ossUrl)){
					var form;
					
					if(!(!ossClient.getOssFormParam)){
						form = ossClient.getOssFormParam(up, file,ossClient.uploadObject.fileKey);
					}
					
					sendPostRequestByJsonObj(
							ossClient.ossUrl,
							form,
							function(data) {
								if (data.result.status == "OK") {
									if (ossClient.successCallBack) {
										ossClient.successCallBack(up, file, result);
									} else {
										layer.msg("上传成功");
									}
								} else {
									if (ossClient.successCallBack) {
										ossClient.successCallBack(up, file, result);
									}else{
										layer.msg("上传失败");
									}
								}
							});
					
				}
			}
		} else {
			layer.msg("上传失败");
		}
	},
	
	BeforeUpload : function(up) {
	},

	pluploadInit : function() {
		ossClient.uploader = new plupload.Uploader(
				{
					runtimes : 'html5,flash,silverlight,html4',
					browse_button : $("#" + ossClient.chooseBtn)[0], // you can
					// pass in
					// id...
					url : platformUrl.commonUploadFile,
					multipart : true,
					multi_selection : false,
					filters : {
						max_file_size : '25mb',
						mime_types : [
								{
									title : "Zip files",
									extensions : "zip,rar,ZIP,RAR"
								},
								{
									title : "Image files",
									extensions : "bmp,jpg,jpeg,gif,png,BMP,JPG,JPEG,GIF,PNG"
								},
								{
									title : "audio files",
									extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r,flv,MP3,MP4,AVI,WAV,WMA,AAC,M4A,M4R,FLV"
								},
								{
									title : "doc files",
									extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt,pages,key,numbers,DOC,DOCX,PPT,PPTX,PPS,XLS,XLSX,PDF,TXT,PAGES,KEY,NUMBERS"
								} ]
					},
					init : {
						PostInit : ossClient.PostInit,
						FilesAdded : ossClient.filesAdded,
						UploadProgress : ossClient.UploadProgress,
						FileUploaded : ossClient.FileUploaded,
						BeforeUpload : ossClient.BeforeUpload,
						Error : function(up, err) {
							layer.msg(err.message);
						}
					}
				});
		ossClient.uploader.init();
	}
}

/*
var ossClient = {
	client : undefined,
	chooseBtn : "chooseBtn",
	uploadBtn : "pluploadUploadBtn",
	fileShowText : "fileShowText",
	uploadObject : undefined,
	ossUrl : undefined,
	getOssFormParam : undefined,
	localUrl : undefined,
	getLocalFormParam : undefined,
	successCallBack : undefined,
	signatureUrl : function(data) {
		var objectKey = data.fileKey;
		var result = ossClient.client.signatureUrl(objectKey, {
			expires : 3600,
			response : {
				'content-disposition' : 'attachment; filename="'
						+ data.fileName + '"'
			}
		});

		return result;
	},
	uploadInit : function(data) {
		// data.fileKey
		// data.uploadBtn
		// data.fileShowText
		// data.ossUrl,
		// data.getOssFormParam,
		// data.localUrl,
		// data.getLocalFormParam
		if (data) {
			ossClient.chooseBtn = data.chooseBtn;
			ossClient.uploadBtn = data.uploadBtn;
			ossClient.fileShowText = data.fileShowText;
			ossClient.ossUrl = data.ossUrl;
			ossClient.getOssFormParam = data.getOssFormParam;
			ossClient.localUrl = data.localUrl;
			ossClient.getLocalFormParam = data.getLocalFormParam;
			if (!data.fileKey) {
				data.fileKey = undefined;
			}
			sendGetRequest(platformUrl.getPolicy + "/" + data.fileKey, null,
					ossClient.uploadInitCallBack);
		}
	},
	uploadInitCallBack : function(data) {
		ossClient.uploadObject = data.userData;
		ossClient.pluploadInit();
	},
	downLoadInit : function(data) {
		sendGetRequest(platformUrl.getPolicy + "/" + undefined, null,
				ossClient.downloadInitCallBack);
	},
	downloadInitCallBack : function(data) {
		ossClient.uploadObject = data.userData;
		ossClient.client = new OSS({
			region : data.userData.region,
			accessKeyId : data.userData.accessid,
			accessKeySecret : data.userData.accesskeysecret,
			// stsToken: '<Your securityToken(STS)>',
			bucket : data.userData.bucket
		});
	},
	PostInit : function() {
		// 上传按钮点击事件开始上传
		$("#" + ossClient.uploadBtn).click(function() {
			ossClient.uploader.start();
			return false;
		})
	},
	filesAdded : function(up, files) {

		// 解决多次文件选择后，文件都存入upload
		if (ossClient.uploader.files.length >= 2) {
			ossClient.uploader.splice(0, ossClient.uploader.files.length - 1)
		}
		plupload.each(files, function(file) {
			$("#" + ossClient.fileShowText).val(file.name);
		});
	},
	UploadProgress : function(up, file) {
	},
	FileUploaded : function(up, file, result) {
		if (result.status == 200) {
			if (ossClient.uploadObject.uploadMode != "oss") {
				// 定制本地上传结束后返回事件
				if (ossClient.successCallBack) {
					ossClient.successCallBack(up, file, result);
				} else {
					layer.msg("上传成功");
				}

			} else {
				var form;
				if (!ossClient.ossUrl) {
					ossClient.ossUrl = platformUrl.fileCallBack;
				}
				if (!ossClient.getOssFormParam) {
					form = {

					}
				} else {
					form = ossClient.getOssFormParam(up, file,
							uploadObject.fileKey);
				}
				sendPostRequestByJsonObj(
						ossClient.ossUrl,
						form,
						function(data) {
							if (data.result.status == "OK") {
								if (ossClient.successCallBack) {
									ossClient.successCallBack(up, file, result);
								} else {
									layer.msg("上传成功");
								}
							} else {
								layer.msg("上传失败");
							}
						});
			}
		} else {
			layer.msg("上传失败");
		}
	},
	BeforeUpload : function(up) {
		if (!ossClient.validateForm) {
			ossClient.validateForm = function(up, file, fileKey) {
				return true;
			}
		}
		if (ossClient.validateForm(up.files[0], ossClient.fileKey)) {
			if (ossClient.uploadObject.uploadMode == "oss") {
				var form = {
					'Filename' : up.files[0].name,
					'key' : ossClient.uploadObject.fileKey,
					'policy' : ossClient.uploadObject.policy,
					'OSSAccessKeyId' : ossClient.uploadObject.accessid,
					'success_action_status' : '200', // 让服务端返回200,不然，默认会返回204
					'signature' : ossClient.uploadObject.signature,
					"Content-Disposition" : "attachment;filename="
							+ up.files[0].name,
					"Content-Length" : up.files[0].size
				};
				up.settings.url = ossClient.uploadObject.host;
			} else {
				var form;
				if (!ossClient.localUrl) {
					ossClient.localUrl = platformUrl.commonUploadFile;
				}
				if (!ossClient.getLocalFormParam) {
					form = {
							"fileSource" : $(_this.id).find("input[name='win_fileSource']:checked").val(),
							"fileType" : $(_this.id).find("#win_fileType").val(),
							"fileWorktype" : $(_this.id).find("#win_fileWorkType").val(),
							"projectId" : $(_this.id).find("#win_sopProjectId").data("tid"),
							"isProve" : $(_this.id).find("#win_isProve").attr("checked"),
							"remark" : $(_this.id).find("#win_FILELIST").val()
					};
				} else {
					form = ossClient.getLocalFormParam(up, up.files[0],
							ossClient.fileKey);
				}
				up.settings.url = ossClient.localUrl;
			}
			up.settings.multipart_params = form;
		} else {
			layer.msg("参数校验不合法");
		}
	},
	uploader : undefined,
	pluploadInit : function() {
		ossClient.uploader = new plupload.Uploader(
				{
					runtimes : 'html5,flash,silverlight,html4',
					browse_button : $("#" + ossClient.chooseBtn)[0], // you can
					// pass in
					// id...
					url : platformUrl.commonUploadFile,
					multipart : true,
					multi_selection : false,
					filters : {
						max_file_size : '25mb',
						mime_types : [
								{
									title : "Zip files",
									extensions : "zip,rar,ZIP,RAR"
								},
								{
									title : "Image files",
									extensions : "bmp,jpg,jpeg,gif,png,BMP,JPG,JPEG,GIF,PNG"
								},
								{
									title : "audio files",
									extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r,flv,MP3,MP4,AVI,WAV,WMA,AAC,M4A,M4R,FLV"
								},
								{
									title : "doc files",
									extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt,pages,key,numbers,DOC,DOCX,PPT,PPTX,PPS,XLS,XLSX,PDF,TXT,PAGES,KEY,NUMBERS"
								} ]
					},
					init : {
						PostInit : ossClient.PostInit,
						FilesAdded : ossClient.filesAdded,
						UploadProgress : ossClient.UploadProgress,
						FileUploaded : ossClient.FileUploaded,
						BeforeUpload : ossClient.BeforeUpload,
						Error : function(up, err) {
							layer.msg(err.message);
						}
					}
				});
		ossClient.uploader.init();
	}
}*/
