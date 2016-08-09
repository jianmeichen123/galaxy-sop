var totalNum_all='';
var totalDay_all='';
$(function() {
	//meetingShedule(),
	createMenus(1);
	top5ProjectMeeting();
	top5CeoPsMeeting();
	ProjectVoteWill();
	selectSopTask();
	
	initHref();
	
	//事项预览
	matterPreviewUtils.init();
	//绩效考核图表
	load_data_chart_kpi();
	/*新版项目进度图表*/
	var progressFormdata = {
			domid : 'container_progress'
	}
	chartProjectProgressUtils.init(progressFormdata);
	/*新版投资资金图表*/
	var investFormdata = {
			domid : 'charts_investment'
	};
	chartsInvestmentUtils.init(investFormdata);
	//项目历时
	load_data_chart_project_time();
	
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
	

	
	
	


	(isCEO=='true' || isHHR=='true') ? $("#ceo_cat").show() : $("#ceo_cat").hide();
	isDSZ=='true' ? $("#dsz_cat").show() : $("#dsz_cat").hide();
	
	function initHref(){
		//console.log('initHref');
		//$("#platform_jxkh_more").attr('href' , path + "/galaxy/report/kpi?guid="+userId+"&sid="+sessionId +"#gg_jxkh_u");//绩效考核链接
		$("#more_xmjd").attr('href' , path + "/galaxy/report/projectAnalysis?guid="+userId+"&sid="+sessionId);//项目进度
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

		
	cutStr(5,'cutstr');
});


function meetingDateFormat(value){
	return value==null||value==''||value=='-'||value==undefined ? '-' : new Date(value).format("yyyy-MM-dd hh:mm:ss"); 
}

//绩效考核，前5

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
