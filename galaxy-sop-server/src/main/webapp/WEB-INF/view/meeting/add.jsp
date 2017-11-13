<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
%>
<!--ck富文本编辑器 -->
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ckeditor/config.js"></script>
<script type="text/javascript" src="<%=path %>/ckeditor/lang/zh-cn.js"></script>
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/lib/jquery.poshytip.js"></script>



<form id="meeting_form" class="myprojecttc new_poptxt myproject_add">
<c:choose>
	<c:when test="${empty entity }">
<div class="title_bj">添加${meetingTypeDesc}记录</div>
	</c:when>
	<c:otherwise>
<div class="title_bj">编辑${meetingTypeDesc}记录</div>	
	</c:otherwise>
</c:choose>
<c:if test="${!empty entity }">
<input type="hidden" name="id" value="${entity.id }">
</c:if>
<input type="hidden" name="projectId" value="${projectId }">
<input type="hidden" name="meetingType" value="${meetingType }">
<div class="tab_con">
	<div class="min clearfix">
		<dl class="fmdl fml clearfix">
			<dt>会议时间：</dt>
			<dd class="clearfix">
				<input type="text" class="datetimepickerHour txt time" value="${entity.meetingDateStr }" readonly
					id="meetingDateStr" name="meetingDateStr" required="true" data-msg-required="<font color=red>*</font><i></i>必填"
					style="height: 23px; width: 150px;"/>
			</dd>
		</dl>
	</div>
	<div class="intw_summary">
        <dl class="fmdl clearfix">
            <dt id="toobar_content">会议纪要：</dt>
            <dd>
                <textarea id="viewNotes">${entity.meetingNotes }</textarea> 
                <span id="viewNotes-error" class="error" for="viewNotes"><font color="red">*</font><i></i>不能超过5000字</span>
            </dd>
        </dl>           
    </div>
    <dl class="fmdl clearfix">
        <dt id="toobar_voice">会议录音：</dt>
        <dd>
        	<p id="file_object"></p>
            <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a>
        </dd>
    </dl>
    <dl class="fmdl clearfix check_result">
        <dt id="toobar_result">会议结论：</dt>
        <c:choose>
        	<c:when test="${meetingType=='meetingType:2' }">
        <dd id="resultRadion">	
        	</c:when>
        	<c:when test="${meetingType=='meetingType:5' }">
        <dd id="resultRadion" class="spresults">	
        	</c:when>
        	<c:otherwise>
        <dd id="resultRadion" class="spresult">
        	</c:otherwise>
        </c:choose>
       	<c:forEach var="result" items="${meetingResultList }" varStatus="status">
       		<div class="clearfix" id="div_${status.index }">
       		<c:choose>
       			<c:when test="${result.code=='meeting1Result:4' or result.code=='meeting3Result:6' or result.code=='meeting4Result:3' or result.code=='meeting5Result:2' or result.code=='meetingResult:3'}">
       				<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="${result.code}" />${result.name}</label>
       				<div class="resel_box">
       					<select required="required" data-msg-required="<font color=red>*</font><i></i>必选" disabled="disabled" class="disabled" name="resultReason" id="meetingVetoReason">
	           				<option value="">请选择原因</option>
	           				<c:forEach var="item" items="${meetingVetoReason }">
	           				<option value="${item.code }">${item.name }</option>
	           				</c:forEach>
	           			</select>
	           		</div>
	         		<div class="reason_box">
	         			<input type="text" disabled="disabled" required data-msg-required="<font color=red>*</font><i></i>必填" name="reasonOther" id="meetingVetoReasonOther" class="txt disabled" placeholder="请填写其它原因"  maxlength="50" data-rule-reasonOther="true">
	         		</div>
       			</c:when>
       			<c:when test="${result.code=='meetingResult:2'}">
       				<label><input name="meetingResult" type="radio" required  data-msg-required="<font color=red>*</font><i></i>必选" value="${result.code}" />${result.name}</label>
       				<div class="resel_box">
       					<select required="required" data-msg-required="<font color=red>*</font><i></i>必选" disabled="disabled" class="disabled" name="resultReason" id="meetingUndeterminedReason" >
	           				<option value="">请选择原因</option>
	           				<c:forEach var="item" items="${meetingUndeterminedReason }">
	           				<option value="${item.code }">${item.name }</option>
	           				</c:forEach>
	           			</select>
	           		</div>
	         		<div class="reason_box">
	         			<input type="text" disabled="disabled" required data-msg-required="<font color=red>*</font><i></i>必填" name="reasonOther" id="meetingUndeterminedReason" class="txt disabled" placeholder="请填写其它原因"  maxlength="50" data-rule-reasonOther="true">
	         		</div>
       			</c:when>
       			<c:when test="${result.code=='meeting5Result:1'}">
       				<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="${result.code}" />${result.name}</label>
       				<div class="resel_box">
       					<select required="required" data-msg-required="<font color=red>*</font><i></i>必选" disabled="disabled" class="disabled" name="resultReason" id="meetingFollowingReason" >
	           				<option value="">请选择原因</option>
	           				<c:forEach var="item" items="${meetingFollowingReason }">
	           				<option value="${item.code }">${item.name }</option>
	           				</c:forEach>
	           			</select>
	           		</div>
	         		<div class="reason_box">
	         			<input type="text" disabled="disabled" required data-msg-required="<font color=red>*</font><i></i>必填" name="reasonOther" id="meetingFollowingReasonOther" class="txt disabled" placeholder="请填写其它原因"  maxlength="50" >
	         		</div>
       			</c:when>
       			
       			<c:otherwise>
        			<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="${result.code}" />${result.name}</label>
       			</c:otherwise>
       		</c:choose>
       		</div>
       	</c:forEach>
            
        </dd>
    </dl>
    <div class="save_button mt_50">
    	<button type=button id="save_meeting" class="pubbtn bluebtn">保存</button>
        <a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="close-btn">取消</a>
    </div>
