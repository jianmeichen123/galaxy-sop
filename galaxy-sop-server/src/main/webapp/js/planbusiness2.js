/**
 * 历史计划列表
 * param : 
 * 		_domid : grid列表domid
 * 		_projectId :  项目ID
 */
var planGrid = {
	projectId : undefined,
	domid : undefined,
	init : 	function(data){
		
		
		var gridFormatter = {
				fileNameFormatter : function(value,row,index){
					return row.fileName + "." + row.fileSuffix;
				},
				operatorFormatter : function(value,row,index){
					if(row.fileKey){
						return [
								'<a class="downloadlink blue"  href="javascript:void(0)">下载</a>'
							   ].join('');
					}
				},
				operatorEvent : {
					'click .updatelink': function (e, value, row, index) {
						 var formData = {
								    _fileId : row.id,
				        			_fileKey : row.fileKey,
				        			_fileSource : row.fileSource,
				        			_fileType : "fileType:1",
				        			_workType : "fileWorktype:12",
				        			_fileTypeAuto : true,
				        			_projectId : initPage.projectId,
				        			_projectName : initPage.projectName,
				        			_remark : "hide",
				    				callFuc : function(){
				    					/*$("#powindow").remove();
				    					$("#popbg").remove();
				    					refreshData();*/
				    					window.location.reload(platformUrl.projectDetail + initPage.projectId);
				    				},
				    				_url : platformUrl.commonUploadFile, //兼容老板插件
				    				_localUrl : platformUrl.commonUploadFile
				    		};
							if('vsopfile'==e.currentTarget.id){
								//签署凭证
								formData._isProve = true;
							}else{
								formData._isProve = "hide";
							}
				    		win.init(formData);
			        },
					 'click .downloadlink': function (e, value, row, index) {
							layer.msg('正在下载，请稍后...',{time:2000});
							window.location.href=platformUrl.downLoadFile+'/'+row.id ;
				        }
				}
		}
		
		 planGrid.domid = data._domid;
		 planGrid.projectId = data._projectId;
		 $('#' + data._domid).bootstrapTable({
			url : platformUrl.searchBusinessPlanHistory, // 请求后台的URL（*）
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
			//文件名称，状态，更新时间，下载
			//fileName + fileSuffix,fileStatusDesc,createDate
			columns : [{
				field : 'fileName',
				title : '文档名称',
				formatter : 'fileNameFormatter'
			}, {
				field : 'fileStatusDesc',
				title : '状态'
			}, {
				field : 'createDate',
				title : '更新时间'
			},{
				field : 'operate',
				title : '操作',
				events : gridFormatter.operatorEvent,
				formatter : gridFormatter.operatorFormatter
			}]
		});	  
	},
	queryParams : function(params){
		params.projectId = planGrid.projectId;
		return params;

	}


};

