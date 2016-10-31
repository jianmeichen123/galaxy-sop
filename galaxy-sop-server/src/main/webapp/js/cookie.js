function setCookie(name,value,hours,path){
		var name = escape(name);
		var value = escape(value);
		var expires = new Date();
		expires.setTime(expires.getTime() + hours * 60*60*1000);
		path = path =="" ? "":";path=" + path;
		_expires = (typeof hours) == "string" ? "": ";expires=" + expires.toUTCString();
		document.cookie = name + "=" + value + _expires + path;
		//console.log("---cookie:" + document.cookie);
	}
	function getCookieValue(name){
		var name = escape(name);
		var allcookies = document.cookie;
		name += "=";
		var pos = allcookies.indexOf(name);
		if(pos != -1){
			var start = pos + name.length;
			var end = allcookies.indexOf(";",start);
			if(end == -1){
				end = allcookies.length;
			}
			var value = allcookies.substring(start,end);
			return unescape(value);
		} else{
			return "";
		}
	}
	function deleteCookie(name,path){
		var name = escape(name);
		var expires = new Date(0);
		path = path == "" ? "" : ";path=" + path;
		document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
	}
	
	/**
	 * 获取分页显示数
	 */
	function cookies_szie(){
		var PageSize_ab = getCookieValue("PageSize_ab");
		if(PageSize_ab!=''){
			return PageSize_ab;
		}else{
        	return 10;
      	}
		
	}
	
	// 关闭浏览器后数据不会消失
	function updatePageCountNoSession(){
		// localStorage.pagecount与localStorage.getItem("pagecount")在此处均可用，
		// 不同的是：若pagecount不存在，前者返回undefined，后者返回null。
		if(localStorage.pagecount){
			localStorage.pagecount = Number(localStorage.pagecount) + 1;
		} else{
			localStorage.pagecount = 1;
			// 取值或设置值的两种方式
			// localStorage.setItem("pagecount",1);
		}
		//console.log("---localStorage pagecount:" + localStorage.getItem("pagecount"));
	}
	// 关闭网页后数据会消失
	function updatePageCountWithSession(){
		if(sessionStorage.pagecount){
			sessionStorage.pagecount = Number(sessionStorage.pagecount) + 1;
		} else{
			sessionStorage.pagecount = 1;
		}
		//console.log("---sessionStorage pagecount:" + sessionStorage.pagecount);	
	}
	$("div").delegate(".url_cookie", "click", function() {
		var href_url=window.location;
		setCookie("href_url", href_url,24,'/');
	});
	$("div").delegate(".project_name", "click", function() {
		var href_url=window.location;
		setCookie("href_url", href_url,24,'/');
	});
