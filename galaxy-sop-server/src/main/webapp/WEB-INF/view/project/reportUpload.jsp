<%@ page language="java" pageEncoding="UTF-8"%>
<form id="report_upload_form" style='margin-top:45px;'>
	<input type="hidden" name="id"/> 
	<input type="hidden" name="projectId"/> 
	<input type="hidden" name="fileWorktype" value="fileWorktype:17"/> 
	<input type="hidden" name="fileStatus" value="fileStatus:2"/>
	<input type="hidden" name="projectProgress" value="projectProgress:4"/>
	<input type="hidden" name="fileType"/> 
	<input type="hidden" name="fileSource" value="1"/> 
	<div class="title_bj">添加项目立项报告</div>

	<div class="fmdl clearfix ">
		<dt style="width: 145px; text-align: right;" id="reportName">项目立项报告：</dt>
		<dd>
			<input type="text" name="fileName" class="txt pointer-events" />
		</dd>
		<dd>
			<a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn">上传附件</a>
		</dd>
	</div>
	<div style='margin-top:25px;'>
		<span style=" float:left; margin-right:95px;"><a href="javascript:;" class="pubbtn bluebtn" id="upload-btn">保存</a></span>
		<span style=" float:left"><a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="upload-close">取消</a></span>
		<div  style=" width:100%; height:20px; overflow:hidden">&nbsp;</div>
	</div>
</form>
<script>
var reportUploader = new plupload.Uploader({
	runtimes : 'html5,html4,flash,silverlight',
	browse_button : $("#report_upload_form #file-select-btn")[0], 
	url : platformUrl.uploadSimpleFile,
	multi_selection:false,
	filters : {
		max_file_size : '25mb',
		mime_types: paramsFilter(null)
	},

	init: {
		PostInit: function() {
			$("#report_upload_form #upload-btn").click(function(){
				if(reportUploader.files.length==0){
					layer.msg("请选择文件.");
					return;
				}else{
					reportUploader.start();
				}
				return false;
			});
		},

		FilesAdded: function(up, files) {
			if(reportUploader.files.length >= 1){
				reportUploader.splice(0, reportUploader.files.length-1);
			} 
			$.each(files, function() {
				$("#report_upload_form input[name='fileName']").val(this.name);
				attrFileType($("#report_upload_form [name='fileType']"),this);
			});
		},
		BeforeUpload : function(up,file){
			$("#report_upload_form").closest(".pop").showLoading({
		    	'addClass': 'loading-indicator'						
		    });
			var $form = $("#report_upload_form");
			var data = JSON.parse($form.serializeObject());
			data['fileType']=$("#report_upload_form [name='fileType']").val();
			up.settings.multipart_params = data;
		},
		FileUploaded: function(up, files, rtn) {
			$("#report_upload_form").closest(".pop").hideLoading();
			var data = $.parseJSON(rtn.response);
			if(data.result.status == 'OK')
			{
				layer.msg("上传成功.");
				var fileWorktype = $("#report_upload_form [name='fileWorktype']").val();
				if('fileWorktype:17' == fileWorktype)
				{
					initLxReportTable();
				}
				else if('fileWorktype:18' == fileWorktype)
				{
					$("#jdqd_report_table").bootstrapTable('refresh');
				}
				else if('fileWorktype:19' == fileWorktype)
				{
					$("#jdzj_report_table").bootstrapTable('refresh');
				}
				$("#report_upload_form").closest("#powindow").find("[data-close='close']").click();
			}
			else
			{	
				reportUploader.splice(0, reportUploader.files.length);
				$("#report_upload_form [name='fileName']").val("");
				layer.msg(data.result.message);
			}
		},
		Error: function(up, err) {
			layer.msg(err.message);
		}
	}
});
reportUploader.init();
</script>