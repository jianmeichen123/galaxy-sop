/**
 * 
 */


var meetingSearchPanel = {
		initData : function(){
			//初始化日历控件
			$("#post_meeting_anlysis").find(".datepicker").val("");
			//初始化会议类型请求
			sendGetRequest(platformUrl.dictFindByParentCode+"/postMeetingType",null,searchPanel.initDataCallBack);
		},
		initDataCallBack : function(data){
			//初始化会议类型
			$("#search_meet_type").html("");
			var html = "";
			$.each(data.entityList,function(){
				html += "<label class='checkbox'><input type='checkbox' name='meetTypeList' checked='checked' value='" +
						this.code
						"'> " +
						this.name +
						"</label>";
			});
			$("#search_meet_type").html(html);
			
			meetGrid.init();
		}
}

var meetGrid = {
		init : function(dom){
			$('#meetGrid').bootstrapTable({
			      url : platformUrl.searchSopFileList,     //请求后台的URL（*）
			      queryParamsType: 'size|page', // undefined
			      showRefresh : false ,
			      search: false,
			      method : 'post',           //请求方式（*）
//			      toolbar: '#toolbar',        //工具按钮用哪个容器
//			      striped: true,           //是否显示行间隔色
			      cache: false,            //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			      pagination: true,          //是否显示分页（*）
			      sortable: false,           //是否启用排序
			      sortOrder: "asc",          //排序方式
			      queryParams: meetGrid.queryParams,//传递参数（*）
			      sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
			      pageNumber:1,            //初始化加载第一页，默认第一页
			      pageSize: 10,            //每页的记录行数（*）
			      pageList: [10, 20],    //可供选择的每页的行数（*）
			      strictSearch: true,
			      clickToSelect: true,        //是否启用点击选中行
//			      height: 460,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
			      uniqueId: "id",           //每一行的唯一标识，一般为主键列
			      cardView: false,          //是否显示详细视图
			      detailView: false,          //是否显示父子表
			      columns: [
					{
			        field: 'meetingName',
			        title: '会议名称'
			      }, {
			        field: 'meetingType',
			        title: '所属项目',
			        formatter: fileGrid.projectNameFormatter	
			      }, {
			        field: 'createUid',
			        title: '发起人（上传人）'
			      }, {
			        field: 'meetingDateStr',
			        title: '会议时间'
			      }, {
			    	  field: 'operate', 
			    	  title: '操作', 
			    	  align: 'center', 
			    	  events: meetGrid.operateEvents, 
			    	  formatter: meetGrid.operateFormatter 

			      }]
			    });
		},
		operateFormatter : function(value, row, index){
			return [
			        '<a class="meet_edit blue"  href="javascript:void(0)">编辑</a>  ',
		            '<a class="meet_delete blue" href="javascript:void(0)">删除</a>  ',
		            '<a class="meet_download blue" href="javascript:void(0)">下载附件</a>  ',
		        ].join('');
		},
		operateEvents : {
			
			'click .meet_edit': function (e, value, row, index) {
				
	        },
	        'click .meet_delete': function (e, value, row, index) {
				
	        },
	        'click .meet_download': function (e, value, row, index) {
				
	        }
		},
		queryParams : function(params){
			return params;
		}	
}



function init(){
	createMenus(5);
	meetingSearchPanel.initData();
}


$(document).ready(init());