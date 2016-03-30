//首页日程数据渲染
function loadAjaxSopUserSchedule(url){
	$.ajax({
		url : url,
		data : null,
		async : false,
		type : 'GET',
		dataType : "json",
		contentType:"application/json; charset=UTF-8",
		cache : false,
		beforeSend : function(xhr) {
			/**清楚浏览器缓存**/
			xhr.setRequestHeader("If-Modified-Since","0"); 
			xhr.setRequestHeader("Cache-Control","no-cache");
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			if(userId){
				xhr.setRequestHeader("guserId", userId);
			}
		},
		error:function(){     
	    }, 
		success : function(data) {
			 var json = eval(data);
			 var dataList=json.pageList.content;
			 $("#top").html("");
			 var htmlstart="";
			 if (dataList.length==0) {
					var tbodyList = $("#top"); 
					var noData =
				
						 '<a >'+'没有找到匹配的记录'+'</a>';
								
					tbodyList.append(noData);
			   }
				if(dataList.length<3){
					$("#top").siblings().children('.more').css("display","none");
				}
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