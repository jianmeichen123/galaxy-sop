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
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectDescribe" : projectDescribe}, saveSuccess());
		}
	});
	function saveSuccess(){
		$("edit").hide();
		$("[data-btn='describe']").show();
		$("[data-btn='edit']").show();
		$("[data-btn='reset']").hide();
		$("[data-btn='submit']").hide();
		window.location.reload();		
	}
	$("[data-btn='reset']").click(function(){
		window.location.reload();
	})

	/**
	 * 保存商业模式
	 */
	$("#save_business_model").click(function(){
		var um = UM.getEditor('business_model_editor');
		var projectBusinessModel = um.getContent();
		if(pid != '' && projectBusinessModel != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectBusinessModel" : projectBusinessModel}, saveSuccess());
		}
	});
	/**
	 * 保存公司定位
	 */
	$("#save_location").click(function(){
		var um = UM.getEditor('location_editor');
		var companyLocation = um.getContent();
		if(pid != '' && companyLocation != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "companyLocation" : companyLocation}, saveSuccess());
		}
	});
	/**
	 * 保存用户画像
	 */
	$("#save_portrait").click(function(){
		var um = UM.getEditor('portrait_editor');
		var userPortrait = um.getContent();
		if(pid != '' && userPortrait != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "userPortrait" : userPortrait}, saveSuccess());
		}
	});
	/**
	 * 保存尽情分析
	 */
	$("#save_analysis").click(function(){
		var um = UM.getEditor('analysis_editor');
		var prospectAnalysis = um.getContent();
		if(pid != '' && prospectAnalysis != ''){
			sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "prospectAnalysis" : prospectAnalysis}, saveSuccess());
		}
	});
	/**
	 * 加载项目详情数据
	 */
	sendGetRequest(platformUrl.detailProject + pid, {}, function(data){
		$("#project_name").text(data.entity.projectName);
		$("#project_code").text(data.entity.projectCode);
		$("#create_date").text(data.entity.createDate);
		//$("#create_date").val(data.entity.createDate);
		$("#projectName").text(data.entity.projectName);
		$("#projectType").text(data.entity.type);
		$("#project_contribution").val(data.entity.projectContribution);
		$("#project_valuations").val(data.entity.projectValuations);
		$("#project_share_ratio").val(data.entity.projectShareRatio);
		
		var currencyUnit = data.entity.currencyUnit;
		var redioId = "";
		if(currencyUnit==null||typeof(currencyUnit) == 'undefined'||isNaN(currencyUnit)){
			redioId = "#currencyUnit";
		}else{
			$("#currencyUnitBlock").css("display","none").remove();
			redioId = "#currencyUnit"+currencyUnit;
		}
		$(redioId).attr("checked","checked");
		
		$("#project_company").val(data.entity.projectCompany);
		$("#project_company_code").val(data.entity.projectCompanyCode);
		
/*		$("#describe_show").text(replaceStr(data.entity.projectDescribe));
		$("#model_show").text(replaceStr(data.entity.projectBusinessModel));
		$("#portrait_show").text(replaceStr(data.entity.userPortrait));
		$("#location_show").text(replaceStr(data.entity.companyLocation));竟情分析暂无公司定位
		$("#analysis_show ").text(replaceStr(data.entity.prospectAnalysis));*/
		$("#describe_show").html(data.entity.projectDescribe==null?"暂无项目概述":data.entity.projectDescribe);
		$("#model_show").html(data.entity.projectBusinessModel==null?"暂无商业模式":data.entity.projectBusinessModel);
		$("#portrait_show").html(data.entity.userPortrait==null?"暂无用户分析":data.entity.userPortrait);
		$("#location_show").html(data.entity.companyLocation==null?"暂无公司定位":data.entity.companyLocation);
		$("#analysis_show ").html(data.entity.prospectAnalysis==null?"暂无竞情分析":data.entity.prospectAnalysis);
		
		//var um = UM.getEditor('describe_editor');
		if(data.entity.projectDescribe){
			//var um = UM.getEditor('describe_editor');
			$("#describe_editor").html(data.entity.projectDescribe)
			//um.setContent(data.entity.projectDescribe);
		}else{
			$("#describe_editor").html('');
		}
		
		//var um = UM.getEditor('business_model_editor');
		if(data.entity.projectBusinessModel){
			$("#business_model_editor").html(data.entity.projectBusinessModel)
			//um.setContent(data.entity.projectBusinessModel);
		}else{
			$("#business_model_editor").html('')
			//um.setContent("");
		}
		//var um = UM.getEditor('location_editor');
		if(data.entity.companyLocation){
			$("#location_editor").html(data.entity.companyLocation)
			//um.setContent(data.entity.companyLocation);
		}else{
			$("#location_editor").html('')
			//um.setContent("");
		}
		
		//var um = UM.getEditor('portrait_editor');
		if(data.entity.userPortrait){
			$("#portrait_editor").html(data.entity.userPortrait)
			//um.setContent(data.entity.userPortrait);
		}else{
			$("#portrait_editor").html('')
			//um.setContent("");
		}
		//var um = UM.getEditor('analysis_editor');
		if(data.entity.prospectAnalysis){
			$("#analysis_editor").html(data.entity.prospectAnalysis)
			//um.setContent(data.entity.prospectAnalysis);
		}else{
			$("#analysis_editor").html('')
			//um.setContent("");
		}
		
		
		sendGetRequest(platformUrl.getBusinessPlanFile+"/"+pid,null,function(data){
			var uploadOperator;
			var html;
			if(data.result.status=="OK"){
				console.log(data);
				//为空时候显示
				if(data.result.errorCode=="null"){
					uploadOperator = "上传";
					html = "<span class='content'>状态：未上传</span><span class='content'>更新时间：无</span><a href='javascript:;' class='ico f1' data-btn='upload' onclick='uploadBusinessPlan()' >"+ uploadOperator  +"</a>";
				}else{
					//不为空时候显示
					uploadOperator = "更新";
					html = "<span class='content'>状态：已上传</span><span class='content'>更新时间："+data.entity.createDate+"</span><a href='javascript:;' class='ico f1' data-btn='upload' onclick='uploadBusinessPlan()' >"+ uploadOperator  +"</a>" +
						   "<a href='javascript:;' class='ico f1' data-btn='download' onclick='downloadBusinessPlan(" + data.entity.id +")' >下载</a>";
				}
				$("#business_plan_dd").html(html);
			}else{
				
			}
		});
		
		
		
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
	/*function replaceStr(str){
		if(str){
			var result=str.replace(/&nbsp;/g,"").replace("<p>","").replace("</p>","");
			return result;
		}
	
	}*/
	
	
	

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
	
	if(beforeSubmit()){
		var projectId = $("#pid").val();
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendPostRequestByJsonObj(platformUrl.addPerson, JSON.parse($("#person_form").serializeObject()), savePersonCallBack);
		}
	}
	
}


