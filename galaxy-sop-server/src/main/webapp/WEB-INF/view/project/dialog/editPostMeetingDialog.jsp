<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.galaxyinternet.com/fx" prefix="fx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<% 
	String path = request.getContextPath(); 
%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>/js/validate/fx.validate.css" />
<link rel="stylesheet" href="<%=path %>/css/showLoading.css"  type="text/css">
<div id="post-meeting-dialog">
	<form id="win_post_meeting_form">
	<div class="editPostMeetingtc">
		<div class="title_bj" id="popup_name">添加运营会议纪要</div>
	    <div class="form clearfix">
	        <div class="conference_all">
	            <dl class="fmdl clearfix">
	                <dt>会议时间：</dt>
	                <dd>
	                <input type="hidden" name="meetingName" id="meetingName"/>
	                    <input type="text" class="datetimepickerHour txt time" id="meetingDate" name="meetingDate" valType="required" msg="<font color=red>*</font>会议时间不能为空"  /> 
	                </dd>
	            </dl>
	            <dl class="fmdl fl_l clearfix" id="edit_meeting_type">
	                 <dt>类型 ：</dt>
	                 <dd><label for=""><input name="meetingType" type="radio" >周纪要</label></dd>
	                 <dd><label for=""><input name="meetingType" type="radio">月纪要</label></dd>
	                 <dd><label for=""><input name="meetingType" type="radio">季度纪要</label></dd>
	            </dl>
	            <dl class="fmdl fl_l">
	                 <dt>会议纪要 ：</dt>
	                 <dd><textarea class="area" name="meetingNotes" id="textarea2" cols="45" rows="5"></textarea></dd>
	            </dl>  
	            <div class="affrim_line"></div> 
	        </div>
	    </div>
	    <div class="button_affrim">
	        <a href="javascript:;" id="win_ok_btn"  class="register_all_affrim fl">确认</a>
	        <a href="javascript:;" id="win_cancel_btn"  class="register_all_input fr">取消</a>
	    </div>  	
	</div>
	</form>
</div>

<!-- time -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script>
<!-- 校验 -->
<script src="<%=path %>/js/bootstrap-v3.3.6.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate.js"></script>
<script type="text/javascript" src="<%=path %>/js/validate/fx.validate-ext.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>

<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
