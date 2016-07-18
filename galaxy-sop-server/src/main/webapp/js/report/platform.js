var totalNum_all='';
var totalDay_all='';
$(function() {
	//meetingShedule(),
	
	top5ProjectMeeting();
	top5CeoPsMeeting();
	ProjectVoteWill();
	selectSopTask();
	createMenus(1);
	initHref();
	load_data_chart_kpi();//绩效考核图表
	load_data_chart_progress();//项目进度图表
	load_data_chart_tz_money();//投资资金图表
	load_data_chart_project_time();//项目历时
	loadAjaxSopUserSchedule(platformUrl.sheduleMoreThree); 
	//项目进度图表默认加载链接
	$("#container_progress .highcharts-title tspan").click(function(){
		var url = platformUrl.projectAnalysis;
		if(forwardParam.progressParam){
			url += "?forwardProgress=" + forwardParam.progressParam ;
		}
		forwardWithHeader(url);
	});
	//项目进度图表默认加载链接----兼容ie8
	$("#container_progress .highcharts-title span").click(function(){
		var url = platformUrl.projectAnalysis;
		if(forwardParam.progressParam){
			url += "?forwardProgress=" + forwardParam.progressParam ;
		}
		forwardWithHeader(url);
	})
	
	/*//项目历时图表默认加载链接
	$("#container_time .highcharts-title tspan").click(function(){
		var url = platformUrl.projectAnalysis;
		if(forwardParam.timeParam){
			url += "?forwardProgress=" + forwardParam.timeParam ;
		}
		forwardWithHeader(url);
	})
	//项目历时图表默认加载链接---兼容ie8
	$("#container_time .highcharts-title span").click(function(){
		var url = platformUrl.projectAnalysis;
		if(forwardParam.timeParam){
			url += "?forwardProgress=" + forwardParam.timeParam ;
		}
		forwardWithHeader(url);
	})*/
	//项目进度无数据样式
	if($("#container_progress .highcharts-title tspan").text()=="0个" || $("#container_progress .highcharts-title span").text()=="0个"){
		$(".mask_platform_progress").show();
		$('#container_progress').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                backgroundColor: 'rgba(255, 255, 255, 0)',
            },
            title: {
                text: "<span style='color:#e9ebf2'>"+'0个'+"</span>",
                verticalAlign:'middle',
                y:5,
                x:-95,
                style:{
                    fontFamily:'微软雅黑',
                    color:'#e9ebf2',
                    fontWeight:'bold',
                },
            },
            //去除版权
            credits: {
              enabled:false
            },
            //去除右上角导出图标
            exporting: {
                enabled:true
            },
            plotOptions: {
            pie: {
                borderWidth: 0,
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:120
                },
                showInLegend: true
            }
        },

        legend: {                                                 
            layout: 'horizontal', 
            floating: false,                                       
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemWidth:90,
            width:200,
            padding:-25,
            minHeight:100,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
            //x:0,
        },            

            series: [{
                type: 'pie',
                size:'140%',
                innerSize :'70%',
                name: '项目退出占比',
                data: [
                    {name:'接触访谈',color:'#e9ebf2',y:8},
                    {name: '内部评审',color:'#e9ebf2',y: 10},
                    { name:'CEO评审',color:'#e9ebf2',y:16},
                    { name:'立项会',color:'#e9ebf2',y:20},
                    { name:'投资意向书',color:'#e9ebf2',y: 30},
                    { name:'尽职调查',color:'#e9ebf2',y:40},
                    { name:'投决会',color:'#e9ebf2',y:50},
                    { name:'投资协议',color:'#e9ebf2',y:55},
                    { name:'股权交割',color:'#e9ebf2',y:60},
                    { name:'投后运营',color:'#e9ebf2',y:90},
                ],
                dataLabels: {
                    enabled: false, 
                }
            }]
        });
	}
	//项目历时无数据样式
	if($("#container_time .highcharts-title tspan").text()=="0天" || $("#container_time .highcharts-title span").text()=="0天"){
		$(".mask_platform_time").show();
		$('#container_time').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                backgroundColor: 'rgba(255, 255, 255, 0)',
            },
            title: {
                text: "<span style='color:#e9ebf2'>"+'0天'+"</span>",
                verticalAlign:'middle',
                y:5,
                x:-95,
                style:{
                    fontFamily:'微软雅黑',
                    color:'#e9ebf2',
                    fontWeight:'bold',
                },
            },
            //去除版权
            credits: {
              enabled:false
            },
            //去除右上角导出图标
            exporting: {
                enabled:true
            },
            tooltip: false,
            plotOptions: {
            pie: {
                borderWidth: 0,
                allowPointSelect: false,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    color:'black',
                    rotation: -90,
                    enabled: true,
                    formatter:function(){
                        return this.point.percentage.toFixed(1)+"%";
                    },
                    connectorWidth:0,
                    connectorPadding:0,
                    distance:120
                },
                showInLegend: true
            }
        },
        legend: {                                                
            layout: 'horizontal',                                  
            floating: false,                                       
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0,
            itemWidth:90,
            width:200,
            padding:-25,
            minHeight:100,
            itemStyle:{
                fontWeight:'normal',
                color:'#7a8798',
            },
        },            

            series: [{
                type: 'pie',
                size:'140%',
                innerSize :'70%',
                name: '项目退出占比',
                data: [
                    {name:'接触访谈',color:'#e9ebf2',y:8},
                    {name: '内部评审',color:'#e9ebf2',y: 0},
                    { name:'CEO评审',color:'#e9ebf2',y:0},
                    { name:'立项会',color:'#e9ebf2',y:0},
                    { name:'投资意向书',color:'#e9ebf2',y: 0},
                    { name:'尽职调查',color:'#e9ebf2',y:0},
                    { name:'投决会',color:'#e9ebf2',y:0},
                    { name:'投资协议',color:'#e9ebf2',y:0},
                    { name:'股权交割',color:'#e9ebf2',y:0},
                ],
                dataLabels: {
                    enabled: false, 
                }
            }]
        });
		
	}
	
	//console.log(isCEO);
	//console.log(isDSZ);
	function meetingShedule(){
		sendGetRequest(platformUrl.MeetingShedule, null, meetingShedulingCallBack);
	
	}
	var obj = {url : platformUrl.MeetingShedule,contentType:"application/json"};
	obj.data = {deptid:-1};
	ajaxCallback(obj,function(data){
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
		               {field:'projectName',align:'center',"class":'data-input',title:'项目名称'},
		               {field:'meetingType',align:'center',"class":'data-input',title:'会议类型'},
		               {field:'status',align:'center',"class":'data-input',title:'状态'},
		               {field:'meetingCount',align:'center',"class":'data-input',title:'过会次数'},
		              // {field:'pre_meeting_date',align:'center',class:'data-input',title:'上次过会时间'},
		               {field:'meetingDateStr',align:'center',"class":'data-input',title:'上次过会时间'},
		               {field:'projectCareerline',align:'center',"class":'data-input',title:'投资事业线'},
		               {field:'createUname',align:'center',"class":'data-input',title:'投资经理'},
		               {field:'remark',align:'center',"class":'data-input',title:'备注'}];
		
		//if ( meeting_number_today >0 ){			
			$("#meeting_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "('meetingType:3','meetingType:4')";
				meeting_obj.params.scheduleStatus = '';
				meeting_obj.params.type ="0";
				forwardWithHeader(platformUrl.popupMeetingList + "meetingType:3,meetingType:4");
			});
		//}
		
		//if ( lxh_number_today >0 ){			
			$("#lxh_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:3";
				meeting_obj.params.scheduleStatus = '';
				meeting_obj.params.type ="0";
				forwardWithHeader(platformUrl.popupMeetingList + "meetingType:3");
			});
		//}
		
		//if ( tjh_number_today >0 ){			
			$("#tjh_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:4";
				meeting_obj.params.scheduleStatus = '';
				meeting_obj.params.type ="0";
				forwardWithHeader(platformUrl.popupMeetingList + "meetingType:4");
			});
		//}
		
		//if ( psh_number_today >0 ){			
			$("#psh_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:2";
				meeting_obj.params.scheduleStatus = '';
				meeting_obj.params.type ="0";
				forwardWithHeader(platformUrl.popupMeetingList + "meetingType:2");
			});
		//}
		
		if ( lxh_eduling_wait >0 ){			
			$("#lxh_eduling_wait").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:3";
				meeting_obj.params.scheduleStatus = 0;
				meeting_obj.params.type ="1";
				ajaxPopup(meeting_obj);
			});
		}
		
		if ( tjh_eduling_wait >0 ){			
			$("#tjh_eduling_wait").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:4";
				meeting_obj.params.scheduleStatus = 0;
				meeting_obj.params.type ="1";
				ajaxPopup(meeting_obj);
			});
		}
		
		if ( psh_eduling_wait >0 ){			
			$("#psh_eduling_wait").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:2";
				meeting_obj.params.scheduleStatus = 0;
				meeting_obj.params.type ="1";
				ajaxPopup(meeting_obj);
			});
		}
		
	});
	
	
	(isCEO=='true' || isHHR=='true') ? $("#ceo_cat").show() : $("#ceo_cat").hide();
	isDSZ=='true' ? $("#dsz_cat").show() : $("#dsz_cat").hide();
	
	function initHref(){
		//console.log('initHref');
		$("#platform_jxkh_more").attr('href' , path + "/galaxy/report/kpi?guid="+userId+"&sid="+sessionId +"#gg_jxkh_u");//绩效考核链接
		$("#more_xmjd").attr('href' , path + "/galaxy/report/projectAnalysis?guid="+userId+"&sid="+sessionId);//项目进度
	}

	//首页相关js
	var top5ProjectMeetingList ={};

	var moreProjectMeetingList ={};


	// top5立项排期
	function top5ProjectMeeting() {
		sendGetRequest(platformUrl.top5ProjectMeeting, null, top5ProjectMeetingCallback);
	}

	//top5投决排期
	function ProjectVoteWill() {
//		alert(platformUrl.ProjectVoteWill)
		sendGetRequest(platformUrl.ProjectVoteWill, null, ProjectVoteWillCallback);
	}
	
	// top5ceo评审
	function top5CeoPsMeeting() {
		sendGetRequest(platformUrl.top5CeoPsMeeting, null, top5CeoPsMeetingCallback);
	}

	// 所有立项排期
	function moreProjectMeeting() {
		sendGetRequest(platformUrl.moreProjectMeeting, null, moreProjectMeetingCallback);
	}


	//所有投决会
	function moreProjectVoteWill() {
		sendGetRequest(platformUrl.moreProjectVoteWill, null, moreVotebodyMeetingCallback);
	}

	//所有CEO评审
	function moreProjectCeoPsWill() {
		sendGetRequest(platformUrl.moreProjectCeoPsWill, null, moreCeoPsbodyMeetingCallback);
	}

	//主页待办任务
	function selectSopTask(){
		var jsonData={"pageNum":0,"pageSize":5}; 
		sendPostRequestByJsonObj(platformUrl.soptaskshouye,jsonData, SopTaskCallback);
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
		
		if (list.length==0) {
			var tbodyList = $("#sopStak"); 
			var noData =
				'<tr>'+
				'<td colspan="4" class="no_info"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
			}
		if(list.length<3){
			$("#sopStak").parent().parent().siblings().children('.more').css("display","none");	
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
				 
				 var _td;
				 sendGetRequest(platformUrl.judgeRole + "/" + temp.projectId, null,function(data){
						if(data.result.status!="OK"){
							return false;
						}
						if(data.result.message=="show"){
							_td = '<td title="'+getValue((temp.projectName))+'">'+ '<a class="cutstr blue" href="javascript:void(0)" onclick="info(' + temp.projectId + ')">'+ getValue(temp.projectName)+ '</a>' + '</td>';
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
		}
		
		if (list.length==0) {
			var tbodyList = $("#tbody"); 
			var noData =
				'<tr>'+
				'<td colspan="4" class="no_info"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
	   }
		if(list.length<3){
			$('.position_12').css("display","none");
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
				  
				 var _td;
				 sendGetRequest(platformUrl.judgeRole + "/" + templ.projectId, null,function(data){
						if(data.result.status!="OK"){
							return false;
						}
						if(data.result.message=="show"){
							_td = '<td title="'+getValue((templ.projectName))+'">'+ '<a class="cutstr blue" href="javascript:void(0)" onclick="info(' + templ.projectId + ')">'+ getValue(templ.projectName)+ '</a>' + '</td>';
						}else{
							_td = '<td class="cutstr" title="'+ getValue(templ.projectName)+'">' + getValue(templ.projectName) + '</td>';
						}
						 i=i+1;
						 var tr='<tr>'+
							 '<td>'+i+'</td>'+
							 _td + 
//							 '<td class="cutstr" title="'+ getValue(templ.projectName)+'">'+ getValue(templ.projectName)+'</td>'+
							 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
							 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
							' </tr>'; 
						 tbodyList.append(tr);
						});
			  });
			
		}
		
		if (list.length==0) {
			var tbodyList = $("#tlbody"); 
			var noData =
				'<tr>'+
				'<td colspan="4" class="no_info no_info01"><span class="no_info_icon">没有找到匹配的记录</span></td>'+
				' </tr>'; 			
			tbodyList.append(noData);
	   }
		if(list.length<3){
			$('.position_11').css("display","none");
		}
	}
	
	function top5CeoPsMeetingCallback(data) {
		var list = data.entityList;
		if(list != "" || list != undefined || list != null){
			var tbodyList = $("#ceopsbodytop"); 
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
							_td = '<td title="'+getValue((templ.projectName))+'">'+ '<a class="cutstr blue" href="javascript:void(0)" onclick="info(' + templ.projectId + ')">'+ getValue(templ.projectName)+ '</a>' + '</td>';
						}else{
							_td = '<td class="cutstr" title="'+ getValue(templ.projectName)+'">' + getValue(templ.projectName) + '</td>';
						}	
						 var tr='<tr>'+
						 '<td>'+i+'</td>'+
						 _td + 
//						 '<td class="cutstr" title="'+getValue((templ.projectName))+'">'+ getValue((templ.projectName))+'</td>'+
						 '<td>'+ getDateValue(templ.meetingDate)+'</td>'+
						 '<td>'+getIntegerValue(templ.meetingCount)+'</td>'+
						' </tr>'; 
					 tbodyList.append(tr);
						
				 });
				 
				 
				
			  });
			
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
			$('.position_10').css("display","none");
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
				 '<td class="cutstr" title="'+getValue(templ.projectName)+'">'+ getValue(templ.projectName)+'</td>'+
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
					 '<td class="cutstr" title="'+getValue(templ.projectName)+'">'+ getValue(templ.projectName)+'</td>'+
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
	
	//立项会弹窗
	$("[data-btn='project']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				/*if (_url.indexOf("?") != -1){
					var index = _url.indexOf("?");
					var str =_url.substring(index+1);
					alert(str)
					if (str =="type=1") {
						showList();
					} else {
						//投诀会
						showList1();
					}
					
				}else {
					showList();
				}*/
				showList();
				}//模版反回成功执行	
		});
		return false;
	});
	
	$("[data-btn='vote']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				showList1();
				}//模版反回成功执行	
		});
		return false;
	});
	
	$("[data-btn='ceops']").on("click",function(){
		var $self = $(this);
		var _url = $self.attr("href");
		$.getHtml({
			url:_url,//模版请求地址
			data:"",//传递参数
			okback:function(){
				showList2();
				}//模版反回成功执行	
		});
		return false;
	});
	
	
	
	/*
	 * 事项预览
	 
	var obj = {url : platformUrl.platformMeetingScheduling,contentType:"application/json"};
	obj.data = {deptid:-1};
	ajaxCallback(obj,function(data){
		//console.log(data);
		var result = data.result;
		var entityList = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var entity = entityList[0];
		if(entity!=null){
			var meeting_number_today = entity.lxh_today + entity.tjh_today;
			var lxh_number_today = entity.lxh_today;
			var tjh_number_today = entity.tjh_today;
			var lxh_eduling_wait = entity.lxh_sche_num;
			var tjh_eduling_wait = entity.tjh_sche_num;
			
			$("#meeting_number_today").html(meeting_number_today);
			$("#lxh_number_today").html(lxh_number_today);
			$("#tjh_number_today").html(tjh_number_today);
			$("#lxh_eduling_wait").html(lxh_eduling_wait);
			$("#tjh_eduling_wait").html(tjh_eduling_wait);
		}else{
			$("#lxh_eduling_wait").html(0);
			$("#tjh_eduling_wait").html(0);
		}
		
		//事项预览点击数字跳出弹出层
		var meeting_obj = {serverUrl: platformUrl.meetingSchedList};
		meeting_obj.params={meetingType:'',sdate:'',edate:''};
		meeting_obj.columns = [
		               {field:'project_name',align:'center',"class":'data-input',title:'项目名称'},
		               {field:'meeting_type_name',align:'center',"class":'data-input',title:'会议类型'},
		               //{field:'status_name',align:'center',class:'data-input',title:'状态'},
		               {field:'meeting_count',align:'center',"class":'data-input',title:'过会次数'},
		              // {field:'pre_meeting_date',align:'center',class:'data-input',title:'上次过会时间'},
		               {field:'meeting_date',align:'center',"class":'data-input',title:'上次过会时间'},
		               {field:'dept_name',align:'center',"class":'data-input',title:'投资事业线'},
		               {field:'real_name',align:'center',"class":'data-input',title:'投资经理'},
		               {field:'remark',align:'center',"class":'data-input',title:'备注'} ];
		
		if ( meeting_number_today >0 ){			
			$("#meeting_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "('meetingType:3','meetingType:4')";
				meeting_obj.params.sdate = GetDateStrFormat(0);
				meeting_obj.params.edate = GetDateStrFormat(0);
				ajaxPopup(meeting_obj);
			});
		}
		
		if ( lxh_number_today >0 ){			
			$("#lxh_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:3";
				meeting_obj.params.sdate = GetDateStrFormat(0);
				meeting_obj.params.edate = GetDateStrFormat(0);
				ajaxPopup(meeting_obj);
			});
		}
		
		if ( tjh_number_today >0 ){			
			$("#tjh_number_today").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:4";
				meeting_obj.params.sdate = GetDateStrFormat(0);
				meeting_obj.params.edate = GetDateStrFormat(0);
				ajaxPopup(meeting_obj);
			});
		}
		
		if ( lxh_eduling_wait >0 ){			
			$("#lxh_eduling_wait").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:3";
				meeting_obj.params.sdate = '-1';
				meeting_obj.params.edate = '-1';
				ajaxPopup(meeting_obj);
			});
		}
		
		if ( tjh_eduling_wait >0 ){			
			$("#tjh_eduling_wait").bind('click',function(){
				meeting_obj.params.meetingType = "meetingType:4";
				meeting_obj.params.sdate = '-1';
				meeting_obj.params.edate = '-1';
				ajaxPopup(meeting_obj);
			});
		}
	});*/
	
	function load_data_chart_kpi(){
    	var obj ={url:platformUrl.deptkpi};
    	obj.contentType="application/json";
    	obj.data = {pageNum:0,pageSize:5},
    	ajaxCallback(obj,function(data){
    		//console.log(data);
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.pageList.content;
    		var isHHR = data.pageList.isHHR;
    		var re = [];
    		var ghl = [];
    		var categories = [];
    		for(var i=0;i<entityList.length;i++){
    			var rate = entityList[i].ghl_rate*100;
    			re.push(entityList[i].completed);
    			ghl.push(parseFloat(rate.toFixed(2)));
    			categories.push(isHHR ? entityList[i].real_name : entityList[i].dept_name);
    		}
    		//console.log(re);
    		containerKpiOptions.series[0].data = re;
    		containerKpiOptions.series[1].data = ghl;
    		containerKpiOptions.xAxis.categories = categories;
    		//containerKpiOptions.xAxis.labels.staggerLines = (categories.length>3) ? 2 : 1;
    		var chart = new Highcharts.Chart(containerKpiOptions);
    		if(re==''&& ghl==''){
    			$('#container_kpi').html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
    		}
    	});
    }
	
	function load_data_chart_progress(){
    	var obj ={url: platformUrl.projectprogress};
    	obj.contentType="application/json";
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.mapList;
    		var re = [];
    		var color=['#587edd','#49ceff','#00bdf4','#88dfd8','#4490d2','#bee6d5','#6ebdea','#ff9c89','#62d1b0','#a3e394'];
    		//var selectedPie = "";
    		var totalNum = 0;
    		for(var i=0;i<entityList.length;i++){
    			var rate = entityList[i].rate*100;
    			var tmp = {
    					name : entityList[i].name,
    					color :color[i],
    					y : entityList[i].c,
    					rate : parseFloat(rate.toFixed(1))
    			};
    			if(i==0){
    				//tmp.sliced=true;
    				//tmp.selected=true;
    				//selectedPie = {num:tmp.y,rate:rate};
    			}
    			re.push(tmp);
    			totalNum += entityList[i].c;
    			
    		}
    		totalNum_all =totalNum;
    		containerProgressOptions.series[0].data = re;
    		containerProgressOptions.title.text = "<span style='color:#4490d2'>"+ totalNum +"个</span>"+"<br/>";
    		containerProgressOptions.plotOptions.pie.events.click = function(e){
    			//console.log(e.point);
    			chart.setTitle(
    					{
    						text: "<span style='color:#4490d2'>"+ e.point.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.point.percentage.toFixed(1)) +"%</span>",
    						y:-5,
    						x:-95
    					}
    			);
    			chart.redraw();
    			e.point.select();
    			
    			//如果没有pie块被选择，返回到只显示数量状态。
    			var selected_curr = chart.getSelectedPoints();
    			if(selected_curr.length==0){
    				chart.setTitle(
        					{
        						text: "<span style='color:#4490d2'>"+ totalNum +"个</span>"+"<br/>",
        						y:5,
        						x:-95
        					}
        			);
    			}
    			judgeProgress($.trim(e.point.name),'progress');
    		};
    		containerProgressOptions.plotOptions.pie.point.events.legendItemClick = function(e){
    			
    			//return false;
    			//console.log(e.target.name);
    			//chart.setTitle({text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>"});
        		//chart.redraw();
    			chart.setTitle(
    					{
    						text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>",
    						y:-5,
    						x:-95
    					}
    			);
    			chart.redraw();
    			e.target.select();
    			var selected_curr = chart.getSelectedPoints();
    			//console.log(selected_curr)
    			if(selected_curr.length==0){
    				chart.setTitle(
        					{
        						text: "<span style='color:#4490d2'>"+ totalNum +"个</span>"+"<br/>",
        						y:5,
        						x:-95
        					}
        			);
    			}
    			judgeProgress($.trim(e.target.name),'progress');
    			//e.target.show();
    			return false;
    		};
    		var chart = new Highcharts.Chart(containerProgressOptions);
    	});
    }	
	function load_data_chart_tz_money(){
		var currDate = new Date();
		var year = currDate.getFullYear();
		var sym = year +'-01';
		var eym = year + '-12';
		
    	var obj ={url:platformUrl.rateRiseMonthChart};
    	obj.data={sym:sym,eym:eym,projectProgress:'projectProgress:10'};
    	obj.contentType="application/json";
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.mapList;
    		var gz = []; //初始估值
    		var tz = []; //初始投资额
    		var categories = [];
    		var valuations=0;
    		var contribution=0;
    		for(var i=0;i<entityList.length;i++){
    			valuations += (entityList[i].project_valuations/100);
    			contribution += (entityList[i].project_contribution/100);
    			var biz_month = entityList[i].biz_date.replace('-','');
    			
    			gz.push(parseFloat(valuations.toFixed(1)));
    			tz.push(parseFloat(contribution.toFixed(1)));
    			categories.push(biz_month);
    		}
    		containerTzMoneyOptions.series[0].data = gz;
    		containerTzMoneyOptions.series[1].data = tz;
    		containerTzMoneyOptions.xAxis.categories = categories;
    		if(categories.length==1){ //当只有一条数据的时候，显示点标记
    			containerTzMoneyOptions.plotOptions.series.marker.enabled=true;
    		}
    		var chart = new Highcharts.Chart(containerTzMoneyOptions);
    		if(gz==''&& tz==''){
    			$('#container_investmentFunds').html('<div  class="no_info_div"><span class="no_info_icon">　没有找到匹配的记录</span></div>');
    		}
    	});
    }
	
	function load_data_chart_project_time(){
    	var obj ={url:platformUrl.progressDurationList};
    	obj.data={model:2,sdate:'-1',edate:'-1',projectProgress:'projectProgress:10',projectStatus:-2};
    	obj.contentType="application/json";
    	ajaxCallback(obj,function(data){
    		var result = data.result;
    		if(result.status=='ERROR'){
    			$.popup(100,'消息',result.message);
    			return false;
    		}
    		var entityList = data.mapList;
    		var re = [];
    		var color=['#587edd','#49ceff','#00bdf4','#88dfd8','#4490d2','#bee6d5','#6ebdea','#ff9c89','#62d1b0'];
    		//var selectedPie = "";
    		var totalDay = 0;
    		for(var i=0;i<entityList.length;i++){
    			totalDay += entityList[i].duration_day;
    		}
    		totalDay_all=totalDay;
    		for(var i=0;i<entityList.length;i++){
    			var rate = entityList[i].duration_day/totalDay;
    			var tmp = {
    					name : entityList[i].project_progress_name,
    					color :color[i],
    					y : entityList[i].duration_day,
    					rate : parseFloat(rate.toFixed(1))
    			};
    			if(i==0){
    				//tmp.sliced=true;
    				//tmp.selected=true;
    				//selectedPie = {num:tmp.y,rate:rate};
    			}
    			re.push(tmp);
    		}
    		//console.log(re);
    		//console.log(totalDay);
    		containerProjectTimeOptions.series[0].data = re;
    		containerProjectTimeOptions.title.text = "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>";
    		containerProjectTimeOptions.plotOptions.pie.events.click = function(e){
    			//console.log(e.point.name);
    			//console.log(chart.title);
    			chart.setTitle(
    					{
    						text: "<span style='color:#4490d2'>"+ e.point.y +"天</span>"+"<br/>"+"<span>"+ parseFloat(e.point.percentage.toFixed(1)) +"%</span>",
    						y:-5,
    						x:-95
    					}
    			);
    			chart.redraw();
    			e.point.select();
    			
    			//如果没有pie块被选择，返回到只显示数量状态。
    			var selected_curr = chart.getSelectedPoints();
    			if(selected_curr.length==0){
    				chart.setTitle(
        					{
        						text: "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>",
        						y:5,
        						x:-90
        					}
        			);
    			}
    			judgeProgress($.trim(e.point.name),'time');
    			
    		};
    		containerProjectTimeOptions.plotOptions.pie.point.events.legendItemClick = function(e){
    			//console.log(e);
    			//chart.setTitle({text: "<span style='color:#4490d2'>"+ e.target.y +"个</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>"});
        		//chart.redraw();
    			chart.setTitle(
    					{
    						text: "<span style='color:#4490d2'>"+ e.target.y +"天</span>"+"<br/>"+"<span>"+ parseFloat(e.target.percentage.toFixed(1)) +"%</span>",
    						y:-5,
    						x:-95
    					}
    			);       		
    			chart.redraw();
    			e.target.select();
    			var selected_curr = chart.getSelectedPoints();
    			//console.log(selected_curr)
    			if(selected_curr.length==0){
    				chart.setTitle(
        					{
        						text: "<span style='color:#4490d2'>"+ totalDay +"天</span>"+"<br/>",
        						y:5,
        						x:-95
        					}
        			);
    			}
    			judgeProgress($.trim(e.target.name),'time');
    			return false;
    			//如果没有pie块被选择，返回到只显示数量状态。
    		
    		};
    		var chart = new Highcharts.Chart(containerProjectTimeOptions);
    	});
    }	
	cutStr(5,'cutstr');
});


