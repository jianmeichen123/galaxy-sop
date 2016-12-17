var planGrid = {
	domid : undefined,
	/*添加商业计划上传表格
	 * param : 
	 *  domid : 表格domId
	 *  
	 * */
	init : 	function(data){
		var gridFormatter = {
				fileNameFormatter : function(value,row,index){
					console.log(row.fileSuffix);
					if(row.fileName){
						return row.fileName + row.fileSuffix;
					}else{
						return "-";
					}
					
				},
				operatorFormatter : function(value,row,index){
					var operator;
					if(row.fileName){
						operator = '更新附件';
					}else{
						operator = '上传附件';
					}
					return [
							'<a class="uploadlink blue ico_pgn 3333"  href="javascript:void(0)">'+ operator +'</a>'
							 ].join('');
					
				},
				operatorEvent : {
					 'click .uploadlink': function (e, value, row, index) {
						 if($(this).text()=='上传附件'){
							 $('.title_bj').html('上传商业计划书');							 
						 }else{
							 $('.title_bj').html('更新商业计划书');	
						 }
						 formData = {
					    			_fileType : "fileType:1",
					    			_fileTypeAuto : true,
					    			_workType : "fileWorktype:12",
					    			_projectId : '0',
					    			_projectName : "-",
					    			_isProve : "hide",
					    			_remark : "hide",
									callFuc : function(){
										console.log("刷新商业计划表格");
//										window.location.reload(platformUrl.projectDetail + project.projectId);
										$('#' + planGrid.domid).bootstrapTable('refresh',planGrid.queryParams);
									},
									_url : platformUrl.uploadBpToSession, //兼容老板插件
									_localUrl : platformUrl.uploadBpToSession
							};
							win.init(formData);
							
				        }
				}
		}
		
		 planGrid.domid = data._domid;
		 $('#' + data._domid).bootstrapTable({
			url : platformUrl.getBusinessPlanFileInSession, // 请求后台的URL（*）
			queryParamsType : 'size|page', // undefined
			showRefresh : false,
			search : false,
			method : 'post', // 请求方式（*）
			// toolbar: '#toolbar', //工具按钮用哪个容器
			// striped: true, //是否显示行间隔色
			cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : false, // 是否显示分页（*）
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
				field : 'createDate',
				title : '更新时间'
			}, {
				field : 'fileName',
				title : '文档名称',
				formatter : gridFormatter.fileNameFormatter
			}, {
				field : 'operate',
				title : '操作',
				events : gridFormatter.operatorEvent,
				formatter : gridFormatter.operatorFormatter
			}]
		});	  
	},
	queryParams : function(params){
		return params;
	}
};


function init(){
	console.log('init');
	var formdata = {
			_domid : 'plan_business_table'
	}
	
	planGrid.init(formdata);
}

$(document).ready(init());