function fileNameFormatter(value,row,index){
	if(row.fileName && row.fileName.length>50){
		var info="<span title='"+row.fileName+"'>"+row.fileName.substring(0,50)+"</span>"
	}else{
		var info="<span title='"+row.fileName+"'>"+row.fileName+"</span>"
	}
	return info;
}
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
							return;
						}
						window.location.href=platformUrl.downLoadFile+'/'+id ;
					},
					uploadBusinessPlan : function(){
						if($(this).hasClass('limits_gray'))
						{
							return;
						}
						if($(this).text()=='上传'){
							 $('.title_bj').html('上传商业计划书');							 
						 }else{
							 $('.title_bj').html('更新商业计划书');	
						 }
						formData = {
				    			_fileType : "fileType:1",
				    			_fileTypeAuto : true,
				    			_workType : "fileWorktype:12",
				    			_projectId : initPage.projectId,
				    			_projectName : initPage.projectName,
				    			_isProve : "hide",
				    			_remark : "hide",
								callFuc : function(){
									//window.location.reload(platformUrl.projectDetail + project.projectId);
									sendGetRequest(platformUrl.getBusinessPlanFile+"/"+initPage.projectId,null,initPage.initCallBack); //局部刷新
									
								},
								_url : platformUrl.commonUploadFile, //兼容老板插件
								_localUrl : platformUrl.commonUploadFile
						};
						win.init(formData);
					},
					businessPlanHistory : function(){
						var historyDialog = {
								
								init : function(){
									$.getHtml({
										url:platformUrl.toBusinessPlanHistory,//模版请求地址
										data:"",//传递参数
										okback:function(){
											var _this = this;											
											var formdata = {
													_domid : 'business_plan_grid',
													_projectId : initPage.projectId
											}
											planGrid.init(formdata);
										}//end okback 模版反回成功执行		
									});
								},
								close : function(_this){
									
								}
						}
						
						historyDialog.init();
						
					}
			}
			
			var uploadOperator;
			var dom = $("#" + initPage.domId);
			dom.html("");
			var planNameHtml = "<div><span class='img_box'></span><p id ='plan_name'></p></div>";
			var operatorHtml ="<div class='relative_box new_fctbox clearfix' id='plan_operator'>" +
									"</div>" ;
			var planUpdateTimeHtml = "<p class='plan_update_time'>更新时间：" +
									 	"<em id='plan_update_time'></em>" +
									 "</p>";

			var operatorDetailHtml = "";
			dom.html(planNameHtml  + operatorHtml + planUpdateTimeHtml);
			if(data.result.status=="OK"){
				//为空时候显示
				var grayClass = "";
				if(isTransferings=='true')
				{
					grayClass = " limits_gray event_none";
				}
				if(data.result.errorCode=="null"){
					$('.plan_name_all').hide();
					if(isCreatedByUser == 'true')
					{
						operatorDetailHtml ="<p class='no_plan"+grayClass+"'>暂无商业计划书，<a href='javascript:;' class='new1' data-btn='edit' id='upload_btn'>点我上传</a></p>";
					}else{
						operatorDetailHtml ="<p class='no_plan'>暂无商业计划书</p>";
					}
					dom.html(operatorDetailHtml);
				}else{
					$('.plan_name_all').show();
					//文档名称
					dom.find("#plan_name").html("《"+data.entity.fileName+"."+data.entity.fileSuffix+"》");
					//更新时间
					dom.find("#plan_update_time").html(data.entity.createDate);
					//操作类型
					if(isCreatedByUser == 'true')
					{
						operatorDetailHtml = "<span class='"+grayClass+"' style='float:left;'><a href='javascript:;' class='new1' data-btn='edit' id='upload_btn' data-toggle='tooltip' data-placement='top' data-trigger='hover' title='更新'></a></span>" ;
					}
					operatorDetailHtml += "<a href='javascript:;' class='f2' data-btn='describe' id='download_btn' data-toggle='tooltip' data-placement='top' data-trigger='hover' title='查看'></a>" +
										 "<a href='javascript:;' class='new2' data-btn='describe' id='show_history_btn' data-toggle='tooltip' data-placement='top' data-trigger='hover' title='查看历史'></a>";
					dom.find("#plan_operator").html(operatorDetailHtml);
				}
			}
			
			var project = {
					projectId : initPage.projectId,
					projectName : initPage.projectName	
			};
			operatorFuc.uploadBusinessPlan();
			//dom.find("#upload_btn").click(operatorFuc.uploadBusinessPlan);
			dom.find("#download_btn").click(operatorFuc.downloadBusinessPlan);
			dom.find("#show_history_btn").click(operatorFuc.businessPlanHistory);
			$("[data-toggle='tooltip']").tooltip();//提示
		}
}

function init(){
}

function refreshData(){
	var historyDialog = {
			init : function(){
				$.getHtml({
					url:platformUrl.toBusinessPlanHistory,//模版请求地址
					data:"",//传递参数
					okback:function(){
						var _this = this;											
						var formdata = {
								_domid : 'business_plan_grid',
								_projectId : initPage.projectId
						}
						planGrid.init(formdata);
					}//end okback 模版反回成功执行		
				});
			},
			close : function(_this){
			}
	}
	historyDialog.init();
}

$(document).ready(init());