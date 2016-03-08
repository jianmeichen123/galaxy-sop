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
		alert(data.entityList)
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#tbody"); 
			tbodyList.empty();
			$(list).each(function(){
				 var temp = $(this)[0];
				// var li = '<li data-tab="nav" '+'
				// code="'+dict.code+'"'+'>'+dict.name+'</li>';
				 var tr='<tr>'+
					 '<td>1</td>'+
					 '<td>'+ temp.projectName+'</td>'+
					 '<td>'+ temp.meetingDate+'</td>'+
					 '<td>'+temp.meetingCount+'</td>'+
					// '<td class="caozuo"><a href="/galaxy/soptask/goClaimtcPage?id=1" data-btn="claim" class="blue" id="flag_'+soptask.statusFlag+'">'+soptask.caozuo+'</a>'+
					// '<input type="hidden" id="taskId" value="'+soptask.id+'"></td>'+
					 //'<td><a href="/galaxy/soptask/goClaimtcPage?id=1" data-btn="claim">认领</a><td>'+
					' </tr>'; 	
			  });
		}
	}


function top5ProjectMeetingCallback(data) {
	
	var list = data.entityList;
	
	$("#projectMeeting tr:not(:first)").empty();
	
	

	// 循环list集合

	for(var i = 0;i<tlist.length;i++){

		// 获取表格的列数

		var tablelength = $("#projectMeeting").length;

		// 插入tablelength列

		var row = showresult.insertRow(tablelength);

		// 获取第一列,赋值

		var col = row.insertCell(0);

		col.innerHTML = (list.length-i);

		// 获取第二列,赋值

		var col = row.insertCell(1);

		var pName = list[i].projectName;

		col.innerHTML = pName;
		var col = row.insertCell(2);

		col.innerHTML = list[i].meetingDate;

		var col = row.insertCell(3);

		col.innerHTML = list[i].meetingCount;
	}
		
}

function moreProjectMeetingCallback(data) {
	moreProjectMeetingList = data.entityList;
}