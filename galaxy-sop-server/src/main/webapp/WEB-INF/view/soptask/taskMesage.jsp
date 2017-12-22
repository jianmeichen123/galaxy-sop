<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 	<div class="taskDetail-ritmin">
 		 <div class='taskDetail-mesage'>
 		 	<div class='taskDetail-mesage-top'>
 		 		<div class='message-detail-top'>
 		 			<div class='task-item task-item-left'>
		        		<ul>
		        			<li>项目名称：<span id="projectName"></span></li>
		        			<li>创建时间：<span id="createDate"></span></li>
		        			<li>事业部总经理：<span id="hhrName"></span></li>
		        		</ul>
		        	</div>
		        	<ul class='task-item'>
		        		<li>项目类型：<span id="type"></span></li>
		       			<li>投资事业线：<span id="projectCareerline"></span></li>
		       			<li>公司名称：<span id="projectCompany"></span></li>
		        	</ul>
		        	<ul class='task-item task-item-right'>
		        		<li>项目编码：<span id="projectCode"></span></li>
		       			<li>投资经理：<span id="createUname"></span></li>
		       			<c:if test="${ sessionScope.galax_session_user.id != task.assignUid }">
		       			<li>认领人：<span>${assignUname }</span></li>
		       			</c:if>
		        	</ul> 
 		 		</div>
	        	<a href='<%=path %>/galaxy/project/detail/${projectId}?mark=t' class='pro-detail'>项目详细信息 ></a>
        	</div>
        	
        	<div class='taskDetail-mesage-table'>
	        	<c:if test="${showIgnore }">
	        	<div class='task-no-need'><label class='task-no-label'></label><input type='checkbox' value=''/>不需要提供</div>
	        	</c:if>
	        	<table width='100%' class='task-detail-table table_new_style' border='0' cellspacing='0' cellpadding='0'>
	        		<thead>
	        			<tr>
	        				<th>创建日期</th>
	        				<th>存储类型</th>
	        				<th>更新日期</th>
	        				<th>档案状态</th>
	        				<th>操作</th>
	        			</tr>
	        		</thead>
	        		<tbody>
	        			<tr>
	        				<td>2017-04-09</td>
	        				<td>图片</td>
	        				<td>2017-08-04</td>
	        				<td>已上传</td>
	        				<td class='task-operation'>
	        					<a href='#'>查看</a>
	        					<a href='#'>查看历史</a>
	        				</td>
	        				
	        			</tr>
	        		</tbody>
	        	
	        	</table>
        	</div>
        	<!-- 处理任务 -->
        	<c:if test="${'taskStatus:2' == task.taskStatus and fx:hasPremission('task_dispose') }">
        	<div class='taskDetail-mesage-update'>
        		<a href='javascript:;' class="upate-task bluebtn_new" id="file-upload-btn">${btnTxt }</a>
        		<a href='javascript:;'  class='upate-task submit-success disabled' id="complete-task-btn" disabled="disabled">提交完成</a>
        	</div>
        	</c:if>
        	<!-- 已完成任务再处理 -->
        	<c:if test="${'taskStatus:3' == task.taskStatus and fx:hasPremission('task_redispose') }">
        	<div class='taskDetail-mesage-update'>
        		<a href='javascript:;' class="upate-task bluebtn_new" id="file-upload-btn">${btnTxt }</a>
        	</div>
        	</c:if>
        </div>
	</div>
