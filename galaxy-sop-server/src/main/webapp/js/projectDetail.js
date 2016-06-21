$(function(){
	var pid = $("#pid").val();
   /**
	 * 加载项目详情数据
	 */
	var projectPro = projectInfo.projectProgress;
	var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);

		$("#project_name_title").text(projectInfo.projectName);
		$("#project_name").text(projectInfo.projectName);
		$("#project_code").text(projectInfo.projectCode);
		$("#create_date").text(projectInfo.createDate);
		$("#updateDate").text(projectInfo.updateDate);
		$("#createUname").text(projectInfo.createUname);
		$("#projectCareerline").text(projectInfo.projectCareerline);
		$("#projectType").text(projectInfo.type);
		$("#project_contribution").text(projectInfo.projectContribution==0||typeof(projectInfo.projectContribution)=="undefined"?"--":projectInfo.projectContribution);
		$("#project_valuations").text(projectInfo.projectValuations==0||typeof(projectInfo.projectValuations)=="undefined"?"--":projectInfo.projectValuations);
		$("#project_share_ratio").text(projectInfo.projectShareRatio==0||typeof(projectInfo.projectShareRatio)=="undefined"?"--":projectInfo.projectShareRatio);
		$("#projectProgress").text(projectInfo.progress);
		$("#projectStatusDs").text(projectInfo.projectStatusDs);
		$("#financeStatusDs").text(projectInfo.financeStatusDs==null?"不明确":projectInfo.financeStatusDs);
		$("#finalValuations").text(projectInfo.finalValuations==0?"--":projectInfo.finalValuations);
		$("#finalContribution").text(projectInfo.finalContribution==0?"--":projectInfo.finalContribution);
		$("#finalShareRatio").text(projectInfo.finalShareRatio==0?"--":projectInfo.finalShareRatio);
		$("#industryOwnDs").text(projectInfo.industryOwnDs);
		var ht=projectProgress(data)
		$("#insertImg").html(ht);
		var p;
		var fs;
		$("[data-on='data-open']").click(function (){
			//基本信息修改
			$("#editImg").html(ht);
			$("#project_name_edit").val(projectInfo.projectName);
			$("#create_date_edit").text(projectInfo.createDate);
			$("#updateDate_edit").text(projectInfo.updateDate);
			$("#createUname_edit").text(projectInfo.createUname);
			$("#projectCareerline_edit").text(projectInfo.projectCareerline);
			$("#projectType_edit").text(projectInfo.type);
			$("#project_contribution_edit").val(projectInfo.projectContribution==0?"":projectInfo.projectContribution);
			$("#project_valuations_edit").val(projectInfo.projectValuations==0?"":projectInfo.projectValuations);
			$("#project_share_ratio_edit").val(projectInfo.projectShareRatio==0?"":projectInfo.projectShareRatio);
			$("#projectProgress_edit").text(projectInfo.progress);
			$("#projectStatusDs_edit").text(projectInfo.projectStatusDs);
			$("#financeStatusDs_edit").text(projectInfo.financeStatusDs);
			$("#finalValuations_edit").val(projectInfo.finalValuations==0?"":projectInfo.finalValuations);
			$("#finalContribution_edit").val(projectInfo.finalContribution==0?"":projectInfo.finalContribution);
			$("#finalShareRatio_edit").val(projectInfo.finalShareRatio==0?"":projectInfo.finalShareRatio);
			 p=projectInfo.industryOwn;
		    fs=projectInfo.financeStatus;
			//融资
			sendGetRequest(platformUrl.getFinanceStatusByParent+"/getFinanceStatusByParent",null,CallBackB);
			sendGetRequest(platformUrl.getDepartMentDict+"/1",null,CallBackA);
			
			initDialogVal();
		
		})
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
		if(projectInfo.projectDescribe){
			var um = UM.getEditor('describe_editor');
			$("#describe_show").html(projectInfo.projectDescribe);
			um.setContent(projectInfo.projectDescribe);
			$("#descript").hide();
			$('.describe_show').show();
			display_show("describe_show");
		}else{
			$('.describe_show').hide();
			$("#describe_show").html('');
		}
		if(projectInfo.projectBusinessModel){
			var um = UM.getEditor('business_editor');
			um.setContent(projectInfo.projectBusinessModel);
			$("#business_model_show").html(projectInfo.projectBusinessModel)
			$("#business_model").hide();
			$('.business_model_show').show();
			display_show("business_model_show");
		}else{
			$("#business_model_show").html('')
			$('.business_model_show').hide();
		}
		if(projectInfo.companyLocation){
			var um = UM.getEditor('company_editor');
			um.setContent(projectInfo.companyLocation);
			$("#location_show").html(projectInfo.companyLocation)
			$("#location").hide();
			$('.location_show').show();
			display_show("location_show");
		}else{
			$("#location_show").html('')
			$('.location_show').hide();
		}
		if(projectInfo.userPortrait){
			var um = UM.getEditor('portrait_editor');
			um.setContent(projectInfo.userPortrait);
			$("#portrait_show").html(projectInfo.userPortrait);
			$("#portrait").hide();
			$(".portrait_show").show();
			display_show("portrait_show");
		}else{
			$("#portrait_show").html('')
			$(".portrait_show").hide();
		}
		if(projectInfo.prospectAnalysis){
			var um = UM.getEditor('analysis_editor');
			um.setContent(projectInfo.prospectAnalysis);
			$("#analysis_show").html(projectInfo.prospectAnalysis)
			$("#analysis").hide();
			$(".analysis_show").show();
			display_show("analysis_show");
		}else{
			$("#analysis_show").html('');
			$(".analysis_show").hide();
		}

		if(projectInfo.operationalData){
			var um = UM.getEditor('operation_editor');
			um.setContent(projectInfo.operationalData);
			$("#operational_data_show").html(projectInfo.operationalData);
			$("#operational_data").hide();
			$(".operational_data_show").show();
			display_show("operational_data_show");
		}else{
			$("#operational_data_show").html('');
			$(".operational_data_show").hide();
		}
		if(projectInfo.industryAnalysis){
			var um = UM.getEditor('industry_editor');
			um.setContent(projectInfo.industryAnalysis);
			$("#industry_analysis_show").html(projectInfo.industryAnalysis);
			$("#industry_analysis").hide();
			$(".industry_analysis_show").show();
			display_show("industry_analysis_show");
		}else{
			$("#industry_analysis_show").html('');
			$(".industry_analysis_show").hide();
		}
		if(projectInfo.nextFinancingSource){
			var um = UM.getEditor('next_financing_editor');
			um.setContent(projectInfo.nextFinancingSource);
			$("#next_financing_source_show").html(projectInfo.nextFinancingSource);
			$("#next_financing_source").hide();
			$(".next_financing_source_show").show();
			display_show("next_financing_source_show");
		}else{
			$("#next_financing_source_show").html('');
			$(".next_financing_source_show").hide();
		}

		function  display_show(obj){
			
			var height=$('#'+obj).outerHeight();
			if(height>100){
				var str='';
				str+='<span class="show_more">',
				str+='<span style="display: block;"  class="blue open ico1 f4" >展开</span> <span style="display: none;" href="#" class="blue searchbox_hidden hide ico1 f3" >收起</span>',
				str+='</span>';
				$('#'+obj).append(str);
				$('#'+obj).parent().css('height','100px')
			}
		}
		$('.new_top_color').delegate(".f4","click",function(){
			$(this).hide();
			$(this).parent().children('.f3').show();
			$(this).parent().parent().parent().css('height','auto')
		}) 
		$('.new_top_color').delegate(".f3","click",function(){
			$(this).hide();
			$(this).parent().children('.f4').show();
			$(this).parent().parent().parent().css('height','100px')
		}) 
		/**
		 * 商业计划
		 */
		var data = {
				_projectId : pid,
				_projectName : projectInfo.projectName,
				_domId :'business_plan'			
		}
		initPage.init(data);
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
			return (projectContribution * (100/projectShareRatio)).toFixed(2);
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
			return (projectContribution * (100/projectShareRatio)).toFixed(2);
		}
		return null;
	}

	function projectProgress(data){
		var projectPro = projectInfo.projectProgress;
		var num = projectPro.substring(projectPro.lastIndexOf(":")+1,projectPro.length);
		var proStatus = projectInfo.projectStatus;
		var pronum = proStatus.substring(proStatus.lastIndexOf(":")+1,proStatus.length);
		if( pronum != 2){
			return "<img src='"+Constants.sopEndpointURL+"img/process/p"+num+".gif' >";
		}else{
			return "<img src='"+Constants.sopEndpointURL+"img/process/pd"+num+".gif'>";
		}
	}
	$("[data-on='save']").click(function(){
		var data=getUpdateData();
		if(beforeSubmit()){
			sendPostRequestByJsonObj(platformUrl.updateProject,data, function(){
				layer.msg("修改项目基本信息项目成功!");
				window.location.reload();
			});
		}
	})

});
function getUpdateData(){
	var id=$("#pid").val();
	var pname=$("#project_name_edit").val().trim();
	var industry_own=$("#industry_own_sel").val().trim();
	var finance_status=$("#finance_status_sel").val().trim();
	
	var project_contribution=$("#project_contribution_edit").val()==""?null:$("#project_contribution_edit").val().trim();
	var project_valuations=$("#project_valuations_edit").val()==""?null:$("#project_valuations_edit").val().trim();
	var project_share_ratio=$("#project_share_ratio_edit").val()==""?null:$("#project_share_ratio_edit").val().trim();
	var finalcontribution=$("#finalContribution_edit").val()==""?null:$("#finalContribution_edit").val().trim();
	var finalvaluations=$("#finalValuations_edit").val()==""?null:$("#finalValuations_edit").val().trim();
	var finalshare_ratio=$("#finalShareRatio_edit").val()==""?null:$("#finalShareRatio_edit").val().trim();
	
	
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

function saveSuccess(){
	$(".project_on").css("display","none");
	window.location.reload();
}
/**
 * 保存项目描述
 */
$("#save_describe").click(function(){
	var um = UM.getEditor('describe_editor');
	var projectDescribe = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectDescribe" : projectDescribe}, saveSuccess());
	}
});
/**
 * 保存项目描述
 */
