var searchPanel = {
		initData : function(){
			//档案类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,searchPanel.initDataCallBack);
			//业务类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorktype",null,searchPanel.initDataCallBack);
			//档案状态
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileStatus",null,searchPanel.initDataCallBack);
			
			$("#file_repository_search_form").find(".datepicker").val("");
			
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
	init : 	function(data){
		searchPanel.initData();
		 fileGrid.domid = data._domid;
		 fileGrid.projectId = data._projectId;
		 $('#' + data._domid).bootstrapTable({
			url : platformUrl.searchSopFileList, // 请求后台的URL（*） +='?'+(new Date()).valueOf()
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
				field : 'updatedDate',
				title : '更新日期'
			}, {
				field : 'fileStatusDesc',
				title : '档案状态'
			}, {
				field : 'operate2',
				title : '附件查看',
				
				events : fileGrid.downloadEvents,
				formatter : fileGrid.downloadFomatter

			} ]
		});
		 // 初始化查询按钮
		 $("#file_repository_btn").click(fileGrid.serarchData);
	},
	updateFormatter : function(value,row,index){
		return [
	            '<a class="fileupdatelink blue"  href="javascript:void(0)">',
	            '更新',
	            '</a>  '
	        ].join('');
	},
	updateEvents : {
		'click .fileupdatelink' : function(e, value, row, index){
        	formData = {
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : undefined,
    				callFuc : function(){
    					fileGrid.serarchData();
    				},
    				_url : platformUrl.commonUploadFile
    		};
    		win.init(formData);
        }
	},
	downloadFomatter : function(value, row, index){
		if(row.fileKey){
			return [
			          '<a class="filedownloadlink blue"  href="javascript:void(0)">',
			          row.fileName,
			          '</a>  '
			       ].join('');
		}
		return '';
	},
	downloadEvents : {
		'click .filedownloadlink': function (e, value, row, index) {
			window.location.href=platformUrl.downLoadFile+'/'+ row.id;
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
		params.endTime = endTime;
		params.pageType = "dialog";
		params.projectId = fileGrid.projectId;
		return params;
	},
	serarchData : function(){
		$('#'+fileGrid.domid).bootstrapTable('refresh',fileGrid.queryParams);
	}
};


function ReplaceAll(str, sptr, sptr1){
	while (str.indexOf(sptr) >= 0){
	   str = str.replace(sptr, sptr1);
	}
	return str;
}

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
function init(){
}

$(document).ready(init());