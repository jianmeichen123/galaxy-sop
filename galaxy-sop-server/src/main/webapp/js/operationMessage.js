/**
 */
$(function(){

	$(".tipslink").on("click","a",function(){
		var a = $(this);
		/*		var a = $(this);
		var tipslink_val = $("#tipslink_val");
		if(tipslink_val == 2 ||tipslink_val == '2'){
			$(".searchbox input").val('');
		}else{
			$(".searchbox").each(function(index,item){
			});
		}
*/
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
		$(".tablink li").removeClass("on");
		a.parent().addClass("on");
		$("#data-table").bootstrapTable("querySearch");
	
	});
	
	
	
});


