$(function(){
	var pid = $("#pid").val();
	/**
	 * 生成左边菜单项列表
	 */
	createMenus(4);
	/**
	 * 保存项目描述
	 */
	$("#save_describe").click(function(){
		var um = UM.getEditor('describe_editor');
		var projectDescribe = um.getContent();
		if(pid != '' && projectDescribe != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectDescribe" : projectDescribe}, function(){});
		}
	});
	/**
	 * 保存商业模式
	 */
	$("#save_business_model").click(function(){
		var um = UM.getEditor('business_model_editor');
		var projectBusinessModel = um.getContent();
		if(pid != '' && projectBusinessModel != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectBusinessModel" : projectBusinessModel}, function(){});
		}
	});
	/**
	 * 保存公司定位
	 */
	$("#save_location").click(function(){
		var um = UM.getEditor('location_editor');
		var companyLocation = um.getContent();
		if(pid != '' && companyLocation != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "companyLocation" : companyLocation}, function(){});
		}
	});
	/**
	 * 保存用户画像
	 */
	$("#save_portrait").click(function(){
		var um = UM.getEditor('portrait_editor');
		var userPortrait = um.getContent();
		if(pid != '' && userPortrait != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "userPortrait" : userPortrait}, function(){});
		}
	});
	/**
	 * 保存尽情分析
	 */
	$("#save_analysis").click(function(){
		var um = UM.getEditor('analysis_editor');
		var prospectAnalysis = um.getContent();
		if(pid != '' && prospectAnalysis != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "prospectAnalysis" : prospectAnalysis}, function(){});
		}
	});
	/**
	 * 加载项目详情数据
	 */
	sendGetRequest(platformUrl.detailProject + pid, {}, function(data){
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
		$("#describe_show").text(replaceStr(data.entity.projectDescribe));
		$("#model_show").text(replaceStr(data.entity.projectBusinessModel));
		$("#portrait_show").text(replaceStr(data.entity.userPortrait));
		$("#location_show").text(replaceStr(data.entity.companyLocation));
		$("#analysis_show ").text(replaceStr(data.entity.prospectAnalysis));
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
	});
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
	function replaceStr(str){
		var result=str.replace(/&nbsp;/g,"").replace("<p>","").replace("</p>","");
		return result;
	}
	
	
	

});

/***
 * 添加团队成员表单验证
 * @returns {Boolean}
 */
function checkFormValida(){
	  var personName = $("#personName").val();
	  var personAge = $("#personAge").val();
	  var personDuties = $("#personDuties").val();
	  
	  if(personName==""){
			$("#personName").focus();
	        layer.tips('请输入用户名', '#personName');
	        return false;
	  }
	  
	  if(personAge==""){
			$("#personAge").focus();
	        layer.tips('请输入年龄', '#personAge');
	        return false;
	  }
	  
	  if(personDuties==""){
			$("#personDuties").focus();
	        layer.tips('请输入职务', '#personDuties');
	        return false;
	  }
	  return true;
}
/** 添加团队成员
	 */

function savePerson(){
	
	
	if(checkFormValida()){
	var projectId = $("#pid").val();
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendPostRequestByJsonObj(platformUrl.addPerson, JSON.parse($("#person_form").serializeObject()), savePersonCallBack);
	}}
}


/**
 * 添加股权结构
 */
function savaStock(){
	
	var projectId = $("#pid").val();
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendPostRequestByJsonObj(platformUrl.addStock, JSON.parse($("#stock_form").serializeObject()), saveProjectCallBack);
	}
}

/**
 * 修改股权结构
 */
function updateStock(){
	var projectId = $("#pid").val();
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendPostRequestByJsonObj(platformUrl.updateStock, JSON.parse($("#up_stock_form").serializeObject()),saveProjectCallBack);
	}
	//window.location.reload("/galaxy/upp");
}

/**
 * 删除股权结构
 * @param id
 */
function delStock(id,url){
	var projectId = $("#pid").val();
	var url = sopContentUrl + "/galaxy/projectShares/deleteProjectShares/"+id+"/"+projectId;
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendGetRequest(url,'',saveProjectCallBack);
	}
}

/**
 * 修改团队成员
 */
function updatePerson(){
	var projectId = $("#pid").val();
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendPostRequestByJsonObj(platformUrl.updatePerson, JSON.parse($("#up_person_form").serializeObject()),savePersonCallBack);
	}
}
/**
 * 删除团队成员
 * @param id
 */
function deletePer(id,url){
	var projectId = $("#pid").val();
	var url = sopContentUrl + "/galaxy/project/dpp/"+id+"/"+projectId;
	if(projectId != ''){
		$("#projectId").val(projectId);
		sendGetRequest(url,'',savePersonCallBack);
	}
}

//保存成功回调
function savePersonCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//alert("error "+data.result.message);
		return;
	}
	//alert("操作成功!");
	$("#popbg,#powindow").remove();
	//window.location.reload(sopContentUrl + "/galaxy/upp");
	getTabPerson();
}

//保存成功回调
function saveProjectCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//alert("error "+data.result.message);
		return;
	}
	//alert("操作成功!");
	$("#powindow,#popbg").remove();
	//window.location.reload(sopContentUrl + "/galaxy/upp");
	getTabShare();
}

function refresh(){
	window.location.href=window.location.href;
}

//删除成功回调
function deleteProjectCallBack(data){
	var result = data.result.status;
	if(result == "ERROR"){ //OK, ERROR
		//alert("error "+data.result.message);
		return;
	}
	//alert("删除成功!");
	window.location.reload(sopContentUrl + "/galaxy/upp");
}

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
 * 更新项目信息
 */
function update(){
	sendPostRequestByJsonObj(platformUrl.updateProject, JSON.parse($("#update_form_basic").serializeObject()), function(){});
}