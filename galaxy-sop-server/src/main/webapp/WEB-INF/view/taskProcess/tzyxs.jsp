<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<div class="btm">
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
		<li><a href="javascript:;" id="download-template-btn">下载投资意向书模板</a></li>
		<li><a href="javascript:;" id="show-upload-btn">上传投资意向书</a></li>
		<!-- <li><a href="javascript:;" id="show-voucher-upload-btn" class="disabled">上传签署凭证</a></li> -->
	</ul>
</div>
<!-- 弹出页面 -->
<div id="upload-dialog" style="display: none;">
	<div class="title_bj">上传投资意向书</div>
	<div class="archivestc margin_45" >
	<form>
		<input type="hidden" name="id">
		<input type="hidden" name="pid" value="${projectId }">
		<input type="hidden" name="stage" value="projectProgress:5">
		<dl class="fmdl clearfix">
	    	<dt>档案来源：</dt>
	        <dd class="clearfix">
	        	<label><input name="fileSource" type="radio" value = "1" checked/>内部</label>
	            <label><input name="fileSource" type="radio" value = "2" />外部</label>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>存储类型：</dt>
	        <dd>
	        	<select name="fileType" data-rule-required="true" class="disabled" disabled="disabled"></select>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>业务分类：</dt>
	        <dd>
	        	<select name="fileWorktype" class="disabled" disabled="disabled"></select>
	        </dd>
	       <!--  <dd>
	        	<label id="tzyxs_qszm"><input type="checkbox" id="voucherType" name="voucherType" value="1" disabled="disabled"/>签署凭证</label>
	        </dd> -->
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
	        	<input type="text" class="txt" name="fileName" disabled data-rule-required="true"/>
	        </dd>
	        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a></dd>
	    </dl> 
	    <a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">上传保存</a>
	</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	loadRows();
	loadRelatedData();
	$("#show-upload-btn").click(function(){
		showUploadPopup($(this).text());
	});
	
	$("#show-voucher-upload-btn").click(function(){
		showUploadPopup("voucher");
	});
	$("#download-template-btn").click(function(){
		forwardWithHeader(platformUrl.tempDownload+"?worktype=templateType:1&projectId=${projectId}");
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
					var $tr = $('<tr data-voucher-id="'+this.voucherId+'" data-voucher-file-name="'+this.voucherFileName+'" data-id="'+this.id+'" data-file-source="'+this.fileSource+'" data-file-type="'+this.fileType+'" data-file-worktype="'+this.fileWorktype+'" data-file-name="'+this.fileName+'"></tr>');
					$tr.append('<td>'+(isBlank(this.createdTime) ? "" : Number(this.createdTime).toDate().format("yyyy/MM/dd")) +'</td>');
					$tr.append('<td>'+(isBlank(this.fType) ? "" : this.fType)+'</td>');
					$tr.append('<td>'+(isBlank(this.updatedTime) ? "" : Number(this.updatedTime).toDate().format("yyyy/MM/dd"))+'</td>');
					$tr.append('<td>'+this.fileStatusDesc+'</td>');
					if(isBlank(this.fileName)){
						$("#show-voucher-upload-btn").addClass("disabled");
						$tr.append('<td></td>');
						
					}
					else
					{
						if(isBlank(this.voucherFileName))
						{
							$("#show-voucher-upload-btn").removeClass("disabled");
						}
						else
						{
							$("#show-voucher-upload-btn").addClass("disabled");
						}
						$tr.append('<td><a href="#" onclick="downloadFile(this)">查看</a></td>');
						$('#show-upload-btn').text('更新投资意向书');
					}
					if(!isBlank(this.voucherFileName))
					{
						$('#show-upload-btn').addClass('disabled');
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
			platformUrl.getTempRelatedData+"?sid="+sessionId+"&guid="+userId,
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
function showUploadPopup(type)
{
	$.popup({
		init:init(type),
		txt:$("#upload-dialog").html(),
		showback:function(){
			if(type=='voucher'){
				$('.title_bj').html('上传签署凭证');
			}else{
				$('.title_bj').html(type);
			}
			var _this = this;
			initUpload(_this,type);
			initForm(_this,type);
		}
	});
}
function init(type){
	if(type != 'voucher'){
        $("#tzyxs_qszm").attr("style","visibility:hidden");
	}else{
		$("#tzyxs_qszm").removeAttr("style");
	}
}
function initUpload(_dialog,type){
	var opts = {
			rules : {
				fileSource:{
					required:true
				}
				
			}
	};
	var validator = $(_dialog.id).find('form').fxValidate(opts);
	var url = platformUrl.stageChange;
	var uploader = new plupload.Uploader({
		runtimes : 'html5,html4,flash,silverlight',
		browse_button : $(_dialog.id).find("#file-select-btn")[0], 
		url : url,
		multi_selection:false,
		filters : {
			max_file_size : '25mb',
			mime_types: paramsFilter(6)
		},

		init: {
			PostInit: function(up) {
				$(_dialog.id).find("#upload-btn").click(function(){
					 if(!validator.form())
					{
						return;
					} 
					var fileName = $(_dialog.id).find('[name="fileName"]').val();
					if(fileName == null || fileName == '')
					{
						layer.msg("请选择文件");
						return;
					}
					uploader.start();
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
				$(_dialog.id).showLoading(
						 {
						    'addClass': 'loading-indicator'						
						 });
				var $form =$(_dialog.id).find("form")
				var data = JSON.parse($form.serializeObject());
				data['type']=data['fileSource'];
				data['fileType']=$(_dialog.id).find("[name='fileType']").val();
				data['fileWorktype']='fileWorktype:5';
				if(type == 'voucher'){
					data['voucherType']=$("[name='voucherType']:checked").val();
				}
				up.settings.multipart_params = data;
			},
			FileUploaded: function(up, files, rtn) {
				$(_dialog.id).hideLoading();
				var data = $.parseJSON(rtn.response);
				if(data.result.status == "OK")
				{
					layer.msg("上传成功.");
					$(_dialog.id).find("[data-close='close']").click();
					loadRows();
				}
				else
				{
					layer.msg("上传失败.");
				}
			},
			Error: function(up, err) {
				$(_dialog.id).hideLoading();
				layer.msg(err.message);
			}
		}
	});

	uploader.init();
}

function initForm(_dialog,type)
{
	var $row = $("#hrjzdc-table tbody tr");
	var fileType = $row.data('file-type') == 'undefined' ? 'fileType:1' : $row.data('file-type');
	var fileName = $row.data('file-name');
	var voucherFileName = $row.data('voucher-file-name');
	var fileSource = $row.data('file-source');
	var worktype = $row.data('file-worktype');
	var projectId = $row.data('project-id');
	if(type == 'voucher')
	{
		$(_dialog.id).find("[name='id']").val($row.data('voucher-id'));
		$(_dialog.id).find("[name='voucherType']").attr('checked',true);
	}
	else
	{
		
		$(_dialog.id).find("[name='id']").val($row.data('id'));
		
	}
	$(_dialog.id).find("[name='fileSource'][value='"+fileSource+"']").attr('checked',true);
	$(_dialog.id).find("[name='fileType']").val(fileType);
	$(_dialog.id).find("[name='projectName']").val($("#project-summary #projectName").text());
	$(_dialog.id).find("[name='fileWorktype']").val(worktype);
}
function downloadFile(ele)
{
	var row = $(ele).closest("tr");
	var fileId = row.data("id");
	forwardWithHeader(platformUrl.downLoadFile+"/"+fileId);
}
</script>
