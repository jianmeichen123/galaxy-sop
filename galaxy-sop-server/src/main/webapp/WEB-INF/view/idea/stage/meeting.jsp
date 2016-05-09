<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- 富文本编辑器 -->
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
<!-- time -->
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDate.js"></script>

<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="meetingtc">
	<div class="top clearfix">
        <dl class="fmdl clearfix">
            <dt>参会时间：</dt>
            <dd>
                <!-- <input type="text" class="txt time"> -->
                <input type="text" class="datepicker txt time" readonly  id="meetingDateStr" name = "meetingDateStr"  style="height:23px;"
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
</div>

<script type="text/javascript">
		UM.getEditor('meetingNotes');
		$("#meetingDateStr").val(new Date().format("yyyy-MM-dd"));
</script>
