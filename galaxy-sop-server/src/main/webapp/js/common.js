/**
 * 发送post请求
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonObj
 *            请求json对象
 * @param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendPostRequestByJsonObj(reqUrl, jsonObj, callbackFun, sessionId) {
	$.ajax({
		url : reqUrl,
		type : "POST",
		data : JSON.stringify(jsonObj),
		dataType : "json",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			xhr.setRequestHeader("Access-Control-Allow-Headers",
					"X-Requested-With");
			xhr
					.setRequestHeader("Access-Control-Allow-Headers",
							"Content-Type");
		},
		async : false,
		error : function(request) {
			alert("connetion error");
		},
		success : function(data) {
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}

/**
 * 发送post请求
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonStr
 *            请求json字符串
 * @param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendPostRequestByJsonStr(reqUrl, jsonStr, callbackFun, sessionId) {
	sendPostRequestByJsonObj(reqUrl, JSON.parse(jsonStr), callbackFun,
			sessionId);
}

/**
 * 发送get请求
 * 
 * @param reqUrl
 *            请求地址
 * @param jsonObj
 *            请求json对象
 * @param sessionId
 *            请求头中需携带的sessionid
 * @param callbackFun
 *            处理成功后的回调方法
 */
function sendGetRequest(reqUrl, jsonObj, callbackFun, sessionId) {
	$.ajax({
		url : reqUrl,
		type : "GET",
		data : jsonObj,
		dataType : "json",
		cache : false,
		contentType : "application/json; charset=UTF-8",
		beforeSend : function(xhr) {
			if (sessionId) {
				xhr.setRequestHeader("sessionId", sessionId);
			}
			xhr.setRequestHeader("Access-Control-Allow-Headers",
					"X-Requested-With");
			xhr
					.setRequestHeader("Access-Control-Allow-Headers",
							"Content-Type");
		},
		async : false,
		error : function(request) {
			alert("connetion error");
		},
		success : function(data) {
			if (callbackFun) {
				callbackFun(data);
			}
		}
	});
}

/**
 * 使用localstage存储数据 <br/>
 * 注意：IE、Firefox测试的时候需要把文件上传到服务器上（或者localhost），直接点开本地的HTML文件，是不行的。
 * 
 * DataStrore.
 */
DataStrore = {
	storage : window.localStorage,
	checkBrowerSupport : function() {
		if (window.localStorage) {
			alert('This browser supports localStorage');
			return true;
		} else {
			alert('This browser does not support localStorage');
			return false;
		}
	},
	addElement : function(key, value) {
		dataStrore.stroage.setItem(key, value);
	},
	getElement : function(key) {
		dataStrore.stroage.getItem(key);
	},
	removeElement : function(key) {
		dataStrore.stroage.removeItem(key);
	},
	removeAll : function() {
		dataStrore.stroage.clear();
	},
	showKeysAndValues : function() {
		for (var i = 0; i < dataStrore.storage.length; i++) {
			document.write(storage.key(i) + " : "
					+ storage.getItem(storage.key(i)) + "<br>");
		}
	}
}
