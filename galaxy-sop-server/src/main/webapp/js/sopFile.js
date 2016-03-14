function init(){
	createMenus(14);
	var utils = {
			path : $("#pathInput").val(),
			each : function(_data,_dom,type){
				_dom.empty();
				$.each(_data.entityList,function(){
					_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
				});
			}
	}
		
	//上传弹出框
	var popPanel = {
			init : function(){
//				alert("初始化面板");
				$("#uploadOpenBtn").click(function(){
					//判断对话框是否存在
					if(popPanel.isCreate()){
						//alert("打开popup面板");
						$("#popbg,#pop").show();
					}else{
//						alert("创建popup面板");
						$.popup({
							txt : $("#addFile").html(),
							showback:function(){
//								$("#popTxt").html($("#addFile").html());
								//alert("弹出层初始化");
								var _this = this;
								//plupload上传对象初始化
								var uploader = new plupload.Uploader({
									runtimes : 'html5,flash,silverlight,html4',
									browse_button : $(_this.id).find("#selectBtn")[0], // you can pass in id...
									url : utils.path + '/galaxy/sopFile/simpleUpload',
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
												//alert("上传保存事件并关闭弹出框");
												uploader.start();
												return false;
											})
										},
										FilesAdded: function(up, files) {
											plupload.each(files, function(file) {
//												alert(111111);
//												document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
												$(_this.id).find("#fileTxt").val(file.name);
											});
										},
										UploadProgress: function(up, file) {
										},
										FileUploaded:function(up,file,result){
											if(result.status==200){
												var _restmp = $.parseJSON(result.response);
												//alert(_restmp.status)
												if(_restmp.status == "OK"){
													alert("上传成功");
													popPanel.close();
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
													"fileWorkType" : $(_this.id).find("#fileWorkType").val(),
													"projectId" : "10"	
											}
											
											up.settings.multipart_params = form;
										},
										Error: function(up, err) {
											alert("上传失败"+err);
//											document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
										}
									}
								});							
								//初始化plupload插件
								uploader.init();								
							},
							hideback:function(t){
								
							}
							
						});
					}
					
				});
			},
			initData : function(_this){
//				alert(platformUrl.dictFindByParentCode);
				sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,popPanel.initDataCallBack);
				sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorkType",null,popPanel.initDataCallBack);
//				sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,popPanel.initDataCallBack,null);
//				sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,popPanel.initDataCallBack,null);
			},
			initDataCallBack : function(data){
				var _dom;
				var _type;
				switch(data.result.message){
				case "fileType" : 
//					_type = "fileType";
					_dom = $("#addFile").find("#fileType");
					break;
				case "fileWorkType":
//					_type = "fileWorktype"
					_dom = $("#addFile").find("#fileWorkType");
					break;
				}
				utils.each(data,_dom,null);
				
			},
			//判断弹出框是否已经存在
			isCreate : function(){
				return $("#pop").length>0;
			},
			//关闭弹出框
			close : function(){
				$("#popbg,#pop").hide();
			},
			//若存在打开弹出框
			open : function(){
				$("#popbg,#pop").show();
			}
	}
	
	//弹出层初始化
	popPanel.init();
	//初始化上传面板页面数据
	popPanel.initData();
}

$(document).ready(init());