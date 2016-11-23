/**
 * 
 */


var meetingSearchPanel = {
		meetingTypeList : undefined,
		initData : function(){
			//初始化查询按钮
			$("#searchBtn").click(meetGrid.searchData);
			
			
			
			
			$("#addPostMeetingBtn").click(function(){
				if($(this).hasClass('limits_gray'))
				{
					return;
				}
				var formdata = {
						 isEdit : true
				}
				editPostMeetingDialog.init(formdata);
			});
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
				$("#search_meet").find(":checkbox").change(meetGrid.searchData);
				//初始化日历控件
				$("#post_meeting_anlysis").find(".datepicker").val("");
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
			    	  pageMode = false;
			    	  if(isTransfering == 'true')
		    		  {
			    		  $.each($('#meetGrid tr'),function(){
			    			  $(this).find('td:last').addClass('limits_gray');
			    		  });
		    		  }
			      },
			      columns: [
					{
			        field: 'meetingName',
			        title: '会议名称',
			        formatter : meetGrid.meetingNameFormatter,
			        events : meetGrid.meetingNameEvents
			      }, {
			        field: 'meetingType',
			        title: '类型',
			        formatter: meetGrid.meetingTypeFormatter	
			      }, {
			        field: 'createUName',
			        title: '上传人'
			      }, {
			        field: 'meetingDateStr',
			        title: '会议时间',
			        formatter:function(val){
			        	if(val != null)
		        		{
			        		return val.substr(0,16);
		        		}
			        }
			      }, {
			    	  field: 'operate', 
			    	  title: '操作', 
			    	  align: 'left', 
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
			var retStr = "<a class='meet_show_detail blue'  href='javascript:void(0)'>";
			$.each(meetingSearchPanel.meetingTypeList,function(){
				if(row.meetingType == this.code){
					retStr += this.name;
					return false;
				}
			});
			retStr += "纪要" + value;
			retStr += "</a> ";
			return retStr;
//			var retStr = row."";
		},
		operateFormatter : function(value, row, index){
			var btns = "";
			if(isCreatedByUser == "true" && isTransfering != 'true')
			{
				btns += '<a class="meet_edit blue"  href="javascript:void(0)">编辑</a>  ';
				btns += '<a class="meet_delete blue" href="javascript:void(0)">删除</a>  ';
			}
			if(row.hasFile && row.hasFile=="true"){
				btns += '<a class="meet_download blue" href="javascript:void(0)">下载附件</a>  '
			}
			
			return btns;
		},
		meetingNameEvents : {
			'click .meet_show_detail': function (e, value, row, index) {
				var retStr = "";
				/*$.each(meetingSearchPanel.meetingTypeList,function(){
					if(row.meetingType == this.code){
						retStr += this.name;
						return false;
					}
				});*/
				retStr = $(this).text();
				var formdata = {
						 id : row.id,
						 meetingDateStr : row.meetingDateStr,
						 meetingName : row.meetingName,
						 meetingType : row.meetingType,
						 meetingNotes : row.meetingNotes,
						 isEdit : false,
						 popName : retStr
				}
				editPostMeetingDialog.init(formdata);		
	        }
		},
		operateEvents : {
			
			'click .meet_edit': function (e, value, row, index) {
				if($(this).parent().hasClass('limits_gray'))
				{
					return;
				}
				var formdata = {
						 id : row.id,
						 meetingDateStr : row.meetingDateStr,
						 meetingName : row.meetingName,
						 meetingType : row.meetingType,
						 meetingNotes : row.meetingNotes,
						 isEdit : true,
						 popName : "编辑会议纪要"
				}
				editPostMeetingDialog.init(formdata);
//				
			
	        },
	        'click .meet_delete': function (e, value, row, index) {
	        	if($(this).parent().hasClass('limits_gray'))
				{
					return;
				}
	        	layer.confirm('是否删除事项?', {
	        		  btn: ['确定', '取消'] //可以无限个按钮
	        		}, function(index, layero){
	        			sendGetRequest(platformUrl.deletePostMeeting + "/" + row.id ,null,function(data){
	    	        		if(data.result.status=="OK"){
	    						layer.msg("删除成功");
	    						meetGrid.searchData();
	    						//刷新投后运营简报信息
	    						setThyyInfo();
	    						
	    					}else{
	    						layer.msg(data.result.errorCode);
	    					}
	    	        	});
	        		  //按钮【按钮一】的回调
	        		}, function(index){
	        		  //按钮【按钮二】的回调
	        		});
 	
	        },
	        'click .meet_download': function (e, value, row, index) {
	        	try {
	        		var url = Constants.sopEndpointURL + '/galaxy/project/postOperation/downloadBatchFile'+"/"+row.id;
	        		layer.msg('正在下载，请稍后...',{time:2000});
	        		window.location.href=url+"?sid="+sessionId+"&guid="+userId;
	        	} catch (e) {
	        		layer.msg("下载失败");
	        	}
	        }
		},
		queryParams : function(params){
			if(pageMode){
				params.pageNum = 0;
			}
			var searchForm = $("#search_meet").serializeObject();
			searchForm = jQuery.parseJSON(searchForm);
			var startTime = (new Date(searchForm.meet_startDate+' 00:00:00'));		
			var endTime = (new Date(searchForm.meet_endDate+' 23:59:59')); 	
			if(startTime > endTime){
				layer.msg("开始时间不能大于结束时间");
				return false;
			}
			//兼容safari
			if(parseInt(searchForm.meet_startDate)>parseInt(searchForm.meet_endDate)){
				layer.msg("开始时间不能大于结束时间");
				return false;
			}
			params.projectId = meetGrid.projectId;
			//bootstrap 对日期要剪掉8个小时么? 源码是怎么写的有时间阅读以下
			if(!searchForm.meet_startDate == ""){
				params.startTime = searchForm.meet_startDate;
			}
			if(!searchForm.meet_endDate == ""){
				params.endTime = searchForm.meet_endDate;
			}
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
			pageMode = true;
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
		 * popName : 对话框名称
		 * 
		 * */
		init : function(_formdata){
			
			$.getHtml({
				url:platformUrl.toEditPostMeeting,//模版请求地址
				data:"",//传递参数
				okback:function(_this){
					if(_formdata.popName){
						$("#popup_name").html(_formdata.popName);
					}
					if($("#popup_name").text()=="添加运营会议纪要"){
						//添加运营会议纪要，未添加附件时，表头隐藏
						$("#filelist").css("display","none");
					}
					var operator = {
							initDataCallBack : function(data){
								if(data.result.status == 'OK'){
									//初始化会议类型
									$("#win_post_meeting_form").find("#edit_meeting_type").html("");
									var htmlPreFix = "<dt>会议类型 ：</dt>";
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
										$("#win_post_meeting_form").find("#meetingDateStr").val(_formdata.meetingDateStr.substr(0,16));
									}
									//会议纪要
									if(_formdata && _formdata.meetingNotes){
										$("#win_post_meeting_form").find("#meetingNotes").val(_formdata.meetingNotes);
									}
									//初始化按钮
//									$("#win_ok_btn").click(operator.save);
									$("#win_cancel_btn").click(function(){
										editPostMeetingDialog.close(_this);
									});
									if(!_formdata.isEdit){
										$("#win_post_meeting_form").find("#div_meetingDateStr").html("<dd>" + _formdata.meetingDateStr  +"</dd>")
										var meetingTypeName;
										$.each(meetingSearchPanel.meetingTypeList,function(){
											if(_formdata.meetingType == this.code){
												meetingTypeName = this.name;
												return false;
											}
										});
										$("#win_post_meeting_form").find("#edit_meeting_type").html("<dt class='edit_meeting_type_dt'>会议类型 ：</dt><dd class='edit_meeting_type_dd'>" + meetingTypeName + "</dd>");
										
										$("#win_post_meeting_form").find("#div_meetingNotes").html("<dd class='div_meetingNotes_dd'>" + _formdata.meetingNotes + "</dd>")
										
										$("#win_post_meeting_form").find("#win_ok_btn").hide()
										$("#win_post_meeting_form").find("#win_cancel_btn").hide();	
										$("#win_post_meeting_form").find("#choose_up_file").hide();
										$("#win_post_meeting_form").find("#div_show_up_file").hide();
										$("#win_post_meeting_form").find(".affrim_line").hide();
										
										
									}
									
								}else{
									layer.msg("初始化项目类型出错");
									return;
								}	
							},
							fileInitDataCallBack : function (data){
								var result = data.result.status;
								if(result == "OK"){
									var deliverInfo = data.entity;
									$.each(data.entity.files,function(){
										var but = "";
										if(_formdata.isEdit){
											 var but = 	"<button type='button' id='"+this.id+"btn' " +
											 			"onclick=del('"+this.id+"','"+this.fileName+"','textarea2')>" +
											 			"删除</button>" ;
											}
										var htm = "<tr id='"+this.id+"tr'>"+
														"<td>"+this.fileName+"."+this.fileSuffix+
															"<input type=\"hidden\" name=\"oldfileids\" value='"+this.id+"' />"+
														"</td>"+
														"<td>"+plupload.formatSize(this.fileLength)+"</td>"+
														"<td>"+ but +"</td>"+
														"<td>100%</td>"+
													"</tr>"
										$("#filelist").append(htm);
									});
									//无附件，隐藏表头
									var fileLen=$("#filelist tr[id]").length;
									console.log(fileLen)
									if(fileLen ==0){
										$("#filelist").css("display","none")
									}
								}
							},
							//回调函数
							saveCallBackFuc : function (data){
								editPostMeetingDialog.callFuc(data,_this);
								//刷新投后运营简报信息
	    						setThyyInfo();
							}
					};
					
					//初始化页面
					sendGetRequest(platformUrl.dictFindByParentCode+"/postMeetingType",null,operator.initDataCallBack);
					if(_formdata.id){
						//filelist(_formdata.id);
						sendGetRequest(Constants.sopEndpointURL + '/galaxy/project/postOperation/selectFile/'+_formdata.id,null,operator.fileInitDataCallBack);
					}
					
					toBachUpload(Constants.sopEndpointURL+'galaxy/sopFile/sendUploadByRedis',
							platformUrl.saveMeeting,"textarea2","select_btn","win_ok_btn","post-meeting-dialog","filelist",
							paramsContion,"win_post_meeting_form",operator.saveCallBackFuc);
					
				
					
					
					
				}//end okback 模版反回成功执行		
			});
		},
		callFuc : function(data,_this){
//			operator.saveCallBackFuc(data);

			layer.closeAll('loading');
			if(data.result.status=="OK"){
				layer.msg("保存成功");
				meetGrid.searchData();
				removePop1();
				editPostMeetingDialog.close(_this);
				//刷新投后运营简报信息
				setThyyInfo();
				
			}
		
		},
		//关闭弹出框
		close : function(_this){
				//启用滚动条
				 $(document.body).css({
				   "overflow-x":"auto",
				   "overflow-y":"auto"
				 });
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
	datePickerInitByHandler();
	pInfo = getProject();
	meetingSearchPanel.initData();
	
}

//获取 页面数据\保存数据
function paramsContion(){
	
	if(!beforeSubmit()){
		return false;
	}
//	$(".pop").showLoading(
//			 {
//			    'addClass': 'loading-indicator'						
//			 });
	var condition = JSON.parse($("#win_post_meeting_form").serializeObject());
	condition.fileReidsKey = Date.parse(new Date());
	condition.projectId = pInfo.id;
	condition.fileNum = $("#show_up_file").find("tr").length - 1;
	var oldFids=[];
	var oldfileids = $("input[name='oldfileids']");
	if(oldfileids && oldfileids.length > 0){
		
		$.each(oldfileids, function(i) { 
			var idVal = oldfileids[i].value;
		   	if(!isNaN(idVal)){
		   		oldFids.push(idVal);
		   	}
		});
		condition.fileIds = oldFids;
	}
	return condition;
}




var pageMode = false; 
var pInfo;


$(document).ready(init());