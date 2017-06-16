// JavaScript Document
var projectId;
function  progress(id){
	projectId = id;
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/index",//模版请求地址
		data:"",//传递参数
		okback:function(){
			$(".close").addClass("progress_close")
		}
	});
}
/**
 * 添加访谈记录弹窗
 * @param id
 */
function  p1(id){
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/p1",//模版请求地址
		data:"",//传递参数
		okback:function(){
			
		}
	});
}