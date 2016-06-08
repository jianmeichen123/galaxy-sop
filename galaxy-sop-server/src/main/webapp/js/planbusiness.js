var planGrid = {
	projectId : undefined,
	domid : undefined,
	progress : undefined,
	init : 	function(data){
		 planGrid.domid = data._domid;
		 planGrid.projectId = data._projectId;
		 planGrid.progress = (data._progress.split("_"))[1];
		 searchPanel.initData();
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
			queryParams : planGrid.queryParams,// 传递参数（*）
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
			    events : planGrid.operatorVEvents,
			    formatter: planGrid.operateVFormatter 	
			  }, {
				field : 'updatedDate',
				title : '更新日期'
			}, {
				field : 'fileStatusDesc',
				title : '档案状态'
			}, {
				field : 'operate',
				title : '操作',
				align : 'center',
				events : planGrid.updateEvents,
				formatter : planGrid.updateFormatter

			}, {
				field : 'operate2',
				title : '附件查看',
				align : 'center',
				events : planGrid.downloadEvents,
				formatter : planGrid.downloadFomatter

			} ]
		});
		 // 初始化查询按钮
		 $("#file_repository_btn").click(planGrid.serarchData);

		  
	},
	updateFormatter : function(value,row,index){
		var tempPro;
		if(typeof(row.projectProgress) != "undefined"){
			tempPro = (row.projectProgress.split(":"))[1];
		}else{
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
		
		if(tempPro <= planGrid.progress && row.isEdit == "true"){
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
    				callFuc : function(){
    					planGrid.serarchData();
    				},
    				_url : platformUrl.commonUploadFile, //兼容老板插件
    				_localUrl : platformUrl.commonUploadFile
    		};
    		win.init(formData);
        },
        //上传文档
        'click .fileuploadlink' : function(e, value, row, index){
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
    				callFuc : function(){
//    					planGrid.serarchData();
    					$("#powindow,#popbg").remove();
    					info(row.projectId);
    				},
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
    				callFuc : function(){
//    					planGrid.serarchData();
    					$("#powindow,#popbg").remove();
    					info(row.projectId);
    				},
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
		params.startTime = startTime;
		params.endTime = endTime
		params.pageType = "dialog";
		params.projectId = planGrid.projectId;
		return params;
	},
	serarchData : function(){
		$('#'+planGrid.domid).bootstrapTable('refresh',planGrid.queryParams);
	}
};
/**
 * 初始化商业计划模块
 * param :
 * 	_projectId : 项目ID
 *  _projectName : 项目名称
 *  _domId : 商业计划页面显示domId
 * 
**/
var initPage = {
		projectId : undefined,
		projectName : undefined,
		domId : undefined,
		init : function(data){
			initPage.projectId = data._projectId;
			initPage.projectName = data._projectName;
			initPage.domId = data._domId;
			sendGetRequest(platformUrl.getBusinessPlanFile+"/"+initPage.projectId,null,initPage.initCallBack);
		},
		initCallBack : function(data){
			
			var operatorFuc = {
					downloadBusinessPlan : function(){
						var id = data.entity.id;
						if(id == undefined || id == ""){
							alert("传入的文件ID为空");
							return;
						}
						window.location.href=platformUrl.downLoadFile+'/'+id ;
					},
					uploadBusinessPlan : function(){
						formData = {
				    			_fileType : "fileType:1",
				    			_fileTypeAuto : true,
				    			_workType : "fileWorktype:12",
				    			_projectId : initPage.projectId,
				    			_projectName : initPage.projectName,
				    			_isProve : "hide",
				    			_remark : "hide",
								callFuc : function(){
									console.log("刷新商业计划模块");
									window.location.reload(platformUrl.projectDetail + project.projectId);
								},
								_url : platformUrl.commonUploadFile, //兼容老板插件
								_localUrl : platformUrl.commonUploadFile
						};
						win.init(formData);
					}
			}
			
			var uploadOperator;
			var dom = $("#" + initPage.domId);
			dom.html("");
			var planNameHtml = "<li><span id ='plan_name'></span></li>";
			var planStatusHtml = "<li>" +
								 	"<span class='new_color_gray'>状态：</span>" +
								 	"<span class='new_color_black' id='plan_status'></span>" +
								 "</li>";
			var planUpdateTimeHtml = "<li>" +
									 	"<span class='new_color_gray'>更新时间：</span>" +
									 	"<span class='new_color_black' id='plan_update_time'></span>" +
									 "</li>";
			var operatorHtml = "<li class='new_ul_right'>" +
									"<span class='new_fctbox' id='plan_operator'>" +
									"</span>" +
								"</li>";
			var operatorDetailHtml = "";
			dom.html(planNameHtml + planStatusHtml + planUpdateTimeHtml + operatorHtml);
			if(data.result.status=="OK"){
				//为空时候显示
				if(data.result.errorCode=="null"){				
					operatorDetailHtml = "<a href='javascript:;' class='ico new1' data-btn='edit' id='upload_btn'>上传</a>";
				}else{
					//文档名称
					dom.find("#plan_name").html("《"+data.entity.fileName+"."+data.entity.fileSuffix+"》");
					//文档状态
					dom.find("#plan_status").html(data.entity.fileStatusDesc);
					//更新时间
					dom.find("#plan_update_time").html(data.entity.createDate);
					//操作类型
					operatorDetailHtml = "<a href='javascript:;' class='ico new1' data-btn='edit' id='upload_btn'>更新</a>" +
										 "<a href='javascript:;' class='ico f2' data-btn='describe' id='download_btn'>查看</a>" +
										 "<a href='javascript:;' class='ico new2' data-btn='describe' id='show_history_btn'>查看历史</a>";
				}
			}
			dom.find("#plan_operator").html(operatorDetailHtml);
			var project = {
					projectId : initPage.projectId,
					projectName : initPage.projectName	
			};
			dom.find("#upload_btn").click(operatorFuc.uploadBusinessPlan);
			dom.find("#download_btn").click(operatorFuc.downloadBusinessPlan);
			dom.find("#show_history_btn").click();
		}
}

function init(){
}

$(document).ready(init());