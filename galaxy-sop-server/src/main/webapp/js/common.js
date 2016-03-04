/**
 * 发送ajax请求
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonObj
 *            请求json对象
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendAjaxRequest(url, method, params, callback) {
	$.ajax({
		url : url,
		type : method,
		data : params,
		dataType : "json",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		async : false,
		error : function(request) {
		},
		success : function(data) {
			if (callback) {
				callback(data);
			}
		}
	});
}