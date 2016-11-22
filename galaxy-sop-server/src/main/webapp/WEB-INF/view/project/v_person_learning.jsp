<%@ page language="java" pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=path %>/bootstrap/bootstrap-datepicker/js/datepicker-init.js" charset="UTF-8"></script>

<div class="qualificationstc">
	<div class="title_bj" id="qualifications_popup_name"></div>
        <div class="qualifications_all" id="learning">
        	<form action="" id="add_person_learning" method="post">
            <div class="info clearfix">
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;时间：</dt>
                    <dd class="clearfix">
                    	<input name="beginDateStr" type="text" class="datepicker-text fl txt time" readonly valType="required" msg="<font color=red>*</font>开始时间不能为空"/>
                    	<span class="fl">&nbsp;至&nbsp;</span>
                    	<input name="overDateStr" type="text" class="datepicker-text fl txt time" readonly valType="required" msg="<font color=red>*</font>结束时间不能为空"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;毕业院校：</dt>
                    <dd class="clearfix">
                        <input name="school" type="text" class="txt" valtype="OTHER" regstring="^.{1,50}$" msg="<font color=red>*</font>不能为空且字符长度最大50"/>
                    </dd>
                </dl>
                <dl class="fmdl fl">
                    <dt><em class="red">*</em>&nbsp;专业：</dt>
                    <dd><input name="major" type="text" class="txt" valtype="OTHER" regstring="^.{1,50}$" msg="<font color=red>*</font>不能为空且字符长度最大50"/></dd>
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
        <a href="javascript:;"  class="register_all_affrim fl" id="save_person_learning" >确定</a>
        <a href="javascript:;"  class="register_all_input fr"  data-close="close">取消</a>
    </div>
</div>
<jsp:include page="../common/validateJs.jsp" flush="true"></jsp:include>
<script>

$(function(){
	$('input[name="beginDateStr"], input[name="overDateStr"]').val(new Date().format("yyyy-MM-dd"));
	initDialogValstr("learning");
	var uuid = $('input[name="uuid"]').val();
	$("#save_person_learning").click(function(){
		if(beforeSubmitById("learning")){
			
			var bbbb = beTimeCompare($('input[name="beginDateStr"]').val(),$('input[name="overDateStr"]').val());
			if(bbbb == false || bbbb == "false"){
				layer.msg("开始时间不能大于结束时间");
				return;
			}
			
			sendPostRequestByJsonStr(Constants.sopEndpointURL + "/galaxy/project/savePersonLearning/"+uuid+"/"+pid, 
					$("#add_person_learning").serializeObject(), 
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