$("#save_location").click(function(){
	var um = UM.getEditor('company_editor');
	var companyLocation = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "companyLocation" : companyLocation}, saveSuccess());
	}
});
/**
 * 保存项目描述
 */
$("#save_portrait").click(function(){
	var um = UM.getEditor('portrait_editor');
	var userPortrait = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "userPortrait" : userPortrait}, saveSuccess());
	}
});
/**
 * 保存项目描述
 */
$("#save_business").click(function(){
	var um = UM.getEditor('business_editor');
	var projectBusinessModel = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "projectBusinessModel" : projectBusinessModel}, saveSuccess());
	}
});
/**
 * 保存项目描述
 */
$("#save_operation").click(function(){
	var um = UM.getEditor('operation_editor');
	var operationalData = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "operationalData" : operationalData}, saveSuccess());
	}
});

/**
 * 保存项目描述
 */
$("#save_industry").click(function(){
	var um = UM.getEditor('industry_editor');
	var industryAnalysis = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "industryAnalysis" : industryAnalysis}, saveSuccess());
	}
});

/**
 * 保存项目描述
 */
$("#save_analysis").click(function(){
	var um = UM.getEditor('analysis_editor');
	var prospectAnalysis = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "prospectAnalysis" : prospectAnalysis}, saveSuccess());
	}
});
/**
 * 保存项目描述
 */
$("#save_next_financing").click(function(){
	var um = UM.getEditor('next_financing_editor');
	var nextFinancingSource = um.getContentTxt();
	if(pid != ''){
		sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "nextFinancingSource" : nextFinancingSource}, saveSuccess());
	}
});

