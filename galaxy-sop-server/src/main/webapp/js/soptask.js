/**
 * 数据字典页面js
 */
$(function(){
	var obj=$("#all")[0];
	setData(obj);
	
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
				 '<td>8小时</td>'+
				 '<td>'+ soptask.taskDeadlineformat+'</td>'+
				 '<td>'+soptask.taskType+'</td>'+
				 '<td>'+soptask.taskName+'</td>'+
				 '<td>'+soptask.taskStatus+'</td>'+
				 '<td>'+soptask.projectName+'</td>'+
				 '<td>'+soptask.createUname+'</td>'+
				 '<td>'+soptask.remark+'</td>'+
				 '<td class="caozuo"><a href="javascript:;" class="blue" id="flag_"'+soptask.statusFlag+'>'+soptask.caozuo+'</a><td></tr>'; 	
			 tasklist_tody.append(tr);
		  });
	}
}
//根据a标签的id去封装json参数
function judgeQueryType(obj,searchName){
	var dataType;
	if(obj.id==="all"){
	     dataType={"taskOrder":"","taskStatus":"","nameLike":searchName};
	}
	if(obj.id==="urgent"){
		 dataType={"taskOrder":"2","taskStatus":"","nameLike":searchName};
	}
	if(obj.id==="normal"){
		 dataType={"taskOrder":"3","taskStatus":"","nameLike":searchName};
	}
	if(obj.id==="claim"){
		 dataType={"taskOrder":"","taskStatus":"1","nameLike":searchName};
	}
	if(obj.id==="todeal"){
	     dataType={"taskOrder":"","taskStatus":"2","nameLike":searchName};
	}
	if(obj.id==="finish"){
	     dataType={"taskOrder":"","taskStatus":"3","nameLike":searchName};
	}
	return dataType;
	
}
//单击按钮刷新页列表里面的内容
$(".searchbox .clearfix").on("click", "#search", function() {
	var searchName=$("#searchName");
	if(soptasklist == "" || soptasklist == undefined || soptasklist == null){
		searchName="";
	}
	setData(this,searchName);
});
//操作处理   startFlag  1，待认领；2，待处理；3，已完成
$(".caozuo").on("click", "a", function() {
	 var str=this.id;
	var startFlag=str.subStr(str.length-1,1) ;
	var jsonData={"taskStatus":startFlag};
	$.ajax({
		url :'/galaxy/soptask/updateTaskStatus',
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
				if(startFlag==="1"){				
					this.href='/galaxy/soptask/goClaimtcPage?id='+data.entity.id;
				} 
			}
		}
});	
});

})

