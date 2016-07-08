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
			        title: '会议名称',
			        formatter : meetGrid.meetingNameFormatter
			      }, {
			        field: 'meetingType',
			        title: '类型',
			        formatter: meetGrid.meetingTypeFormatter	
			      }, {
			        field: 'createUName',
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
			return retStr;
		},
		meetingNameFormatter : function(value,row,index){
			var retStr;
			$.each(meetingSearchPanel.meetingTypeList,function(){
				if(row.meetingType == this.code){
					retStr = this.name;
					return false;
				}
			});
			retStr += "纪要" + value;
			return retStr;
//			var retStr = row."";
		},
		operateFormatter : function(value, row, index){
			var btns = "";
			if(isCreatedByUser == "true")
			{
				btns += '<a class="meet_edit blue"  href="javascript:void(0)">编辑</a>  ';
				btns += '<a class="meet_delete blue" href="javascript:void(0)">删除</a>  ';
			}
			btns += '<a class="meet_download blue" href="javascript:void(0)">下载附件</a>  '
			return btns;
		},
		operateEvents : {
			
			'click .meet_edit': function (e, value, row, index) {
				console.log("edit");
				var formdata = {
						 id : row.id,
						 meetingDateStr : row.meetingDateStr,
						 meetingName : row.meetingName,
						 meetingType : row.meetingType,
						 meetingNotes : row.meetingNotes,
				}
				editPostMeetingDialog.init(formdata);
				filelist(row.id);
			
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
		/*
		 * 参数formdata
		 * id : 会议ID,
		 * meetingDateStr :会议时间,
		 * meetingNotes : 会议纪要,
		 * meetingName : 会议名称,
		 * meetingType : 会议类型,
		 * meetingNotes : 会议纪要
		 * 
		 * */
		init : function(_formdata){
			$.getHtml({
				url:platformUrl.toEditPostMeeting,//模版请求地址
				data:"",//传递参数
				okback:function(_this){
					
					
					
//					console.log(111111);
//					$("#meetingDate").val("");		
					var operator = {
							initDataCallBack : function(data){
								if(data.result.status == 'OK'){
									//初始化会议类型
									$("#win_post_meeting_form").find("#edit_meeting_type").html("");
									var htmlPreFix = "<dt>类型 ：</dt>";
									var html = "";
									$.each(data.entityList,function(index){
										var checked = "";
										if(_formdata && _formdata.meetingType){
											//会议类型回填
											if(_formdata.meetingType == this.code){
												checked = "checked='checked'";
											}
										}else{
											if(index==0){
												checked = "checked='checked'";
											}
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
									
									//初始化页面
									//会议名称回填
									if(_formdata && _formdata.meetingName){
										$("#win_post_meeting_form").find("#meetingName").val(_formdata.meetingName);
									}
									//会议id
									if(_formdata && _formdata.id){
										$("#win_post_meeting_form").find("#id").val(_formdata.id);
									}
									
									//会议时间
									if(_formdata && _formdata.meetingDateStr){
										$("#win_post_meeting_form").find("#meetingDateStr").val(_formdata.meetingDateStr);
									}
									//会议纪要
									if(_formdata && _formdata.meetingNotes){
										$("#win_post_meeting_form").find("#meetingNotes").val(_formdata.meetingNotes);
									}
									//初始化按钮
									$("#win_ok_btn").click(operator.save);
									$("#win_cancel_btn").click(function(){
										editPostMeetingDialog.close(_this);
									});	
								}else{
									layer.msg("初始化项目类型出错");
									return;
								}	
							}
					};
					//初始化页面
					sendGetRequest(platformUrl.dictFindByParentCode+"/postMeetingType",null,operator.initDataCallBack);
					toBachUpload(Constants.sopEndpointURL+'galaxy/sopFile/sendUploadByRedis',
							platformUrl.saveMeeting,"textarea2","select_btn","win_ok_btn","post-meeting-dialog","filelist",
							paramsContion,"win_post_meeting_form",saveCallBackFuc);
					
					
				}//end okback 模版反回成功执行		
			});
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

//获取 页面数据\保存数据
function paramsContion(){
	
	if(!beforeSubmit()){
		return false;
	}
	$(".pop").showLoading(
			 {
			    'addClass': 'loading-indicator'						
			 });
	var condition = JSON.parse($("#win_post_meeting_form").serializeObject());
	condition.fileReidsKey = Date.parse(new Date());
	condition.projectId = pInfo.id;
	condition.fileNum = $("#show_up_file").find("tr").length - 1;
	return condition;
}
//回调函数
function saveCallBackFuc(data){
	$(".pop").hideLoading();
	if(data.result.status=="OK"){
		layer.msg("保存成功");
		meetGrid.searchData();
		removePop1();
		//刷新投后运营简报信息
		setThyyInfo();
		
	}
}

function filelist(id){
	var _url = Constants.sopEndpointURL + '/galaxy/project/postOperation/selectFile/'+row.id;
	sendGetRequest(_url, {}, function(data){
		var result = data.result.status;
		if(result == "OK"){
			var deliverInfo = data.entity;
			$.each(data.entity.files,function(){
				var but = type == 'v' ? " -" : "<button type='button' id='"+this.id+"btn' onclick=del('"+this.id+"','"+this.fileName+"','textarea2')>删除</button>" ;
				var htm = "<tr id='"+this.id+"tr'>"+
								"<td>"+this.fileName+
									"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
								"</td>"+
								"<td>"+this.fileLength+"</td>"+
								"<td>"+ but +"</td>"+
								"<td>100%</td>"+
							"</tr>"
				$("#filelist").append(htm);
				alert(9);
			});
		}
		});
}



var pInfo;


$(document).ready(init());