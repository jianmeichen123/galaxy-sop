<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js" charset="UTF-8"></script>
<div class="qualificationstc">

	<div class="title_bj" id="qualifications_popup_name"></div>
	
        <div class="qualifications_all" id="working">
        
        	<form action="" id="add_person_work" method="post">
    	
	    	<!-- 工作--项目的成员 id -->
	    	<input type="hidden" name="personId" id="work_person_Id" />
	    	
	    	<!-- 工作-- id -->
	    	<input type="hidden" name="id" id="work_id" value="" />
	                    
			<!-- name="beginWorkStr"  name="overWorkStr"  name="companyName" name="workPosition"  -->               
	                    
            <div class="info clearfix">
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;时间：</dt>
                   	<dd>   <!-- type="text" class="datetimepickerHour txt time fl" -->
	                	<input name="beginWorkStr" type="text" class="datepicker-text fl txt time" readonly valType="required" msg="<font color=red>*</font>开始时间不能为空"/>
	                	<span class="fl">&nbsp;至&nbsp;</span>
	                	<input name="overWorkStr" type="text" class="datepicker-text txt time" readonly valType="required" msg="<font color=red>*</font>结束时间不能为空"/>
                    </dd>
                </dl>
                
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;任职公司名称：</dt>
                    <dd class="clearfix">
                        <input type="text" name="companyName" class="txt" valType="required" msg="<font color=red>*</font>任职公司不能为空"/>
                    </dd>
                </dl>
                
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;职位：</dt>
                    <dd>
                    	<input type="text" name="workPosition" class="txt" valType="required" msg="<font color=red>*</font>职位不能为空"/>
                    </dd>
                </dl>
                
            </div>
        </form>
    </div>
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_per_work" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>

<jsp:include page="../../common/validateJs.jsp" flush="true"></jsp:include>

<script>

$('input[name="beginWorkStr"], input[name="overWorkStr"]').val(new Date().format("yyyy-MM-dd"));

/* $('input[name="beginWorkStr"], input[name="overWorkStr"]').datepicker({
    format: 'yyyy-mm-dd',
    language: "zh-CN",
    autoclose: true,
    todayHighlight: false,
    defaultDate : Date,
    today: "Today",
    todayBtn:'linked',
    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
    forceParse:false,
    currentText: 'Now'
}); */

$(function(){
	
	initDialogValstr("working");
	
	$("#save_per_work").click(function(){
		if(beforeSubmitById("working")){
			if(beTimeCompare($("#add_person_work [name='beginWorkStr']").val(),$("#add_person_work [name='overWorkStr']").val())){
				savePersonWork();
			}
		}
	});
});


</script>


