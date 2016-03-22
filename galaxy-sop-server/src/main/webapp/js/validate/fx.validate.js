/****
 * 依赖:BOOTSTRAP TOOLTIP,JQUERY,JQUERY-VALIDATE 
 */
$.fn.fxValidate = function(options){
	var defaultOption = {
			errorElement : 'p',
	        focusInvalid : false, 
	        success : function(label,element) { 
	        	$(element).tooltip('destroy');
	        },   
	        errorPlacement : function(error, element) {  
	            $(element).tooltip({
	            	title:error,
	            	trigger:'manual',
	            	placement:'right',
	            	html:true
	            });
	            $(element).tooltip('show');
	        }
	};
	options = $.extend(defaultOption,options);
	return $(this).validate(options);
}