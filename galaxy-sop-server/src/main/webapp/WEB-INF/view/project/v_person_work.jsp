<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js" charset="UTF-8"></script>

<div class="qualificationstc">
	<div class="title_bj" id="qualifications_popup_name"></div>
        <div class="qualifications_all" id="work">
        	<form action="" id="add_person_work" method="post">
            <div class="info clearfix">
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;时间：</dt>
                    <dd>
                    	<input name="beginWorkStr" type="text" class="datepicker-text fl txt time" readonly valType="required" msg="<font color=red>*</font>开始时间不能为空"/>
                    	<span class="fl">&nbsp;至&nbsp;</span>
                    	<input name="overWorkStr" type="text" class="datepicker-text txt time" readonly valType="required" msg="<font color=red>*</font>结束时间不能为空"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;任职公司：</dt>
                    <dd class="clearfix">
                        <input name="companyName" type="text" class="txt" valType="required" msg="<font color=red>*</font>任职公司不能为空"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;职位：</dt>
                    <dd>
						<input name="workPosition" type="text" class="txt" valType="required" msg="<font color=red>*</font>职位不能为空"/>
					</dd>
                </dl>
            </div>
            </form>
        </div>
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="save_person_work" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script>
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
	$('input[name="beginWorkStr"], input[name="overWorkStr"]').val(new Date().format("yyyy-MM-dd"));
	initDialogValstr("work");
	var uuid = $('input[name="uuid"]').val();
	$("#save_person_work").click(function(){
		if(beforeSubmitById("work")){
			sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/savePersonWork/"+uuid+"/"+pid, 
					$("#add_person_work").serializeObject(), 
					function(data){
				$.popupTwoClose();
				if(data.result.status == 'OK'
					&& typeof(data.entityList) != 'undefined' 
					&& data.entityList.length > 0){
					generateWorkInnerHtml(data.entityList);
					$("#person-work").val(data.entityList.length);
					$("#work-tip").hide();
				}else{
					generateWorkEmptyInnerHtml();
				}
			});
		}
	});
});
</script>