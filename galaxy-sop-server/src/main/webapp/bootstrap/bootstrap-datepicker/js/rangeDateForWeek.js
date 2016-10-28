
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

$(function(){
	
	setDateRange(new Date(),"INIT");
	//日期视图
	$('.weekStartDatepicker').datepicker({
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
	    endDate: endTime,
	    //beforeShowDay: disableSpecificDates,
	    currentText: 'Now',
	    setDate:'2016-09-09'
	}).on('changeDate', function(ev) {
		setDateRange(ev,"START");
	});
	
	//日期视图
	$('.weekEndDatepicker').datepicker({
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
	    endDate: endTime,
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
    var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -1);
    var getWeekStartDate =  formatDate(getWeekStartDate);
    //获得本周的结束日期
    var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek-1));
    var getWeekEndDate =  formatDate(getWeekEndDate);
    
    
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
    }
    
	if(startOrEnd == "INIT"){
		current = new Date();
	}
	var start = new Date(getWeekStartDate).getTime();
	var end = new Date(getWeekEndDate).getTime();
	
    if(start < current && current < end){
    	//获得上周的开始日期
	    var getUpWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -7 -1);
	    var getUpWeekStartDate =  formatDate(getUpWeekStartDate);
	    //获得上周的结束日期
	    var getUpWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek - 7 -1));
	    var getUpWeekEndDate =  formatDate(getUpWeekEndDate);
	    endTime = getUpWeekEndDate;
	    $(".weekStartDatepicker").val(getUpWeekStartDate);
	    alert(getUpWeekStartDate);
	    alert("上周："+nowDayOfWeek)
   	    $(".weekEndDatepicker").val(getUpWeekEndDate);
    }else{
		//获得本周的开始日期
	    var getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek -1);
	    var getWeekStartDate =  formatDate(getWeekStartDate);
	    //获得本周的结束日期
	    var getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek-1));
	    var getWeekEndDate =  formatDate(getWeekEndDate);
	     endTime = getWeekEndDate;
	     alert("本周："+nowDayOfWeek)
	     
	    if(nowDayOfWeek == 6){
	    	//获得本周的开始日期
		    getWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 7 -1);
		    getWeekStartDate =  formatDate(getWeekStartDate);
		    //获得本周的结束日期
		    getWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek + 7 -1));
		    getWeekEndDate =  formatDate(getWeekEndDate);
	    }
	    $(".weekStartDatepicker").val(getWeekStartDate);
	    $(".weekEndDatepicker").val(getWeekEndDate);
    }
}
