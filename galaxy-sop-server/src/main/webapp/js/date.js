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