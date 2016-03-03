/**
 * 数据字典页面js
 */
$(function(){
	var obj=$("#all")[0];
	setData(obj);
//	var data={"taskOrder":"","taskType":""};
	//var soptasklist = getSopTaskList(data);
	
//	if(soptasklist.length > 0 ){
//		var tasklist_tody = $("#tasklist"); 
//		$(soptasklist).each(function(){
//			 var soptask = $(this)[0];
//			// var li = '<li data-tab="nav" '+'
//			// code="'+dict.code+'"'+'>'+dict.name+'</li>';
//			 var tr='<tr>'+
//				 '<td>1</td>'+
//				 '<td><span class="red">'+soptask.taskOrder+'</span></td>'+
//				 '<td>8小时</td>'+
//				 '<td>'+ soptask.taskDeadlineformat+'</td>'+
//				 '<td>'+soptask.taskType+'</td>'+
//				 '<td>'+soptask.taskName+'</td>'+
//				 '<td>'+soptask.taskStatus+'</td>'+
//				 '<td>'+soptask.projectName+'</td>'+
//				 '<td>'+soptask.createUname+'</td>'+
//				 '<td>'+soptask.remark+'</td>'+
//				 '<td><a href="javascript:;" class="blue">'+soptask.caozuo+'</a><td></tr>'; 
//				
//			
//			 tasklist_tody.append(tr);
//		  });
//	}
	
function getSopTaskList(jsonData){
	var soptasks ;
	var url = location.search;
	var sessionId=url.split("=")[1];
	$.ajax({
		url :'/galaxy/soptask/taskListByRole',
		async : false,
		type : 'POST',
		data : JSON.stringify(jsonData),
	    contentType:"application/json; charset=UTF-8",
		dataType : "json",
		beforeSend :function(xhr){ 
			xhr.setRequestHeader('sessionID',sessionId);
		},
		cache : false,
		error:function(){     
	    }, 
		success : function(data) {
			console.log(data);
			if(data.result.status == 'OK'){
				soptasks = data.pageList.content; 
			}
		}
});	
	return soptasks;
}
// 单击按钮刷新页列表里面的内容
$(".tipslink").on("click", "a", function() {
	setData(this);
});
//向页面组装数据
function setData(obj){
	//根据id判断类型（组装json数据）
	var data=judgeQueryType(obj);
	var soptasklist = getSopTaskList(data);
	if(soptasklist.length > 0 ){
		var tasklist_tody = $("#tasklist"); 
		tasklist_tody.empty();
		$(soptasklist).each(function(){
			 var soptask = $(this)[0];
			// var li = '<li data-tab="nav" '+'
			// code="'+dict.code+'"'+'>'+dict.name+'</li>';
			 var tr='<tr>'+
				 '<td>1</td>'+
				 '<td><span class="red">'+soptask.taskOrder+'</span></td>'+
				 '<td>8小时</td>'+
				 '<td>'+ soptask.taskDeadlineformat+'</td>'+
				 '<td>'+soptask.taskType+'</td>'+
				 '<td>'+soptask.taskName+'</td>'+
				 '<td>'+soptask.taskStatus+'</td>'+
				 '<td>'+soptask.projectName+'</td>'+
				 '<td>'+soptask.createUname+'</td>'+
				 '<td>'+soptask.remark+'</td>'+
				 '<td><a href="javascript:;" class="blue">'+soptask.caozuo+'</a><td></tr>'; 
				
			
			 tasklist_tody.append(tr);
		  });
	}
}
//根据a标签的id去封装json参数
function judgeQueryType(obj){
	var dataType;
	if(obj.id==="all"){
	     dataType={"taskOrder":"","taskType":""};
	}
	if(obj.id==="urgent"){
		 dataType={"taskOrder":"2","taskType":""};
	}
	if(obj.id==="normal"){
		 dataType={"taskOrder":"3","taskType":""};
	}
	if(obj.id==="claim"){
		 dataType={"taskOrder":"","taskType":"1"};
	}
	if(obj.id==="todeal"){
	     dataType={"taskOrder":"","taskType":"2"};
	}
	if(obj.id==="finish"){
	     dataType={"taskOrder":"","taskType":"3"};
	}
	return dataType;
	
}

})

