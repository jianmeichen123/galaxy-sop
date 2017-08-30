/****
 * 依赖:BOOTSTRAP TOOLTIP,JQUERY,JQUERY-VALIDATE 
 */
$.fn.fxValidate = function(options){
	var defaultOption = {
			errorElement : 'p',
	        focusInvalid : false, 
	        onfocusout:false,
	        success : function(label,element) { 
	        	var target = $(element).closest('dd')[0];
	        	$(target).poshytip('hide');
        		$(target).poshytip('destory');
	        },   
	        errorPlacement : function(error, element) { 
	        	var target = $(element).closest('dd')[0];
	        	if(!error.is(':empty'))
        		{
	        		$(target).poshytip('hide');
	        		$(target).poshytip('destory');
	        		$(target).poshytip({
	    				className: 'tip-yellowsimple',
	    				content: error,
	    				showOn: 'none',
	    				alignTo: 'target',
	    				alignX: 'right',
	    				alignY: 'center',
	    				offsetX: 5,
	    				offsetY: 10
	    			});
	        		$(target).poshytip('show');
        		}
	        	else
        		{
	        		$(target).poshytip('hide');
	        		$(target).poshytip('destory');
        		}
	        }
	};
	options = $.extend(defaultOption,options);
	return $(this).validate(options);
}