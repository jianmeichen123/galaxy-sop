
	//拦截form,在form提交前进行验证
    $('form').bind('submit',beforeSubmit);
	//为带有valType属性的元素初始化提示信息并注册onblur事件
	$.each($("[valType]"),function(i, n) {
		$(n).poshytip({
			className: 'tip-yellowsimple',
			content: $(n).attr('msg'),
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 5,
			offsetY: 10
		});
		$(n).bind('blur',validateBefore);
	});
	//为带有valType1属性的元素初始化提示信息并注册onblur事件
	$.each($("[valType1]"),function(i, n) {  //团队成员验证，单独加了一个表单提示的样式
		/*$(n).poshytip({
			className: 'tip-yellowsimple',
			content: $(n).attr('msg'),
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 5,
			offsetY: 10
		});*/
		$(n).bind('blur',validateBeforeScroll);
	});
	
	//定义一个验证器对象
	$.Validator=function(para) {
	}
	$.Validator.ajaxValidate=function() {
		beforeSubmit();
	}
	
	/**
	 * 执行正则匹配
	 * para为一个对象参数{data[被验证的值]\rule[验证的类型]\regString[验证的格式]}
	 * rule="OTHER":表示自定义正则格式,regString为正则表达式
	 * rule="MAXBYTE":regString为允许的最大字节数,data的字节数是否超过regString
	 * 其他情况regString无效，使用默认defaultVal的正则规则,比如：rule="NUMBER\TEL\..."
	 */
	$.Validator.match=function(para) {
		//定义默认的验证规则
		var defaultVal = {
			NUMBER : "^[0-9]*$",
			TEL : "^0(10|2[0-5789]|\\d{3})-\\d{7,8}$",
			IP : "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$",
			//MOBILE : "^1(3[0-9]|5[0-35-9]|8[0235-9]|7[0-9])\\d{8}$",
			MOBILE : "^1[0-9]{10}$",
			MAIL : "^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$",
			IDENTITY : "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4})){1,18}$",
			CHINESE : "^([\u4E00-\uFA29]|[\uE7C7-\uE7F3])*$",
			URL : "^http[s]?://[\\w\\.\\-]+$",
			LIMIT_11_NUMBER : "^(([0-9]{1,11})|([0-9]{1,11}\.[0-9]{1,2}))$",
		//	LIMIT_NUMBER:"^(?!0(\.0+)?$)([1-9][0-9]*|0)(\.[0-9]+)?$",  //大于0的整数和小数
			LIMIT_NUMBER:"^(([1-9][0-9]{0,10})|([0-9]{1,11}\.[1-9]{1,2})|([0-9]{1,11}\.[0][1-9]{1})|([0-9]{1,11}\.[1-9]{1}[0])|([1-9][0-9]{0,10}\.[0][0]))$",  //两位小数
			LIMIT_9_NUMBER : "^(([0-9]{1,9})|([0-9]{1,9}\.[0-9]{1,2}))$",
			LIMIT_2_INTEGER : "^[0-9]{1,2}$",
			CODE : "^[A-Za-z0-9\-]+$"
		};
		var flag=false;
		if(para.rule=='OTHER') {//自定义的验证规则匹配
			flag=new RegExp(para.regString).test(para.data);
		}else if(para.rule=='MAXBYTE'){
			var len = 0;
			for (var i = 0; i < para.data.length; i++) {
				if (para.data.charCodeAt(i) >= 0x4e00 && para.data.charCodeAt(i) <= 0x9fa5){ 
					len += 2;
				}else {
					len++;
				}
			}
			if(len<parseInt(para.regString)){
				flag = true;
			}
		}else {
			if(para.rule in defaultVal) {//默认的验证规则匹配
				flag=new RegExp(defaultVal[para.rule]).test(para.data);
			}
		}
		return flag;
	}
	//为jquery扩展一个doValidate方法，对所有带有valType的元素进行表单验证，可用于ajax提交前自动对表单进行验证
	$.extend({
		doValidate: function() {
			return $.Validator.ajaxValidate();
		}
	});


