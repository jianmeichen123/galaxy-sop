<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
%>
<div class="btm">
	<div id="tzxy_options" class="btnbox_f btnbox_f1 btnbox_m clearfix">
		<div id="stock_transfer_model" class="option_item_mark">
			
		</div>
	</div>
	<table width="100%" cellspacing="0" cellpadding="0" id="hrjzdc-table">
		<thead>
			<tr>
				<th>业务分类</th>
				<th>存储类型</th>
				<th>更新日期</th>
				<th>档案状态</th>
				<th>模板下载</th>
				<th>上传附件</th>
				<th>查看附件</th>
				<!-- <th>签署凭证</th> -->
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
	<div class="archivestc margin_45" >
	<div class="title_bj" id="upload-dialog-name"></div>
	<form>
		<input type="hidden" name="id">
		<input type="hidden" name="type">
		<input type="hidden" name="pid" value="${projectId }">
		<input type="hidden" name="stage" value="projectProgress:8">
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
	        	<select name="fileType" class="disabled" disabled="disabled"></select>
	        </dd>
	    </dl>
	    <dl class="fmdl clearfix">
	    	<dt>业务分类：</dt>
	        <dd>
	        	<select name="fileWorktype" class="disabled" disabled="disabled"></select>
	        </dd>
	      <!--   <dd>
	        	<label id="tzxy_qszm"><input type="checkbox" id="voucherType" name="voucherType" value="1" disabled="disabled"/>签署凭证</label>
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
	        	<input type="text" class="txt" name="fileName" disabled/>
	        </dd>
	        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">选择档案</a></dd>
	    </dl> 
	    <a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">上传保存</a>
	</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	loadRelatedData();
	
	if("${taskFlag}" == 7)
	{
		$("[name='hasStockTransfer']").attr('checked',true).attr('disabled',true);
	}
});
var stockTransfer = 0;
function projectLoaded(project)
{
	
	loadRows();
	
	
}

/**
 * "是否涉及股权转让"按钮点击事件
 */
