/**
 */
$(function(){

	$(".tipslink").on("click","a",function(){
		var a = $(this);
		var tipslink = $("#tipslink_val");
		var query_by = a.attr("query-by");
		var query_val = a.attr("query-val");
		if(tipslink.attr("name")== query_by && tipslink.val()==query_val){
			return;
		}
		if(query_by == "all"){
			tipslink.removeAttr("name");
			tipslink.removeAttr("value");
		}else{
			tipslink.attr("name",query_by);
			tipslink.attr("value",query_val)
		}
		
		$("#data-table").bootstrapTable("querySearch");
	
	});
	
	
	
});


