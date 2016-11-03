$(function(){
	/**
	 * 编辑弹出富文本窗口宽度设置
	 */
	var width_fwb=$('.tabtable_con_on').width();
	$('.width_fwb').css('width',(width_fwb-20));
	
	/**
	 * 富文本实例化
	 */
	var describeUm = UM.getEditor('describe_editor');//项目 描述
	var describeUm2 = UM.getEditor('describe_editor2');//项目描述要点
	var companyUm = UM.getEditor('company_editor');//公司定位
	var portraitUm = UM.getEditor('portrait_editor');//用户画像
	var operationUm = UM.getEditor('operation_editor');//运营数据
	var businessUm = UM.getEditor('business_editor');//产品服务
	var industryUm = UM.getEditor('industry_editor');//行业分析
	var analysisUm = UM.getEditor('analysis_editor');//竞争分析
	var nextFinancingUm = UM.getEditor('next_financing_editor');//下一轮融资
	
	/**
	 * 屏幕边距设置
	 */
	$('.edui-icon-fullscreen').on('click',function(){
			$('body').css('padding-bottom','300px')
	})
	
	/**
	 * 富文本前置点击编辑事件
	 */
	$('[data-on="data-open"]').on('click',function(){
		if($(this).hasClass('limits_gray'))
		{
			return;
		}
		var open=$(this).attr('data-name')
		$('.'+open+'_on').show();
		$('.'+open+'_on').css("top","30%")
		$('.'+open+'_center').hide();
		$('.bj_hui_on').show();
		responseData();
	})
	
	/**
	 * 富文本弹出框点击取消事件
	 */
	$('[data-on="close"]').on('click',function(){
		var close=$(this).attr('data-name')
		$('.'+close+'_on').hide();
		$('.'+close+'_center').show();
		$('.bj_hui_on').hide();
	})
	
	/**
	 * 保存项目描述
	 */
	$("#save_describe").click(function(){
		var projectDescribe = describeUm.getContent();
		var projectDescribeFinancing = describeUm2.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "projectDescribe" : projectDescribe,"projectDescribeFinancing":projectDescribeFinancing}, saveCallBack);
		
	});
	
	/**
	 * 公司定位
	 */
	$("#save_location").click(function(){
		var companyLocation = companyUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "companyLocation" : companyLocation}, saveCallBack);
		
	});
	
	/**
	 * 用户画像
	 */
	$("#save_portrait").click(function(){
		var userPortrait = portraitUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "userPortrait" : userPortrait}, saveCallBack);
	});
	
	/**
	 * 产品服务
	 */
	$("#save_business").click(function(){
		var projectBusinessModel = businessUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "projectBusinessModel" : projectBusinessModel}, saveCallBack);
		
	});
	
	/**
	 * 运营数据
	 */
	$("#save_operation").click(function(){
		var operationalData = operationUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "operationalData" : operationalData}, saveCallBack);
	
	});
	
	/**
	 * 行业分析
	 */
	$("#save_industry").click(function(){
		var industryAnalysis = industryUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "industryAnalysis" : industryAnalysis}, saveCallBack);
		
	});
	
	/**
	 * 竞争分析
	 */
	$("#save_analysis").click(function(){
		var prospectAnalysis = analysisUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "prospectAnalysis" : prospectAnalysis}, saveCallBack);
		
	});
	
	/**
	 * 下一轮融资
	 */
	$("#save_next_financing").click(function(){
		var nextFinancingSource = nextFinancingUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : id, "nextFinancingSource" : nextFinancingSource}, saveCallBack);
	
	});
	
	/**
	 * 组装数据
	 */
	function responseData(){
		$("#describe_editor").html($("#describe_show").html());
		$("#describe_editor2").html($("#describe2_show").html());
		$("#company_editor").html($("#location_show").html());
		$("#portrait_editor").html($("#portrait_show").html());
		$("#business_editor").html($("#business_model_show").html());
		$("#operation_editor").html($("#operational_data_show").html());
		$("#industry_editor").html($("#industry_analysis_show").html());
		$("#analysis_editor").html($("#analysis_show").html());
		$("#next_financing_editor").html($("#next_financing_source_show").html());
	}
	
	/**
	 * 弹窗保存回调函数
	 */
	function saveCallBack(data){
		if(data.result.status == "OK"){
			//跳转页面...
			$("#describe_show").html(data.entity.projectDescribe);
			$("#describe2_show").html(data.entity.projectDescribeFinancing);
			$("#location_show").html(data.entity.companyLocation);
			$("#portrait_show").html(data.entity.userPortrait);
			$("#business_model_show").html(data.entity.projectBusinessModel);
			$("#operational_data_show").html(data.entity.operationalData);
			$("#industry_analysis_show").html(data.entity.industryAnalysis);
			$("#analysis_show").html(data.entity.prospectAnalysis);
			$("#next_financing_source_show").html(data.entity.nextFinancingSource);
			
		}else{
			layer.msg(data.result.message);
		}
	}
});
/**
 * 表单验证
 */
function step2Valiate(id){
	var flag = true;
	$.each($("#"+id).find("[valiate]"),function(i, n) {
		 //清除可能已有的提示信息
		 if($(n).attr("valiate")=='required') {//对不能为空的文本框进行验证
			 var id = $(n).attr("id").replace("_show","_valiate");
			 if($(n).text()=='' || $.trim($(n).text())=='') {
				 $("#"+id).html('<span style="font-color:red">参数丢失!</span>');
				 $("#"+id).show();
				 flag = false;
			 }else{
				 $("#"+id).hide();
			 }
			 
		 }
	});
	return flag;
}