/**
 * 添加股权结构
 */
function savaStock(){
	if(beforeSubmit()){
		var projectId = $("#pid").val();
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendPostRequestByJsonObj(platformUrl.addStock, JSON.parse($("#stock_form").serializeObject()), saveProjectCallBack);
		}
	}
}

/**
 * 修改股权结构
 */
function updateStock(){
	if(beforeSubmit()){
		var projectId = $("#pid").val();
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendPostRequestByJsonObj(platformUrl.updateStock, JSON.parse($("#up_stock_form").serializeObject()),saveProjectCallBack);
		}
	}
}

/**
 * 删除股权结构
 * @param id
 */
function delStock(id,url){
	if(confirm("确定要删除该股权结构吗？")){
		var projectId = $("#pid").val();
		var url = platformUrl.deleteProjectShares+id+"/"+projectId;
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendGetRequest(url,'',saveProjectCallBack);
		}
	}
}

/**
 * 修改团队成员
 */
function updatePerson(){
	if(beforeSubmit()){
		var projectId = $("#pid").val();
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendPostRequestByJsonObj(platformUrl.updatePerson, JSON.parse($("#up_person_form").serializeObject()),savePersonCallBack);
		}
	}
}
/**
 * 删除团队成员
 * @param id
 */
function deletePer(id,url){
	if(confirm("确定要删除该团队成员吗？")){
		var projectId = $("#pid").val();
		var url = platformUrl.deletePPerson+id+"/"+projectId;
		if(projectId != ''){
			$("#projectId").val(projectId);
			sendGetRequest(url,'',savePersonCallBack);
		}
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
	//window.location.reload(Constants.sopEndpointURL + "/galaxy/upp");
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
	//window.location.reload(Constants.sopEndpointURL + "/galaxy/upp");
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
	window.location.reload(Constants.sopEndpointURL + "/galaxy/upp");
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
	if(beforeSubmit()){
		sendPostRequestByJsonObj(platformUrl.updateProject, JSON.parse($("#update_form_basic").serializeObject()), function(){
			layer.msg("修改项目基本信息项目成功!");
			window.location.reload();
		});
	}
	
}