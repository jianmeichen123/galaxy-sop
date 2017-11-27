<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%
	String path = request.getContextPath(); 
%>
<div class="pagebox clearfix task-pagebox">
    <!--右中部内容-->
 	<div class="taskDetail-ritmin">
 		 <div class='taskDetail-mesage'>
 		 	<div class='taskDetail-mesage-top'>
	        	<div class='task-item task-item-left'>
	        		<ul>
	        			<li>项目名称：<span id="projectName">创投</span></li>
	        			<li>创建时间：<span id="createDate">2016-12-22</span></li>
	        			<li>事业部总经理：<span id="hhrName">李凯</span></li>
	        		</ul>
	        	</div>
	        	<div class='task-item'>
	        		<li>项目类型：<span id="type">投资</span></li>
	       			<li>投资事业线：<span id="projectCareerline">人工智能</span></li>
	       			<li>公司名称：<span id="projectCompany">星河互联集团</span></li>
	        	</div>
	        	<div class='task-item task-item-right'>
	        		<li>项目编码：<span id="projectCode">27000021</span></li>
	       			<li>投资经理：<span id="createUname">人工智能-投资经理</span></li>
	       			<a href='<%=path %>/galaxy/project/detail/${projectId}?mark=t' class='pro-detail'>项目详细信息 ></a>
	        	</div> 
        	</div>
        	<div class='task-no-need'><label class='task-no-label'></label><input type='checkbox' value=''/>不需要提供</div>
        	<div class='taskDetail-mesage-table'>
	        	<table width='100%' class='task-detail-table' border='0' cellspacing='0' cellpadding='0'>
	        		<thead>
	        			<tr>
	        				<th>上传日期</th>
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
        	<div class='taskDetail-mesage-update'>
        		<a href='javascript:;' class="upate-task" id="file-upload-btn">${btnTxt }</a>
        		<a href='javascript:;'  class='upate-task submit-success disabled' id="complete-task-btn" disabled="disabled">提交完成</a>
        	</div>
        </div>
	</div>
</div>
<!-- 弹出上传页面  START-->
<div id="upload-dialog" style="display: none;">
	<div class="title_bj"></div>
	<div class="archivestc margin_45" >
	<form>
		<input type="hidden" name="id">
		<dl class="fmdl clearfix">
	    	<dt>档案来源：</dt>
	        <dd class="clearfix">
	        	<label><input name="fileSource" type="radio" value = "1" checked/>内部</label>
	            <label><input name="fileSource" type="radio" value = "2"/>外部</label>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>存储类型：</dt>
	        <dd>
	        	<select name="fileType" class="disabled" disabled="disabled">
	        		<option value="">请选择</option>
	        		<option value="fileType:1">文档</option>
	        		<option value="fileType:2">音频文件</option>
	        		<option value="fileType:3">视频文件</option>
	        		<option value="fileType:4">图片</option>
	        	</select>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>业务分类：</dt>
	        <dd>
	        	<select name="fileWorktype" class="disabled" disabled="disabled">
	        		<option value="">请选择</option>
	        		<option value="fileWorktype:2">人力资源尽职调查报告</option>
	        		<option value="fileWorktype:3">法务资源尽职调查报告</option>
	        		<option value="fileWorktype:4">财务资源尽职调查报告</option>
	        		<option value="fileWorktype:8">工商转让凭证</option>
	        		<option value="fileWorktype:9">资金拨付凭证</option>
	        	</select>
	        </dd>
	    <!--     <dd>
	        	<label><input type="checkbox"/>签署凭证</label>
	        </dd> -->
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>所属项目：</dt>
	        <dd>
	        	<input type="text" name="projectName" disabled="disabled" class="txt disabled"/>
	        </dd>
	    </dl>
	    
	     <dl class="fmdl clearfix">
	    	<dt>文档上传：</dt>
	        <dd>
	        	<input type="text" class="txt" name="fileName" disabled/>
	        </dd>
	        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a></dd>
	    </dl> 
	    <a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">上传保存</a>
	</form>
	</div>
