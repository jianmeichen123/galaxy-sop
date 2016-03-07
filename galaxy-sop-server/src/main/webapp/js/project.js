$(function(){
	/**
	 * 生成左边菜单项列表
	 */
	createMenus(4);
	/**
	 * 保存项目描述
	 */
	$("#save_describe").click(function(){
		var pid = $("#pid").val();
		var um = UM.getEditor('describe_editor');
		var projectDescribe = um.getContent();
		if(pid != '' && projectDescribe != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectDescribe" : projectDescribe}, function(){},null);
		}
	});
	/**
	 * 保存商业模式
	 */
	$("#save_business_model").click(function(){
		var pid = $("#pid").val();
		var um = UM.getEditor('business_model_editor');
		var projectBusinessModel = um.getContent();
		if(pid != '' && projectBusinessModel != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectBusinessModel" : projectBusinessModel}, function(){},null);
		}
	});
	/**
	 * 保存公司定位
	 */
	$("#save_location").click(function(){
		var pid = $("#pid").val();
		var um = UM.getEditor('location_editor');
		var companyLocation = um.getContent();
		if(pid != '' && companyLocation != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "companyLocation" : companyLocation}, function(){},null);
		}
	});
	/**
	 * 保存用户画像
	 */
	$("#save_portrait").click(function(){
		var pid = $("#pid").val();
		var um = UM.getEditor('portrait_editor');
		var userPortrait = um.getContent();
		if(pid != '' && userPortrait != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "userPortrait" : userPortrait}, function(){},null);
		}
	});
	/**
	 * 保存尽情分析
	 */
	$("#save_analysis").click(function(){
		var pid = $("#pid").val();
		var um = UM.getEditor('analysis_editor');
		var prospectAnalysis = um.getContent();
		if(pid != '' && prospectAnalysis != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "prospectAnalysis" : prospectAnalysis}, function(){},null);
		}
	});
	/**
	 * 加载项目详情数据
	 */
	sendGetRequest(platformUrl.detailProject + "10", {}, function(data){
		$("#pid").val(data.entity.id);
		$("#project_name").text(data.entity.projectName);
		$("#project_code").text(data.entity.projectCode);
		$("#create_date").text(data.entity.createDate);
		$("#projectName").text(data.entity.projectName);
		$("#projectType").text(data.entity.type);
		$("#project_contribution").val(data.entity.projectContribution);
		$("#project_valuations").text(data.entity.projectValuations);
		$("#project_share_ratio").val(data.entity.projectShareRatio);
		var redioId = "#currencyUnit" + data.entity.currencyUnit;
		$(redioId).attr("checked","checked");
		$("#project_company").val(data.entity.projectCompany);
		$("#project_company_code").val(data.entity.projectCompanyCode);
		var um = UM.getEditor('describe_editor');
		um.setContent(data.entity.projectDescribe);
		var um = UM.getEditor('business_model_editor');
		um.setContent(data.entity.projectBusinessModel);
		var um = UM.getEditor('location_editor');
		um.setContent(data.entity.companyLocation);
		var um = UM.getEditor('portrait_editor');
		um.setContent(data.entity.userPortrait);
		var um = UM.getEditor('analysis_editor');
		um.setContent(data.entity.prospectAnalysis);
	},null);
	
	/**
	 * 计算初始估值
	 */
	$("#project_share_ratio").blur(function(){
		var valuations = calculationValuations();
		$("#project_valuations").text("");
		if(valuations){
			$("#project_valuations").text(valuations);
		}
	});
	/**
	 * 计算初始估值
	 */
	$("#project_contribution").blur(function(){
		var valuations = calculationValuations();
		$("#project_valuations").text("");
		if(valuations){
			$("#project_valuations").text(valuations);
		}
	});
});
/**
 * 计算初始估值
 */
function calculationValuations(){
	var projectShareRatio = $("#project_share_ratio").val();
	var projectContribution = $("#project_contribution").val();
	if(projectShareRatio > 0 && projectContribution > 0){
		return projectContribution * (100/projectShareRatio);
	}
	return null;
}
/**
 * 添加团队成员
 */
function addPerson(){
	var projectId = $("#pid").val();
	alert(projectId);
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendPostRequestByJsonObj(platformUrl.addPerson, JSON.parse($("#person_form").serializeObject()), function(){},null);
	}
}
/**
 * 更新项目信息
 */
function update(){
	sendPostRequestByJsonObj(platformUrl.updateProject, JSON.parse($("#update_form_basic").serializeObject()), function(){},null);
}
