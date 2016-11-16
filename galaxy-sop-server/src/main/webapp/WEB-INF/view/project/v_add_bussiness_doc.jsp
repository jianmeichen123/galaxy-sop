<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 文件上传 -->
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=path %>/js/jquery.showLoading.min.js"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/js/plupload.full.min.js"></script>
<div id="uploadPanel">
	<div class="title_bj">上传更新</div>
	<div class="meetingtc margin_45">
	<dl class="fmdl clearfix">
    	<dt>档案来源：</dt>
        <dd class="clearfix">
        	<label><input name="fileSource" type="radio" value = "1" checked="checked"/>内部</label>
            <label><input name="fileSource" type="radio" value = "2"/>外部</label>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
    	<dt>存储类型：</dt>
        <dd>
        	<select id="fileType" disabled="disabled" class="disabled">
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
         	<select id="fileWorktype" disabled="disabled" class="disabled">
         		<option value="fileWorktype:12">商业计划书</option>
         	</select>
        </dd>
    </dl>
     <dl class="fmdl clearfix">
    	<dt>文档上传：</dt>
        <dd>
        	<input type="text" class="txt" id="win_fileTxt" readonly="readonly"/>
        </dd>
        <dd> <a href="javascript:;" class="pubbtn fffbtn" id="win_selectBtn">选择档案</a></dd>
    </dl>  
    <a href="javascript:;" class="pubbtn bluebtn" id="win_uploadBtn" style="margin-left:80px;">上传保存</a>
	</div>
</div>

<!--  文件提示  -->
<!-- <div id="file-tip" class="tip-yellowsimple" style="display:none;visibility: inherit; left: 511px; top: 271px;">
    <div class="tip-inner tip-bg-image">
        <font color="red">*</font>商业计划书不能为空
    </div>
    <div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div>
</div> -->
<script type="text/javascript">
$(function(){
	var fileValidate = false;
	var uploader = new plupload.Uploader({
		runtimes : 'html5,html4,flash,silverlight',
        browse_button : 'win_selectBtn',
        url : Constants.sopEndpointURL + "/galaxy/project/inBuessDoc",
        multipart:true,//为true时将以multipart/form-data的形式来上传文件，为false时则以二进制的格式来上传文件
		multi_selection:false,
		max_retries:0,//重试次数
		//chunk_size:"200kb",//分片上传
		filters : {
			mime_types : [
	           { title : "doc files", extensions : "doc,docx,ppt,pptx,pps,xls,xlsx,pdf,txt,pages,key,numbers,DOC,DOCX,PPT,PPTX,PPS,XLS,XLSX,PDF,TXT,PAGES,KEY,NUMBER" }
	        ],
			max_file_size : '25mb',
			prevent_duplicates : true //不允许选取重复文件
		},
		headers : {}//Object类型，设置上传时的自定义头信息，以键/值对的形式传入
    });
	uploader.init();  
	uploader.bind('FilesAdded',function(uploader,files){
    	//解决多次文件选择后，文件都存入upload
		if(uploader.files.length >= 1){
			fileValidate = true;
			$("#file-tip").css("display","none");
			uploader.splice(0, uploader.files.length-1);
		}
		plupload.each(files, function(file) {
			$("#win_fileTxt").val(file.name);
		});
    });
	uploader.bind('PostInit',function(uploader){
    	var params = {};
    	params.tempPid=pid;
    	var fileKey = $("#file_key").val();
    	if(typeof(fileKey) != 'undefined' && fileKey != ''){
    		params.fileKey = $("#file_key").val();
    	}
		params.fileSource = $('input[name="fileSource"]:checked').val();
		params.fileType = $("#fileType").val();
		params.fileWorktype = $("#fileWorktype").val();
    	uploader.settings.multipart_params = params;
    });
	uploader.bind('BeforeUpload',function(uploader){
		$("#powindow").showLoading(
				 {
				    'addClass': 'loading-indicator'						
				 });
		$("#win_uploadBtn").addClass("disabled");
	});
    uploader.bind('FileUploaded',function(uploader,files,rtn){
    	$("#powindow").hideLoading();
    	$("#win_uploadBtn").removeClass("disabled");
    	var response = $.parseJSON(rtn.response);
		if(response.result.status == "OK"){
			$.popupOneClose();
			$.locksCreenOpen();
			generateBuessDocInnerHtml(response.entity);
			$("#buess_doc").val(1);
			//下一步变亮
			var plan_business_table_val=$("#plan_business_table tbody td").eq(0).text()
			if(step2Valiate("step2") && plan_business_table_val!="-"){
				$("[data-btn='page1'] span[data-btn='next']").removeClass("disabled");
				return;
			}
		}else{
			layer.msg(response.result.message);
		}
    });
    $("#win_uploadBtn").click(function(){
    	if(!fileValidate){
    		$("#file-tip").css("display","block");
    		$.popupOneClose();
    		$.locksCreenOpen();
    	}
    	if(fileValidate){
    		uploader.start();
    		$.locksCreenOpen();
    	}
    });
});
</script>