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



<div class="title_bj">添加${meetingTypeDesc}记录</div>
<form id="meeting-form">
<input type="hidden" name="projectId" value="${projectId }">
<input type="hidden" name="meetingType" value="${meetingType }">
<div class="meetingtc margin_45" id="add_meet_tc">
	<div class="min clearfix">
		<dl class="fmdl fml clearfix">
			<dt>会议时间：</dt>
			<dd class="clearfix">
				<input type="text" class="datetimepickerHour txt time" readonly
					id="meetingDateStr" name="meetingDateStr"
					style="height: 23px; width: 150px;" valType="required"
					msg="<font color=red>*</font>会议日期不能为空" />
			</dd>
		</dl>
	</div>
	<div class="intw_summary">
        <dl class="fmdl clearfix">
            <dt id="toobar_content">访谈纪要：</dt>
            <dd>
                <textarea id="viewNotes"></textarea> 
                <span id="viewNotes-error" class="error" for="viewNotes"><font color="red">*</font><i></i>不能超过5000字</span>
            </dd>
        </dl>           
    </div>
    <dl class="fmdl clearfix">
        <dt id="toobar_voice"会议>访谈录音：</dt>
        <dd>
        	<p id="file_object"></p>
            <a href="javascript:;" class="pubbtn fffbtn" id="select_btn" style="position: relative; z-index: 1;">选择文件</a>
        </dd>
    </dl>
    <dl class="fmdl clearfix check_result">
        <dt id="toobar_result">会议结论：</dt>
        <dd id="resultRadion" class="spresults">
       	<c:forEach var="result" items="${meetingResultList }">
       		<c:choose>
       			<c:when test="${result.code=='meeting1Result:4' or result.code=='meeting3Result:6' or result.code=='meeting4Result:3' or result.code=='meeting5Result:2'}">
       			<div class="clearfix">
       				<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="result.code" />${result.name}</label>
       				<div class="resel_box">
       					<select required="required" disabled="disabled" class="disabled" name="meetingVetoReason" id="meetingVetoReason" data-msg-required="<font color=red>*</font><i></i>必选">
	           				<option value="">请选择原因</option>
	           				<c:forEach var="item" items="${meetingVetoReason }">
	           				<option value="${item.code }">${item.name }</option>
	           				</c:forEach>
	           			</select>
	           		</div>
	         		<div class="reason_box">
	         			<input type="text" disabled="disabled" name="meetingVetoReasonOther" id="meetingVetoReasonOther" class="txt disabled" placeholder="请填写其它原因" data-msg-required="<font color=red>*</font><i></i>必填" maxlength="50" data-rule-reasonOther="true">
	         		</div>
       			</div>
       			</c:when>
       			<c:when test="${result.code=='meetingResult:2'}">
       			<div class="clearfix">
       				<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="result.code" />${result.name}</label>
       				<div class="resel_box">
       					<select required="required" disabled="disabled" class="disabled" name="meetingUndeterminedReason" id="meetingUndeterminedReason" data-msg-required="<font color=red>*</font><i></i>必选">
	           				<option value="">请选择原因</option>
	           				<c:forEach var="item" items="${meetingUndeterminedReason }">
	           				<option value="${item.code }">${item.name }</option>
	           				</c:forEach>
	           			</select>
	           		</div>
	         		<div class="reason_box">
	         			<input type="text" disabled="disabled" name="meetingUndeterminedReasonOther" id="meetingUndeterminedReason" class="txt disabled" placeholder="请填写其它原因" data-msg-required="<font color=red>*</font><i></i>必填" maxlength="50" data-rule-reasonOther="true">
	         		</div>
       			</div>
       			</c:when>
       			<c:when test="${result.code=='meeting5Result:1'}">
       			<div class="clearfix">
       				<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="result.code" />${result.name}</label>
       				<div class="resel_box">
       					<select required="required" disabled="disabled" class="disabled" name="meetingFollowingReason" id="meetingFollowingReason" data-msg-required="<font color=red>*</font><i></i>必选">
	           				<option value="">请选择原因</option>
	           				<c:forEach var="item" items="${meetingFollowingReason }">
	           				<option value="${item.code }">${item.name }</option>
	           				</c:forEach>
	           			</select>
	           		</div>
	         		<div class="reason_box">
	         			<input type="text" disabled="disabled" name="meetingFollowingReasonOther" id="meetingFollowingReasonOther" class="txt disabled" placeholder="请填写其它原因" data-msg-required="<font color=red>*</font><i></i>必填" maxlength="50" data-rule-reasonOther="true">
	         		</div>
       			</div>
       			</c:when>
       			
       			<c:otherwise>
	        		<label><input name="meetingResult" type="radio" required data-msg-required="<font color=red>*</font><i></i>必选" value="result.code" />${result.name}</label>
       			</c:otherwise>
       		</c:choose>
       	
       	</c:forEach>
            
        </dd>
    </dl>
    <div class="btnbox" id="btnNotBeUse">
    	<a href="javascript:;" class="pubbtn bluebtn" id="savemeet">保存</a>
    	<a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>	
</form>
<script>
var viewNotes=CKEDITOR.replace('viewNotes',{height:'100px',width:'538px'});
</script>