function meetingDateFormat(value){
	return value==null||value==''||value=='-'||value==undefined ? '-' : new Date(value).format("yyyy-MM-dd hh:mm:ss"); 
}

//绩效考核，前5
var containerKpiOptions = {
    chart: {
    	renderTo:'container_kpi',
        zoomType: 'xy',
        backgroundColor: 'rgba(255, 255, 255, 0)',
    },
    title: {
        text: ''
    },
    //去除版权
    credits: {
        enabled:false
    },
    //去除右上角导出图标
    exporting: {
    	enabled:false
    },
    xAxis: {
//    	 plotLines: [{
//             value: -1,
//             width: 1,
//             color: '#808080'
//         }],
        lineWidth: 1,
        lineColor: "#edeff5",
        tickWidth: 0,
        labels: {
            y: 20, //x轴刻度往下移动20px
            staggerLines:1,
            style: {
                color: '#7a8798',//颜色
                fontFamily:'宋体',
            }
        }
        //categories: ['物联网', 'O2O事业部', '互联网服装', '互联网金融', '互联网工业']
    },
    yAxis: [
			{ // Secondary yAxis
			    gridLineColor: '#f6f7fa',
			    gridLineWidth: 1,
			    title: {
			        text: '项目数(个)',
			        style: {
			            color: '#7a8798'
			        }
			    },
			    labels: {
			        style: {
			            color: '#4572A7'
			        }
			    },
			},
            { // Primary yAxis
        gridLineColor: '#f6f7fa',
        gridLineWidth: 1,
        opposite: true,
        min:0,
        max:100,
        //lineColor: "#edeff5",
        //lineWidth: 1,
        labels: {
            format: '{value} %',
            style: {
                color: '#999',//颜色
                fontFamily:'宋体',  //字体
            }
        },
           title: {
                text: '过会率(%)',
                style: {
                    color: '#7a8798'
                }
            }
        }],
            plotOptions: {
        column: {
            pointWidth: 20
        },
    },
    tooltip: {
        shared: true
    },
    legend: {
        itemMarginTop:-10,
        itemMarginBottom:-10,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'top',
        borderWidth: 0,
        itemStyle:{
            fontWeight:'normal',
            color:'#7a8798',
        },
    },
    series: [{
        name: '项目数',
        color: '#9dd2fc',
        type: 'column',
        //data: [20, 18, 9, 7, 18],
        tooltip: {
            valueSuffix: '个'
        }
    },{
        lineWidth:3,
        name: '过会率',
        yAxis: 1,
        color: '#88dfd8',
        type: 'spline',
        //data: [10.5, 15.8, 60.1, 50.9, 30.3],
        tooltip: {
            valueSuffix: '%'
        }
    }]
};

