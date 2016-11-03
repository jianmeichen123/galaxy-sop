/**
 * 事项预览
 * matterPreviewUtils.init();
 */
var matterPreviewUtils = {
		init : function(){
			var params = {deptid:-1};
			sendPostRequestByJsonObj(platformUrl.MeetingShedule,params,function(data){
				//console.log(data);
				var result = data.result;
				var entity = data.entity;
				if(result.status=='ERROR'){
					$.popup(100,'消息',result.message);
					return false;
				}
				if(entity!=null){
					var meeting_number_today = data.entity.meetingTotal;
					var lxh_number_today = data.entity.lxhTotal;
					var tjh_number_today = data.entity.tjhTotal;
					var psh_number_today = data.entity.pshTotal;
					var lxh_eduling_wait = data.entity.lxhWait;
					var tjh_eduling_wait =data.entity.tjhWait;
					var psh_eduling_wait = data.entity.pshWait;
					if(lxh_eduling_wait=="0"){
						$("#lxh_eduling_wait").addClass("gray")
					}
					if(tjh_eduling_wait=="0"){
						$("#tjh_eduling_wait").addClass("gray")
					}
					if(psh_eduling_wait=="0"){
						$("#psh_eduling_wait").addClass("gray")
					}
					
					$("#meeting_number_today").html(data.entity.meetingTotal);
					$("#lxh_number_today").html(data.entity.lxhTotal);
					$("#tjh_number_today").html(data.entity.tjhTotal);
					$("#psh_number_today").html(data.entity.pshTotal);
					
					$("#lxh_eduling_wait").html(data.entity.lxhWait);
					$("#tjh_eduling_wait").html(data.entity.tjhWait);
					$("#psh_eduling_wait").html(data.entity.pshWait);
					
				}else{
					$("#lxh_eduling_wait").html(0);
					$("#tjh_eduling_wait").html(0);
					$("#psh_eduling_wait").html(0);
				}
				
				//事项预览点击数字跳出弹出层
				var meeting_obj = {serverUrl: platformUrl.MeetingSheduleNewList};
				meeting_obj.params={meetingType:'',scheduleStatus:'',type:''};
				meeting_obj.columns = [
				               {field:'projectName',"class":'data-input',width:'135px',title:'项目名称'},
				               {field:'meetingType',"class":'data-input',width:'90px',title:'会议类型'},
				               {field:'status',"class":'data-input',width:'80px',title:'状态'},
				               {field:'meetingCount',"class":'data-input',width:'80px',title:'过会次数'},
				              // {field:'pre_meeting_date',class:'data-input',title:'上次过会时间'},
				               {field:'meetingDateStr',"class":'data-input',width:'135px',title:'上次过会时间'},
				               {field:'projectCareerline',"class":'data-input',width:'135px',title:'投资事业线'},
				               {field:'createUname',"class":'data-input',width:'162px',title:'投资经理'},
				               {field:'remark',"class":'data-input',width:'66px',title:'备注'}];
						
				$("#meeting_number_today").bind('click',function(){
					meeting_obj.params.meetingType = "('meetingType:3','meetingType:4')";
					meeting_obj.params.scheduleStatus = '';
					meeting_obj.params.type ="0";
					forwardWithHeader(platformUrl.popupMeetingList + "meetingType:3,meetingType:4");
				});
				
					
				$("#lxh_number_today").bind('click',function(){
					meeting_obj.params.meetingType = "meetingType:3";
					meeting_obj.params.scheduleStatus = '';
					meeting_obj.params.type ="0";
					forwardWithHeader(platformUrl.popupMeetingList + "meetingType:3");
				});
						
				$("#tjh_number_today").bind('click',function(){
					meeting_obj.params.meetingType = "meetingType:4";
					meeting_obj.params.scheduleStatus = '';
					meeting_obj.params.type ="0";
					forwardWithHeader(platformUrl.popupMeetingList + "meetingType:4");
				});
				
				
				$("#psh_number_today").bind('click',function(){
					meeting_obj.params.meetingType = "meetingType:2";
					meeting_obj.params.scheduleStatus = '';
					meeting_obj.params.type ="0";
					forwardWithHeader(platformUrl.popupMeetingList + "meetingType:2");
				});
				
				if ( lxh_eduling_wait >0 ){	
					$("#lxh_eduling_wait").bind('click',function(){
						meeting_obj.params.meetingType = "meetingType:3";
						meeting_obj.params.scheduleStatus = 0;
						meeting_obj.params.type ="1";
						var tite_mame=$(this).attr("data-name");
						ajaxPopup(meeting_obj,tite_mame);
					});
				}
				
				if ( tjh_eduling_wait >0 ){			
					$("#tjh_eduling_wait").bind('click',function(){
						meeting_obj.params.meetingType = "meetingType:4";
						meeting_obj.params.scheduleStatus = 0;
						meeting_obj.params.type ="1";
						var tite_mame=$(this).attr("data-name");
						ajaxPopup(meeting_obj,tite_mame);
					});
				}
				
				if ( psh_eduling_wait >0 ){			
					$("#psh_eduling_wait").bind('click',function(){
						meeting_obj.params.meetingType = "meetingType:2";
						meeting_obj.params.scheduleStatus = 0;
						meeting_obj.params.type ="1";
						var tite_mame=$(this).attr("data-name");
						ajaxPopup(meeting_obj,tite_mame);
					});
				}
			});
		}
}
	
	