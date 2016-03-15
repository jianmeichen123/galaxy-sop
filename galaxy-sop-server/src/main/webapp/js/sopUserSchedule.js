//首页日程数据渲染
function loadAjaxSopUserSchedule(type,url){
	$.ajax({
		url : url,
		data : null,
		async : false,
		type : 'GET',
		dataType : "json",
		contentType:"application/json; charset=UTF-8",
		cache : false,
		error:function(){     
	    }, 
		success : function(data) {
			 var json = eval(data);
			 var dataList=json.pageList.content;
			 $("#top").html("");
			 var htmlstart="";
			 for(var p in dataList){
				 htmlstart +="<a href=\"javascript:;\" class=\"link\"><b class=\"b1 null\">点</b>"+dataList[p].timeTask+"</a>";
			 }
			 $("#top").html(htmlstart);
		}
	});
}
//点击日程面板
function shecudle(){
	$.getHtml({
		url:platformUrl.toShedule,//模版请求地址
		data:"",//传递参数
			
	});
	return false;
}