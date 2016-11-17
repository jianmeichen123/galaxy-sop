<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<div class="qualificationstc">
	<div class="title_bj" id="qualifications_popup_name"></div>
        <div class="qualifications_all" id="updatelearning">
        	<form action="" id="update_person_learning" method="post">
        	<input type="hidden" value="${puuid}" name="puuid">
        	<input type="hidden" value="${luuid}" name="uuid" id="luuid">
            <div class="info clearfix">
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;时间：</dt>
                    <dd>
                    	<input name="beginDateStr" type="text" class="datetimepickerHour txt time fl" readonly valType="required" msg="<font color=red>*</font>开始时间不能为空"/>
                    	<span class="fl">&nbsp;至&nbsp;</span>
                    	<input name="overDateStr" type="text" class="datetimepickerHour txt time fl" readonly valType="required" msg="<font color=red>*</font>结束时间不能为空"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;毕业院校：</dt>
                    <dd class="clearfix">
                        <input name="school" type="text" class="txt" valType="required" msg="<font color=red>*</font>毕业院校不能为空"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;专业：</dt>
                    <dd><input name="major" type="text" class="txt" valType="required" msg="<font color=red>*</font>专业不能为空"/></dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;学历：</dt>
                    <dd>
                       <select name="degree" valType="required" msg="<font color=red>*</font>学历不能为空">
                           <option value="">请选择</option>
                           <option value="高中">高中</option>
                           <option value="大专">大专</option>
                           <option value="本科">本科</option>
                           <option value="硕士">硕士</option>
                           <option value="MBA">MBA</option>
                           <option value="博士">博士</option>
                           <option value="其他">其他</option>
                       </select>
                    </dd>
                </dl>
            </div>
            </form>
        </div>
    <div class="button_affrim">
        <a href="javascript:;"  class="register_all_affrim fl" id="update_person_learn" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script type="text/javascript">
$('input[name="beginDateStr"], input[name="overDateStr"]').datepicker({
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
});
$(function(){
	var puuid = $('input[name="puuid"]').val();
	var luuid = $('#luuid').val();
	sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/lookProjectLearning/"+luuid+"/"+pid, 
			null, 
			function(data){
		$('input[name="beginDateStr"]').val(data.entity.beginDateStr);
		$('input[name="overDateStr"]').val(data.entity.overDateStr);
		$('input[name="school"]').val(data.entity.school);
		$('input[name="major"]').val(data.entity.major);
		$('select[name="degree"]').val(data.entity.degree);
	});
	initDialogValstr("updatelearning");
	$("#update_person_learn").click(function(){
		if(beforeSubmitById("updatelearning")){
			sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/updatePersonLearning/"+puuid+"/"+luuid+"/"+pid, 
					$("#update_person_learning").serializeObject(), 
					function(data){
				$.popupTwoClose();
				if(data.result.status == 'OK'
					&& typeof(data.entityList) != 'undefined' 
					&& data.entityList.length > 0){
					generateLearningInnerHtml(data.entityList);
					$("#person-learning").val(data.entityList.length);
					$("#learn-tip").hide();
				}else{
					generateLearningEmptyInnerHtml();
				}
			});
		}
	});
});
</script>