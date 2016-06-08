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
		$("#projectProgress").text(data.entity.projectProgress);
		$("#projectStatusDs").text(data.entity.projectStatusDs);
		$("#financeStatusDs").text(data.entity.financeStatusDs);
		
		$("#project_name_edit").val(data.entity.projectName);
		$("#project_code").text(data.entity.projectCode);
		$("#create_date_edit").text(data.entity.createDate);
		$("#updateDate_edit").text(data.entity.updateDate);
		$("#createUname_edit").text(data.entity.createUname);
		$("#projectCareerline_edit").text(data.entity.projectCareerline);
		$("#projectType_edit").text(data.entity.type);
		$("#project_contribution_edit").val(data.entity.projectContribution);
		$("#project_valuations_edit").val(data.entity.projectValuations);
		$("#project_share_ratio_edit").val(data.entity.projectShareRatio);
		$("#projectProgress_edit").text(data.entity.projectProgress);
		$("#projectStatusDs_edit").text(data.entity.projectStatusDs);
		$("#financeStatusDs_edit").text(data.entity.financeStatusDs);
		
		
		
		var currencyUnit = data.entity.currencyUnit;
		var redioId = "";
		if(currencyUnit==null||typeof(currencyUnit) == 'undefined'||isNaN(currencyUnit)){
			redioId = "#currencyUnit";
		}else{
			$("#currencyUnitBlock").css("display","none").remove();
			redioId = "#currencyUnit"+currencyUnit;
		}
		$(redioId).attr("checked","checked");
		if(data.entity.operationalData){
			$("#operational_data_show").html(data.entity.operationalData);
			$("#operational_data").hide();
		}else{
			$("#operational_data_show").html('');
		}
		if(data.entity.industryAnalysis){
			$("#industry_analysis_show").html(data.entity.industryAnalysis);
			$("#industry_analysis").hide();
		}else{
			$("#industry_analysis_show").html('');
		}
		if(data.entity.nextFinancingSource){
			$("#next_financing_source_show").html(data.entity.nextFinancingSource);
			$("#next_financing_source").hide();
		}else{
			$("#next_financing_source_show").html('');
		}
		
		//var um = UM.getEditor('describe_editor');
		if(data.entity.projectDescribe){
			$("#describe_show").html(data.entity.projectDescribe);
			$("#descript").hide();
			
		}else{
			$("#describe_show").html('');
		}
		if(data.entity.projectBusinessModel){
			$("#business_model_show").html(data.entity.projectBusinessModel)
			$("#business_model").hide();
			
		}else{
			$("#business_model_show").html('')
		}
		if(data.entity.companyLocation){
			$("#location_show").html(data.entity.companyLocation)
			$("#location").hide();
		}else{
			$("#location_show").html('')
		}
		if(data.entity.userPortrait){
			$("#portrait_show").html(data.entity.userPortrait)
			$("#portrait").hide();
		}else{
			$("#portrait_show").html('')
		}
		if(data.entity.prospectAnalysis){
			$("#analysis_show").html(data.entity.prospectAnalysis)
			$("#analysis").hide();
		}else{
			$("#analysis_show").html('')
		}
		var formdata = {
				_projectId : projectId,
				_projectName : data.entity.projectName,
				_domId : 'business_plan'
		}
		initPage.init(formdata);
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