</div>	
</form>
<script>
//如果是添加，关闭上一弹窗；如果是编辑忽略
<c:if test="${empty entity}">
$.popupOneClose();
</c:if>
//会议纪要富文本
var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'538px'});
viewNotes.on( 'change', function() {   //访谈纪要 
	var viewNotesLen=viewNotes.document.getBody().getText().trim().length;
	if(viewNotesLen>5000){
		$("#viewNotes-error").show();
	}else{
		$("#viewNotes-error").hide();
	}
   
});
viewNotes.on( 'keyup', function() {    //兼容ie10
	var viewNotesLen=viewNotes.document.getBody().getText().trim().length;
	if(viewNotesLen>5000){
		$("#viewNotes-error").show();
	}else{
		$("#viewNotes-error").hide();
	}
   
});

//表单验证
jQuery.validator.addMethod("reasonOther", function(value, element) {  
	var reasonOther =/\s*\S+/;
	return this.optional(element) || (reasonOther.test(value));
}, "<font color=red>*</font><i></i>不能全为空格"); 
var validator = $("#meeting_form").validate({
	focusCleanup:true,
	onfocusout:false,
	onclick:false,
	focusCleanup:true
});

/**
 * 选择会议结果
 */
$("#resultRadion input[name='meetingResult']").click(function(){
	$(".resel_box select").attr("disabled","disabled").addClass("disabled").val('');
	$(".reason_box input").attr("disabled","disabled").addClass("disabled").val('');
	//否决/跟进/待定
	var _result = $("#resultRadion input:checked");
	var _reason = _result.parent().parent().find("select[name='resultReason']");
	var _other = _result.parent().parent().find("input[name='reasonOther']");
	if(_reason.length >0)
	{
		_reason.removeAttr("disabled").removeClass("disabled");
	}
});
/**
 * 选择其他原因
 */
