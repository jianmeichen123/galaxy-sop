<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/validate/lib/tip-yellowsimple/tip-yellowsimple.css" />
<!-- 富文本编辑器 -->
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/ueditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=path %>/ueditor/lang/zh-cn/zh-cn.js"></script>
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
<!-- 保存进度条 -->
<link href="<%=path %>/css/showLoading.css" type="text/css" rel="stylesheet"/>
<script src="<%=path %>/js/jquery.showLoading.min.js"></script>
<!-- 校验 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validate/lib/jquery.poshytip.js"></script>
<script type='text/javascript' src='<%=request.getContextPath() %>/js/validate/lib/jq.validate.js'></script>
<div class="title_bj">新增访谈</div>
<div class="meetingtc margin_45" id="inter_subm_val">
	<div class="top clearfix">
    	<div class="searchall clearfix" id="div_pro">
            <dl>
            	<dt>项目 :</dt> 
                <dd>
                	<select id="projectId" name="projectId"   
                	valType="required" msg="<font color=red>*</font>项目不能为空" >
                    </select>
                	<!-- <input type="text" id="proName" name = "proName"  placeholder="请输入关键字查找" class="txt"/>
                	<input type="hidden" id="projectId" name = "projectId" value="" /> -->
                </dd>
            </dl>
        </div>
        <dl class="fmdl clearfix fl"> 
            <dt>访谈时间：</dt>
            <dd>
            	<input type="text" class="datetimepickerHour txt time" readonly  id="viewDate" name ="viewDate" style="height:23px;width:150px;"  
            	valType="required" msg="<font color=red>*</font>访谈日期不能为空"  />
            </dd>
        </dl>
		<script type="text/javascript">
			$("#viewDate").val(new Date().format("yyyy-MM-dd hh:mm"));
		</script>
    </div>
    <div class="min clearfix">
    	<dl class="fmdl fml clearfix">
            <dt>访谈对象：</dt>
            <dd>
           		<input type="text" id="viewTarget" name = "viewTarget"  placeholder="访谈对象" class="txt" maxLength="50"  
           		valType="required" msg="<font color=red>*</font>访谈对象不能为空"  />
            </dd>
        </dl>
    </div>
    
<!-- 赋值     var um = UM.getEditor('describe_editor');
		um.setContent(data.entity.projectDescribe); 
	取值 	var um = UM.getEditor('viewNotes');
		var projectDescribe = um.getContent();
		-->
	    <dl class="fmdl clearfix">
	        <dt>访谈日志:</dt>
	        <dd style="width:675px;">
	        	 <%--  <div type="text/plain" id="viewNotes" style="width:100%; height:150px;"
	        	  valType="requiredDiv" regString="^.{0,9000}$" msg="<font color=red>*</font>访谈纪要不能超过9000字节" >
	        	  </div> --%>
	        	  
	        	  <div type="text/plain" id="viewNotes" class='width_fwb'
	        	  valType="MAXBYTE" regString="9000" msg="<font color=red>*</font>访谈纪要不能超过9000字节" >
	        	  </div>
			</dd>
        </dl>
            
    <dl class="fmdl clearfix" id="fileNotBeUse">
        <dt>访谈录音：</dt>
            <div class="fmload clearfix"  >
	            <dd>
		        	<input type="text" name="fileName" id="fileName" class="txt" readonly="readonly" />
		        </dd>
		        <dd>
		        	<a href="javascript:;" class="pubbtn fffbtn" id="file-select-btn" >上传录音</a>
	    		</dd>
            </div>
    </dl>

	<div class="btnbox" id="btnNotBeUse"> 
    	<a href="javascript:;" id="saveInterView" class="pubbtn bluebtn">保存</a>
    	<a href="javascript:;" class="pubbtn fffbtn" data-close="close">取消</a>
    </div>
    
</div>


 <script type="text/javascript">
	var interviewEditor = UM.getEditor('viewNotes');
	initDialogValstr("inter_subm_val");
</script>

