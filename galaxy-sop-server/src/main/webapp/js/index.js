//首页相关js
	var top5ProjectMeetingList ={};

	var moreProjectMeetingList ={};

	var ProjectVoteWill ={};

	var moreProjectVoteWill={};
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
		sendGetRequest(platformUrl.moreProjectMeeting, null, moreProjectMeetingCallback, null);
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
			
			tbodyList.empty();
			$(list).each(function(){
				 var temp = $(this)[0];
				
				 var tr='<tr>'+
					 '<td>1</td>'+
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
			$(list).each(function(){
				 var templ = $(this)[0];
				 var tr='<tr>'+
					 '<td>1</td>'+
					 '<td>'+ templ.projectName+'</td>'+
					 '<td>'+ templ.meetingDate+'</td>'+
					 '<td>'+templ.meetingCount+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			
		}
	}



function moreProjectMeetingCallback(data) {
	moreProjectMeetingList = data.entityList;
}