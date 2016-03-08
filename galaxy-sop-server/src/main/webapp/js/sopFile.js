function init(){
	var modal = {
			initModal : function(){
				$("[data-btn='archives']").on("click",function(){
					alert(111);
					var $self = $(this);
					var _url = $self.attr("href");
					$.getHtml({
						url:_url,//模版请求地址
						data:"",//传递参数
						okback:function(){}//模版反回成功执行	
					});
					return false;
				});
			}
	};
	
}

$(document).ready(init());