function selected(obj){
	var pid = "${projectId}";
	if(pid != '' && pid != null && pid != undefined){
		sendGetRequest(platformUrl.storeUrl + pid,
				null,
				function(data){
					stockTransfer = data.entity.stockTransfer;
				});
	}
	
	if(stockTransfer == 1){
		$("#stock_transfer").attr("checked","checked");
		$("tr[data-file-worktype='fileWorktype:7']").css("display","table-row");
	}else{
		$("tr[data-file-worktype='fileWorktype:7']").css("display","none");
	}
	
}

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
				var hidden = false;
				$.each(data.entityList,function(){
					var $tr = $('<tr data-id="'+this.id+'" data-voucher-id="'+this.voucherId+'" data-file-source="'+this.fileSource+'" data-file-type="'+this.fileType+'" data-file-worktype="'+this.fileWorktype+'" data-file-name="'+this.fileName+'"></tr>');
					//业务分类
					$tr.append('<td>'+(isBlank(this.fWorktype) ? "" : this.fWorktype) +'</td>');
					//存储类型
					$tr.append('<td>'+(isBlank(this.fType) ? "" : this.fType)+'</td>');
					//更新日期
					$tr.append('<td>'+(isBlank(this.updatedTime) ? "" : Number(this.updatedTime).toDate().format("yyyy/MM/dd"))+'</td>');
					//档案状态
					$tr.append('<td>'+this.fileStatusDesc+'</td>');
					//模板下载
					var tempType = 'templateType:2';
					if(this.fileWorktype == 'fileWorktype:7')
					{
						tempType = 'templateType:7';
					}
					$tr.append('<td><a href="javascript:;" onclick="downloadTemplate(\''+tempType+'\');" data-type="">下载</a></td>');
					//上传附件
					if(isBlank(this.fileName)){
						$tr.append('<td><a href="#" onclick="showUploadPopup(this);" data-name="上传">上传</a></td>');
					}
					else
					{
						$tr.append('<td><a href="javascript:;" onclick="showUploadPopup(this);" data-name="更新">更新</a></td>');
					}
					//查看附件
					if(isBlank(this.fileName)){
						$tr.append('<td>无</td>');
					}
					else
					{
						$tr.append('<td><a href="#" onclick="downloadFile(this);" data-type="">查看</a></td>');
					}
					//签署凭证
					/* if(isBlank(this.voucherFileName)){
						$tr.append('<td><a href="#" onclick="showUploadPopup(this);" data-type="voucher" data-name="签署凭证">上传</a></td>');
					}
					else
					{
						$tr.append('<td><a href="javascript:;" onclick="downloadFile(this);" data-type="voucher">查看</a></td>');
					} */	
					$("#hrjzdc-table tbody").append($tr);
					if((this.fileWorktype == 'fileWorktype:6' && this.voucherFileName != '' && this.voucherFileName != null) || (this.fileWorktype == 'fileWorktype:7' && this.voucherFileName != '' && this.voucherFileName != null)){
						$("#stock_transfer").attr("disabled","true");
					}
				});
				
				if(stockTransfer == 1){
					$("#stock_transfer").attr("checked","checked");
				}else{
					$("tr[data-file-worktype='fileWorktype:7']").css("display","none");
				}
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
	var name =$(ele).attr('data-name');
	if(name=='上传'){
		$('#upload-dialog-name').html('上传附件');
	}
	if(name=='更新'){
		$('#upload-dialog-name').html('更新附件')
	}
	if(name=='签署凭证'){
		$('#upload-dialog-name').html('上传签署凭证')
	}
	$.popup({
		init:init(type),
		txt:$("#upload-dialog").html(),
		showback:function(){
			var _this = this;
			initUpload(_this,type);
			initForm(_this,row.data("file-worktype"),type);
		}
	});
}
function init(type){
	if(type == ''){
        $("#tzxy_qszm").attr("style","visibility:hidden");
	}else{
		$("#tzxy_qszm").removeAttr("style");
	}
}
function initUpload(_dialog,type){
	var url = platformUrl.stageChange;
	var uploader = new plupload.Uploader({
		runtimes : 'html5,html4,flash,silverlight',
		browse_button : $(_dialog.id).find("#file-select-btn")[0], 
		url : url+"?sid="+sessionId+"&guid="+userId,
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
				var $form =$(_dialog.id).find("form");
				var data = JSON.parse($form.serializeObject());
				data['type'] = data['fileSource'];
				data['fileType']=$(_dialog.id).find("[name='fileType']").val();
				data['fileWorktype']=$form.find("[name='fileWorktype']").val();
				data['hasStockTransfer']=$("#stock_transfer:checked").val();
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
					layer.msg(data.result.message);
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

function initForm(_dialog,fileWorktype,type)
{
	var $row = $("#hrjzdc-table tbody tr[data-file-worktype='"+fileWorktype+"']");
	var fileType = $row.data('file-type') == 'undefined' ? 'fileType:1' : $row.data('file-type');
	var fileName = $row.data('file-name');
	var fileSource = $row.data('file-source');
	var worktype = $row.data('file-worktype');
	if(type == 'voucher')
	{
		$(_dialog.id).find("[name='id']").val($row.data('voucher-id'));
		$(_dialog.id).find("[name='voucherType']").attr('checked',true);
	}
	else
	{
		$(_dialog.id).find("[name='id']").val($row.data('id'));
	}
	$(_dialog.id).find("[name='type']").val(type);
	$(_dialog.id).find("[name='fileSource'][value='"+fileSource+"']").attr('checked',true);
	$(_dialog.id).find("[name='fileWorktype']").val(worktype);
	$(_dialog.id).find("[name='fileType']").val(fileType);
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
	forwardWithHeader(platformUrl.downLoadFile+"/"+fileId+"?type="+type);
}
function downloadTemplate(templateType)
{
	var url = platformUrl.tempDownload+"?worktype="+templateType+"&projectId=${projectId}";
	forwardWithHeader(url);
}
</script>