/**
 * 输入框焦点离开后对文本框的内容进行格式验证
 * 所有表单元素可允许设置的属性详解如下{
 * 	   valType:验证类型
 *     regString:
 *     msg:验证不通过时的提示信息
 *     allowNULL:允许为空且不为空时再对输入值进行验证
 * }
 * textarea都是可以为空的，且当不为空时才去验证
 */
function validateBefore() {
	//验证通过标识
	var flag=true;
	//获取验证类型
	var valType=$(this).attr('valType');
	//获取验证不通过时的提示信息
	var msg=$(this).attr('msg');
	//自定义的验证字符串
	var regString;
	if(valType=='required') {//不能为空的判断
		if($(this).val()=='' || $.trim($(this).val())=='') {
			flag=false;
		}
	} else if(valType=='requiredDiv'){
		if($(this).text()!='') {
			if(!($(this).text()!=''&&$.Validator.match({data:$(this).text(), rule:'OTHER', regString:$(this).attr('regString')}))) {
				flag=false;
		    }
	   }
	} else if(valType=='MAXBYTE') {
		if($.trim($(this).html())!='') {
			if (!($(this).html()!=''&&$.Validator.match({
				data : $.trim($(this).html()),
				rule : $(this).attr('valType'),
				regString : $(this).attr('regString')
			}))) {
				$(this).poshytip('show');
				flag = false;
			}
		}
	} else{
		if($(this).attr("allowNULL") =='yes' && $(this).val() == ''){
			//允许为空且未输入值,放行
		}else{
			if(valType=='OTHER'){//如果类型是自定义，则获取自定义的验证字符串
				regString=$(this).attr('regString');
				flag=$(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:valType, regString:$(this).attr('regString')});
			} else {
				if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType')}))) {
					flag=$(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType')});
				}
			}
		}
	}  
	//先清除原来的tips
	$(this).poshytip('hide');
	//如果验证没有通过，显示tips
	if(!flag) {
		$(this).poshytip('show');
	}
	
}
function validateBeforeScroll() {  //团队成员验证，单独加了一个表单提示的样式
	//验证通过标识
	var flag=true;
	//获取验证类型
	var valType=$(this).attr('valType1');
	//获取验证不通过时的提示信息
	var id =$(this).attr("name")+'_valiate';
	//var msg=$(this).attr('msg');
	//自定义的验证字符串
	var regString;
	if(valType=='required') {//不能为空的判断
		if($(this).val()=='' || $.trim($(this).val())=='') {
			flag=false;
		}
	} else if(valType=='requiredDiv'){
		if($(this).text()!='') {
			if(!($(this).text()!=''&&$.Validator.match({data:$(this).text(), rule:'OTHER', regString:$(this).attr('regString')}))) {
				flag=false;
		    }
	   }
	} else if(valType=='MAXBYTE') {
		if($.trim($(this).html())!='') {
			if (!($(this).html()!=''&&$.Validator.match({
				data : $.trim($(this).html()),
				rule : $(this).attr('valType'),
				regString : $(this).attr('regString')
			}))) {
				//$(this).poshytip('show');
				$("#"+id).show();
				flag = false;
			}
		}
	} else{
		if($(this).attr("allowNULL") =='yes' && $(this).val() == ''){
			//允许为空且未输入值,放行
		}else{
			if(valType=='OTHER'){//如果类型是自定义，则获取自定义的验证字符串
				regString=$(this).attr('regString');
				flag=$(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:valType, regString:$(this).attr('regString')});
			} else {
				if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType1')}))) {
					flag=$(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType1')});
				}
			}
		}
	}  
	//先清除原来的tips
	$("#"+id).hide();
	//$(this).poshytip('hide');
	//如果验证没有通过，显示tips
	if(!flag) {
		$("#"+id).show();
	}
	
}
//submit之前对所有表单进行验证
function beforeSubmit() {
	var flag=true;
	 $.each($("[valType]"),function(i, n) {
		 //清除可能已有的提示信息
		 $(n).poshytip('hide');
		 if($(n).attr("valType")=='required') {//对不能为空的文本框进行验证
			if($(n).val()=='' || $.trim($(n).val())=='') {
				//显示tips			
				$(n).poshytip('show');
				flag=false;
			}
		 } else if($(n).attr("valType")=='requiredDiv'){//html元素的文本值是否为空
			 if($(n).text()!='') {
				if(!($(n).text()!=''&&$.Validator.match({data:$(n).text(), rule:'OTHER', regString:$(n).attr('regString')}))) {
					//显示tips			
					$(n).poshytip('show');
					flag=false;
				}
			 }
		} else if($(n).attr("valType")=='MAXBYTE') {//对自定义的文本框进行验证
			if($.trim($(n).html())!='') {
				if (!($(n).html()!=''&&$.Validator.match({
					data : $.trim($(n).html()),
					rule : $(n).attr('valType'),
					regString : $(n).attr('regString')
				}))) {
					$(n).poshytip('show');
					flag = false;
				}
			}
		} else{
			if($(this).attr("allowNULL") =='yes' && $(this).val() == ''){
				//允许为空且未输入值,放行
			}else{
				 if($(n).attr("valType")=='OTHER') {//对自定义的文本框进行验证
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType'), regString:$(this).attr('regString')}))) {
						$(n).poshytip('show');
						flag=false;
					}
				} else {//对使用已定义规则的文本框进行验证	
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType')}))) {
						$(n).poshytip('show');
						flag=false;
					}
				}
			}
		}
	 });
     return flag;
}

