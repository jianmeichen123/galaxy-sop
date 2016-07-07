/**
 * 
 */


var meetingSearchPanel = {
		meetingTypeList : undefined,
		initData : function(){
			//初始化查询按钮
			$("#searchBtn").click(meetGrid.searchData);
			$("#addPostMeetingBtn").click(editPostMeetingDialog.init);
			//初始化会议类型请求
			sendGetRequest(platformUrl.dictFindByParentCode+"/postMeetingType",null,meetingSearchPanel.initDataCallBack);
			
			
		},
		initDataCallBack : function(data){
			if(data.result.status == 'OK'){
				meetingSearchPanel.meetingTypeList = data.entityList;
				//初始化会议类型
				$("#search_meet_type").html("");
				var html = "";
				$.each(data.entityList,function(index){
					html += "<label class='checkbox'><input type='checkbox' name='meetingTypeList' checked='checked' value='" +
							this.code +
							"'> " +
							this.name +
							"</label>";
				});
				$("#search_meet_type").html(html);
				
				meetGrid.init(pInfo);
			}else{
				layer.msg("初始化项目类型出错");
				return;
			}
			
		}
}

var meetGrid = {
		projectId : undefined,
		init : function(_project){
			if(!_project.id){
				layer.msg("参数错误");
			}
			meetGrid.projectId = _project.id;
			$('#meetGrid').bootstrapTable({
			      url : platformUrl.queryPostMeeting,     //请求后台的URL（*）
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
			      onLoadSuccess : function(data){
			    	  //初始化日历控件
						$("#post_meeting_anlysis").find(".datepicker").val("");
			      },
			      columns: [
					{
			        field: 'meetingName',
			        title: '会议名称'
			      }, {
			        field: 'meetingType',
			        title: '类型',
			        formatter: meetGrid.meetingTypeFormatter	
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
		meetingTypeFormatter : function(value,row,index){
			var retStr;
			$.each(meetingSearchPanel.meetingTypeList,function(){
				if(value == this.code){
					retStr = this.name;
					return false;
				}
			});
			console.log(value);
			return retStr;
		},
		operateFormatter : function(value, row, index){
			var btns = "";
			if("isCreatedByUser" == "true")
			{
				btns += '<a class="meet_edit blue"  href="javascript:void(0)">编辑</a>  ';
				btns += '<a class="meet_delete blue" href="javascript:void(0)">删除</a>  ';
			}
			btns += '<a class="meet_download blue" href="javascript:void(0)">下载附件</a>  '
			return btns;
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
			var searchForm = $("#search_meet").serializeObject();
			searchForm = jQuery.parseJSON(searchForm);
			var startTime = (new Date(searchForm.meet_startDate+' 00:00:00'));		
			var endTime = (new Date(searchForm.meet_endDate+' 23:59:59')); 	
			if(startTime > endTime){
				layer.msg("开始时间不能大于结束时间");
				return false;
			}
			//兼容safari
			if(searchForm.meet_startDate>searchForm.meet_endDate){
				layer.msg("开始时间不能大于结束时间");
				return false;
			}
			params.projectId = meetGrid.projectId;
			params.startTime = startTime;
			params.endTime = endTime;
			if(searchForm.meetingTypeList){
				//当选择一个复选框时，serializeObject方法
				if(!isArray(searchForm.meetingTypeList)){
					var arr = new Array();
					arr.push(searchForm.meetingTypeList);
					searchForm.meetingTypeList = arr;
				}
				params.meetingTypeList = searchForm.meetingTypeList;
				
			}
			
			return params;
		},
		searchData : function(){
			$('#meetGrid').bootstrapTable('refresh',meetGrid.queryParams);	 
		 }
}


var editPostMeetingDialog = {
		init : function(){
			$.getHtml({
				url:platformUrl.toEditPostMeeting,//模版请求地址
				data:"",//传递参数
				okback:function(_this){
//					console.log(111111);
//					$("#meetingDate").val("");
					//初始化页面
					sendGetRequest(platformUrl.dictFindByParentCode+"/postMeetingType",null,editPostMeetingDialog.initDataCallBack);
					
					
				}//end okback 模版反回成功执行		
			});
		},
		initDataCallBack : function(data){
			if(data.result.status == 'OK'){
				//初始化会议类型
				$("#win_post_meeting_form").find("#edit_meeting_type").html("");
				var htmlPreFix = "<dt>类型 ：</dt>";
				var html = "";
				$.each(data.entityList,function(index){
					var checked = "";
					if(index==0){
						checked = "checked='checked'";
					}
					html += "<dd><label for=''><input name='meetingType' type='radio' " +
							checked + 
							" value='" +
							this.code + 
							"' >" +
							this.name + 
							"</label></dd>";
						
						
				});
				$("#win_post_meeting_form").find("#edit_meeting_type").html(htmlPreFix +　html);
				
				var operator = {
						save : function(){
							if(beforeSubmit()){
								var form = $("#win_post_meeting_form").serializeObject();
								form = jQuery.parseJSON(form);
								form.projectId = pInfo.id;
								var nowTime = (new Date()).getTime();
								var meetingTime = (new Date(form.meetingDate)).getTime();
								if(meetingTime <= nowTime){
									layer.msg("会议时间应为未来的某一时刻");
									return false;
								}
								$(".pop").showLoading(
										 {
										    'addClass': 'loading-indicator'						
										 });
								sendPostRequestByJsonObj(platformUrl.saveMeeting,form,operator.saveCallBackFuc);

							}
						},
						saveCallBackFuc : function(data){
							if(data.result.status=="OK"){
								layer.msg("保存成功");
								//刷新投后运营简报信息
								setThyyInfo();
							}else{
								layer.msg(data.result.errorCode);
							}
							$(".pop").hideLoading();
							editPostMeetingDialog.close(_this);
							editPostMeetingDialog.callFuc();
						}
				};
				
				//初始化按钮
				$("#win_ok_btn").click(operator.save);
				$("#win_cancel_btn").click(function(){
					editPostMeetingDialog.close(_this);
				});
				
				
				
				
				
				
				
			}else{
				layer.msg("初始化项目类型出错");
				return;
			}
		},
		callFuc : function(){
			
		},
		//关闭弹出框
		close : function(_this){
				//关闭对外接口
				_this.hideback.apply(_this);
				$(_this.id).remove();
				$('.tip-yellowsimple').hide();
				//判断是否关闭背景
				if($(".pop").length==0){
					$("#popbg").hide();	
				}
		}
}


function init(){
	createMenus(5);
	pInfo = getProject();
	meetingSearchPanel.initData();
	
}

var pInfo;


$(document).ready(init());