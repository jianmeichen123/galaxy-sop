/**
 * 数据字典页面js
 */
var projectid ;
$(function(){
	var obj=$("#all")[0];
	setData(obj);
	
function getSopTaskList(jsonData){
	var soptasks ;
	var url = location.search;
	var sessionId=url.split("=")[1];
	$.ajax({
		url :platformUrl.geSopTastList,
		async : false,
		type : 'POST',
		data : JSON.stringify(jsonData),
	    contentType:"application/json; charset=UTF-8",
		dataType : "json",
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
//单击按钮刷新页列表里面的内容
$(".searchbox").on("click", "#search", function() {
	var searchName=$("#searchName").val();
	if(searchName == "" || searchName == undefined || searchName == null){
		searchName="";
	}
	setData(this,searchName);
});
//向页面组装数据
function setData(obj ,searchName){
	//根据id判断类型（组装json数据）
	var data=judgeQueryType(obj,searchName);
	var soptasklist = getSopTaskList(data);
	if(soptasklist != "" || soptasklist != undefined || soptasklist != null){
		var tasklist_tody = $("#tasklist"); 
		tasklist_tody.empty();
		$(soptasklist).each(function(){
			 var soptask = $(this)[0];
			// var li = '<li data-tab="nav" '+'
			// code="'+dict.code+'"'+'>'+dict.name+'</li>';
			 var tr='<tr>'+
				 '<td>1</td>'+
				 '<td><span class="red">'+soptask.taskOrder+'</span></td>'+
				 '<td>'+ soptask.hours+'</td>'+
				 '<td>'+ soptask.taskDeadlineformat+'</td>'+
				 '<td>'+soptask.taskType+'</td>'+
				 '<td>'+soptask.taskName+'</td>'+
				 '<td>'+soptask.taskStatus+'</td>'+
				 '<td>'+soptask.projectName+'</td>'+
				 '<td>'+soptask.createUname+'</td>'+
				 '<td>'+soptask.remark+'</td>'+
				 '<td>'+soptask.caozuohtml+'<td>'+
				// '<td class="caozuo"><a href="/galaxy/soptask/goClaimtcPage?id=1" data-btn="claim" class="blue" id="flag_'+soptask.statusFlag+'">'+soptask.caozuo+'</a>'+
				// '<input type="hidden" id="taskId" value="'+soptask.id+'"></td>'+
				 //'<td><a href="/galaxy/soptask/goClaimtcPage?id=1" data-btn="claim">认领</a><td>'+
				' </tr>'; 	
			 tasklist_tody.append(tr);
		  });
	}
}

//待认领
$("#dai").on("click", "", function() {
	var taskid=$("#taskid").val();
	projectid=$("#projectid").val();
	this.href="/galaxy/soptask/goClaimtcPage?id="+taskid;
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
		
})
function getProjectid(){
	return projectid;
}

