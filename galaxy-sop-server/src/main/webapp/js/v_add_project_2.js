$(function(){
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
		 var scroll_top=$(this).offset().top;
		 $('html,body').animate({  
		        scrollTop: scroll_top
		    }, 1000);  
		var open=$(this).attr('data-name')
		$('.'+open+'_on').show();
		//$('.'+open+'_on').css("top","30%")
		$('.'+open+'_center').hide();
		$('.bj_hui_on').show();
		responseData();
		//调整富文本宽度
		var width=$(".addProject").width();
		$("#step2 .edui-container").css("width",width*0.98);
		$("#step2 .width_fwb").css("width",width*0.96);
		//跳跃填写，提示框展示
		/*var btnName=$(this).attr("data-btn");
		btnNum=btnName.substring(btnName.length-1,btnName.length);
		for(var i=0;i<btnNum;i++){
			if($('[data-btn="editor'+i+'"]').text()=="" || $.trim($('[data-btn="editor'+i+'"]').text())==''){
				var id=$('[data-btn="editor'+i+'"]').attr("id").replace("_show","_valiate");
				$("#"+id).show();
			}
		}*/
		
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
	 * 取消
	 */
	$(".fffbtn").click(function(){
		var idArr=$(this).siblings().attr("id").split("_");
		id=idArr[idArr.length-1];
		if($('#'+id+'_show').text()==""|| $.trim($('#'+id+'_show').text())==""){
			$('#'+id+'_valiate').show();
		}else{
			$('#'+id+'_valiate').hide();
		}
		
	})
	
	/**
	 * 保存项目描述
	 */
	$("#save_describe").click(function(){
		var projectDescribe = describeUm.getContent();
		var projectDescribeFinancing = describeUm2.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "projectDescribe" : projectDescribe,"projectDescribeFinancing":projectDescribeFinancing}, saveCallBack);
		//step2Valiate("step2");
		Valiate("describe_show");
	});
	
	/**
	 * 公司定位
	 */
	$("#save_location").click(function(){
		var companyLocation = companyUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "companyLocation" : companyLocation}, saveCallBack);
		//step2Valiate("step2");
		Valiate("location_show")
	});
	
	/**
	 * 用户画像
	 */
	$("#save_portrait").click(function(){
		var userPortrait = portraitUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "userPortrait" : userPortrait}, saveCallBack);
		//step2Valiate("step2");
		Valiate("portrait_show")
	});
	
	/**
	 * 产品服务
	 */
	$("#save_business").click(function(){
		var projectBusinessModel = businessUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "projectBusinessModel" : projectBusinessModel}, saveCallBack);
		//step2Valiate("step2");
		Valiate("business_show")
	});
	
	/**
	 * 运营数据
	 */
	$("#save_operation").click(function(){
		var operationalData = operationUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "operationalData" : operationalData}, saveCallBack);
		//step2Valiate("step2");
	});
	
	/**
	 * 行业分析
	 */
	$("#save_industry").click(function(){
		var industryAnalysis = industryUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "industryAnalysis" : industryAnalysis}, saveCallBack);
		//step2Valiate("step2");
		Valiate("industry_show")
	});
	
	/**
	 * 竞争分析
	 */
	$("#save_analysis").click(function(){
		var prospectAnalysis = analysisUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "prospectAnalysis" : prospectAnalysis}, saveCallBack);
		//step2Valiate("step2");
		Valiate("analysis_show")
	});
	
	/**
	 * 下一轮融资
	 */
	$("#save_next_financing").click(function(){
		var nextFinancingSource = nextFinancingUm.getContent();
		sendPostRequestByJsonObj(Constants.sopEndpointURL+"galaxy/project/addProjectStep2", {"id" : pid, "nextFinancingSource" : nextFinancingSource}, saveCallBack);
		//step2Valiate("step2");
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
			
			if($("#describe_show").html()!=""  && $.trim($("#describe_show").text())!="" ){
				$(".describe_show").show();
			}else{
				$(".describe_show").hide();
			};
			if($("#location_show").html()!="" && $.trim($("#location_show").text())!=""){
				$(".location_show").show();
			}else{
				$(".location_show").hide();
			};
			if($("#portrait_show").html()!="" && $.trim($("#portrait_show").text())!=""){
				$(".portrait_show").show();
			}else{
				$(".portrait_show").hide();
			};
			if($("#business_model_show").html()!="" && $.trim($("#business_model_show").text())!=""){
				$(".business_model_show").show();
			}else{
				$(".business_model_show").hide();
			};
			if($("#operational_data_show").html()!="" && $.trim($("#operational_data_show").text())!=""){
				$(".operational_data_show").show();
			}else{
				$(".operational_data_show").hide();
			};
			if($("#industry_analysis_show").html()!="" && $.trim($("#industry_analysis_show").text())!=""){
				$(".industry_analysis_show").show();
			}else{
				$(".industry_analysis_show").hide();
			};
			if($("#analysis_show").html()!="" && $.trim($("#analysis_show").text())!=""){
				$(".analysis_show").show();
			}else{
				$(".analysis_show").hide();
			};
			if($("#next_financing_source_show").html()!="" && $.trim($("#next_financing_source_show").text())!=""){
				$(".next_financing_source_show").show();
			}else{
				$(".next_financing_source_show").hide();
			};
			
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
				/* $("#"+id).html('<span style="font-color:red">参数丢失!</span>');*/
				 $("#"+id).show();
				 flag = false;
			 }else{
				 $("#"+id).hide();
			 }
			 
		 }
	});
	return flag;
}
/**
 * 表单单个验证
 */
function Valiate(id){
	var flag = true;
	$.each($("#"+id),function(i, n) {
		 //清除可能已有的提示信息
		 if($(n).attr("valiate")=='required') {//对不能为空的文本框进行验证
			 var id = $(n).attr("id").replace("_show","_valiate");
			 if($(n).text()=='' || $.trim($(n).text())=='') {
				/* $("#"+id).html('<span style="font-color:red">参数丢失!</span>');*/
				 $("#"+id).show();
				 flag = false;
			 }else{
				 $("#"+id).hide();
			 }
			 
		 }
	});
	return flag;
}
