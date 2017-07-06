<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<div class="btm">
  
	    <div class="task_noprovide"  style="display: none">
		 	   <input type="checkbox"  value="${isShow}"> 不需要提供
		</div>
	
	<table width="100%" cellspacing="0" cellpadding="0" id="hrjzdc-table">
		<thead>
			<tr>
				<th>创建日期</th>
				<th>存储类型</th>
				<th>更新日期</th>
				<th>档案状态</th>
				<th>查看附件</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<ul>
		<li><a href="javascript:;" id="show-upload-btn">${btnTxt }</a></li>
		<li><a href="javascript:;" id="complete-task-btn" disabled="disabled" class="disabled">提交完成</a></li>
	</ul>
</div>
<!-- 弹出页面 -->
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
	        	<select name="fileType" class="disabled" disabled="disabled"></select>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>业务分类：</dt>
	        <dd>
	        	<select name="fileWorktype" class="disabled" disabled="disabled"></select>
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
<script type="text/javascript">
//不需要提供框是否显示
$(function(){
	loadRows();
	loadRelatedData();
	var isShow="${isShow}";
	if($("#type").val()=="创建"&&isShow=="true"){
		$(".task_noprovide").show();
	}
	$("#show-upload-btn").click(function(){
		var title_name=$(this).text();
		showUploadPopup(title_name);
	});
	$("#complete-task-btn").click(function(){
		//判断是否放弃该任务的提交
		var btn=$(".task_noprovide input");
		var giveUp=false;
		if(btn.prop( "checked" )==true){
			giveUp=true;
		}
		//更新task为完成状态
		sendPostRequestByJsonObj(
			platformUrl.submitTask,
			{
				id:"${taskId}",
				taskStatus:"taskStatus:3",
				giveUp:giveUp
			},
			function(data){
				if(data.result.status=="OK"){
					layer.msg("提交成功。");
					var url = $("#menus .on a").attr('href');
					window.location=url;
				}
				else
				{
					layer.msg("提交失败。");
				}
			}
		);
	});
});
function projectLoaded(project)
{
	
}
function loadRows()
{
	var url = platformUrl.queryFile;
	var data = {
		"projectId":"${projectId}",
		"fileWorktype":"${fileWorktype}"
	};
	$("#hrjzdc-table tbody").empty();
	sendPostRequestByJsonObj(
			url,
			data,
			function(data){
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
						$tr.append('<td><a href="#" onclick="downloadFile(this)">查看</a></td>');
						$("#complete-task-btn").removeClass('disabled');
						$("#complete-task-btn").removeProp("disabled");
						var btnText = $("#show-upload-btn").text();
						if(btnText != null && btnText.indexOf('上传')>-1)
						{
							$("#show-upload-btn").text(btnText.replace('上传','更新'))
						}
						$(".task_noprovide").hide();
					}
					$("#hrjzdc-table tbody").append($tr);
				});
			}
	);
}

function isBlank(val)
{
	if(val == "" || val == null || val == 'undefined')
	{
		return true;
	}
	return false;
}
function loadRelatedData()
{
	sendGetRequest(
			platformUrl.getTempRelatedData,
			null,
			function(data){
				$.each(data.fileType,function(){
					$("#upload-dialog [name='fileType']").append("<option value='"+this.code+"'>"+this.name+"</option>")
				});
				$.each(data.fileWorktype,function(){
					$("#upload-dialog [name='fileWorktype']").append("<option value='"+this.code+"'>"+this.name+"</option>")
				});
			}
	);
}
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
			max_file_size : '25mb'
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
	var $row = $("#hrjzdc-table tbody tr");
	var fileType = $row.data('file-type') == 'undefined' ? 'fileType:1' : $row.data('file-type');
	var fileName = $row.data('file-name');
	var fileSource = $row.data('file-source');
	var worktype = $row.data('file-worktype');
	
	$(_dialog.id).find("[name='id']").val($row.data('id'));
	$(_dialog.id).find("[name='fileSource'][value='"+fileSource+"']").attr('checked',true);
	$(_dialog.id).find("[name='fileWorktype']").val(worktype);
	$(_dialog.id).find("[name='fileType']").val(fileType);
	//$(_dialog.id).find("[name='fileName']").val(isBlank(fileName) ? "" : fileName);
	$(_dialog.id).find("[name='projectName']").val($("#project-summary #projectName").text());
}
function downloadFile(ele)
{
	var row = $(ele).closest("tr");
	var fileId = row.data("id");
	forwardWithHeader(platformUrl.downLoadFile+"/"+fileId);
}
</script>
