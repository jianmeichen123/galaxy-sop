
function GetDateStr(AddDayCount)
{
	var dd = new Date();
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = dd.getMonth()+1;//获取当前月份的日期
	var d = dd.getDate();
	return y+"-"+m+"-"+d;
}
var dateLimit = GetDateStr(1);
var nowTemp = new Date();
var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
var weekLimitnow = GetDateStr(-(nowTemp.getDay())+7);
var weekLimit = GetDateStr(-(nowTemp.getDay()));
var createTime=$("#projectTime").val();
var projectTime='';
if(createTime !='' || createTime !=null || typeof(createTime) != "undefined"){
	projectTime=createTime;
}
//浏览器的判断
var browser=navigator.appName 
var b_version=navigator.appVersion 
var version=b_version.split(";"); 
//var trim_Version=version[1].replace(/[ ]/g,""); 
$(function(){
	setDateRange(new Date(),"INIT");
	//日期视图
	$('.visitweekStartDatepicker').datepicker({
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
	    //startDate: '2016-09-09',
	    // endDate: endTime,
	    //beforeShowDay: disableSpecificDates,
	    currentText: 'Now',
	    setDate:'2016-09-09',
	}).on('changeDate', function(ev) {
		setDateRange(ev,"START");
	});
	
	//日期视图
	$('.visitweekEndDatepicker').datepicker({
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
	    // endDate: endTime,
	    currentText: 'Now',
	    setDate:'2016-09-09'
	}).on('changeDate', function(ev) {
		setDateRange(ev,"END");
	});
	


});
//切换周:选中某一天，获得本周－到周日的日期
function UTCDate(){
	return new Date(Date.UTC.apply(Date, arguments));
}

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
}

function disableSpecificDates(date) { 
    var month = date.getMonth(); 
    var day = date.getDate(); 
    var year = date.getFullYear(); 
    for (i = 0; i < daysToDisable.length; i++) { 
        if ($.inArray((month + 1) + '-' + day + '-' + year, daysToDisable) != -1) { 
            return [false]; 
        } 
    } 
    return [true]; 
} 

function setDateRange(ev,startOrEnd){
	var now = new Date();
	/***获取本周的日期**/
	var nowYear = now.getFullYear();//当前年
	var nowMonth = now.getMonth();//当前月
	var nowDay = now.getDate();//当前日
	var nowDayOfWeek = now.getDay();//今天本周的第几天
	
	//获得本周的开始日期
    getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek +1);
    getWeekStartDate =  formatDate(getWeekStartDate);
    //获得本周的结束日期
    getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (7 - nowDayOfWeek));
    getWeekEndDate =  formatDate(getWeekEndDate);
     if(nowDayOfWeek == 0){
    	 getWeekStartDate = new Date(nowYear, nowMonth, nowDay-nowDayOfWeek-6);
		 getWeekStartDate =  formatDate(getWeekStartDate);
		 //获取本周结束时间
		 getWeekEndDate = new Date(nowYear, nowMonth, nowDay- nowDayOfWeek);
		 getWeekEndDate =  formatDate(getWeekEndDate);
     }

    var current;
    if(startOrEnd != "INIT"){
		var currentDate =ev.date.format("yyyy-MM-dd");
		current = new Date(currentDate).getTime();
		//星期几
		now = UTCDate(ev.date.getFullYear(), ev.date.getMonth(), ev.date.getDate());
		nowYear = now.getFullYear();//当前年
		nowMonth = now.getMonth();//当前月
		nowDay = now.getDate();//当前日
		nowDayOfWeek = now.getDay();//今天本周的第几天

		//获得本周的开始日期
	    getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek +1);
	    getWeekStartDate =  formatDate(getWeekStartDate);
	    //获得本周的结束日期
	    getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (7- nowDayOfWeek));
	    getWeekEndDate =  formatDate(getWeekEndDate);
	     if(nowDayOfWeek == 0){
	    	 getWeekStartDate = new Date(nowYear, nowMonth, nowDay-nowDayOfWeek-6);
			 getWeekStartDate =  formatDate(getWeekStartDate);
			 //获取本周结束时间
			 getWeekEndDate = new Date(nowYear, nowMonth, nowDay- nowDayOfWeek);
			 getWeekEndDate =  formatDate(getWeekEndDate);
	     }
    }
    $(".visitweekStartDatepicker").val(getWeekStartDate);
    $(".visitweekEndDatepicker").val(getWeekEndDate);
}
