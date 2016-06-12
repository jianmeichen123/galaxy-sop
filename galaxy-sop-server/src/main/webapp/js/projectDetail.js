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
		$("#projectProgress").text(data.entity.progress);
		$("#projectStatusDs").text(data.entity.projectStatusDs);
		$("#financeStatusDs").text(data.entity.financeStatusDs);
		$("#finalValuations").text(data.entity.finalValuations);
		$("#finalContribution").text(data.entity.finalContribution);
		$("#finalShareRatio").text(data.entity.finalShareRatio);
		$("#industryOwnDs").text(data.entity.industryOwnDs);
		
		
		
		
		//基本信息修改
		$("#project_name_edit").val(data.entity.projectName);
		$("#create_date_edit").text(data.entity.createDate);
		$("#updateDate_edit").text(data.entity.updateDate);
		$("#createUname_edit").text(data.entity.createUname);
		$("#projectCareerline_edit").text(data.entity.projectCareerline);
		$("#projectType_edit").text(data.entity.type);
		$("#project_contribution_edit").val(data.entity.projectContribution);
		$("#project_valuations_edit").val(data.entity.projectValuations);
		$("#project_share_ratio_edit").val(data.entity.projectShareRatio);
		$("#projectProgress_edit").text(data.entity.progress);
		$("#projectStatusDs_edit").text(data.entity.projectStatusDs);
		$("#financeStatusDs_edit").text(data.entity.financeStatusDs);
		$("#finalValuations_edit").val(data.entity.finalValuations);
		$("#finalContribution_edit").val(data.entity.finalContribution);
		$("#finalShareRatio_edit").val(data.entity.finalShareRatio);
		var p=data.entity.industryOwn;
		var fs=data.entity.financeStatus;
		//融资
		sendGetRequest(platformUrl.getFinanceStatusByParent+"/getFinanceStatusByParent",null,CallBackB);
		sendGetRequest(platformUrl.getDepartMentDict+"/1",null,CallBackA);
		function CallBackB(data){
		    var _dom=$("#finance_status_sel");
			 $.each(data.entityList,function(){
					if(this.code){
						if(this.code==fs){
							_dom.append("<option selected value='"+this.code+"'>"+this.name+"</option>");
						}else{
							_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
						}
						
					}else{
						_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
					}
					
				});
		}
		function CallBackA(data){
		       var _dom=$("#industry_own_sel");
				 $.each(data.entityList,function(){
						if(this.code){
							_dom.append("<option value='"+this.code+"'>"+this.name+"</option>");
						}else{
							if(this.id==p){
								_dom.append("<option selected value='"+this.id+"'>"+this.name+"</option>");
							}else{
								_dom.append("<option value='"+this.id+"'>"+this.name+"</option>");
							}
						}
						
					});
			}
		
		
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
		});
	/**
	 * 计算初始估值
	 */
	$("#project_share_ratio_edit").blur(function(){
		var valuations = calculationValuations();
		$("#project_valuations_edit").val("");
		if(valuations){
			$("#project_valuations_edit").val(valuations);
		}
	});
	/**
	 * 计算初始估值
	 * project_contribution_edit
	 * project_valuations_edit
	 * project_share_ratio_edit
	 */
	$("#project_contribution_edit").blur(function(){
		var valuations = calculationValuations();
		$("#project_valuations_edit").val("");
		if(valuations){
			$("#project_valuations_edit").val(valuations);
		}
	});
	/**
	 * 计算初始估值
	 */
	function calculationValuations(){
		var projectShareRatio = $("#project_share_ratio_edit").val();
		var projectContribution = $("#project_contribution_edit").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
	//实际值计算************************************************************
	/**
	 * 计算实际估值
	 */
	$("#finalShareRatio_edit").blur(function(){
		var valuations = finalValuations();
		$("finalValuations_edit").val("");
		if(valuations){
			$("#finalValuations_edit").val(valuations);
		}
	});
	/**
	 * 计算实际投资
	 * project_contribution_edit
	 * project_valuations_edit
	 * project_share_ratio_edit
	 */
	$("#finalContribution_edit").blur(function(){
		var valuations = finalValuations();
		$("#finalValuations_edit").val("");
		if(valuations){
			$("#finalValuations_edit").val(valuations);
		}
	});
	/**
	 * 计算初始估值
	 */
	function finalValuations(){
		var projectShareRatio = $("#finalShareRatio_edit").val();
		var projectContribution = $("#finalContribution_edit").val();
		if(projectShareRatio > 0 && projectContribution > 0){
			return projectContribution * (100/projectShareRatio);
		}
		return null;
	}
	
	$("[data-on='save']").click(function(){
		var data=getUpdateData();
	//	if(beforeSubmit()){
			sendPostRequestByJsonObj(platformUrl.updateProject,data, function(){
				layer.msg("修改项目基本信息项目成功!");
				window.location.reload();
			});
	//	}
		
		
		
	})
	
	
});
function getUpdateData(){
	var id=$("#pid").val();
	var pname=$("#project_name_edit").val();
	var industry_own=$("#industry_own_sel").val();
	var finance_status=$("#finance_status_sel").val();
	var project_contribution=$("#project_contribution_edit").val();
	var project_valuations=$("#project_valuations_edit").val();
	var project_share_ratio=$("#project_share_ratio_edit").val();
	var finalcontribution=$("#finalContribution_edit").val();
	var finalvaluations=$("#finalValuations_edit").val();
	var finalshare_ratio=$("#finalShareRatio_edit").val();
	var formatData={"id":id,
			       "projectName":pname,
			        "industryOwn":industry_own,
			        "financeStatus":finance_status,
			       "projectValuations":project_valuations,
			       "projectContribution" :project_contribution,
			       "projectShareRatio":project_share_ratio,
			       "finalValuations":finalvaluations,//实际估值
                   "finalContribution":finalcontribution,//实际投资
  	               "finalShareRatio":finalshare_ratio	//实际股权占比		
	};
	return formatData;
}