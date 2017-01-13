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
 * 去掉小数后面多余的0.1000
 */
function fixSizeDecimal(number, size){
	if(typeof(size) == "undefined"){
		size = 2;
	}
	if(number != 0){
		var n = number.toFixed(size);
		
		var retu = parseFloat(n);
		
		return retu;
	}
	return 0;
}

/**
 * 处理数字整数，每三位加逗号分隔
 * addCommas(1000)   // 1,000
 * addCommas(1231.897243)  // 1,231.897243
 */
function addCommas(nStr){
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

/**
 * 去除千分位
 *@param{Object}num
 */
 
function delCommas(num){
  if($.trim(num+"")==""){
   return"";
  }
  num=num.replace(/,/gi,'');
  console.log(num);
  return num;
}

/**
 * 保留指定位数的小数
 * 若为0，则为0
 * 若不为0，则按照四舍五入保留指定位数的小数
 */
function fixSizeTwo(numberOld, size){
	if(typeof(size) == "undefined"){
		size = 2;
	}
	var number=numberOld.toString();
	if(number != 0){
	  var index=number.indexOf(".");
		if(index>0){
			number=number.substr(0, index+size+1);
		}
		return number;
	}
	return 0;
}

