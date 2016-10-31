
//bootstrapTable全局通用基础配置
var DefaultBootstrapTableOptions = {
	queryParamsType: 'size|page',
	pageSize:10,
	showRefresh : false ,
	sidePagination: 'server',
	url: '',
	method :'post',
	pagination: true,
	pageList: [10, 20, 50],
	search: false,
	toolbar:'',
	contentType:'application/json',
	columns:[],
	onLoadSuccess:function(response){}
};

//通用ajax数据回调
function ajaxCallback(obj,callback){
	$.ajax({
		url:obj.url,
		dataType:obj.dataType||'json',
		//contentType: obj.contentType ||"application/x-www-form-urlencoded; charset=UTF-8",
		contentType:obj.contentType ||"application/json",
		type:obj.type||'POST',
		data: JSON.stringify( obj.data||{} ),
		async : false,
		beforeSend:function(xhr){
			/**清楚浏览器缓存**/
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");
			if(sessionId){
				xhr.setRequestHeader("sessionId",sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId",userId);
			}
		},
		error : function(request) {
		},
		success:function(data){
			if(data){
				var type =typeof(data);
				if(type=='string'){
					if(data.indexOf("<!DOCTYPE html>")){
						location.href = platformUrl.toLoginPage;
					}
				}
			}
			callback.call(this,data);
		}
	});
}


/**
 * 通用弹出层列表，结合分页插件
 * @param obj
 * obj.url  非必需，弹出层的html路径
 * obj.datatable 非必需，弹出层的datatable标识（id）
 * obj.toolbar  非必需，table插件参数区
 * obj.serverUrl  必需，请求数据的url
 * obj.params  非必需，table插件筛选参数
 * obj.columns  必需，table插件列表项,例如columns: [{field:'project_code',align:'center',class:'"data-input"',title:'项目编号'},{field:'project_contribution',align:'center',class:'"data-input"',title:'投资金额（万）',formatter:'money_format'}],
 * @returns {Boolean}
 */
function ajaxPopup(obj){
	var url = obj.url || path + "/galaxy/report/popupList";
	var divid = obj.datatable || "data-table-ajax-popup"; 
	var toolbar = obj.toolbar || "#custom-toolbasr-ajax-popup";
	var serverUrl = obj.serverUrl;
	var params = obj.params || {};
	var columns = obj.columns || {};
	$.getHtml({
 		url: obj.url || (path + "/galaxy/report/popupList"),
 		data:"",
 		okback:function(){
 			for(var k in params){
 				var hiddenInput = '<input type="hidden" name="'+ k +'" id="'+ k +'" value="'+ params[k] +'">';
 				$(toolbar).append(hiddenInput);
 			}
 			$('#'+ divid).bootstrapTable('destroy');
 			$('#'+ divid).bootstrapTable({
 				queryParamsType: 'size|page',
 				pageSize:20,
 				showRefresh : false ,
 				sidePagination: 'server',
 				url: serverUrl,
 				method : 'post',
 				pagination: true,
 				pageList: [10, 20, 50],
 			    search: false,
 			    toolbar: toolbar,
 			    contentType:'application/json',
 			    columns:columns,
 			    undefinedText:' ',
 			    onLoadSuccess:function(result){
 			    	//$(toolbar).html("");
 			    }
 			});
 		}
 	});
 	return false;
}

//格式化比率
function rate_format(value, row, index){
	if(value=='undefined' || value==0 || !value){
		return "0%";
	} else if(value=='-'){
		return value;
	}else{
		value = value * 100;
		return value.toFixed(2)+"%";
	}
}

function format (num) {
    return (num.toFixed(3) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}

//格式化金额
function money_format(value,row,index){
	if(value=='undefined' || value==0 || !value || value=='-'){
		return 0;
	}else{
		//value = value/10000;
		//return $.format(value.toFixed(3));
		return format(value);
	}
}

//公司排名
function com_ranking(value,row,index){
	return value;
}


//获取部门（事业线）下拉列表
function getDeptOptionsList(targetIdObj,type){
	type = type || 1;
	var obj ={url:platformUrl.departmentList};
	obj.contentType="application/json";
	obj.data={type:type};
	ajaxCallback(obj,function(data){
		var result = data.result;
		var mapList = data.mapList;
		if(result.status=='ERROR'){
			$.popup(100,'消息',result.message);
			return false;
		}
		var options ='<option value="-1">全部</option>';
		for(var i=0;i<mapList.length;i++){
			options += '<option value="'+ mapList[i].id +'">'+ mapList[i].name  +'</option>';
		}
		for(var j=0; j<targetIdObj.length; j++){			
			$("#"+targetIdObj[j]).html(options);
		}
	});
}




//鼠标移动到帮助信息上显示浮层
//$('.overview').tooltip({
//	selector: "a[rel=tooltip]"
//})
//$('.detail').tooltip({
//	selector: "span[rel=tooltip]"
//})


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

function GetDateStrFormat(AddDayCount)
{
	var dd = new Date();
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
	var y = dd.getFullYear();
	var m = dd.getMonth()+1;//获取当前月份的日期
	var d = dd.getDate();
	m = m>9? m : '0'+m;
	d = d>9? d : '0'+d;
	
	return y+"-"+m+"-"+d;
}

var dateLimit = GetDateStr(-1);
var nowTemp = new Date();
var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
var weekLimitnow = GetDateStr(-(nowTemp.getDay())+7);
var weekLimit = GetDateStr(-(nowTemp.getDay()));

/*// 获取左侧菜单
function createMenus(current){
	sendGetRequest(platformUrl.createMenus + current, {}, function(data){
		 var selected = data.header.attachment;
	   	 var html = "";
	   	 $.each(data.entityList, function(i,o){
	   		 if(typeof(o.nodes) == "undefined"){
	   			if(selected == o.id){
		   			html += '<li class="on"><a href="' + o.url + '" data-menueid="' + o.id + '" ><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   		}else{
		   			html += '<li><a href="' + o.url + '"  data-menueid="' + o.id + '"><span class="navbar nav'+o.navNum+'"></span>' + o.menuName + '</a></li>';
		   		}
	   		 }else{
	   			var innerHtml ="";
	   			var isExend = false;
	   			 $.each(o.nodes, function(i,obj){
	   				 if(selected == obj.id){
	   					isExend = true;
	   					innerHtml += '<li class="on"><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>';
			   		 }else{
			   			innerHtml += '<li><a href="' + obj.url + '" data-menueid="' + o.id + '">' + obj.menuName + '</a></li>';
			   		 }
	   			 });
	   			 
	   			 if(isExend){
	   				html += '<li class="toggle_li on"><a href="javascript:;"><span class="navbar nav'+o.navNum+'"></span>'+o.menuName+'</a><ul style="display:block;">';
	   			 }else{
	   				html +='<li class="toggle_li"><a href="javascript:;"><span class="navbar nav'+o.navNum+'"></span>'+o.menuName+'</a><ul>';
	   			 }
	   			 html += innerHtml;
	   			 html += '</ul></li>';
	   		 }
	   	 });
	   	 $("#menus").html(html);
	   	 //投后菜单显示隐藏    
	     $(".pagebox .lft .toggle_li").click(function(event) {
	           $(this).children('ul').stop().slideToggle();
	         });
	});
}*/



$(function(){
	
	//tip
	$("[data-toggle='tooltip']").tooltip();
	
	//日期视图
	$('.datepicker').datepicker({
	    format: 'yyyy-mm-dd',
	    language: "zh-CN",
	    autoclose: true,
	    todayHighlight: false,
	    calendarWeeks: false,
	    weekStart:1,
	    today: "Today",
	    todayBtn:'linked',
	    leftArrow: '<i class="fa fa-long-arrow-left"></i>',
	    rightArrow: '<i class="fa fa-long-arrow-right"></i>',
	    endDate: '+0d',	//只能选择今天之前的日期
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

	//月份视图
	$('.change_month').datepicker({
	    language: "zh-CN",
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
			//console.log(date_start);
			//console.log(date_end);
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
});

