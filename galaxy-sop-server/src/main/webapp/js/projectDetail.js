$(function(){
	var pid = $("#pid").val();
/**
	 * 加载项目详情数据
	 */
	sendGetRequest(platformUrl.detailProject + pid, {}, function(data){
		
		$("#project_name_title").text(data.entity.projectName);
		$("#project_name").text(data.entity.projectName);
		$("#project_code").text(data.entity.projectCode);
		$("#create_date").text(data.entity.createDate);
		$("#updateDate").text(data.entity.updateDate);
		$("#createUname").text(data.entity.createUname);
		$("#projectCareerline").text(data.entity.projectCareerline);
		$("#projectType").text(data.entity.type);
		$("#project_contribution").text(data.entity.projectContribution);
		$("#project_valuations").text(data.entity.projectValuations);
		$("#project_share_ratio").text(data.entity.projectShareRatio);
		
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
		$("#describe_show").html(data.entity.projectDescribe==null?"暂无项目概述":data.entity.projectDescribe);
		$("#model_show").html(data.entity.projectBusinessModel==null?"暂无商业模式":data.entity.projectBusinessModel);
		$("#portrait_show").html(data.entity.userPortrait==null?"暂无用户分析":data.entity.userPortrait);
		$("#location_show").html(data.entity.companyLocation==null?"暂无公司定位":data.entity.companyLocation);
		$("#analysis_show ").html(data.entity.prospectAnalysis==null?"暂无竞情分析":data.entity.prospectAnalysis);
		$("#operational_data").html(data.entity.operationalData==null?"暂无运营数据":data.entity.operationalData);
		$("#industry_analysis").html(data.entity.industryAnalysis==null?"暂无行业分析":data.entity.industryAnalysis);
		$("#next_financing_source").html(data.entity.nextFinancingSource==null?"暂无下一轮融资路径":data.entity.nextFinancingSource);
		
		if(data.entity.operationalData){
			$("#operational_data_editor").html(data.entity.operationalData);
			$("#operational_data").hide();
		}else{
			$("#operational_data_editor").html('');
		}
		if(data.entity.industryAnalysis){
			$("#industry_analysis_editor").html(data.entity.industryAnalysis);
			$("#industry_analysis").hide();
		}else{
			$("#industry_analysis_editor").html('');
		}
		if(data.entity.nextFinancingSource){
			$("#next_financing_source_editor").html(data.entity.nextFinancingSource);
			$("#next_financing_source").hide();
		}else{
			$("#next_financing_source_editor").html('');
		}
		
		//var um = UM.getEditor('describe_editor');
		if(data.entity.projectDescribe){
			$("#describe_editor").html(data.entity.projectDescribe);
			$("#descript").hide();
			
		}else{
			$("#describe_editor").html('');
		}
		if(data.entity.projectBusinessModel){
			$("#business_model_editor").html(data.entity.projectBusinessModel)
			$("#business_model").hide();
			
		}else{
			$("#business_model_editor").html('')
		}
		if(data.entity.companyLocation){
			$("#location_editor").html(data.entity.companyLocation)
			$("#location").hide();
		}else{
			$("#location_editor").html('')
		}
		if(data.entity.userPortrait){
			$("#portrait_editor").html(data.entity.userPortrait)
			$("#portrait").hide();
		}else{
			$("#portrait_editor").html('')
		}
		if(data.entity.prospectAnalysis){
			$("#analysis_editor").html(data.entity.prospectAnalysis)
			$("#analysis").hide();
		}else{
			$("#analysis_editor").html('')
		}
		
		
		var formdata = {
				_projectId : projectId,
				_projectName : data.entity.projectName,
				_domId : 'business_plan'
		}
		initPage.init(formdata);
		
//		sendGetRequest(platformUrl.getBusinessPlanFile+"/"+pid,null,function(data){
//			var uploadOperator;
//			var html;
//			if(data.result.status=="OK"){
//				console.log(data);
//				//为空时候显示
//				if(data.result.errorCode=="null"){
//					uploadOperator = "上传";
//					$("#uploadtime").text("无");
//					$("#is_upload").text("未上传");
//					$("#bpName").text("");
//					$("#uploadOperator").text(uploadOperator);
//				}else{
//					//不为空时候显示
//					uploadOperator = "更新";
//					$("#uploadtime").text(data.entity.createDate);
//					$("#is_upload").text("已上传");
//					$("#bpName").text("");
//					$("#uploadOperator").text(uploadOperator);
//				}
//			}else{
//				
//			}
//		});
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

});