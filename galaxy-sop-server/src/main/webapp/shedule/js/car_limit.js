function getXHNumber(tDate,sDate)
{
	var nDayNum = tDate.getDay() == 0 ? 7 : tDate.getDay();
	if(nDayNum>5) return nDayNum;
	var nDiff = (tDate - sDate)/1000/3600/24/7/13;
	nDiff = Math.floor(nDiff) % 5;
	nDayNum = 5 - nDiff + nDayNum ;
	if(nDayNum>5) nDayNum -= 5;
	return nDayNum;	
}
//获取今天的日期
var dd = new Date();
var y = dd.getFullYear(); 
var m = dd.getMonth()+1;//获取当前月份的日期 
var d = dd.getDate();
if(m<10){
m="0"+m;
}
if(d<10){
d="0"+d;
}
var sday = y+"-"+m+"-"+d;

var sStartDate = '2012-10-08';//开始星期，周一的日期
var x1 = '1、6';
var x2 = '2、7';
var x3 = '3、8';
var x4 = '4、9';
var x5 = '5、0';
var x6 = '不限行';
var x7 = '不限行';
var arr1=sStartDate.split("-");
var vStartDate = new Date(arr1[0],arr1[1]-1,arr1[2]);
var arr2 = sday.split("-");
var vToday = new Date(arr2[0],arr2[1]-1,arr2[2]);
var nTodayNum = getXHNumber(vToday,vStartDate);
vToday.setDate(vToday.getDate()+1);
var nTomorrowNum = getXHNumber(vToday,vStartDate);
/***start week***/
var arr_week=new Array("星期六","星期日","星期一","星期二","星期三","星期四","星期五");
var todayweek=vToday.getDay();
document.getElementById("todayweek").innerHTML='今日';
document.getElementById("tomorrowweek").innerHTML='明日';
/***end week***/
document.getElementById("todaynum").innerHTML = eval('x'+nTodayNum);
document.getElementById("tomorrownum").innerHTML = eval('x'+nTomorrowNum);