$("select[name='resultReason']").change(function(){
	var _this = $(this);
	var val = _this.find("option:selected").val();
	if(val == _this.find("option:last").val())
	{
		_this.parent().next().find("input[name='reasonOther']").removeAttr("disabled").removeClass("disabled");
	}
});
//文件上传/提交会议信息
var index;
var url = Constants.sopEndpointURL+"/galaxy/meeting/save"
var fileUploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : $("#select_btn")[0], 
	url : url,
	multipart:true,
	multi_selection:false,
	filters : {
		max_file_size : '50MB',
		mime_types: paramsFilter(1)
	},
	init: {
		PostInit: function(up){
			$("#save_meeting").click(function(){
				if(!validator.form()){
					return;
				}
				var data = $.parseJSON($("#meeting_form").serializeObject());
				data.meetingNotes = $.trim(CKEDITOR.instances.viewNotes.getData());
				if(up.files.length > 0){
					up.settings.multipart_params = data;  
					fileUploader.start();
				}
				else
				{
					sendPostRequestByJsonObj(url,data,function(data){
						var result = data.result.status;
						if(result == "ERROR"){ //OK, ERROR
                            layer.msg(data.result.message);
                            $("#save_meeting").removeClass("disabled");
							return;
						}else{
							layer.msg("保存成功", {time : 500});							
							$("#close-btn").click();
							$.locksCreenOpen();
							//$.popupTwoClose();
							$('#data-table').bootstrapTable('refresh');
						}
					});
				}
				return false;
			});
		},
		FilesAdded: function(up, files) {
			if(fileUploader.files.length >= 2){
				fileUploader.splice(0, fileUploader.files.length-1)
			}
			plupload.each(files, function(file) {
				var size=up.settings.filters.max_file_size.replace("MB","");   
				var fileSize = 0;
				if (navigator.userAgent.indexOf('Mac') != -1) {
					 fileSize = file.size / 1000/1000;
					 if(parseInt(fileSize) > parseInt(size) * 1000){
							layer.msg("最大支持"+size+"MB");
							return false;
					 }
				} else {
					 fileSize = file.size / 1024/1024;
					 if(parseInt(fileSize) > parseInt(size)){
							layer.msg("最大支持"+size+"MB");
							return false;
					 }
				}
				$("#file_object").removeClass("no_bg");
				$("#file_object").text(file.name);
				$("#select_btn").next().find("input").hide();
				$("#select_btn").text("更新");
				$("#file_object").addClass("audio_name");
			});
		},
		FileUploaded: function(up, files, rtn) {  //上传回调
			layer.close(index);
			var response = $.parseJSON(rtn.response);
			var rs = response.result.status;
			if(rs == "ERROR"){ //OK, ERROR
				$("#save_meeting").removeClass("disabled");
				$("#file_object").text("");
				$("#select_btn").text("选择文件");
				$("#file_object").removeClass("audio_name")
				viewuploader.splice(0, viewuploader.files.length)
				layer.msg(response.result.message);
				return false;
			}else{
				layer.msg("保存成功", {time : 500});
				$("#close-btn").click();
				//$.popupTwoClose();
				$('#data-table').bootstrapTable('refresh');
			}
		},
		BeforeUpload:function(up){
			index = layer.load(1, {
			  shade: [0.1,'#fff'] 
			});
		},
		Error: function(up, err) {
			$("#powindow").hideLoading();
			$("#save_meeting").removeClass("disabled");
			//$("#file_object").text("");
			$("#select_btn").text("选择文件");
			//$("#file_object").removeClass("audio_name");
			if(err.code=="-600"){
				layer.msg("最大支持"+up.settings.filters.max_file_size);
			}else if(err.code=="-601"){
				layer.msg("文件类型错误");
			}
			return false;
		}
	}
});
fileUploader.init();


$(function(){
	//回显文件
	<c:if test="${!empty file}">
	//文件
	$("#file_object").removeClass("no_bg");
	$("#file_object").text('${file.fileName}.${file.fileSuffix}');
	$("#select_btn").next().find("input").hide();
	$("#select_btn").text("更新");
	$("#file_object").addClass("audio_name");
	</c:if>
	//结果回显
	$("#resultRadion input[name='meetingResult']").on('showback',function(){
		var _reason = $(this).parent().parent().find("select[name='resultReason']");
		if(!_reason.hasClass("disabled"))
		{
			_reason.val('${entity.resultReason}')
			_reason.change();
			var _other = $(this).parent().parent().find("input[name='reasonOther']");
			if(!_other.hasClass("disabled"))
			{
				_other.val('${entity.reasonOther}')
			}
		}
	});
	<c:if test="${!empty entity}">
	$("input[name='meetingResult'][value='${entity.meetingResult}']").attr("checked",true).click().trigger('showback');
	</c:if>
});
</script>