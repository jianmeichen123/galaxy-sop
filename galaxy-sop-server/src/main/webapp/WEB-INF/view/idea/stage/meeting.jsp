<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- 富文本编辑器
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
 -->

<!-- 富文本编辑器 -->
<link id="f" href="<%=path %>/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script id="a" src="<%=path %>/js/plupload.full.min.js" type="text/javascript"></script>
<script src="<%=path %>/js/plupload/zh_CN.js" type="text/javascript"></script>
<script id="b" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/dialogs/map/map.js"></script>
<script id="c" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script id="d" type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script id="e" type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>

<script>
	$("#f").attr("href",Constants.sopEndpointURL + "/ueditor/themes/default/css/umeditor.css");
	$("#a").attr("src",Constants.sopEndpointURL + "/js/plupload.full.min.js");
	$("#a").attr("src",Constants.sopEndpointURL + "/js/plupload/zh_CN.js");
	$("#b").attr("src",Constants.sopEndpointURL + "/ueditor/dialogs/map/map.js");
	$("#c").attr("src",Constants.sopEndpointURL + "/ueditor/umeditor.config.js");
	$("#d").attr("src",Constants.sopEndpointURL + "/ueditor/umeditor.min.js");
	$("#e").attr("src",Constants.sopEndpointURL + "/ueditor/lang/zh-cn/zh-cn.js");
</script>

<!-- time
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDate.js"></script>
 -->
<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<link href="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDateForHour.js"></script>

<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="meetingtc margin_45">
<div class="title_bj">添加会议纪要</div>
	<form id="win_meetingtc">
	<div class="top clearfix">
        <dl class="fmdl clearfix">
            <dt>参会时间：</dt>
            <dd>
                <!-- <input type="text" class="txt time"> -->
                <input type="text" class="datetimepickerHour txt time" readonly  id="meetingDateStr" name = "meetingDateStr"  style="height:23px;"
            	valType="required" msg="<font color=red>*</font>会议日期不能为空"  />
           
            </dd>
        </dl>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>会议结论：</dt>
            <dd class="clearfix">
                <label><input type="radio" name="meetingResult" value="meetingResult:1"/>通过</label>
                <label><input type="radio" name="meetingResult" value="meetingResult:2" checked="checked" />待定</label>
                <label><input type="radio" name="meetingResult" value="meetingResult:3"/>否决</label>
           
            </dd>
        </dl>
    </div>
    
    <dl class="fmdl clearfix">
        <dt>会议纪要：</dt>
        <dd>
            <!-- <div class="edit">  这里是编辑器 </div> -->
             <div type="text/plain" id="meetingNotes"  style="width:100%;height:150px;max-height:150px;overflow:auto;" 
       	 	 valType="MAXBYTE" regString="9000" msg="<font color=red>*</font>会议纪要不能超过9000字节" >
       	  </div>
        </dd>
    </dl>
    
    
    <dl class="fmdl clearfix">
        <dt>会议录音：</dt>
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="fileName" class="txt" readonly="readonly" />
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="cyfile-select-btn">上传录音</a>
    		</dd>
        </div>
    </dl>
    
    <div class="btnbox">
    	<a href="javascript:;" class="pubbtn bluebtn" id="savemeet">保存</a><a href="javascript:;" class="pubbtn fffbtn" data-close="close" id="cyMeetTcC">取消</a>
    </div>
    </form>
</div>

<script type="text/javascript">
		UM.getEditor('meetingNotes');
		//$("#meetingDateStr").val(new Date().format("yyyy-MM-dd"));
		$("#meetingDateStr").val(new Date().format("yyyy-MM-dd hh:mm"));
		
		//验证获取参数
		function getMeetCondition(hasProid,projectId,
				meetDateId,
				hasMeetType,meetTypeName,
				meetResultName,
				meetNotesId
				){
			
			var	condition = {};
			if(!beforeSubmit("add_meet_tc")){
				return false;
			}
			
			var projectIdVal = null;
			if(hasProid == "y" ){
				projectIdVal = $.trim(projectId);
			}else{
				projectIdVal = $("#"+projectId).val();
			}
			
			var meetingDateStr = $.trim($("#"+meetDateId).val());
			
			var meetingTypeVal = null;
			if(hasMeetType == "y" ){
				meetingTypeVal = $.trim(meetTypeName);
			}else{
				meetingTypeVal = $.trim($('input:radio[name="'+meetTypeName+'"]:checked').val());
			}
			
			var meetingResult = $.trim($('input:radio[name="'+meetResultName+'"]:checked').val());
			
			var um = UM.getEditor(meetNotesId);
			var meetingNotes = $.trim(um.getContent());
			
			if(projectIdVal == null || projectIdVal == ""){
				layer.msg("项目不能为空");
				return false;
			}
			
			/*if(meetingDateStr == null ||  meetingDateStr == ""){
				layer.msg("会议日期不能为空");
				return false;
			}else{
				var clock = getNowDay("-");
				if((new Date(meetingDateStr)) > (new Date(clock))){
					layer.msg("会议日期不能超过今天");
					return false;
		         }
			 }*/
			
			if(meetingTypeVal == null ||  meetingTypeVal == ""){
				layer.msg("类型不能为空");
				return false;
			}
			
			if(meetingResult == null ||  meetingResult == ""){
				layer.msg("结果不能为空");
				return false;
			}
			
			if(meetingNotes != null && meetingNotes.length > 0){
				if(getLength(meetingNotes) > 9000){
					layer.msg("会议记录长度最大9000字节");
					return false;
				}
			}
			
			condition.projectId = projectIdVal;
			condition.meetingDateStr = meetingDateStr;
			condition.meetingType = meetingTypeVal;
			condition.meetingResult = meetingResult;
			condition.meetingNotes = meetingNotes;
			return condition;
		}
</script>
