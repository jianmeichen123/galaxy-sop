//首页相关js
	var top5ProjectMeetingList ={};

	var moreProjectMeetingList ={};

	function top5Message() {
		var data = {};
		data["pageSize"] = 3;
		data["pageNum"] = 0;
		sendPostRequestByJsonObj(platformUrl.operationMessageQueryList, data, top5MessageCallback);
	}
	

	// top5立项排期
	function top5ProjectMeeting() {
		sendGetRequest(platformUrl.top5ProjectMeeting, null, top5ProjectMeetingCallback);
	}

	//top5投决排期
	function ProjectVoteWill() {
		sendGetRequest(platformUrl.ProjectVoteWill, null, ProjectVoteWillCallback);
	}

	// 所有立项排期
	function moreProjectMeeting() {
		sendGetRequest(sopContentUrl+"/galaxy/home/moreProjectMeeting", null, moreProjectMeetingCallback);
	}


	//所有投决会
	function moreProjectVoteWill() {
		sendGetRequest(platformUrl.moreProjectVoteWill, null, moreVotebodyMeetingCallback);
	}


	//主页待办任务
	function selectSopTask(){
		var jsonData={"pageNum":0,"pageSize":3,"flagUrl":"index"}; 
		sendPostRequestByJsonObj(platformUrl.soptaskshouye,jsonData, SopTaskCallback);
	}

	function top5MessageCallback(data){
		if(data.result.status == "OK"){
			var news_table =  $(".r_news table tbody");
			var content = data.pageList.content;
			
			$(content).each(function(index,item){
				var tr = " <tr><td>"+(index+1)+"</td><td>"+longTimeFormatChines(item.createdTime)+"</td> <td>"+item.operator+"</td><td>"+item.content+"</td></tr>"
				news_table.append(tr);
			})
		}
	}
	
	function SopTaskCallback(data){
		//组装数据
		
		var list =  data.pageList.content;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#sopStak"); 
			var i=0;
			$(list).each(function(){
				 var temp = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 '<td>'+ temp.taskOrder+'</td>'+
					 '<td>'+ temp.taskType+'</td>'+
					 '<td>'+ temp.projectName+'</td>'+
					 '<td>'+ temp.taskStatus+'</td>'+
					 '<td>'+ temp.hours+'</td>'+
					 '<td>'+temp.caozuohtml+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			
		}
	}

	function ProjectVoteWillCallback(data){
		//根据id判断类型（组装json数据）
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#tbody"); 
			var i=0;
			$(list).each(function(){
				 var temp = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
				 '<td>'+i+'</td>'+
				 '<td>'+ getValue(temp.projectName)+'</td>'+
				 '<td>'+ getDateValue(temp.meetingDate)+'</td>'+
				 '<td>'+getIntegerValue(temp.meetingCount)+'</td>'+
				' </tr>'; 
			 tbodyList.append(tr);
			  });
			
		}
	}

	function top5ProjectMeetingCallback(data) {
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#tlbody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 '<td>'+ getValue(templ.projectName)+'</td>'+
					 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
					 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			
		}
	}
	function moreProjectMeetingCallback(data) {
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#tcbody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
				 '<td>'+i+'</td>'+
				 '<td>'+ getValue(templ.projectName)+'</td>'+
				 '<td>'+ getStatusValue(templ.status)+'</td>'+
				 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
				 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
				 '<td>'+getValue(templ.projectCareerline)+'</td>'+
				 '<td>'+getValue(templ.createUname)+'</td>'+
				 '<td>'+getValue(templ.remark)+'</td>'+
				' </tr>'; 
			    tbodyList.append(tr);
			  });
			
		}
	}
	function moreVotebodyMeetingCallback(data) {
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#votebody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 '<td>'+ getValue(templ.projectName)+'</td>'+
					 '<td>'+ getStatusValue(templ.status)+'</td>'+
					 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
					 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
					 '<td>'+getValue(templ.projectCareerline)+'</td>'+
					 '<td>'+getValue(templ.createUname)+'</td>'+
					 '<td>'+getValue(templ.remark)+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			
		}
	}
	
	function getValue(str) {
		if (typeof(str) == "undefined") { 
			 return "-";
		}  else {
			return str;
		}
	}
	
	function getStatusValue(str) {
		if (typeof(str) == "undefined" || str == null) { 
			 return "-";
		}  else if (str=="meetingResult:2"){
			return "待完成";
		} else if(str=="meetingResult:1") {
			return "待认领";
		} else if (str=="meetingResult:3") {
			return "已完成";
		}
	}
	function getDateValue(str) {
		if (str == null || typeof(str) == "undefined") {
			return " ";
		} else {
		  return formatDate(str);
		}
	}
	
	function getIntegerValue(str) {
		if (str == null) {
			return 0
		} else {
			return str
		}
	}
	function formatDate(date, format) {   
	    if (!date) return null;   
	    if (!format) format = "yyyy-MM-dd";   
	    switch(typeof date) {   
	        case "string":   
	            date = new Date(date.replace(/-/, "/"));   
	            break;   
	        case "number":   
	            date = new Date(date);   
	            break;   
	    }    
	    if (!date instanceof Date) return;   
	    var dict = {   
	        "yyyy": date.getFullYear(),   
	        "M": date.getMonth() + 1,   
	        "d": date.getDate(),   
	        "H": date.getHours(),   
	        "m": date.getMinutes(),   
	        "s": date.getSeconds(),   
	        "MM": ("" + (date.getMonth() + 101)).substr(1),   
	        "dd": ("" + (date.getDate() + 100)).substr(1),   
	        "HH": ("" + (date.getHours() + 100)).substr(1),   
	        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
	        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
	    };       
	    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
	        return dict[arguments[0]];   
	    });                   
	}   
function showList() {
	moreProjectMeeting();
}
function showList1() {
	moreProjectVoteWill();
}
/*//紧急任务
function totalUrgent() {
	sendGetRequest(platformUrl.totalUrgent, null, totalUrgentCallback, null);
}
//待办任务
function totalMission() {
	sendGetRequest(platformUrl.totalMission, null, totalMissionCallback, null);
}
function totalUrgentCallback(data) {
	$('.totalUrgent').html(data.total)
}

function totalMissionCallback(data) {
	$('.bubble').html(data.total)
}
*/
