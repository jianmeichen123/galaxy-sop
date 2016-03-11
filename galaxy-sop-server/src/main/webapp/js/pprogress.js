	function projectProgress9(id){
		$.ajax({
			url : sopContentUrl + '/galaxy/project/progress/proFileInfo/'+id+'/9',
			data : null,
			async : false,
			type : 'GET',
			dataType : "json",
			contentType:"application/json; charset=UTF-8",
			cache : false,
			error:function(){     
		        alert('error');     
		    }, 
			success : function(data) {
				 var json = eval(data);
				 var dataList=json.entityList;
				 var htmlstart='<table width=\"100%" cellspacing="0" cellpadding="0" >'+
					             '<thead>'+
					                '<tr>'+
					                 '<th>业务分类</th>'+
					                 '<th>创建日期</th>'+
					                 '<th>存储类型</th>'+
					                 '<th>更新日期</th>'+
					                 '<th>催办</th>'+
					                 '<th>查看附件</th>'+
					                 '</tr>'+
					            '</thead>'+                                                                                                                                   
					             '<tbody>';
								for(var p in dataList){
											var typehtml = "";
											if (typeof(dataList[p].fType) == "undefined") { 
												typehtml ='<td></td>';
											}else{
												typehtml = '<td>'+dataList[p].fType+'</td>';
											}
											var handlehtml = "";
											
											if (dataList[p].fileStatusDesc == "缺失") { 
												handlehtml ='<td><a href="javascript:; " class="blue">催办</a></td>';
											}else{
												handlehtml = '<td>'+dataList[p].fileName+'</td>';
											}
											var endhtml ="";
											if (dataList[p].fileStatusDesc == "缺失") { 
												endhtml ='<td></td>';
											}else{
												endhtml = '<td><a href="javascript:; " onclick="handleDownload('+dataList[p].id+');" class="blue">附件</a></td>';
											}
											
											htmlstart +='<tr>'+
											'<td>'+dataList[p].fWorktype+'</td>'+
											'<td>'+dataList[p].createDate+'</td>'+
											typehtml+
											'<td></td>'+
											handlehtml+   
											endhtml+   
											'</tr>';   
											
								}
					var htmlend= '</tbody></table>';
					$("#projectProgress_9_con").html(htmlstart+htmlend);
			}
		});
	}
	function projectProgress5(id){
		$.ajax({
			url : sopContentUrl + '/galaxy/project/progress/proFileInfo/'+id+'/5',
			data : null,
			async : false,
			type : 'GET',
			dataType : "json",
			contentType:"application/json; charset=UTF-8",
			cache : false,
			error:function(){     
		        alert('error');     
		    }, 
			success : function(data) {
				 var json = eval(data);
				 var dataList=json.entityList;
					for(var p in dataList){
						var handlefile="";
				        if (dataList[p].fileStatusDesc == "缺失") { 
				        	handlefile ='<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn" onclick="init('+id+','+1+','+1+');">上传投资意向书</a></td>';
						}else{
							handlefile = '<td><a href="javascript:; " class="pubbtn fffbtn llpubbtn">更新投资意向书</a><a  href="javascript:; " class="pubbtn fffbtn lpubbtn" onclick="init('+id+','+2+');">上传签署证明</a></td>';
						}
						 var htmlhead = '<div class="btnbox_f btnbox_f1 btnbox_m clearfix">'+
					        '<a href="javascript:;" class="pubbtn fffbtn llpubbtn">下载投资意向书模板</a>'+
					        handlefile+
					        '</div>'+
					        '<div class="process clearfix">'+
					        '<h2>投资意向书盖章流程</h2>'+
					        '<img src="img/process.png" alt="">'+
					        '</div>';
					        
						 var htmlstart=htmlhead+'<table width=\"100%" cellspacing="0" cellpadding="0" >'+
							             '<thead>'+
							                '<tr>'+
							                 '<th>业务分类</th>'+
							                 '<th>创建日期</th>'+
							                 '<th>存储类型</th>'+
							                 '<th>更新日期</th>'+
							                 '<th>催办</th>'+
							                 '<th>查看附件</th>'+
							                 '</tr>'+
							            '</thead>'+                                                                                                                                   
							             '<tbody>';
								var typehtml = "";
								if (typeof(dataList[p].fType) == "undefined") { 
									typehtml ='<td></td>';
								}else{
									typehtml = '<td>'+dataList[p].fType+'</td>';
								}
								var handlehtml ="";
								
								if (dataList[p].fileStatus == "fileStatus:1") { 
									handlehtml ='<td><a href="javascript:; " class="blue">催办</a></td>';
								}else{
									handlehtml = '<td>'+dataList[p].fileName+'</td>';
								}
								
								var endhtml ="";
								if (dataList[p].fileStatusDesc == "缺失") { 
									endhtml ='<td></td>';
								}else{
									endhtml = '<td><a href="javascript:;" onclick="handleDownload('+dataList[p].id+');" class="blue">附件</a></td>';
								}
								
								htmlstart +='<tr>'+
								'<td>'+dataList[p].fWorktype+'</td>'+
								'<td>'+dataList[p].createDate+'</td>'+
								typehtml+
								'<td></td>'+
								handlehtml+   
								endhtml+   
								'</tr>';   
								
					}
					var htmlend= '</tbody></table>';
					$("#projectProgress_5_con").html(htmlstart+htmlend);
			}
		});
	
	}
	
	function init(id,type,flag){
		var urls ='';
		if(type == '1'){
			urls = sopContentUrl + '/galaxy/sopFile/projectFileUpload/';
		}
		if(type == '2'){
			urls = sopContentUrl + '/galaxy/sopFile/commonUpload/';
		}
		var utils = {
				path : $("#pathInput").val(),
				each : function(_data,_dom,type){
					_dom.empty();
					$.each(_data.entityList,function(){
							_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
							/***
							if(flag == '1'){
								$("#fileWorkType option[value='fileWorktype:5']").attr("selected","selected");
							}
							if(flag == '2'){
								$("#fileWorkType option[value='fileWorktype:1']").attr("selected","selected");
							}***/
					});
				}
		}
			
		//上传弹出框
		var popPanel = {
				init : function(){
						//判断对话框是否存在
						if(popPanel.isCreate()){
							alert("打开popup面板");
							$("#popbg,#pop").show();
						}else{
							$.popup({
								txt : $("#addFile").html(),
								showback:function(){

									var _this = this;
									//plupload上传对象初始化
									var uploader = new plupload.Uploader({
										runtimes : 'html5,flash,silverlight,html4',
										browse_button : $(_this.id).find("#selectBtn")[0], // you can pass in id...
										url : urls,
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
													$(_this.id).find("#fileTxt").val(file.name);
												});
											},
											UploadProgress: function(up, file) {
											},
											FileUploaded:function(up,file,result){
												if(result.status==200){
													var _restmp = $.parseJSON(result.response);
													if(_restmp.result.status == "OK"){
														alert("上传成功");
														popPanel.close();
														
													}else{
														alert(_restmp.result.message);
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
														"projectId" : id	
												}
												
												up.settings.multipart_params = form;
											},
											Error: function(up, err) {
												alert("上传失败"+err);
//												document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
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
				},
				initData : function(_this){
					sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,popPanel.initDataCallBack,null);
					sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorkType",null,popPanel.initDataCallBack,null);
				},
				initDataCallBack : function(data){
					var _dom;
					var _type;
					switch(data.result.message){
					case "fileType" : 
						_dom = $("#addFile").find("#fileType");
						break;
					case "fileWorkType":
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
		//初始化上传面板页面数据
		popPanel.initData();
		//弹出层初始化
		popPanel.init();
		
		
	}
	
	function handleDownload(id)
	{
		var url = "<%=path %>"+platformUrl.downLoadFile+"/"+id;
		window.location.href=url;
	}
	
	   //页面传参
    function queryParams(params) {
		var projectIdstr=$("#project_id").val();
    	return {
	    	pageSize: params.limit,
	    	pageNum: params.offset,
	    	order: params.order,
	    	projectId:projectIdstr,
    	};
    }