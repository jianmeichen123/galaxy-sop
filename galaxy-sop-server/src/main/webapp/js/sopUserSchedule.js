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
				    htmlstart+="<div style=\"font-size:12px;font-family:'宋体';text-align:center;color:#7a8798;hight:50px;line-height:50px\">无日程安排</div>";
			   }
				if(dataList.length<3){
					$("#top").siblings().children('.more').css("display","none");
				}else{
					$("#top").siblings().children('.more').css("display","block");
				}
				if (dataList.length!=0){
					 for(var ii = 0 ; ii < dataList.length ; ii++){
						 htmlstart +="<a href=\"javascript:;\" class=\"link\"><b class=\"b1 null\">点</b>"+dataList[ii].timeTask+"</a>";
					 }					
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