</div>
<!-- 弹出上传页面  END-->
<script type="text/javascript">

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
			_item.text(project[id]);
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
					$tr.append('<td class="task-operation"><span onclick="downloadFile(this)">查看</span></td>');
					$("#complete-task-btn").removeClass('disabled');
					$("#complete-task-btn").removeProp("disabled");
					/* 
					var btnText = $("#show-upload-btn").text();
					if(btnText != null && btnText.indexOf('上传')>-1)
					{
						$("#show-upload-btn").text(btnText.replace('上传','更新'))
					}
					$(".task_noprovide").hide();
					 */
				}
				$(".task-detail-table tbody").append($tr);
			});
			
		};
		sendPostRequestByJsonObj(url, data, callback);
	}
	/**********************显示文件 END ************************/
	/**********************文件上传 START ************************/
	$("#file-upload-btn").click(function(){
		var title_name=$(this).text();
		showUploadPopup(title_name);
	});
	function showUploadPopup(title_name)
	{
		$.popup({
			txt:$("#upload-dialog").html(),
			showback:function(){
				$('.title_bj').html(title_name)
				var _this = this;
				initUpload(_this);
				initForm(_this);
			}
		});
	}
	function initUpload(_dialog){
		var uploader = new plupload.Uploader({
			runtimes : 'html5,html4,flash,silverlight',
			browse_button : $(_dialog.id).find("#file-select-btn")[0], 
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
				PostInit: function(up) {
					$(_dialog.id).find("#upload-btn").click(function(){
						var fileName = $(_dialog.id).find('[name="fileName"]').val();
						if(fileName == null || fileName == '')
						{
							layer.msg("请选择文件");
							return;
						}
						uploader.start();
	
						layer.load(2);
						return false;
					});
				},
	
				FilesAdded: function(up, files) {
					$.each(uploader.files,function(){
						if(this != files[0])
						{
							uploader.removeFile(this);
						}
					});
					$.each(files, function() {
						$(_dialog.id).find("input[name='fileName']").val(this.name);
						var fileType = getFileTypeByName(this.name);
						$(_dialog.id).find("[name='fileType']").val(fileType);
					});
				},
				BeforeUpload:function(up){
					var $form =$(_dialog.id).find("form")
					var data = JSON.parse($form.serializeObject());
					data['fileType']=$(_dialog.id).find("[name='fileType']").val();
					up.settings.multipart_params = data;
				},
				FileUploaded: function(up, files, rtn) {
					var data = $.parseJSON(rtn.response);
					
					if(data.status == "OK")
					{
						layer.closeAll('loading');
						layer.msg("上传成功.");
						$("#complete-task-btn").removeProp("disabled");
						$(_dialog.id).find("[data-close='close']").click();
						loadRows();
					}
					else
					{
						layer.closeAll('loading');
						layer.msg("上传失败.");
					}
				},
				Error: function(up, err) {
				 	var txt=$(_dialog.id).find("input[name='fileName']");
					layer.msg(err.message);
				  	setTimeout(function(){
					  	layer.closeAll('loading');
				  		txt.val("");
				  	}, 2000);
				}
			}
		});
		uploader.init();
	}

	function initForm(_dialog)
	{
		var $row = $(".task-detail-table tbody tr");
		var fileType = $row.data('file-type') == 'undefined' ? 'fileType:1' : $row.data('file-type');
		var fileName = $row.data('file-name');
		var fileSource = $row.data('file-source');
		var worktype = $row.data('file-worktype');
		
		$(_dialog.id).find("[name='id']").val($row.data('id'));
		$(_dialog.id).find("[name='fileSource'][value='"+fileSource+"']").attr('checked',true);
		$(_dialog.id).find("[name='fileWorktype']").val(worktype);
		$(_dialog.id).find("[name='fileType']").val(fileType);
		//$(_dialog.id).find("[name='fileName']").val(isBlank(fileName) ? "" : fileName);
		$(_dialog.id).find("[name='projectName']").val($(".taskDetail-mesage-top #projectName").text());
	}
	/**********************文件上传 START ************************/

	$('.task-no-label').click(function(){
		$(this).toggleClass('label-checked');
	})
</script>

