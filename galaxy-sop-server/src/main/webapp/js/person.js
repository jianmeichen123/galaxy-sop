/***
 * 可作为通用的tab切换
 * url:路径
 * data:项目id等参数
 */
function changeTab(url,data) {
    $.ajax({
		type:"GET",
		data:"",
		dataType:"html",
		url:url+data,
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
		success:function(html){
			$("#cur_tab").html(html);
		},
		error:function(){
			alert("网络错误")
		}	
	})
} 