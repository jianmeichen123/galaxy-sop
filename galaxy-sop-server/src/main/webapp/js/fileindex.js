
var fileGridIndex = {
	domid : undefined,
	init : 	function(data){
		fileGridIndex.domid = data._domid;
		 $('#' + data._domid).bootstrapTable({
			url : platformUrl.searchSopFileList, // 请求后台的URL（*）
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
			queryParams : fileGridIndex.queryParams,// 传递参数（*）
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
			onLoadSuccess : function(data){
				var obj=this;
				var result=data;
				if(typeof(result.pageList.total)=='undefined' || result.pageList.total<4){
					$("#file_gird_more").hide();
				}
			},
			columns : [{
				field : 'id',
				title : '序号',
				formatter : fileGridIndex.rowFormatter
			}, {
				field : 'fWorktype',
				title : '文档名称',
				formatter:fileGridIndex.Doctype
			},{
		        field: 'projectName',
		        title: '所属项目',
		        formatter:fileGridIndex.projectName
		    },{
				field : 'fType',
				title : '存储类型'
				}]
		});


		  
	},
	projectName :function(value,row,index){
		var str=row.projectName;
		if(str.length>10){
			subStr = str.substring(0,10);
			var str = "<span title='"+str+"'>"+subStr+"</span>";
			return str;
		}
		else{
			var str = "<span title='"+str+"'>"+str+"</span>";
			return str;
		}
	},
	Doctype :function(value,row,index){
		var str=row.fWorktype;
		if(str.length>5){
			subStr = str.substring(0,5);
			var str = "<span title='"+str+"'>"+subStr+"</span>";
			return str;
		}
		else{
			var str = "<span title='"+str+"'>"+str+"</span>";
			return str;
		}
	},
	rowFormatter : function(value, row, index){
		return [index+1].join('');
	},
	queryParams : function(params){
		params.pageType = "index";
		return params;
	},
	serarchData : function(){
		$('#'+fileGridIndex.domid).bootstrapTable('refresh',fileGridIndex.queryParams);
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
function init(){
}

$(document).ready(init());