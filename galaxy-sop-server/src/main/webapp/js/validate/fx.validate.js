/****
 * 依赖:BOOTSTRAP TOOLTIP,JQUERY,JQUERY-VALIDATE 
 */
$.fn.fxValidate = function(options){
	var defaultOption = {
			errorElement : 'p',
	        focusInvalid : false, 
	        success : function(label,element) { 
	        	$(element).closest('dd').tooltip('destroy');
	        },   
	        errorPlacement : function(error, element) { 
	        	var target = $(element).closest('dd')[0];
	            $(target).tooltip({
	            	title:error,
	            	trigger:'manual',
	            	placement:'right',
	            	html:true
	            });
	            $(target).tooltip('show');
	        }
	};
	options = $.extend(defaultOption,options);
	return $(this).validate(options);
}