$(function(){
	/**
	 * 编辑弹出富文本窗口宽度设置
	 */
	var width_fwb=$('.tabtable_con_on').width();
	$('.width_fwb').css('width',(width_fwb-20));
	
	/**
	 * 富文本实例化
	 */
	var describeUm = UM.getEditor('describe_editor');
	var describeUm2 = UM.getEditor('describe_editor2');
	var companyUm = UM.getEditor('company_editor');
	var portraitUm = UM.getEditor('portrait_editor');
	var operationUm = UM.getEditor('operation_editor');
	var businessUm = UM.getEditor('business_editor');
	var industryUm = UM.getEditor('industry_editor');
	var analysisUm = UM.getEditor('analysis_editor');
	var nextFinancingUm = UM.getEditor('next_financing_editor');
	
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
	})
	
	/**
	 * 富文本弹出框点击取消事件
	 */
	$('[data-on="close"]').on('click',function(){
		var close=$(this).attr('data-name')
		$('.'+close+'_on').hide();
		$('.'+close+'_center').show();
		$('.bj_hui_on').hide();
		//$('.tip-yellowsimple').hide();
	})
	
	/**
	 * 保存项目描述
	 */
	$("#save_describe").click(function(){
		var describe = describeUm.getContent();
		var describe2 = describeUm2.getContent();
		//sendPostRequestByJsonObj(platformUrl.updateProject, {"id" : pid, "prospectAnalysis" : prospectAnalysis}, saveSuccess);
		
	});
	
	/**
	 * 公司定位
	 */
	$("#save_location").click(function(){
		var companyUm = companyUm.getContent();
		
	});
	
	/**
	 * 用户画像
	 */
	$("#save_portrait").click(function(){
		var portraitUm = portraitUm.getContent();
		
	});
	
	/**
	 * 产品服务
	 */
	$("#save_business").click(function(){
		
	});
	
	/**
	 * 运营数据
	 */
	$("#save_operation").click(function(){
		
	});
	
	/**
	 * 行业分析
	 */
	$("#save_industry").click(function(){
		
	});
	
	/**
	 * 竞争分析
	 */
	$("#save_analysis").click(function(){
		
	});
	
	/**
	 * 下一轮融资
	 */
	$("#save_next_financing").click(function(){
		
	});
});