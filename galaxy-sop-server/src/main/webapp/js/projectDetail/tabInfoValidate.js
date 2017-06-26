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
		var amount = /^(([0-9]{1,6})|([0-9]{1,6}\\.[0-9]{1,4}))$/;
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
		var procontribution = /^(0(?:[.](?:[1-9]\d{0,3}|0[1-9]0{0,2}|0[1-9]{2}0{0,1}|0[1-9]{3}|0{2}[1-9]0{0,1}|0{2}[1-9]{2}|0{3}[1-9]))|([1-9][0-9]{0,8})|([1-9][0-9]{0,8}\.[0-9]{1,4}))$/;
		return this.optional(element) || (procontribution.test(value));
	}, "支持四位小数");
	//项目估值LIMIT_11_NUMBER
	jQuery.validator.addMethod("provaluations", function (value, element) {
		var provaluations = /^(0(?:[.](?:[1-9]\d{0,3}|0[1-9]0{0,2}|0[1-9]{2}0{0,1}|0[1-9]{3}|0{2}[1-9]0{0,1}|0{2}[1-9]{2}|0{3}[1-9]))|([1-9][0-9]{0,8})|([1-9][0-9]{0,8}\.[0-9]{1,4}))$/;
		return this.optional(element) || (provaluations.test(value));
	}, "支持四位小数");
	//出让股份
	jQuery.validator.addMethod("proshare", function (value, element) {
		var proshare = /^(\d{1,2}(\.\d{1,4})?)$/;
		return this.optional(element) || (proshare.test(value));
	}, "0到100之间的四位小数");
	//投资金额LIMIT_11_NUMBER
	jQuery.validator.addMethod("finalContribution", function (value, element) {
		var finalContribution = /^(0(?:[.](?:[1-9]\d{0,3}|0[1-9]0{0,2}|0[1-9]{2}0{0,1}|0[1-9]{3}|0{2}[1-9]0{0,1}|0{2}[1-9]{2}|0{3}[1-9]))|([1-9][0-9]{0,8})|([1-9][0-9]{0,8}\.[0-9]{1,4}))$/;
		return this.optional(element) || (finalContribution.test(value));
	}, "支持四位小数");
	//项目估值LIMIT_11_NUMBER
	jQuery.validator.addMethod("finalValuations", function (value, element) {
		var finalValuations = /^(0(?:[.](?:[1-9]\d{0,3}|0[1-9]0{0,2}|0[1-9]{2}0{0,1}|0[1-9]{3}|0{2}[1-9]0{0,1}|0{2}[1-9]{2}|0{3}[1-9]))|([1-9][0-9]{0,8})|([1-9][0-9]{0,8}\.[0-9]{1,4}))$/;
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
	
	function labelPosition(){
		//错误提示定位
		$.each($("label.error"),function(){
			if($(this).is(":visible")){
				var prevEle=$(this).prev();
				var prevEleWid=$(prevEle).width();
				$(this).parent().css("position","relative");
				$(this).css({"position":"absolute","left":prevEleWid+15,"white-space":"nowrap","background":"#fff9c9","border":"1px solid #c7bf93","padding":"3px 6px","border-radius":"4px","z-index":"2"})
			}else{
				$(this).css({"padding":"0","border":"0"})
			}
		})
	}
	
