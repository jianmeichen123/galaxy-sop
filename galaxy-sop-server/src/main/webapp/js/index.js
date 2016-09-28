//首页相关js
	var top5ProjectMeetingList ={};
	

	var moreProjectMeetingList ={};

	function top5Message() {
		var data = {};
		data["pageSize"] = 4;
		data["pageNum"] = 0;
		data["module"] = 1;
		//sendPostRequestByJsonObj(platformUrl.operationMessageQueryList, data, top5MessageCallback);
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
		sendGetRequest(Constants.sopEndpointURL+"/galaxy/home/moreProjectMeeting", null, moreProjectMeetingCallback);
	}


	// top5ceo评审
	function top5CeoPsMeeting() {
		sendGetRequest(platformUrl.top5CeoPsMeeting, null, top5CeoPsMeetingCallback);
	}

	//所有CEO评审
	function moreProjectCeoPsWill() {
		sendGetRequest(platformUrl.moreProjectCeoPsWill, null, moreCeoPsbodyMeetingCallback);
		
	}


	//所有投决会
	function moreProjectVoteWill() {
		sendGetRequest(platformUrl.moreProjectVoteWill, null, moreVotebodyMeetingCallback);
	}


	//主页待办任务
	function selectSopTask(){
		var jsonData={"pageNum":0,"pageSize":4,"flagUrl":"index"}; 
		sendPostRequestByJsonObj(platformUrl.soptaskshouye,jsonData, SopTaskCallback);
	}

	function top5CeoPsMeetingCallback(data) {		
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#ceopsbodytop"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 var _td;
				 sendGetRequest(platformUrl.judgeRole + "/" + templ.projectId, null,function(data){
					if(data.result.status!="OK"){
						return false;
					}
					if(data.result.message=="show"){
						_td = '<td  title="'+ getValue(templ.projectName)+'">'+ '<a class="blue cutstr" href="javascript:void(0)" onclick="to_pro_info(' + templ.projectId + ')">' + getValue(templ.projectName)+ '</a>' +'</td>';
					}else{
						_td = '<td  title="'+ getValue(templ.projectName)+'" class="cutstr">'+ getValue(templ.projectName) +'</td>';
					}
				 });
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 _td+
					 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
					 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
				 
			  });
			cutStr(5,'cutstr');
		}		
		if (list.length==0) {
			
			var tbodyList = $("#ceopsbodytop"); 
			var noData =
				'<tr>'+
				 '<td colspan="4" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
	   }
		if(list.length<3){
			$("#ceo_p .position").css("display","none");
		}
	}
	
	function moreCeoPsbodyMeetingCallback(data) {
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#ceopsbody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 '<td>'+ getValue(templ.projectName)+'</td>'+
					 //'<td>'+ getStatusValue(templ.status)+'</td>'+
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
//	消息提醒
	function projectNameFormat(value, row, index){
		var id = row.projectId;
		 
		var aa = "<a href=\'" + Constants.sopEndpointURL + "/galaxy/project/detail/" +id + "?mark=m\' class=\"blue project_name\"> "+
					row.projectName +
				" </a>" ;	
		var content =value.replace("projectname",aa);
		return content;	
	}
	/*function top5MessageCallback(data){
		if(data.result.status == "OK"){
			var news_table =  $(".r_news table tbody");
			var content = data.pageList.content;			
			$(content).each(function(index,item){
				var tr = " <tr><td>"+longTimeFormat_Chines(item.createdTime)+"</td> <td>"+item.projectName+"</td></tr>"
				news_table.append(tr);
			})
			if (content.length==0) {
				var noData =
					'<tr>'+
					 '<td colspan="2" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
					' </tr>'; 			
				news_table.append(noData);
		   }
			if(content.length<3){
				news_table.parent().parent().siblings().children('.more').css("display","none");
			};
		}
	}*/
	
	function SopTaskCallback(data){
		//组装数据
		
		var list =  data.pageList.content;
		if(list.length<4){
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
					 '<td>'+i+'</td>'+
					 '<td>'+ taskOrderDesc+'</td>'+
					 '<td>'+ temp.taskType+'</td>'+
					 '<td>'+ temp.taskName+'</td>'+
					 '<td>'+ temp.taskStatus+'</td>'+
					 '<td title="'+ getValue(temp.projectName)+'" class="cutstr">'+ temp.projectName+'</td>'+
					 '<td>'+ temp.caozuohtml+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			cutStr(10,'cutstr');
		}else{
			var tbodyList = $("#sopStak"); 
			var noData =
				'<tr>'+
				 '<td colspan="7" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
		}	
	}

	function ProjectVoteWillCallback(data){
		
		//根据id判断类型（组装json数据）
		var list = data.entityList;
		if(list.length<3){
			
			$("#tbody").parent().parent().siblings().children('.more').css("display","none");
		};
		if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
			var tbodyList = $("#tbody"); 
			var i=0;
			$(list).each(function(){
				 var temp = $(this)[0];
				 
				 	var _td;
					sendGetRequest(platformUrl.judgeRole + "/" + temp.projectId, null,function(data){
						if(data.result.status!="OK"){
							return false;
						}
						if(data.result.message=="show"){
							_td = '<td title="'+ getValue(temp.projectName)+'">' + '<a class="cutstr blue" href="javascript:void(0)" onclick="to_pro_info(' + temp.projectId + ')">'+ getValue(temp.projectName)+ '</a>' + '</td>';
						}else{
							_td = '<td class="cutstr" title="'+ getValue(temp.projectName)+'">' + getValue(temp.projectName) + '</td>';
						}
						
						 i=i+1;
						 var tr='<tr>'+
						 '<td>'+i+'</td>'+
						 _td + 
						 '<td>'+ getDateValue(temp.meetingDate)+'</td>'+
						 '<td>'+getIntegerValue(temp.meetingCount)+'</td>'+
						' </tr>'; 
						 tbodyList.append(tr);
					});
			  });
			cutStr(5,'cutstr');
		}else{
			var tbodyList = $("#tbody"); 
			var noData =
				'<tr>'+
				 '<td colspan="4" class="no_info"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
		}
		
	
		
		
		
		}

	function top5ProjectMeetingCallback(data) {			
		var list = data.entityList;
		if(list.length<3){
			$("#tlbody").parent().parent().siblings().children('.more').css("display","none");
		};
		if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
			var tbodyList = $("#tlbody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 
				 var _td;
					sendGetRequest(platformUrl.judgeRole + "/" + templ.projectId, null,function(data){
						if(data.result.status!="OK"){
							return false;
						}
						if(data.result.message=="show"){
							_td = '<td  title="'+ getValue(templ.projectName)+'">'+ '<a class="blue cutstr" href="javascript:void(0)" onclick="to_pro_info(' + templ.projectId + ')">' + getValue(templ.projectName)+ '</a>' +'</td>';
						}else{
							_td = '<td  title="'+ getValue(templ.projectName)+'" class="cutstr">'+ getValue(templ.projectName) +'</td>';
						}
						
						var tr='<tr>'+
						 '<td>'+i+'</td>'+
						 _td +
						 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
						 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
						' </tr>'; 
					 tbodyList.append(tr);
						
					});
			  });			
		}else{
			var tbodyList = $("#tlbody"); 
			var noData =
				'<tr>'+
				 '<td colspan="4" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
		}
