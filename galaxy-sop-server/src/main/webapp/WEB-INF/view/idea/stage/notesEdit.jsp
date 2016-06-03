<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />

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

<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<div class="meetingtc">

	<input type="hidden" id="notesid" />
    <dl class="fmdl clearfix">
        <dt id="notetitle">会议纪要：</dt>
        <dd>
            <div type="text/plain" id="notes" style="width:100%;height:100px;" valType="requiredDiv" regString="^.{0,9000}$" msg="<font color=red>*</font>文本录入不能超过9000字符"></div>
        </dd>
    </dl>
    
    <div class="btnbox save_log">
    	<a href="javascript:;" id="savenotes" class="pubbtn bluebtn" onclick="savenotes()">保存</a>
    	<a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>

<script type="text/javascript">
	UM.getEditor('notes');
</script>
