//首页相关js
	var top5ProjectMeetingList ={};

	var moreProjectMeetingList ={};


	// top5立项排期
	function top5ProjectMeeting() {
		sendGetRequest(platformUrl.top5ProjectMeeting, null, top5ProjectMeetingCallback, null);
	}

	//top5投决排期
	function ProjectVoteWill() {
		sendGetRequest(platformUrl.ProjectVoteWill, null, ProjectVoteWillCallback, null);
	}

	// 所有立项排期
	function moreProjectMeeting() {
		sendGetRequest("http://localhost:8080/galaxy-sop-server/galaxy/home/moreProjectMeeting", null, moreProjectMeetingCallback, null);
	}


	//所有投决会
	function moreProjectVoteWill() {
		sendGetRequest(platformUrl.moreProjectVoteWill, null, moreProjectVoteWillCallback, null);
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
					 '<td>'+ temp.projectName+'</td>'+
					 '<td>'+ temp.meetingDate+'</td>'+
					 '<td>'+temp.meetingCount+'</td>'+
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
					 '<td>'+ templ.projectName+'</td>'+
					 '<td>'+ formatDate(templ.meetingDate)+'</td>'+
					 '<td>'+templ.meetingCount+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			
		}
	}
	function moreProjectMeetingCallback(data) {
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#tbody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 '<td>'+ templ.projectName+'</td>'+
					 '<td>'+ formatDate(templ.meetingDate)+'</td>'+
					 '<td>'+templ.meetingCount+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			
		}
	}
	
	function formatDate(date, format) {   
	    if (!date) return;   
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
	alert("ok");
	moreProjectMeeting();
}