var searchPanel = {
		initData : function(){
			//档案类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,searchPanel.initDataCallBack);
			//业务类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorktype",null,searchPanel.initDataCallBack);
			//档案状态
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileStatus",null,searchPanel.initDataCallBack);
			
			$("#file_repository_search_form").find(".datepicker").val("");
			
			//注册发送邮件按钮
			$("#file-show-mail-btn").click(function(){
				var rows = $("#"+fileGrid.domid).bootstrapTable('getSelections');
				if(rows.length==0)
				{
					layer.msg('请选择档案。');
					return;
				}
				
				var i = 0;
				$.each(rows,function(){
					if(this.fileLength){
						i++;
						return false;
					}	
				});
				if(i == 0){
					layer.msg('无文件。');
					return;
				}
				/*for(var i=0;i<rows.length;i++){
					if(!rows[i].fileKey){
						layer.msg("发送邮件中含有缺失档案！");
						return;
					}
				}*/

				var data = {
						_rows : rows
				}
				
				
				mailWin.init(data);
			});
			
		},
		initDataCallBack : function(data){
			var _dom;
			var _type;
			var _domType;
			var _domName;
			switch(data.result.message){
			case "fileType" : 
				_dom = $("#search_file_type");
				_domType = "radio";
				break;
			case "fileWorktype":
				_dom = $("#search_file_worktype");
				break;
			case "fileStatus":
				_dom = $("#search_file_status");
				_domType = "radio";
				break;
			default :
				
			}
			_domName = "search_" + data.result.message;
			utils.each(data,_dom,"all",_domType,_domName);
		}	
}
var fileGrid = {
	projectId : undefined,
	domid : undefined,
	progress : undefined,
	initFlag : false,
	init : 	function(data){
		 fileGrid.domid = data._domid;
		 fileGrid.projectId = data._projectId;
		 fileGrid.progress = (data._progress.split("_"))[1];
		 //2016-06-12新追加页面开发
		 if(!fileGrid.progress){
			 fileGrid.progress =  (data._progress.split(":"))[1];
		 }
		 if(data._callFuc){
			 fileGrid.callFuc = data._callFuc;
		 }
		 searchPanel.initData();
		 if(!fileGrid.initFlag){
			 $('#' + data._domid).bootstrapTable({
					url : platformUrl.searchSopFileList, // 请求后台的URL（*）
					queryParamsType : 'size|page', // undefined
					showRefresh : false,
					search : false,
					method : 'post', // 请求方式（*）
					// toolbar: '#toolbar', //工具按钮用哪个容器
					// striped: true, //是否显示行间隔色
					cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, // 是否显示分页（*）
					sortable : false, // 是否启用排序
					sortOrder : "asc", // 排序方式
					queryParams : fileGrid.queryParams,// 传递参数（*）
					sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, // 初始化加载第一页，默认第一页
					pageSize : 10, // 每页的记录行数（*）
					pageList : [ 10, 20 ], // 可供选择的每页的行数（*）
					strictSearch : true,
					clickToSelect : true, // 是否启用点击选中行
					// height: 460, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", // 每一行的唯一标识，一般为主键列
					cardView : false, // 是否显示详细视图
					detailView : false, // 是否显示父子表
					columns : [{
						field : 'checkbox',
						checkbox : "true",
						title : ''
					},{
						field : 'fSource',
						title : '文件来源'
					}, {
						field : 'fileUName',
						title : '起草者'
					}, {
						field : 'fType',
						title : '存储类型'
					}, {
						field : 'fWorktype',
						title : '业务分类'
					}, {
					    field: 'voucherFile',
					    title: '签署凭证',
					    events : fileGrid.operatorVEvents,
					    formatter: fileGrid.operateVFormatter 	
					  }, {
						field : 'updatedDate',
						title : '更新日期'
					}, {
						field : 'fileStatusDesc',
						title : '档案状态'
					}, {
						field : 'operate',
						title : '操作',
						events : fileGrid.updateEvents,
						formatter : fileGrid.updateFormatter

					}, {
						field : 'operate2',
						title : '附件查看',
						events : fileGrid.downloadEvents,
						formatter : fileGrid.downloadFomatter

					} ],
					onLoadSuccess : function(){
						if(typeof(isTransfering) != 'undefined' && isTransfering == 'true')
						{
							$('#' + data._domid).find('.fileuploadlink').parent().addClass('limits_gray');
							$('#' + data._domid).find('.fileupdatelink').parent().addClass('limits_gray');
						}
					}
				});
				 // 初始化查询按钮
				 $("#file_repository_btn").click(fileGrid.serarchData);
				 fileGrid.initFlag = true;
		 }else{
			 fileGrid.serarchData();
		 }
		 

		  
	},
	callFuc : undefined,
	updateFormatter : function(value,row,index){
		var tempPro;
		if(typeof(row.projectProgress) != "undefined"){
			tempPro = (row.projectProgress.split(":"))[1];
		}else{
			return '';
		}
		if(typeof(isTransfering) != 'undefined' && isTransfering == 'true')
		{
			return '';
		}
		var uploadOpt,uploadClass;
		if(row.fileKey){
			uploadOpt = "更新";
			uploadClass = "fileupdatelink";
		}else{
			uploadOpt = "上传";
			uploadClass = "fileuploadlink";
		}
		
		if(tempPro <= fileGrid.progress && row.isEdit == "true"){
			return [
		            '<a class= "' + uploadClass +' blue"  href="javascript:void(0)">',
		            uploadOpt,
		            '</a>  '
		        ].join('');
		}
		return '';
		
	},
	updateEvents : {
		//更新文档
		'click .fileupdatelink' : function(e, value, row, index){
			if($(this).parent().hasClass('limits_gray'))
			{
				return;
			}

			if(!fileGrid.callFuc){
        		fileGrid.callFuc = function(){
    				$("#powindow,#popbg").remove();
    				info(row.projectId);
    			}
        	}
			
        	formData = {
        			_fileKey : row.fileKey,
        			_fileSource : row.fileSource,
        			_fileType : "fileType:1",
        			_fileTypeAuto : true,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : "hide",
        			_remark : "hide",
    				callFuc : fileGrid.callFuc,
    				_url : platformUrl.commonUploadFile, //兼容老板插件
    				_localUrl : platformUrl.commonUploadFile
    		};
    		win.init(formData);
        },
        //上传文档
        'click .fileuploadlink' : function(e, value, row, index){
        	if($(this).parent().hasClass('limits_gray'))
			{
				return;
			}
        	var uploadUrl = undefined;
        	var uploadFormFuc = undefined;
        	if(row.isChangeTask == "true"){
        		uploadUrl = platformUrl.stageChange;
        		uploadFormFuc = function(dom){
					//本地上传回掉参数获取事件, 通过dom.find(“”)获取表单数据 jquery 语法
					var form = {
							"pid" : dom.find("#win_sopProjectId").data("tid"),
							"stage":row.projectProgress,
							"type":dom.find("input[name='win_fileSource']:checked").val(),
							"fileType":dom.find("#win_fileType").val(),
							"fileWorktype":dom.find("#win_fileWorkType").val()
					}
					return form;
				}
        	}else{
        		uploadUrl = platformUrl.commonUploadFile;
        	}
        	
        	if(!fileGrid.callFuc){
        		fileGrid.callFuc = function(){
    				$("#powindow,#popbg").remove();
    				info(row.projectId);
    			}
        	}
        	formData = {
        			_fileKey : row.fileKey,
        			_fileSource : row.fileSource,
        			_fileType : "fileType:1",
        			_fileTypeAuto : true,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : "hide",
        			_remark : "hide",
    				callFuc : fileGrid.callFuc,
    				_url : platformUrl.stageChange, //兼容老板插件
    				_localUrl : uploadUrl,
    				_getLocalFormParam : uploadFormFuc
    		};
    		win.init(formData);
        }
	},
	operatorVEvents : {
		//上传签署凭证文档
        'click .voucherfileuploadlink' : function(e, value, row, index){
        	if(!fileGrid.callFuc){
        		fileGrid.callFuc = function(){
    				$("#powindow,#popbg").remove();
    				info(row.projectId);
    			}
        	}
        	
        	formData = {
        			_fileKey : row.fileKey,
        			_fileSource : row.fileSource,
        			_fileType : "fileType:1",
        			_fileTypeAuto : true,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : true,
        			_remark : "hide",
    				callFuc : fileGrid.callFuc,
    				_url : platformUrl.stageChange, //兼容老板插件
    				_localUrl : platformUrl.stageChange,
    				_getLocalFormParam : function(dom){
    					//本地上传回掉参数获取事件, 通过dom.find(“”)获取表单数据 jquery 语法
    					var form = {
    							"pid" : dom.find("#win_sopProjectId").data("tid"),
    							"stage":row.projectProgress,
    							"type":dom.find("input[name='win_fileSource']:checked").val(),
    							"fileType":dom.find("#win_fileType").val(),
    							"fileWorktype":dom.find("#win_fileWorkType").val(),
    							"voucherType" : $("input[id='win_isProve']:checked").val()
    					}
    					return form;
    				}
    		};
    		win.init(formData);
        },
        'click .filedownloadlink': function (e, value, row, index) {
//			data = {
//					fileKey : row.fileKey,
//					fileName : row.fileName + "." + row.fileSuffix
//			};
			
			layer.msg('正在下载，请稍后...',{time:2000});
			var keyvalue;
			if(e.target.id=="sopfile"){
				keyvalue=row.id;
			}else if(e.target.id=="vsopfile"){
				keyvalue=row.voucherId + "?type=voucher"; 	
			}else{
				keyvalue="";
			}
			window.location.href=platformUrl.downLoadFile+'/'+keyvalue ;
        }
	},
	operateVFormatter : function(value, row, index){
		if(row.Vstatus=="false"){
			if(row.fileKey){
				return [
						'<a class="voucherfileuploadlink blue"  href="javascript:void(0)">上传</a>'
				        ].join('');
			}else{
				return [
						'暂不能操作'
				        ].join('');
			}
		}
		if(row.Vstatus=="true"){
			return [
				'<a class="filedownloadlink blue" id="vsopfile"  href="javascript:void(0)">查看</a>  '
		        ].join('');
		}
		if(row.Vstatus=="no"){
			return [
					'无'
			        ].join('');
		}
	
	},
	downloadFomatter : function(value, row, index){
		if(row.fileKey && row.fileValid==1){
			return [
			          '<a class="filedownloadlink blue" id ="sopfile"  href="javascript:void(0)">',
			          '查看',
			          '</a>  '
			       ].join('');
		}
		return '';
	},
	downloadEvents : {
		'click .filedownloadlink': function (e, value, row, index) {
//			data = {
//					fileKey : row.fileKey,
//					fileName : row.fileName + "." + row.fileSuffix
//			};
			
			layer.msg('正在下载，请稍后...',{time:2000});
			var keyvalue;
			if(e.target.id=="sopfile"){
				keyvalue=row.id;
			}else if(e.target.id=="vsopfile"){
				keyvalue=row.voucherId; 	
			}else{
				keyvalue="";
			}
			window.location.href=platformUrl.downLoadFile+'/'+keyvalue ;
        }
	},
	queryParams : function(params){
		var form = $("#file_repository_search_form").serializeObject();
		form = jQuery.parseJSON(form);
		params.fileSource = utils.confident(form.search_fileSource,"all");
		params.fileType = utils.confident(form.search_fileType,"all");
		params.fileWorktype = utils.confident(form.search_fileWorktype,"all");
		params.fileStatus = utils.confident(form.search_fileStatus,"all");
		var startTime = (new Date(form.file_startDate+' 00:00:00')).getTime();		
		var endTime = (new Date(form.file_endDate+' 23:59:59')).getTime(); 	
		if(startTime > endTime){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
		//兼容safari
		if(form.file_startDate>form.file_endDate){
			layer.msg("开始时间不能大于结束时间");
			return false;
		}
		
		params.startTime = startTime;
		params.endTime = endTime
		params.pageType = "dialog";
		params.projectId = fileGrid.projectId;
		return params;
	},
	serarchData : function(){
		$('#'+fileGrid.domid).bootstrapTable('refresh',fileGrid.queryParams);
	}
};





















var utils = {
		path : $("#pathInput").val(),
		each : function(_data,_dom,type,domType,domName){
			if(type=="all"){
				if(domType=="radio"){
					_dom.empty();
					if(domName == "search_fileType"){
						_dom.append("<dt>存储类型：</dt>")
					}else if(domName == "search_fileStatus"){
						_dom.append("<dt>档案状态：</dt>")
					}
					
					_dom.append("<dd><label for=''><input type='radio' name='"+domName+"' value='all' checked>不限</label></dd>");
				}else{
					_dom.empty();
					_dom.append("<option value='all'>全部</option>");
				}
				
			}
			if(domType=="radio"){
				$.each(_data.entityList,function(){
					if(this.code){
						_dom.append("<dd><label for=''><input type='radio' name='"+domName+"' value='"+this.code+"' >"+this.name+"</label></dd>");
					}else{
						_dom.append("<dd><label for=''><input type='radio' name='"+domName+"' value='"+this.id+"' >"+this.name+"</label></dd>");
					}
					
				});
			}else{
				$.each(_data.entityList,function(){
					if(this.code){
						_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
					}else{
						_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
					}
					
				});
			}
			
			
		},
		confident : function(value,tem){
			if(value==tem){
				return;
			}else{
				return value;
			}
		}
		
}
//function init(){
//}
//
//$(document).ready(init());