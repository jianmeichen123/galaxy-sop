	var dataGrid = {
			load : function(projectId){
				alert("更新Grid");
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
								if(this.fileKey != null){	
									$tr.append('<td><a data-act="uploadFileBtn" data-tid='+this.id+' data-worktype='+this.fWorktype+' data-projectid='+this.projectId+' href="javascript:; " class="blue">上传</a></td>')
								}else{
									$tr.append('<td><a data-act="downloadFileBtn" data-tid='+this.id+' data-worktype='+this.fWorktype+' data-projectid='+this.projectId+' href="javascript:; " class="blue">查看</a></td>') ; 	
								}
								
								_tbody.append($tr);
								dataGrid.initPanel(this);
								
								
							});
						}
				);	
			},
			empty : function(_tbody){
				alert("清空Grid");
				_tbody.empty();
			},
			render : function(_tr,param){
				alert("渲染Grid");
			},
			initPanel : function(_item){
				$("[data-tid='"+_item.id+"'][data-act='uploadFileBtn']").click(function(){
					var _formdata = {
							_workType : _item.fWorktype,
							_workTypeId : _item.fileWorktype,
							_projectId : _item.projectId
						}
					win.initData();
					win.init(_formdata);
					
				});
				$("[data-tid='"+_item.id+"'][data-act='downloadFileBtn']").click(function(){
					alert("下载附件");
				});
				
			}

	};
	
	var win = {
			init : function(_formdata){
				$.popup({
					txt : $("#addFile").html(),
					showback:function(){
						alert("弹出层初始化");
						var _this = this;
						win.fillData(_this,_formdata);
						//plupload上传对象初始化
						var uploader = new plupload.Uploader({
							runtimes : 'html5,flash,silverlight,html4',
							browse_button : $(_this.id).find("#selectBtn")[0], // you can pass in id...
							url : platformUrl.simpleSopFileUpload,
							multipart:true,
							multi_selection:false,
							filters : {
								max_file_size : '10mb',
								mime_types: [
									{title : "Image files", extensions : "jpg,gif,png"},
									{title : "Zip files", extensions : "zip,rar"},
									{title : "Offices files", extensions : "doc,docx,excel"}
								]
							},
							init: {
								PostInit: function(){	
									//上传按钮点击事件开始上传
									$(_this.id).find("#uploadBtn").click(function(){
										alert("上传保存事件并关闭弹出框");
										uploader.start();
										return false;
									})
								},
								FilesAdded: function(up, files) {
									plupload.each(files, function(file) {
//										alert(111111);
//										document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
										$(_this.id).find("#fileTxt").val(file.name);
									});
								},
								UploadProgress: function(up, file) {
								},
								FileUploaded:function(up,file,result){
									if(result.status==200){
										var _restmp = $.parseJSON(result.response);
//										alert(_restmp.status)
//										alert(_restmp.message);
										var _projectId = _restmp.message;
										if(_restmp.status == "OK"){
											alert("上传成功");
											win.close(_this);
//											popEve.closepop();
											dataGrid.load(_projectId);
										}else{
											alert(result.response.errorCode);
										}
										
									}else{
										alert("上传失败");
									}
								},
								BeforeUpload:function(up){
									
									var form = {
											"fileSource" : $(_this.id).find("input[name='fileSource']:checked").val(),
											"fileType" : $(_this.id).find("#fileType").val(),
											"fileWorkType" : $(_this.id).find("#fileWorkTypeId").val(),
											"projectId" : $(_this.id).find("#sopProjectId").val(),	
									}
									
									up.settings.multipart_params = form;
								},
								Error: function(up, err) {
									alert("上传失败"+err);
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

				var _fileWorkType = $(_this.id).find("#fileWorkType");
				var _fileWorkTypeId = $(_this.id).find("#fileWorkTypeId");
				var _sopProject = $(_this.id).find("#sopProject");
				var _sopProjectId = $(_this.id).find("#sopProjectId");
				//业务分类id隐藏于
				_fileWorkTypeId.val(_formdata._workTypeId);
				//业务分类文本域
				_fileWorkType.val(_formdata._workType);
				//项目文本域(此处先写ID)
				_sopProject.val(_formdata._projectId);
				//项目ID隐藏域
				_sopProjectId.val(_formdata._projectId);
				_fileWorkType.attr("disabled","disabled");		
				_sopProject.attr("disabled","disabled");
				
				
			},
			initData : function(){
				sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,win.initDataCallBack,null);
			},
			initDataCallBack : function(data){	
				var _dom;
				var _type;
				switch(data.result.message){
				case "fileType" : 
					_dom = $("#fileType");
					break;		
				}
				utils.each(data,_dom,null);	
				},
			//判断弹出框是否已经存在
			isCreate : function(){
				return $("#pop").length>0;
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
	
	var utils = {
			each : function(_data,_dom,type){
				_dom.empty();
				$.each(_data.entityList,function(){
					_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
				});
			}
	}
	
	function init(){
		
	}
$(document).ready(init());

