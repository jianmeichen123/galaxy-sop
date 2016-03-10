/**
 * sop用户任务
 */
var taskId ;
$(function(){
	//待认领
	$("table").on("click", "a[data-btn='claim']", function() {
		console.log("ok");
		taskId=$("#taskid").val();
		projectid=$("#projectid").val();
	   	var _url = "/galaxy/soptask/goClaimtcPage?id="+taskId;
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				var taskid=getTaskId();
				$(".btnbox").on("click", "#dotask", function() {
					this.href="/galaxy/soptask/doTask?taskId="+taskid;
	            });
				//单击按钮刷新页列表里面的内容
				$(".btnbox").on("click", "#notdo", function() {
					$("#data-table").bootstrapTable("refresh");
				 });
			}//模版反回成功执行	
		});
		return false;
	});


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
function getTaskId(){
	return taskId;
}


