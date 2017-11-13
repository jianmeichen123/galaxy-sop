function judgeBrower(){
	  //application/vnd.chromium.remoting-viewer 可能为360特有
    var is360 = _mime("type", "application/vnd.chromium.remoting-viewer");
    if (isChrome() && is360) { 
        return "360";
    }else{
         var result=myBrowser();
         return result;
    }
}
//检测是否是谷歌内核(可排除360及谷歌以外的浏览器)
function isChrome(){
    var ua = navigator.userAgent.toLowerCase();

    return ua.indexOf("chrome") > 1;
}
//测试mime
function _mime(option, value) {
    var mimeTypes = navigator.mimeTypes;
    for (var mt in mimeTypes) {
        if (mimeTypes[mt][option] == value) {
            return true;
        }
    }
    return false;
}

	function myBrowser(){
	var userAgent =window.navigator.userAgent; //取得浏览器的userAgent字符串
	var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
	var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
	var isSafari = userAgent.indexOf("Safari") > -1; //判断是否Safari浏览器
	var isChrome = userAgent.indexOf("Chrome") > -1; //判断是否Opera浏览器
	var isQQBrowser=userAgent.indexOf("QQBrowser") > -1; 
	var isEdge = userAgent.indexOf("Edge") > -1 && isChrome;
	
		
	var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : 0;
    
    if (Sys.ie) {
    	return 'IE: ' + Sys.ie;
    }else if (isFF) {
    	return "Firefox";
	}else if (isOpera) {
		return "Opera";
	}else if(isChrome && !isQQBrowser && isSafari&&!isEdge){
		return "Chrome";
	}else if(isQQBrowser && isChrome && isSafari){
		return 'QQBrowser';
	}else if(isSafari && !isQQBrowser && !isChrome){
		return "isSafari";
	}else if(isEdge){
	    return "isEdge";
	}else{
		return "other";
	}
	

}

/**
 * 埋点工功能相关js开始
 * @param code
 */
function buryPoint(code){
	var url=Constants.sopEndpointURL+"/galaxy/common/buryPoint";
	  var pCode=code;
	  var userId=userId;
      var recordDate=Date.parse(new Date());
      var os=1;
      var osType="";
      var osVersion=judgeBrower();
      var hardware="";
      var softVersion="";
	  var data={
			  "pCode":pCode,
			  "userId":userId,
			  "recordDate":recordDate,
			  "os":os,
			  "osType":osType,
			  "osVersion":osVersion,
			  "hardware":hardware, 
			  "softVersion":softVersion
	  };
	  sendPostRequestByJsonObj(url,data,function(resultDate){
	  });
	}


