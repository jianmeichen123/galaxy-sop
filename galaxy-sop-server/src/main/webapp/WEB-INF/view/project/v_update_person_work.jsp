<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js" charset="UTF-8"></script>

<div class="qualificationstc">
	<div class="title_bj" id="qualifications_popup_name"></div>
        <div class="qualifications_all" id="updatework">
        	<form action="" id="update_person_work" method="post">
        	<input type="hidden" value="${puuid}" name="puuid">
			<input type="hidden" value="${wuuid}" name="uuid" id="wuuid">
            <div class="info clearfix">
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;时间：</dt>
                    <dd class="clearfix">
                    	<input name="beginWorkStr" type="text" class="datepicker-text fl txt time" readonly valType="required" msg="<font color=red>*</font>开始时间不能为空"/>
                    	<span class="fl">&nbsp;至&nbsp;</span>
                    	<input name="overWorkStr" type="text" class="datepicker-text fl txt time" readonly valType="required" msg="<font color=red>*</font>结束时间不能为空"/>
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
        <a href="javascript:;"  class="register_all_affrim fl" id="sure_btn" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script>
$(function(){
	var puuid = $('input[name="puuid"]').val();
	var wuuid = $('#wuuid').val();
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/lookProjectWork/"+wuuid+"/"+pid, 
			null, 
			function(data){
		$('input[name="beginWorkStr"]').val(data.entity.beginWorkStr);
		$('input[name="overWorkStr"]').val(data.entity.overWorkStr);
		$('input[name="companyName"]').val(data.entity.companyName);
		$('input[name="workPosition"]').val(data.entity.workPosition);
	});
	initDialogValstr("updatework");
	$("#sure_btn").click(function(){
		if(beforeSubmitById("updatework")){
			var bbbb = beTimeCompare($('input[name="beginWorkStr"]').val(),$('input[name="overWorkStr"]').val());
			if(bbbb == false || bbbb == "false"){
				layer.msg("开始时间不能大于结束时间");
				return;
			}
			sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/updatePersonWork/"+puuid+"/"+wuuid+"/"+pid, 
					$("#update_person_work").serializeObject(), 
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