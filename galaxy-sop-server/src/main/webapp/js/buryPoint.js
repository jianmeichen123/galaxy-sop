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
			if (isIE) {
			var IE5 = IE55 = IE6 = IE7 = IE8 =IE9= false;
			var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
			reIE.test(userAgent);
			var fIEVersion = parseFloat(RegExp["$1"]);
			IE55 = fIEVersion == 5.5;
			IE6 = fIEVersion == 6.0;
			IE7 = fIEVersion == 7.0;
			IE8 = fIEVersion == 8.0;
			IE9 = fIEVersion == 9.0;
			 IE10 = fIEVersion == 10.0;
			 IE11 = fIEVersion == 11.0;
				if (IE55) {
				   return "IE55" ;
				}
				if (IE6) {
				   return "IE6" ;
				}
				if (IE7) {
				   return "IE7" ;
				}
				if (IE8) {
				   return "IE8" ;
				}
				if (IE9) {
				   return "IE9" ;
				}
				if (IE11) {
				  return "IE11";
				}
		}//isIE end
		if (isFF) {
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
//	/alert(Constants.buryEndpointURL);
	var url=Constants.sopEndpointURL+"/galaxy/common/buryPoint";
	  var pCode=code;
	  var userId=userId;
      var recordDate=Date.parse(new Date());
      var os=1;
      var osType="";
      var osVersion=judgeBrower();
      alert(osVersion);
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


