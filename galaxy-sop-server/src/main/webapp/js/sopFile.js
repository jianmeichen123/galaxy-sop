var searchPanel = {
		initData : function(){
			
			sendPostRequestByJsonObj(platformUrl.sopFileCheckShow,null,searchPanel.initCheckShow);
			//档案类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,searchPanel.initDataCallBack);
			//业务类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorkType",null,searchPanel.initDataCallBack);
			//所属事业线
			sendGetRequest(platformUrl.getDepartMentDict+"/department",null,searchPanel.initDataCallBack);
		},
		initDataCallBack : function(data){
			var _dom;
			var _type;
			switch(data.result.message){
			case "fileType" : 
				_dom = $("#searchFileType");
				break;
			case "fileWorkType":
				_dom = $("#searchFileWorktype");
				break;
			default :
				_dom = $("#searchCareerLine")
			}
			utils.each(data,_dom,"all");
		},
		initCheckShow : function(data){
			if(data.result.status!='OK'){
				$("#srearch_careerline_div").hide();
			}
		},
		serarchData : function(){
			
			$('#fileGrid').bootstrapTable('refresh',fileGrid.queryParams);	 
		 }
};

var fileGrid = {
	init : 	function(){
		
//		$("html").showLoading(
//				 {
//				    'addClass': 'loading-indicator'
//								
//		});
		$('#fileGrid').bootstrapTable({
		      url : platformUrl.searchSopFileList,     //请求后台的URL（*）
		      queryParamsType: 'size|page', // undefined
		      showRefresh : false ,
		      search: false,
		      method : 'post',           //请求方式（*）
//		      toolbar: '#toolbar',        //工具按钮用哪个容器
//		      striped: true,           //是否显示行间隔色
		      cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		      pagination: true,          //是否显示分页（*）
		      sortable: false,           //是否启用排序
		      sortOrder: "asc",          //排序方式
		      queryParams: fileGrid.queryParams,//传递参数（*）
		      sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
		      pageNumber:1,            //初始化加载第一页，默认第一页
		      pageSize: 10,            //每页的记录行数（*）
		      pageList: [10, 20],    //可供选择的每页的行数（*）
		      strictSearch: true,
		      clickToSelect: true,        //是否启用点击选中行
//		      height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		      uniqueId: "id",           //每一行的唯一标识，一般为主键列
		      cardView: false,          //是否显示详细视图
		      detailView: false,          //是否显示父子表
		      columns: [{
		        field: 'id',
		        title: '序号'
		      }, {
		        field: 'careerLineName',
		        title: '所属业务线'
		      }, {
		        field: 'projectName',
		        title: '所属项目'
		      }, {
		        field: 'fSource',
		        title: '档案来源'
		      }, {
		        field: 'fileUName',
		        title: '起草者'
		      }, {
		        field: 'fType',
		        title: '存储类型'
		      }, {
		        field: 'fWorktype',
		        title: '业务分类'
		      }, {
		        field: 'updatedDate',
		        title: '更新日期'
		      }, {
		        field: 'fileStatusDesc',
		        title: '档案状态'
		      },{
		    	  field: 'operate', 
		    	  title: '操作', 
		    	  align: 'center', 
		    	  events: fileGrid.operateEvents, 
		    	  formatter: fileGrid.operateFormatter 

		      }]
		    });
		 //初始化查询按钮
		 $("#searchBtn").click(searchPanel.serarchData);
		 $('#fileGrid').on("load-success.bs.table", function (data) {
			    // ... 
//			 $(".ritmin").hideLoading();
			});
		 

		  
	},
	operateFormatter : function(value, row, index){
		
		if(row.fileKey && row.isEdit == "true"){
			return [
			        '<a class="fileupdatelink blue"  href="javascript:void(0)">',
			        '更新',
			        '</a>  ',
		            '<a class="filedownloadlink blue"  href="javascript:void(0)">',
		            '下载',
		            '</a>  '
		        ].join('');
		}else if(!row.fileKey && row.isEdit == "true"){
			return [
		            '<a class="fileupdatelink blue"  href="javascript:void(0)">',
		            '更新',
		            '</a>  '
		        ].join('');
		}else if(row.fileKey && row.isEdit == "false"){
			return [
			        '<a class="filedownloadlink blue"  href="javascript:void(0)">',
			        '下载',
			        '</a>  '
		        ].join('');
		}else if(!row.fileKey && row.isEdit == "false"){
			return '';
		}
		return '';
	},
	operateEvents : {
		'click .filedownloadlink': function (e, value, row, index) {
			window.location.href=platformUrl.downLoadFile+'/'+ row.id;
        },
        'click .fileupdatelink' : function(e, value, row, index){
        	formData = {
        			_fileSource : row.fileSource,
        			_workType : row.fileWorktype,
        			_projectId : row.projectId,
        			_projectName : row.projectName,
        			_isProve : undefined,
    				callFuc : function(){
    					searchPanel.serarchData();
    				},
    				_url : platformUrl.commonUploadFile
    		};
    		win.init(formData);
        }
	},
	queryParams : function(params){
		var fileSource = utils.confident($("input[name='source']:checked").val(),"all");
		var fileType = utils.confident($("#searchFileType").val(),"all");
		var projectName = $("#searchProjectId").val().trim();
		if(projectName=="") projectName=undefined;
		var fileWorktype = utils.confident($("#searchFileWorktype").val(),"all");
		var careerLine = utils.confident($("#searchCareerLine").val(),"all");
		params.fileType = fileType;
		params.fileSource = fileSource;
		params.projectName = projectName;
		params.fileWorktype = fileWorktype;
		params.careerLine = careerLine;
//		$('#fileGrid').bootstrapTable('refresh',parameters);	
		return params;
	},
	downloadCallBackfunction : function(data){
		alert(1)
	}
	
};











var utils = {
		path : $("#pathInput").val(),
		each : function(_data,_dom,type){
			_dom.empty();
			if(type=="all"){
				_dom.append("<option value='all'>--全部--</option>");
			}
			$.each(_data.entityList,function(){
				if(this.code){
					_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
				}else{
					_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
				}
				
			});
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
	
//	if(roleId == "")
	createMenus(14);
	searchPanel.initData();
	fileGrid.init();
	
	
	
	
	
	
}

$(document).ready(init());