/**
 * sop用户任务
 */
var projectid ;
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
	
	
	//待认领
	$("#dai").on("click", "", function() {
		var taskid=$("#taskid").val();
		projectid=$("#projectid").val();
		this.href="/galaxy/soptask/goClaimtcPage?id="+taskid;
	});
});
	//根据a标签的id去封装json参数
	function judgeQueryType(obj,searchName){
		var dataType;
		if(obj.id==="all"){
		     dataType={"taskOrder":0,"taskStatus":"","nameLike":searchName};
		}
		if(obj.id==="urgent"){
			 dataType={"taskOrder":"taskType:2","taskStatus":"","nameLike":searchName};
		}
		if(obj.id==="normal"){
			 dataType={"taskOrder":"taskType:3","taskStatus":"","nameLike":searchName};
		}
		if(obj.id==="claim"){
			 dataType={"taskOrder":0,"taskStatus":"taskStatus:1","nameLike":searchName};
		}
		if(obj.id==="todeal"){
		     dataType={"taskOrder":0,"taskStatus":"taskStatus:2","nameLike":searchName};
		}
		if(obj.id==="finish"){
		     dataType={"taskOrder":0,"taskStatus":"taskStatus:3","nameLike":searchName};
		}
		return dataType;
		
	}
function getProjectid(){
	return projectid;
}