cutStr(5,'cutstr');
	}
	function moreProjectMeetingCallback(data) {
		var list = data.entityList;
		if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
			var tbodyList = $("#tcbody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
				 '<td>'+i+'</td>'+
				 '<td>'+ getValue(templ.projectName)+'</td>'+
				 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
				 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
				 '<td>'+getValue(templ.projectCareerline)+'</td>'+
				 '<td>'+getValue(templ.createUname)+'</td>'+
				 '<td>'+getValue(templ.remark)+'</td>'+
				' </tr>'; 
			    tbodyList.append(tr);
			  });
			if(list.length<3){
				$("#tcbody").parent().parent().siblings().children('.more').css("display","none");
			}
		}else{
			var tbodyList = $("#tcbody"); 
			var noData =
				'<tr>'+
				 '<td colspan="4" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
		}
	}
	function moreVotebodyMeetingCallback(data) {
		var list = data.entityList;
		if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
			var tbodyList = $("#votebody"); 
			tbodyList.empty();
			var i=0;
			$(list).each(function(){
				 var templ = $(this)[0];
				 i=i+1;
				 var tr='<tr>'+
					 '<td>'+i+'</td>'+
					 '<td>'+ getValue(templ.projectName)+'</td>'+
					 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
					 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
					 '<td>'+getValue(templ.projectCareerline)+'</td>'+
					 '<td>'+getValue(templ.createUname)+'</td>'+
					 '<td>'+getValue(templ.remark)+'</td>'+
					' </tr>'; 
				 tbodyList.append(tr);
			  });
			if(list.length<3){
				$("#votebody").parent().parent().siblings().children('.more').css("display","none");
	         }
		}else{
			var tbodyList = $("#votebody"); 
			var noData =
				'<tr>'+
				 '<td colspan="4" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
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
function showList2() {
	moreProjectCeoPsWill();
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




//主页创意
function selectCyIndex(){ 
	var jsonData={"pageNum":0,"pageSize":4,"isforindex":"isfor"}; 
	sendPostRequestByJsonObj(platformUrl.sopcyshouye,jsonData, cyIndexCallback);
}
function cyIndexCallback(data){
	//组装数据
	var tbodyList = $("#cy_index");
	
	var list =  data.pageList.content;
	if(list != null && list != "" && typeof(list) != 'undefined' && list.length != 0 ){
      var ideaProgress = {
			"ideaProgress:1":"待认领",
			"ideaProgress:2":"调研",
			"ideaProgress:3":"创建立项会",
			"ideaProgress:4":"搁置",
			"ideaProgress:5":"创建项目"
		};
      
      
		$.each(list, function(i, temp){
			
			var ideaProgressDesc = "";
			if (temp.ideaProgress in ideaProgress) {
				if (temp.ideaProgress == "ideaProgress:1" || temp.ideaProgress == "ideaProgress:4" ) {
					ideaProgressDesc = "<a href=\'javascript:;\' class=\'blue\' onclick=\'toCyOper("+temp.id+")\' >" + ideaProgress[temp.ideaProgress] + '</a>';
				} else {
					ideaProgressDesc = ideaProgress[temp.ideaProgress];
				}
			}
			 
			var tr='<tr>'+
				'<td>'+ temp.ideaCode+'</td>'+
				'<td class="cutstr" title="'+ temp.ideaName+'">'+ temp.ideaName+'</td>'+
				'<td>'+ temp.departmentDesc+'</td>'+ 
				'<td>'+ ((isNaN(temp.createdTime))?'&nbsp;&nbsp;-': Number(temp.createdTime).toDate().format("yyyy-MM-dd"))+'</td>'+
				'<td>'+ ((isNaN(temp.updatedTime))?'&nbsp;&nbsp;-': Number(temp.updatedTime).toDate().format("yyyy-MM-dd"))+'</td>'+
				'<td class="cutstrName" title="'+ temp.createdUname+'">'+ temp.createdUname+'</td>'+
				'<td>'+ ideaProgressDesc+'</td>'+
				'</tr>'; 
			tbodyList.append(tr);
		});
		cutStr(10,'cutstr');
		cutStr(4,'cutstrName');
		if(list.length<4){
			$("#cy_index").parent().parent().siblings().children('.more').css("display","none");	
		}
	}else{
		$("#cy_index").parent().parent().siblings().children('.more').css("display","none");
		var noData =
			'<tr>'+
			'<td colspan="7" class="no_info"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
			' </tr>'; 			
		tbodyList.append(noData);
	}	
}
function dealtTask(){
	window.location.href=$("#menus").find("[data-menueid='168']").attr("href");
}

function toCyPage(){
	window.location.href=$("#menus").find("[data-menueid='137']").attr("href");
}

function toCyOper(ideaid){
	window.location.href=$("#menus").find("[data-menueid='137']").attr("href")+"&indextoid="+ideaid;
}
function Sopfile(){
	window.location.href=$("#menus").find("[data-menueid='135']").attr("href");
}

