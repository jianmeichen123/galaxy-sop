//首页相关js

var top5ProjectMeetingList ={};

var moreProjectMeetingList ={};
// top5立项排期
function top5ProjectMeeting() {
	sendGetRequest(platformUrl.top5ProjectMeeting, null, top5ProjectMeetingCallback, null);
}

// 所有立项排期
function moreProjectMeeting() {
	sendGetRequest(platformUrl.moreProjectMeeting, null, moreProjectMeetingCallback, null);
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