//项目进度配置
var containerProgressOptions = {
    chart: {
    	renderTo:'container_progress',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        backgroundColor: 'rgba(255, 255, 255, 0)'
    },
    title: {
        text: "<span style='color:#4490d2'>"+'0个'+"</span>"+"<br/>"+"<span>"+'0%'+"</span>",
        verticalAlign:'middle',
        y:5,
        x:-95,
        style:{
            fontFamily:'微软雅黑',
            color:'#4490d2',
            fontWeight:'bold',
            cursor:'pointer'
        },
    },
    //去除版权
    credits: {
      enabled:false
    },
    //去除右上角导出图标
    exporting: {
        enabled:true
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    plotOptions: {
	    pie: {
	        borderWidth: 0,
	        allowPointSelect: true,
	        cursor: 'pointer',
	        depth: 35,
	        dataLabels: {
	            color:'black',
	            rotation: -90,
	            enabled: true,
	            formatter:function(){
	                return this.point.percentage.toFixed(1)+"%";
	            },
	            connectorWidth:0,
	            connectorPadding:0,
	            distance:120
	        },
	        showInLegend: true,
	        events :{
	        	click : function(e){
	        		
	        	}
	        },
	        point:{
	        	events:{
		        	click: function(e){
		        		//console.log(e.point.name);
		        		//this.title="123";
		        	},
		        	legendItemClick : function(e){

		        	}
		        }	
	        }
	    }
    },
	legend: {                                                 
	    layout: 'horizontal', 
	    floating: false,                                       
	    align: 'right',
	    verticalAlign: 'middle',
	    borderWidth: 0,
	    itemWidth:90,
	    width:200,
	    padding:-25,
	    //minHeight:100,
	    itemStyle:{
	        fontWeight:'normal',
	        color:'#7a8798',
	    }
	    //x:0,
	},            
    series: [{
        type: 'pie',
        size:'140%',
        innerSize :'70%',
        name: '项目进度占比',
        /*data: [
            {name:'接触访谈',color:'#c5b33b',y:8},
            {name: '内部评审',color:'#cbc63a',y: 10},
            {name:'CEO评审',color:'#bac73b',y:16},
            {name:'立项会',color:'#a6cb2b',y:20},
            {name:'投资意向书',color:'#69bf56',y: 30},
            {name:'尽职调查',color:'#58b260',y:40},
            {name:'投决会',color:'#36afa2',y:50},
            {name:'投资协议',color:'#159196',y:55},
            {name:'股权交割',color:'#4790d2',y:60},
            {name:'投后运营',color:'#3c84c6',y:90},
        ],*/
        dataLabels: {
            enabled: false, 
        }
    }]
};

//投资资金
var containerTzMoneyOptions = {
    chart: {
    	renderTo: 'container_investmentFunds',
        type: 'line'
    },
    title: {
        text: '',
        x: -20 //center
    },
    //去除版权
    credits: {
        enabled:false
    },
    //去除右上角导出图标
    exporting: {
        enabled:true
    },
    xAxis: {
        lineWidth: 1,
        lineColor: "#edeff5",
        tickWidth: 0,
        labels: {
            y: 20, //x轴刻度往下移动20px
            style: {
                color: '#7a8798',//颜色
                fontFamily:'宋体',
            }
        },
        //categories: ['201501', '201502', '201503', '201504', '201505', '201506', '201507', '201508', '201509', '201510', '201511', '201512']
    },
    yAxis: {
        gridLineColor: '#f6f7fa',
        gridLineWidth: 1,
        labels: {
            format: '{value} M',
            x: -10, //y轴刻度往左移动10px
            style: {
                color: '#999',//颜色
                fontFamily:'宋体',  //字体
            }
        },
        title: {
            text: '金额(百万)'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }],
        //tickPositions: [0, 500, 1000, 1500, 2000, 2500, 3000]
    },
    plotOptions: {
        series: {
            marker: {
                enabled: false
            }
        },
    },
    tooltip: {
        valueSuffix: '百万'
    },
    legend: {
        itemMarginTop:-10,
        itemMarginBottom:-10,
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'top',
        borderWidth: 0,
        itemStyle:{
            fontWeight:'normal',
            color:'#7a8798',
        },
    },
    series: [{
        lineWidth: 1.5,
        name: '估值',
        color:'#65ade7',
        //data: [10, 100, 500, 750,800, 900, 1000,1200, 1500, 1800,2000,3000]
    }, {
        lineWidth: 1.5,
        name: '投资金额',
        color:'#ff9c89',
        //data: [0, 90, 300, 700,700, 800, 900,1100, 1400, 1600,2000,2500]
    }]
};

