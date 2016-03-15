var searchPanel = {
		initData : function(){
			//档案类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileType",null,searchPanel.initDataCallBack,null);
			//业务类型
			sendGetRequest(platformUrl.dictFindByParentCode+"/fileWorkType",null,searchPanel.initDataCallBack,null);
			//所属事业线
			sendGetRequest(platformUrl.getDepartMentDict+"/department",null,searchPanel.initDataCallBack,null);
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
		serarchData : function(){
			 var fileSource = utils.confident($("input[name='source']:checked").val(),"all");
			 var fileType = utils.confident($("#searchFileType").val(),"all");
			 var projectId = $("#searchProjectId").val();
			 if(projectId=="") projectId=undefined;
			 var fileWorktype = utils.confident($("#searchFileWorktype").val(),"all");
			 var careerLine = utils.confident($("#searchCareerLine").val(),"all");
			 parameters = {query: 
			 {"fileType" : fileType,
		      "fileSource" : fileSource,
		      "projectId" : projectId,
		      "fileWorktype": fileWorktype,
		      "careerLine" : careerLine
		    	  }};
			 $('#fileGrid').bootstrapTable('refresh',parameters);	 
		 }
};

var fileGrid = {
	init : 	function(){
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
//		      queryParams: oTableInit.queryParams,//传递参数（*）
		      sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
		      pageNumber:1,            //初始化加载第一页，默认第一页
		      pageSize: 10,            //每页的记录行数（*）
		      pageList: [10, 20],    //可供选择的每页的行数（*）
		      strictSearch: true,
		      clickToSelect: true,        //是否启用点击选中行
//		      height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		      uniqueId: "sopFileId",           //每一行的唯一标识，一般为主键列
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
		        title: '档案管理'
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
		 $("#searchBtn,#openBtn").click(searchPanel.serarchData);

		  
	},
	operateFormatter : function(value, row, index){
		
		if(row.fileKey){
			return [
		            '<a class="link"  href="javascript:void(0)">',
		            '下载',
		            '</a>  '
		        ].join('');
		}
		return '';
	},
	operateEvents : {
		'click .link': function (e, value, row, index) {
			window.location.href=platformUrl.downLoadFile+'/'+ row.id;
        }
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
	createMenus(14);
	searchPanel.initData();
	fileGrid.init();
	$("#uploadOpenBtn").click(function(){
		formData = {
				callFuc : function(){
					searchPanel.serarchData();
				}
		};
		win.init(formData);
	})
	
}

$(document).ready(init());