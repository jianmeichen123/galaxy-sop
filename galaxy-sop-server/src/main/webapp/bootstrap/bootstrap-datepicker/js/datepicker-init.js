
//鼠标移动到帮助信息上显示浮层
//$('.overview').tooltip({
//	selector: "a[rel=tooltip]"
//})
//$('.detail').tooltip({
//	selector: "span[rel=tooltip]"
//})
// 格式化日期
Date.prototype.format = function(format){
	var o = { 
			"M+" : this.getMonth()+1, //month 
			"d+" : this.getDate(),    //day 
			"h+" : this.getHours(),   //hour 
			"m+" : this.getMinutes(), //minute 
			"s+" : this.getSeconds(), //second 
			"q+" : Math.floor((this.getMonth()+3)/3),  //quarter 
			"S" : this.getMilliseconds() //millisecond 
	} 
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1, 
			(this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	for(var k in o)if(new RegExp("("+ k +")").test(format)) 
		format = format.replace(RegExp.$1, 
				RegExp.$1.length==1 ? o[k] : 
					("00"+ o[k]).substr((""+ o[k]).length)); 
	return format; 
}

// 格式化数字
$.extend({
	format : function(str, step, splitor) {
		step = 3;
		splitor = ',';
		str = str.toString();
		var len = str.length;
		if(len > step) {
			 var l1 = len%step, 
				 l2 = parseInt(len/step),
				 arr = [],
				 first = str.substr(0, l1);
			 if(first != '') {
				 arr.push(first);
			 };
			 for(var i=0; i<l2 ; i++) {
				 arr.push(str.substr(l1 + i*step, step));
			 };
			 str = arr.join(splitor);
		 };
		 return str;
	}
});
function GetDateStr(AddDayCount)
{
	var dd = new Date();
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = dd.getMonth()+1;//获取当前月份的日期
	var d = dd.getDate();
	return y+"-"+m+"-"+d;
}
function GetDateYearStr(AddDayCount)
{
	var dd = new Date();
	var y = dd.getFullYear();
	return y;
}

var dateLimit = GetDateStr(-1);
var dateadd=GetDateStr(-0);
var enddata = GetDateYearStr();
var nowTemp = new Date();
var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
var weekLimitnow = GetDateStr(-(nowTemp.getDay())+7);
var weekLimit = GetDateStr(-(nowTemp.getDay()));


$(function(){
	//日期视图
	$('.datepicker').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    defaultDate : Date,
	    //weekStart:1,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now',
	    setDate:'2016-09-09'
	    //defaultViewDate: new Date()
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	    //startDate: '-3d',
	    //endDate: '+3d'
	});
	$('.datepicker').datepicker("setDate",new Date());
	//日期视图--限制选择当天之前的日期
	$('.end-datepicker').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    defaultDate : new Date(),
	    //weekStart:1,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    endDate: dateLimit,	//只能选择今天之前的日期
	    forceParse:false
	    //defaultViewDate: { year: 1977, month: 04, day: 25 },
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	    //startDate: '-3d',
	    //endDate: '+3d'
	});
	//日期视图--限制选择当天之后的日期
	$('.start-datepicker').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    defaultDate : new Date(),
	    //weekStart:1,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    startDate: dateLimit,	//只能选择今天之后的日期
	    forceParse:false
	    //defaultViewDate: { year: 1977, month: 04, day: 25 },
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	    //startDate: '-3d',
	    //endDate: '+3d'
	});
	$('.start-datepicker').datepicker("setDate",new Date());
	//月份视图
	$('.change_month').datepicker({
		format: "yyyy-mm",
		minViewMode: 1,
		autoclose: true,
		endDate: dateLimit
	}).on('changeDate', function(ev){
		var today = new Date();
		var time = ev.date.valueOf();
		if (today.valueOf() > time+86400000){
			var date = new Date(time).format("yyyy-MM");
			$(this).val(ev.date);
		}
	});

	//切换周:选中某一天，获得本周－到周日的日期
	function UTCDate(){
		return new Date(Date.UTC.apply(Date, arguments));
	}
	$('#change_week').datepicker({
		language: "zh-CN",
		format: "yyyy-mm-dd",
		//weekStart: 1,
		calendarWeeks: true,
		autoclose: true,
		endDate: weekLimit
	}).on('changeDate', function(ev){
		var today = new Date();
		var time = ev.date.valueOf();
		if (today.valueOf() > time+86400000){
			var viewDate = UTCDate(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate()),
			// Start of select week: based on weekstart/select date
			weekstart = 1,
			ws = new Date(+viewDate + (weekstart - viewDate.getUTCDay() - 7) % 7 * 864e5),
			// Thursday of this week
			th = new Date(+ws + (7 + 4 - ws.getUTCDay()) % 7 * 864e5),
			// First Thursday of year, year from thursday
			yth = new Date(+(yth = UTCDate(th.getUTCFullYear(), 0, 1)) + (7 + 4 - yth.getUTCDay())%7*864e5),
			// Calendar week: ms between thursdays, div ms per day, div 7 days
			calWeek =  (th - yth) / 864e5 / 7 + 1,
			// End of this week
			we = new Date(+ws + (7 + 7 - ws.getUTCDay()) % 7 * 864e5);
			
			var date_start = ws.format("yyyy-MM-dd");
			var date_end = we.format("yyyy-MM-dd");
			//var date = new Date(time).format("yyyy-MM-dd");
		}
	});
	//默认周起始日期为周一，选中当周所有日期
	$("#change_week").click(function(){
		$(".day.active").siblings(".day").addClass("active");;
	});

	//周区间选择
	$('#week_start_calendar').datepicker({
		language: "zh-CN",
		format: "yyyy-mm-dd",
		//weekStart: 1,
		calendarWeeks: true,
		autoclose: true,
		endDate: weekLimit
	}).on('changeDate', function(ev) {
		$("#handle").val("next");
		var today = new Date();
		var time = ev.date.valueOf();
		if (today.valueOf() > time+86400000){
			var viewDate = UTCDate(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate()),
			// Start of select week: based on weekstart/select date
			weekstart = 1,
			ws = new Date(+viewDate + (weekstart - viewDate.getUTCDay() - 7) % 7 * 864e5),
			// End of this week
			we = new Date(+ws + (7 + 7 - ws.getUTCDay()) % 7 * 864e5);
			
			var date_start = ws.format("yyyyMMdd");
			var date_end = we.format("yyyyMMdd");
			
			$("#week_start").val(date_start+"-"+date_end);
			$('#week_start_calendar').attr('data-date',ev.date.format("yyyy-MM-dd"));
			$('#week_start_calendar').html(date_start+"-"+date_end+" <b class=\"caret\"></b>");
		}
	});
	//默认周起始日期为周一，选中当周所有日期
	$("#week_start_calendar").click(function(){
		$(".day.active").siblings(".day").addClass("active");
	});

	$('#week_end_calendar').datepicker({
		language: "zh-CN",
		format: "yyyy-mm-dd",
		//weekStart: 1,
		calendarWeeks: true,
		autoclose: true,
		endDate: weekLimit
	}).on('changeDate', function(ev) {
		$("#handle").val("prev");
		var today = new Date();
		var time = ev.date.valueOf();
		if (today.valueOf() > time+86400000){
			var viewDate = UTCDate(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate()),
			// Start of select week: based on weekstart/select date
			weekstart = 1,
			ws = new Date(+viewDate + (weekstart - viewDate.getUTCDay() - 7) % 7 * 864e5),
			// End of this week
			we = new Date(+ws + (7 + 7 - ws.getUTCDay()) % 7 * 864e5);
			
			var date_start = ws.format("yyyyMMdd");
			var date_end = we.format("yyyyMMdd");
			
			$("#week_end").val(date_start+"-"+date_end);
			$('#week_end_calendar').attr('data-date',ev.date.format("yyyy-MM-dd"));
			$('#week_end_calendar').html(date_start+"-"+date_end+" <b class=\"caret\"></b>");
		}
	});
	$("#week_end_calendar").click(function(){
		$(".day.active").siblings(".day").addClass("active");;
	});

	$('#week_end_calendar_now').datepicker({
		language: "zh-CN",
		format: "yyyy-mm-dd",
		//weekStart: 1,
		calendarWeeks: true,
		autoclose: true,
		endDate: weekLimitnow
	}).on('changeDate', function(ev) {
		$("#handle").val("prev");
		var today = new Date();
		var time = ev.date.valueOf();
		var viewDate = UTCDate(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate()),
		// Start of select week: based on weekstart/select date
		weekstart = 1,
		ws = new Date(+viewDate + (weekstart - viewDate.getUTCDay() - 7) % 7 * 864e5),
		// End of this week
		we = new Date(+ws + (7 + 7 - ws.getUTCDay()) % 7 * 864e5);
		
		var date_start = ws.format("yyyyMMdd");
		var date_end = we.format("yyyyMMdd");
		$("#week_end").val(date_start+"-"+date_end);
		$('#week_end_calendar_now').attr('data-date',ev.date.format("yyyy-MM-dd"));
		$('#week_end_calendar_now').html(date_start+"-"+date_end+" <b class=\"caret\"></b>");
	});
	$("#week_end_calendar_now").click(function(){
		$(".day.active").siblings(".day").addClass("active");;
	});
	//日期视图
	$('.datepicker-text').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    //defaultDate : new Date(),
	    //weekStart:1,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now',
	    //defaultViewDate: new Date()
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	  //  startDate: projectTime,
	    endDate: dateadd,
	    defaultDate : new Date(),
	   // setDate:"Today"
	    //endDate: '+3d'
	});
	//日期视图
	$('.datepicker-year-text').datepicker({
	    format: 'yyyy',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    //defaultDate : new Date(),
	    //weekStart:1,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now',
	    minViewMode: 2,
	    //defaultViewDate: new Date()
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	  //  startDate: projectTime,
	    //endDate: enddata,
	   // startDate : '1900',
	    defaultDate : new Date(),
	   // setDate:"Today"
	    //endDate: '+3d'
	});
	//日期视图
	$('.datepicker-month-start-text').datepicker({
	    format: 'yyyy-mm',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    //defaultDate : new Date(),
	    //weekStart:1,
	    today: "Today",
	    //upToNowBtn:true,
	    //todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now',
	    minViewMode: 1,
	    orientation: "bottom left",
	    //defaultViewDate: new Date()
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	  //  startDate: projectTime,
	    //endDate: enddata,
	   // startDate : '1900',
	    defaultDate : new Date(),
	   // setDate:"Today"
	    //endDate: '+3d'
	});
	
	//日期视图
	$('.datepicker-month-text').datepicker({
	    format: 'yyyy-mm',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    //calendarWeeks: true,
	    //defaultDate : new Date(),
	    //weekStart:1,
	    today: "Today",
	    upToNowBtn:true,
	    //todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    forceParse:false,
	    currentText: 'Now',
	    minViewMode: 1,
	    orientation: "bottom left",
	    //defaultViewDate: new Date()
	    //daysOfWeekDisabled: "0",
	    //daysOfWeekHighlighted: "0",
	    //clearBtn: true,
	    //startView: 1, //0,month 1,year 2,decade
	    //minViewMode: 1,  //0,days 1,month 2,years
	    //maxViewMode: 1, //0,days 1,month 2,years
	  //  startDate: projectTime,
	    //endDate: enddata,
	   // startDate : '1900',
	    defaultDate : new Date(),
	   // setDate:"Today"
	    //endDate: '+3d'
	});
	//$('.datepicker-text').datepicker("setDate",dateadd);
});