</div>
<script type="text/javascript">
	$("#task-title").text('${task.taskName}');
	/**********************显示任务详情 START************************/
	var url = platformUrl.detailProject+"/${projectId}";
	var data = {};
	var callback = function(data){
		if(data.result.status == "Error")
		{
			return;
		}
		var project = data.entity;
		$(".task-item span").each(function(){
			var _item = $(this);
			var id = _item.attr('id');
			if(!project.hasOwnProperty(id))
			{
				return;
			}
			var str='';
			if(project[id].length>20){
				str=project[id].substring(0,20);
				_item.attr('title',project[id]);
			}else{
				str=project[id];
			}
			_item.text(str);
		});
	};
	sendGetRequest(url,data,callback);
	/**********************显示任务详情 END ************************/
	/**********************显示文件 START ************************/
	loadRows();
	function isBlank(val)
	{
		if(val == "" || val == null || val == 'undefined')
		{
			return true;
		}
		return false;
	}
	function downloadFile(ele)
	{
		var row = $(ele).closest("tr");
		var fileId = row.data("id");
		forwardWithHeader(platformUrl.downLoadFile+"/"+fileId);
	}
	function loadRows()
	{
		var url = platformUrl.queryFile;
		var data = {
			"projectId":"${projectId}",
			"fileWorktype":"${fileWorktype}"
		};
		var callback = function(data){
			$(".task-detail-table tbody").empty();
			$.each(data.entityList,function(){
				var $tr = $('<tr data-id="'+this.id+'" data-file-source="'+this.fileSource+'" data-file-type="'+this.fileType+'" data-file-worktype="'+this.fileWorktype+'" data-file-name="'+this.fileName+'"></tr>');
				$tr.append('<td>'+(isBlank(this.createdTime) ? "" : Number(this.createdTime).toDate().format("yyyy/MM/dd")) +'</td>');
				$tr.append('<td>'+(isBlank(this.fType) ? "" : this.fType)+'</td>');
				$tr.append('<td>'+(isBlank(this.updatedTime) ? "" : Number(this.updatedTime).toDate().format("yyyy/MM/dd"))+'</td>');
				$tr.append('<td>'+this.fileStatusDesc+'</td>');
				if(isBlank(this.fileName)){
					$tr.append('<td></td>');
					$("#complete-task-btn").addClass('disabled');
				}
				else
				{
					$tr.append('<td class="task-operation"><span onclick="downloadFile(this)">查看</span><span onclick="viewHistory('+this.id+')">查看历史</span></td>');
					$("#complete-task-btn").removeClass('disabled');
					$("#complete-task-btn").removeProp("disabled");
					var btnText = $("#file-upload-btn").text();
					if(btnText != null && btnText.indexOf('上传')>-1)
					{
						$("#file-upload-btn").text(btnText.replace('上传','更新'))
					}
					$(".task-no-need").hide();
				}
				$(".task-detail-table tbody").append($tr);
			});
			
		};
		sendPostRequestByJsonObj(url, data, callback);
	}
	/**********************显示文件 END ************************/
	/**********************文件上传 START ************************/
	var uploader;
	initUpload();
	function initUpload()
	{
		uploader = new plupload.Uploader({
			runtimes : 'html5,html4,flash,silverlight',
			browse_button : $("#file-upload-btn")[0], 
			url : platformUrl.uploadFile2Task+"?sid="+sessionId+"&guid="+userId,
			multi_selection:false,
			filters : {
				max_file_size : '25mb',
				mime_types : [
					           { title : "Image files", extensions : "jpg,jpeg,png,JPG,JPEG,PNG" }, 
					           { title : "PDF files", extensions : "pdf,PDF" },
					           { title : "DOC", extensions : "xls,xlsx,XLS,XLSX"}
					        ]
			},
	
			init: {
				FilesAdded: function(up, files) {
					$.each(uploader.files,function(){
						if(this != files[0])
						{
							uploader.removeFile(this);
						}
					});
					uploader.start();
					layer.load(2);
				},
				BeforeUpload: function(up){
					var file = up.files[0];
					var fileType = getFileTypeByName(file.name);
					var id = $(".task-detail-table tbody tr").attr('data-id');
					var data = {id:id, fileType: fileType};
					up.settings.multipart_params = data;
				},
				FileUploaded: function(up, files, rtn) {
					var data = $.parseJSON(rtn.response);
					
					if(data.status == "OK")
					{
						layer.closeAll('loading');
						layer.msg("上传成功");
						$("#complete-task-btn").removeProp("disabled");
						loadRows();
					}
					else
					{
						layer.closeAll('loading');
						layer.msg("上传失败");
					}
				},
				Error: function(up, err) {
					layer.msg(err.message);
				  	setTimeout(function(){
					  	layer.closeAll('loading');
				  	}, 2000);
				}
			}
		});
		uploader.init();
	}
	/**********************文件上传 START ************************/
	/**********************提交完成 START ************************/
	$("#complete-task-btn").click(function(){
		//判断是否放弃该任务的提交
		var btn=$(".task-no-need input");
		var giveUp=false;
		if(btn.prop( "checked" )==true){
			giveUp=true;
		}
		var url = platformUrl.submitTask;
		var data = {
				id:"${taskId}",
				taskStatus:"taskStatus:3",
				giveUp:giveUp
			};
		var callback = function(data){
			layer.closeAll('loading');
			if(data.result.status=="OK"){
				layer.msg("提交成功",{time:1000},function(){
					var menu = $("#menus .on a");
					if(menu.length == 0)
					{
						$.each($("#menus li a"),function(){
							var href = $(this).attr('href');
							if(href.indexOf('galaxy/soptask')>-1)
							{
								menu = $(this);
								return false;
							}
						});
					}
					window.location=menu.attr('href');
				});
			}
			else
			{
				layer.msg("提交失败");
			}
		};
		//更新task为完成状态
		layer.load(2);
		sendPostRequestByJsonObj(url, data, callback);
	});
	/**********************提交完成 END ************************/
	
	function viewHistory(fileId)
	{
		$.getHtml({
			url:platformUrl.toFileHistory,
			data:{},
			okback:function(){
				var title = '${task.taskName}'.replace('上传','')+'-历史列表'
				$("#file-history-dialog .title_bj").text(title);
				var url = '<%=path%>/galaxy/sopFile/'+fileId+'/history';
				var columns = [
					{
						field:'fileName', 
						title: '文档名称',
						formatter: function(val, row, index){
							return row.fileName+'.'+row.fileSuffix;
						}
					},
					{
						field:'fileStatusDesc', 
						title: '状态'
					},
					{
						field:'updatedTime', 
						title: '更新时间', 
						formatter: 'longTimeFormat'
					},
					{
						field:'opt', 
						title: '操作',
						formatter: function(val, row, index){
							return '<a href="#" class="blue" onclick="downloadHistory('+row.id+')">下载</a>';
						}
					}
				];
				$("#file-history-dialog #file_history_grid").bootstrapTable({
					url: url,
					columns: columns,
					queryParamsType: 'size|page', 
					pageSize:10,
					showRefresh : false ,
					sidePagination: 'server',
					method : 'post',
					sortOrder : 'desc',
					sortName : 'created_time',
					pagination: true,
				    search: false,
				    queryParams: function(params){
				    	params.page = params.pageNum;
				    	params.size = params.pageSize;
				    	return params;
				    }
				});
			}	
		});
	}
	function downloadHistory(id)
	{
		forwardWithHeader(platformUrl.downLoadFile+"/"+id+'?type=history');
	}
	$('.task-no-label').click(function(){
		var _this = $(this);
		if(_this.hasClass('label-checked'))
		{
			//不勾选
			_this.removeClass('label-checked').next().attr('checked',false);
			$("#complete-task-btn").addClass('disabled').prop("disabled","disabled");
			$("#file-upload-btn").removeClass('disabled').prop("disabled","false");
			uploader.disableBrowse(false);
		}
		else
		{
			//勾选
			_this.addClass('label-checked').next().attr('checked',true);
			$("#complete-task-btn").removeClass('disabled').prop("disabled","false");
			$("#file-upload-btn").addClass('disabled').prop("disabled","disabled");
			uploader.disableBrowse(true);
		}
	})
</script>

