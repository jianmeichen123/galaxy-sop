<%@ page language="java" pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath();
%>
<dl style="position: relative;" class="task">
	<dt>
		<h3 class="ico t1">待办任务</h3>
	</dt>
	<dd>
		<table width="100%" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th style="width:20%">优先级</th>
					<th style="width:20%">任务名称</th>
					<th style="width:20%">任务状态</th>
					<th style="width:25%">所属项目</th>
					<th style="width:15%">操作</th>
				</tr>
			</thead>
			<tbody id="sopStak">

			</tbody>
		</table>
	</dd>
	<dd class="clearfix position">
		<a href="javascript:;" onclick="dealtTask()" class="more null">more</a>
	</dd>
</dl>
<script>
$(function(){
	var jsonData={"pageNum":0,"pageSize":3,"flagUrl":"index"}; 
	sendPostRequestByJsonObj(platformUrl.soptaskshouye,jsonData, SopTaskCallback);
});
function SopTaskCallback(data){
	//组装数据
	var list =  data.pageList.content;
	if(list.length<3){
		$("#sopStak").parent().parent().siblings().children('.more').css("display","none");	
	}
	if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
		var tbodyList = $("#sopStak"); 
		var i=0;
		var taskOrder = {
				"0":"正常",	
				"1":"紧急",	
				"2":"特急"
			};
		$(list).each(function(){
			 var temp = $(this)[0];
			 i=i+1;
			 var taskOrderDesc = "";
			 if(temp.taskOrder in taskOrder)
			 {
				 if(temp.taskOrder>0)
				 {
					 taskOrderDesc = '<font class="red">'+taskOrder[temp.taskOrder]+'</fonr>';
				 }
				 else
				 {
					 taskOrderDesc = taskOrder[temp.taskOrder];
				 }
			 }
			 var tr='<tr>'+
				 '<td style="width:20%">'+ taskOrderDesc+'</td>'+
				 '<td style="width:20%" title="'+ temp.taskName+'" >'+ temp.taskName+'</td>'+
				 '<td style="width:25%">'+ temp.taskStatus+'</td>'+
				 '<td style="width:20%" title="'+ temp.projectName+'">'+ getValue(temp.projectName)+'</td>'+
				 '<td style="width:15%">'+ temp.caozuohtml+'</td>'+
				' </tr>'; 
			 tbodyList.append(tr);
		  });
		//cutStr(10,'cutstr');
	}else{
		var tbodyList = $("#sopStak"); 
		var noData =
			'<tr>'+
			 '<td colspan="5" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
			' </tr>'; 			
		tbodyList.append(noData);
	}	
}
function getValue(str) {
	if (typeof(str) == "undefined") { 
		 return "-";
	}  else {
		return str;
	}
}
function dealtTask(){
	var url = Constants.sopEndpointURL + "galaxy/soptask" 
	window.location.href=url;
}
$(function(){
	//待认领
	$("tbody").on("click", "#doclaim", function() {
		var task=this;
		var taskId=task.childNodes[1].value;
		var url=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId;
	    forwardWithHeader(url);
//		this.href=endUrl;
	});
	//待认领
	$("tbody").on("click", "a[data-btn='claim']", function() {
		var obj=this;
		var taskId=obj.childNodes[1].value;;
		var projectid=obj.childNodes[2].value;;
	    var  _url=Constants.sopEndpointURL+"/galaxy/soptask/goClaimtcPage?id="+taskId+"&sid="+sessionId+"&guid="+userId;
	  // 	var _url = forwardWithHeader(claimUrl);
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
			//	var taskid=getTaskId();
				$(".btnbox").on("click", "#dotask", function() {	
				//	var endUrl=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId+"&sid="+sessionId+"&guid="+userId;
					var endUrl=Constants.sopEndpointURL+"/galaxy/soptask/doTask?taskId="+taskId;
					forwardWithHeader(endUrl);
	            });
				//单击按钮刷新页列表里面的内容
				$(".btnbox").on("click", "#notdo", function() {
					//$("#data-table").bootstrapTable("refresh");
					window.location.reload();
				 });
				$(".pop").on("click", "[data-close='close']", function() {
					window.location.reload();
				 });
			}//模版反回成功执行	
		});
		return false;
	});	
	var data = {
			_domid : "file_gird_index"
	}
	fileGridIndex.init(data);
		
});
</script>