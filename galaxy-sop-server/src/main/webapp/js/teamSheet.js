	var dataGrid = {
			load : function(projectId){
				var params = {
						"projectId" : projectId
				}
				sendPostRequestByJsonObj(
						platformUrl.searchSopFileListWithoutPage,
						params,
						function(data){
							var _table = $("#teamSeheetDataGrid");
							var _tbody = _table.find("tbody");
							dataGrid.empty(_tbody);
							$.each(data.entityList,function(){
								var $tr = $('<tr data-file-work-type="'+this.fileWorktype+'" data-file-key="'+this.fileKey+'" data-doc-type="'+this.docType+'" data-file-name="'+this.fileName+'"></tr>');
								$tr.append('<td>'+this.fWorktype+'</td>') ;
								if(this.fileType){
									$tr.append('<td>'+this.fType+'</td>');
									$tr.append('<td>'+this.updatedDate+'</td>') ;
								}else{
									$tr.append('<td>文档未上传</td>');
									$tr.append('<td></td>') ;
								}	
								$tr.append('<td>'+this.fileStatusDesc+'</td>') ;
								if(this.fileKey == null){	
									$tr.append('<td><a data-act="uploadFileBtn" data-tid='+this.id+' data-worktype='+this.fWorktype+' data-projectid='+this.projectId+' href="javascript:; " class="blue">上传</a></td>');
								}else{
									$tr.append('<td><a data-act="downloadFileBtn" data-tid='+this.id+' data-worktype='+this.fWorktype+' data-projectid='+this.projectId+' href="javascript:; " class="blue">查看</a></td>'); 	
								}
								$tr.append('<td><a data-act="uploadProveFileBtn" data-tid='+this.id+' data-worktype='+this.fWorktype+' data-projectid='+this.projectId+' href="javascript:; " class="blue">上传</a></td>') ;
								_tbody.append($tr);
								dataGrid.initPanel(this);
								
								
							});
						}
				);	
			},
			empty : function(_tbody){
				_tbody.empty();
			},
			render : function(_tr,param){
			},
			initPanel : function(_item){
				$("[data-tid='"+_item.id+"'][data-act='uploadFileBtn']").click(function(){
					//上传插件参数
					var _formdata = {
							_id : item.id,
							_fileType : "auto",
							_fileSource : _item.fileSource,
							_workType : _item.fileWorktype,
							_projectId : _item.projectId,
							callFuc : function(){
								dataGrid.load(_projectId);
							},
							_url : platformUrl.commonUploadFile,
							_isProve : "hide",
							_remark : "hide"
						};
//					win.initData();
					win.init(_formdata);
					
				});
				$("[data-tid='"+_item.id+"'][data-act='uploadProveFileBtn']").click(function(){
					//上传插件参数
					var _formdata = {
							_id : item.id,
							_workType : _item.fileWorktype,
							_projectId : _item.projectId,
							_isProve : true,
							callFuc : function(){
								dataGrid.load(_projectId);
							}
						};
					
					win.init(_formdata);
				});
				
				
				$("[data-tid='"+_item.id+"'][data-act='downloadFileBtn']").click(function(){
				});
				
			}

	};
	
	var win = {
			id : undefined,
			init : function(_formdata){
				win.initData();
				win.callFuc = _formdata.callFuc;
				$.popup({
					txt : $("#uploadPanel").html(),
					showback:function(){
						var _this = this;
						win.fillData(_this,_formdata);
						//plupload上传对象初始化
						var uploader = new plupload.Uploader({
							runtimes : 'html5,flash,silverlight,html4',
							browse_button : $(_this.id).find("#win_selectBtn")[0], // you can pass in id...
							url : _formdata._url,
							multipart:true,
							multi_selection:false,
							filters : {
								max_file_size : '25mb',
								mime_types: [
								    {title : "Zip files", extensions : "zip,rar,ZIP,RAR"},
									{title : "Image files", extensions : "bmp,jpg,jpeg,gif,png,BMP,JPG,JPEG,GIF,PNG"},
									{title : "audio files", extensions : "mp3,mp4,avi,wav,wma,aac,m4a,m4r,flv,MP3,MP4,AVI,WAV,WMA,AAC,M4A,M4R,FLV"},
									{title : "doc files", extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt,pages,key,numbers,DOC,DOCX,PPT,PPTX,PPS,XLS,XLSX,PDF,TXT,PAGES,KEY,NUMBERS"}
								]
							},
							init: {
								PostInit: function(){	
									//上传按钮点击事件开始上传
									$(_this.id).find("#win_uploadBtn").click(function(){
										uploader.start();
										return false;
									})
								},
								FilesAdded: function(up, files) {
									var $fileType = $(_this.id).find("#win_fileType");
									
									//解决多次文件选择后，文件都存入upload
									if(uploader.files.length >= 2){
										uploader.splice(0, uploader.files.length-1)
									}
									plupload.each(files, function(file) {
//										document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
										$(_this.id).find("#win_fileTxt").val(file.name);
										if(_formdata._fileTypeAuto){
											attrFileType($fileType, file);
										}
										
									});
									
									
									
								},
								UploadProgress: function(up, file) {
								},
								FileUploaded:function(up,file,result){
									$(_this.id).hideLoading();
									if(result.status==200){
										var _restmp = $.parseJSON(result.response);
										var _projectId = _restmp.message;
										if(_restmp.result.status == "OK"){
											layer.msg("上传成功");
											win.close(_this);
											win.callFuc();
											dataGrid.load(_projectId);
										}else{
											layer.msg(_restmp.result.errorCode);
										}
										
									}else{
										layer.msg("上传失败");
									}
								},
								BeforeUpload:function(up){
									
									var form = {
											"fileSource" : $(_this.id).find("input[name='win_fileSource']:checked").val(),
											"fileType" : $(_this.id).find("#win_fileType").val(),
											"fileWorktype" : $(_this.id).find("#win_fileWorkType").val(),
											"projectId" : $(_this.id).find("#win_sopProjectId").data("tid"),
											"isProve" : $(_this.id).find("#win_isProve").attr("checked"),
											"remark" : $(_this.id).find("#win_FILELIST").val(),
											"progress" : _formdata._progress
									};
									
									$(_this.id).showLoading(
											 {
											    'addClass': 'loading-indicator'						
											 });
									
		
//									up.settings.headers = from 
									up.settings.multipart_params = form;
								},
								Error: function(up, err) {
									$(_this.id).hideLoading();
									layer.msg(err.message);
//									document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
								}
							}
						});							
						//初始化plupload插件
						uploader.init();								
					},
					hideback:function(t){
						
					}
					
				});
			},
			fillData : function(_this,_formdata){
				//业务分类隐藏域
//				var _fileWorkTypeId = $(_this.id).find("#fileWorkTypeId");
//				_fileWorkTypeId.val(_formdata._workTypeId);
//				var $fileSource = $(_this.id).find("input[name='win_fileSource']");
				var $fileWorkType = $(_this.id).find("#win_fileWorkType");
				var $fileType = $(_this.id).find("#win_fileType");
				var $sopProjectId = $(_this.id).find("#win_sopProjectId");
				var $searchProjectBtn = $(_this.id).find("#win_searchProjectBtn");
				
				var $isProve = $(_this.id).find("#win_isProve");
				var $remark = $(_this.id).find("#win_FILELIST"); 
				

				
				//项目文本域(此处先写ID)
				$sopProjectId.val(_formdata._projectId);
				//项目ID隐藏域
				$sopProjectId.val(_formdata._projectId);
				
				
				//文档来源
				if(typeof(_formdata._fileSource) != "undefined"){
					$(_this.id).find("input[name='win_fileSource'][value='"+_formdata._fileSource+"']").attr("checked",true);
				}
				//文档分类
				if(_formdata._fileType){
					$fileType.val(_formdata._fileType);
					$fileType.attr("disabled","disabled");
					$fileType.attr("class","disabled");
				}
				//业务分类
				if(_formdata._workType){
					$fileWorkType.val(_formdata._workType);
					$fileWorkType.attr("disabled","disabled");	
					$fileWorkType.attr("class","disabled");
				}
				//项目
				if(_formdata._projectId){
					//此处应该获取项目名称
					$sopProjectId.data("tid",_formdata._projectId);
					$sopProjectId.val(_formdata._projectName);
					$sopProjectId.attr("disabled","disabled");
					$searchProjectBtn.attr("disabled","disabled");
					
					
				}else{
					$searchProjectBtn.click(function(){
						commWin.init("searchProjectPanel",$sopProjectId);
					})
				}
				//签署证明
				if(typeof(_formdata._isProve) != "undefined"){
					if(_formdata._isProve == "hide"){
						$(_this.id).find("#win_isProve_div").hide();
					}else{
						$isProve.attr("checked",_formdata._isProve); 
						$isProve.attr("disabled","disabled");
						
					}
					
				}
				if(typeof(_formdata._remark) != "undefined"){
					if(_formdata._remark == "hide"){
						$remark.hide();
					}else{
						$remark.val(_formdata._remark);
					}
					
				}
					
				
			},
			initData : function(){
				sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorkType",null,win.initDataCallBack);
				sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,win.initDataCallBack);
			},
			initDataCallBack : function(data){	
				var _dom;
				var _type;
				switch(data.result.message){
				case "fileType" : 
					_dom = $("#uploadPanel").find("#win_fileType");
					break;	
				case "fileWorkType":
//					_type = "fileWorktype"
					_dom = $("#uploadPanel").find("#win_fileWorkType");
					break;
				}
				win.utils.each(data,_dom,null);
				},
			utils : {
				each : function(_data,_dom,type){
					_dom.empty();
					$.each(_data.entityList,function(){
						_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
					});
				}
			},
			//判断弹出框是否已经存在
			isCreate : function(){
				return $("#pop").length>0;
			},
			callFuc : function(){
				
			},
			//关闭弹出框
			close : function(_this){
					$(_this.id).remove();	
					//关闭对外接口
					_this.hideback.apply(_this);
					//判断是否关闭背景
					if($(".pop").length==0){
						$("#popbg").hide();	
					}
			},
			//若存在打开弹出框
			open : function(_this){
				_this.show();
			}
	};
	
	
	var mailWin = {
			init : function(data){
				var rows = data._rows
				mailWin.callFuc = data._callFuc;
				$.popup({
					txt:$("#mail-dialog").html(),
					showback:function(){
						var _dialog = this;
						var opts = {
								rules : {
									toAddress:{
										required:true,
										emails:true
									}
									
								}
						};
						var valdator = $(_dialog.id).find('form').fxValidate(opts);
						var i =1;
						var ids = new Array();
						$.each(rows,function(){
							var $tr=$("<tr></tr>");
							$tr.append("<td>"+ i +"</td>");
							$tr.append("<td>"+ this.fWorktype +"</td>");
						//	$tr.append("<td>"+ getFileSize(this.fileLength) +"</td>");
							$(_dialog.id).find("#attach-table tbody").append($tr);
							ids.push(this.id);
							i++;
						});
						
						$(_dialog.id).find("#send-mail-btn").click(function(){
							if(!valdator.form())
							{
								return;
							}
							$(this).addClass('disabled');
						 	var $form = $(_dialog.id).find("#mail-form");
							var data = JSON.parse($form .serializeObject());
							data['templateIds']=ids;
							var url = platformUrl.fileSendEmail;
							sendPostRequestByJsonObj(
									url,
									data,
									function(data){
										if(data.status=="OK")
										{
											layer.msg("发送邮件成功.");
											mailWin.close(_dialog);
										}
										else
										{
											layer.msg("发送邮件失败.");
											$(_dialog.id).find("#send-mail-btn").removeClass('disabled');
										}
									}
							); 
						});
					}
				});
				
				
				
				
							
				
			},
			callFuc : function(){
				
			},
			//关闭弹出框
			close : function(_this){
					$(_this.id).remove();	
					//关闭对外接口
					_this.hideback.apply(_this);
					//判断是否关闭背景
					if($(".pop").length==0){
						$("#popbg").hide();	
					}
			},
			
	}
	
	
	
	
	function init(){
	}
$(document).ready(init());