//submit之前对所有表单进行验证
function beforeSubmitById(id) {
	var flag=true;
	 $.each($("#"+id).find("[valType]"),function(i, n) {
		 //清除可能已有的提示信息
		 $(n).poshytip('hide');
		 if($(n).attr("valType")=='required') {//对不能为空的文本框进行验证
			if($(n).val()=='' || $.trim($(n).val())=='') {
				//显示tips			
				$(n).poshytip('show');
				flag=false;
			}
		 } else if($(n).attr("valType")=='requiredDiv'){//html元素的文本值是否为空
			 if($(n).text()!='') {
				if(!($(n).text()!=''&&$.Validator.match({data:$(n).text(), rule:'OTHER', regString:$(n).attr('regString')}))) {
					//显示tips			
					$(n).poshytip('show');
					flag=false;
				}
			 }
		} else if($(n).attr("valType")=='MAXBYTE') {//对自定义的文本框进行验证
			if($.trim($(n).html())!='') {
				if (!($(n).html()!=''&&$.Validator.match({
					data : $.trim($(n).html()),
					rule : $(n).attr('valType'),
					regString : $(n).attr('regString')
				}))) {
					$(n).poshytip('show');
					flag = false;
				}
			}
		} else{
			if($(this).attr("allowNULL") =='yes' && $(this).val() == ''){
				//允许为空且未输入值,放行
			}else{
				 if($(n).attr("valType")=='OTHER') {//对自定义的文本框进行验证
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType'), regString:$(this).attr('regString')}))) {
						$(n).poshytip('show');
						flag=false;
					}
				} else {//对使用已定义规则的文本框进行验证	
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType')}))) {
						$(n).poshytip('show');
						flag=false;
					}
				}
			}
		}
	 });
     return flag;
}

function beforeSubmitByIdNext(id) {
	var flag=true;
	 $.each($("#"+id).find("[valType]"),function(i, n) {
		 //清除可能已有的提示信息
		 //$(n).poshytip('hide');
		 if($(n).attr("valType")=='required') {//对不能为空的文本框进行验证
			if($(n).val()=='' || $.trim($(n).val())=='') {
				//显示tips			
				//$(n).poshytip('show');
				flag=false;
			}
		 } else if($(n).attr("valType")=='requiredDiv'){//html元素的文本值是否为空
			 if($(n).text()!='') {
				if(!($(n).text()!=''&&$.Validator.match({data:$(n).text(), rule:'OTHER', regString:$(n).attr('regString')}))) {
					//显示tips			
					//$(n).poshytip('show');
					flag=false;
				}
			 }
		} else if($(n).attr("valType")=='MAXBYTE') {//对自定义的文本框进行验证
			if($.trim($(n).html())!='') {
				if (!($(n).html()!=''&&$.Validator.match({
					data : $.trim($(n).html()),
					rule : $(n).attr('valType'),
					regString : $(n).attr('regString')
				}))) {
					//$(n).poshytip('show');
					flag = false;
				}
			}
		} else{
			if($(this).attr("allowNULL") =='yes' && $(this).val() == ''){
				//允许为空且未输入值,放行
			}else{
				 if($(n).attr("valType")=='OTHER') {//对自定义的文本框进行验证
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType'), regString:$(this).attr('regString')}))) {
						//$(n).poshytip('show');
						flag=false;
					}
				} else {//对使用已定义规则的文本框进行验证	
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType')}))) {
						//$(n).poshytip('show');
						flag=false;
					}
				}
			}
		}
	 });
     return flag;
}

