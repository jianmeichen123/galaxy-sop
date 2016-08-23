/**
 * 格式化时间戳
 * 此处返回yyyy年MM月dd日
 */
function time_zh(s_time, yearName, monthName, dayName){	
	var date = new Date(s_time);
	Y = date.getFullYear();
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
	D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
	h = date.getHours();
	m = date.getMinutes();
	s = date.getSeconds(); 
	if(typeof(yearName) == "undefined"){
		yearName = "-";
	}
	if(typeof(monthName) == "undefined"){
		monthName = "-";
	}
	if(typeof(dayName) == "undefined"){
		dayName = " ";
	}
	s_times = Y+yearName+M+monthName+D+dayName;
	return s_times ;
}
/**
 * 保留指定位数的小数
 * 若为0，则为0
 * 若不为0，则按照四舍五入保留指定位数的小数
 */
function fixSizeDecimal(number, size){
	if(typeof(size) == "undefined"){
		size = 2;
	}
	if(number != 0){
		return number.toFixed(size);
	}
	return 0;
}