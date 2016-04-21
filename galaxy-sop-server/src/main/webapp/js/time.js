// JavaScript Document
(function($){
	//日期和时间
	$.fn.today = function(options){
		if($(this).length==0){return false;}
		var $this = $(this);
		var opts = $.extend({
			time:".time",
			date:".date"	
		},options);
		var _time = $this.find(opts.time),
			_date = $this.find(opts.date);
		function checkTime(i){
		  if(i<10){i="0"+ i}
		  return i
		}
		function week(i){
			switch(i){
				case 0:
					return "周末";
				break;	
				case 1:
					return "周一";
				break;	
				case 2:
					return "周二";
				break;	
				case 3:
					return "周三";
				break;	
				case 4:
					return "周四";
				break;	
				case 5:
					return "周五";
				break;	
				case 6:
					return "周六";
				break;	
			}	
		}
		function getdate(){
			var nowDate  = new Date(),
				nyr = nowDate.getFullYear()+"年"+(nowDate.getMonth()+1)+"月"+nowDate.getDate()+"日&nbsp;"+week(nowDate.getDay()),
				sfm = checkTime(nowDate.getHours())+":"+checkTime(nowDate.getMinutes())+":"+checkTime(nowDate.getSeconds());
			_time.html(sfm);
			_date.html(nyr);
			window.setTimeout(getdate,500);
		}
		getdate();
	};	
	
})(jQuery);