function beforeSubmitScroll(id) {  //团队成员验证，单独加了一个表单提示的样式
	var flag=true;
	 $.each($("#"+id).find("[valType1]"),function(i, n){
		 //清除可能已有的提示信息
		 if($(n).attr("valType1")=='required') {//对不能为空的文本框进行验证
			 var id = $(n).attr("name")+'_valiate';
			if($(n).val()=='' || $.trim($(n).val())=='') {
				//显示tips			
				$("#"+id).show();
				flag=false;
			}
		 } else if($(n).attr("valType1")=='requiredDiv'){//html元素的文本值是否为空
			 var id = $(n).attr("name")+'_valiate';
			 if($(n).text()!='') {
				if(!($(n).text()!=''&&$.Validator.match({data:$(n).text(), rule:'OTHER', regString:$(n).attr('regString')}))) {
					//显示tips			
					$("#"+id).show();
					flag=false;
				}
			 }
		} else if($(n).attr("valType1")=='MAXBYTE') {//对自定义的文本框进行验证
			 var id = $(n).attr("name")+'_valiate';
			if($.trim($(n).html())!='') {
				if (!($(n).html()!=''&&$.Validator.match({
					data : $.trim($(n).html()),
					rule : $(n).attr('valType1'),
					regString : $(n).attr('regString')
				}))) {
					$("#"+id).show();
					flag = false;
				}
			}
		} else{
			if($(this).attr("allowNULL") =='yes' && $(this).val() == ''){
				//允许为空且未输入值,放行
			}else{
				 var id = $(n).attr("name")+'_valiate';
				 if($(n).attr("valType1")=='OTHER') {//对自定义的文本框进行验证
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType1'), regString:$(this).attr('regString')}))) {
						$("#"+id).show();
						flag=false;
					}
				} else {//对使用已定义规则的文本框进行验证	
					 var id = $(n).attr("name")+'_valiate';
					if(!($(this).val()!=''&&$.Validator.match({data:$(this).val(), rule:$(this).attr('valType1')}))) {
						$("#"+id).show();
						flag=false;
					}
				}
			}
		}
	 });
     return flag;
}

function initDialogVal(){
	$.each($("[valType]"),function(i, n) {
		$(n).poshytip({
			className: 'tip-yellowsimple',
			content: $(n).attr('msg'),
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 5,
			offsetY: 10
		});
		$(n).bind('blur',validateBefore);
	});
}

function initDialogValstr(id){
	$.each($("#"+id).find("[valType]"),function(i, n) {
		$(n).poshytip({
			className: 'tip-yellowsimple',
			content: $(n).attr('msg'),
			showOn: 'none',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 5,
			offsetY: 10
		});
		$(n).bind('blur',validateBefore);
	});
}


/**
 * 1.不能为空
 * <input id="email" name="email" type="text" valType="required"/>
 * 2.
 * <textarea rows="3" cols="20" valType="requiredDiv" msg="<font color=red>*</font>0-100间整数"></textarea>
 * 3.可以为空，但一单输入值就需要进行验证，这里设置自定义验证
 * <input id="email" name="email" type="text" allowNULL="yes" valType="OTHER" regString="^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$" msg="<font color=red>*</font>0-100间整数"/>
 */