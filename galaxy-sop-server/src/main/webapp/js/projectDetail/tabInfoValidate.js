//基本信息表单验证
	$(function(){
		$("#basicForm").validate();
		$.each($("#basicForm input"),function(){
			$(this).on("blur",function(){
				labelPosition();
			})
		})
		
		
	})
//验证
//投资形式-投资金额
	jQuery.validator.addMethod("amount", function (value, element) {
		var amount = /^(([0-9]{1,6})|([0-9]{1,6}\.[0-9]{1,4}))$/;
		return this.optional(element) || (amount.test(value));
	}, "支持0-1000000的四位小数");
//占股比例
	jQuery.validator.addMethod("share", function (value, element) {
		var share = /^([1-9]\d?(\.\d{1,2})?|0\.[1-9]0?|0\.\d[1-9])$/;
		return this.optional(element) || (share.test(value));
	}, "0到100之间的两位小数");
	
	//来源fa
	jQuery.validator.addMethod("faname", function (value, element) {
		var faname = /^[^\s](.{0,19})$/;
		return this.optional(element) || (faname.test(value));
	}, "不能以空格开头，字符最大长度为20");
	//融资金额LIMIT_11_NUMBER
	jQuery.validator.addMethod("procontribution", function (value, element) {
		var procontribution = /^(\d(\.\d{1,4})?|([1-9][0-9]{1,8})(\.\d{1,4})?)$/;
		return this.optional(element) || (procontribution.test(value));
	}, "支持四位小数");
	//项目估值LIMIT_13_NUMBER
	jQuery.validator.addMethod("provaluations", function (value, element) {
		var provaluations = /^(\d(\.\d{1,4})?|([1-9][0-9]{1,12})(\.\d{1,4})?)$/;
		return this.optional(element) || (provaluations.test(value));
	}, "支持四位小数");
	//出让股份
	jQuery.validator.addMethod("proshare", function (value, element) {
		var proshare = /^(\d{1,2}(\.\d{1,4})?)$/;
		return this.optional(element) || (proshare.test(value));
	}, "0到100之间的四位小数");
	//投资金额LIMIT_11_NUMBER
	jQuery.validator.addMethod("finalContribution", function (value, element) {
		var finalContribution = /^(\d(\.\d{1,4})?|([1-9][0-9]{1,8})(\.\d{1,4})?)$/;
		return this.optional(element) || (finalContribution.test(value));
	}, "支持四位小数");
	//项目估值LIMIT_13_NUMBER
	jQuery.validator.addMethod("finalValuations", function (value, element) {
		var finalValuations = /^(\d(\.\d{1,4})?|([1-9][0-9]{1,12})(\.\d{1,4})?)$/;
		return this.optional(element) || (finalValuations.test(value));
	}, "支持四位小数");
	//股权占比
	jQuery.validator.addMethod("finalShareRatio", function (value, element) {
		var finalShareRatio = /^(\d{1,2}(\.\d{1,4})?)$/;
		return this.optional(element) || (finalShareRatio.test(value));
	}, "0到100之间的四位小数");
	//加速服务费占比
	jQuery.validator.addMethod("serviceChargeedit", function (value, element) {
		var serviceChargeedit = /^([0-4](\.\d{1,4})?)$|^(5(\.[0]{1,4})?)$/;
		return this.optional(element) || (serviceChargeedit.test(value));
	}, "0到5之间的四位小数");
	//机构名称
	jQuery.validator.addMethod("delivery", function(value, element) {   
		var delivery = /^(?!.{51}|^\s*$)/;
		return this.optional(element) || (delivery.test(value));
	}, "不能全为空格");
	//配置错误提示的节点，默认为label，这里配置成 span （errorElement:'span'）
	$.validator.setDefaults({
		errorElement:'span'
	});
	//inputValRuleMark=="10,2"
	jQuery.validator.addMethod("verify_102", function(value, element) {   
		var verify_102 = /^(\d(\.\d{1,2})?|([1-9][0-9]{1,9})(\.\d{1,2})?)$/;
		return this.optional(element) || (verify_102.test(value));
	}, "不能超过9999999999");
	//inputValRuleMark=="13,4"
	jQuery.validator.addMethod("verify_134", function(value, element) {
		var verify_n4 = /^(\d(\.\d{1,4})?|([1-9][0-9]{1,12})(\.\d{1,4})?)$/;
		return this.optional(element) || (verify_n4.test(value));
	}, "不能超过999999999");
	//inputValRuleMark=="8,2"
	jQuery.validator.addMethod("verify_82", function(value, element) {   
		var verify_82 = /^(\d(\.\d{1,2})?|([1-9][0-9]{1,7})(\.\d{1,2})?)$/;
		return this.optional(element) || (verify_82.test(value));
	}, "不能超过99999999");
	//inputValRuleMark=="9,4"
	jQuery.validator.addMethod("verify_94", function(value, element) {
		var verify_94 = /^(\d(\.\d{1,4})?|([1-9][0-9]{1,8})(\.\d{1,4})?)$/;
		return this.optional(element) || (verify_94.test(value));
	}, "支持9位长度的四位小数");
	//vinputValRule=="2"
	jQuery.validator.addMethod("vinputValRule_2", function(value, element) {   
		var vinputValRule_2 = /^([1-9]{1}[0-9]{0,2})$/;;
		return this.optional(element) || (vinputValRule_2.test(value));
	}, "不能超过100"); 
	jQuery.validator.addMethod("vinputValRule_1", function(value, element) {   
		var vinputValRule_1 = /^([1-9]{1}[0-9]{0,1}|0)$/;;
		return this.optional(element) || (vinputValRule_1.test(value));
	}, "不能超过100")
	//vinputValRule=="3"
	jQuery.validator.addMethod("vinputValRule_3", function(value, element) {   
		var vinputValRule_3 = /^(\d|[1-9]\d?(\.\d{1,2})?|0\.\d{1,2}|100|100\.0{1,2})$/;
		return this.optional(element) || (vinputValRule_3.test(value));
	}, "不能超过100"); 
	//inputValRuleMark=="3,2"
	jQuery.validator.addMethod("verify_32", function(value, element) {   
		var verify_32 = /^(\d|[1-9]\d?(\.\d{1,2})?|0\.\d{1,2}|100|100\.0{1,2})$/;
		return this.optional(element) || (verify_32.test(value));
	}, "不能超过100"); 
	//inputValRuleMark=="5,2"
	jQuery.validator.addMethod("verify_52", function(value, element) {   
		var verify_52 = /^(\d(\.\d{1,2})?|([1-9][0-9]{1,4})?(\.\d{1,2})?)$/;
		return this.optional(element) || (verify_52.test(value));
	}, "不能超过99999"); 
	//inputValRuleMark=="40"
	jQuery.validator.addMethod("verify_40", function(value, element) {   
		var verify_40 = /^(?!.{41}|^\s*$)/;
		return this.optional(element) || (verify_40.test(value));
	}, "不能超过40字"); 
	//inputValRuleMark=="100"
	jQuery.validator.addMethod("verify_100", function(value, element) {  
		var verify_100 = /^(?!.{101}|^\s*$)/;
		return this.optional(element) || (verify_100.test(value));
	}, "不能超过100字");
	jQuery.validator.addMethod("verify_200", function(value, element) {  
		var verify_200 = /^(?!.{201}|^\s*$)/;
		return this.optional(element) || (verify_200.test(value));
	}, "不能超过200字");
	//inputValRule=="4"
	jQuery.validator.addMethod("vinputValRule_4", function(value, element) { 
		var vinputValRule_4 = /^(((([1-9]{1}[0-9]{0,1}|0)|([1][0-5][0-9])|([1][6][0-7]))(\.\d{1})?)|168|168.0)$/;
		return this.optional(element) || (vinputValRule_4.test(value));
	}, "不能超过168"); 
	//不能超过50个字
	jQuery.validator.addMethod("verify_50_font", function(value, element) { 
		var verify_50_font = /^[^\s](.{0,49})$/;
		return this.optional(element) || (verify_50_font.test(value));
	}, "不能超过50个字"); 
	//0到10之间的一位小数
	jQuery.validator.addMethod("verify_10_1", function(value, element) { 
		var verify_10_1 = /^([0-9](\.\d{0,1})|\d{0,1}|10|10.0|0)$/;
		return this.optional(element) || (verify_10_1.test(value));
	}, "0到10之间的一位小数"); 
	//百分数
	jQuery.validator.addMethod("percentage", function(value, element) {   
		var percentage = /^\d+(\.\d{2})?$/;
		return this.optional(element) || (percentage.test(value) && value>0 && value <=100);
	}, "只能是0～100的整数和两位小数"); 
	function labelPosition(){
		//错误提示定位
		$.each($("label.error"),function(){
			if($(this).is(":visible")){
				var prevEle=$(this).siblings();
				var prevEleWid;
				var sum=0;
				$.each(prevEle,function(i,n){
					if($(n).is(":visible")){
						prevEleWid=$(n).width();
					}else{
						prevEleWid=0;
					}
					sum+= prevEleWid;
				})
				var top=$(prevEle).offset().top;
				$(this).parent().css("position","relative");
				$(this).css({"position":"absolute","left":sum+15,"top":"-2px","white-space":"nowrap","z-index":"2","border":"1px solid #c7bf93","padding":"3px 8px"})
			}else{
				$(this).css({"padding":"0","border":"0"})
			}
		})
	}
	
