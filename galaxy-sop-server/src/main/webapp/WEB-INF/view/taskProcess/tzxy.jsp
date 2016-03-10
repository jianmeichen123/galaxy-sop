﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<div class="btm">
	<table width="100%" cellspacing="0" cellpadding="0" id="hrjzdc-table">
		<thead>
			<tr>
				<th>业务类型</th>
				<th>存储类型</th>
				<th>更新日期</th>
				<th>档案状态</th>
				<th>上传/查看附件</th>
				<th>签署凭证</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
<!-- 	
	<ul>
		<li><a href="javascript:;" id="show-upload-btn">${btnTxt }</a></li>
		<li><a href="javascript:;">提交完成</a></li>
	</ul>
	 -->
</div>
<!-- 弹出页面 -->
<div id="upload-dialog" style="display: none;">
	<div class="archivestc" >
	<form>
		<input type="hidden" name="id">
		<input type="hidden" name="type">
		<dl class="fmdl clearfix">
	    	<dt>档案来源：</dt>
	        <dd class="clearfix">
	        	<label><input name="fileSource" type="radio" value = "1" />内部</label>
	            <label><input name="fileSource" type="radio" value = "2" />外部</label>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>存储类型：</dt>
	        <dd>
	        	<select name="fileType" disabled></select>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>业务分类：</dt>
	        <dd>
	        	<select name="fileWorktype" disabled></select>
	        </dd>
	        <dd>
	        	<label><input type="checkbox"/>签署凭证</label>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>所属项目：</dt>
	        <dd>
	        	<input type="text" name="projectName" disabled class="txt"/>
	        </dd>
	    </dl>
	    
	     <dl class="fmdl clearfix">
	    	<dt>文档上传：</dt>
	        <dd>
	        	<input type="text" class="txt" name="fileName" disabled/>
	        </dd>
	        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a></dd>
	    </dl> 
	     <div class="fmarea">
	    	<textarea name="remark"></textarea>
	    </div> 
	    <a href="javascript:;" class="pubbtn bluebtn" id="upload-btn";>上传保存</a>
	</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	loadRows();
	loadRelatedData();
});
function loadRows()
{
	var url = platformUrl.queryFile;
	var data = {
		"projectId":"${projectId}",
		"fileworktypeList":["fileWorktype:6","fileWorktype:7"]
	};
	$("#hrjzdc-table tbody").empty();
	sendPostRequestByJsonObj(
			url,
			data,
			function(data){
				
				$.each(data.entityList,function(){
					var $tr = $('<tr data-id="'+this.id+'" data-voucher-id="'+this.voucherId+'" data-file-source="'+this.fileSource+'" data-file-type="'+this.fileType+'" data-file-worktype="'+this.fileWorktype+'" data-file-name="'+this.fileName+'" data-remark="'+this.remark+'"></tr>');
					$tr.append('<td>'+(isBlank(this.fWorktype) ? "" : this.fWorktype) +'</td>');
					$tr.append('<td>'+(isBlank(this.fType) ? "" : this.fType)+'</td>');
					$tr.append('<td>'+(isBlank(this.updatedTime) ? "" : Number(this.updatedTime).toDate().format("yyyy/MM/dd"))+'</td>');
					$tr.append('<td>'+this.fileStatusDesc+'</td>');
					if(isBlank(this.fileName)){
						$tr.append('<td><a href="#" onclick="showUploadPopup(this);" data-type="">上传</a></td>');
					}
					else
					{
						$tr.append('<td><a href="javascript:;" onclick="downloadFile(this);" data-type="">'+this.fileName+'</a></td>');
					}
					if(isBlank(this.voucherFileName)){
						$tr.append('<td><a href="#" onclick="showUploadPopup(this);" data-type="voucher">上传</a></td>');
					}
					else
					{
						$tr.append('<td><a href="javascript:;" onclick="downloadFile(this);" data-type="voucher">'+this.voucherFileName+'</a></td>');
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
function showUploadPopup(ele)
{
	var row = $(ele).closest("tr");
	var type = $(ele).data("type");
	$.popup({
		txt:$("#upload-dialog").html(),
		showback:function(){
			var _this = this;
			initUpload(_this,type);
			console.log(row.data("file-worktype"));
			console.log(type);
			initForm(_this,row.data("file-worktype"),type);
		}
	});
}
function initUpload(_dialog,type){
	var url = platformUrl.uploadFile2Task;
	if(type == 'voucher')
	{
		url = platformUrl.uploadVoucher2Task;
	}
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $(_dialog.id).find("#file-select-btn")[0], 
		url : url,
		multi_selection:false,
		filters : {
			max_file_size : '30mb'
		},

		init: {
			PostInit: function(up) {
				$(_dialog.id).find("#upload-btn").click(function(){
					console.log("upload");
					var fileName = $(_dialog.id).find('[name="fileName"]').val();
					if(fileName == null || fileName == '')
					{
						alert("请选择文件");
						return;
					}
					//只更新内容，不更新文档
					if(uploader.files == 0)
					{
						var $form =$(_dialog.id).find("form")
						var data = JSON.parse($form.serializeObject());
						sendGetRequest(
								platformUrl.uploadFile2Task,
								data,
								function(data){
									afterSave(data);
								}
						);
					}
					uploader.start();
					return false;
				});
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					$(_dialog.id).find("input[name='fileName']").val(file.name);
				});
			},
			BeforeUpload:function(up){
				var $form =$(_dialog.id).find("form")
				var data = JSON.parse($form.serializeObject());
				up.settings.multipart_params = data;
			},
			FileUploaded: function(up, files, rtn) {
				var data = $.parseJSON(rtn.response);
				afterSave(data)
			}
		}
	});

	uploader.init();
}
function afterSave(data)
{
	if(data.status == "OK")
	{
		alert("上传成功.");
		loadRows();
	}
	else
	{
		alert("上传失败.");
	}
}
function initForm(_dialog,fileWorktype,type)
{
	console.log('initform');
	var $row = $("#hrjzdc-table tbody tr[data-file-worktype='"+fileWorktype+"']");
	var fileType = $row.data('file-type');
	var fileName = $row.data('file-name');
	var fileSource = $row.data('file-source');
	var remark = $row.data('remark');
	console.log($row.data('voucher-id'));
	if(type == 'voucher')
	{
		$(_dialog.id).find("[name='id']").val($row.data('voucher-id'));
	}
	else
	{
		$(_dialog.id).find("[name='id']").val($row.data('id'));
	}
	$(_dialog.id).find("[name='type']").val(type);
	$(_dialog.id).find("[name='fileSource'][value='"+fileSource+"']").attr('checked',true);
	$(_dialog.id).find("[name='fileName']").val(isBlank(fileName) ? "" : fileName);
	$(_dialog.id).find("[name='remark']").val(isBlank(remark) ? "" : remark);
	$(_dialog.id).find("[name='projectName']").val($("#project-summary #projectName").text());
}
function downloadFile(ele)
{
	var row = $(ele).closest("tr");
	var fileId = row.data("id");
	var type = $(ele).data("type");
	if(type == 'voucher')
	{
		fileId = row.data("voucher-id")
	}
	window.location.href=platformUrl.downLoadFile+"/"+fileId+"?type="+type;
}
</script>
