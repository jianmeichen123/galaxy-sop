<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<!-- 添加访谈记录弹出层 -->
<div class="meetingtc">
	<div class="top clearfix">
        <dl class="fmdl clearfix">
            <dt>访谈日期：</dt>
            <dd>
            	<input class="form-control" type="date" id="viewDate" name="viewDate"/>
            </dd>
        </dl>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>访谈对象：</dt>
            <dd class="clearfix">
                <input type="text" id="viewTarget" name="viewTarget" placeholder="访谈对象" class="txt">
            </dd>
        </dl>
    </div>
    <dl class="fmdl clearfix">
        <dt>会议纪要：</dt>
        <dd>
            <div type="text/plain" id="viewNotes" style="width:100%;height:100px;"></div>
        </dd>
    </dl>
    <dl class="fmdl clearfix">
        <dt>会议录音：</dt>
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="file_object" class="txt" readonly="readonly"/>
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="select_btn">上传录音</a>
    		</dd>
        </div>
    </dl>
    <div class="btnbox">
    	<a href="javascript:;" id="save_interview" class="pubbtn bluebtn">保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>
<link href="http://fx.qa.galaxyinternet.com/sop/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath() %>/js/jquery-1.11.1.js" type="text/javascript"></script>
<script type="text/javascript">
	UM.getEditor('viewNotes');
</script>