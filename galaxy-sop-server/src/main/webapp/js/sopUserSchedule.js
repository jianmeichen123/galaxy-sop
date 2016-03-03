function loadAjaxSopUserSchedule(type,url){
	$.ajax({
		url : url,
		data : null,
		async : false,
		type : 'GET',
		dataType : "json",
		contentType:"application/json; charset=UTF-8",
		cache : false,
		error:function(){     
	        alert('error');     
	    }, 
		success : function(data) {
			 var json = eval(data);
			 var dataList=json.pageList.content;
			 var htmlstart= "<b class=\"sj ico null\">三角</b>"+
	                        "<div class=\"tody ico\">"+
            	            "<p class=\"time\"></p>"+
                            "<p class=\"date\"></p>"+
                            "</div>";
			 for(var p in dataList){
				 htmlstart +="<a href=\"javascript:;\" class=\"link\"><b class=\"b1 null\">点</b>"+dataList[p].timeTask+"</a>";
			 }
			 var htmlend= "<div class=\"morebox\"><a href=\"javascript:;\" class=\"more null\">more</a></div>";
			 document.getElementById("top").innerHTML +=htmlstart+htmlend;
		}
	});
}