$("[data-btn='add_history']").on("click",function(){ 
	var $self = $(this);
	var _url =platformUrl.addFinanceHistory;
	var _name= $self.attr("data-name");
	$.getHtml({
		url:_url,//模版请求地址
		data:"",//传递参数
		okback:function(){
			$("#popup_name").html(_name);
		}//模版反回成功执行	
	});
	return false;
});