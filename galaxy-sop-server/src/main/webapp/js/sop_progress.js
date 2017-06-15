// JavaScript Document
function  progress(id){
	$.getHtml({
		url:Constants.sopEndpointURL + "/galaxy/progress/index",//模版请求地址
		data:"",//传递参数
		okback:function(){
			$(".close").addClass("progress_close")
		}
	});
}
