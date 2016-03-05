/**
 * 
 */
    $(function () {
    	$('#data-table').bootstrapTable({
    		queryParamsType: 'size|page', // undefined
    		
    	});
        
    	 $(".form_datetime").datetimepicker({
    		 	language:"zh-CN",
    	        format: "yyyy-mm-dd HH:ii",
    	        showMeridian: true,
    	        autoclose: true,
    	        todayBtn: true
    	    });
    

    });