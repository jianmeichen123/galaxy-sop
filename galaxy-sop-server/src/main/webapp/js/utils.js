/**
 * 格式化时间戳
 * 此处返回yyyy年MM月dd日
 */
function time_zh(s_time){	
	var date = new Date(s_time);
	Y = date.getFullYear();
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
	D = date.getDate();
	h = date.getHours();
	m = date.getMinutes();
	s = date.getSeconds(); 
	s_times = Y+"年"+M+"月"+D+"日";
	return s_times ;
}
/**
 * 保留两位小数
 * 若为0，则为0
 * 若不为0，则按照四舍五入保留两位小数
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