function judgeProgress(name,flag){
	
	var param;
	if(typeof(name) != 'undefined'){
		if(name == '接触访谈'){
			param = 1;
		}else if(name == 'CEO评审'){
			param = 3;
		}else if(name == '投资意向书'){
			param = 5;
		}else if(name == '投资决策会'){
			param = 7;
		}else if(name == '股权交割'){
			param = 9;
		}else if(name == '内部评审'){
			param = 2;
		}else if(name == '立项会'){
			param = 4;
		}else if(name == '尽职调查'){
			param = 6;
		}else if(name == '投资协议'){
			param = 8;
		}else if(name=="投后运营"){
			param = 10;
		}
	}
	
	if(flag=='progress'){
		forwardParam.progressParam = param;
		$("#container_progress .highcharts-title tspan").click(function(){
			
			if($("#container_progress .highcharts-title tspan").text()== (totalNum_all+'个')){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.progressParam){
					url += "?forwardProgress=" + forwardParam.progressParam ;
				}
				forwardWithHeader(url);
			}
			
		})
		$("#container_progress .highcharts-title span").click(function(){
			if($("#container_progress .highcharts-title span").text()== (totalNum_all+'个')){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.progressParam){
					url += "?forwardProgress=" + forwardParam.progressParam ;
				}
				forwardWithHeader(url);
			}
		})
	}/*else{
		forwardParam.timeParam = param;
		$("#container_time .highcharts-title tspan").click(function(){
			if($("#container_time .highcharts-title tspan").text()==(totalDay_all+"天")){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.timeParam){
					url += "?forwardProgress=" + forwardParam.timeParam ;
				}
				forwardWithHeader(url);
				
			}
			
			
			
			
		})
		$("#container_time .highcharts-title span").click(function(){
			if($("#container_time .highcharts-title span").text()==(totalDay_all+"天")){
				var url = platformUrl.projectAnalysis;
				forwardWithHeader(url);
			}else{
				var url = platformUrl.projectAnalysis;
				if(forwardParam.timeParam){
					url += "?forwardProgress=" + forwardParam.timeParam ;
				}
				forwardWithHeader(url);
				
			}
		})
	}*/
	
	
	
}

