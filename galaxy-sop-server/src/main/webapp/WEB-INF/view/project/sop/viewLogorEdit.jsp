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

<!-- 日历插件 -->
<link href="<%=path %>/bootstrap/bootstrap-datepicker/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="<%=path %>/bootstrap/bootstrap-datepicker/js/rangeDate.js"></script>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>


<!-- 添加访谈记录弹出层 -->
<div class="meetingtc margin_45">
<div class="title_bj">访谈日志</div>
	<div>
        <dl>
        <input type="hidden" id="vid">
            <!-- <dt>访谈时间：</dt> -->
            <dd>
            	<%-- <input class="form-control" type="date" id="viewDate" name="viewDate" valType="required" msg="<font color=red>*</font>创建时间不能为空"/> --%>
                <input type="text" class="datepicker txt time" id="viewDate" name="viewDate" readonly value="" valType="required" msg="<font color=red>*</font>创建时间不能为空" style=
                'display:none;'/>
            </dd>
        </dl>
    </div>
    <%-- <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>访谈对象：</dt>
            <dd class="clearfix">
                <input type="text" id="viewTarget" name="viewTarget" regString="^.{1,50}$" placeholder="访谈对象" class="txt" valType="OTHER" msg="<font color=red>*</font>访谈对象不能为空且不能超过50字符"/>
            </dd>
        </dl>
    </div> --%>
    <dl class="fmdl clearfix">
        <dd style='position: relative'>
			<script id="viewNotes" name="content" type="text/plain" style="width:680px"></script>
			<div class="tip-yellowsimple" id='hint_all' style="visibility: inherit; display: none; left: 678px; top: 60.472px; opacity: 1; width: 169.778px;"><div class="tip-inner tip-bg-image"><font color="red">*</font>访谈纪要不能超过9000字节</div><div class="tip-arrow tip-arrow-left" style="visibility: inherit;"></div></div>
        </dd>
    </dl>
<!--     <dl class="fmdl clearfix">
        <dt>访谈录音：</dt>
        <div class="fmload clearfix">
            <dd>
	        	<input type="text" name="fileName" id="file_object" class="txt" readonly="readonly"/>
	        </dd>
	        <dd>
	        	<a href="javascript:;" class="pubbtn fffbtn" id="select_btn">上传录音</a>
    		</dd>
        </div>
    </dl> -->
    <div class="btnbox save_log">
    	<a href="javascript:;" id="interviewsave" class="pubbtn bluebtn" onclick="interviewsave()">保存</a><a href="javascript:;" class="pubbtn fffbtn"data-close="close">取消</a>
    </div>
</div>
<script type="text/javascript">
	//UM.getEditor('viewNotes');
	$("#viewDate").val(new Date().format("yyyy-MM-dd"));
</script>