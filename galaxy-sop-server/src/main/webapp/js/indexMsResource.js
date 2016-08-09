/**
 * 秘书首页模块特殊处理
 * 
 */

var msResourceUtils = {
		specialDeal : function(){
//			layer.msg(roleId);
			if(roleId == 18 || roleId == 19){
				$("#ceo_cat").find("#title_ceopq").html("<h3 class='ico t5'>CEO评审排期</h3>");
				var html = $("#position_7").html();
				$("#position_7").html("");
				$("#position_5").html(html);
				//ceo秘书排期弹窗
				$("#ceo_cat").find("[data-btn='ceops']").on("click",function(){
					var $self = $(this);
					var _url = $self.attr("href");
					$.getHtml({
						url:_url,//模版请求地址
						data:"",//传递参数
						okback:function(){
							showList2();
							}//模版反回成功执行	
					});
					return false;
				});
			}else{
				$("#ceo_cat").find("#title_ceopq").html("CEO评审排期");
			}
		}
}