function info(id){
	sendGetRequest(platformUrl.judgeRole + "/" + id, null,function(data){
		if(data.result.status!="OK"){
			return false;
		}
		var _url;
		if(data.result.message=="show"){
			var _url=platformUrl.projectStage4Manager+id;
			$.getHtml({
				url:_url,//模版请求地址
				data:""
			});
		}else{
			return false;
		}	
	});
	
}
//项目历时
var containerProjectTimeOptions = {
        chart: {
        	renderTo: 'container_time',
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            backgroundColor: 'rgba(255, 255, 255, 0)',
        },
        title: {
            text: "<span style='color:#4490d2'>"+'60天'+"</span>"+"<br/>"+"<span>"+'45%'+"</span>",
            verticalAlign:'middle',
            y:5,
            x:-90,
            style:{
                fontFamily:'微软雅黑',
                color:'#4490d2',
                fontWeight:'bold',
                cursor:'pointer'
            },
        },
        //去除版权
        credits: {
          enabled:false
        },
        //去除右上角导出图标
        exporting: {
            enabled:true
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
        pie: {
            borderWidth: 0,
            allowPointSelect: true,
            cursor: 'pointer',
            depth: 35,
            dataLabels: {
                color:'black',
                rotation: -90,
                enabled: true,
                formatter:function(){
                    return this.point.percentage.toFixed(1)+"%";
                },
                connectorWidth:0,
                connectorPadding:0,
                distance:120
            },
            showInLegend: true,
            events :{
	        	click : function(e){
	        		
	        	}
	        },
	        point:{
	        	events:{
		        	click: function(e){
		        		//console.log(e.point.name);
		        		//this.title="123";
		        	},
		        	legendItemClick : function(e){
		        		
		        	}
		        }	
	        }
        }
    },
    legend: {                                                
        layout: 'horizontal',                                  
        floating: false,                                       
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0,
        width:200,
        padding:-25,
        itemWidth:90,
        //minHeight:100,
        itemStyle:{
            fontWeight:'normal',
            color:'#7a8798',
        },
        //x:0,
    },            

        series: [{
            type: 'pie',
            size:'140%',
            innerSize :'70%',
            name: '项目历时',
            data: [
                {name:'接触访谈',color:'#c5b33b',y:8},
                {name: '内部评审',color:'#cbc63a',y: 10},
                { name:'CEO评审',color:'#bac73b',y:16},
                { name:'立项会',color:'#a6cb2b',y:20},
                { name:'投资意向书',color:'#69bf56',y: 30},
                { name:'尽职调查',color:'#58b260',y:40},
                { name:'投决会',color:'#36afa2',y:50},
                { name:'投资协议',color:'#159196',y:55},
                { name:'股权交割',color:'#4790d2',y:60},
            ],
            dataLabels: {
                enabled: false, 
            }
        }]
    };