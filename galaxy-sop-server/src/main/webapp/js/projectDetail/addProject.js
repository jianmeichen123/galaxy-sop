$(function(){
		
		var width_fwb=$('.tabtable_con_on').width();
		$('.width_fwb').css('width',(width_fwb-20));

		$("#faNameEdit").keydown(function(){
				if(this.value=="请输入FA名称"){
					this.value = "";
				}
			
		})
		$("#faNameEdit").blur(function(){
				if(this.value==""){
					this.value = "请输入FA名称";
				}
			
		})
		
//		UM.getEditor('editor');
		var describeUm = UM.getEditor('describe_editor');
		var describeUm = UM.getEditor('describe_editor2');
		var companyUm = UM.getEditor('company_editor');
		var portraitUm = UM.getEditor('portrait_editor');
		var operationUm = UM.getEditor('operation_editor');
		var businessUm = UM.getEditor('business_editor');
		var industryUm = UM.getEditor('industry_editor');
		var analysisUm = UM.getEditor('analysis_editor');
		var nextFinancingUm = UM.getEditor('next_financing_editor');
		//统一显示
		 $('.edui-icon-fullscreen').on('click',function(){
				$('body').css('padding-bottom','300px')
		})
		$('[data-on="data-open"]').on('click',function(){
			if($(this).hasClass('limits_gray'))
			{
				return;
			}
			/*var scroll_top=$(this).offset().top;
			 $('html,body').animate({  
			        scrollTop: scroll_top
			    }, 1000); */
			var open=$(this).attr('data-name')
			$('.'+open+'_on').show();
			$('.'+open+'_on').css("top","30%")
			$('.'+open+'_center').hide();
			$('.bj_hui_on').show();
		})
		//统一关
		$('[data-on="close"]').on('click',function(){
			var close=$(this).attr('data-name')
			$('.'+close+'_on').hide();
			$('.'+close+'_center').show();
			$('.bj_hui_on').hide();
			$('.tip-yellowsimple').hide();
		})
		
});




