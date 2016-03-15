/**
 * sop用户任务
 */
$(function(){
	//待认领
	$("table").on("click", "a[data-btn='claim']", function() {
		console.log("ok");
		var taskId=$("#taskid").val();
		projectid=$("#projectid").val();
	    var  _url=sopContentUrl+"/galaxy/soptask/goClaimtcPage?id="+taskId+"&sid="+sessionId+"&guid="+userId;
	  // 	var _url = forwardWithHeader(claimUrl);
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
			//	var taskid=getTaskId();
				$(".btnbox").on("click", "#dotask", function() {	
				//	var endUrl=sopContentUrl+"/galaxy/soptask/doTask?taskId="+taskId+"&sid="+sessionId+"&guid="+userId;
					var endUrl=sopContentUrl+"/galaxy/soptask/doTask?taskId="+taskId;
					forwardWithHeader(endUrl);
	            });
				//单击按钮刷新页列表里面的内容
				$(".btnbox").on("click", "#notdo", function() {
					$("#data-table").bootstrapTable("refresh");
				 });
			}//模版反回成功执行	
		});
		return false;
	});
	//待认领
	$("table").on("click", "#doclaim", function() {
		var task=this;
		var taskId=task.childNodes[1].value;
		var url=sopContentUrl+"/galaxy/soptask/doTask?taskId="+taskId;
	    forwardWithHeader(url);
	//	this.href=endUrl;
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


